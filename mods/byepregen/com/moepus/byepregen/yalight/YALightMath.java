package com.moepus.byepregen.yalight;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

final class YALightMath {
   private static final int DIRECTION_COUNT = 6;
   static final int ALL_DIRECTIONS = 63;
   static final long FLAG_RECHECK = 4611686018427387904L;
   static final long FLAG_WRITE_LEVEL = 2305843009213693952L;
   static final long FLAG_FRESH_OWNER_TRANSFER = 288230376151711744L;
   private static final int LEVEL_SHIFT = 0;
   private static final int DIRECTIONS_SHIFT = 4;

   private YALightMath() {
   }

   static long meta(int level, int directions, long flags) {
      return (level & 15L) << 0 | (directions & 63L) << 4 | flags;
   }

   static int level(long entry) {
      return (int)(entry >>> 0) & 15;
   }

   static int directions(long entry) {
      return (int)(entry >>> 4) & 63;
   }

   static int withoutOpposite(int directionIndex) {
      return 63 & ~oppositeMask(directionIndex);
   }

   static int only(Direction direction) {
      return 1 << direction.ordinal();
   }

   static int oppositeMask(int directionIndex) {
      return 1 << (directionIndex ^ 1);
   }

   static int stepX(int directionIndex) {
      return switch (directionIndex) {
         case 4 -> -1;
         case 5 -> 1;
         default -> 0;
      };
   }

   static int stepY(int directionIndex) {
      return switch (directionIndex) {
         case 0 -> -1;
         case 1 -> 1;
         default -> 0;
      };
   }

   static int stepZ(int directionIndex) {
      return switch (directionIndex) {
         case 2 -> -1;
         case 3 -> 1;
         default -> 0;
      };
   }

   static Direction direction(int directionIndex) {
      return switch (directionIndex) {
         case 0 -> Direction.DOWN;
         case 1 -> Direction.UP;
         case 2 -> Direction.NORTH;
         case 3 -> Direction.SOUTH;
         case 4 -> Direction.WEST;
         case 5 -> Direction.EAST;
         default -> throw new IllegalArgumentException();
      };
   }

   static boolean isEmptyShape(BlockState state) {
      return !state.canOcclude() || !state.useShapeForLightOcclusion();
   }
}
