package com.mrbysco.oreberriesreplanted.block;

import com.mrbysco.oreberriesreplanted.mixin.LivingEntityAccessor;
import com.mrbysco.oreberriesreplanted.tile.VatTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class VatBlock extends Block {
	private static final VoxelShape SHAPE = Stream.of(
			Block.box(1, 0, 1, 15, 1, 15),
			Block.box(1, 1, 0, 15, 8, 1),
			Block.box(0, 1, 1, 1, 8, 15),
			Block.box(1, 1, 15, 15, 8, 16),
			Block.box(15, 1, 1, 16, 8, 15)
	).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

	public VatBlock(Properties properties) {
		super(properties);
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new VatTile();
	}

	@Override
	public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
		float f = (float)entity.getY() - 0.5F;
		float yPos = (float)(pos.getY() - 0.25f);
		if (!entity.isShiftKeyDown() && (double)f <= yPos) {
			TileEntity tile = world.getBlockEntity(pos);
			if(world.getGameTime() % 10 == 0 && tile instanceof VatTile) {
				VatTile vatTile = (VatTile)tile;
				if(entity instanceof LivingEntity && !(entity instanceof PlayerEntity && ((PlayerEntity)entity).isSpectator())) {
					if(!vatTile.handler.getStackInSlot(0).isEmpty()) {
						LivingEntity livingEntity = (LivingEntity)entity;
						((LivingEntityAccessor)livingEntity).invokeJumpFromGround();
					}

					if(!world.isClientSide && world.random.nextInt(8) == 0) {
						vatTile.crushBerry();
					}
				}
				if(!world.isClientSide && entity instanceof ItemEntity && tile instanceof VatTile) {
					ItemEntity itemEntity = (ItemEntity) entity;
					vatTile.addBerry(itemEntity);
				}
			}
		}
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult) {
		TileEntity tile = world.getBlockEntity(pos);
		if (tile instanceof VatTile) {
			ItemStack stack = player.getItemInHand(hand);
			LazyOptional<IItemHandler> itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, blockRayTraceResult.getDirection());
			itemHandler.ifPresent((handler) -> {
				if(player.isShiftKeyDown()) {
					ItemStack berryStack = handler.getStackInSlot(0);
					if(!berryStack.isEmpty()) {
						InventoryHelper.dropItemStack(world, player.getX(), player.getY() + 0.5, player.getZ(), berryStack);
					}
				} else {
					if(handler.getStackInSlot(0).getCount() < handler.getSlotLimit(0)) {
						ItemStack remaining = ItemHandlerHelper.copyStackWithSize(stack, stack.getCount());
						if(!remaining.isEmpty()) {
							remaining = ItemHandlerHelper.insertItem(handler, stack, false);
							player.setItemInHand(hand, remaining);
						}
					}
				}
			});

			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			TileEntity tile = worldIn.getBlockEntity(pos);
			if (tile instanceof VatTile) {
				tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
					for(int i = 0; i < handler.getSlots(); ++i) {
						InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), handler.getStackInSlot(i));
					}
				});
			}

			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
		return reader.getFluidState(pos).isEmpty();
	}
}
