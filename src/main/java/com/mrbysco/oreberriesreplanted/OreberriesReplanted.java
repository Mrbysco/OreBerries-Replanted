package com.mrbysco.oreberriesreplanted;

import com.mrbysco.oreberriesreplanted.client.ClientHandler;
import com.mrbysco.oreberriesreplanted.config.OreBerriesConfig;
import com.mrbysco.oreberriesreplanted.handler.WorldgenHandler;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class OreberriesReplanted {
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    public OreberriesReplanted() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OreBerriesConfig.commonSpec);
        eventBus.register(OreBerriesConfig.class);

        eventBus.addListener(this::commonSetup);

        OreBerryRegistry.BLOCKS.register(eventBus);
        OreBerryRegistry.ITEMS.register(eventBus);
        OreBerryRegistry.FLUIDS.register(eventBus);
        OreBerryRegistry.TILES.register(eventBus);
        OreBerryRegistry.RECIPE_SERIALIZERS.register(eventBus);
        OreBerryRegistry.FEATURES.register(eventBus);
        OreBerryRegistry.DECORATORS.register(eventBus);

        MinecraftForge.EVENT_BUS.register(new WorldgenHandler());

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            eventBus.addListener(ClientHandler::onClientSetup);
        });
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            OreBerryRegistry.registerBlockData();
        });
    }
}
