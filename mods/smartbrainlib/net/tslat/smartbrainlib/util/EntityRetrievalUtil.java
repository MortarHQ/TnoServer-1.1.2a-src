package net.tslat.smartbrainlib.util;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.core.SectionPos;
import net.minecraft.util.AbortableIterationConsumer.Continuation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntitySection;
import net.minecraft.world.level.entity.EntitySectionStorage;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.LevelEntityGetterAdapter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.SBLConstants;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;

public final class EntityRetrievalUtil {
   @Nullable
   public static <T extends Entity> T getNearest(Vec3 origin, List<T> entities) {
      if (entities.isEmpty()) {
         return null;
      }

      double dist = Double.MAX_VALUE;
      T closest = null;

      for (T entity : entities) {
         double entityDist = entity.distanceToSqr(origin);
         if (entityDist < dist) {
            dist = entityDist;
            closest = entity;
         }
      }

      return closest;
   }

   public static <T> Optional<T> getNearestEntity(Entity origin, double radius) {
      return getNearestEntity(origin, radius, radius, radius);
   }

   public static <T> Optional<T> getNearestEntity(Entity origin, double radiusX, double radiusY, double radiusZ) {
      return Optional.ofNullable(
         getNearestEntity(origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), origin.position(), entity -> entity != origin)
      );
   }

   public static <T extends Entity> Optional<T> getNearestEntity(Level level, Vec3 origin, double radius) {
      return getNearestEntity(level, origin, radius, radius, radius);
   }

   public static <T extends Entity> Optional<T> getNearestEntity(Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ) {
      return getNearestEntity(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), origin);
   }

   public static <T extends Entity> Optional<T> getNearestEntity(Level level, AABB bounds, Vec3 origin) {
      return getNearestEntity(level, bounds, origin, (Class<T>)Entity.class);
   }

   public static <T extends Entity> Optional<T> getNearestEntity(Entity origin, double radius, Class<T> minimumClass) {
      return getNearestEntity(origin, radius, radius, radius, minimumClass);
   }

   public static <T extends Entity> Optional<T> getNearestEntity(Entity origin, double radiusX, double radiusY, double radiusZ, Class<T> minimumClass) {
      return getNearestEntity(
         origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), origin.position(), minimumClass, entity -> entity != origin
      );
   }

   public static <T extends Entity> Optional<T> getNearestEntity(Level level, Vec3 origin, double radius, Class<T> minimumClass) {
      return getNearestEntity(level, origin, radius, radius, radius, minimumClass);
   }

   public static <T extends Entity> Optional<T> getNearestEntity(
      Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ, Class<T> minimumClass
   ) {
      return getNearestEntity(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), origin, minimumClass);
   }

   public static <T extends Entity> Optional<T> getNearestEntity(Level level, AABB bounds, Vec3 origin, Class<T> minimumClass) {
      return getNearestEntity(level, bounds, origin, minimumClass, entity -> true);
   }

   public static <T extends Entity> T getNearestEntity(Entity origin, double radius, Predicate<? extends Entity> predicate) {
      return getNearestEntity(origin, radius, radius, radius, predicate);
   }

   public static <T extends Entity> T getNearestEntity(Entity origin, double radiusX, double radiusY, double radiusZ, Predicate<? extends Entity> predicate) {
      return getNearestEntity(
         origin.level(),
         origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ),
         origin.position(),
         (entity -> entity != origin).and((Predicate<? super Entity>)predicate)
      );
   }

   public static <T extends Entity> Optional<T> getNearestEntity(Level level, Vec3 origin, double radius, Predicate<Entity> predicate) {
      return getNearestEntity(level, origin, radius, radius, radius, predicate);
   }

   public static <T extends Entity> Optional<T> getNearestEntity(
      Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ, Predicate<Entity> predicate
   ) {
      return Optional.ofNullable(getNearestEntity(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), origin, predicate));
   }

   @Nullable
   public static <T extends Entity> T getNearestEntity(Level level, AABB bounds, Vec3 origin, Predicate<? extends Entity> predicate) {
      return (T)getNearestEntity(level, bounds, origin, Entity.class, predicate).orElse(null);
   }

   public static <T extends Entity> Optional<T> getNearestEntity(Entity origin, double radius, Class<T> minimumClass, Predicate<T> predicate) {
      return getNearestEntity(origin, radius, radius, radius, minimumClass, predicate);
   }

   public static <T extends Entity> Optional<T> getNearestEntity(
      Entity origin, double radiusX, double radiusY, double radiusZ, Class<T> minimumClass, Predicate<T> predicate
   ) {
      return getNearestEntity(
         origin.level(),
         origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ),
         origin.position(),
         minimumClass,
         (entity -> entity != origin).and(predicate)
      );
   }

   public static <T extends Entity> Optional<T> getNearestEntity(Level level, Vec3 origin, double radius, Class<T> minimumClass, Predicate<T> predicate) {
      return getNearestEntity(level, AABB.ofSize(origin, radius, radius, radius), origin, minimumClass, predicate);
   }

   public static <T extends Entity> Optional<T> getNearestEntity(
      Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ, Class<T> minimumClass, Predicate<T> predicate
   ) {
      return getNearestEntity(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), origin, minimumClass, predicate);
   }

   public static <T extends Entity> Optional<T> getNearestEntity(Level level, AABB bounds, Vec3 origin, Class<T> minimumClass, Predicate<T> predicate) {
      MutableDouble dist = new MutableDouble(Double.MAX_VALUE);
      MutableObject<T> closest = new MutableObject(null);
      EntityTypeTest<Entity, T> typeTest = makeLazyTypeTest(minimumClass);
      level.getEntities().get(typeTest, bounds, entity -> {
         if (predicate.test((T)entity)) {
            double entityDist = entity.distanceToSqr(origin);
            if (entityDist < dist.doubleValue()) {
               dist.setValue(entityDist);
               closest.setValue(entity);
            }
         }

         return Continuation.CONTINUE;
      });
      return Optional.ofNullable((T)closest.getValue());
   }

   public static <T extends Player> T getNearestPlayer(Entity origin, double radius) {
      return getNearestPlayer(origin, radius, radius, radius);
   }

   public static <T extends Player> T getNearestPlayer(Entity origin, double radiusX, double radiusY, double radiusZ) {
      return (T)getNearestPlayer(origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), origin.position(), entity -> entity != origin);
   }

   public static <T extends Player> T getNearestPlayer(Level level, Vec3 origin, double radius) {
      return getNearestPlayer(level, origin, radius, radius, radius);
   }

   public static <T extends Player> T getNearestPlayer(Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ) {
      return getNearestPlayer(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), origin);
   }

   public static <T extends Player> T getNearestPlayer(Level level, AABB area, Vec3 origin) {
      return (T)getNearestPlayer(level, area, origin, pl -> true);
   }

   public static Player getNearestPlayer(Entity origin, double radius, Predicate<Player> predicate) {
      return getNearestPlayer(origin, radius, radius, radius, predicate);
   }

   public static Player getNearestPlayer(Entity origin, double radiusX, double radiusY, double radiusZ, Predicate<Player> predicate) {
      return getNearestPlayer(
         origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), origin.position(), (entity -> entity != origin).and(predicate)
      );
   }

   public static <T extends Player> T getNearestPlayer(Level level, Vec3 origin, double radius, Predicate<Player> predicate) {
      return getNearestPlayer(level, origin, radius, radius, radius, predicate);
   }

   public static <T extends Player> T getNearestPlayer(Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ, Predicate<Player> predicate) {
      return (T)getNearestPlayer(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), origin, predicate);
   }

   @Nullable
   public static Player getNearestPlayer(Level level, AABB bounds, Vec3 origin, Predicate<Player> predicate) {
      double dist = Double.MAX_VALUE;
      Player closest = null;

      for (Player player : level.players()) {
         if (bounds.contains(player.position()) && predicate.test(player)) {
            double playerDist = player.distanceToSqr(origin);
            if (playerDist < dist) {
               dist = playerDist;
               closest = player;
            }
         }
      }

      return closest;
   }

   public static List<Player> getPlayers(Entity origin, double radius) {
      return getPlayers(origin, radius, radius, radius);
   }

   public static List<Player> getPlayers(Entity origin, double radiusX, double radiusY, double radiusZ) {
      return getPlayers(origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), entity -> entity != origin);
   }

   public static <T extends Player> List<T> getPlayers(Level level, Vec3 origin, double radius) {
      return getPlayers(level, origin, radius, radius, radius);
   }

   public static <T extends Player> List<T> getPlayers(Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ) {
      return (List<T>)getPlayers(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ));
   }

   public static <T extends Player> List<T> getPlayers(Level level, Vec3 from, Vec3 to) {
      return (List<T>)getPlayers(level, new AABB(from, to));
   }

   public static List<Player> getPlayers(Level level, AABB area) {
      return getPlayers(level, area, pl -> true);
   }

   public static List<Player> getPlayers(Entity origin, double radius, Predicate<Player> predicate) {
      return getPlayers(origin, radius, radius, radius, predicate);
   }

   public static List<Player> getPlayers(Entity origin, double radiusX, double radiusY, double radiusZ, Predicate<Player> predicate) {
      return getPlayers(origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), (entity -> entity != origin).and(predicate));
   }

   public static <T extends Player> List<T> getPlayers(Level level, Vec3 origin, double radius, Predicate<Player> predicate) {
      return getPlayers(level, origin, radius, radius, radius, predicate);
   }

   public static <T extends Player> List<T> getPlayers(Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ, Predicate<Player> predicate) {
      return (List<T>)getPlayers(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), predicate);
   }

   public static <T extends Player> List<T> getPlayers(Level level, Vec3 from, Vec3 to, Predicate<Player> predicate) {
      return (List<T>)getPlayers(level, new AABB(from, to), predicate);
   }

   public static List<Player> getPlayers(Level level, AABB bounds, Predicate<Player> predicate) {
      List<Player> players = new ObjectArrayList();

      for (Player player : level.players()) {
         if (bounds.contains(player.position()) && predicate.test(player)) {
            players.add(player);
         }
      }

      return players;
   }

   public static <T> List<T> getEntities(Entity origin, double radius) {
      return getEntities(origin, radius, radius, radius);
   }

   public static <T> List<T> getEntities(Entity origin, double radiusX, double radiusY, double radiusZ) {
      return getEntities(origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), entity -> entity != origin);
   }

   public static <T extends Entity> List<T> getEntities(Level level, Vec3 origin, double radius) {
      return getEntities(level, AABB.ofSize(origin, radius, radius, radius));
   }

   public static <T extends Entity> List<T> getEntities(Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ) {
      return getEntities(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ));
   }

   public static <T extends Entity> List<T> getEntities(Level level, Vec3 from, Vec3 to) {
      return getEntities(level, new AABB(from, to));
   }

   public static <T extends Entity> List<T> getEntities(Level level, AABB area) {
      return getEntities(level, area, (Class<T>)Entity.class);
   }

   public static <T extends Entity> List<T> getEntities(Entity origin, double radius, Class<T> minimumClass) {
      return getEntities(origin, radius, radius, radius, minimumClass);
   }

   public static <T extends Entity> List<T> getEntities(Entity origin, double radiusX, double radiusY, double radiusZ, Class<T> minimumClass) {
      return getEntities(origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), minimumClass, entity -> entity != origin);
   }

   public static <T extends Entity> List<T> getEntities(Level level, Vec3 origin, double radius, Class<T> minimumClass) {
      return getEntities(level, AABB.ofSize(origin, radius, radius, radius), minimumClass);
   }

   public static <T extends Entity> List<T> getEntities(Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ, Class<T> minimumClass) {
      return getEntities(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), minimumClass);
   }

   public static <T extends Entity> List<T> getEntities(Level level, Vec3 from, Vec3 to, Class<T> minimumClass) {
      return getEntities(level, new AABB(from, to), minimumClass);
   }

   public static <T extends Entity> List<T> getEntities(Level level, AABB bounds, Class<T> minimumClass) {
      return getEntities(level, bounds, minimumClass, entity -> true);
   }

   public static <T> List<T> getEntities(Entity origin, double radius, Predicate<? extends Entity> predicate) {
      return getEntities(origin, radius, radius, radius, predicate);
   }

   public static <T> List<T> getEntities(Entity origin, double radiusX, double radiusY, double radiusZ, Predicate<? extends Entity> predicate) {
      return getEntities(
         origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), (entity -> entity != origin).and((Predicate<? super Entity>)predicate)
      );
   }

   public static <T extends Entity> List<T> getEntities(Level level, Vec3 origin, double radius, Predicate<Entity> predicate) {
      return getEntities(level, AABB.ofSize(origin, radius, radius, radius), predicate);
   }

   public static <T extends Entity> List<T> getEntities(Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ, Predicate<Entity> predicate) {
      return getEntities(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), predicate);
   }

   public static <T extends Entity> List<T> getEntities(Level level, Vec3 from, Vec3 to, Predicate<Entity> predicate) {
      return getEntities(level, new AABB(from, to), predicate);
   }

   public static <T extends Entity> List<T> getEntities(Level level, AABB area, Predicate<? extends Entity> predicate) {
      return getEntities(level, area, (Class<T>)Entity.class, (Predicate<T>)predicate);
   }

   public static <T extends Entity> List<T> getEntities(Entity origin, double radius, Class<T> minimumClass, Predicate<T> predicate) {
      return getEntities(origin, radius, radius, radius, minimumClass, predicate);
   }

   public static <T extends Entity> List<T> getEntities(
      Entity origin, double radiusX, double radiusY, double radiusZ, Class<T> minimumClass, Predicate<T> predicate
   ) {
      return getEntities(origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), minimumClass, (entity -> entity != origin).and(predicate));
   }

   public static <T extends Entity> List<T> getEntities(Level level, Vec3 origin, double radius, Class<T> minimumClass, Predicate<T> predicate) {
      return getEntities(level, AABB.ofSize(origin, radius, radius, radius), minimumClass, predicate);
   }

   public static <T extends Entity> List<T> getEntities(
      Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ, Class<T> minimumClass, Predicate<T> predicate
   ) {
      return getEntities(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), minimumClass, predicate);
   }

   public static <T extends Entity> List<T> getEntities(Level level, Vec3 from, Vec3 to, Class<T> minimumClass, Predicate<T> predicate) {
      return getEntities(level, new AABB(from, to), minimumClass, predicate);
   }

   public static <T extends Entity> List<T> getEntities(Level level, AABB bounds, Class<T> minimumClass, Predicate<T> predicate) {
      List<T> foundEntities = new ObjectArrayList();
      EntityTypeTest<Entity, T> typeTest = makeLazyTypeTest(minimumClass);
      level.getEntities().get(typeTest, bounds, entityx -> {
         if (predicate.test((T)entityx)) {
            foundEntities.add((T)entityx);
         }

         return Continuation.CONTINUE;
      });
      Pair<Collection<? extends Entity>, Function<Entity, ? extends Entity>> partEntities = SBLConstants.SBL_LOADER.getPartEntities(level);

      for (Entity part : (Collection)partEntities.getFirst()) {
         T entity = (T)typeTest.tryCast((Entity)((Function)partEntities.getSecond()).apply(part));
         if (entity != null && part.getBoundingBox().intersects(bounds) && predicate.test(entity)) {
            foundEntities.add(entity);
         }
      }

      return foundEntities;
   }

   public static <T extends Player> Optional<T> findPlayer(Entity origin, double radius, Predicate<Player> predicate) {
      return findPlayer(origin, radius, radius, radius, predicate);
   }

   public static <T extends Player> Optional<T> findPlayer(Entity origin, double radiusX, double radiusY, double radiusZ, Predicate<Player> predicate) {
      return findPlayer(origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), (entity -> entity != origin).and(predicate));
   }

   public static <T extends Player> Optional<T> findPlayer(Level level, Vec3 origin, double radius, Predicate<Player> predicate) {
      return findPlayer(level, AABB.ofSize(origin, radius, radius, radius), predicate);
   }

   public static <T extends Player> Optional<T> findPlayer(
      Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ, Predicate<Player> predicate
   ) {
      return findPlayer(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), predicate);
   }

   public static <T extends Player> Optional<T> findPlayer(Level level, Vec3 from, Vec3 to, Predicate<Player> predicate) {
      return findPlayer(level, new AABB(from, to), predicate);
   }

   public static <T extends Player> Optional<T> findPlayer(Level level, AABB bounds, Predicate<Player> predicate) {
      for (Player player : level.players()) {
         if (bounds.contains(player.position()) && predicate.test(player)) {
            return Optional.of((T)player);
         }
      }

      return Optional.empty();
   }

   public static <T extends Entity> Optional<T> findEntity(Entity origin, double radius, Predicate<Entity> predicate) {
      return findEntity(origin, radius, radius, radius, predicate);
   }

   public static <T extends Entity> Optional<T> findEntity(Entity origin, double radiusX, double radiusY, double radiusZ, Predicate<Entity> predicate) {
      return findEntity(origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), (entity -> entity != origin).and(predicate));
   }

   public static <T extends Entity> Optional<T> findEntity(Level level, Vec3 origin, double radius, Predicate<Entity> predicate) {
      return findEntity(level, AABB.ofSize(origin, radius, radius, radius), predicate);
   }

   public static <T extends Entity> Optional<T> findEntity(
      Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ, Predicate<Entity> predicate
   ) {
      return findEntity(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), predicate);
   }

   public static <T extends Entity> Optional<T> findEntity(Level level, Vec3 from, Vec3 to, Predicate<Entity> predicate) {
      return findEntity(level, new AABB(from, to), predicate);
   }

   public static <T extends Entity> Optional<T> findEntity(Level level, AABB bounds, Predicate<Entity> predicate) {
      return findEntity(level, bounds, (Class<T>)Entity.class, (Predicate<T>)predicate);
   }

   public static <T extends Entity> Optional<T> findEntity(Entity origin, double radius, Class<T> minimumClass, Predicate<T> predicate) {
      return findEntity(origin, radius, radius, radius, minimumClass, predicate);
   }

   public static <T extends Entity> Optional<T> findEntity(
      Entity origin, double radiusX, double radiusY, double radiusZ, Class<T> minimumClass, Predicate<T> predicate
   ) {
      return findEntity(origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), minimumClass, (entity -> entity != origin).and(predicate));
   }

   public static <T extends Entity> Optional<T> findEntity(Level level, Vec3 origin, double radius, Class<T> minimumClass, Predicate<T> predicate) {
      return findEntity(level, AABB.ofSize(origin, radius, radius, radius), minimumClass, predicate);
   }

   public static <T extends Entity> Optional<T> findEntity(
      Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ, Class<T> minimumClass, Predicate<T> predicate
   ) {
      return findEntity(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), minimumClass, predicate);
   }

   public static <T extends Entity> Optional<T> findEntity(Level level, Vec3 from, Vec3 to, Class<T> minimumClass, Predicate<T> predicate) {
      return findEntity(level, new AABB(from, to), minimumClass, predicate);
   }

   public static <T extends Entity> Optional<T> findEntity(Level level, AABB bounds, Class<T> minimumClass, Predicate<T> predicate) {
      AtomicReference<T> foundEntity = new AtomicReference<>(null);
      EntityTypeTest<Entity, T> typeTest = makeLazyTypeTest(minimumClass);
      level.getEntities().get(typeTest, bounds, entityx -> {
         if (predicate.test((T)entityx)) {
            foundEntity.set((T)entityx);
            return Continuation.ABORT;
         } else {
            return Continuation.CONTINUE;
         }
      });
      if (foundEntity.get() == null) {
         Pair<Collection<? extends Entity>, Function<Entity, ? extends Entity>> partEntities = SBLConstants.SBL_LOADER.getPartEntities(level);

         for (Entity part : (Collection)partEntities.getFirst()) {
            T entity = (T)typeTest.tryCast((Entity)((Function)partEntities.getSecond()).apply(part));
            if (entity != null && part.getBoundingBox().intersects(bounds) && predicate.test(entity)) {
               foundEntity.set(entity);
               break;
            }
         }
      }

      return Optional.ofNullable(foundEntity.get());
   }

   public static <T extends Entity> List<T> findEntities(Entity origin, double radius, int max, Predicate<Entity> predicate) {
      return findEntities(origin, radius, radius, radius, max, predicate);
   }

   public static <T extends Entity> List<T> findEntities(Entity origin, double radiusX, double radiusY, double radiusZ, int max, Predicate<Entity> predicate) {
      return findEntities(
         origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), (Class<T>)Entity.class, max, (entity -> entity != origin).and(predicate)
      );
   }

   public static <T extends Entity> List<T> findEntities(Level level, Vec3 origin, double radius, int max, Predicate<Entity> predicate) {
      return findEntities(level, origin, radius, radius, radius, max, predicate);
   }

   public static <T extends Entity> List<T> findEntities(
      Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ, int max, Predicate<Entity> predicate
   ) {
      return findEntities(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), (Class<T>)Entity.class, max, predicate);
   }

   public static <T extends Entity> List<T> findEntities(Level level, Vec3 from, Vec3 to, int max, Predicate<Entity> predicate) {
      return findEntities(level, new AABB(from, to), (Class<T>)Entity.class, max, predicate);
   }

   public static <T extends Entity> List<T> findEntities(Entity origin, double radius, Class<T> minimumClass, int max, Predicate<? super T> predicate) {
      return findEntities(origin, radius, radius, radius, minimumClass, max, predicate);
   }

   public static <T extends Entity> List<T> findEntities(
      Entity origin, double radiusX, double radiusY, double radiusZ, Class<T> minimumClass, int max, Predicate<? super T> predicate
   ) {
      return findEntities(
         origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), minimumClass, max, (entity -> entity != origin).and(predicate)
      );
   }

   public static <T extends Entity> List<T> findEntities(
      Level level, Vec3 origin, double radius, Class<T> minimumClass, int max, Predicate<? super T> predicate
   ) {
      return findEntities(level, AABB.ofSize(origin, radius, radius, radius), minimumClass, max, predicate);
   }

   public static <T extends Entity> List<T> findEntities(
      Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ, Class<T> minimumClass, int max, Predicate<? super T> predicate
   ) {
      return findEntities(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), minimumClass, max, predicate);
   }

   public static <T extends Entity> List<T> findEntities(Level level, Vec3 from, Vec3 to, Class<T> minimumClass, int max, Predicate<? super T> predicate) {
      return findEntities(level, new AABB(from, to), minimumClass, max, predicate);
   }

   public static <T extends Entity> List<T> findEntities(Level level, AABB bounds, Class<T> minimumClass, int max, Predicate<? super T> predicate) {
      List<T> foundEntities = new ObjectArrayList(max);
      EntityTypeTest<Entity, T> typeTest = makeLazyTypeTest(minimumClass);
      level.getEntities().get(typeTest, bounds, entityx -> {
         if (predicate.test((T)entityx)) {
            foundEntities.add((T)entityx);
            if (foundEntities.size() >= max) {
               return Continuation.ABORT;
            }
         }

         return Continuation.CONTINUE;
      });
      if (foundEntities.size() < max) {
         Pair<Collection<? extends Entity>, Function<Entity, ? extends Entity>> partEntities = SBLConstants.SBL_LOADER.getPartEntities(level);

         for (Entity part : (Collection)partEntities.getFirst()) {
            T entity = (T)typeTest.tryCast((Entity)((Function)partEntities.getSecond()).apply(part));
            if (entity != null && part.getBoundingBox().intersects(bounds) && predicate.test(entity)) {
               foundEntities.add(entity);
               if (foundEntities.size() >= max) {
                  break;
               }
            }
         }
      }

      return foundEntities;
   }

   public static Stream<Entity> streamEntities(Entity origin, double radius) {
      return streamEntities(origin, radius, radius, radius);
   }

   public static Stream<Entity> streamEntities(Entity origin, double radiusX, double radiusY, double radiusZ) {
      return streamEntities(origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), Entity.class);
   }

   public static Stream<Entity> streamEntities(Level level, Vec3 origin, double radius) {
      return streamEntities(level, origin, radius, radius, radius);
   }

   public static Stream<Entity> streamEntities(Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ) {
      return streamEntities(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), Entity.class);
   }

   public static Stream<Entity> streamEntities(Level level, Vec3 fromPos, Vec3 toPos) {
      return streamEntities(level, new AABB(fromPos, toPos), Entity.class);
   }

   public static <T extends Entity> Stream<T> streamEntities(Entity origin, double radius, Class<T> minimumClass) {
      return streamEntities(origin, radius, radius, radius, minimumClass);
   }

   public static <T extends Entity> Stream<T> streamEntities(Entity origin, double radiusX, double radiusY, double radiusZ, Class<T> minimumClass) {
      return streamEntities(origin.level(), origin.getBoundingBox().inflate(radiusX, radiusY, radiusZ), minimumClass);
   }

   public static <T extends Entity> Stream<T> streamEntities(Level level, Vec3 origin, double radius, Class<T> minimumClass) {
      return streamEntities(level, origin, radius, radius, radius, minimumClass);
   }

   public static <T extends Entity> Stream<T> streamEntities(Level level, Vec3 origin, double radiusX, double radiusY, double radiusZ, Class<T> minimumClass) {
      return streamEntities(level, AABB.ofSize(origin, radiusX, radiusY, radiusZ), minimumClass);
   }

   public static <T extends Entity> Stream<T> streamEntities(Level level, Vec3 fromPos, Vec3 toPos, Class<T> minimumClass) {
      return streamEntities(level, new AABB(fromPos, toPos), minimumClass);
   }

   public static <T extends Entity> Stream<T> streamEntities(Level level, AABB bounds, Class<T> minimumClass) {
      level.getProfiler().incrementCounter("getEntities");
      if (level.getEntities() instanceof LevelEntityGetterAdapter<Entity> entities) {
         EntitySectionStorage var14 = entities.sectionStorage;
         EntityTypeTest clazzLookup = makeLazyTypeTest(minimumClass);
         int minSectionX = SectionPos.posToSectionCoord(bounds.minX - 2.0);
         int minSectionY = SectionPos.posToSectionCoord(bounds.minY - 4.0);
         int minSectionZ = SectionPos.posToSectionCoord(bounds.minZ - 2.0);
         int maxSectionX = SectionPos.posToSectionCoord(bounds.maxX + 2.0);
         int maxSectionY = SectionPos.posToSectionCoord(bounds.maxY);
         int maxSectionZ = SectionPos.posToSectionCoord(bounds.maxZ + 2.0);
         Pair partEntities = SBLConstants.SBL_LOADER.getPartEntities(level);
         Stream stream = IntStream.rangeClosed(minSectionX, maxSectionX)
            .mapToObj(sectionX -> var14.sectionIds.subSet(SectionPos.asLong(sectionX, 0, 0), SectionPos.asLong(sectionX, -1, -1)).iterator())
            .<Long>mapMulti((iterator, consumer) -> consumer.accept(iterator.nextLong()))
            .filter(sectionId -> {
               int sectionYPos = SectionPos.y(sectionId);
               if (sectionYPos >= minSectionY && sectionYPos <= maxSectionY) {
                  int sectionZPos = SectionPos.z(sectionId);
                  return sectionZPos >= minSectionZ && sectionZPos <= maxSectionZ;
               } else {
                  return false;
               }
            })
            .<EntitySection>map(var14::getSection)
            .filter(section -> section != null && !section.isEmpty() && section.getStatus().isAccessible())
            .map(section -> section.storage.find(clazzLookup.getBaseClass()))
            .filter(collection -> !collection.isEmpty())
            .mapMulti(Iterable::forEach)
            .<Entity>map(clazzLookup::tryCast)
            .filter(entity -> entity != null && entity.getBoundingBox().intersects(bounds));
         return ((Collection)partEntities.getFirst()).isEmpty()
            ? stream
            : Stream.concat(
               stream,
               ((Collection)partEntities.getFirst())
                  .stream()
                  .map(entity -> Pair.of(entity, (Entity)clazzLookup.tryCast((Entity)((Function)partEntities.getSecond()).apply(entity))))
                  .filter(entity -> ((Entity)entity.getFirst()).getBoundingBox().intersects(bounds))
                  .map(Pair::getSecond)
            );
      } else {
         return (minimumClass == Entity.class ? getEntities(level, bounds, entity -> true) : getEntities(level, bounds, minimumClass, entity -> true)).stream();
      }
   }

   @Internal
   private static <T extends Entity> EntityTypeTest<Entity, T> makeLazyTypeTest(Class<T> forClass) {
      return forClass != Entity.class ? EntityTypeTest.forClass(forClass) : new EntityTypeTest<Entity, Entity>() {
         @Nullable
         public Entity tryCast(Entity entity) {
            return entity;
         }

         public Class<? extends Entity> getBaseClass() {
            return Entity.class;
         }
      };
   }
}
