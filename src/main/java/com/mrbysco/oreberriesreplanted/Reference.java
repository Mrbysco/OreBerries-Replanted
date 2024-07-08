package com.mrbysco.oreberriesreplanted;

import net.minecraft.resources.ResourceLocation;

public class Reference {
	public static final String MOD_ID = "oreberriesreplanted";

	public static final String IRON_TOOLTIP = MOD_ID + ".iron_oreberry.tooltip";
	public static final String GOLD_TOOLTIP = MOD_ID + ".gold_oreberry.tooltip";
	public static final String COPPER_TOOLTIP = MOD_ID + ".copper_oreberry.tooltip";
	public static final String TIN_TOOLTIP = MOD_ID + ".tin_oreberry.tooltip";
	public static final String ALUMINUM_TOOLTIP = MOD_ID + ".aluminum_oreberry.tooltip";
	public static final String LEAD_TOOLTIP = MOD_ID + ".lead_oreberry.tooltip";
	public static final String NICKEL_TOOLTIP = MOD_ID + ".nickel_oreberry.tooltip";
	public static final String URANIUM_TOOLTIP = MOD_ID + ".uranium_oreberry.tooltip";
	public static final String OSMIUM_TOOLTIP = MOD_ID + ".osmium_oreberry.tooltip";
	public static final String ZINC_TOOLTIP = MOD_ID + ".zinc_oreberry.tooltip";
	public static final String SILVER_TOOLTIP = MOD_ID + ".silver_oreberry.tooltip";
	public static final String ESSENCE_TOOLTIP = MOD_ID + ".essence_berry.tooltip";
	
	public static ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
