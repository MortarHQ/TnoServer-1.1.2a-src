package com.iafenvoy.iceandfire.entity.pathfinding;

import com.iafenvoy.iceandfire.entity.DeathWormEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class DeathWormLandNavigation extends PathNavigation {
   private final DeathWormEntity worm;
   private boolean shouldAvoidSun;

   public DeathWormLandNavigation(DeathWormEntity worm, Level world) {
      super(worm, world);
      this.worm = worm;
   }

   protected PathFinder createPathFinder(int i) {
      this.nodeEvaluator = new WalkNodeEvaluator();
      Vec3i vec3i = new BlockPos(64, 64, 64);
      this.nodeEvaluator
         .prepare(new PathNavigationRegion(this.level, this.mob.blockPosition().subtract(vec3i), this.mob.blockPosition().offset(vec3i)), this.mob);
      this.nodeEvaluator.setCanPassDoors(true);
      this.nodeEvaluator.setCanFloat(true);
      return new PathFinder(this.nodeEvaluator, i);
   }

   protected boolean canUpdatePath() {
      return this.mob.onGround() || this.worm.isInSand() || this.mob.isPassenger();
   }

   protected Vec3 getTempMobPos() {
      return new Vec3(this.mob.getX(), this.getPathablePosY(), this.mob.getZ());
   }

   public Path createPath(BlockPos pos, int i) {
      if (this.level.getBlockState(pos).isAir()) {
         BlockPos blockpos = pos.below();

         while (blockpos.getY() > 0 && this.level.getBlockState(blockpos).isAir()) {
            blockpos = blockpos.below();
         }

         if (blockpos.getY() > 0) {
            return super.createPath(blockpos.above(), i);
         }

         while (blockpos.getY() < this.level.getMaxBuildHeight() && this.level.getBlockState(blockpos).isAir()) {
            blockpos = blockpos.above();
         }

         pos = blockpos;
      }

      if (!this.level.getBlockState(pos).isSolid()) {
         return super.createPath(pos, i);
      }

      BlockPos blockpos1 = pos.above();

      while (blockpos1.getY() < this.level.getMaxBuildHeight() && this.level.getBlockState(blockpos1).isSolid()) {
         blockpos1 = blockpos1.above();
      }

      return super.createPath(blockpos1, i);
   }

   public Path createPath(Entity entityIn, int i) {
      return this.createPath(entityIn.blockPosition(), i);
   }

   private int getPathablePosY() {
      if (this.worm.isInSand()) {
         int i = (int)this.mob.getBoundingBox().minY;
         BlockState blockstate = this.level.getBlockState(new BlockPos(this.mob.getBlockX(), i, this.mob.getBlockZ()));
         int j = 0;

         while (blockstate.is(BlockTags.SAND)) {
            blockstate = this.level.getBlockState(new BlockPos(this.mob.getBlockX(), ++i, this.mob.getBlockZ()));
            if (++j > 16) {
               return (int)this.mob.getBoundingBox().minY;
            }
         }

         return i;
      } else {
         return (int)(this.mob.getBoundingBox().minY + 0.5);
      }
   }

   protected boolean canMoveDirectly(Vec3 posVec31, Vec3 posVec32) {
      int i = Mth.floor(posVec31.x);
      int j = Mth.floor(posVec31.z);
      double d0 = posVec32.x - posVec31.x;
      double d1 = posVec32.z - posVec31.z;
      double d2 = d0 * d0 + d1 * d1;
      int sizeX = (int)this.worm.getBoundingBox().getXsize();
      int sizeY = (int)this.worm.getBoundingBox().getYsize();
      int sizeZ = (int)this.worm.getBoundingBox().getZsize();
      if (d2 < 1.0E-8) {
         return false;
      }

      double d3 = 1.0 / Math.sqrt(d2);
      d0 *= d3;
      d1 *= d3;
      sizeX += 2;
      sizeZ += 2;
      if (!this.isSafeToStandAt(i, (int)posVec31.y, j, sizeX, sizeY, sizeZ, posVec31, d0, d1)) {
         return false;
      }

      sizeX -= 2;
      sizeZ -= 2;
      double d4 = 1.0 / Math.abs(d0);
      double d5 = 1.0 / Math.abs(d1);
      double d6 = i - posVec31.x;
      double d7 = j - posVec31.z;
      if (d0 >= 0.0) {
         d6++;
      }

      if (d1 >= 0.0) {
         d7++;
      }

      d6 /= d0;
      d7 /= d1;
      int k = d0 < 0.0 ? -1 : 1;
      int l = d1 < 0.0 ? -1 : 1;
      int i1 = Mth.floor(posVec32.x);
      int j1 = Mth.floor(posVec32.z);
      int k1 = i1 - i;
      int l1 = j1 - j;

      while (k1 * k > 0 || l1 * l > 0) {
         if (d6 < d7) {
            d6 += d4;
            i += k;
            k1 = i1 - i;
         } else {
            d7 += d5;
            j += l;
            l1 = j1 - j;
         }

         if (!this.isSafeToStandAt(i, (int)posVec31.y, j, sizeX, sizeY, sizeZ, posVec31, d0, d1)) {
            return false;
         }
      }

      return true;
   }

   private boolean isSafeToStandAt(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3 vec31, double p_179683_8_, double p_179683_10_) {
      int i = x - sizeX / 2;
      int j = z - sizeZ / 2;
      this.nodeEvaluator.mob = this.worm;
      if (!this.isPositionClear(i, y, j, sizeX, sizeY, sizeZ, vec31, p_179683_8_, p_179683_10_)) {
         return false;
      }

      for (int k = i; k < i + sizeX; k++) {
         for (int l = j; l < j + sizeZ; l++) {
            double d0 = k + 0.5 - vec31.x;
            double d1 = l + 0.5 - vec31.z;
            if (d0 * p_179683_8_ + d1 * p_179683_10_ >= 0.0) {
               PathType pathnodetype = this.nodeEvaluator.getPathTypeOfMob(new PathfindingContext(this.level, this.mob), k, y - 1, l, this.mob);
               if (pathnodetype == PathType.LAVA) {
                  return false;
               }

               pathnodetype = this.nodeEvaluator.getPathTypeOfMob(new PathfindingContext(this.level, this.mob), k, y, l, this.mob);
               float f = this.mob.getPathfindingMalus(pathnodetype);
               if (f < 0.0F || f >= 8.0F) {
                  return false;
               }

               if (pathnodetype == PathType.DAMAGE_FIRE || pathnodetype == PathType.DANGER_FIRE || pathnodetype == PathType.DAMAGE_OTHER) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private boolean isPositionClear(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3 vec3d, double p_179692_8_, double p_179692_10_) {
      for (BlockPos blockpos : BlockPos.betweenClosedStream(new BlockPos(x, y, z), new BlockPos(x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1)).toList()) {
         double d0 = blockpos.getX() + 0.5 - vec3d.x;
         double d1 = blockpos.getZ() + 0.5 - vec3d.z;
         if (d0 * p_179692_8_ + d1 * p_179692_10_ >= 0.0 && this.level.getBlockState(blockpos).blocksMotion()
            || this.level.getBlockState(blockpos).is(BlockTags.SAND)) {
            return false;
         }
      }

      return true;
   }

   public boolean canFloat() {
      return this.nodeEvaluator.canFloat();
   }

   public void setCanFloat(boolean canSwim) {
      this.nodeEvaluator.setCanFloat(canSwim);
   }
}
