package com.mrbysco.oreberriesreplanted.handler;

import com.mrbysco.oreberriesreplanted.config.OreBerriesConfig;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryFeatures;
import com.mrbysco.oreberriesreplanted.worldgen.placement.ChanceRangePlacement;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.function.Supplier;

public class WorldgenHandler {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void biomeLoadingEvent(BiomeLoadingEvent event) {
		BiomeGenerationSettingsBuilder builder = event.getGeneration();
		Biome.BiomeCategory category = event.getCategory();
		ResourceKey<Biome> biomeKey = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());

		if(category != BiomeCategory.THEEND && category != BiomeCategory.NETHER) {
			if(BiomeDictionary.hasType(biomeKey, Type.OVERWORLD)) {
				if(OreBerriesConfig.COMMON.generateIronBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, getPlacedFeature(OreBerryFeatures.IRON_OREBERRY_BUSH_FEATURE,
							OreBerriesConfig.COMMON.ironBushMinY::get, OreBerriesConfig.COMMON.ironBushMaxY::get, OreBerriesConfig.COMMON.ironBushRarity::get));
				}

				if(OreBerriesConfig.COMMON.generateGoldBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, getPlacedFeature(OreBerryFeatures.GOLD_OREBERRY_BUSH_FEATURE,
							OreBerriesConfig.COMMON.goldBushMinY::get, OreBerriesConfig.COMMON.goldBushMaxY::get, OreBerriesConfig.COMMON.goldBushRarity::get));
				}

				if(OreBerriesConfig.COMMON.generateCopperBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, getPlacedFeature(OreBerryFeatures.COPPER_OREBERRY_BUSH_FEATURE,
							OreBerriesConfig.COMMON.copperBushMinY::get, OreBerriesConfig.COMMON.copperBushMaxY::get, OreBerriesConfig.COMMON.copperBushRarity::get));
				}

				if(OreBerriesConfig.COMMON.generateTinBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, getPlacedFeature(OreBerryFeatures.TIN_OREBERRY_BUSH_FEATURE,
							OreBerriesConfig.COMMON.tinBushMinY::get, OreBerriesConfig.COMMON.tinBushMaxY::get, OreBerriesConfig.COMMON.tinBushRarity::get));
				}

				if(OreBerriesConfig.COMMON.generateAluminumBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, getPlacedFeature(OreBerryFeatures.ALUMINUM_OREBERRY_BUSH_FEATURE,
							OreBerriesConfig.COMMON.aluminumBushMinY::get, OreBerriesConfig.COMMON.aluminumBushMaxY::get, OreBerriesConfig.COMMON.aluminumBushRarity::get));
				}

				if(OreBerriesConfig.COMMON.generateLeadBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, getPlacedFeature(OreBerryFeatures.LEAD_OREBERRY_BUSH_FEATURE,
							OreBerriesConfig.COMMON.leadBushMinY::get, OreBerriesConfig.COMMON.leadBushMaxY::get, OreBerriesConfig.COMMON.leadBushRarity::get));
				}

				if(OreBerriesConfig.COMMON.generateNickelBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, getPlacedFeature(OreBerryFeatures.NICKEL_OREBERRY_BUSH_FEATURE,
							OreBerriesConfig.COMMON.nickelBushMinY::get, OreBerriesConfig.COMMON.nickelBushMaxY::get, OreBerriesConfig.COMMON.nickelBushRarity::get));
				}

				if(OreBerriesConfig.COMMON.generateUraniumBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, getPlacedFeature(OreBerryFeatures.URANIUM_OREBERRY_BUSH_FEATURE,
							OreBerriesConfig.COMMON.uraniumBushMinY::get, OreBerriesConfig.COMMON.uraniumBushMaxY::get, OreBerriesConfig.COMMON.uraniumBushRarity::get));
				}

				if(OreBerriesConfig.COMMON.generateOsmiumBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, getPlacedFeature(OreBerryFeatures.OSMIUM_OREBERRY_BUSH_FEATURE,
							OreBerriesConfig.COMMON.osmiumBushMinY::get, OreBerriesConfig.COMMON.osmiumBushMaxY::get, OreBerriesConfig.COMMON.osmiumBushRarity::get));
				}

				if(OreBerriesConfig.COMMON.generateZincBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, getPlacedFeature(OreBerryFeatures.ZINC_OREBERRY_BUSH_FEATURE,
							OreBerriesConfig.COMMON.zincBushMinY::get, OreBerriesConfig.COMMON.zincBushMaxY::get, OreBerriesConfig.COMMON.zincBushRarity::get));
				}

				if(OreBerriesConfig.COMMON.generateSilverBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, getPlacedFeature(OreBerryFeatures.SILVER_OREBERRY_BUSH_FEATURE,
							OreBerriesConfig.COMMON.silverBushMinY::get, OreBerriesConfig.COMMON.silverBushMaxY::get, OreBerriesConfig.COMMON.silverBushRarity::get));
				}

				if(OreBerriesConfig.COMMON.generateEssenceBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, getPlacedFeature(OreBerryFeatures.ESSENCE_BERRY_BUSH_FEATURE,
							OreBerriesConfig.COMMON.essenceBushMinY::get, OreBerriesConfig.COMMON.essenceBushMaxY::get, OreBerriesConfig.COMMON.essenceBushRarity::get));
				}
			}
		}
	}

	public PlacedFeature getPlacedFeature(ConfiguredFeature<?, ?> feature, Supplier<Integer> minY, Supplier<Integer> maxY, Supplier<Integer> rarity) {
		PlacementModifier rangeModifier = new ChanceRangePlacement(minY.get(), 0, maxY.get(), rarity.get());
		return feature.placed(rangeModifier, InSquarePlacement.spread(), BiomeFilter.biome());
	}
}
