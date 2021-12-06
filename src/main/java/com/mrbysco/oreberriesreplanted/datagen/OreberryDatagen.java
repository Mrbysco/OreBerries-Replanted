package com.mrbysco.oreberriesreplanted.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Item;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.mrbysco.oreberriesreplanted.registry.OreBerryRegistry.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class OreberryDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(new OreBerryLoot(generator));
//			generator.addProvider(new OreBerryRecipes(generator));
		}
		if (event.includeClient()) {
			generator.addProvider(new OreBerryLanguage(generator));
			generator.addProvider(new OreBerryBlockStates(generator, helper));
			generator.addProvider(new OreBerryItemModels(generator, helper));
		}
	}

	private static class OreBerryLoot extends LootTableProvider {
		public OreBerryLoot(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootParameterSet>> getTables() {
			return ImmutableList.of(Pair.of(OreBerryBlocks::new, LootParameterSets.BLOCK));
		}

		private static class OreBerryBlocks extends BlockLootTables {

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
		protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
			map.forEach((name, table) -> LootTableManager.validate(validationtracker, name, table));
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
			this.add("fluid." + fluid.getId().toString(), name);
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

			withParent(OAK_VAT_ITEM.get(), modLoc("block/" + OAK_VAT.get().getRegistryName().getPath()));
			withParent(SPRUCE_VAT_ITEM.get(), modLoc("block/" + SPRUCE_VAT.get().getRegistryName().getPath()));
			withParent(BIRCH_VAT_ITEM.get(), modLoc("block/" + BIRCH_VAT.get().getRegistryName().getPath()));
			withParent(JUNGLE_VAT_ITEM.get(), modLoc("block/" + JUNGLE_VAT.get().getRegistryName().getPath()));
			withParent(ACACIA_VAT_ITEM.get(), modLoc("block/" + ACACIA_VAT.get().getRegistryName().getPath()));
			withParent(DARK_OAK_VAT_ITEM.get(), modLoc("block/" + DARK_OAK_VAT.get().getRegistryName().getPath()));
			withParent(CRIMSON_VAT_ITEM.get(), modLoc("block/" + CRIMSON_VAT.get().getRegistryName().getPath()));
			withParent(WARPED_VAT_ITEM.get(), modLoc("block/" + WARPED_VAT.get().getRegistryName().getPath()));

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
			withExistingParent(item.getRegistryName().getPath(), parent);
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
			ModelFile age0 = models().getBuilder(block.getRegistryName().getPath() + "_stage0")
					.parent(models().getExistingFile(modLoc("block/base/oreberry_stage0")))
					.texture("all", "block/" + type + "_oreberry");
			ModelFile age1 =models().getBuilder(block.getRegistryName().getPath() + "_stage1")
					.parent(models().getExistingFile(modLoc("block/base/oreberry_stage1")))
					.texture("all", "block/" + type + "_oreberry");
			ModelFile age2 = models().getBuilder(block.getRegistryName().getPath() + "_stage2")
					.parent(models().getExistingFile(modLoc("block/base/oreberry_stage2")))
					.texture("all", "block/" + type + "_oreberry");
			ModelFile age3 = models().getBuilder(block.getRegistryName().getPath() + "_stage3")
					.parent(models().getExistingFile(modLoc("block/base/oreberry_stage2")))
					.texture("all", "block/" + type + "_oreberry_ripe");

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
			ModelFile model = models().getBuilder(block.getRegistryName().getPath())
					.parent(models().getExistingFile(modLoc("block/base/flower_pot_bush")))
					.texture("bush", "block/" + type + "_oreberry_ripe");

			getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
		}

		private void makeVat(Block block, ResourceLocation planks) {
			ModelFile model = models().getBuilder(block.getRegistryName().getPath())
					.parent(models().getExistingFile(modLoc("block/vat/vat_base")))
					.texture("0", planks);

			getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
		}
	}
}
