package com.mrbysco.oreberriesreplanted.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;

/**
 * Taken from the Grinder repository from Noobanidus <3
 */
public class TagFurnaceRecipe extends AbstractCookingRecipe {
	protected final Ingredient resultIngredient;

	public TagFurnaceRecipe(CookingBookCategory category, String groupIn, Ingredient ingredientIn, Ingredient resultIn, float experienceIn, int cookTimeIn) {
		super(groupIn, category, ingredientIn, ItemStack.EMPTY, experienceIn, cookTimeIn);
		this.resultIngredient = resultIn;
	}

	@Override
	protected Item furnaceIcon() {
		return Items.FURNACE;
	}

	public Ingredient getIngredient() {
		return input;
	}

	public Ingredient getResultIngredient() {
		return resultIngredient;
	}

	@Override
	public ItemStack assemble(SingleRecipeInput recipeInput, Provider provider) {
		return this.getResultItem().copy();
	}

	public ItemStack getResultItem() {
		return new ItemStack(resultIngredient.getValues().get(0));
	}

	@Override
	public RecipeSerializer<TagFurnaceRecipe> getSerializer() {
		return OreBerryRecipes.TAG_FURNACE_SERIALIZER.get();
	}

	@Override
	public RecipeType<SmeltingRecipe> getType() {
		return RecipeType.SMELTING;
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return switch (this.category()) {
			case BLOCKS -> RecipeBookCategories.BLAST_FURNACE_BLOCKS;
			case FOOD, MISC -> RecipeBookCategories.BLAST_FURNACE_MISC;
		};
	}

	public static class Serializer implements RecipeSerializer<TagFurnaceRecipe> {
		public static final MapCodec<TagFurnaceRecipe> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
								CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter(recipe -> recipe.category),
								Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
								Ingredient.CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.input),
								Ingredient.CODEC.fieldOf("result").forGetter(recipe -> recipe.resultIngredient),
								Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(recipe -> recipe.experience),
								Codec.INT.fieldOf("cookingtime").orElse(200).forGetter(recipe -> recipe.cookingTime)
						)
						.apply(instance, TagFurnaceRecipe::new)
		);
		public static final StreamCodec<RegistryFriendlyByteBuf, TagFurnaceRecipe> STREAM_CODEC = StreamCodec.of(
				TagFurnaceRecipe.Serializer::toNetwork, TagFurnaceRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<TagFurnaceRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, TagFurnaceRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		public static TagFurnaceRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			String s = buffer.readUtf();
			CookingBookCategory cookingbookcategory = buffer.readEnum(CookingBookCategory.class);
			Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
			Ingredient result = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
			float f = buffer.readFloat();
			int i = buffer.readVarInt();
			return new TagFurnaceRecipe(cookingbookcategory, s, ingredient, result, f, i);
		}

		public static void toNetwork(RegistryFriendlyByteBuf buffer, TagFurnaceRecipe recipe) {
			buffer.writeUtf(recipe.group());
			buffer.writeEnum(recipe.category());
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getIngredient());
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getResultIngredient());
			buffer.writeFloat(recipe.experience());
			buffer.writeVarInt(recipe.cookingTime());
		}
	}
}

