package com.mrbysco.oreberriesreplanted.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

public class VatRecipe implements Recipe<RecipeInput> {
	public static final MapCodec<VatRecipe> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
							Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
							Ingredient.CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
							FluidIngredient.CODEC.fieldOf("fluid").forGetter(recipe -> recipe.fluid),
							Ingredient.CODEC.fieldOf("result").forGetter(recipe -> recipe.resultIngredient),
							Codec.INT.optionalFieldOf("evaporationTime", 100).forGetter(recipe -> recipe.evaporationTime),
							Codec.INT.optionalFieldOf("evaporationAmount", 100).forGetter(recipe -> recipe.evaporationAmount),
							Codec.FLOAT.optionalFieldOf("min", 1.5f).forGetter(recipe -> recipe.min),
							Codec.FLOAT.optionalFieldOf("max", 2.0f).forGetter(recipe -> recipe.max)
					)
					.apply(instance, VatRecipe::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, VatRecipe> STREAM_CODEC = StreamCodec.of(
			VatRecipe::toNetwork, VatRecipe::fromNetwork
	);
	public static final RecipeSerializer<VatRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

	protected final String group;
	protected final Ingredient ingredient;
	protected final FluidIngredient fluid;
	protected final Ingredient resultIngredient;
	protected final int evaporationTime;
	protected final int evaporationAmount;
	protected final float min;
	protected final float max;

	public VatRecipe(String group, Ingredient ingredient, FluidIngredient fluid, Ingredient resultStack, int time, int amount, float min, float max) {
		this.group = group;
		this.ingredient = ingredient;
		this.fluid = fluid;
		this.resultIngredient = resultStack;
		this.evaporationTime = time;
		this.evaporationAmount = amount;
		this.min = min;
		this.max = max;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public Ingredient getResultIngredient() {
		return resultIngredient;
	}

	@Override
	public boolean matches(RecipeInput input, Level level) {
		return this.ingredient.test(input.getItem(0));
	}

	public FluidStack getFluidStack() {
		if (fluid.test(FluidStack.EMPTY))
			return FluidStack.EMPTY;
		return new FluidStack(fluid.fluids().getFirst(), 1000);
	}

	public Fluid getFluid() {
		return getFluidStack().getFluid();
	}

	@Override
	public ItemStack assemble(RecipeInput recipeInput) {
		return this.getResultItem().copy();
	}

	public ItemStack getResultItem() {
		return new ItemStack(resultIngredient.getValues().get(0));
	}

	@Override
	public String group() {
		return this.group;
	}

	public int getEvaporationTime() {
		return this.evaporationTime;
	}

	public int getEvaporationAmount() {
		return evaporationAmount;
	}

	@Override
	public RecipeType<VatRecipe> getType() {
		return OreBerryRecipes.VAT_RECIPE_TYPE.get();
	}

	@Override
	public PlacementInfo placementInfo() {
		return PlacementInfo.NOT_PLACEABLE;
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return RecipeBookCategories.CRAFTING_MISC;
	}

	public float getMin() {
		return min;
	}

	public float getMax() {
		return max;
	}

	@Override
	public RecipeSerializer<VatRecipe> getSerializer() {
		return OreBerryRecipes.VAT_SERIALIZER.get();
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public boolean showNotification() {
		return false;
	}

	public static VatRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
		String group = buffer.readUtf(32767);
		Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
		FluidIngredient fluid = FluidIngredient.STREAM_CODEC.decode(buffer);
		Ingredient result = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
		int evaporationTime = buffer.readVarInt();
		int evaporationAmount = buffer.readVarInt();
		float min = buffer.readFloat();
		float max = buffer.readFloat();
		return new VatRecipe(group, ingredient, fluid, result, evaporationTime, evaporationAmount, min, max);
	}

	public static void toNetwork(RegistryFriendlyByteBuf buffer, VatRecipe recipe) {
		buffer.writeUtf(recipe.group());
		Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getIngredient());
		FluidIngredient.STREAM_CODEC.encode(buffer, recipe.fluid);
		Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getResultIngredient());
		buffer.writeVarInt(recipe.getEvaporationTime());
		buffer.writeVarInt(recipe.getEvaporationAmount());
		buffer.writeFloat(recipe.getMin());
		buffer.writeFloat(recipe.getMax());
	}

}
