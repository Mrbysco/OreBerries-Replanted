package com.mrbysco.oreberriesreplanted.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EssenceBerryItem extends OreBerryItem {
	public EssenceBerryItem(Properties properties) {
		super(properties);
	}

	public EssenceBerryItem(Properties properties, String tooltip) {
		super(properties, tooltip);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		int xpGained = level.random.nextInt(14) + 6;
		if(playerIn.isShiftKeyDown()) {
			xpGained = 0;
			for(int i = 0; i < itemstack.getCount(); i++) {
				xpGained += level.random.nextInt(14) + 6;
			}
		}
		ExperienceOrb xpEntity = new ExperienceOrb(level, playerIn.getX(), playerIn.getY(), playerIn.getZ(), xpGained);
		if(!level.isClientSide) {
			level.addFreshEntity(xpEntity);
		}
		if(!playerIn.getAbilities().instabuild) {
			if(playerIn.isShiftKeyDown()) {
				itemstack.shrink(itemstack.getCount());
			} else {
				itemstack.shrink(1);
			}
		}
		return InteractionResultHolder.success(itemstack);
	}
}
