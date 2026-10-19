package org.crypticdev.stextras.config;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import java.util.List;

@SyncToClient
public class STSkillLockConfig extends ManasConfig {
   @Comment("The amount of EP needed to unlock a skill.")
   public double getNeededEPToUnlockSkill = 1.0E7;
   @Comment("The Soul Grades Cost to unlock a skill.")
   public int getSoulGradeCostToUnlockSkill = 3;
   @Comment(
      "Max skills a player can lock based on their Soul Grade Level. \n Format: The value at each index represents the max locks for that level.\n Index 0 = Level 0, Index 1 = Level 1, etc."
   )
   public List<Integer> maxLocksPerLevel = List.of(0, 1, 1, 2, 2, 2, 3);
   @Comment("List of valid skills to lock.")
   public List<String> validSkillsTOLock = List.of(
      "tensura:absolute_severance",
      "tensura:analyst",
      "tensura:berserk",
      "tensura:berserker",
      "tensura:bewilder",
      "tensura:chef",
      "tensura:chosen_one",
      "tensura:commander",
      "tensura:cook",
      "tensura:creator",
      "tensura:degenerate",
      "tensura:engorger",
      "tensura:envy",
      "tensura:falsifier",
      "tensura:fighter",
      "tensura:fusionist",
      "tensura:gourmand",
      "tensura:gourmet",
      "tensura:great_sage",
      "tensura:greed",
      "tensura:guardian",
      "tensura:healer",
      "tensura:infinity_prison",
      "tensura:lust",
      "tensura:martial_master",
      "tensura:mathematician",
      "tensura:merciless",
      "tensura:murderer",
      "tensura:musician",
      "tensura:observer",
      "tensura:oppressor",
      "tensura:predator",
      "tensura:pride",
      "tensura:reflector",
      "tensura:researcher",
      "tensura:royal_beast",
      "tensura:reaper",
      "tensura:reverser",
      "tensura:seer",
      "tensura:severer",
      "tensura:shadow_striker",
      "tensura:sloth",
      "tensura:sniper",
      "tensura:spearhead",
      "tensura:suppressor",
      "tensura:survivor",
      "tensura:seeker",
      "tensura:traveler",
      "tensura:thrower",
      "tensura:tuner",
      "tensura:unyielding",
      "tensura:usurper",
      "tensura:villain",
      "tensura:wrath",
      "stextras:imitator"
   );
   @Comment("If the data in locked skill should be reset")
   public boolean RestSkillData = false;
   @Comment("If RestSkillData is false you can list Specific skill you do want to have reset")
   public List<String> RestSkillDataSkill = List.of("tensura:predator", "tensura:gourmet", "tensura:pride");

   public String getFileName() {
      return "stextras/skill_lock_config";
   }
}
