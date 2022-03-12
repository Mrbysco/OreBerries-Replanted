package com.mrbysco.oreberriesreplanted.compat.jei;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.compat.jei.vat.VatCategory;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipeTypes;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.plugins.vanilla.crafting.CategoryRecipeValidator;
import mezz.jei.util.ErrorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;
import java.util.List;

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
		registration.addRecipeCatalyst(VanillaTypes.ITEM, new ItemStack(OreBerryRegistry.OAK_VAT.get()), VatCategory.TYPE);
		registration.addRecipeCatalyst(VanillaTypes.ITEM, new ItemStack(OreBerryRegistry.SPRUCE_VAT.get()), VatCategory.TYPE);
		registration.addRecipeCatalyst(VanillaTypes.ITEM, new ItemStack(OreBerryRegistry.BIRCH_VAT.get()), VatCategory.TYPE);
		registration.addRecipeCatalyst(VanillaTypes.ITEM, new ItemStack(OreBerryRegistry.JUNGLE_VAT.get()), VatCategory.TYPE);
		registration.addRecipeCatalyst(VanillaTypes.ITEM, new ItemStack(OreBerryRegistry.ACACIA_VAT.get()), VatCategory.TYPE);
		registration.addRecipeCatalyst(VanillaTypes.ITEM, new ItemStack(OreBerryRegistry.DARK_OAK_VAT.get()), VatCategory.TYPE);
		registration.addRecipeCatalyst(VanillaTypes.ITEM, new ItemStack(OreBerryRegistry.CRIMSON_VAT.get()), VatCategory.TYPE);
		registration.addRecipeCatalyst(VanillaTypes.ITEM, new ItemStack(OreBerryRegistry.WARPED_VAT.get()), VatCategory.TYPE);
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

		registration.addRecipes(VatCategory.TYPE, getVatRecipes(vatCategory));
	}

	public List<VatRecipe> getVatRecipes(IRecipeCategory<VatRecipe> vatCategory) {
		CategoryRecipeValidator<VatRecipe> validator = new CategoryRecipeValidator<>(vatCategory, 1);
		return getValidHandledRecipes(Minecraft.getInstance().level.getRecipeManager(), OreBerryRecipeTypes.VAT_RECIPE_TYPE, validator);
	}

	private static <C extends Container, T extends Recipe<C>> List<T> getValidHandledRecipes(
			RecipeManager recipeManager,
			RecipeType<T> recipeType,
			CategoryRecipeValidator<T> validator
	) {
		return recipeManager.getAllRecipesFor(recipeType)
				.stream()
				.filter(r -> validator.isRecipeValid(r) && validator.isRecipeHandled(r))
				.toList();
	}
}
