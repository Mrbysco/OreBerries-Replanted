package com.mrbysco.oreberriesreplanted.registry;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.recipes.TagBlastingRecipe;
import com.mrbysco.oreberriesreplanted.recipes.TagFurnaceRecipe;
import com.mrbysco.oreberriesreplanted.recipes.TagFurnaceRecipe.Serializer;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OreBerryRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MOD_ID);
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Reference.MOD_ID);

	public static final RegistryObject<RecipeType<VatRecipe>> VAT_RECIPE_TYPE = RECIPE_TYPES.register("vat_recipe", () -> new RecipeType<>() {
	});

	public static final RegistryObject<Serializer> TAG_FURNACE_SERIALIZER = RECIPE_SERIALIZERS.register("furnace", TagFurnaceRecipe.Serializer::new);
	public static final RegistryObject<TagBlastingRecipe.Serializer> TAG_BLASTING_SERIALIZER = RECIPE_SERIALIZERS.register("blasting", TagBlastingRecipe.Serializer::new);
	public static final RegistryObject<VatRecipe.Serializer> VAT_SERIALIZER = RECIPE_SERIALIZERS.register("vat", VatRecipe.Serializer::new);
}
