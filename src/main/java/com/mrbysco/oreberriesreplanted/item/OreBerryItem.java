package com.mrbysco.oreberriesreplanted.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

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
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if (!tooltip.isEmpty()) {
			tooltip.add(new TranslatableComponent(this.tooltip).withStyle(ChatFormatting.GRAY));
		}
	}
}
