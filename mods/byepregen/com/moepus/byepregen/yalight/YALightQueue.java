package com.moepus.byepregen.yalight;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;

public final class YALightQueue {
   private static final int INITIAL_TASK_CAPACITY = 16;
   private static final int MAX_RETAINED_TASKS = 256;
   private static final int MAX_POOLED_TASKS = 256;
   private final Long2ObjectOpenHashMap<YALightQueue.ChunkTask> tasks = new Long2ObjectOpenHashMap(16);
   private YALightQueue.ChunkTask firstTask;
   private YALightQueue.ChunkTask lastTask;
   private YALightQueue.ChunkTask pooledTask;
   private int pooledTaskCount;

   void queueCheck(BlockPos pos) {
      this.task(ChunkPos.asLong(pos)).addCheck(pos);
   }

   void queueCheck(long pos) {
      this.task(ChunkPos.asLong(SectionPos.blockToSectionCoord(BlockPos.getX(pos)), SectionPos.blockToSectionCoord(BlockPos.getZ(pos)))).addCheck(pos);
   }

   void queueSource(ChunkPos pos, boolean fresh) {
      YALightQueue.ChunkTask task = this.task(pos.toLong());
      if (fresh) {
         task.freshSource = true;
      } else {
         task.normalSource = true;
      }
   }

   void removeChunk(ChunkPos pos) {
      YALightQueue.ChunkTask task = (YALightQueue.ChunkTask)this.tasks.remove(pos.toLong());
      if (task != null) {
         this.unlink(task);
         this.recycle(task);
         if (this.tasks.isEmpty()) {
            this.tasks.trim(256);
         }
      }
   }

   boolean isEmpty() {
      return this.tasks.isEmpty();
   }

   void collectSourceHalo(YASourceHalo halo, byte layerMask) {
      for (YALightQueue.ChunkTask task = this.firstTask; task != null; task = task.nextQueued) {
         if (task.hasSource()) {
            int chunkX = task.chunkX();
            int chunkZ = task.chunkZ();

            for (int dz = -1; dz <= 1; dz++) {
               for (int dx = -1; dx <= 1; dx++) {
                  halo.add(ChunkPos.asLong(chunkX + dx, chunkZ + dz), layerMask);
               }
            }
         }
      }
   }

   YALightQueue.ChunkTask poll() {
      YALightQueue.ChunkTask task = this.firstTask;
      if (task == null) {
         return null;
      }

      this.unlink(task);
      this.tasks.remove(task.chunkKey);
      if (this.tasks.isEmpty()) {
         this.tasks.trim(256);
      }

      return task;
   }

   void recycle(YALightQueue.ChunkTask task) {
      if (this.pooledTaskCount < 256) {
         task.clearForReuse();
         task.nextPooled = this.pooledTask;
         this.pooledTask = task;
         this.pooledTaskCount++;
      }
   }

   private YALightQueue.ChunkTask task(long chunkKey) {
      YALightQueue.ChunkTask task = (YALightQueue.ChunkTask)this.tasks.get(chunkKey);
      if (task == null) {
         task = this.pooledTask;
         if (task == null) {
            task = new YALightQueue.ChunkTask();
         } else {
            this.pooledTask = task.nextPooled;
            task.nextPooled = null;
            this.pooledTaskCount--;
         }

         task.reset(chunkKey);
         this.append(task);
         this.tasks.put(chunkKey, task);
      }

      return task;
   }

   private void append(YALightQueue.ChunkTask task) {
      task.previousQueued = this.lastTask;
      if (this.lastTask == null) {
         this.firstTask = task;
      } else {
         this.lastTask.nextQueued = task;
      }

      this.lastTask = task;
   }

   private void unlink(YALightQueue.ChunkTask task) {
      if (task.previousQueued == null) {
         this.firstTask = task.nextQueued;
      } else {
         task.previousQueued.nextQueued = task.nextQueued;
      }

      if (task.nextQueued == null) {
         this.lastTask = task.previousQueued;
      } else {
         task.nextQueued.previousQueued = task.previousQueued;
      }

      task.previousQueued = null;
      task.nextQueued = null;
   }

   static final class ChunkTask {
      private static final int LOCAL_BITS = 4;
      private static final int LOCAL_MASK = 15;
      private static final int Z_SHIFT = 4;
      private static final int Y_SHIFT = 8;
      private static final int Y_MASK = 16777215;
      private static final int INITIAL_CHECK_CAPACITY = 16;
      private static final int MAX_RETAINED_CHECKS = 256;
      final YAIntQueue checks = new YAIntQueue(16, 256);
      private final IntOpenHashSet queuedChecks = new IntOpenHashSet(16);
      long chunkKey;
      boolean freshSource;
      boolean normalSource;
      YALightQueue.ChunkTask previousQueued;
      YALightQueue.ChunkTask nextQueued;
      YALightQueue.ChunkTask nextPooled;

      void reset(long chunkKey) {
         this.chunkKey = chunkKey;
         this.freshSource = false;
         this.normalSource = false;
      }

      void addCheck(BlockPos pos) {
         this.addPackedCheck(packCheck(pos.getX(), pos.getY(), pos.getZ()));
      }

      void addCheck(long pos) {
         this.addPackedCheck(packCheck(BlockPos.getX(pos), BlockPos.getY(pos), BlockPos.getZ(pos)));
      }

      private void addPackedCheck(int packed) {
         if (this.queuedChecks.add(packed)) {
            this.checks.add(packed);
         }
      }

      long pollCheckPos() {
         return unpackCheck(this.chunkKey, this.checks.poll());
      }

      int chunkX() {
         return ChunkPos.getX(this.chunkKey);
      }

      int chunkZ() {
         return ChunkPos.getZ(this.chunkKey);
      }

      boolean hasSource() {
         return this.freshSource || this.normalSource;
      }

      private void clearForReuse() {
         this.checks.clear();
         this.queuedChecks.clear();
         this.queuedChecks.trim(256);
      }

      private static int packCheck(int x, int y, int z) {
         return (y & 16777215) << 8 | (z & 15) << 4 | x & 15;
      }

      private static long unpackCheck(long chunkKey, int packed) {
         int chunkX = ChunkPos.getX(chunkKey);
         int chunkZ = ChunkPos.getZ(chunkKey);
         int x = chunkX << 4 | packed & 15;
         int y = packed >> 8;
         int z = chunkZ << 4 | packed >>> 4 & 15;
         return BlockPos.asLong(x, y, z);
      }
   }
}
