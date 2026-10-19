package net.p3pp3rf1y.sophisticatedcore.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.util.SlotRange;

public class InventoryPartRegistry {
   private static final Map<String, IInventoryPartHandler.Factory> INVENTORY_PART_FACTORIES = new HashMap<>();

   private InventoryPartRegistry() {
   }

   public static void registerFactory(String name, IInventoryPartHandler.Factory factory) {
      INVENTORY_PART_FACTORIES.put(name, factory);
   }

   public static IInventoryPartHandler instantiatePart(
      String name, InventoryHandler parent, SlotRange slotRange, Supplier<MemorySettingsCategory> getMemorySettings
   ) {
      return INVENTORY_PART_FACTORIES.get(name).create(parent, slotRange, getMemorySettings);
   }

   static {
      registerFactory("default", (parent, slotRange, getMemorySettings) -> new IInventoryPartHandler.Default(parent, slotRange.numberOfSlots()));
   }
}
