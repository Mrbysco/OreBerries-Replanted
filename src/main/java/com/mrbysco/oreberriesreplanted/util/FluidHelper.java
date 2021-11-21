package com.mrbysco.oreberriesreplanted.util;

import com.mrbysco.oreberriesreplanted.Reference;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

public class FluidHelper {
	private final static ResourceLocation STILL_BERRY = new ResourceLocation(Reference.MOD_ID, "block/liquid_berry_still");
	private final static ResourceLocation FLOWING_BERRY = new ResourceLocation(Reference.MOD_ID, "block/liquid_berry_flow");

	public static FluidAttributes.Builder createAttributes(int color) {
		return FluidAttributes.builder(STILL_BERRY, FLOWING_BERRY)
				.color(color)
				.rarity(Rarity.COMMON)
				.density(2000)
				.viscosity(8200)
				.temperature(1000)
				.luminosity(0);
	}
}
