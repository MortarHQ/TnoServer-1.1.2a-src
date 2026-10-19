package com.github.wal_bos.moreskills.world.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

public final class ArsWeissCloneScanBudget {
   private static final Map<UUID, ArsWeissCloneScanBudget.Budget> BUDGETS = new HashMap<>();
   private static long lastPruneTick;
   private static final int MAX_POINTS_PER_TICK = 4;

   private ArsWeissCloneScanBudget() {
   }

   public static boolean tryUse(UUID owner, long gameTime, int cost) {
      if (owner == null) {
         return false;
      }

      ArsWeissCloneScanBudget.Budget budget = BUDGETS.computeIfAbsent(owner, ignored -> new ArsWeissCloneScanBudget.Budget(gameTime));
      if (budget.tick != gameTime) {
         budget.tick = gameTime;
         budget.used = 0;
      }

      int amount = Math.max(1, cost);
      if (budget.used + amount > 4) {
         return false;
      }

      budget.used += amount;
      if (gameTime - lastPruneTick > 1200L) {
         prune(gameTime);
      }

      return true;
   }

   private static void prune(long gameTime) {
      lastPruneTick = gameTime;
      Iterator<Entry<UUID, ArsWeissCloneScanBudget.Budget>> iterator = BUDGETS.entrySet().iterator();

      while (iterator.hasNext()) {
         if (gameTime - iterator.next().getValue().tick > 200L) {
            iterator.remove();
         }
      }
   }

   private static final class Budget {
      private long tick;
      private int used;

      private Budget(long tick) {
         this.tick = tick;
      }
   }
}
