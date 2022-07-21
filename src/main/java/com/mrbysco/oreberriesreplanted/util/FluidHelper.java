package com.mrbysco.oreberriesreplanted.util;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

public class FluidHelper {

	public static FluidType.Properties createTypeProperties() {
		return FluidType.Properties.create()
				.canSwim(false)
				.canDrown(false)
				.pathType(BlockPathTypes.LAVA)
				.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
				.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
				.rarity(Rarity.COMMON)
				.density(2000)
				.viscosity(8200)
				.temperature(1000)
				.lightLevel(0);
	}
}
