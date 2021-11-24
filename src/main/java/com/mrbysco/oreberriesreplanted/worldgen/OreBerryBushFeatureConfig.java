package com.mrbysco.oreberriesreplanted.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public class OreBerryBushFeatureConfig implements FeatureConfiguration {
	public static final Codec<OreBerryBushFeatureConfig> CODEC = RecordCodecBuilder.create((bushFeatureConfig) -> {
		return bushFeatureConfig.group(RuleTest.CODEC.fieldOf("target").forGetter((config) -> {
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
