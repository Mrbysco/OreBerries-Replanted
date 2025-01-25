package com.mrbysco.oreberriesreplanted.worldgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.oreberriesreplanted.registry.OreBerryPlacementModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class ChanceRangePlacement extends PlacementModifier {
	public static final MapCodec<ChanceRangePlacement> CODEC = RecordCodecBuilder.mapCodec((builder) ->
			builder.group(
					Codec.INT.fieldOf("bottom_offset").orElse(0).forGetter((config) -> config.bottomOffset),
					Codec.INT.fieldOf("top_offset").orElse(0).forGetter((config) -> config.topOffset),
					Codec.INT.fieldOf("maximum").orElse(0).forGetter((config) -> config.maximum),
					Codec.INT.fieldOf("rarity").orElse(0).forGetter((config) -> config.rarity)
			).apply(builder, ChanceRangePlacement::new));
	public final int bottomOffset;
	public final int topOffset;
	public final int maximum;
	public final int rarity;

	public ChanceRangePlacement(int bottomOffset, int topOffset, int maximum, int rarity) {
		this.bottomOffset = bottomOffset;
		this.topOffset = topOffset;
		this.maximum = maximum;
		this.rarity = rarity;
	}

	@NotNull
	@Override
	public Stream<BlockPos> getPositions(@NotNull PlacementContext context, RandomSource random, @NotNull BlockPos pos) {
		if (random.nextInt(rarity) == 0) {
			int i = pos.getX();
			int j = pos.getZ();
			int k = random.nextInt(maximum - topOffset) + bottomOffset;
			return Stream.of(new BlockPos(i, k, j));
		} else {
			return Stream.empty();
		}
	}

	@NotNull
	@Override
	public PlacementModifierType<?> type() {
		return OreBerryPlacementModifiers.CAVE_EDGE_RANGE.get();
	}
}
