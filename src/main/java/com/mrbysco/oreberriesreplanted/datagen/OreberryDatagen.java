package com.mrbysco.oreberriesreplanted.datagen;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.block.OreBerryBushBlock;
import com.mrbysco.oreberriesreplanted.block.VatBlock;
import com.mrbysco.oreberriesreplanted.datagen.builder.TagSmeltingRecipeBuilder;
import com.mrbysco.oreberriesreplanted.datagen.builder.VatRecipeBuilder;
import com.mrbysco.oreberriesreplanted.registry.LiquidReg;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryFeatures;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryPlacements;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.model.item.DynamicFluidContainerModel;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class OreberryDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new OreBerryLoot(packOutput, lookupProvider));

		BlockTagsProvider blockTagsProvider = new OreberryBlockTags(packOutput, lookupProvider);
		generator.addProvider(true, blockTagsProvider);
		generator.addProvider(true, new OreberryItemTags(packOutput, lookupProvider, blockTagsProvider));
		generator.addProvider(true, new OreberryRecipeProvider.Runner(packOutput, lookupProvider));

		generator.addProvider(true, new OreBerryDatapack(packOutput, event.getLookupProvider(), Set.of(Reference.MOD_ID)));

		generator.addProvider(true, new OreBerryLanguage(packOutput));
		generator.addProvider(true, new OreBerryModels(packOutput));
	}

	private static class OreBerryDatapack extends DatapackBuiltinEntriesProvider {
		public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
				.add(Registries.CONFIGURED_FEATURE, OreBerryFeatures::bootstrap)
				.add(Registries.PLACED_FEATURE, OreBerryPlacements::bootstrap)
				.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, OreBerryBiomeModifiers::bootstrap);

		public OreBerryDatapack(PackOutput output, CompletableFuture<Provider> registries, Set<String> modIds) {
			super(output, registries, BUILDER, modIds);
		}
	}

	private static class OreBerryLoot extends LootTableProvider {
		public OreBerryLoot(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(packOutput, Set.of(), List.of(new SubProviderEntry(OreBerryBlocks::new, LootContextParamSets.BLOCK)), lookupProvider);
		}

		private static class OreBerryBlocks extends BlockLootSubProvider {

			protected OreBerryBlocks(HolderLookup.Provider provider) {
				super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
			}

			@Override
			protected void generate() {
				this.dropSelf(OreBerryRegistry.IRON_OREBERRY_BUSH.get());
				this.dropSelf(OreBerryRegistry.GOLD_OREBERRY_BUSH.get());
				this.dropSelf(OreBerryRegistry.COPPER_OREBERRY_BUSH.get());
				this.dropSelf(OreBerryRegistry.TIN_OREBERRY_BUSH.get());
				this.dropSelf(OreBerryRegistry.ALUMINUM_OREBERRY_BUSH.get());
				this.dropSelf(OreBerryRegistry.LEAD_OREBERRY_BUSH.get());
				this.dropSelf(OreBerryRegistry.NICKEL_OREBERRY_BUSH.get());
				this.dropSelf(OreBerryRegistry.URANIUM_OREBERRY_BUSH.get());
				this.dropSelf(OreBerryRegistry.OSMIUM_OREBERRY_BUSH.get());
				this.dropSelf(OreBerryRegistry.ZINC_OREBERRY_BUSH.get());
				this.dropSelf(OreBerryRegistry.SILVER_OREBERRY_BUSH.get());
				this.dropSelf(OreBerryRegistry.ESSENCE_BERRY_BUSH.get());

				this.dropPottedContents(OreBerryRegistry.POTTED_IRON_OREBERRY_BUSH.get());
				this.dropPottedContents(OreBerryRegistry.POTTED_GOLD_OREBERRY_BUSH.get());
				this.dropPottedContents(OreBerryRegistry.POTTED_COPPER_OREBERRY_BUSH.get());
				this.dropPottedContents(OreBerryRegistry.POTTED_TIN_OREBERRY_BUSH.get());
				this.dropPottedContents(OreBerryRegistry.POTTED_ALUMINUM_OREBERRY_BUSH.get());
				this.dropPottedContents(OreBerryRegistry.POTTED_LEAD_OREBERRY_BUSH.get());
				this.dropPottedContents(OreBerryRegistry.POTTED_NICKEL_OREBERRY_BUSH.get());
				this.dropPottedContents(OreBerryRegistry.POTTED_URANIUM_OREBERRY_BUSH.get());
				this.dropPottedContents(OreBerryRegistry.POTTED_OSMIUM_OREBERRY_BUSH.get());
				this.dropPottedContents(OreBerryRegistry.POTTED_ZINC_OREBERRY_BUSH.get());
				this.dropPottedContents(OreBerryRegistry.POTTED_SILVER_OREBERRY_BUSH.get());
				this.dropPottedContents(OreBerryRegistry.POTTED_ESSENCE_BERRY_BUSH.get());

				this.dropSelf(OreBerryRegistry.OAK_VAT.get());
				this.dropSelf(OreBerryRegistry.SPRUCE_VAT.get());
				this.dropSelf(OreBerryRegistry.BIRCH_VAT.get());
				this.dropSelf(OreBerryRegistry.JUNGLE_VAT.get());
				this.dropSelf(OreBerryRegistry.ACACIA_VAT.get());
				this.dropSelf(OreBerryRegistry.DARK_OAK_VAT.get());
				this.dropSelf(OreBerryRegistry.MANGROVE_VAT.get());
				this.dropSelf(OreBerryRegistry.CHERRY_VAT.get());
				this.dropSelf(OreBerryRegistry.CRIMSON_VAT.get());
				this.dropSelf(OreBerryRegistry.WARPED_VAT.get());
			}

			@NotNull
			@Override
			protected Iterable<Block> getKnownBlocks() {
				return (Iterable<Block>) OreBerryRegistry.BLOCKS.getEntries().stream().map(holder -> (Block) holder.get())::iterator;
			}
		}

		@Override
		protected void validate(@NotNull WritableRegistry<LootTable> writableregistry,
		                        @NotNull ValidationContext validationcontext,
		                        @NotNull ProblemReporter.Collector problemreporter$collector) {
			super.validate(writableregistry, validationcontext, problemreporter$collector);
		}
	}

	private static class OreBerryLanguage extends LanguageProvider {
		public OreBerryLanguage(PackOutput packOutput) {
			super(packOutput, Reference.MOD_ID, "en_us");
		}

		@Override
		protected void addTranslations() {
			//Tab
			add("itemGroup.oreberriesreplanted.tab", "Oreberries Replanted");

			//Blocks
			addBlock(OreBerryRegistry.POTTED_IRON_OREBERRY_BUSH, "Potted Iron Oreberry Bush");
			addBlock(OreBerryRegistry.POTTED_GOLD_OREBERRY_BUSH, "Potted Gold Oreberry Bush");
			addBlock(OreBerryRegistry.POTTED_COPPER_OREBERRY_BUSH, "Potted Copper Oreberry Bush");
			addBlock(OreBerryRegistry.POTTED_TIN_OREBERRY_BUSH, "Potted Tin Oreberry Bush");
			addBlock(OreBerryRegistry.POTTED_ALUMINUM_OREBERRY_BUSH, "Potted Aluminum Oreberry Bush");
			addBlock(OreBerryRegistry.POTTED_LEAD_OREBERRY_BUSH, "Potted Lead Oreberry Bush");
			addBlock(OreBerryRegistry.POTTED_NICKEL_OREBERRY_BUSH, "Potted Nickel Oreberry Bush");
			addBlock(OreBerryRegistry.POTTED_URANIUM_OREBERRY_BUSH, "Potted Uranium Oreberry Bush");
			addBlock(OreBerryRegistry.POTTED_OSMIUM_OREBERRY_BUSH, "Potted Osmium Oreberry Bush");
			addBlock(OreBerryRegistry.POTTED_ZINC_OREBERRY_BUSH, "Potted Zinc Oreberry Bush");
			addBlock(OreBerryRegistry.POTTED_SILVER_OREBERRY_BUSH, "Potted Silver Oreberry Bush");
			addBlock(OreBerryRegistry.POTTED_ESSENCE_BERRY_BUSH, "Potted Essence Berry Bush");

			addBlock(OreBerryRegistry.OAK_VAT, "Oak Vat");
			addBlock(OreBerryRegistry.SPRUCE_VAT, "Spruce Vat");
			addBlock(OreBerryRegistry.BIRCH_VAT, "Birch Vat");
			addBlock(OreBerryRegistry.JUNGLE_VAT, "Jungle Vat");
			addBlock(OreBerryRegistry.ACACIA_VAT, "Acacia Vat");
			addBlock(OreBerryRegistry.DARK_OAK_VAT, "Dark Oak Vat");
			addBlock(OreBerryRegistry.MANGROVE_VAT, "Mangrove Vat");
			addBlock(OreBerryRegistry.CHERRY_VAT, "Cherry Vat");
			addBlock(OreBerryRegistry.CRIMSON_VAT, "Crimson Vat");
			addBlock(OreBerryRegistry.WARPED_VAT, "Warped Vat");

			//Items
			addItem(OreBerryRegistry.IRON_OREBERRY_BUSH_ITEM, "Iron Oreberry Bush");
			addItem(OreBerryRegistry.GOLD_OREBERRY_BUSH_ITEM, "Gold Oreberry Bush");
			addItem(OreBerryRegistry.COPPER_OREBERRY_BUSH_ITEM, "Copper Oreberry Bush");
			addItem(OreBerryRegistry.TIN_OREBERRY_BUSH_ITEM, "Tin Oreberry Bush");
			addItem(OreBerryRegistry.ALUMINUM_OREBERRY_BUSH_ITEM, "Aluminum Oreberry Bush");
			addItem(OreBerryRegistry.LEAD_OREBERRY_BUSH_ITEM, "Lead Oreberry Bush");
			addItem(OreBerryRegistry.NICKEL_OREBERRY_BUSH_ITEM, "Nickel Oreberry Bush");
			addItem(OreBerryRegistry.URANIUM_OREBERRY_BUSH_ITEM, "Uranium Oreberry Bush");
			addItem(OreBerryRegistry.OSMIUM_OREBERRY_BUSH_ITEM, "Osmium Oreberry Bush");
			addItem(OreBerryRegistry.ZINC_OREBERRY_BUSH_ITEM, "Zinc Oreberry Bush");
			addItem(OreBerryRegistry.SILVER_OREBERRY_BUSH_ITEM, "Silver Oreberry Bush");
			addItem(OreBerryRegistry.ESSENCE_BERRY_BUSH_ITEM, "Essence Berry Bush");

			addItem(OreBerryRegistry.IRON_OREBERRY, "Iron Oreberry");
			addItem(OreBerryRegistry.GOLD_OREBERRY, "Gold Oreberry");
			addItem(OreBerryRegistry.COPPER_OREBERRY, "Copper Oreberry");
			addItem(OreBerryRegistry.TIN_OREBERRY, "Tin Oreberry");
			addItem(OreBerryRegistry.ALUMINUM_OREBERRY, "Aluminum Oreberry");
			addItem(OreBerryRegistry.LEAD_OREBERRY, "Lead Oreberry");
			addItem(OreBerryRegistry.NICKEL_OREBERRY, "Nickel Oreberry");
			addItem(OreBerryRegistry.URANIUM_OREBERRY, "Uranium Oreberry");
			addItem(OreBerryRegistry.OSMIUM_OREBERRY, "Osmium Oreberry");
			addItem(OreBerryRegistry.ZINC_OREBERRY, "Zinc Oreberry");
			addItem(OreBerryRegistry.SILVER_OREBERRY, "Silver Oreberry");
			addItem(OreBerryRegistry.ESSENCE_BERRY, "Concentrated Essence Berry");

			addItem(OreBerryRegistry.IRON_OREBERRY_JUICE.getBucket(), "Iron Oreberry Juice Bucket");
			addItem(OreBerryRegistry.GOLD_OREBERRY_JUICE.getBucket(), "Gold Oreberry Juice Bucket");
			addItem(OreBerryRegistry.COPPER_OREBERRY_JUICE.getBucket(), "Copper Oreberry Juice Bucket");
			addItem(OreBerryRegistry.TIN_OREBERRY_JUICE.getBucket(), "Tin Oreberry Juice Bucket");
			addItem(OreBerryRegistry.ALUMINUM_OREBERRY_JUICE.getBucket(), "Aluminum Oreberry Juice Bucket");
			addItem(OreBerryRegistry.LEAD_OREBERRY_JUICE.getBucket(), "Lead Oreberry Juice Bucket");
			addItem(OreBerryRegistry.NICKEL_OREBERRY_JUICE.getBucket(), "Nickel Oreberry Juice Bucket");
			addItem(OreBerryRegistry.URANIUM_OREBERRY_JUICE.getBucket(), "Uranium Oreberry Juice Bucket");
			addItem(OreBerryRegistry.OSMIUM_OREBERRY_JUICE.getBucket(), "Osmium Oreberry Juice Bucket");
			addItem(OreBerryRegistry.ZINC_OREBERRY_JUICE.getBucket(), "Zinc Oreberry Juice Bucket");
			addItem(OreBerryRegistry.SILVER_OREBERRY_JUICE.getBucket(), "Silver Oreberry Juice Bucket");

			addItem(OreBerryRegistry.COPPER_NUGGET, "Copper Nugget");

			//Fluids
			addFluid(OreBerryRegistry.IRON_OREBERRY_JUICE.getSource(), "Iron Oreberry Juice");
			addFluid(OreBerryRegistry.GOLD_OREBERRY_JUICE.getSource(), "Gold Oreberry Juice");
			addFluid(OreBerryRegistry.COPPER_OREBERRY_JUICE.getSource(), "Copper Oreberry Juice");
			addFluid(OreBerryRegistry.TIN_OREBERRY_JUICE.getSource(), "Tin Oreberry Juice");
			addFluid(OreBerryRegistry.ALUMINUM_OREBERRY_JUICE.getSource(), "Aluminum Oreberry Juice");
			addFluid(OreBerryRegistry.LEAD_OREBERRY_JUICE.getSource(), "Lead Oreberry Juice");
			addFluid(OreBerryRegistry.NICKEL_OREBERRY_JUICE.getSource(), "Nickel Oreberry Juice");
			addFluid(OreBerryRegistry.URANIUM_OREBERRY_JUICE.getSource(), "Uranium Oreberry Juice");
			addFluid(OreBerryRegistry.OSMIUM_OREBERRY_JUICE.getSource(), "Osmium Oreberry Juice");
			addFluid(OreBerryRegistry.ZINC_OREBERRY_JUICE.getSource(), "Zinc Oreberry Juice");
			addFluid(OreBerryRegistry.SILVER_OREBERRY_JUICE.getSource(), "Silver Oreberry Juice");

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

			//Config
			config("General", "General", "General Settings");
			config("growthChance", "Growth Chance", "Dictates the 1 in X chance the Oreberry Bush grows every time it tries to grow [Default: 20]");
			config("Placement", "Placement", "Placement Settings");
			config("darknessOnlyIronBush", "Darkness Only Iron Bush", "Only allow placing Iron Bushes in darkness [Default: true]");
			config("darknessOnlyGoldBush", "Darkness Only Gold Bush", "Only allow placing Gold Bushes in darkness [Default: true]");
			config("darknessOnlyCopperBush", "Darkness Only Copper Bush", "Only allow placing Copper Bushes in darkness [Default: true]");
			config("darknessOnlyTinBush", "Darkness Only Tin Bush", "Only allow placing Tin Bushes in darkness [Default: true]");
			config("darknessOnlyAluminumBush", "Darkness Only Aluminum Bush", "Only allow placing Aluminum Bushes in darkness [Default: true]");
			config("darknessOnlyLeadBush", "Darkness Only Lead Bush", "Only allow placing Lead Bushes in darkness [Default: true]");
			config("darknessOnlyNickelBush", "Darkness Only Nickel Bush", "Only allow placing Nickel Bushes in darkness [Default: true]");
			config("darknessOnlyUraniumBush", "Darkness Only Uranium Bush", "Only allow placing Uranium Bushes in darkness [Default: true]");
			config("darknessOnlyOsmiumBush", "Darkness Only Osmium Bush", "Only allow placing Osmium Bushes in darkness [Default: true]");
			config("darknessOnlyZincBush", "Darkness Only Zinc Bush", "Only allow placing Zinc Bushes in darkness [Default: true]");
			config("darknessOnlySilverBush", "Darkness Only Silver Bush", "Only allow placing Silver Bushes in darkness [Default: true]");
			config("darknessOnlyEssenceBush", "Darkness Only Essence Bush", "Only allow placing Essence Bushes in darkness [Default: true]");
			config("Density", "Density", "Density Settings");
			config("ironBushDensity", "Iron Bush Density", "Iron Bush Density [Default: 1]");
			config("goldBushDensity", "Gold Bush Density", "Gold Bush Density [Default: 1]");
			config("copperBushDensity", "Copper Bush Density", "Copper Bush Density [Default: 2]");
			config("tinBushDensity", "Tin Bush Density", "Tin Bush Density [Default: 2]");
			config("aluminumBushDensity", "Aluminum Bush Density", "Aluminum Bush Density [Default: 2]");
			config("leadBushDensity", "Lead Bush Density", "Lead Bush Density [Default: 1]");
			config("nickelBushDensity", "Nickel Bush Density", "Nickel Bush Density [Default: 1]");
			config("uraniumBushDensity", "Uranium Bush Density", "Uranium Bush Density [Default: 1]");
			config("osmiumBushDensity", "Osmium Bush Density", "Osmium Bush Density [Default: 1]");
			config("zincBushDensity", "Zinc Bush Density", "Zinc Bush Density [Default: 2]");
			config("silverBushDensity", "Silver Bush Density", "Silver Bush Density [Default: 1]");
			config("essenceBushDensity", "Essence Bush Density", "Essence Bush Density [Default: 2]");
		}

		private void addFluid(DeferredHolder<Fluid, BaseFlowingFluid> fluid, String name) {
			ResourceLocation id = fluid.getId();
			this.add("fluid_type." + id.getNamespace() + "." + id.getPath(), name);
		}

		private void config(String path, String name, String description) {
			this.add("oreberriesreplanted.configuration." + path, name);
			this.add("oreberriesreplanted.configuration." + path + ".tooltip", description);
		}
	}

	private static class OreBerryModels extends ModelProvider {
		public static final TextureSlot BUSH = TextureSlot.create("bush");

		public static final ModelTemplate BUSH_STAGE0 = ModelTemplates.create("oreberriesreplanted:base/oreberry_stage0", TextureSlot.ALL).extend().renderType("cutout_mipped").build();
		public static final ModelTemplate BUSH_STAGE1 = ModelTemplates.create("oreberriesreplanted:base/oreberry_stage1", TextureSlot.ALL).extend().renderType("cutout_mipped").build();
		public static final ModelTemplate BUSH_STAGE2 = ModelTemplates.create("oreberriesreplanted:base/oreberry_stage2", TextureSlot.ALL).extend().renderType("cutout_mipped").build();
		public static final ModelTemplate FLOWER_POT_BUSH = ModelTemplates.create("oreberriesreplanted:base/flower_pot_bush", BUSH).extend().renderType("cutout_mipped").build();
		public static final ModelTemplate VAT = ModelTemplates.create("oreberriesreplanted:vat/vat_base", TextureSlot.ALL).extend().renderType("cutout_mipped").build();

		public OreBerryModels(PackOutput output) {
			super(output, Reference.MOD_ID);
		}

		@Override
		protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
			makeBush(blockModels, OreBerryRegistry.IRON_OREBERRY_BUSH, "iron");
			makeBush(blockModels, OreBerryRegistry.GOLD_OREBERRY_BUSH, "gold");
			makeBush(blockModels, OreBerryRegistry.COPPER_OREBERRY_BUSH, "copper");
			makeBush(blockModels, OreBerryRegistry.TIN_OREBERRY_BUSH, "tin");
			makeBush(blockModels, OreBerryRegistry.ALUMINUM_OREBERRY_BUSH, "aluminum");
			makeBush(blockModels, OreBerryRegistry.LEAD_OREBERRY_BUSH, "lead");
			makeBush(blockModels, OreBerryRegistry.NICKEL_OREBERRY_BUSH, "nickel");
			makeBush(blockModels, OreBerryRegistry.URANIUM_OREBERRY_BUSH, "uranium");
			makeBush(blockModels, OreBerryRegistry.OSMIUM_OREBERRY_BUSH, "osmium");
			makeBush(blockModels, OreBerryRegistry.ZINC_OREBERRY_BUSH, "zinc");
			makeBush(blockModels, OreBerryRegistry.SILVER_OREBERRY_BUSH, "silver");
			makeBush(blockModels, OreBerryRegistry.ESSENCE_BERRY_BUSH, "essence");

			makePottedBush(blockModels, OreBerryRegistry.POTTED_IRON_OREBERRY_BUSH, "iron");
			makePottedBush(blockModels, OreBerryRegistry.POTTED_GOLD_OREBERRY_BUSH, "gold");
			makePottedBush(blockModels, OreBerryRegistry.POTTED_COPPER_OREBERRY_BUSH, "copper");
			makePottedBush(blockModels, OreBerryRegistry.POTTED_TIN_OREBERRY_BUSH, "tin");
			makePottedBush(blockModels, OreBerryRegistry.POTTED_ALUMINUM_OREBERRY_BUSH, "aluminum");
			makePottedBush(blockModels, OreBerryRegistry.POTTED_LEAD_OREBERRY_BUSH, "lead");
			makePottedBush(blockModels, OreBerryRegistry.POTTED_NICKEL_OREBERRY_BUSH, "nickel");
			makePottedBush(blockModels, OreBerryRegistry.POTTED_URANIUM_OREBERRY_BUSH, "uranium");
			makePottedBush(blockModels, OreBerryRegistry.POTTED_OSMIUM_OREBERRY_BUSH, "osmium");
			makePottedBush(blockModels, OreBerryRegistry.POTTED_ZINC_OREBERRY_BUSH, "zinc");
			makePottedBush(blockModels, OreBerryRegistry.POTTED_SILVER_OREBERRY_BUSH, "silver");
			makePottedBush(blockModels, OreBerryRegistry.POTTED_ESSENCE_BERRY_BUSH, "essence");

			makeVat(blockModels, OreBerryRegistry.OAK_VAT, ResourceLocation.withDefaultNamespace("block/oak_planks"));
			makeVat(blockModels, OreBerryRegistry.SPRUCE_VAT, ResourceLocation.withDefaultNamespace("block/spruce_planks"));
			makeVat(blockModels, OreBerryRegistry.BIRCH_VAT, ResourceLocation.withDefaultNamespace("block/birch_planks"));
			makeVat(blockModels, OreBerryRegistry.JUNGLE_VAT, ResourceLocation.withDefaultNamespace("block/jungle_planks"));
			makeVat(blockModels, OreBerryRegistry.ACACIA_VAT, ResourceLocation.withDefaultNamespace("block/acacia_planks"));
			makeVat(blockModels, OreBerryRegistry.DARK_OAK_VAT, ResourceLocation.withDefaultNamespace("block/dark_oak_planks"));
			makeVat(blockModels, OreBerryRegistry.MANGROVE_VAT, ResourceLocation.withDefaultNamespace("block/mangrove_planks"));
			makeVat(blockModels, OreBerryRegistry.CHERRY_VAT, ResourceLocation.withDefaultNamespace("block/cherry_planks"));
			makeVat(blockModels, OreBerryRegistry.CRIMSON_VAT, ResourceLocation.withDefaultNamespace("block/crimson_planks"));
			makeVat(blockModels, OreBerryRegistry.WARPED_VAT, ResourceLocation.withDefaultNamespace("block/warped_planks"));

			itemModels.generateFlatItem(OreBerryRegistry.COPPER_NUGGET.get(), ModelTemplates.FLAT_ITEM);

			itemModels.generateFlatItem(OreBerryRegistry.IRON_OREBERRY.get(), ModelTemplates.FLAT_ITEM);
			itemModels.generateFlatItem(OreBerryRegistry.GOLD_OREBERRY.get(), ModelTemplates.FLAT_ITEM);
			itemModels.generateFlatItem(OreBerryRegistry.COPPER_OREBERRY.get(), ModelTemplates.FLAT_ITEM);
			itemModels.generateFlatItem(OreBerryRegistry.TIN_OREBERRY.get(), ModelTemplates.FLAT_ITEM);
			itemModels.generateFlatItem(OreBerryRegistry.ALUMINUM_OREBERRY.get(), ModelTemplates.FLAT_ITEM);
			itemModels.generateFlatItem(OreBerryRegistry.LEAD_OREBERRY.get(), ModelTemplates.FLAT_ITEM);
			itemModels.generateFlatItem(OreBerryRegistry.NICKEL_OREBERRY.get(), ModelTemplates.FLAT_ITEM);
			itemModels.generateFlatItem(OreBerryRegistry.URANIUM_OREBERRY.get(), ModelTemplates.FLAT_ITEM);
			itemModels.generateFlatItem(OreBerryRegistry.OSMIUM_OREBERRY.get(), ModelTemplates.FLAT_ITEM);
			itemModels.generateFlatItem(OreBerryRegistry.ZINC_OREBERRY.get(), ModelTemplates.FLAT_ITEM);
			itemModels.generateFlatItem(OreBerryRegistry.SILVER_OREBERRY.get(), ModelTemplates.FLAT_ITEM);
			itemModels.generateFlatItem(OreBerryRegistry.ESSENCE_BERRY.get(), ModelTemplates.FLAT_ITEM);

			generateBucket(blockModels, OreBerryRegistry.IRON_OREBERRY_JUICE);
			generateBucket(blockModels, OreBerryRegistry.GOLD_OREBERRY_JUICE);
			generateBucket(blockModels, OreBerryRegistry.COPPER_OREBERRY_JUICE);
			generateBucket(blockModels, OreBerryRegistry.TIN_OREBERRY_JUICE);
			generateBucket(blockModels, OreBerryRegistry.ALUMINUM_OREBERRY_JUICE);
			generateBucket(blockModels, OreBerryRegistry.LEAD_OREBERRY_JUICE);
			generateBucket(blockModels, OreBerryRegistry.NICKEL_OREBERRY_JUICE);
			generateBucket(blockModels, OreBerryRegistry.URANIUM_OREBERRY_JUICE);
			generateBucket(blockModels, OreBerryRegistry.OSMIUM_OREBERRY_JUICE);
			generateBucket(blockModels, OreBerryRegistry.ZINC_OREBERRY_JUICE);
			generateBucket(blockModels, OreBerryRegistry.SILVER_OREBERRY_JUICE);
		}

		private void makeBush(BlockModelGenerators blockModels, DeferredBlock<OreBerryBushBlock> deferredBush, String type) {
			ResourceLocation texture = Reference.modLoc("block/" + type + "_oreberry");

			ResourceLocation stage0 = BUSH_STAGE0.createWithSuffix(deferredBush.get(), "_stage0", TextureMapping.cube(texture), blockModels.modelOutput);
			ResourceLocation stage1 = BUSH_STAGE1.createWithSuffix(deferredBush.get(), "_stage1", TextureMapping.cube(texture), blockModels.modelOutput);
			ResourceLocation stage2 = BUSH_STAGE2.createWithSuffix(deferredBush.get(), "_stage2", TextureMapping.cube(texture), blockModels.modelOutput);
			ResourceLocation stage3 = BUSH_STAGE2.createWithSuffix(deferredBush.get(), "_stage3", TextureMapping.cube(texture.withSuffix("_ripe")), blockModels.modelOutput);

			blockModels.blockStateOutput
					.accept(
							MultiVariantGenerator.dispatch(deferredBush.get())
									.with(
											PropertyDispatch.initial(OreBerryBushBlock.AGE)
													.select(0,
															BlockModelGenerators.variants(BlockModelGenerators.plainModel(stage0))
													)
													.select(1,
															BlockModelGenerators.variants(BlockModelGenerators.plainModel(stage1))
													)
													.select(2,
															BlockModelGenerators.variants(BlockModelGenerators.plainModel(stage2))
													)
													.select(3,
															BlockModelGenerators.variants(BlockModelGenerators.plainModel(stage3))
													)
									)
					);

			blockModels.registerSimpleItemModel(deferredBush.asItem(), stage1);
		}

		private void makePottedBush(BlockModelGenerators blockModels, DeferredBlock<FlowerPotBlock> deferredPot, String type) {
			ResourceLocation texture = Reference.modLoc("block/" + type + "_oreberry_ripe");
			ResourceLocation model = FLOWER_POT_BUSH.create(deferredPot.get(),
					new TextureMapping().put(BUSH, texture), blockModels.modelOutput);

			blockModels.blockStateOutput
					.accept(
							MultiVariantGenerator.dispatch(deferredPot.get(),
									BlockModelGenerators.variants(BlockModelGenerators.plainModel(model))
							)
					);
		}

		private void makeVat(BlockModelGenerators blockModels, DeferredBlock<VatBlock> deferredVat, ResourceLocation planks) {
			ResourceLocation model = VAT.create(deferredVat.get(),
					TextureMapping.cube(planks), blockModels.modelOutput);

			blockModels.blockStateOutput
					.accept(
							MultiVariantGenerator.dispatch(deferredVat.get(),
									BlockModelGenerators.variants(BlockModelGenerators.plainModel(model))
							)
					);
			blockModels.registerSimpleItemModel(deferredVat.asItem(), model);
		}

		private void generateBucket(BlockModelGenerators blockModels, LiquidReg liquidReg) {
			blockModels.itemModelOutput.accept(liquidReg.getBucket().get(), new DynamicFluidContainerModel.Unbaked(
					new DynamicFluidContainerModel.Textures(
							Optional.of(ResourceLocation.withDefaultNamespace("item/bucket")),
							Optional.of(ResourceLocation.withDefaultNamespace("item/bucket")),
							Optional.of(ResourceLocation.fromNamespaceAndPath("neoforge", "item/mask/bucket_fluid")),
							Optional.of(ResourceLocation.fromNamespaceAndPath("neoforge", "item/mask/bucket_fluid_cover"))
					), liquidReg.getSource().get(), false, true, false
			));
		}
	}

	public static class OreberryBlockTags extends BlockTagsProvider {
		public OreberryBlockTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(packOutput, lookupProvider, Reference.MOD_ID);
		}

		@Override
		protected void addTags(@NotNull HolderLookup.Provider provider) {
			this.tag(BlockTags.MINEABLE_WITH_AXE).add(OreBerryRegistry.OAK_VAT.get(), OreBerryRegistry.SPRUCE_VAT.get(), OreBerryRegistry.BIRCH_VAT.get(), OreBerryRegistry.JUNGLE_VAT.get(), OreBerryRegistry.ACACIA_VAT.get(), OreBerryRegistry.DARK_OAK_VAT.get(), OreBerryRegistry.MANGROVE_VAT.get(), OreBerryRegistry.CHERRY_VAT.get(), OreBerryRegistry.CRIMSON_VAT.get(), OreBerryRegistry.WARPED_VAT.get());
		}
	}

	public static class OreberryItemTags extends ItemTagsProvider {


		public OreberryItemTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTagsProvider) {
			super(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), Reference.MOD_ID);
		}

		public static final TagKey<Item> NUGGETS_COPPER = commonTag("nuggets/copper");
		public static final TagKey<Item> OREBERRIES = modTag("oreberries");
		public static final TagKey<Item> OREBERRIES_SMELTABLE = modTag("oreberries/smeltable");
		public static final TagKey<Item> VATS = modTag("vats");

		private static TagKey<Item> commonTag(String name) {
			return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
		}

		private static TagKey<Item> modTag(String name) {
			return ItemTags.create(Reference.modLoc(name));
		}

		@Override
		protected void addTags(@NotNull HolderLookup.Provider provider) {
			this.tag(NUGGETS_COPPER).add(OreBerryRegistry.COPPER_NUGGET.get());
			this.tag(Tags.Items.NUGGETS).addTag(NUGGETS_COPPER);
			this.tag(OREBERRIES_SMELTABLE).add(
					OreBerryRegistry.IRON_OREBERRY.get(),
					OreBerryRegistry.GOLD_OREBERRY.get(),
					OreBerryRegistry.COPPER_OREBERRY.get(),
					OreBerryRegistry.TIN_OREBERRY.get(),
					OreBerryRegistry.ALUMINUM_OREBERRY.get(),
					OreBerryRegistry.LEAD_OREBERRY.get(),
					OreBerryRegistry.NICKEL_OREBERRY.get(),
					OreBerryRegistry.URANIUM_OREBERRY.get(),
					OreBerryRegistry.OSMIUM_OREBERRY.get(),
					OreBerryRegistry.ZINC_OREBERRY.get(),
					OreBerryRegistry.SILVER_OREBERRY.get()
			);
			this.tag(OREBERRIES)
					.addTag(OREBERRIES_SMELTABLE)
					.add(OreBerryRegistry.ESSENCE_BERRY.get());
			this.tag(VATS).add(
					OreBerryRegistry.OAK_VAT_ITEM.get(),
					OreBerryRegistry.SPRUCE_VAT_ITEM.get(),
					OreBerryRegistry.BIRCH_VAT_ITEM.get(),
					OreBerryRegistry.JUNGLE_VAT_ITEM.get(),
					OreBerryRegistry.ACACIA_VAT_ITEM.get(),
					OreBerryRegistry.DARK_OAK_VAT_ITEM.get(),
					OreBerryRegistry.MANGROVE_VAT_ITEM.get(),
					OreBerryRegistry.CHERRY_VAT_ITEM.get(),
					OreBerryRegistry.CRIMSON_VAT_ITEM.get(),
					OreBerryRegistry.WARPED_VAT_ITEM.get()
			);
		}
	}

	public static class OreberryRecipeProvider extends RecipeProvider {

		public OreberryRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
			super(provider, recipeOutput);
		}

		@Override
		protected void buildRecipes() {
			shaped(RecipeCategory.MISC, Items.COPPER_INGOT, 1)
					.pattern("NNN")
					.pattern("NNN")
					.pattern("NNN")
					.define('N', OreBerryRegistry.COPPER_NUGGET.get())
					.unlockedBy("has_copper_nugget", has(OreBerryRegistry.COPPER_NUGGET.get()))
					.save(output, ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "copper_ingot_from_nugget").toString());

			generateRecipes(output, "iron", OreBerryRegistry.IRON_OREBERRY.get());
			generateRecipes(output, "gold", OreBerryRegistry.GOLD_OREBERRY.get());
			generateRecipes(output, "copper", OreBerryRegistry.COPPER_OREBERRY.get());
			generateRecipes(output, "tin", OreBerryRegistry.TIN_OREBERRY.get());
			generateRecipes(output, "aluminum", OreBerryRegistry.ALUMINUM_OREBERRY.get());
			generateRecipes(output, "lead", OreBerryRegistry.LEAD_OREBERRY.get());
			generateRecipes(output, "nickel", OreBerryRegistry.NICKEL_OREBERRY.get());
			generateRecipes(output, "uranium", OreBerryRegistry.URANIUM_OREBERRY.get());
			generateRecipes(output, "osmium", OreBerryRegistry.OSMIUM_OREBERRY.get());
			generateRecipes(output, "zinc", OreBerryRegistry.ZINC_OREBERRY.get());
			generateRecipes(output, "silver", OreBerryRegistry.SILVER_OREBERRY.get());

			generateVatRecipe(output, Items.OAK_PLANKS, Items.OAK_SLAB, OreBerryRegistry.OAK_VAT.get());
			generateVatRecipe(output, Items.SPRUCE_PLANKS, Items.SPRUCE_SLAB, OreBerryRegistry.SPRUCE_VAT.get());
			generateVatRecipe(output, Items.BIRCH_PLANKS, Items.BIRCH_SLAB, OreBerryRegistry.BIRCH_VAT.get());
			generateVatRecipe(output, Items.JUNGLE_PLANKS, Items.JUNGLE_SLAB, OreBerryRegistry.JUNGLE_VAT.get());
			generateVatRecipe(output, Items.ACACIA_PLANKS, Items.ACACIA_SLAB, OreBerryRegistry.ACACIA_VAT.get());
			generateVatRecipe(output, Items.DARK_OAK_PLANKS, Items.DARK_OAK_SLAB, OreBerryRegistry.DARK_OAK_VAT.get());
			generateVatRecipe(output, Items.MANGROVE_PLANKS, Items.MANGROVE_SLAB, OreBerryRegistry.MANGROVE_VAT.get());
			generateVatRecipe(output, Items.CHERRY_PLANKS, Items.CHERRY_SLAB, OreBerryRegistry.CHERRY_VAT.get());
			generateVatRecipe(output, Items.CRIMSON_PLANKS, Items.CRIMSON_SLAB, OreBerryRegistry.CRIMSON_VAT.get());
			generateVatRecipe(output, Items.WARPED_PLANKS, Items.WARPED_SLAB, OreBerryRegistry.WARPED_VAT.get());
		}

		private void generateRecipes(RecipeOutput output, String type, ItemLike berry) {
			TagKey<Item> nuggetTag = commonTag("nuggets/" + type);
			Ingredient nuggetIngredient = Ingredient.of(tagSet(nuggetTag));

			RecipeOutput tagOutput = output.withConditions(new NotCondition(new TagEmptyCondition<Item>(nuggetTag)));

			TagSmeltingRecipeBuilder.blasting(Ingredient.of(berry), RecipeCategory.MISC, nuggetIngredient, 0.2F, 100)
					.unlockedBy("has_berry", has(berry))
					.save(tagOutput, Reference.modLoc(type + "_from_blasting").toString());

			TagSmeltingRecipeBuilder.smelting(Ingredient.of(berry), RecipeCategory.MISC, nuggetIngredient, 0.2F, 200)
					.unlockedBy("has_berry", has(berry))
					.save(tagOutput, Reference.modLoc(type + "_from_smelting").toString());

			ResourceLocation fluidLocation = Reference.modLoc(type + "_oreberry_juice");
			VatRecipeBuilder.vat(nuggetIngredient, FluidIngredient.of(BuiltInRegistries.FLUID.getValue(fluidLocation)),
							Ingredient.of(berry)).unlockedBy("has_berry", has(berry))
					.save(tagOutput, Reference.modLoc("vat/" + type + "_nugget").toString());
		}

		private HolderSet<Item> tagSet(TagKey<Item> tagKey) {
			return this.registries.lookupOrThrow(Registries.ITEM).getOrThrow(tagKey);
		}

		private void generateVatRecipe(RecipeOutput recipeConsumer, ItemLike planks, ItemLike slab, ItemLike result) {
			shaped(RecipeCategory.MISC, result).pattern("P P").pattern("PSP").define('P', planks).define('S', slab).unlockedBy("has_planks", has(planks)).unlockedBy("has_slab", has(slab)).save(recipeConsumer);
		}

		private static TagKey<Item> commonTag(String name) {
			return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
		}

		public static class Runner extends RecipeProvider.Runner {
			public Runner(PackOutput output, CompletableFuture<Provider> completableFuture) {
				super(output, completableFuture);
			}

			@Override
			protected RecipeProvider createRecipeProvider(@NotNull HolderLookup.Provider provider, @NotNull RecipeOutput recipeOutput) {
				return new OreberryRecipeProvider(provider, recipeOutput);
			}

			@NotNull
			@Override
			public String getName() {
				return "OreBerries Replanted Recipes";
			}
		}
	}
}
