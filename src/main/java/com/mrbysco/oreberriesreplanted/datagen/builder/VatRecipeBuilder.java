package com.mrbysco.oreberriesreplanted.datagen.builder;

import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class VatRecipeBuilder implements RecipeBuilder {
	private final Ingredient result;
	protected final FluidIngredient fluid;
	private final Ingredient ingredient;
	protected int evaporationTime = 100;
	protected int evaporationAmount = 100;
	protected float min = 1.5f;
	protected float max = 2.0f;
	private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
	@Nullable
	private String group;

	private VatRecipeBuilder(Ingredient result, FluidIngredient fluid, Ingredient ingredient) {
		this.result = result;
		this.fluid = fluid;
		this.ingredient = ingredient;
	}

	public static VatRecipeBuilder vat(Ingredient ingredient, FluidIngredient fluid, Ingredient result) {
		return new VatRecipeBuilder(ingredient, fluid, result);
	}

	public VatRecipeBuilder evaporationTime(int time) {
		this.evaporationTime = time;
		return this;
	}

	public VatRecipeBuilder evaporationAmount(int amount) {
		this.evaporationAmount = amount;
		return this;
	}

	public VatRecipeBuilder min(float min) {
		this.min = min;
		return this;
	}

	public VatRecipeBuilder max(float max) {
		this.max = max;
		return this;
	}

	public VatRecipeBuilder unlockedBy(String criteria, Criterion<?> criterion) {
		this.criteria.put(criteria, criterion);
		return this;
	}

	public VatRecipeBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	public Item getResult() {
		return this.result.getItems()[0].getItem();
	}

	public void save(RecipeOutput recipeOutput, ResourceLocation id) {
		this.ensureValid(id);
		Advancement.Builder advancement$builder = recipeOutput.advancement()
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
				.rewards(AdvancementRewards.Builder.recipe(id))
				.requirements(AdvancementRequirements.Strategy.OR);
		this.criteria.forEach(advancement$builder::addCriterion);
		if (fluid == null) {
			throw new IllegalStateException("Fluid: " + this.fluid + " does not exist");
		} else {
			VatRecipe vatRecipe = new VatRecipe(this.group == null ? "" : this.group, this.ingredient, fluid, this.result, this.evaporationAmount, this.evaporationTime, this.min, this.max);
			recipeOutput.accept(id, vatRecipe, advancement$builder.build(id.withPrefix("recipes/oreberries/")));
		}
	}

	private void ensureValid(ResourceLocation id) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + id);
		}
	}
}