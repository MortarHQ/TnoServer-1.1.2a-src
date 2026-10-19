package com.github.wal_bos.moreskills.world.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

public final class ArsWeissCloneSearchGovernor {
   private static final Map<ArsWeissCloneSearchGovernor.Key, ArsWeissCloneSearchGovernor.Budget> BUDGETS = new HashMap<>();
   private static long lastPruneTick;

   private ArsWeissCloneSearchGovernor() {
   }

   public static boolean allow(UUID owner, String dimension, String channel, long gameTime, int intervalTicks, int maxPerWindow) {
      if (owner != null && channel != null && !channel.isBlank()) {
         int interval = Math.max(1, intervalTicks);
         int limit = Math.max(1, maxPerWindow);
         long window = Math.floorDiv(gameTime, interval);
         ArsWeissCloneSearchGovernor.Key key = new ArsWeissCloneSearchGovernor.Key(owner, dimension == null ? "" : dimension, channel);
         ArsWeissCloneSearchGovernor.Budget budget = BUDGETS.computeIfAbsent(key, ignored -> new ArsWeissCloneSearchGovernor.Budget(window));
         if (budget.window != window) {
            budget.window = window;
            budget.used = 0;
         }

         if (budget.used >= limit) {
            return false;
         }

         budget.used++;
         if (gameTime - lastPruneTick > 2400L) {
            prune(gameTime);
         }

         return true;
      } else {
         return false;
      }
   }

   private static void prune(long gameTime) {
      lastPruneTick = gameTime;
      long old = Math.floorDiv(gameTime, 2400L) - 2L;
      Iterator<Entry<ArsWeissCloneSearchGovernor.Key, ArsWeissCloneSearchGovernor.Budget>> iterator = BUDGETS.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<ArsWeissCloneSearchGovernor.Key, ArsWeissCloneSearchGovernor.Budget> entry = iterator.next();
         if (entry.getValue().window < old) {
            iterator.remove();
         }
      }
   }

   private static final class Budget {
      private long window;
      private int used;

      private Budget(long window) {
         this.window = window;
      }
   }

   private record Key(UUID owner, String dimension, String channel) {
   }
}
