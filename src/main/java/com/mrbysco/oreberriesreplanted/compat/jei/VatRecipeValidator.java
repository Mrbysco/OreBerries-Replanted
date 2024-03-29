package com.mrbysco.oreberriesreplanted.compat.jei;

import com.mrbysco.oreberriesreplanted.OreberriesReplanted;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class VatRecipeValidator {
	private static final int INVALID_COUNT = -1;

	private final IRecipeCategory<VatRecipe> recipeCategory;

	public VatRecipeValidator(IRecipeCategory<VatRecipe> recipeCategory) {
		this.recipeCategory = recipeCategory;
	}

	public boolean isRecipeValid(VatRecipe recipe) {
		return hasValidInputsAndOutputs(recipe);
	}

	public boolean isRecipeHandled(VatRecipe recipe) {
		return this.recipeCategory.isHandled(recipe);
	}

	@SuppressWarnings("ConstantConditions")
	private boolean hasValidInputsAndOutputs(VatRecipe recipe) {
		if (recipe.isSpecial()) {
			return true;
		}
		ItemStack recipeOutput = recipe.getResultItem(null);
		if (recipeOutput == null || recipeOutput.isEmpty()) {
			OreberriesReplanted.LOGGER.error("Recipe has no output. {}", recipe.getId());
			return false;
		}
		List<Ingredient> ingredients = recipe.getIngredients();
		if (ingredients == null) {
			OreberriesReplanted.LOGGER.error("Recipe has no input Ingredients. {}", recipe.getId());
			return false;
		}
		int inputCount = getInputCount(ingredients);
		if (inputCount == INVALID_COUNT) {
			return false;
		} else if (inputCount > 1) {
			OreberriesReplanted.LOGGER.error("Recipe has too many inputs. {}", recipe.getId());
			return false;
		} else if (inputCount == 0) {
			OreberriesReplanted.LOGGER.error("Recipe has no inputs. {}", recipe.getId());
			return false;
		}
		return true;
	}

	@SuppressWarnings("ConstantConditions")
	private static int getInputCount(List<Ingredient> ingredientList) {
		int inputCount = 0;
		for (Ingredient ingredient : ingredientList) {
			ItemStack[] input = ingredient.getItems();
			if (input == null) {
				return INVALID_COUNT;
			} else {
				inputCount++;
			}
		}
		return inputCount;
	}
}