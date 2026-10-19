package dev.latvian.mods.kubejs.plugin.builtin.wrapper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public interface AABBWrapper {
   AABB EMPTY = new AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   AABB CUBE = new AABB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

   static AABB of(double x0, double y0, double z0, double x1, double y1, double z1) {
      return new AABB(x0, y0, z0, x1, y1, z1);
   }

   static AABB ofBlocks(BlockPos pos1, BlockPos pos2) {
      return of(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX() + 1.0, pos2.getY() + 1.0, pos2.getZ() + 1.0);
   }

   static AABB ofBlock(BlockPos pos) {
      return ofBlocks(pos, pos);
   }

   static AABB ofSize(double x, double y, double z) {
      return ofSize(Vec3.ZERO, x, y, z);
   }

   static AABB ofSize(Vec3 vec3, double x, double y, double z) {
      return AABB.ofSize(vec3, x, y, z);
   }

   static AABB wrap(Object o) {
      return switch (o) {
         case AABB aabb -> aabb;
         case BlockPos blockPos -> ofBlock(blockPos);
         case double[] d -> {
            switch (d.length) {
               case 3:
                  yield ofSize(d[0], d[1], d[2]);
               case 6:
                  yield of(d[0], d[1], d[2], d[3], d[4], d[5]);
               default:
                  yield EMPTY;
            }
         }
         case null, default -> EMPTY;
      };
   }
}
