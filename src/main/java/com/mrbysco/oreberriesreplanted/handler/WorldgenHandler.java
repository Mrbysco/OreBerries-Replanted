package com.mrbysco.oreberriesreplanted.handler;

import com.mrbysco.oreberriesreplanted.config.OreBerriesConfig;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryFeatures;
import com.mrbysco.oreberriesreplanted.worldgen.placement.ChanceTopSolidRangeConfig;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WorldgenHandler {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void biomeLoadingEvent(BiomeLoadingEvent event) {
		BiomeGenerationSettingsBuilder builder = event.getGeneration();
		Biome.BiomeCategory category = event.getCategory();
		if(category != BiomeCategory.THEEND && category != BiomeCategory.NETHER) {
			if(OreBerriesConfig.COMMON.generateIronBush.get()) {
				builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.IRON_OREBERRY_BUSH_FEATURE
						.decorated(OreBerryRegistry.CAVE_EDGE_RANGE.get().configured(new ChanceTopSolidRangeConfig(OreBerriesConfig.COMMON.ironBushMinY.get(), 0,
								OreBerriesConfig.COMMON.ironBushMaxY.get(), OreBerriesConfig.COMMON.ironBushRarity.get()))));
			}

			if(OreBerriesConfig.COMMON.generateGoldBush.get()) {
				builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.GOLD_OREBERRY_BUSH_FEATURE
						.decorated(OreBerryRegistry.CAVE_EDGE_RANGE.get().configured(new ChanceTopSolidRangeConfig(OreBerriesConfig.COMMON.goldBushMinY.get(), 0,
								OreBerriesConfig.COMMON.goldBushMaxY.get(), OreBerriesConfig.COMMON.goldBushRarity.get()))));
			}

			if(OreBerriesConfig.COMMON.generateCopperBush.get()) {
				builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.COPPER_OREBERRY_BUSH_FEATURE
						.decorated(OreBerryRegistry.CAVE_EDGE_RANGE.get().configured(new ChanceTopSolidRangeConfig(OreBerriesConfig.COMMON.copperBushMinY.get(), 0,
								OreBerriesConfig.COMMON.copperBushMaxY.get(), OreBerriesConfig.COMMON.copperBushRarity.get()))));
			}

			if(OreBerriesConfig.COMMON.generateTinBush.get()) {
				builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.TIN_OREBERRY_BUSH_FEATURE
						.decorated(OreBerryRegistry.CAVE_EDGE_RANGE.get().configured(new ChanceTopSolidRangeConfig(OreBerriesConfig.COMMON.tinBushMinY.get(), 0,
								OreBerriesConfig.COMMON.tinBushMaxY.get(), OreBerriesConfig.COMMON.tinBushRarity.get()))));
			}

			if(OreBerriesConfig.COMMON.generateAluminumBush.get()) {
				builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.ALUMINUM_OREBERRY_BUSH_FEATURE
						.decorated(OreBerryRegistry.CAVE_EDGE_RANGE.get().configured(new ChanceTopSolidRangeConfig(OreBerriesConfig.COMMON.aluminumBushMinY.get(), 0,
								OreBerriesConfig.COMMON.aluminumBushMaxY.get(), OreBerriesConfig.COMMON.aluminumBushRarity.get()))));
			}

			if(OreBerriesConfig.COMMON.generateLeadBush.get()) {
				builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.LEAD_OREBERRY_BUSH_FEATURE
						.decorated(OreBerryRegistry.CAVE_EDGE_RANGE.get().configured(new ChanceTopSolidRangeConfig(OreBerriesConfig.COMMON.leadBushMinY.get(), 0,
								OreBerriesConfig.COMMON.leadBushMaxY.get(), OreBerriesConfig.COMMON.leadBushRarity.get()))));
			}

			if(OreBerriesConfig.COMMON.generateNickelBush.get()) {
				builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.NICKEL_OREBERRY_BUSH_FEATURE
						.decorated(OreBerryRegistry.CAVE_EDGE_RANGE.get().configured(new ChanceTopSolidRangeConfig(OreBerriesConfig.COMMON.nickelBushMinY.get(), 0,
								OreBerriesConfig.COMMON.nickelBushMaxY.get(), OreBerriesConfig.COMMON.nickelBushRarity.get()))));
			}

			if(OreBerriesConfig.COMMON.generateUraniumBush.get()) {
				builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.URANIUM_OREBERRY_BUSH_FEATURE
						.decorated(OreBerryRegistry.CAVE_EDGE_RANGE.get().configured(new ChanceTopSolidRangeConfig(OreBerriesConfig.COMMON.uraniumBushMinY.get(), 0,
								OreBerriesConfig.COMMON.uraniumBushMaxY.get(), OreBerriesConfig.COMMON.uraniumBushRarity.get()))));
			}

			if(OreBerriesConfig.COMMON.generateOsmiumBush.get()) {
				builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.OSMIUM_OREBERRY_BUSH_FEATURE
						.decorated(OreBerryRegistry.CAVE_EDGE_RANGE.get().configured(new ChanceTopSolidRangeConfig(OreBerriesConfig.COMMON.osmiumBushMinY.get(), 0,
								OreBerriesConfig.COMMON.osmiumBushMaxY.get(), OreBerriesConfig.COMMON.osmiumBushRarity.get()))));
			}

			if(OreBerriesConfig.COMMON.generateZincBush.get()) {
				builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.ZINC_OREBERRY_BUSH_FEATURE
						.decorated(OreBerryRegistry.CAVE_EDGE_RANGE.get().configured(new ChanceTopSolidRangeConfig(OreBerriesConfig.COMMON.zincBushMinY.get(), 0,
								OreBerriesConfig.COMMON.zincBushMaxY.get(), OreBerriesConfig.COMMON.zincBushRarity.get()))));
			}

			if(OreBerriesConfig.COMMON.generateSilverBush.get()) {
				builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.SILVER_OREBERRY_BUSH_FEATURE
						.decorated(OreBerryRegistry.CAVE_EDGE_RANGE.get().configured(new ChanceTopSolidRangeConfig(OreBerriesConfig.COMMON.silverBushMinY.get(), 0,
								OreBerriesConfig.COMMON.silverBushMaxY.get(), OreBerriesConfig.COMMON.silverBushRarity.get()))));
			}

			if(OreBerriesConfig.COMMON.generateEssenceBush.get()) {
				builder.addFeature(Decoration.UNDERGROUND_ORES, OreBerryFeatures.ESSENCE_BERRY_BUSH_FEATURE
						.decorated(OreBerryRegistry.CAVE_EDGE_RANGE.get().configured(new ChanceTopSolidRangeConfig(OreBerriesConfig.COMMON.essenceBushMinY.get(), 0,
								OreBerriesConfig.COMMON.essenceBushMaxY.get(), OreBerriesConfig.COMMON.essenceBushRarity.get()))));
			}
		}
	}
}
