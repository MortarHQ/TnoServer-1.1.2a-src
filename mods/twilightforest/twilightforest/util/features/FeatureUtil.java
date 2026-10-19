package twilightforest.util.features;

import java.util.function.Predicate;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;

@Deprecated
public final class FeatureUtil {
   private static final Direction[] directionsExceptDown = new Direction[]{Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

   public static boolean isAreaSuitable(WorldGenLevel world, BlockPos pos, int xWidth, int height, int zWidth) {
      return isAreaSuitable(world, pos, xWidth, height, zWidth, false);
   }

   public static boolean isAreaSuitable(WorldGenLevel world, BlockPos pos, int xWidth, int height, int zWidth, boolean underwaterAllowed) {
      for (int cx = 0; cx < xWidth; cx++) {
         for (int cz = 0; cz < zWidth; cz++) {
            BlockPos pos_ = pos.offset(cx, 0, cz);
            if (!world.hasChunkAt(pos_)) {
               return false;
            }

            BlockState state = world.getBlockState(pos_.below());
            if (state.isSolidRender(world, pos_) && !FeatureLogic.isBlockNotOk(state)) {
               for (int cy = 0; cy < height; cy++) {
                  if (!world.isEmptyBlock(pos_.above(cy))
                     && !world.getBlockState(pos_.above(cy)).canBeReplaced()
                     && (!underwaterAllowed || !world.getBlockState(pos_.above(cy)).liquid())) {
                     return false;
                  }
               }
            } else if (!underwaterAllowed || !state.liquid()) {
               return false;
            }
         }
      }

      return true;
   }

   public static boolean anyBelowMatch(BlockPos exposedPos, int depth, Predicate<BlockPos> predicate) {
      return isAnyMatchInArea(exposedPos.below(depth), 1, depth + 1, 1, predicate);
   }

   public static boolean isAnyMatchInArea(BlockPos pos, int xWidth, int height, int zWidth, Predicate<BlockPos> predicate) {
      return IntStream.range(0, xWidth)
         .mapToObj(cx -> IntStream.range(0, height).mapToObj(cy -> IntStream.range(0, zWidth).mapToObj(cz -> pos.offset(cx, cy, cz))))
         .flatMap(stream -> stream.flatMap(s -> s))
         .anyMatch(predicate);
   }

   public static void drawBlob(LevelAccessor world, BlockPos pos, int rad, BlockState state) {
      for (byte dx = 0; dx <= rad; dx++) {
         for (byte dy = 0; dy <= rad; dy++) {
            for (byte dz = 0; dz <= rad; dz++) {
               int dist;
               if (dx >= dy && dx >= dz) {
                  dist = dx + (Math.max(dy, dz) >> 1) + (Math.min(dy, dz) >> 2);
               } else if (dy >= dx && dy >= dz) {
                  dist = dy + (Math.max(dx, dz) >> 1) + (Math.min(dx, dz) >> 2);
               } else {
                  dist = dz + (Math.max(dx, dy) >> 1) + (Math.min(dx, dy) >> 2);
               }

               if (dist <= rad) {
                  world.setBlock(pos.offset(dx, dy, dz), state, 3);
                  world.setBlock(pos.offset(dx, dy, -dz), state, 3);
                  world.setBlock(pos.offset(-dx, dy, dz), state, 3);
                  world.setBlock(pos.offset(-dx, dy, -dz), state, 3);
                  world.setBlock(pos.offset(dx, -dy, dz), state, 3);
                  world.setBlock(pos.offset(dx, -dy, -dz), state, 3);
                  world.setBlock(pos.offset(-dx, -dy, dz), state, 3);
                  world.setBlock(pos.offset(-dx, -dy, -dz), state, 3);
               }
            }
         }
      }
   }

   public static boolean hasAirAround(LevelAccessor world, BlockPos pos) {
      for (Direction e : directionsExceptDown) {
         if (world.isEmptyBlock(pos.relative(e))) {
            return true;
         }
      }

      return false;
   }

   public static boolean isNearSolid(LevelReader world, BlockPos pos) {
      for (Direction e : Direction.values()) {
         if (world.hasChunkAt(pos.relative(e)) && world.getBlockState(pos.relative(e)).isSolid()) {
            return true;
         }
      }

      return false;
   }
}
