package com.trbeyond.tensura_better_subs.config;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;

@SyncToClient
public class ExpandedSkillConfig extends ManasConfig {
   public ExpandedSkillConfig.SkillGranting SkillGranting = new ExpandedSkillConfig.SkillGranting();
   @Comment("How many tensura:slime should be killed to acquire Self Regen")
   public int slimeKillsForRegen = 500;
   @Comment("How many centipedes should be killed to acquire Paralysis")
   public int centipedeKillsForParalysis = 50;
   @Comment("How many black spiders should be killed for Poison (1st requirement)")
   public int spiderKillsForPoison = 50;
   @Comment("How many spider eyes should be eaten to acquire Poison (2nd requirement)")
   public int spiderEyesEatenForPoison = 25;
   @Comment("How many entities should be killed to acquire Strength (1st requirement)")
   public int killsForStrength = 100;
   @Comment("How much EP required to acquire Strength (2nd requirement)")
   public double epForStrength = 4000.0;
   @Comment("How may entities should be killed to acquire Steel Strength (1st requirement)")
   public int killsForSteelStrength = 1000;
   @Comment("How much EP required to acquire Steel Strength (2nd requirement)")
   public double epForSteelStrength = 20000.0;
   @Comment("How much damage needs to be taken to acquire Strengthen Body")
   public float damageTakenForStrengthenBody = 5000.0F;
   @Comment("How much EP required to acquire Strengthen Body")
   public double epForStrengthenBody = 20000.0;
   @Comment("How much EP required to acquire Water Current Control")
   public double epForWaterCurrentControl = 7000.0;
   @Comment("How much EP required to acquire Haki (Entities getting Haki does nothing currently)")
   public double epForHaki = 100000.0;
   @Comment("How much EP required to acquire Infinite Regeneration (additionally requires Ultraspeed Regeneration mastered)")
   public double epForInfiniteRegeneration = 1000000.0;
   @Comment("Should owners be able to see subordinate player skills")
   public boolean subordinatePlayerSkillMenu = false;
   @Comment("Should owners be able to manipulate subordinate player skills (requires subordinatePlayerSkillMenu set to true to work)")
   public boolean subordinatePlayerSkillManipulation = false;

   public String getFileName() {
      return "tensura_better_subs";
   }

   public static class SkillGranting extends ManasSubConfig {
      @Comment("Multiplier of the acquisition cost subtracted from owner's current Magicules")
      public double ownerCostMultiplier = 10.0;
      @Comment("Ability granting cooldown")
      public int skillGrantCooldown = 1200;
      @Comment("Overall acquisition cost multiplier")
      public double skillGrantCostMultiplier = 1.0;
      @Comment("Allow the granting of abilities without a default acquisition cost (Ex. Magic, Battlewills)")
      public boolean allowNonSkillGrants = false;
      @Comment("Default cost for abilities without a cost. Granting abilities without a default cost needs to be allowed for this to take effect.")
      public double defaultAbilityGrantCost = 5000.0;
   }
}
