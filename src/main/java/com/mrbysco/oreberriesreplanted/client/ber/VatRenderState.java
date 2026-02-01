package com.mrbysco.oreberriesreplanted.client.ber;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.neoforged.neoforge.fluids.FluidStack;

public class VatRenderState extends BlockEntityRenderState {
	public int tankCapacity = 0;
	public FluidStack fluidStack = FluidStack.EMPTY;
	public int berryAmount = 0;
	public ItemStackRenderState berryStack = null;
}
