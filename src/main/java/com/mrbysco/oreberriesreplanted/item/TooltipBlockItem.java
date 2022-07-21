package com.mrbysco.oreberriesreplanted.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

public class TooltipBlockItem extends BlockItem {
	private final String tooltip;

	public TooltipBlockItem(Block blockIn, Properties builder, String tooltip) {
		super(blockIn, builder);
		this.tooltip = tooltip;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable(this.tooltip).withStyle(ChatFormatting.GRAY));
	}
}
