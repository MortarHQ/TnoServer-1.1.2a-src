package com.github.wal_bos.moreutils.handler;

import com.github.wal_bos.moreutils.data.MoreUtilsLeaderboardData;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;

public final class MoreUtilsLeaderboardHandler {
   private MoreUtilsLeaderboardHandler() {
   }

   public static void init() {
      NeoForge.EVENT_BUS.addListener(MoreUtilsLeaderboardHandler::onLogin);
      NeoForge.EVENT_BUS.addListener(MoreUtilsLeaderboardHandler::onLogout);
   }

   private static void onLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         MoreUtilsLeaderboardData.get(player.server).update(player);
      }
   }

   private static void onLogout(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         MoreUtilsLeaderboardData.get(player.server).update(player);
      }
   }
}
