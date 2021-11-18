package com.mrbysco.oreberriesreplanted.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class VatRecipe implements IRecipe<IInventory> {
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

	public boolean matches(IInventory inventory, World world) {
		return this.ingredient.test(inventory.getItem(0));
	}

	public ItemStack assemble(IInventory inventory) {
		return this.result.copy();
	}

	public boolean canCraftInDimensions(int x, int y) {
		return false;
	}

	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		nonnulllist.add(this.ingredient);
		return nonnulllist;
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

	public IRecipeType<?> getType() {
		return OreBerryRegistry.VAT_RECIPE_TYPE;
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
	public IRecipeSerializer<?> getSerializer() {
		return OreBerryRegistry.VAT_SERIALIZER.get();
	}


	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<VatRecipe> {

		@Override
		public VatRecipe fromJson(ResourceLocation location, JsonObject jsonObject) {
			String group = JSONUtils.getAsString(jsonObject, "group", "");
			JsonElement jsonelement = (JSONUtils.isArrayNode(jsonObject, "ingredient") ?
					JSONUtils.getAsJsonArray(jsonObject, "ingredient") : JSONUtils.getAsJsonObject(jsonObject, "ingredient"));
			Ingredient ingredient = Ingredient.fromJson(jsonelement);
			String s1 = JSONUtils.getAsString(jsonObject, "fluid");
			ResourceLocation resourcelocation = new ResourceLocation(s1);
			Fluid fluid = Registry.FLUID.getOptional(resourcelocation).orElseThrow(() -> new IllegalStateException("Fluid: " + s1 + " does not exist"));
			int evaporationtime = JSONUtils.getAsInt(jsonObject, "evaporationtime", 100);
			int evaporationamount = JSONUtils.getAsInt(jsonObject, "evaporationamount", 100);
			float min = JSONUtils.getAsFloat(jsonObject, "min", 1.5f);
			float max = JSONUtils.getAsFloat(jsonObject, "max", 2.0f);

			//Forge: Check if primitive string to keep vanilla or an object which can contain a count field.
			if (!jsonObject.has("result"))
				throw new JsonSyntaxException("Missing result, expected to find a string or object");
			jsonelement = (JSONUtils.isArrayNode(jsonObject, "result") ? JSONUtils.getAsJsonArray(jsonObject, "result") : JSONUtils.getAsJsonObject(jsonObject, "result"));
			Ingredient result = Ingredient.fromJson(jsonelement);
			if (result.isEmpty()) {
				throw new JsonSyntaxException("Missing result: ingredient has no matching stacks");
			}
			ItemStack itemstack = result.getItems()[0];

			return new VatRecipe(location, group, ingredient, fluid, itemstack, evaporationtime, evaporationamount, min, max);
		}

		@Nullable
		@Override
		public VatRecipe fromNetwork(ResourceLocation location, PacketBuffer buffer) {
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
		public void toNetwork(PacketBuffer buffer, VatRecipe recipe) {
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
