package com.mrbysco.oreberriesreplanted.datagen;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryPlacements;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class OreBerryBiomeModifiers {
	public static final ResourceKey<BiomeModifier> IRON_OREBERRY_BUSH_FEATURE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Reference.MOD_ID, "iron_oreberry_bush"));
	public static final ResourceKey<BiomeModifier> GOLD_OREBERRY_BUSH_FEATURE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Reference.MOD_ID, "gold_oreberry_bush"));
	public static final ResourceKey<BiomeModifier> COPPER_OREBERRY_BUSH_FEATURE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Reference.MOD_ID, "copper_oreberry_bush"));
	public static final ResourceKey<BiomeModifier> TIN_OREBERRY_BUSH_FEATURE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Reference.MOD_ID, "tin_oreberry_bush"));
	public static final ResourceKey<BiomeModifier> ALUMINUM_OREBERRY_BUSH_FEATURE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Reference.MOD_ID, "aluminum_oreberry_bush"));
	public static final ResourceKey<BiomeModifier> LEAD_OREBERRY_BUSH_FEATURE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Reference.MOD_ID, "lead_oreberry_bush"));
	public static final ResourceKey<BiomeModifier> NICKEL_OREBERRY_BUSH_FEATURE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Reference.MOD_ID, "nickel_oreberry_bush"));
	public static final ResourceKey<BiomeModifier> URANIUM_OREBERRY_BUSH_FEATURE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Reference.MOD_ID, "uranium_oreberry_bush"));
	public static final ResourceKey<BiomeModifier> OSMIUM_OREBERRY_BUSH_FEATURE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Reference.MOD_ID, "osmium_oreberry_bush"));
	public static final ResourceKey<BiomeModifier> ZINC_OREBERRY_BUSH_FEATURE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Reference.MOD_ID, "zinc_oreberry_bush"));
	public static final ResourceKey<BiomeModifier> SILVER_OREBERRY_BUSH_FEATURE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Reference.MOD_ID, "silver_oreberry_bush"));
	public static final ResourceKey<BiomeModifier> ESSENCE_BERRY_BUSH_FEATURE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Reference.MOD_ID, "essence_berry_bush"));

	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);
		HolderGetter<PlacedFeature> placedGetter = context.lookup(Registries.PLACED_FEATURE);
		HolderSet.Named<Biome> overworldTag = biomeGetter.getOrThrow(BiomeTags.IS_OVERWORLD);

		context.register(IRON_OREBERRY_BUSH_FEATURE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(overworldTag, HolderSet.direct(placedGetter.getOrThrow(OreBerryPlacements.IRON_OREBERRY_BUSH_FEATURE)), GenerationStep.Decoration.UNDERGROUND_ORES));
		context.register(GOLD_OREBERRY_BUSH_FEATURE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(overworldTag, HolderSet.direct(placedGetter.getOrThrow(OreBerryPlacements.GOLD_OREBERRY_BUSH_FEATURE)), GenerationStep.Decoration.UNDERGROUND_ORES));
		context.register(COPPER_OREBERRY_BUSH_FEATURE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(overworldTag, HolderSet.direct(placedGetter.getOrThrow(OreBerryPlacements.COPPER_OREBERRY_BUSH_FEATURE)), GenerationStep.Decoration.UNDERGROUND_ORES));
		context.register(TIN_OREBERRY_BUSH_FEATURE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(overworldTag, HolderSet.direct(placedGetter.getOrThrow(OreBerryPlacements.TIN_OREBERRY_BUSH_FEATURE)), GenerationStep.Decoration.UNDERGROUND_ORES));
		context.register(ALUMINUM_OREBERRY_BUSH_FEATURE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(overworldTag, HolderSet.direct(placedGetter.getOrThrow(OreBerryPlacements.ALUMINUM_OREBERRY_BUSH_FEATURE)), GenerationStep.Decoration.UNDERGROUND_ORES));
		context.register(LEAD_OREBERRY_BUSH_FEATURE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(overworldTag, HolderSet.direct(placedGetter.getOrThrow(OreBerryPlacements.LEAD_OREBERRY_BUSH_FEATURE)), GenerationStep.Decoration.UNDERGROUND_ORES));
		context.register(NICKEL_OREBERRY_BUSH_FEATURE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(overworldTag, HolderSet.direct(placedGetter.getOrThrow(OreBerryPlacements.NICKEL_OREBERRY_BUSH_FEATURE)), GenerationStep.Decoration.UNDERGROUND_ORES));
		context.register(URANIUM_OREBERRY_BUSH_FEATURE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(overworldTag, HolderSet.direct(placedGetter.getOrThrow(OreBerryPlacements.URANIUM_OREBERRY_BUSH_FEATURE)), GenerationStep.Decoration.UNDERGROUND_ORES));
		context.register(OSMIUM_OREBERRY_BUSH_FEATURE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(overworldTag, HolderSet.direct(placedGetter.getOrThrow(OreBerryPlacements.OSMIUM_OREBERRY_BUSH_FEATURE)), GenerationStep.Decoration.UNDERGROUND_ORES));
		context.register(ZINC_OREBERRY_BUSH_FEATURE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(overworldTag, HolderSet.direct(placedGetter.getOrThrow(OreBerryPlacements.ZINC_OREBERRY_BUSH_FEATURE)), GenerationStep.Decoration.UNDERGROUND_ORES));
		context.register(SILVER_OREBERRY_BUSH_FEATURE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(overworldTag, HolderSet.direct(placedGetter.getOrThrow(OreBerryPlacements.SILVER_OREBERRY_BUSH_FEATURE)), GenerationStep.Decoration.UNDERGROUND_ORES));
		context.register(ESSENCE_BERRY_BUSH_FEATURE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(overworldTag, HolderSet.direct(placedGetter.getOrThrow(OreBerryPlacements.ESSENCE_BERRY_BUSH_FEATURE)), GenerationStep.Decoration.UNDERGROUND_ORES));
	}
}
