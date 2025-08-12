package com.mrbysco.oreberriesreplanted.client;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.client.ber.VatBER;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipeCache;
import com.mrbysco.oreberriesreplanted.registry.LiquidReg;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.joml.Vector4f;

import java.util.Collection;
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
				public Vector4f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
					int color = this.getTintColor();
					return new Vector4f(ARGB.red(color), ARGB.green(color), ARGB.blue(color), ARGB.alpha(color));
				}
			}, reg.getFluidType());
		});
	}

	public static void onRecipeReceived(final RecipesReceivedEvent event) {
		Collection<RecipeHolder<VatRecipe>> vatRecipes = event.getRecipeMap().byType(OreBerryRecipes.VAT_RECIPE_TYPE.get());
		VatRecipeCache.vatRecipes.clear();
		VatRecipeCache.vatRecipes.addAll(vatRecipes);
	}
}
