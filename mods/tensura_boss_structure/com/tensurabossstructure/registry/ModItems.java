package com.tensurabossstructure.registry;

import com.tensurabossstructure.TensuraBossStructure;
import com.tensurabossstructure.item.SlimeKingSummonItem;
import com.tensurabossstructure.item.StructureMapItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, "tensura_boss_structure");
   public static final DeferredHolder<Item, BlockItem> RIMURU_OGRE_FIGHT_SPAWNER = ITEMS.register(
      "rimuru_ogre_fight_spawner", () -> new BlockItem((Block)ModBlocks.RIMURU_OGRE_FIGHT_SPAWNER.get(), new Properties())
   );
   public static final DeferredHolder<Item, BlockItem> HINATA_SPAWNER = ITEMS.register(
      "hinata_spawner", () -> new BlockItem((Block)ModBlocks.HINATA_SPAWNER.get(), new Properties())
   );
   public static final DeferredHolder<Item, BlockItem> ORC_DISASTER_SPAWNER = ITEMS.register(
      "orc_disaster_spawner", () -> new BlockItem((Block)ModBlocks.ORC_DISASTER_SPAWNER.get(), new Properties())
   );
   public static final DeferredHolder<Item, BlockItem> LUMINOUS_SPAWNER = ITEMS.register(
      "luminous_spawner", () -> new BlockItem((Block)ModBlocks.LUMINOUS_SPAWNER.get(), new Properties())
   );
   public static final DeferredHolder<Item, BlockItem> CARRION_SPAWNER = ITEMS.register(
      "carrion_spawner", () -> new BlockItem((Block)ModBlocks.CARRION_SPAWNER.get(), new Properties())
   );
   public static final DeferredHolder<Item, BlockItem> ELEMENTAL_COLOSSUS_SPAWNER = ITEMS.register(
      "elemental_colossus_spawner", () -> new BlockItem((Block)ModBlocks.ELEMENTAL_COLOSSUS_SPAWNER.get(), new Properties())
   );
   public static final DeferredHolder<Item, BlockItem> SHIZU_SPAWNER = ITEMS.register(
      "shizu_spawner", () -> new BlockItem((Block)ModBlocks.SHIZU_SPAWNER.get(), new Properties())
   );
   public static final DeferredHolder<Item, BlockItem> CHILD_OTHERWORLDER_SPAWNER = ITEMS.register(
      "child_otherworlder_spawner", () -> new BlockItem((Block)ModBlocks.CHILD_OTHERWORLDER_SPAWNER.get(), new Properties())
   );
   public static final DeferredHolder<Item, StructureMapItem> RIMURU_ARENA_MAP = ITEMS.register(
      "rimuru_arena_map",
      () -> new StructureMapItem(new Properties().stacksTo(1), "tensura_boss_structure:rimuru_ogre_fight", "filled_map.tensura_boss_structure.rimuru_arena")
   );
   public static final DeferredHolder<Item, StructureMapItem> HINATA_CHURCH_MAP = ITEMS.register(
      "hinata_church_map",
      () -> new StructureMapItem(new Properties().stacksTo(1), "tensura_boss_structure:hinata_church", "filled_map.tensura_boss_structure.hinata_church")
   );
   public static final DeferredHolder<Item, StructureMapItem> ORC_DISASTER_TEMPLE_MAP = ITEMS.register(
      "orc_disaster_temple_map",
      () -> new StructureMapItem(
         new Properties().stacksTo(1), "tensura_boss_structure:orc_disaster_temple", "filled_map.tensura_boss_structure.orc_disaster_temple"
      )
   );
   public static final DeferredHolder<Item, StructureMapItem> NIGHT_ROSE_MAP = ITEMS.register(
      "night_rose_map",
      () -> new StructureMapItem(new Properties().stacksTo(1), "tensura_boss_structure:night_rose", "filled_map.tensura_boss_structure.night_rose")
   );
   public static final DeferredHolder<Item, StructureMapItem> SHIZHU_SCHOOL_MAP = ITEMS.register(
      "shizhu_school_map",
      () -> new StructureMapItem(new Properties().stacksTo(1), "tensura_boss_structure:shizhu_school", "filled_map.tensura_boss_structure.shizhu_school")
   );
   public static final DeferredHolder<Item, StructureMapItem> BEAST_KINGDOM_OF_EURAZANIA_MAP = ITEMS.register(
      "beast_kingdom_of_eurazania_map",
      () -> new StructureMapItem(
         new Properties().stacksTo(1), "tensura_boss_structure:beast_kingdom_of_eurazania", "filled_map.tensura_boss_structure.beast_kingdom_of_eurazania"
      )
   );
   public static final DeferredHolder<Item, SlimeKingSummonItem> SLIME_KING_SUMMON = ITEMS.register(
      "slime_king_summon", () -> new SlimeKingSummonItem(new Properties().stacksTo(1))
   );

   public static void init(IEventBus bus) {
      ITEMS.register(bus);
      bus.addListener(ModItems::addToCreativeTabs);
      TensuraBossStructure.LOGGER.info("Items registered!");
   }

   private static void addToCreativeTabs(BuildCreativeModeTabContentsEvent event) {
      if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
         event.accept((ItemLike)RIMURU_ARENA_MAP.get());
         event.accept((ItemLike)HINATA_CHURCH_MAP.get());
         event.accept((ItemLike)ORC_DISASTER_TEMPLE_MAP.get());
         event.accept((ItemLike)NIGHT_ROSE_MAP.get());
         event.accept((ItemLike)SHIZHU_SCHOOL_MAP.get());
         event.accept((ItemLike)BEAST_KINGDOM_OF_EURAZANIA_MAP.get());
         event.accept((ItemLike)SLIME_KING_SUMMON.get());
      }
   }
}
