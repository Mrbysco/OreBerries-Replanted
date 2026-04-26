package com.mrbysco.oreberriesreplanted.blockentity;

import com.mrbysco.oreberriesreplanted.OreberriesReplanted;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

public class VatBlockEntity extends BlockEntity {
	public final FluidStacksResourceHandler tank = new FluidStacksResourceHandler(1, 3200) {

		@Override
		protected void onContentsChanged(int index, FluidStack previousContents) {
			refreshClient();
		}

		@Override
		public boolean isValid(int index, FluidResource resource) {
			if (level != null && level instanceof ServerLevel serverLevel) {
				for (RecipeHolder<VatRecipe> recipe : serverLevel.recipeAccess().recipeMap().byType(OreBerryRecipes.VAT_RECIPE_TYPE.get())) {
					if (resource.getFluid().isSame(recipe.value().getFluid())) {
						return true;
					}
				}
			}
			return false;
		}
	};

	public final ItemStacksResourceHandler handler = new ItemStacksResourceHandler(1) {
		@Override
		protected int getCapacity(int index, ItemResource resource) {
			return 32;
		}

		@Override
		public boolean isValid(int index, ItemResource resource) {
			if (level != null && level instanceof ServerLevel serverLevel) {
				for (RecipeHolder<VatRecipe> recipe : serverLevel.recipeAccess().recipeMap().byType(OreBerryRecipes.VAT_RECIPE_TYPE.get())) {
					if (recipe.value().getIngredient().test(resource.toStack())) {
						return true;
					}
				}
			}
			return false;
		}

		@Override
		protected void onContentsChanged(int index, ItemStack previousContents) {
			refreshClient();
		}
	};

	protected RecipeHolder<VatRecipe> curRecipe;

	private int evaporateProgress;
	private int evaporateTotalTime;
	private int crushCooldown = -1;

	public VatBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
		super(blockEntityType, pos, state);
	}

	public VatBlockEntity(BlockPos pos, BlockState state) {
		this(OreBerryRegistry.VAT_BLOCK_ENTITY.get(), pos, state);
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);

		this.evaporateProgress = input.getIntOr("evaporateProgress", 0);
		this.evaporateTotalTime = input.getIntOr("evaporateTotalTime", 0);
		this.crushCooldown = input.getIntOr("crushCooldown", 0);

		this.handler.deserialize(input.childOrEmpty("ItemStackHandler"));
		this.tank.deserialize(input);
	}

	@Override
	public void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);

		output.putInt("evaporateProgress", this.evaporateProgress);
		output.putInt("evaporateTotalTime", this.evaporateTotalTime);
		output.putInt("crushCooldown", this.crushCooldown);

		handler.serialize(output.child("ItemStackHandler"));
		tank.serialize(output);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, VatBlockEntity vatBlockEntity) {
		if (level.isClientSide()) {
			return;
		}

		if (vatBlockEntity.crushCooldown > 0) {
			--vatBlockEntity.crushCooldown;
		}
		if (vatBlockEntity.tank.getAmountAsInt(0) > 0) {
			RecipeHolder<VatRecipe> vatRecipe = vatBlockEntity.getRecipe();
			boolean valid = vatBlockEntity.canEvaporate(vatRecipe);
			if (valid) {
				if (vatBlockEntity.evaporateTotalTime == 0) {
					vatBlockEntity.evaporateTotalTime = vatBlockEntity.getMaxEvaporateTime();
					vatBlockEntity.evaporateProgress = 0;
				}
				vatBlockEntity.evaporateProgress++;

				if (vatBlockEntity.evaporateProgress < vatBlockEntity.evaporateTotalTime) {
					return;
				}
				vatBlockEntity.evaporateProgress = 0;
				vatBlockEntity.evaporateTotalTime = vatBlockEntity.getMaxEvaporateTime();
				vatBlockEntity.evaporateLiquid(vatRecipe);

				vatBlockEntity.refreshClient();
			}
		}
	}

	protected void evaporateLiquid(RecipeHolder<VatRecipe> recipe) {
		if (recipe != null) {
			int evaporationAmount = recipe.value().getEvaporationAmount();
			ItemStack outputStack = curRecipe.value().assemble(null);

			try (Transaction tx = Transaction.openRoot()) {
				FluidResource fluidResource = tank.getResource(0);
				int extracted = tank.extract(fluidResource, evaporationAmount, tx);
				if (extracted != evaporationAmount) return;
				tx.commit();
			}

			BlockPos blockpos = this.getBlockPos();
			Containers.dropItemStack(this.level, (double) blockpos.getX(), (double) blockpos.getY() + 0.1D, (double) blockpos.getZ(), outputStack);
			level.playSound((Player) null, worldPosition, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 0.5F, 1.0F);
		}
	}

	protected void refreshClient() {
		this.setChanged();
		this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
	}

	protected boolean canEvaporate(@Nullable RecipeHolder<VatRecipe> recipe) {
		if (tank.getAmountAsInt(0) > 0 && recipe != null) {
			return tank.getAmountAsInt(0) >= recipe.value().getEvaporationAmount();
		}
		return false;
	}

	public void crushBerry() {
		if (!isOnCooldown()) {
			try (Transaction tx = Transaction.openRoot()) {
				ItemResource berryStack = handler.getResource(0);
				RecipeHolder<VatRecipe> holder = getRecipe();
				if (holder != null && !berryStack.isEmpty()) {
					VatRecipe recipe = holder.value();
					int liquidAmount = level.getRandom().nextInt((int) (recipe.getMax() * 100) - (int) (recipe.getMin() * 100)) + (int) (recipe.getMin() * 100);
					liquidAmount = (int) Math.round(liquidAmount / 10.0) * 10;
					FluidStack stack = new FluidStack(recipe.getFluid(), liquidAmount);
					int insertedFluid = tank.insert(FluidResource.of(stack), stack.getAmount(), tx);
					if (insertedFluid > 0) {
						if (handler.extract(berryStack, 1, tx) != 1) return;
						tx.commit();
					}
				}
				setCooldown(20);
			}
		}
	}

	public void addBerry(ItemEntity entity) {
		ItemStack itemstack = entity.getItem().copy();
		int originalCount = itemstack.getCount();

		try (Transaction tx = Transaction.openRoot()) {
			int inserted = handler.insert(ItemResource.of(itemstack), itemstack.getCount(), tx);
			itemstack.shrink(inserted);

			if (itemstack.isEmpty()) {
				entity.discard();
			} else {
				entity.setItem(itemstack);
			}

			if (originalCount != itemstack.getCount()) {
				refreshClient();
			}

			tx.commit();
		}
	}

	protected RecipeHolder<VatRecipe> getRecipe() {
		if (level instanceof ServerLevel serverLevel) {
			ItemStack input = handler.getResource(0).toStack(handler.getAmountAsInt(0));
			if (input.isEmpty()) {
				FluidStack fluidStack = tank.getResource(0).toStack(tank.getAmountAsInt(0));
				if (!fluidStack.isEmpty()) {
					for (RecipeHolder<VatRecipe> recipe : serverLevel.recipeAccess().recipeMap().byType(OreBerryRecipes.VAT_RECIPE_TYPE.get())) {
						if (fluidStack.getFluid().isSame(recipe.value().getFluid())) {
							return curRecipe = recipe;
						}
					}
				}
				return null;
			}

			SingleRecipeInput inventory = new SingleRecipeInput(input);
			if (curRecipe != null && curRecipe.value().matches(inventory, level)) return curRecipe;
			else {
				RecipeHolder<VatRecipe> rec = serverLevel.recipeAccess().getRecipeFor(OreBerryRecipes.VAT_RECIPE_TYPE.get(),
						inventory, this.level).orElse(null);
				return curRecipe = rec;
			}
		}
		return null;
	}

	protected int getMaxEvaporateTime() {
		RecipeHolder<VatRecipe> recipe = getRecipe();
		if (recipe == null) return 100;
		return recipe.value().getEvaporationTime();
	}

	protected boolean isFluidEqual(FluidStack fluid) {
		return isFluidEqual(fluid.getFluid());
	}

	protected boolean isFluidEqual(Fluid fluid) {
		return tank.getResource(0).getFluid().equals(fluid);
	}

	public void setCooldown(int cooldown) {
		this.crushCooldown = cooldown;
	}

	private boolean isOnCooldown() {
		return this.crushCooldown > 0;
	}

	@Nullable
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public void onDataPacket(Connection net, ValueInput valueInput) {
		super.onDataPacket(net, valueInput);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
		CompoundTag tag = new CompoundTag();
		try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(OreberriesReplanted.LOGGER)) {
			TagValueOutput output = TagValueOutput.createWithContext(problemreporter$scopedcollector, lookupProvider);
			this.saveAdditional(output);
			tag.merge(output.buildResult());
		}
		return tag;
	}

	@Override
	public CompoundTag getPersistentData() {
		CompoundTag tag = new CompoundTag();
		try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(OreberriesReplanted.LOGGER)) {
			HolderLookup.Provider lookupProvider = this.level != null ? this.level.registryAccess() : VanillaRegistries.createLookup();
			TagValueOutput output = TagValueOutput.createWithContext(problemreporter$scopedcollector, lookupProvider);
			this.saveAdditional(output);
			tag.merge(output.buildResult());
		}
		return tag;
	}

	public ItemStacksResourceHandler getHandler(@Nullable Direction direction) {
		return direction != Direction.DOWN ? handler : null;
	}

	public FluidStacksResourceHandler getTank(@Nullable Direction direction) {
		return tank;
	}

	@Override
	public void preRemoveSideEffects(BlockPos pos, BlockState state) {
		if (handler != null && this.level != null) {
			for (int i = 0; i < handler.size(); ++i) {
				ItemStack stack = handler.getResource(i).toStack(handler.getAmountAsInt(i));
				Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
			}
		}
	}
}
