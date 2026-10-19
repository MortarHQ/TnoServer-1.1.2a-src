package cn.leolezury.eternalstarlight.common.entity.living.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class RandomFlyGoal extends Goal {
   private final PathfinderMob mob;

   public RandomFlyGoal(PathfinderMob mob) {
      this.mob = mob;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      if (this.mob.hasControllingPassenger()) {
         return false;
      } else {
         return this.mob.getNavigation().isDone() ? true : this.mob.getRandom().nextInt(10) == 0;
      }
   }

   public boolean canContinueToUse() {
      return !this.mob.getNavigation().isDone() && !this.mob.hasControllingPassenger() && this.mob.getRandom().nextInt(40) != 0;
   }

   public void start() {
      Vec3 target = this.getRandomPos();
      this.mob.getNavigation().moveTo(target.x, target.y, target.z, 1.0);
   }

   private Vec3 getRandomPos() {
      Vec3 target = this.mob
         .position()
         .add((this.mob.getRandom().nextFloat() - 0.5F) * 2.0F * 15.0F, 0.0, (this.mob.getRandom().nextFloat() - 0.5F) * 2.0F * 15.0F);
      BlockPos position = this.mob.blockPosition();
      int blockY = position.getY();
      if (!this.mob.level().getBlockState(position).isAir()) {
         int i = position.getY();

         while (!this.mob.level().getBlockState(new BlockPos(position.getX(), i, position.getZ())).isAir() && i <= this.mob.level().getMaxBuildHeight()) {
            blockY = i++;
         }
      } else {
         for (int i = position.getY();
            this.mob.level().getBlockState(new BlockPos(position.getX(), i, position.getZ())).isAir() && i >= this.mob.level().getMinBuildHeight();
            i--
         ) {
            blockY = i + 1;
         }
      }

      target = new Vec3(target.x, blockY + 5 + this.mob.getRandom().nextInt(3), target.z);
      BlockHitResult result = this.mob.level().clip(new ClipContext(this.mob.position(), target, Block.COLLIDER, Fluid.ANY, this.mob));
      if (result.getType() != Type.MISS) {
         target = result.getLocation();
      }

      return target;
   }
}
