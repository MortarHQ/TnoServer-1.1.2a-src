package io.github.Memoires.mysticism.config.ability.skill;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class ExtraMysticismSkillConfig extends ManasConfig {
   public ExtraMysticismSkillConfig.DarknessManipulation DarknessManipulation = new ExtraMysticismSkillConfig.DarknessManipulation();
   public ExtraMysticismSkillConfig.GravityFlux GravityFlux = new ExtraMysticismSkillConfig.GravityFlux();
   public ExtraMysticismSkillConfig.LightManipulation LightManipulation = new ExtraMysticismSkillConfig.LightManipulation();
   public ExtraMysticismSkillConfig.MithrilStrength MithrilStrength = new ExtraMysticismSkillConfig.MithrilStrength();
   public ExtraMysticismSkillConfig.IceManipulation IceManipulation = new ExtraMysticismSkillConfig.IceManipulation();
   public ExtraMysticismSkillConfig.CryogenicCessation CryogenicCessation = new ExtraMysticismSkillConfig.CryogenicCessation();
   public ExtraMysticismSkillConfig.ProfanedProminence ProfanedProminence = new ExtraMysticismSkillConfig.ProfanedProminence();

   public String getFileName() {
      return "mysticism/ability/skill/extra_config";
   }

   public static class CryogenicCessation extends ManasSubConfig {
      @Comment("Magicule cost for Ice Breath.")
      public double iceBreathMPCost = 500.0;
      @Comment("The damage each second of the Ice Breath.")
      public int damage = 16;
      @Comment("The damage each second of the Ice Breath when mastered.")
      public int damageMastered = 32;
      @Comment("Magicule cost for Freezing Point.")
      public double freezingPointMPCost = 1000.0;
      @Comment("The radius of freezing point.")
      public int radius = 8;
      @Comment("The cooldown of Freezing Point when it's mastered.")
      public int freezingPointCooldownMastered = 10;
      @Comment("The cooldown of Freezing Point.")
      public int freezingPointCooldown = 20;
      @Comment("The Ice Damage Boost when Cryogenic Cessation is toggled.")
      public double cryogenicCessationBoost = 1.5;
      @Comment("The duration of the chill effect in ticks. (Multiply seconds by 20.)")
      public int duration = 200;
      @Comment("The level of the chill effect.")
      public int level = 1;
      @Comment("The level of the chill effect when the skill is mastered.")
      public int levelMastered = 3;
   }

   public static class DarknessManipulation extends ManasSubConfig {
      @Comment("The number of mastered darkness skills needed to learn Darkness Manipulation.")
      public double darknessSkillAcquirement = 3.0;
      @Comment("EP Requirement for to learn Domination.")
      public double dominationEpAcquirement = 2000000.0;
      @Comment("EP Requirement for to use Resist Degradation when mastered.")
      public double resistDegradationAcquirement = 2000000.0;
      @Comment("The Darkness Damage Boost when activated Manipulation.")
      public double manipulationBoost = 1.5;
      @Comment("The Darkness Damage Boost when activated Domination.")
      public double dominationBoost = 3.0;
      @Comment("Magicule Cost to use.")
      public double magiculeCost = 800.0;
      @Comment("The range in block of the magic.")
      public double range = 15.0;
      @Comment("The radius in block of the cube.")
      public float cubeRadius = 5.0F;
      @Comment("The damage each 10 ticks of the cube.")
      public float cubeDamage = 10.0F;
      @Comment("The damage each 10 ticks of the cube when mastered.")
      public float cubeDamageMastered = 20.0F;
      @Comment("The level of Movement Interference effect (-10% speed each) when applied by the cube.")
      public int cubeSpeed = 5;
      @Comment("The duration in tick of the cube when casted.")
      public int cubeDuration = 300;
      @Comment("The cooldown in tick of the magic.")
      public int cooldown = 4;
      @Comment("The cooldown in tick of the magic when mastered.")
      public int cooldownMastered = 2;
      @Comment("The damage of the magic.")
      public float damage = 20.0F;
   }

   public static class GravityFlux extends ManasSubConfig {
      @Comment("The targeting range of the skill.")
      public double range = 15.0;
      @Comment("The targeting range of the skill when mastered.")
      public double rangeMastered = 20.0;
      @Comment("The duration of the gravity fluctuation effect from the Fluctuate mode in ticks. (Multiply seconds by 20.)")
      public double gravityDuration = 200.0;
      @Comment("The duration of the gravity fluctuation effect from the Fluctuate mode when mastered, in ticks. (Multiply seconds by 20.)")
      public double gravityDurationMastered = 200.0;
      @Comment("The level of the gravity fluctuation effect from the Fluctuate mode.")
      public int gravityLevel = 1;
      @Comment("The level of the gravity fluctuation effect from the Fluctuate mode when the skill is mastered.")
      public int gravityLevelMastered = 2;
      @Comment("The duration of the nausea effect from the Fluctuate mode in ticks. (Multiply seconds by 20.)")
      public double nauseaDuration = 200.0;
      @Comment("The duration of the nausea effect from the Fluctuate mode when mastered, in ticks. (Multiply seconds by 20.)")
      public double nauseaDurationMastered = 200.0;
      @Comment("The level of the nausea effect from the Fluctuate mode.")
      public int nauseaLevel = 1;
      @Comment("The level of the nausea effect from the Fluctuate mode when the skill is mastered.")
      public int nauseaLevelMastered = 2;
      @Comment("The duration of the burden effect from the Hamper mode in ticks. (Multiply seconds by 20.)")
      public double burdenDuration = 200.0;
      @Comment("The duration of the burden effect from the Hamper mode when mastered, in ticks. (Multiply seconds by 20.)")
      public double burdenDurationMastered = 200.0;
      @Comment("The level of the burden effect from the Hamper mode.")
      public int burdenLevel = 1;
      @Comment("The level of the burden effect from the Hamper mode when the skill is mastered.")
      public int burdenLevelMastered = 2;
   }

   public static class IceManipulation extends ManasSubConfig {
      @Comment("The number of mastered ice skills needed to learn Ice Manipulation.")
      public double iceSkillAcquirement = 1.0;
      @Comment("EP Requirement for to learn Domination.")
      public double dominationEpAcquirement = 2000000.0;
      @Comment("EP Requirement for to use Resist Degradation when mastered.")
      public double resistDegradationAcquirement = 2000000.0;
      @Comment("The Ice Damage Boost when activated Manipulation.")
      public double manipulationBoost = 1.5;
      @Comment("The Ice Damage Boost when activated Domination.")
      public double dominationBoost = 3.0;
      @Comment("Magicule cost for Ice Breath.")
      public double magiculeCost = 500.0;
      @Comment("The damage each second of the Ice Breath.")
      public int damage = 12;
      @Comment("The damage each second of the Ice Breath when mastered.")
      public int damageMastered = 24;
   }

   public static class LightManipulation extends ManasSubConfig {
      @Comment("The number of mastered light skills needed to learn Light Manipulation.")
      public double lightSkillAcquirement = 3.0;
      @Comment("EP Requirement for to learn Domination.")
      public double dominationEpAcquirement = 2000000.0;
      @Comment("EP Requirement for to use Resist Degradation when mastered.")
      public double resistDegradationAcquirement = 2000000.0;
      @Comment("The Light Damage Boost when activated Manipulation.")
      public double manipulationBoost = 1.5;
      @Comment("The Light Damage Boost when activated Domination.")
      public double dominationBoost = 3.0;
      @Comment("Magicule Cost to use.")
      public double magiculeCost = 200.0;
      @Comment("The range in block of the magic.")
      public float range = 30.0F;
      @Comment("The damage of the magic.")
      public float damage = 20.0F;
      @Comment("The number of arrows.")
      public int arrowNumber = 10;
      @Comment("The number of arrows when the skill is mastered.")
      public int arrowNumberMastered = 15;
   }

   public static class MithrilStrength extends ManasSubConfig {
      @Comment("Magicule Cost to activate.")
      public double magiculeCost = 10000.0;
      @Comment("The duration of the Strengthen effect when activated (doubled when mastered).")
      public int strengthenDuration = 1200;
      @Comment("The level of the Strengthen effect when activated (+3 Attack Damage per level).")
      public int strengthenLevel = 3;
      @Comment("The level of the Strengthen effect when activated when Mastered.")
      public int strengthenLevelMastered = 4;
   }

   public static class ProfanedProminence extends ManasSubConfig {
      @Comment("Magicule cost for Fire Breath.")
      public double fireBreathMPCost = 500.0;
      @Comment("The damage each second of the Fire Breath.")
      public int damage = 16;
      @Comment("The damage each second of the Fire Breath when mastered.")
      public int damageMastered = 32;
      @Comment("Magicule cost for Providence.")
      public double providenceMPCost = 1000.0;
      @Comment("The radius of Providence.")
      public int radius = 8;
      @Comment("The cooldown of Providence when it's mastered.")
      public int providenceCooldownMastered = 10;
      @Comment("The cooldown of Providence.")
      public int providenceCooldown = 20;
      @Comment("The Fire Damage Boost when Profaned Prominence is toggled.")
      public double profanedProminenceBoost = 1.5;
      @Comment("How long in tick that the target will be set on fire when attacked with Prominence's Toggle (doubled with Mastery).")
      public int prominenceBurnTick = 300;
   }
}
