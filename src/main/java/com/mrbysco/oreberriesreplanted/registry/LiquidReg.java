package com.mrbysco.oreberriesreplanted.registry;

import com.mrbysco.oreberriesreplanted.util.FluidHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class LiquidReg {

	private final String name;
	private final int color;
	private final DeferredHolder<FluidType, FluidType> fluidType;
	private DeferredHolder<Fluid, BaseFlowingFluid> source;
	private DeferredHolder<Fluid, BaseFlowingFluid> flowing;
	private DeferredItem<BucketItem> bucket;

	@Nonnull
	public String getName() {
		return name;
	}

	@NotNull
	public int getColor() {
		return color;
	}

	@Nonnull
	public DeferredHolder<FluidType, FluidType> getFluidType() {
		return fluidType;
	}

	@Nonnull
	public DeferredHolder<Fluid, BaseFlowingFluid> getSource() {
		return source;
	}

	@Nonnull
	public DeferredHolder<Fluid, BaseFlowingFluid> getFlowing() {
		return flowing;
	}

	public DeferredItem<BucketItem> getBucket() {
		return bucket;
	}

	public static BaseFlowingFluid.Properties createProperties(Supplier<FluidType> type, Supplier<BaseFlowingFluid> still,
															   Supplier<BaseFlowingFluid> flowing, Supplier<BucketItem> bucket) {
		return new BaseFlowingFluid.Properties(type, still, flowing).bucket(bucket);
	}

	public LiquidReg(String name, int color, boolean hot) {
		this.name = name;
		this.color = color;
		this.fluidType = OreBerryRegistry.FLUID_TYPES.register(name, () -> new FluidType(FluidHelper.createTypeProperties().temperature(hot ? 300 : 1000)) {
			@Override
			public double motionScale(Entity entity) {
				return entity.level().dimensionType().ultraWarm() ? 0.007D : 0.0023333333333333335D;
			}

			@Override
			public void setItemMovement(ItemEntity entity) {
				Vec3 vec3 = entity.getDeltaMovement();
				entity.setDeltaMovement(vec3.x * (double) 0.95F, vec3.y + (double) (vec3.y < (double) 0.06F ? 5.0E-4F : 0.0F), vec3.z * (double) 0.95F);
			}
		});
		source = OreBerryRegistry.FLUIDS.register(name, () -> new BaseFlowingFluid.Source(
				createProperties(fluidType, source, flowing, bucket))
		);
		flowing = OreBerryRegistry.FLUIDS.register(name + "_flowing", () -> new BaseFlowingFluid.Flowing(
				createProperties(fluidType, source, flowing, bucket))
		);
		bucket = OreBerryRegistry.ITEMS.register(name + "_bucket", () -> new BucketItem(source.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	}

	public static class Builder {
		private final String name;
		private final int color;
		private boolean hot;

		public Builder(String name, boolean hot, int color) {
			this.name = name;
			this.hot = hot;
			this.color = color;
		}

		public LiquidReg build() {
			return new LiquidReg(name, color, hot);
		}
	}
}