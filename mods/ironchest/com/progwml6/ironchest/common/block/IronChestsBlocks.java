package com.progwml6.ironchest.common.block;

import com.progwml6.ironchest.common.block.regular.CopperChestBlock;
import com.progwml6.ironchest.common.block.regular.CrystalChestBlock;
import com.progwml6.ironchest.common.block.regular.DiamondChestBlock;
import com.progwml6.ironchest.common.block.regular.DirtChestBlock;
import com.progwml6.ironchest.common.block.regular.GoldChestBlock;
import com.progwml6.ironchest.common.block.regular.IronChestBlock;
import com.progwml6.ironchest.common.block.regular.ObsidianChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedCopperChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedCrystalChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedDiamondChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedDirtChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedGoldChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedIronChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedObsidianChestBlock;
import com.progwml6.ironchest.common.item.IronChestBlockItem;
import com.progwml6.ironchest.common.item.IronChestsItems;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class IronChestsBlocks {
   public static final Blocks BLOCKS = DeferredRegister.createBlocks("ironchest");
   public static final Items ITEMS = IronChestsItems.ITEMS;
   public static final DeferredBlock<IronChestBlock> IRON_CHEST = register(
      "iron_chest", () -> new IronChestBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F)), IronChestsTypes.IRON, false
   );
   public static final DeferredBlock<GoldChestBlock> GOLD_CHEST = register(
      "gold_chest", () -> new GoldChestBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F)), IronChestsTypes.GOLD, false
   );
   public static final DeferredBlock<DiamondChestBlock> DIAMOND_CHEST = register(
      "diamond_chest", () -> new DiamondChestBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F)), IronChestsTypes.DIAMOND, false
   );
   public static final DeferredBlock<CopperChestBlock> COPPER_CHEST = register(
      "copper_chest", () -> new CopperChestBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F)), IronChestsTypes.COPPER, false
   );
   public static final DeferredBlock<CrystalChestBlock> CRYSTAL_CHEST = register(
      "crystal_chest", () -> new CrystalChestBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F)), IronChestsTypes.CRYSTAL, false
   );
   public static final DeferredBlock<ObsidianChestBlock> OBSIDIAN_CHEST = register(
      "obsidian_chest", () -> new ObsidianChestBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F)), IronChestsTypes.OBSIDIAN, false
   );
   public static final DeferredBlock<DirtChestBlock> DIRT_CHEST = register(
      "dirt_chest", () -> new DirtChestBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F)), IronChestsTypes.DIRT, false
   );
   public static final DeferredBlock<TrappedIronChestBlock> TRAPPED_IRON_CHEST = register(
      "trapped_iron_chest", () -> new TrappedIronChestBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F)), IronChestsTypes.IRON, true
   );
   public static final DeferredBlock<TrappedGoldChestBlock> TRAPPED_GOLD_CHEST = register(
      "trapped_gold_chest", () -> new TrappedGoldChestBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F)), IronChestsTypes.GOLD, true
   );
   public static final DeferredBlock<TrappedDiamondChestBlock> TRAPPED_DIAMOND_CHEST = register(
      "trapped_diamond_chest", () -> new TrappedDiamondChestBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F)), IronChestsTypes.DIAMOND, true
   );
   public static final DeferredBlock<TrappedCopperChestBlock> TRAPPED_COPPER_CHEST = register(
      "trapped_copper_chest", () -> new TrappedCopperChestBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F)), IronChestsTypes.COPPER, true
   );
   public static final DeferredBlock<TrappedCrystalChestBlock> TRAPPED_CRYSTAL_CHEST = register(
      "trapped_crystal_chest", () -> new TrappedCrystalChestBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F)), IronChestsTypes.CRYSTAL, true
   );
   public static final DeferredBlock<TrappedObsidianChestBlock> TRAPPED_OBSIDIAN_CHEST = register(
      "trapped_obsidian_chest", () -> new TrappedObsidianChestBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F)), IronChestsTypes.OBSIDIAN, true
   );
   public static final DeferredBlock<TrappedDirtChestBlock> TRAPPED_DIRT_CHEST = register(
      "trapped_dirt_chest", () -> new TrappedDirtChestBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F)), IronChestsTypes.DIRT, true
   );

   private static <T extends Block> DeferredBlock<T> register(String name, Supplier<? extends T> sup, IronChestsTypes chestType, boolean trapped) {
      return register(name, sup, block -> item(block, chestType, trapped));
   }

   private static <T extends Block> DeferredBlock<T> register(
      String name, Supplier<? extends T> sup, Function<DeferredBlock<T>, Supplier<? extends Item>> itemCreator
   ) {
      DeferredBlock<T> ret = registerNoItem(name, sup);
      ITEMS.register(name, itemCreator.apply(ret));
      return ret;
   }

   private static <T extends Block> DeferredBlock<T> registerNoItem(String name, Supplier<? extends T> sup) {
      return BLOCKS.register(name, sup);
   }

   private static Supplier<BlockItem> item(DeferredBlock<? extends Block> block, IronChestsTypes chestType, Boolean trapped) {
      return () -> new IronChestBlockItem((Block)block.get(), new net.minecraft.world.item.Item.Properties(), chestType, trapped);
   }
}
