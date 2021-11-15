package com.mrbysco.oreberriesreplanted.block;

import com.mrbysco.oreberriesreplanted.config.OreBerriesConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
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

	protected IItemProvider getBerryItem() {
		return berryItem.get();
	}

	public static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
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

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if(!worldIn.isClientSide && !isMaxAge(state) && worldIn.getRawBrightness(pos, 0) < 10 && ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt() == OreBerriesConfig.COMMON.growthChance.get())) {
			int currentAge = getAge(state);
			worldIn.setBlock(pos, withAge(currentAge + 1), 3);
			ForgeHooks.onCropsGrowPost(worldIn, pos, worldIn.getBlockState(pos));
		}
	}

	@Override
	public void attack(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		harvestBerry(state, worldIn, pos);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		return harvestBerry(state, worldIn, pos);
	}

	public ActionResultType harvestBerry(BlockState state, World worldIn, BlockPos pos) {
		if(isMaxAge(state)) {
			if(worldIn.isClientSide)
				return ActionResultType.SUCCESS;

			worldIn.setBlock(pos, withAge(getMaxAge() - 1), 3);
			popResource(worldIn, pos, new ItemStack(getBerryItem(), worldIn.random.nextInt(3) + 1));
		}

		return ActionResultType.PASS;
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
		if (state.getBlock() == this)
			return state.getValue(this.getAgeProperty()) >= 2;

		return super.canSustainPlant(state, world, pos, facing, plantable);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		boolean flag = oreType.getDarknessOnly() ? worldIn.getRawBrightness(pos, 0) < 13 : true;
		return flag && blockstate.canSustainPlant(worldIn, blockpos, net.minecraft.util.Direction.UP, this);
	}

	@Override
	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if(!(entityIn instanceof ItemEntity)) {
			entityIn.hurt(DamageSource.CACTUS, 1.0F);
		}
	}

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() != this) return defaultBlockState();
		return state;
	}

	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
		return PlantType.CAVE;
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}
