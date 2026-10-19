package com.chronodawn.worldgen.protection;

import com.chronodawn.ChronoDawn;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class PermanentProtectionHandler {
   private static final Map<String, BoundingBox> PROTECTED_AREAS = new ConcurrentHashMap<>();

   public static void registerProtectedArea(ServerLevel level, BoundingBox area, Object uniqueId) {
      String key = makeKey(level, uniqueId);
      PROTECTED_AREAS.put(key, area);
      ChronoDawn.LOGGER.debug("Registered permanent protected area: {} with bounds {}", key, area);
   }

   public static boolean isProtected(Level level, BlockPos pos) {
      String dimensionKey = level.dimension().location().toString();

      for (Entry<String, BoundingBox> entry : PROTECTED_AREAS.entrySet()) {
         String key = entry.getKey();
         BoundingBox area = entry.getValue();
         if (key.startsWith(dimensionKey + ":") && area.isInside(pos)) {
            return true;
         }
      }

      return false;
   }

   private static String makeKey(ServerLevel level, Object uniqueId) {
      return level.dimension().location() + ":" + uniqueId.toString();
   }

   public static void reset() {
      PROTECTED_AREAS.clear();
      ChronoDawn.LOGGER.debug("Permanent Protection Handler reset");
   }
}
