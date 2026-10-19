package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.AmphithereEntity;
import com.iafenvoy.iceandfire.util.IafMath;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class AmphithereAIFleePlayerGoal extends Goal {
   protected final AmphithereEntity entity;
   private final double farSpeed;
   private final double nearSpeed;
   private final float avoidDistance;
   protected Player closestLivingEntity;
   private Path path;
   private List<Player> list = IafMath.emptyPlayerEntityList;

   public AmphithereAIFleePlayerGoal(AmphithereEntity entityIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
      this.entity = entityIn;
      this.avoidDistance = avoidDistanceIn;
      this.farSpeed = farSpeedIn;
      this.nearSpeed = nearSpeedIn;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      if (!this.entity.isFlying() && !this.entity.isTame()) {
         if (this.entity.level().getGameTime() % 4L == 0L) {
            this.list = this.entity
               .level()
               .getEntitiesOfClass(
                  Player.class, this.entity.getBoundingBox().inflate(this.avoidDistance, 6.0, this.avoidDistance), EntitySelector.NO_CREATIVE_OR_SPECTATOR
               );
         }

         if (this.list.isEmpty()) {
            return false;
         }

         this.closestLivingEntity = this.list.getFirst();
         Vec3 Vector3d = DefaultRandomPos.getPosAway(
            this.entity, 20, 7, new Vec3(this.closestLivingEntity.getX(), this.closestLivingEntity.getY(), this.closestLivingEntity.getZ())
         );
         if (Vector3d == null) {
            return false;
         }

         if (this.closestLivingEntity.distanceToSqr(Vector3d) < this.closestLivingEntity.distanceToSqr(this.entity)) {
            return false;
         }

         this.path = this.entity.getNavigation().createPath(Vector3d.x, Vector3d.y, Vector3d.z, 0);
         return this.path != null;
      } else {
         this.list = IafMath.emptyPlayerEntityList;
         return false;
      }
   }

   public boolean canContinueToUse() {
      return !this.entity.getNavigation().isDone();
   }

   public void start() {
      this.entity.getNavigation().moveTo(this.path, this.farSpeed);
   }

   public void stop() {
      this.closestLivingEntity = null;
   }

   public void tick() {
      this.entity.getNavigation().setSpeedModifier(this.entity.distanceToSqr(this.closestLivingEntity) < 49.0 ? this.nearSpeed : this.farSpeed);
   }
}
