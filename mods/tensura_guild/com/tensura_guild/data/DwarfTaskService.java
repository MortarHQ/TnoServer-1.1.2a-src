package com.tensura_guild.data;

import com.tensura_guild.compat.GuildCompatHooks;
import com.tensura_guild.config.GuildConfig;
import com.tensura_guild.network.OpenDwarfTaskDialoguePayload;
import com.tensura_guild.registry.GuildAttachments;
import com.tensura_guild.registry.GuildItems;
import io.github.manasmods.tensura.entity.human.DwarfEntity;
import io.github.manasmods.tensura.registry.entity.HumanEntityTypes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.neoforged.neoforge.network.PacketDistributor;
import net.tslat.smartbrainlib.util.BrainUtils;

public final class DwarfTaskService {
   private static final int INTERACTION_DISTANCE_SQR = 64;
   private static final List<String> NEGATIVE_REPUTATION_REFUSALS = List.of(
      "dialogue.tensura_guild.dwarf_task.refuse.1",
      "dialogue.tensura_guild.dwarf_task.refuse.2",
      "dialogue.tensura_guild.dwarf_task.refuse.3",
      "dialogue.tensura_guild.dwarf_task.refuse.4",
      "dialogue.tensura_guild.dwarf_task.refuse.5",
      "dialogue.tensura_guild.dwarf_task.refuse.6",
      "dialogue.tensura_guild.dwarf_task.refuse.7",
      "dialogue.tensura_guild.dwarf_task.refuse.8",
      "dialogue.tensura_guild.dwarf_task.refuse.9",
      "dialogue.tensura_guild.dwarf_task.refuse.10",
      "dialogue.tensura_guild.dwarf_task.refuse.11",
      "dialogue.tensura_guild.dwarf_task.refuse.12",
      "dialogue.tensura_guild.dwarf_task.refuse.13",
      "dialogue.tensura_guild.dwarf_task.refuse.14",
      "dialogue.tensura_guild.dwarf_task.refuse.15",
      "dialogue.tensura_guild.dwarf_task.refuse.16",
      "dialogue.tensura_guild.dwarf_task.refuse.17",
      "dialogue.tensura_guild.dwarf_task.refuse.18"
   );
   private static final CopyOnWriteArrayList<Predicate<DwarfEntity>> TASK_DWARF_PREDICATES = new CopyOnWriteArrayList<>();
   private static final Map<UUID, UUID> PLAYER_DIALOGUE_DWARFS = new ConcurrentHashMap<>();
   private static final Map<UUID, UUID> DWARF_DIALOGUE_PLAYERS = new ConcurrentHashMap<>();
   private static final Map<UUID, ResourceLocation> COOLDOWN_DWARFS = new ConcurrentHashMap<>();

   private DwarfTaskService() {
   }

   public static Runnable registerTaskDwarfPredicate(Predicate<DwarfEntity> predicate) {
      Predicate<DwarfEntity> checkedPredicate = Objects.requireNonNull(predicate, "predicate");
      TASK_DWARF_PREDICATES.add(checkedPredicate);
      return () -> TASK_DWARF_PREDICATES.remove(checkedPredicate);
   }

   public static List<ResourceLocation> availableDwarfTaskIds() {
      return DwarfTaskDefinitions.availableTaskIds();
   }

   public static boolean isKnownDwarfTask(ResourceLocation taskId) {
      return DwarfTaskDefinitions.contains(taskId);
   }

   public static boolean assignTaskOffer(DwarfEntity dwarf, ResourceLocation taskId) {
      Optional<DwarfTaskDefinitions.DwarfTaskDefinition> definition = DwarfTaskDefinitions.get(taskId);
      if (isValidDwarf(dwarf) && !definition.isEmpty()) {
         dwarf.setData(GuildAttachments.DWARF_TASK_OFFER, DwarfTaskOfferData.assigned(taskId, definition.get().rollRequiredCount(dwarf.getRandom())));
         dwarf.syncData(GuildAttachments.DWARF_TASK_OFFER);
         COOLDOWN_DWARFS.remove(dwarf.getUUID());
         return true;
      } else {
         return false;
      }
   }

   public static boolean clearTaskOffer(DwarfEntity dwarf) {
      if (dwarf == null) {
         return false;
      }

      dwarf.removeData(GuildAttachments.DWARF_TASK_OFFER);
      dwarf.syncData(GuildAttachments.DWARF_TASK_OFFER);
      COOLDOWN_DWARFS.remove(dwarf.getUUID());
      return true;
   }

   public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
      if (!event.getLevel().isClientSide() && event.getEntity() instanceof DwarfEntity dwarf) {
         indexCooldownOffer(dwarf);
      }
   }

   public static boolean isPotentialTaskDwarf(DwarfEntity dwarf) {
      return offeredTask(dwarf, false).isPresent();
   }

   public static boolean shouldShowMarker(Player player, DwarfEntity dwarf) {
      if (player != null && isValidDwarf(dwarf)) {
         DwarfTaskData data = (DwarfTaskData)player.getData(GuildAttachments.DWARF_TASK);
         if (!data.hasActiveTask() && hasPendingGuildMapReward(player, dwarf)) {
            return true;
         } else if (data.hasActiveTask()) {
            return data.dwarfId().filter(dwarf.getUUID()::equals).isPresent();
         } else {
            return hasNegativeDwarfReputation(player) ? false : visibleTaskOffer(player, dwarf).isPresent();
         }
      } else {
         return false;
      }
   }

   public static void onEntityInteract(EntityInteract event) {
      if (event.getHand() == InteractionHand.MAIN_HAND
         && event.getTarget() instanceof DwarfEntity dwarf
         && isWithinInteractionDistance(event.getEntity(), dwarf)) {
         Player player = event.getEntity();
         DwarfTaskData data = (DwarfTaskData)player.getData(GuildAttachments.DWARF_TASK);
         boolean hasPendingGuildMapReward = !data.hasActiveTask() && hasPendingGuildMapReward(player, dwarf);
         boolean hasThisTask = data.hasActiveTask() && data.dwarfId().filter(dwarf.getUUID()::equals).isPresent();
         boolean hasVisibleOffer = visibleTaskOffer(player, dwarf).isPresent();
         boolean negativeReputation = hasVisibleOffer && hasNegativeDwarfReputation(player);
         boolean shouldHandle = hasPendingGuildMapReward || hasThisTask || hasVisibleOffer || negativeReputation;
         if (shouldHandle) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.sidedSuccess(event.getEntity().level().isClientSide()));
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
               if (hasPendingGuildMapReward) {
                  openGuildMapRewardDialogue(serverPlayer, dwarf);
                  return;
               }

               if (negativeReputation && !hasThisTask) {
                  sendNegativeReputationRefusal(serverPlayer);
                  return;
               }

               openDialogue(serverPlayer, dwarf);
            }
         }
      }
   }

   public static boolean acceptTask(ServerPlayer player, int entityId) {
      if (hasNegativeDwarfReputation(player)) {
         sendNegativeReputationRefusal(player);
         return false;
      } else {
         Entity entity = player.level().getEntity(entityId);
         Optional<DwarfTaskOfferData> offer = entity instanceof DwarfEntity dwarf ? offeredTask(dwarf, true) : Optional.empty();
         Optional<ResourceLocation> taskId = offer.flatMap(DwarfTaskOfferData::taskId);
         Optional<DwarfTaskDefinitions.DwarfTaskDefinition> definition = taskId.flatMap(DwarfTaskDefinitions::get);
         if (offer.flatMap(DwarfTaskOfferData::reservedBy).filter(reservedBy -> !reservedBy.equals(player.getUUID())).isPresent()) {
            return false;
         } else if (entity instanceof DwarfEntity dwarf
            && isWithinInteractionDistance(player, dwarf)
            && !((DwarfTaskData)player.getData(GuildAttachments.DWARF_TASK)).hasActiveTask()
            && !taskId.isEmpty()
            && !definition.isEmpty()) {
            int requiredCount = offer.<Integer>map(value -> value.amountOrDefault(definition.get().requiredCount()))
               .orElseGet(() -> definition.get().requiredCount());
            player.setData(GuildAttachments.DWARF_TASK, DwarfTaskData.active(taskId.get(), dwarf.getUUID(), player.serverLevel().getGameTime(), requiredCount));
            dwarf.setData(GuildAttachments.DWARF_TASK_OFFER, DwarfTaskOfferData.assigned(taskId.get(), requiredCount, player.getUUID()));
            dwarf.syncData(GuildAttachments.DWARF_TASK_OFFER);
            syncToPlayer(player);
            player.displayClientMessage(Component.translatable("screen.tensura_guild.dwarf_task.accepted"), true);
            return true;
         } else {
            return false;
         }
      }
   }

   public static boolean submitTask(ServerPlayer player, int entityId) {
      DwarfTaskData data = (DwarfTaskData)player.getData(GuildAttachments.DWARF_TASK);
      Optional<DwarfTaskDefinitions.DwarfTaskDefinition> definition = data.taskId().flatMap(DwarfTaskDefinitions::get);
      if (!(
         player.level().getEntity(entityId) instanceof DwarfEntity dwarf
            && isWithinInteractionDistance(player, dwarf)
            && isAssignedDwarf(data, dwarf)
            && !definition.isEmpty()
      )) {
         return false;
      } else {
         if (clearIfExpired(player, data, definition.get())) {
            return false;
         }

         int requiredCount = data.requiredCountOrDefault(definition.get().requiredCount());
         if (!hasRequiredItems(player, definition.get(), requiredCount)) {
            openDialogue(player, dwarf);
            return false;
         }

         GuildTaskRewardPenalty rewardPenalty = GuildTaskRewardPenalty.forPlayer(player);
         consumeRequiredItems(player, definition.get(), requiredCount);
         giveRewards(player, definition.get(), rewardPenalty);
         GuildReputationShopService.awardDwarfTaskCoins(player, rewardPenalty);
         data.taskId().ifPresent(taskId -> beginOfferCooldown(dwarf, taskId, player.serverLevel().getGameTime()));
         clearTask(player);
         player.displayClientMessage(Component.translatable("screen.tensura_guild.dwarf_task.completed"), true);
         GuildAdvancementService.grantCraftsmansRequest(player);
         GuildCompatHooks.onDwarfTaskCompleted(player);
         if (recordDwarfTaskCompletion(player, dwarf)) {
            openGuildMapRewardDialogue(player, dwarf);
         }

         return true;
      }
   }

   public static void openDialogue(ServerPlayer player, DwarfEntity dwarf) {
      if (isWithinInteractionDistance(player, dwarf)) {
         DwarfTaskData data = (DwarfTaskData)player.getData(GuildAttachments.DWARF_TASK);
         if (!data.hasActiveTask() && hasPendingGuildMapReward(player, dwarf)) {
            openGuildMapRewardDialogue(player, dwarf);
         } else if (!data.hasActiveTask() && hasNegativeDwarfReputation(player)) {
            sendNegativeReputationRefusal(player);
         } else {
            tick(player);
            data = (DwarfTaskData)player.getData(GuildAttachments.DWARF_TASK);
            Optional<DwarfTaskOfferData> offer = data.hasActiveTask() ? Optional.empty() : visibleTaskOffer(player, dwarf, true);
            Optional<ResourceLocation> offerTaskId = offer.flatMap(DwarfTaskOfferData::taskId);
            Optional<DwarfTaskDefinitions.DwarfTaskDefinition> definition = data.hasActiveTask()
               ? data.taskId().flatMap(DwarfTaskDefinitions::get)
               : offerTaskId.flatMap(DwarfTaskDefinitions::get);
            if (!data.hasActiveTask() || isAssignedDwarf(data, dwarf)) {
               if ((data.hasActiveTask() || !offerTaskId.isEmpty()) && !definition.isEmpty()) {
                  DwarfTaskDefinitions.DwarfTaskDefinition task = definition.get();
                  int requiredCount = data.hasActiveTask()
                     ? data.requiredCountOrDefault(task.requiredCount())
                     : offer.<Integer>map(value -> value.amountOrDefault(task.requiredCount())).orElseGet(task::requiredCount);
                  int heldCount = countRequiredItems(player, task);
                  long remainingTicks = data.hasActiveTask()
                     ? data.remainingTicks(player.serverLevel().getGameTime(), task.timeLimitTicks())
                     : task.timeLimitTicks();
                  OpenDwarfTaskDialoguePayload.Mode mode = data.hasActiveTask()
                     ? (hasRequiredItems(player, task, requiredCount) ? OpenDwarfTaskDialoguePayload.Mode.READY : OpenDwarfTaskDialoguePayload.Mode.ACTIVE)
                     : OpenDwarfTaskDialoguePayload.Mode.OFFER;
                  startDialogueLock(player, dwarf);
                  PacketDistributor.sendToPlayer(
                     player,
                     new OpenDwarfTaskDialoguePayload(
                        dwarf.getId(),
                        mode,
                        dwarf.getName().getString(),
                        task.dialogue().offer(),
                        task.dialogue().active(),
                        task.dialogue().ready(),
                        heldCount,
                        requiredCount,
                        remainingTicks
                     ),
                     new CustomPacketPayload[0]
                  );
               }
            }
         }
      }
   }

   public static boolean claimGuildMapReward(ServerPlayer player, int entityId) {
      if (player.level().getEntity(entityId) instanceof DwarfEntity dwarf
         && isWithinInteractionDistance(player, dwarf)
         && hasPendingGuildMapReward(player, dwarf)) {
         player.setData(
            GuildAttachments.DWARF_TASK_PROGRESS, ((DwarfTaskProgressData)player.getData(GuildAttachments.DWARF_TASK_PROGRESS)).claimGuildMapReward()
         );
         syncDwarfTaskProgress(player);
         ItemStack stack = new ItemStack((ItemLike)GuildItems.ADVENTURER_GUILD_MAP.get());
         if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
         }

         player.displayClientMessage(Component.translatable("screen.tensura_guild.dwarf_task.guild_map_received"), true);
         return true;
      } else {
         return false;
      }
   }

   public static void stopDialogue(Player player, int entityId) {
      if (player != null) {
         if (player.level().getEntity(entityId) instanceof DwarfEntity dwarf) {
            stopDialogueLock(player.getUUID(), dwarf);
         }
      }
   }

   public static void tickDialogueLock(ServerPlayer player) {
      UUID playerId = player.getUUID();
      UUID dwarfId = PLAYER_DIALOGUE_DWARFS.get(playerId);
      if (dwarfId != null) {
         Optional<Entity> entity = findEntity(player.server, dwarfId);
         if (!(!entity.isEmpty() && entity.get() instanceof DwarfEntity dwarf)) {
            PLAYER_DIALOGUE_DWARFS.remove(playerId);
            DWARF_DIALOGUE_PLAYERS.remove(dwarfId, playerId);
         } else if (!Objects.equals(DWARF_DIALOGUE_PLAYERS.get(dwarfId), playerId)) {
            PLAYER_DIALOGUE_DWARFS.remove(playerId, dwarfId);
         } else if (player.isAlive() && dwarf.level() == player.level() && isValidDwarf(dwarf) && isWithinInteractionDistance(player, dwarf)) {
            applyDialogueLock(player, dwarf);
         } else {
            stopDialogueLock(playerId, dwarf);
         }
      }
   }

   public static void tick(ServerPlayer player) {
      DwarfTaskData data = (DwarfTaskData)player.getData(GuildAttachments.DWARF_TASK);
      if (data.hasActiveTask()) {
         Optional<DwarfTaskDefinitions.DwarfTaskDefinition> definition = data.taskId().flatMap(DwarfTaskDefinitions::get);
         if (!definition.isEmpty() && !clearIfExpired(player, data, definition.get())) {
            data.dwarfId().flatMap(id -> findEntity(player.server, id)).filter(entity -> entity instanceof DwarfEntity).ifPresentOrElse(entity -> {
               if (!isValidDwarf((DwarfEntity)entity)) {
                  clearTask(player);
                  player.displayClientMessage(Component.translatable("screen.tensura_guild.dwarf_task.dwarf_lost"), true);
               }
            }, () -> {
               clearTask(player);
               player.displayClientMessage(Component.translatable("screen.tensura_guild.dwarf_task.dwarf_lost"), true);
            });
         }
      }
   }

   public static void tickTaskOffers(MinecraftServer server) {
      if (server != null) {
         long currentGameTime = server.overworld().getGameTime();
         COOLDOWN_DWARFS.keySet().removeIf(dwarfId -> tickIndexedTaskOffer(server, dwarfId, currentGameTime));
      }
   }

   public static void handleDwarfDeath(DwarfEntity dwarf, MinecraftServer server) {
      UUID dwarfId = dwarf.getUUID();
      UUID playerId = DWARF_DIALOGUE_PLAYERS.remove(dwarfId);
      if (playerId != null) {
         PLAYER_DIALOGUE_DWARFS.remove(playerId, dwarfId);
      }

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         DwarfTaskData data = (DwarfTaskData)player.getData(GuildAttachments.DWARF_TASK);
         if (!data.dwarfId().filter(dwarfId::equals).isEmpty()) {
            clearTask(player);
            player.displayClientMessage(Component.translatable("screen.tensura_guild.dwarf_task.dwarf_lost"), true);
         }
      }
   }

   public static void syncToPlayer(ServerPlayer player) {
      player.syncData(GuildAttachments.DWARF_TASK);
   }

   private static boolean recordDwarfTaskCompletion(ServerPlayer player, DwarfEntity dwarf) {
      DwarfTaskProgressData previous = (DwarfTaskProgressData)player.getData(GuildAttachments.DWARF_TASK_PROGRESS);
      DwarfTaskProgressData updated = previous.recordCompletedTask(dwarf.getUUID(), GuildConfig.dwarfTasksForGuildMap());
      player.setData(GuildAttachments.DWARF_TASK_PROGRESS, updated);
      syncDwarfTaskProgress(player);
      return previous.pendingGuildMapDwarfId().isEmpty() && updated.hasPendingGuildMapRewardFor(dwarf.getUUID());
   }

   private static void openGuildMapRewardDialogue(ServerPlayer player, DwarfEntity dwarf) {
      if (isWithinInteractionDistance(player, dwarf) && hasPendingGuildMapReward(player, dwarf)) {
         startDialogueLock(player, dwarf);
         PacketDistributor.sendToPlayer(
            player,
            new OpenDwarfTaskDialoguePayload(
               dwarf.getId(),
               OpenDwarfTaskDialoguePayload.Mode.GUILD_MAP_REWARD,
               dwarf.getName().getString(),
               "dialogue.tensura_guild.dwarf_task.guild_map_reward",
               "",
               "",
               0,
               0,
               0L
            ),
            new CustomPacketPayload[0]
         );
      }
   }

   private static void syncDwarfTaskProgress(ServerPlayer player) {
      player.syncData(GuildAttachments.DWARF_TASK_PROGRESS);
   }

   private static boolean hasPendingGuildMapReward(Player player, DwarfEntity dwarf) {
      return player != null
         && isValidDwarf(dwarf)
         && ((DwarfTaskProgressData)player.getData(GuildAttachments.DWARF_TASK_PROGRESS)).hasPendingGuildMapRewardFor(dwarf.getUUID());
   }

   private static boolean canInteract(Player player, DwarfEntity dwarf) {
      return player != null && isWithinInteractionDistance(player, dwarf) && shouldShowMarker(player, dwarf);
   }

   private static void startDialogueLock(ServerPlayer player, DwarfEntity dwarf) {
      UUID playerId = player.getUUID();
      UUID dwarfId = dwarf.getUUID();
      UUID previousPlayer = DWARF_DIALOGUE_PLAYERS.put(dwarfId, playerId);
      if (previousPlayer != null && !previousPlayer.equals(playerId)) {
         PLAYER_DIALOGUE_DWARFS.remove(previousPlayer, dwarfId);
      }

      PLAYER_DIALOGUE_DWARFS.put(playerId, dwarfId);
      applyDialogueLock(player, dwarf);
   }

   private static void stopDialogueLock(UUID playerId, DwarfEntity dwarf) {
      UUID dwarfId = dwarf.getUUID();
      if (Objects.equals(PLAYER_DIALOGUE_DWARFS.get(playerId), dwarfId)) {
         PLAYER_DIALOGUE_DWARFS.remove(playerId, dwarfId);
         if (DWARF_DIALOGUE_PLAYERS.remove(dwarfId, playerId)) {
            clearDialogueLock(dwarf);
         }
      }
   }

   private static void applyDialogueLock(Player player, DwarfEntity dwarf) {
      dwarf.getNavigation().stop();
      BrainUtils.clearMemory(dwarf, MemoryModuleType.WALK_TARGET);
      BrainUtils.clearMemory(dwarf, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      BrainUtils.setMemory(dwarf, MemoryModuleType.LOOK_TARGET, new EntityTracker(player, true));
      dwarf.getLookControl().setLookAt(player, 30.0F, 30.0F);
      dwarf.lookAt(player, 30.0F, 30.0F);
      dwarf.setYRot(dwarf.yHeadRot);
      dwarf.yBodyRot = dwarf.getYRot();
      dwarf.yBodyRotO = dwarf.yBodyRot;
   }

   private static void clearDialogueLock(DwarfEntity dwarf) {
      BrainUtils.clearMemory(dwarf, MemoryModuleType.WALK_TARGET);
      BrainUtils.clearMemory(dwarf, MemoryModuleType.LOOK_TARGET);
      BrainUtils.clearMemory(dwarf, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      dwarf.getNavigation().stop();
   }

   private static boolean clearIfExpired(ServerPlayer player, DwarfTaskData data, DwarfTaskDefinitions.DwarfTaskDefinition task) {
      long currentGameTime = player.serverLevel().getGameTime();
      if (!data.isExpired(currentGameTime, task.timeLimitTicks())) {
         return false;
      }

      applyExpirationPenalty(player, task);
      releaseExpiredTask(player, data, currentGameTime);
      return true;
   }

   private static void applyExpirationPenalty(ServerPlayer player, DwarfTaskDefinitions.DwarfTaskDefinition task) {
      int reputationReward = task.dwarfReputationReward();
      if (reputationReward > 0) {
         addDwarfReputation(player, -reputationReward * 2);
      }
   }

   private static void releaseExpiredTask(ServerPlayer player, DwarfTaskData data, long currentGameTime) {
      Optional<ResourceLocation> taskId = data.taskId();
      Optional<UUID> dwarfId = data.dwarfId();
      if (taskId.isPresent() && dwarfId.isPresent()) {
         findEntity(player.server, dwarfId.get())
            .filter(entity -> entity instanceof DwarfEntity)
            .map(entity -> (DwarfEntity)entity)
            .ifPresent(dwarf -> beginOfferCooldown(dwarf, taskId.get(), currentGameTime));
      }

      clearTask(player);
      player.displayClientMessage(Component.translatable("screen.tensura_guild.dwarf_task.expired"), true);
   }

   private static boolean isAssignedDwarf(DwarfTaskData data, DwarfEntity dwarf) {
      return data.hasActiveTask() && data.dwarfId().filter(dwarf.getUUID()::equals).isPresent();
   }

   private static void clearTask(ServerPlayer player) {
      player.setData(GuildAttachments.DWARF_TASK, DwarfTaskData.EMPTY);
      syncToPlayer(player);
   }

   private static void beginOfferCooldown(DwarfEntity dwarf, ResourceLocation taskId, long currentGameTime) {
      if (isValidDwarf(dwarf) && isKnownDwarfTask(taskId)) {
         dwarf.setData(GuildAttachments.DWARF_TASK_OFFER, DwarfTaskOfferData.cooldown(taskId, currentGameTime, 86400L));
         dwarf.syncData(GuildAttachments.DWARF_TASK_OFFER);
         indexCooldownOffer(dwarf);
      }
   }

   private static boolean tickIndexedTaskOffer(MinecraftServer server, UUID dwarfId, long currentGameTime) {
      Optional<Entity> entity = findEntity(server, dwarfId);
      if (entity.isEmpty()) {
         return false;
      } else {
         return entity.get() instanceof DwarfEntity dwarf && isValidDwarf(dwarf) ? tickTaskOffer(dwarf, currentGameTime) : true;
      }
   }

   private static boolean tickTaskOffer(DwarfEntity dwarf, long currentGameTime) {
      DwarfTaskOfferData offer = (DwarfTaskOfferData)dwarf.getData(GuildAttachments.DWARF_TASK_OFFER);
      if (!offer.isReadyForRoll(currentGameTime)) {
         indexCooldownOffer(dwarf);
         return false;
      } else {
         Optional<ResourceLocation> taskId = offer.cooldownTaskId().filter(DwarfTaskService::isKnownDwarfTask);
         if (taskId.isEmpty()) {
            dwarf.removeData(GuildAttachments.DWARF_TASK_OFFER);
            dwarf.syncData(GuildAttachments.DWARF_TASK_OFFER);
            return true;
         } else {
            DwarfTaskOfferData nextOffer = dwarf.getRandom().nextDouble() < 0.1
               ? DwarfTaskDefinitions.get(taskId.get())
                  .map(task -> DwarfTaskOfferData.assigned(taskId.get(), task.rollRequiredCount(dwarf.getRandom())))
                  .orElseGet(offer::offerAgain)
               : offer.rollFailed(currentGameTime);
            dwarf.setData(GuildAttachments.DWARF_TASK_OFFER, nextOffer);
            dwarf.syncData(GuildAttachments.DWARF_TASK_OFFER);
            return nextOffer.cooldownTaskId().isEmpty();
         }
      }
   }

   private static void indexCooldownOffer(DwarfEntity dwarf) {
      DwarfTaskOfferData offer = (DwarfTaskOfferData)dwarf.getData(GuildAttachments.DWARF_TASK_OFFER);
      offer.cooldownTaskId()
         .filter(DwarfTaskService::isKnownDwarfTask)
         .ifPresentOrElse(taskId -> COOLDOWN_DWARFS.put(dwarf.getUUID(), taskId), () -> COOLDOWN_DWARFS.remove(dwarf.getUUID()));
   }

   private static boolean isWithinInteractionDistance(Player player, DwarfEntity dwarf) {
      return player.distanceToSqr(dwarf) <= 64.0;
   }

   private static boolean isValidDwarf(DwarfEntity dwarf) {
      return dwarf != null && !dwarf.isBaby() && !dwarf.isRemoved() && !dwarf.isDeadOrDying();
   }

   private static boolean hasNegativeDwarfReputation(Player player) {
      ITensuraPlayer data = TensuraStorages.getPlayerDataFrom(player);
      return data.getReputation((EntityType)HumanEntityTypes.DWARF.get()) < 0.0;
   }

   private static Optional<DwarfTaskOfferData> visibleTaskOffer(Player player, DwarfEntity dwarf) {
      return visibleTaskOffer(player, dwarf, false);
   }

   private static Optional<DwarfTaskOfferData> visibleTaskOffer(Player player, DwarfEntity dwarf, boolean materialize) {
      return offeredTask(dwarf, materialize).filter(offer -> isVisibleToPlayer(player, offer));
   }

   private static boolean isVisibleToPlayer(Player player, DwarfTaskOfferData offer) {
      return offer != null && !offer.taskId().isEmpty()
         ? offer.reservedBy().isEmpty() || offer.reservedBy().filter(player.getUUID()::equals).isPresent()
         : false;
   }

   private static void sendNegativeReputationRefusal(ServerPlayer player) {
      if (!NEGATIVE_REPUTATION_REFUSALS.isEmpty()) {
         String key = NEGATIVE_REPUTATION_REFUSALS.get(player.getRandom().nextInt(NEGATIVE_REPUTATION_REFUSALS.size()));
         player.displayClientMessage(Component.translatable(key), false);
      }
   }

   private static Optional<ResourceLocation> offeredTaskId(DwarfEntity dwarf) {
      return offeredTask(dwarf, false).flatMap(DwarfTaskOfferData::taskId);
   }

   private static Optional<DwarfTaskOfferData> offeredTask(DwarfEntity dwarf, boolean materialize) {
      if (!isValidDwarf(dwarf)) {
         return Optional.empty();
      }

      DwarfTaskOfferData offer = (DwarfTaskOfferData)dwarf.getData(GuildAttachments.DWARF_TASK_OFFER);
      Optional<ResourceLocation> assignedTask = offer.taskId().filter(DwarfTaskService::isKnownDwarfTask);
      if (assignedTask.isPresent()) {
         return Optional.of(offer);
      }

      if (offer.suppressesFallbackOffer()) {
         return Optional.empty();
      }

      Optional<ResourceLocation> professionTask = professionTask(dwarf, materialize);
      if (professionTask.isPresent()) {
         return createOffer(dwarf, professionTask.get(), materialize);
      }

      for (Predicate<DwarfEntity> predicate : TASK_DWARF_PREDICATES) {
         if (predicate.test(dwarf)) {
            return createOffer(dwarf, DwarfTaskDefinitions.HIPOKUTE_FLOWER_TASK, materialize);
         }
      }

      return Optional.empty();
   }

   private static Optional<DwarfTaskOfferData> createOffer(DwarfEntity dwarf, ResourceLocation taskId, boolean materialize) {
      return DwarfTaskDefinitions.get(taskId).map(task -> {
         DwarfTaskOfferData created = DwarfTaskOfferData.assigned(taskId, task.rollRequiredCount(dwarf.getRandom()));
         if (materialize && !dwarf.level().isClientSide()) {
            dwarf.setData(GuildAttachments.DWARF_TASK_OFFER, created);
            dwarf.syncData(GuildAttachments.DWARF_TASK_OFFER);
            COOLDOWN_DWARFS.remove(dwarf.getUUID());
         }

         return created;
      });
   }

   private static Optional<ResourceLocation> professionTask(DwarfEntity dwarf, boolean random) {
      ResourceLocation profession = BuiltInRegistries.VILLAGER_PROFESSION.getKey(dwarf.getProfession());
      List<ResourceLocation> tasks = DwarfTaskDefinitions.availableTaskIdsForProfession(profession);
      return tasks.isEmpty() ? Optional.empty() : Optional.of(tasks.get(random ? dwarf.getRandom().nextInt(tasks.size()) : 0));
   }

   private static int countRequiredItems(ServerPlayer player, DwarfTaskDefinitions.DwarfTaskDefinition task) {
      if (task.hasRequiredItemSet()) {
         int count = 0;

         for (ResourceLocation itemId : task.requiredItems()) {
            Item item = (Item)BuiltInRegistries.ITEM.get(itemId);
            if (item != Items.AIR && countInventoryItem(player, item) > 0) {
               count++;
            }
         }

         return count;
      } else {
         Item item = (Item)BuiltInRegistries.ITEM.get(task.requiredItem());
         return item == Items.AIR ? 0 : countInventoryItem(player, item);
      }
   }

   private static int countInventoryItem(ServerPlayer player, Item item) {
      int count = 0;

      for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
         ItemStack stack = player.getInventory().getItem(slot);
         if (stack.is(item)) {
            count += stack.getCount();
         }
      }

      return count;
   }

   private static void consumeRequiredItems(ServerPlayer player, DwarfTaskDefinitions.DwarfTaskDefinition task, int requiredCount) {
      if (task.hasRequiredItemSet()) {
         for (ResourceLocation itemId : task.requiredItems()) {
            Item item = (Item)BuiltInRegistries.ITEM.get(itemId);
            if (item != Items.AIR) {
               consumeInventoryItem(player, item, 1);
            }
         }

         player.getInventory().setChanged();
      } else {
         Item item = (Item)BuiltInRegistries.ITEM.get(task.requiredItem());
         if (item != Items.AIR) {
            consumeInventoryItem(player, item, Math.max(1, requiredCount));
            player.getInventory().setChanged();
         }
      }
   }

   private static void consumeInventoryItem(ServerPlayer player, Item item, int count) {
      int remaining = Math.max(1, count);

      for (int slot = 0; slot < player.getInventory().getContainerSize() && remaining > 0; slot++) {
         ItemStack stack = player.getInventory().getItem(slot);
         if (stack.is(item)) {
            int removed = Math.min(remaining, stack.getCount());
            stack.shrink(removed);
            remaining -= removed;
         }
      }
   }

   private static boolean hasRequiredItems(ServerPlayer player, DwarfTaskDefinitions.DwarfTaskDefinition task, int requiredCount) {
      return countRequiredItems(player, task) >= Math.max(1, requiredCount);
   }

   private static void giveRewards(ServerPlayer player, DwarfTaskDefinitions.DwarfTaskDefinition task, GuildTaskRewardPenalty penalty) {
      giveRewardItem(player, task, penalty);
      giveRandomBonusReward(player, task, penalty);
      addDwarfReputation(player, task.dwarfReputationReward());
      giveAdventurerExperience(player, task.experienceReward(), penalty);
   }

   private static void giveAdventurerExperience(ServerPlayer player, int amount, GuildTaskRewardPenalty penalty) {
      int adjustedAmount = (penalty == null ? GuildTaskRewardPenalty.NONE : penalty).adjustExperienceAmount(amount);
      if (adjustedAmount > 0 && ((GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER)).hasJoinedGuild()) {
         player.setData(GuildAttachments.GUILD_LEVEL, ((GuildLevelData)player.getData(GuildAttachments.GUILD_LEVEL)).addExperience(adjustedAmount));
         player.syncData(GuildAttachments.GUILD_LEVEL);
         GuildCompatHooks.onAdventureExperienceChanged(player);
      }
   }

   private static void giveRewardItem(ServerPlayer player, DwarfTaskDefinitions.DwarfTaskDefinition task, GuildTaskRewardPenalty penalty) {
      Item reward = (Item)BuiltInRegistries.ITEM.get(task.rewardItem());
      if (reward != Items.AIR) {
         int count = (penalty == null ? GuildTaskRewardPenalty.NONE : penalty).adjustItemRewardAmount(task.rewardItem(), task.rewardCount());
         if (count > 0) {
            ItemStack stack = new ItemStack(reward, count);
            if (!player.getInventory().add(stack)) {
               player.drop(stack, false);
            }
         }
      }
   }

   private static void giveRandomBonusReward(ServerPlayer player, DwarfTaskDefinitions.DwarfTaskDefinition task, GuildTaskRewardPenalty penalty) {
      task.rollRandomBonusReward(player.getRandom()).ifPresent(reward -> {
         Item item = (Item)BuiltInRegistries.ITEM.get(reward.item());
         if (item != Items.AIR) {
            int count = (penalty == null ? GuildTaskRewardPenalty.NONE : penalty).adjustItemRewardAmount(reward.item(), reward.rollCount(player.getRandom()));
            if (count > 0) {
               ItemStack stack = new ItemStack(item, count);
               if (!player.getInventory().add(stack)) {
                  player.drop(stack, false);
               }
            }
         }
      });
   }

   private static void addDwarfReputation(ServerPlayer player, int amount) {
      ITensuraPlayer data = TensuraStorages.getPlayerDataFrom(player);
      data.setReputation((EntityType)HumanEntityTypes.DWARF.get(), data.getReputation((EntityType)HumanEntityTypes.DWARF.get()) + amount);
      data.markDirty();
   }

   private static Optional<Entity> findEntity(MinecraftServer server, UUID entityId) {
      for (ServerLevel level : server.getAllLevels()) {
         Entity entity = level.getEntity(entityId);
         if (entity != null) {
            return Optional.of(entity);
         }
      }

      return Optional.empty();
   }
}
