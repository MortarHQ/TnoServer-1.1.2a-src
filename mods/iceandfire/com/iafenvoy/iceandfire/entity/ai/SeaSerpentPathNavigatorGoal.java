package com.iafenvoy.iceandfire.entity.ai;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class SeaSerpentPathNavigatorGoal extends PathNavigation {
   public SeaSerpentPathNavigatorGoal(Mob entitylivingIn, Level worldIn) {
      super(entitylivingIn, worldIn);
   }

   protected PathFinder createPathFinder(int p_179679_1_) {
      this.nodeEvaluator = new SwimNodeEvaluator(true);
      return new PathFinder(this.nodeEvaluator, p_179679_1_);
   }

   protected boolean canUpdatePath() {
      return true;
   }

   protected Vec3 getTempMobPos() {
      return new Vec3(this.mob.getX(), this.mob.getY(0.5), this.mob.getZ());
   }

   public void tick() {
      this.tick++;
      if (this.hasDelayedRecomputation) {
         this.recomputePath();
      }

      if (!this.isDone()) {
         if (this.canUpdatePath()) {
            this.followThePath();
         } else if (this.path != null && !this.path.isDone()) {
            Vec3 vec3d = this.path.getNextEntityPos(this.mob);
            if (Mth.floor(this.mob.getX()) == Mth.floor(vec3d.x)
               && Mth.floor(this.mob.getY()) == Mth.floor(vec3d.y)
               && Mth.floor(this.mob.getZ()) == Mth.floor(vec3d.z)) {
               this.path.advance();
            }
         }

         DebugPackets.sendPathFindingPacket(this.level, this.mob, this.path, this.maxDistanceToWaypoint);
         if (!this.isDone()) {
            assert this.path != null;
            Vec3 vec3d = this.path.getNextEntityPos(this.mob);
            this.mob.getMoveControl().setWantedPosition(vec3d.x, vec3d.y, vec3d.z, this.speedModifier);
         }
      }
   }

   protected void followThePath() {
      if (this.path != null) {
         Vec3 entityPos = this.getTempMobPos();
         float entityWidth = this.mob.getBbWidth();
         float range = entityWidth > 0.75F ? entityWidth / 2.0F : 0.75F - entityWidth / 2.0F;
         Vec3 velocity = this.mob.getDeltaMovement();
         if (Math.abs(velocity.x) > 0.2 || Math.abs(velocity.z) > 0.2) {
            range = (float)(range * velocity.length() * 6.0);
         }

         Vec3 center = Vec3.atCenterOf(this.path.getNextNodePos());
         if (Math.abs(this.mob.getX() - center.x) < range
            && Math.abs(this.mob.getZ() - center.z) < range
            && Math.abs(this.mob.getY() - center.y) < range * 2.0F) {
            this.path.advance();
         }

         for (int lvt_7_1_ = Math.min(this.path.getNextNodeIndex() + 6, this.path.getNodeCount() - 1); lvt_7_1_ > this.path.getNextNodeIndex(); lvt_7_1_--) {
            center = this.path.getEntityPosAtNode(this.mob, lvt_7_1_);
            if (center.distanceToSqr(entityPos) <= 36.0 && this.canMoveDirectly(entityPos, center)) {
               this.path.setNextNodeIndex(lvt_7_1_);
               break;
            }
         }

         this.doStuckDetection(entityPos);
      }
   }

   protected void doStuckDetection(Vec3 positionVec3) {
      if (this.tick - this.lastStuckCheck > 100) {
         if (positionVec3.distanceToSqr(this.lastStuckCheckPos) < 2.25) {
            this.stop();
         }

         this.lastStuckCheck = this.tick;
         this.lastStuckCheckPos = positionVec3;
      }

      if (this.path != null && !this.path.isDone()) {
         Vec3i pos = this.path.getNextNodePos();
         if (pos.equals(this.timeoutCachedNode)) {
            this.timeoutTimer = this.timeoutTimer + (Util.getMillis() - this.lastTimeoutCheck);
         } else {
            this.timeoutCachedNode = pos;
            double lvt_3_1_ = positionVec3.distanceTo(Vec3.atCenterOf(this.timeoutCachedNode));
            this.timeoutLimit = this.mob.getSpeed() > 0.0F ? lvt_3_1_ / this.mob.getSpeed() * 100.0 : 0.0;
         }

         if (this.timeoutLimit > 0.0 && this.timeoutTimer > this.timeoutLimit * 2.0) {
            this.timeoutCachedNode = Vec3i.ZERO;
            this.timeoutTimer = 0L;
            this.timeoutLimit = 0.0;
            this.stop();
         }

         this.lastTimeoutCheck = Util.getMillis();
      }
   }

   protected boolean canMoveDirectly(Vec3 posVec31, Vec3 posVec32) {
      Vec3 lvt_6_1_ = new Vec3(posVec32.x, posVec32.y + this.mob.getBbHeight() * 0.5, posVec32.z);
      return this.level.clip(new ClipContext(posVec31, lvt_6_1_, Block.COLLIDER, Fluid.NONE, this.mob)).getType() == Type.MISS;
   }

   public boolean isStableDestination(BlockPos pos) {
      return !this.level.getBlockState(pos).isSolidRender(this.level, pos);
   }

   public void setCanFloat(boolean canSwim) {
   }
}
