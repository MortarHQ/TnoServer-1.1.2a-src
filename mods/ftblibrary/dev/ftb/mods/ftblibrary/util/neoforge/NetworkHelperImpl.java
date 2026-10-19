package dev.ftb.mods.ftblibrary.util.neoforge;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class NetworkHelperImpl {
   public static void sendToAll(Type<?> type, MinecraftServer server, Packet<?> packet) {
      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         sendTo(type, player, packet);
      }
   }

   public static void sendTo(Type<?> type, ServerPlayer player, Packet<?> packet) {
      if (player.connection.hasChannel(type)) {
         player.connection.send(packet);
      }
   }

   public static <T extends CustomPacketPayload> void sendToAll(MinecraftServer server, T packet) {
      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         sendTo(player, packet);
      }
   }

   public static <T extends CustomPacketPayload> void sendTo(ServerPlayer player, T packet) {
      if (player.connection.hasChannel(packet.type())) {
         NetworkManager.sendToPlayer(player, packet);
      }
   }
}
