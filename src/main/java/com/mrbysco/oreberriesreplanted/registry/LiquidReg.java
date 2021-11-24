package com.mrbysco.oreberriesreplanted.registry;

import com.mrbysco.oreberriesreplanted.util.FluidHelper;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fmllegacy.RegistryObject;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class LiquidReg {
	private final String name;
	private RegistryObject<ForgeFlowingFluid> source;
	private RegistryObject<ForgeFlowingFluid> flowing;
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