package com.chronodawn.core.dimension;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatAdvancementHelper;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.data.ChronoDawnGlobalState;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class DimensionStabilizer {
   public static void stabilizeDimension(ServerLevel level) {
      MinecraftServer server = level.getServer();
      ChronoDawnGlobalState globalState = ChronoDawnGlobalState.get(server);
      if (globalState.isTyrantDefeated()) {
         ChronoDawn.LOGGER.debug("Time Tyrant already defeated, skipping stabilization");
      } else {
         globalState.markTyrantDefeated();
         ChronoDawn.LOGGER.debug("ChronoDawn dimension stabilized after Time Tyrant defeat");
         grantTimeTyrantDefeatAdvancement(server);
         Component message = Component.translatable("message.chronodawn.tyrant_defeated")
            .withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD});

         for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            player.displayClientMessage(message, false);
            Component title = Component.translatable("message.chronodawn.tyrant_defeated_title")
               .withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD});
            Component subtitle = Component.translatable("message.chronodawn.tyrant_defeated_subtitle").withStyle(ChatFormatting.YELLOW);
            player.connection.send(new ClientboundSetTitlesAnimationPacket(10, 70, 20));
            player.connection.send(new ClientboundSetTitleTextPacket(title));
            player.connection.send(new ClientboundSetSubtitleTextPacket(subtitle));
         }
      }
   }

   public static boolean isTyrantDefeated(MinecraftServer server) {
      ChronoDawnGlobalState globalState = ChronoDawnGlobalState.get(server);
      return globalState.isTyrantDefeated();
   }

   private static void grantTimeTyrantDefeatAdvancement(MinecraftServer server) {
      ResourceLocation advancementId = CompatResourceLocation.create("chronodawn", "story/us3/time_tyrant_defeat");
      int grantedCount = 0;

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         if (CompatAdvancementHelper.grantAdvancement(server, player, advancementId)) {
            grantedCount++;
            ChronoDawn.LOGGER.debug("Granted Time Tyrant defeat advancement to player {}", player.getName().getString());
         }
      }

      ChronoDawn.LOGGER.debug("Granted Time Tyrant defeat advancement to {} online players", grantedCount);
   }
}
