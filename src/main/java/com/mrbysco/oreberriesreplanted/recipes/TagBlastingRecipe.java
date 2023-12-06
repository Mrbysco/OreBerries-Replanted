package com.mrbysco.oreberriesreplanted.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.crafting.CraftingHelper;
import org.apache.commons.lang3.NotImplementedException;

import javax.annotation.Nullable;


/**
 * Taken from the Grinder repository from Noobanidus <3
 */
public class TagBlastingRecipe extends BlastingRecipe {
	protected final Ingredient resultIngredient;

	public TagBlastingRecipe(CookingBookCategory category, String groupIn, Ingredient ingredientIn, Ingredient resultIn, float experienceIn, int cookTimeIn) {
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
		return OreBerryRecipes.TAG_BLASTING_SERIALIZER.get();
	}

	public static class Serializer implements RecipeSerializer<TagBlastingRecipe> {
		private static final Codec<TagBlastingRecipe> CODEC = RawBlastingRecipe.CODEC.flatXmap(rawLootRecipe -> {
			return DataResult.success(new TagBlastingRecipe(
					rawLootRecipe.category,
					rawLootRecipe.group,
					rawLootRecipe.ingredient,
					rawLootRecipe.result,
					rawLootRecipe.experience,
					rawLootRecipe.cookingtime
			));
		}, recipe -> {
			throw new NotImplementedException("Serializing TagBlastingRecipe is not implemented yet.");
		});

		@Override
		public Codec<TagBlastingRecipe> codec() {
			return CODEC;
		}

		@Nullable
		@Override
		public TagBlastingRecipe fromNetwork(FriendlyByteBuf buffer) {
			String s = buffer.readUtf();
			CookingBookCategory cookingbookcategory = buffer.readEnum(CookingBookCategory.class);
			Ingredient ingredient = Ingredient.fromNetwork(buffer);
			Ingredient result = Ingredient.fromNetwork(buffer);
			float f = buffer.readFloat();
			int i = buffer.readVarInt();
			return new TagBlastingRecipe(cookingbookcategory, s, ingredient, result, f, i);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, TagBlastingRecipe recipe) {
			buffer.writeUtf(recipe.getGroup());
			buffer.writeEnum(recipe.category());
			recipe.getIngredient().toNetwork(buffer);
			recipe.getResultIngredient().toNetwork(buffer);
			buffer.writeFloat(recipe.getExperience());
			buffer.writeVarInt(recipe.getCookingTime());
		}

		static record RawBlastingRecipe(
				String group, CookingBookCategory category, Ingredient ingredient, Ingredient result, float experience,
				int cookingtime
		) {
			public static final Codec<RawBlastingRecipe> CODEC = RecordCodecBuilder.create(
					instance -> instance.group(
									ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
									CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter(recipe -> recipe.category),
									Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
									Ingredient.CODEC_NONEMPTY.fieldOf("result").forGetter(recipe -> recipe.result),
									Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(recipe -> recipe.experience),
									Codec.INT.fieldOf("cookingtime").orElse(100).forGetter(recipe -> recipe.cookingtime)
							)
							.apply(instance, RawBlastingRecipe::new)
			);
		}
	}
}

