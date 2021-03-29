package com.mrbysco.oreberriesreplanted.worldgen;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.block.OreBerryBushBlock;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class OreBerryFeatures {
	public static final ConfiguredFeature<?, ?> IRON_OREBERRY_BUSH_FEATURE =
			register("iron_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					.withConfiguration(new OreBerryBushFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OreBerryRegistry.IRON_OREBERRY_BUSH.get().getDefaultState().with(OreBerryBushBlock.AGE, 3), 12)));

	public static final ConfiguredFeature<?, ?> GOLD_OREBERRY_BUSH_FEATURE =
			register("gold_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					.withConfiguration(new OreBerryBushFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OreBerryRegistry.GOLD_OREBERRY_BUSH.get().getDefaultState().with(OreBerryBushBlock.AGE, 3), 6)));

	public static final ConfiguredFeature<?, ?> COPPER_OREBERRY_BUSH_FEATURE =
			register("copper_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					.withConfiguration(new OreBerryBushFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OreBerryRegistry.COPPER_OREBERRY_BUSH.get().getDefaultState().with(OreBerryBushBlock.AGE, 3), 12)));

	public static final ConfiguredFeature<?, ?> TIN_OREBERRY_BUSH_FEATURE =
			register("tin_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					.withConfiguration(new OreBerryBushFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OreBerryRegistry.TIN_OREBERRY_BUSH.get().getDefaultState().with(OreBerryBushBlock.AGE, 3), 12)));

	public static final ConfiguredFeature<?, ?> ALUMINUM_OREBERRY_BUSH_FEATURE =
			register("aluminum_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					.withConfiguration(new OreBerryBushFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OreBerryRegistry.ALUMINUM_OREBERRY_BUSH.get().getDefaultState().with(OreBerryBushBlock.AGE, 3), 14)));

	public static final ConfiguredFeature<?, ?> LEAD_OREBERRY_BUSH_FEATURE =
			register("lead_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					.withConfiguration(new OreBerryBushFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OreBerryRegistry.LEAD_OREBERRY_BUSH.get().getDefaultState().with(OreBerryBushBlock.AGE, 3), 14)));

	public static final ConfiguredFeature<?, ?> NICKEL_OREBERRY_BUSH_FEATURE =
			register("nickel_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					.withConfiguration(new OreBerryBushFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OreBerryRegistry.NICKEL_OREBERRY_BUSH.get().getDefaultState().with(OreBerryBushBlock.AGE, 3), 12)));

	public static final ConfiguredFeature<?, ?> URANIUM_OREBERRY_BUSH_FEATURE =
			register("uranium_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					.withConfiguration(new OreBerryBushFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OreBerryRegistry.URANIUM_OREBERRY_BUSH.get().getDefaultState().with(OreBerryBushBlock.AGE, 3), 14)));

	public static final ConfiguredFeature<?, ?> OSMIUM_OREBERRY_BUSH_FEATURE =
			register("osmium_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					.withConfiguration(new OreBerryBushFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OreBerryRegistry.OSMIUM_OREBERRY_BUSH.get().getDefaultState().with(OreBerryBushBlock.AGE, 3), 13)));

	public static final ConfiguredFeature<?, ?> ZINC_OREBERRY_BUSH_FEATURE =
			register("zinc_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					.withConfiguration(new OreBerryBushFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OreBerryRegistry.ZINC_OREBERRY_BUSH.get().getDefaultState().with(OreBerryBushBlock.AGE, 3), 12)));

	public static final ConfiguredFeature<?, ?> SILVER_OREBERRY_BUSH_FEATURE =
			register("silver_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					.withConfiguration(new OreBerryBushFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OreBerryRegistry.SILVER_OREBERRY_BUSH.get().getDefaultState().with(OreBerryBushBlock.AGE, 3), 14)));

	public static final ConfiguredFeature<?, ?> ESSENCE_BERRY_BUSH_FEATURE =
			register("essence_berry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					.withConfiguration(new OreBerryBushFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OreBerryRegistry.ESSENCE_BERRY_BUSH.get().getDefaultState().with(OreBerryBushBlock.AGE, 3), 8)));

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> feature) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Reference.MOD_ID, key), feature);
	}
}
