package com.mrbysco.oreberriesreplanted.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EssenceBerryItem extends OreBerryItem {

	public EssenceBerryItem(Properties properties, String tooltip) {
		super(properties, tooltip);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		int xpGained = level.random.nextInt(14) + 6;
		if (player.isShiftKeyDown()) {
			xpGained = 0;
			for (int i = 0; i < itemstack.getCount(); i++) {
				xpGained += level.random.nextInt(14) + 6;
			}
		}
		ExperienceOrb xpEntity = new ExperienceOrb(level, player.getX(), player.getY(), player.getZ(), xpGained);
		if (!level.isClientSide()) {
			level.addFreshEntity(xpEntity);
		}
		if (!player.getAbilities().instabuild) {
			if (player.isShiftKeyDown()) {
				itemstack.shrink(itemstack.getCount());
			} else {
				itemstack.shrink(1);
			}
		}
		return InteractionResult.SUCCESS;
	}
}
