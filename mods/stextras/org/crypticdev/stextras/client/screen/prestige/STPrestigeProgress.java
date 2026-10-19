package org.crypticdev.stextras.client.screen.prestige;

import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.utils.PrestigeUtils;

public final class STPrestigeProgress {
   private static final int BAR_PIXELS = 92;

   public static boolean[] computeConditions(Player player, Races raceData, IExistence existenceData, ITensuraPlayer tensuraPlayerData) {
      return new boolean[]{
         isMaxEvolution(player, raceData),
         isAwakened(existenceData),
         hasEnoughRe(tensuraPlayerData),
         hasBossDefeated(tensuraPlayerData),
         isRequiredQuestCompleted()
      };
   }

   public static STPrestigeProgress.BarState prestigeProgress(Player player, Races raceData, IExistence existenceData, ITensuraPlayer tensuraPlayerData) {
      double progress = baseProgressCompletedUnits(player, raceData, existenceData, tensuraPlayerData) / 5.0;
      return toBarState(progress);
   }

   public static STPrestigeProgress.BarState raceProgress(Player player, Races raceData, IExistence existenceData, ITensuraPlayer tensuraPlayerData) {
      Optional<ManasRaceInstance> currentRace = raceData.getRace();
      int completed = STExtarsStorage.Quests.getClient().completed(QuestCategory.RACE_PRESTIGE).size();
      int active = STExtarsStorage.Quests.getClient().active(QuestCategory.RACE_PRESTIGE).size();
      int total = completed + active;
      boolean raceCompleted = currentRace.<Boolean>map(race -> STExtarsStorage.Quests.hasCompletedRaceClient(race.getRaceId())).orElse(false);
      double questFraction;
      if (raceCompleted) {
         questFraction = 1.0;
      } else if (total <= 0) {
         questFraction = 0.0;
      } else {
         questFraction = Math.min(1.0, (double)completed / total);
      }

      double progress = (baseProgressCompletedUnits(player, raceData, existenceData, tensuraPlayerData) + questFraction) / 6.0;
      return toBarState(progress);
   }

   public static String formatCountdown(long seconds) {
      if (seconds <= 0L) {
         return "0s";
      }

      long days = seconds / 86400L;
      if (days > 0L) {
         return days + "D";
      }

      long hours = seconds % 86400L / 3600L;
      if (hours > 0L) {
         return hours + "h";
      }

      long mins = seconds % 3600L / 60L;
      return mins > 0L ? mins + "m" : seconds % 60L + "s";
   }

   private static double baseProgressCompletedUnits(Player player, Races raceData, IExistence existenceData, ITensuraPlayer tensuraPlayerData) {
      int resetCounter = tensuraPlayerData.getResetCounter();
      int reincarnationEssenceCost = PrestigeUtils.getReincarnationEssenceCost(resetCounter);
      int currentRe = STExtarsStorage.Player.getReincarnationEssence();
      double reFraction = reincarnationEssenceCost <= 0 ? 1.0 : Math.min(1.0, (double)currentRe / reincarnationEssenceCost);
      return (isMaxEvolution(player, raceData) ? 1.0 : 0.0)
         + (isAwakened(existenceData) ? 1.0 : 0.0)
         + reFraction
         + (hasBossDefeated(tensuraPlayerData) ? 1.0 : 0.0)
         + (isRequiredQuestCompleted() ? 1.0 : 0.0);
   }

   private static boolean isMaxEvolution(Player player, Races raceData) {
      Optional<ManasRaceInstance> raceOptional = raceData.getRace();
      return raceOptional.isEmpty() ? false : raceOptional.get().getNextEvolutions(player).isEmpty();
   }

   private static boolean isAwakened(IExistence existenceData) {
      return existenceData.isTrueDemonLord() || existenceData.isTrueHero();
   }

   private static boolean hasEnoughRe(ITensuraPlayer tensuraPlayerData) {
      int resetCounter = tensuraPlayerData.getResetCounter();
      int reincarnationEssenceCost = PrestigeUtils.getReincarnationEssenceCost(resetCounter);
      return STExtarsStorage.Player.getReincarnationEssence() >= reincarnationEssenceCost;
   }

   private static boolean hasBossDefeated(ITensuraPlayer tensuraPlayerData) {
      int defeatedBosses = STExtarsStorage.Client.bossKills();
      int requiredBosses = PrestigeUtils.requiredBossKills(tensuraPlayerData.getResetCounter());
      return defeatedBosses >= requiredBosses;
   }

   private static boolean isRequiredQuestCompleted() {
      return STExtarsStorage.Quests.getClient().active(QuestCategory.REQUIRED).isEmpty();
   }

   private static STPrestigeProgress.BarState toBarState(double progress) {
      double clamped = Math.max(0.0, Math.min(1.0, progress));
      int percent = (int)Math.round(clamped * 100.0);
      int fillPixels = (int)Math.round(clamped * 92.0);
      return new STPrestigeProgress.BarState(fillPixels, percent);
   }

   private STPrestigeProgress() {
   }

   public record BarState(int fillPixels, int percent) {
   }
}
