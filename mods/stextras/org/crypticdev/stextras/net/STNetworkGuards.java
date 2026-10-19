package org.crypticdev.stextras.net;

import net.minecraft.server.level.ServerPlayer;

public final class STNetworkGuards {
   private STNetworkGuards() {
   }

   public static boolean canSendTo(ServerPlayer player) {
      return player != null && player.connection != null && !player.hasDisconnected() && !player.isRemoved();
   }

   public static String playerName(ServerPlayer player) {
      return player == null ? "null" : player.getGameProfile().getName() + "/" + player.getUUID();
   }
}
