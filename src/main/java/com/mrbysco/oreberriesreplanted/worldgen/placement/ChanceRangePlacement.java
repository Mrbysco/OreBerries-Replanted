package com.mrbysco.oreberriesreplanted.worldgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

import java.util.Random;
import java.util.stream.Stream;

public class ChanceRangePlacement extends FeatureDecorator<ChanceTopSolidRangeConfig> {
	public ChanceRangePlacement(Codec<ChanceTopSolidRangeConfig> codec) {
		super(codec);
	}

	public Stream<BlockPos> getPositions(DecorationContext context, Random random, ChanceTopSolidRangeConfig config, BlockPos pos) {
		if(random.nextInt(config.rarity) == 0) {
			int i = pos.getX();
			int j = pos.getZ();
			int k = random.nextInt(config.maximum - config.topOffset) + config.bottomOffset;
			return Stream.of(new BlockPos(i, k, j));
		} else {
			return Stream.empty();
		}
	}
}
