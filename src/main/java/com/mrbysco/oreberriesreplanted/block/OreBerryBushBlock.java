package com.mrbysco.oreberriesreplanted.block;

import com.mrbysco.oreberriesreplanted.config.OreBerriesConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.Random;
import java.util.function.Supplier;

public class OreBerryBushBlock extends Block implements IPlantable {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
			Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
	private final Supplier<Item> berryItem;
	private final OreEnum oreType;

	public OreBerryBushBlock(Properties properties, Supplier<Item> berryItem, OreEnum oreType) {
		super(properties.randomTicks().strength(0.3F).sound(SoundType.METAL).noOcclusion()
				.isSuffocating(OreBerryBushBlock::isntSolid).isViewBlocking(OreBerryBushBlock::isntSolid));
		this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), Integer.valueOf(0)));

		this.berryItem = berryItem;
		this.oreType = oreType;
	}

	public int getMinY() {
		return oreType.getMinY();
	}

	public int getMaxY() {
		return oreType.getMaxY();
	}

	public int getDensity() {
		return oreType.getDensity();
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
		return this.defaultBlockState().setValue(this.getAgeProperty(), Integer.valueOf(age));
	}

	public boolean isMaxAge(BlockState state) {
		return state.getValue(this.getAgeProperty()) >= this.getMaxAge();
	}

	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
	}

	@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
		if (!worldIn.isClientSide && !isMaxAge(state) && worldIn.getRawBrightness(pos, 0) < 10 && ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(OreBerriesConfig.COMMON.growthChance.get()) == 0)) {
			int currentAge = getAge(state);
			worldIn.setBlock(pos, withAge(currentAge + 1), 3);
			ForgeHooks.onCropsGrowPost(worldIn, pos, worldIn.getBlockState(pos));
		}
	}

	@Override
	public void attack(BlockState state, Level worldIn, BlockPos pos, Player player) {
		harvestBerry(state, worldIn, pos);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		return harvestBerry(state, worldIn, pos);
	}

	public InteractionResult harvestBerry(BlockState state, Level worldIn, BlockPos pos) {
		if (isMaxAge(state)) {
			if (worldIn.isClientSide)
				return InteractionResult.SUCCESS;

			worldIn.setBlock(pos, withAge(getMaxAge() - 1), 3);
			popResource(worldIn, pos, new ItemStack(getBerryItem(), worldIn.random.nextInt(3) + 1));
		}

		return InteractionResult.PASS;
	}

	@Override
	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
		if (state.getBlock() == this)
			return state.getValue(this.getAgeProperty()) >= 2;

		return super.canSustainPlant(state, world, pos, facing, plantable);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		boolean flag = !oreType.getDarknessOnly() || worldIn.getRawBrightness(pos, 0) < 13;
		return flag && blockstate.canSustainPlant(worldIn, blockpos, net.minecraft.core.Direction.UP, this);
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		if (!(entityIn instanceof ItemEntity)) {
			entityIn.hurt(DamageSource.CACTUS, 1.0F);
		}
	}

	@Override
	public BlockState getPlant(BlockGetter world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() != this) return defaultBlockState();
		return state;
	}

	@Override
	public PlantType getPlantType(BlockGetter world, BlockPos pos) {
		return PlantType.CAVE;
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}
