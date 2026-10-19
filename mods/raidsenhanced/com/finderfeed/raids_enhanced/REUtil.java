package com.finderfeed.raids_enhanced;

import com.finderfeed.raids_enhanced.content.packets.REPosEventPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public class REUtil {
   public static final int GOLEM_SMACK = 1;
   public static final int LIGHTNING_DEBRIS = 2;
   public static final int DRILL_DEATH = 3;

   public static void drillDeath(ServerLevel serverLevel, Vec3 pos, double radius, int drillId) {
      posEvent(serverLevel, pos, 3, drillId, radius);
   }

   public static void golemSmackParticles(ServerLevel serverLevel, Vec3 pos, double radius) {
      posEvent(serverLevel, pos, 1, 0, radius);
   }

   public static void lightningDebris(ServerLevel serverLevel, Vec3 pos, double radius) {
      posEvent(serverLevel, pos, 2, 0, radius);
   }

   public static void posEvent(ServerLevel serverLevel, Vec3 pos, int event, int data, double radius) {
      PacketDistributor.sendToPlayersNear(serverLevel, null, pos.x, pos.y, pos.z, radius, new REPosEventPacket(pos, event, data), new CustomPacketPayload[0]);
   }

   public static Vec3 calculateMortarProjectileVelocity(Vec3 startPos, Vec3 endPos, double gravity, int tickTravelTime) {
      Vec3 between = endPos.subtract(startPos);
      double horizontalDistance = Math.sqrt(between.x * between.x + between.z * between.z);
      double d = between.y;
      double horizontalSpeed = horizontalDistance / tickTravelTime;
      double verticalSpeed = (gravity * tickTravelTime * tickTravelTime / 2.0 - d) / -tickTravelTime;
      return between.multiply(1.0, 0.0, 1.0).normalize().multiply(horizontalSpeed, 0.0, horizontalSpeed).add(0.0, verticalSpeed, 0.0);
   }
}
