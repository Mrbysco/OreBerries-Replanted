package com.mrbysco.oreberriesreplanted.block;

import com.mrbysco.oreberriesreplanted.config.OreBerriesConfig;

import java.util.function.Supplier;

public enum OreEnum {
	IRON("iron", OreBerriesConfig.COMMON.darknessOnlyIronBush::get, OreBerriesConfig.COMMON.ironBushDensity::get),
	GOLD("gold", OreBerriesConfig.COMMON.darknessOnlyGoldBush::get, OreBerriesConfig.COMMON.goldBushDensity::get),
	COPPER("copper", OreBerriesConfig.COMMON.darknessOnlyCopperBush::get, OreBerriesConfig.COMMON.copperBushDensity::get),
	TIN("tin", OreBerriesConfig.COMMON.darknessOnlyTinBush::get, OreBerriesConfig.COMMON.tinBushDensity::get),
	ALUMINUM("aluminum", OreBerriesConfig.COMMON.darknessOnlyAluminumBush::get, OreBerriesConfig.COMMON.aluminumBushDensity::get),
	LEAD("lead", OreBerriesConfig.COMMON.darknessOnlyLeadBush::get, OreBerriesConfig.COMMON.leadBushDensity::get),
	NICKEL("nickel", OreBerriesConfig.COMMON.darknessOnlyNickelBush::get, OreBerriesConfig.COMMON.nickelBushDensity::get),
	URANIUM("uranium", OreBerriesConfig.COMMON.darknessOnlyUraniumBush::get, OreBerriesConfig.COMMON.uraniumBushDensity::get),
	OSMIUM("osmium", OreBerriesConfig.COMMON.darknessOnlyOsmiumBush::get, OreBerriesConfig.COMMON.osmiumBushDensity::get),
	ZINC("zinc", OreBerriesConfig.COMMON.darknessOnlyZincBush::get, OreBerriesConfig.COMMON.zincBushDensity::get),
	SILVER("silver", OreBerriesConfig.COMMON.darknessOnlySilverBush::get, OreBerriesConfig.COMMON.silverBushDensity::get),
	ESSENCE("essence", OreBerriesConfig.COMMON.darknessOnlyEssenceBush::get, OreBerriesConfig.COMMON.essenceBushDensity::get);

	private final String name;
	private final Supplier<Boolean> darknessOnly;
	private final Supplier<Integer> density;

	OreEnum(String name, Supplier<Boolean> darknessOnly, Supplier<Integer> density) {
		this.name = name;
		this.darknessOnly = darknessOnly;
		this.density = density;
	}

	public int getDensity() {
		return density.get();
	}

	public boolean getDarknessOnly() {
		return darknessOnly.get();
	}
}
