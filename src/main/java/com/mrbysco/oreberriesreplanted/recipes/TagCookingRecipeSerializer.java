package com.mrbysco.oreberriesreplanted.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * Taken from the Grinder repository from Noobanidus <3
 */
public abstract class TagCookingRecipeSerializer<T extends AbstractCookingRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
  protected final int defaultCookTime;
  protected final IFactory<T> serializer;

  public TagCookingRecipeSerializer(IFactory<T> serializer, int defaultCookTime) {
    this.defaultCookTime = defaultCookTime;
    this.serializer = serializer;
  }

  @Override
  public T read(ResourceLocation recipeId, JsonObject json) {
    String s = JSONUtils.getString(json, "group", "");
    JsonElement jsonelement = (JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient"));
    Ingredient ingredient = Ingredient.deserialize(jsonelement);

    //Forge: Check if primitive string to keep vanilla or a object which can contain a count field.
    if (!json.has("result"))
      throw new JsonSyntaxException("Missing result, expected to find a string or object");
    jsonelement = (JSONUtils.isJsonArray(json, "result") ? JSONUtils.getJsonArray(json, "result") : JSONUtils.getJsonObject(json, "result"));
    Ingredient result = Ingredient.deserialize(jsonelement);
    if (result.hasNoMatchingItems()) {
      throw new JsonSyntaxException("Missing result: ingredient has no matching stacks");
    }
    ItemStack itemstack = result.getMatchingStacks()[0];

    float f = JSONUtils.getFloat(json, "experience", 0.0F);
    int i = JSONUtils.getInt(json, "cookingtime", this.defaultCookTime);
    return this.serializer.create(recipeId, s, ingredient, itemstack, f, i);
  }

  @Override
  public T read(ResourceLocation recipeId, PacketBuffer buffer) {
    String s = buffer.readString(32767);
    Ingredient ingredient = Ingredient.read(buffer);
    ItemStack itemstack = buffer.readItemStack();
    float f = buffer.readFloat();
    int i = buffer.readVarInt();
    return this.serializer.create(recipeId, s, ingredient, itemstack, f, i);
  }

  @Override
  public void write(PacketBuffer buffer, T recipe) {
    buffer.writeString(recipe.getGroup());
    recipe.getIngredients().forEach(o -> o.write(buffer));
    buffer.writeItemStack(recipe.getRecipeOutput());
    buffer.writeFloat(recipe.getExperience());
    buffer.writeVarInt(recipe.getCookTime());
  }

  public interface IFactory<T extends AbstractCookingRecipe> {
    T create(ResourceLocation rl, String group, Ingredient input, ItemStack result, float xp, int cookTime);
  }
}
