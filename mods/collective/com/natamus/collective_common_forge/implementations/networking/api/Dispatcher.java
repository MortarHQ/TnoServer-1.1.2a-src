package com.natamus.collective_common_forge.implementations.networking.api;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

public class Dispatcher {
   public static <T> void sendToServer(T packet) {
      Network.getNetworkHandler().sendToServer(packet);
   }

   public static <T> void sendToServer(T packet, boolean ignoreCheck) {
      Network.getNetworkHandler().sendToServer(packet, ignoreCheck);
   }

   public static <T> void sendToClient(T packet, ServerPlayer player) {
      Network.getNetworkHandler().sendToClient(packet, player);
   }

   public static <T> void sendToClients(T packet, List<ServerPlayer> players) {
      for (ServerPlayer player : players) {
         sendToClient(packet, player);
      }
   }

   public static <T> void sendToAllClients(T packet, MinecraftServer server) {
      sendToClients(packet, server.getPlayerList().getPlayers());
   }

   public static <T> void sendToClientsInLevel(T packet, ServerLevel level) {
      sendToClients(packet, level.players());
   }

   public static <T> void sendToClientsLoadingChunk(T packet, LevelChunk chunk) {
      ServerChunkCache chunkCache = (ServerChunkCache)chunk.getLevel().getChunkSource();
      sendToClients(packet, chunkCache.chunkMap.getPlayers(chunk.getPos(), false));
   }

   public static <T> void sendToClientsLoadingPos(T packet, ServerLevel level, ChunkPos pos) {
      sendToClientsLoadingChunk(packet, level.getChunk(pos.x, pos.z));
   }

   public static <T> void sendToClientsLoadingPos(T packet, ServerLevel level, BlockPos pos) {
      sendToClientsLoadingPos(packet, level, new ChunkPos(pos));
   }

   public static <T> void sendToClientsInRange(T packet, ServerLevel level, BlockPos pos, double range) {
      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) <= range * range) {
            sendToClient(packet, player);
         }
      }
   }
}
