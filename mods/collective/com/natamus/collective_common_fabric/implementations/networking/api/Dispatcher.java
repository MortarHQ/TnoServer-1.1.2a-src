package com.natamus.collective_common_fabric.implementations.networking.api;

import java.util.List;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import net.minecraft.class_2818;
import net.minecraft.class_3215;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.server.MinecraftServer;

public class Dispatcher {
   public static <T> void sendToServer(T packet) {
      Network.getNetworkHandler().sendToServer(packet);
   }

   public static <T> void sendToServer(T packet, boolean ignoreCheck) {
      Network.getNetworkHandler().sendToServer(packet, ignoreCheck);
   }

   public static <T> void sendToClient(T packet, class_3222 player) {
      Network.getNetworkHandler().sendToClient(packet, player);
   }

   public static <T> void sendToClients(T packet, List<class_3222> players) {
      for (class_3222 player : players) {
         sendToClient(packet, player);
      }
   }

   public static <T> void sendToAllClients(T packet, MinecraftServer server) {
      sendToClients(packet, server.method_3760().method_14571());
   }

   public static <T> void sendToClientsInLevel(T packet, class_3218 level) {
      sendToClients(packet, level.method_18456());
   }

   public static <T> void sendToClientsLoadingChunk(T packet, class_2818 chunk) {
      class_3215 chunkCache = (class_3215)chunk.method_12200().method_8398();
      sendToClients(packet, chunkCache.field_17254.method_17210(chunk.method_12004(), false));
   }

   public static <T> void sendToClientsLoadingPos(T packet, class_3218 level, class_1923 pos) {
      sendToClientsLoadingChunk(packet, level.method_8497(pos.field_9181, pos.field_9180));
   }

   public static <T> void sendToClientsLoadingPos(T packet, class_3218 level, class_2338 pos) {
      sendToClientsLoadingPos(packet, level, new class_1923(pos));
   }

   public static <T> void sendToClientsInRange(T packet, class_3218 level, class_2338 pos, double range) {
      for (class_3222 player : level.method_18456()) {
         if (player.method_5649(pos.method_10263(), pos.method_10264(), pos.method_10260()) <= range * range) {
            sendToClient(packet, player);
         }
      }
   }
}
