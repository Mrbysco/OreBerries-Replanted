package com.mrbysco.oreberriesreplanted.block;

import com.mrbysco.oreberriesreplanted.config.OreBerriesConfig;

import java.util.function.Supplier;

public enum OreEnum {
	IRON(OreBerriesConfig.COMMON.ironBushMinY::get, OreBerriesConfig.COMMON.ironBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyIronBush::get),
	GOLD(OreBerriesConfig.COMMON.goldBushMinY::get, OreBerriesConfig.COMMON.goldBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyGoldBush::get),
	COPPER(OreBerriesConfig.COMMON.copperBushMinY::get, OreBerriesConfig.COMMON.copperBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyCopperBush::get),
	TIN(OreBerriesConfig.COMMON.tinBushMinY::get, OreBerriesConfig.COMMON.tinBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyTinBush::get),
	ALUMINUM(OreBerriesConfig.COMMON.aluminumBushMinY::get, OreBerriesConfig.COMMON.aluminumBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyAluminumBush::get),
	LEAD(OreBerriesConfig.COMMON.leadBushMinY::get, OreBerriesConfig.COMMON.leadBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyLeadBush::get),
	NICKEL(OreBerriesConfig.COMMON.nickelBushMinY::get, OreBerriesConfig.COMMON.nickelBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyNickelBush::get),
	URANIUM(OreBerriesConfig.COMMON.uraniumBushMinY::get, OreBerriesConfig.COMMON.uraniumBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyUraniumBush::get),
	OSMIUM(OreBerriesConfig.COMMON.osmiumBushMinY::get, OreBerriesConfig.COMMON.osmiumBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyOsmiumBush::get),
	ZINC(OreBerriesConfig.COMMON.zincBushMinY::get, OreBerriesConfig.COMMON.zincBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyZincBush::get),
	SILVER(OreBerriesConfig.COMMON.silverBushMinY::get, OreBerriesConfig.COMMON.silverBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlySilverBush::get),
	ESSENCE(OreBerriesConfig.COMMON.essenceBushMinY::get, OreBerriesConfig.COMMON.essenceBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyEssenceBush::get);

	private final Supplier<Integer> minY;
	private final Supplier<Integer> maxY;
	private final Supplier<Boolean> darknessOnly;

	OreEnum(Supplier<Integer> minY, Supplier<Integer> maxY, Supplier<Boolean> darknessOnly) {
		this.minY = minY;
		this.maxY = maxY;
		this.darknessOnly = darknessOnly;
	}

	public int getMinY() {
		return minY.get();
	}

	public int getMaxY() {
		return maxY.get();
	}

	public boolean getDarknessOnly() {
		return darknessOnly.get();
	}
}
