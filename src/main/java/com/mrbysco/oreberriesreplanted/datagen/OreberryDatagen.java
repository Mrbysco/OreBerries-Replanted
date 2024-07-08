package com.mrbysco.oreberriesreplanted.datagen;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.block.OreBerryBushBlock;
import com.mrbysco.oreberriesreplanted.block.VatBlock;
import com.mrbysco.oreberriesreplanted.datagen.builder.TagSmeltingRecipeBuilder;
import com.mrbysco.oreberriesreplanted.datagen.builder.VatRecipeBuilder;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryFeatures;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryPlacements;
import net.minecraft.core.Cloner;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
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
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.registries.VanillaRegistries;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class OreberryDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new OreBerryLoot(packOutput, lookupProvider));

			BlockTagsProvider blockTagsProvider = new OreberryBlockTags(packOutput, lookupProvider, helper);
			generator.addProvider(event.includeServer(), blockTagsProvider);
			generator.addProvider(event.includeServer(), new OreberryItemTags(packOutput, lookupProvider, blockTagsProvider, helper));
			generator.addProvider(event.includeServer(), new OreberryRecipeProvider(packOutput, lookupProvider));

			generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
					packOutput, CompletableFuture.supplyAsync(OreberryDatagen::getProvider), Set.of(Reference.MOD_ID)));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeServer(), new OreBerryLanguage(packOutput));
			generator.addProvider(event.includeServer(), new OreBerryBlockStates(packOutput, helper));
			generator.addProvider(event.includeServer(), new OreBerryItemModels(packOutput, helper));
		}
	}

	private static RegistrySetBuilder.PatchedRegistries getProvider() {
		final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
		registryBuilder.add(Registries.CONFIGURED_FEATURE, OreBerryFeatures::bootstrap);
		registryBuilder.add(Registries.PLACED_FEATURE, OreBerryPlacements::bootstrap);
		registryBuilder.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, OreBerryBiomeModifiers::bootstrap);
		// We need the BIOME registry to be present, so we can use a biome tag, doesn't matter that it's empty
		registryBuilder.add(Registries.BIOME, $ -> {
		});
		RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		Cloner.Factory cloner$factory = new Cloner.Factory();
		net.neoforged.neoforge.registries.DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().forEach(data -> data.runWithArguments(cloner$factory::addCodec));
		return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup(), cloner$factory);
	}

	private static class OreBerryLoot extends LootTableProvider {
		public OreBerryLoot(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(packOutput, Set.of(), List.of(
					new SubProviderEntry(OreBerryBlocks::new, LootContextParamSets.BLOCK)), lookupProvider);
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

			@Override
			protected Iterable<Block> getKnownBlocks() {
				return (Iterable<Block>) OreBerryRegistry.BLOCKS.getEntries().stream().map(holder -> (Block) holder.get())::iterator;
			}
		}

		@Override
		protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
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
			addBlock(OreBerryRegistry.IRON_OREBERRY_BUSH, "Iron Oreberry Bush");
			addBlock(OreBerryRegistry.GOLD_OREBERRY_BUSH, "Gold Oreberry Bush");
			addBlock(OreBerryRegistry.COPPER_OREBERRY_BUSH, "Copper Oreberry Bush");
			addBlock(OreBerryRegistry.TIN_OREBERRY_BUSH, "Tin Oreberry Bush");
			addBlock(OreBerryRegistry.ALUMINUM_OREBERRY_BUSH, "Aluminum Oreberry Bush");
			addBlock(OreBerryRegistry.LEAD_OREBERRY_BUSH, "Lead Oreberry Bush");
			addBlock(OreBerryRegistry.NICKEL_OREBERRY_BUSH, "Nickel Oreberry Bush");
			addBlock(OreBerryRegistry.URANIUM_OREBERRY_BUSH, "Uranium Oreberry Bush");
			addBlock(OreBerryRegistry.OSMIUM_OREBERRY_BUSH, "Osmium Oreberry Bush");
			addBlock(OreBerryRegistry.ZINC_OREBERRY_BUSH, "Zinc Oreberry Bush");
			addBlock(OreBerryRegistry.SILVER_OREBERRY_BUSH, "Silver Oreberry Bush");
			addBlock(OreBerryRegistry.ESSENCE_BERRY_BUSH, "Essence Berry Bush");

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
			addBlock(OreBerryRegistry.DARK_OAK_VAT, "Oak Vat");
			addBlock(OreBerryRegistry.MANGROVE_VAT, "Mangrove Vat");
			addBlock(OreBerryRegistry.CHERRY_VAT, "Cherry Vat");
			addBlock(OreBerryRegistry.CRIMSON_VAT, "Crimson Vat");
			addBlock(OreBerryRegistry.WARPED_VAT, "Warped Vat");

			//Items
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
		}

		private void addFluid(DeferredHolder<Fluid, BaseFlowingFluid> fluid, String name) {
			ResourceLocation id = fluid.getId();
			this.add("fluid_type." + id.getNamespace() + "." + id.getPath(), name);
		}
	}

	private static class OreBerryItemModels extends ItemModelProvider {
		public OreBerryItemModels(PackOutput packOutput, ExistingFileHelper helper) {
			super(packOutput, Reference.MOD_ID, helper);
		}

		@Override
		protected void registerModels() {
			withParent(OreBerryRegistry.IRON_OREBERRY_BUSH_ITEM.getId(), modLoc("block/iron_oreberry_bush_stage1"));
			withParent(OreBerryRegistry.GOLD_OREBERRY_BUSH_ITEM.getId(), modLoc("block/gold_oreberry_bush_stage1"));
			withParent(OreBerryRegistry.COPPER_OREBERRY_BUSH_ITEM.getId(), modLoc("block/copper_oreberry_bush_stage1"));
			withParent(OreBerryRegistry.TIN_OREBERRY_BUSH_ITEM.getId(), modLoc("block/tin_oreberry_bush_stage1"));
			withParent(OreBerryRegistry.ALUMINUM_OREBERRY_BUSH_ITEM.getId(), modLoc("block/aluminum_oreberry_bush_stage1"));
			withParent(OreBerryRegistry.LEAD_OREBERRY_BUSH_ITEM.getId(), modLoc("block/lead_oreberry_bush_stage1"));
			withParent(OreBerryRegistry.NICKEL_OREBERRY_BUSH_ITEM.getId(), modLoc("block/nickel_oreberry_bush_stage1"));
			withParent(OreBerryRegistry.URANIUM_OREBERRY_BUSH_ITEM.getId(), modLoc("block/uranium_oreberry_bush_stage1"));
			withParent(OreBerryRegistry.OSMIUM_OREBERRY_BUSH_ITEM.getId(), modLoc("block/osmium_oreberry_bush_stage1"));
			withParent(OreBerryRegistry.ZINC_OREBERRY_BUSH_ITEM.getId(), modLoc("block/zinc_oreberry_bush_stage1"));
			withParent(OreBerryRegistry.SILVER_OREBERRY_BUSH_ITEM.getId(), modLoc("block/silver_oreberry_bush_stage1"));
			withParent(OreBerryRegistry.ESSENCE_BERRY_BUSH_ITEM.getId(), modLoc("block/essence_berry_bush_stage1"));

			withParent(OreBerryRegistry.OAK_VAT_ITEM.getId(), modLoc("block/" + OreBerryRegistry.OAK_VAT.getId().getPath()));
			withParent(OreBerryRegistry.SPRUCE_VAT_ITEM.getId(), modLoc("block/" + OreBerryRegistry.SPRUCE_VAT.getId().getPath()));
			withParent(OreBerryRegistry.BIRCH_VAT_ITEM.getId(), modLoc("block/" + OreBerryRegistry.BIRCH_VAT.getId().getPath()));
			withParent(OreBerryRegistry.JUNGLE_VAT_ITEM.getId(), modLoc("block/" + OreBerryRegistry.JUNGLE_VAT.getId().getPath()));
			withParent(OreBerryRegistry.ACACIA_VAT_ITEM.getId(), modLoc("block/" + OreBerryRegistry.ACACIA_VAT.getId().getPath()));
			withParent(OreBerryRegistry.DARK_OAK_VAT_ITEM.getId(), modLoc("block/" + OreBerryRegistry.DARK_OAK_VAT.getId().getPath()));
			withParent(OreBerryRegistry.MANGROVE_VAT_ITEM.getId(), modLoc("block/" + OreBerryRegistry.MANGROVE_VAT.getId().getPath()));
			withParent(OreBerryRegistry.CHERRY_VAT_ITEM.getId(), modLoc("block/" + OreBerryRegistry.CHERRY_VAT.getId().getPath()));
			withParent(OreBerryRegistry.CRIMSON_VAT_ITEM.getId(), modLoc("block/" + OreBerryRegistry.CRIMSON_VAT.getId().getPath()));
			withParent(OreBerryRegistry.WARPED_VAT_ITEM.getId(), modLoc("block/" + OreBerryRegistry.WARPED_VAT.getId().getPath()));

			singleTexture(OreBerryRegistry.COPPER_NUGGET.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/copper_nugget"));

			singleTexture(OreBerryRegistry.IRON_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/iron_oreberry"));
			singleTexture(OreBerryRegistry.GOLD_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/gold_oreberry"));
			singleTexture(OreBerryRegistry.COPPER_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/copper_oreberry"));
			singleTexture(OreBerryRegistry.TIN_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/tin_oreberry"));
			singleTexture(OreBerryRegistry.ALUMINUM_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/aluminum_oreberry"));
			singleTexture(OreBerryRegistry.LEAD_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/lead_oreberry"));
			singleTexture(OreBerryRegistry.NICKEL_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/nickel_oreberry"));
			singleTexture(OreBerryRegistry.URANIUM_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/uranium_oreberry"));
			singleTexture(OreBerryRegistry.OSMIUM_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/osmium_oreberry"));
			singleTexture(OreBerryRegistry.ZINC_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/zinc_oreberry"));
			singleTexture(OreBerryRegistry.SILVER_OREBERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/silver_oreberry"));
			singleTexture(OreBerryRegistry.ESSENCE_BERRY.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/essence_berry"));
		}

		private void withParent(ResourceLocation itemLocation, ResourceLocation parent) {
			withExistingParent(itemLocation.getPath(), parent);
		}
	}

	private static class OreBerryBlockStates extends BlockStateProvider {

		public OreBerryBlockStates(PackOutput packOutput, ExistingFileHelper helper) {
			super(packOutput, Reference.MOD_ID, helper);
		}

		@Override
		protected void registerStatesAndModels() {
			makeBush(OreBerryRegistry.IRON_OREBERRY_BUSH, "iron");
			makeBush(OreBerryRegistry.GOLD_OREBERRY_BUSH, "gold");
			makeBush(OreBerryRegistry.COPPER_OREBERRY_BUSH, "copper");
			makeBush(OreBerryRegistry.TIN_OREBERRY_BUSH, "tin");
			makeBush(OreBerryRegistry.ALUMINUM_OREBERRY_BUSH, "aluminum");
			makeBush(OreBerryRegistry.LEAD_OREBERRY_BUSH, "lead");
			makeBush(OreBerryRegistry.NICKEL_OREBERRY_BUSH, "nickel");
			makeBush(OreBerryRegistry.URANIUM_OREBERRY_BUSH, "uranium");
			makeBush(OreBerryRegistry.OSMIUM_OREBERRY_BUSH, "osmium");
			makeBush(OreBerryRegistry.ZINC_OREBERRY_BUSH, "zinc");
			makeBush(OreBerryRegistry.SILVER_OREBERRY_BUSH, "silver");
			makeBush(OreBerryRegistry.ESSENCE_BERRY_BUSH, "essence");

			makePottedBush(OreBerryRegistry.POTTED_IRON_OREBERRY_BUSH, "iron");
			makePottedBush(OreBerryRegistry.POTTED_GOLD_OREBERRY_BUSH, "gold");
			makePottedBush(OreBerryRegistry.POTTED_COPPER_OREBERRY_BUSH, "copper");
			makePottedBush(OreBerryRegistry.POTTED_TIN_OREBERRY_BUSH, "tin");
			makePottedBush(OreBerryRegistry.POTTED_ALUMINUM_OREBERRY_BUSH, "aluminum");
			makePottedBush(OreBerryRegistry.POTTED_LEAD_OREBERRY_BUSH, "lead");
			makePottedBush(OreBerryRegistry.POTTED_NICKEL_OREBERRY_BUSH, "nickel");
			makePottedBush(OreBerryRegistry.POTTED_URANIUM_OREBERRY_BUSH, "uranium");
			makePottedBush(OreBerryRegistry.POTTED_OSMIUM_OREBERRY_BUSH, "osmium");
			makePottedBush(OreBerryRegistry.POTTED_ZINC_OREBERRY_BUSH, "zinc");
			makePottedBush(OreBerryRegistry.POTTED_SILVER_OREBERRY_BUSH, "silver");
			makePottedBush(OreBerryRegistry.POTTED_ESSENCE_BERRY_BUSH, "essence");

			makeVat(OreBerryRegistry.OAK_VAT, mcLoc("block/oak_planks"));
			makeVat(OreBerryRegistry.SPRUCE_VAT, mcLoc("block/spruce_planks"));
			makeVat(OreBerryRegistry.BIRCH_VAT, mcLoc("block/birch_planks"));
			makeVat(OreBerryRegistry.JUNGLE_VAT, mcLoc("block/jungle_planks"));
			makeVat(OreBerryRegistry.ACACIA_VAT, mcLoc("block/acacia_planks"));
			makeVat(OreBerryRegistry.DARK_OAK_VAT, mcLoc("block/dark_oak_planks"));
			makeVat(OreBerryRegistry.MANGROVE_VAT, mcLoc("block/mangrove_planks"));
			makeVat(OreBerryRegistry.CHERRY_VAT, mcLoc("block/cherry_planks"));
			makeVat(OreBerryRegistry.CRIMSON_VAT, mcLoc("block/crimson_planks"));
			makeVat(OreBerryRegistry.WARPED_VAT, mcLoc("block/warped_planks"));
		}

		private void makeBush(DeferredBlock<OreBerryBushBlock> deferredBush, String type) {
			ResourceLocation location = deferredBush.getId();
			ModelFile age0 = models().getBuilder(location.getPath() + "_stage0")
					.parent(models().getExistingFile(modLoc("block/base/oreberry_stage0")))
					.texture("all", "block/" + type + "_oreberry").renderType("cutout_mipped");
			ModelFile age1 = models().getBuilder(location.getPath() + "_stage1")
					.parent(models().getExistingFile(modLoc("block/base/oreberry_stage1")))
					.texture("all", "block/" + type + "_oreberry").renderType("cutout_mipped");
			ModelFile age2 = models().getBuilder(location.getPath() + "_stage2")
					.parent(models().getExistingFile(modLoc("block/base/oreberry_stage2")))
					.texture("all", "block/" + type + "_oreberry").renderType("cutout_mipped");
			ModelFile age3 = models().getBuilder(location.getPath() + "_stage3")
					.parent(models().getExistingFile(modLoc("block/base/oreberry_stage2")))
					.texture("all", "block/" + type + "_oreberry_ripe").renderType("cutout_mipped");

			getVariantBuilder(deferredBush.get())
					.partialState().with(BlockStateProperties.AGE_3, 0)
					.modelForState().modelFile(age0).addModel()
					.partialState().with(BlockStateProperties.AGE_3, 1)
					.modelForState().modelFile(age1).addModel()
					.partialState().with(BlockStateProperties.AGE_3, 2)
					.modelForState().modelFile(age2).addModel()
					.partialState().with(BlockStateProperties.AGE_3, 3)
					.modelForState().modelFile(age3).addModel();
		}

		private void makePottedBush(DeferredBlock<FlowerPotBlock> deferredPot, String type) {
			ModelFile model = models().getBuilder(deferredPot.getId().getPath())
					.parent(models().getExistingFile(modLoc("block/base/flower_pot_bush")))
					.texture("bush", "block/" + type + "_oreberry_ripe").renderType("cutout_mipped");

			getVariantBuilder(deferredPot.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
		}

		private void makeVat(DeferredBlock<VatBlock> deferredVat, ResourceLocation planks) {
			ModelFile model = models().getBuilder(deferredVat.getId().getPath())
					.parent(models().getExistingFile(modLoc("block/vat/vat_base")))
					.texture("0", planks);

			getVariantBuilder(deferredVat.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
		}
	}


	public static class OreberryBlockTags extends BlockTagsProvider {
		public OreberryBlockTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider,
		                         @Nullable ExistingFileHelper existingFileHelper) {
			super(packOutput, lookupProvider, Reference.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags(HolderLookup.Provider provider) {
			this.tag(BlockTags.MINEABLE_WITH_AXE).add(OreBerryRegistry.OAK_VAT.get(), OreBerryRegistry.SPRUCE_VAT.get(),
					OreBerryRegistry.BIRCH_VAT.get(), OreBerryRegistry.JUNGLE_VAT.get(), OreBerryRegistry.ACACIA_VAT.get(),
					OreBerryRegistry.DARK_OAK_VAT.get(), OreBerryRegistry.MANGROVE_VAT.get(), OreBerryRegistry.CHERRY_VAT.get(),
					OreBerryRegistry.CRIMSON_VAT.get(), OreBerryRegistry.WARPED_VAT.get());
		}
	}

	public static class OreberryItemTags extends ItemTagsProvider {

		public OreberryItemTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
			super(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), Reference.MOD_ID, existingFileHelper);
		}

		public static final TagKey<Item> NUGGETS_COPPER = commonTag("nuggets/copper");

		private static TagKey<Item> commonTag(String name) {
			return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
		}

		@Override
		protected void addTags(HolderLookup.Provider provider) {
			this.tag(NUGGETS_COPPER).add(OreBerryRegistry.COPPER_NUGGET.get());
			this.tag(Tags.Items.NUGGETS).addTag(NUGGETS_COPPER);
		}
	}

	public static class OreberryRecipeProvider extends RecipeProvider {

		public OreberryRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(packOutput, lookupProvider);
		}

		@Override
		protected void buildRecipes(RecipeOutput recipeOutput) {
			generateRecipes(recipeOutput, "iron", OreBerryRegistry.IRON_OREBERRY.get());
			generateRecipes(recipeOutput, "gold", OreBerryRegistry.GOLD_OREBERRY.get());
			generateRecipes(recipeOutput, "copper", OreBerryRegistry.COPPER_OREBERRY.get());
			generateRecipes(recipeOutput, "tin", OreBerryRegistry.TIN_OREBERRY.get());
			generateRecipes(recipeOutput, "aluminum", OreBerryRegistry.ALUMINUM_OREBERRY.get());
			generateRecipes(recipeOutput, "lead", OreBerryRegistry.LEAD_OREBERRY.get());
			generateRecipes(recipeOutput, "nickel", OreBerryRegistry.NICKEL_OREBERRY.get());
			generateRecipes(recipeOutput, "uranium", OreBerryRegistry.URANIUM_OREBERRY.get());
			generateRecipes(recipeOutput, "osmium", OreBerryRegistry.OSMIUM_OREBERRY.get());
			generateRecipes(recipeOutput, "zinc", OreBerryRegistry.ZINC_OREBERRY.get());
			generateRecipes(recipeOutput, "silver", OreBerryRegistry.SILVER_OREBERRY.get());

			generateVatRecipe(recipeOutput, Items.OAK_PLANKS, Items.OAK_SLAB, OreBerryRegistry.OAK_VAT.get());
			generateVatRecipe(recipeOutput, Items.SPRUCE_PLANKS, Items.SPRUCE_SLAB, OreBerryRegistry.SPRUCE_VAT.get());
			generateVatRecipe(recipeOutput, Items.BIRCH_PLANKS, Items.BIRCH_SLAB, OreBerryRegistry.BIRCH_VAT.get());
			generateVatRecipe(recipeOutput, Items.JUNGLE_PLANKS, Items.JUNGLE_SLAB, OreBerryRegistry.JUNGLE_VAT.get());
			generateVatRecipe(recipeOutput, Items.ACACIA_PLANKS, Items.ACACIA_SLAB, OreBerryRegistry.ACACIA_VAT.get());
			generateVatRecipe(recipeOutput, Items.DARK_OAK_PLANKS, Items.DARK_OAK_SLAB, OreBerryRegistry.DARK_OAK_VAT.get());
			generateVatRecipe(recipeOutput, Items.MANGROVE_PLANKS, Items.MANGROVE_SLAB, OreBerryRegistry.MANGROVE_VAT.get());
			generateVatRecipe(recipeOutput, Items.CHERRY_PLANKS, Items.CHERRY_SLAB, OreBerryRegistry.CHERRY_VAT.get());
			generateVatRecipe(recipeOutput, Items.CRIMSON_PLANKS, Items.CRIMSON_SLAB, OreBerryRegistry.CRIMSON_VAT.get());
			generateVatRecipe(recipeOutput, Items.WARPED_PLANKS, Items.WARPED_SLAB, OreBerryRegistry.WARPED_VAT.get());
		}

		private void generateRecipes(RecipeOutput recipeOutput, String type, ItemLike berry) {
			TagKey<Item> nuggetTag = commonTag("nuggets/" + type);
			Ingredient nuggetIngredient = Ingredient.of(nuggetTag);

			RecipeOutput tagOutput = recipeOutput.withConditions(new NotCondition(new TagEmptyCondition(nuggetTag.location())));

			TagSmeltingRecipeBuilder.blasting(Ingredient.of(berry), RecipeCategory.MISC, nuggetIngredient, 0.2F, 100)
					.unlockedBy("has_berry", has(berry))
					.save(tagOutput,
							Reference.modLoc(type + "_from_blasting"));

			TagSmeltingRecipeBuilder.smelting(Ingredient.of(berry), RecipeCategory.MISC, nuggetIngredient, 0.2F, 200)
					.unlockedBy("has_berry", has(berry))
					.save(tagOutput,
							Reference.modLoc(type + "_from_smelting"));

			ResourceLocation fluidLocation = Reference.modLoc(type + "_oreberry_juice");
			VatRecipeBuilder.vat(Ingredient.of(nuggetTag), FluidIngredient.of(BuiltInRegistries.FLUID.get(fluidLocation)), Ingredient.of(berry))
					.unlockedBy("has_berry", has(berry))
					.save(tagOutput,
							Reference.modLoc("vat/" + type + "_nugget"));
		}

		private void generateVatRecipe(RecipeOutput recipeConsumer, ItemLike planks, ItemLike slab, ItemLike result) {
			ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
					.pattern("P P")
					.pattern("PSP")
					.define('P', planks)
					.define('S', slab)
					.unlockedBy("has_planks", has(planks))
					.unlockedBy("has_slab", has(slab))
					.save(recipeConsumer);
		}

		private static TagKey<Item> commonTag(String name) {
			return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
		}
	}
}
