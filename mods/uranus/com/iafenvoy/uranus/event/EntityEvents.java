package com.iafenvoy.uranus.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class EntityEvents {
   public static final Event<EntityEvents.JoinWorld> ON_JOIN_WORLD = new Event<>(callbacks -> (entity, world) -> {
      for (EntityEvents.JoinWorld callback : callbacks) {
         if (!callback.onJoinWorld(entity, world)) {
            return true;
         }
      }

      return false;
   });
   public static final Event<EntityEvents.Tracking> START_TRACKING_TAIL = new Event<>(callbacks -> (entity, player) -> {
      for (EntityEvents.Tracking callback : callbacks) {
         callback.onTrackingStart(entity, player);
      }
   });

   @FunctionalInterface
   public interface JoinWorld {
      boolean onJoinWorld(Entity var1, Level var2);
   }

   @FunctionalInterface
   public interface Tracking {
      void onTrackingStart(Entity var1, ServerPlayer var2);
   }
}
