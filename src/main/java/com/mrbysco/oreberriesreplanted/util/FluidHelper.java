package com.mrbysco.oreberriesreplanted.util;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

public class FluidHelper {

	public static FluidType.Properties createTypeProperties() {
		return FluidType.Properties.create()
				.canSwim(false)
				.canDrown(false)
				.pathType(PathType.LAVA)
				.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
				.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
				.rarity(Rarity.COMMON)
				.density(2000)
				.viscosity(8200)
				.temperature(1000)
				.lightLevel(0);
	}
}
