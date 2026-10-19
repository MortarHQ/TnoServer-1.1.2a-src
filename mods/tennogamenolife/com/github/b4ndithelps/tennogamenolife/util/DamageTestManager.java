package com.github.b4ndithelps.tennogamenolife.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DamageTestManager {
   private static final Map<UUID, Boolean> playerDamageTestModes = new HashMap<>();

   public static void setDamageTestMode(UUID playerUUID, boolean enabled) {
      playerDamageTestModes.put(playerUUID, enabled);
   }

   public static boolean isDamageTestModeEnabled(UUID playerUUID) {
      return playerDamageTestModes.getOrDefault(playerUUID, false);
   }
}
