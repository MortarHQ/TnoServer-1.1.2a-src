package com.moepus.byepregen.yalight;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

final class YASkyLightPropagation {
   private static final int CHUNK_LOCAL_MASK = 15;

   private YASkyLightPropagation() {
   }

   static void propagateIncrease(YASkyLightEngine engine, long pos, long meta) {
      int level = YALightMath.level(meta);
      int x = BlockPos.getX(pos);
      int y = BlockPos.getY(pos);
      int z = BlockPos.getZ(pos);
      int stored = engine.getCachedUpdatingLight(x, y, z);
      if (stored != level) {
         boolean canWrite = (meta & 6917529027641081856L) == 2305843009213693952L && stored < level;
         if (!canWrite) {
            return;
         }

         engine.setCachedUpdatingLight(x, y, z, level);
      }

      int fromBlock = Integer.MIN_VALUE;
      int directions = YALightMath.directions(meta);

      while (directions != 0) {
         int directionIndex = Integer.numberOfTrailingZeros(directions);
         directions &= directions - 1;
         fromBlock = tryPropagateIncreaseEdge(engine, x, y, z, level, directionIndex, fromBlock, meta);
      }
   }

   static int tryPropagateIncreaseEdge(YASkyLightEngine engine, int x, int y, int z, int level, int directionIndex, int fromBlock, long flags) {
      int toX = x;
      int toY = y;
      int toZ = z;
      switch (directionIndex) {
         case 0:
            toY--;
            break;
         case 1:
            toY++;
            break;
         case 2:
            toZ--;
            break;
         case 3:
            toZ++;
            break;
         case 4:
            toX--;
            break;
         case 5:
            toX++;
      }

      boolean freshOwnerExit = (flags & 4899916394579099648L) == 288230376151711744L && crossesChunkBoundary(x, z, directionIndex);
      if (freshOwnerExit && tryTransferFreshOwnerEdge(engine, x, y, z, toX, toY, toZ, level, directionIndex)) {
         return fromBlock;
      }

      long childFlags = freshOwnerExit ? 0L : flags & 288230376151711744L;
      return propagateIncreaseEdge(engine, x, y, z, toX, toY, toZ, level, directionIndex, fromBlock, childFlags);
   }

   private static boolean crossesChunkBoundary(int x, int z, int directionIndex) {
      return switch (directionIndex) {
         case 2 -> (z & 15) == 0;
         case 3 -> (z & 15) == 15;
         case 4 -> (x & 15) == 0;
         case 5 -> (x & 15) == 15;
         default -> false;
      };
   }

   static void propagateTransferredEdge(YASkyLightEngine engine, long fromPos, int level, int directionIndex, long flags) {
      int x = BlockPos.getX(fromPos);
      int y = BlockPos.getY(fromPos);
      int z = BlockPos.getZ(fromPos);
      propagateIncreaseEdge(
         engine,
         x,
         y,
         z,
         x + YALightMath.stepX(directionIndex),
         y + YALightMath.stepY(directionIndex),
         z + YALightMath.stepZ(directionIndex),
         level,
         directionIndex,
         Integer.MIN_VALUE,
         flags
      );
   }

   private static int propagateIncreaseEdge(
      YASkyLightEngine engine, int x, int y, int z, int toX, int toY, int toZ, int level, int directionIndex, int fromBlock, long childFlags
   ) {
      int current = engine.getEnabledCachedUpdatingLight(toX, toY, toZ);
      if (current >= 0 && current < level - 1) {
         int toBlock = engine.blocks.blockAt(toX, toY, toZ);
         if (engine.blocks.isFull(toBlock)) {
            return fromBlock;
         }

         int target = engine.blocks.attenuatedLevel(level, toX, toY, toZ, toBlock);
         if (target <= current) {
            return fromBlock;
         }

         if (fromBlock == Integer.MIN_VALUE) {
            fromBlock = engine.blocks.blockAt(x, y, z);
         }

         if ((fromBlock | toBlock) != 0 && engine.blocks.shapeOccludes(x, y, z, fromBlock, toX, toY, toZ, toBlock, YALightMath.direction(directionIndex))) {
            return fromBlock;
         }

         engine.setCachedUpdatingLight(toX, toY, toZ, target);
         if (target > 1) {
            engine.enqueueIncrease(BlockPos.asLong(toX, toY, toZ), target, YALightMath.withoutOpposite(directionIndex), childFlags);
         }

         return fromBlock;
      } else {
         return fromBlock;
      }
   }

   private static boolean tryTransferFreshOwnerEdge(YASkyLightEngine engine, int x, int y, int z, int toX, int toY, int toZ, int level, int directionIndex) {
      long targetOwner = ChunkPos.asLong(toX >> 4, toZ >> 4);
      byte targetOwnerState = engine.ownerTransfers.state(targetOwner);
      if (!engine.ownerTransfers.isTransferTarget(targetOwnerState)) {
         return false;
      }

      if (engine.ownerTransfers.isInitialized(targetOwnerState)) {
         int current = engine.getEnabledCachedUpdatingLight(toX, toY, toZ);
         if (current < 0 || current >= level - 1) {
            return true;
         }
      }

      engine.ownerTransfers.enqueue(targetOwner, BlockPos.asLong(x, y, z), level, directionIndex);
      return true;
   }

   static void propagateDecrease(YASkyLightEngine engine, long pos, long meta) {
      int level = YALightMath.level(meta);
      int x = BlockPos.getX(pos);
      int y = BlockPos.getY(pos);
      int z = BlockPos.getZ(pos);
      int directions = YALightMath.directions(meta);

      while (directions != 0) {
         int directionIndex = Integer.numberOfTrailingZeros(directions);
         directions &= directions - 1;
         propagateDecreaseEdge(engine, x, y, z, level, directionIndex);
      }
   }

   private static void propagateDecreaseEdge(YASkyLightEngine engine, int x, int y, int z, int level, int directionIndex) {
      int toX = x + YALightMath.stepX(directionIndex);
      int toY = y + YALightMath.stepY(directionIndex);
      int toZ = z + YALightMath.stepZ(directionIndex);
      int current = engine.getEnabledCachedUpdatingLight(toX, toY, toZ);
      if (current > 0) {
         long toPos = BlockPos.asLong(toX, toY, toZ);
         int toBlock = engine.blocks.blockAt(toX, toY, toZ);
         if (engine.blocks.isFull(toBlock)) {
            engine.setCachedUpdatingLight(toX, toY, toZ, 0);
         } else {
            int target = engine.blocks.attenuatedLevel(level, toX, toY, toZ, toBlock);
            if (current > target) {
               engine.enqueueIncrease(toPos, current, YALightMath.oppositeMask(directionIndex), 4611686018427387904L);
            } else {
               engine.setCachedUpdatingLight(toX, toY, toZ, 0);
               int source = engine.getSourceLight(toPos);
               if (source > 0) {
                  engine.enqueueIncrease(toPos, source, engine.sources.skySourceDirections(toX, toY, toZ), 2305843009213693952L);
               }

               engine.enqueueDecrease(toPos, current, YALightMath.withoutOpposite(directionIndex));
            }
         }
      }
   }
}
