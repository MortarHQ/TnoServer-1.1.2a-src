package org.crypticdev.stextras.utils;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import java.util.Locale;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import org.crypticdev.stextras.config.STPrestigeConfig;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.crypticdev.stextras.net.S2C.STExtrasBossKillsSync;
import org.crypticdev.stextras.quest.assignment.PlayerQuestAssignmentService;
import org.crypticdev.stextras.quest.assignment.RequiredQuestSelector;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.storage.STExtarsStorage;

public class PrestigeUtils {
   private static final int[] PRESTIGE_RE_COSTS = new int[]{
      500, 625, 775, 900, 1125, 1350, 1675, 2000, 2775, 3525, 4300, 5525, 6750, 8325, 9925, 11500, 13800, 16100, 18400, 20700, 23000
   };
   public static final STPrestigeConfig CONFIG_PRESTIGE = (STPrestigeConfig)ConfigRegistry.getConfig(STPrestigeConfig.class);

   public static int getReincarnationEssenceCost(int prestige) {
      if (prestige <= 0) {
         return PRESTIGE_RE_COSTS[0];
      }

      if (prestige < PRESTIGE_RE_COSTS.length) {
         return PRESTIGE_RE_COSTS[prestige];
      }

      int cost = PRESTIGE_RE_COSTS[PRESTIGE_RE_COSTS.length - 1];

      for (int currentPrestige = PRESTIGE_RE_COSTS.length; currentPrestige <= prestige; currentPrestige++) {
         cost = roundToNearest25(cost * 1.12);
      }

      return cost;
   }

   public static boolean canPrestige(ServerPlayer player) {
      ITensuraPlayer tensuraPlayerData = TensuraStorages.getPlayerDataFrom(player);
      Races raceData = RaceAPI.getRaceFrom(player);
      IExistence existenceData = TensuraStorages.getExistenceFrom(player);
      boolean maxEvo = ((ManasRaceInstance)raceData.getRace().get()).getNextEvolutions(player).isEmpty();
      boolean awaken = existenceData.isTrueHero() || existenceData.isTrueDemonLord();
      boolean enoughRe = STExtarsStorage.Player.getReincarnationEssence(player) >= getReincarnationEssenceCost(tensuraPlayerData.getResetCounter());
      boolean boss = getBossKillCount(player) >= requiredBossKills(tensuraPlayerData.getResetCounter());
      boolean quests = STExtarsStorage.Quests.active(player, QuestCategory.REQUIRED).isEmpty();
      return maxEvo && awaken && enoughRe && boss && quests;
   }

   public static void doPrestige(ServerPlayer player) {
      if (canPrestige(player)) {
         STPlayerEvents.firePrestige(player);
         boolean racePrestige = PlayerQuestAssignmentService.tryCompleteRacePrestigeForCurrentRace(player);
         if (!racePrestige) {
            STExtarsStorage.Player.setSoulGrade(player, STExtarsStorage.Player.getSoulGrade(player) + 1);
         }

         switch (resolvePrestigeQuestRerollMode()) {
            case BOTH:
               PlayerQuestAssignmentService.rerollPlayerQuestsBoth(player.getServer(), player);
               break;
            case WEEKLY:
               PlayerQuestAssignmentService.rerollPlayerQuestsWeekly(player.getServer(), player);
               break;
            case DAILY:
               PlayerQuestAssignmentService.rerollPlayerQuestsDaily(player.getServer(), player);
            case NONE:
         }
      }
   }

   public static void resetPlayer(ServerPlayer player) {
      STExtarsStorage.Player.setReincarnationEssence(player, 0);
      STExtrasBossKillsSync.sendTo(player, getBossKillCount(player));
   }

   public static void applyPrestigeQuests(ServerPlayer player) {
      int prestige = TensuraStorages.getPlayerDataFrom(player).getResetCounter();
      RequiredQuestSelector.applyRequiredOnCharacterReset(player, prestige);
   }

   public static int getBossKillCount(ServerPlayer player) {
      int bossKillCount = 0;

      for (String bossId : CONFIG_PRESTIGE.validBosses) {
         ResourceLocation rl = ResourceLocation.tryParse(bossId);
         if (rl != null && BuiltInRegistries.ENTITY_TYPE.containsKey(rl)) {
            EntityType<?> entityType = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(rl);
            Stat<EntityType<?>> stat = Stats.ENTITY_KILLED.get(entityType);
            int kills = player.getStats().getValue(stat);
            if (CONFIG_PRESTIGE.allowRepeatedPrestigeBossKills) {
               bossKillCount += kills;
            } else if (kills > 0) {
               bossKillCount++;
            }
         }
      }

      return bossKillCount;
   }

   private static int getActualValidBossCount() {
      int validCount = 0;

      for (String bossId : CONFIG_PRESTIGE.validBosses) {
         ResourceLocation rl = ResourceLocation.tryParse(bossId);
         if (rl != null && BuiltInRegistries.ENTITY_TYPE.containsKey(rl)) {
            validCount++;
         }
      }

      return validCount;
   }

   public static int requiredBossKills(int prestige) {
      if (CONFIG_PRESTIGE.allowRepeatedPrestigeBossKills) {
         int bosses = (int)Math.round(1.0 + prestige * 0.55);
         return Math.max(1, Math.min(15, bosses));
      }

      int actualValidBosses = getActualValidBossCount();
      int maxBosses = Math.min(6, actualValidBosses);
      if (maxBosses <= 0) {
         return 0;
      }

      double scale = maxBosses * (1.0 - Math.exp(-0.25 * prestige));
      return Math.min(maxBosses, Math.max(1, (int)Math.ceil(scale)));
   }

   private static int roundToNearest25(double value) {
      return (int)(Math.round(value / 25.0) * 25L);
   }

   private static PrestigeUtils.PrestigeQuestRerollMode resolvePrestigeQuestRerollMode() {
      return PrestigeUtils.PrestigeQuestRerollMode.fromConfig(CONFIG_PRESTIGE.prestigeQuestRerollMode);
   }

   private enum PrestigeQuestRerollMode {
      BOTH,
      WEEKLY,
      DAILY,
      NONE;

      private static PrestigeUtils.PrestigeQuestRerollMode fromConfig(String raw) {
         if (raw != null && !raw.isBlank()) {
            try {
               return valueOf(raw.trim().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException ignored) {
               return BOTH;
            }
         } else {
            return BOTH;
         }
      }
   }
}
