package com.craisinlord.apple_cows.neoforge.registry;

import net.minecraft.world.item.Item.Properties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public final class AppleCowsNeoforgeItems {
   private static final Items ITEMS = DeferredRegister.createItems("apple_cows");
   public static final DeferredItem<DeferredSpawnEggItem> APPLE_COW_SPAWN_EGG = ITEMS.register(
      "apple_cow_spawn_egg", () -> new DeferredSpawnEggItem(AppleCowsNeoforgeEntities.APPLE_COW, 16718362, 3342130, new Properties())
   );
   public static final DeferredItem<DeferredSpawnEggItem> GOLDEN_APPLE_COW_SPAWN_EGG = ITEMS.register(
      "golden_apple_cow_spawn_egg", () -> new DeferredSpawnEggItem(AppleCowsNeoforgeEntities.GOLDEN_APPLE_COW, 16769354, 3342130, new Properties())
   );
   public static final DeferredItem<DeferredSpawnEggItem> ENCHANTED_GOLDEN_APPLE_COW_SPAWN_EGG = ITEMS.register(
      "enchanted_golden_apple_cow_spawn_egg",
      () -> new DeferredSpawnEggItem(AppleCowsNeoforgeEntities.ENCHANTED_GOLDEN_APPLE_COW, 7356598, 16769354, new Properties())
   );

   private AppleCowsNeoforgeItems() {
   }

   public static void register(IEventBus modEventBus) {
      ITEMS.register(modEventBus);
   }
}
