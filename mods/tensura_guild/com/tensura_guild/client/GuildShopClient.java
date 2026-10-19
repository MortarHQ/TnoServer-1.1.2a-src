package com.tensura_guild.client;

import com.tensura_guild.client.screen.GuildShopScreen;
import com.tensura_guild.network.GuildShopLimitReachedPayload;
import com.tensura_guild.network.GuildShopStatePayload;
import net.minecraft.client.Minecraft;

public final class GuildShopClient {
   private GuildShopClient() {
   }

   public static void handleLimitReached(GuildShopLimitReachedPayload payload) {
      if (Minecraft.getInstance().screen instanceof GuildShopScreen screen) {
         screen.handleLimitReached(payload.rankLevel(), payload.slot());
      }
   }

   public static void handleState(GuildShopStatePayload payload) {
      if (Minecraft.getInstance().screen instanceof GuildShopScreen screen) {
         screen.handleShopState(payload);
      }
   }

   public static void handleRefresh() {
      if (Minecraft.getInstance().screen instanceof GuildShopScreen screen) {
         screen.handleShopRefresh();
      }
   }
}
