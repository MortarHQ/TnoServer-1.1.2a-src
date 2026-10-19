package org.crypticdev.stextras.quest.assignment;

import io.github.manasmods.tensura.storage.TensuraStorages;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.quest.schedule.QuestSchedule;
import org.crypticdev.stextras.quest.schedule.QuestScheduleSavedData;
import org.crypticdev.stextras.quest.schedule.QuestScheduleService;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;

public final class PlayerQuestAssignmentService {
   public static void setupQuests(MinecraftServer server, ServerPlayer player) {
      QuestScheduleService.ensureScheduleUpToDate(server);
      ensurePlayerMatchesSchedule(server, player);
      ensureRequiredApplied(player);
      RepeatableQuestService.ensureRepeatablesApplied(player);
      RacePrestigeAssignmentService.ensureForCurrentRace(player, false, false);
   }

   public static void ensurePlayerMatchesSchedule(MinecraftServer server, ServerPlayer player) {
      QuestSchedule schedule = QuestScheduleSavedData.get(server).schedule();
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      store.pruneUnavailableQuestIds();
      boolean fresh = store.validateAgainst(schedule);
      applyCurrentToPlayer(server, player, fresh);
   }

   public static void applyCurrentToPlayer(MinecraftServer server, ServerPlayer player, boolean freshReroll) {
      ScheduledQuestAssignmentService.applyCurrentToPlayer(server, player, freshReroll);
   }

   public static void ensureRacePrestigeForCurrentRace(ServerPlayer player) {
      RacePrestigeAssignmentService.ensureForCurrentRace(player);
   }

   public static void ensureRacePrestigeForCurrentRace(ServerPlayer player, boolean statFirst) {
      RacePrestigeAssignmentService.ensureForCurrentRace(player, statFirst);
   }

   public static boolean tryCompleteRacePrestigeForCurrentRace(ServerPlayer player) {
      return RacePrestigeAssignmentService.tryCompleteForCurrentRace(player);
   }

   public static void rerollPlayerQuestsDaily(MinecraftServer server, ServerPlayer player) {
      ScheduledQuestAssignmentService.rerollPlayerQuestsDaily(server, player);
   }

   public static void rerollPlayerQuestsWeekly(MinecraftServer server, ServerPlayer player) {
      ScheduledQuestAssignmentService.rerollPlayerQuestsWeekly(server, player);
   }

   public static void rerollPlayerQuestsBoth(MinecraftServer server, ServerPlayer player) {
      ScheduledQuestAssignmentService.rerollPlayerQuestsBoth(server, player);
   }

   public static void rerollPlayerQuests(MinecraftServer server, ServerPlayer player, boolean rerollDaily, boolean rerollWeekly) {
      ScheduledQuestAssignmentService.rerollPlayerQuests(server, player, rerollDaily, rerollWeekly);
   }

   private static void ensureRequiredApplied(ServerPlayer player) {
      Set<ResourceLocation> active = STExtarsStorage.Quests.active(player);
      Set<ResourceLocation> completed = STExtarsStorage.Quests.completed(player);
      if (active.isEmpty() && completed.isEmpty()) {
         RequiredQuestSelector.applyRequiredOnCharacterReset(player, TensuraStorages.getPlayerDataFrom(player).getResetCounter());
      }
   }

   private PlayerQuestAssignmentService() {
   }
}
