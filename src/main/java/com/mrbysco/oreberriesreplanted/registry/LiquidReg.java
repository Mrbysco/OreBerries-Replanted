package com.mrbysco.oreberriesreplanted.registry;

import com.mojang.math.Vector3f;
import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.util.FluidHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LiquidReg {
	private final String name;
	private RegistryObject<FluidType> fluidType;
	private RegistryObject<ForgeFlowingFluid> source;
	private RegistryObject<ForgeFlowingFluid> flowing;
	private RegistryObject<Item> bucket;

	@Nonnull
	public String getName() {
		return name;
	}

	@Nonnull
	public RegistryObject<FluidType> getFluidType() {
		return fluidType;
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

	public static ForgeFlowingFluid.Properties createProperties(Supplier<FluidType> type, Supplier<ForgeFlowingFluid> still,
																Supplier<ForgeFlowingFluid> flowing, Supplier<Item> bucket) {
		return new ForgeFlowingFluid.Properties(type, still, flowing).bucket(bucket);
	}

	public LiquidReg(String name, Material material, int color) {
		this.name = name;
		fluidType = OreBerryRegistry.FLUID_TYPES.register(name, () -> new FluidType(FluidHelper.createTypeProperties().temperature(material == Material.WATER ? 300 : 1000)) {
			@Override
			public double motionScale(Entity entity) {
				return entity.level.dimensionType().ultraWarm() ? 0.007D : 0.0023333333333333335D;
			}

			@Override
			public void setItemMovement(ItemEntity entity) {
				Vec3 vec3 = entity.getDeltaMovement();
				entity.setDeltaMovement(vec3.x * (double) 0.95F, vec3.y + (double) (vec3.y < (double) 0.06F ? 5.0E-4F : 0.0F), vec3.z * (double) 0.95F);
			}

			@Override
			public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
				consumer.accept(new IClientFluidTypeExtensions() {
					private final static ResourceLocation STILL_BERRY = new ResourceLocation(Reference.MOD_ID, "block/liquid_berry_still");
					private final static ResourceLocation FLOWING_BERRY = new ResourceLocation(Reference.MOD_ID, "block/liquid_berry_flow");

					@Override
					public ResourceLocation getStillTexture() {
						return STILL_BERRY;
					}

					@Override
					public ResourceLocation getFlowingTexture() {
						return FLOWING_BERRY;
					}

					@Override
					public int getTintColor() {
						return color;
					}

					@Override
					public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
						int color = this.getTintColor();
						return new Vector3f((color >> 16 & 0xFF) / 255F, (color >> 8 & 0xFF) / 255F, (color & 0xFF) / 255F);
					}
				});
			}
		});

		source = OreBerryRegistry.FLUIDS.register(name, () -> new ForgeFlowingFluid.Source(
				createProperties(fluidType, source, flowing, bucket))
		);
		flowing = OreBerryRegistry.FLUIDS.register(name + "_flowing", () -> new ForgeFlowingFluid.Flowing(
				createProperties(fluidType, source, flowing, bucket))
		);
		bucket = OreBerryRegistry.ITEMS.register(name + "_bucket", () -> new BucketItem(source, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(OreBerryTab.TAB)));
	}
}