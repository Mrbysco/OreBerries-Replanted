package com.mrbysco.oreberriesreplanted.block;

import com.mrbysco.oreberriesreplanted.config.OreBerriesConfig;
import com.mrbysco.oreberriesreplanted.item.OreBerryItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.util.TriState;

import java.util.function.Supplier;

public class OreBerryBushBlock extends Block {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
			Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
	private final Supplier<OreBerryItem> berryItem;
	private final OreEnum oreType;

	public OreBerryBushBlock(Properties properties, Supplier<OreBerryItem> berryItem, OreEnum oreType) {
		super(properties.randomTicks().strength(0.3F).sound(SoundType.METAL).noOcclusion()
				.isSuffocating(OreBerryBushBlock::isntSolid).isViewBlocking(OreBerryBushBlock::isntSolid));
		this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0));

		this.berryItem = berryItem;
		this.oreType = oreType;
	}

	public int getDensity() {
		return oreType.getDensity();
	}

	public boolean darknessOnly() {
		return oreType.getDarknessOnly();
	}

	protected ItemLike getBerryItem() {
		return berryItem.get();
	}

	public static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {
		return false;
	}

	public IntegerProperty getAgeProperty() {
		return AGE;
	}

	public int getMaxAge() {
		return 3;
	}

	protected int getAge(BlockState state) {
		return state.getValue(this.getAgeProperty());
	}

	public BlockState withAge(int age) {
		return this.defaultBlockState().setValue(this.getAgeProperty(), age);
	}

	public boolean isMaxAge(BlockState state) {
		return state.getValue(this.getAgeProperty()) >= this.getMaxAge();
	}

	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		tick(state, level, pos, random);
	}

	@Override
	public void tick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource rand) {
		if (!serverLevel.isClientSide && !isMaxAge(state) &&
				CommonHooks.canCropGrow(serverLevel, pos, state,
						rand.nextInt(OreBerriesConfig.COMMON.growthChance.get()) == 0)) {
			boolean flag = !oreType.getDarknessOnly() || serverLevel.getRawBrightness(pos, 0) < 10;
			if (flag) {
				int currentAge = getAge(state);
				serverLevel.setBlock(pos, withAge(currentAge + 1), 3);
				CommonHooks.fireCropGrowPost(serverLevel, pos, serverLevel.getBlockState(pos));
			}
		}
	}

	@Override
	public void attack(BlockState state, Level level, BlockPos pos, Player player) {
		harvestBerry(state, level, pos);
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
	                                          Player player, InteractionHand hand, BlockHitResult result) {
		return harvestBerry(state, level, pos);
	}

	public ItemInteractionResult harvestBerry(BlockState state, Level level, BlockPos pos) {
		if (isMaxAge(state)) {
			if (level.isClientSide)
				return ItemInteractionResult.SUCCESS;

			level.setBlock(pos, withAge(getMaxAge() - 1), 3);
			popResource(level, pos, new ItemStack(getBerryItem(), level.random.nextInt(3) + 1));
		}

		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
		if (state.getBlock() == this && state.getValue(this.getAgeProperty()) >= 2)
			return TriState.TRUE;
		return TriState.DEFAULT;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos belowPos = pos.below();
		BlockState belowState = level.getBlockState(belowPos);
		net.neoforged.neoforge.common.util.TriState soilDecision = belowState
				.canSustainPlant(level, belowPos, net.minecraft.core.Direction.UP, state);
		boolean flag = !oreType.getDarknessOnly() || level.getRawBrightness(pos, 0) < 13;
		return !soilDecision.isFalse() && flag;
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entityIn) {
		if (!(entityIn instanceof ItemEntity)) {
			entityIn.hurt(level.damageSources().cactus(), 1.0F);
		}
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}
