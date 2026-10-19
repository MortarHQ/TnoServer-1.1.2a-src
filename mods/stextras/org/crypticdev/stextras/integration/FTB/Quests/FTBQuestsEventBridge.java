package org.crypticdev.stextras.integration.FTB.Quests;

import dev.ftb.mods.ftbquests.quest.QuestObjectBase;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.Task;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.crypticdev.stextras.integration.FTB.Quests.task.LockSkillTask;
import org.crypticdev.stextras.integration.FTB.Quests.task.PrestigeTask;
import org.crypticdev.stextras.integration.FTB.Quests.task.RacePrestigeTask;
import org.crypticdev.stextras.integration.FTB.Quests.task.ReachPrestigeLevelTask;
import org.crypticdev.stextras.integration.FTB.Quests.task.ReachSoulGradeTask;
import org.crypticdev.stextras.integration.FTB.Quests.task.STExtrasQuestCompleteTask;

public final class FTBQuestsEventBridge {
   private static final Set<UUID> PRESTIGE_SUBMITTING = new HashSet<>();
   private static final Map<UUID, ResourceLocation> QUEST_COMPLETING = new HashMap<>();
   private static final Map<UUID, ResourceLocation> RACE_PRESTIGE_COMPLETING = new HashMap<>();
   private static final Map<UUID, ResourceLocation> SKILL_LOCKING = new HashMap<>();

   private FTBQuestsEventBridge() {
   }

   public static void init() {
      STPlayerEvents.PRESTIGE.register(FTBQuestsEventBridge::onPrestige);
      STPlayerEvents.PRESTIGE_LEVEL
         .register((STPlayerEvents.PrestigeLevelCallback)(player, prestigeLevel) -> submitMatching(player, ReachPrestigeLevelTask.class, task -> true));
      STPlayerEvents.SOUL_GRADE_CHANGED
         .register((STPlayerEvents.SoulGradeChangedCallback)(player, before, after) -> submitMatching(player, ReachSoulGradeTask.class, task -> true));
      STPlayerEvents.SKILL_LOCK_CHANGED.register((STPlayerEvents.SkillLockChangedCallback)(player, skillId, locked) -> {
         if (locked) {
            withContext(SKILL_LOCKING, player, skillId, () -> submitMatching(player, LockSkillTask.class, task -> task.matchesSkill(skillId)));
         }
      });
      STPlayerEvents.QUEST_COMPLETED
         .register(
            (STPlayerEvents.QuestCompletedCallback)(player, questId) -> withContext(
               QUEST_COMPLETING, player, questId, () -> submitMatching(player, STExtrasQuestCompleteTask.class, task -> task.matchesQuest(questId))
            )
         );
      STPlayerEvents.RACE_PRESTIGE_COMPLETED
         .register(
            (STPlayerEvents.RacePrestigeCompletedCallback)(player, raceId) -> withContext(
               RACE_PRESTIGE_COMPLETING, player, raceId, () -> submitMatching(player, RacePrestigeTask.class, task -> task.matchesRace(raceId))
            )
         );
   }

   public static boolean isPrestigeSubmitting(ServerPlayer player) {
      return player != null && PRESTIGE_SUBMITTING.contains(player.getUUID());
   }

   public static boolean isQuestCompleting(ServerPlayer player, String configuredQuestId) {
      return matchesContext(QUEST_COMPLETING, player, configuredQuestId);
   }

   public static boolean isRacePrestigeCompleting(ServerPlayer player, String configuredRaceId) {
      return matchesContext(RACE_PRESTIGE_COMPLETING, player, configuredRaceId);
   }

   public static boolean isSkillLocking(ServerPlayer player, String configuredSkillId) {
      return matchesContext(SKILL_LOCKING, player, configuredSkillId);
   }

   private static void onPrestige(ServerPlayer player) {
      if (player != null) {
         UUID uuid = player.getUUID();
         PRESTIGE_SUBMITTING.add(uuid);

         try {
            submitMatching(player, PrestigeTask.class, task -> true);
         } finally {
            PRESTIGE_SUBMITTING.remove(uuid);
         }
      }
   }

   private static <T extends Task> void submitMatching(ServerPlayer player, Class<T> taskClass, Predicate<T> filter) {
      if (player != null && ServerQuestFile.INSTANCE != null) {
         TeamData teamData = TeamData.get(player);
         if (teamData != null) {
            for (QuestObjectBase object : ServerQuestFile.INSTANCE.getAllObjects()) {
               if (taskClass.isInstance(object)) {
                  T task = (T)taskClass.cast(object);
                  if (filter.test(task) && !teamData.isCompleted(task)) {
                     task.submitTask(teamData, player);
                  }
               }
            }
         }
      }
   }

   private static void withContext(Map<UUID, ResourceLocation> context, ServerPlayer player, ResourceLocation id, Runnable action) {
      if (player != null) {
         UUID uuid = player.getUUID();
         ResourceLocation previous = context.put(uuid, id);

         try {
            action.run();
         } finally {
            if (previous == null) {
               context.remove(uuid);
            } else {
               context.put(uuid, previous);
            }
         }
      }
   }

   private static boolean matchesContext(Map<UUID, ResourceLocation> context, ServerPlayer player, String configuredId) {
      return player == null ? false : matchesConfiguredId(configuredId, context.get(player.getUUID()));
   }

   private static boolean matchesConfiguredId(String configuredId, ResourceLocation actualId) {
      if (actualId == null) {
         return false;
      } else if (configuredId != null && !configuredId.isBlank()) {
         ResourceLocation parsed = ResourceLocation.tryParse(configuredId.trim());
         return actualId.equals(parsed);
      } else {
         return true;
      }
   }
}
