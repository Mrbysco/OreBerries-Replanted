package com.mrbysco.oreberriesreplanted.tile;

import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
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

public class VatTile extends TileEntity implements ITickableTileEntity {
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

	public VatTile(TileEntityType<?> tileEntityType) {
		super(tileEntityType);
	}

	public VatTile() {
		super(OreBerryRegistry.VAT_TILE.get());
	}

	@Override
	public void load(BlockState state, CompoundNBT tag) {
		this.evaporateProgress = tag.getInt("evaporateProgress");
		this.evaporateTotalTime = tag.getInt("evaporateTotalTime");
		this.crushCooldown = tag.getInt("crushCooldown");

		this.handler.deserializeNBT(tag.getCompound("ItemStackHandler"));
		this.tank.readFromNBT(tag);

		super.load(state, tag);
	}

	@Override
	public CompoundNBT save(CompoundNBT tag) {
		tag = super.save(tag);

		tag.putInt("evaporateProgress", this.evaporateProgress);
		tag.putInt("evaporateTotalTime", this.evaporateTotalTime);
		tag.putInt("crushCooldown", this.crushCooldown);
		tag.put("ItemStackHandler", handler.serializeNBT());
		tank.writeToNBT(tag);

		return tag;
	}

	@Override
	public void tick() {
		if(level.isClientSide) {
			return;
		}

		if(this.crushCooldown > 0) {
			--this.crushCooldown;
		}
		if (!tank.isEmpty()) {
			VatRecipe irecipe = getRecipe();
			boolean valid = canEvaporate(irecipe);
			if(valid) {
				if(this.evaporateTotalTime == 0) {
					this.evaporateTotalTime = this.getMaxEvaporateTime();
					this.evaporateProgress = 0;
				}
				this.evaporateProgress++;

				if (this.evaporateProgress < this.evaporateTotalTime) {
					return;
				}
				this.evaporateProgress = 0;
				this.evaporateTotalTime = this.getMaxEvaporateTime();
				evaporateLiquid(irecipe);

				refreshClient();
			}
		}
	}

	protected void evaporateLiquid(VatRecipe recipe) {
		int evaporationAmount = recipe.getEvaporationAmount();
		ItemStack outputStack = curRecipe.assemble(null);
		tank.drain(evaporationAmount, FluidAction.EXECUTE);

		BlockPos blockpos = this.getBlockPos();
		InventoryHelper.dropItemStack(this.level, (double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), outputStack);
		level.playSound((PlayerEntity) null, worldPosition, SoundEvents.LAVA_POP, SoundCategory.BLOCKS, 0.5F, 1.0F);
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
			entity.remove();
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

		Inventory inventory = new Inventory(1);
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
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.getBlockPos(), 0, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.load(getBlockState(), pkt.getTag());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT nbt = new CompoundNBT();
		this.save(nbt);
		return nbt;
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		super.handleUpdateTag(state, tag);
	}

	@Override
	public CompoundNBT getTileData() {
		CompoundNBT nbt = new CompoundNBT();
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
	protected void invalidateCaps() {
		super.invalidateCaps();
		this.handlerHolder.invalidate();
		this.tankHolder.invalidate();
	}
}
