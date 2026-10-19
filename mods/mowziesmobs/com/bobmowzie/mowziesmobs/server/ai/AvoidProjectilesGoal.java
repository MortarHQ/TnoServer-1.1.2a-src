package com.bobmowzie.mowziesmobs.server.ai;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AvoidProjectilesGoal extends Goal {
   protected final PathfinderMob entity;
   private final double farSpeed;
   private final double nearSpeed;
   protected Projectile avoidTarget;
   protected final float avoidDistance;
   protected Path path;
   protected Vec3 dodgeVec;
   protected final PathNavigation navigation;
   protected final Class<Projectile> classToAvoid;
   protected final Predicate<Projectile> avoidTargetSelector;
   private int dodgeTimer = 0;

   public AvoidProjectilesGoal(PathfinderMob entityIn, Class<Projectile> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
      this(entityIn, classToAvoidIn, entity -> true, avoidDistanceIn, farSpeedIn, nearSpeedIn);
   }

   public AvoidProjectilesGoal(
      PathfinderMob entityIn, Class<Projectile> avoidClass, Predicate<Projectile> targetPredicate, float distance, double nearSpeedIn, double farSpeedIn
   ) {
      this.entity = entityIn;
      this.classToAvoid = avoidClass;
      this.avoidTargetSelector = targetPredicate.and(target -> {
         Vec3 aActualMotion = new Vec3(target.getX() - target.xo, target.getY() - target.yo, target.getZ() - target.zo);
         if (!(aActualMotion.length() < 0.1) && target.tickCount >= 0) {
            if (!this.entity.getSensing().hasLineOfSight(target)) {
               return false;
            }

            float dot = (float)target.getDeltaMovement().normalize().dot(this.entity.position().subtract(target.position()).normalize());
            return !(dot < 0.8);
         } else {
            return false;
         }
      });
      this.avoidDistance = distance;
      this.farSpeed = nearSpeedIn;
      this.nearSpeed = farSpeedIn;
      this.navigation = entityIn.getNavigation();
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      if (this.dodgeTimer > 0) {
         return false;
      }

      this.avoidTarget = this.getMostMovingTowardsMeEntity(
         this.classToAvoid, this.avoidTargetSelector, this.entity, this.entity.getBoundingBox().inflate(this.avoidDistance, 3.0, this.avoidDistance)
      );
      if (this.avoidTarget == null) {
         return false;
      }

      Vec3 projectilePos = this.guessProjectileDestination(this.avoidTarget);
      this.dodgeVec = this.avoidTarget.getDeltaMovement().cross(new Vec3(0.0, 1.0, 0.0)).normalize().scale(1.0);
      Vec3 newPosLeft = this.entity.position().add(this.dodgeVec);
      Vec3 newPosRight = this.entity.position().add(this.dodgeVec.scale(-1.0));
      Vec3 diffLeft = newPosLeft.subtract(projectilePos);
      Vec3 diffRight = newPosRight.subtract(projectilePos);
      if (diffRight.lengthSqr() > diffLeft.lengthSqr()) {
         this.dodgeVec = this.dodgeVec.scale(-1.0);
      }

      Vec3 dodgeDest = diffRight.lengthSqr() > diffLeft.lengthSqr() ? newPosRight : newPosLeft;
      Vec3 vector3d = DefaultRandomPos.getPosTowards(this.entity, 5, 3, dodgeDest, (float) (Math.PI / 2));
      if (vector3d == null) {
         this.path = null;
         return true;
      }

      if (projectilePos.subtract(vector3d).lengthSqr() < projectilePos.subtract(this.entity.position()).lengthSqr()) {
         return false;
      }

      this.path = this.navigation.createPath(vector3d.x, vector3d.y, vector3d.z, 0);
      return true;
   }

   public boolean canContinueToUse() {
      return !this.navigation.isDone() || this.dodgeTimer < 10;
   }

   public void start() {
      if (this.path != null) {
         this.navigation.moveTo(this.path, this.farSpeed);
         this.dodgeVec = this.path.getNextEntityPos(this.entity).subtract(this.entity.position()).normalize().scale(1.0);
      }

      this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(this.dodgeVec));
   }

   public void stop() {
      this.avoidTarget = null;
      this.dodgeTimer = 0;
   }

   public void tick() {
      if (this.entity.distanceToSqr(this.avoidTarget) < 49.0) {
         this.entity.getNavigation().setSpeedModifier(this.nearSpeed);
      } else {
         this.entity.getNavigation().setSpeedModifier(this.farSpeed);
      }

      this.dodgeTimer++;
   }

   private Vec3 guessProjectileDestination(Projectile projectile) {
      Vec3 vector3d = projectile.position();
      Vec3 vector3d1 = vector3d.add(projectile.getDeltaMovement().scale(50.0));
      return this.entity.level().clip(new ClipContext(vector3d, vector3d1, Block.COLLIDER, Fluid.NONE, projectile)).getLocation();
   }

   @Nullable
   private <T extends Projectile> T getMostMovingTowardsMeEntity(
      Class<? extends T> entityClazz, Predicate<? super T> predicate, LivingEntity entity, AABB p_225318_10_
   ) {
      return this.getMostMovingTowardsMeEntityFromList(entity.level().getEntitiesOfClass(entityClazz, p_225318_10_, predicate), entity);
   }

   private <T extends Projectile> T getMostMovingTowardsMeEntityFromList(List<? extends T> entities, LivingEntity target) {
      double d0 = -2.0;
      T t = null;

      for (T t1 : entities) {
         double d1 = t1.getDeltaMovement().normalize().dot(target.position().subtract(t1.position()).normalize());
         if (d1 > d0) {
            d0 = d1;
            t = t1;
         }
      }

      return t;
   }
}
