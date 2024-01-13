package com.mrbysco.oreberriesreplanted.compat.rei.display;

import com.mrbysco.oreberriesreplanted.compat.rei.REIPlugin;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import dev.architectury.fluid.FluidStack;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

public class VatDisplay implements Display {
	public final int evaporationTime, evaporationAmount;
	public final float min, max;
	public final String displayName;

	private final List<EntryIngredient> inputEntries;
	private final List<EntryIngredient> outputEntries;

	public VatDisplay(RecipeHolder<VatRecipe> recipeHolder) {
		VatRecipe recipe = recipeHolder.value();
		this.evaporationTime = recipe.getEvaporationTime();
		this.evaporationAmount = recipe.getEvaporationAmount();
		this.min = recipe.getMin();
		this.max = recipe.getMax();

		this.inputEntries = List.of(EntryIngredients.ofIngredient(recipe.getIngredient()));

		this.displayName = recipe.getFluid().getFluidType().getDescription().getString();

		this.outputEntries = new ArrayList<>();
		FluidStack fluidStack = FluidStack.create(recipe.getFluid(), 1000);
		this.outputEntries.add(EntryIngredients.of(VanillaEntryTypes.FLUID, List.of(fluidStack)));
		this.outputEntries.add(EntryIngredients.ofIngredient(recipe.getResultIngredient()));
	}

	@Override
	public List<EntryIngredient> getInputEntries() {
		return this.inputEntries;
	}

	@Override
	public List<EntryIngredient> getOutputEntries() {
		return this.outputEntries;
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return REIPlugin.VAT;
	}
}
