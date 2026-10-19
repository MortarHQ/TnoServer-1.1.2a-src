package org.crypticdev.ancient_artifacts.platform.neoforge;

import net.minecraft.world.entity.player.Player;
import org.crypticdev.ancient_artifacts.attachments.CombatData;
import org.crypticdev.ancient_artifacts.neoforge.AncientArtifactsNeoForge;

public class PlatformCombatHelperImpl {
   public static void setLastCombatTick(Player player, long tick) {
      ((CombatData)player.getData(AncientArtifactsNeoForge.COMBAT_DATA)).setLastCombatTime(tick);
   }

   public static long getLastCombatTick(Player player) {
      return ((CombatData)player.getData(AncientArtifactsNeoForge.COMBAT_DATA)).getLastCombatTime();
   }

   public static void setFirstCombatTick(Player player, long tick) {
      ((CombatData)player.getData(AncientArtifactsNeoForge.COMBAT_DATA)).setFirstCombatTime(tick);
   }

   public static long getFirstCombatTick(Player player) {
      return ((CombatData)player.getData(AncientArtifactsNeoForge.COMBAT_DATA)).getFirstCombatTime();
   }
}
