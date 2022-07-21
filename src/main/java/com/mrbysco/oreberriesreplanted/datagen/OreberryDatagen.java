package com.mrbysco.oreberriesreplanted.datagen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.ACACIA_VAT;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.ACACIA_VAT_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.ALUMINUM_OREBERRY;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.ALUMINUM_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.ALUMINUM_OREBERRY_BUSH_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.ALUMINUM_OREBERRY_JUICE;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.BIRCH_VAT;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.BIRCH_VAT_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.COPPER_NUGGET;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.COPPER_OREBERRY;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.COPPER_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.COPPER_OREBERRY_BUSH_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.COPPER_OREBERRY_JUICE;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.CRIMSON_VAT;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.CRIMSON_VAT_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.DARK_OAK_VAT;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.DARK_OAK_VAT_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.ESSENCE_BERRY;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.ESSENCE_BERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.ESSENCE_BERRY_BUSH_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.GOLD_OREBERRY;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.GOLD_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.GOLD_OREBERRY_BUSH_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.GOLD_OREBERRY_JUICE;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.IRON_OREBERRY;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.IRON_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.IRON_OREBERRY_BUSH_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.IRON_OREBERRY_JUICE;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.JUNGLE_VAT;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.JUNGLE_VAT_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.LEAD_OREBERRY;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.LEAD_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.LEAD_OREBERRY_BUSH_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.LEAD_OREBERRY_JUICE;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.NICKEL_OREBERRY;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.NICKEL_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.NICKEL_OREBERRY_BUSH_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.NICKEL_OREBERRY_JUICE;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.OAK_VAT;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.OAK_VAT_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.OSMIUM_OREBERRY;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.OSMIUM_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.OSMIUM_OREBERRY_BUSH_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.OSMIUM_OREBERRY_JUICE;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.POTTED_ALUMINUM_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.POTTED_COPPER_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.POTTED_ESSENCE_BERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.POTTED_GOLD_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.POTTED_IRON_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.POTTED_LEAD_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.POTTED_NICKEL_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.POTTED_OSMIUM_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.POTTED_SILVER_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.POTTED_TIN_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.POTTED_URANIUM_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.POTTED_ZINC_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.SILVER_OREBERRY;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.SILVER_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.SILVER_OREBERRY_BUSH_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.SILVER_OREBERRY_JUICE;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.SPRUCE_VAT;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.SPRUCE_VAT_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.TIN_OREBERRY;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.TIN_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.TIN_OREBERRY_BUSH_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.TIN_OREBERRY_JUICE;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.URANIUM_OREBERRY;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.URANIUM_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.URANIUM_OREBERRY_BUSH_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.URANIUM_OREBERRY_JUICE;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.WARPED_VAT;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.WARPED_VAT_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.ZINC_OREBERRY;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.ZINC_OREBERRY_BUSH;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.ZINC_OREBERRY_BUSH_ITEM;
import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.ZINC_OREBERRY_JUICE;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class OreberryDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new OreBerryLoot(generator));

			BlockTagsProvider blockTagsProvider = new OreberryBlockTags(generator, helper);
			generator.addProvider(event.includeServer(), blockTagsProvider);
			generator.addProvider(event.includeServer(), new OreberryItemTags(generator, blockTagsProvider, helper));
//			generator.addProvider(new OreBerryRecipes(generator));


			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
					generator, helper, Reference.MOD_ID, ops, Registry.PLACED_FEATURE_REGISTRY, getConfiguredFeatures(ops)));

			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
					generator, helper, Reference.MOD_ID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS, getBiomeModifiers(ops)));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeServer(), new OreBerryLanguage(generator));
			generator.addProvider(event.includeServer(), new OreBerryBlockStates(generator, helper));
			generator.addProvider(event.includeServer(), new OreBerryItemModels(generator, helper));
		}
	}

	public static Map<ResourceLocation, PlacedFeature> getConfiguredFeatures(RegistryOps<JsonElement> ops) {
		Map<ResourceLocation, PlacedFeature> map = Maps.newHashMap();

		map.putAll(generateConfiguredFeature(ops, OreBerryFeatures.IRON_OREBERRY_BUSH_FEATURE.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get(),
				OreBerryFeatures.getPlacedFeature(-64, 64, 5)));

		map.putAll(generateConfiguredFeature(ops, OreBerryFeatures.GOLD_OREBERRY_BUSH_FEATURE.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get(),
				OreBerryFeatures.getPlacedFeature(-64, 32, 8)));

		map.putAll(generateConfiguredFeature(ops, OreBerryFeatures.COPPER_OREBERRY_BUSH_FEATURE.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get(),
				OreBerryFeatures.getPlacedFeature(-44, 60, 3)));

		map.putAll(generateConfiguredFeature(ops, OreBerryFeatures.TIN_OREBERRY_BUSH_FEATURE.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get(),
				OreBerryFeatures.getPlacedFeature(-44, 60, 3)));

		map.putAll(generateConfiguredFeature(ops, OreBerryFeatures.ALUMINUM_OREBERRY_BUSH_FEATURE.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get(),
				OreBerryFeatures.getPlacedFeature(-44, 60, 2)));

		map.putAll(generateConfiguredFeature(ops, OreBerryFeatures.LEAD_OREBERRY_BUSH_FEATURE.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get(),
				OreBerryFeatures.getPlacedFeature(-64, 40, 7)));

		map.putAll(generateConfiguredFeature(ops, OreBerryFeatures.NICKEL_OREBERRY_BUSH_FEATURE.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get(),
				OreBerryFeatures.getPlacedFeature(-64, 120, 5)));

		map.putAll(generateConfiguredFeature(ops, OreBerryFeatures.URANIUM_OREBERRY_BUSH_FEATURE.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get(),
				OreBerryFeatures.getPlacedFeature(-64, 60, 5)));

		map.putAll(generateConfiguredFeature(ops, OreBerryFeatures.OSMIUM_OREBERRY_BUSH_FEATURE.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get(),
				OreBerryFeatures.getPlacedFeature(-64, 60, 6)));

		map.putAll(generateConfiguredFeature(ops, OreBerryFeatures.ZINC_OREBERRY_BUSH_FEATURE.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get(),
				OreBerryFeatures.getPlacedFeature(-64, 70, 6)));

		map.putAll(generateConfiguredFeature(ops, OreBerryFeatures.SILVER_OREBERRY_BUSH_FEATURE.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get(),
				OreBerryFeatures.getPlacedFeature(-64, 40, 7)));

		map.putAll(generateConfiguredFeature(ops, OreBerryFeatures.ESSENCE_BERRY_BUSH_FEATURE.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get(),
				OreBerryFeatures.getPlacedFeature(-64, 32, 6)));

		return map;
	}

	private static Map<ResourceLocation, PlacedFeature> generateConfiguredFeature(RegistryOps<JsonElement> ops, ResourceKey<ConfiguredFeature<?, ?>> featureKey, List<PlacementModifier> modifiers) {
		final Holder<ConfiguredFeature<?, ?>> featureKeyHolder = ops.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(featureKey);
		final PlacedFeature feature = new PlacedFeature(featureKeyHolder, modifiers);
		return Map.of(featureKey.location(), feature);
	}

	public static Map<ResourceLocation, BiomeModifier> getBiomeModifiers(RegistryOps<JsonElement> ops) {
		Map<ResourceLocation, BiomeModifier> map = Maps.newHashMap();

		map.putAll(generateBiomeModifier(ops, OreBerryFeatures.IRON_OREBERRY_BUSH_FEATURE.unwrapKey().get().location(), Decoration.UNDERGROUND_ORES));
		map.putAll(generateBiomeModifier(ops, OreBerryFeatures.GOLD_OREBERRY_BUSH_FEATURE.unwrapKey().get().location(), Decoration.UNDERGROUND_ORES));
		map.putAll(generateBiomeModifier(ops, OreBerryFeatures.COPPER_OREBERRY_BUSH_FEATURE.unwrapKey().get().location(), Decoration.UNDERGROUND_ORES));
		map.putAll(generateBiomeModifier(ops, OreBerryFeatures.TIN_OREBERRY_BUSH_FEATURE.unwrapKey().get().location(), Decoration.UNDERGROUND_ORES));
		map.putAll(generateBiomeModifier(ops, OreBerryFeatures.ALUMINUM_OREBERRY_BUSH_FEATURE.unwrapKey().get().location(), Decoration.UNDERGROUND_ORES));
		map.putAll(generateBiomeModifier(ops, OreBerryFeatures.LEAD_OREBERRY_BUSH_FEATURE.unwrapKey().get().location(), Decoration.UNDERGROUND_ORES));
		map.putAll(generateBiomeModifier(ops, OreBerryFeatures.NICKEL_OREBERRY_BUSH_FEATURE.unwrapKey().get().location(), Decoration.UNDERGROUND_ORES));
		map.putAll(generateBiomeModifier(ops, OreBerryFeatures.URANIUM_OREBERRY_BUSH_FEATURE.unwrapKey().get().location(), Decoration.UNDERGROUND_ORES));
		map.putAll(generateBiomeModifier(ops, OreBerryFeatures.OSMIUM_OREBERRY_BUSH_FEATURE.unwrapKey().get().location(), Decoration.UNDERGROUND_ORES));
		map.putAll(generateBiomeModifier(ops, OreBerryFeatures.ZINC_OREBERRY_BUSH_FEATURE.unwrapKey().get().location(), Decoration.UNDERGROUND_ORES));
		map.putAll(generateBiomeModifier(ops, OreBerryFeatures.SILVER_OREBERRY_BUSH_FEATURE.unwrapKey().get().location(), Decoration.UNDERGROUND_ORES));
		map.putAll(generateBiomeModifier(ops, OreBerryFeatures.ESSENCE_BERRY_BUSH_FEATURE.unwrapKey().get().location(), Decoration.UNDERGROUND_ORES));

		return map;
	}

	private static Map<ResourceLocation, BiomeModifier> generateBiomeModifier(RegistryOps<JsonElement> ops, ResourceLocation location, Decoration decorationType) {
		HolderSet.Named<Biome> IS_OVERWORLD = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).orElseThrow(), BiomeTags.IS_OVERWORLD);
		final BiomeModifier addFeature = new AddFeaturesBiomeModifier(IS_OVERWORLD, HolderSet.direct(ops.registry(Registry.PLACED_FEATURE_REGISTRY).get()
				.getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, location))), decorationType);
		return Map.of(location, addFeature);
	}

	private static class OreBerryLoot extends LootTableProvider {
		public OreBerryLoot(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
			return ImmutableList.of(Pair.of(OreBerryBlocks::new, LootContextParamSets.BLOCK));
		}

		private static class OreBerryBlocks extends BlockLoot {

			@Override
			protected void addTables() {
				this.dropSelf(IRON_OREBERRY_BUSH.get());
				this.dropSelf(GOLD_OREBERRY_BUSH.get());
				this.dropSelf(COPPER_OREBERRY_BUSH.get());
				this.dropSelf(TIN_OREBERRY_BUSH.get());
				this.dropSelf(ALUMINUM_OREBERRY_BUSH.get());
				this.dropSelf(LEAD_OREBERRY_BUSH.get());
				this.dropSelf(NICKEL_OREBERRY_BUSH.get());
				this.dropSelf(URANIUM_OREBERRY_BUSH.get());
				this.dropSelf(OSMIUM_OREBERRY_BUSH.get());
				this.dropSelf(ZINC_OREBERRY_BUSH.get());
				this.dropSelf(SILVER_OREBERRY_BUSH.get());
				this.dropSelf(ESSENCE_BERRY_BUSH.get());

				this.dropPottedContents(POTTED_IRON_OREBERRY_BUSH.get());
				this.dropPottedContents(POTTED_GOLD_OREBERRY_BUSH.get());
				this.dropPottedContents(POTTED_COPPER_OREBERRY_BUSH.get());
				this.dropPottedContents(POTTED_TIN_OREBERRY_BUSH.get());
				this.dropPottedContents(POTTED_ALUMINUM_OREBERRY_BUSH.get());
				this.dropPottedContents(POTTED_LEAD_OREBERRY_BUSH.get());
				this.dropPottedContents(POTTED_NICKEL_OREBERRY_BUSH.get());
				this.dropPottedContents(POTTED_URANIUM_OREBERRY_BUSH.get());
				this.dropPottedContents(POTTED_OSMIUM_OREBERRY_BUSH.get());
				this.dropPottedContents(POTTED_ZINC_OREBERRY_BUSH.get());
				this.dropPottedContents(POTTED_SILVER_OREBERRY_BUSH.get());
				this.dropPottedContents(POTTED_ESSENCE_BERRY_BUSH.get());

				this.dropSelf(OAK_VAT.get());
				this.dropSelf(SPRUCE_VAT.get());
				this.dropSelf(BIRCH_VAT.get());
				this.dropSelf(JUNGLE_VAT.get());
				this.dropSelf(ACACIA_VAT.get());
				this.dropSelf(DARK_OAK_VAT.get());
				this.dropSelf(CRIMSON_VAT.get());
				this.dropSelf(WARPED_VAT.get());
			}

			@Override
			protected Iterable<Block> getKnownBlocks() {
				return (Iterable<Block>) OreBerryRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
			}
		}

		@Override
		protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationContext validationContext) {
			map.forEach((name, table) -> LootTables.validate(validationContext, name, table));
		}
	}

	private static class OreBerryLanguage extends LanguageProvider {
		public OreBerryLanguage(DataGenerator gen) {
			super(gen, Reference.MOD_ID, "en_us");
		}

		@Override
		protected void addTranslations() {
			//Tab
			add("itemGroup.oreberriesreplanted", "Oreberries Replanted");

			//Blocks
			addBlock(IRON_OREBERRY_BUSH, "Iron Oreberry Bush");
			addBlock(GOLD_OREBERRY_BUSH, "Gold Oreberry Bush");
			addBlock(COPPER_OREBERRY_BUSH, "Copper Oreberry Bush");
			addBlock(TIN_OREBERRY_BUSH, "Tin Oreberry Bush");
			addBlock(ALUMINUM_OREBERRY_BUSH, "Aluminum Oreberry Bush");
			addBlock(LEAD_OREBERRY_BUSH, "Lead Oreberry Bush");
			addBlock(NICKEL_OREBERRY_BUSH, "Nickel Oreberry Bush");
			addBlock(URANIUM_OREBERRY_BUSH, "Uranium Oreberry Bush");
			addBlock(OSMIUM_OREBERRY_BUSH, "Osmium Oreberry Bush");
			addBlock(ZINC_OREBERRY_BUSH, "Zinc Oreberry Bush");
			addBlock(SILVER_OREBERRY_BUSH, "Silver Oreberry Bush");
			addBlock(ESSENCE_BERRY_BUSH, "Essence Berry Bush");

			addBlock(POTTED_IRON_OREBERRY_BUSH, "Potted Iron Oreberry Bush");
			addBlock(POTTED_GOLD_OREBERRY_BUSH, "Potted Gold Oreberry Bush");
			addBlock(POTTED_COPPER_OREBERRY_BUSH, "Potted Copper Oreberry Bush");
			addBlock(POTTED_TIN_OREBERRY_BUSH, "Potted Tin Oreberry Bush");
			addBlock(POTTED_ALUMINUM_OREBERRY_BUSH, "Potted Aluminum Oreberry Bush");
			addBlock(POTTED_LEAD_OREBERRY_BUSH, "Potted Lead Oreberry Bush");
			addBlock(POTTED_NICKEL_OREBERRY_BUSH, "Potted Nickel Oreberry Bush");
			addBlock(POTTED_URANIUM_OREBERRY_BUSH, "Potted Uranium Oreberry Bush");
			addBlock(POTTED_OSMIUM_OREBERRY_BUSH, "Potted Osmium Oreberry Bush");
			addBlock(POTTED_ZINC_OREBERRY_BUSH, "Potted Zinc Oreberry Bush");
			addBlock(POTTED_SILVER_OREBERRY_BUSH, "Potted Silver Oreberry Bush");
			addBlock(POTTED_ESSENCE_BERRY_BUSH, "Potted Essence Berry Bush");

			addBlock(OAK_VAT, "Oak Vat");
			addBlock(SPRUCE_VAT, "Spruce Vat");
			addBlock(BIRCH_VAT, "Birch Vat");
			addBlock(JUNGLE_VAT, "Jungle Vat");
			addBlock(ACACIA_VAT, "Acacia Vat");
			addBlock(DARK_OAK_VAT, "Oak Vat");
			addBlock(CRIMSON_VAT, "Crimson Vat");
			addBlock(WARPED_VAT, "Warped Vat");

			//Items
			addItem(IRON_OREBERRY, "Iron Oreberry");
			addItem(GOLD_OREBERRY, "Gold Oreberry");
			addItem(COPPER_OREBERRY, "Copper Oreberry");
			addItem(TIN_OREBERRY, "Tin Oreberry");
			addItem(ALUMINUM_OREBERRY, "Aluminum Oreberry");
			addItem(LEAD_OREBERRY, "Lead Oreberry");
			addItem(NICKEL_OREBERRY, "Nickel Oreberry");
			addItem(URANIUM_OREBERRY, "Uranium Oreberry");
			addItem(OSMIUM_OREBERRY, "Osmium Oreberry");
			addItem(ZINC_OREBERRY, "Zinc Oreberry");
			addItem(SILVER_OREBERRY, "Silver Oreberry");
			addItem(ESSENCE_BERRY, "Concentrated Essence Berry");

			addItem(IRON_OREBERRY_JUICE.getBucket(), "Iron Oreberry Juice Bucket");
			addItem(GOLD_OREBERRY_JUICE.getBucket(), "Gold Oreberry Juice Bucket");
			addItem(COPPER_OREBERRY_JUICE.getBucket(), "Copper Oreberry Juice Bucket");
			addItem(TIN_OREBERRY_JUICE.getBucket(), "Tin Oreberry Juice Bucket");
			addItem(ALUMINUM_OREBERRY_JUICE.getBucket(), "Aluminum Oreberry Juice Bucket");
			addItem(LEAD_OREBERRY_JUICE.getBucket(), "Lead Oreberry Juice Bucket");
			addItem(NICKEL_OREBERRY_JUICE.getBucket(), "Nickel Oreberry Juice Bucket");
			addItem(URANIUM_OREBERRY_JUICE.getBucket(), "Uranium Oreberry Juice Bucket");
			addItem(OSMIUM_OREBERRY_JUICE.getBucket(), "Osmium Oreberry Juice Bucket");
			addItem(ZINC_OREBERRY_JUICE.getBucket(), "Zinc Oreberry Juice Bucket");
			addItem(SILVER_OREBERRY_JUICE.getBucket(), "Silver Oreberry Juice Bucket");

			addItem(COPPER_NUGGET, "Copper Nugget");

			//Fluids
			addFluid(IRON_OREBERRY_JUICE.getSource(), "Iron Oreberry Juice");
			addFluid(GOLD_OREBERRY_JUICE.getSource(), "Gold Oreberry Juice");
			addFluid(COPPER_OREBERRY_JUICE.getSource(), "Copper Oreberry Juice");
			addFluid(TIN_OREBERRY_JUICE.getSource(), "Tin Oreberry Juice");
			addFluid(ALUMINUM_OREBERRY_JUICE.getSource(), "Aluminum Oreberry Juice");
			addFluid(LEAD_OREBERRY_JUICE.getSource(), "Lead Oreberry Juice");
			addFluid(NICKEL_OREBERRY_JUICE.getSource(), "Nickel Oreberry Juice");
			addFluid(URANIUM_OREBERRY_JUICE.getSource(), "Uranium Oreberry Juice");
			addFluid(OSMIUM_OREBERRY_JUICE.getSource(), "Osmium Oreberry Juice");
			addFluid(ZINC_OREBERRY_JUICE.getSource(), "Zinc Oreberry Juice");
			addFluid(SILVER_OREBERRY_JUICE.getSource(), "Silver Oreberry Juice");

			//Tooltips
			add("oreberriesreplanted.iron_oreberry.tooltip", "Sweet Irony");
			add("oreberriesreplanted.gold_oreberry.tooltip", "To barter for");
			add("oreberriesreplanted.copper_oreberry.tooltip", "Tastes like metal");
			add("oreberriesreplanted.tin_oreberry.tooltip", "Tin Man");
			add("oreberriesreplanted.aluminum_oreberry.tooltip", "White Chocolate");
			add("oreberriesreplanted.silver_oreberry.tooltip", "Now with less werewolves");
			add("oreberriesreplanted.lead_oreberry.tooltip", "Would not eat, may be poisonous");
			add("oreberriesreplanted.nickel_oreberry.tooltip", "If I had a nickel for every one I found");
			add("oreberriesreplanted.uranium_oreberry.tooltip", "RADIOACTIVE, RADIOACTIVE!");
			add("oreberriesreplanted.osmium_oreberry.tooltip", "I am blue Da ba dee da ba di");
			add("oreberriesreplanted.zinc_oreberry.tooltip", "Let that one Zinc in");
			add("oreberriesreplanted.essence_berry.tooltip", "Tastes like Creeper");

			//Compat
			add("oreberriesreplanted.gui.jei.category.vat", "Vat Crushing");
			add("oreberriesreplanted.gui.jei.category.vat.tooltip", "Produces approximately %s to %smb worth of %s");
			add("oreberriesreplanted.gui.jei.category.vat_output.tooltip", "Produces 1 nugget per %smb");
		}

		private void addFluid(RegistryObject<ForgeFlowingFluid> fluid, String name) {
			ResourceLocation id = fluid.getId();
			this.add("fluid." + id.getNamespace() + "." + id.getPath(), name);
		}
	}

	private static class OreBerryItemModels extends ItemModelProvider {
		public OreBerryItemModels(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, Reference.MOD_ID, helper);
		}

		@Override
		protected void registerModels() {
			withParent(IRON_OREBERRY_BUSH_ITEM.get(), modLoc("block/iron_oreberry_bush_stage1"));
			withParent(GOLD_OREBERRY_BUSH_ITEM.get(), modLoc("block/gold_oreberry_bush_stage1"));
			withParent(COPPER_OREBERRY_BUSH_ITEM.get(), modLoc("block/copper_oreberry_bush_stage1"));
			withParent(TIN_OREBERRY_BUSH_ITEM.get(), modLoc("block/tin_oreberry_bush_stage1"));
			withParent(ALUMINUM_OREBERRY_BUSH_ITEM.get(), modLoc("block/aluminum_oreberry_bush_stage1"));
			withParent(LEAD_OREBERRY_BUSH_ITEM.get(), modLoc("block/lead_oreberry_bush_stage1"));
			withParent(NICKEL_OREBERRY_BUSH_ITEM.get(), modLoc("block/nickel_oreberry_bush_stage1"));
			withParent(URANIUM_OREBERRY_BUSH_ITEM.get(), modLoc("block/uranium_oreberry_bush_stage1"));
			withParent(OSMIUM_OREBERRY_BUSH_ITEM.get(), modLoc("block/osmium_oreberry_bush_stage1"));
			withParent(ZINC_OREBERRY_BUSH_ITEM.get(), modLoc("block/zinc_oreberry_bush_stage1"));
			withParent(SILVER_OREBERRY_BUSH_ITEM.get(), modLoc("block/silver_oreberry_bush_stage1"));
			withParent(ESSENCE_BERRY_BUSH_ITEM.get(), modLoc("block/essence_berry_bush_stage1"));

			withParent(OAK_VAT_ITEM.get(), modLoc("block/" + OAK_VAT.getId().getPath()));
			withParent(SPRUCE_VAT_ITEM.get(), modLoc("block/" + SPRUCE_VAT.getId().getPath()));
			withParent(BIRCH_VAT_ITEM.get(), modLoc("block/" + BIRCH_VAT.getId().getPath()));
			withParent(JUNGLE_VAT_ITEM.get(), modLoc("block/" + JUNGLE_VAT.getId().getPath()));
			withParent(ACACIA_VAT_ITEM.get(), modLoc("block/" + ACACIA_VAT.getId().getPath()));
			withParent(DARK_OAK_VAT_ITEM.get(), modLoc("block/" + DARK_OAK_VAT.getId().getPath()));
			withParent(CRIMSON_VAT_ITEM.get(), modLoc("block/" + CRIMSON_VAT.getId().getPath()));
			withParent(WARPED_VAT_ITEM.get(), modLoc("block/" + WARPED_VAT.getId().getPath()));

			singleTexture(COPPER_NUGGET.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/copper_nugget"));

			singleTexture(IRON_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/iron_oreberry"));
			singleTexture(GOLD_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/gold_oreberry"));
			singleTexture(COPPER_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/copper_oreberry"));
			singleTexture(TIN_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/tin_oreberry"));
			singleTexture(ALUMINUM_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/aluminum_oreberry"));
			singleTexture(LEAD_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/lead_oreberry"));
			singleTexture(NICKEL_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/nickel_oreberry"));
			singleTexture(URANIUM_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/uranium_oreberry"));
			singleTexture(OSMIUM_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/osmium_oreberry"));
			singleTexture(ZINC_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/zinc_oreberry"));
			singleTexture(SILVER_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/silver_oreberry"));
			singleTexture(ESSENCE_BERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/essence_berry"));
		}

		private void withParent(Item item, ResourceLocation parent) {
			withExistingParent(ForgeRegistries.ITEMS.getKey(item).getPath(), parent);
		}
	}

	private static class OreBerryBlockStates extends BlockStateProvider {

		public OreBerryBlockStates(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, Reference.MOD_ID, helper);
		}

		@Override
		protected void registerStatesAndModels() {
			makeBush(IRON_OREBERRY_BUSH.get(), "iron");
			makeBush(GOLD_OREBERRY_BUSH.get(), "gold");
			makeBush(COPPER_OREBERRY_BUSH.get(), "copper");
			makeBush(TIN_OREBERRY_BUSH.get(), "tin");
			makeBush(ALUMINUM_OREBERRY_BUSH.get(), "aluminum");
			makeBush(LEAD_OREBERRY_BUSH.get(), "lead");
			makeBush(NICKEL_OREBERRY_BUSH.get(), "nickel");
			makeBush(URANIUM_OREBERRY_BUSH.get(), "uranium");
			makeBush(OSMIUM_OREBERRY_BUSH.get(), "osmium");
			makeBush(ZINC_OREBERRY_BUSH.get(), "zinc");
			makeBush(SILVER_OREBERRY_BUSH.get(), "silver");
			makeBush(ESSENCE_BERRY_BUSH.get(), "essence");

			makePottedBush(POTTED_IRON_OREBERRY_BUSH.get(), "iron");
			makePottedBush(POTTED_GOLD_OREBERRY_BUSH.get(), "gold");
			makePottedBush(POTTED_COPPER_OREBERRY_BUSH.get(), "copper");
			makePottedBush(POTTED_TIN_OREBERRY_BUSH.get(), "tin");
			makePottedBush(POTTED_ALUMINUM_OREBERRY_BUSH.get(), "aluminum");
			makePottedBush(POTTED_LEAD_OREBERRY_BUSH.get(), "lead");
			makePottedBush(POTTED_NICKEL_OREBERRY_BUSH.get(), "nickel");
			makePottedBush(POTTED_URANIUM_OREBERRY_BUSH.get(), "uranium");
			makePottedBush(POTTED_OSMIUM_OREBERRY_BUSH.get(), "osmium");
			makePottedBush(POTTED_ZINC_OREBERRY_BUSH.get(), "zinc");
			makePottedBush(POTTED_SILVER_OREBERRY_BUSH.get(), "silver");
			makePottedBush(POTTED_ESSENCE_BERRY_BUSH.get(), "essence");

			makeVat(OAK_VAT.get(), mcLoc("block/oak_planks"));
			makeVat(SPRUCE_VAT.get(), mcLoc("block/spruce_planks"));
			makeVat(BIRCH_VAT.get(), mcLoc("block/birch_planks"));
			makeVat(JUNGLE_VAT.get(), mcLoc("block/jungle_planks"));
			makeVat(ACACIA_VAT.get(), mcLoc("block/acacia_planks"));
			makeVat(DARK_OAK_VAT.get(), mcLoc("block/dark_oak_planks"));
			makeVat(CRIMSON_VAT.get(), mcLoc("block/crimson_planks"));
			makeVat(WARPED_VAT.get(), mcLoc("block/warped_planks"));
		}

		private void makeBush(Block block, String type) {
			ModelFile age0 = models().getBuilder(ForgeRegistries.BLOCKS.getKey(block).getPath() + "_stage0")
					.parent(models().getExistingFile(modLoc("block/base/oreberry_stage0")))
					.texture("all", "block/" + type + "_oreberry").renderType("cutout_mipped");
			ModelFile age1 = models().getBuilder(ForgeRegistries.BLOCKS.getKey(block).getPath() + "_stage1")
					.parent(models().getExistingFile(modLoc("block/base/oreberry_stage1")))
					.texture("all", "block/" + type + "_oreberry").renderType("cutout_mipped");
			ModelFile age2 = models().getBuilder(ForgeRegistries.BLOCKS.getKey(block).getPath() + "_stage2")
					.parent(models().getExistingFile(modLoc("block/base/oreberry_stage2")))
					.texture("all", "block/" + type + "_oreberry").renderType("cutout_mipped");
			ModelFile age3 = models().getBuilder(ForgeRegistries.BLOCKS.getKey(block).getPath() + "_stage3")
					.parent(models().getExistingFile(modLoc("block/base/oreberry_stage2")))
					.texture("all", "block/" + type + "_oreberry_ripe").renderType("cutout_mipped");

			getVariantBuilder(block)
					.partialState().with(BlockStateProperties.AGE_3, 0)
					.modelForState().modelFile(age0).addModel()
					.partialState().with(BlockStateProperties.AGE_3, 1)
					.modelForState().modelFile(age1).addModel()
					.partialState().with(BlockStateProperties.AGE_3, 2)
					.modelForState().modelFile(age2).addModel()
					.partialState().with(BlockStateProperties.AGE_3, 3)
					.modelForState().modelFile(age3).addModel();
		}

		private void makePottedBush(Block block, String type) {
			ModelFile model = models().getBuilder(ForgeRegistries.BLOCKS.getKey(block).getPath())
					.parent(models().getExistingFile(modLoc("block/base/flower_pot_bush")))
					.texture("bush", "block/" + type + "_oreberry_ripe").renderType("cutout_mipped");

			getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
		}

		private void makeVat(Block block, ResourceLocation planks) {
			ModelFile model = models().getBuilder(ForgeRegistries.BLOCKS.getKey(block).getPath())
					.parent(models().getExistingFile(modLoc("block/vat/vat_base")))
					.texture("0", planks);

			getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
		}
	}


	public static class OreberryBlockTags extends BlockTagsProvider {
		public OreberryBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
			super(generator, Reference.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			this.tag(BlockTags.MINEABLE_WITH_AXE).add(OAK_VAT.get(), SPRUCE_VAT.get(), BIRCH_VAT.get(),
					JUNGLE_VAT.get(), ACACIA_VAT.get(), DARK_OAK_VAT.get(), CRIMSON_VAT.get(), WARPED_VAT.get());
		}
	}

	public static class OreberryItemTags extends ItemTagsProvider {

		public OreberryItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
			super(dataGenerator, blockTagsProvider, Reference.MOD_ID, existingFileHelper);
		}

		public static final TagKey<Item> NUGGETS_COPPER = forgeTag("nuggets/copper");

		private static TagKey<Item> forgeTag(String name) {
			return ItemTags.create(new ResourceLocation("forge", name));
		}

		@Override
		protected void addTags() {
			this.tag(NUGGETS_COPPER).add(COPPER_NUGGET.get());
			this.tag(Items.NUGGETS).addTag(NUGGETS_COPPER);
		}
	}
}