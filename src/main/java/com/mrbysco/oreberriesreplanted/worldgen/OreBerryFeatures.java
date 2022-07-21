package com.mrbysco.oreberriesreplanted.worldgen;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.block.OreBerryBushBlock;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import com.mrbysco.oreberriesreplanted.worldgen.placement.ChanceRangePlacement;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class OreBerryFeatures {
	public static final Holder<ConfiguredFeature<OreBerryBushFeatureConfig, ?>> IRON_OREBERRY_BUSH_FEATURE =
			register("iron_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					, new OreBerryBushFeatureConfig(OreFeatures.NATURAL_STONE, OreBerryRegistry.IRON_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 12));

	public static final Holder<ConfiguredFeature<OreBerryBushFeatureConfig, ?>> GOLD_OREBERRY_BUSH_FEATURE =
			register("gold_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					, new OreBerryBushFeatureConfig(OreFeatures.NATURAL_STONE, OreBerryRegistry.GOLD_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 6));

	public static final Holder<ConfiguredFeature<OreBerryBushFeatureConfig, ?>> COPPER_OREBERRY_BUSH_FEATURE =
			register("copper_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					, new OreBerryBushFeatureConfig(OreFeatures.NATURAL_STONE, OreBerryRegistry.COPPER_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 12));

	public static final Holder<ConfiguredFeature<OreBerryBushFeatureConfig, ?>> TIN_OREBERRY_BUSH_FEATURE =
			register("tin_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					, new OreBerryBushFeatureConfig(OreFeatures.NATURAL_STONE, OreBerryRegistry.TIN_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 12));

	public static final Holder<ConfiguredFeature<OreBerryBushFeatureConfig, ?>> ALUMINUM_OREBERRY_BUSH_FEATURE =
			register("aluminum_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					, new OreBerryBushFeatureConfig(OreFeatures.NATURAL_STONE, OreBerryRegistry.ALUMINUM_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 14));

	public static final Holder<ConfiguredFeature<OreBerryBushFeatureConfig, ?>> LEAD_OREBERRY_BUSH_FEATURE =
			register("lead_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					, new OreBerryBushFeatureConfig(OreFeatures.NATURAL_STONE, OreBerryRegistry.LEAD_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 14));

	public static final Holder<ConfiguredFeature<OreBerryBushFeatureConfig, ?>> NICKEL_OREBERRY_BUSH_FEATURE =
			register("nickel_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					, new OreBerryBushFeatureConfig(OreFeatures.NATURAL_STONE, OreBerryRegistry.NICKEL_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 12));

	public static final Holder<ConfiguredFeature<OreBerryBushFeatureConfig, ?>> URANIUM_OREBERRY_BUSH_FEATURE =
			register("uranium_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					, new OreBerryBushFeatureConfig(OreFeatures.NATURAL_STONE, OreBerryRegistry.URANIUM_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 14));

	public static final Holder<ConfiguredFeature<OreBerryBushFeatureConfig, ?>> OSMIUM_OREBERRY_BUSH_FEATURE =
			register("osmium_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					, new OreBerryBushFeatureConfig(OreFeatures.NATURAL_STONE, OreBerryRegistry.OSMIUM_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 13));

	public static final Holder<ConfiguredFeature<OreBerryBushFeatureConfig, ?>> ZINC_OREBERRY_BUSH_FEATURE =
			register("zinc_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					, new OreBerryBushFeatureConfig(OreFeatures.NATURAL_STONE, OreBerryRegistry.ZINC_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 12));

	public static final Holder<ConfiguredFeature<OreBerryBushFeatureConfig, ?>> SILVER_OREBERRY_BUSH_FEATURE =
			register("silver_oreberry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					, new OreBerryBushFeatureConfig(OreFeatures.NATURAL_STONE, OreBerryRegistry.SILVER_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 14));

	public static final Holder<ConfiguredFeature<OreBerryBushFeatureConfig, ?>> ESSENCE_BERRY_BUSH_FEATURE =
			register("essence_berry_bush", OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get()
					, new OreBerryBushFeatureConfig(OreFeatures.NATURAL_STONE, OreBerryRegistry.ESSENCE_BERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 8));

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String name, F feature, FC featureConfiguration) {
		return FeatureUtils.register(new ResourceLocation(Reference.MOD_ID, name).toString(), feature, featureConfiguration);
	}

	public static List<PlacementModifier> getPlacedFeature(int minY, int maxY, int rarity) {
		return List.of(new ChanceRangePlacement(minY, 0, maxY, rarity), InSquarePlacement.spread(), BiomeFilter.biome());
	}

	public static void init() {
		//Just here to load the class
	}
}
