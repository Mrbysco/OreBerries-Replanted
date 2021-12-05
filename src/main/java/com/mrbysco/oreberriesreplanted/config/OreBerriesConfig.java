package com.mrbysco.oreberriesreplanted.config;

import com.mrbysco.oreberriesreplanted.OreberriesReplanted;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public class OreBerriesConfig {
	public static class Common {
		public final IntValue growthChance;

		public final BooleanValue generateIronBush;
		public final BooleanValue generateGoldBush;
		public final BooleanValue generateCopperBush;
		public final BooleanValue generateTinBush;
		public final BooleanValue generateAluminumBush;
		public final BooleanValue generateLeadBush;
		public final BooleanValue generateNickelBush;
		public final BooleanValue generateUraniumBush;
		public final BooleanValue generateOsmiumBush;
		public final BooleanValue generateZincBush;
		public final BooleanValue generateSilverBush;
		public final BooleanValue generateEssenceBush;

		public final BooleanValue darknessOnlyIronBush;
		public final BooleanValue darknessOnlyGoldBush;
		public final BooleanValue darknessOnlyCopperBush;
		public final BooleanValue darknessOnlyTinBush;
		public final BooleanValue darknessOnlyAluminumBush;
		public final BooleanValue darknessOnlyLeadBush;
		public final BooleanValue darknessOnlyNickelBush;
		public final BooleanValue darknessOnlyUraniumBush;
		public final BooleanValue darknessOnlyOsmiumBush;
		public final BooleanValue darknessOnlyZincBush;
		public final BooleanValue darknessOnlySilverBush;
		public final BooleanValue darknessOnlyEssenceBush;

		public final IntValue ironBushDensity;
		public final IntValue goldBushDensity;
		public final IntValue copperBushDensity;
		public final IntValue tinBushDensity;
		public final IntValue aluminumBushDensity;
		public final IntValue leadBushDensity;
		public final IntValue nickelBushDensity;
		public final IntValue uraniumBushDensity;
		public final IntValue osmiumBushDensity;
		public final IntValue zincBushDensity;
		public final IntValue silverBushDensity;
		public final IntValue essenceBushDensity;

		public final IntValue ironBushRarity;
		public final IntValue goldBushRarity;
		public final IntValue copperBushRarity;
		public final IntValue tinBushRarity;
		public final IntValue aluminumBushRarity;
		public final IntValue leadBushRarity;
		public final IntValue nickelBushRarity;
		public final IntValue uraniumBushRarity;
		public final IntValue osmiumBushRarity;
		public final IntValue zincBushRarity;
		public final IntValue silverBushRarity;
		public final IntValue essenceBushRarity;

		public final IntValue ironBushMinY;
		public final IntValue ironBushMaxY;
		public final IntValue goldBushMinY;
		public final IntValue goldBushMaxY;
		public final IntValue copperBushMinY;
		public final IntValue copperBushMaxY;
		public final IntValue tinBushMinY;
		public final IntValue tinBushMaxY;
		public final IntValue aluminumBushMinY;
		public final IntValue aluminumBushMaxY;
		public final IntValue leadBushMinY;
		public final IntValue leadBushMaxY;
		public final IntValue nickelBushMinY;
		public final IntValue nickelBushMaxY;
		public final IntValue uraniumBushMinY;
		public final IntValue uraniumBushMaxY;
		public final IntValue osmiumBushMinY;
		public final IntValue osmiumBushMaxY;
		public final IntValue zincBushMinY;
		public final IntValue zincBushMaxY;
		public final IntValue silverBushMinY;
		public final IntValue silverBushMaxY;
		public final IntValue essenceBushMinY;
		public final IntValue essenceBushMaxY;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("General");

			growthChance = builder
					.comment("Dictates the 1 in X chance the Oreberry Bush grows every time it tries to grow [Default: 20]")
					.defineInRange("growthChance", 20, 1, Integer.MAX_VALUE);

			builder.pop();
			builder.comment("Generation settings")
					.push("Generation");

			generateIronBush = builder
					.comment("Generate Iron Bushes [Default: true]")
					.define("generateIronBush", true);

			generateGoldBush = builder
					.comment("Generate Gold Bushes [Default: true]")
					.define("generateGoldBush", true);

			generateCopperBush = builder
					.comment("Generate Copper Bushes [Default: true]")
					.define("generateCopperBush", true);

			generateTinBush = builder
					.comment("Generate Tin Bushes [Default: false]")
					.define("generateTinBush", false);

			generateAluminumBush = builder
					.comment("Generate Aluminum Bushes [Default: false]")
					.define("generateAluminumBush", false);

			generateLeadBush = builder
					.comment("Generate Lead Bushes [Default: false]")
					.define("generateLeadBush", false);

			generateNickelBush = builder
					.comment("Generate Nickel Bushes [Default: false]")
					.define("generateNickelBush", false);

			generateUraniumBush = builder
					.comment("Generate Uranium Bushes [Default: false]")
					.define("generateUraniumBush", false);

			generateOsmiumBush = builder
					.comment("Generate Osmium Bushes [Default: false]")
					.define("generateOsmiumBush", false);

			generateZincBush = builder
					.comment("Generate Zinc Bushes [Default: false]")
					.define("generateZincBush", false);

			generateSilverBush = builder
					.comment("Generate Silver Bushes [Default: false]")
					.define("generateSilverBush", false);

			generateEssenceBush = builder
					.comment("Generate Essence Bushes [Default: true]")
					.define("generateEssenceBush", true);

			builder.pop();
			builder.comment("Placement settings")
					.push("Placement");

			darknessOnlyIronBush = builder
					.comment("Only allow placing Iron Bushes in darkness [Default: true]")
					.define("darknessOnlyIronBush", true);

			darknessOnlyGoldBush = builder
					.comment("Only allow placing Gold Bushes in darkness [Default: true]")
					.define("darknessOnlyGoldBush", true);

			darknessOnlyCopperBush = builder
					.comment("Only allow placing Copper Bushes in darkness [Default: true]")
					.define("darknessOnlyCopperBush", true);

			darknessOnlyTinBush = builder
					.comment("Only allow placing Tin Bushes in darkness [Default: true]")
					.define("darknessOnlyTinBush", true);

			darknessOnlyAluminumBush = builder
					.comment("Only allow placing Aluminum Bushes in darkness [Default: true]")
					.define("darknessOnlyAluminumBush", true);

			darknessOnlyLeadBush = builder
					.comment("Only allow placing Lead Bushes in darkness [Default: true]")
					.define("darknessOnlyLeadBush", true);

			darknessOnlyNickelBush = builder
					.comment("Only allow placing Nickel Bushes in darkness [Default: true]")
					.define("darknessOnlyNickelBush", true);

			darknessOnlyUraniumBush = builder
					.comment("Only allow placing Uranium Bushes in darkness [Default: true]")
					.define("darknessOnlyUraniumBush", true);

			darknessOnlyOsmiumBush = builder
					.comment("Only allow placing Osmium Bushes in darkness [Default: true]")
					.define("darknessOnlyOsmiumBush", true);

			darknessOnlyZincBush = builder
					.comment("Only allow placing Zinc Bushes in darkness [Default: true]")
					.define("darknessOnlyZincBush", true);

			darknessOnlySilverBush = builder
					.comment("Only allow placing Silver Bushes in darkness [Default: true]")
					.define("darknessOnlySilverBush", true);

			darknessOnlyEssenceBush = builder
					.comment("Only allow placing Essence Bushes in darkness [Default: true]")
					.define("darknessOnlyEssenceBush", true);

			builder.pop();
			builder.comment("Density settings")
					.push("Density");

			ironBushDensity = builder
					.comment("Iron Bush Density [Default: 1]")
					.defineInRange("ironBushDensity", 1, 1, 32);

			goldBushDensity = builder
					.comment("Gold Bush Density [Default: 1]")
					.defineInRange("goldBushDensity", 1, 1, 32);

			copperBushDensity = builder
					.comment("Copper Bush Density [Default: 2]")
					.defineInRange("copperBushDensity", 2, 1, 32);

			tinBushDensity = builder
					.comment("Tin Bush Density [Default: 2]")
					.defineInRange("tinBushDensity", 2, 1, 32);

			aluminumBushDensity = builder
					.comment("Aluminum Bush Density [Default: 2]")
					.defineInRange("aluminumBushDensity", 2, 1, 32);

			leadBushDensity = builder
					.comment("Lead Bush Density [Default: 1]")
					.defineInRange("leadBushDensity", 1, 1, 32);

			nickelBushDensity = builder
					.comment("Nickel Bush Density [Default: 1]")
					.defineInRange("nickelBushDensity", 1, 1, 32);

			uraniumBushDensity = builder
					.comment("Uranium Bush Density [Default: 1]")
					.defineInRange("uraniumBushDensity", 1, 1, 32);

			osmiumBushDensity = builder
					.comment("Osmium Bush Density [Default: 1]")
					.defineInRange("osmiumBushDensity", 1, 1, 32);

			zincBushDensity = builder
					.comment("Zinc Bush Density [Default: 2]")
					.defineInRange("zincBushDensity", 2, 1, 32);

			silverBushDensity = builder
					.comment("Silver Bush Density [Default: 1]")
					.defineInRange("silverBushDensity", 1, 1, 32);

			essenceBushDensity = builder
					.comment("Essence Bush Density [Default: 2]")
					.defineInRange("essenceBushDensity", 2, 1, 32);

			builder.pop();
			builder.comment("Rarity settings")
					.push("Rarity");

			ironBushRarity = builder
					.comment("Iron Bush Rarity [Default: 5]")
					.defineInRange("ironBushRarity", 5, 1, Integer.MAX_VALUE);

			goldBushRarity = builder
					.comment("Gold Bush Rarity [Default: 8]")
					.defineInRange("goldBushRarity", 8, 1, Integer.MAX_VALUE);

			copperBushRarity = builder
					.comment("Copper Bush Rarity [Default: 3]")
					.defineInRange("copperBushRarity", 3, 1, Integer.MAX_VALUE);

			tinBushRarity = builder
					.comment("Tin Bush Rarity [Default: 3]")
					.defineInRange("tinBushRarity", 3, 1, Integer.MAX_VALUE);

			aluminumBushRarity = builder
					.comment("Aluminum Bush Rarity [Default: 2]")
					.defineInRange("aluminumBushRarity", 2, 1, Integer.MAX_VALUE);

			leadBushRarity = builder
					.comment("Lead Bush Rarity [Default: 7]")
					.defineInRange("leadBushRarity", 7, 1, Integer.MAX_VALUE);

			nickelBushRarity = builder
					.comment("Nickel Bush Rarity [Default: 5]")
					.defineInRange("nickelBushRarity", 5, 1, Integer.MAX_VALUE);

			uraniumBushRarity = builder
					.comment("Uranium Bush Rarity [Default: 9]")
					.defineInRange("uraniumBushRarity", 5, 1, Integer.MAX_VALUE);

			osmiumBushRarity = builder
					.comment("Osmium Bush Rarity [Default: 6]")
					.defineInRange("osmiumBushRarity", 6, 1, Integer.MAX_VALUE);

			zincBushRarity = builder
					.comment("Zinc Bush Rarity [Default: 6]")
					.defineInRange("zincBushDensity", 6, 1, Integer.MAX_VALUE);

			silverBushRarity = builder
					.comment("Silver Bush Rarity [Default: 7]")
					.defineInRange("silverBushRarity", 7, 1, Integer.MAX_VALUE);

			essenceBushRarity = builder
					.comment("Essence Bush Rarity [Default: 6]")
					.defineInRange("essenceBushRarity", 6, 1, Integer.MAX_VALUE);

			builder.pop();
			builder.comment("Location settings")
					.push("Location");

			ironBushMinY = builder
					.comment("Iron Bush Min Y [Default: -64]")
					.defineInRange("ironBushMinY", -64, Integer.MIN_VALUE, Integer.MAX_VALUE);
			ironBushMaxY = builder
					.comment("Iron Bush Max Y [Default: 62]")
					.defineInRange("ironBushMaxY", 64, Integer.MIN_VALUE, Integer.MAX_VALUE);

			goldBushMinY = builder
					.comment("Gold Bush Min Y [Default: -64]")
					.defineInRange("goldBushMinY", -64, Integer.MIN_VALUE, Integer.MAX_VALUE);
			goldBushMaxY = builder
					.comment("Gold Bush Max Y [Default: 32]")
					.defineInRange("goldBushMaxY", 32, Integer.MIN_VALUE, Integer.MAX_VALUE);

			copperBushMinY = builder
					.comment("Copper Bush Min Y [Default: -44]")
					.defineInRange("copperBushMinY", -44, Integer.MIN_VALUE, Integer.MAX_VALUE);
			copperBushMaxY = builder
					.comment("Copper Bush Max Y [Default: 60]")
					.defineInRange("copperBushMaxY", 60, Integer.MIN_VALUE, Integer.MAX_VALUE);

			tinBushMinY = builder
					.comment("Tin Bush Min Y [Default: -44]")
					.defineInRange("tinBushMinY", -44, Integer.MIN_VALUE, Integer.MAX_VALUE);
			tinBushMaxY = builder
					.comment("Tin Bush Max Y [Default: 60]")
					.defineInRange("tinBushMaxY", 60, Integer.MIN_VALUE, Integer.MAX_VALUE);

			aluminumBushMinY = builder
					.comment("Aluminum Bush Min Y [Default: -44]")
					.defineInRange("aluminumBushMinY", -44, Integer.MIN_VALUE, Integer.MAX_VALUE);
			aluminumBushMaxY = builder
					.comment("Aluminum Bush Max Y [Default: 60]")
					.defineInRange("aluminumBushMaxY", 60, Integer.MIN_VALUE, Integer.MAX_VALUE);

			leadBushMinY = builder
					.comment("Lead Bush Min Y [Default: -64]")
					.defineInRange("leadBushMinY", -64, Integer.MIN_VALUE, Integer.MAX_VALUE);
			leadBushMaxY = builder
					.comment("Lead Bush Max Y [Default: 40]")
					.defineInRange("leadBushMaxY", 40, Integer.MIN_VALUE, Integer.MAX_VALUE);

			nickelBushMinY = builder
					.comment("Nickel Bush Min Y [Default: -64]")
					.defineInRange("nickelBushMinY", -64, Integer.MIN_VALUE, Integer.MAX_VALUE);
			nickelBushMaxY = builder
					.comment("Nickel Bush Max Y [Default: 120]")
					.defineInRange("nickelBushMaxY", 120, Integer.MIN_VALUE, Integer.MAX_VALUE);

			uraniumBushMinY = builder
					.comment("Uranium Bush Min Y [Default: -64]")
					.defineInRange("uraniumBushMinY", -64, Integer.MIN_VALUE, Integer.MAX_VALUE);
			uraniumBushMaxY = builder
					.comment("Uranium Bush Max Y [Default: 60]")
					.defineInRange("uraniumBushMaxY", 60, Integer.MIN_VALUE, Integer.MAX_VALUE);

			osmiumBushMinY = builder
					.comment("Osmium Bush Min Y [Default: -64]")
					.defineInRange("osmiumBushMinY", -64, Integer.MIN_VALUE, Integer.MAX_VALUE);
			osmiumBushMaxY = builder
					.comment("Osmium Bush Max Y [Default: 60]")
					.defineInRange("osmiumBushMaxY", 60, Integer.MIN_VALUE, Integer.MAX_VALUE);

			zincBushMinY = builder
					.comment("Zinc Bush Min Y [Default: -64]")
					.defineInRange("zincBushMinY", -64, Integer.MIN_VALUE, Integer.MAX_VALUE);
			zincBushMaxY = builder
					.comment("Zinc Bush Max Y [Default: 70]")
					.defineInRange("zincBushMaxY", 70, Integer.MIN_VALUE, Integer.MAX_VALUE);

			silverBushMinY = builder
					.comment("Silver Bush Min Y [Default: -64]")
					.defineInRange("silverBushMinY", -64, Integer.MIN_VALUE, Integer.MAX_VALUE);
			silverBushMaxY = builder
					.comment("Silver Bush Max Y [Default: 40]")
					.defineInRange("silverBushMaxY", 40, Integer.MIN_VALUE, Integer.MAX_VALUE);

			essenceBushMinY = builder
					.comment("Essence Bush Min Y [Default: -64]")
					.defineInRange("essenceBushMinY", -64, Integer.MIN_VALUE, Integer.MAX_VALUE);
			essenceBushMaxY = builder
					.comment("Essence Bush Max Y [Default: 32]")
					.defineInRange("essenceBushMaxY", 32, Integer.MIN_VALUE, Integer.MAX_VALUE);

			builder.pop();
		}
	}

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;
	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		OreberriesReplanted.LOGGER.debug("Loaded Oreberries Replanted's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		OreberriesReplanted.LOGGER.fatal("Oreberries Replanted's config just got changed on the file system!");
	}

}
