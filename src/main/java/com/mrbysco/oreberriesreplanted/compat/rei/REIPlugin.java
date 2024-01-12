package com.mrbysco.oreberriesreplanted.compat.rei;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.compat.rei.category.VatCategory;
import com.mrbysco.oreberriesreplanted.compat.rei.display.VatDisplay;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

@REIPluginClient
public class REIPlugin implements REIClientPlugin {
	public static final CategoryIdentifier<VatDisplay> VAT = CategoryIdentifier.of(Reference.MOD_ID, "plugins/vat");

	@Override
	public void registerCategories(CategoryRegistry registry) {
		registry.add(new VatCategory());

		registry.addWorkstations(VAT, EntryStacks.of(OreBerryRegistry.OAK_VAT.get()));
		registry.addWorkstations(VAT, EntryStacks.of(OreBerryRegistry.SPRUCE_VAT.get()));
		registry.addWorkstations(VAT, EntryStacks.of(OreBerryRegistry.BIRCH_VAT.get()));
		registry.addWorkstations(VAT, EntryStacks.of(OreBerryRegistry.JUNGLE_VAT.get()));
		registry.addWorkstations(VAT, EntryStacks.of(OreBerryRegistry.ACACIA_VAT.get()));
		registry.addWorkstations(VAT, EntryStacks.of(OreBerryRegistry.DARK_OAK_VAT.get()));
		registry.addWorkstations(VAT, EntryStacks.of(OreBerryRegistry.MANGROVE_VAT.get()));
		registry.addWorkstations(VAT, EntryStacks.of(OreBerryRegistry.CHERRY_VAT.get()));
		registry.addWorkstations(VAT, EntryStacks.of(OreBerryRegistry.CRIMSON_VAT.get()));
		registry.addWorkstations(VAT, EntryStacks.of(OreBerryRegistry.WARPED_VAT.get()));
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {
		List<RecipeHolder<VatRecipe>> vatHolders = registry.getRecipeManager().getAllRecipesFor(OreBerryRecipes.VAT_RECIPE_TYPE.get());
		vatHolders.forEach((holder) -> registry.add(new VatDisplay(holder)));
	}

}
