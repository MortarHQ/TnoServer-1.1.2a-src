package org.crypticdev.stextras.quest.assignment;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import org.crypticdev.stextras.net.STExtrasQuestSync;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.definition.QuestRarity;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.quest.schedule.QuestSchedule;
import org.crypticdev.stextras.quest.schedule.QuestScheduleSavedData;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;

public final class ScheduledQuestAssignmentService {
   private static final int[] BONUS_QUEST_PERCENT = new int[]{0, 3, 7, 12, 18, 28, 40};
   private static final QuestRarity[] RARITIES_ORDER = new QuestRarity[]{
      QuestRarity.NOVICE, QuestRarity.ADEPT, QuestRarity.INTERMEDIATE, QuestRarity.EXPERT, QuestRarity.MASTER
   };
   private static final int[][] DAILY_RARITY_WEIGHTS = new int[][]{
      {30, 30, 25, 10, 5}, {28, 30, 27, 10, 5}, {26, 28, 29, 12, 5}, {24, 26, 31, 14, 5}, {22, 24, 33, 16, 6}, {20, 22, 35, 17, 6}, {18, 20, 37, 18, 7}
   };
   private static final int[][] WEEKLY_RARITY_WEIGHTS = new int[][]{
      {20, 25, 30, 20, 5}, {18, 25, 32, 20, 5}, {16, 23, 34, 22, 5}, {14, 21, 36, 24, 5}, {12, 19, 38, 25, 6}, {10, 17, 40, 26, 6}, {8, 15, 42, 27, 8}
   };

   public static void applyCurrentToPlayer(MinecraftServer server, ServerPlayer player, boolean freshReroll) {
      QuestSchedule schedule = QuestScheduleSavedData.get(server).schedule();
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      Set<String> blockedSignatures = QuestDuplicateSignatures.collectActiveSignatures(store, QuestCategory.DAILY, QuestCategory.WEEKLY);
      Set<ResourceLocation> desiredWeekly = chooseForPlayer(
         server, player, QuestCategory.WEEKLY, schedule.weeklyCode, schedule.weeklyNonce, Math.max(0, schedule.weeklyCount), blockedSignatures
      );
      QuestDuplicateSignatures.addSignatures(blockedSignatures, desiredWeekly);
      Set<ResourceLocation> desiredDaily = chooseForPlayer(
         server, player, QuestCategory.DAILY, schedule.dailyCode, schedule.dailyNonce, Math.max(0, schedule.dailyCount), blockedSignatures
      );
      applySetForCategory(player, QuestCategory.DAILY, desiredDaily, freshReroll);
      applySetForCategory(player, QuestCategory.WEEKLY, desiredWeekly, freshReroll);
      store.setLastApplied(schedule.dailyCode, schedule.weeklyCode, schedule.dailyNonce, schedule.weeklyNonce);
   }

   public static void rerollPlayerQuestsDaily(MinecraftServer server, ServerPlayer player) {
      rerollPlayerQuests(server, player, true, false);
   }

   public static void rerollPlayerQuestsWeekly(MinecraftServer server, ServerPlayer player) {
      rerollPlayerQuests(server, player, false, true);
   }

   public static void rerollPlayerQuestsBoth(MinecraftServer server, ServerPlayer player) {
      rerollPlayerQuests(server, player, true, true);
   }

   public static void rerollPlayerQuests(MinecraftServer server, ServerPlayer player, boolean rerollDaily, boolean rerollWeekly) {
      if (rerollDaily || rerollWeekly) {
         QuestSchedule schedule = QuestScheduleSavedData.get(server).schedule();
         STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
         Set<String> blockedSignatures = collectBlockedSignatures(store, rerollDaily, rerollWeekly);
         Set<ResourceLocation> desiredWeekly = Set.of();
         if (rerollDaily) {
            store.setDailyRerollSalt(store.getDailyRerollSalt() + 1);
         }

         if (rerollWeekly) {
            store.setWeeklyRerollSalt(store.getWeeklyRerollSalt() + 1);
            desiredWeekly = chooseForPlayer(
               server, player, QuestCategory.WEEKLY, schedule.weeklyCode, schedule.weeklyNonce, Math.max(0, schedule.weeklyCount), blockedSignatures
            );
            QuestDuplicateSignatures.addSignatures(blockedSignatures, desiredWeekly);
            applySetForCategory(player, QuestCategory.WEEKLY, desiredWeekly, true);
         }

         if (rerollDaily) {
            Set<ResourceLocation> desiredDaily = chooseForPlayer(
               server, player, QuestCategory.DAILY, schedule.dailyCode, schedule.dailyNonce, Math.max(0, schedule.dailyCount), blockedSignatures
            );
            applySetForCategory(player, QuestCategory.DAILY, desiredDaily, true);
         }

         store.setLastApplied(schedule.dailyCode, schedule.weeklyCode, schedule.dailyNonce, schedule.weeklyNonce);
         if (store.isDirty()) {
            STExtrasQuestSync.sendTo(player, store.toNet());
            store.clearDirty();
         }
      }
   }

   private static void applySetForCategory(ServerPlayer player, QuestCategory category, Set<ResourceLocation> desired, boolean freshReroll) {
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      if (!freshReroll) {
         Set<ResourceLocation> completed = new HashSet<>(store.completed(category));
         Set<ResourceLocation> desiredFiltered = new LinkedHashSet<>();

         for (ResourceLocation id : desired) {
            if (!completed.contains(id)) {
               desiredFiltered.add(id);
            }
         }

         for (ResourceLocation id : new ArrayList<>(store.active(category))) {
            if (!desiredFiltered.contains(id)) {
               store.removeActive(id);
               store.setProgress(id, 0);
            }
         }

         for (ResourceLocation id : desiredFiltered) {
            if (!store.active(category).contains(id)) {
               store.addActive(id);
               store.setProgress(id, 0);
            }
         }
      } else {
         store.clearCategory(category);

         for (ResourceLocation id : desired) {
            store.addActive(id);
            store.setProgress(id, 0);
         }
      }
   }

   private static Set<ResourceLocation> chooseForPlayer(
      MinecraftServer server, ServerPlayer player, QuestCategory category, long code, int nonce, int baseCount, Set<String> blockedSignatures
   ) {
      if (baseCount <= 0) {
         return Set.of();
      }

      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      int rerollSalt = category == QuestCategory.DAILY ? store.getDailyRerollSalt() : store.getWeeklyRerollSalt();
      long seed = seedFor(server, code ^ nonce * 2654435769L)
         ^ player.getUUID().getMostSignificantBits()
         ^ player.getUUID().getLeastSignificantBits()
         ^ rerollSalt * 2654435769L;
      RandomSource random = RandomSource.create(seed);
      int soulIndexRaw = STExtarsStorage.Player.getSoulGradeLevel(player);
      int soulIndex = clamp(soulIndexRaw, 0, BONUS_QUEST_PERCENT.length - 1);
      int count = applySoulBonusToCount(baseCount, soulIndex, random);
      if (count <= 0) {
         return Set.of();
      }

      List<QuestDefinition> pool = new ArrayList<>(QuestRegistry.INSTANCE.byCategory(category));
      if (pool.isEmpty()) {
         return Set.of();
      }

      Set<ResourceLocation> out = new LinkedHashSet<>();
      Set<String> usedSignatures = QuestDuplicateSignatures.shouldSkipDupes() ? new LinkedHashSet<>(blockedSignatures) : Set.of();

      for (int i = 0; i < count && !pool.isEmpty(); i++) {
         List<QuestDefinition> available = QuestDuplicateSignatures.shouldSkipDupes()
            ? pool.stream().filter(questx -> !QuestDuplicateSignatures.isBlocked(questx, usedSignatures)).toList()
            : pool;
         if (available.isEmpty()) {
            break;
         }

         QuestRarity targetRarity = rollRarityFor(random, category, soulIndex, available);
         List<QuestDefinition> bucket = available.stream().filter(questx -> questx.rarity == targetRarity).toList();
         if (bucket.isEmpty()) {
            bucket = available;
         }

         QuestDefinition quest = bucket.get(random.nextInt(bucket.size()));
         out.add(quest.id);
         pool.remove(quest);
         QuestDuplicateSignatures.addSignature(usedSignatures, quest);
      }

      return out;
   }

   private static Set<String> collectBlockedSignatures(STExtrasQuestStorage store, boolean rerollDaily, boolean rerollWeekly) {
      EnumSet<QuestCategory> excluded = EnumSet.of(QuestCategory.REPEATABLE);
      if (rerollDaily) {
         excluded.add(QuestCategory.DAILY);
      }

      if (rerollWeekly) {
         excluded.add(QuestCategory.WEEKLY);
      }

      return QuestDuplicateSignatures.collectActiveSignatures(store, excluded.toArray(QuestCategory[]::new));
   }

   private static int clamp(int value, int min, int max) {
      return Math.max(min, Math.min(max, value));
   }

   private static int applySoulBonusToCount(int baseCount, int soulIndex, RandomSource random) {
      if (baseCount <= 0) {
         return 0;
      } else {
         int clampedIndex = clamp(soulIndex, 0, BONUS_QUEST_PERCENT.length - 1);
         int percent = BONUS_QUEST_PERCENT[clampedIndex];
         if (percent <= 0) {
            return baseCount;
         } else {
            return random.nextInt(100) < percent ? baseCount + 1 : baseCount;
         }
      }
   }

   private static QuestRarity rollRarityFor(RandomSource random, QuestCategory category, int soulIndex, List<QuestDefinition> pool) {
      int[] weightsRaw = category == QuestCategory.DAILY ? DAILY_RARITY_WEIGHTS[soulIndex] : WEEKLY_RARITY_WEIGHTS[soulIndex];
      List<QuestRarity> rarities = new ArrayList<>();
      List<Integer> weights = new ArrayList<>();

      for (int i = 0; i < RARITIES_ORDER.length; i++) {
         QuestRarity rarity = RARITIES_ORDER[i];
         int weight = weightsRaw[i];
         if (weight > 0) {
            boolean anyInPool = pool.stream().anyMatch(quest -> quest.rarity == rarity);
            if (anyInPool) {
               rarities.add(rarity);
               weights.add(weight);
            }
         }
      }

      if (rarities.isEmpty()) {
         List<QuestRarity> distinct = pool.stream().map(quest -> quest.rarity).distinct().toList();
         return distinct.get(random.nextInt(distinct.size()));
      }

      int total = 0;

      for (int weight : weights) {
         total += weight;
      }

      int roll = random.nextInt(total);

      for (int i = 0; i < rarities.size(); i++) {
         int weight = weights.get(i);
         if (roll < weight) {
            return rarities.get(i);
         }

         roll -= weight;
      }

      return rarities.get(rarities.size() - 1);
   }

   private static long seedFor(MinecraftServer server, long salt) {
      long worldSeed = server.overworld().getSeed();
      return worldSeed ^ salt * -7046029254386353131L;
   }

   private ScheduledQuestAssignmentService() {
   }
}
