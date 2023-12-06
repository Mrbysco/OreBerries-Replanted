package com.mrbysco.oreberriesreplanted.registry;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.recipes.TagBlastingRecipe;
import com.mrbysco.oreberriesreplanted.recipes.TagFurnaceRecipe;
import com.mrbysco.oreberriesreplanted.recipes.TagFurnaceRecipe.Serializer;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class OreBerryRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Reference.MOD_ID);
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Reference.MOD_ID);

	public static final Supplier<RecipeType<VatRecipe>> VAT_RECIPE_TYPE = RECIPE_TYPES.register("vat_recipe", () -> new RecipeType<>() {
	});

	public static final Supplier<Serializer> TAG_FURNACE_SERIALIZER = RECIPE_SERIALIZERS.register("furnace", TagFurnaceRecipe.Serializer::new);
	public static final Supplier<TagBlastingRecipe.Serializer> TAG_BLASTING_SERIALIZER = RECIPE_SERIALIZERS.register("blasting", TagBlastingRecipe.Serializer::new);
	public static final Supplier<VatRecipe.Serializer> VAT_SERIALIZER = RECIPE_SERIALIZERS.register("vat", VatRecipe.Serializer::new);
}
