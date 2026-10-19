package org.crypticdev.ancient_artifacts.attachments;

import net.minecraft.world.entity.player.Player;
import org.crypticdev.ancient_artifacts.platform.PlatformCombatHelper;

public class CombatTracker {
   private static final int COMBAT_DURATION_TICKS = 100;

   public static void enterCombat(Player player) {
      long currentTick = player.tickCount;
      if (!isInCombat(player)) {
         setFirstCombatTick(player, currentTick);
      }

      setLastCombatTick(player, currentTick);
   }

   public static boolean isInCombat(Player player) {
      long lastCombatTick = getLastCombatTick(player);
      return player.tickCount - lastCombatTick < 100L && lastCombatTick > 0L;
   }

   public static void setLastCombatTick(Player player, long tick) {
      PlatformCombatHelper.setLastCombatTick(player, tick);
   }

   public static void setFirstCombatTick(Player player, long tick) {
      PlatformCombatHelper.setFirstCombatTick(player, tick);
   }

   public static long getLastCombatTick(Player player) {
      return PlatformCombatHelper.getLastCombatTick(player);
   }

   public static long getFirstCombatTick(Player player) {
      return PlatformCombatHelper.getFirstCombatTick(player);
   }
}
