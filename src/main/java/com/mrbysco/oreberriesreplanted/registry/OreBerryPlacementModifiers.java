package com.mrbysco.oreberriesreplanted.registry;

import com.mojang.serialization.Codec;
import com.mrbysco.oreberriesreplanted.worldgen.placement.ChanceRangePlacement;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class OreBerryPlacementModifiers {
	public static final PlacementModifierType<ChanceRangePlacement> CAVE_EDGE_RANGE = register("change_range", ChanceRangePlacement.CODEC);

	private static <P extends PlacementModifier> PlacementModifierType<P> register(String name, Codec<P> codec) {
		return Registry.register(Registry.PLACEMENT_MODIFIERS, name, () -> {
			return codec;
		});
	}

	public static void init() {
		//Just here to load the class
	}
}
