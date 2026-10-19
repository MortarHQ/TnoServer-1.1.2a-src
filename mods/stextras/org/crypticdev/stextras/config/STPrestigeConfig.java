package org.crypticdev.stextras.config;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import java.util.List;

@SyncToClient
public class STPrestigeConfig extends ManasConfig {
   @Comment("List of valid bosses that count to your required boss kills.")
   public List<String> validBosses = List.of(
      "tensura:charybdis",
      "tensura:elemental_colossus",
      "tensura:hinata_sakaguchi",
      "tensura:orc_disaster",
      "tensura:shizu",
      "tensura:supermassive_slime",
      "tensura_neb:carrion",
      "tensura_neb:luminous_valentine",
      "tensura_neb:rimuru_ogre_fight"
   );
   @Comment("If true, the same boss can be killed repeatedly for prestige boss requirements. If false, each configured boss only counts once.")
   public boolean allowRepeatedPrestigeBossKills = true;
   @Comment("Should the QuestDefinition messages use small caps.")
   public boolean questsUseSmallCaps = true;
   @Comment("Should the quests RE rewards boost based on the players prestige.")
   public boolean questREBoost = true;
   @Comment("The percentage that the RE rewards will boost based on the players prestige.")
   public double questREBoostAmount = 0.05;
   @Comment("If true, daily quest RE rewards scale with prestige.")
   public boolean questREBoostDaily = true;
   @Comment("If true, weekly quest RE rewards scale with prestige.")
   public boolean questREBoostWeekly = true;
   @Comment("If true, required quest RE rewards scale with prestige.")
   public boolean questREBoostRequired = false;
   @Comment("If true, repeatable quest RE rewards scale with prestige.")
   public boolean questREBoostRepeatable = false;
   @Comment("If true, quests that are similor be skipped until a different quest can be assigned.")
   public boolean skipDupe = true;
   @Comment("If true, matching daily, weekly, required, and race prestige quests take priority over repeatable quests for the same progression event.")
   public boolean questPriority = true;
   @Comment("If true, race prestige quests are applied as soon as the player reaches max evolution")
   public boolean ImmediateRaceQuests = false;
   @Comment("If true, use the configured soul grade thresholds below instead of auto-splitting the total race prestige soul grade into 6 levels.")
   public boolean overrideSoulGradeLevels = false;
   @Comment("Additional soul grade added to the auto-calculated total when overrideSoulGradeLevels is false.")
   public int additionalSoulGradeForAutoLevels = 0;
   @Comment("Soul grade needed for levels 1 through 6 when overrideSoulGradeLevels is true.")
   public List<Integer> soulGradeLevelThresholds = List.of(20, 40, 60, 80, 100, 120);
   @Comment(
      "Race ids to disable from race prestige entirely. Disabled races are excluded from race prestige quests, race lists, and auto-calculated soul grade totals. Example: stextras:divine_fox"
   )
   public List<String> disabledRacePrestigeRaces = List.of();
   @Comment("Which quest schedules reroll when a player prestiges. Valid values: BOTH, WEEKLY, DAILY, NONE.")
   public String prestigeQuestRerollMode = "NONE";
   @Comment("If entities that are owned by you or other players will contribute to your kill quest progression")
   public boolean killOwnedEntities = false;
   @Comment("Prevents locked skills from reducing the number of unique skills granted during a reset.")
   public boolean ignoreLockedSkillsOnReset = false;

   public String getFileName() {
      return "stextras/prestige_config";
   }
}
