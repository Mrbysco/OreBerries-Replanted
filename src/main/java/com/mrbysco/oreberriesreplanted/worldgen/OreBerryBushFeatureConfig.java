package com.mrbysco.oreberriesreplanted.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;

public class OreBerryBushFeatureConfig implements IFeatureConfig {
	public static final Codec<OreBerryBushFeatureConfig> CODEC = RecordCodecBuilder.create((bushFeatureConfig) -> {
		return bushFeatureConfig.group(RuleTest.field_237127_c_.fieldOf("target").forGetter((config) -> {
			return config.target;
		}), BlockState.CODEC.fieldOf("state").forGetter((config) -> {
			return config.state;
		}), Codec.INT.fieldOf("chance").forGetter((config) -> {
			return config.chance;
		})).apply(bushFeatureConfig, OreBerryBushFeatureConfig::new);
	});

	public final RuleTest target;
	public final BlockState state;
	public final int chance;

	public OreBerryBushFeatureConfig(RuleTest test, BlockState state, int chance) {
		this.target = test;
		this.state = state;
		this.chance = chance;
	}
}
