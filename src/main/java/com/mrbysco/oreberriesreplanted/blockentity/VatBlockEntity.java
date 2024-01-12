package com.mrbysco.oreberriesreplanted.blockentity;

import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class VatBlockEntity extends BlockEntity {
	public final FluidTank tank = new FluidTank(3200) {
		@Override
		public FluidStack drain(FluidStack resource, FluidAction action) {
			if (!isFluidEqual(resource)) {
				return FluidStack.EMPTY;
			}
			if (action.simulate()) {
				int amount = tank.getFluidAmount() - resource.getAmount() < 0 ? tank.getFluidAmount() : resource.getAmount();
				return new FluidStack(tank.getFluid(), amount);
			}
			return super.drain(resource.getAmount(), action);
		}

		@Override
		protected void onContentsChanged() {
			refreshClient();
		}

		@Override
		public FluidStack drain(int maxDrain, FluidAction action) {
			return super.drain(maxDrain, action);
		}

		@Override
		public boolean isFluidValid(FluidStack stack) {
			for (RecipeHolder<VatRecipe> recipe : level.getRecipeManager().getAllRecipesFor(OreBerryRecipes.VAT_RECIPE_TYPE.get())) {
				if (BuiltInRegistries.FLUID.getKey(stack.getFluid()).equals(recipe.value().getFluidKey().location())) {
					return true;
				}
			}
			return false;
		}
	};

	public final ItemStackHandler handler = new ItemStackHandler(1) {
		@Override
		protected int getStackLimit(int slot, ItemStack stack) {
			return 32;
		}

		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			for (RecipeHolder<VatRecipe> recipe : level.getRecipeManager().getAllRecipesFor(OreBerryRecipes.VAT_RECIPE_TYPE.get())) {
				if (recipe.value().getIngredients().get(0).test(stack)) {
					return true;
				}
			}
			return false;
		}

		@Override
		protected void onContentsChanged(int slot) {
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
		super(OreBerryRegistry.VAT_BLOCK_ENTITY.get(), pos, state);
	}

	@Override
	public void load(CompoundTag tag) {
		this.evaporateProgress = tag.getInt("evaporateProgress");
		this.evaporateTotalTime = tag.getInt("evaporateTotalTime");
		this.crushCooldown = tag.getInt("crushCooldown");

		this.handler.deserializeNBT(tag.getCompound("ItemStackHandler"));
		this.tank.readFromNBT(tag);

		super.load(tag);
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putInt("evaporateProgress", this.evaporateProgress);
		tag.putInt("evaporateTotalTime", this.evaporateTotalTime);
		tag.putInt("crushCooldown", this.crushCooldown);
		tag.put("ItemStackHandler", handler.serializeNBT());
		tank.writeToNBT(tag);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, VatBlockEntity vatBlockEntity) {
		if (level.isClientSide) {
			return;
		}

		if (vatBlockEntity.crushCooldown > 0) {
			--vatBlockEntity.crushCooldown;
		}
		if (!vatBlockEntity.tank.isEmpty()) {
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
			ItemStack outputStack = curRecipe.value().assemble(null, level.registryAccess());
			tank.drain(evaporationAmount, FluidAction.EXECUTE);

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
		if (!tank.isEmpty() && recipe != null) {
			return tank.getFluidAmount() > recipe.value().getEvaporationAmount();
		}
		return false;
	}

	public void crushBerry() {
		if (!isOnCooldown()) {
			ItemStack berryStack = handler.getStackInSlot(0);
			RecipeHolder<VatRecipe> holder = getRecipe();
			if (holder != null && !berryStack.isEmpty()) {
				VatRecipe recipe = holder.value();
				int liquidAmount = level.random.nextInt((int) (recipe.getMax() * 100) - (int) (recipe.getMin() * 100)) + (int) (recipe.getMin() * 100);
				liquidAmount = (int) Math.round(liquidAmount / 10.0) * 10;
				Fluid fluid = BuiltInRegistries.FLUID.get(recipe.getFluidKey());
				if (fluid == null)
					return;
				FluidStack stack = new FluidStack(fluid, liquidAmount);
				int accepted = tank.fill(stack, FluidAction.SIMULATE);
				if (accepted > 0) {
					tank.fill(stack, FluidAction.EXECUTE);
					berryStack.shrink(1);
				}
			}
			setCooldown(20);
		}
	}

	public void addBerry(ItemEntity entity) {
		ItemStack itemstack = entity.getItem().copy();
		int originalCount = itemstack.getCount();
		ItemStack resultStack = handler.insertItem(0, itemstack, false);
		if (resultStack.isEmpty()) {
			entity.discard();
		} else {
			entity.setItem(resultStack);
		}

		if (originalCount != resultStack.getCount()) {
			refreshClient();
		}
	}

	protected RecipeHolder<VatRecipe> getRecipe() {
		ItemStack input = handler.getStackInSlot(0);
		if (input.isEmpty()) {
			FluidStack fluidStack = tank.getFluidInTank(0);
			if (!fluidStack.isEmpty()) {
				for (RecipeHolder<VatRecipe> recipe : level.getRecipeManager().getAllRecipesFor(OreBerryRecipes.VAT_RECIPE_TYPE.get())) {
					if (BuiltInRegistries.FLUID.getKey(fluidStack.getFluid()).equals(recipe.value().getFluidKey().location())) {
						return curRecipe = recipe;
					}
				}
			}
			return null;
		}

		SimpleContainer inventory = new SimpleContainer(1);
		inventory.setItem(0, input);
		if (curRecipe != null && curRecipe.value().matches(inventory, level)) return curRecipe;
		else {
			RecipeHolder<VatRecipe> rec = level.getRecipeManager().getRecipeFor(OreBerryRecipes.VAT_RECIPE_TYPE.get(), inventory, this.level).orElse(null);
			return curRecipe = rec;
		}
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
		return tank.getFluid().getFluid().equals(fluid);
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
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		this.load(pkt.getTag());
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = new CompoundTag();
		this.saveAdditional(tag);
		return tag;
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		super.handleUpdateTag(tag);
	}

	@Override
	public CompoundTag getPersistentData() {
		CompoundTag tag = new CompoundTag();
		this.saveAdditional(tag);
		return tag;
	}

	public ItemStackHandler getHandler(@Nullable Direction direction) {
		return direction != Direction.DOWN ? handler : null;
	}

	public FluidTank getTank(@Nullable Direction direction) {
		return tank;
	}
}
