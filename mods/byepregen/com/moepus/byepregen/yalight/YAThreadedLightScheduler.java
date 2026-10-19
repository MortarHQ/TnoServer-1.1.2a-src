package com.moepus.byepregen.yalight;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.function.LongConsumer;
import net.minecraft.server.level.ThreadedLevelLightEngine.TaskType;

public final class YAThreadedLightScheduler {
   public static final int BATCH_SIZE = 8;
   private static final int INITIAL_TASK_CAPACITY = 16;
   private static final int MAX_RETAINED_TASKS = 256;
   private static final int MAX_POOLED_TASKS = 64;
   private final Long2ObjectLinkedOpenHashMap<YAThreadedLightScheduler.ChunkTask> tasks = new Long2ObjectLinkedOpenHashMap(16);
   private final LongConsumer taskCreated;
   private final LongConsumer taskCompleted;
   private YAThreadedLightScheduler.ChunkTask pooledTask;
   private int pooledTaskCount;

   public YAThreadedLightScheduler(LongConsumer taskCreated, LongConsumer taskCompleted) {
      this.taskCreated = taskCreated;
      this.taskCompleted = taskCompleted;
   }

   public synchronized void enqueue(long chunkKey, TaskType type, Runnable runnable, boolean ticketed) {
      YAThreadedLightScheduler.ChunkTask task = this.task(chunkKey, ticketed);
      if (type == TaskType.PRE_UPDATE) {
         task.preOps.add(runnable);
      } else {
         task.postOps.add(runnable);
      }
   }

   public synchronized void enqueueLightChunk(long chunkKey, Runnable preOp, Runnable postOp) {
      YAThreadedLightScheduler.ChunkTask task = this.task(chunkKey, false);
      task.lightPreOps.add(preOp);
      task.postOps.add(postOp);
   }

   public synchronized boolean hasWork() {
      return !this.tasks.isEmpty();
   }

   public int drain(YALightEngine engine, int maxTasks) {
      YAThreadedLightScheduler.ChunkTask firstTask = this.pollBatch(Math.max(1, maxTasks));
      if (firstTask == null) {
         return engine.runLightUpdates();
      }

      try {
         for (YAThreadedLightScheduler.ChunkTask task = firstTask; task != null; task = task.nextPooled) {
            task.runPreOps();
         }

         int work = engine.runLightUpdates();
         this.releaseBatchTickets(firstTask);

         for (YAThreadedLightScheduler.ChunkTask task = firstTask; task != null; task = task.nextPooled) {
            task.runPostOps();
         }

         return work;
      } finally {
         this.recycleBatch(firstTask);
      }
   }

   private void releaseBatchTickets(YAThreadedLightScheduler.ChunkTask first) {
      for (YAThreadedLightScheduler.ChunkTask task = first; task != null; task = task.nextPooled) {
         if (task.ticketed) {
            task.ticketed = false;
            this.taskCompleted.accept(task.chunkKey);
         }
      }
   }

   private synchronized YAThreadedLightScheduler.ChunkTask pollBatch(int maxTasks) {
      YAThreadedLightScheduler.ChunkTask first = null;
      YAThreadedLightScheduler.ChunkTask last = null;

      for (int i = 0; i < maxTasks && !this.tasks.isEmpty(); i++) {
         YAThreadedLightScheduler.ChunkTask task = (YAThreadedLightScheduler.ChunkTask)this.tasks.removeFirst();
         if (first == null) {
            first = task;
         } else {
            last.nextPooled = task;
         }

         last = task;
      }

      if (last != null) {
         last.nextPooled = null;
      }

      if (this.tasks.isEmpty()) {
         this.tasks.trim(256);
      }

      return first;
   }

   private synchronized void recycleBatch(YAThreadedLightScheduler.ChunkTask first) {
      YAThreadedLightScheduler.ChunkTask task = first;

      while (task != null) {
         YAThreadedLightScheduler.ChunkTask next = task.nextPooled;
         if (task.ticketed) {
            this.taskCompleted.accept(task.chunkKey);
         }

         if (this.pooledTaskCount < 64) {
            task.clearForReuse();
            task.nextPooled = this.pooledTask;
            this.pooledTask = task;
            this.pooledTaskCount++;
         }

         task = next;
      }
   }

   private YAThreadedLightScheduler.ChunkTask task(long chunkKey, boolean ticketed) {
      YAThreadedLightScheduler.ChunkTask task = (YAThreadedLightScheduler.ChunkTask)this.tasks.get(chunkKey);
      if (task != null) {
         if (ticketed && !task.ticketed) {
            task.ticketed = true;
            this.taskCreated.accept(chunkKey);
         }

         return task;
      } else {
         task = this.pooledTask;
         if (task == null) {
            task = new YAThreadedLightScheduler.ChunkTask();
         } else {
            this.pooledTask = task.nextPooled;
            task.nextPooled = null;
            this.pooledTaskCount--;
         }

         task.chunkKey = chunkKey;
         task.ticketed = ticketed;
         this.tasks.put(chunkKey, task);
         if (ticketed) {
            this.taskCreated.accept(chunkKey);
         }

         return task;
      }
   }

   private static final class ChunkTask {
      private static final int INITIAL_OPERATION_CAPACITY = 4;
      private static final int MAX_RETAINED_OPERATIONS = 16;
      private final ObjectArrayList<Runnable> lightPreOps = new ObjectArrayList(4);
      private final ObjectArrayList<Runnable> preOps = new ObjectArrayList(4);
      private final ObjectArrayList<Runnable> postOps = new ObjectArrayList(4);
      private long chunkKey;
      private boolean ticketed;
      private YAThreadedLightScheduler.ChunkTask nextPooled;

      private void runPreOps() {
         ObjectListIterator var1 = this.lightPreOps.iterator();

         while (var1.hasNext()) {
            Runnable op = (Runnable)var1.next();
            op.run();
         }

         var1 = this.preOps.iterator();

         while (var1.hasNext()) {
            Runnable op = (Runnable)var1.next();
            op.run();
         }
      }

      private void runPostOps() {
         ObjectListIterator var1 = this.postOps.iterator();

         while (var1.hasNext()) {
            Runnable op = (Runnable)var1.next();
            op.run();
         }
      }

      private void clearForReuse() {
         this.lightPreOps.clear();
         this.preOps.clear();
         this.postOps.clear();
         this.ticketed = false;
         this.lightPreOps.trim(16);
         this.preOps.trim(16);
         this.postOps.trim(16);
      }
   }
}
