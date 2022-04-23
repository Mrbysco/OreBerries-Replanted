package com.mrbysco.oreberriesreplanted.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

/**
 * Taken from the Grinder repository from Noobanidus <3
 */
public class TagFurnaceRecipe extends SmeltingRecipe {
	protected final Ingredient resultIngredient;

	public TagFurnaceRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, Ingredient resultIn, float experienceIn, int cookTimeIn) {
		super(idIn, groupIn, ingredientIn, ItemStack.EMPTY, experienceIn, cookTimeIn);
		this.resultIngredient = resultIn;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public Ingredient getResultIngredient() {
		return resultIngredient;
	}

	public ItemStack assemble(Container container) {
		return this.getResultItem().copy();
	}

	public ItemStack getResultItem() {
		return resultIngredient.getItems()[0];
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return OreBerryRecipes.TAG_FURNACE_SERIALIZER.get();
	}

	public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<TagFurnaceRecipe> {
		@Override
		public TagFurnaceRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			String s = GsonHelper.getAsString(json, "group", "");
			JsonElement jsonelement = (GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient"));
			Ingredient ingredient = Ingredient.fromJson(jsonelement);

			//Forge: Check if primitive string to keep vanilla or a object which can contain a count field.
			if (!json.has("result"))
				throw new JsonSyntaxException("Missing result, expected to find a string or object");
			jsonelement = (GsonHelper.isArrayNode(json, "result") ? GsonHelper.getAsJsonArray(json, "result") : GsonHelper.getAsJsonObject(json, "result"));
			Ingredient result = Ingredient.fromJson(jsonelement);
			if (result.isEmpty()) {
				throw new JsonSyntaxException("Missing result: ingredient has no matching stacks");
			}

			float f = GsonHelper.getAsFloat(json, "experience", 0.0F);
			int i = GsonHelper.getAsInt(json, "cookingtime", 100);
			return new TagFurnaceRecipe(recipeId, s, ingredient, result, f, i);
		}

		@Nullable
		@Override
		public TagFurnaceRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			String s = buffer.readUtf(32767);
			Ingredient ingredient = Ingredient.fromNetwork(buffer);
			Ingredient result = Ingredient.fromNetwork(buffer);
			float f = buffer.readFloat();
			int i = buffer.readVarInt();
			return new TagFurnaceRecipe(recipeId, s, ingredient, result, f, i);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, TagFurnaceRecipe recipe) {
			buffer.writeUtf(recipe.getGroup());
			recipe.getIngredients().forEach(o -> o.toNetwork(buffer));
			recipe.getResultIngredient().toNetwork(buffer);
			buffer.writeItem(recipe.getResultItem());
			buffer.writeFloat(recipe.getExperience());
			buffer.writeVarInt(recipe.getCookingTime());
		}
	}
}

