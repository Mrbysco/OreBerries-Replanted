package com.mrbysco.oreberriesreplanted.registry;

import com.mrbysco.oreberriesreplanted.Reference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class OreBerryTab {
	public static final CreativeModeTab TAB = new CreativeModeTab(Reference.MOD_ID) {
		public ItemStack makeIcon() {
			return new ItemStack(Blocks.OAK_LEAVES);
		}
	};
}
