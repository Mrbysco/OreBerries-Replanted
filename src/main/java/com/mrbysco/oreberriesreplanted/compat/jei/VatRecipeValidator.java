package com.mrbysco.oreberriesreplanted.compat.jei;

import com.mrbysco.oreberriesreplanted.OreberriesReplanted;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class VatRecipeValidator {
	private static final int INVALID_COUNT = -1;

	private final IRecipeCategory<VatRecipe> recipeCategory;

	public VatRecipeValidator(IRecipeCategory<VatRecipe> recipeCategory) {
		this.recipeCategory = recipeCategory;
	}

	public boolean isRecipeValid(RecipeHolder<VatRecipe> recipe) {
		return hasValidInputsAndOutputs(recipe);
	}

	public boolean isRecipeHandled(RecipeHolder<VatRecipe> recipe) {
		return this.recipeCategory.isHandled(recipe.value());
	}

	@SuppressWarnings("ConstantConditions")
	private boolean hasValidInputsAndOutputs(RecipeHolder<VatRecipe> holder) {
		if (holder.value().isSpecial()) {
			return true;
		}
		ItemStack recipeOutput = holder.value().getResultItem(null);
		if (recipeOutput == null || recipeOutput.isEmpty()) {
			OreberriesReplanted.LOGGER.error("Recipe has no output. {}", holder.id());
			return false;
		}
		List<Ingredient> ingredients = holder.value().getIngredients();
		if (ingredients == null) {
			OreberriesReplanted.LOGGER.error("Recipe has no input Ingredients. {}", holder.id());
			return false;
		}
		int inputCount = getInputCount(ingredients);
		if (inputCount == INVALID_COUNT) {
			return false;
		} else if (inputCount > 1) {
			OreberriesReplanted.LOGGER.error("Recipe has too many inputs. {}", holder.id());
			return false;
		} else if (inputCount == 0) {
			OreberriesReplanted.LOGGER.error("Recipe has no inputs. {}", holder.id());
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