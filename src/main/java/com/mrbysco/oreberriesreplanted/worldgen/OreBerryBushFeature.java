package com.mrbysco.oreberriesreplanted.worldgen;

import com.mojang.serialization.Codec;
import com.mrbysco.oreberriesreplanted.block.OreBerryBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Random;

public class OreBerryBushFeature extends Feature<OreBerryBushFeatureConfig> {
	public OreBerryBushFeature(Codec<OreBerryBushFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<OreBerryBushFeatureConfig> context) {
		WorldGenLevel level = context.level();
		Random rand = context.random();
		BlockPos pos = context.origin();
		ChunkGenerator generator = context.chunkGenerator();

		int density = 1;
		if(context.config().state.getBlock() instanceof OreBerryBushBlock block) {
			density = block.getDensity();
		}

		OreBerryBushFeatureConfig config = context.config();
		for(int i = 0; i < density; i++) {
			BlockPos adequateLocation = findAdequateLocation(level, pos, generator, config);
			if(adequateLocation != null) {
				int type = rand.nextInt(config.chance);
				if (type == 11)
					generateMediumNode(level, rand, adequateLocation, config);
				else if (type >= 5)
					generateSmallNode(level, rand, adequateLocation, config);
				else
					generateTinyNode(level, rand, adequateLocation, config);
			}
		}

		return true;
	}

	protected BlockPos findAdequateLocation(LevelAccessor world, BlockPos blockPos, ChunkGenerator generator, OreBerryBushFeatureConfig config) {
		int minY = 0;
		int maxY = generator.getSpawnHeight(world);

		if(config.state.getBlock() instanceof OreBerryBushBlock block) {
			minY = block.getMinY();
			maxY = block.getMaxY();
		}

		BlockPos pos = new BlockPos(blockPos);
		do {
			if(world.isEmptyBlock(pos) && !world.isEmptyBlock(pos.above()))
				return pos.above();
			pos = pos.above();
		} while (pos.getY() <maxY);

		pos = new BlockPos(blockPos);
		do {
			if(world.isEmptyBlock(pos) && !world.isEmptyBlock(pos.below()))
				return pos.below();
			pos = pos.below();
		} while (pos.getY() > minY);

		return null;
	}

	public void generateMediumNode(LevelAccessor world, Random random, BlockPos pos, OreBerryBushFeatureConfig config) {
		for (int xPos = -1; xPos <= 1; xPos++)
			for (int yPos = -1; yPos <= 1; yPos++)
				for (int zPos = -1; zPos <= 1; zPos++)
					if (random.nextInt(4) == 0)
						generateBerryBlock(world, pos.offset(xPos, yPos, zPos), random, config);

		generateSmallNode(world, random, pos, config);
	}

	public void generateSmallNode(LevelAccessor world, Random random, BlockPos pos, OreBerryBushFeatureConfig config) {
		generateBerryBlock(world, pos, random, config);
		if (random.nextBoolean())
			generateBerryBlock(world, pos.east(), random, config);
		if (random.nextBoolean())
			generateBerryBlock(world, pos.west(), random, config);
		if (random.nextBoolean())
			generateBerryBlock(world, pos.south(), random, config);
		if (random.nextBoolean())
			generateBerryBlock(world, pos.north(), random, config);
		if (random.nextInt(4) != 0)
			generateBerryBlock(world, pos.above(), random, config);
	}

	public void generateTinyNode(LevelAccessor world, Random random, BlockPos pos, OreBerryBushFeatureConfig config) {
		generateBerryBlock(world, pos, random, config);
		if (random.nextInt(4) == 0)
			generateBerryBlock(world, pos.east(), random, config);
		if (random.nextInt(4) == 0)
			generateBerryBlock(world, pos.west(), random, config);
		if (random.nextInt(4) == 0)
			generateBerryBlock(world, pos.south(), random, config);
		if (random.nextInt(4) == 0)
			generateBerryBlock(world, pos.north(), random, config);
		if (random.nextInt(16) < 7)
			generateBerryBlock(world, pos.above(), random, config);
	}

	void generateBerryBlock(LevelAccessor world, BlockPos pos, Random random, OreBerryBushFeatureConfig config) {
		BlockState state = world.getBlockState(pos);
		if ((!world.isEmptyBlock(pos.above()) || !world.isEmptyBlock(pos.below())) && (isCaveAir(world, pos) || config.target.test(state, random))) {
			world.setBlock(pos, config.state, 2);
		}
	}

	public boolean isCaveAir(LevelAccessor world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR;
	}
}
