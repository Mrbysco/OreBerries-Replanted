package com.mrbysco.oreberriesreplanted.item;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TooltipBlockItem extends BlockItem {
	private String tooltip = "";
	public TooltipBlockItem(Block blockIn, Properties builder, String tooltip) {
		super(blockIn, builder);
		this.tooltip = tooltip;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent(this.tooltip).withStyle(TextFormatting.GRAY));
	}
}
