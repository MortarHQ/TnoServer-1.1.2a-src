package org.crypticdev.stextras.quest.assignment;

import dev.architectury.registry.registries.Registrar;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.crypticdev.stextras.net.STExtrasQuestSync;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.definition.QuestObjectiveType;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.quest.registry.RacePrestigeMetadataRegistry;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;
import org.crypticdev.stextras.utils.PrestigeUtils;

public final class RacePrestigeAssignmentService {
   public static void ensureForCurrentRace(ServerPlayer player) {
      ensureForCurrentRace(player, false, true);
   }

   public static void ensureForCurrentRace(ServerPlayer player, boolean statFirst) {
      ensureForCurrentRace(player, statFirst, true);
   }

   public static void ensureForCurrentRace(ServerPlayer player, boolean statFirst, boolean sync) {
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      pruneUnavailableRacePrestigeQuests(store);
      pruneStartedRaceMessagesWithoutState(store);
      Optional<ResourceLocation> maybeRace = currentRaceId(player);
      if (maybeRace.isEmpty()) {
         syncIfDirty(player, store, sync, false);
      } else {
         ResourceLocation currentRace = maybeRace.get();
         List<QuestDefinition> racePool = QuestRegistry.INSTANCE
            .byCategory(QuestCategory.RACE_PRESTIGE)
            .stream()
            .filter(quest -> currentRace.equals(quest.race))
            .toList();
         Set<ResourceLocation> allowed = racePool.stream().map(quest -> quest.id).collect(Collectors.toSet());

         for (ResourceLocation id : new ArrayList<>(store.active(QuestCategory.RACE_PRESTIGE))) {
            if (!allowed.contains(id)) {
               store.resetQuest(id);
            }
         }

         for (ResourceLocation id : new ArrayList<>(store.progressMap(QuestCategory.RACE_PRESTIGE).keySet())) {
            if (!allowed.contains(id)) {
               store.resetQuest(id);
            }
         }

         for (ResourceLocation id : new ArrayList<>(store.completed(QuestCategory.RACE_PRESTIGE))) {
            if (!allowed.contains(id)) {
               store.resetQuest(id);
            }
         }

         if (store.hasCompletedRace(currentRace)) {
            clearCompletedRaceQuestState(store, currentRace);
            syncIfDirty(player, store, sync, false);
         } else {
            if (PrestigeUtils.CONFIG_PRESTIGE.ImmediateRaceQuests && isAtMaxEvolution(player)) {
               applyRaceQuests(player, store, racePool, Set.of(), 0);
            }

            syncIfDirty(player, store, sync, false);
         }
      }
   }

   public static boolean tryCompleteForCurrentRace(ServerPlayer player) {
      Optional<ResourceLocation> maybeRace = STExtarsStorage.Quests.currentRaceId(player);
      if (maybeRace.isEmpty()) {
         return false;
      }

      ResourceLocation raceId = maybeRace.get();
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      if (store.hasCompletedRace(raceId)) {
         return false;
      }

      List<QuestDefinition> raceQuests = QuestRegistry.INSTANCE
         .byCategory(QuestCategory.RACE_PRESTIGE)
         .stream()
         .filter(quest -> raceId.equals(quest.race))
         .toList();
      if (raceQuests.isEmpty()) {
         return false;
      }

      Set<ResourceLocation> completed = store.completed(QuestCategory.RACE_PRESTIGE);
      boolean allDone = raceQuests.stream().map(quest -> quest.id).allMatch(completed::contains);
      if (!allDone) {
         return false;
      }

      store.addCompletedRace(raceId);
      STPlayerEvents.fireRacePrestigeCompleted(player, raceId);
      clearCompletedRaceQuestState(store, raceId);
      syncIfDirty(player, store, true, true);
      int bonus = RacePrestigeMetadataRegistry.INSTANCE.soulGradeReward(raceId);
      if (bonus > 0) {
         int current = STExtarsStorage.Player.getSoulGrade(player);
         STExtarsStorage.Player.setSoulGrade(player, current + bonus);
      }

      return true;
   }

   private static void pruneUnavailableRacePrestigeQuests(STExtrasQuestStorage store) {
      Set<ResourceLocation> validQuestIds = QuestRegistry.INSTANCE
         .byCategory(QuestCategory.RACE_PRESTIGE)
         .stream()
         .map(quest -> quest.id)
         .collect(Collectors.toSet());

      for (ResourceLocation id : new ArrayList<>(store.active(QuestCategory.RACE_PRESTIGE))) {
         if (!validQuestIds.contains(id)) {
            store.resetQuest(id);
         }
      }

      for (ResourceLocation id : new ArrayList<>(store.progressMap(QuestCategory.RACE_PRESTIGE).keySet())) {
         if (!validQuestIds.contains(id)) {
            store.resetQuest(id);
         }
      }

      for (ResourceLocation id : new ArrayList<>(store.completed(QuestCategory.RACE_PRESTIGE))) {
         if (!validQuestIds.contains(id)) {
            store.resetQuest(id);
         }
      }

      pruneStartedRaceMessagesWithoutState(store);
   }

   public static boolean applyRaceQuests(
      ServerPlayer player, STExtrasQuestStorage store, List<QuestDefinition> racePool, Set<ResourceLocation> triggeredQuestIds, int currentActionAmount
   ) {
      boolean addedAny = false;
      Set<ResourceLocation> triggering = triggeredQuestIds == null ? Set.of() : new HashSet<>(triggeredQuestIds);

      for (QuestDefinition quest : racePool) {
         if (!store.active(QuestCategory.RACE_PRESTIGE).contains(quest.id) && !store.completed(QuestCategory.RACE_PRESTIGE).contains(quest.id)) {
            store.addActive(quest.id);
            int seedAmount = triggering.contains(quest.id) ? currentActionAmount : 0;
            int seed = initialProgressOnStart(player, quest, seedAmount);
            if (seed > 0) {
               store.setProgress(quest.id, seed);
            }

            addedAny = true;
         }
      }

      return addedAny;
   }

   private static Optional<ResourceLocation> currentRaceId(ServerPlayer player) {
      Optional<ManasRaceInstance> race = RaceAPI.getRaceFrom(player).getRace();
      if (race.isEmpty()) {
         return Optional.empty();
      }

      Registrar<ManasRace> registry = RaceAPI.getRaceRegistry();
      return Optional.ofNullable(registry.getId(race.get().getRace()));
   }

   private static void clearCompletedRaceQuestState(STExtrasQuestStorage store, ResourceLocation raceId) {
      clearQuestState(store, new ArrayList<>(store.active(QuestCategory.RACE_PRESTIGE)), raceId);
      clearQuestState(store, new ArrayList<>(store.completed(QuestCategory.RACE_PRESTIGE)), raceId);
      clearQuestState(store, new ArrayList<>(store.progressMap(QuestCategory.RACE_PRESTIGE).keySet()), raceId);
      store.clearStartedRaceMessage(raceId);
   }

   private static void clearQuestState(STExtrasQuestStorage store, List<ResourceLocation> ids, ResourceLocation raceId) {
      for (ResourceLocation id : ids) {
         QuestDefinition quest = QuestRegistry.INSTANCE.byId(id);
         if (quest != null && raceId.equals(quest.race)) {
            store.resetQuest(id);
         }
      }
   }

   public static int initialProgressOnStart(ServerPlayer player, QuestDefinition quest, int currentActionAmount) {
      if (quest != null && quest.statFirst && quest.creator == QuestObjectiveType.KILL && quest.amount > 0) {
         int required = Math.max(1, quest.amount);
         int seed = Math.max(0, computeInitialKillProgressFromStats(player, quest) - Math.max(0, currentActionAmount));
         return Math.min(seed, required);
      } else {
         return 0;
      }
   }

   private static int computeInitialKillProgressFromStats(ServerPlayer player, QuestDefinition quest) {
      if (!quest.types.isEmpty()) {
         int totalKills = 0;

         for (ResourceLocation id : quest.types) {
            EntityType<?> entityType = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(id);
            if (entityType != null) {
               totalKills += getKillStat(player, entityType);
            }
         }

         return totalKills;
      } else if (quest.mobCategory != null && (quest.tags == null || quest.tags.isEmpty())) {
         int sum = 0;

         for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
            try {
               if (entityType.getCategory() == quest.mobCategory) {
                  sum += getKillStat(player, entityType);
               }
            } catch (Throwable var6) {
            }
         }

         return sum;
      } else {
         return 0;
      }
   }

   private static int getKillStat(ServerPlayer player, EntityType<?> entityType) {
      try {
         return player.getStats().getValue(Stats.ENTITY_KILLED.get(entityType));
      } catch (Throwable ignored) {
         return 0;
      }
   }

   private static boolean hasRaceQuestState(STExtrasQuestStorage store, ResourceLocation raceId) {
      for (ResourceLocation id : store.active(QuestCategory.RACE_PRESTIGE)) {
         QuestDefinition quest = QuestRegistry.INSTANCE.byId(id);
         if (quest != null && raceId.equals(quest.race)) {
            return true;
         }
      }

      for (ResourceLocation id : store.completed(QuestCategory.RACE_PRESTIGE)) {
         QuestDefinition quest = QuestRegistry.INSTANCE.byId(id);
         if (quest != null && raceId.equals(quest.race)) {
            return true;
         }
      }

      for (ResourceLocation id : store.progressMap(QuestCategory.RACE_PRESTIGE).keySet()) {
         QuestDefinition quest = QuestRegistry.INSTANCE.byId(id);
         if (quest != null && raceId.equals(quest.race)) {
            return true;
         }
      }

      return false;
   }

   private static void pruneStartedRaceMessagesWithoutState(STExtrasQuestStorage store) {
      for (ResourceLocation raceId : new ArrayList<>(store.getStartedRaceMessages())) {
         if (!hasRaceQuestState(store, raceId)) {
            store.clearStartedRaceMessage(raceId);
         }
      }
   }

   private static boolean isAtMaxEvolution(ServerPlayer player) {
      Optional<ManasRaceInstance> current = RaceAPI.getRaceFrom(player).getRace();
      return current.isPresent() && current.get().getNextEvolutions(player).isEmpty();
   }

   private static void syncIfDirty(ServerPlayer player, STExtrasQuestStorage store, boolean sync, boolean includeRaceIds) {
      if (sync && store.isDirty()) {
         if (includeRaceIds) {
            STExtrasQuestSync.sendWithRaceIds(player, store.toNet());
         } else {
            STExtrasQuestSync.sendTo(player, store.toNet());
         }

         store.clearDirty();
      }
   }

   private RacePrestigeAssignmentService() {
   }
}
