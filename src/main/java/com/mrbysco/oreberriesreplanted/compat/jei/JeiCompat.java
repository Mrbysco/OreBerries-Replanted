package com.mrbysco.oreberriesreplanted.compat.jei;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.compat.jei.vat.VatCategory;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.util.ErrorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Objects;

@JeiPlugin
public class JeiCompat implements IModPlugin {
	public static final ResourceLocation RECIPE_VAT_JEI = new ResourceLocation(Reference.MOD_ID, "textures/gui/jei/vat.png");

	public static final ResourceLocation PLUGIN_UID = new ResourceLocation(Reference.MOD_ID, "main");
	public static final ResourceLocation VAT = new ResourceLocation(Reference.MOD_ID, "vat");

	@Nullable
	private IRecipeCategory<VatRecipe> vatCategory;

	@Override
	public ResourceLocation getPluginUid() {
		return PLUGIN_UID;
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.OAK_VAT.get()), VAT);
		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.SPRUCE_VAT.get()), VAT);
		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.BIRCH_VAT.get()), VAT);
		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.JUNGLE_VAT.get()), VAT);
		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.ACACIA_VAT.get()), VAT);
		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.DARK_OAK_VAT.get()), VAT);
		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.CRIMSON_VAT.get()), VAT);
		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.WARPED_VAT.get()), VAT);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IJeiHelpers jeiHelpers = registration.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registration.addRecipeCategories(
				vatCategory = new VatCategory(guiHelper)
		);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		ErrorUtil.checkNotNull(vatCategory, "freezingCategory");

		ClientWorld world = Objects.requireNonNull(Minecraft.getInstance().level);
		registration.addRecipes(world.getRecipeManager().getAllRecipesFor(OreBerryRegistry.VAT_RECIPE_TYPE), VAT);
	}
}
