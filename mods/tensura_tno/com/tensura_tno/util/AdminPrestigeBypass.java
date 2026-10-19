package com.tensura_tno.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AdminPrestigeBypass {
   private static final Set<UUID> BYPASS_SET = Collections.synchronizedSet(new HashSet<>());

   public static void enable(UUID uuid) {
      BYPASS_SET.add(uuid);
   }

   public static void disable(UUID uuid) {
      BYPASS_SET.remove(uuid);
   }

   public static boolean isEnabled(UUID uuid) {
      return BYPASS_SET.contains(uuid);
   }
}
