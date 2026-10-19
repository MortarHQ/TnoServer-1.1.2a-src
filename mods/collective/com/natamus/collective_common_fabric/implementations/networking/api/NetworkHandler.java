package com.natamus.collective_common_fabric.implementations.networking.api;

import java.util.List;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import net.minecraft.class_2818;
import net.minecraft.class_3215;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.server.MinecraftServer;

public interface NetworkHandler {
   <T> void sendToServer(T var1);

   <T> void sendToServer(T var1, boolean var2);

   <T> void sendToClient(T var1, class_3222 var2);

   default <T> void sendToClients(T packet, List<class_3222> players) {
      for (class_3222 player : players) {
         this.sendToClient(packet, player);
      }
   }

   default <T> void sendToAllClients(T packet, MinecraftServer server) {
      this.sendToClients(packet, server.method_3760().method_14571());
   }

   default <T> void sendToClientsInLevel(T packet, class_3218 level) {
      this.sendToClients(packet, level.method_18456());
   }

   default <T> void sendToClientsLoadingChunk(T packet, class_2818 chunk) {
      class_3215 chunkCache = (class_3215)chunk.method_12200().method_8398();
      this.sendToClients(packet, chunkCache.field_17254.method_17210(chunk.method_12004(), false));
   }

   default <T> void sendToClientsLoadingPos(T packet, class_3218 level, class_1923 pos) {
      this.sendToClientsLoadingChunk(packet, level.method_8497(pos.field_9181, pos.field_9180));
   }

   default <T> void sendToClientsLoadingPos(T packet, class_3218 level, class_2338 pos) {
      this.sendToClientsLoadingPos(packet, level, new class_1923(pos));
   }

   default <T> void sendToClientsInRange(T packet, class_3218 level, class_2338 pos, double range) {
      for (class_3222 player : level.method_18456()) {
         if (player.method_5649(pos.method_10263(), pos.method_10264(), pos.method_10260()) <= range * range) {
            this.sendToClient(packet, player);
         }
      }
   }
}
