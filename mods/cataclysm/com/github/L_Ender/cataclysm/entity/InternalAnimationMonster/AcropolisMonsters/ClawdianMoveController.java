package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AcropolisMonsters;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ClawdianMoveController extends MoveControl {
   protected ClawdianMoveController.ClawdianOperation operation = ClawdianMoveController.ClawdianOperation.WAIT;

   public ClawdianMoveController(Mob mob) {
      super(mob);
   }

   public double getSpeedModifier() {
      return super.getSpeedModifier();
   }

   public void setWantedPosition(double x, double y, double z, double speed) {
      this.wantedX = x;
      this.wantedY = y;
      this.wantedZ = z;
      this.speedModifier = speed;
      if (this.operation != ClawdianMoveController.ClawdianOperation.JUMPING) {
         this.operation = ClawdianMoveController.ClawdianOperation.MOVE_TO;
      }
   }

   public void strafe(float forward, float strafe) {
      this.operation = ClawdianMoveController.ClawdianOperation.STRAFE;
      this.strafeForwards = forward;
      this.strafeRight = strafe;
      this.speedModifier = 1.15;
   }

   public void forward(float forward, float speed) {
      this.operation = ClawdianMoveController.ClawdianOperation.FORWARD;
      this.strafeForwards = forward;
      this.speedModifier = speed;
   }

   public void tick() {
      if (this.operation == ClawdianMoveController.ClawdianOperation.STRAFE) {
         float baseSpeed = (float)this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
         float adjustedSpeed = (float)this.speedModifier * baseSpeed;
         float forward = this.strafeForwards;
         float right = this.strafeRight;
         float mag = Mth.sqrt(forward * forward + right * right);
         if (mag < 1.0F) {
            mag = 1.0F;
         }

         forward *= adjustedSpeed / mag;
         right *= adjustedSpeed / mag;
         float yawRad = this.mob.getYRot() * (float) (Math.PI / 180.0);
         float sin = Mth.sin(yawRad);
         float cos = Mth.cos(yawRad);
         float dx = forward * cos - right * sin;
         float dz = right * cos + forward * sin;
         if (this.isWalkable(dx, dz)) {
            this.mob.setSpeed(adjustedSpeed);
            this.mob.setZza(this.strafeForwards);
            this.mob.setXxa(this.strafeRight);
         } else {
            this.mob.setZza(0.0F);
            this.mob.setXxa(0.0F);
         }

         this.operation = ClawdianMoveController.ClawdianOperation.WAIT;
      } else if (this.operation == ClawdianMoveController.ClawdianOperation.FORWARD) {
         this.strafeRight = 0.0F;
         this.ForwardMoveControl();
         this.operation = ClawdianMoveController.ClawdianOperation.WAIT;
      } else if (this.operation == ClawdianMoveController.ClawdianOperation.MOVE_TO) {
         this.operation = ClawdianMoveController.ClawdianOperation.WAIT;
         double d0 = this.wantedX - this.mob.getX();
         double d1 = this.wantedZ - this.mob.getZ();
         double d2 = this.wantedY - this.mob.getY();
         double d3 = d0 * d0 + d2 * d2 + d1 * d1;
         if (d3 < 2.5000003E-7F) {
            this.mob.setZza(0.0F);
            return;
         }

         float f9 = (float)(Mth.atan2(d1, d0) * 180.0 / (float) Math.PI) - 90.0F;
         this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f9, 90.0F));
         this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
         BlockPos blockpos = this.mob.blockPosition();
         BlockState blockstate = this.mob.level().getBlockState(blockpos);
         VoxelShape voxelshape = blockstate.getCollisionShape(this.mob.level(), blockpos);
         if (d2 > this.mob.maxUpStep() && d0 * d0 + d1 * d1 < Math.max(1.0F, this.mob.getBbWidth())
            || !voxelshape.isEmpty()
               && this.mob.getY() < voxelshape.max(Axis.Y) + blockpos.getY()
               && !blockstate.is(BlockTags.DOORS)
               && !blockstate.is(BlockTags.FENCES)) {
            this.mob.getJumpControl().jump();
            this.operation = ClawdianMoveController.ClawdianOperation.JUMPING;
         }
      } else if (this.operation == ClawdianMoveController.ClawdianOperation.JUMPING) {
         this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
         if (this.mob.onGround()) {
            this.operation = ClawdianMoveController.ClawdianOperation.WAIT;
         }
      } else {
         this.mob.setZza(0.0F);
      }
   }

   private void ForwardMoveControl() {
      float speedAttr = (float)this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
      float finalSpeed = (float)this.speedModifier * speedAttr;
      float fForward = this.strafeForwards;
      float fStrafe = this.strafeRight;
      float dist = Mth.sqrt(fForward * fForward + fStrafe * fStrafe);
      if (dist < 1.0F) {
         dist = 1.0F;
      }

      fForward /= dist;
      fStrafe /= dist;
      float sin = Mth.sin(this.mob.getYRot() * (float) (Math.PI / 180.0));
      float cos = Mth.cos(this.mob.getYRot() * (float) (Math.PI / 180.0));
      float worldRelX = fStrafe * cos - fForward * sin;
      float worldRelZ = fForward * cos + fStrafe * sin;
      if (!this.isWalkableForward(worldRelX, worldRelZ)) {
         this.strafeForwards = 0.0F;
         this.strafeRight = 0.0F;
         this.mob.setZza(0.0F);
         this.mob.setXxa(0.0F);
         this.mob.setSpeed(0.0F);
      } else {
         fForward *= finalSpeed;
         fStrafe *= finalSpeed;
         this.mob.setSpeed(finalSpeed);
         this.mob.setZza(fForward);
         this.mob.setXxa(fStrafe);
      }
   }

   private boolean isWalkableForward(float worldRelX, float worldRelZ) {
      float lenSq = worldRelX * worldRelX + worldRelZ * worldRelZ;
      if (lenSq < 1.0E-5F) {
         return true;
      }

      float lookAheadDist = this.mob.getBbWidth() / 2.0F + 1.2F;
      float len = Mth.sqrt(lenSq);
      float checkX = worldRelX / len * lookAheadDist;
      float checkZ = worldRelZ / len * lookAheadDist;
      BlockPos targetPos = BlockPos.containing(this.mob.getX() + checkX, this.mob.getY(), this.mob.getZ() + checkZ);
      int maxFallDist = 4;

      for (int i = 0; i <= maxFallDist; i++) {
         BlockPos checkPos = targetPos.below(i);
         BlockState state = this.mob.level().getBlockState(checkPos);
         boolean isSolid = !state.isAir() && state.getFluidState().isEmpty() && !state.getCollisionShape(this.mob.level(), checkPos).isEmpty();
         if (isSolid) {
            if (i == 0) {
               return true;
            }

            return true;
         }
      }

      return false;
   }

   private boolean isWalkable(float relativeX, float relativeZ) {
      PathNavigation pathnavigation = this.mob.getNavigation();
      if (pathnavigation != null) {
         NodeEvaluator nodeevaluator = pathnavigation.getNodeEvaluator();
         if (nodeevaluator != null
            && nodeevaluator.getPathType(this.mob, BlockPos.containing(this.mob.getX() + relativeX, this.mob.getBlockY(), this.mob.getZ() + relativeZ))
               != PathType.WALKABLE) {
            return false;
         }
      }

      return true;
   }

   protected float rotlerp(float sourceAngle, float targetAngle, float maximumChange) {
      float f = Mth.wrapDegrees(targetAngle - sourceAngle);
      if (f > maximumChange) {
         f = maximumChange;
      }

      if (f < -maximumChange) {
         f = -maximumChange;
      }

      float f1 = sourceAngle + f;
      if (f1 < 0.0F) {
         f1 += 360.0F;
      } else if (f1 > 360.0F) {
         f1 -= 360.0F;
      }

      return f1;
   }

   protected enum ClawdianOperation {
      WAIT,
      MOVE_TO,
      FORWARD,
      STRAFE,
      JUMPING;
   }
}
