package com.bobmowzie.mowziesmobs.server.ai;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public final class EntityAIAvoidEntity<T extends Entity> extends Goal {
   private final PathfinderMob entity;
   private final Class<T> avoidClass;
   private final float distance;
   private final Predicate<T> predicate;
   private final double speed;
   private final PathNavigation navigator;
   private T avoiding;
   private Path path;

   public EntityAIAvoidEntity(PathfinderMob entity, Class<T> avoidClass, float distance, double speed) {
      this(entity, avoidClass, e -> true, distance, speed);
   }

   public EntityAIAvoidEntity(PathfinderMob entity, Class<T> avoidClass, Predicate<? super T> predicate, float distance, double speed) {
      this.entity = entity;
      this.avoidClass = avoidClass;
      this.distance = distance;
      Predicate<T> visible = e -> e.isAlive() && entity.getSensing().hasLineOfSight(e);
      Predicate<T> targetable = e -> !(e instanceof Player) || !e.isSpectator() && !((Player)e).isCreative();
      this.predicate = targetable.and(predicate).and(visible);
      this.speed = speed;
      this.navigator = entity.getNavigation();
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      List<T> entities = this.entity
         .level()
         .getEntitiesOfClass(this.avoidClass, this.entity.getBoundingBox().inflate(this.distance, 3.0, this.distance), this.predicate);
      if (entities.isEmpty()) {
         return false;
      }

      this.avoiding = entities.get(this.entity.getRandom().nextInt(entities.size()));
      Vec3 pos = DefaultRandomPos.getPosAway(
         this.entity,
         (int)(this.distance + 1.0F),
         (int)(this.distance / 2.0F + 1.0F),
         new Vec3(this.avoiding.getX(), this.avoiding.getY(), this.avoiding.getZ())
      );
      if (pos == null) {
         return false;
      }

      if (this.avoiding.distanceToSqr(pos.x, pos.y, pos.z) < this.avoiding.distanceToSqr(this.entity)) {
         return false;
      }

      this.path = this.navigator.createPath(BlockPos.containing(pos), 0);
      return this.path != null;
   }

   public boolean canContinueToUse() {
      return !this.navigator.isDone();
   }

   public void start() {
      this.navigator.moveTo(this.path, this.speed);
   }

   public void stop() {
      this.avoiding = null;
   }

   public void tick() {
      this.entity.getNavigation().setSpeedModifier(this.speed);
   }
}
