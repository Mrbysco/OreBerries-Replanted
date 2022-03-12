package com.mrbysco.oreberriesreplanted.registry;

import com.mojang.serialization.Codec;
import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.block.OreBerryBushBlock;
import com.mrbysco.oreberriesreplanted.block.OreEnum;
import com.mrbysco.oreberriesreplanted.block.VatBlock;
import com.mrbysco.oreberriesreplanted.blockentity.VatBlockEntity;
import com.mrbysco.oreberriesreplanted.item.EssenceBerryItem;
import com.mrbysco.oreberriesreplanted.item.OreBerryItem;
import com.mrbysco.oreberriesreplanted.item.TooltipBlockItem;
import com.mrbysco.oreberriesreplanted.recipes.TagBlastingRecipe;
import com.mrbysco.oreberriesreplanted.recipes.TagFurnaceRecipe;
import com.mrbysco.oreberriesreplanted.recipes.TagFurnaceRecipe.Serializer;
import com.mrbysco.oreberriesreplanted.recipes.VatRecipe;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryBushFeature;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryBushFeatureConfig;
import com.mrbysco.oreberriesreplanted.worldgen.placement.ChanceRangePlacement;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Supplier;

public class OreBerryRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Reference.MOD_ID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Reference.MOD_ID);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MOD_ID);
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MOD_ID);

	public static final RegistryObject<Feature<OreBerryBushFeatureConfig>> OREBERRY_FEATURE_CONFIG = FEATURES.register("oreberry_bush", () ->
			new OreBerryBushFeature(OreBerryBushFeatureConfig.CODEC));

	public static final RegistryObject<Serializer> TAG_FURNACE_SERIALIZER = RECIPE_SERIALIZERS.register("furnace", TagFurnaceRecipe.Serializer::new);
	public static final RegistryObject<TagBlastingRecipe.Serializer> TAG_BLASTING_SERIALIZER = RECIPE_SERIALIZERS.register("blasting", TagBlastingRecipe.Serializer::new);
	public static final RegistryObject<VatRecipe.Serializer> VAT_SERIALIZER = RECIPE_SERIALIZERS.register("vat", VatRecipe.Serializer::new);

	//Fluids
	public static LiquidReg IRON_OREBERRY_JUICE = new LiquidReg("iron_oreberry_juice", Material.LAVA, 0xFFc9c9c9);
	public static LiquidReg GOLD_OREBERRY_JUICE = new LiquidReg("gold_oreberry_juice", Material.LAVA, 0xFFfad64a);
	public static LiquidReg COPPER_OREBERRY_JUICE = new LiquidReg("copper_oreberry_juice", Material.LAVA, 0xFFf8b18d);
	public static LiquidReg TIN_OREBERRY_JUICE = new LiquidReg("tin_oreberry_juice", Material.LAVA, 0xFF74609e);
	public static LiquidReg ALUMINUM_OREBERRY_JUICE = new LiquidReg("aluminum_oreberry_juice", Material.LAVA, 0xFFc5dbed);
	public static LiquidReg LEAD_OREBERRY_JUICE = new LiquidReg("lead_oreberry_juice", Material.LAVA, 0xFF707e8a);
	public static LiquidReg NICKEL_OREBERRY_JUICE = new LiquidReg("nickel_oreberry_juice", Material.LAVA, 0xFFb0b59f);
	public static LiquidReg URANIUM_OREBERRY_JUICE = new LiquidReg("uranium_oreberry_juice", Material.LAVA, 0xFF98b350);
	public static LiquidReg OSMIUM_OREBERRY_JUICE = new LiquidReg("osmium_oreberry_juice", Material.LAVA, 0xFF83b0bd);
	public static LiquidReg ZINC_OREBERRY_JUICE = new LiquidReg("zinc_oreberry_juice", Material.LAVA, 0xFFd1d1a5);
	public static LiquidReg SILVER_OREBERRY_JUICE = new LiquidReg("silver_oreberry_juice", Material.LAVA, 0xFF898fc9);

	//Blocks
	public static final RegistryObject<Block> IRON_OREBERRY_BUSH = BLOCKS.register("iron_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), OreBerryRegistry.IRON_OREBERRY, OreEnum.IRON));
	public static final RegistryObject<Block> GOLD_OREBERRY_BUSH = BLOCKS.register("gold_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), OreBerryRegistry.GOLD_OREBERRY, OreEnum.GOLD));
	public static final RegistryObject<Block> COPPER_OREBERRY_BUSH = BLOCKS.register("copper_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), OreBerryRegistry.COPPER_OREBERRY, OreEnum.COPPER));
	public static final RegistryObject<Block> TIN_OREBERRY_BUSH = BLOCKS.register("tin_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), OreBerryRegistry.TIN_OREBERRY, OreEnum.TIN));
	public static final RegistryObject<Block> ALUMINUM_OREBERRY_BUSH = BLOCKS.register("aluminum_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), OreBerryRegistry.ALUMINUM_OREBERRY, OreEnum.ALUMINUM));
	public static final RegistryObject<Block> LEAD_OREBERRY_BUSH = BLOCKS.register("lead_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), OreBerryRegistry.LEAD_OREBERRY, OreEnum.LEAD));
	public static final RegistryObject<Block> NICKEL_OREBERRY_BUSH = BLOCKS.register("nickel_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), OreBerryRegistry.NICKEL_OREBERRY, OreEnum.NICKEL));
	public static final RegistryObject<Block> URANIUM_OREBERRY_BUSH = BLOCKS.register("uranium_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), OreBerryRegistry.URANIUM_OREBERRY, OreEnum.URANIUM));
	public static final RegistryObject<Block> OSMIUM_OREBERRY_BUSH = BLOCKS.register("osmium_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), OreBerryRegistry.OSMIUM_OREBERRY, OreEnum.OSMIUM));
	public static final RegistryObject<Block> ZINC_OREBERRY_BUSH = BLOCKS.register("zinc_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), OreBerryRegistry.ZINC_OREBERRY, OreEnum.ZINC));
	public static final RegistryObject<Block> SILVER_OREBERRY_BUSH = BLOCKS.register("silver_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), OreBerryRegistry.SILVER_OREBERRY, OreEnum.SILVER));
	public static final RegistryObject<Block> ESSENCE_BERRY_BUSH = BLOCKS.register("essence_berry_bush", () -> new OreBerryBushBlock(blockBuilder(), OreBerryRegistry.ESSENCE_BERRY, OreEnum.ESSENCE));

	public static final RegistryObject<Block> POTTED_IRON_OREBERRY_BUSH = BLOCKS.register("potted_iron_oreberry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.IRON_OREBERRY_BUSH, potBuilder()));
	public static final RegistryObject<Block> POTTED_GOLD_OREBERRY_BUSH = BLOCKS.register("potted_gold_oreberry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.GOLD_OREBERRY_BUSH, potBuilder()));
	public static final RegistryObject<Block> POTTED_COPPER_OREBERRY_BUSH = BLOCKS.register("potted_copper_oreberry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.COPPER_OREBERRY_BUSH, potBuilder()));
	public static final RegistryObject<Block> POTTED_TIN_OREBERRY_BUSH = BLOCKS.register("potted_tin_oreberry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.TIN_OREBERRY_BUSH, potBuilder()));
	public static final RegistryObject<Block> POTTED_ALUMINUM_OREBERRY_BUSH = BLOCKS.register("potted_aluminum_oreberry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.ALUMINUM_OREBERRY_BUSH, potBuilder()));
	public static final RegistryObject<Block> POTTED_LEAD_OREBERRY_BUSH = BLOCKS.register("potted_lead_oreberry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.LEAD_OREBERRY_BUSH, potBuilder()));
	public static final RegistryObject<Block> POTTED_NICKEL_OREBERRY_BUSH = BLOCKS.register("potted_nickel_oreberry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.NICKEL_OREBERRY_BUSH, potBuilder()));
	public static final RegistryObject<Block> POTTED_URANIUM_OREBERRY_BUSH = BLOCKS.register("potted_uranium_oreberry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.URANIUM_OREBERRY_BUSH, potBuilder()));
	public static final RegistryObject<Block> POTTED_OSMIUM_OREBERRY_BUSH = BLOCKS.register("potted_osmium_oreberry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.OSMIUM_OREBERRY_BUSH, potBuilder()));
	public static final RegistryObject<Block> POTTED_ZINC_OREBERRY_BUSH = BLOCKS.register("potted_zinc_oreberry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.ZINC_OREBERRY_BUSH, potBuilder()));
	public static final RegistryObject<Block> POTTED_SILVER_OREBERRY_BUSH = BLOCKS.register("potted_silver_oreberry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.SILVER_OREBERRY_BUSH, potBuilder()));
	public static final RegistryObject<Block> POTTED_ESSENCE_BERRY_BUSH = BLOCKS.register("potted_essence_berry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OreBerryRegistry.ESSENCE_BERRY_BUSH, potBuilder()));

	public static final RegistryObject<Block> OAK_VAT = BLOCKS.register("oak_vat", () -> new VatBlock(blockBuilder()));
	public static final RegistryObject<Block> SPRUCE_VAT = BLOCKS.register("spruce_vat", () -> new VatBlock(blockBuilder()));
	public static final RegistryObject<Block> BIRCH_VAT = BLOCKS.register("birch_vat", () -> new VatBlock(blockBuilder()));
	public static final RegistryObject<Block> JUNGLE_VAT = BLOCKS.register("jungle_vat", () -> new VatBlock(blockBuilder()));
	public static final RegistryObject<Block> ACACIA_VAT = BLOCKS.register("acacia_vat", () -> new VatBlock(blockBuilder()));
	public static final RegistryObject<Block> DARK_OAK_VAT = BLOCKS.register("dark_oak_vat", () -> new VatBlock(blockBuilder()));
	public static final RegistryObject<Block> CRIMSON_VAT = BLOCKS.register("crimson_vat", () -> new VatBlock(blockBuilder()));
	public static final RegistryObject<Block> WARPED_VAT = BLOCKS.register("warped_vat", () -> new VatBlock(blockBuilder()));

	//Tiles
	public static final RegistryObject<BlockEntityType<VatBlockEntity>> VAT_BLOCK_ENTITY = BLOCK_ENTITIES.register("vat", () -> BlockEntityType.Builder.of(VatBlockEntity::new,
			OAK_VAT.get(), SPRUCE_VAT.get(), BIRCH_VAT.get(), JUNGLE_VAT.get(), ACACIA_VAT.get(), DARK_OAK_VAT.get(), CRIMSON_VAT.get(), WARPED_VAT.get()).build(null));

	//Items
	public static final RegistryObject<Item> IRON_OREBERRY_BUSH_ITEM = ITEMS.register("iron_oreberry_bush", () -> new TooltipBlockItem(IRON_OREBERRY_BUSH.get(), itemBuilder(), Reference.IRON_TOOLTIP));
	public static final RegistryObject<Item> GOLD_OREBERRY_BUSH_ITEM = ITEMS.register("gold_oreberry_bush", () -> new TooltipBlockItem(GOLD_OREBERRY_BUSH.get(), itemBuilder(), Reference.GOLD_TOOLTIP));
	public static final RegistryObject<Item> COPPER_OREBERRY_BUSH_ITEM = ITEMS.register("copper_oreberry_bush", () -> new TooltipBlockItem(COPPER_OREBERRY_BUSH.get(), itemBuilder(), Reference.COPPER_TOOLTIP));
	public static final RegistryObject<Item> TIN_OREBERRY_BUSH_ITEM = ITEMS.register("tin_oreberry_bush", () -> new TooltipBlockItem(TIN_OREBERRY_BUSH.get(), itemBuilder(), Reference.TIN_TOOLTIP));
	public static final RegistryObject<Item> ALUMINUM_OREBERRY_BUSH_ITEM = ITEMS.register("aluminum_oreberry_bush", () -> new TooltipBlockItem(ALUMINUM_OREBERRY_BUSH.get(), itemBuilder(), Reference.ALUMINUM_TOOLTIP));
	public static final RegistryObject<Item> LEAD_OREBERRY_BUSH_ITEM = ITEMS.register("lead_oreberry_bush", () -> new TooltipBlockItem(LEAD_OREBERRY_BUSH.get(), itemBuilder(), Reference.LEAD_TOOLTIP));
	public static final RegistryObject<Item> NICKEL_OREBERRY_BUSH_ITEM = ITEMS.register("nickel_oreberry_bush", () -> new TooltipBlockItem(NICKEL_OREBERRY_BUSH.get(), itemBuilder(), Reference.NICKEL_TOOLTIP));
	public static final RegistryObject<Item> URANIUM_OREBERRY_BUSH_ITEM = ITEMS.register("uranium_oreberry_bush", () -> new TooltipBlockItem(URANIUM_OREBERRY_BUSH.get(), itemBuilder(), Reference.URANIUM_TOOLTIP));
	public static final RegistryObject<Item> OSMIUM_OREBERRY_BUSH_ITEM = ITEMS.register("osmium_oreberry_bush", () -> new TooltipBlockItem(OSMIUM_OREBERRY_BUSH.get(), itemBuilder(), Reference.OSMIUM_TOOLTIP));
	public static final RegistryObject<Item> ZINC_OREBERRY_BUSH_ITEM = ITEMS.register("zinc_oreberry_bush", () -> new TooltipBlockItem(ZINC_OREBERRY_BUSH.get(), itemBuilder(), Reference.ZINC_TOOLTIP));
	public static final RegistryObject<Item> SILVER_OREBERRY_BUSH_ITEM = ITEMS.register("silver_oreberry_bush", () -> new TooltipBlockItem(SILVER_OREBERRY_BUSH.get(), itemBuilder(), Reference.SILVER_TOOLTIP));
	public static final RegistryObject<Item> ESSENCE_BERRY_BUSH_ITEM = ITEMS.register("essence_berry_bush", () -> new TooltipBlockItem(ESSENCE_BERRY_BUSH.get(), itemBuilder(), Reference.ESSENCE_TOOLTIP));

	public static final RegistryObject<Item> IRON_OREBERRY = ITEMS.register("iron_oreberry", () -> new OreBerryItem(itemBuilder(), Reference.IRON_TOOLTIP));
	public static final RegistryObject<Item> GOLD_OREBERRY = ITEMS.register("gold_oreberry", () -> new OreBerryItem(itemBuilder(), Reference.GOLD_TOOLTIP));
	public static final RegistryObject<Item> COPPER_OREBERRY = ITEMS.register("copper_oreberry", () -> new OreBerryItem(itemBuilder(), Reference.COPPER_TOOLTIP));
	public static final RegistryObject<Item> TIN_OREBERRY = ITEMS.register("tin_oreberry", () -> new OreBerryItem(itemBuilder(), Reference.TIN_TOOLTIP));
	public static final RegistryObject<Item> ALUMINUM_OREBERRY = ITEMS.register("aluminum_oreberry", () -> new OreBerryItem(itemBuilder(), Reference.ALUMINUM_TOOLTIP));
	public static final RegistryObject<Item> LEAD_OREBERRY = ITEMS.register("lead_oreberry", () -> new OreBerryItem(itemBuilder(), Reference.LEAD_TOOLTIP));
	public static final RegistryObject<Item> NICKEL_OREBERRY = ITEMS.register("nickel_oreberry", () -> new OreBerryItem(itemBuilder(), Reference.NICKEL_TOOLTIP));
	public static final RegistryObject<Item> URANIUM_OREBERRY = ITEMS.register("uranium_oreberry", () -> new OreBerryItem(itemBuilder(), Reference.URANIUM_TOOLTIP));
	public static final RegistryObject<Item> OSMIUM_OREBERRY = ITEMS.register("osmium_oreberry", () -> new OreBerryItem(itemBuilder(), Reference.OSMIUM_TOOLTIP));
	public static final RegistryObject<Item> ZINC_OREBERRY = ITEMS.register("zinc_oreberry", () -> new OreBerryItem(itemBuilder(), Reference.ZINC_TOOLTIP));
	public static final RegistryObject<Item> SILVER_OREBERRY = ITEMS.register("silver_oreberry", () -> new OreBerryItem(itemBuilder(), Reference.SILVER_TOOLTIP));
	public static final RegistryObject<Item> ESSENCE_BERRY = ITEMS.register("essence_berry", () -> new EssenceBerryItem(itemBuilder(), Reference.ESSENCE_TOOLTIP));

	public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(itemBuilder()));

	public static final RegistryObject<Item> OAK_VAT_ITEM = ITEMS.register("oak_vat", () -> new BlockItem(OAK_VAT.get(), itemBuilder()));
	public static final RegistryObject<Item> SPRUCE_VAT_ITEM = ITEMS.register("spruce_vat", () -> new BlockItem(SPRUCE_VAT.get(), itemBuilder()));
	public static final RegistryObject<Item> BIRCH_VAT_ITEM = ITEMS.register("birch_vat", () -> new BlockItem(BIRCH_VAT.get(), itemBuilder()));
	public static final RegistryObject<Item> JUNGLE_VAT_ITEM = ITEMS.register("jungle_vat", () -> new BlockItem(JUNGLE_VAT.get(), itemBuilder()));
	public static final RegistryObject<Item> ACACIA_VAT_ITEM = ITEMS.register("acacia_vat", () -> new BlockItem(ACACIA_VAT.get(), itemBuilder()));
	public static final RegistryObject<Item> DARK_OAK_VAT_ITEM = ITEMS.register("dark_oak_vat", () -> new BlockItem(DARK_OAK_VAT.get(), itemBuilder()));
	public static final RegistryObject<Item> CRIMSON_VAT_ITEM = ITEMS.register("crimson_vat", () -> new BlockItem(CRIMSON_VAT.get(), itemBuilder()));
	public static final RegistryObject<Item> WARPED_VAT_ITEM = ITEMS.register("warped_vat", () -> new BlockItem(WARPED_VAT.get(), itemBuilder()));

	private static BlockBehaviour.Properties blockBuilder() {
		return BlockBehaviour.Properties.of(Material.LEAVES).sound(SoundType.SWEET_BERRY_BUSH).noOcclusion().isSuffocating(OreBerryBushBlock::isntSolid).isViewBlocking(OreBerryBushBlock::isntSolid);
	}

	private static BlockBehaviour.Properties potBuilder() {
		return BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion();
	}

	private static Item.Properties itemBuilder() {
		return new Item.Properties().tab(OreBerryTab.TAB);
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

	private static void registerPottablePlant(Supplier<Block> plant, Supplier<Block> pottedPlant) {
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(plant.get().getRegistryName()), pottedPlant);
	}
}
