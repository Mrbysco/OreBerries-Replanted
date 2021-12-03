package com.mrbysco.oreberriesreplanted.block;

import com.mrbysco.oreberriesreplanted.config.OreBerriesConfig;

import java.util.function.Supplier;

public enum OreEnum {
	IRON(OreBerriesConfig.COMMON.ironBushMinY::get, OreBerriesConfig.COMMON.ironBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyIronBush::get, OreBerriesConfig.COMMON.ironBushDensity::get),
	GOLD(OreBerriesConfig.COMMON.goldBushMinY::get, OreBerriesConfig.COMMON.goldBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyGoldBush::get, OreBerriesConfig.COMMON.goldBushDensity::get),
	COPPER(OreBerriesConfig.COMMON.copperBushMinY::get, OreBerriesConfig.COMMON.copperBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyCopperBush::get, OreBerriesConfig.COMMON.copperBushDensity::get),
	TIN(OreBerriesConfig.COMMON.tinBushMinY::get, OreBerriesConfig.COMMON.tinBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyTinBush::get, OreBerriesConfig.COMMON.tinBushDensity::get),
	ALUMINUM(OreBerriesConfig.COMMON.aluminumBushMinY::get, OreBerriesConfig.COMMON.aluminumBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyAluminumBush::get, OreBerriesConfig.COMMON.aluminumBushDensity::get),
	LEAD(OreBerriesConfig.COMMON.leadBushMinY::get, OreBerriesConfig.COMMON.leadBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyLeadBush::get, OreBerriesConfig.COMMON.leadBushDensity::get),
	NICKEL(OreBerriesConfig.COMMON.nickelBushMinY::get, OreBerriesConfig.COMMON.nickelBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyNickelBush::get, OreBerriesConfig.COMMON.nickelBushDensity::get),
	URANIUM(OreBerriesConfig.COMMON.uraniumBushMinY::get, OreBerriesConfig.COMMON.uraniumBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyUraniumBush::get, OreBerriesConfig.COMMON.uraniumBushDensity::get),
	OSMIUM(OreBerriesConfig.COMMON.osmiumBushMinY::get, OreBerriesConfig.COMMON.osmiumBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyOsmiumBush::get, OreBerriesConfig.COMMON.osmiumBushDensity::get),
	ZINC(OreBerriesConfig.COMMON.zincBushMinY::get, OreBerriesConfig.COMMON.zincBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyZincBush::get, OreBerriesConfig.COMMON.zincBushDensity::get),
	SILVER(OreBerriesConfig.COMMON.silverBushMinY::get, OreBerriesConfig.COMMON.silverBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlySilverBush::get, OreBerriesConfig.COMMON.silverBushDensity::get),
	ESSENCE(OreBerriesConfig.COMMON.essenceBushMinY::get, OreBerriesConfig.COMMON.essenceBushMaxY::get, OreBerriesConfig.COMMON.darknessOnlyEssenceBush::get, OreBerriesConfig.COMMON.essenceBushDensity::get);

	private final Supplier<Integer> minY;
	private final Supplier<Integer> maxY;
	private final Supplier<Boolean> darknessOnly;
	private final Supplier<Integer> density;

	OreEnum(Supplier<Integer> minY, Supplier<Integer> maxY, Supplier<Boolean> darknessOnly, Supplier<Integer> density) {
		this.minY = minY;
		this.maxY = maxY;
		this.darknessOnly = darknessOnly;
		this.density = density;
	}

	public int getMinY() {
		return minY.get();
	}

	public int getMaxY() {
		return maxY.get();
	}

	public int getDensity() {
		return density.get();
	}

	public boolean getDarknessOnly() {
		return darknessOnly.get();
	}
}
