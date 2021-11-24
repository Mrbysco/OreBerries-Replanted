package com.mrbysco.oreberriesreplanted.recipes;

import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;

/**
 * Taken from the Grinder repository from Noobanidus <3
 */
public class TagFurnaceRecipe extends SmeltingRecipe {
  public TagFurnaceRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
    super(idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return OreBerryRegistry.TAG_FURNACE_SERIALIZER.get();
  }

  public static class Serializer extends TagCookingRecipeSerializer<TagFurnaceRecipe> {
    public Serializer() {
      super(TagFurnaceRecipe::new, 100);
    }
  }
}

