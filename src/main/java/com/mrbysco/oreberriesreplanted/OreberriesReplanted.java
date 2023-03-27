package com.mrbysco.oreberriesreplanted;

import com.mojang.logging.LogUtils;
import com.mrbysco.oreberriesreplanted.client.ClientHandler;
import com.mrbysco.oreberriesreplanted.config.OreBerriesConfig;
import com.mrbysco.oreberriesreplanted.registry.OreBerryPlacementModifiers;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import com.mrbysco.oreberriesreplanted.registry.OreBerryTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Reference.MOD_ID)
public class OreberriesReplanted {
	public static final Logger LOGGER = LogUtils.getLogger();

	public OreberriesReplanted() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OreBerriesConfig.commonSpec);
		eventBus.register(OreBerriesConfig.class);

		eventBus.addListener(this::commonSetup);

		OreBerryRegistry.BLOCKS.register(eventBus);
		OreBerryRegistry.ITEMS.register(eventBus);
		OreBerryRegistry.FLUIDS.register(eventBus);
		OreBerryRegistry.FLUID_TYPES.register(eventBus);
		OreBerryRegistry.BLOCK_ENTITY_TYPES.register(eventBus);
		OreBerryRecipes.RECIPE_TYPES.register(eventBus);
		OreBerryRecipes.RECIPE_SERIALIZERS.register(eventBus);
		OreBerryRegistry.FEATURES.register(eventBus);
		OreBerryPlacementModifiers.PLACEMENT_MODIFIERS.register(eventBus);
		OreBerryRegistry.load();

		eventBus.register(new OreBerryTab());

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::registerEntityRenders);
			eventBus.addListener(ClientHandler::registerItemColors);
		});
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			OreBerryRegistry.registerBlockData();
		});
	}
}
