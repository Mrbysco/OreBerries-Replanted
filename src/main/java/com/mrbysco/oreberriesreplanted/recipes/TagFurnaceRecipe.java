package com.mrbysco.oreberriesreplanted.recipes;

import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

/**
 * Taken from the Grinder repository from Noobanidus <3
 */
public class TagFurnaceRecipe extends FurnaceRecipe {
  public TagFurnaceRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
    super(idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
  }

  @Override
  public IRecipeSerializer<?> getSerializer() {
    return OreBerryRegistry.TAG_FURNACE_SERIALIZER.get();
  }

  public static class Serializer extends TagCookingRecipeSerializer<TagFurnaceRecipe> {
    public Serializer() {
      super(TagFurnaceRecipe::new, 100);
    }
  }
}

