package com.mrbysco.oreberriesreplanted.compat.jei;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.compat.jei.vat.VatCategory;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipeCache;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.Nullable;
import java.util.ArrayList;

@JeiPlugin
public class JeiCompat implements IModPlugin {
	public static final ResourceLocation RECIPE_VAT_JEI = Reference.modLoc("textures/gui/jei/vat.png");

	public static final ResourceLocation PLUGIN_UID = Reference.modLoc("main");

	public static final IRecipeType<VatRecipe> VAT_TYPE = IRecipeType.create(Reference.MOD_ID, "vat_recipe", VatRecipe.class);

	@Nullable
	private IRecipeCategory<VatRecipe> vatCategory;

	@Override
	public ResourceLocation getPluginUid() {
		return PLUGIN_UID;
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addCraftingStation(VAT_TYPE, new ItemStack(OreBerryRegistry.OAK_VAT.get()));
		registration.addCraftingStation(VAT_TYPE, new ItemStack(OreBerryRegistry.SPRUCE_VAT.get()));
		registration.addCraftingStation(VAT_TYPE, new ItemStack(OreBerryRegistry.BIRCH_VAT.get()));
		registration.addCraftingStation(VAT_TYPE, new ItemStack(OreBerryRegistry.JUNGLE_VAT.get()));
		registration.addCraftingStation(VAT_TYPE, new ItemStack(OreBerryRegistry.ACACIA_VAT.get()));
		registration.addCraftingStation(VAT_TYPE, new ItemStack(OreBerryRegistry.DARK_OAK_VAT.get()));
		registration.addCraftingStation(VAT_TYPE, new ItemStack(OreBerryRegistry.CRIMSON_VAT.get()));
		registration.addCraftingStation(VAT_TYPE, new ItemStack(OreBerryRegistry.WARPED_VAT.get()));
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IJeiHelpers jeiHelpers = registration.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registration.addRecipeCategories(vatCategory = new VatCategory(guiHelper));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		assert vatCategory != null;

		registration.addRecipes(VAT_TYPE, new ArrayList<>(VatRecipeCache.vatRecipes).stream().map(RecipeHolder::value).toList());
	}
}
