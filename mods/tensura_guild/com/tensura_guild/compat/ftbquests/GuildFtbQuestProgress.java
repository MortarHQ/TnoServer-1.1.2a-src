package com.tensura_guild.compat.ftbquests;

import com.tensura_guild.data.GuildLevelData;
import dev.architectury.hooks.level.entity.PlayerHooks;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.Task;
import net.minecraft.server.level.ServerPlayer;

public final class GuildFtbQuestProgress {
   private GuildFtbQuestProgress() {
   }

   public static void onDwarfTaskCompleted(ServerPlayer player) {
      submitTasks(player, CompleteDwarfTask.class, AbstractGuildEventTask::recordCompletion);
   }

   public static void onGuildTaskCompleted(ServerPlayer player, String rank) {
      String normalizedRank = GuildLevelData.normalizeRank(rank);
      submitTasks(player, CompleteGuildTask.class, (task, teamData, serverPlayer) -> task.recordCompletion(teamData, serverPlayer, normalizedRank));
   }

   public static void onJoinedGuild(ServerPlayer player) {
      submitTasks(player, JoinGuildTask.class, (task, teamData, serverPlayer) -> task.submitTask(teamData, serverPlayer));
   }

   public static void onAdventureExperienceChanged(ServerPlayer player) {
      submitTasks(player, AdventureExperienceTask.class, AdventureExperienceTask::recordProgress);
   }

   private static <T extends Task> void submitTasks(ServerPlayer player, Class<T> taskClass, GuildFtbQuestProgress.TaskSubmitter<T> submitter) {
      if (player != null && !PlayerHooks.isFake(player) && ServerQuestFile.INSTANCE != null) {
         ServerQuestFile.INSTANCE.getTeamData(player).ifPresent(teamData -> {
            for (Task task : ServerQuestFile.INSTANCE.getAllTasks()) {
               if (taskClass.isInstance(task)) {
                  submitter.submit(taskClass.cast(task), teamData, player);
               }
            }
         });
      }
   }

   @FunctionalInterface
   private interface TaskSubmitter<T extends Task> {
      void submit(T var1, TeamData var2, ServerPlayer var3);
   }
}
