package io.github.Memoires.mysticism.config.ability.skill;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class IntrinsicMysticismSkillConfig extends ManasConfig {
   public IntrinsicMysticismSkillConfig.BulletPunch BulletPunch = new IntrinsicMysticismSkillConfig.BulletPunch();
   public IntrinsicMysticismSkillConfig.ClangorousSoul ClangorousSoul = new IntrinsicMysticismSkillConfig.ClangorousSoul();
   public IntrinsicMysticismSkillConfig.Discharge Discharge = new IntrinsicMysticismSkillConfig.Discharge();
   public IntrinsicMysticismSkillConfig.Dissonance Dissonance = new IntrinsicMysticismSkillConfig.Dissonance();
   public IntrinsicMysticismSkillConfig.Exoskeleton Exoskeleton = new IntrinsicMysticismSkillConfig.Exoskeleton();
   public IntrinsicMysticismSkillConfig.HellGate HellGate = new IntrinsicMysticismSkillConfig.HellGate();
   public IntrinsicMysticismSkillConfig.HellHall HellHall = new IntrinsicMysticismSkillConfig.HellHall();
   public IntrinsicMysticismSkillConfig.LethalPoison LethalPoison = new IntrinsicMysticismSkillConfig.LethalPoison();
   public IntrinsicMysticismSkillConfig.LightningMode LightningMode = new IntrinsicMysticismSkillConfig.LightningMode();
   public IntrinsicMysticismSkillConfig.MagisteelBody MagisteelBody = new IntrinsicMysticismSkillConfig.MagisteelBody();
   public IntrinsicMysticismSkillConfig.Relapse Relapse = new IntrinsicMysticismSkillConfig.Relapse();
   public IntrinsicMysticismSkillConfig.Spark Spark = new IntrinsicMysticismSkillConfig.Spark();
   public IntrinsicMysticismSkillConfig.Tenacity Tenacity = new IntrinsicMysticismSkillConfig.Tenacity();

   public String getFileName() {
      return "mysticism/ability/skill/intrinsic_config";
   }

   public static class BulletPunch extends ManasSubConfig {
      @Comment("The cooldown in seconds.")
      public int cooldown = 15;
      @Comment("The cooldown in seconds when the skill is mastered.")
      public int cooldownMastered = 15;
      @Comment("The amount of punches.")
      public int amount = 4;
   }

   public static class ClangorousSoul extends ManasSubConfig {
      @Comment("The radius of the taunt.")
      public int tauntRadius = 10;
      @Comment("The radius of the taunt when the skill is mastered.")
      public int tauntRadiusMastered = 15;
      @Comment("The cooldown in seconds.")
      public int cooldown = 60;
      @Comment("The cooldown in seconds when the skill is mastered.")
      public int cooldownMastered = 45;
   }

   public static class Discharge extends ManasSubConfig {
      @Comment("Magicule Cost to activate.")
      public double magiculeCost = 1000.0;
      @Comment("How much damage that the Lightning Bolt does when activated.")
      public int boltDamage = 15;
      @Comment("The range for damage that the Lightning Bolt does when activated.")
      public int boltRange = 3;
      @Comment("The selection range of the target in blocks.")
      public int selectionRange = 30;
      @Comment("The cooldown in seconds.")
      public int cooldown = 5;
   }

   public static class Dissonance extends ManasSubConfig {
      @Comment("Magicule Cost to activate.")
      public double magiculeCost = 10000.0;
      @Comment("How much damage that the Lightning Bolt does when activated.")
      public int boltDamage = 30;
      @Comment("The range for damage that the Lightning Bolt does when activated.")
      public int boltRange = 5;
      @Comment("The selection range of the target in blocks.")
      public int selectionRange = 30;
      @Comment("The cooldown in seconds.")
      public int cooldown = 5;
      @Comment("The radius of effect.")
      public int radius = 5;
   }

   public static class Exoskeleton extends ManasSubConfig {
      @Comment("The amount of EP needed for Hihi'irokane Armor's stats.")
      public double hihiirokaneEP = 4000000.0;
      @Comment("The amount of EP needed for Monster Leather Special A Armor's stats.")
      public double mlSAEP = 800000.0;
      @Comment("The amount of EP needed for Monster Leather A Armor's stats.")
      public double mlAEP = 80000.0;
      @Comment("The amount of EP needed for Monster Leather B Armor's stats.")
      public double mlBEP = 50000.0;
   }

   public static class HellGate extends ManasSubConfig {
      @Comment("The cooldown in seconds.")
      public int cooldown = 600;
      @Comment("The cooldown in seconds when the skill is mastered.")
      public int cooldownMastered = 300;
   }

   public static class HellHall extends ManasSubConfig {
      @Comment("Magicule Cost to activate.")
      public double magiculeCost = 5000.0;
      @Comment("The cooldown in seconds.")
      public int cooldown = 600;
      @Comment("The cooldown in seconds when the skill is mastered.")
      public int cooldownMastered = 600;
      @Comment("The radius.")
      public int radius = 4;
      @Comment("The radius when the skill is mastered.")
      public int radiusMastered = 4;
   }

   public static class LethalPoison extends ManasSubConfig {
      @Comment("The duration of the poison effect in ticks. (Multiply seconds by 20.)")
      public int duration = 200;
      @Comment("The level of the poison effect.")
      public int level = 3;
      @Comment("The level of the poison effect when the skill is mastered.")
      public int levelMastered = 6;
   }

   public static class LightningMode extends ManasSubConfig {
      @Comment("Magicule Cost to activate.")
      public double magiculeCost = 0.0;
      @Comment("The duration in tick of the Transformation.")
      public int transformationDuration = 3600;
      @Comment("The duration in tick of the Transformation.")
      public int transformationDurationMastered = 7200;
      @Comment("The Cooldown in second after activation.")
      public int cooldown = 1200;
   }

   public static class MagisteelBody extends ManasSubConfig {
      @Comment("The amount of EP needed for Hihi'irokane Armor's stats.")
      public double hihiirokaneEP = 1000000.0;
      @Comment("The amount of EP needed for Adamantite's stats.")
      public double adamantiteEP = 500000.0;
      @Comment("The amount of EP needed for Pure Magisteel's stats.")
      public double pureMagisteelEP = 100000.0;
      @Comment("The amount of EP needed for High Magisteel's stats.")
      public double highMagisteelEP = 50000.0;
   }

   public static class Relapse extends ManasSubConfig {
      @Comment("The level of Fragility given to a clone created by the Aleph mode, when the user is a Forgotten.")
      public int alephCloneFragilityForgotten = 2;
      @Comment("The level of Slowness given to a clone created by the Aleph mode, when the user is a Forgotten.")
      public int alephCloneSlownessForgotten = 5;
      @Comment("The level of Fragility given to a clone created by the Aleph mode, when the user is a Remnant.")
      public int alephCloneFragilityRemnant = 2;
      @Comment("The level of Slowness given to a clone created by the Aleph mode, when the user is a Remnant.")
      public int alephCloneSlownessRemnant = 4;
      @Comment("The level of Fragility given to a clone created by the Aleph mode, when the user is an Empty or Whole.")
      public int alephCloneFragilityEmptyWhole = 1;
      @Comment("The level of Slowness given to a clone created by the Aleph mode, when the user is an Empty or Whole.")
      public int alephCloneSlownessEmptyWhole = 3;
      @Comment("The level of Fragility given to a clone created by the Aleph mode, when the user is a Revenant or Ascended.")
      public int alephCloneFragilityRevenantAscended = 1;
      @Comment("The level of Slowness given to a clone created by the Aleph mode, when the user is a Revenant or Ascended.")
      public int alephCloneSlownessRevenantAscended = 1;
      @Comment("The level of Fragility given to a clone created by the Aleph mode, when the user is a Divine Inferius or Divine Excelsius.")
      public int alephCloneFragilityInferiusExcelsius = 0;
      @Comment("The level of Slowness given to a clone created by the Aleph mode, when the user is a Divine Inferius or Divine Excelsius.")
      public int alephCloneSlownessInferiusExcelsius = 0;
      @Comment("The cooldown of the Aleph mode, when the user is a Forgotten in seconds.")
      public int alephCooldownForgotten = 300;
      @Comment("The cooldown of the Aleph mode, when the user is a Remnant in seconds.")
      public int alephCooldownRemnant = 240;
      @Comment("The cooldown of the Aleph mode, when the user is an Empty or Whole in seconds.")
      public int alephCooldownEmptyWhole = 180;
      @Comment("The cooldown of the Aleph mode, when the user is a Revenant or Ascended in seconds.")
      public int alephCooldownRevenantAscended = 120;
      @Comment("The cooldown of the Aleph mode, when the user is a Divine Inferius or Divine Excelsius in seconds.")
      public int alephCooldownInferiusExcelsius = 60;
      @Comment("The amount of clones summoned with the Bet mode, when the user is a Remnant.")
      public int betClonesSummonedRemnant = 5;
      @Comment("The amount of clones summoned with the Bet mode, when the user is an Empty or Whole.")
      public int betClonesSummonedEmptyWhole = 7;
      @Comment("The amount of clones summoned with the Bet mode, when the user is a Revenant or Ascended.")
      public int betClonesSummonedRevenantAscended = 10;
      @Comment("The amount of clones summoned with the Bet mode, when the user is a Divine Inferius or Divine Excelsius.")
      public int betClonesSummonedInferiusExcelsius = 10;
      @Comment("The cooldown of the Bet mode, when the user is a Remnant in seconds.")
      public int betCooldownRemnant = 300;
      @Comment("The cooldown of the Bet mode, when the user is an Empty or Whole in seconds.")
      public int betCooldownEmptyWhole = 180;
      @Comment("The cooldown of the Bet mode, when the user is a Revenant or Ascended in seconds.")
      public int betCooldownRevenantAscended = 120;
      @Comment("The cooldown of the Bet mode, when the user is a Divine Inferius or Divine Excelsius in seconds.")
      public int betCooldownInferiusExcelsius = 60;
      @Comment("The number of kills required for the user to use Gimel if the user is an Empty.")
      public int gimelEmptyKills = 10;
      @Comment("The number of kills required for the user to use Gimel if the user is a Revenant.")
      public int gimelRevenantKills = 50;
      @Comment("The number of kills required for the user to use Gimel if the user has achieved Divinity.")
      public int gimelInferiusKills = 100;
      @Comment("The cooldown of the Gimel mode if the user is an Empty.")
      public int gimelCooldownEmpty = 180;
      @Comment("The cooldown of the Gimel mode if the user is a Revenant.")
      public int gimelCooldownRevenant = 120;
      @Comment("The cooldown of the Gimel mode if the user is a Divine Inferius.")
      public int gimelCooldownInferius = 60;
      @Comment("The number of beams that descend onto a target if the user is a Whole.")
      public int gimelBeamsWhole = 3;
      @Comment("The damage of the beams that descend onto a target if the user is a Whole.")
      public float gimelDamageWhole = 30.0F;
      @Comment("The number of beams that descend onto a target if the user is an Ascended.")
      public int gimelBeamsAscended = 5;
      @Comment("The damage of the beams that descend onto a target if the user is an Ascended.")
      public float gimelDamageAscended = 40.0F;
      @Comment("The number of beams that descend onto a target if the user is a Divine Excelsius.")
      public int gimelBeamsExcelsius = 10;
      @Comment("The damage of the beams that descend onto a target if the user is a Divine Excelsius.")
      public float gimelDamageExcelsius = 50.0F;
      @Comment("The duration in seconds that target marking will last while Gimel is activated on the Excelsius path.")
      public int gimelDurationHoly = 30;
      @Comment("The cooldown of the Gimel mode if the user is a Whole.")
      public int gimelCooldownWhole = 180;
      @Comment("The cooldown of the Gimel mode if the user is an Ascended.")
      public int gimelCooldownAscended = 120;
      @Comment("The cooldown of the Gimel mode if the user is a Divine Excelsius.")
      public int gimelCooldownExcelsius = 60;
      @Comment("The duration of the Daleth mode if the user is a Revenant, in seconds.")
      public int dalethDurationRevenant = 30;
      @Comment("The cooldown of the Daleth mode if the user is a Divine Inferius, in seconds.")
      public int dalethDurationInferius = 60;
      @Comment("The duration of the Daleth mode if the user is an Ascended, in seconds.")
      public int dalethDurationAscended = 30;
      @Comment("The cooldown of the Daleth mode if the user is a Divine Excelsius, in seconds.")
      public int dalethDurationExcelsius = 60;
      @Comment("The cooldown of the Daleth mode if the user is a Revenant, in seconds.")
      public int dalethCooldownRevenant = 600;
      @Comment("The cooldown of the Daleth mode if the user is a Divine Inferius, in seconds.")
      public int dalethCooldownInferius = 300;
      @Comment("The cooldown of the Daleth mode if the user is an Ascended, in seconds.")
      public int dalethCooldownAscended = 600;
      @Comment("The cooldown of the Daleth mode if the user is a Divine Excelsius, in seconds.")
      public int dalethCooldownExcelsius = 300;
      @Comment("The cooldown placed on both Aleph and Aleph: Null when Aleph: Null is used as a Divine Inferius.")
      public int alephNullCooldownInferius = 600;
      @Comment("The seconds of Collective given to the user if the user uses Aleph: Null as a Divine Inferius.")
      public int alephNullInferiusCollective = 120;
      @Comment("The cooldown placed on both Aleph and Aleph: Null when Aleph: Null is used as a Divine Excelsius.")
      public int alephNullCooldownExcelsius = 300;
      @Comment("The range of the Aleph: Null mode, when the user is a Divine Excelsius.")
      public int alephNullExcelsiusRange = 20;
      @Comment("The seconds of invincibility given to an Aleph clone if the user uses Aleph: Null as a Divine Excelsius.")
      public int alephNullExcelsiusInvincibility = 60;
   }

   public static class Spark extends ManasSubConfig {
      @Comment("The duration of the burn effect in ticks. (Multiply seconds by 20.)")
      public int burnDuration = 100;
      @Comment("The duration of the burn effect when mastered, in ticks. (Multiply seconds by 20.)")
      public int burnDurationMastered = 200;
      @Comment("The cooldown in seconds.")
      public int cooldown = 15;
      @Comment("The cooldown when mastered, in seconds.")
      public int cooldownMastered = 15;
   }

   public static class Tenacity extends ManasSubConfig {
      @Comment("Aura Cost per HP regenerated.")
      public double auraCost = 100.0;
      @Comment("Aura Cost per HP regenerated when mastered.")
      public double auraCostMastered = 60.0;
      @Comment("Should Tenacity heal the spiritual body when the skill is mastered?")
      public boolean shouldRegenSHPOnMastery = true;
      @Comment("Aura Cost per SHP regenerated.")
      public double shpAuraCost = 120.0;
      @Comment("Aura Cost per SHP regenerated when mastered.")
      public double shpAuraCostMastered = 80.0;
   }
}
