package com.mrbysco.oreberriesreplanted.config;

import com.mrbysco.oreberriesreplanted.OreberriesReplanted;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

public class OreBerriesConfig {
	public static class Common {
		public final IntValue growthChance;

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


		Common(ModConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("General");

			growthChance = builder
					.comment("Dictates the 1 in X chance the Oreberry Bush grows every time it tries to grow [Default: 20]")
					.defineInRange("growthChance", 20, 1, Integer.MAX_VALUE);

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
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		OreberriesReplanted.LOGGER.debug("Loaded Oreberries Replanted's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		OreberriesReplanted.LOGGER.warn("Oreberries Replanted's config just got changed on the file system!");
	}

}
