package com.iafenvoy.iceandfire.entity.util;

import com.iafenvoy.iceandfire.IceAndFire;
import com.iafenvoy.iceandfire.entity.GorgonEntity;
import com.iafenvoy.iceandfire.entity.MultipartPartEntity;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class IafEntityUtil {
   public static void updatePart(MultipartPartEntity part, LivingEntity parent) {
      if (part != null && parent.level() instanceof ServerLevel serverLevel && !parent.isRemoved()) {
         if (!part.shouldContinuePersisting()) {
            UUID uuid = part.getUUID();
            Entity existing = serverLevel.getEntity(uuid);
            if (existing != null && existing != part) {
               while (serverLevel.getEntity(uuid) != null) {
                  uuid = Mth.createInsecureUUID(parent.getRandom());
               }

               IceAndFire.LOGGER.debug("Updated the UUID of [{}] due to a clash with [{}]", part, existing);
            }

            part.setUUID(uuid);
            serverLevel.addFreshEntity(part);
         }

         part.setParent(parent);
      }
   }

   public static boolean isEntityLookingAt(LivingEntity looker, LivingEntity seen, double degree) {
      degree *= 1.0 + looker.distanceTo(seen) * 0.1;
      Vec3 Vector3d = looker.getViewVector(1.0F).normalize();
      Vec3 Vector3d1 = new Vec3(
         seen.getX() - looker.getX(), seen.getBoundingBox().minY + seen.getEyeHeight() - (looker.getY() + looker.getEyeHeight()), seen.getZ() - looker.getZ()
      );
      double d0 = Vector3d1.length();
      Vector3d1 = Vector3d1.normalize();
      double d1 = Vector3d.dot(Vector3d1);
      return d1 > 1.0 - degree / d0 && looker.hasLineOfSight(seen) && !GorgonEntity.isStoneMob(seen);
   }
}
