package com.blackgear.vanillabackport.core.util;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CollisionUtils {
   public static boolean intersects(AABB box, BlockPos pos) {
      return box.intersects(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
   }

   public static boolean collidedWithFluid(LivingEntity entity, FluidState state, BlockPos pos, Vec3 origin, Vec3 target) {
      AABB box = getFluidAABB(state, entity.level(), pos);
      return box != null && collidedWithShapeMovingFrom(entity, origin, target, List.of(box));
   }

   public static boolean collidedWithShapeMovingFrom(LivingEntity entity, Vec3 origin, Vec3 target, List<AABB> boxes) {
      AABB box = entity.dimensions.makeBoundingBox(origin);
      Vec3 distance = target.subtract(origin);
      return collidedAlongVector(box, distance, boxes);
   }

   @Nullable
   public static AABB getFluidAABB(FluidState state, BlockGetter level, BlockPos pos) {
      if (state.isEmpty()) {
         return null;
      }

      float fluidHeight = state.getHeight(level, pos);
      return new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + fluidHeight, pos.getZ() + 1.0);
   }

   public static boolean collidedAlongVector(AABB entityBox, Vec3 origin, List<AABB> obstacles) {
      Vec3 center = entityBox.getCenter();
      Vec3 distance = center.add(origin);

      for (AABB obstacle : obstacles) {
         AABB box = obstacle.inflate(entityBox.getXsize() * 0.5, entityBox.getYsize() * 0.5, entityBox.getZsize() * 0.5);
         if (box.contains(distance) || box.contains(center)) {
            return true;
         }

         if (box.clip(center, distance).isPresent()) {
            return true;
         }
      }

      return false;
   }

   public static CollisionContext positionContext(double y) {
      return new CollisionUtils.PositionCollisionContext(y);
   }

   private static class PositionCollisionContext extends EntityCollisionContext {
      private final double y;

      private PositionCollisionContext(double y) {
         super(false, -Double.MAX_VALUE, ItemStack.EMPTY, fluidState -> false, null);
         this.y = y;
      }

      public boolean isDescending() {
         return false;
      }

      public boolean isAbove(VoxelShape shape, BlockPos pos, boolean canAscend) {
         return this.y > pos.getY() + shape.max(Axis.Y) - 1.0E-5F;
      }

      public boolean isHoldingItem(Item item) {
         return false;
      }

      public boolean canStandOnFluid(FluidState fluid1, FluidState fluid2) {
         return false;
      }
   }
}
