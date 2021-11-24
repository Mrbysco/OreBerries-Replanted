package com.mrbysco.oreberriesreplanted.client;

import com.mrbysco.oreberriesreplanted.client.ber.VatBER;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
	public static void onClientSetup(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.IRON_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.GOLD_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.COPPER_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.TIN_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.ALUMINUM_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.LEAD_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.NICKEL_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.URANIUM_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.OSMIUM_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.ZINC_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.SILVER_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.ESSENCE_BERRY_BUSH.get(), RenderType.cutoutMipped());

		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.POTTED_IRON_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.POTTED_GOLD_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.POTTED_COPPER_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.POTTED_TIN_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.POTTED_ALUMINUM_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.POTTED_LEAD_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.POTTED_NICKEL_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.POTTED_URANIUM_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.POTTED_OSMIUM_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.POTTED_ZINC_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.POTTED_SILVER_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OreBerryRegistry.POTTED_ESSENCE_BERRY_BUSH.get(), RenderType.cutoutMipped());
	}


	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(OreBerryRegistry.VAT_BLOCK_ENTITY.get(), VatBER::new);
	}

}
