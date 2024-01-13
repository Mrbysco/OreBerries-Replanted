package com.mrbysco.oreberriesreplanted.compat.rei.category;

import com.mrbysco.oreberriesreplanted.compat.rei.REIPlugin;
import com.mrbysco.oreberriesreplanted.compat.rei.display.VatDisplay;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class VatCategory implements DisplayCategory<VatDisplay> {
	@Override
	public CategoryIdentifier<? extends VatDisplay> getCategoryIdentifier() {
		return REIPlugin.VAT;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("oreberriesreplanted.gui.jei.category.vat");
	}

	@Override
	public Renderer getIcon() {
		return EntryStacks.of(OreBerryRegistry.OAK_VAT.get());
	}

	@Override
	public List<Widget> setupDisplay(VatDisplay display, Rectangle bounds) {
		Point centerPoint = new Point(bounds.getCenterX(), bounds.getCenterY());
		List<Widget> widgets = new ArrayList<>();
		widgets.add(Widgets.createRecipeBase(bounds));
		widgets.add(Widgets.createArrow(new Point(centerPoint.x - 40, centerPoint.y - 7)));

		widgets.add(Widgets.createSlot(new Point(bounds.getMinX() + 8, centerPoint.y - 7)).entries(display.getInputEntries().get(0)).markOutput());
		if(!display.getOutputEntries().isEmpty()) {
			if (display.getOutputEntries().size() == 3) {
				widgets.add(Widgets.createSlot(new Point(centerPoint.x - 8, centerPoint.y - 7)).entries(display.getOutputEntries().get(0)).markInput());
				widgets.add(Widgets.createArrow(new Point(centerPoint.x + 16, centerPoint.y - 7)));
				widgets.add(Widgets.createSlot(new Point(centerPoint.x + 46, centerPoint.y - 7)).entries(display.getOutputEntries().get(1)).markOutput());
			}
		}

		widgets.add(Widgets.createTooltip(bounds, Component.translatable("oreberriesreplanted.gui.jei.category.vat.tooltip",
				((int) (display.min * 100)), ((int) (display.max * 100)), display.displayName).withStyle(ChatFormatting.GOLD)));

		return widgets;
	}

	@Override
	public int getDisplayWidth(VatDisplay display) {
		return 140;
	}

	@Override
	public int getDisplayHeight() {
		return 40;
	}
}
