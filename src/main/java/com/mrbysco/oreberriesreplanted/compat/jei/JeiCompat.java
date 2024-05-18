//package com.mrbysco.oreberriesreplanted.compat.jei;
//
//import com.mrbysco.oreberriesreplanted.Reference;
//import com.mrbysco.oreberriesreplanted.compat.jei.vat.VatCategory;
//import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
//import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
//import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
//import mezz.jei.api.IModPlugin;
//import mezz.jei.api.JeiPlugin;
//import mezz.jei.api.helpers.IGuiHelper;
//import mezz.jei.api.helpers.IJeiHelpers;
//import mezz.jei.api.recipe.RecipeType;
//import mezz.jei.api.recipe.category.IRecipeCategory;
//import mezz.jei.api.registration.IRecipeCatalystRegistration;
//import mezz.jei.api.registration.IRecipeCategoryRegistration;
//import mezz.jei.api.registration.IRecipeRegistration;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.Container;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.RecipeHolder;
//import net.minecraft.world.item.crafting.RecipeManager;
//
//import javax.annotation.Nullable;
//import java.util.List;
//
//@JeiPlugin
//public class JeiCompat implements IModPlugin {
//	public static final ResourceLocation RECIPE_VAT_JEI = new ResourceLocation(Reference.MOD_ID, "textures/gui/jei/vat.png");
//
//	public static final ResourceLocation PLUGIN_UID = new ResourceLocation(Reference.MOD_ID, "main");
//
//	public static final RecipeType<VatRecipe> VAT_TYPE = RecipeType.create(Reference.MOD_ID, "vat_recipe", VatRecipe.class);
//
//	@Nullable
//	private IRecipeCategory<VatRecipe> vatCategory;
//
//	@Override
//	public ResourceLocation getPluginUid() {
//		return PLUGIN_UID;
//	}
//
//	@Override
//	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
//		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.OAK_VAT.get()), VAT_TYPE);
//		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.SPRUCE_VAT.get()), VAT_TYPE);
//		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.BIRCH_VAT.get()), VAT_TYPE);
//		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.JUNGLE_VAT.get()), VAT_TYPE);
//		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.ACACIA_VAT.get()), VAT_TYPE);
//		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.DARK_OAK_VAT.get()), VAT_TYPE);
//		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.CRIMSON_VAT.get()), VAT_TYPE);
//		registration.addRecipeCatalyst(new ItemStack(OreBerryRegistry.WARPED_VAT.get()), VAT_TYPE);
//	}
//
//	@Override
//	public void registerCategories(IRecipeCategoryRegistration registration) {
//		IJeiHelpers jeiHelpers = registration.getJeiHelpers();
//		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
//		registration.addRecipeCategories(vatCategory = new VatCategory(guiHelper));
//	}
//
//	@Override
//	public void registerRecipes(IRecipeRegistration registration) {
//		assert vatCategory != null;
//
//		registration.addRecipes(VAT_TYPE, getVatRecipes(vatCategory));
//	}
//
//	public List<VatRecipe> getVatRecipes(IRecipeCategory<VatRecipe> vatCategory) {
//		Minecraft minecraft = Minecraft.getInstance();
//		ClientLevel level = minecraft.level;
//		if (level == null) {
//			throw new NullPointerException("level must not be null.");
//		}
//		return getValidHandledRecipes(level.getRecipeManager(), new VatRecipeValidator(vatCategory));
//	}
//
//	private static <C extends Container> List<VatRecipe> getValidHandledRecipes(RecipeManager recipeManager, VatRecipeValidator validator) {
//		return recipeManager.getAllRecipesFor(OreBerryRecipes.VAT_RECIPE_TYPE.get()).stream()
//				.filter(r -> validator.isRecipeValid(r) && validator.isRecipeHandled(r)).map(RecipeHolder::value).toList();
//	}
//}
