package com.bobmowzie.mowziesmobs.server.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class MMAIAvoidEntity<U extends PathfinderMob, T extends Entity> extends Goal {
   private double nearDistance = 7.0;
   protected final U entity;
   private final Predicate<T> selector;
   private final double farSpeed;
   private final double nearSpeed;
   private final float evadeDistance;
   private final Class<T> avoidedEntityType;
   private final int horizontalEvasion;
   private final int verticalEvasion;
   private final int numChecks;
   private T entityEvading;
   private Path entityPathEntity;
   private boolean isActive;

   public MMAIAvoidEntity(U entity, Class<T> avoidedEntityType, float evadeDistance, double farSpeed, double nearSpeed) {
      this(entity, avoidedEntityType, Predicates.alwaysTrue(), evadeDistance, farSpeed, nearSpeed, 10, 12, 7, 7.0);
   }

   public MMAIAvoidEntity(
      U entity, Class<T> avoidedEntityType, float evadeDistance, double farSpeed, double nearSpeed, int numChecks, int horizontalEvasion, int verticalEvasion
   ) {
      this(entity, avoidedEntityType, Predicates.alwaysTrue(), evadeDistance, farSpeed, nearSpeed, numChecks, horizontalEvasion, verticalEvasion, 7.0);
   }

   public MMAIAvoidEntity(
      U entity,
      Class<T> avoidedEntityType,
      Predicate<? super T> predicate,
      float evadeDistance,
      double farSpeed,
      double nearSpeed,
      int numChecks,
      int horizontalEvasion,
      int verticalEvasion
   ) {
      this(entity, avoidedEntityType, predicate, evadeDistance, farSpeed, nearSpeed, numChecks, horizontalEvasion, verticalEvasion, 7.0);
   }

   public MMAIAvoidEntity(
      U entity,
      Class<T> avoidedEntityType,
      Predicate<? super T> predicate,
      float evadeDistance,
      double farSpeed,
      double nearSpeed,
      int numChecks,
      int horizontalEvasion,
      int verticalEvasion,
      double nearDistance
   ) {
      this.entity = entity;
      this.selector = e -> e != null
         && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e)
         && e.isAlive()
         && entity.getSensing().hasLineOfSight(e)
         && !entity.isAlliedTo(e)
         && predicate.test(e);
      this.avoidedEntityType = avoidedEntityType;
      this.evadeDistance = evadeDistance;
      this.farSpeed = farSpeed;
      this.nearSpeed = nearSpeed;
      this.numChecks = numChecks;
      this.horizontalEvasion = horizontalEvasion;
      this.verticalEvasion = verticalEvasion;
      this.nearDistance = nearDistance;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      List<T> entities = this.entity
         .level()
         .getEntitiesOfClass(this.avoidedEntityType, this.entity.getBoundingBox().inflate(this.evadeDistance, 3.0, this.evadeDistance), this.selector);
      if (entities.isEmpty()) {
         this.onSafe();
         return false;
      }

      this.entityEvading = entities.get(0);

      for (int n = 0; n < this.numChecks; n++) {
         Vec3 pos = DefaultRandomPos.getPosAway(this.entity, this.horizontalEvasion, this.verticalEvasion, this.entityEvading.position());
         if (pos != null && !(this.entityEvading.distanceToSqr(pos.x, pos.y, pos.z) < this.entityEvading.distanceToSqr(this.entity))) {
            this.entityPathEntity = this.entity.getNavigation().createPath(BlockPos.containing(pos), 0);
            if (this.entityPathEntity != null) {
               return true;
            }
         }
      }

      this.onPathNotFound();
      return false;
   }

   protected void onSafe() {
   }

   protected void onPathNotFound() {
   }

   public boolean canContinueToUse() {
      return !this.entity.getNavigation().isDone();
   }

   public void start() {
      this.isActive = true;
      this.entity.getNavigation().moveTo(this.entityPathEntity, this.farSpeed);
   }

   public void stop() {
      this.isActive = false;
      this.entityEvading = null;
   }

   public void tick() {
      this.entity
         .getNavigation()
         .setSpeedModifier(this.entity.distanceToSqr(this.entityEvading) < this.nearDistance * this.nearDistance ? this.nearSpeed : this.farSpeed);
   }

   public T getEntityEvading() {
      return this.entityEvading;
   }

   public boolean isActive() {
      return this.isActive;
   }
}
