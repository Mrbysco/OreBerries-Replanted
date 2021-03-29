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
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		int xpGained = random.nextInt(14) + 6;
		if(playerIn.isSneaking()) {
			xpGained = 0;
			for(int i = 0; i < itemstack.getCount(); i++) {
				xpGained += random.nextInt(14) + 6;
			}
		}
		ExperienceOrbEntity xpEntity = new ExperienceOrbEntity(worldIn, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), xpGained);
		if(!worldIn.isRemote) {
			worldIn.addEntity(xpEntity);
		}
		if(!playerIn.abilities.isCreativeMode) {
			if(playerIn.isSneaking()) {
				itemstack.shrink(itemstack.getCount());
			} else {
				itemstack.shrink(1);
			}
		}
		return ActionResult.resultSuccess(itemstack);
	}
}
