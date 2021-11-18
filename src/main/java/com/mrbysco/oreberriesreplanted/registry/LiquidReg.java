package com.mrbysco.oreberriesreplanted.registry;

import com.mrbysco.oreberriesreplanted.util.FluidHelper;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class LiquidReg<B extends FlowingFluidBlock> {
	private String name;
	private RegistryObject<ForgeFlowingFluid> source;
	private RegistryObject<ForgeFlowingFluid> flowing;
	private RegistryObject<FlowingFluidBlock> fluidblock;
	private RegistryObject<Item> bucket;

	@Nonnull
	public String getName() {
		return name;
	}

	@Nonnull
	public RegistryObject<ForgeFlowingFluid> getSource() {
		return source;
	}

	@Nonnull
	public RegistryObject<ForgeFlowingFluid> getFlowing() {
		return flowing;
	}

	public RegistryObject<Item> getBucket() {
		return bucket;
	}

	public static ForgeFlowingFluid.Properties createProperties(FluidAttributes.Builder attributeBuilder, Supplier<ForgeFlowingFluid> still,
																Supplier<ForgeFlowingFluid> flowing, Supplier<Item> bucket) {
		return new ForgeFlowingFluid.Properties(still, flowing,
				attributeBuilder)
				.bucket(bucket);
	}

	public LiquidReg(String name, Material material, int color) {
		this.name = name;
		source = OreBerryRegistry.FLUIDS.register(name, () -> new ForgeFlowingFluid.Source(
				createProperties(FluidHelper.createAttributes(color).temperature(material == Material.WATER ? 300 : 1000), source, flowing, bucket))
		);
		flowing = OreBerryRegistry.FLUIDS.register(name + "_flowing", () -> new ForgeFlowingFluid.Flowing(
				createProperties(FluidHelper.createAttributes(color).temperature(material == Material.WATER ? 300 : 1000), source, flowing, bucket))
		);
		bucket = OreBerryRegistry.ITEMS.register(name + "_bucket", () -> new BucketItem(source, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(OreBerryTab.TAB)));
	}
}