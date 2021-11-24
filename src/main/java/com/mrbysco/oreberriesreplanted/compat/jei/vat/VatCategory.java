package com.mrbysco.oreberriesreplanted.compat.jei.vat;

import com.mrbysco.oreberriesreplanted.compat.jei.JeiCompat;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.gui.ingredient.ITooltipCallback;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class VatCategory implements IRecipeCategory<VatRecipe> {
	private final IDrawable background;
	private final IDrawable icon;
	private final Component title;

	public VatCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(JeiCompat.RECIPE_VAT_JEI, 0, 0, 140, 37);
		this.icon = guiHelper.createDrawableIngredient(new ItemStack(OreBerryRegistry.OAK_VAT.get()));
		this.title = new TranslatableComponent("oreberriesreplanted.gui.jei.category.vat");
	}

	@Override
	public ResourceLocation getUid() {
		return JeiCompat.VAT;
	}

	@Override
	public Class<? extends VatRecipe> getRecipeClass() {
		return VatRecipe.class;
	}

	@Override
	public Component getTitle() {
		return title;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setIngredients(VatRecipe recipe, IIngredients ingredients) {
		ingredients.setInputIngredients(recipe.getIngredients());
		ingredients.setInput(VanillaTypes.FLUID, new FluidStack(recipe.getFluid(), 1000));
		ingredients.setOutput(VanillaTypes.FLUID, new FluidStack(recipe.getFluid(), 1000));
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, VatRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

		guiItemStacks.init(0, true, 9, 9);
		guiItemStacks.init(1, false, 112, 9);
		guiFluidStacks.init(0, false, 62, 10);

		guiItemStacks.set(ingredients);
		guiFluidStacks.set(ingredients);
		guiFluidStacks.addTooltipCallback(new ITooltipCallback<FluidStack>() {
			@OnlyIn(Dist.CLIENT)
			@Override
			public void onTooltip(int slotIndex, boolean input, FluidStack ingredient, List<Component> tooltip) {
				tooltip.add(new TranslatableComponent("oreberriesreplanted.gui.jei.category.vat.tooltip",
						((int)(recipe.getMin() * 100)), ((int)(recipe.getMax() * 100)), ingredient.getDisplayName().getString()).withStyle(ChatFormatting.GOLD));
			}
		});
		guiItemStacks.addTooltipCallback(new ITooltipCallback<ItemStack>() {
			@OnlyIn(Dist.CLIENT)
			@Override
			public void onTooltip(int slot, boolean input, ItemStack stack, List<Component> tooltip) {
				if(!input) {
					tooltip.add(new TranslatableComponent("oreberriesreplanted.gui.jei.category.vat_output.tooltip", recipe.getEvaporationAmount()).withStyle(ChatFormatting.GOLD));
				}
			}
		});
	}
}
