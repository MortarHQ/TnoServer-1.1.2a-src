package com.bobmowzie.mowziesmobs.server.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.PathType;

public class MMEntityMoveHelper extends MoveControl {
   private float maxRotate = 90.0F;

   public MMEntityMoveHelper(Mob entitylivingIn, float maxRotate) {
      super(entitylivingIn);
      this.maxRotate = maxRotate;
   }

   public void tick() {
      if (this.operation == Operation.STRAFE) {
         float f = (float)this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
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
         PathNavigation pathnavigate = this.mob.getNavigation();
         if (pathnavigate != null) {
            NodeEvaluator nodeprocessor = pathnavigate.getNodeEvaluator();
            if (nodeprocessor != null
               && nodeprocessor.getPathType(this.mob, BlockPos.containing(this.mob.getX() + f7, this.mob.getY(), this.mob.getZ() + f8)) != PathType.WALKABLE) {
               this.strafeForwards = 1.0F;
               this.strafeRight = 0.0F;
               f1 = f;
            }
         }

         this.mob.setSpeed(f1);
         this.mob.setZza(this.strafeForwards);
         this.mob.setXxa(this.strafeRight);
         this.operation = Operation.WAIT;
      } else if (this.operation == Operation.MOVE_TO) {
         this.operation = Operation.WAIT;
         double d0 = this.wantedX - this.mob.getX();
         double d1 = this.wantedZ - this.mob.getZ();
         double d2 = this.wantedY - this.mob.getY();
         double d3 = d0 * d0 + d2 * d2 + d1 * d1;
         if (d3 < 2.5000003E-7F) {
            this.mob.setZza(0.0F);
            return;
         }

         float f9 = (float)(Mth.atan2(d1, d0) * (180.0 / Math.PI)) - 90.0F;
         this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f9, this.maxRotate));
         this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));
         if (d2 > this.mob.maxUpStep() && d0 * d0 + d1 * d1 < Math.max(1.0F, this.mob.getBbWidth())) {
            this.mob.getJumpControl().jump();
            this.operation = Operation.JUMPING;
         }
      } else if (this.operation == Operation.JUMPING) {
         this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));
         if (this.mob.onGround()) {
            this.operation = Operation.WAIT;
         }
      } else {
         this.mob.setZza(0.0F);
      }
   }
}
