package com.mrbysco.oreberriesreplanted.client;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.client.ber.VatBER;
import com.mrbysco.oreberriesreplanted.registry.LiquidReg;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;

public class ClientHandler {
	private final static ResourceLocation STILL_BERRY = Reference.modLoc("block/liquid_berry_still");
	private final static ResourceLocation FLOWING_BERRY = Reference.modLoc("block/liquid_berry_flow");

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(OreBerryRegistry.VAT_BLOCK_ENTITY.get(), VatBER::new);
	}

	public static void registerExtensions(RegisterClientExtensionsEvent event) {
		List<LiquidReg> liquidRegs = List.of(OreBerryRegistry.IRON_OREBERRY_JUICE, OreBerryRegistry.GOLD_OREBERRY_JUICE,
				OreBerryRegistry.COPPER_OREBERRY_JUICE, OreBerryRegistry.TIN_OREBERRY_JUICE,
				OreBerryRegistry.ALUMINUM_OREBERRY_JUICE, OreBerryRegistry.LEAD_OREBERRY_JUICE,
				OreBerryRegistry.NICKEL_OREBERRY_JUICE, OreBerryRegistry.URANIUM_OREBERRY_JUICE,
				OreBerryRegistry.OSMIUM_OREBERRY_JUICE, OreBerryRegistry.ZINC_OREBERRY_JUICE,
				OreBerryRegistry.SILVER_OREBERRY_JUICE);
		liquidRegs.forEach(reg -> {
			event.registerFluidType(new IClientFluidTypeExtensions() {

				@Override
				public ResourceLocation getStillTexture() {
					return STILL_BERRY;
				}

				@Override
				public ResourceLocation getFlowingTexture() {
					return FLOWING_BERRY;
				}

				@Override
				public int getTintColor() {
					return reg.getColor();
				}

				@Override
				public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
					int color = this.getTintColor();
					return new Vector3f((color >> 16 & 0xFF) / 255F, (color >> 8 & 0xFF) / 255F, (color & 0xFF) / 255F);
				}
			}, reg.getFluidType());
		});
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
