package com.mrbysco.oreberriesreplanted.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

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
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		if (!tooltip.isEmpty()) {
			tooltip.add(Component.translatable(this.tooltip).withStyle(ChatFormatting.GRAY));
		}
	}
}
