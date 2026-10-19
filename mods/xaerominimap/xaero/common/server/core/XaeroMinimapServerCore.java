package xaero.common.server.core;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import xaero.common.IXaeroMinimap;

public class XaeroMinimapServerCore {
   public static IXaeroMinimap modMain;

   public static void onServerWorldInfo(Player player) {
      if (isModLoaded()) {
         modMain.getCommonEvents().onPlayerWorldJoin((ServerPlayer)player);
      }
   }

   public static boolean isModLoaded() {
      return modMain != null && (modMain.isLoadedServer() || modMain.isLoadedClient());
   }
}
