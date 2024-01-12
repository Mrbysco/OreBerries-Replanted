package com.mrbysco.oreberriesreplanted.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;

public class VatRecipe implements Recipe<Container> {
	protected final String group;
	protected final Ingredient ingredient;
	protected final Fluid fluid;
	protected final Ingredient result;
	protected final int evaporationTime;
	protected final int evaporationAmount;
	protected final float min;
	protected final float max;

	public VatRecipe(String group, Ingredient ingredient, Fluid fluid, Ingredient resultStack, int time, int amount, float min, float max) {
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

	public boolean matches(Container inventory, Level pLevel) {
		return this.ingredient.test(inventory.getItem(0));
	}

	public ItemStack assemble(Container inventory, RegistryAccess registryAccess) {
		return getResultItem(registryAccess).copy();
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

	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return result.getItems()[0];
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

	public RecipeType<?> getType() {
		return OreBerryRecipes.VAT_RECIPE_TYPE.get();
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
		return OreBerryRecipes.VAT_SERIALIZER.get();
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public static class Serializer implements RecipeSerializer<VatRecipe> {
		public static final Codec<VatRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
								Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
								BuiltInRegistries.FLUID.byNameCodec().fieldOf("fluid").forGetter(recipe -> recipe.fluid),
								Ingredient.CODEC_NONEMPTY.fieldOf("result").forGetter(recipe -> recipe.result),
								Codec.INT.optionalFieldOf("evaporationTime", 100).forGetter(recipe -> recipe.evaporationTime),
								Codec.INT.optionalFieldOf("evaporationAmount", 100).forGetter(recipe -> recipe.evaporationAmount),
								Codec.FLOAT.optionalFieldOf("min", 1.5f).forGetter(recipe -> recipe.min),
								Codec.FLOAT.optionalFieldOf("max", 2.0f).forGetter(recipe -> recipe.max)
						)
						.apply(instance, VatRecipe::new)
		);

		@Override
		public Codec<VatRecipe> codec() {
			return CODEC;
		}


		@Nullable
		@Override
		public VatRecipe fromNetwork(FriendlyByteBuf buffer) {
			String group = buffer.readUtf(32767);
			Ingredient ingredient = Ingredient.fromNetwork(buffer);
			ResourceLocation fluidLocation = buffer.readResourceLocation();
			Fluid fluid = BuiltInRegistries.FLUID.getOptional(fluidLocation).orElseThrow(() -> new IllegalStateException("Fluid: " + fluidLocation + " does not exist"));
			Ingredient result = Ingredient.fromNetwork(buffer);
			int evaporationTime = buffer.readVarInt();
			int evaporationAmount = buffer.readVarInt();
			float min = buffer.readFloat();
			float max = buffer.readFloat();
			return new VatRecipe(group, ingredient, fluid, result, evaporationTime, evaporationAmount, min, max);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, VatRecipe recipe) {
			buffer.writeUtf(recipe.getGroup());
			recipe.getIngredient().toNetwork(buffer);
			buffer.writeResourceLocation(BuiltInRegistries.FLUID.getKey(recipe.fluid));
			recipe.getResultIngredient().toNetwork(buffer);
			buffer.writeVarInt(recipe.getEvaporationTime());
			buffer.writeVarInt(recipe.getEvaporationAmount());
			buffer.writeFloat(recipe.getMin());
			buffer.writeFloat(recipe.getMax());
		}
	}

}
