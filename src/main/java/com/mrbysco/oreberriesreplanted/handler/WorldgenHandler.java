package com.mrbysco.oreberriesreplanted.handler;

import com.mrbysco.oreberriesreplanted.config.OreBerriesConfig;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryFeatures;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WorldgenHandler {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void biomeLoadingEvent(BiomeLoadingEvent event) {
		BiomeGenerationSettingsBuilder builder = event.getGeneration();
		Biome.BiomeCategory category = event.getCategory();
		ResourceKey<Biome> biomeKey = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());

		if (category != BiomeCategory.THEEND && category != BiomeCategory.NETHER) {
			if (BiomeDictionary.hasType(biomeKey, Type.OVERWORLD)) {
				if (OreBerriesConfig.COMMON.generateIronBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.IRON_OREBERRY_BUSH_PLACED_FEATURE);
				}

				if (OreBerriesConfig.COMMON.generateGoldBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.GOLD_OREBERRY_BUSH_PLACED_FEATURE);
				}

				if (OreBerriesConfig.COMMON.generateCopperBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.COPPER_OREBERRY_BUSH_PLACED_FEATURE);
				}

				if (OreBerriesConfig.COMMON.generateTinBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.TIN_OREBERRY_BUSH_PLACED_FEATURE);
				}

				if (OreBerriesConfig.COMMON.generateAluminumBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.ALUMINUM_OREBERRY_BUSH_PLACED_FEATURE);
				}

				if (OreBerriesConfig.COMMON.generateLeadBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.LEAD_OREBERRY_BUSH_PLACED_FEATURE);
				}

				if (OreBerriesConfig.COMMON.generateNickelBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.NICKEL_OREBERRY_BUSH_PLACED_FEATURE);
				}

				if (OreBerriesConfig.COMMON.generateUraniumBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.URANIUM_OREBERRY_BUSH_PLACED_FEATURE);
				}

				if (OreBerriesConfig.COMMON.generateOsmiumBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.OSMIUM_OREBERRY_BUSH_PLACED_FEATURE);
				}

				if (OreBerriesConfig.COMMON.generateZincBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.ZINC_OREBERRY_BUSH_PLACED_FEATURE);
				}

				if (OreBerriesConfig.COMMON.generateSilverBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.SILVER_OREBERRY_BUSH_PLACED_FEATURE);
				}

				if (OreBerriesConfig.COMMON.generateEssenceBush.get()) {
					builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.ESSENCE_OREBERRY_BUSH_PLACED_FEATURE);
				}
			}
		}
	}
}
