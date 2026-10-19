package com.tno_tensura_extras.util;

import com.tno_tensura_extras.entity.AlignmentFieldEntity;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AlignmentFieldManager {
   private static final Set<AlignmentFieldEntity> ACTIVE_FIELDS = ConcurrentHashMap.newKeySet();
   private static final double MOTION_EPSILON = 1.0E-7;
   private static final double SURFACE_EPSILON = 1.0E-4;

   private AlignmentFieldManager() {
   }

   public static void register(AlignmentFieldEntity field) {
      ACTIVE_FIELDS.add(field);
   }

   public static void unregister(AlignmentFieldEntity field) {
      ACTIVE_FIELDS.remove(field);
   }

   public static void clear() {
      ACTIVE_FIELDS.clear();
   }

   public static boolean isInsideField(Level level, Vec3 position) {
      if (ACTIVE_FIELDS.isEmpty()) {
         return false;
      }

      for (AlignmentFieldEntity field : ACTIVE_FIELDS) {
         if (!isStale(field) && field.level() == level && field.isBlocking() && field.contains(position)) {
            return true;
         }
      }

      return false;
   }

   public static Vec3 clipMovement(Entity entity, Vec3 requestedMovement) {
      if (!ACTIVE_FIELDS.isEmpty() && !(requestedMovement.lengthSqr() < 1.0E-7)) {
         if (!(entity instanceof AlignmentFieldEntity) && !entity.isSpectator()) {
            if (entity instanceof Player player && player.isCreative() && player.getAbilities().flying) {
               return requestedMovement;
            } else {
               AABB box = entity.getBoundingBox();
               Vec3 start = box.getCenter();
               Vec3 target = start.add(requestedMovement);
               Vec3 clipped = target;

               for (AlignmentFieldEntity field : ACTIVE_FIELDS) {
                  if (!isStale(field) && field.level() == entity.level() && field.isBlocking() && field.getOwner() != entity) {
                     clipped = clipAgainstField(box, start, clipped, field);
                  }
               }

               return clipped == target ? requestedMovement : clipped.subtract(start);
            }
         } else {
            return requestedMovement;
         }
      } else {
         return requestedMovement;
      }
   }

   private static boolean isStale(AlignmentFieldEntity field) {
      if (field.isRemoved()) {
         ACTIVE_FIELDS.remove(field);
         return true;
      } else {
         return false;
      }
   }

   public static Vec3 clipPlayerTarget(Player player, Vec3 requestedTarget) {
      if (requestedTarget == null) {
         return player.position();
      }

      Vec3 requestedMovement = requestedTarget.subtract(player.position());
      return player.position().add(clipMovement(player, requestedMovement));
   }

   private static Vec3 clipAgainstField(AABB box, Vec3 start, Vec3 target, AlignmentFieldEntity field) {
      Vec3 centre = new Vec3(field.getX(), field.getY(), field.getZ());
      double halfHeight = field.getHalfHeight();
      double boxHalfHeight = box.getYsize() * 0.5;
      double boxHalfWidth = Math.max(box.getXsize(), box.getZsize()) * 0.5;
      double radius = field.getRadius() + boxHalfWidth;
      double minY = centre.y - halfHeight - boxHalfHeight;
      double maxY = centre.y + halfHeight + boxHalfHeight;
      if (radius <= 1.0E-7) {
         return target;
      }

      if (isInside(start, centre, radius, minY, maxY)) {
         return target;
      }

      if (!isInside(target, centre, radius, minY, maxY)) {
         return clampBeforeEntry(start, target, centre, radius, minY, maxY);
      }

      double dx = target.x - centre.x;
      double dz = target.z - centre.z;
      double horizontal = Math.sqrt(dx * dx + dz * dz);
      double sidePenetration = radius - horizontal;
      double topPenetration = maxY - target.y;
      double bottomPenetration = target.y - minY;
      if (sidePenetration <= topPenetration && sidePenetration <= bottomPenetration) {
         if (horizontal < 1.0E-7) {
            Vec3 outward = target.subtract(start).multiply(1.0, 0.0, 1.0);
            if (outward.lengthSqr() < 1.0E-7) {
               return target;
            }

            outward = outward.normalize().scale(-(radius + 1.0E-4));
            return new Vec3(centre.x + outward.x, target.y, centre.z + outward.z);
         } else {
            double scale = (radius + 1.0E-4) / horizontal;
            return new Vec3(centre.x + dx * scale, target.y, centre.z + dz * scale);
         }
      } else {
         return topPenetration <= bottomPenetration ? new Vec3(target.x, maxY + 1.0E-4, target.z) : new Vec3(target.x, minY - 1.0E-4, target.z);
      }
   }

   private static Vec3 clampBeforeEntry(Vec3 start, Vec3 target, Vec3 centre, double radius, double minY, double maxY) {
      Vec3 movement = target.subtract(start);
      double a = movement.x * movement.x + movement.z * movement.z;
      if (a < 1.0E-7) {
         return target;
      }

      double ox = start.x - centre.x;
      double oz = start.z - centre.z;
      double b = 2.0 * (ox * movement.x + oz * movement.z);
      double c = ox * ox + oz * oz - radius * radius;
      double discriminant = b * b - 4.0 * a * c;
      if (discriminant <= 0.0) {
         return target;
      }

      double root = Math.sqrt(discriminant);
      double entry = (-b - root) / (2.0 * a);
      if (!(entry <= 0.0) && !(entry >= 1.0)) {
         double entryY = start.y + movement.y * entry;
         if (!(entryY < minY) && !(entryY > maxY)) {
            double safeProgress = Math.max(0.0, entry - 1.0E-4);
            return start.add(movement.scale(safeProgress));
         } else {
            return target;
         }
      } else {
         return target;
      }
   }

   private static boolean isInside(Vec3 point, Vec3 centre, double radius, double minY, double maxY) {
      if (!(point.y < minY) && !(point.y > maxY)) {
         double dx = point.x - centre.x;
         double dz = point.z - centre.z;
         return dx * dx + dz * dz < radius * radius;
      } else {
         return false;
      }
   }
}
