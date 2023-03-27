package com.mrbysco.oreberriesreplanted.worldgen;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.block.OreBerryBushBlock;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class OreBerryFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> IRON_OREBERRY_BUSH_FEATURE = FeatureUtils.createKey(new ResourceLocation(Reference.MOD_ID, "iron_oreberry_bush").toString());
	public static final ResourceKey<ConfiguredFeature<?, ?>> GOLD_OREBERRY_BUSH_FEATURE = FeatureUtils.createKey(new ResourceLocation(Reference.MOD_ID, "gold_oreberry_bush").toString());
	public static final ResourceKey<ConfiguredFeature<?, ?>> COPPER_OREBERRY_BUSH_FEATURE = FeatureUtils.createKey(new ResourceLocation(Reference.MOD_ID, "copper_oreberry_bush").toString());
	public static final ResourceKey<ConfiguredFeature<?, ?>> TIN_OREBERRY_BUSH_FEATURE = FeatureUtils.createKey(new ResourceLocation(Reference.MOD_ID, "tin_oreberry_bush").toString());
	public static final ResourceKey<ConfiguredFeature<?, ?>> ALUMINUM_OREBERRY_BUSH_FEATURE = FeatureUtils.createKey(new ResourceLocation(Reference.MOD_ID, "aluminum_oreberry_bush").toString());
	public static final ResourceKey<ConfiguredFeature<?, ?>> LEAD_OREBERRY_BUSH_FEATURE = FeatureUtils.createKey(new ResourceLocation(Reference.MOD_ID, "lead_oreberry_bush").toString());
	public static final ResourceKey<ConfiguredFeature<?, ?>> NICKEL_OREBERRY_BUSH_FEATURE = FeatureUtils.createKey(new ResourceLocation(Reference.MOD_ID, "nickel_oreberry_bush").toString());
	public static final ResourceKey<ConfiguredFeature<?, ?>> URANIUM_OREBERRY_BUSH_FEATURE = FeatureUtils.createKey(new ResourceLocation(Reference.MOD_ID, "uranium_oreberry_bush").toString());
	public static final ResourceKey<ConfiguredFeature<?, ?>> OSMIUM_OREBERRY_BUSH_FEATURE = FeatureUtils.createKey(new ResourceLocation(Reference.MOD_ID, "osmium_oreberry_bush").toString());
	public static final ResourceKey<ConfiguredFeature<?, ?>> ZINC_OREBERRY_BUSH_FEATURE = FeatureUtils.createKey(new ResourceLocation(Reference.MOD_ID, "zinc_oreberry_bush").toString());
	public static final ResourceKey<ConfiguredFeature<?, ?>> SILVER_OREBERRY_BUSH_FEATURE = FeatureUtils.createKey(new ResourceLocation(Reference.MOD_ID, "silver_oreberry_bush").toString());
	public static final ResourceKey<ConfiguredFeature<?, ?>> ESSENCE_BERRY_BUSH_FEATURE = FeatureUtils.createKey(new ResourceLocation(Reference.MOD_ID, "essence_berry_bush").toString());

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		RuleTest ruletest = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);

		FeatureUtils.register(context, IRON_OREBERRY_BUSH_FEATURE,
				OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get(),
				new OreBerryBushFeatureConfig(ruletest, OreBerryRegistry.IRON_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 12));

		FeatureUtils.register(context, GOLD_OREBERRY_BUSH_FEATURE,
				OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get(),
				new OreBerryBushFeatureConfig(ruletest, OreBerryRegistry.GOLD_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 6));

		FeatureUtils.register(context, COPPER_OREBERRY_BUSH_FEATURE,
				OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get(),
				new OreBerryBushFeatureConfig(ruletest, OreBerryRegistry.COPPER_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 12));

		FeatureUtils.register(context, TIN_OREBERRY_BUSH_FEATURE,
				OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get(),
				new OreBerryBushFeatureConfig(ruletest, OreBerryRegistry.TIN_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 12));

		FeatureUtils.register(context, ALUMINUM_OREBERRY_BUSH_FEATURE,
				OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get(),
				new OreBerryBushFeatureConfig(ruletest, OreBerryRegistry.ALUMINUM_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 14));

		FeatureUtils.register(context, LEAD_OREBERRY_BUSH_FEATURE,
				OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get(),
				new OreBerryBushFeatureConfig(ruletest, OreBerryRegistry.LEAD_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 14));

		FeatureUtils.register(context, NICKEL_OREBERRY_BUSH_FEATURE,
				OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get(),
				new OreBerryBushFeatureConfig(ruletest, OreBerryRegistry.NICKEL_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 12));

		FeatureUtils.register(context, URANIUM_OREBERRY_BUSH_FEATURE,
				OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get(),
				new OreBerryBushFeatureConfig(ruletest, OreBerryRegistry.URANIUM_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 14));

		FeatureUtils.register(context, OSMIUM_OREBERRY_BUSH_FEATURE,
				OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get(),
				new OreBerryBushFeatureConfig(ruletest, OreBerryRegistry.OSMIUM_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 13));

		FeatureUtils.register(context, ZINC_OREBERRY_BUSH_FEATURE,
				OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get(),
				new OreBerryBushFeatureConfig(ruletest, OreBerryRegistry.ZINC_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 12));

		FeatureUtils.register(context, SILVER_OREBERRY_BUSH_FEATURE,
				OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get(),
				new OreBerryBushFeatureConfig(ruletest, OreBerryRegistry.SILVER_OREBERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 14));

		FeatureUtils.register(context, ESSENCE_BERRY_BUSH_FEATURE,
				OreBerryRegistry.OREBERRY_FEATURE_CONFIG.get(),
				new OreBerryBushFeatureConfig(ruletest, OreBerryRegistry.ESSENCE_BERRY_BUSH.get().defaultBlockState().setValue(OreBerryBushBlock.AGE, 3), 8));

	}
}
