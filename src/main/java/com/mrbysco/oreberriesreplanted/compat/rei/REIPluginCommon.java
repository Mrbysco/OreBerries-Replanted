//package com.mrbysco.oreberriesreplanted.compat.rei;
//
//import com.mrbysco.oreberriesreplanted.Reference;
//import com.mrbysco.oreberriesreplanted.compat.rei.display.VatDisplay;
//import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
//import com.mrbysco.oreberriesreplanted.registry.OreBerryRecipes;
//import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
//import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
//import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
//
//@me.shedaniel.rei.forge.REIPluginCommon
//public class REIPluginCommon implements REICommonPlugin {
//
//	@Override
//	public void registerDisplays(ServerDisplayRegistry registry) {
//		registry.beginRecipeFiller(VatRecipe.class)
//				.filterType(OreBerryRecipes.VAT_RECIPE_TYPE.get())
//				.fill(VatDisplay::new);
//	}
//
//	@Override
//	public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
//		registry.register(Reference.modLoc("vat"), VatDisplay.SERIALIZER);
//	}
//}
