package com.moepus.byepregen.yalight;

import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

final class YALightBlockAccess {
   private static final int RAW_ID_BITS = 30;
   private static final int RAW_ID_MASK = 1073741823;
   private static final int SLOW_BIT = 1073741824;
   private static final int STATEFUL_BIT = Integer.MIN_VALUE;
   private static final int EMPTY_BLOCK = 0;
   private static final int FULL_BLOCK = 1;
   private static final int SHAPED_BLOCK = Integer.MIN_VALUE;
   private static final int SLOW_BLOCK = -1073741824;
   private final YAChunkRunCache cache;
   private final LightChunkGetter chunkGetter;
   private final BlockGetter level;
   private final MutableBlockPos mutablePos;

   YALightBlockAccess(YAChunkRunCache cache, LightChunkGetter chunkGetter, BlockGetter level, MutableBlockPos mutablePos) {
      this.cache = cache;
      this.chunkGetter = chunkGetter;
      this.level = level;
      this.mutablePos = mutablePos;
   }

   int blockAt(int x, int y, int z) {
      int rawId = this.cache.getRawId(this.chunkGetter, x, y, z);
      if (rawId < 0) {
         return 0;
      } else {
         int lightClass = YABlockStateLightClass.fromRawId(rawId);
         if (lightClass == 0) {
            return 0;
         } else if (lightClass == 1) {
            return 1;
         } else {
            return lightClass == 2 ? -2147483648 | rawId & 1073741823 : -1073741824 | rawId & 1073741823;
         }
      }
   }

   int rawId(int block) {
      return block & 1073741823;
   }

   boolean isFull(int block) {
      return block == 1;
   }

   boolean isSlow(int block) {
      return block < 0 && (block & 1073741824) != 0;
   }

   BlockState toState(int block) {
      if (block == 0) {
         return Blocks.AIR.defaultBlockState();
      } else {
         return block == 1 ? Blocks.STONE.defaultBlockState() : Block.stateById(this.rawId(block));
      }
   }

   int attenuatedLevel(int level, int x, int y, int z, int block) {
      if (!this.isSlow(block)) {
         return level - 1;
      }

      int opacity = Math.max(1, this.toState(block).getLightBlock(this.level, this.mutablePos.set(x, y, z)));
      return Math.max(0, level - opacity);
   }

   boolean shapeOccludes(int fromX, int fromY, int fromZ, int fromBlock, int toX, int toY, int toZ, int toBlock, Direction direction) {
      if (fromBlock == 0 && toBlock == 0) {
         return false;
      } else if (fromBlock != 1 && toBlock != 1) {
         VoxelShape fromShape = this.faceShapeAt(fromX, fromY, fromZ, fromBlock, direction);
         VoxelShape toShape = this.faceShapeAt(toX, toY, toZ, toBlock, direction.getOpposite());
         return Shapes.faceShapeOccludes(fromShape, toShape);
      } else {
         return true;
      }
   }

   private VoxelShape faceShapeAt(int x, int y, int z, int block, Direction direction) {
      if (block == 0) {
         return Shapes.empty();
      }

      if (block == 1) {
         return Shapes.block();
      }

      BlockState state = this.toState(block);
      return this.isSlow(block) && YALightMath.isEmptyShape(state)
         ? Shapes.empty()
         : state.getFaceOcclusionShape(this.level, this.mutablePos.set(x, y, z), direction);
   }
}
