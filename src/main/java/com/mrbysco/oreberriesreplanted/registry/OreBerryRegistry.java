package com.mrbysco.oreberriesreplanted.registry;

import com.mrbysco.oreberriesreplanted.Reference;
import com.mrbysco.oreberriesreplanted.block.OreBerryBushBlock;
import com.mrbysco.oreberriesreplanted.block.OreEnum;
import com.mrbysco.oreberriesreplanted.item.EssenceBerryItem;
import com.mrbysco.oreberriesreplanted.item.OreBerryItem;
import com.mrbysco.oreberriesreplanted.item.TooltipBlockItem;
import com.mrbysco.oreberriesreplanted.recipes.TagBlastingRecipe;
import com.mrbysco.oreberriesreplanted.recipes.TagFurnaceRecipe;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryBushFeature;
import com.mrbysco.oreberriesreplanted.worldgen.OreBerryBushFeatureConfig;
import com.mrbysco.oreberriesreplanted.worldgen.placement.ChanceRangePlacement;
import com.mrbysco.oreberriesreplanted.worldgen.placement.ChanceTopSolidRangeConfig;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class OreBerryRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MOD_ID);
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MOD_ID);
	public static final DeferredRegister<Placement<?>> DECORATORS = DeferredRegister.create(ForgeRegistries.DECORATORS, Reference.MOD_ID);

	public static final RegistryObject<Feature<OreBerryBushFeatureConfig>> OREBERRY_FEATURE_CONFIG = FEATURES.register("oreberry_bush", () -> new OreBerryBushFeature(OreBerryBushFeatureConfig.CODEC));
	public static final RegistryObject<ChanceRangePlacement> CAVE_EDGE_RANGE = DECORATORS.register("change_range", () -> new ChanceRangePlacement(ChanceTopSolidRangeConfig.CODEC));

	public static final RegistryObject<TagFurnaceRecipe.Serializer> TAG_FURNACE_SERIALIZER = RECIPE_SERIALIZERS.register("furnace", TagFurnaceRecipe.Serializer::new);
	public static final RegistryObject<TagBlastingRecipe.Serializer> TAG_BLASTING_SERIALIZER = RECIPE_SERIALIZERS.register("blasting", TagBlastingRecipe.Serializer::new);

	public static final RegistryObject<Block> IRON_OREBERRY_BUSH = BLOCKS.register("iron_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), () -> OreBerryRegistry.IRON_OREBERRY.get(), OreEnum.IRON));
	public static final RegistryObject<Item> IRON_OREBERRY_BUSH_ITEM = ITEMS.register("iron_oreberry_bush", () -> new TooltipBlockItem(IRON_OREBERRY_BUSH.get(), itemBuilder(), Reference.IRON_TOOLTIP));
	public static final RegistryObject<Block> GOLD_OREBERRY_BUSH = BLOCKS.register("gold_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), () -> OreBerryRegistry.GOLD_OREBERRY.get(), OreEnum.GOLD));
	public static final RegistryObject<Item> GOLD_OREBERRY_BUSH_ITEM = ITEMS.register("gold_oreberry_bush", () -> new TooltipBlockItem(GOLD_OREBERRY_BUSH.get(), itemBuilder(), Reference.GOLD_TOOLTIP));
	public static final RegistryObject<Block> COPPER_OREBERRY_BUSH = BLOCKS.register("copper_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), () -> OreBerryRegistry.COPPER_OREBERRY.get(), OreEnum.COPPER));
	public static final RegistryObject<Item> COPPER_OREBERRY_BUSH_ITEM = ITEMS.register("copper_oreberry_bush", () -> new TooltipBlockItem(COPPER_OREBERRY_BUSH.get(), itemBuilder(), Reference.COPPER_TOOLTIP));
	public static final RegistryObject<Block> TIN_OREBERRY_BUSH = BLOCKS.register("tin_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), () -> OreBerryRegistry.TIN_OREBERRY.get(), OreEnum.TIN));
	public static final RegistryObject<Item> TIN_OREBERRY_BUSH_ITEM = ITEMS.register("tin_oreberry_bush", () -> new TooltipBlockItem(TIN_OREBERRY_BUSH.get(), itemBuilder(), Reference.TIN_TOOLTIP));
	public static final RegistryObject<Block> ALUMINUM_OREBERRY_BUSH = BLOCKS.register("aluminum_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), () -> OreBerryRegistry.ALUMINUM_OREBERRY.get(), OreEnum.ALUMINUM));
	public static final RegistryObject<Item> ALUMINUM_OREBERRY_BUSH_ITEM = ITEMS.register("aluminum_oreberry_bush", () -> new TooltipBlockItem(TIN_OREBERRY_BUSH.get(), itemBuilder(), Reference.ALUMINUM_TOOLTIP));
	public static final RegistryObject<Block> LEAD_OREBERRY_BUSH = BLOCKS.register("lead_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), () -> OreBerryRegistry.LEAD_OREBERRY.get(), OreEnum.LEAD));
	public static final RegistryObject<Item> LEAD_OREBERRY_BUSH_ITEM = ITEMS.register("lead_oreberry_bush", () -> new TooltipBlockItem(LEAD_OREBERRY_BUSH.get(), itemBuilder(), Reference.LEAD_TOOLTIP));
	public static final RegistryObject<Block> NICKEL_OREBERRY_BUSH = BLOCKS.register("nickel_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), () -> OreBerryRegistry.NICKEL_OREBERRY.get(), OreEnum.NICKEL));
	public static final RegistryObject<Item> NICKEL_OREBERRY_BUSH_ITEM = ITEMS.register("nickel_oreberry_bush", () -> new TooltipBlockItem(NICKEL_OREBERRY_BUSH.get(), itemBuilder(), Reference.NICKEL_TOOLTIP));
	public static final RegistryObject<Block> URANIUM_OREBERRY_BUSH = BLOCKS.register("uranium_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), () -> OreBerryRegistry.URANIUM_OREBERRY.get(), OreEnum.URANIUM));
	public static final RegistryObject<Item> URANIUM_OREBERRY_BUSH_ITEM = ITEMS.register("uranium_oreberry_bush", () -> new TooltipBlockItem(URANIUM_OREBERRY_BUSH.get(), itemBuilder(), Reference.URANIUM_TOOLTIP));
	public static final RegistryObject<Block> OSMIUM_OREBERRY_BUSH = BLOCKS.register("osmium_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), () -> OreBerryRegistry.OSMIUM_OREBERRY.get(), OreEnum.OSMIUM));
	public static final RegistryObject<Item> OSMIUM_OREBERRY_BUSH_ITEM = ITEMS.register("osmium_oreberry_bush", () -> new TooltipBlockItem(OSMIUM_OREBERRY_BUSH.get(), itemBuilder(), Reference.OSMIUM_TOOLTIP));
	public static final RegistryObject<Block> ZINC_OREBERRY_BUSH = BLOCKS.register("zinc_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), () -> OreBerryRegistry.ZINC_OREBERRY.get(), OreEnum.ZINC));
	public static final RegistryObject<Item> ZINC_OREBERRY_BUSH_ITEM = ITEMS.register("zinc_oreberry_bush", () -> new TooltipBlockItem(ZINC_OREBERRY_BUSH.get(), itemBuilder(), Reference.ZINC_TOOLTIP));
	public static final RegistryObject<Block> SILVER_OREBERRY_BUSH = BLOCKS.register("silver_oreberry_bush", () -> new OreBerryBushBlock(blockBuilder(), () -> OreBerryRegistry.SILVER_OREBERRY.get(), OreEnum.SILVER));
	public static final RegistryObject<Item> SILVER_OREBERRY_BUSH_ITEM = ITEMS.register("silver_oreberry_bush", () -> new TooltipBlockItem(SILVER_OREBERRY_BUSH.get(), itemBuilder(), Reference.SILVER_TOOLTIP));
	public static final RegistryObject<Block> ESSENCE_BERRY_BUSH = BLOCKS.register("essence_berry_bush", () -> new OreBerryBushBlock(blockBuilder(), () -> OreBerryRegistry.ESSENCE_BERRY.get(), OreEnum.ESSENCE));
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

	private static Block.Properties blockBuilder() {
		return Block.Properties.create(Material.LEAVES).notSolid().setSuffocates(OreBerryBushBlock::isntSolid).setBlocksVision(OreBerryBushBlock::isntSolid);
	}

	private static Item.Properties itemBuilder() {
		return new Item.Properties().group(OreBerryTab.TAB);
	}
}
