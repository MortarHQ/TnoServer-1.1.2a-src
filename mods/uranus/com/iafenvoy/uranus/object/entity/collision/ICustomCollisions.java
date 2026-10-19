package com.iafenvoy.uranus.object.entity.collision;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface ICustomCollisions {
   static Vec3 getAllowedMovementForEntity(Entity entity, Vec3 vecIN) {
      AABB aabb = entity.getBoundingBox();
      List<VoxelShape> list = entity.level().getEntityCollisions(entity, aabb.expandTowards(vecIN));
      Vec3 vec3 = vecIN.lengthSqr() == 0.0 ? vecIN : collideBoundingBox2(entity, vecIN, aabb, entity.level(), list);
      boolean flag = vecIN.x != vec3.x;
      boolean flag1 = vecIN.y != vec3.y;
      boolean flag2 = vecIN.z != vec3.z;
      boolean flag3 = entity.onGround() || flag1 && vecIN.y < 0.0;
      if (entity.maxUpStep() > 0.0F && flag3 && (flag || flag2)) {
         Vec3 vec31 = collideBoundingBox2(entity, new Vec3(vecIN.x, entity.maxUpStep(), vecIN.z), aabb, entity.level(), list);
         Vec3 vec32 = collideBoundingBox2(entity, new Vec3(0.0, entity.maxUpStep(), 0.0), aabb.expandTowards(vecIN.x, 0.0, vecIN.z), entity.level(), list);
         if (vec32.y < entity.maxUpStep()) {
            Vec3 vec33 = collideBoundingBox2(entity, new Vec3(vecIN.x, 0.0, vecIN.z), aabb.move(vec32), entity.level(), list).add(vec32);
            if (vec33.horizontalDistanceSqr() > vec31.horizontalDistanceSqr()) {
               vec31 = vec33;
            }
         }

         if (vec31.horizontalDistanceSqr() > vec3.horizontalDistanceSqr()) {
            return vec31.add(collideBoundingBox2(entity, new Vec3(0.0, -vec31.y + vecIN.y, 0.0), aabb.move(vec31), entity.level(), list));
         }
      }

      return vec3;
   }

   private static Vec3 collideBoundingBox2(Entity entity, Vec3 vec3d, AABB box, Level world, List<VoxelShape> voxelShapes) {
      Builder<VoxelShape> builder = ImmutableList.builder();
      if (!voxelShapes.isEmpty()) {
         builder.addAll(voxelShapes);
      }

      WorldBorder worldborder = world.getWorldBorder();
      boolean flag = entity != null && worldborder.isInsideCloseToBorder(entity, box.expandTowards(vec3d));
      if (flag) {
         builder.add(worldborder.getCollisionShape());
      }

      builder.addAll(new CustomCollisionsBlockCollisions(world, entity, box.expandTowards(vec3d)));
      return collideWithShapes2(vec3d, box, builder.build());
   }

   private static Vec3 collideWithShapes2(Vec3 vec3d, AABB box, List<VoxelShape> voxelShapes) {
      if (voxelShapes.isEmpty()) {
         return vec3d;
      }

      double d0 = vec3d.x;
      double d1 = vec3d.y;
      double d2 = vec3d.z;
      if (d1 != 0.0) {
         d1 = Shapes.collide(Axis.Y, box, voxelShapes, d1);
         if (d1 != 0.0) {
            box = box.move(0.0, d1, 0.0);
         }
      }

      boolean flag = Math.abs(d0) < Math.abs(d2);
      if (flag && d2 != 0.0) {
         d2 = Shapes.collide(Axis.Z, box, voxelShapes, d2);
         if (d2 != 0.0) {
            box = box.move(0.0, 0.0, d2);
         }
      }

      if (d0 != 0.0) {
         d0 = Shapes.collide(Axis.X, box, voxelShapes, d0);
         if (!flag && d0 != 0.0) {
            box = box.move(d0, 0.0, 0.0);
         }
      }

      if (!flag && d2 != 0.0) {
         d2 = Shapes.collide(Axis.Z, box, voxelShapes, d2);
      }

      return new Vec3(d0, d1, d2);
   }

   boolean canPassThrough(BlockPos var1, BlockState var2, VoxelShape var3);
}
