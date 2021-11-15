package com.mrbysco.oreberriesreplanted.item;

import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EssenceBerryItem extends OreBerryItem {
	public EssenceBerryItem(Properties properties) {
		super(properties);
	}

	public EssenceBerryItem(Properties properties, String tooltip) {
		super(properties, tooltip);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		int xpGained = random.nextInt(14) + 6;
		if(playerIn.isShiftKeyDown()) {
			xpGained = 0;
			for(int i = 0; i < itemstack.getCount(); i++) {
				xpGained += random.nextInt(14) + 6;
			}
		}
		ExperienceOrbEntity xpEntity = new ExperienceOrbEntity(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), xpGained);
		if(!worldIn.isClientSide) {
			worldIn.addFreshEntity(xpEntity);
		}
		if(!playerIn.abilities.instabuild) {
			if(playerIn.isShiftKeyDown()) {
				itemstack.shrink(itemstack.getCount());
			} else {
				itemstack.shrink(1);
			}
		}
		return ActionResult.success(itemstack);
	}
}
