package com.mrbysco.oreberriesreplanted.datagen.builder;

import com.google.gson.JsonObject;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class VatRecipeBuilder implements RecipeBuilder {
	private final Ingredient result;
	protected final ResourceLocation fluid;
	private final Ingredient ingredient;
	protected int evaporationTime = 100;
	protected int evaporationAmount = 100;
	protected float min = 1.5f;
	protected float max = 2.0f;
	private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
	@Nullable
	private String group;

	private VatRecipeBuilder(Ingredient result, ResourceLocation fluid, Ingredient ingredient) {
		this.result = result;
		this.fluid = fluid;
		this.ingredient = ingredient;
	}

	public static VatRecipeBuilder vat(Ingredient ingredient, ResourceLocation fluid, Ingredient result) {
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
		recipeOutput.accept(new VatRecipeBuilder.Result(id, this.group == null ? "" : this.group, this.ingredient, this.fluid, this.evaporationAmount, this.evaporationTime, this.min, this.max, this.result, advancement$builder.build(id.withPrefix("recipes/oreberries/"))));
	}

	private void ensureValid(ResourceLocation id) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + id);
		}
	}

	static record Result(ResourceLocation id, String group, Ingredient ingredient, ResourceLocation fluid,
						 int evaporationTime, int evaporationAmount,
						 float min, float max, Ingredient result,
						 AdvancementHolder advancementHolder) implements FinishedRecipe {

		public void serializeRecipeData(JsonObject jsonObject) {
			if (!this.group.isEmpty()) {
				jsonObject.addProperty("group", this.group);
			}

			jsonObject.add("ingredient", this.ingredient.toJson(false));
			jsonObject.addProperty("fluid", this.fluid.toString());
			jsonObject.add("result", this.result.toJson(false));
			jsonObject.addProperty("evaporationTime", this.evaporationTime);
			if (this.evaporationAmount != 100)
				jsonObject.addProperty("evaporationAmount", this.evaporationAmount);
			if (this.min != 1.5f)
				jsonObject.addProperty("min", this.min);
			if (this.max != 2.0f)
				jsonObject.addProperty("max", this.max);
		}

		public RecipeSerializer<?> type() {
			return OreBerryRecipes.VAT_SERIALIZER.get();
		}

		public ResourceLocation id() {
			return this.id;
		}

		@Nullable
		@Override
		public AdvancementHolder advancement() {
			return this.advancementHolder;
		}
	}
}