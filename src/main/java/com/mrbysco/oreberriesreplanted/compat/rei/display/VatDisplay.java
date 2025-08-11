package com.mrbysco.oreberriesreplanted.compat.rei.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.oreberriesreplanted.compat.rei.REIPlugin;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import dev.architectury.fluid.FluidStack;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VatDisplay implements Display {
	public static final DisplaySerializer<VatDisplay> SERIALIZER = DisplaySerializer.of(
			RecordCodecBuilder.mapCodec(instance -> instance.group(
					Codec.INT.fieldOf("evaporationTime").forGetter(d -> d.evaporationTime),
					Codec.INT.fieldOf("evaporationAmount").forGetter(d -> d.evaporationAmount),
					Codec.FLOAT.fieldOf("min").forGetter(d -> d.min),
					Codec.FLOAT.fieldOf("max").forGetter(d -> d.max),
					Codec.STRING.fieldOf("displayName").forGetter(d -> d.displayName),
					EntryIngredient.codec().fieldOf("input").forGetter(d -> d.input),
					EntryIngredient.codec().listOf().fieldOf("output").forGetter(d -> d.outputs)
			).apply(instance, VatDisplay::new)),
			StreamCodec.composite(
					ByteBufCodecs.INT,
					d -> d.evaporationTime,
					ByteBufCodecs.INT,
					d -> d.evaporationAmount,
					ByteBufCodecs.FLOAT,
					d -> d.min,
					ByteBufCodecs.FLOAT,
					d -> d.max,
					ByteBufCodecs.STRING_UTF8,
					d -> d.displayName,
					EntryIngredient.streamCodec(),
					d -> d.input,
					EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
					d -> d.outputs,
					VatDisplay::new
			));

	public final int evaporationTime, evaporationAmount;
	public final float min, max;
	public final String displayName;

	private final EntryIngredient input;
	private final List<EntryIngredient> outputs;

	public VatDisplay(RecipeHolder<VatRecipe> recipeHolder) {
		VatRecipe recipe = recipeHolder.value();
		this.evaporationTime = recipe.getEvaporationTime();
		this.evaporationAmount = recipe.getEvaporationAmount();
		this.min = recipe.getMin();
		this.max = recipe.getMax();

		this.input = EntryIngredients.ofIngredient(recipe.getIngredient());

		this.displayName = recipe.getFluid().getFluidType().getDescription().getString();

		this.outputs = new ArrayList<>();
		FluidStack fluidStack = FluidStack.create(recipe.getFluidStack().getFluid(), 1000);
		this.outputs.add(EntryIngredients.of(VanillaEntryTypes.FLUID, List.of(fluidStack)));
		this.outputs.add(EntryIngredients.ofIngredient(recipe.getResultIngredient()));
	}

	public VatDisplay(int evaporationTime, int evaporationAmount, float min, float max, String displayName,
	                  EntryIngredient input, List<EntryIngredient> outputs) {
		this.evaporationTime = evaporationTime;
		this.evaporationAmount = evaporationAmount;
		this.min = min;
		this.max = max;
		this.displayName = displayName;

		this.input = input;
		this.outputs = outputs;
	}

	@Override
	public List<EntryIngredient> getInputEntries() {
		return List.of(this.input);
	}

	@Override
	public List<EntryIngredient> getOutputEntries() {
		return this.outputs;
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return REIPlugin.VAT;
	}

	@Override
	public Optional<ResourceLocation> getDisplayLocation() {
		return Optional.empty();
	}

	@Nullable
	@Override
	public DisplaySerializer<? extends Display> getSerializer() {
		return SERIALIZER;
	}
}
