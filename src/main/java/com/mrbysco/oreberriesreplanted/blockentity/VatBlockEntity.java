package com.mrbysco.oreberriesreplanted.blockentity;

import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class VatBlockEntity extends BlockEntity {
	public FluidTank tank = new FluidTank(3200) {
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
			for(VatRecipe recipe : level.getRecipeManager().getAllRecipesFor(OreBerryRegistry.VAT_RECIPE_TYPE)) {
				if(stack.getFluid().isSame(recipe.getFluid())) {
					return true;
				}
			}
			return false;
		}
	};
	private final LazyOptional<IFluidHandler> tankHolder = LazyOptional.of(() -> tank);

	public ItemStackHandler handler = new ItemStackHandler(1) {
		@Override
		protected int getStackLimit(int slot, ItemStack stack) {
			return 32;
		}

		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			for(VatRecipe recipe : level.getRecipeManager().getAllRecipesFor(OreBerryRegistry.VAT_RECIPE_TYPE)) {
				if(recipe.getIngredients().get(0).test(stack)) {
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
	private final LazyOptional<IItemHandler> handlerHolder = LazyOptional.of(() -> handler);

	protected VatRecipe curRecipe;

	private int evaporateProgress;
	private int evaporateTotalTime;
	private int crushCooldown = -1;

	public VatBlockEntity(BlockEntityType<?> tileEntityType, BlockPos pos, BlockState state) {
		super(tileEntityType, pos, state);
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
	public CompoundTag save(CompoundTag tag) {
		tag = super.save(tag);

		tag.putInt("evaporateProgress", this.evaporateProgress);
		tag.putInt("evaporateTotalTime", this.evaporateTotalTime);
		tag.putInt("crushCooldown", this.crushCooldown);
		tag.put("ItemStackHandler", handler.serializeNBT());
		tank.writeToNBT(tag);

		return tag;
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, VatBlockEntity vatBlockEntity) {
		if(level.isClientSide) {
			return;
		}

		if(vatBlockEntity.crushCooldown > 0) {
			--vatBlockEntity.crushCooldown;
		}
		if (!vatBlockEntity.tank.isEmpty()) {
			VatRecipe irecipe = vatBlockEntity.getRecipe();
			boolean valid = vatBlockEntity.canEvaporate(irecipe);
			if(valid) {
				if(vatBlockEntity.evaporateTotalTime == 0) {
					vatBlockEntity.evaporateTotalTime = vatBlockEntity.getMaxEvaporateTime();
					vatBlockEntity.evaporateProgress = 0;
				}
				vatBlockEntity.evaporateProgress++;

				if (vatBlockEntity.evaporateProgress < vatBlockEntity.evaporateTotalTime) {
					return;
				}
				vatBlockEntity.evaporateProgress = 0;
				vatBlockEntity.evaporateTotalTime = vatBlockEntity.getMaxEvaporateTime();
				vatBlockEntity.evaporateLiquid(irecipe);

				vatBlockEntity.refreshClient();
			}
		}
	}

	protected void evaporateLiquid(VatRecipe recipe) {
		int evaporationAmount = recipe.getEvaporationAmount();
		ItemStack outputStack = curRecipe.assemble(null);
		tank.drain(evaporationAmount, FluidAction.EXECUTE);

		BlockPos blockpos = this.getBlockPos();
		Containers.dropItemStack(this.level, (double)blockpos.getX(), (double)blockpos.getY() + 0.1D, (double)blockpos.getZ(), outputStack);
		level.playSound((Player) null, worldPosition, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 0.5F, 1.0F);
	}

	protected void refreshClient() {
		this.setChanged();
		this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
	}

	protected boolean canEvaporate(@Nullable VatRecipe recipe) {
		if (!tank.isEmpty() && recipe != null) {
			return tank.getFluidAmount() > recipe.getEvaporationAmount();
		}
		return false;
	}

	public void crushBerry() {
		if(!isOnCooldown()) {
			ItemStack berryStack = handler.getStackInSlot(0);
			VatRecipe rec = getRecipe();
			if (rec != null && !berryStack.isEmpty()) {
				int liquidAmount = level.random.nextInt((int) (rec.getMax() * 100) - (int) (rec.getMin() * 100)) + (int) (rec.getMin() * 100);
				liquidAmount = (int) Math.round(liquidAmount / 10.0) * 10;
				FluidStack stack = new FluidStack(rec.getFluid(), liquidAmount);
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

		if(originalCount != resultStack.getCount()) {
			refreshClient();
		}
	}

	protected VatRecipe getRecipe() {
		ItemStack input = handler.getStackInSlot(0);
		if (input.isEmpty()) {
			FluidStack fluidStack = tank.getFluidInTank(0);
			if(!fluidStack.isEmpty()) {
				for(VatRecipe recipe : level.getRecipeManager().getAllRecipesFor(OreBerryRegistry.VAT_RECIPE_TYPE)) {
					if(recipe.getFluid().isSame(fluidStack.getFluid())) {
						return curRecipe = recipe;
					}
				}
			}
			return null;
		}

		SimpleContainer inventory = new SimpleContainer(1);
		inventory.setItem(0, input);
		if (curRecipe != null && curRecipe.matches(inventory, level)) return curRecipe;
		else {
			VatRecipe rec = level.getRecipeManager().getRecipeFor(OreBerryRegistry.VAT_RECIPE_TYPE, inventory, this.level).orElse(null);
			return curRecipe = rec;
		}
	}

	protected int getMaxEvaporateTime() {
		VatRecipe recipe = getRecipe();
		if (recipe == null) return 100;
		return recipe.getEvaporationTime();
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
		CompoundTag nbt = new CompoundTag();
		this.save(nbt);
		return nbt;
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		super.handleUpdateTag(tag);
	}

	@Override
	public CompoundTag getTileData() {
		CompoundTag nbt = new CompoundTag();
		this.save(nbt);
		return nbt;
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (side != Direction.DOWN && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return handlerHolder.cast();
		}
		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return tankHolder.cast();
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		this.handlerHolder.invalidate();
		this.tankHolder.invalidate();
	}
}
