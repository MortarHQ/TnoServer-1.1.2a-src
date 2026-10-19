package org.popcraft.chunky;

import java.util.Deque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import org.popcraft.chunky.api.event.task.GenerationCompleteEvent;
import org.popcraft.chunky.api.event.task.GenerationProgressEvent;
import org.popcraft.chunky.event.task.GenerationTaskFinishEvent;
import org.popcraft.chunky.event.task.GenerationTaskUpdateEvent;
import org.popcraft.chunky.iterator.ChunkIterator;
import org.popcraft.chunky.iterator.ChunkIteratorFactory;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.shape.Shape;
import org.popcraft.chunky.shape.ShapeFactory;
import org.popcraft.chunky.util.ChunkCoordinate;
import org.popcraft.chunky.util.Input;
import org.popcraft.chunky.util.Pair;
import org.popcraft.chunky.util.RegionCache;

public class GenerationTask implements Runnable {
   private static final int MAX_WORKING_COUNT = Input.tryInteger(System.getProperty("chunky.maxWorkingCount")).orElse(50);
   private static final double SAMPLE_INTERVAL = 1000.0 * Math.max(Input.tryInteger(System.getProperty("chunky.sampleInterval")).orElse(30), 30);
   private static final double SAMPLE_SUB_INTERVAL = SAMPLE_INTERVAL / 30.0;
   private final Chunky chunky;
   private final Selection selection;
   private final Shape shape;
   private final AtomicLong startTime = new AtomicLong();
   private final AtomicLong updateTime = new AtomicLong();
   private final AtomicLong finishedChunks = new AtomicLong();
   private final Deque<Pair<Long, AtomicLong>> updateSamples = new ConcurrentLinkedDeque<>();
   private final GenerationTask.Progress progress;
   private final RegionCache.WorldState worldState;
   private ChunkIterator chunkIterator;
   private boolean stopped;
   private boolean cancelled;
   private long prevTime;

   public GenerationTask(Chunky chunky, Selection selection, long count, long time, boolean cancelled) {
      this(chunky, selection);
      this.chunkIterator = ChunkIteratorFactory.getChunkIterator(selection, count);
      this.finishedChunks.set(count);
      this.cancelled = cancelled;
      this.prevTime = time;
   }

   public GenerationTask(Chunky chunky, Selection selection) {
      this.chunky = chunky;
      this.selection = selection;
      this.chunkIterator = ChunkIteratorFactory.getChunkIterator(selection);
      this.shape = ShapeFactory.getShape(selection);
      this.progress = new GenerationTask.Progress(selection.world().getName());
      this.worldState = chunky.getRegionCache().getWorld(selection.world().getName());
   }

   private synchronized void update(int chunkX, int chunkZ, boolean loaded) {
      if (!this.stopped) {
         this.progress.chunkCount = this.finishedChunks.addAndGet(1L);
         this.progress.percentComplete = 100.0F * (float)this.progress.chunkCount / (float)this.chunkIterator.total();
         long currentTime = System.currentTimeMillis();
         Pair<Long, AtomicLong> bin = this.updateSamples.peekLast();
         if (loaded) {
            this.worldState.setGenerated(chunkX, chunkZ);
            if (bin != null && currentTime - bin.left() < SAMPLE_SUB_INTERVAL) {
               bin.right().addAndGet(1L);
            } else if (this.updateSamples.add(Pair.of(currentTime, new AtomicLong(1L)))) {
               while (!this.updateSamples.isEmpty() && currentTime - this.updateSamples.peek().left() > SAMPLE_INTERVAL) {
                  this.updateSamples.poll();
               }
            }
         }

         Pair<Long, AtomicLong> oldest = this.updateSamples.peek();
         long oldestTime = oldest == null ? currentTime : oldest.left();
         long chunksLeft = this.chunkIterator.total() - this.finishedChunks.get();
         double timeDiff = (currentTime - oldestTime) / 1000.0;
         if (chunksLeft <= 0L || !(timeDiff < 0.1)) {
            long sampleCount = 0L;

            for (Pair<Long, AtomicLong> b : this.updateSamples) {
               sampleCount += b.right().get();
            }

            this.progress.rate = timeDiff > 0.0 ? sampleCount / timeDiff : 0.0;
            long time;
            if (chunksLeft == 0L) {
               time = (this.prevTime + (currentTime - this.startTime.get())) / 1000L;
               this.progress.complete = true;
            } else {
               time = (long)(chunksLeft / this.progress.rate);
            }

            this.progress.hours = time / 3600L;
            this.progress.minutes = (time - this.progress.hours * 3600L) / 60L;
            this.progress.seconds = time - this.progress.hours * 3600L - this.progress.minutes * 60L;
            this.progress.chunkX = chunkX;
            this.progress.chunkZ = chunkZ;
            this.chunky
               .getEventBus()
               .call(
                  new GenerationProgressEvent(
                     this.progress.world,
                     this.progress.chunkCount,
                     this.progress.complete,
                     this.progress.percentComplete,
                     this.progress.hours,
                     this.progress.minutes,
                     this.progress.seconds,
                     this.progress.rate,
                     this.progress.chunkX,
                     this.progress.chunkZ
                  )
               );
            if (this.progress.complete) {
               this.progress.sendUpdate(this.chunky.getServer().getConsole());
               this.chunky.getEventBus().call(new GenerationTaskUpdateEvent(this));
            } else {
               boolean silentMode = this.chunky.getConfig().isSilent();
               boolean updateIntervalElapsed = (currentTime - this.updateTime.get()) / 1000.0 > this.chunky.getConfig().getUpdateInterval();
               if (updateIntervalElapsed) {
                  if (!silentMode) {
                     this.progress.sendUpdate(this.chunky.getServer().getConsole());
                  }

                  this.chunky.getEventBus().call(new GenerationTaskUpdateEvent(this));
                  this.updateTime.set(currentTime);
               }
            }
         }
      }
   }

   @Override
   public void run() {
      String poolThreadName = Thread.currentThread().getName();
      Thread.currentThread().setName(String.format("Chunky-%s Thread", this.selection.world().getName()));
      if (!this.chunkIterator.process()) {
         this.stop(true);
      }

      Semaphore working = new Semaphore(MAX_WORKING_COUNT);
      boolean forceLoadExistingChunks = this.chunky.getConfig().isForceLoadExistingChunks();
      this.startTime.set(System.currentTimeMillis());

      while (!this.stopped && this.chunkIterator.hasNext()) {
         ChunkCoordinate chunk = this.chunkIterator.next();
         int chunkCenterX = (chunk.x() << 4) + 8;
         int chunkCenterZ = (chunk.z() << 4) + 8;
         if (!this.shape.isBounding(chunkCenterX, chunkCenterZ)) {
            this.update(chunk.x(), chunk.z(), false);
         } else if (!forceLoadExistingChunks && this.worldState.isGenerated(chunk.x(), chunk.z())) {
            this.update(chunk.x(), chunk.z(), false);
         } else {
            try {
               working.acquire();
            } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
               this.stop(this.cancelled);
               break;
            }

            CompletableFuture<Boolean> isChunkGenerated = forceLoadExistingChunks
               ? CompletableFuture.completedFuture(false)
               : this.selection.world().isChunkGenerated(chunk.x(), chunk.z());
            isChunkGenerated.<Void>thenCompose(
                  generated -> Boolean.TRUE.equals(generated)
                     ? CompletableFuture.completedFuture(null)
                     : this.selection.world().getChunkAtAsync(chunk.x(), chunk.z())
               )
               .whenComplete((ignored, throwable) -> {
                  working.release();
                  this.update(chunk.x(), chunk.z(), true);
               });
         }
      }

      if (this.stopped) {
         this.chunky.getServer().getConsole().sendMessagePrefixed("task_stopped", this.selection.world().getName());
      } else {
         this.cancelled = true;
      }

      this.chunky.getTaskLoader().saveTask(this);
      this.chunky.getGenerationTasks().remove(this.selection.world().getName());
      Thread.currentThread().setName(poolThreadName);
      this.chunky.getEventBus().call(new GenerationTaskFinishEvent(this));
      this.chunky.getEventBus().call(new GenerationCompleteEvent(this.selection.world().getName()));
   }

   public void stop(boolean cancelled) {
      this.stopped = true;
      this.cancelled = cancelled;
   }

   public Chunky getChunky() {
      return this.chunky;
   }

   public Selection getSelection() {
      return this.selection;
   }

   public long getCount() {
      return this.finishedChunks.get();
   }

   public ChunkIterator getChunkIterator() {
      return this.chunkIterator;
   }

   public Shape getShape() {
      return this.shape;
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public long getTotalTime() {
      return this.prevTime + (this.startTime.get() > 0L ? System.currentTimeMillis() - this.startTime.get() : 0L);
   }

   public GenerationTask.Progress getProgress() {
      return this.progress;
   }

   public static final class Progress {
      private final String world;
      private long chunkCount;
      private boolean complete;
      private float percentComplete;
      private long hours;
      private long minutes;
      private long seconds;
      private double rate;
      private int chunkX;
      private int chunkZ;

      private Progress(String world) {
         this.world = world;
      }

      public String getWorld() {
         return this.world;
      }

      public long getChunkCount() {
         return this.chunkCount;
      }

      public boolean isComplete() {
         return this.complete;
      }

      public float getPercentComplete() {
         return this.percentComplete;
      }

      public long getHours() {
         return this.hours;
      }

      public long getMinutes() {
         return this.minutes;
      }

      public long getSeconds() {
         return this.seconds;
      }

      public double getRate() {
         return this.rate;
      }

      public int getChunkX() {
         return this.chunkX;
      }

      public int getChunkZ() {
         return this.chunkZ;
      }

      public void sendUpdate(Sender sender) {
         if (this.complete) {
            sender.sendMessagePrefixed(
               "task_done",
               this.world,
               this.chunkCount,
               String.format("%.2f", this.percentComplete),
               String.format("%01d", this.hours),
               String.format("%02d", this.minutes),
               String.format("%02d", this.seconds)
            );
         } else {
            sender.sendMessagePrefixed(
               "task_update",
               this.world,
               this.chunkCount,
               String.format("%.2f", this.percentComplete),
               String.format("%01d", this.hours),
               String.format("%02d", this.minutes),
               String.format("%02d", this.seconds),
               String.format("%.1f", this.rate),
               this.chunkX,
               this.chunkZ
            );
         }
      }
   }
}
