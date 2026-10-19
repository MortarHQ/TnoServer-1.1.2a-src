package com.chronodawn.worldgen.protection;

import com.chronodawn.ChronoDawn;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class BlockProtectionHandler {
   private static final Map<String, BoundingBox> PROTECTED_AREAS = new ConcurrentHashMap<>();
   private static final Set<String> DEFEATED_BOSSES = ConcurrentHashMap.newKeySet();

   public static void registerProtectedArea(ServerLevel level, BoundingBox area, Object uniqueId) {
      String key = makeKey(level, uniqueId);
      PROTECTED_AREAS.put(key, area);
      ChronoDawn.LOGGER.debug("Registered protected area: {} with bounds {}", key, area);
   }

   public static void onBossDefeated(ServerLevel level, Object uniqueId) {
      String key = makeKey(level, uniqueId);
      DEFEATED_BOSSES.add(key);
      ChronoDawn.LOGGER.debug("Boss defeated, unprotected area: {}", key);
   }

   public static boolean isProtected(Level level, BlockPos pos) {
      String dimensionKey = level.dimension().location().toString();

      for (Entry<String, BoundingBox> entry : PROTECTED_AREAS.entrySet()) {
         String key = entry.getKey();
         BoundingBox area = entry.getValue();
         if (key.startsWith(dimensionKey + ":") && !DEFEATED_BOSSES.contains(key) && area.isInside(pos)) {
            return true;
         }
      }

      return false;
   }

   public static boolean onBossDefeatedAt(ServerLevel level, BlockPos bossPos) {
      String dimensionKey = level.dimension().location().toString();

      for (Entry<String, BoundingBox> entry : PROTECTED_AREAS.entrySet()) {
         String key = entry.getKey();
         BoundingBox area = entry.getValue();
         if (key.startsWith(dimensionKey + ":") && area.isInside(bossPos)) {
            DEFEATED_BOSSES.add(key);
            ChronoDawn.LOGGER.debug("Boss defeated at {}! Unprotected boss room: {}", bossPos, key);
            return true;
         }
      }

      ChronoDawn.LOGGER.warn("Boss defeated at {} but no protected area found containing this position", bossPos);
      return false;
   }

   private static String makeKey(ServerLevel level, Object uniqueId) {
      return level.dimension().location() + ":" + uniqueId.toString();
   }

   public static void reset() {
      PROTECTED_AREAS.clear();
      DEFEATED_BOSSES.clear();
      ChronoDawn.LOGGER.debug("Block Protection Handler reset");
   }
}
