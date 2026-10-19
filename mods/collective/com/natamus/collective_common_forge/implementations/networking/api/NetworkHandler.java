package com.natamus.collective_common_forge.implementations.networking.api;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

public interface NetworkHandler {
   <T> void sendToServer(T var1);

   <T> void sendToServer(T var1, boolean var2);

   <T> void sendToClient(T var1, ServerPlayer var2);

   default <T> void sendToClients(T packet, List<ServerPlayer> players) {
      for (ServerPlayer player : players) {
         this.sendToClient(packet, player);
      }
   }

   default <T> void sendToAllClients(T packet, MinecraftServer server) {
      this.sendToClients(packet, server.getPlayerList().getPlayers());
   }

   default <T> void sendToClientsInLevel(T packet, ServerLevel level) {
      this.sendToClients(packet, level.players());
   }

   default <T> void sendToClientsLoadingChunk(T packet, LevelChunk chunk) {
      ServerChunkCache chunkCache = (ServerChunkCache)chunk.getLevel().getChunkSource();
      this.sendToClients(packet, chunkCache.chunkMap.getPlayers(chunk.getPos(), false));
   }

   default <T> void sendToClientsLoadingPos(T packet, ServerLevel level, ChunkPos pos) {
      this.sendToClientsLoadingChunk(packet, level.getChunk(pos.x, pos.z));
   }

   default <T> void sendToClientsLoadingPos(T packet, ServerLevel level, BlockPos pos) {
      this.sendToClientsLoadingPos(packet, level, new ChunkPos(pos));
   }

   default <T> void sendToClientsInRange(T packet, ServerLevel level, BlockPos pos, double range) {
      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) <= range * range) {
            this.sendToClient(packet, player);
         }
      }
   }
}
