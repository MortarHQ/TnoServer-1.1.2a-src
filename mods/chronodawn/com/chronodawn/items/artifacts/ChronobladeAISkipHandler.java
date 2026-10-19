package com.chronodawn.items.artifacts;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class ChronobladeAISkipHandler {
   private static final Map<UUID, Long> AI_SKIP_ENTITIES = new ConcurrentHashMap<>();
   public static final int AI_SKIP_DURATION = 20;

   public static void applyAISkip(LivingEntity entity) {
      if (entity instanceof Mob) {
         AI_SKIP_ENTITIES.put(entity.getUUID(), entity.level().getGameTime());
      }
   }

   public static boolean shouldSkipAI(LivingEntity entity) {
      if (entity instanceof Mob mob) {
         UUID uuid = entity.getUUID();
         Long appliedTime = AI_SKIP_ENTITIES.get(uuid);
         if (appliedTime == null) {
            return false;
         } else {
            long currentTime = entity.level().getGameTime();
            long elapsed = currentTime - appliedTime;
            if (elapsed > 20L) {
               AI_SKIP_ENTITIES.remove(uuid);
               return false;
            } else if (mob.getTarget() != null && mob.distanceToSqr(mob.getTarget()) < 9.0) {
               AI_SKIP_ENTITIES.remove(uuid);
               mob.setTarget(null);
               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   public static void cleanup(long currentTime) {
      AI_SKIP_ENTITIES.entrySet().removeIf(entry -> currentTime - entry.getValue() > 20L);
   }
}
