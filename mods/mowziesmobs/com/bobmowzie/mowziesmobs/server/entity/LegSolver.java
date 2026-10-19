package com.bobmowzie.mowziesmobs.server.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LegSolver {
   public final LegSolver.Leg[] legs;

   public LegSolver(LegSolver.Leg... legs) {
      this.legs = legs;
   }

   public final void update(LivingEntity entity) {
      double sideTheta = entity.yBodyRot / (180.0 / Math.PI);
      double sideX = Math.cos(sideTheta);
      double sideZ = Math.sin(sideTheta);
      double forwardTheta = sideTheta + (Math.PI / 2);
      double forwardX = Math.cos(forwardTheta);
      double forwardZ = Math.sin(forwardTheta);

      for (LegSolver.Leg leg : this.legs) {
         leg.update(entity, sideX, sideZ, forwardX, forwardZ);
      }
   }

   public static final class Leg {
      public final float forward;
      public final float side;
      private float height;
      private float prevHeight;

      public Leg(float forward, float side) {
         this.forward = forward;
         this.side = side;
      }

      public float getHeight(float delta) {
         return this.prevHeight + (this.height - this.prevHeight) * delta;
      }

      public void update(LivingEntity entity, double sideX, double sideZ, double forwardX, double forwardZ) {
         this.prevHeight = this.height;
         this.height = this.settle(
            entity,
            entity.getX() + sideX * this.side + forwardX * this.forward,
            entity.getY(),
            entity.getZ() + sideZ * this.side + forwardZ * this.forward,
            this.height
         );
      }

      private float settle(LivingEntity entity, double x, double y, double z, float height) {
         BlockPos pos = BlockPos.containing(x, y + 0.001, z);
         float dist = this.getDistance(entity.level(), pos);
         if (1.0F - dist < 0.001) {
            dist = this.getDistance(entity.level(), pos.below()) + (float)y % 1.0F;
         } else {
            dist = (float)(dist - (1.0 - y % 1.0));
         }

         if (entity.onGround() && height <= dist) {
            return height == dist ? height : Math.min(height + 0.3F, dist);
         } else {
            return height > 0.0F ? Math.max(height - 0.4F, dist) : height;
         }
      }

      private float getDistance(Level world, BlockPos pos) {
         BlockState state = world.getBlockState(pos);
         VoxelShape shape = state.getBlockSupportShape(world, pos);
         float f = 0.0F;
         if (!shape.isEmpty()) {
            AABB aabb = shape.bounds();
            f = (float)aabb.maxY;
         }

         return 1.0F - Math.min(f, 1.0F);
      }
   }
}
