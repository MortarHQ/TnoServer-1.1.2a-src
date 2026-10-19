package com.natamus.collective_common_fabric.functions;

import com.natamus.collective_common_fabric.events.CollectiveEvents;
import java.util.ArrayList;
import java.util.WeakHashMap;
import net.minecraft.class_1297;
import net.minecraft.class_3218;

public class SpawnEntityFunctions {
   public static void spawnEntityOnNextTick(class_3218 serverlevel, class_1297 entity) {
      CollectiveEvents.entitiesToSpawn.computeIfAbsent(serverlevel, k -> new ArrayList<>()).add(entity);
   }

   public static void startRidingEntityOnNextTick(class_3218 serverlevel, class_1297 ridden, class_1297 rider) {
      CollectiveEvents.entitiesToRide.computeIfAbsent(serverlevel, k -> new WeakHashMap<>()).put(ridden, rider);
   }
}
