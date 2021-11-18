package com.mrbysco.oreberriesreplanted.client;

import com.mrbysco.oreberriesreplanted.client.tesr.VatTESR;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
	public static void onClientSetup(final FMLClientSetupEvent event) {
		ClientRegistry.bindTileEntityRenderer(OreBerryRegistry.VAT_TILE.get(), VatTESR::new);

		RenderTypeLookup.setRenderLayer(OreBerryRegistry.IRON_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.GOLD_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.COPPER_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.TIN_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.ALUMINUM_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.LEAD_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.NICKEL_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.URANIUM_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.OSMIUM_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.ZINC_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.SILVER_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.ESSENCE_BERRY_BUSH.get(), RenderType.cutoutMipped());

		RenderTypeLookup.setRenderLayer(OreBerryRegistry.POTTED_IRON_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.POTTED_GOLD_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.POTTED_COPPER_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.POTTED_TIN_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.POTTED_ALUMINUM_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.POTTED_LEAD_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.POTTED_NICKEL_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.POTTED_URANIUM_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.POTTED_OSMIUM_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.POTTED_ZINC_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.POTTED_SILVER_OREBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(OreBerryRegistry.POTTED_ESSENCE_BERRY_BUSH.get(), RenderType.cutoutMipped());
	}
}
