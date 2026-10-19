package org.crypticdev.stextras.storage;

import dev.architectury.registry.registries.Registrar;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.config.STPrestigeConfig;
import org.crypticdev.stextras.config.STSkillLockConfig;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.quest.registry.RacePrestigeMetadataRegistry;
import org.crypticdev.stextras.quest.schedule.QuestScheduleService;
import org.crypticdev.stextras.storage.player.ISTExtrasPlayer;
import org.crypticdev.stextras.storage.player.client.ClientBossKills;
import org.crypticdev.stextras.storage.player.client.ClientSTExtrasPlayerStorage;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;
import org.crypticdev.stextras.storage.quest.client.ClientSTExtrasQuests;

public final class STExtarsStorage {
   public static ISTExtrasPlayer player(ServerPlayer player) {
      return (ISTExtrasPlayer)player;
   }

   public static STExtrasQuestStorage quests(ServerPlayer player) {
      return ((ISTExtrasQuests)player).stextras$getQuests();
   }

   public static ClientSTExtrasPlayerStorage clientPlayer() {
      return Minecraft.getInstance().player instanceof ClientSTExtrasPlayerStorage access ? access : null;
   }

   public static STExtrasQuestStorage clientQuests() {
      return Minecraft.getInstance().player instanceof ClientSTExtrasQuests access ? access.stextras$clientQuests() : null;
   }

   private STExtarsStorage() {
   }

   public static final class Client {
      public static ClientSTExtrasPlayerStorage player() {
         return STExtarsStorage.clientPlayer();
      }

      public static STExtrasQuestStorage quests() {
         return STExtarsStorage.clientQuests();
      }

      public static int bossKills() {
         return ClientBossKills.get();
      }

      public static void setBossKills(int value) {
         ClientBossKills.set(value);
      }

      private Client() {
      }
   }

   public static final class Player {
      private static final int SOUL_GRADE_LEVEL_COUNT = 6;
      private static final List<Integer> DEFAULT_SOUL_GRADE_LEVEL_THRESHOLDS = List.of(20, 40, 60, 80, 100, 120);
      private static final List<Integer> DEFAULT_MAX_LOCKS_PER_LEVEL = List.of(0, 1, 1, 2, 2, 2, 3);
      private static volatile List<Integer> CLIENT_SOUL_GRADE_LEVEL_THRESHOLDS = null;
      public static final STPrestigeConfig CONFIG_PRESTIGE = (STPrestigeConfig)ConfigRegistry.getConfig(STPrestigeConfig.class);
      public static final STSkillLockConfig CONFIG_SKILL_LOCK = (STSkillLockConfig)ConfigRegistry.getConfig(STSkillLockConfig.class);

      public static int getReincarnationEssence(ServerPlayer player) {
         return STExtarsStorage.player(player).getReincarnationEssence();
      }

      public static void setReincarnationEssence(ServerPlayer player, int amount) {
         int before = getReincarnationEssence(player);
         STExtarsStorage.player(player).setReincarnationEssence(amount);
         int after = getReincarnationEssence(player);
         if (before != after) {
            STPlayerEvents.fireReincarnationEssenceChanged(player, before, after);
         }
      }

      public static int getReincarnationEssence() {
         ClientSTExtrasPlayerStorage storage = STExtarsStorage.clientPlayer();
         return storage != null ? storage.getReincarnationEssence() : 0;
      }

      public static int getSoulGrade(ServerPlayer player) {
         return STExtarsStorage.player(player).getSoulGrade();
      }

      public static int getSoulGrade() {
         ClientSTExtrasPlayerStorage storage = STExtarsStorage.clientPlayer();
         return storage != null ? storage.getSoulGrade() : 0;
      }

      public static void setSoulGrade(ServerPlayer player, int value) {
         int before = getSoulGrade(player);
         STExtarsStorage.player(player).setSoulGrade(value);
         int after = getSoulGrade(player);
         if (before != after) {
            STPlayerEvents.fireSoulGradeChanged(player, before, after);
         }
      }

      public static int getSoulGradeLevel(ServerPlayer player) {
         return getSoulGradeLevel(getSoulGrade(player), resolveSoulGradeLevelThresholds());
      }

      public static int getSoulGradeLevel() {
         return getSoulGradeLevel(getSoulGrade(), resolveSoulGradeLevelThresholdsClient());
      }

      public static List<Integer> getSoulGradeLevelThresholds() {
         return List.copyOf(resolveSoulGradeLevelThresholds());
      }

      public static double getRequiredEpToUnlockSkill() {
         return Math.max(0.0, CONFIG_SKILL_LOCK.getNeededEPToUnlockSkill);
      }

      public static int getRequiredSoulGradeToUnlockSkill() {
         return Math.max(0, CONFIG_SKILL_LOCK.getSoulGradeCostToUnlockSkill);
      }

      public static double getRequiredMagiculeToUnlockSkill() {
         return getRequiredEpToUnlockSkill() / 2.0;
      }

      public static int getMaxLocksForSoulGradeLevel(int soulGradeLevel) {
         List<Integer> maxLocksPerLevel = resolveMaxLocksPerLevel();
         int index = Math.min(Math.max(0, soulGradeLevel), maxLocksPerLevel.size() - 1);
         return maxLocksPerLevel.get(index);
      }

      public static boolean isSoulGradeLevelOverrideEnabled() {
         return CONFIG_PRESTIGE.overrideSoulGradeLevels;
      }

      public static int getTotalConfiguredSoulGrade() {
         int total = RacePrestigeMetadataRegistry.INSTANCE.getAllRaceIds().stream().mapToInt(RacePrestigeMetadataRegistry.INSTANCE::soulGradeReward).sum();
         if (!CONFIG_PRESTIGE.overrideSoulGradeLevels) {
            total += getAdditionalSoulGradeForAutoLevels();
         }

         return Math.max(0, total);
      }

      public static int getAdditionalSoulGradeForAutoLevels() {
         return Math.max(0, CONFIG_PRESTIGE.additionalSoulGradeForAutoLevels);
      }

      public static int getSoulGradeRequiredForLevel(int level) {
         if (level <= 0) {
            return 0;
         }

         List<Integer> thresholds = resolveSoulGradeLevelThresholds();
         int index = Math.min(level, thresholds.size()) - 1;
         return thresholds.get(index);
      }

      public static void acceptSoulGradeLevelSync(List<Integer> thresholds) {
         CLIENT_SOUL_GRADE_LEVEL_THRESHOLDS = thresholds == null ? null : List.copyOf(thresholds);
      }

      public static int GetREBoosted(int prestige, int baseRe, QuestCategory category) {
         if (CONFIG_PRESTIGE.questREBoost && baseRe > 0 && shouldBoostQuestReward(category)) {
            double questReBoostAmount = CONFIG_PRESTIGE.questREBoostAmount;
            return (int)(baseRe * (1.0 + prestige * questReBoostAmount));
         } else {
            return baseRe;
         }
      }

      private static boolean shouldBoostQuestReward(QuestCategory category) {
         if (category == null) {
            return false;
         }

         return switch (category) {
            case DAILY -> CONFIG_PRESTIGE.questREBoostDaily;
            case WEEKLY -> CONFIG_PRESTIGE.questREBoostWeekly;
            case REQUIRED -> CONFIG_PRESTIGE.questREBoostRequired;
            case REPEATABLE -> CONFIG_PRESTIGE.questREBoostRepeatable;
            case RACE_PRESTIGE -> false;
         };
      }

      public static List<ResourceLocation> getLockedSkills(ServerPlayer player) {
         return STExtarsStorage.player(player).getLockedSkills();
      }

      public static List<ResourceLocation> getLockedSkills() {
         ClientSTExtrasPlayerStorage storage = STExtarsStorage.clientPlayer();
         return storage != null ? new ArrayList<>(storage.getLockedSkills()) : List.of();
      }

      public static boolean lockSkill(ServerPlayer player, ResourceLocation id) {
         boolean locked = STExtarsStorage.player(player).addLockedSkill(id);
         if (locked) {
            STPlayerEvents.fireSkillLockChanged(player, id, true);
         }

         return locked;
      }

      public static boolean unlockSkill(ServerPlayer player, ResourceLocation id) {
         boolean unlocked = STExtarsStorage.player(player).removeLockedSkill(id);
         if (unlocked) {
            STPlayerEvents.fireSkillLockChanged(player, id, false);
         }

         return unlocked;
      }

      public static List<ResourceLocation> getAdminLockedSkills(ServerPlayer player) {
         return STExtarsStorage.player(player).getAdminLockedSkills();
      }

      public static List<ResourceLocation> getAdminLockedSkills() {
         ClientSTExtrasPlayerStorage storage = STExtarsStorage.clientPlayer();
         return storage != null ? new ArrayList<>(storage.getAdminLockedSkills()) : List.of();
      }

      public static boolean isSkillLocked(ServerPlayer player, ResourceLocation id) {
         return isSkillLocked(id, getLockedSkills(player), getAdminLockedSkills(player));
      }

      public static boolean isSkillLocked(ResourceLocation id) {
         return isSkillLocked(id, getLockedSkills(), getAdminLockedSkills());
      }

      public static boolean isSkillAdminLocked(ServerPlayer player, ResourceLocation id) {
         return isSkillAdminLocked(id, getAdminLockedSkills(player));
      }

      public static boolean isSkillAdminLocked(ResourceLocation id) {
         return isSkillAdminLocked(id, getAdminLockedSkills());
      }

      public static boolean isSkillValid(ResourceLocation id) {
         return id != null && CONFIG_SKILL_LOCK.validSkillsTOLock.contains(id.toString());
      }

      public static boolean hasAvailableSkillLockSlot(ServerPlayer player) {
         return getLockedSkills(player).size() < getMaxLocksForSoulGradeLevel(getSoulGradeLevel(player));
      }

      public static boolean hasAvailableSkillLockSlot() {
         return getLockedSkills().size() < getMaxLocksForSoulGradeLevel(getSoulGradeLevel());
      }

      public static boolean canLockSkill(ServerPlayer player, ResourceLocation id) {
         return canLockSkill(id, getLockedSkills(player), getAdminLockedSkills(player), getSoulGradeLevel(player));
      }

      public static boolean canLockSkill(ResourceLocation id) {
         return canLockSkill(id, getLockedSkills(), getAdminLockedSkills(), getSoulGradeLevel());
      }

      public static boolean canUnlockSkill(ServerPlayer player, ResourceLocation id) {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         return canUnlockSkill(id, getLockedSkills(player), getAdminLockedSkills(player), getSoulGrade(player), existence.getEP(), existence.getMagicule());
      }

      public static boolean canUnlockSkill(ResourceLocation id, double ep, double magicule) {
         return canUnlockSkill(id, getLockedSkills(), getAdminLockedSkills(), getSoulGrade(), ep, magicule);
      }

      public static MutableComponent soulGradeLabel() {
         int grade = getSoulGradeLevel();
         String key;
         if (grade <= 0) {
            key = "stextras.soul_grade.none";
         } else if (grade == 1) {
            key = "stextras.soul_grade.low";
         } else if (grade == 2) {
            key = "stextras.soul_grade.medium";
         } else if (grade == 3) {
            key = "stextras.soul_grade.high";
         } else if (grade == 4) {
            key = "stextras.soul_grade.greater";
         } else if (grade == 5) {
            key = "stextras.soul_grade.grand";
         } else {
            key = "stextras.soul_grade.supreme";
         }

         return Component.translatable(key);
      }

      private static int getSoulGradeLevel(int soulGrade, List<Integer> thresholds) {
         if (soulGrade <= 0) {
            return 0;
         }

         int level = 0;

         for (int threshold : thresholds) {
            if (soulGrade < threshold) {
               break;
            }

            level++;
         }

         return Math.min(level, thresholds.size());
      }

      private static List<Integer> resolveSoulGradeLevelThresholds() {
         if (CONFIG_PRESTIGE.overrideSoulGradeLevels) {
            List<Integer> override = normalizeSoulGradeThresholds(CONFIG_PRESTIGE.soulGradeLevelThresholds);
            if (!override.isEmpty()) {
               return override;
            }
         }

         int totalConfiguredSoulGrade = getTotalConfiguredSoulGrade();
         return totalConfiguredSoulGrade > 0 ? splitSoulGradeThresholds(totalConfiguredSoulGrade) : DEFAULT_SOUL_GRADE_LEVEL_THRESHOLDS;
      }

      private static List<Integer> resolveSoulGradeLevelThresholdsClient() {
         if (CONFIG_PRESTIGE.overrideSoulGradeLevels) {
            return resolveSoulGradeLevelThresholds();
         }

         List<Integer> synced = CLIENT_SOUL_GRADE_LEVEL_THRESHOLDS;
         return synced != null && !synced.isEmpty() ? synced : resolveSoulGradeLevelThresholds();
      }

      private static List<Integer> splitSoulGradeThresholds(int totalConfiguredSoulGrade) {
         List<Integer> thresholds = new ArrayList<>(6);

         for (int level = 1; level <= 6; level++) {
            thresholds.add((int)Math.ceil((double)totalConfiguredSoulGrade * level / 6.0));
         }

         return thresholds;
      }

      private static List<Integer> normalizeSoulGradeThresholds(List<Integer> configuredThresholds) {
         if (configuredThresholds != null && !configuredThresholds.isEmpty()) {
            List<Integer> normalized = new ArrayList<>(6);
            int last = 0;

            for (int i = 0; i < 6; i++) {
               int current = i < configuredThresholds.size() ? Math.max(0, configuredThresholds.get(i)) : last;
               int minimum = i == 0 ? 1 : last;
               if (current < minimum) {
                  current = minimum;
               }

               normalized.add(current);
               last = current;
            }

            return normalized.get(normalized.size() - 1) <= 0 ? List.of() : List.copyOf(normalized);
         } else {
            return List.of();
         }
      }

      private static boolean canLockSkill(
         ResourceLocation id, List<ResourceLocation> lockedSkills, List<ResourceLocation> adminLockedSkills, int soulGradeLevel
      ) {
         return isSkillValid(id) && !isSkillLocked(id, lockedSkills, adminLockedSkills) && lockedSkills.size() < getMaxLocksForSoulGradeLevel(soulGradeLevel);
      }

      private static boolean canUnlockSkill(
         ResourceLocation id, List<ResourceLocation> lockedSkills, List<ResourceLocation> adminLockedSkills, int soulGrade, double ep, double magicule
      ) {
         return isSkillValid(id)
            && !isSkillAdminLocked(id, adminLockedSkills)
            && isSkillInList(lockedSkills, id)
            && ep >= getRequiredEpToUnlockSkill()
            && soulGrade >= getRequiredSoulGradeToUnlockSkill()
            && magicule >= getRequiredMagiculeToUnlockSkill();
      }

      private static boolean isSkillLocked(ResourceLocation id, List<ResourceLocation> lockedSkills, List<ResourceLocation> adminLockedSkills) {
         return isSkillInList(lockedSkills, id) || isSkillAdminLocked(id, adminLockedSkills);
      }

      private static boolean isSkillAdminLocked(ResourceLocation id, List<ResourceLocation> adminLockedSkills) {
         return isSkillInList(adminLockedSkills, id);
      }

      private static boolean isSkillInList(List<ResourceLocation> skills, ResourceLocation id) {
         return id != null && skills.contains(id);
      }

      private static List<Integer> resolveMaxLocksPerLevel() {
         List<Integer> configured = CONFIG_SKILL_LOCK.maxLocksPerLevel;
         if (configured != null && !configured.isEmpty()) {
            List<Integer> normalized = new ArrayList<>(configured.size());

            for (Integer rawValue : configured) {
               normalized.add(Math.max(0, rawValue == null ? 0 : rawValue));
            }

            return normalized.isEmpty() ? DEFAULT_MAX_LOCKS_PER_LEVEL : List.copyOf(normalized);
         } else {
            return DEFAULT_MAX_LOCKS_PER_LEVEL;
         }
      }

      private Player() {
      }
   }

   public static final class Quests {
      private static volatile Set<ResourceLocation> CLIENT_KNOWN_RACE_IDS = null;
      private static final Map<ResourceLocation, List<ResourceLocation>> CLIENT_RACE_QUEST_IDS = new ConcurrentHashMap<>();

      public static Set<ResourceLocation> disabledRacePrestigeRaces() {
         Set<ResourceLocation> out = new LinkedHashSet<>();

         for (String raw : STExtarsStorage.Player.CONFIG_PRESTIGE.disabledRacePrestigeRaces) {
            ResourceLocation id = ResourceLocation.tryParse(raw);
            if (id != null) {
               out.add(id);
            }
         }

         return Set.copyOf(out);
      }

      public static boolean isRacePrestigeEnabled(ResourceLocation raceId) {
         return raceId != null && !disabledRacePrestigeRaces().contains(raceId);
      }

      public static STExtrasQuestStorage get(ServerPlayer player) {
         return STExtarsStorage.quests(player);
      }

      public static STExtrasQuestStorage getClient() {
         return STExtarsStorage.clientQuests();
      }

      public static Set<ResourceLocation> active(ServerPlayer player) {
         return Set.copyOf(get(player).activeAll());
      }

      public static Set<ResourceLocation> active(ServerPlayer player, QuestCategory category) {
         return Set.copyOf(get(player).active(category));
      }

      public static Set<ResourceLocation> completed(ServerPlayer player) {
         return Set.copyOf(get(player).completedAll());
      }

      public static Set<ResourceLocation> completed(ServerPlayer player, QuestCategory category) {
         return Set.copyOf(get(player).completed(category));
      }

      public static void addActive(ServerPlayer player, ResourceLocation id) {
         get(player).addActive(id);
      }

      public static void removeActive(ServerPlayer player, ResourceLocation id) {
         get(player).removeActive(id);
      }

      public static Optional<ResourceLocation> currentRaceId(ServerPlayer player) {
         Optional<ManasRaceInstance> race = RaceAPI.getRaceFrom(player).getRace();
         if (race.isEmpty()) {
            return Optional.empty();
         }

         Registrar<ManasRace> registry = RaceAPI.getRaceRegistry();
         return Optional.ofNullable(registry.getId(race.get().getRace()));
      }

      public static Set<ResourceLocation> activeClient() {
         STExtrasQuestStorage storage = getClient();
         return storage == null ? Set.of() : Set.copyOf(storage.activeAll());
      }

      public static Set<ResourceLocation> activeClient(QuestCategory category) {
         STExtrasQuestStorage storage = getClient();
         return storage == null ? Set.of() : Set.copyOf(storage.active(category));
      }

      public static Set<ResourceLocation> completedClient() {
         STExtrasQuestStorage storage = getClient();
         return storage == null ? Set.of() : storage.completedAll();
      }

      public static Set<ResourceLocation> completedClient(QuestCategory category) {
         STExtrasQuestStorage storage = getClient();
         return storage == null ? Set.of() : Set.copyOf(storage.completed(category));
      }

      public static int progressClient(ResourceLocation id) {
         STExtrasQuestStorage storage = getClient();
         return storage == null ? 0 : storage.getProgress(id);
      }

      public static long cooldownRemainingClient(ResourceLocation id) {
         STExtrasQuestStorage storage = getClient();
         return storage == null ? 0L : storage.cooldownRemaining(id);
      }

      public static Set<ResourceLocation> completedRacesClient() {
         STExtrasQuestStorage storage = getClient();
         return storage == null ? Set.of() : Set.copyOf(storage.getCompletedRaces());
      }

      public static boolean hasCompletedRaceClient(ResourceLocation raceId) {
         STExtrasQuestStorage storage = getClient();
         return storage != null && storage.hasCompletedRace(raceId);
      }

      public static Set<ResourceLocation> allRaceIdsClient() {
         Set<ResourceLocation> cached = CLIENT_KNOWN_RACE_IDS;
         if (cached != null) {
            return Set.copyOf(cached);
         }

         Set<ResourceLocation> out = new LinkedHashSet<>();

         for (QuestDefinition quest : QuestRegistry.INSTANCE.byCategoryClient(QuestCategory.RACE_PRESTIGE)) {
            ResourceLocation raceId = quest.race != null ? quest.race : inferRaceIdFromQuestId(quest.id);
            if (raceId != null) {
               out.add(raceId);
            }
         }

         return out;
      }

      public static List<QuestDefinition> raceQuestsClient(ResourceLocation raceId) {
         if (raceId == null) {
            return List.of();
         }

         List<ResourceLocation> ids = CLIENT_RACE_QUEST_IDS.get(raceId);
         if (ids != null && !ids.isEmpty()) {
            List<QuestDefinition> quests = new ArrayList<>(ids.size());

            for (ResourceLocation questId : ids) {
               QuestDefinition quest = QuestRegistry.INSTANCE.byIdClient(questId);
               if (quest != null) {
                  quests.add(quest);
               }
            }

            return quests;
         } else {
            return QuestRegistry.INSTANCE.byCategoryClient(QuestCategory.RACE_PRESTIGE).stream().filter(questx -> raceId.equals(questx.race)).toList();
         }
      }

      public static long secondsUntilNextDaily() {
         return Math.max(0L, QuestScheduleService.secondsUntilNextDailyUtc());
      }

      public static long secondsUntilNextWeekly() {
         return Math.max(0L, QuestScheduleService.secondsUntilNextWeeklyUtc());
      }

      public static void acceptRaceIdsSync(Set<ResourceLocation> ids) {
         CLIENT_KNOWN_RACE_IDS = ids == null ? null : new LinkedHashSet<>(ids);
      }

      public static void acceptRaceQuestsSync(Map<ResourceLocation, List<ResourceLocation>> map) {
         CLIENT_RACE_QUEST_IDS.clear();
         if (map != null && !map.isEmpty()) {
            CLIENT_RACE_QUEST_IDS.putAll(map);
         }
      }

      private static ResourceLocation inferRaceIdFromQuestId(ResourceLocation questId) {
         if (questId == null) {
            return null;
         } else {
            String[] parts = questId.getPath().split("/");
            if (parts.length >= 2 && "race_prestige".equals(parts[0])) {
               String raceKey = parts[1];
               String full = raceKey.contains(":") ? raceKey : questId.getNamespace() + ":" + raceKey;
               return ResourceLocation.tryParse(full);
            } else {
               return null;
            }
         }
      }

      private Quests() {
      }
   }
}
