package com.mrbysco.oreberriesreplanted.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import javax.annotation.Nullable;

/**
 * Taken from the Grinder repository from Noobanidus <3
 */
public class TagFurnaceRecipe extends SmeltingRecipe {
	protected final Ingredient resultIngredient;

	public TagFurnaceRecipe(CookingBookCategory category, String groupIn, Ingredient ingredientIn, Ingredient resultIn, float experienceIn, int cookTimeIn) {
		super(groupIn, category, ingredientIn, ItemStack.EMPTY, experienceIn, cookTimeIn);
		this.resultIngredient = resultIn;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public Ingredient getResultIngredient() {
		return resultIngredient;
	}

	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		return this.getResultItem(registryAccess).copy();
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return resultIngredient.getItems()[0];
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return OreBerryRecipes.TAG_FURNACE_SERIALIZER.get();
	}

	public static class Serializer implements RecipeSerializer<TagFurnaceRecipe> {
		public static final Codec<TagFurnaceRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter(recipe -> recipe.category),
								ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
								Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
								Ingredient.CODEC_NONEMPTY.fieldOf("result").forGetter(recipe -> recipe.resultIngredient),
								Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(recipe -> recipe.experience),
								Codec.INT.fieldOf("cookingtime").orElse(200).forGetter(recipe -> recipe.cookingTime)
						)
						.apply(instance, TagFurnaceRecipe::new)
		);

		@Override
		public Codec<TagFurnaceRecipe> codec() {
			return CODEC;
		}

		@Nullable
		@Override
		public TagFurnaceRecipe fromNetwork(FriendlyByteBuf buffer) {
			String s = buffer.readUtf();
			CookingBookCategory cookingbookcategory = buffer.readEnum(CookingBookCategory.class);
			Ingredient ingredient = Ingredient.fromNetwork(buffer);
			Ingredient result = Ingredient.fromNetwork(buffer);
			float f = buffer.readFloat();
			int i = buffer.readVarInt();
			return new TagFurnaceRecipe(cookingbookcategory, s, ingredient, result, f, i);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, TagFurnaceRecipe recipe) {
			buffer.writeUtf(recipe.getGroup());
			buffer.writeEnum(recipe.category());
			recipe.getIngredient().toNetwork(buffer);
			recipe.getResultIngredient().toNetwork(buffer);
			buffer.writeFloat(recipe.getExperience());
			buffer.writeVarInt(recipe.getCookingTime());
		}
	}
}

