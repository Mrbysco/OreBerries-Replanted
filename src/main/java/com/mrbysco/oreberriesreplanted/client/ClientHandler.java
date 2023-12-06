package com.mrbysco.oreberriesreplanted.client;

import com.mrbysco.oreberriesreplanted.client.ber.VatBER;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ClientHandler {

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(OreBerryRegistry.VAT_BLOCK_ENTITY.get(), VatBER::new);
	}

	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
		for (DeferredHolder<Item, ? extends Item> itemObject : OreBerryRegistry.ITEMS.getEntries()) {
			if (itemObject.get() instanceof BucketItem) {
				event.register((stack, tintIndex) -> {
					if (tintIndex != 1) return 0xFFFFFFFF;
					return FluidUtil.getFluidContained(stack)
							.map(fluidStack -> IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack))
							.orElse(0xFFFFFFFF);
				}, itemObject.get());
			}
		}
	}
}
