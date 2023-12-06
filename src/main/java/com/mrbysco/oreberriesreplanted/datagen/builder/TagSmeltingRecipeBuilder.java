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
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class TagSmeltingRecipeBuilder implements RecipeBuilder {
	private final RecipeCategory category;
	private final CookingBookCategory bookCategory;
	private final Ingredient result;
	private final Ingredient ingredient;
	private final float experience;
	private final int cookingTime;
	private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
	@Nullable
	private String group;
	private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

	private TagSmeltingRecipeBuilder(RecipeCategory category, CookingBookCategory bookCategory, Ingredient result, Ingredient ingredient, float xp, int cookingTime, RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
		this.category = category;
		this.bookCategory = bookCategory;
		this.result = result;
		this.ingredient = ingredient;
		this.experience = xp;
		this.cookingTime = cookingTime;
		this.serializer = serializer;
	}

	public static TagSmeltingRecipeBuilder blasting(Ingredient ingredient, RecipeCategory category, Ingredient result, float xp, int cookingTime) {
		return new TagSmeltingRecipeBuilder(category, CookingBookCategory.MISC, result, ingredient, xp, cookingTime, OreBerryRecipes.TAG_BLASTING_SERIALIZER.get());
	}

	public static TagSmeltingRecipeBuilder smelting(Ingredient ingredient, RecipeCategory category, Ingredient result, float xp, int cookingTime) {
		return new TagSmeltingRecipeBuilder(category, CookingBookCategory.MISC, result, ingredient, xp, cookingTime, OreBerryRecipes.TAG_FURNACE_SERIALIZER.get());
	}

	public TagSmeltingRecipeBuilder unlockedBy(String criteria, Criterion<?> criterion) {
		this.criteria.put(criteria, criterion);
		return this;
	}

	public TagSmeltingRecipeBuilder group(@Nullable String group) {
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
		recipeOutput.accept(new TagSmeltingRecipeBuilder.Result(id, this.group == null ? "" : this.group, this.bookCategory, this.ingredient, this.result, this.experience, this.cookingTime, advancement$builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")), this.serializer));
	}

	private void ensureValid(ResourceLocation id) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + id);
		}
	}

	static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final String group;
		private final CookingBookCategory category;
		private final Ingredient ingredient;
		private final Ingredient result;
		private final float experience;
		private final int cookingTime;
		private final AdvancementHolder advancementHolder;
		private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

		public Result(ResourceLocation id, String group, CookingBookCategory category, Ingredient ingredient, Ingredient result, float xp, int cookingTime, AdvancementHolder advancementHolder, RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
			this.id = id;
			this.group = group;
			this.category = category;
			this.ingredient = ingredient;
			this.result = result;
			this.experience = xp;
			this.cookingTime = cookingTime;
			this.advancementHolder = advancementHolder;
			this.serializer = serializer;
		}

		public void serializeRecipeData(JsonObject jsonObject) {
			if (!this.group.isEmpty()) {
				jsonObject.addProperty("group", this.group);
			}

			jsonObject.addProperty("category", this.category.getSerializedName());
			jsonObject.add("ingredient", this.ingredient.toJson(false));
			jsonObject.add("result", this.result.toJson(false));
			jsonObject.addProperty("experience", this.experience);
			jsonObject.addProperty("cookingtime", this.cookingTime);
		}

		public RecipeSerializer<?> type() {
			return this.serializer;
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