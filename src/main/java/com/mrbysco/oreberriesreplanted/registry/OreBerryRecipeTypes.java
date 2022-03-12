package com.mrbysco.oreberriesreplanted.registry;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

public class OreBerryRecipeTypes {
	public static final RecipeType<VatRecipe> VAT_RECIPE_TYPE = RecipeType.register(new ResourceLocation(Reference.MOD_ID, "vat_recipe").toString());

	public static void init() {
		//Just here to load the class
	}
}
