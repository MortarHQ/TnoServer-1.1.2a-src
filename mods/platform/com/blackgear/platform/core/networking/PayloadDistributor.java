package com.blackgear.platform.core.networking;

import com.blackgear.platform.core.networking.neoforge.PayloadDistributorImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import org.jetbrains.annotations.Nullable;

public class PayloadDistributor {
   @ExpectPlatform
   @Transformed
   public static void sendToServer(CustomPacketPayload packetPayload) {
      PayloadDistributorImpl.sendToServer(packetPayload);
   }

   @ExpectPlatform
   @Transformed
   public static void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
      PayloadDistributorImpl.sendToPlayer(player, payload);
   }

   @ExpectPlatform
   @Transformed
   public static void sendToPlayersInDimension(ServerLevel level, CustomPacketPayload payload) {
      PayloadDistributorImpl.sendToPlayersInDimension(level, payload);
   }

   @ExpectPlatform
   @Transformed
   public static void sendToPlayersNear(
      ServerLevel level, @Nullable ServerPlayer excluded, double x, double y, double z, double radius, CustomPacketPayload payload
   ) {
      PayloadDistributorImpl.sendToPlayersNear(level, excluded, x, y, z, radius, payload);
   }

   @ExpectPlatform
   @Transformed
   public static void sendToAllPlayers(CustomPacketPayload payload) {
      PayloadDistributorImpl.sendToAllPlayers(payload);
   }

   @ExpectPlatform
   @Transformed
   public static void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload payload) {
      PayloadDistributorImpl.sendToPlayersTrackingEntity(entity, payload);
   }

   @ExpectPlatform
   @Transformed
   public static void sendToPlayersTrackingChunk(ServerLevel level, ChunkPos pos, CustomPacketPayload payload) {
      PayloadDistributorImpl.sendToPlayersTrackingChunk(level, pos, payload);
   }
}
