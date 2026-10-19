package org.crypticdev.ancient_artifacts.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import net.minecraft.world.entity.player.Player;
import org.crypticdev.ancient_artifacts.platform.neoforge.PlatformCombatHelperImpl;

public class PlatformCombatHelper {
   @ExpectPlatform
   @Transformed
   public static void setLastCombatTick(Player player, long tick) {
      PlatformCombatHelperImpl.setLastCombatTick(player, tick);
   }

   @ExpectPlatform
   @Transformed
   public static long getLastCombatTick(Player player) {
      return PlatformCombatHelperImpl.getLastCombatTick(player);
   }

   @ExpectPlatform
   @Transformed
   public static void setFirstCombatTick(Player player, long tick) {
      PlatformCombatHelperImpl.setFirstCombatTick(player, tick);
   }

   @ExpectPlatform
   @Transformed
   public static long getFirstCombatTick(Player player) {
      return PlatformCombatHelperImpl.getFirstCombatTick(player);
   }
}
