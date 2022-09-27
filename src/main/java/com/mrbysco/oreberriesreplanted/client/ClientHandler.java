package com.mrbysco.oreberriesreplanted.client;

import com.mrbysco.oreberriesreplanted.client.ber.VatBER;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.RegistryObject;

public class ClientHandler {

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(OreBerryRegistry.VAT_BLOCK_ENTITY.get(), VatBER::new);
	}

	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
		for (RegistryObject<Item> itemObject : OreBerryRegistry.ITEMS.getEntries()) {
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
