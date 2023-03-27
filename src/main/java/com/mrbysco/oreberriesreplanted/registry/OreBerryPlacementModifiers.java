package com.mrbysco.oreberriesreplanted.registry;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.worldgen.placement.ChanceRangePlacement;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class OreBerryPlacementModifiers {
	public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, Reference.MOD_ID);

	public static final RegistryObject<PlacementModifierType<ChanceRangePlacement>> CAVE_EDGE_RANGE =
			PLACEMENT_MODIFIERS.register("change_range", () -> () -> ChanceRangePlacement.CODEC);
}
