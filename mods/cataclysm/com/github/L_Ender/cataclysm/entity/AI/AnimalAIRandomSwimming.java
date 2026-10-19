package com.github.L_Ender.cataclysm.entity.AI;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class AnimalAIRandomSwimming extends RandomStrollGoal {
   private int xzSpread;
   private boolean submerged;
   private int ySpread = 3;

   public AnimalAIRandomSwimming(PathfinderMob creature, double speed, int chance, int xzSpread) {
      super(creature, speed, chance, false);
      this.xzSpread = xzSpread;
      this.submerged = false;
   }

   public AnimalAIRandomSwimming(PathfinderMob creature, double speed, int chance, int xzSpread, boolean submerged) {
      super(creature, speed, chance, false);
      this.xzSpread = xzSpread;
      this.submerged = submerged;
   }

   public AnimalAIRandomSwimming(PathfinderMob creature, double speed, int chance, int xzSpread, int ySpread, boolean submerged) {
      super(creature, speed, chance, false);
      this.xzSpread = xzSpread;
      this.ySpread = ySpread;
      this.submerged = submerged;
   }

   public boolean canUse() {
      if (!this.mob.isVehicle() && this.mob.getTarget() == null && (this.mob.isInWater() || this.mob.isInLava())) {
         if (!this.forceTrigger && this.mob.getRandom().nextInt(this.interval) != 0) {
            return false;
         }

         Vec3 vector3d = this.getPosition();
         if (vector3d == null) {
            return false;
         }

         this.wantedX = vector3d.x;
         this.wantedY = vector3d.y;
         this.wantedZ = vector3d.z;
         this.forceTrigger = false;
         return true;
      } else {
         return false;
      }
   }

   @Nullable
   protected Vec3 getPosition() {
      if (this.mob.hasRestriction()
         && this.mob.distanceToSqr(Vec3.atCenterOf(this.mob.getRestrictCenter())) > this.mob.getRestrictRadius() * this.mob.getRestrictRadius()) {
         return DefaultRandomPos.getPosTowards(this.mob, this.xzSpread, 3, Vec3.atBottomCenterOf(this.mob.getRestrictCenter()), 3.0);
      }

      if (this.mob.getRandom().nextFloat() < 0.3F) {
         Vec3 vector3d = this.findSurfaceTarget(this.mob, this.xzSpread, this.ySpread * 2);
         if (vector3d != null) {
            return vector3d;
         }
      }

      return DefaultRandomPos.getPos(this.mob, this.xzSpread, this.ySpread);
   }

   private boolean canJumpTo(BlockPos pos, int dx, int dz, int scale) {
      BlockPos blockpos = pos.offset(dx * scale, 0, dz * scale);
      return this.mob.level().getFluidState(blockpos).is(FluidTags.LAVA)
         || this.mob.level().getFluidState(blockpos).is(FluidTags.WATER) && !this.mob.level().getBlockState(blockpos).blocksMotion();
   }

   private boolean isAirAbove(BlockPos pos, int dx, int dz, int scale) {
      return this.mob.level().getBlockState(pos.offset(dx * scale, 1, dz * scale)).isAir()
         && this.mob.level().getBlockState(pos.offset(dx * scale, 2, dz * scale)).isAir();
   }

   private Vec3 findSurfaceTarget(PathfinderMob creature, int i, int i1) {
      BlockPos upPos = creature.blockPosition();

      while (creature.level().getFluidState(upPos).is(FluidTags.WATER) || creature.level().getFluidState(upPos).is(FluidTags.LAVA)) {
         upPos = upPos.above();
      }

      return this.isAirAbove(upPos.below(), 0, 0, 0) && this.canJumpTo(upPos.below(), 0, 0, 0)
         ? new Vec3(upPos.getX() + 0.5F, upPos.getY() - 1.0F, upPos.getZ() + 0.5F)
         : null;
   }
}
