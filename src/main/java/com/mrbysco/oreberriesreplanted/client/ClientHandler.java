package com.mrbysco.oreberriesreplanted.client;

import com.mrbysco.oreberriesreplanted.client.ber.VatBER;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(OreBerryRegistry.VAT_BLOCK_ENTITY.get(), VatBER::new);
	}
}
