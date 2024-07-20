package com.mrbysco.oreberriesreplanted;

import com.mojang.logging.LogUtils;
import com.mrbysco.oreberriesreplanted.blockentity.VatBlockEntity;
import com.mrbysco.oreberriesreplanted.client.ClientHandler;
import com.mrbysco.oreberriesreplanted.config.OreBerriesConfig;
import com.mrbysco.oreberriesreplanted.registry.OreBerryPlacementModifiers;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import com.mrbysco.oreberriesreplanted.registry.OreBerryTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

@Mod(Reference.MOD_ID)
public class OreberriesReplanted {
	public static final Logger LOGGER = LogUtils.getLogger();

	public OreberriesReplanted(IEventBus eventBus, Dist dist, ModContainer container) {
		container.registerConfig(ModConfig.Type.COMMON, OreBerriesConfig.commonSpec);
		eventBus.register(OreBerriesConfig.class);

		eventBus.addListener(this::commonSetup);
		eventBus.addListener(this::registerCapabilities);

		OreBerryRegistry.BLOCKS.register(eventBus);
		OreBerryRegistry.ITEMS.register(eventBus);
		OreBerryRegistry.FLUIDS.register(eventBus);
		OreBerryRegistry.FLUID_TYPES.register(eventBus);
		OreBerryRegistry.BLOCK_ENTITY_TYPES.register(eventBus);
		OreBerryTab.CREATIVE_MODE_TABS.register(eventBus);
		OreBerryRecipes.RECIPE_TYPES.register(eventBus);
		OreBerryRecipes.RECIPE_SERIALIZERS.register(eventBus);
		OreBerryRegistry.FEATURES.register(eventBus);
		OreBerryPlacementModifiers.PLACEMENT_MODIFIERS.register(eventBus);
		OreBerryRegistry.load();

		if (dist.isClient()) {
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
			eventBus.addListener(ClientHandler::registerEntityRenders);
			eventBus.addListener(ClientHandler::registerItemColors);
		}
	}

	public void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, OreBerryRegistry.VAT_BLOCK_ENTITY.get(), VatBlockEntity::getHandler);
		event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, OreBerryRegistry.VAT_BLOCK_ENTITY.get(), VatBlockEntity::getTank);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			OreBerryRegistry.registerBlockData();
		});
	}
}
