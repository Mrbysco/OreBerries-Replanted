package com.mrbysco.oreberriesreplanted.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class TooltipBlockItem extends BlockItem {
	private final String tooltip;

	public TooltipBlockItem(Block blockIn, Properties builder, String tooltip) {
		super(blockIn, builder);
		this.tooltip = tooltip;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		tooltip.add(Component.translatable(this.tooltip).withStyle(ChatFormatting.GRAY));
	}
}
