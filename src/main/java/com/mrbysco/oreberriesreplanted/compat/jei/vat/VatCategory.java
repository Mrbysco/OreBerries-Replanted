package com.mrbysco.oreberriesreplanted.compat.jei.vat;

import com.mrbysco.oreberriesreplanted.compat.jei.JeiCompat;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotRichTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class VatCategory implements IRecipeCategory<VatRecipe> {
	private final IDrawable icon;
	private final Component title;

	public VatCategory(IGuiHelper guiHelper) {
		this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(OreBerryRegistry.OAK_VAT.get()));
		this.title = Component.translatable("oreberriesreplanted.gui.jei.category.vat");
	}

	@Override
	public IRecipeType<VatRecipe> getRecipeType() {
		return JeiCompat.VAT_TYPE;
	}

	@Override
	public Component getTitle() {
		return title;
	}

	@Override
	public int getWidth() {
		return 140;
	}

	@Override
	public int getHeight() {
		return 37;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, VatRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 10, 10)
				.add(recipe.getIngredient())
				.setStandardSlotBackground();
		builder.addSlot(RecipeIngredientRole.OUTPUT, 113, 10)
				.add(recipe.getResultItem())
				.addRichTooltipCallback(new OutputTooltip(recipe))
				.setStandardSlotBackground();
		builder.addSlot(RecipeIngredientRole.OUTPUT, 62, 10)
				.add(NeoForgeTypes.FLUID_STACK, new FluidStack(recipe.getFluid(), 1000))
				.addRichTooltipCallback(new FluidTooltip(recipe))
				.setStandardSlotBackground();
	}

	@Override
	public void draw(VatRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

	}

	public static class OutputTooltip implements IRecipeSlotRichTooltipCallback {
		private final VatRecipe recipe;

		public OutputTooltip(VatRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void onRichTooltip(IRecipeSlotView recipeSlotView, ITooltipBuilder tooltip) {
			tooltip.add(Component.translatable("oreberriesreplanted.gui.jei.category.vat_output.tooltip", recipe.getEvaporationAmount()).withStyle(ChatFormatting.GOLD));
		}
	}

	public static class FluidTooltip implements IRecipeSlotRichTooltipCallback {
		private final VatRecipe recipe;

		public FluidTooltip(VatRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void onRichTooltip(IRecipeSlotView recipeSlotView, ITooltipBuilder tooltip) {
			recipeSlotView.getDisplayedIngredient().flatMap(fluidStack -> fluidStack.getIngredient(NeoForgeTypes.FLUID_STACK)).ifPresent(fluid ->
					tooltip.add(Component.translatable("oreberriesreplanted.gui.jei.category.vat.tooltip",
							((int) (recipe.getMin() * 100)), ((int) (recipe.getMax() * 100)), fluid.getHoverName().getString()).withStyle(ChatFormatting.GOLD)));
		}
	}
}
