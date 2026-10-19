package com.tensura_tno.registry;

import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlock;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;

public final class TensuraTNOBackpacks {
   private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, "tensura_tno");
   public static final DeferredHolder<Item, BackpackItem> LOW_MAGISTEEL_BACKPACK = registerBackpack(
      "low_magisteel_backpack", 144, 8, TensuraTNOBlocks.LOW_MAGISTEEL_BACKPACK, false
   );
   public static final DeferredHolder<Item, BackpackItem> HIGH_MAGISTEEL_BACKPACK = registerBackpack(
      "high_magisteel_backpack", 180, 8, TensuraTNOBlocks.HIGH_MAGISTEEL_BACKPACK, false
   );
   public static final DeferredHolder<Item, BackpackItem> PURE_MAGISTEEL_BACKPACK = registerBackpack(
      "pure_magisteel_backpack", 288, 9, TensuraTNOBlocks.PURE_MAGISTEEL_BACKPACK, true
   );
   public static final DeferredHolder<Item, BackpackItem> ADAMANTITE_BACKPACK = registerBackpack(
      "adamantite_backpack", 324, 10, TensuraTNOBlocks.ADAMANTITE_BACKPACK, true
   );
   public static final DeferredHolder<Item, BackpackItem> HIHIIROKANE_BACKPACK = registerBackpack(
      "hihiirokane_backpack", 384, 10, TensuraTNOBlocks.HIHIIROKANE_BACKPACK, true
   );

   private TensuraTNOBackpacks() {
   }

   public static void register(IEventBus modBus) {
      ITEMS.register(modBus);
   }

   private static DeferredHolder<Item, BackpackItem> registerBackpack(
      String name, int inventorySlots, int upgradeSlots, Supplier<BackpackBlock> blockSupplier, boolean fireResistant
   ) {
      return ITEMS.register(
         name,
         () -> fireResistant
            ? new BackpackItem(() -> inventorySlots, () -> upgradeSlots, blockSupplier, Properties::fireResistant)
            : new BackpackItem(() -> inventorySlots, () -> upgradeSlots, blockSupplier)
      );
   }
}
