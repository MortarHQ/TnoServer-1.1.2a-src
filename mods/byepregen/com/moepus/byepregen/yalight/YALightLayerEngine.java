package com.moepus.byepregen.yalight;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.LayerLightEventListener;

interface YALightLayerEngine extends LayerLightEventListener {
   LightChunkGetter chunkGetter();

   LightLayer lightLayer();

   YALightStorage storage();

   YAChunkRunCache runCache();

   YALightQueue lightQueue();

   YADLongQueue decreaseQueue();

   YADLongQueue increaseQueue();

   YALightBlockAccess blockAccess();

   int sourceLight(long var1, int var3);

   default void checkBlock(BlockPos pos) {
      this.lightQueue().queueCheck(pos);
   }

   default boolean hasLightWork() {
      return !this.lightQueue().isEmpty() || !this.decreaseQueue().isEmpty() || !this.increaseQueue().isEmpty();
   }

   default int runLightUpdates() {
      int work = 0;

      try {
         YALightQueue.ChunkTask task;
         while ((task = this.lightQueue().poll()) != null) {
            work += this.runTask(task);
            this.lightQueue().recycle(task);
         }

         work += this.propagateDecreases();
         work += this.propagateIncreases();
         return work + this.storage().publishDirty(this.chunkGetter(), this.lightLayer());
      } finally {
         this.finishRun();
         this.clearRunCache();
      }
   }

   private int runTask(YALightQueue.ChunkTask task) {
      int work = 0;
      if (task.hasSource()) {
         boolean fresh = task.freshSource && !task.normalSource && task.checks.isEmpty();
         this.propagateLightSourcesInternal(task.chunkX(), task.chunkZ(), fresh);
         work++;
      }

      while (!task.checks.isEmpty()) {
         this.checkBlockInternal(task.pollCheckPos());
         work++;
      }

      return work;
   }

   default void updateSectionStatus(SectionPos pos, boolean isEmpty) {
      this.updateSectionStatus(this.storage().chunkAccess(pos.x(), pos.z()), pos.y(), isEmpty);
   }

   default void updateSectionStatus(ChunkAccess chunk, int sectionY, boolean isEmpty) {
      if (!isEmpty) {
         this.storage().getOrCreateSection(chunk, sectionY);
      }
   }

   default void setLightEnabled(ChunkPos pos, boolean lightEnabled) {
      this.setLightEnabled(this.storage().chunkAccess(pos.x, pos.z), lightEnabled);
   }

   default void setLightEnabled(ChunkAccess chunk, boolean lightEnabled) {
      this.storage().setLightEnabled(chunk, lightEnabled);
      this.clearRunCache();
   }

   default void collectSourceHalo(YASourceHalo halo, byte layerMask) {
      this.lightQueue().collectSourceHalo(halo, layerMask);
   }

   default void checkChunkEdges(ChunkAccess chunk) {
      YAChunkEdgeChecker.check(this, chunk);
   }

   default void enableSourceChunk(ChunkAccess chunk) {
      this.storage().setLightEnabled(chunk, true);
   }

   default void propagateLightSources(ChunkPos pos) {
      this.lightQueue().queueSource(pos, false);
   }

   default void propagateFreshLightSources(ChunkPos pos) {
      this.lightQueue().queueSource(pos, true);
   }

   default DataLayer getDataLayerData(SectionPos pos) {
      YANibbleArray nibble = this.getNibble(pos.x(), pos.y(), pos.z());
      if (nibble != null) {
         DataLayer layer = nibble.toVanilla();
         if (layer != null) {
            return layer;
         }
      }

      return this.storage().lightEnabled(pos.x(), pos.z()) ? new DataLayer() : null;
   }

   default void queueSectionData(SectionPos pos, DataLayer dataLayer) {
      this.queueNibble(pos, YANibbleArray.fromVanilla(dataLayer));
   }

   default void queueOwnedSectionBytes(SectionPos pos, byte[] data) {
      this.queueNibble(pos, YANibbleArray.fromOwnedBytes(data));
   }

   default void queueZeroSectionData(SectionPos pos) {
      this.queueNibble(pos, new YANibbleArray());
   }

   private void queueNibble(SectionPos pos, YANibbleArray nibble) {
      this.storage().setSection(pos, nibble);
   }

   default String getDebugData(SectionPos pos) {
      YANibbleArray nibble = this.getNibble(pos.x(), pos.y(), pos.z());
      return nibble != null && nibble.hasVisibleLayer() ? "YA" : "n/a";
   }

   default boolean lightOnInSection(SectionPos pos) {
      return this.storage().lightOnInSection(pos);
   }

   default void clearChunk(ChunkPos pos) {
      this.lightQueue().removeChunk(pos);
      this.storage().removeChunk(pos);
   }

   default void clearRunCache() {
      this.runCache().clear();
   }

   default void finishRun() {
   }

   default int propagateAfterIncreases() {
      return 0;
   }

   void checkBlockInternal(long var1);

   void propagateLightSourcesInternal(int var1, int var2, boolean var3);

   void propagateIncrease(long var1, long var3);

   void propagateDecrease(long var1, long var3);

   default boolean canUseSection(int chunkX, int sectionY, int chunkZ) {
      return sectionY >= this.storage().minLightSection()
         && sectionY <= this.storage().maxLightSection()
         && this.runCache().lightEnabled(this.storage(), chunkX, chunkZ);
   }

   default int getCachedUpdatingLight(int x, int y, int z) {
      return this.runCache().getUpdatingLight(this.storage(), x, y, z);
   }

   default int getEnabledCachedUpdatingLight(int x, int y, int z) {
      return this.runCache().getEnabledUpdatingLight(this.storage(), x, y, z);
   }

   default int calculateLightValue(long pos, int expected) {
      int x = BlockPos.getX(pos);
      int y = BlockPos.getY(pos);
      int z = BlockPos.getZ(pos);
      YALightBlockAccess blocks = this.blockAccess();
      int centerBlock = blocks.blockAt(x, y, z);
      int level = this.sourceLight(pos, centerBlock);
      if (level <= expected && level != 15 && !blocks.isFull(centerBlock)) {
         for (int directionIndex = 0; directionIndex < 6; directionIndex++) {
            int neighborX = x + YALightMath.stepX(directionIndex);
            int neighborY = y + YALightMath.stepY(directionIndex);
            int neighborZ = z + YALightMath.stepZ(directionIndex);
            int neighborLevel = this.getEnabledCachedUpdatingLight(neighborX, neighborY, neighborZ);
            if (neighborLevel > level + 1) {
               int neighborBlock = blocks.blockAt(neighborX, neighborY, neighborZ);
               int candidate = blocks.attenuatedLevel(neighborLevel, x, y, z, centerBlock);
               if (candidate > level
                  && !blocks.shapeOccludes(neighborX, neighborY, neighborZ, neighborBlock, x, y, z, centerBlock, YALightMath.direction(directionIndex ^ 1))) {
                  level = candidate;
                  if (level > expected) {
                     return level;
                  }
               }
            }
         }

         return level;
      } else {
         return level;
      }
   }

   default void setCachedUpdatingLight(int x, int y, int z, int value) {
      this.runCache().setUpdatingLight(this.storage(), x, y, z, value);
   }

   default YANibbleArray getNibble(int chunkX, int sectionY, int chunkZ) {
      return this.storage().getSection(chunkX, sectionY, chunkZ);
   }

   default void enqueueDecrease(long pos, int level, int directions) {
      this.decreaseQueue().add(pos, YALightMath.meta(level, directions, 0L));
   }

   default void enqueueIncrease(long pos, int level, int directions, long flags) {
      if (directions != 0) {
         this.increaseQueue().add(pos, YALightMath.meta(level, directions, flags));
      }
   }

   private int propagateDecreases() {
      int work;
      for (work = 0; !this.decreaseQueue().isEmpty(); work++) {
         long pos = this.decreaseQueue().first();
         long meta = this.decreaseQueue().second();
         this.decreaseQueue().remove();
         this.propagateDecrease(pos, meta);
      }

      this.decreaseQueue().clear();
      return work;
   }

   private int propagateIncreases() {
      int work = 0;

      while (true) {
         while (this.increaseQueue().isEmpty()) {
            this.increaseQueue().clear();
            int afterWork = this.propagateAfterIncreases();
            work += afterWork;
            if (this.increaseQueue().isEmpty() && afterWork <= 0) {
               return work;
            }
         }

         long pos = this.increaseQueue().first();
         long meta = this.increaseQueue().second();
         this.increaseQueue().remove();
         this.propagateIncrease(pos, meta);
         work++;
      }
   }
}
