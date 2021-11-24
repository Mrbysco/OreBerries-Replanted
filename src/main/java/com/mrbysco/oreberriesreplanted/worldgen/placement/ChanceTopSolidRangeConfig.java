package com.mrbysco.oreberriesreplanted.worldgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public class ChanceTopSolidRangeConfig implements DecoratorConfiguration {
	public static final Codec<ChanceTopSolidRangeConfig> CODEC = RecordCodecBuilder.create((builder) -> {
		return builder.group(Codec.INT.fieldOf("bottom_offset").orElse(0).forGetter((config) -> {
			return config.bottomOffset;
		}), Codec.INT.fieldOf("top_offset").orElse(0).forGetter((config) -> {
			return config.topOffset;
		}), Codec.INT.fieldOf("maximum").orElse(0).forGetter((config) -> {
			return config.maximum;
		}), Codec.INT.fieldOf("rarity").orElse(0).forGetter((config) -> {
			return config.rarity;
		})).apply(builder, ChanceTopSolidRangeConfig::new);
	});
	public final int bottomOffset;
	public final int topOffset;
	public final int maximum;
	public final int rarity;

	public ChanceTopSolidRangeConfig(int bottomOffset, int topOffset, int maximum, int rarity) {
		this.bottomOffset = bottomOffset;
		this.topOffset = topOffset;
		this.maximum = maximum;
		this.rarity = rarity;
	}
}
