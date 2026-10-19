package net.p3pp3rf1y.sophisticatedbackpacks.data;

import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;

public class SBInjectLootSubProvider implements LootTableSubProvider {
   private static final String INJECT_FOLDER = "inject/";
   public static final ResourceKey<LootTable> ABANDONED_MINESHAFT = createInjectLootTableRegistryKey(BuiltInLootTables.ABANDONED_MINESHAFT);
   public static final ResourceKey<LootTable> BASTION_TREASURE = createInjectLootTableRegistryKey(BuiltInLootTables.BASTION_TREASURE);
   public static final ResourceKey<LootTable> DESERT_PYRAMID = createInjectLootTableRegistryKey(BuiltInLootTables.DESERT_PYRAMID);
   public static final ResourceKey<LootTable> END_CITY_TREASURE = createInjectLootTableRegistryKey(BuiltInLootTables.END_CITY_TREASURE);
   public static final ResourceKey<LootTable> NETHER_BRIDGE = createInjectLootTableRegistryKey(BuiltInLootTables.NETHER_BRIDGE);
   public static final ResourceKey<LootTable> SHIPWRECK_TREASURE = createInjectLootTableRegistryKey(BuiltInLootTables.SHIPWRECK_TREASURE);
   public static final ResourceKey<LootTable> SIMPLE_DUNGEON = createInjectLootTableRegistryKey(BuiltInLootTables.SIMPLE_DUNGEON);
   public static final ResourceKey<LootTable> WOODLAND_MANSION = createInjectLootTableRegistryKey(BuiltInLootTables.WOODLAND_MANSION);
   public static final ResourceKey<LootTable> SPAWN_BONUS_CHEST = createInjectLootTableRegistryKey(BuiltInLootTables.SPAWN_BONUS_CHEST);
   public static final Set<ResourceKey<LootTable>> ALL_TABLES = Set.of(
      ABANDONED_MINESHAFT,
      BASTION_TREASURE,
      DESERT_PYRAMID,
      END_CITY_TREASURE,
      NETHER_BRIDGE,
      SHIPWRECK_TREASURE,
      SIMPLE_DUNGEON,
      WOODLAND_MANSION,
      SPAWN_BONUS_CHEST
   );

   private static ResourceKey<LootTable> createInjectLootTableRegistryKey(ResourceKey<LootTable> vanillaLootTable) {
      ResourceLocation location = ResourceLocation.fromNamespaceAndPath("sophisticatedbackpacks", "inject/" + vanillaLootTable.location().getPath());
      return ResourceKey.create(Registries.LOOT_TABLE, location);
   }

   public SBInjectLootSubProvider(Provider registries) {
   }

   public void generate(BiConsumer<ResourceKey<LootTable>, Builder> tables) {
      tables.accept(SPAWN_BONUS_CHEST, getLootTable(0, this.getItemLootEntry((Item)ModItems.BACKPACK.get(), 100)));
      tables.accept(
         SIMPLE_DUNGEON,
         getLootTable(
            90,
            this.getItemLootEntry((Item)ModItems.BACKPACK.get(), 5),
            this.getItemLootEntry((Item)ModItems.COPPER_BACKPACK.get(), 3),
            this.getItemLootEntry((Item)ModItems.PICKUP_UPGRADE.get(), 2)
         )
      );
      tables.accept(
         ABANDONED_MINESHAFT,
         getLootTable(
            84,
            this.getItemLootEntry((Item)ModItems.BACKPACK.get(), 7),
            this.getItemLootEntry((Item)ModItems.COPPER_BACKPACK.get(), 5),
            this.getItemLootEntry((Item)ModItems.IRON_BACKPACK.get(), 3),
            this.getItemLootEntry((Item)ModItems.GOLD_BACKPACK.get(), 1),
            this.getItemLootEntry((Item)ModItems.MAGNET_UPGRADE.get(), 2)
         )
      );
      tables.accept(
         DESERT_PYRAMID,
         getLootTable(
            89,
            this.getItemLootEntry((Item)ModItems.COPPER_BACKPACK.get(), 5),
            this.getItemLootEntry((Item)ModItems.IRON_BACKPACK.get(), 3),
            this.getItemLootEntry((Item)ModItems.GOLD_BACKPACK.get(), 1),
            this.getItemLootEntry((Item)ModItems.MAGNET_UPGRADE.get(), 2)
         )
      );
      tables.accept(
         SHIPWRECK_TREASURE,
         getLootTable(
            92,
            this.getItemLootEntry((Item)ModItems.IRON_BACKPACK.get(), 4),
            this.getItemLootEntry((Item)ModItems.GOLD_BACKPACK.get(), 2),
            this.getItemLootEntry((Item)ModItems.ADVANCED_MAGNET_UPGRADE.get(), 2)
         )
      );
      tables.accept(
         WOODLAND_MANSION,
         getLootTable(
            92,
            this.getItemLootEntry((Item)ModItems.IRON_BACKPACK.get(), 4),
            this.getItemLootEntry((Item)ModItems.GOLD_BACKPACK.get(), 2),
            this.getItemLootEntry((Item)ModItems.ADVANCED_MAGNET_UPGRADE.get(), 2)
         )
      );
      tables.accept(
         NETHER_BRIDGE,
         getLootTable(
            90,
            this.getItemLootEntry((Item)ModItems.IRON_BACKPACK.get(), 5),
            this.getItemLootEntry((Item)ModItems.GOLD_BACKPACK.get(), 3),
            this.getItemLootEntry((Item)ModItems.FEEDING_UPGRADE.get(), 2)
         )
      );
      tables.accept(
         BASTION_TREASURE,
         getLootTable(
            90,
            this.getItemLootEntry((Item)ModItems.IRON_BACKPACK.get(), 3),
            this.getItemLootEntry((Item)ModItems.GOLD_BACKPACK.get(), 5),
            this.getItemLootEntry((Item)ModItems.FEEDING_UPGRADE.get(), 2)
         )
      );
      tables.accept(
         END_CITY_TREASURE,
         getLootTable(
            90,
            this.getItemLootEntry((Item)ModItems.DIAMOND_BACKPACK.get(), 3),
            this.getItemLootEntry((Item)ModItems.GOLD_BACKPACK.get(), 5),
            this.getItemLootEntry((Item)ModItems.ADVANCED_MAGNET_UPGRADE.get(), 2)
         )
      );
   }

   private net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder<?> getItemLootEntry(Item item, int weight) {
      return LootItem.lootTableItem(item).setWeight(weight);
   }

   private static Builder getLootTable(int emptyWeight, net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder<?>... entries) {
      net.minecraft.world.level.storage.loot.LootPool.Builder pool = LootPool.lootPool().name("main").setRolls(ConstantValue.exactly(1.0F));

      for (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder<?> entry : entries) {
         pool.add(entry);
      }

      pool.add(EmptyLootItem.emptyItem().setWeight(emptyWeight));
      return LootTable.lootTable().withPool(pool);
   }
}
