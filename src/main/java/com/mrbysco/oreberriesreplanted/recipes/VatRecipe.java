package com.mrbysco.oreberriesreplanted.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

public class VatRecipe implements Recipe<Container> {
	protected final String group;
	protected final Ingredient ingredient;
	protected final FluidIngredient fluid;
	protected final Ingredient result;
	protected final int evaporationTime;
	protected final int evaporationAmount;
	protected final float min;
	protected final float max;

	public VatRecipe(String group, Ingredient ingredient, FluidIngredient fluid, Ingredient resultStack, int time, int amount, float min, float max) {
		this.group = group;
		this.ingredient = ingredient;
		this.fluid = fluid;
		this.result = resultStack;
		this.evaporationTime = time;
		this.evaporationAmount = amount;
		this.min = min;
		this.max = max;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public Ingredient getResultIngredient() {
		return result;
	}

	@Override
	public boolean matches(Container inventory, Level pLevel) {
		return this.ingredient.test(inventory.getItem(0));
	}

	@Override
	public ItemStack assemble(Container inventory, HolderLookup.Provider registryAccess) {
		return getResultItem(registryAccess).copy();
	}

	@Override
	public boolean canCraftInDimensions(int x, int y) {
		return false;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> ingredients = NonNullList.create();
		ingredients.add(this.ingredient);
		return ingredients;
	}

	public FluidStack getFluidStack() {
		if (fluid.isEmpty())
			return FluidStack.EMPTY;
		return fluid.getStacks()[0];
	}

	public Fluid getFluid() {
		return getFluidStack().getFluid();
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider registryAccess) {
		return result.getItems()[0];
	}

	@Override
	public String getGroup() {
		return this.group;
	}

	public int getEvaporationTime() {
		return this.evaporationTime;
	}

	public int getEvaporationAmount() {
		return evaporationAmount;
	}

	@Override
	public RecipeType<?> getType() {
		return OreBerryRecipes.VAT_RECIPE_TYPE.get();
	}

	@Override
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
		return OreBerryRecipes.VAT_SERIALIZER.get();
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public static class Serializer implements RecipeSerializer<VatRecipe> {
		public static final MapCodec<VatRecipe> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
								Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
								FluidIngredient.CODEC.fieldOf("fluid").forGetter(recipe -> recipe.fluid),
								Ingredient.CODEC_NONEMPTY.fieldOf("result").forGetter(recipe -> recipe.result),
								Codec.INT.optionalFieldOf("evaporationTime", 100).forGetter(recipe -> recipe.evaporationTime),
								Codec.INT.optionalFieldOf("evaporationAmount", 100).forGetter(recipe -> recipe.evaporationAmount),
								Codec.FLOAT.optionalFieldOf("min", 1.5f).forGetter(recipe -> recipe.min),
								Codec.FLOAT.optionalFieldOf("max", 2.0f).forGetter(recipe -> recipe.max)
						)
						.apply(instance, VatRecipe::new)
		);
		public static final StreamCodec<RegistryFriendlyByteBuf, VatRecipe> STREAM_CODEC = StreamCodec.of(
				VatRecipe.Serializer::toNetwork, VatRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<VatRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, VatRecipe> streamCodec() {
			return STREAM_CODEC;
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
			buffer.writeUtf(recipe.getGroup());
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getIngredient());
			FluidIngredient.STREAM_CODEC.encode(buffer, recipe.fluid);
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getResultIngredient());
			buffer.writeVarInt(recipe.getEvaporationTime());
			buffer.writeVarInt(recipe.getEvaporationAmount());
			buffer.writeFloat(recipe.getMin());
			buffer.writeFloat(recipe.getMax());
		}
	}

}
