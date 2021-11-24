package com.mrbysco.oreberriesreplanted.recipes;

import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;


/**
 * Taken from the Grinder repository from Noobanidus <3
 */
public class TagBlastingRecipe extends BlastingRecipe {
  public TagBlastingRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
    super(idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return OreBerryRegistry.TAG_BLASTING_SERIALIZER.get();
  }

  public static class Serializer extends TagCookingRecipeSerializer<TagBlastingRecipe> {
    public Serializer() {
      super(TagBlastingRecipe::new, 100);
    }
  }
}

