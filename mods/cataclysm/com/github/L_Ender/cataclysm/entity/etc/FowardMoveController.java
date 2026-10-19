package com.github.L_Ender.cataclysm.entity.etc;

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

public class FowardMoveController extends MoveControl {
   protected FowardMoveController.FowardOperation operation = FowardMoveController.FowardOperation.WAIT;

   public FowardMoveController(Mob mob) {
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
      if (this.operation != FowardMoveController.FowardOperation.JUMPING) {
         this.operation = FowardMoveController.FowardOperation.MOVE_TO;
      }
   }

   public void strafe(float forward, float strafe) {
      this.operation = FowardMoveController.FowardOperation.STRAFE;
      this.strafeForwards = forward;
      this.strafeRight = strafe;
      this.speedModifier = 0.15F;
   }

   public void forward(float forward, float speed) {
      this.operation = FowardMoveController.FowardOperation.FORWARD;
      this.strafeForwards = forward;
      this.speedModifier = speed;
   }

   public void tick() {
      if (this.operation == FowardMoveController.FowardOperation.STRAFE) {
         float f = (float)this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
         float f1 = (float)this.speedModifier * f;
         float f2 = this.strafeForwards;
         float f3 = this.strafeRight;
         float f4 = Mth.sqrt(f2 * f2 + f3 * f3);
         if (f4 < 1.0F) {
            f4 = 1.0F;
         }

         f4 = f1 / f4;
         f2 *= f4;
         f3 *= f4;
         float f5 = Mth.sin(this.mob.getYRot() * (float) (Math.PI / 180.0));
         float f6 = Mth.cos(this.mob.getYRot() * (float) (Math.PI / 180.0));
         float f7 = f2 * f6 - f3 * f5;
         float f8 = f3 * f6 + f2 * f5;
         if (!this.isWalkable(f7, f8)) {
            this.strafeForwards = 1.0F;
            this.strafeRight = 0.0F;
         }

         this.mob.setSpeed(f1);
         this.mob.setZza(this.strafeForwards);
         this.mob.setXxa(this.strafeRight);
         this.operation = FowardMoveController.FowardOperation.WAIT;
      } else if (this.operation == FowardMoveController.FowardOperation.FORWARD) {
         this.strafeRight = 0.0F;
         this.ForwardMoveControl();
         this.operation = FowardMoveController.FowardOperation.WAIT;
      } else if (this.operation == FowardMoveController.FowardOperation.MOVE_TO) {
         this.operation = FowardMoveController.FowardOperation.WAIT;
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
            this.operation = FowardMoveController.FowardOperation.JUMPING;
         }
      } else if (this.operation == FowardMoveController.FowardOperation.JUMPING) {
         this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
         if (this.mob.onGround()) {
            this.operation = FowardMoveController.FowardOperation.WAIT;
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

   protected enum FowardOperation {
      WAIT,
      MOVE_TO,
      FORWARD,
      STRAFE,
      JUMPING;
   }
}
