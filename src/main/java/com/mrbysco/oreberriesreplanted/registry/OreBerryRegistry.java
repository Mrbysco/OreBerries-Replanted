package com.mrbysco.oreberriesreplanted.registry;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.block.OreBerryBushBlock;
import com.mrbysco.oreberriesreplanted.block.OreEnum;
import com.mrbysco.oreberriesreplanted.block.VatBlock;
import com.mrbysco.oreberriesreplanted.blockentity.VatBlockEntity;
import com.mrbysco.oreberriesreplanted.item.EssenceBerryItem;
import com.mrbysco.oreberriesreplanted.item.OreBerryItem;
import com.mrbysco.oreberriesreplanted.item.TooltipBlockItem;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryBushFeature;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryBushFeatureConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class OreBerryRegistry {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Reference.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Reference.MOD_ID);
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, Reference.MOD_ID);
	public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, Reference.MOD_ID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Reference.MOD_ID);
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Reference.MOD_ID);

	public static final Supplier<Feature<OreBerryBushFeatureConfig>> OREBERRY_FEATURE_CONFIG = FEATURES.register("oreberry_bush", () ->
			new OreBerryBushFeature(OreBerryBushFeatureConfig.CODEC));


	//Fluids
	public static final LiquidReg IRON_OREBERRY_JUICE = new LiquidReg.Builder("iron_oreberry_juice", true, 0xFFc9c9c9).build();
	public static final LiquidReg GOLD_OREBERRY_JUICE = new LiquidReg.Builder("gold_oreberry_juice", true, 0xFFfad64a).build();
	public static final LiquidReg COPPER_OREBERRY_JUICE = new LiquidReg.Builder("copper_oreberry_juice", true, 0xFFf8b18d).build();
	public static final LiquidReg TIN_OREBERRY_JUICE = new LiquidReg.Builder("tin_oreberry_juice", true, 0xFF74609e).build();
	public static final LiquidReg ALUMINUM_OREBERRY_JUICE = new LiquidReg.Builder("aluminum_oreberry_juice", true, 0xFFc5dbed).build();
	public static final LiquidReg LEAD_OREBERRY_JUICE = new LiquidReg.Builder("lead_oreberry_juice", true, 0xFF707e8a).build();
	public static final LiquidReg NICKEL_OREBERRY_JUICE = new LiquidReg.Builder("nickel_oreberry_juice", true, 0xFFb0b59f).build();
	public static final LiquidReg URANIUM_OREBERRY_JUICE = new LiquidReg.Builder("uranium_oreberry_juice", true, 0xFF98b350).build();
	public static final LiquidReg OSMIUM_OREBERRY_JUICE = new LiquidReg.Builder("osmium_oreberry_juice", true, 0xFF83b0bd).build();
	public static final LiquidReg ZINC_OREBERRY_JUICE = new LiquidReg.Builder("zinc_oreberry_juice", true, 0xFFd1d1a5).build();
	public static final LiquidReg SILVER_OREBERRY_JUICE = new LiquidReg.Builder("silver_oreberry_juice", true, 0xFF898fc9).build();

	//Blocks
	public static final DeferredBlock<OreBerryBushBlock> IRON_OREBERRY_BUSH = BLOCKS.registerBlock("iron_oreberry_bush", (properties) -> new OreBerryBushBlock(properties, OreBerryRegistry.IRON_OREBERRY, OreEnum.IRON), blockBuilder(Blocks.OAK_LEAVES.defaultMapColor()));
	public static final DeferredBlock<OreBerryBushBlock> GOLD_OREBERRY_BUSH = BLOCKS.registerBlock("gold_oreberry_bush", (properties) -> new OreBerryBushBlock(properties, OreBerryRegistry.GOLD_OREBERRY, OreEnum.GOLD), blockBuilder(Blocks.OAK_LEAVES.defaultMapColor()));
	public static final DeferredBlock<OreBerryBushBlock> COPPER_OREBERRY_BUSH = BLOCKS.registerBlock("copper_oreberry_bush", (properties) -> new OreBerryBushBlock(properties, OreBerryRegistry.COPPER_OREBERRY, OreEnum.COPPER), blockBuilder(Blocks.OAK_LEAVES.defaultMapColor()));
	public static final DeferredBlock<OreBerryBushBlock> TIN_OREBERRY_BUSH = BLOCKS.registerBlock("tin_oreberry_bush", (properties) -> new OreBerryBushBlock(properties, OreBerryRegistry.TIN_OREBERRY, OreEnum.TIN), blockBuilder(Blocks.OAK_LEAVES.defaultMapColor()));
	public static final DeferredBlock<OreBerryBushBlock> ALUMINUM_OREBERRY_BUSH = BLOCKS.registerBlock("aluminum_oreberry_bush", (properties) -> new OreBerryBushBlock(properties, OreBerryRegistry.ALUMINUM_OREBERRY, OreEnum.ALUMINUM), blockBuilder(Blocks.OAK_LEAVES.defaultMapColor()));
	public static final DeferredBlock<OreBerryBushBlock> LEAD_OREBERRY_BUSH = BLOCKS.registerBlock("lead_oreberry_bush", (properties) -> new OreBerryBushBlock(properties, OreBerryRegistry.LEAD_OREBERRY, OreEnum.LEAD), blockBuilder(Blocks.OAK_LEAVES.defaultMapColor()));
	public static final DeferredBlock<OreBerryBushBlock> NICKEL_OREBERRY_BUSH = BLOCKS.registerBlock("nickel_oreberry_bush", (properties) -> new OreBerryBushBlock(properties, OreBerryRegistry.NICKEL_OREBERRY, OreEnum.NICKEL), blockBuilder(Blocks.OAK_LEAVES.defaultMapColor()));
	public static final DeferredBlock<OreBerryBushBlock> URANIUM_OREBERRY_BUSH = BLOCKS.registerBlock("uranium_oreberry_bush", (properties) -> new OreBerryBushBlock(properties, OreBerryRegistry.URANIUM_OREBERRY, OreEnum.URANIUM), blockBuilder(Blocks.OAK_LEAVES.defaultMapColor()));
	public static final DeferredBlock<OreBerryBushBlock> OSMIUM_OREBERRY_BUSH = BLOCKS.registerBlock("osmium_oreberry_bush", (properties) -> new OreBerryBushBlock(properties, OreBerryRegistry.OSMIUM_OREBERRY, OreEnum.OSMIUM), blockBuilder(Blocks.OAK_LEAVES.defaultMapColor()));
	public static final DeferredBlock<OreBerryBushBlock> ZINC_OREBERRY_BUSH = BLOCKS.registerBlock("zinc_oreberry_bush", (properties) -> new OreBerryBushBlock(properties, OreBerryRegistry.ZINC_OREBERRY, OreEnum.ZINC), blockBuilder(Blocks.OAK_LEAVES.defaultMapColor()));
	public static final DeferredBlock<OreBerryBushBlock> SILVER_OREBERRY_BUSH = BLOCKS.registerBlock("silver_oreberry_bush", (properties) -> new OreBerryBushBlock(properties, OreBerryRegistry.SILVER_OREBERRY, OreEnum.SILVER), blockBuilder(Blocks.OAK_LEAVES.defaultMapColor()));
	public static final DeferredBlock<OreBerryBushBlock> ESSENCE_BERRY_BUSH = BLOCKS.registerBlock("essence_berry_bush", (properties) -> new OreBerryBushBlock(properties, OreBerryRegistry.ESSENCE_BERRY, OreEnum.ESSENCE), blockBuilder(Blocks.OAK_LEAVES.defaultMapColor()));

	public static final DeferredBlock<FlowerPotBlock> POTTED_IRON_OREBERRY_BUSH = BLOCKS.registerBlock("potted_iron_oreberry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.IRON_OREBERRY_BUSH, properties), potBuilder());
	public static final DeferredBlock<FlowerPotBlock> POTTED_GOLD_OREBERRY_BUSH = BLOCKS.registerBlock("potted_gold_oreberry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.GOLD_OREBERRY_BUSH, properties), potBuilder());
	public static final DeferredBlock<FlowerPotBlock> POTTED_COPPER_OREBERRY_BUSH = BLOCKS.registerBlock("potted_copper_oreberry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.COPPER_OREBERRY_BUSH, properties), potBuilder());
	public static final DeferredBlock<FlowerPotBlock> POTTED_TIN_OREBERRY_BUSH = BLOCKS.registerBlock("potted_tin_oreberry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.TIN_OREBERRY_BUSH, properties), potBuilder());
	public static final DeferredBlock<FlowerPotBlock> POTTED_ALUMINUM_OREBERRY_BUSH = BLOCKS.registerBlock("potted_aluminum_oreberry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.ALUMINUM_OREBERRY_BUSH, properties), potBuilder());
	public static final DeferredBlock<FlowerPotBlock> POTTED_LEAD_OREBERRY_BUSH = BLOCKS.registerBlock("potted_lead_oreberry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.LEAD_OREBERRY_BUSH, properties), potBuilder());
	public static final DeferredBlock<FlowerPotBlock> POTTED_NICKEL_OREBERRY_BUSH = BLOCKS.registerBlock("potted_nickel_oreberry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.NICKEL_OREBERRY_BUSH, properties), potBuilder());
	public static final DeferredBlock<FlowerPotBlock> POTTED_URANIUM_OREBERRY_BUSH = BLOCKS.registerBlock("potted_uranium_oreberry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.URANIUM_OREBERRY_BUSH, properties), potBuilder());
	public static final DeferredBlock<FlowerPotBlock> POTTED_OSMIUM_OREBERRY_BUSH = BLOCKS.registerBlock("potted_osmium_oreberry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.OSMIUM_OREBERRY_BUSH, properties), potBuilder());
	public static final DeferredBlock<FlowerPotBlock> POTTED_ZINC_OREBERRY_BUSH = BLOCKS.registerBlock("potted_zinc_oreberry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.ZINC_OREBERRY_BUSH, properties), potBuilder());
	public static final DeferredBlock<FlowerPotBlock> POTTED_SILVER_OREBERRY_BUSH = BLOCKS.registerBlock("potted_silver_oreberry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.SILVER_OREBERRY_BUSH, properties), potBuilder());
	public static final DeferredBlock<FlowerPotBlock> POTTED_ESSENCE_BERRY_BUSH = BLOCKS.registerBlock("potted_essence_berry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.ESSENCE_BERRY_BUSH, properties), potBuilder());

	public static final DeferredBlock<VatBlock> OAK_VAT = BLOCKS.registerBlock("oak_vat", VatBlock::new, blockBuilder(Blocks.OAK_PLANKS.defaultMapColor()));
	public static final DeferredBlock<VatBlock> SPRUCE_VAT = BLOCKS.registerBlock("spruce_vat", VatBlock::new, blockBuilder(Blocks.SPRUCE_PLANKS.defaultMapColor()));
	public static final DeferredBlock<VatBlock> BIRCH_VAT = BLOCKS.registerBlock("birch_vat", VatBlock::new, blockBuilder(Blocks.BIRCH_PLANKS.defaultMapColor()));
	public static final DeferredBlock<VatBlock> JUNGLE_VAT = BLOCKS.registerBlock("jungle_vat", VatBlock::new, blockBuilder(Blocks.JUNGLE_PLANKS.defaultMapColor()));
	public static final DeferredBlock<VatBlock> ACACIA_VAT = BLOCKS.registerBlock("acacia_vat", VatBlock::new, blockBuilder(Blocks.ACACIA_PLANKS.defaultMapColor()));
	public static final DeferredBlock<VatBlock> DARK_OAK_VAT = BLOCKS.registerBlock("dark_oak_vat", VatBlock::new, blockBuilder(Blocks.DARK_OAK_PLANKS.defaultMapColor()));
	public static final DeferredBlock<VatBlock> MANGROVE_VAT = BLOCKS.registerBlock("mangrove_vat", VatBlock::new, blockBuilder(Blocks.MANGROVE_PLANKS.defaultMapColor()));
	public static final DeferredBlock<VatBlock> CHERRY_VAT = BLOCKS.registerBlock("cherry_vat", VatBlock::new, blockBuilder(Blocks.CHERRY_PLANKS.defaultMapColor()));
	public static final DeferredBlock<VatBlock> CRIMSON_VAT = BLOCKS.registerBlock("crimson_vat", VatBlock::new, blockBuilder(Blocks.CRIMSON_PLANKS.defaultMapColor()));
	public static final DeferredBlock<VatBlock> WARPED_VAT = BLOCKS.registerBlock("warped_vat", VatBlock::new, blockBuilder(Blocks.WARPED_PLANKS.defaultMapColor()));

	//Tiles
	public static final Supplier<BlockEntityType<VatBlockEntity>> VAT_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("vat", () -> new BlockEntityType<>(VatBlockEntity::new,
			OAK_VAT.get(), SPRUCE_VAT.get(), BIRCH_VAT.get(), JUNGLE_VAT.get(), ACACIA_VAT.get(), DARK_OAK_VAT.get(), CRIMSON_VAT.get(), MANGROVE_VAT.get(), WARPED_VAT.get()));

	//Items
	public static final DeferredItem<TooltipBlockItem> IRON_OREBERRY_BUSH_ITEM = ITEMS.registerItem("iron_oreberry_bush", (properties) -> new TooltipBlockItem(IRON_OREBERRY_BUSH.get(), properties, Reference.IRON_TOOLTIP), itemBuilder());
	public static final DeferredItem<TooltipBlockItem> GOLD_OREBERRY_BUSH_ITEM = ITEMS.registerItem("gold_oreberry_bush", (properties) -> new TooltipBlockItem(GOLD_OREBERRY_BUSH.get(), properties, Reference.GOLD_TOOLTIP), itemBuilder());
	public static final DeferredItem<TooltipBlockItem> COPPER_OREBERRY_BUSH_ITEM = ITEMS.registerItem("copper_oreberry_bush", (properties) -> new TooltipBlockItem(COPPER_OREBERRY_BUSH.get(), properties, Reference.COPPER_TOOLTIP), itemBuilder());
	public static final DeferredItem<TooltipBlockItem> TIN_OREBERRY_BUSH_ITEM = ITEMS.registerItem("tin_oreberry_bush", (properties) -> new TooltipBlockItem(TIN_OREBERRY_BUSH.get(), properties, Reference.TIN_TOOLTIP), itemBuilder());
	public static final DeferredItem<TooltipBlockItem> ALUMINUM_OREBERRY_BUSH_ITEM = ITEMS.registerItem("aluminum_oreberry_bush", (properties) -> new TooltipBlockItem(ALUMINUM_OREBERRY_BUSH.get(), properties, Reference.ALUMINUM_TOOLTIP), itemBuilder());
	public static final DeferredItem<TooltipBlockItem> LEAD_OREBERRY_BUSH_ITEM = ITEMS.registerItem("lead_oreberry_bush", (properties) -> new TooltipBlockItem(LEAD_OREBERRY_BUSH.get(), properties, Reference.LEAD_TOOLTIP), itemBuilder());
	public static final DeferredItem<TooltipBlockItem> NICKEL_OREBERRY_BUSH_ITEM = ITEMS.registerItem("nickel_oreberry_bush", (properties) -> new TooltipBlockItem(NICKEL_OREBERRY_BUSH.get(), properties, Reference.NICKEL_TOOLTIP), itemBuilder());
	public static final DeferredItem<TooltipBlockItem> URANIUM_OREBERRY_BUSH_ITEM = ITEMS.registerItem("uranium_oreberry_bush", (properties) -> new TooltipBlockItem(URANIUM_OREBERRY_BUSH.get(), properties, Reference.URANIUM_TOOLTIP), itemBuilder());
	public static final DeferredItem<TooltipBlockItem> OSMIUM_OREBERRY_BUSH_ITEM = ITEMS.registerItem("osmium_oreberry_bush", (properties) -> new TooltipBlockItem(OSMIUM_OREBERRY_BUSH.get(), properties, Reference.OSMIUM_TOOLTIP), itemBuilder());
	public static final DeferredItem<TooltipBlockItem> ZINC_OREBERRY_BUSH_ITEM = ITEMS.registerItem("zinc_oreberry_bush", (properties) -> new TooltipBlockItem(ZINC_OREBERRY_BUSH.get(), properties, Reference.ZINC_TOOLTIP), itemBuilder());
	public static final DeferredItem<TooltipBlockItem> SILVER_OREBERRY_BUSH_ITEM = ITEMS.registerItem("silver_oreberry_bush", (properties) -> new TooltipBlockItem(SILVER_OREBERRY_BUSH.get(), properties, Reference.SILVER_TOOLTIP), itemBuilder());
	public static final DeferredItem<TooltipBlockItem> ESSENCE_BERRY_BUSH_ITEM = ITEMS.registerItem("essence_berry_bush", (properties) -> new TooltipBlockItem(ESSENCE_BERRY_BUSH.get(), properties, Reference.ESSENCE_TOOLTIP), itemBuilder());

	public static final DeferredItem<OreBerryItem> IRON_OREBERRY = ITEMS.registerItem("iron_oreberry", (properties) -> new OreBerryItem(properties, Reference.IRON_TOOLTIP), itemBuilder());
	public static final DeferredItem<OreBerryItem> GOLD_OREBERRY = ITEMS.registerItem("gold_oreberry", (properties) -> new OreBerryItem(properties, Reference.GOLD_TOOLTIP), itemBuilder());
	public static final DeferredItem<OreBerryItem> COPPER_OREBERRY = ITEMS.registerItem("copper_oreberry", (properties) -> new OreBerryItem(properties, Reference.COPPER_TOOLTIP), itemBuilder());
	public static final DeferredItem<OreBerryItem> TIN_OREBERRY = ITEMS.registerItem("tin_oreberry", (properties) -> new OreBerryItem(properties, Reference.TIN_TOOLTIP), itemBuilder());
	public static final DeferredItem<OreBerryItem> ALUMINUM_OREBERRY = ITEMS.registerItem("aluminum_oreberry", (properties) -> new OreBerryItem(properties, Reference.ALUMINUM_TOOLTIP), itemBuilder());
	public static final DeferredItem<OreBerryItem> LEAD_OREBERRY = ITEMS.registerItem("lead_oreberry", (properties) -> new OreBerryItem(properties, Reference.LEAD_TOOLTIP), itemBuilder());
	public static final DeferredItem<OreBerryItem> NICKEL_OREBERRY = ITEMS.registerItem("nickel_oreberry", (properties) -> new OreBerryItem(properties, Reference.NICKEL_TOOLTIP), itemBuilder());
	public static final DeferredItem<OreBerryItem> URANIUM_OREBERRY = ITEMS.registerItem("uranium_oreberry", (properties) -> new OreBerryItem(properties, Reference.URANIUM_TOOLTIP), itemBuilder());
	public static final DeferredItem<OreBerryItem> OSMIUM_OREBERRY = ITEMS.registerItem("osmium_oreberry", (properties) -> new OreBerryItem(properties, Reference.OSMIUM_TOOLTIP), itemBuilder());
	public static final DeferredItem<OreBerryItem> ZINC_OREBERRY = ITEMS.registerItem("zinc_oreberry", (properties) -> new OreBerryItem(properties, Reference.ZINC_TOOLTIP), itemBuilder());
	public static final DeferredItem<OreBerryItem> SILVER_OREBERRY = ITEMS.registerItem("silver_oreberry", (properties) -> new OreBerryItem(properties, Reference.SILVER_TOOLTIP), itemBuilder());
	public static final DeferredItem<OreBerryItem> ESSENCE_BERRY = ITEMS.registerItem("essence_berry", (properties) -> new EssenceBerryItem(properties, Reference.ESSENCE_TOOLTIP), itemBuilder());

	public static final DeferredItem<Item> COPPER_NUGGET = ITEMS.registerItem("copper_nugget", (properties) -> new Item(properties), itemBuilder());

	public static final DeferredItem<BlockItem> OAK_VAT_ITEM = ITEMS.registerSimpleBlockItem(OAK_VAT);
	public static final DeferredItem<BlockItem> SPRUCE_VAT_ITEM = ITEMS.registerSimpleBlockItem(SPRUCE_VAT);
	public static final DeferredItem<BlockItem> BIRCH_VAT_ITEM = ITEMS.registerSimpleBlockItem(BIRCH_VAT);
	public static final DeferredItem<BlockItem> JUNGLE_VAT_ITEM = ITEMS.registerSimpleBlockItem(JUNGLE_VAT);
	public static final DeferredItem<BlockItem> ACACIA_VAT_ITEM = ITEMS.registerSimpleBlockItem(ACACIA_VAT);
	public static final DeferredItem<BlockItem> DARK_OAK_VAT_ITEM = ITEMS.registerSimpleBlockItem(DARK_OAK_VAT);
	public static final DeferredItem<BlockItem> MANGROVE_VAT_ITEM = ITEMS.registerSimpleBlockItem(MANGROVE_VAT);
	public static final DeferredItem<BlockItem> CHERRY_VAT_ITEM = ITEMS.registerSimpleBlockItem(CHERRY_VAT);
	public static final DeferredItem<BlockItem> CRIMSON_VAT_ITEM = ITEMS.registerSimpleBlockItem(CRIMSON_VAT);
	public static final DeferredItem<BlockItem> WARPED_VAT_ITEM = ITEMS.registerSimpleBlockItem(WARPED_VAT);

	private static BlockBehaviour.Properties blockBuilder(MapColor mapColor) {
		return BlockBehaviour.Properties.of().mapColor(mapColor).sound(SoundType.SWEET_BERRY_BUSH).noOcclusion().isSuffocating(OreBerryBushBlock::isntSolid).isViewBlocking(OreBerryBushBlock::isntSolid);
	}

	private static BlockBehaviour.Properties potBuilder() {
		return BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
	}

	private static Item.Properties itemBuilder() {
		return new Item.Properties();
	}

	public static void registerBlockData() {
		registerPottablePlant(IRON_OREBERRY_BUSH, POTTED_IRON_OREBERRY_BUSH);
		registerPottablePlant(GOLD_OREBERRY_BUSH, POTTED_GOLD_OREBERRY_BUSH);
		registerPottablePlant(COPPER_OREBERRY_BUSH, POTTED_COPPER_OREBERRY_BUSH);
		registerPottablePlant(TIN_OREBERRY_BUSH, POTTED_TIN_OREBERRY_BUSH);
		registerPottablePlant(ALUMINUM_OREBERRY_BUSH, POTTED_ALUMINUM_OREBERRY_BUSH);
		registerPottablePlant(LEAD_OREBERRY_BUSH, POTTED_LEAD_OREBERRY_BUSH);
		registerPottablePlant(NICKEL_OREBERRY_BUSH, POTTED_NICKEL_OREBERRY_BUSH);
		registerPottablePlant(URANIUM_OREBERRY_BUSH, POTTED_URANIUM_OREBERRY_BUSH);
		registerPottablePlant(OSMIUM_OREBERRY_BUSH, POTTED_OSMIUM_OREBERRY_BUSH);
		registerPottablePlant(ZINC_OREBERRY_BUSH, POTTED_ZINC_OREBERRY_BUSH);
		registerPottablePlant(SILVER_OREBERRY_BUSH, POTTED_SILVER_OREBERRY_BUSH);
		registerPottablePlant(ESSENCE_BERRY_BUSH, POTTED_ESSENCE_BERRY_BUSH);
	}

	private static void registerPottablePlant(Supplier<OreBerryBushBlock> plant, Supplier<FlowerPotBlock> pottedPlant) {
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(plant.get())), pottedPlant);
	}

	public static void load() {
		//NOP
	}
}
