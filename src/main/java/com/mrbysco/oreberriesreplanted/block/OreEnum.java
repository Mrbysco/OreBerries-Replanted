package com.mrbysco.oreberriesreplanted.block;

import com.mrbysco.oreberriesreplanted.config.OreBerriesConfig;
import net.minecraft.util.StringRepresentable;

import java.util.function.Supplier;

public enum OreEnum implements StringRepresentable {
	IRON("iron", OreBerriesConfig.COMMON.darknessOnlyIronBush, OreBerriesConfig.COMMON.ironBushDensity),
	GOLD("gold", OreBerriesConfig.COMMON.darknessOnlyGoldBush, OreBerriesConfig.COMMON.goldBushDensity),
	COPPER("copper", OreBerriesConfig.COMMON.darknessOnlyCopperBush, OreBerriesConfig.COMMON.copperBushDensity),
	TIN("tin", OreBerriesConfig.COMMON.darknessOnlyTinBush, OreBerriesConfig.COMMON.tinBushDensity),
	ALUMINUM("aluminum", OreBerriesConfig.COMMON.darknessOnlyAluminumBush, OreBerriesConfig.COMMON.aluminumBushDensity),
	LEAD("lead", OreBerriesConfig.COMMON.darknessOnlyLeadBush, OreBerriesConfig.COMMON.leadBushDensity),
	NICKEL("nickel", OreBerriesConfig.COMMON.darknessOnlyNickelBush, OreBerriesConfig.COMMON.nickelBushDensity),
	URANIUM("uranium", OreBerriesConfig.COMMON.darknessOnlyUraniumBush, OreBerriesConfig.COMMON.uraniumBushDensity),
	OSMIUM("osmium", OreBerriesConfig.COMMON.darknessOnlyOsmiumBush, OreBerriesConfig.COMMON.osmiumBushDensity),
	ZINC("zinc", OreBerriesConfig.COMMON.darknessOnlyZincBush, OreBerriesConfig.COMMON.zincBushDensity),
	SILVER("silver", OreBerriesConfig.COMMON.darknessOnlySilverBush, OreBerriesConfig.COMMON.silverBushDensity),
	ESSENCE("essence", OreBerriesConfig.COMMON.darknessOnlyEssenceBush, OreBerriesConfig.COMMON.essenceBushDensity);

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

	@Override
	public String getSerializedName() {
		return name;
	}
}
