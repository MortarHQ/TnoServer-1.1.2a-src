package com.tensura_guild.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;

public record GuildTaskBoardData(
   int debugTaskCount,
   List<ResourceLocation> availableTasks,
   Map<ResourceLocation, Long> availableTaskWithdrawAt,
   Optional<ResourceLocation> activeTaskId,
   Optional<UUID> activeTaskPartyLeaderId,
   Optional<Integer> activeTaskTargetX,
   Optional<Integer> activeTaskTargetZ,
   Optional<UUID> activeTaskTrackedEntityId,
   int activeTaskRequiredCount,
   boolean activeTaskTargetSpawned,
   int taskProgress,
   long taskStartedAt,
   int taskTimeLimitTicks,
   long taskStartedAtMillis,
   long taskTimeLimitMillis,
   List<GuildTaskBoardData.ActiveTask> extraActiveTasks,
   List<ResourceLocation> failedTasks
) {
   private static final Codec<UUID> UUID_CODEC = Codec.STRING.xmap(UUID::fromString, UUID::toString);
   public static final GuildTaskBoardData EMPTY = new GuildTaskBoardData(
      0,
      List.of(GuildTaskDefinitions.KILL_5_SLIMES),
      Map.of(),
      Optional.empty(),
      Optional.empty(),
      Optional.empty(),
      Optional.empty(),
      Optional.empty(),
      -1,
      false,
      0,
      -1L,
      0,
      -1L,
      0L,
      List.of(),
      List.of()
   );
   public static final Codec<GuildTaskBoardData> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.INT.optionalFieldOf("debug_task_count", 0).forGetter(GuildTaskBoardData::debugTaskCount),
            ResourceLocation.CODEC.listOf().optionalFieldOf("available_tasks", List.of()).forGetter(GuildTaskBoardData::availableTasks),
            Codec.unboundedMap(ResourceLocation.CODEC, Codec.LONG)
               .optionalFieldOf("available_task_withdraw_at", Map.of())
               .forGetter(GuildTaskBoardData::availableTaskWithdrawAt),
            ResourceLocation.CODEC.optionalFieldOf("active_task_id").forGetter(GuildTaskBoardData::activeTaskId),
            UUID_CODEC.optionalFieldOf("active_task_party_leader_id").forGetter(GuildTaskBoardData::activeTaskPartyLeaderId),
            Codec.INT.optionalFieldOf("active_task_target_x").forGetter(GuildTaskBoardData::activeTaskTargetX),
            Codec.INT.optionalFieldOf("active_task_target_z").forGetter(GuildTaskBoardData::activeTaskTargetZ),
            UUID_CODEC.optionalFieldOf("active_task_tracked_entity_id").forGetter(GuildTaskBoardData::activeTaskTrackedEntityId),
            GuildTaskBoardData.PrimaryTaskOptions.CODEC.forGetter(GuildTaskBoardData::primaryTaskOptions),
            Codec.INT.optionalFieldOf("task_progress", 0).forGetter(GuildTaskBoardData::taskProgress),
            Codec.LONG.optionalFieldOf("task_started_at", -1L).forGetter(GuildTaskBoardData::taskStartedAt),
            Codec.INT.optionalFieldOf("task_time_limit_ticks", 0).forGetter(GuildTaskBoardData::taskTimeLimitTicks),
            Codec.LONG.optionalFieldOf("task_started_at_millis", -1L).forGetter(GuildTaskBoardData::taskStartedAtMillis),
            Codec.LONG.optionalFieldOf("task_time_limit_millis", 0L).forGetter(GuildTaskBoardData::taskTimeLimitMillis),
            GuildTaskBoardData.ActiveTask.CODEC.listOf().optionalFieldOf("extra_active_tasks", List.of()).forGetter(GuildTaskBoardData::extraActiveTasks),
            ResourceLocation.CODEC.listOf().optionalFieldOf("failed_tasks", List.of()).forGetter(GuildTaskBoardData::failedTasks)
         )
         .apply(
            instance,
            (debugTaskCount, availableTasks, availableTaskWithdrawAt, activeTaskId, activeTaskPartyLeaderId, activeTaskTargetX, activeTaskTargetZ, activeTaskTrackedEntityId, primaryTaskOptions, taskProgress, taskStartedAt, taskTimeLimitTicks, taskStartedAtMillis, taskTimeLimitMillis, extraActiveTasks, failedTasks) -> new GuildTaskBoardData(
               debugTaskCount,
               availableTasks,
               availableTaskWithdrawAt,
               activeTaskId,
               activeTaskPartyLeaderId,
               activeTaskTargetX,
               activeTaskTargetZ,
               activeTaskTrackedEntityId,
               primaryTaskOptions.requiredCount(),
               primaryTaskOptions.targetSpawned(),
               taskProgress,
               taskStartedAt,
               taskTimeLimitTicks,
               taskStartedAtMillis,
               taskTimeLimitMillis,
               extraActiveTasks,
               failedTasks
            )
         )
   );
   public static final StreamCodec<ByteBuf, GuildTaskBoardData> STREAM_CODEC = new StreamCodec<ByteBuf, GuildTaskBoardData>() {
      public GuildTaskBoardData decode(ByteBuf buffer) {
         CompoundTag tag = new FriendlyByteBuf(buffer).readNbt();
         return tag == null ? GuildTaskBoardData.EMPTY : GuildTaskBoardData.CODEC.parse(NbtOps.INSTANCE, tag).result().orElse(GuildTaskBoardData.EMPTY);
      }

      public void encode(ByteBuf buffer, GuildTaskBoardData value) {
         CompoundTag tag = (CompoundTag)GuildTaskBoardData.CODEC.encodeStart(NbtOps.INSTANCE, value).result().orElseGet(CompoundTag::new);
         new FriendlyByteBuf(buffer).writeNbt(tag);
      }
   };

   public GuildTaskBoardData {
      debugTaskCount = Math.max(0, debugTaskCount);
      availableTasks = List.copyOf(availableTasks == null ? List.of() : availableTasks);
      availableTaskWithdrawAt = Map.copyOf(availableTaskWithdrawAt == null ? Map.of() : availableTaskWithdrawAt);
      activeTaskId = activeTaskId == null ? Optional.empty() : activeTaskId;
      activeTaskPartyLeaderId = activeTaskPartyLeaderId == null ? Optional.empty() : activeTaskPartyLeaderId;
      activeTaskTargetX = activeTaskTargetX == null ? Optional.empty() : activeTaskTargetX;
      activeTaskTargetZ = activeTaskTargetZ == null ? Optional.empty() : activeTaskTargetZ;
      activeTaskTrackedEntityId = activeTaskTrackedEntityId == null ? Optional.empty() : activeTaskTrackedEntityId;
      activeTaskPartyLeaderId = activeTaskId.isPresent() ? activeTaskPartyLeaderId : Optional.empty();
      activeTaskTargetX = activeTaskId.isPresent() ? activeTaskTargetX : Optional.empty();
      activeTaskTargetZ = activeTaskId.isPresent() ? activeTaskTargetZ : Optional.empty();
      activeTaskTrackedEntityId = activeTaskId.isPresent() ? activeTaskTrackedEntityId : Optional.empty();
      activeTaskRequiredCount = activeTaskId.isPresent() && activeTaskRequiredCount > 0 ? activeTaskRequiredCount : -1;
      activeTaskTargetSpawned = activeTaskId.isPresent() && activeTaskTargetSpawned;
      int maxProgress = activeTaskId.flatMap(GuildTaskDefinitions::get).map(GuildTaskDefinitions.GuildTaskDefinition::requiredKills).orElse(Integer.MAX_VALUE);
      if (activeTaskRequiredCount > 0) {
         maxProgress = activeTaskRequiredCount;
      }

      taskProgress = Mth.clamp(taskProgress, 0, maxProgress);
      taskStartedAt = activeTaskId.isPresent() ? Math.max(-1L, taskStartedAt) : -1L;
      taskTimeLimitTicks = activeTaskId.isPresent() ? Math.max(0, taskTimeLimitTicks) : 0;
      taskStartedAtMillis = activeTaskId.isPresent() ? Math.max(-1L, taskStartedAtMillis) : -1L;
      taskTimeLimitMillis = activeTaskId.isPresent() ? Math.max(0L, taskTimeLimitMillis) : 0L;
      extraActiveTasks = List.copyOf(extraActiveTasks == null ? List.of() : extraActiveTasks);
      failedTasks = List.copyOf(failedTasks == null ? List.of() : failedTasks);
   }

   private GuildTaskBoardData.PrimaryTaskOptions primaryTaskOptions() {
      return new GuildTaskBoardData.PrimaryTaskOptions(this.activeTaskRequiredCount, this.activeTaskTargetSpawned);
   }

   public GuildTaskBoardData addDebugTasks(int amount) {
      return new GuildTaskBoardData(
         this.debugTaskCount + Math.max(0, amount),
         this.availableTasks,
         this.availableTaskWithdrawAt,
         this.activeTaskId,
         this.activeTaskPartyLeaderId,
         this.activeTaskTargetX,
         this.activeTaskTargetZ,
         this.activeTaskTrackedEntityId,
         this.activeTaskRequiredCount,
         this.activeTaskTargetSpawned,
         this.taskProgress,
         this.taskStartedAt,
         this.taskTimeLimitTicks,
         this.taskStartedAtMillis,
         this.taskTimeLimitMillis,
         this.extraActiveTasks,
         this.failedTasks
      );
   }

   public GuildTaskBoardData clearDebugTasks() {
      return new GuildTaskBoardData(
         0,
         this.availableTasks,
         this.availableTaskWithdrawAt,
         this.activeTaskId,
         this.activeTaskPartyLeaderId,
         this.activeTaskTargetX,
         this.activeTaskTargetZ,
         this.activeTaskTrackedEntityId,
         this.activeTaskRequiredCount,
         this.activeTaskTargetSpawned,
         this.taskProgress,
         this.taskStartedAt,
         this.taskTimeLimitTicks,
         this.taskStartedAtMillis,
         this.taskTimeLimitMillis,
         this.extraActiveTasks,
         this.failedTasks
      );
   }

   public GuildTaskBoardData withAvailableTasks(List<ResourceLocation> availableTasks) {
      return this.withAvailableTasks(availableTasks, this.availableTaskWithdrawAt);
   }

   public GuildTaskBoardData withAvailableTasks(List<ResourceLocation> availableTasks, Map<ResourceLocation, Long> availableTaskWithdrawAt) {
      return new GuildTaskBoardData(
         this.debugTaskCount,
         availableTasks,
         availableTaskWithdrawAt,
         this.activeTaskId,
         this.activeTaskPartyLeaderId,
         this.activeTaskTargetX,
         this.activeTaskTargetZ,
         this.activeTaskTrackedEntityId,
         this.activeTaskRequiredCount,
         this.activeTaskTargetSpawned,
         this.taskProgress,
         this.taskStartedAt,
         this.taskTimeLimitTicks,
         this.taskStartedAtMillis,
         this.taskTimeLimitMillis,
         this.extraActiveTasks,
         this.failedTasks
      );
   }

   public GuildTaskBoardData acceptTask(ResourceLocation taskId, long gameTime, int timeLimitTicks) {
      return this.acceptTask(taskId, gameTime, timeLimitTicks, Optional.empty(), Optional.empty());
   }

   public GuildTaskBoardData acceptTask(ResourceLocation taskId, long gameTime, int timeLimitTicks, Optional<UUID> partyLeaderId) {
      return this.acceptTask(taskId, gameTime, timeLimitTicks, partyLeaderId, Optional.empty());
   }

   public GuildTaskBoardData acceptTask(ResourceLocation taskId, long gameTime, int timeLimitTicks, Optional<UUID> partyLeaderId, Optional<BlockPos> targetPos) {
      return this.acceptTask(taskId, gameTime, timeLimitTicks, partyLeaderId, targetPos, -1);
   }

   public GuildTaskBoardData acceptTask(
      ResourceLocation taskId, long gameTime, int timeLimitTicks, Optional<UUID> partyLeaderId, Optional<BlockPos> targetPos, int requiredCount
   ) {
      if (!this.hasAcceptedTask(taskId) && !GuildTaskDefinitions.get(taskId).isEmpty()) {
         GuildTaskBoardData.ActiveTask accepted = GuildTaskBoardData.ActiveTask.create(
            taskId, gameTime, timeLimitTicks, partyLeaderId, targetPos, requiredCount
         );
         if (this.activeTaskId.isEmpty()) {
            return new GuildTaskBoardData(
               this.debugTaskCount,
               this.withoutAvailableTask(taskId),
               this.availableTaskWithdrawAt,
               Optional.of(taskId),
               accepted.partyLeaderId(),
               accepted.targetX(),
               accepted.targetZ(),
               accepted.trackedEntityId(),
               accepted.requiredCount(),
               accepted.targetSpawned(),
               0,
               accepted.taskStartedAt(),
               accepted.taskTimeLimitTicks(),
               accepted.taskStartedAtMillis(),
               accepted.taskTimeLimitMillis(),
               this.extraActiveTasks,
               this.failedTasks
            );
         }

         List<GuildTaskBoardData.ActiveTask> tasks = new ArrayList<>(this.extraActiveTasks);
         tasks.add(accepted);
         return new GuildTaskBoardData(
            this.debugTaskCount,
            this.withoutAvailableTask(taskId),
            this.availableTaskWithdrawAt,
            this.activeTaskId,
            this.activeTaskPartyLeaderId,
            this.activeTaskTargetX,
            this.activeTaskTargetZ,
            this.activeTaskTrackedEntityId,
            this.activeTaskRequiredCount,
            this.activeTaskTargetSpawned,
            this.taskProgress,
            this.taskStartedAt,
            this.taskTimeLimitTicks,
            this.taskStartedAtMillis,
            this.taskTimeLimitMillis,
            tasks,
            this.failedTasks
         );
      } else {
         return this;
      }
   }

   public GuildTaskBoardData addKillFor(
      ResourceLocation entityType, ServerLevel level, BlockPos killPos, boolean partyReady, long currentGameTime, int fallbackTimeLimitTicks
   ) {
      return this.addKillFor(entityType, Optional.empty(), level, killPos, partyReady, currentGameTime, fallbackTimeLimitTicks);
   }

   public GuildTaskBoardData addKillFor(
      ResourceLocation entityType,
      Optional<UUID> killedEntityId,
      ServerLevel level,
      BlockPos killPos,
      boolean partyReady,
      long currentGameTime,
      int fallbackTimeLimitTicks
   ) {
      if (!this.hasActiveTask()) {
         return this;
      }

      Optional<GuildTaskBoardData.ActiveTask> primary = this.primaryTask()
         .map(task -> task.addKillFor(entityType, killedEntityId, level, killPos, partyReady, currentGameTime, fallbackTimeLimitTicks));
      List<GuildTaskBoardData.ActiveTask> extras = this.extraActiveTasks
         .stream()
         .map(task -> task.addKillFor(entityType, killedEntityId, level, killPos, partyReady, currentGameTime, fallbackTimeLimitTicks))
         .toList();
      return fromTasks(this.debugTaskCount, this.availableTasks, this.availableTaskWithdrawAt, primary, extras, this.failedTasks);
   }

   public GuildTaskBoardData addPartyKillFor(
      ResourceLocation entityType,
      UUID partyLeaderId,
      ServerLevel level,
      BlockPos killPos,
      boolean partyReady,
      long currentGameTime,
      int fallbackTimeLimitTicks
   ) {
      return this.addPartyKillFor(entityType, Optional.empty(), partyLeaderId, level, killPos, partyReady, currentGameTime, fallbackTimeLimitTicks);
   }

   public GuildTaskBoardData addPartyKillFor(
      ResourceLocation entityType,
      Optional<UUID> killedEntityId,
      UUID partyLeaderId,
      ServerLevel level,
      BlockPos killPos,
      boolean partyReady,
      long currentGameTime,
      int fallbackTimeLimitTicks
   ) {
      if (this.hasActiveTask() && partyLeaderId != null) {
         Optional<GuildTaskBoardData.ActiveTask> primary = this.primaryTask()
            .map(task -> task.addPartyKillFor(entityType, killedEntityId, partyLeaderId, level, killPos, partyReady, currentGameTime, fallbackTimeLimitTicks));
         List<GuildTaskBoardData.ActiveTask> extras = this.extraActiveTasks
            .stream()
            .map(task -> task.addPartyKillFor(entityType, killedEntityId, partyLeaderId, level, killPos, partyReady, currentGameTime, fallbackTimeLimitTicks))
            .toList();
         return fromTasks(this.debugTaskCount, this.availableTasks, this.availableTaskWithdrawAt, primary, extras, this.failedTasks);
      } else {
         return this;
      }
   }

   public GuildTaskBoardData completeTask(ResourceLocation taskId) {
      List<GuildTaskBoardData.ActiveTask> tasks = new ArrayList<>(this.activeTasks());
      return !tasks.removeIf(task -> task.taskId().equals(taskId))
         ? this
         : fromTasks(this.debugTaskCount, this.availableTasks, this.availableTaskWithdrawAt, tasks, this.failedTasks);
   }

   public GuildTaskBoardData failTask(ResourceLocation taskId) {
      List<GuildTaskBoardData.ActiveTask> tasks = new ArrayList<>(this.activeTasks());
      if (!tasks.removeIf(task -> task.taskId().equals(taskId))) {
         return this;
      }

      List<ResourceLocation> failures = new ArrayList<>(this.failedTasks);
      if (!failures.contains(taskId)) {
         failures.add(taskId);
      }

      return fromTasks(this.debugTaskCount, this.availableTasks, this.availableTaskWithdrawAt, tasks, failures);
   }

   public GuildTaskBoardData updateTrackedTask(ResourceLocation taskId, BlockPos targetPos, Optional<UUID> trackedEntityId) {
      List<GuildTaskBoardData.ActiveTask> tasks = new ArrayList<>();
      boolean changed = false;

      for (GuildTaskBoardData.ActiveTask task : this.activeTasks()) {
         if (task.taskId().equals(taskId)) {
            GuildTaskBoardData.ActiveTask updated = task.withTracking(targetPos, trackedEntityId);
            tasks.add(updated);
            changed = changed || !updated.equals(task);
         } else {
            tasks.add(task);
         }
      }

      return changed ? fromTasks(this.debugTaskCount, this.availableTasks, this.availableTaskWithdrawAt, tasks, this.failedTasks) : this;
   }

   public GuildTaskBoardData markTargetSpawned(ResourceLocation taskId, BlockPos targetPos) {
      List<GuildTaskBoardData.ActiveTask> tasks = new ArrayList<>();
      boolean changed = false;

      for (GuildTaskBoardData.ActiveTask task : this.activeTasks()) {
         if (task.taskId().equals(taskId)) {
            GuildTaskBoardData.ActiveTask updated = task.withTargetSpawned(targetPos, true);
            tasks.add(updated);
            changed = changed || !updated.equals(task);
         } else {
            tasks.add(task);
         }
      }

      return changed ? fromTasks(this.debugTaskCount, this.availableTasks, this.availableTaskWithdrawAt, tasks, this.failedTasks) : this;
   }

   public GuildTaskBoardData markVisitStructureComplete(ResourceLocation taskId, long currentGameTime, int fallbackTimeLimitTicks) {
      List<GuildTaskBoardData.ActiveTask> tasks = new ArrayList<>();
      boolean changed = false;

      for (GuildTaskBoardData.ActiveTask task : this.activeTasks()) {
         GuildTaskBoardData.ActiveTask updated = task.taskId().equals(taskId) ? task.markVisitStructureComplete(currentGameTime, fallbackTimeLimitTicks) : task;
         tasks.add(updated);
         changed = changed || !updated.equals(task);
      }

      return changed ? fromTasks(this.debugTaskCount, this.availableTasks, this.availableTaskWithdrawAt, tasks, this.failedTasks) : this;
   }

   public GuildTaskBoardData failExpiredTasks(long currentGameTime, int fallbackTimeLimitTicks) {
      List<GuildTaskBoardData.ActiveTask> remainingTasks = new ArrayList<>();
      List<ResourceLocation> failures = new ArrayList<>(this.failedTasks);
      boolean changed = false;

      for (GuildTaskBoardData.ActiveTask task : this.activeTasks()) {
         if (task.isExpired(currentGameTime, fallbackTimeLimitTicks)) {
            if (!failures.contains(task.taskId())) {
               failures.add(task.taskId());
            }

            changed = true;
         } else {
            remainingTasks.add(task);
         }
      }

      return changed ? fromTasks(this.debugTaskCount, this.availableTasks, this.availableTaskWithdrawAt, remainingTasks, failures) : this;
   }

   public GuildTaskBoardData dismissFailedTask(ResourceLocation taskId) {
      if (taskId != null && this.failedTasks.contains(taskId)) {
         List<ResourceLocation> failures = new ArrayList<>(this.failedTasks);
         failures.remove(taskId);
         return fromTasks(this.debugTaskCount, this.availableTasks, this.availableTaskWithdrawAt, this.activeTasks(), failures);
      } else {
         return this;
      }
   }

   public GuildTaskBoardData removePartyTasks(UUID partyLeaderId) {
      if (partyLeaderId == null) {
         return this;
      }

      List<GuildTaskBoardData.ActiveTask> tasks = new ArrayList<>(this.activeTasks());
      return !tasks.removeIf(task -> task.partyLeaderId().filter(partyLeaderId::equals).isPresent())
         ? this
         : fromTasks(this.debugTaskCount, this.availableTasks, this.availableTaskWithdrawAt, tasks, this.failedTasks);
   }

   public List<ResourceLocation> partyTaskIds(UUID partyLeaderId) {
      return partyLeaderId == null
         ? List.of()
         : this.activeTasks()
            .stream()
            .filter(task -> task.partyLeaderId().filter(partyLeaderId::equals).isPresent())
            .map(GuildTaskBoardData.ActiveTask::taskId)
            .toList();
   }

   public List<UUID> partyLeaderIds() {
      List<UUID> leaderIds = new ArrayList<>();

      for (GuildTaskBoardData.ActiveTask task : this.activeTasks()) {
         task.partyLeaderId().ifPresent(leaderId -> {
            if (!leaderIds.contains(leaderId)) {
               leaderIds.add(leaderId);
            }
         });
      }

      return List.copyOf(leaderIds);
   }

   public boolean hasActiveTask() {
      return !this.activeTasks().isEmpty();
   }

   public int activeTaskCount() {
      return this.activeTasks().size();
   }

   public boolean hasAcceptedTask(ResourceLocation taskId) {
      return this.activeTasks().stream().anyMatch(task -> task.taskId().equals(taskId));
   }

   public boolean hasFailedTask(ResourceLocation taskId) {
      return this.failedTasks.contains(taskId);
   }

   public boolean isTaskAvailable(ResourceLocation taskId) {
      return this.availableTasks.contains(taskId);
   }

   public boolean isTaskComplete() {
      return this.activeTaskId.<Boolean>map(this::isTaskComplete).orElse(false);
   }

   public boolean isTaskComplete(ResourceLocation taskId) {
      return this.activeTask(taskId)
         .flatMap(
            active -> GuildTaskDefinitions.get(active.taskId())
               .filter(task -> task.isKillTask() || task.isVisitStructureTask())
               .map(task -> active.taskProgress() >= active.requiredCountOrDefault(task.requiredCount()))
         )
         .orElse(false);
   }

   public int taskProgress(ResourceLocation taskId) {
      return this.activeTask(taskId).map(GuildTaskBoardData.ActiveTask::taskProgress).orElse(0);
   }

   public Optional<GuildTaskBoardData.ActiveTask> activeTask(ResourceLocation taskId) {
      return this.activeTasks().stream().filter(task -> task.taskId().equals(taskId)).findFirst();
   }

   public List<GuildTaskBoardData.ActiveTask> activeTasks() {
      List<GuildTaskBoardData.ActiveTask> tasks = new ArrayList<>();
      this.primaryTask().ifPresent(tasks::add);
      tasks.addAll(this.extraActiveTasks);
      return List.copyOf(tasks);
   }

   public boolean isTaskExpired(long currentGameTime, int timeLimitTicks) {
      return this.activeTaskId.<Boolean>map(taskId -> this.isTaskExpired(taskId, currentGameTime, timeLimitTicks)).orElse(false);
   }

   public boolean isTaskExpired(ResourceLocation taskId, long currentGameTime, int timeLimitTicks) {
      return this.activeTask(taskId).map(task -> task.isExpired(currentGameTime, timeLimitTicks)).orElse(false);
   }

   public long remainingTaskTicks(long currentGameTime, int fallbackTimeLimitTicks) {
      int effectiveLimitTicks = this.taskTimeLimitTicks > 0 ? this.taskTimeLimitTicks : fallbackTimeLimitTicks;
      return GuildTaskTime.remainingTicks(currentGameTime, this.taskStartedAt, effectiveLimitTicks);
   }

   public Optional<Long> remainingTaskTicks(ResourceLocation taskId, long currentGameTime, int fallbackTimeLimitTicks) {
      return this.activeTask(taskId).map(task -> task.remainingTicks(currentGameTime, fallbackTimeLimitTicks));
   }

   private Optional<GuildTaskBoardData.ActiveTask> primaryTask() {
      return this.activeTaskId
         .map(
            taskId -> new GuildTaskBoardData.ActiveTask(
               taskId,
               this.taskProgress,
               this.taskStartedAt,
               this.taskTimeLimitTicks,
               this.taskStartedAtMillis,
               this.taskTimeLimitMillis,
               this.activeTaskPartyLeaderId,
               this.activeTaskTargetX,
               this.activeTaskTargetZ,
               this.activeTaskTrackedEntityId,
               this.activeTaskRequiredCount,
               this.activeTaskTargetSpawned
            )
         );
   }

   private List<ResourceLocation> withoutAvailableTask(ResourceLocation taskId) {
      List<ResourceLocation> tasks = new ArrayList<>(this.availableTasks);
      tasks.remove(taskId);
      return tasks;
   }

   private static GuildTaskBoardData fromTasks(
      int debugTaskCount,
      List<ResourceLocation> availableTasks,
      Map<ResourceLocation, Long> availableTaskWithdrawAt,
      Optional<GuildTaskBoardData.ActiveTask> primary,
      List<GuildTaskBoardData.ActiveTask> extras,
      List<ResourceLocation> failedTasks
   ) {
      List<GuildTaskBoardData.ActiveTask> tasks = new ArrayList<>();
      primary.ifPresent(tasks::add);
      tasks.addAll(extras);
      return fromTasks(debugTaskCount, availableTasks, availableTaskWithdrawAt, tasks, failedTasks);
   }

   private static GuildTaskBoardData fromTasks(
      int debugTaskCount,
      List<ResourceLocation> availableTasks,
      Map<ResourceLocation, Long> availableTaskWithdrawAt,
      List<GuildTaskBoardData.ActiveTask> tasks,
      List<ResourceLocation> failedTasks
   ) {
      if (tasks.isEmpty()) {
         return new GuildTaskBoardData(
            debugTaskCount,
            availableTasks,
            availableTaskWithdrawAt,
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            -1,
            false,
            0,
            -1L,
            0,
            -1L,
            0L,
            List.of(),
            failedTasks
         );
      }

      GuildTaskBoardData.ActiveTask primary = tasks.get(0);
      List<GuildTaskBoardData.ActiveTask> extras = tasks.size() > 1 ? tasks.subList(1, tasks.size()) : List.of();
      return new GuildTaskBoardData(
         debugTaskCount,
         availableTasks,
         availableTaskWithdrawAt,
         Optional.of(primary.taskId()),
         primary.partyLeaderId(),
         primary.targetX(),
         primary.targetZ(),
         primary.trackedEntityId(),
         primary.requiredCount(),
         primary.targetSpawned(),
         primary.taskProgress(),
         primary.taskStartedAt(),
         primary.taskTimeLimitTicks(),
         primary.taskStartedAtMillis(),
         primary.taskTimeLimitMillis(),
         extras,
         failedTasks
      );
   }

   public record ActiveTask(
      ResourceLocation taskId,
      int taskProgress,
      long taskStartedAt,
      int taskTimeLimitTicks,
      long taskStartedAtMillis,
      long taskTimeLimitMillis,
      Optional<UUID> partyLeaderId,
      Optional<Integer> targetX,
      Optional<Integer> targetZ,
      Optional<UUID> trackedEntityId,
      int requiredCount,
      boolean targetSpawned
   ) {
      public static final Codec<GuildTaskBoardData.ActiveTask> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ResourceLocation.CODEC.fieldOf("task_id").forGetter(GuildTaskBoardData.ActiveTask::taskId),
               Codec.INT.optionalFieldOf("task_progress", 0).forGetter(GuildTaskBoardData.ActiveTask::taskProgress),
               Codec.LONG.optionalFieldOf("task_started_at", -1L).forGetter(GuildTaskBoardData.ActiveTask::taskStartedAt),
               Codec.INT.optionalFieldOf("task_time_limit_ticks", 0).forGetter(GuildTaskBoardData.ActiveTask::taskTimeLimitTicks),
               Codec.LONG.optionalFieldOf("task_started_at_millis", -1L).forGetter(GuildTaskBoardData.ActiveTask::taskStartedAtMillis),
               Codec.LONG.optionalFieldOf("task_time_limit_millis", 0L).forGetter(GuildTaskBoardData.ActiveTask::taskTimeLimitMillis),
               GuildTaskBoardData.UUID_CODEC.optionalFieldOf("party_leader_id").forGetter(GuildTaskBoardData.ActiveTask::partyLeaderId),
               Codec.INT.optionalFieldOf("target_x").forGetter(GuildTaskBoardData.ActiveTask::targetX),
               Codec.INT.optionalFieldOf("target_z").forGetter(GuildTaskBoardData.ActiveTask::targetZ),
               GuildTaskBoardData.UUID_CODEC.optionalFieldOf("tracked_entity_id").forGetter(GuildTaskBoardData.ActiveTask::trackedEntityId),
               Codec.INT.optionalFieldOf("required_count", -1).forGetter(GuildTaskBoardData.ActiveTask::requiredCount),
               Codec.BOOL.optionalFieldOf("target_spawned", false).forGetter(GuildTaskBoardData.ActiveTask::targetSpawned)
            )
            .apply(instance, GuildTaskBoardData.ActiveTask::new)
      );

      public ActiveTask {
         requiredCount = requiredCount > 0 ? requiredCount : -1;
         int maxProgress = GuildTaskDefinitions.get(taskId).map(GuildTaskDefinitions.GuildTaskDefinition::requiredKills).orElse(Integer.MAX_VALUE);
         if (requiredCount > 0) {
            maxProgress = requiredCount;
         }

         taskProgress = Mth.clamp(taskProgress, 0, maxProgress);
         taskStartedAt = Math.max(-1L, taskStartedAt);
         taskTimeLimitTicks = Math.max(0, taskTimeLimitTicks);
         taskStartedAtMillis = Math.max(-1L, taskStartedAtMillis);
         taskTimeLimitMillis = Math.max(0L, taskTimeLimitMillis);
         partyLeaderId = partyLeaderId == null ? Optional.empty() : partyLeaderId;
         targetX = targetX == null ? Optional.empty() : targetX;
         targetZ = targetZ == null ? Optional.empty() : targetZ;
         trackedEntityId = trackedEntityId == null ? Optional.empty() : trackedEntityId;
      }

      public static GuildTaskBoardData.ActiveTask create(ResourceLocation taskId, long gameTime, int timeLimitTicks) {
         return create(taskId, gameTime, timeLimitTicks, Optional.empty());
      }

      public static GuildTaskBoardData.ActiveTask create(ResourceLocation taskId, long gameTime, int timeLimitTicks, Optional<UUID> partyLeaderId) {
         return create(taskId, gameTime, timeLimitTicks, partyLeaderId, Optional.empty());
      }

      public static GuildTaskBoardData.ActiveTask create(
         ResourceLocation taskId, long gameTime, int timeLimitTicks, Optional<UUID> partyLeaderId, Optional<BlockPos> targetPos
      ) {
         return create(taskId, gameTime, timeLimitTicks, partyLeaderId, targetPos, -1);
      }

      public static GuildTaskBoardData.ActiveTask create(
         ResourceLocation taskId, long gameTime, int timeLimitTicks, Optional<UUID> partyLeaderId, Optional<BlockPos> targetPos, int requiredCount
      ) {
         return new GuildTaskBoardData.ActiveTask(
            taskId,
            0,
            Math.max(0L, gameTime),
            Math.max(0, timeLimitTicks),
            -1L,
            0L,
            partyLeaderId,
            targetPos.map(Vec3i::getX),
            targetPos.map(Vec3i::getZ),
            Optional.empty(),
            requiredCount,
            false
         );
      }

      public int requiredCountOrDefault(int fallback) {
         return this.requiredCount > 0 ? this.requiredCount : fallback;
      }

      private GuildTaskBoardData.ActiveTask addKillFor(
         ResourceLocation entityType,
         Optional<UUID> killedEntityId,
         ServerLevel level,
         BlockPos killPos,
         boolean partyReady,
         long currentGameTime,
         int fallbackTimeLimitTicks
      ) {
         if (this.isExpired(currentGameTime, fallbackTimeLimitTicks)) {
            return this;
         } else {
            Optional<GuildTaskDefinitions.GuildTaskDefinition> task = GuildTaskDefinitions.get(this.taskId);
            if (!task.isEmpty() && task.get().isKillTask() && (!task.get().partyOnly() || partyReady)) {
               int requiredKills = this.requiredCountOrDefault(task.get().requiredKills());
               return this.countsSpawnTargetKill(task.get(), killedEntityId, killPos)
                     && this.taskProgress < requiredKills
                     && task.get().countsKill(entityType, level, killPos)
                  ? new GuildTaskBoardData.ActiveTask(
                     this.taskId,
                     this.taskProgress + 1,
                     this.taskStartedAt,
                     this.taskTimeLimitTicks,
                     this.taskStartedAtMillis,
                     this.taskTimeLimitMillis,
                     this.partyLeaderId,
                     this.targetX,
                     this.targetZ,
                     this.trackedEntityId,
                     this.requiredCount,
                     this.targetSpawned
                  )
                  : this;
            } else {
               return this;
            }
         }
      }

      private boolean countsSpawnTargetKill(GuildTaskDefinitions.GuildTaskDefinition task, Optional<UUID> killedEntityId, BlockPos killPos) {
         if (!task.isSpawnTargetTask()) {
            return true;
         } else {
            return !task.isMultiSpawnTargetTask()
               ? this.trackedEntityId.filter(id -> killedEntityId != null && killedEntityId.filter(id::equals).isPresent()).isPresent()
               : this.targetSpawned && this.isNearTarget(killPos, Math.max(192, task.spawnTriggerDistance() + 128));
         }
      }

      private boolean isNearTarget(BlockPos pos, int distance) {
         if (pos != null && !this.targetX.isEmpty() && !this.targetZ.isEmpty()) {
            double dx = pos.getX() - (this.targetX.get().intValue() + 0.5);
            double dz = pos.getZ() - (this.targetZ.get().intValue() + 0.5);
            return dx * dx + dz * dz <= (double)distance * distance;
         } else {
            return false;
         }
      }

      private GuildTaskBoardData.ActiveTask addPartyKillFor(
         ResourceLocation entityType,
         Optional<UUID> killedEntityId,
         UUID partyLeaderId,
         ServerLevel level,
         BlockPos killPos,
         boolean partyReady,
         long currentGameTime,
         int fallbackTimeLimitTicks
      ) {
         return this.partyLeaderId.filter(partyLeaderId::equals).isEmpty()
            ? this
            : this.addKillFor(entityType, killedEntityId, level, killPos, partyReady, currentGameTime, fallbackTimeLimitTicks);
      }

      private GuildTaskBoardData.ActiveTask withTracking(BlockPos targetPos, Optional<UUID> trackedEntityId) {
         return new GuildTaskBoardData.ActiveTask(
            this.taskId,
            this.taskProgress,
            this.taskStartedAt,
            this.taskTimeLimitTicks,
            this.taskStartedAtMillis,
            this.taskTimeLimitMillis,
            this.partyLeaderId,
            targetPos == null ? this.targetX : Optional.of(targetPos.getX()),
            targetPos == null ? this.targetZ : Optional.of(targetPos.getZ()),
            trackedEntityId == null ? this.trackedEntityId : trackedEntityId,
            this.requiredCount,
            this.targetSpawned
         );
      }

      private GuildTaskBoardData.ActiveTask withTargetSpawned(BlockPos targetPos, boolean targetSpawned) {
         return new GuildTaskBoardData.ActiveTask(
            this.taskId,
            this.taskProgress,
            this.taskStartedAt,
            this.taskTimeLimitTicks,
            this.taskStartedAtMillis,
            this.taskTimeLimitMillis,
            this.partyLeaderId,
            targetPos == null ? this.targetX : Optional.of(targetPos.getX()),
            targetPos == null ? this.targetZ : Optional.of(targetPos.getZ()),
            this.trackedEntityId,
            this.requiredCount,
            targetSpawned
         );
      }

      private GuildTaskBoardData.ActiveTask markVisitStructureComplete(long currentGameTime, int fallbackTimeLimitTicks) {
         if (this.isExpired(currentGameTime, fallbackTimeLimitTicks)) {
            return this;
         }

         Optional<GuildTaskDefinitions.GuildTaskDefinition> task = GuildTaskDefinitions.get(this.taskId)
            .filter(GuildTaskDefinitions.GuildTaskDefinition::isVisitStructureTask);
         if (task.isEmpty()) {
            return this;
         }

         int required = this.requiredCountOrDefault(task.get().requiredCount());
         return this.taskProgress >= required
            ? this
            : new GuildTaskBoardData.ActiveTask(
               this.taskId,
               required,
               this.taskStartedAt,
               this.taskTimeLimitTicks,
               this.taskStartedAtMillis,
               this.taskTimeLimitMillis,
               this.partyLeaderId,
               this.targetX,
               this.targetZ,
               this.trackedEntityId,
               this.requiredCount,
               this.targetSpawned
            );
      }

      private boolean isExpired(long currentGameTime, int fallbackTimeLimitTicks) {
         int effectiveLimitTicks = this.taskTimeLimitTicks > 0 ? this.taskTimeLimitTicks : fallbackTimeLimitTicks;
         return effectiveLimitTicks > 0 && this.taskStartedAt >= 0L && currentGameTime - this.taskStartedAt > effectiveLimitTicks;
      }

      private long remainingTicks(long currentGameTime, int fallbackTimeLimitTicks) {
         int effectiveLimitTicks = this.taskTimeLimitTicks > 0 ? this.taskTimeLimitTicks : fallbackTimeLimitTicks;
         return GuildTaskTime.remainingTicks(currentGameTime, this.taskStartedAt, effectiveLimitTicks);
      }
   }

   private record PrimaryTaskOptions(int requiredCount, boolean targetSpawned) {
      private static final MapCodec<GuildTaskBoardData.PrimaryTaskOptions> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Codec.INT.optionalFieldOf("active_task_required_count", -1).forGetter(GuildTaskBoardData.PrimaryTaskOptions::requiredCount),
               Codec.BOOL.optionalFieldOf("active_task_target_spawned", false).forGetter(GuildTaskBoardData.PrimaryTaskOptions::targetSpawned)
            )
            .apply(instance, GuildTaskBoardData.PrimaryTaskOptions::new)
      );
   }
}
