package com.mrbysco.oreberriesreplanted.worldgen;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.block.OreBerryBushBlock;
import com.mrbysco.oreberriesreplanted.config.OreBerriesConfig;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import com.mrbysco.oreberriesreplanted.worldgen.placement.ChanceRangePlacement;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;
import java.util.function.Supplier;

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

	public static Holder<PlacedFeature> IRON_OREBERRY_BUSH_PLACED_FEATURE = PlacementUtils.register(new ResourceLocation(Reference.MOD_ID, "iron_oreberry_bush").toString(),
			IRON_OREBERRY_BUSH_FEATURE, getPlacedFeature(OreBerriesConfig.COMMON.ironBushMinY::get, OreBerriesConfig.COMMON.ironBushMaxY::get, OreBerriesConfig.COMMON.ironBushRarity::get));

	public static Holder<PlacedFeature> GOLD_OREBERRY_BUSH_PLACED_FEATURE = PlacementUtils.register(new ResourceLocation(Reference.MOD_ID, "gold_oreberry_bush").toString(),
			GOLD_OREBERRY_BUSH_FEATURE, getPlacedFeature(OreBerriesConfig.COMMON.goldBushMinY::get, OreBerriesConfig.COMMON.goldBushMaxY::get, OreBerriesConfig.COMMON.goldBushRarity::get));

	public static Holder<PlacedFeature> COPPER_OREBERRY_BUSH_PLACED_FEATURE = PlacementUtils.register(new ResourceLocation(Reference.MOD_ID, "copper_oreberry_bush").toString(),
			COPPER_OREBERRY_BUSH_FEATURE, getPlacedFeature(OreBerriesConfig.COMMON.copperBushMinY::get, OreBerriesConfig.COMMON.copperBushMaxY::get, OreBerriesConfig.COMMON.copperBushRarity::get));

	public static Holder<PlacedFeature> TIN_OREBERRY_BUSH_PLACED_FEATURE = PlacementUtils.register(new ResourceLocation(Reference.MOD_ID, "tin_oreberry_bush").toString(),
			TIN_OREBERRY_BUSH_FEATURE, getPlacedFeature(OreBerriesConfig.COMMON.tinBushMinY::get, OreBerriesConfig.COMMON.tinBushMaxY::get, OreBerriesConfig.COMMON.tinBushRarity::get));

	public static Holder<PlacedFeature> ALUMINUM_OREBERRY_BUSH_PLACED_FEATURE = PlacementUtils.register(new ResourceLocation(Reference.MOD_ID, "aluminum_oreberry_bush").toString(),
			ALUMINUM_OREBERRY_BUSH_FEATURE, getPlacedFeature(OreBerriesConfig.COMMON.aluminumBushMinY::get, OreBerriesConfig.COMMON.aluminumBushMaxY::get, OreBerriesConfig.COMMON.aluminumBushRarity::get));

	public static Holder<PlacedFeature> LEAD_OREBERRY_BUSH_PLACED_FEATURE = PlacementUtils.register(new ResourceLocation(Reference.MOD_ID, "lead_oreberry_bush").toString(),
			LEAD_OREBERRY_BUSH_FEATURE, getPlacedFeature(OreBerriesConfig.COMMON.leadBushMinY::get, OreBerriesConfig.COMMON.leadBushMaxY::get, OreBerriesConfig.COMMON.leadBushRarity::get));

	public static Holder<PlacedFeature> NICKEL_OREBERRY_BUSH_PLACED_FEATURE = PlacementUtils.register(new ResourceLocation(Reference.MOD_ID, "nickel_oreberry_bush").toString(),
			NICKEL_OREBERRY_BUSH_FEATURE, getPlacedFeature(OreBerriesConfig.COMMON.nickelBushMinY::get, OreBerriesConfig.COMMON.nickelBushMaxY::get, OreBerriesConfig.COMMON.nickelBushRarity::get));

	public static Holder<PlacedFeature> URANIUM_OREBERRY_BUSH_PLACED_FEATURE = PlacementUtils.register(new ResourceLocation(Reference.MOD_ID, "uranium_oreberry_bush").toString(),
			URANIUM_OREBERRY_BUSH_FEATURE, getPlacedFeature(OreBerriesConfig.COMMON.uraniumBushMinY::get, OreBerriesConfig.COMMON.uraniumBushMaxY::get, OreBerriesConfig.COMMON.uraniumBushRarity::get));

	public static Holder<PlacedFeature> OSMIUM_OREBERRY_BUSH_PLACED_FEATURE = PlacementUtils.register(new ResourceLocation(Reference.MOD_ID, "osmium_oreberry_bush").toString(),
			OSMIUM_OREBERRY_BUSH_FEATURE, getPlacedFeature(OreBerriesConfig.COMMON.osmiumBushMinY::get, OreBerriesConfig.COMMON.osmiumBushMaxY::get, OreBerriesConfig.COMMON.osmiumBushRarity::get));

	public static Holder<PlacedFeature> ZINC_OREBERRY_BUSH_PLACED_FEATURE = PlacementUtils.register(new ResourceLocation(Reference.MOD_ID, "zinc_oreberry_bush").toString(),
			ZINC_OREBERRY_BUSH_FEATURE, getPlacedFeature(OreBerriesConfig.COMMON.zincBushMinY::get, OreBerriesConfig.COMMON.zincBushMaxY::get, OreBerriesConfig.COMMON.zincBushRarity::get));

	public static Holder<PlacedFeature> SILVER_OREBERRY_BUSH_PLACED_FEATURE = PlacementUtils.register(new ResourceLocation(Reference.MOD_ID, "silver_oreberry_bush").toString(),
			SILVER_OREBERRY_BUSH_FEATURE, getPlacedFeature(OreBerriesConfig.COMMON.silverBushMinY::get, OreBerriesConfig.COMMON.silverBushMaxY::get, OreBerriesConfig.COMMON.silverBushRarity::get));

	public static Holder<PlacedFeature> ESSENCE_OREBERRY_BUSH_PLACED_FEATURE = PlacementUtils.register(new ResourceLocation(Reference.MOD_ID, "essence_oreberry_bush").toString(),
			ESSENCE_BERRY_BUSH_FEATURE, getPlacedFeature(OreBerriesConfig.COMMON.essenceBushMinY::get, OreBerriesConfig.COMMON.essenceBushMaxY::get, OreBerriesConfig.COMMON.essenceBushRarity::get));

	public static List<PlacementModifier> getPlacedFeature(Supplier<Integer> minY, Supplier<Integer> maxY, Supplier<Integer> rarity) {
		PlacementModifier rangeModifier = new ChanceRangePlacement(minY.get(), 0, maxY.get(), rarity.get());
		return List.of(rangeModifier, InSquarePlacement.spread(), BiomeFilter.biome());
	}

	public static void init() {
		//Just here to load the class
	}
}
