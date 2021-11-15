package com.mrbysco.oreberriesreplanted.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class OreBerryItem extends Item {
	private String tooltip = "";

	public OreBerryItem(Properties properties) {
		super(properties);
	}

	public OreBerryItem(Properties properties, String tooltip) {
		super(properties);
		this.tooltip = tooltip;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if(!tooltip.isEmpty()) {
			tooltip.add(new TranslationTextComponent(this.tooltip).withStyle(TextFormatting.GRAY));
		}
	}
}
