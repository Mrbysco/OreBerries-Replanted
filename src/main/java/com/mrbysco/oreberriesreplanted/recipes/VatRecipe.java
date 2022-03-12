package com.mrbysco.oreberriesreplanted.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipeTypes;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class VatRecipe implements Recipe<Container> {
	protected final ResourceLocation id;
	protected final String group;
	protected final Ingredient ingredient;
	protected final Fluid fluid;
	protected final ItemStack result;
	protected final int evaporationTime;
	protected final int evaporationAmount;
	protected final float min;
	protected final float max;

	public VatRecipe(ResourceLocation location, String group, Ingredient ingredient, Fluid fluid, ItemStack resultStack, int time, int amount, float min, float max) {
		this.id = location;
		this.group = group;
		this.ingredient = ingredient;
		this.fluid = fluid;
		this.result = resultStack;
		this.evaporationTime = time;
		this.evaporationAmount = amount;
		this.min = min;
		this.max = max;
	}

	public boolean matches(Container inventory, Level world) {
		return this.ingredient.test(inventory.getItem(0));
	}

	public ItemStack assemble(Container inventory) {
		return this.result.copy();
	}

	public boolean canCraftInDimensions(int x, int y) {
		return false;
	}

	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> ingredients = NonNullList.create();
		ingredients.add(this.ingredient);
		return ingredients;
	}

	public Fluid getFluid() {
		return fluid;
	}

	public ItemStack getResultItem() {
		return this.result;
	}

	public String getGroup() {
		return this.group;
	}

	public int getEvaporationTime() {
		return this.evaporationTime;
	}

	public int getEvaporationAmount() {
		return evaporationAmount;
	}

	public ResourceLocation getId() {
		return this.id;
	}

	public RecipeType<?> getType() {
		return OreBerryRecipeTypes.VAT_RECIPE_TYPE;
	}

	public ItemStack getToastSymbol() {
		return new ItemStack(OreBerryRegistry.OAK_VAT.get());
	}

	public float getMin() {
		return min;
	}

	public float getMax() {
		return max;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return OreBerryRegistry.VAT_SERIALIZER.get();
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<VatRecipe> {

		@Override
		public VatRecipe fromJson(ResourceLocation location, JsonObject jsonObject) {
			String group = GsonHelper.getAsString(jsonObject, "group", "");
			JsonElement jsonelement = (GsonHelper.isArrayNode(jsonObject, "ingredient") ?
					GsonHelper.getAsJsonArray(jsonObject, "ingredient") : GsonHelper.getAsJsonObject(jsonObject, "ingredient"));
			Ingredient ingredient = Ingredient.fromJson(jsonelement);
			String s1 = GsonHelper.getAsString(jsonObject, "fluid");
			ResourceLocation resourcelocation = new ResourceLocation(s1);
			Fluid fluid = Registry.FLUID.getOptional(resourcelocation).orElseThrow(() -> new IllegalStateException("Fluid: " + s1 + " does not exist"));
			int evaporationtime = GsonHelper.getAsInt(jsonObject, "evaporationtime", 100);
			int evaporationamount = GsonHelper.getAsInt(jsonObject, "evaporationamount", 100);
			float min = GsonHelper.getAsFloat(jsonObject, "min", 1.5f);
			float max = GsonHelper.getAsFloat(jsonObject, "max", 2.0f);

			//Forge: Check if primitive string to keep vanilla or an object which can contain a count field.
			if (!jsonObject.has("result"))
				throw new JsonSyntaxException("Missing result, expected to find a string or object");
			jsonelement = (GsonHelper.isArrayNode(jsonObject, "result") ? GsonHelper.getAsJsonArray(jsonObject, "result") : GsonHelper.getAsJsonObject(jsonObject, "result"));
			Ingredient result = Ingredient.fromJson(jsonelement);
			if (result.isEmpty()) {
				throw new JsonSyntaxException("Missing result: ingredient has no matching stacks");
			}
			ItemStack itemstack = result.getItems()[0];

			return new VatRecipe(location, group, ingredient, fluid, itemstack, evaporationtime, evaporationamount, min, max);
		}

		@Nullable
		@Override
		public VatRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buffer) {
			String group = buffer.readUtf(32767);
			Ingredient ingredient = Ingredient.fromNetwork(buffer);
			ResourceLocation fluidLocation = buffer.readResourceLocation();
			Fluid fluid = Registry.FLUID.getOptional(fluidLocation).orElseThrow(() -> new IllegalStateException("Fluid: " + fluidLocation + " does not exist"));
			ItemStack result = buffer.readItem();
			int evaporationTime = buffer.readVarInt();
			int evaporationamount = buffer.readVarInt();
			float min = buffer.readFloat();
			float max = buffer.readFloat();
			return new VatRecipe(location, group, ingredient, fluid, result, evaporationTime, evaporationamount, min, max);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, VatRecipe recipe) {
			buffer.writeUtf(recipe.getGroup());
			recipe.getIngredients().forEach(o -> o.toNetwork(buffer));
			buffer.writeResourceLocation(recipe.fluid.getRegistryName());
			buffer.writeItem(recipe.getResultItem());
			buffer.writeVarInt(recipe.getEvaporationTime());
			buffer.writeVarInt(recipe.getEvaporationAmount());
			buffer.writeFloat(recipe.getMin());
			buffer.writeFloat(recipe.getMax());
		}
	}

}
