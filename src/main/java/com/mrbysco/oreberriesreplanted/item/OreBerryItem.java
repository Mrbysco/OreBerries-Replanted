package com.mrbysco.oreberriesreplanted.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

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
	public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
	                            @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder,
	                            @NotNull TooltipFlag flag) {
		if (!tooltip.isEmpty()) {
			tooltipAdder.accept(Component.translatable(this.tooltip).withStyle(ChatFormatting.GRAY));
		}
	}
}
