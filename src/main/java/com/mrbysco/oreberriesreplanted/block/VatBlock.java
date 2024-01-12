package com.mrbysco.oreberriesreplanted.block;

import com.mojang.serialization.MapCodec;
import com.mrbysco.oreberriesreplanted.blockentity.VatBlockEntity;
import com.mrbysco.oreberriesreplanted.mixin.LivingEntityAccessor;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class VatBlock extends BaseEntityBlock {
	public static final MapCodec<VatBlock> CODEC = simpleCodec(VatBlock::new);
	private static final VoxelShape SHAPE = Stream.of(
			Block.box(1, 0, 1, 15, 1, 15),
			Block.box(1, 1, 0, 15, 8, 1),
			Block.box(0, 1, 1, 1, 8, 15),
			Block.box(1, 1, 15, 15, 8, 16),
			Block.box(15, 1, 1, 16, 8, 15)
	).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

	public VatBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		float f = (float) entity.getY() - 0.5F;
		float yPos = (float) (pos.getY() - 0.25f);
		if (!entity.isShiftKeyDown() && (double) f <= yPos) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (level.getGameTime() % 10 == 0 && blockEntity instanceof VatBlockEntity vat) {
				if (entity instanceof LivingEntity && !(entity instanceof Player && ((Player) entity).isSpectator())) {
					if (!vat.handler.getStackInSlot(0).isEmpty()) {
						LivingEntity livingEntity = (LivingEntity) entity;
						((LivingEntityAccessor) livingEntity).invokeJumpFromGround();
					}

					if (!level.isClientSide && level.random.nextInt(8) == 0) {
						vat.crushBerry();
					}
				}
				if (!level.isClientSide && entity instanceof ItemEntity itemEntity && blockEntity instanceof VatBlockEntity) {
					vat.addBerry(itemEntity);
				}
			}
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockRayTraceResult) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof VatBlockEntity) {
			ItemStack stack = player.getItemInHand(hand);
			IItemHandler itemHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, blockRayTraceResult.getDirection());
			if (itemHandler != null) {
				if (player.isShiftKeyDown()) {
					ItemStack berryStack = itemHandler.getStackInSlot(0);
					if (!berryStack.isEmpty()) {
						Containers.dropItemStack(level, player.getX(), player.getY() + 0.5, player.getZ(), berryStack);
					}
				} else {
					if (itemHandler.getStackInSlot(0).getCount() < itemHandler.getSlotLimit(0)) {
						ItemStack remaining = ItemHandlerHelper.copyStackWithSize(stack, stack.getCount());
						if (!remaining.isEmpty()) {
							remaining = ItemHandlerHelper.insertItem(itemHandler, stack, false);
							player.setItemInHand(hand, remaining);
						}
					}
				}
			}
			;

			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof VatBlockEntity) {
				IItemHandler itemHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
				if (itemHandler != null) {
					for (int i = 0; i < itemHandler.getSlots(); ++i) {
						Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), itemHandler.getStackInSlot(i));
					}
				}
			}

			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		return reader.getFluidState(pos).isEmpty();
	}

	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
		return createVatTicker(level, entityType, OreBerryRegistry.VAT_BLOCK_ENTITY.get());
	}

	protected static <T extends BlockEntity> BlockEntityTicker<T> createVatTicker(Level level, BlockEntityType<T> entityType, BlockEntityType<? extends VatBlockEntity> blockEntityType) {
		return level.isClientSide ? null : createTickerHelper(entityType, blockEntityType, VatBlockEntity::serverTick);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new VatBlockEntity(pos, state);
	}
}
