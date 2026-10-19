package com.tom.storagemod.util;

import com.tom.storagemod.inventory.IChangeTrackerAccess;
import com.tom.storagemod.inventory.IInventoryAccess;
import com.tom.storagemod.inventory.InventoryCableNetwork;
import com.tom.storagemod.inventory.InventoryChangeTracker;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;

public class WorldStates {
   public static Map<IItemHandler, IInventoryAccess.IInventoryChangeTracker> trackers = new WeakHashMap<>();
   public static Map<Level, InventoryCableNetwork> cableNetworks = new HashMap<>();

   public static void clearWorldStates() {
      trackers.clear();
      cableNetworks.clear();
   }

   public static IInventoryAccess.IInventoryChangeTracker getTracker(IItemHandler h) {
      return h instanceof IChangeTrackerAccess a ? a.tracker() : trackers.computeIfAbsent(h, InventoryChangeTracker::new);
   }
}
