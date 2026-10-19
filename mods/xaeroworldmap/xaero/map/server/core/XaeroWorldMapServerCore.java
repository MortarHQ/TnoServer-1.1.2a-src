package xaero.map.server.core;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import xaero.map.WorldMap;

public class XaeroWorldMapServerCore {
   public static void onServerWorldInfo(Player player) {
      if (WorldMap.loaded) {
         WorldMap.commonEvents.onPlayerWorldJoin((ServerPlayer)player);
      }
   }
}
