package com.mrbysco.oreberriesreplanted.fluid;

import net.neoforged.neoforge.fluids.FluidType;

public class BerryFluidType extends FluidType {
	private int color;

	public BerryFluidType(int color, Properties properties) {
		super(properties);
		this.color = color;
	}

	public int getColor() {
		return color;
	}

}
