package com.github.b4ndithelps.tennogamenolife.ability.skill.intrinsic;

import com.github.b4ndithelps.tennogamenolife.entity.DragonRoarCircle;
import com.github.b4ndithelps.tennogamenolife.registry.entity.TenreincarnationEntities;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class FourthProtectionManager {
   private static final double MOTION_EPSILON = 1.0E-7;
   private static final double SURFACE_EPSILON = 1.0E-4;
   private static final double CLIENT_BARRIER_SEARCH_RADIUS = 128.0;
   private static final List<FourthProtectionManager.ActiveBarrier> ACTIVE_BARRIERS = new ArrayList<>();

   private FourthProtectionManager() {
   }

   public static void activate(ServerLevel level, LivingEntity caster, LivingEntity target, float radius, int durationTicks) {
      removeCasterBarrier(caster.getUUID());
      Vec3 center = target.getBoundingBox().getCenter();
      DragonRoarCircle visual = new DragonRoarCircle((EntityType<?>)TenreincarnationEntities.DRAGON_ROAR_CIRCLE.get(), level);
      visual.setBarrierSphereVfx();
      visual.setSize(radius);
      visual.setLife(durationTicks);
      visual.setVfxDuration(durationTicks);
      visual.setVfxStartGameTime(level.getGameTime());
      visual.moveTo(center.x, center.y, center.z, 0.0F, 0.0F);
      level.addFreshEntity(visual);
      ACTIVE_BARRIERS.add(
         new FourthProtectionManager.ActiveBarrier(level, caster.getUUID(), center, radius * radius, level.getGameTime() + durationTicks, visual)
      );
   }

   public static boolean dismiss(LivingEntity caster) {
      return removeCasterBarrier(caster.getUUID());
   }

   public static Vec3 clipPlayerMovement(Entity entity, Vec3 requestedMovement) {
      if (entity instanceof Player && !entity.isSpectator() && !(requestedMovement.lengthSqr() < 1.0E-7)) {
         Vec3 start = entity.getBoundingBox().getCenter();
         Vec3 target = start.add(requestedMovement);
         Vec3 originalTarget = target;
         List<FourthProtectionManager.BarrierSnapshot> barriers = getCollisionBarriers(entity.level(), start, target);
         if (barriers.isEmpty()) {
            return requestedMovement;
         }

         for (int pass = 0; pass < 2; pass++) {
            Vec3 previousTarget = target;

            for (FourthProtectionManager.BarrierSnapshot barrier : barriers) {
               target = slideAlongBarrier(entity.getBoundingBox(), start, target, barrier);
            }

            if (target.distanceToSqr(previousTarget) < 1.0E-7) {
               break;
            }
         }

         return target == originalTarget ? requestedMovement : target.subtract(start);
      } else {
         return requestedMovement;
      }
   }

   public static Vec3 clipPlayerTarget(Player player, Vec3 requestedTarget) {
      return requestedTarget == null ? player.position() : player.position().add(clipPlayerMovement(player, requestedTarget.subtract(player.position())));
   }

   public static boolean crossesBoundary(Level level, Vec3 from, Vec3 to) {
      if (level instanceof ServerLevel serverLevel && from != null && to != null) {
         for (FourthProtectionManager.ActiveBarrier barrier : ACTIVE_BARRIERS) {
            if (barrier.level() == serverLevel && !barrier.isExpired()) {
               Vec3 center = barrier.center();
               if (center != null && isInside(from, center, barrier.radiusSquared()) != isInside(to, center, barrier.radiusSquared())) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean isInsideBarrier(Level level, Vec3 position) {
      if (level instanceof ServerLevel serverLevel && position != null) {
         for (FourthProtectionManager.ActiveBarrier barrier : ACTIVE_BARRIERS) {
            if (barrier.level() == serverLevel && !barrier.isExpired()) {
               Vec3 center = barrier.center();
               if (center != null && isInside(position, center, barrier.radiusSquared())) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean intersectsBoundary(Level level, Vec3 from, Vec3 to) {
      if (level instanceof ServerLevel serverLevel && from != null && to != null) {
         Vec3 movement = to.subtract(from);
         double movementLengthSquared = movement.lengthSqr();

         for (FourthProtectionManager.ActiveBarrier barrier : ACTIVE_BARRIERS) {
            if (barrier.level() == serverLevel && !barrier.isExpired()) {
               Vec3 center = barrier.center();
               if (center != null) {
                  boolean fromInside = isInside(from, center, barrier.radiusSquared());
                  boolean toInside = isInside(to, center, barrier.radiusSquared());
                  if (fromInside != toInside) {
                     return true;
                  }

                  if (!fromInside && !(movementLengthSquared < 1.0E-8)) {
                     double progress = center.subtract(from).dot(movement) / movementLengthSquared;
                     if (progress > 0.0 && progress < 1.0) {
                        Vec3 closestPoint = from.add(movement.scale(progress));
                        if (isInside(closestPoint, center, barrier.radiusSquared())) {
                           return true;
                        }
                     }
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static void tick() {
      Iterator<FourthProtectionManager.ActiveBarrier> iterator = ACTIVE_BARRIERS.iterator();

      while (iterator.hasNext()) {
         FourthProtectionManager.ActiveBarrier barrier = iterator.next();
         if (barrier.isExpired()) {
            iterator.remove();
         }
      }
   }

   public static void clear() {
      for (FourthProtectionManager.ActiveBarrier barrier : ACTIVE_BARRIERS) {
         if (!barrier.visual().isRemoved()) {
            barrier.visual().discard();
         }
      }

      ACTIVE_BARRIERS.clear();
   }

   private static boolean removeCasterBarrier(UUID casterId) {
      boolean removed = false;
      Iterator<FourthProtectionManager.ActiveBarrier> iterator = ACTIVE_BARRIERS.iterator();

      while (iterator.hasNext()) {
         FourthProtectionManager.ActiveBarrier barrier = iterator.next();
         if (barrier.casterId().equals(casterId)) {
            iterator.remove();
            removed = true;
            if (!barrier.visual().isRemoved()) {
               barrier.visual().discard();
            }
         }
      }

      return removed;
   }

   private static boolean isInside(Vec3 position, Vec3 center, double radiusSquared) {
      return position.distanceToSqr(center) <= radiusSquared;
   }

   private static List<FourthProtectionManager.BarrierSnapshot> getCollisionBarriers(Level level, Vec3 start, Vec3 target) {
      List<FourthProtectionManager.BarrierSnapshot> barriers = new ArrayList<>();
      if (level instanceof ServerLevel serverLevel) {
         for (FourthProtectionManager.ActiveBarrier barrier : ACTIVE_BARRIERS) {
            if (barrier.level() == serverLevel && !barrier.isExpired()) {
               barriers.add(new FourthProtectionManager.BarrierSnapshot(barrier.center(), Math.sqrt(barrier.radiusSquared())));
            }
         }

         return barriers;
      } else {
         AABB searchArea = new AABB(start, target).inflate(128.0);

         for (DragonRoarCircle visual : level.getEntitiesOfClass(DragonRoarCircle.class, searchArea)) {
            if (visual.isBarrierSphereVfx() && visual.getLife() > 0) {
               barriers.add(new FourthProtectionManager.BarrierSnapshot(visual.position(), visual.getSize()));
            }
         }

         return barriers;
      }
   }

   private static Vec3 slideAlongBarrier(AABB entityBox, Vec3 start, Vec3 requestedTarget, FourthProtectionManager.BarrierSnapshot barrier) {
      Vec3 movement = requestedTarget.subtract(start);
      if (movement.lengthSqr() < 1.0E-7) {
         return requestedTarget;
      }

      Vec3 relativeStart = start.subtract(barrier.center());
      double startDistance = relativeStart.length();
      boolean startsInside = startDistance <= barrier.radius();
      Vec3 startNormal = startDistance < 1.0E-7 ? movement.normalize() : relativeStart.scale(1.0 / startDistance);
      double collisionRadius = barrier.radius() + (startsInside ? -1.0 : 1.0) * getEntitySupportRadius(entityBox, startNormal);
      if (collisionRadius <= 1.0E-7) {
         return requestedTarget;
      }

      boolean collides;
      if (startsInside) {
         double targetDistanceSquared = requestedTarget.distanceToSqr(barrier.center());
         boolean startsInInnerSafeSpace = startDistance <= collisionRadius + 1.0E-4;
         if (startsInInnerSafeSpace) {
            collides = targetDistanceSquared > collisionRadius * collisionRadius + 1.0E-7;
         } else {
            double radialMovement = movement.dot(startNormal);
            collides = radialMovement > 1.0E-7 || targetDistanceSquared > barrier.radius() * barrier.radius() + 1.0E-7;
         }
      } else {
         collides = segmentDistanceSquared(start, requestedTarget, barrier.center()) < collisionRadius * collisionRadius - 1.0E-7;
      }

      if (!collides) {
         return requestedTarget;
      } else {
         double contactProgress = findContactProgress(start, requestedTarget, barrier.center(), collisionRadius, startsInside);
         Vec3 contact = contactProgress >= 0.0 ? start.add(movement.scale(contactProgress)) : barrier.center().add(startNormal.scale(collisionRadius));
         Vec3 contactOffset = contact.subtract(barrier.center());
         Vec3 normal = contactOffset.lengthSqr() < 1.0E-7 ? startNormal : contactOffset.normalize();
         double contactRadius = barrier.radius() + (startsInside ? -1.0 : 1.0) * getEntitySupportRadius(entityBox, normal);
         double safeRadius = contactRadius + (startsInside ? -1.0E-4 : 1.0E-4);
         if (safeRadius <= 1.0E-7) {
            return requestedTarget;
         } else {
            Vec3 safeContact = barrier.center().add(normal.scale(safeRadius));
            Vec3 remaining = requestedTarget.subtract(contact);
            Vec3 tangent = remaining.subtract(normal.scale(remaining.dot(normal)));
            Vec3 slidTarget = safeContact.add(tangent);
            Vec3 slidOffset = slidTarget.subtract(barrier.center());
            if (slidOffset.lengthSqr() < 1.0E-7) {
               return safeContact;
            } else {
               Vec3 slidNormal = slidOffset.normalize();
               double slidRadius = barrier.radius() + (startsInside ? -1.0 : 1.0) * getEntitySupportRadius(entityBox, slidNormal);
               double slidDistance = slidOffset.length();
               boolean crossedAfterSlide = startsInside ? slidDistance > slidRadius - 1.0E-4 : slidDistance < slidRadius + 1.0E-4;
               if (crossedAfterSlide) {
                  double correctedRadius = slidRadius + (startsInside ? -1.0E-4 : 1.0E-4);
                  return barrier.center().add(slidNormal.scale(correctedRadius));
               } else {
                  return slidTarget;
               }
            }
         }
      }
   }

   private static double getEntitySupportRadius(AABB entityBox, Vec3 normal) {
      double halfWidthX = (entityBox.maxX - entityBox.minX) * 0.5;
      double halfHeight = (entityBox.maxY - entityBox.minY) * 0.5;
      double halfWidthZ = (entityBox.maxZ - entityBox.minZ) * 0.5;
      return Math.abs(normal.x) * halfWidthX + Math.abs(normal.y) * halfHeight + Math.abs(normal.z) * halfWidthZ;
   }

   private static double segmentDistanceSquared(Vec3 start, Vec3 end, Vec3 point) {
      Vec3 movement = end.subtract(start);
      double movementLengthSquared = movement.lengthSqr();
      if (movementLengthSquared < 1.0E-7) {
         return start.distanceToSqr(point);
      }

      double progress = point.subtract(start).dot(movement) / movementLengthSquared;
      progress = Math.max(0.0, Math.min(1.0, progress));
      return start.add(movement.scale(progress)).distanceToSqr(point);
   }

   private static double findContactProgress(Vec3 start, Vec3 target, Vec3 center, double radius, boolean startsInside) {
      Vec3 movement = target.subtract(start);
      double a = movement.lengthSqr();
      if (a < 1.0E-7) {
         return -1.0;
      }

      Vec3 offset = start.subtract(center);
      double b = 2.0 * offset.dot(movement);
      double c = offset.lengthSqr() - radius * radius;
      double discriminant = b * b - 4.0 * a * c;
      if (discriminant < 0.0) {
         return -1.0;
      }

      double root = Math.sqrt(discriminant);
      double first = (-b - root) / (2.0 * a);
      double second = (-b + root) / (2.0 * a);
      double progress = startsInside ? second : first;
      return progress >= 0.0 && progress <= 1.0 ? progress : -1.0;
   }

   private record ActiveBarrier(ServerLevel level, UUID casterId, Vec3 center, double radiusSquared, long expiresAt, DragonRoarCircle visual) {
      private boolean isExpired() {
         return this.level.getGameTime() >= this.expiresAt;
      }
   }

   private record BarrierSnapshot(Vec3 center, double radius) {
   }
}
