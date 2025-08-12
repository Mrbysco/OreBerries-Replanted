package com.mrbysco.oreberriesreplanted.item;

import com.mrbysco.oreberriesreplanted.block.OreBerryBushBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.SpecialPlantable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class TooltipBlockItem extends BlockItem implements SpecialPlantable {
	private final String tooltip;

	public TooltipBlockItem(Block blockIn, Properties builder, String tooltip) {
		super(blockIn, builder);
		this.tooltip = tooltip;
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
	                            @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder,
	                            @NotNull TooltipFlag flag) {
		tooltipAdder.accept(Component.translatable(this.tooltip).withStyle(ChatFormatting.GRAY));
	}

	@Override
	public boolean canPlacePlantAtPosition(ItemStack stack, LevelReader level, BlockPos pos, @Nullable Direction direction) {
		if (this.getBlock() instanceof OreBerryBushBlock berryBushBlock) {
			return !berryBushBlock.darknessOnly() || level.getRawBrightness(pos, 0) < 13;
		}
		return false;
	}

	@Override
	public void spawnPlantAtPosition(ItemStack stack, LevelAccessor level, BlockPos pos, @Nullable Direction direction) {
		level.setBlock(pos, getBlock().defaultBlockState(), 3);
	}
}
