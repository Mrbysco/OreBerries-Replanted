package com.mrbysco.oreberriesreplanted.worldgen;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.worldgen.placement.ChanceRangePlacement;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class OreBerryPlacements {
	public static final ResourceKey<PlacedFeature> IRON_OREBERRY_BUSH_FEATURE = createKey("iron_oreberry_bush");
	public static final ResourceKey<PlacedFeature> GOLD_OREBERRY_BUSH_FEATURE = createKey("gold_oreberry_bush");
	public static final ResourceKey<PlacedFeature> COPPER_OREBERRY_BUSH_FEATURE = createKey("copper_oreberry_bush");
	public static final ResourceKey<PlacedFeature> TIN_OREBERRY_BUSH_FEATURE = createKey("tin_oreberry_bush");
	public static final ResourceKey<PlacedFeature> ALUMINUM_OREBERRY_BUSH_FEATURE = createKey("aluminum_oreberry_bush");
	public static final ResourceKey<PlacedFeature> LEAD_OREBERRY_BUSH_FEATURE = createKey("lead_oreberry_bush");
	public static final ResourceKey<PlacedFeature> NICKEL_OREBERRY_BUSH_FEATURE = createKey("nickel_oreberry_bush");
	public static final ResourceKey<PlacedFeature> URANIUM_OREBERRY_BUSH_FEATURE = createKey("uranium_oreberry_bush");
	public static final ResourceKey<PlacedFeature> OSMIUM_OREBERRY_BUSH_FEATURE = createKey("osmium_oreberry_bush");
	public static final ResourceKey<PlacedFeature> ZINC_OREBERRY_BUSH_FEATURE = createKey("zinc_oreberry_bush");
	public static final ResourceKey<PlacedFeature> SILVER_OREBERRY_BUSH_FEATURE = createKey("silver_oreberry_bush");
	public static final ResourceKey<PlacedFeature> ESSENCE_BERRY_BUSH_FEATURE = createKey("essence_berry_bush");
	
	public static ResourceKey<PlacedFeature> createKey(String path) {
		return ResourceKey.create(Registries.PLACED_FEATURE, Reference.modLoc(path));
	}

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);

		PlacementUtils.register(context, IRON_OREBERRY_BUSH_FEATURE, holdergetter.getOrThrow(OreBerryFeatures.IRON_OREBERRY_BUSH_FEATURE), getBushPlacements(-64, 64, 5));
		PlacementUtils.register(context, GOLD_OREBERRY_BUSH_FEATURE, holdergetter.getOrThrow(OreBerryFeatures.GOLD_OREBERRY_BUSH_FEATURE), getBushPlacements(-64, 32, 8));
		PlacementUtils.register(context, COPPER_OREBERRY_BUSH_FEATURE, holdergetter.getOrThrow(OreBerryFeatures.COPPER_OREBERRY_BUSH_FEATURE), getBushPlacements(-44, 60, 3));
		PlacementUtils.register(context, TIN_OREBERRY_BUSH_FEATURE, holdergetter.getOrThrow(OreBerryFeatures.TIN_OREBERRY_BUSH_FEATURE), getBushPlacements(-44, 60, 3));
		PlacementUtils.register(context, ALUMINUM_OREBERRY_BUSH_FEATURE, holdergetter.getOrThrow(OreBerryFeatures.ALUMINUM_OREBERRY_BUSH_FEATURE), getBushPlacements(-44, 60, 2));
		PlacementUtils.register(context, LEAD_OREBERRY_BUSH_FEATURE, holdergetter.getOrThrow(OreBerryFeatures.LEAD_OREBERRY_BUSH_FEATURE), getBushPlacements(-64, 40, 7));
		PlacementUtils.register(context, NICKEL_OREBERRY_BUSH_FEATURE, holdergetter.getOrThrow(OreBerryFeatures.NICKEL_OREBERRY_BUSH_FEATURE), getBushPlacements(-64, 120, 5));
		PlacementUtils.register(context, URANIUM_OREBERRY_BUSH_FEATURE, holdergetter.getOrThrow(OreBerryFeatures.URANIUM_OREBERRY_BUSH_FEATURE), getBushPlacements(-64, 60, 5));
		PlacementUtils.register(context, OSMIUM_OREBERRY_BUSH_FEATURE, holdergetter.getOrThrow(OreBerryFeatures.OSMIUM_OREBERRY_BUSH_FEATURE), getBushPlacements(-64, 60, 6));
		PlacementUtils.register(context, ZINC_OREBERRY_BUSH_FEATURE, holdergetter.getOrThrow(OreBerryFeatures.ZINC_OREBERRY_BUSH_FEATURE), getBushPlacements(-64, 70, 6));
		PlacementUtils.register(context, SILVER_OREBERRY_BUSH_FEATURE, holdergetter.getOrThrow(OreBerryFeatures.SILVER_OREBERRY_BUSH_FEATURE), getBushPlacements(-64, 40, 7));
		PlacementUtils.register(context, ESSENCE_BERRY_BUSH_FEATURE, holdergetter.getOrThrow(OreBerryFeatures.ESSENCE_BERRY_BUSH_FEATURE), getBushPlacements(-64, 32, 6));
	}

	public static List<PlacementModifier> getBushPlacements(int minY, int maxY, int rarity) {
		return List.of(new ChanceRangePlacement(minY, 0, maxY, rarity), InSquarePlacement.spread(), BiomeFilter.biome());
	}
}
