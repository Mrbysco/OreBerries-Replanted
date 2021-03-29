package com.mrbysco.oreberriesreplanted.worldgen;

import com.mojang.serialization.Codec;
import com.mrbysco.oreberriesreplanted.block.OreBerryBushBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class OreBerryBushFeature extends Feature<OreBerryBushFeatureConfig> {
	public OreBerryBushFeature(Codec<OreBerryBushFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, OreBerryBushFeatureConfig config) {
		BlockPos adequateLocation = findAdequateLocation(reader, pos, generator, config);
		if(adequateLocation != null) {
			int type = rand.nextInt(config.chance);
			if (type == 11)
				generateMediumNode(reader, rand, adequateLocation, config);
			else if (type >= 5)
				generateSmallNode(reader, rand, adequateLocation, config);
			else
				generateTinyNode(reader, rand, adequateLocation, config);
		}

		return true;
	}

	protected BlockPos findAdequateLocation(IWorld world, BlockPos blockPos, ChunkGenerator generator, OreBerryBushFeatureConfig config) {
		int minY = 0;
		int maxY = generator.getGroundHeight();

		if(config.state.getBlock() instanceof OreBerryBushBlock) {
			OreBerryBushBlock block = (OreBerryBushBlock)config.state.getBlock();
			minY = block.getMinY();
			maxY = block.getMaxY();
		}

		BlockPos pos = new BlockPos(blockPos);
		do {
			if(world.isAirBlock(pos) && !world.isAirBlock(pos.up()))
				return pos.up();
			pos = pos.up();
		} while (pos.getY() <maxY);

		pos = new BlockPos(blockPos);
		do {
			if(world.isAirBlock(pos) && !world.isAirBlock(pos.down()))
				return pos.down();
			pos = pos.down();
		} while (pos.getY() > minY);

		return null;
	}

	public void generateMediumNode(IWorld world, Random random, BlockPos pos, OreBerryBushFeatureConfig config) {
		for (int xPos = -1; xPos <= 1; xPos++)
			for (int yPos = -1; yPos <= 1; yPos++)
				for (int zPos = -1; zPos <= 1; zPos++)
					if (random.nextInt(4) == 0)
						generateBerryBlock(world, pos.add(xPos, yPos, zPos), random, config);

		generateSmallNode(world, random, pos, config);
	}

	public void generateSmallNode(IWorld world, Random random, BlockPos pos, OreBerryBushFeatureConfig config) {
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
			generateBerryBlock(world, pos.up(), random, config);
	}

	public void generateTinyNode(IWorld world, Random random, BlockPos pos, OreBerryBushFeatureConfig config) {
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
			generateBerryBlock(world, pos.up(), random, config);
	}

	void generateBerryBlock(IWorld world, BlockPos pos, Random random, OreBerryBushFeatureConfig config) {
		BlockState state = world.getBlockState(pos);
		if ((!world.isAirBlock(pos.up()) || !world.isAirBlock(pos.down())) && (isCaveAir(world, pos) || config.target.test(state, random))) {
			world.setBlockState(pos, config.state, 2);
		}
	}

	public boolean isCaveAir(IWorld world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR;
	}
}
