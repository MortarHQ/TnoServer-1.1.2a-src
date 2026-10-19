package com.natamus.collective_common_neoforge.functions;

import com.natamus.collective_common_neoforge.events.CollectiveEvents;
import java.util.ArrayList;
import java.util.WeakHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class SpawnEntityFunctions {
   public static void spawnEntityOnNextTick(ServerLevel serverlevel, Entity entity) {
      CollectiveEvents.entitiesToSpawn.computeIfAbsent(serverlevel, k -> new ArrayList<>()).add(entity);
   }

   public static void startRidingEntityOnNextTick(ServerLevel serverlevel, Entity ridden, Entity rider) {
      CollectiveEvents.entitiesToRide.computeIfAbsent(serverlevel, k -> new WeakHashMap<>()).put(ridden, rider);
   }
}
