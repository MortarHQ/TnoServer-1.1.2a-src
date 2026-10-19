package com.github.wal_bos.moreskills.registry.block;

import com.github.wal_bos.moreskills.world.block.BlackBloodMireBlock;
import com.github.wal_bos.moreskills.world.block.BloodAltarBlock;
import com.github.wal_bos.moreskills.world.block.BloodFlowerCoreBlock;
import com.github.wal_bos.moreskills.world.block.BloodFlowerPetalBlock;
import com.github.wal_bos.moreskills.world.block.BloodFlowerRootBlock;
import com.github.wal_bos.moreskills.world.block.BloodWorldHeartCoreBlock;
import com.github.wal_bos.moreskills.world.block.InfectedBloodBlock;
import com.github.wal_bos.moreskills.world.block.InfectedGrassBlock;
import com.github.wal_bos.moreskills.world.block.InfectedLeavesBlock;
import com.github.wal_bos.moreskills.world.block.InfectedWoodBlock;
import com.github.wal_bos.moreskills.world.block.PathsBlock;
import com.github.wal_bos.moreskills.world.block.PathsPortalBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public final class ModBlocks {
   public static final Blocks BLOCKS = DeferredRegister.createBlocks("tensuramoreskills");
   public static final Items ITEMS = DeferredRegister.createItems("tensuramoreskills");
   public static final DeferredBlock<Block> PATHS_BLOCK = BLOCKS.register(
      "paths_block",
      () -> new PathsBlock(Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(3.0F, 6.0F).sound(SoundType.SAND).requiresCorrectToolForDrops())
   );
   public static final DeferredBlock<Block> PATHS_PORTAL = BLOCKS.register(
      "paths_portal",
      () -> new PathsPortalBlock(
         Properties.of()
            .mapColor(MapColor.COLOR_PURPLE)
            .noCollission()
            .noLootTable()
            .strength(-1.0F, 3600000.0F)
            .lightLevel(state -> 11)
            .sound(SoundType.GLASS)
      )
   );
   public static final DeferredBlock<Block> BLACK_BLOOD_MIRE = BLOCKS.register(
      "black_blood_mire",
      () -> new BlackBloodMireBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.NETHERRACK)
            .mapColor(MapColor.COLOR_BLACK)
            .strength(1.2F, 4.0F)
            .sound(SoundType.NETHER_WART)
            .randomTicks()
      )
   );
   public static final DeferredBlock<Block> INFECTED_GRASS = BLOCKS.register(
      "infected_grass",
      () -> new InfectedGrassBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.GRASS_BLOCK).mapColor(MapColor.COLOR_BLACK).randomTicks())
   );
   public static final DeferredBlock<Block> INFECTED_DIRT = BLOCKS.register(
      "infected_dirt",
      () -> new InfectedBloodBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DIRT).mapColor(MapColor.COLOR_BLACK).randomTicks())
   );
   public static final DeferredBlock<Block> INFECTED_WOOD = BLOCKS.register(
      "infected_wood",
      () -> new InfectedWoodBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_LOG).mapColor(MapColor.COLOR_BLACK).randomTicks())
   );
   public static final DeferredBlock<Block> INFECTED_LEAVES = BLOCKS.register(
      "infected_leaves",
      () -> new InfectedLeavesBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_BLACK).randomTicks())
   );
   public static final DeferredBlock<Block> BLOOD_ALTAR = BLOCKS.register(
      "blood_altar",
      () -> new BloodAltarBlock(Properties.of().mapColor(MapColor.COLOR_RED).strength(12.0F, 1200.0F).sound(SoundType.DEEPSLATE_BRICKS).noOcclusion())
   );
   public static final DeferredBlock<Block> BLOOD_FLOWER_ROOT = BLOCKS.register(
      "blood_flower_root",
      () -> new BloodFlowerRootBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.SCULK)
            .mapColor(MapColor.COLOR_BLACK)
            .strength(8.0F, 900.0F)
            .sound(SoundType.SCULK)
            .randomTicks()
      )
   );
   public static final DeferredBlock<Block> BLOOD_FLOWER_PETAL = BLOCKS.register(
      "blood_flower_petal",
      () -> new BloodFlowerPetalBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.NETHER_WART_BLOCK)
            .mapColor(MapColor.COLOR_RED)
            .strength(6.0F, 600.0F)
            .sound(SoundType.NETHER_WART)
            .noOcclusion()
            .randomTicks()
      )
   );
   public static final DeferredBlock<Block> BLOOD_FLOWER_CORE = BLOCKS.register(
      "blood_flower_core",
      () -> new BloodFlowerCoreBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.RESPAWN_ANCHOR)
            .mapColor(MapColor.COLOR_RED)
            .strength(35.0F, 3600.0F)
            .sound(SoundType.DEEPSLATE_BRICKS)
            .lightLevel(state -> 7)
            .noOcclusion()
      )
   );
   public static final DeferredBlock<Block> BLOOD_WORLD_HEART_CORE = BLOCKS.register(
      "blood_world_heart_core",
      () -> new BloodWorldHeartCoreBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.REINFORCED_DEEPSLATE)
            .mapColor(MapColor.COLOR_RED)
            .strength(120.0F, 12000.0F)
            .sound(SoundType.ANCIENT_DEBRIS)
            .lightLevel(state -> 12)
            .noOcclusion()
      )
   );
   public static final DeferredItem<Item> PATHS_BLOCK_ITEM = blockItem("paths_block", PATHS_BLOCK);
   public static final DeferredItem<Item> BLACK_BLOOD_MIRE_ITEM = blockItem("black_blood_mire", BLACK_BLOOD_MIRE);
   public static final DeferredItem<Item> INFECTED_GRASS_ITEM = blockItem("infected_grass", INFECTED_GRASS);
   public static final DeferredItem<Item> INFECTED_DIRT_ITEM = blockItem("infected_dirt", INFECTED_DIRT);
   public static final DeferredItem<Item> INFECTED_WOOD_ITEM = blockItem("infected_wood", INFECTED_WOOD);
   public static final DeferredItem<Item> INFECTED_LEAVES_ITEM = blockItem("infected_leaves", INFECTED_LEAVES);
   public static final DeferredItem<Item> BLOOD_ALTAR_ITEM = blockItem("blood_altar", BLOOD_ALTAR);
   public static final DeferredItem<Item> BLOOD_FLOWER_ROOT_ITEM = blockItem("blood_flower_root", BLOOD_FLOWER_ROOT);
   public static final DeferredItem<Item> BLOOD_FLOWER_PETAL_ITEM = blockItem("blood_flower_petal", BLOOD_FLOWER_PETAL);
   public static final DeferredItem<Item> BLOOD_FLOWER_CORE_ITEM = blockItem("blood_flower_core", BLOOD_FLOWER_CORE);
   public static final DeferredItem<Item> BLOOD_WORLD_HEART_CORE_ITEM = blockItem("blood_world_heart_core", BLOOD_WORLD_HEART_CORE);

   private ModBlocks() {
   }

   private static DeferredItem<Item> blockItem(String id, DeferredBlock<? extends Block> block) {
      return ITEMS.register(id, () -> new BlockItem((Block)block.get(), new net.minecraft.world.item.Item.Properties()));
   }

   public static void init(IEventBus modBus) {
      BLOCKS.register(modBus);
      ITEMS.register(modBus);
      modBus.addListener(ModBlocks::buildCreativeTabContents);
   }

   private static void buildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
      if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
         event.accept((ItemLike)PATHS_BLOCK_ITEM.get());
         event.accept((ItemLike)BLACK_BLOOD_MIRE_ITEM.get());
         event.accept((ItemLike)INFECTED_GRASS_ITEM.get());
         event.accept((ItemLike)INFECTED_DIRT_ITEM.get());
         event.accept((ItemLike)INFECTED_WOOD_ITEM.get());
         event.accept((ItemLike)INFECTED_LEAVES_ITEM.get());
         event.accept((ItemLike)BLOOD_ALTAR_ITEM.get());
         event.accept((ItemLike)BLOOD_FLOWER_ROOT_ITEM.get());
         event.accept((ItemLike)BLOOD_FLOWER_PETAL_ITEM.get());
         event.accept((ItemLike)BLOOD_FLOWER_CORE_ITEM.get());
         event.accept((ItemLike)BLOOD_WORLD_HEART_CORE_ITEM.get());
      }
   }
}
