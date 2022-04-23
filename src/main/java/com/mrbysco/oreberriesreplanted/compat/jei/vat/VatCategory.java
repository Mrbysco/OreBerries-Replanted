package com.mrbysco.oreberriesreplanted.compat.jei.vat;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.compat.jei.JeiCompat;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class VatCategory implements IRecipeCategory<VatRecipe> {
	public static final RecipeType<VatRecipe> TYPE = RecipeType.create(Reference.MOD_ID, "vat_recipe", VatRecipe.class);
	private final IDrawable background;
	private final IDrawable icon;
	private final Component title;

	public VatCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(JeiCompat.RECIPE_VAT_JEI, 0, 0, 140, 37);
		this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(OreBerryRegistry.OAK_VAT.get()));
		this.title = new TranslatableComponent("oreberriesreplanted.gui.jei.category.vat");
	}

	@SuppressWarnings("removal")
	@Override
	public ResourceLocation getUid() {
		return JeiCompat.VAT;
	}

	@Override
	public RecipeType<VatRecipe> getRecipeType() {
		return TYPE;
	}

	@SuppressWarnings("removal")
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
	public void setRecipe(IRecipeLayoutBuilder builder, VatRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 10, 10).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 113, 10)
				.addItemStack(recipe.getResultItem()).addTooltipCallback(new OutputTooltip(recipe));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 62, 10)
				.addIngredient(VanillaTypes.FLUID, new FluidStack(recipe.getFluid(), 1000)).addTooltipCallback(new FluidTooltip(recipe));
	}

	public static class OutputTooltip implements IRecipeSlotTooltipCallback {
		private final VatRecipe recipe;

		public OutputTooltip(VatRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void onTooltip(IRecipeSlotView recipeSlotView, List<Component> tooltip) {
			tooltip.add(new TranslatableComponent("oreberriesreplanted.gui.jei.category.vat_output.tooltip", recipe.getEvaporationAmount()).withStyle(ChatFormatting.GOLD));
		}
	}

	public static class FluidTooltip implements IRecipeSlotTooltipCallback {
		private final VatRecipe recipe;

		public FluidTooltip(VatRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void onTooltip(IRecipeSlotView recipeSlotView, List<Component> tooltip) {
			recipeSlotView.getDisplayedIngredient().flatMap(fluidStack -> fluidStack.getIngredient(VanillaTypes.FLUID)).ifPresent(fluid -> tooltip.add(new TranslatableComponent("oreberriesreplanted.gui.jei.category.vat.tooltip",
					((int) (recipe.getMin() * 100)), ((int) (recipe.getMax() * 100)), fluid.getDisplayName().getString()).withStyle(ChatFormatting.GOLD)));
		}
	}
}
