package net.unusual.block_factorys_bosses.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.network.PlayerPushMessage;

public interface SpatialUtil {
   static float rotateDegrees(float rotationDegrees, Rotation rotation) {
      return Mth.wrapDegrees(rotationDegrees) + switch (rotation) {
         case NONE -> 0.0F;
         case CLOCKWISE_90 -> 90.0F;
         case CLOCKWISE_180 -> 180.0F;
         case COUNTERCLOCKWISE_90 -> 270.0F;
         default -> throw new MatchException(null, null);
      };
   }

   static float mirrorDegrees(float rotationDegrees, Mirror mirror) {
      rotationDegrees = Mth.wrapDegrees(rotationDegrees);

      return switch (mirror) {
         case NONE -> rotationDegrees;
         case FRONT_BACK -> -rotationDegrees;
         case LEFT_RIGHT -> 180.0F - rotationDegrees;
         default -> throw new MatchException(null, null);
      };
   }

   static void pushEntity(Entity target, Vec3 force) {
      pushEntity(target, force, -1.0);
   }

   static void pushEntity(Entity target, Vec3 force, double speedLimit) {
      if (speedLimit >= 0.0) {
         Vec3 newMotion = target.getDeltaMovement().add(force);
         double entityDeltaMovementLength = newMotion.dot(force.normalize());
         if (entityDeltaMovementLength > speedLimit) {
            force = newMotion.normalize().scale(speedLimit);
         }
      }

      target.push(force);
      if (target instanceof ServerPlayer player) {
         player.connection.send(new PlayerPushMessage(force));
      }
   }
}
