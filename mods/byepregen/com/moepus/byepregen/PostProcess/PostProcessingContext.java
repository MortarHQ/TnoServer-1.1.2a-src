package com.moepus.byepregen.PostProcess;

import it.unimi.dsi.fastutil.shorts.ShortList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

abstract class PostProcessingContext {
   static final int LOCAL_MASK = 15;
   static final int DELAYED_FLUID_TICK_DELAY = 20;
   private static final Direction[] FLUID_CHECK_DIRECTIONS = new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
   final ChunkAccess chunk;
   final ChunkPos chunkPos;
   final MutableBlockPos pos = new MutableBlockPos();
   final MutableBlockPos neighborPos = new MutableBlockPos();

   private PostProcessingContext(ChunkAccess chunk) {
      this.chunk = chunk;
      this.chunkPos = chunk.getPos();
   }

   static PostProcessingContext preNorm(ChunkAccess chunk) {
      return new PostProcessingContext.PreNorm(chunk);
   }

   static PostProcessingContext postProcess(LevelChunk chunk) {
      if (chunk.getLevel() instanceof ServerLevel serverLevel) {
         return new PostProcessingContext.PostProcess(chunk, serverLevel);
      } else {
         throw new IllegalStateException("LevelChunk post-processing requires ServerLevel");
      }
   }

   void preProcess(ShortList[] postProcessing) {
   }

   void processList(int sectionIndex, ShortList list) {
      int writeIndex = 0;
      int size = list.size();

      for (int readIndex = 0; readIndex < size; readIndex++) {
         short packedPos = list.getShort(readIndex);
         BlockPos pos = this.unpackPos(sectionIndex, packedPos);
         BlockState state = this.getBlockState(pos);
         if (!this.removePostProcessingEntry(packedPos, pos, state)) {
            if (writeIndex != readIndex) {
               list.set(writeIndex, packedPos);
            }

            writeIndex++;
         }
      }

      if (writeIndex < size) {
         list.removeElements(writeIndex, size);
      }
   }

   abstract boolean removePostProcessingEntry(short var1, BlockPos var2, BlockState var3);

   BlockPos unpackPos(int sectionIndex, short packedPos) {
      int sectionY = this.chunk.getSectionYFromSectionIndex(sectionIndex);
      return this.pos
         .set(
            this.chunkPos.getMinBlockX() + localX(packedPos),
            SectionPos.sectionToBlockCoord(sectionY, localY(packedPos)),
            this.chunkPos.getMinBlockZ() + localZ(packedPos)
         );
   }

   boolean isOutsideBuildHeight(BlockPos pos) {
      int y = pos.getY();
      return y < this.chunk.getMinBuildHeight() || y >= this.chunk.getMaxBuildHeight();
   }

   abstract BlockState getBlockState(BlockPos var1);

   boolean isTriviallyStableFluid(BlockPos pos, FluidState fluidState) {
      if (fluidState.getType() instanceof FlowingFluid && fluidState.isSource()) {
         for (Direction direction : FLUID_CHECK_DIRECTIONS) {
            MutableBlockPos neighborPos = this.neighborPos;
            neighborPos.setWithOffset(pos, direction);
            if (!this.isOutsideBuildHeight(neighborPos)) {
               BlockState neighborState = this.getBlockState(neighborPos);
               FluidState neighborFluid = neighborState.getFluidState();
               if ((!neighborFluid.getType().isSame(fluidState.getType()) || !neighborFluid.isSource()) && !neighborState.blocksMotion()) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   static int localX(short packedPos) {
      return packedPos & 15;
   }

   static int localY(short packedPos) {
      return packedPos >>> 4 & 15;
   }

   static int localZ(short packedPos) {
      return packedPos >>> 8 & 15;
   }

   static boolean isLocalChunkEdge(int localX, int localZ) {
      return localX == 0 || localX == 15 || localZ == 0 || localZ == 15;
   }

   private static final class PostProcess extends PostProcessingContext {
      private final LevelChunk levelChunk;
      private final ServerLevel level;
      private boolean westChecked;
      private boolean eastChecked;
      private boolean northChecked;
      private boolean southChecked;
      private LevelChunk westChunk;
      private LevelChunk eastChunk;
      private LevelChunk northChunk;
      private LevelChunk southChunk;

      private PostProcess(LevelChunk chunk, ServerLevel level) {
         super(chunk);
         this.levelChunk = chunk;
         this.level = level;
      }

      @Override
      boolean removePostProcessingEntry(short packedPos, BlockPos pos, BlockState state) {
         FluidState fluidState = state.getFluidState();
         if (fluidState.isEmpty()) {
            return false;
         } else {
            return this.scheduleDelayedEdgeFluidTickIfNeighborMissing(pos, fluidState) ? true : this.isTriviallyStableFluid(pos, fluidState);
         }
      }

      @Override
      BlockState getBlockState(BlockPos pos) {
         return this.level.getBlockState(pos);
      }

      void scheduleDelayedFluidTick(BlockPos pos, Fluid fluid, int delay) {
         this.levelChunk.getLevel().scheduleTick(pos.immutable(), fluid, delay);
      }

      boolean areRequiredNeighborsFull(int localX, int localZ) {
         if (localX == 0 && !this.isWestFull()) {
            return false;
         } else if (localX == 15 && !this.isEastFull()) {
            return false;
         } else {
            return localZ == 0 && !this.isNorthFull() ? false : localZ != 15 || this.isSouthFull();
         }
      }

      private boolean scheduleDelayedEdgeFluidTickIfNeighborMissing(BlockPos pos, FluidState fluidState) {
         int localX = SectionPos.sectionRelative(pos.getX());
         int localZ = SectionPos.sectionRelative(pos.getZ());
         if (!isLocalChunkEdge(localX, localZ)) {
            return false;
         }

         if (this.areRequiredNeighborsFull(localX, localZ)) {
            return false;
         }

         this.scheduleDelayedFluidTick(pos, fluidState.getType(), 20);
         return true;
      }

      private boolean isWestFull() {
         if (!this.westChecked) {
            this.westChunk = this.getLoadedFullChunk(this.chunkPos.x - 1, this.chunkPos.z);
            this.westChecked = true;
         }

         return this.westChunk != null;
      }

      private boolean isEastFull() {
         if (!this.eastChecked) {
            this.eastChunk = this.getLoadedFullChunk(this.chunkPos.x + 1, this.chunkPos.z);
            this.eastChecked = true;
         }

         return this.eastChunk != null;
      }

      private boolean isNorthFull() {
         if (!this.northChecked) {
            this.northChunk = this.getLoadedFullChunk(this.chunkPos.x, this.chunkPos.z - 1);
            this.northChecked = true;
         }

         return this.northChunk != null;
      }

      private boolean isSouthFull() {
         if (!this.southChecked) {
            this.southChunk = this.getLoadedFullChunk(this.chunkPos.x, this.chunkPos.z + 1);
            this.southChecked = true;
         }

         return this.southChunk != null;
      }

      private LevelChunk getLoadedFullChunk(int chunkX, int chunkZ) {
         return this.level.getChunkSource().getChunkNow(chunkX, chunkZ);
      }
   }

   private static final class PreNorm extends PostProcessingContext {
      private PreNorm(ChunkAccess chunk) {
         super(chunk);
      }

      @Override
      void preProcess(ShortList[] postProcessing) {
         PostProcessingSorter.sortAndDeduplicate(postProcessing);
      }

      @Override
      boolean removePostProcessingEntry(short packedPos, BlockPos pos, BlockState state) {
         FluidState fluidState = state.getFluidState();
         if (fluidState.isEmpty()) {
            return PostProcessGenerationOptimizer.isNoOpPostProcess(state);
         } else {
            return isLocalChunkEdge(localX(packedPos), localZ(packedPos)) ? false : this.isTriviallyStableFluid(pos, fluidState);
         }
      }

      @Override
      BlockState getBlockState(BlockPos pos) {
         return this.chunk.getBlockState(pos);
      }
   }
}
