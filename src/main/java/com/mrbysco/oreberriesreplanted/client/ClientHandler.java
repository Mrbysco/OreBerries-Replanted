package com.mrbysco.oreberriesreplanted.client;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.client.ber.VatBER;
import com.mrbysco.oreberriesreplanted.fluid.BerryFluidType;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipeCache;
import com.mrbysco.oreberriesreplanted.registry.LiquidReg;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.joml.Vector4f;

import java.util.Collection;
import java.util.List;

@EventBusSubscriber(Dist.CLIENT)
public class ClientHandler {
	private final static Identifier STILL_BERRY = Reference.modLoc("block/liquid_berry_still");
	private final static Identifier FLOWING_BERRY = Reference.modLoc("block/liquid_berry_flow");

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(OreBerryRegistry.VAT_BLOCK_ENTITY.get(), VatBER::new);
	}

	@SubscribeEvent
	public static void registerExtensions(RegisterClientExtensionsEvent event) {
		List<LiquidReg> liquidRegs = List.of(OreBerryRegistry.IRON_OREBERRY_JUICE, OreBerryRegistry.GOLD_OREBERRY_JUICE,
				OreBerryRegistry.COPPER_OREBERRY_JUICE, OreBerryRegistry.TIN_OREBERRY_JUICE,
				OreBerryRegistry.ALUMINUM_OREBERRY_JUICE, OreBerryRegistry.LEAD_OREBERRY_JUICE,
				OreBerryRegistry.NICKEL_OREBERRY_JUICE, OreBerryRegistry.URANIUM_OREBERRY_JUICE,
				OreBerryRegistry.OSMIUM_OREBERRY_JUICE, OreBerryRegistry.ZINC_OREBERRY_JUICE,
				OreBerryRegistry.SILVER_OREBERRY_JUICE);
		liquidRegs.forEach(reg -> {
			if (reg.getFluidType().get() instanceof BerryFluidType berryFluidType) {
				event.registerFluidType(new IClientFluidTypeExtensions() {
					@Override
					public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
						int color = berryFluidType.getColor();
						fluidFogColor.set(new Vector4f(ARGB.red(color), ARGB.green(color), ARGB.blue(color), ARGB.alpha(color)));
					}
				}, berryFluidType);
			}
		});
	}

	@SubscribeEvent
	public static void registerFluidModels(final RegisterFluidModelsEvent event) {
		List<LiquidReg> liquidRegs = List.of(OreBerryRegistry.IRON_OREBERRY_JUICE, OreBerryRegistry.GOLD_OREBERRY_JUICE,
				OreBerryRegistry.COPPER_OREBERRY_JUICE, OreBerryRegistry.TIN_OREBERRY_JUICE,
				OreBerryRegistry.ALUMINUM_OREBERRY_JUICE, OreBerryRegistry.LEAD_OREBERRY_JUICE,
				OreBerryRegistry.NICKEL_OREBERRY_JUICE, OreBerryRegistry.URANIUM_OREBERRY_JUICE,
				OreBerryRegistry.OSMIUM_OREBERRY_JUICE, OreBerryRegistry.ZINC_OREBERRY_JUICE,
				OreBerryRegistry.SILVER_OREBERRY_JUICE);
		liquidRegs.forEach(reg -> {
			event.register(new FluidModel.Unbaked(
					new Material(STILL_BERRY),
					new Material(FLOWING_BERRY),
					null,
					berryLiquid(reg.getColor())), reg.getSource(), reg.getFlowing());
		});
	}

	private static BlockTintSource berryLiquid(int color) {

		return new BlockTintSource() {
			@Override
			public int color(BlockState state) {
				return color;
			}

			@Override
			public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
				return color;
			}
		};
	}

	@SubscribeEvent
	public static void onRecipeReceived(final RecipesReceivedEvent event) {
		Collection<RecipeHolder<VatRecipe>> vatRecipes = event.getRecipeMap().byType(OreBerryRecipes.VAT_RECIPE_TYPE.get());
		VatRecipeCache.vatRecipes.clear();
		VatRecipeCache.vatRecipes.addAll(vatRecipes);
	}
}
