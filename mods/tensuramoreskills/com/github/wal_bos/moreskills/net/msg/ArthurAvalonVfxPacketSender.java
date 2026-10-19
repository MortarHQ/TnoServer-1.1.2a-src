package com.github.wal_bos.moreskills.net.msg;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public final class ArthurAvalonVfxPacketSender {
   private ArthurAvalonVfxPacketSender() {
   }

   public static void send(ServerLevel level, CustomPacketPayload payload, Vec3 start, Vec3 end, float radius) {
      Vec3 center = start.add(end).scale(0.5);
      double range = Math.max(128.0, start.distanceTo(end) + Math.max(48.0, radius * 2.0));
      double rangeSqr = range * range;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(center.x, center.y, center.z) <= rangeSqr) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }
}
