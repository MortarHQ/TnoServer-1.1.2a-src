package com.tensura_guild.data;

import com.mojang.datafixers.util.Pair;
import com.tensura_guild.compat.GuildCompatHooks;
import com.tensura_guild.config.GuildConfig;
import com.tensura_guild.registry.GuildAttachments;
import com.tensura_guild.registry.GuildItems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;

public final class GuildTaskService {
   private static final double DROPPED_TASK_ITEM_CLEANUP_RADIUS = 128.0;
   private static final double TASK_FAILURE_EXPERIENCE_PENALTY = 0.1;
   private static final double EVOLVED_TRACKED_ENTITY_SEARCH_RADIUS = 128.0;
   private static final int SPAWN_CONTEXT_CACHE_BLOCK_SIZE = 256;
   private static final long SPAWN_CONTEXT_CACHE_TICKS = 600L;
   private static final int MAX_SPAWN_CONTEXT_CACHE_ENTRIES = 2048;
   private static final int VISIT_STRUCTURE_SEARCH_RADIUS_CHUNKS = 128;
   private static final int COMMISSION_MONSTER_COMPLETED_TASKS = 50;
   private static final String COMMISSION_MONSTER_TITLE = "&4委托怪物";
   private static final Map<GuildTaskService.SpawnContextCacheKey, GuildTaskService.SpawnContextCacheEntry> SPAWN_CONTEXT_CACHE = new HashMap<>();
   private static final ResourceLocation ORC_LORD_ENTITY = ResourceLocation.fromNamespaceAndPath("tensura", "orc_lord");
   private static final ResourceLocation ORC_DISASTER_ENTITY = ResourceLocation.fromNamespaceAndPath("tensura", "orc_disaster");

   private GuildTaskService() {
   }

   public static void syncToPlayer(ServerPlayer player) {
      syncTaskAvailabilityToPlayer(player);
      player.syncData(GuildAttachments.GUILD_TASK_BOARD);
   }

   public static void syncTaskAvailabilityToAll(MinecraftServer server) {
      reserveOnlineActiveTasks(server);

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         syncToPlayer(player);
      }
   }

   private static void reserveOnlineActiveTasks(MinecraftServer server) {
      GuildWorldData worldData = GuildWorldData.get(server);

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         for (GuildTaskBoardData.ActiveTask activeTask : ((GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD)).activeTasks()) {
            worldData.reserveTask(activeTask.taskId());
         }
      }
   }

   private static void syncTaskAvailabilityToPlayer(ServerPlayer player) {
      GuildTaskBoardData current = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);
      GuildWorldData worldData = GuildWorldData.get(player.server);

      for (GuildTaskBoardData.ActiveTask activeTask : current.activeTasks()) {
         worldData.reserveTask(activeTask.taskId());
      }

      GuildLevelData levelData = (GuildLevelData)player.getData(GuildAttachments.GUILD_LEVEL);
      GuildLevelData partyLevelData = GuildPartyService.isInParty(player) ? GuildPartyService.highestOnlinePartyLevel(player) : levelData;
      boolean hasRealParty = GuildPartyService.isInParty(player) && GuildPartyService.onlinePartyMemberCount(player) > 1;
      List<ResourceLocation> availableTasks = worldData.availableTasks()
         .stream()
         .filter(
            taskId -> GuildTaskDefinitions.get(taskId)
               .map(task -> canDisplayAvailableTask(player, taskId, task, levelData, partyLevelData, hasRealParty))
               .orElse(false)
         )
         .toList();
      GuildTaskBoardData updated = current.withAvailableTasks(
         availableTasks, worldData.availableTaskWithdrawAt(availableTasks, GuildConfig.commissionWithdrawTicks())
      );
      if (!updated.equals(current)) {
         player.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
      }
   }

   private static boolean canDisplayAvailableTask(
      ServerPlayer player,
      ResourceLocation taskId,
      GuildTaskDefinitions.GuildTaskDefinition task,
      GuildLevelData levelData,
      GuildLevelData partyLevelData,
      boolean hasRealParty
   ) {
      boolean unlocked = task.partyOnly() ? hasRealParty && task.isUnlockedFor(partyLevelData) : task.isUnlockedFor(levelData);
      return unlocked && hasCachedSpawnTargetContext(player, taskId, task);
   }

   public static boolean addTask(MinecraftServer server, ResourceLocation taskId) {
      boolean changed = GuildWorldData.get(server).addTask(taskId);
      if (changed) {
         syncTaskAvailabilityToAll(server);
      }

      return changed;
   }

   public static int refreshTasks(MinecraftServer server) {
      int count = GuildWorldData.get(server).refreshAvailableTasks(new Random());
      syncTaskAvailabilityToAll(server);
      return count;
   }

   public static boolean autoUpdateTasks(MinecraftServer server) {
      return autoUpdateTasks(server, GuildLevelData.rankForLevel(1));
   }

   public static boolean autoUpdateTasks(MinecraftServer server, String rank) {
      if (server.getPlayerList().getPlayerCount() <= 0) {
         return false;
      }

      Set<ResourceLocation> acceptedTasks = new HashSet<>();

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         for (GuildTaskBoardData.ActiveTask activeTask : ((GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD)).activeTasks()) {
            acceptedTasks.add(activeTask.taskId());
         }
      }

      GuildWorldData.TaskRefreshRange range = GuildWorldData.TaskRefreshRange.forRank(rank);
      int added = GuildWorldData.get(server)
         .addRandomAvailableTasks(new Random(), Math.min(GuildConfig.commissionBoardMaxTasks(), range.max()), acceptedTasks, rank, range.min(), range.max());
      if (added > 0) {
         syncTaskAvailabilityToAll(server);
      }

      return added > 0;
   }

   public static boolean expireAvailableTasks(MinecraftServer server) {
      if (server.getPlayerList().getPlayerCount() <= 0) {
         return false;
      }

      boolean changed = GuildWorldData.get(server).expireAvailableTasks(server.overworld().getGameTime(), GuildConfig.commissionWithdrawTicks());
      if (changed) {
         syncTaskAvailabilityToAll(server);
      }

      return changed;
   }

   public static boolean acceptTask(ServerPlayer player, ResourceLocation taskId, long gameTime) {
      expireAvailableTasks(player.server);
      if (!canAccessTask(player, taskId)) {
         syncToPlayer(player);
         return false;
      }

      if (GuildPartyService.isInParty(player)) {
         if (!GuildPartyService.isPartyLeader(player)) {
            syncToPlayer(player);
            return false;
         } else {
            return acceptPartyTask(player, taskId, gameTime);
         }
      } else {
         return acceptSoloTask(player, taskId, gameTime);
      }
   }

   private static boolean acceptSoloTask(ServerPlayer player, ResourceLocation taskId, long gameTime) {
      Optional<GuildTaskDefinitions.GuildTaskDefinition> task = GuildTaskDefinitions.get(taskId);
      if (task.map(GuildTaskDefinitions.GuildTaskDefinition::partyOnly).orElse(false)) {
         syncToPlayer(player);
         return false;
      } else if (!canAcceptTask(player, taskId)) {
         syncToPlayer(player);
         return false;
      } else {
         Optional<BlockPos> targetPos = task.flatMap(value -> generateTaskTarget(player, value));
         if (task.map(GuildTaskService::requiresGeneratedTarget).orElse(false) && targetPos.isEmpty()) {
            syncToPlayer(player);
            return false;
         } else if (!GuildWorldData.get(player.server).consumeTask(taskId)) {
            syncToPlayer(player);
            return false;
         } else {
            GuildTaskBoardData current = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);
            int requiredCount = generatedRequiredCount(task);
            GuildTaskBoardData updated = current.acceptTask(taskId, gameTime, taskTimeLimitTicks(task), Optional.empty(), targetPos, requiredCount);
            if (!updated.equals(current)) {
               applyAcceptedTask(player, current, updated, taskId);
               syncTaskAvailabilityToAll(player.server);
               return true;
            } else {
               syncTaskAvailabilityToAll(player.server);
               return false;
            }
         }
      }
   }

   private static boolean acceptPartyTask(ServerPlayer leader, ResourceLocation taskId, long gameTime) {
      List<ServerPlayer> members = GuildPartyService.onlinePartyMembers(leader);
      Optional<GuildTaskDefinitions.GuildTaskDefinition> task = GuildTaskDefinitions.get(taskId);
      if (!members.isEmpty() && !task.isEmpty() && (!task.get().partyOnly() || members.size() > 1)) {
         for (ServerPlayer member : members) {
            if (!canAcceptTask(member, taskId)) {
               syncToPlayer(leader);
               return false;
            }
         }

         int timeLimitTicks = taskTimeLimitTicks(task);
         Optional<BlockPos> targetPos = task.flatMap(value -> generateTaskTarget(leader, value));
         if (task.map(GuildTaskService::requiresGeneratedTarget).orElse(false) && targetPos.isEmpty()) {
            syncToPlayer(leader);
            return false;
         }

         if (!GuildWorldData.get(leader.server).consumeTask(taskId)) {
            syncToPlayer(leader);
            return false;
         }

         int requiredCount = generatedRequiredCount(task);

         for (ServerPlayer member : members) {
            GuildTaskBoardData current = (GuildTaskBoardData)member.getData(GuildAttachments.GUILD_TASK_BOARD);
            GuildTaskBoardData updated = current.acceptTask(taskId, gameTime, timeLimitTicks, Optional.of(leader.getUUID()), targetPos, requiredCount);
            applyAcceptedTask(member, current, updated, taskId);
         }

         syncTaskAvailabilityToAll(leader.server);
         return true;
      } else {
         syncToPlayer(leader);
         return false;
      }
   }

   private static boolean canAcceptTask(ServerPlayer player, ResourceLocation taskId) {
      GuildTaskBoardData current = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);
      return current.activeTaskCount() < maxAcceptedTasks(player) && !current.hasAcceptedTask(taskId);
   }

   private static boolean canAccessTask(ServerPlayer player, ResourceLocation taskId) {
      GuildLevelData levelData = (GuildLevelData)player.getData(GuildAttachments.GUILD_LEVEL);
      return GuildTaskDefinitions.get(taskId)
         .map(
            task -> {
               if (!hasSpawnTargetContext(player, task)) {
                  return false;
               } else {
                  return !task.partyOnly()
                     ? task.isUnlockedFor(levelData)
                     : GuildPartyService.isInParty(player)
                        && GuildPartyService.onlinePartyMemberCount(player) > 1
                        && task.isUnlockedFor(GuildPartyService.highestOnlinePartyLevel(player));
               }
            }
         )
         .orElse(false);
   }

   private static boolean hasSpawnTargetContext(ServerPlayer player, GuildTaskDefinitions.GuildTaskDefinition task) {
      return task.isSpawnTargetTask() && !task.spawnBiomes().isEmpty()
         ? findClosestAllowedBiome(player.serverLevel(), player.blockPosition(), task, task.spawnRadius()).isPresent()
         : true;
   }

   private static boolean hasCachedSpawnTargetContext(ServerPlayer player, ResourceLocation taskId, GuildTaskDefinitions.GuildTaskDefinition task) {
      if (task.isSpawnTargetTask() && !task.spawnBiomes().isEmpty()) {
         ServerLevel level = player.serverLevel();
         long now = level.getGameTime();
         GuildTaskService.SpawnContextCacheKey key = GuildTaskService.SpawnContextCacheKey.of(level, taskId, player.blockPosition(), task.spawnRadius());
         GuildTaskService.SpawnContextCacheEntry cached = SPAWN_CONTEXT_CACHE.get(key);
         if (cached != null && now - cached.gameTime() <= 600L) {
            return cached.hasContext();
         }

         boolean hasContext = hasSpawnTargetContext(player, task);
         putSpawnContextCache(key, new GuildTaskService.SpawnContextCacheEntry(hasContext, now), now);
         return hasContext;
      } else {
         return true;
      }
   }

   private static void putSpawnContextCache(GuildTaskService.SpawnContextCacheKey key, GuildTaskService.SpawnContextCacheEntry entry, long now) {
      if (SPAWN_CONTEXT_CACHE.size() >= 2048) {
         SPAWN_CONTEXT_CACHE.entrySet().removeIf(value -> now - value.getValue().gameTime() > 600L);
      }

      while (SPAWN_CONTEXT_CACHE.size() >= 2048) {
         Iterator<GuildTaskService.SpawnContextCacheKey> iterator = SPAWN_CONTEXT_CACHE.keySet().iterator();
         if (!iterator.hasNext()) {
            break;
         }

         iterator.next();
         iterator.remove();
      }

      SPAWN_CONTEXT_CACHE.put(key, entry);
   }

   private static void applyAcceptedTask(ServerPlayer player, GuildTaskBoardData current, GuildTaskBoardData updated, ResourceLocation taskId) {
      if (!updated.equals(current)) {
         player.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
         player.syncData(GuildAttachments.GUILD_TASK_BOARD);
         player.setData(GuildAttachments.GUILD_MEMBER, ((GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER)).withAcceptedTask());
         player.syncData(GuildAttachments.GUILD_MEMBER);
         updated.activeTask(taskId).ifPresent(task -> giveTaskItem(player, task));
      }
   }

   public static boolean addKill(ServerPlayer killer, ResourceLocation entityType, BlockPos killPos) {
      return addKill(killer, entityType, Optional.empty(), killPos);
   }

   private static boolean addKill(ServerPlayer killer, ResourceLocation entityType, Optional<UUID> killedEntityId, BlockPos killPos) {
      expireTasks(killer);
      BlockPos progressPos = killPos == null ? killer.blockPosition() : killPos;
      boolean partyReady = GuildPartyService.partyLeaderId(killer)
         .map(leaderId -> GuildPartyService.onlinePartyMembers(killer.server, leaderId).size() > 1)
         .orElse(false);
      GuildTaskBoardData current = (GuildTaskBoardData)killer.getData(GuildAttachments.GUILD_TASK_BOARD);
      GuildTaskBoardData updated = current.addKillFor(
         entityType, killedEntityId, killer.serverLevel(), progressPos, partyReady, killer.serverLevel().getGameTime(), GuildConfig.commissionTimeLimitTicks()
      );
      if (!updated.equals(current)) {
         killer.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
         killer.syncData(GuildAttachments.GUILD_TASK_BOARD);
         updateTaskItems(killer, updated.activeTasks());
         syncPartyKillProgress(killer, entityType, killedEntityId, progressPos);
         return true;
      } else {
         syncPartyKillProgress(killer, entityType, killedEntityId, progressPos);
         return false;
      }
   }

   private static void syncPartyKillProgress(ServerPlayer killer, ResourceLocation entityType, BlockPos killPos) {
      syncPartyKillProgress(killer, entityType, Optional.empty(), killPos);
   }

   private static void syncPartyKillProgress(ServerPlayer killer, ResourceLocation entityType, Optional<UUID> killedEntityId, BlockPos killPos) {
      Optional<UUID> leaderId = GuildPartyService.partyLeaderId(killer);
      if (!leaderId.isEmpty()) {
         boolean partyReady = GuildPartyService.onlinePartyMembers(killer.server, leaderId.get()).size() > 1;

         for (ServerPlayer member : GuildPartyService.onlinePartyMembers(killer.server, leaderId.get())) {
            if (!member.getUUID().equals(killer.getUUID())) {
               expireTasks(member);
               GuildTaskBoardData current = (GuildTaskBoardData)member.getData(GuildAttachments.GUILD_TASK_BOARD);
               GuildTaskBoardData updated = current.addPartyKillFor(
                  entityType,
                  killedEntityId,
                  leaderId.get(),
                  killer.serverLevel(),
                  killPos,
                  partyReady,
                  member.serverLevel().getGameTime(),
                  GuildConfig.commissionTimeLimitTicks()
               );
               if (!updated.equals(current)) {
                  member.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
                  member.syncData(GuildAttachments.GUILD_TASK_BOARD);
                  updateTaskItems(member, updated.activeTasks());
               }
            }
         }
      }
   }

   public static boolean expireTasks(ServerPlayer player) {
      GuildTaskBoardData current = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);
      if (!current.hasActiveTask()) {
         return false;
      }

      GuildTaskBoardData updated = current.failExpiredTasks(player.serverLevel().getGameTime(), GuildConfig.commissionTimeLimitTicks());
      if (updated.equals(current)) {
         return false;
      }

      List<ResourceLocation> expiredTasks = new ArrayList<>();

      for (GuildTaskBoardData.ActiveTask activeTask : current.activeTasks()) {
         if (!updated.hasAcceptedTask(activeTask.taskId())) {
            expiredTasks.add(activeTask.taskId());
         }
      }

      for (ResourceLocation taskId : expiredTasks) {
         current.activeTask(taskId).ifPresent(activeTask -> removeTrackedEntity(player.server, activeTask));
         removeTaskItems(player, taskId);
         removeDroppedTaskItems(player, taskId);
         GuildWorldData.get(player.server).releaseTask(taskId);
      }

      player.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
      player.syncData(GuildAttachments.GUILD_TASK_BOARD);
      player.setData(GuildAttachments.GUILD_MEMBER, ((GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER)).withFailedTasks(expiredTasks.size()));
      player.syncData(GuildAttachments.GUILD_MEMBER);
      applyTaskFailurePenalty(player, expiredTasks.size());
      return true;
   }

   public static boolean dismissFailedTask(ServerPlayer player, ResourceLocation taskId) {
      GuildTaskBoardData current = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);
      GuildTaskBoardData updated = current.dismissFailedTask(taskId);
      if (updated.equals(current)) {
         return false;
      }

      player.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
      player.syncData(GuildAttachments.GUILD_TASK_BOARD);
      return true;
   }

   public static boolean completeTask(ServerPlayer player, ResourceLocation taskId) {
      expireTasks(player);
      GuildTaskBoardData current = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);
      Optional<GuildTaskBoardData.ActiveTask> activeTask = current.activeTask(taskId);
      Optional<GuildTaskDefinitions.GuildTaskDefinition> task = activeTask.map(GuildTaskBoardData.ActiveTask::taskId).flatMap(GuildTaskDefinitions::get);
      if (activeTask.isEmpty()
         || task.isEmpty()
         || current.isTaskExpired(taskId, player.serverLevel().getGameTime(), GuildConfig.commissionTimeLimitTicks())
         || !canCompleteTask(player, current, taskId, task.get())) {
         return false;
      }

      if (activeTask.get().partyLeaderId().isPresent()) {
         return completePartyTask(player, taskId, activeTask.get(), task.get(), current);
      }

      GuildTaskBoardData updated = current.completeTask(taskId);
      if (updated.equals(current)) {
         return false;
      }

      GuildTaskRewardPenalty rewardPenalty = GuildTaskRewardPenalty.forPlayer(player);
      consumeRequiredItems(player, task.get());
      giveTaskReward(player, task.get(), rewardPenalty);
      giveTaskExperience(player, task.get(), rewardPenalty);
      GuildReputationShopService.awardGuildTaskCoins(player, task.get().rank(), rewardPenalty);
      removeTaskItems(player, taskId);
      removeDroppedTaskItems(player, taskId);
      GuildWorldData.get(player.server).releaseTask(taskId);
      player.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
      player.syncData(GuildAttachments.GUILD_TASK_BOARD);
      recordCompletedCommission(player);
      GuildAdvancementService.grantGuildRookie(player);
      GuildCompatHooks.onGuildTaskCompleted(player, task.get().rank());
      return true;
   }

   private static boolean completePartyTask(
      ServerPlayer submitter,
      ResourceLocation taskId,
      GuildTaskBoardData.ActiveTask activeTask,
      GuildTaskDefinitions.GuildTaskDefinition task,
      GuildTaskBoardData submitterData
   ) {
      UUID leaderId = activeTask.partyLeaderId().orElse(null);
      if (leaderId != null && !GuildPartyService.partyLeaderId(submitter).filter(leaderId::equals).isEmpty()) {
         List<ServerPlayer> participants = GuildPartyService.onlinePartyMembers(submitter.server, leaderId)
            .stream()
            .filter(
               memberx -> ((GuildTaskBoardData)memberx.getData(GuildAttachments.GUILD_TASK_BOARD))
                  .activeTask(taskId)
                  .flatMap(GuildTaskBoardData.ActiveTask::partyLeaderId)
                  .filter(leaderId::equals)
                  .isPresent()
            )
            .toList();
         if (participants.isEmpty()) {
            return false;
         }

         if (task.partyOnly() && participants.size() <= 1) {
            return false;
         }

         Map<UUID, GuildTaskRewardPenalty> rewardPenalties = new HashMap<>();

         for (ServerPlayer participant : participants) {
            rewardPenalties.put(participant.getUUID(), GuildTaskRewardPenalty.forPlayer(participant));
         }

         consumeRequiredItems(submitter, task);
         giveTaskRewardSplit(submitter.server, participants, leaderId, task, rewardPenalties);
         giveTaskExperienceSplit(submitter.server, participants, leaderId, task, rewardPenalties);

         for (ServerPlayer member : participants) {
            GuildTaskBoardData current = member.getUUID().equals(submitter.getUUID())
               ? submitterData
               : (GuildTaskBoardData)member.getData(GuildAttachments.GUILD_TASK_BOARD);
            GuildTaskBoardData updated = current.completeTask(taskId);
            if (!updated.equals(current)) {
               removeTaskItems(member, taskId);
               removeDroppedTaskItems(member, taskId);
               member.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
               member.syncData(GuildAttachments.GUILD_TASK_BOARD);
               recordCompletedCommission(member);
               GuildTaskRewardPenalty rewardPenalty = rewardPenalties.getOrDefault(member.getUUID(), GuildTaskRewardPenalty.NONE);
               GuildReputationShopService.awardGuildTaskCoins(member, task.rank(), rewardPenalty);
               GuildAdvancementService.grantGuildRookie(member);
               GuildCompatHooks.onGuildTaskCompleted(member, task.rank());
            }
         }

         GuildWorldData.get(submitter.server).releaseTask(taskId);
         return true;
      } else {
         return false;
      }
   }

   private static void recordCompletedCommission(ServerPlayer player) {
      GuildMemberData updated = ((GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER)).withCompletedTask();
      player.setData(GuildAttachments.GUILD_MEMBER, updated);
      player.syncData(GuildAttachments.GUILD_MEMBER);
      GuildMemberService.syncPublicData(player, updated);
      grantCommissionMonsterTitle(player, updated);
   }

   private static void grantCommissionMonsterTitle(ServerPlayer player, GuildMemberData memberData) {
      if (memberData.completedTasks() > 50 && !((GuildTitleData)player.getData(GuildAttachments.GUILD_TITLES)).contains("&4委托怪物")) {
         GuildMemberService.grantTitle(player, "&4委托怪物");
         Component message = Component.translatable(
            "message.tensura_guild.title.commission_monster", new Object[]{player.getName(), GuildTitleFormatter.format("&4委托怪物")}
         );

         for (ServerPlayer onlinePlayer : player.server.getPlayerList().getPlayers()) {
            onlinePlayer.sendSystemMessage(message);
         }
      }
   }

   private static boolean canCompleteTask(ServerPlayer player, GuildTaskBoardData data, ResourceLocation taskId, GuildTaskDefinitions.GuildTaskDefinition task) {
      return !task.isKillTask() && !task.isVisitStructureTask()
         ? task.isItemTask() && countRequiredItems(player, task) >= task.requiredCount()
         : data.isTaskComplete(taskId);
   }

   private static void giveTaskItem(ServerPlayer player, GuildTaskBoardData.ActiveTask data) {
      ItemStack task = new ItemStack((ItemLike)GuildItems.GUILD_TASK.get());
      CompoundTag tag = new CompoundTag();
      tag.putString("TaskId", data.taskId().toString());
      tag.putUUID("Owner", player.getUUID());
      data.partyLeaderId().ifPresent(leaderId -> {
         tag.putUUID("PartyLeader", leaderId);
         GuildPartyService.partyName(player.server, leaderId).ifPresent(partyName -> tag.putString("PartyName", partyName));
      });
      tag.putLong("TaskStartedAt", data.taskStartedAt());
      tag.putInt("TaskTimeLimitTicks", data.taskTimeLimitTicks());
      data.targetX().ifPresent(value -> tag.putInt("TargetX", value));
      data.targetZ().ifPresent(value -> tag.putInt("TargetZ", value));
      data.trackedEntityId().ifPresent(value -> tag.putUUID("TrackedEntity", value));
      if (data.requiredCount() > 0) {
         tag.putInt("RequiredCount", data.requiredCount());
      }

      tag.putBoolean("TaskComplete", isActiveTaskComplete(data));
      task.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
      if (!player.getInventory().add(task)) {
         player.drop(task, false);
      }
   }

   public static void invalidatePartyTasks(ServerPlayer player, UUID partyLeaderId) {
      GuildTaskBoardData current = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);
      List<ResourceLocation> taskIds = current.partyTaskIds(partyLeaderId);
      if (!taskIds.isEmpty()) {
         GuildTaskBoardData updated = current.removePartyTasks(partyLeaderId);

         for (ResourceLocation taskId : taskIds) {
            invalidateTaskItems(player, taskId);
            invalidateDroppedTaskItems(player, taskId);
         }

         player.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
         player.syncData(GuildAttachments.GUILD_TASK_BOARD);
         player.setData(GuildAttachments.GUILD_MEMBER, ((GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER)).withRemovedActiveTasks(taskIds.size()));
         player.syncData(GuildAttachments.GUILD_MEMBER);

         for (ResourceLocation taskId : taskIds) {
            releaseInvalidatedPartyTaskIfUnused(player, partyLeaderId, taskId);
         }
      }
   }

   public static void invalidateOtherPartyTasks(ServerPlayer player, Optional<UUID> validPartyLeaderId) {
      GuildTaskBoardData current = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);

      for (UUID partyLeaderId : current.partyLeaderIds()) {
         if (!validPartyLeaderId.filter(partyLeaderId::equals).isPresent()) {
            invalidatePartyTasks(player, partyLeaderId);
         }
      }
   }

   private static void releaseInvalidatedPartyTaskIfUnused(ServerPlayer player, UUID partyLeaderId, ResourceLocation taskId) {
      if (!GuildPartyService.partyExists(player.server, partyLeaderId) && !isPartyTaskHeldByOnlinePlayer(player.server, partyLeaderId, taskId)) {
         GuildWorldData.get(player.server).releaseTask(taskId);
      }
   }

   private static boolean isPartyTaskHeldByOnlinePlayer(MinecraftServer server, UUID partyLeaderId, ResourceLocation taskId) {
      for (ServerPlayer onlinePlayer : server.getPlayerList().getPlayers()) {
         if (((GuildTaskBoardData)onlinePlayer.getData(GuildAttachments.GUILD_TASK_BOARD))
            .activeTask(taskId)
            .flatMap(GuildTaskBoardData.ActiveTask::partyLeaderId)
            .filter(partyLeaderId::equals)
            .isPresent()) {
            return true;
         }
      }

      return false;
   }

   private static void removeTaskItems(ServerPlayer player, ResourceLocation taskId) {
      for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
         ItemStack stack = player.getInventory().getItem(slot);
         if (stack.is(GuildItems.GUILD_TASK.get()) && isTaskItem(stack, taskId)) {
            stack.shrink(stack.getCount());
         }
      }

      player.getInventory().setChanged();
   }

   private static void invalidateTaskItems(ServerPlayer player, ResourceLocation taskId) {
      for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
         ItemStack stack = player.getInventory().getItem(slot);
         if (stack.is(GuildItems.GUILD_TASK.get()) && isTaskItem(stack, taskId)) {
            markTaskItemInvalid(stack);
         }
      }

      player.getInventory().setChanged();
   }

   private static void removeDroppedTaskItems(ServerPlayer player, ResourceLocation taskId) {
      for (ItemEntity item : player.serverLevel()
         .getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(128.0), itemx -> isTaskItem(itemx.getItem(), player.getUUID(), taskId))) {
         item.discard();
      }
   }

   private static void invalidateDroppedTaskItems(ServerPlayer player, ResourceLocation taskId) {
      for (ItemEntity item : player.serverLevel()
         .getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(128.0), itemx -> isTaskItem(itemx.getItem(), player.getUUID(), taskId))) {
         markTaskItemInvalid(item.getItem());
      }
   }

   private static void markTaskItemInvalid(ItemStack stack) {
      CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag tag = customData.copyTag();
      tag.putBoolean("Invalid", true);
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
   }

   private static boolean isTaskItem(ItemStack stack, ResourceLocation taskId) {
      CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag tag = customData.copyTag();
      return tag.contains("TaskId") && taskId.toString().equals(tag.getString("TaskId"));
   }

   private static boolean isTaskItem(ItemStack stack, UUID owner, ResourceLocation taskId) {
      CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag tag = customData.copyTag();
      return tag.contains("Owner") && owner.equals(tag.getUUID("Owner")) && tag.contains("TaskId") && taskId.toString().equals(tag.getString("TaskId"));
   }

   private static void giveTaskReward(ServerPlayer player, GuildTaskDefinitions.GuildTaskDefinition task, GuildTaskRewardPenalty penalty) {
      Item rewardItem = (Item)BuiltInRegistries.ITEM.get(task.rewardItem());
      if (rewardItem != Items.AIR) {
         giveItem(player, rewardItem, adjustedRewardCount(task.rewardItem(), task.rewardCount(), penalty));
      }
   }

   private static void giveTaskRewardSplit(
      MinecraftServer server,
      List<ServerPlayer> participants,
      UUID leaderId,
      GuildTaskDefinitions.GuildTaskDefinition task,
      Map<UUID, GuildTaskRewardPenalty> rewardPenalties
   ) {
      Item rewardItem = (Item)BuiltInRegistries.ITEM.get(task.rewardItem());
      if (rewardItem != Items.AIR && !participants.isEmpty() && task.rewardCount() > 0) {
         if (!task.rewardPerMember()) {
            int share = task.rewardCount() / participants.size();
            int remainder = task.rewardCount() % participants.size();
            ServerPlayer leader = server.getPlayerList().getPlayer(leaderId);

            for (ServerPlayer participant : participants) {
               int amount = share;
               if (leader != null && participant.getUUID().equals(leader.getUUID())) {
                  amount += remainder;
               }

               giveItem(
                  participant,
                  rewardItem,
                  adjustedRewardCount(task.rewardItem(), amount, rewardPenalties.getOrDefault(participant.getUUID(), GuildTaskRewardPenalty.NONE))
               );
            }
         } else {
            for (ServerPlayer participant : participants) {
               giveItem(
                  participant,
                  rewardItem,
                  adjustedRewardCount(task.rewardItem(), task.rewardCount(), rewardPenalties.getOrDefault(participant.getUUID(), GuildTaskRewardPenalty.NONE))
               );
            }
         }
      }
   }

   private static int adjustedRewardCount(ResourceLocation itemId, int amount, GuildTaskRewardPenalty penalty) {
      return (penalty == null ? GuildTaskRewardPenalty.NONE : penalty).adjustItemRewardAmount(itemId, amount);
   }

   private static void giveItem(ServerPlayer player, Item item, int amount) {
      if (amount > 0) {
         ItemStack reward = new ItemStack(item, amount);
         if (!player.getInventory().add(reward)) {
            player.drop(reward, false);
         }
      }
   }

   private static int countRequiredItems(ServerPlayer player, GuildTaskDefinitions.GuildTaskDefinition task) {
      int count = 0;

      for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
         ItemStack stack = player.getInventory().getItem(slot);
         if (matchesRequiredItem(stack, task)) {
            count += stack.getCount();
         }
      }

      return count;
   }

   private static void consumeRequiredItems(ServerPlayer player, GuildTaskDefinitions.GuildTaskDefinition task) {
      if (task.isItemTask()) {
         int remaining = task.requiredCount();

         for (int slot = 0; slot < player.getInventory().getContainerSize() && remaining > 0; slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (matchesRequiredItem(stack, task)) {
               int removed = Math.min(remaining, stack.getCount());
               stack.shrink(removed);
               remaining -= removed;
            }
         }

         player.getInventory().setChanged();
      }
   }

   private static boolean matchesRequiredItem(ItemStack stack, GuildTaskDefinitions.GuildTaskDefinition task) {
      if (stack.isEmpty()) {
         return false;
      }

      if (task.requiredItem().isPresent()) {
         Item item = (Item)BuiltInRegistries.ITEM.get(task.requiredItem().get());
         if (item != Items.AIR && stack.is(item)) {
            return true;
         }
      }

      if (task.requiredItemTag().isPresent()) {
         TagKey<Item> tag = TagKey.create(Registries.ITEM, task.requiredItemTag().get());
         return stack.is(tag);
      } else {
         return false;
      }
   }

   private static void giveTaskExperience(ServerPlayer player, GuildTaskDefinitions.GuildTaskDefinition task, GuildTaskRewardPenalty penalty) {
      if (task.experienceReward() > 0) {
         giveExperience(player, adjustedExperience(task.experienceReward(), penalty));
      }
   }

   private static void giveTaskExperienceSplit(
      MinecraftServer server,
      List<ServerPlayer> participants,
      UUID leaderId,
      GuildTaskDefinitions.GuildTaskDefinition task,
      Map<UUID, GuildTaskRewardPenalty> rewardPenalties
   ) {
      if (!participants.isEmpty() && task.experienceReward() > 0) {
         int share = task.experienceReward() / participants.size();
         int remainder = task.experienceReward() % participants.size();
         ServerPlayer leader = server.getPlayerList().getPlayer(leaderId);

         for (ServerPlayer participant : participants) {
            GuildTaskRewardPenalty penalty = rewardPenalties.getOrDefault(participant.getUUID(), GuildTaskRewardPenalty.NONE);
            if (task.experiencePerMember()) {
               giveExperience(participant, adjustedExperience(task.experienceReward(), penalty));
            } else {
               int amount = share;
               if (leader != null && participant.getUUID().equals(leader.getUUID())) {
                  amount += remainder;
               }

               giveExperience(participant, adjustedExperience(amount, penalty));
            }
         }
      }
   }

   private static int adjustedExperience(int amount, GuildTaskRewardPenalty penalty) {
      return (penalty == null ? GuildTaskRewardPenalty.NONE : penalty).adjustExperienceAmount(amount);
   }

   private static void giveExperience(ServerPlayer player, int amount) {
      if (amount > 0) {
         player.setData(GuildAttachments.GUILD_LEVEL, ((GuildLevelData)player.getData(GuildAttachments.GUILD_LEVEL)).addExperience(amount));
         player.syncData(GuildAttachments.GUILD_LEVEL);
         syncToPlayer(player);
         GuildCompatHooks.onAdventureExperienceChanged(player);
      }
   }

   public static int maxAcceptedTasks(ServerPlayer player) {
      return Math.max(2, ((GuildLevelData)player.getData(GuildAttachments.GUILD_LEVEL)).levelIndex() + 1);
   }

   public static void tickActiveTasks(ServerPlayer player) {
      expireTasks(player);
      GuildTaskBoardData data = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);

      for (GuildTaskBoardData.ActiveTask activeTask : data.activeTasks()) {
         GuildTaskDefinitions.GuildTaskDefinition task = GuildTaskDefinitions.get(activeTask.taskId()).orElse(null);
         if (task != null && !data.isTaskComplete(activeTask.taskId())) {
            if (task.isVisitStructureTask()) {
               tickVisitStructureTask(player, activeTask, task);
            } else if (task.isSpawnTargetTask()) {
               tickSpawnTargetTask(player, activeTask, task);
            }
         }
      }
   }

   private static void tickVisitStructureTask(ServerPlayer player, GuildTaskBoardData.ActiveTask activeTask, GuildTaskDefinitions.GuildTaskDefinition task) {
      if (isNearVisitStructure(player, activeTask, task)) {
         markVisitStructureComplete(player.server, player, activeTask.taskId(), activeTask.partyLeaderId());
      }
   }

   private static boolean isNearVisitStructure(ServerPlayer player, GuildTaskBoardData.ActiveTask activeTask, GuildTaskDefinitions.GuildTaskDefinition task) {
      int radius = Math.max(1, task.visitRadius());
      if (isNearTaskTarget(player, activeTask, radius)) {
         return true;
      }

      TagKey<Structure> tag = TagKey.create(Registries.STRUCTURE, task.visitStructureTag().orElseThrow());
      int searchRadius = Math.max(1, (int)Math.ceil(radius / 16.0) + 2);
      BlockPos nearest = player.serverLevel().findNearestMapStructure(tag, player.blockPosition(), searchRadius, false);
      return nearest != null && horizontalDistanceSqr(nearest, player.blockPosition()) <= (double)radius * radius;
   }

   private static void markVisitStructureComplete(MinecraftServer server, ServerPlayer visitor, ResourceLocation taskId, Optional<UUID> partyLeaderId) {
      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         if (!partyLeaderId.isEmpty() || player.getUUID().equals(visitor.getUUID())) {
            GuildTaskBoardData current = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);
            if (!current.activeTask(taskId).filter(task -> samePartyTask(task.partyLeaderId(), partyLeaderId)).isEmpty()) {
               GuildTaskBoardData updated = current.markVisitStructureComplete(
                  taskId, player.serverLevel().getGameTime(), GuildConfig.commissionTimeLimitTicks()
               );
               if (!updated.equals(current)) {
                  player.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
                  player.syncData(GuildAttachments.GUILD_TASK_BOARD);
                  updated.activeTask(taskId).ifPresent(task -> updateTaskItems(player, task));
               }
            }
         }
      }
   }

   public static boolean handleTrackedTaskEntityDeath(Entity entity, Entity sourceEntity) {
      if (entity != null && !entity.level().isClientSide() && entity.level().getServer() != null) {
         UUID entityId = entity.getUUID();
         List<GuildTaskService.TrackedTaskHolder> holders = trackedTaskHolders(entity.level().getServer(), entityId);
         if (holders.isEmpty()) {
            return false;
         } else if (holders.stream().anyMatch(holder -> !isTrackedEntityValidForTask(entity, holder.task()))) {
            failTrackedTaskHolders(entity.level().getServer(), holders);
            return true;
         } else if (sourceEntity instanceof ServerPlayer killer && isAuthorizedTrackedTaskKiller(killer, entityId)) {
            ResourceLocation entityType = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
            addKill(killer, entityType, Optional.of(entityId), entity.blockPosition());
            return true;
         } else {
            failTrackedTaskHolders(entity.level().getServer(), holders);
            return true;
         }
      } else {
         return false;
      }
   }

   private static void tickSpawnTargetTask(ServerPlayer player, GuildTaskBoardData.ActiveTask activeTask, GuildTaskDefinitions.GuildTaskDefinition task) {
      if (activeTask.trackedEntityId().isPresent()) {
         Entity entity = findEntity(player.server, activeTask.trackedEntityId().get());
         if (entity == null || !entity.isAlive() || entity.isRemoved()) {
            if (isNearTaskTarget(player, activeTask, Math.max(task.spawnTriggerDistance(), 128))) {
               discardEvolvedTrackedEntity(player.server, activeTask);
               BlockPos target = taskTarget(activeTask).orElse(player.blockPosition());
               syncTrackedTask(player.server, activeTask.taskId(), activeTask.partyLeaderId(), target, Optional.empty());
            }
         } else if (!isTrackedEntityValidForTask(entity, activeTask)) {
            failTaskGroup(player.server, activeTask.taskId(), activeTask.partyLeaderId());
         } else {
            syncTrackedTask(player.server, activeTask.taskId(), activeTask.partyLeaderId(), entity.blockPosition(), Optional.of(entity.getUUID()));
         }
      } else if (isNearTaskTarget(player, activeTask, task.spawnTriggerDistance())) {
         BlockPos target = taskTarget(activeTask).orElse(player.blockPosition());
         if (task.isMultiSpawnTargetTask()) {
            if (!activeTask.targetSpawned()) {
               if (!task.targetEntity().flatMap(BuiltInRegistries.ENTITY_TYPE::getOptional).isEmpty()) {
                  int spawnCount = Math.max(1, activeTask.requiredCountOrDefault(task.requiredKills()));
                  if (spawnTargetGroup(player.serverLevel(), task, target, spawnCount)) {
                     syncTargetSpawnedTask(player.server, activeTask.taskId(), activeTask.partyLeaderId(), target);
                  }
               }
            }
         } else {
            Optional<BlockPos> spawnPos = findNearbyGroundPosition(player.serverLevel(), target, 128, 8, task);
            if (!spawnPos.isEmpty()) {
               Entity spawned = spawnTrackedEntity(player.serverLevel(), task, spawnPos.get());
               if (spawned != null) {
                  syncTrackedTask(player.server, activeTask.taskId(), activeTask.partyLeaderId(), spawned.blockPosition(), Optional.of(spawned.getUUID()));
               }
            }
         }
      }
   }

   private static Entity spawnTrackedEntity(ServerLevel level, GuildTaskDefinitions.GuildTaskDefinition task, BlockPos spawnPos) {
      Optional<EntityType<?>> type = task.targetEntity().flatMap(BuiltInRegistries.ENTITY_TYPE::getOptional);
      if (type.isEmpty()) {
         return null;
      }

      Entity entity = type.get().create(level);
      if (entity == null) {
         return null;
      }

      entity.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, level.random.nextFloat() * 360.0F, 0.0F);
      if (entity instanceof Mob mob) {
         mob.setPersistenceRequired();
         mob.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), MobSpawnType.TRIGGERED, null);
      }

      return level.addFreshEntity(entity) ? entity : null;
   }

   private static boolean spawnTargetGroup(ServerLevel level, GuildTaskDefinitions.GuildTaskDefinition task, BlockPos target, int count) {
      List<Entity> spawnedEntities = new ArrayList<>();
      Set<BlockPos> usedPositions = new HashSet<>();

      for (int i = 0; i < count; i++) {
         Optional<BlockPos> spawnPos = findRandomNearbyGroundPosition(level, target, 128, task, usedPositions);
         if (spawnPos.isEmpty()) {
            spawnedEntities.forEach(Entity::discard);
            return false;
         }

         Entity spawned = spawnTrackedEntity(level, task, spawnPos.get());
         if (spawned == null) {
            spawnedEntities.forEach(Entity::discard);
            return false;
         }

         spawnedEntities.add(spawned);
         usedPositions.add(spawnPos.get());
      }

      return true;
   }

   private static boolean requiresGeneratedTarget(GuildTaskDefinitions.GuildTaskDefinition task) {
      return task.isVisitStructureTask() || task.isSpawnTargetTask();
   }

   private static Optional<BlockPos> generateTaskTarget(ServerPlayer player, GuildTaskDefinitions.GuildTaskDefinition task) {
      if (task.isVisitStructureTask()) {
         TagKey<Structure> tag = TagKey.create(Registries.STRUCTURE, task.visitStructureTag().orElseThrow());
         return Optional.ofNullable(player.serverLevel().findNearestMapStructure(tag, player.blockPosition(), 128, false));
      } else {
         return task.isSpawnTargetTask() ? generateSpawnTarget(player, task) : Optional.empty();
      }
   }

   private static Optional<BlockPos> generateSpawnTarget(ServerPlayer player, GuildTaskDefinitions.GuildTaskDefinition task) {
      Random random = new Random();
      int radius = Math.max(1, task.spawnRadius());
      int minDistance = Math.max(0, Math.min(task.spawnMinDistance(), radius));
      return task.spawnBiomes().isEmpty()
         ? Optional.of(randomSpawnTarget(player.blockPosition(), random, radius, minDistance))
         : findSpawnBiomeTarget(player.serverLevel(), player.blockPosition(), task, random, radius, minDistance);
   }

   private static BlockPos randomSpawnTarget(BlockPos origin, Random random, int radius, int minDistance) {
      double angle = random.nextDouble() * Math.PI * 2.0;
      double distance = minDistance + Math.sqrt(random.nextDouble()) * (radius - minDistance);
      int x = origin.getX() + (int)Math.round(Math.cos(angle) * distance);
      int z = origin.getZ() + (int)Math.round(Math.sin(angle) * distance);
      return new BlockPos(x, origin.getY(), z);
   }

   private static Optional<BlockPos> findSpawnBiomeTarget(
      ServerLevel level, BlockPos origin, GuildTaskDefinitions.GuildTaskDefinition task, Random random, int radius, int minDistance
   ) {
      for (int attempt = 0; attempt < 96; attempt++) {
         BlockPos searchCenter = randomSpawnTarget(origin, random, radius, minDistance);
         Optional<BlockPos> located = findClosestAllowedBiome(level, searchCenter, task, 512)
            .filter(target -> isInTargetDistance(origin, target, minDistance, radius));
         if (located.isPresent()) {
            return located;
         }
      }

      Optional<BlockPos> nearest = findClosestAllowedBiome(level, origin, task, radius)
         .filter(target -> isInTargetDistance(origin, target, minDistance, radius));
      return nearest.isPresent() ? nearest : Optional.empty();
   }

   private static Optional<BlockPos> findClosestAllowedBiome(ServerLevel level, BlockPos center, GuildTaskDefinitions.GuildTaskDefinition task, int radius) {
      Pair<BlockPos, Holder<Biome>> pair = level.findClosestBiome3d(biome -> isAllowedSpawnBiome(biome, task), center, Math.max(32, radius), 32, 64);
      if (pair == null) {
         return Optional.empty();
      }

      BlockPos pos = (BlockPos)pair.getFirst();
      return Optional.of(new BlockPos(pos.getX(), center.getY(), pos.getZ()));
   }

   private static boolean isInTargetDistance(BlockPos origin, BlockPos target, int minDistance, int radius) {
      double dx = target.getX() - origin.getX();
      double dz = target.getZ() - origin.getZ();
      double distanceSqr = dx * dx + dz * dz;
      return distanceSqr >= (double)minDistance * minDistance && distanceSqr <= (double)radius * radius;
   }

   private static Optional<BlockPos> findNearbyGroundPosition(ServerLevel level, BlockPos origin, int radius, int step) {
      return findNearbyGroundPosition(level, origin, radius, step, null);
   }

   private static Optional<BlockPos> findRandomNearbyGroundPosition(
      ServerLevel level, BlockPos origin, int radius, GuildTaskDefinitions.GuildTaskDefinition task, Set<BlockPos> usedPositions
   ) {
      int safeRadius = Math.max(1, radius);
      Set<BlockPos> used = usedPositions == null ? Set.of() : usedPositions;

      for (int attempt = 0; attempt < 64; attempt++) {
         double angle = level.random.nextDouble() * Math.PI * 2.0;
         double distance = Math.sqrt(level.random.nextDouble()) * safeRadius;
         int x = origin.getX() + (int)Math.round(Math.cos(angle) * distance);
         int z = origin.getZ() + (int)Math.round(Math.sin(angle) * distance);
         Optional<BlockPos> pos = findGroundPosition(level, x, z, task).filter(value -> !used.contains(value));
         if (pos.isPresent()) {
            return pos;
         }
      }

      return findNearbyGroundPosition(level, origin, safeRadius, 4, task).filter(value -> !used.contains(value));
   }

   private static Optional<BlockPos> findNearbyGroundPosition(
      ServerLevel level, BlockPos origin, int radius, int step, GuildTaskDefinitions.GuildTaskDefinition task
   ) {
      int safeStep = Math.max(1, step);

      for (int distance = 0; distance <= radius; distance += safeStep) {
         if (distance == 0) {
            Optional<BlockPos> center = findGroundPosition(level, origin.getX(), origin.getZ(), task);
            if (center.isPresent()) {
               return center;
            }
         } else {
            for (int dx = -distance; dx <= distance; dx += safeStep) {
               Optional<BlockPos> north = findGroundPosition(level, origin.getX() + dx, origin.getZ() - distance, task);
               if (north.isPresent()) {
                  return north;
               }

               Optional<BlockPos> south = findGroundPosition(level, origin.getX() + dx, origin.getZ() + distance, task);
               if (south.isPresent()) {
                  return south;
               }
            }

            for (int dz = -distance + safeStep; dz <= distance - safeStep; dz += safeStep) {
               Optional<BlockPos> west = findGroundPosition(level, origin.getX() - distance, origin.getZ() + dz, task);
               if (west.isPresent()) {
                  return west;
               }

               Optional<BlockPos> east = findGroundPosition(level, origin.getX() + distance, origin.getZ() + dz, task);
               if (east.isPresent()) {
                  return east;
               }
            }
         }
      }

      return Optional.empty();
   }

   private static Optional<BlockPos> findGroundPosition(ServerLevel level, int x, int z) {
      return findGroundPosition(level, x, z, null);
   }

   private static Optional<BlockPos> findGroundPosition(ServerLevel level, int x, int z, GuildTaskDefinitions.GuildTaskDefinition task) {
      int y = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, x, z);
      BlockPos surface = new BlockPos(x, y, z);
      return isStandableSurface(level, surface) && isAllowedSpawnBiome(level, surface, task) ? Optional.of(surface) : Optional.empty();
   }

   private static boolean isAllowedSpawnBiome(ServerLevel level, BlockPos pos, GuildTaskDefinitions.GuildTaskDefinition task) {
      return task != null && !task.spawnBiomes().isEmpty() ? isAllowedSpawnBiome(level.getBiome(pos), task) : true;
   }

   private static boolean isAllowedSpawnBiome(Holder<Biome> biomeHolder, GuildTaskDefinitions.GuildTaskDefinition task) {
      if (task != null && !task.spawnBiomes().isEmpty()) {
         for (ResourceLocation biome : task.spawnBiomes()) {
            if (biomeHolder.is(ResourceKey.create(Registries.BIOME, biome))) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   private static boolean isStandableSurface(ServerLevel level, BlockPos surface) {
      if (surface.getY() > level.getMinBuildHeight() && surface.getY() < level.getMaxBuildHeight()) {
         BlockPos ground = surface.below();
         BlockState groundState = level.getBlockState(ground);
         if (groundState.isAir()) {
            return false;
         } else {
            FluidState groundFluid = groundState.getFluidState();
            if (!groundFluid.isEmpty()) {
               return false;
            } else {
               return !groundState.isFaceSturdy(level, ground, Direction.UP)
                  ? false
                  : level.getBlockState(surface).isAir() && level.getBlockState(surface.above()).isAir();
            }
         }
      } else {
         return false;
      }
   }

   private static boolean isNearTaskTarget(ServerPlayer player, GuildTaskBoardData.ActiveTask task, int distance) {
      Optional<BlockPos> target = taskTarget(task);
      if (target.isEmpty()) {
         return false;
      }

      double dx = player.getX() - (target.get().getX() + 0.5);
      double dz = player.getZ() - (target.get().getZ() + 0.5);
      return dx * dx + dz * dz <= (double)distance * distance;
   }

   private static double horizontalDistanceSqr(BlockPos first, BlockPos second) {
      double dx = first.getX() - (second.getX() + 0.5);
      double dz = first.getZ() - (second.getZ() + 0.5);
      return dx * dx + dz * dz;
   }

   private static Optional<BlockPos> taskTarget(GuildTaskBoardData.ActiveTask task) {
      return !task.targetX().isEmpty() && !task.targetZ().isEmpty()
         ? Optional.of(new BlockPos(task.targetX().get(), 0, task.targetZ().get()))
         : Optional.empty();
   }

   private static void syncTrackedTask(
      MinecraftServer server, ResourceLocation taskId, Optional<UUID> partyLeaderId, BlockPos targetPos, Optional<UUID> trackedEntityId
   ) {
      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         GuildTaskBoardData current = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);
         if (!current.activeTask(taskId).filter(task -> samePartyTask(task.partyLeaderId(), partyLeaderId)).isEmpty()) {
            GuildTaskBoardData updated = current.updateTrackedTask(taskId, targetPos, trackedEntityId);
            if (!updated.equals(current)) {
               player.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
               player.syncData(GuildAttachments.GUILD_TASK_BOARD);
               updated.activeTask(taskId).ifPresent(task -> updateTaskItems(player, task));
            }
         }
      }
   }

   private static void syncTargetSpawnedTask(MinecraftServer server, ResourceLocation taskId, Optional<UUID> partyLeaderId, BlockPos targetPos) {
      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         GuildTaskBoardData current = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);
         if (!current.activeTask(taskId).filter(task -> samePartyTask(task.partyLeaderId(), partyLeaderId)).isEmpty()) {
            GuildTaskBoardData updated = current.markTargetSpawned(taskId, targetPos);
            if (!updated.equals(current)) {
               player.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
               player.syncData(GuildAttachments.GUILD_TASK_BOARD);
               updated.activeTask(taskId).ifPresent(task -> updateTaskItems(player, task));
            }
         }
      }
   }

   private static void updateTaskItems(ServerPlayer player, GuildTaskBoardData.ActiveTask task) {
      updateTaskItems(player, List.of(task));
   }

   private static void updateTaskItems(ServerPlayer player, List<GuildTaskBoardData.ActiveTask> tasks) {
      if (!tasks.isEmpty()) {
         for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.is(GuildItems.GUILD_TASK.get())) {
               for (GuildTaskBoardData.ActiveTask task : tasks) {
                  if (isTaskItem(stack, task.taskId())) {
                     updateTaskItemTracking(stack, task);
                     break;
                  }
               }
            }
         }

         player.getInventory().setChanged();
      }
   }

   private static void updateTaskItemTracking(ItemStack stack, GuildTaskBoardData.ActiveTask task) {
      CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag tag = customData.copyTag();
      task.targetX().ifPresentOrElse(value -> tag.putInt("TargetX", value), () -> tag.remove("TargetX"));
      task.targetZ().ifPresentOrElse(value -> tag.putInt("TargetZ", value), () -> tag.remove("TargetZ"));
      task.trackedEntityId().ifPresentOrElse(value -> tag.putUUID("TrackedEntity", value), () -> tag.remove("TrackedEntity"));
      if (task.requiredCount() > 0) {
         tag.putInt("RequiredCount", task.requiredCount());
      } else {
         tag.remove("RequiredCount");
      }

      tag.putBoolean("TaskComplete", isActiveTaskComplete(task));
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
   }

   private static boolean isActiveTaskComplete(GuildTaskBoardData.ActiveTask task) {
      return GuildTaskDefinitions.get(task.taskId())
         .filter(definition -> definition.isKillTask() || definition.isVisitStructureTask())
         .map(definition -> task.taskProgress() >= task.requiredCountOrDefault(definition.requiredCount()))
         .orElse(false);
   }

   private static void failTaskGroup(MinecraftServer server, ResourceLocation taskId, Optional<UUID> partyLeaderId) {
      List<GuildTaskService.TrackedTaskHolder> holders = new ArrayList<>();

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         ((GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD))
            .activeTask(taskId)
            .filter(task -> samePartyTask(task.partyLeaderId(), partyLeaderId))
            .ifPresent(task -> holders.add(new GuildTaskService.TrackedTaskHolder(player, task)));
      }

      failTrackedTaskHolders(server, holders);
   }

   private static void failTrackedTaskHolders(MinecraftServer server, List<GuildTaskService.TrackedTaskHolder> holders) {
      for (GuildTaskService.TrackedTaskHolder holder : holders) {
         failTask(holder.player(), holder.task().taskId());
      }
   }

   private static boolean failTask(ServerPlayer player, ResourceLocation taskId) {
      GuildTaskBoardData current = (GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD);
      GuildTaskBoardData updated = current.failTask(taskId);
      if (updated.equals(current)) {
         return false;
      }

      current.activeTask(taskId).ifPresent(task -> removeTrackedEntity(player.server, task));
      removeTaskItems(player, taskId);
      removeDroppedTaskItems(player, taskId);
      GuildWorldData.get(player.server).releaseTask(taskId);
      player.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
      player.syncData(GuildAttachments.GUILD_TASK_BOARD);
      player.setData(GuildAttachments.GUILD_MEMBER, ((GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER)).withFailedTasks(1));
      player.syncData(GuildAttachments.GUILD_MEMBER);
      applyTaskFailurePenalty(player, 1);
      return true;
   }

   private static void applyTaskFailurePenalty(ServerPlayer player, int failures) {
      if (failures > 0) {
         boolean changed = false;

         for (int i = 0; i < failures; i++) {
            GuildLevelData levelData = (GuildLevelData)player.getData(GuildAttachments.GUILD_LEVEL);
            int totalExperience = levelData.totalExperience();
            if (totalExperience <= 0) {
               break;
            }

            int penalty = Math.max(1, (int)Math.ceil(totalExperience * 0.1));
            player.setData(GuildAttachments.GUILD_LEVEL, levelData.removeExperience(penalty));
            changed = true;
         }

         if (changed) {
            player.syncData(GuildAttachments.GUILD_LEVEL);
            syncToPlayer(player);
         }
      }
   }

   private static List<GuildTaskService.TrackedTaskHolder> trackedTaskHolders(MinecraftServer server, UUID entityId) {
      List<GuildTaskService.TrackedTaskHolder> holders = new ArrayList<>();

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         for (GuildTaskBoardData.ActiveTask task : ((GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD)).activeTasks()) {
            if (task.trackedEntityId().filter(entityId::equals).isPresent()) {
               holders.add(new GuildTaskService.TrackedTaskHolder(player, task));
            }
         }
      }

      return holders;
   }

   private static boolean isAuthorizedTrackedTaskKiller(ServerPlayer killer, UUID entityId) {
      return ((GuildTaskBoardData)killer.getData(GuildAttachments.GUILD_TASK_BOARD))
         .activeTasks()
         .stream()
         .anyMatch(task -> task.trackedEntityId().filter(entityId::equals).isPresent());
   }

   private static boolean isTrackedEntityValidForTask(Entity entity, GuildTaskBoardData.ActiveTask activeTask) {
      if (entity != null && activeTask != null) {
         ResourceLocation entityType = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
         return GuildTaskDefinitions.get(activeTask.taskId())
            .flatMap(GuildTaskDefinitions.GuildTaskDefinition::targetEntity)
            .filter(entityType::equals)
            .isPresent();
      } else {
         return false;
      }
   }

   private static void discardEvolvedTrackedEntity(MinecraftServer server, GuildTaskBoardData.ActiveTask activeTask) {
      if (isOrcLordTask(activeTask)) {
         Optional<BlockPos> target = taskTarget(activeTask);
         if (!target.isEmpty()) {
            double radius = 128.0;

            for (ServerLevel level : server.getAllLevels()) {
               AABB area = new AABB(
                  target.get().getX() - radius,
                  level.getMinBuildHeight(),
                  target.get().getZ() - radius,
                  target.get().getX() + radius,
                  level.getMaxBuildHeight(),
                  target.get().getZ() + radius
               );

               for (Entity entity : level.getEntities((Entity)null, area, GuildTaskService::isOrcDisasterEntity)) {
                  entity.discard();
               }
            }
         }
      }
   }

   private static boolean isOrcLordTask(GuildTaskBoardData.ActiveTask activeTask) {
      return GuildTaskDefinitions.get(activeTask.taskId())
         .flatMap(GuildTaskDefinitions.GuildTaskDefinition::targetEntity)
         .filter(ORC_LORD_ENTITY::equals)
         .isPresent();
   }

   private static boolean isOrcDisasterEntity(Entity entity) {
      return entity != null && ORC_DISASTER_ENTITY.equals(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()));
   }

   private static boolean samePartyTask(Optional<UUID> first, Optional<UUID> second) {
      return first.equals(second);
   }

   private static Entity findEntity(MinecraftServer server, UUID entityId) {
      for (ServerLevel level : server.getAllLevels()) {
         Entity entity = level.getEntity(entityId);
         if (entity != null) {
            return entity;
         }
      }

      return null;
   }

   private static void removeTrackedEntity(MinecraftServer server, GuildTaskBoardData.ActiveTask task) {
      task.trackedEntityId().map(entityId -> findEntity(server, entityId)).ifPresent(Entity::discard);
   }

   private static int taskTimeLimitTicks(Optional<GuildTaskDefinitions.GuildTaskDefinition> task) {
      return task.map(GuildTaskDefinitions.GuildTaskDefinition::effectiveTimeLimitTicks).orElseGet(GuildConfig::commissionTimeLimitTicks);
   }

   private static int generatedRequiredCount(Optional<GuildTaskDefinitions.GuildTaskDefinition> task) {
      return task.filter(value -> value.hasRandomKillCount() || value.isMultiSpawnTargetTask())
         .map(value -> value.isMultiSpawnTargetTask() ? value.randomSpawnCount(new Random()) : value.rollRequiredCount(new Random()))
         .orElse(-1);
   }

   private record SpawnContextCacheEntry(boolean hasContext, long gameTime) {
   }

   private record SpawnContextCacheKey(ResourceLocation dimension, ResourceLocation taskId, int gridX, int gridZ, int radius) {
      private static GuildTaskService.SpawnContextCacheKey of(ServerLevel level, ResourceLocation taskId, BlockPos pos, int radius) {
         return new GuildTaskService.SpawnContextCacheKey(
            level.dimension().location(), taskId, Math.floorDiv(pos.getX(), 256), Math.floorDiv(pos.getZ(), 256), radius
         );
      }
   }

   private record TrackedTaskHolder(ServerPlayer player, GuildTaskBoardData.ActiveTask task) {
   }
}
