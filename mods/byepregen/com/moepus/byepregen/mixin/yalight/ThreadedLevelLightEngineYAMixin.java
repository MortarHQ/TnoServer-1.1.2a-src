package com.moepus.byepregen.mixin.yalight;

import com.moepus.byepregen.mixin.ChunkMapAccessor;
import com.moepus.byepregen.yalight.YAImmediateChunkAccess;
import com.moepus.byepregen.yalight.YALightEngine;
import com.moepus.byepregen.yalight.YALightEngineHolder;
import com.moepus.byepregen.yalight.YAThreadedLightScheduler;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import java.util.Comparator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.ThreadedLevelLightEngine.TaskType;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ThreadedLevelLightEngine.class)
public abstract class ThreadedLevelLightEngineYAMixin {
   @Unique
   private static final TicketType<ChunkPos> YA_LIGHT_WORK = TicketType.create("ya_light_work", Comparator.comparingLong(ChunkPos::toLong));
   @Unique
   private static final String BYEPREGEN_LIGHT_SCHEDULER_WAKE_ON_ADD_PROPERTY = "byepregen.yaLightSchedulerWakeOnAdd";
   @Unique
   private static final boolean BYEPREGEN_LIGHT_SCHEDULER_WAKE_ON_ADD = Boolean.parseBoolean(System.getProperty("byepregen.yaLightSchedulerWakeOnAdd", "true"));
   @Shadow
   @Final
   private ProcessorMailbox<Runnable> taskMailbox;
   @Shadow
   @Final
   private AtomicBoolean scheduled;
   @Shadow
   @Final
   private ChunkMap chunkMap;
   @Unique
   private final Long2IntOpenHashMap byepregen$ticketReferences = new Long2IntOpenHashMap();
   @Unique
   private final YAThreadedLightScheduler byepregen$lightScheduler = new YAThreadedLightScheduler(
      this::byepregen$acquireTaskTicket, this::byepregen$releaseTaskTicket
   );

   @Unique
   private YALightEngine byepregen$yaEngine() {
      return ((YALightEngineHolder)this).byepregen$getYALightEngine();
   }

   @Unique
   private ServerLevel byepregen$level() {
      return ((ChunkMapAccessor)this.chunkMap).byepregen$getLevel();
   }

   @Unique
   private void byepregen$acquireTaskTicket(long chunkKey) {
      int previous = this.byepregen$ticketReferences.addTo(chunkKey, 1);
      if (previous == 0) {
         ChunkPos pos = new ChunkPos(chunkKey);
         this.byepregen$level().getChunkSource().addRegionTicket(YA_LIGHT_WORK, pos, 0, pos);
      }
   }

   @Unique
   private void byepregen$releaseTaskTicket(long chunkKey) {
      ServerLevel level = this.byepregen$level();
      ((ChunkMapAccessor)this.chunkMap).byepregen$getMainThreadExecutor().execute(() -> {
         int references = this.byepregen$ticketReferences.get(chunkKey);
         if (references <= 1) {
            this.byepregen$ticketReferences.remove(chunkKey);
            ChunkPos pos = new ChunkPos(chunkKey);
            level.getChunkSource().removeRegionTicket(YA_LIGHT_WORK, pos, 0, pos);
         } else {
            this.byepregen$ticketReferences.put(chunkKey, references - 1);
         }
      });
   }

   @Unique
   private void byepregen$enqueueTask(long chunkKey, TaskType type, Runnable task, boolean ticketed) {
      this.byepregen$lightScheduler.enqueue(chunkKey, type, task, ticketed);
      if (BYEPREGEN_LIGHT_SCHEDULER_WAKE_ON_ADD) {
         this.byepregen$scheduleDrainWithKnownWork();
      }
   }

   @Unique
   private void byepregen$enqueueRuntimeTask(int chunkX, int chunkZ, TaskType type, Runnable task) {
      long chunkKey = ChunkPos.asLong(chunkX, chunkZ);
      ChunkAccess chunk = ((YAImmediateChunkAccess)this.byepregen$level().getChunkSource()).byepregen$getAnyChunkNow(chunkX, chunkZ);
      if (chunk != null && chunk.getPersistedStatus().isOrAfter(ChunkStatus.LIGHT)) {
         if (chunk.getPersistedStatus() != ChunkStatus.FULL) {
            this.byepregen$enqueueTask(chunkKey, type, task, false);
         } else {
            BlockableEventLoop<Runnable> mainThreadExecutor = ((ChunkMapAccessor)this.chunkMap).byepregen$getMainThreadExecutor();
            if (!mainThreadExecutor.isSameThread()) {
               mainThreadExecutor.execute(() -> this.byepregen$enqueueRuntimeTask(chunkX, chunkZ, type, task));
            } else {
               this.byepregen$enqueueTask(chunkKey, type, task, true);
            }
         }
      }
   }

   @Unique
   private boolean byepregen$scheduleDrain() {
      if (this.byepregen$hasScheduledWork() && this.scheduled.compareAndSet(false, true)) {
         this.byepregen$tellDrainTask();
         return true;
      } else {
         return false;
      }
   }

   @Unique
   private boolean byepregen$scheduleDrainWithKnownWork() {
      if (!this.scheduled.compareAndSet(false, true)) {
         return false;
      }

      this.byepregen$tellDrainTask();
      return true;
   }

   @Unique
   private void byepregen$tellDrainTask() {
      this.taskMailbox.tell((Runnable)() -> {
         try {
            this.byepregen$drainUntilIdle();
         } finally {
            this.scheduled.set(false);
            this.byepregen$scheduleDrain();
         }
      });
   }

   @Unique
   private boolean byepregen$hasScheduledWork() {
      return this.byepregen$lightScheduler.hasWork() || this.byepregen$yaEngine().hasLightWork();
   }

   @Unique
   private void byepregen$drainUntilIdle() {
      while (this.byepregen$hasScheduledWork()) {
         this.byepregen$lightScheduler.drain(this.byepregen$yaEngine(), 8);
      }
   }

   @Overwrite
   public void addTask(int chunkX, int chunkZ, IntSupplier queueLevelSupplier, TaskType type, Runnable task) {
      this.byepregen$enqueueTask(ChunkPos.asLong(chunkX, chunkZ), type, task, false);
   }

   @Overwrite
   public void checkBlock(BlockPos pos) {
      BlockPos immutablePos = pos.immutable();
      this.byepregen$enqueueRuntimeTask(
         SectionPos.blockToSectionCoord(pos.getX()),
         SectionPos.blockToSectionCoord(pos.getZ()),
         TaskType.PRE_UPDATE,
         Util.name(() -> this.byepregen$yaEngine().checkBlock(immutablePos), () -> "YA checkBlock " + immutablePos)
      );
   }

   @Overwrite
   public void updateSectionStatus(SectionPos pos, boolean isEmpty) {
      this.byepregen$enqueueRuntimeTask(
         pos.x(),
         pos.z(),
         TaskType.PRE_UPDATE,
         Util.name(() -> this.byepregen$yaEngine().updateSectionStatus(pos, isEmpty), () -> "YA updateSectionStatus " + pos + " " + isEmpty)
      );
   }

   @Unique
   private void byepregen$addLightChunkTask(int chunkX, int chunkZ, Runnable prepare, Runnable complete) {
      long chunkKey = ChunkPos.asLong(chunkX, chunkZ);
      this.byepregen$lightScheduler.enqueueLightChunk(chunkKey, prepare, complete);
      if (BYEPREGEN_LIGHT_SCHEDULER_WAKE_ON_ADD) {
         this.byepregen$scheduleDrainWithKnownWork();
      }
   }

   @Overwrite
   public void tryScheduleUpdate() {
      this.byepregen$scheduleDrain();
   }

   @Overwrite
   public void updateChunkStatus(ChunkPos chunkPos) {
   }

   @Overwrite
   public void queueSectionData(LightLayer layer, SectionPos pos, @Nullable DataLayer dataLayer) {
      this.byepregen$enqueueTask(
         ChunkPos.asLong(pos.x(), pos.z()),
         TaskType.PRE_UPDATE,
         Util.name(() -> this.byepregen$yaEngine().queueSectionData(layer, pos, dataLayer), () -> "YA queueData " + pos),
         false
      );
   }

   @Overwrite
   public void retainData(ChunkPos pos, boolean retain) {
   }

   @Overwrite
   public CompletableFuture<ChunkAccess> initializeLight(ChunkAccess chunk, boolean lightEnabled) {
      return CompletableFuture.completedFuture(chunk);
   }

   @Overwrite
   public CompletableFuture<ChunkAccess> lightChunk(ChunkAccess chunk, boolean isLighted) {
      ChunkPos chunkPos = chunk.getPos();
      CompletableFuture<ChunkAccess> future = new CompletableFuture<>();
      chunk.setLightCorrect(false);
      Runnable prepare = Util.name(() -> {
         YALightEngine engine = this.byepregen$yaEngine();
         engine.setEdgeCheckReady(chunk, false);
         LevelChunkSection[] sections = chunk.getSections();

         for (int i = 0; i < chunk.getSectionsCount(); i++) {
            LevelChunkSection section = sections[i];
            if (!section.hasOnlyAir()) {
               engine.updateSectionStatus(chunk, chunk.getSectionYFromSectionIndex(i), false);
            }
         }

         engine.setLightEnabled(chunk, true);
         if (!isLighted) {
            engine.propagateFreshLightSources(chunkPos);
         } else {
            engine.restoreSavedSkyLight(chunk);
            engine.setEdgeCheckReady(chunk, true);
            engine.checkChunkEdges(chunk);
         }
      }, () -> "YA lightChunk " + chunkPos + " " + isLighted);
      Runnable complete = () -> {
         this.byepregen$yaEngine().setEdgeCheckReady(chunk, true);
         chunk.setLightCorrect(true);
         future.complete(chunk);
      };
      this.byepregen$addLightChunkTask(chunkPos.x, chunkPos.z, prepare, complete);
      return future;
   }

   @Overwrite
   public CompletableFuture<?> waitForPendingTasks(int x, int z) {
      CompletableFuture<Void> future = new CompletableFuture<>();
      BlockableEventLoop<Runnable> mainThreadExecutor = ((ChunkMapAccessor)this.chunkMap).byepregen$getMainThreadExecutor();
      this.byepregen$enqueueTask(ChunkPos.asLong(x, z), TaskType.POST_UPDATE, () -> mainThreadExecutor.execute(() -> future.complete(null)), false);
      return future;
   }
}
