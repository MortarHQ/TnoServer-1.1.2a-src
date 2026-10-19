package com.github.wal_bos.moreskills.world.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public final class ArsWeissClonePathing {
   private ArsWeissClonePathing() {
   }

   public static BlockPos findSafeStandNear(ServerLevel level, BlockPos center, int horizontalRadius, int up, int down) {
      if (level != null && center != null) {
         BlockPos direct = scanColumn(level, center, up, down);
         if (direct != null) {
            return direct;
         }

         int max = Math.max(1, horizontalRadius);

         for (int r = 1; r <= max; r++) {
            for (int x = -r; x <= r; x++) {
               for (int z = -r; z <= r; z++) {
                  if (Math.abs(x) == r || Math.abs(z) == r) {
                     BlockPos found = scanColumn(level, center.offset(x, 0, z), up, down);
                     if (found != null) {
                        return found;
                     }
                  }
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static BlockPos findSurfaceStandNear(ServerLevel level, BlockPos center, int horizontalRadius) {
      if (level != null && center != null) {
         int max = Math.max(1, horizontalRadius);
         BlockPos direct = surfaceColumn(level, center);
         if (direct != null) {
            return direct;
         }

         for (int r = 1; r <= max; r++) {
            for (int x = -r; x <= r; x++) {
               for (int z = -r; z <= r; z++) {
                  if (Math.abs(x) == r || Math.abs(z) == r) {
                     BlockPos found = surfaceColumn(level, center.offset(x, 0, z));
                     if (found != null) {
                        return found;
                     }
                  }
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static BlockPos surfaceColumn(ServerLevel level, BlockPos column) {
      if (!level.hasChunkAt(column)) {
         return null;
      }

      BlockPos top = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, column);
      if (!level.isLoaded(top)) {
         return null;
      }

      BlockPos feet = top;
      BlockPos floor = feet.below();
      BlockPos head = feet.above();
      if (!level.isLoaded(head)) {
         return null;
      }

      BlockState floorState = level.getBlockState(floor);
      BlockState feetState = level.getBlockState(feet);
      BlockState headState = level.getBlockState(head);
      return isSolidFloor(floorState) && isFree(feetState) && isFree(headState) ? feet.immutable() : scanColumn(level, top, 10, 32);
   }

   private static BlockPos scanColumn(ServerLevel level, BlockPos column, int up, int down) {
      int top = Math.max(2, up);
      int bottom = Math.max(2, down);

      for (int y = top; y >= -bottom; y--) {
         BlockPos floor = column.offset(0, y, 0);
         if (level.isLoaded(floor)) {
            BlockPos feet = floor.above();
            BlockPos head = feet.above();
            if (level.isLoaded(head)) {
               BlockState floorState = level.getBlockState(floor);
               BlockState feetState = level.getBlockState(feet);
               BlockState headState = level.getBlockState(head);
               if (isSolidFloor(floorState) && isFree(feetState) && isFree(headState)) {
                  return feet.immutable();
               }
            }
         }
      }

      return null;
   }

   private static boolean isSolidFloor(BlockState state) {
      return !state.isAir() && !state.liquid() && state.getFluidState().isEmpty();
   }

   private static boolean isFree(BlockState state) {
      return state.isAir() || !state.getFluidState().isEmpty();
   }
}
