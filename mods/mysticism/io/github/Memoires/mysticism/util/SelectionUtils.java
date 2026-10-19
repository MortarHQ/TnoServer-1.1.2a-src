package io.github.Memoires.mysticism.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SelectionUtils {
   @Nullable
   public static <T extends Entity> Entity getClosestEntity(List<T> entities, Entity origin, Predicate<? super Entity> predicate) {
      if (entities.isEmpty()) {
         return null;
      }

      Vec3 position = origin.position();
      Entity closestEntity = entities.getFirst();
      double farthestDistance = closestEntity.position().distanceToSqr(position);

      for (T entity : entities) {
         if (predicate.test(entity)) {
            double distance = entity.position().distanceToSqr(position);
            if (!(distance >= farthestDistance)) {
               closestEntity = entity;
               farthestDistance = distance;
            }
         }
      }

      return closestEntity;
   }

   @Nullable
   public static <T extends Class<? extends Entity>> Entity getClosestEntity(
      List<T> classes, Entity origin, double maxDistance, Predicate<? super Entity> predicate
   ) {
      List<Entity> entities = new ArrayList<>();
      Level level = origin.level();
      AABB area = origin.getBoundingBox().inflate(maxDistance);

      for (T clazz : classes) {
         entities.addAll(level.getEntitiesOfClass(clazz, area, predicate));
      }

      return getClosestEntity(entities, origin, predicate);
   }

   @Nullable
   public static <T extends Class<? extends Entity>> Entity getClosestEntityInView(
      List<T> classes, Entity origin, double distance, double radius, Predicate<? super Entity> predicate
   ) {
      Level level = origin.level();
      List<Entity> entities = new ArrayList<>();
      Vec3 view = origin.getViewVector(1.0F);
      Vec3 distantView = view.scale(distance);
      AABB area = origin.getBoundingBox().expandTowards(distantView);

      for (Class<? extends Entity> clazz : classes) {
         entities.addAll(level.getEntitiesOfClass(clazz, area, predicate));
      }

      if (entities.isEmpty()) {
         return null;
      }

      Vec3 ownPoint = origin.getEyePosition(1.0F).add(view.scale(-1.0));
      Vec3 farPoint = ownPoint.add(distantView);
      Entity closest = null;
      double closestDist = distance;

      for (Entity entity : entities) {
         AABB aabb = entity.getBoundingBox().inflate(radius);
         Optional<Vec3> clip = aabb.clip(ownPoint, farPoint);
         if (!clip.isEmpty() && !aabb.contains(ownPoint)) {
            double entityDistance = ownPoint.distanceTo(clip.get());
            if (!(entityDistance >= closestDist)) {
               if (entity.getRootVehicle() == origin.getRootVehicle() && closestDist == 0.0) {
                  closest = entity;
                  break;
               }

               closest = entity;
               closestDist = entityDistance;
            }
         }
      }

      return closest;
   }

   @Nullable
   public static <T extends Entity> Vec3 getClosestEntityPosition(List<T> entities, Entity origin, Predicate<? super Entity> predicate) {
      Entity entity = getClosestEntity(entities, origin, predicate);
      return entity == null ? null : entity.position();
   }

   @Nullable
   public static <T extends Class<? extends Entity>> Vec3 getClosestEntityPosition(
      List<T> classes, Entity origin, double maxDistance, Predicate<? super Entity> predicate
   ) {
      Entity entity = getClosestEntity(classes, origin, maxDistance, predicate);
      return entity == null ? null : entity.position();
   }

   @Nullable
   public static <T extends Class<? extends Entity>> Vec3 getClosestEntityInViewPosition(
      List<T> classes, Entity origin, double maxDistance, double radius, Predicate<? super Entity> predicate
   ) {
      Entity entity = getClosestEntityInView(classes, origin, maxDistance, radius, predicate);
      return entity == null ? null : entity.position();
   }

   @Nullable
   public static <T extends Entity> Entity getClosestEntity(List<T> entities, Entity origin) {
      return getClosestEntity(entities, origin, entity -> true);
   }

   @Nullable
   public static <T extends Class<? extends Entity>> Entity getClosestEntity(List<T> classes, Entity origin, double maxDistance) {
      return getClosestEntity(classes, origin, maxDistance, entity -> true);
   }

   @Nullable
   public static <T extends Class<? extends Entity>> Entity getClosestEntity(T clazz, Entity origin, double maxDistance) {
      return getClosestEntity(List.of(clazz), origin, maxDistance, entity -> true);
   }

   @Nullable
   public static <T extends Class<? extends Entity>> Entity getClosestEntityInView(List<T> classes, Entity origin, double maxDistance, double radius) {
      return getClosestEntityInView(classes, origin, maxDistance, radius, entity -> true);
   }

   @Nullable
   public static <T extends Class<? extends Entity>> Entity getClosestEntityInView(T clazz, Entity origin, double maxDistance, double radius) {
      return getClosestEntityInView(List.of(clazz), origin, maxDistance, radius, entity -> true);
   }

   @Nullable
   public static <T extends Class<? extends Entity>> Vec3 getClosestEntityInViewPosition(List<T> classes, Entity origin, double maxDistance, double radius) {
      return getClosestEntityInViewPosition(classes, origin, maxDistance, radius, entity -> true);
   }

   @Nullable
   public static <T extends Class<? extends Entity>> Vec3 getClosestEntityInViewPosition(T clazz, Entity origin, double maxDistance, double radius) {
      return getClosestEntityInViewPosition(List.of(clazz), origin, maxDistance, radius, entity -> true);
   }

   @Nullable
   public static <T extends Entity> Vec3 getClosestEntityPosition(List<T> entities, Entity origin) {
      return getClosestEntityPosition(entities, origin, entity -> true);
   }

   @Nullable
   public static <T extends Class<? extends Entity>> Vec3 getClosestEntityPosition(List<T> classes, Entity origin, double maxDistance) {
      return getClosestEntityPosition(classes, origin, maxDistance, entity -> true);
   }

   @Nullable
   public static <T extends Class<? extends Entity>> Vec3 getClosestEntityPosition(T clazz, Entity origin, double maxDistance) {
      return getClosestEntityPosition(List.of(clazz), origin, maxDistance, entity -> true);
   }
}
