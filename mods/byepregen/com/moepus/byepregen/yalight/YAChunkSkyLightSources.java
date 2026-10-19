package com.moepus.byepregen.yalight;

import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.lighting.ChunkSkyLightSources;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class YAChunkSkyLightSources extends ChunkSkyLightSources {
   public static final int NEGATIVE_INFINITY_CODE = 0;
   public static final int NO_SOURCE_CODE = 65535;
   private static final int SIZE = 16;
   private static final int AREA = 256;
   private static final BlockState AIR = Blocks.AIR.defaultBlockState();
   private final int minY;
   private final short[] lowest = new short[256];

   public YAChunkSkyLightSources(LevelHeightAccessor level) {
      super(level);
      this.minY = level.getMinBuildHeight() - 1;
      int maxCode = level.getMaxBuildHeight() - this.minY;
      if (maxCode >= 65535) {
         throw new IllegalArgumentException("YA sky source height exceeds unsigned short range: " + maxCode);
      }
   }

   public int sourceCode(int x, int z) {
      return this.sourceCodeAt(index(x, z));
   }

   public boolean consumeSourceDirty(int x, int z) {
      return ((YASkySourceDirtyAccess)this).byepregen$consumeSourceDirty(index(x, z));
   }

   public int decodeSourceCode(int code) {
      if (code == 65535) {
         return Integer.MAX_VALUE;
      } else {
         return code == 0 ? Integer.MIN_VALUE : this.minY + code;
      }
   }

   public void fillFrom(ChunkAccess chunk) {
      int highestSection = chunk.getHighestFilledSectionIndex();
      if (highestSection == -1) {
         Arrays.fill(this.lowest, (short)0);
      } else {
         for (int z = 0; z < 16; z++) {
            for (int x = 0; x < 16; x++) {
               int lowestY = Math.max(this.findLowestSourceY(chunk, highestSection, x, z), this.minY);
               this.set(index(x, z), lowestY);
            }
         }
      }
   }

   public boolean update(BlockGetter level, int x, int y, int z) {
      int aboveY = y + 1;
      int index = index(x, z);
      int lowestY = this.storedSourceY(index);
      if (aboveY < lowestY) {
         return false;
      }

      BlockPos abovePos = this.mutablePos1.set(x, aboveY, z);
      BlockState aboveState = level.getBlockState(abovePos);
      BlockPos pos = this.mutablePos2.set(x, y, z);
      BlockState state = level.getBlockState(pos);
      if (this.updateEdge(level, index, lowestY, abovePos, aboveState, pos, state)) {
         return true;
      }

      BlockPos belowPos = this.mutablePos1.set(x, y - 1, z);
      BlockState belowState = level.getBlockState(belowPos);
      return this.updateEdge(level, index, lowestY, pos, state, belowPos, belowState);
   }

   public int getLowestSourceY(int x, int z) {
      return this.decodeSourceCode(this.sourceCode(x, z));
   }

   public int getHighestLowestSourceY() {
      int highestCode = 0;

      for (short source : this.lowest) {
         int code = source & '\uffff';
         if (code > highestCode) {
            highestCode = code;
         }
      }

      return this.decodeSourceCode(highestCode);
   }

   private int findLowestSourceY(ChunkAccess chunk, int sectionIndex, int x, int z) {
      int y = SectionPos.sectionToBlockCoord(chunk.getSectionYFromSectionIndex(sectionIndex) + 1);
      MutableBlockPos abovePos = this.mutablePos1.set(x, y, z);
      MutableBlockPos belowPos = this.mutablePos2.setWithOffset(abovePos, Direction.DOWN);
      BlockState aboveState = AIR;

      for (int i = sectionIndex; i >= 0; i--) {
         LevelChunkSection section = chunk.getSection(i);
         if (section.hasOnlyAir()) {
            aboveState = AIR;
            int sectionY = chunk.getSectionYFromSectionIndex(i);
            abovePos.setY(SectionPos.sectionToBlockCoord(sectionY));
            belowPos.setY(abovePos.getY() - 1);
         } else {
            for (int localY = 15; localY >= 0; localY--) {
               BlockState belowState = section.getBlockState(x, localY, z);
               if (isEdgeOccluded(chunk, abovePos, aboveState, belowPos, belowState)) {
                  return abovePos.getY();
               }

               aboveState = belowState;
               abovePos.set(belowPos);
               belowPos.move(Direction.DOWN);
            }
         }
      }

      return this.minY;
   }

   private boolean updateEdge(BlockGetter level, int index, int lowestY, BlockPos pos1, BlockState state1, BlockPos pos2, BlockState state2) {
      int y = pos1.getY();
      if (isEdgeOccluded(level, pos1, state1, pos2, state2)) {
         if (y > lowestY) {
            this.setUpdatedSource(index, y);
            return true;
         }
      } else if (y == lowestY) {
         this.setUpdatedSource(index, this.findLowestSourceBelow(level, pos2, state2));
         return true;
      }

      return false;
   }

   private int findLowestSourceBelow(BlockGetter level, BlockPos pos, BlockState state) {
      MutableBlockPos abovePos = this.mutablePos1.set(pos);
      MutableBlockPos belowPos = this.mutablePos2.setWithOffset(pos, Direction.DOWN);
      BlockState aboveState = state;

      while (belowPos.getY() >= this.minY) {
         BlockState belowState = level.getBlockState(belowPos);
         if (isEdgeOccluded(level, abovePos, aboveState, belowPos, belowState)) {
            return abovePos.getY();
         }

         aboveState = belowState;
         abovePos.set(belowPos);
         belowPos.move(Direction.DOWN);
      }

      return this.minY;
   }

   private int storedSourceY(int index) {
      return this.minY + this.sourceCodeAt(index);
   }

   private int sourceCodeAt(int index) {
      return this.lowest[index] & 65535;
   }

   private void set(int index, int value) {
      int code = value - this.minY;
      if (code >= 0 && code < 65535) {
         this.lowest[index] = (short)code;
      } else {
         throw new IllegalArgumentException("Invalid YA sky source y: " + value);
      }
   }

   private void setUpdatedSource(int index, int value) {
      this.set(index, value);
      ((YASkySourceDirtyAccess)this).byepregen$markSourceDirty(index);
   }

   private static boolean isEdgeOccluded(BlockGetter level, BlockPos pos1, BlockState state1, BlockPos pos2, BlockState state2) {
      if (state2.getLightBlock(level, pos2) != 0) {
         return true;
      }

      VoxelShape fromShape = LightEngine.getOcclusionShape(level, pos1, state1, Direction.DOWN);
      VoxelShape toShape = LightEngine.getOcclusionShape(level, pos2, state2, Direction.UP);
      return Shapes.faceShapeOccludes(fromShape, toShape);
   }

   private static int index(int x, int z) {
      return (x & 15) + ((z & 15) << 4);
   }
}
