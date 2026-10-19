package com.chronodawn.client;

import net.minecraft.core.BlockPos;

public final class LeafColorProvider {
   private static final int CELL_SIZE_LOG2 = 3;

   private LeafColorProvider() {
   }

   public static int colorAt(LeafColorProvider.LeafKind kind, BlockPos pos) {
      if (pos == null) {
         return kind.iconColor();
      }

      int cellX = pos.getX() >> 3;
      int cellZ = pos.getZ() >> 3;
      int idx = Math.floorMod(hash(cellX, cellZ, kind.ordinal()), kind.palette.length);
      return kind.palette[idx];
   }

   private static int hash(int x, int z, int kindSalt) {
      int h = x * 374761393 + z * 668265263 + kindSalt * 1274126177;
      h = (h ^ h >>> 13) * 1274126177;
      return h ^ h >>> 16;
   }

   public enum LeafKind {
      TIME_WOOD(new int[]{7907034, 7907034, 7907034, 7907034, 10207712, 10207712, 10207712, 5670837, 5670837, 5670837, 7320534, 7320534, 12888155}),
      DARK_TIME_WOOD(
         new int[]{16777215, 16777215, 16777215, 16777215, 14739711, 14739711, 14739711, 16771288, 16771288, 16771288, 15269864, 15269864, 14727320}
      ),
      ANCIENT_TIME_WOOD(
         new int[]{16777215, 16777215, 16777215, 16777215, 16771272, 16771272, 16771272, 15263999, 15263999, 15263999, 16767152, 16767152, 11061472}
      );

      private final int[] palette;

      LeafKind(int[] palette) {
         this.palette = palette;
      }

      public int iconColor() {
         return this.palette[0];
      }
   }
}
