package com.github.L_Ender.cataclysm.entity.Pet.Summoned_Entity;

import com.github.L_Ender.cataclysm.entity.etc.IFollower;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class FlyingFollowSummonerGoal extends Goal {
   private final Abstract_Summoned_Entity tameAnimal;
   private LivingEntity owner;
   private final LevelReader world;
   private final double followSpeed;
   private final PathNavigation navigator;
   private int timeToRecalcPath;
   private final float maxDist;
   private final float minDist;
   private float oldWaterCost;
   private final boolean teleportToLeaves;
   private final IFollower follower;

   public FlyingFollowSummonerGoal(Abstract_Summoned_Entity tameAnimal, double speed, float minDist, float maxDist, boolean teleportToLeaves) {
      this.tameAnimal = tameAnimal;
      this.world = tameAnimal.level();
      this.followSpeed = speed;
      this.navigator = tameAnimal.getNavigation();
      this.minDist = minDist;
      this.maxDist = maxDist;
      this.teleportToLeaves = teleportToLeaves;
      this.follower = tameAnimal;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      LivingEntity livingentity = this.tameAnimal.getOwner();
      if (livingentity == null) {
         return false;
      } else if (livingentity.isSpectator()) {
         return false;
      } else if (!(this.tameAnimal.distanceToSqr(livingentity) < this.minDist * this.minDist) && !this.isInCombat()) {
         this.owner = livingentity;
         return this.follower.shouldFollow();
      } else {
         return false;
      }
   }

   public boolean canContinueToUse() {
      return this.isInCombat() ? false : this.tameAnimal.distanceToSqr(this.owner) > this.maxDist * this.maxDist;
   }

   private boolean isInCombat() {
      Entity owner = this.tameAnimal.getOwner();
      return owner == null ? false : this.tameAnimal.distanceTo(owner) < 30.0F && this.tameAnimal.getTarget() != null && this.tameAnimal.getTarget().isAlive();
   }

   public void start() {
      this.timeToRecalcPath = 0;
      this.oldWaterCost = this.tameAnimal.getPathfindingMalus(PathType.WATER);
      this.tameAnimal.setPathfindingMalus(PathType.WATER, 0.0F);
   }

   public void stop() {
      this.owner = null;
      this.navigator.stop();
      this.tameAnimal.setPathfindingMalus(PathType.WATER, this.oldWaterCost);
   }

   public void tick() {
      this.tameAnimal.getLookControl().setLookAt(this.owner, 10.0F, this.tameAnimal.getMaxHeadXRot());
      if (--this.timeToRecalcPath <= 0) {
         this.timeToRecalcPath = 10;
         if (!this.tameAnimal.isLeashed() && !this.tameAnimal.isPassenger()) {
            if (this.tameAnimal.distanceToSqr(this.owner) >= 144.0) {
               this.tryToTeleportNearEntity();
            }

            this.follower.followEntity(this.tameAnimal, this.owner, this.followSpeed);
         }
      }
   }

   private void tryToTeleportNearEntity() {
      BlockPos blockpos = this.owner.blockPosition();

      for (int i = 0; i < 10; i++) {
         int j = this.getRandomNumber(-3, 3);
         int k = this.getRandomNumber(-1, 1);
         int l = this.getRandomNumber(-3, 3);
         boolean flag = this.tryToTeleportToLocation(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
         if (flag) {
            return;
         }
      }
   }

   private boolean tryToTeleportToLocation(int x, int y, int z) {
      if (Math.abs(x - this.owner.getX()) < 2.0 && Math.abs(z - this.owner.getZ()) < 2.0) {
         return false;
      }

      if (!this.isTeleportFriendlyBlock(new BlockPos(x, y, z))) {
         return false;
      }

      this.tameAnimal.moveTo(x + 0.5, y, z + 0.5, this.tameAnimal.getYRot(), this.tameAnimal.getXRot());
      this.navigator.stop();
      return true;
   }

   private boolean isTeleportFriendlyBlock(BlockPos pos) {
      if (this.world.getBlockState(pos).isAir()) {
         BlockPos blockpos = pos.subtract(this.tameAnimal.blockPosition());
         return this.world.noCollision(this.tameAnimal, this.tameAnimal.getBoundingBox().move(blockpos));
      }

      PathType pathnodetype = WalkNodeEvaluator.getPathTypeStatic(this.tameAnimal, pos);
      if (pathnodetype != PathType.WALKABLE) {
         return false;
      }

      BlockState blockstate = this.world.getBlockState(pos.below());
      if (!this.teleportToLeaves && blockstate.getBlock() instanceof LeavesBlock) {
         return false;
      }

      BlockPos blockpos = pos.subtract(this.tameAnimal.blockPosition());
      return this.world.noCollision(this.tameAnimal, this.tameAnimal.getBoundingBox().move(blockpos));
   }

   private int getRandomNumber(int min, int max) {
      return this.tameAnimal.getRandom().nextInt(max - min + 1) + min;
   }
}
