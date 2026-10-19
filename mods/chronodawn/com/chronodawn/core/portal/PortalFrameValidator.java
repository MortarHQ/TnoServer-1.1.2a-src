package com.chronodawn.core.portal;

import com.chronodawn.registry.ModBlocks;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PortalFrameValidator {
   public static final int MIN_WIDTH = 4;
   public static final int MIN_HEIGHT = 5;
   public static final int MAX_WIDTH = 23;
   public static final int MAX_HEIGHT = 23;

   public static PortalFrameValidator.PortalFrameData validateFrame(Level level, BlockPos pos, Axis axis) {
      if (!isFrameBlock(level, pos)) {
         return null;
      }

      Direction horizontal = axis == Axis.X ? Direction.EAST : Direction.SOUTH;
      Direction vertical = Direction.UP;
      PortalFrameValidator.PortalBounds bounds = findPortalBounds(level, pos, horizontal, vertical);
      int width = bounds.maxHorizontal - bounds.minHorizontal + 1;
      int height = bounds.maxVertical - bounds.minVertical + 1;
      BlockPos bottomLeft;
      if (horizontal.getAxis() == Axis.X) {
         bottomLeft = new BlockPos(bounds.minHorizontal, bounds.minVertical, pos.getZ());
      } else {
         bottomLeft = new BlockPos(pos.getX(), bounds.minVertical, bounds.minHorizontal);
      }

      if (width < 4 || width > 23 || height < 5 || height > 23) {
         return null;
      } else {
         return !validateFrameStructure(level, bottomLeft, horizontal, vertical, width, height)
            ? null
            : new PortalFrameValidator.PortalFrameData(bottomLeft, width, height, axis);
      }
   }

   private static PortalFrameValidator.PortalBounds findPortalBounds(Level level, BlockPos pos, Direction horizontal, Direction vertical) {
      int minHorizontal = getHorizontalCoord(pos, horizontal.getAxis());
      int maxHorizontal = minHorizontal;
      int minVertical = pos.getY();
      int maxVertical = minVertical;

      for (int h = -23; h <= 23; h++) {
         for (int v = -23; v <= 23; v++) {
            BlockPos checkPos = pos.relative(horizontal, h).relative(vertical, v);
            if (isFrameBlock(level, checkPos)) {
               int coord = getHorizontalCoord(checkPos, horizontal.getAxis());
               minHorizontal = Math.min(minHorizontal, coord);
               maxHorizontal = Math.max(maxHorizontal, coord);
               minVertical = Math.min(minVertical, checkPos.getY());
               maxVertical = Math.max(maxVertical, checkPos.getY());
            }
         }
      }

      return new PortalFrameValidator.PortalBounds(minHorizontal, maxHorizontal, minVertical, maxVertical);
   }

   private static int getHorizontalCoord(BlockPos pos, Axis axis) {
      return axis == Axis.X ? pos.getX() : pos.getZ();
   }

   private static int findFrameDimension(Level level, BlockPos start, Direction direction, int maxSize) {
      int dimension = 0;

      for (int i = 1; i <= maxSize; i++) {
         BlockPos checkPos = start.relative(direction, i);
         if (isFrameBlock(level, checkPos)) {
            dimension = i;
         } else {
            if (i + 1 > maxSize) {
               break;
            }

            BlockPos nextPos = start.relative(direction, i + 1);
            if (!isFrameBlock(level, nextPos)) {
               break;
            }
         }
      }

      return dimension + 1;
   }

   private static boolean validateFrameStructure(Level level, BlockPos bottomLeft, Direction horizontal, Direction vertical, int width, int height) {
      Set<BlockPos> framePositions = new HashSet<>();
      Set<BlockPos> cornerPositions = new HashSet<>();
      BlockPos bottomLeftCorner = bottomLeft;
      BlockPos bottomRightCorner = bottomLeft.relative(horizontal, width - 1);
      BlockPos topLeftCorner = bottomLeft.relative(vertical, height - 1);
      BlockPos topRightCorner = bottomLeft.relative(horizontal, width - 1).relative(vertical, height - 1);
      cornerPositions.add(bottomLeftCorner);
      cornerPositions.add(bottomRightCorner);
      cornerPositions.add(topLeftCorner);
      cornerPositions.add(topRightCorner);

      for (int x = 1; x < width - 1; x++) {
         framePositions.add(bottomLeft.relative(horizontal, x));
      }

      for (int x = 1; x < width - 1; x++) {
         framePositions.add(bottomLeft.relative(horizontal, x).relative(vertical, height - 1));
      }

      for (int y = 1; y < height - 1; y++) {
         framePositions.add(bottomLeft.relative(vertical, y));
      }

      for (int y = 1; y < height - 1; y++) {
         framePositions.add(bottomLeft.relative(horizontal, width - 1).relative(vertical, y));
      }

      for (BlockPos framePos : framePositions) {
         if (!isFrameBlock(level, framePos)) {
            return false;
         }
      }

      for (int x = 1; x < width - 1; x++) {
         for (int y = 1; y < height - 1; y++) {
            BlockPos interiorPos = bottomLeft.relative(horizontal, x).relative(vertical, y);
            if (!isValidInteriorBlock(level, interiorPos)) {
               return false;
            }
         }
      }

      return true;
   }

   private static boolean isFrameBlock(Level level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      return state.is((Block)ModBlocks.CLOCKSTONE_BLOCK.get());
   }

   private static boolean isValidInteriorBlock(Level level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      return state.isAir();
   }

   private static class PortalBounds {
      final int minHorizontal;
      final int maxHorizontal;
      final int minVertical;
      final int maxVertical;

      PortalBounds(int minH, int maxH, int minV, int maxV) {
         this.minHorizontal = minH;
         this.maxHorizontal = maxH;
         this.minVertical = minV;
         this.maxVertical = maxV;
      }
   }

   public static class PortalFrameData {
      private final BlockPos bottomLeft;
      private final int width;
      private final int height;
      private final Axis axis;

      public PortalFrameData(BlockPos bottomLeft, int width, int height, Axis axis) {
         this.bottomLeft = bottomLeft;
         this.width = width;
         this.height = height;
         this.axis = axis;
      }

      public BlockPos getBottomLeft() {
         return this.bottomLeft;
      }

      public int getWidth() {
         return this.width;
      }

      public int getHeight() {
         return this.height;
      }

      public Axis getAxis() {
         return this.axis;
      }

      public Set<BlockPos> getInteriorPositions() {
         Set<BlockPos> positions = new HashSet<>();
         Direction horizontal = this.axis == Axis.X ? Direction.EAST : Direction.SOUTH;
         Direction vertical = Direction.UP;

         for (int x = 1; x < this.width - 1; x++) {
            for (int y = 1; y < this.height - 1; y++) {
               positions.add(this.bottomLeft.relative(horizontal, x).relative(vertical, y));
            }
         }

         return positions;
      }
   }
}
