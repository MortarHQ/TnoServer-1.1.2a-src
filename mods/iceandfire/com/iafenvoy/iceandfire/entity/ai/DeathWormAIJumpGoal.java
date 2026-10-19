package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.DeathWormEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.phys.Vec3;

public class DeathWormAIJumpGoal extends JumpGoal {
   private static final int[] JUMP_DISTANCES = new int[]{0, 1, 4, 5, 6, 7};
   private final DeathWormEntity dolphin;
   private final int chance;
   private boolean inWater;
   private int jumpCooldown;

   public DeathWormAIJumpGoal(DeathWormEntity dolphin, int p_i50329_2_) {
      this.dolphin = dolphin;
      this.chance = p_i50329_2_;
   }

   public boolean canUse() {
      if (this.jumpCooldown > 0) {
         this.jumpCooldown--;
      }

      if (this.dolphin.getRandom().nextInt(this.chance) == 0 && !this.dolphin.isVehicle() && this.dolphin.getTarget() == null) {
         Direction direction = this.dolphin.getMotionDirection();
         int i = direction.getStepX();
         int j = direction.getStepZ();
         BlockPos blockpos = this.dolphin.blockPosition();

         for (int k : JUMP_DISTANCES) {
            if (!this.canJumpTo(blockpos, i, j, k) || !this.isAirAbove(blockpos, i, j, k)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean canJumpTo(BlockPos pos, int dx, int dz, int scale) {
      BlockPos blockpos = pos.offset(dx * scale, 0, dz * scale);
      return this.dolphin.level().getBlockState(blockpos).is(BlockTags.SAND);
   }

   private boolean isAirAbove(BlockPos pos, int dx, int dz, int scale) {
      return this.dolphin.level().getBlockState(pos.offset(dx * scale, 1, dz * scale)).isAir()
         && this.dolphin.level().getBlockState(pos.offset(dx * scale, 2, dz * scale)).isAir();
   }

   public boolean canContinueToUse() {
      double d0 = this.dolphin.getDeltaMovement().y;
      return this.jumpCooldown > 0
         && (d0 * d0 >= 0.03F || this.dolphin.getXRot() == 0.0F || Math.abs(this.dolphin.getXRot()) >= 10.0F || !this.dolphin.isInSand())
         && !this.dolphin.onGround();
   }

   public boolean isInterruptable() {
      return false;
   }

   public void start() {
      Direction direction = this.dolphin.getMotionDirection();
      float up = (this.dolphin.getAgeScale() > 3.0F ? 0.7F : 0.4F) + this.dolphin.getRandom().nextFloat() * 0.4F;
      this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add(direction.getStepX() * 0.6, up, direction.getStepZ() * 0.6));
      this.dolphin.getNavigation().stop();
      this.dolphin.setWormJumping(30);
      this.jumpCooldown = this.dolphin.getRandom().nextInt(65) + 32;
   }

   public void stop() {
      this.dolphin.setXRot(0.0F);
   }

   public void tick() {
      boolean flag = this.inWater;
      if (!flag) {
         this.inWater = this.dolphin.level().getBlockState(this.dolphin.blockPosition()).is(BlockTags.SAND);
      }

      Vec3 vector3d = this.dolphin.getDeltaMovement();
      if (vector3d.y * vector3d.y < 0.1F && this.dolphin.getXRot() != 0.0F) {
         this.dolphin.setXRot(Mth.rotLerp(this.dolphin.getXRot(), 0.0F, 0.2F));
      } else {
         double d0 = vector3d.horizontalDistance();
         double d1 = Math.signum(-vector3d.y) * Math.acos(d0 / vector3d.length()) * 180.0F / (float)Math.PI;
         this.dolphin.setXRot((float)d1);
      }
   }
}
