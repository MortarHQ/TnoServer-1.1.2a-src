package io.github.Memoires.mysticism.config.ability.skill;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import io.github.manasmods.tensura.enchantment.TensuraEnchantmentHelper;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.util.ArrayList;
import java.util.List;

public class UniqueMysticismSkillConfig extends ManasConfig {
   public UniqueMysticismSkillConfig.Butcher Butcher = new UniqueMysticismSkillConfig.Butcher();
   public UniqueMysticismSkillConfig.Captivator Captivator = new UniqueMysticismSkillConfig.Captivator();
   public UniqueMysticismSkillConfig.Coalescence Coalescence = new UniqueMysticismSkillConfig.Coalescence();
   public UniqueMysticismSkillConfig.Constant Constant = new UniqueMysticismSkillConfig.Constant();
   public UniqueMysticismSkillConfig.Corroder Corroder = new UniqueMysticismSkillConfig.Corroder();
   public UniqueMysticismSkillConfig.Crasher Crasher = new UniqueMysticismSkillConfig.Crasher();
   public UniqueMysticismSkillConfig.Cultivator Cultivator = new UniqueMysticismSkillConfig.Cultivator();
   public UniqueMysticismSkillConfig.Disintegration Disintegration = new UniqueMysticismSkillConfig.Disintegration();
   public UniqueMysticismSkillConfig.Dreamer Dreamer = new UniqueMysticismSkillConfig.Dreamer();
   public UniqueMysticismSkillConfig.Engineer Engineer = new UniqueMysticismSkillConfig.Engineer();
   public UniqueMysticismSkillConfig.Gardener Gardener = new UniqueMysticismSkillConfig.Gardener();
   public UniqueMysticismSkillConfig.HiddenRuler HiddenRuler = new UniqueMysticismSkillConfig.HiddenRuler();
   public UniqueMysticismSkillConfig.Jinchuriki Jinchuriki = new UniqueMysticismSkillConfig.Jinchuriki();
   public UniqueMysticismSkillConfig.Kyurem Kyurem = new UniqueMysticismSkillConfig.Kyurem();
   public UniqueMysticismSkillConfig.Looter Looter = new UniqueMysticismSkillConfig.Looter();
   public UniqueMysticismSkillConfig.Malleable Malleable = new UniqueMysticismSkillConfig.Malleable();
   public UniqueMysticismSkillConfig.Melancholy Melancholy = new UniqueMysticismSkillConfig.Melancholy();
   public UniqueMysticismSkillConfig.Phaser Phaser = new UniqueMysticismSkillConfig.Phaser();
   public UniqueMysticismSkillConfig.Provider Provider = new UniqueMysticismSkillConfig.Provider();
   public UniqueMysticismSkillConfig.Reducer Reducer = new UniqueMysticismSkillConfig.Reducer();
   public UniqueMysticismSkillConfig.Repeater Repeater = new UniqueMysticismSkillConfig.Repeater();
   public UniqueMysticismSkillConfig.Reshiram Reshiram = new UniqueMysticismSkillConfig.Reshiram();
   public UniqueMysticismSkillConfig.Restricted Restricted = new UniqueMysticismSkillConfig.Restricted();
   public UniqueMysticismSkillConfig.Saint Saint = new UniqueMysticismSkillConfig.Saint();
   public UniqueMysticismSkillConfig.Scholar Scholar = new UniqueMysticismSkillConfig.Scholar();
   public UniqueMysticismSkillConfig.Schrodinger Schrodinger = new UniqueMysticismSkillConfig.Schrodinger();
   public UniqueMysticismSkillConfig.Spiritualist Spiritualist = new UniqueMysticismSkillConfig.Spiritualist();
   public UniqueMysticismSkillConfig.Stagnator Stagnator = new UniqueMysticismSkillConfig.Stagnator();
   public UniqueMysticismSkillConfig.Subjugator Subjugator = new UniqueMysticismSkillConfig.Subjugator();
   public UniqueMysticismSkillConfig.TheBalance TheBalance = new UniqueMysticismSkillConfig.TheBalance();
   public UniqueMysticismSkillConfig.VictoriousHarbinger VictoriousHarbinger = new UniqueMysticismSkillConfig.VictoriousHarbinger();
   public UniqueMysticismSkillConfig.Zekrom Zekrom = new UniqueMysticismSkillConfig.Zekrom();
   public UniqueMysticismSkillConfig.Gatekeeper Gatekeeper = new UniqueMysticismSkillConfig.Gatekeeper();

   public String getFileName() {
      return "mysticism/ability/skill/unique_config";
   }

   public static class Butcher extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 80000.0;
      @Comment("The magicule cost of the Heighten mode.")
      public double heightenCost = 1500.0;
      @Comment("The magicule cost of the Rupture mode.")
      public double ruptureCost = 2500.0;
      @Comment("The number of marks required on an entity to trigger the Rupture mode.")
      public int ruptureMarkRequirement = 15;
      @Comment("The cooldown of the Rupture mode in seconds.")
      public int ruptureCooldown = 30;
      @Comment("Should the cooldown be per target or flat? TRUE for \"per target\", FALSE for flat cooldown.")
      public boolean ruptureCooldownMode = true;
      @Comment("The magicule cost of 血淋林的爱.")
      public double 血淋林的爱Cost = 3000.0;
      @Comment("The cooldown of the 血淋林的爱 Art in seconds.")
      public int 血淋林的爱Cooldown = 120;
      @Comment("The number of marks required to gain a charge of the 血淋林的爱 Art.")
      public int 血淋林的爱MarkRequirement = 20;
      @Comment("The amount of charges you gain when killing an enemy that meets the above threshold defined.")
      public int chargesGained = 1;
   }

   public static class Captivator extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 60000.0;
      @Comment("Magicule cost of Performance mode.")
      public double mpPerformanceCost = 250.0;
      @Comment("The cooldown of the Performance mode in seconds.")
      public int performanceCooldown = 60;
      @Comment("The range for the Performance mode in blocks.")
      public int performanceRange = 20;
      @Comment("Magicule cost of Masquerade mode.")
      public double mpMasqueradeCost = 300.0;
      @Comment("The cooldown of the Masquerade mode in seconds.")
      public int masqueradeCooldown = 30;
      @Comment("The range for the Masquerade mode in blocks.")
      public int masqueradeRange = 15;
      @Comment("Magicule cost of Unveil mode.")
      public double mpUnveilCost = 500.0;
      @Comment("The cooldown of the Unveil mode in seconds.")
      public int unveilCooldown = 30;
      @Comment("The range in blocks for Unveil")
      public int unveilRange = 15;
      @Comment("Magicule cost of Star Power Mode.")
      public double mpStarPowerCost = 1000.0;
      @Comment("The cooldown of the Star Power mode in seconds.")
      public int starPowerCooldown = 40;
   }

   public static class Coalescence extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 90000.0;
      @Comment("The magicule cost of the Fixation mode.")
      public double fixationCost = 1000.0;
      @Comment("The range of the Fixation mode in blocks.")
      public int fixationRange = 10;
      @Comment("The range of the Fixation mode in blocks when the skill is mastered.")
      public int fixationRangeMastery = 15;
      @Comment("The duration of the Fixation effect in ticks (seconds x 20).")
      public int fixationDuration = 200;
      @Comment("The duration of the Fixation effect in ticks (seconds x 20).")
      public int fixationDurationMastered = 400;
      @Comment("The cooldown of the Fixation mode in seconds.")
      public int fixationCooldown = 30;
      @Comment("The cooldown of the Fixation mode in seconds when the skill is mastered.")
      public int fixationCooldownMastered = 15;
      @Comment("The cooldown of the Fixation mode when the user misses.")
      public int fixationMissCooldown = 5;
      @Comment("The cooldown of the Fixation mode when the user misses and the skill is mastered.")
      public int fixationMissCooldownMastered = 5;
      @Comment("The magicule cost of the Solidification mode.")
      public double solidificationCost = 1000.0;
      @Comment("The cooldown of the solidification mode in seconds.")
      public int solidificationCooldown = 20;
      @Comment("The cooldown of the solidification mode in seconds when the skill is mastered.")
      public int solidificationCooldownMastered = 10;
      @Comment("The cooldown of the solidification shield in ticks.")
      public int solidificationShieldDuration = 400;
      @Comment("The cooldown of the solidification shield in ticks when the skill is mastered.")
      public int solidificationShieldDurationMastered = 800;
      @Comment("The magiule cost of the Eternal Domain art.")
      public double eternalDomainCost = 10000.0;
      @Comment("The radius of Eternal Domain in blocks.")
      public int eternalDomainRange = 8;
      @Comment("The radius of Eternal Domain in blocks when the skill is mastered.")
      public int eternalDomainRangeMastered = 16;
      @Comment("How long in ticks (seconds x 20) the Eternal Domain should last.")
      public int eternalDomainLife = 400;
      @Comment("How long in ticks (seconds x 20) the Eternal Domain should last when the skill is mastered.")
      public int eternalDomainLifeMastered = 800;
      @Comment("The cooldown of the Eternal Domain art in seconds.")
      public int eternalDomainCooldown = 180;
      @Comment("The cooldown of the Eternal Domain art in seconds when the skill is mastered.")
      public int eternalDomainCooldownMastered = 90;
   }

   public static class Constant extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 40000.0;
      @Comment("Magicule cost of the Constant: Health mode.")
      public double constantHealthCost = 1000.0;
      @Comment("Magicule cost of the Constant: Health mode that drains every second (20 ticks).")
      public double constantHealthTickCost = 1000.0;
      @Comment("The maximum amount of time in ticks (multiply seconds by 20) that you can hold down Constant: Health for.")
      public int constantHealthHeldTicks = 300;
      @Comment("The maximum amount of time in ticks (multiply seconds by 20) that you can hold down Constant: Health for when the skill is mastered.")
      public int constantHealthHeldTicksMastered = 300;
      @Comment("The cooldown of the Constant: Health mode.")
      public int constantHealthCooldown = 15;
      @Comment("The cooldown of the Constant: Health mode when the skill is mastered.")
      public int constantHealthCooldownMastered = 15;
      @Comment("Magicule cost of the Constant: Physical mode.")
      public double constantPhysicalCost = 0.0;
      @Comment("How long should the timer for Constant: Physical run for in ticks.")
      public int constantPhysicalTimer = 100;
      @Comment("The cooldown of the Constant: Physical mode.")
      public int constantPhysicalCooldown = 10;
      @Comment("The cooldown of the Constant: Physical mode.")
      public int constantPhysicalCooldownMastered = 10;
      @Comment("Magicule cost of the Constant: Destruction mode.")
      public double constantDestructionCost = 3000.0;
      @Comment("The maximum amount of time in ticks (multiply seconds by 20) that you can hold down Constant: Destruction for.")
      public int constantDestructionHeldTicks = 200;
      @Comment("The maximum amount of time in ticks (multiply seconds by 20) that you can hold down Constant: Destruction for when the skill is mastered.")
      public int constantDestructionHeldTicksMastered = 200;
      @Comment("The maximum damage that Constant: Destruction can have. (Default: 5000)")
      public float constantDestructionMaxDamage = 5000.0F;
      @Comment("The cooldown of the Constant: Destruction mode.")
      public int constantDestructionCooldown = 20;
      @Comment("The cooldown of the Constant: Destruction mode when the skill is mastered.")
      public int constantDestructionCooldownMastered = 20;
      @Comment("Magicule cost of the Constant: Energy mode.")
      public double constantEnergyCost = 1000.0;
      @Comment("The duration, in seconds, of the Constant: Energy effect.")
      public int constantEnergyDuration = 10;
      @Comment("The duration, in seconds, of the debuff of the Constant: Energy effect.")
      public int constantEnergyDebuffDuration = 180;
      @Comment("The cooldown of the Constant: Energy mode.")
      public int constantEnergyCooldown = 120;
   }

   public static class Corroder extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 25000.0;
      @Comment("The level of Corrosion you apply when performing a melee attack when the skill is in slot.")
      public int meleeCorrosionLevel = 2;
      @Comment("The level of Corrosion you apply when performing a melee attack when the skill is mastered and in slot.")
      public int meleeCorrosionLevelMastered = 3;
      @Comment("The amount of time in ticks of the the Corrosion effect when performing a melee attack when the skill is in slot. (Take seconds times 20.)")
      public int meleeCorrosionDuration = 200;
      @Comment(
         "The amount of time in ticks of the Corrosion effect when performing a melee attack when the skill is mastered and in slot. (Take seconds times 20.)"
      )
      public int meleeCorrosionDurationMastered = 300;
      @Comment("The magicule cost of the Dissolve mode, taken every 10 ticks.")
      public double dissolveCost = 100.0;
      @Comment("The radius of your corrosion damage from the Dissolve mode.")
      public int dissolveRadius = 10;
      @Comment("The radius of your corrosion damage from the Dissolve mode when the skill is mastered.")
      public int dissolveRadiusMastered = 15;
      @Comment("The damage of your corrosion from the Dissolve mode.")
      public float dissolveDamage = 10.0F;
      @Comment("The damage of your corrosion from the Dissolve mode when the skill is mastered.")
      public float dissolveDamageMastered = 15.0F;
      @Comment("The level of Corrosion you apply from the Dissolve mode.")
      public int dissolveCorrosionLevel = 1;
      @Comment("The level of Corrosion you apply from the Dissolve mode.")
      public int dissolveCorrosionLevelMastered = 2;
      @Comment("The amount of time in ticks of the Corrosion effect from the Dissolve mode. (Take seconds times 20.)")
      public int dissolveCorrosionDuration = 200;
      @Comment("The amount of time in ticks of the Corrosion effect from the Dissolve mode when the skill is mastered. (Take seconds times 20.)")
      public int dissolveCorrosionDurationMastered = 300;
      @Comment("The magicule cost of the Cross Corrosion mode.")
      public double crossCorrosionCost = 1000.0;
      @Comment("The damage multiplier of your next attack when Cross Corrosion is used.")
      public double crossCorrosionMultiplier = 2.0;
      @Comment("The cooldown of Cross Corrosion in seconds.")
      public int crossCorrosionCooldown = 20;
      @Comment("The cooldown of Cross Corrosion in seconds when the skill is mastered.")
      public int crossCorrosionCooldownMastered = 15;
   }

   public static class Crasher extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 45000.0;
      @Comment("The magicule cost of the Destroyer Haki mode, taken every 10 ticks.")
      public double destroyerHakiCost = 250.0;
      @Comment("The radius in blocks of the Destroyer Haki mode.")
      public int destroyerHakiRadius = 15;
      @Comment("The multiplier of EP that an entity needs to have below to be affected by the Destroyer Haki's movement speed reduction.")
      public float destroyerHakiEP = 0.6F;
      @Comment("The multiplier of EP that an entity needs to have below to be affected by Destroyer Haki's movement speed reduction when mastered.")
      public float destroyerHakiEPMastered = 0.8F;
      @Comment("The EP difference multiplier for each Slowness Level when applied by Destroyer Haki.")
      public double epDifferenceMultiplier = 0.1;
      @Comment("The damage that Destroyer Haki will deal every 20 ticks (one second).")
      public double destroyerHakiDamage = 25.0;
      @Comment("The damage that Destroyer Haki will deal every 20 ticks (one second) when the skill is mastered.")
      public double destroyerHakiDamageMastered = 50.0;
      @Comment("The chance that Insanity is applied to the USER every 60 ticks when Destroyer Haki is used.")
      public int insanityChance = 20;
      @Comment("The increasing level of Insanity when it is applied to the USER when Destroyer Haki is used.")
      public int insanityLevel = 1;
      @Comment("The duration in tick of the Insanity effect when the USER is affected by Destroyer Haki.")
      public int insanityDuration = 200;
      @Comment("The duration in tick of the Slowness effect when targets are affected by Destroyer Haki.")
      public int slowDuration = 200;
      @Comment("The duration in tick of the Slowness effect when targets are affected by Destroyer Haki when [Crasher] is mastered..")
      public int slowDurationMastered = 250;
      @Comment("The magicule cost of the Dimension Hopper mode.")
      public double dimensionHopperCost = 1000.0;
      @Comment("The cooldown of the Dimension Hopper mode.")
      public int dimensionHopperCooldown = 600;
      @Comment("The cooldown of the Dimension Hopper mode when the skill is mastered.")
      public int dimensionHopperCooldownMastered = 300;
   }

   public static class Cultivator extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 80000.0;
      @Comment("The physical attack damage boost when in Slot.")
      public float attackBoost = 50.0F;
      @Comment("The physical attack damage boost when in Slot with mastery.")
      public float attackBoostMastered = 100.0F;
      @Comment("The ammount of mob kills needed to start Cultivation.")
      public int mobKillsNeeded = 1000;
      @Comment("The cooldown on Breakthrough when it completes.")
      public int breakthroughCooldown = 3600;
      @Comment("How long cultivation lasts in ticks (seconds x 20).")
      public int cultivationTimer = 6000;
      @Comment("The amount of mastery the user gains when successfully breaking through.")
      public int breakthroughMasteryGain = 100;
      @Comment("The bonus Magicule percentage the user gains.")
      public double magiculePercentage = 10.0;
      @Comment("The bonus Magicule percentage the user gains with Mastery.")
      public double magiculePercentageMastered = 15.0;
      @Comment("The multiplicative magicule gain the user receives while in the middle of Breaking Through.")
      public double cultivationMPMultiplier = 3.0;
   }

   public static class Disintegration extends ManasSubConfig {
      @Comment("The Magicule cost of the Disintegration mode.")
      public double disintegrationCost = 250000.0;
      @Comment("Can this skill be used in survival? (True for yes, false for no).")
      public boolean survivalUsable = false;
      @Comment("Should this skill have a cooldown?")
      public boolean haveCooldown = false;
      @Comment("The cooldown of the Disintegration mode if the user successfully hits a target in seconds.")
      public int disintegrationCooldown = 1800;
      @Comment("The cooldown of the Disintegration mode if the user successfully hits a target in seconds, when the skill is mastered.")
      public int disintegrationCooldownMastered = 1200;
      @Comment("The cooldown incurred on the Disintegration mode if the user does not hit a target.")
      public int disintegrationCooldownMiss = 30;
      @Comment("The cooldown incurred on the Disintegration mode if the user does not hit a target, when the skill is mastered.")
      public int disintegrationCooldownMissMastered = 15;
   }

   public static class Dreamer extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 60000.0;
      @Comment("The percentage of magicules that will be recovered every 5 seconds.")
      public double magiculeRecovery = 0.5;
      @Comment("The percentage of magicules that will be recovered every 5 seconds when the skill is mastered.")
      public double magiculeRecoveryMastered = 1.0;
      @Comment("The draining mode of magicules when the Dream mode is used. FALSE for percentage cost. TRUE for flat cost.")
      public boolean drainMode = false;
      @Comment("The percentage of magicules that will be drained from the user to set a dream, IF drainMode above is set to false.")
      public double dreamSetPercentageCost = 25.0;
      @Comment("The flat cost of magicules that will be drained from the user to set a dream, IF drainMode above is set to true.")
      public double dreamSetFlatCost = 750.0;
      @Comment("The amount of magicules used when a dream is activated.")
      public double dreamActivateCost = 0.0;
      @Comment("The amount of magicules that the Hypnosis mode drains every second it is active.")
      public double hypnosisCost = 150.0;
      @Comment("The amount of magicules that the Dream Eater mode drains when it is used.")
      public double dreamEaterCost = 300.0;
      @Comment("The cooldown of the Dream mode after it has been activated.")
      public int dreamCooldown = 300;
      @Comment("The cooldown of the Dream mode after it has been activated, when the skill is mastered.")
      public int dreamCooldownMastered = 120;
      @Comment("The radius of the Hypnosis mode in blocks.")
      public double hypnosisRadius = 10.0;
      @Comment("The radius of the Hypnosis mode in blocks when the skill is mastered.")
      public double hypnosisRadiusMastered = 15.0;
      @Comment("The duration of the Drowsiness effect in ticks (seconds x 20).")
      public int hypnosisDuration = 200;
      @Comment("The duration of the Drowsiness effect in ticks when the skill is mastered (seconds x 20).")
      public int hypnosisDurationMastered = 300;
      @Comment("The effect level of the Drowsiness effect when applied by Hypnosis.")
      public int hypnosisLevel = 1;
      @Comment("The effect level of the Drowsiness effect when applied by Hypnosis when the skill is mastered.")
      public int hypnosisLevelMastered = 2;
      @Comment("The range of the Dream Eater mode in blocks.")
      public int dreamEaterRange = 15;
      @Comment("The range of the Dream Eater mode in blocks when the skill is mastered.")
      public int dreamEaterRangeMastered = 20;
      @Comment("The range of the Dream Eater mode in blocks.")
      public int dreamEaterDamage = 30;
      @Comment("The range of the Dream Eater mode in blocks when the skill is mastered.")
      public int dreamEaterDamageMastered = 50;
      @Comment("The range of the Dream Eater mode in blocks.")
      public int dreamEaterSpiritualDamage = 30;
      @Comment("The range of the Dream Eater mode in blocks when the skill is mastered.")
      public int dreamEaterSpiritualDamageMastered = 50;
      @Comment("The number of seconds that Dream Eater reduces the cooldown of Dream by.")
      public int dreamEaterDreamCooldownReduction = 15;
      @Comment("The number of seconds that Dream Eater reduces the cooldown of Dream by when the skill is mastered.")
      public int dreamEaterDreamCooldownReductionMastered = 15;
   }

   public static class Engineer extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 50000.0;
      @Comment("The magicule cost of the Build mode when placing a Sentry.")
      public double buildSentryCost = 500.0;
      @Comment("The magicule cost of the Build mode when placing a Sentry, when the skill is mastered.")
      public double buildSentryCostMastered = 500.0;
      @Comment("The fire rate of a Level 1 Sentry.")
      public int sentryLevel1FireRate = 10;
      @Comment("The time taken (in ticks) for a Level 1 Sentry to evolve to Level 2.")
      public int sentryLevel2LevelUpTime = 2400;
      @Comment("The fire rate of a Level 2 Sentry.")
      public int sentryLevel2FireRate = 5;
      @Comment("The time taken (in ticks) for a Level 2 Sentry to evolve to Level 3.")
      public int sentryLevel3LevelUpTime = 6000;
      @Comment("The fire rate of a Level 3 Sentry.")
      public int sentryLevel3FireRate = 10;
      @Comment("The magicule cost of the Build mode when placing a Dispenser.")
      public double buildDispenserCost = 500.0;
      @Comment("The magicule cost of the Build mode when placing a Dispenser, when the skill is mastered.")
      public double buildDispenserCostMastered = 500.0;
      @Comment("The cooldown of the Build mode in seconds.")
      public int buildCooldown = 30;
      @Comment("The cooldown of the Build mode in seconds, when the skill is mastered.")
      public int buildCooldownMastered = 15;
      @Comment("The magicule cost of the Bubble Shield mode.")
      public double bubbleShieldCost = 1000.0;
      @Comment("The magicule cost of the Bubble Shield mode when the skill is mastered.")
      public double bubbleShieldCostMastered = 1000.0;
      @Comment("Should the Bubble Shield be able to be destroyed from within? (\"true\" = yes/\"false\" = no)")
      public boolean bubbleShieldShouldHurt = false;
      @Comment("The size of the Bubble Shield in radius. Decimal values will crash your game.")
      public float bubbleShieldSize = 7.0F;
      @Comment("The cooldown in seconds of the Bubble Shield mode.")
      public int bubbleShieldCooldown = 25;
      @Comment("The cooldown in seconds of the Bubble Shield mode when the skill is mastered.")
      public int bubbleShieldCooldownMastered = 15;
      @Comment("The lifespan of the Bubble Shield, in ticks (seconds x 20. 600 = 30 seconds).")
      public int bubbleShieldLifespan = 300;
      @Comment("The lifespan of the Bubble Shield when the skill is mastered, in ticks (seconds x 20. 600 = 30 seconds).")
      public int bubbleShieldLifespanMastered = 600;
      @Comment("The percentage of EP regenerated to a Dipsenser's allies when it's activated when it is level one.")
      public float percentageDispenserEPRegenLevel1 = 0.5F;
      @Comment("The percentage of EP regenerated to a Dipsenser's allies when it's activated when it is level two.")
      public float percentageDispenserEPRegenLevel2 = 1.0F;
      @Comment("The percentage of EP regenerated to a Dipsenser's allies when it's activated when it is level three.")
      public float percentageDispenserEPRegenLevel3 = 3.0F;
      @Comment("The amount of HP regenerated to a Dipsenser's allies when it's activated when it is level two.")
      public int healthDispenserRegenLevel2 = 10;
      @Comment("The percentage of HP regenerated to a Dipsenser's allies when it's activated when it is level three.")
      public float healthDispenserRegenLevel3 = 5.0F;
      @Comment("The amount of food regenerated to a Dipsenser's allies when it's activated when it is level one.")
      public int foodDispenserRegenLevel1 = 1;
      @Comment("The amount of food regenerated to a Dipsenser's allies when it's activated when it is level two.")
      public int foodDispenserRegenLevel2 = 2;
      @Comment("The amount of food regenerated to a Dipsenser's allies when it's activated when it is level three.")
      public int foodDispenserRegenLevel3 = 3;
   }

   public static class Gardener extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 40000.0;
      @Comment("The magicules taken every 5 seconds for the Crop Mastery passive.")
      public double cropMasteryCost = 100.0;
      @Comment("The chance for a crop to grow to the next stage every 5 seconds when Crop Mastery is triggered.")
      public double cropMasteryChance = 50.0;
      @Comment("The chance for a Hipokute plant to grow to the next stage every 5 seconds when Crop Mastery is triggered.")
      public double cropMasteryChanceHipokute = 10.0;
      @Comment("The chance for a crop to grow to the next stage every 5 seconds when Crop Mastery is triggered.")
      public double cropMasteryChanceMastered = 75.0;
      @Comment("The chance for a Hipokute plant to grow to the next stage every 5 seconds when Crop Mastery is triggered.")
      public double cropMasteryChanceHipokuteMastered = 20.0;
      @Comment("The amount of crops you need to grow to gain a charge for Overgrow.")
      public int overgrowCropRequirement = 25;
      @Comment("The magicule cost of the Blessing mode, for each item in the stack (blessingCost * stack amount = MP cost).")
      public double blessingCost = 250.0;
      @Comment(
         "The magicule cost of the Blessing mode when the skill is mastered, for each item in the stack (blessingCostMastered * stack amount = MP cost)."
      )
      public double blessingCostMastered = 500.0;
      @Comment("The cooldown of the Blessing mode in seconds.")
      public int blessingCooldown = 3;
      @Comment("The cooldown of the Blessing mode in seconds when the skill is mastered.")
      public int blessingCooldownMastered = 1;
      @Comment("The effect granted to the food stack when Blessing is used at dawn.")
      public String dawnBlessingEffect = "tensura:strengthen";
      @Comment("The effect level granted to the consumer of the food stack when Blessing is used at dawn.")
      public int dawnEffectLevel = 3;
      @Comment("The duration of the effect granted to the consumer of the food stack when Blessing is used at dawn.")
      public int dawnDuration = 3600;
      @Comment("The effect level granted to the consumer of the food stack when Blessing is used at dawn when Gardener is mastered.")
      public int dawnEffectLevelMastered = 5;
      @Comment("The duration of the effect granted to the consumer of the food stack when Blessing is used at dawn when Gardener is mastered.")
      public int dawnDurationMastered = 6000;
      @Comment("The effect granted to the food stack when Blessing is used at noon.")
      public String noonBlessingEffect = "minecraft:resistance";
      @Comment("The effect level granted to the consumer of the food stack when Blessing is used at noon.")
      public int noonEffectLevel = 1;
      @Comment("The duration of the effect granted to the consumer of the food stack when Blessing is used at noon.")
      public int noonDuration = 3600;
      @Comment("The effect level granted to the consumer of the food stack when Blessing is used at noon when Gardener is mastered.")
      public int noonEffectLevelMastered = 2;
      @Comment("The duration of the effect granted to the consumer of the food stack when Blessing is used at noon when Gardener is mastered.")
      public int noonDurationMastered = 3600;
      @Comment("The effect granted to the food stack when Blessing is used at night.")
      public String nightBlessingEffect = "minecraft:speed";
      @Comment("The effect level granted to the consumer of the food stack when Blessing is used at night.")
      public int nightEffectLevel = 3;
      @Comment("The duration of the effect granted to the consumer of the food stack when Blessing is used at night.")
      public int nightDuration = 3600;
      @Comment("The effect level granted to the consumer of the food stack when Blessing is used at night when Gardener is mastered.")
      public int nightEffectLevelMastered = 5;
      @Comment("The duration of the effect granted to the consumer of the food stack when Blessing is used at night when Gardener is mastered.")
      public int nightDurationMastered = 6000;
      @Comment("The cooldown of the Overgrow mode in seconds.")
      public int overgrowCooldown = 300;
      @Comment("The duration that Overgrow lasts for, in ticks (seconds x 20).")
      public int overgrowDuration = 500;
   }

   public static class Gatekeeper extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 85000.0;
      @Comment("The range in blocks of the Enkidu mode.")
      public int enkiduRange = 10;
      @Comment("The magicule cost of the Enkidu mode.")
      public double enkiduCost = 25000.0;
      @Comment("The duration in seconds of the Enkidu mode.")
      public int enkiduDuration = 120;
      @Comment("The duration in seconds of the Enkidu mode when Gatekeeper is mastered.")
      public int enkiduDurationMastered = 240;
      @Comment("The cooldown of the Enkidu mode in seconds.")
      public int enkiduCooldown = 20;
      @Comment("The initial magicule cost of the Gate of Babylon mode.")
      public double babylonCost = 25000.0;
      @Comment("The continuous magicule cost of the Gate of Babylon mode.")
      public double babylonContinousCost = 25000.0;
   }

   public static class HiddenRuler extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 50000.0;
      @Comment("The Magicule cost of Hidden Ruler's toggled passive.")
      public double vanishMagiculeCost = 50.0;
      @Comment("The Aura cost of Hidden Ruler's toggled passive.")
      public double vanishAuraCost = 0.0;
      @Comment("The radius in blocks of the Inspiration effect provided by Hidden Ruler to nearby allies.")
      public double inspireRadius = 15.0;
      @Comment("The cooldown that is applied if there is no target for either Copy or Paste.")
      public int noTargetCooldown = 5;
      @Comment("The FLAT cost of the Copy mode when attempting to copy a skill from the target.")
      public double copyCost = 1000.0;
      @Comment("The cooldown of the Copy mode.")
      public int copyCooldown = 10;
      @Comment("The cooldown of the Copy mode when the skill is mastered.")
      public int copyCooldownMastered = 10;
      @Comment("The chance for the Copy mode to pass.")
      public int copyChance = 25;
      @Comment("The chance for the Copy mode to pass when the skill is mastered.")
      public int copyChanceMastered = 50;
      @Comment("The cooldown of the Copy mode when a failure is encountered.")
      public int copyFailedCooldown = 10;
      @Comment("The EP threshold that is checked when the user is weaker than the target when the Copy mode is used.")
      public double failureThreshold = 2.0;
      @Comment("The EP threshold that is checked when the user is weaker than the target when the Copy mode is used and the skill is mastered.")
      public double failureThresholdMastered = 2.5;
      @Comment("The FLAT cost of the Paste mode when attempting to paste a skill onto the target.")
      public double pasteCost = 1000.0;
      @Comment("The cooldown of the Paste mode.")
      public int pasteCooldown = 10;
      @Comment("The cooldown of the Paste mode when the skill is mastered.")
      public int pasteCooldownMastered = 10;
      @Comment("The chance for the Paste mode to pass.")
      public int pasteChance = 25;
      @Comment("The chance for the Paste mode to pass when the skill is mastered.")
      public int pasteChanceMastered = 50;
      @Comment("The cooldown of the Paste mode when a failure is encountered.")
      public int pasteFailedCooldown = 10;
      @Comment("How many magicules should be taken from the user when attempting to Charm a target?.")
      public double darknessConscriptionCost = 1000.0;
      @Comment("The light level that both the target AND user MUST be under to successfully charm the target with Darkness Conscription.")
      public int lightLevel = 5;
      @Comment("The cooldown of the Darkness Conscription mode.")
      public int darknessConscriptionCooldown = 10;
      @Comment("The cooldown of the Darkness Conscription mode when the skill is mastered.")
      public int darknessConscriptionCooldownMastery = 10;
   }

   public static class Jinchuriki extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 95000.0;
      @Comment("The EP gain increase the user receives while the skill is toggled on.")
      public int epGainIncrease = 6;
      @Comment("The EP gain increase the user receives while the skill is toggled on when the skill is mastered.")
      public int epGainIncreaseMastered = 11;
      @Comment("The time that the Tailed Beast Cloak lasts before gaining control over it, in seconds.")
      public int cloakPreControlLifespan = 30;
      @Comment("The cooldown of the Tailed Beast Cloak mode, in seconds.")
      public int tailedBeastCloakCooldown = 60;
      @Comment("The cooldown of the Tailed Beast Cloak mode when the skill is mastered, in seconds.")
      public int tailedBeastCloakCooldownMastered = 30;
      @Comment("The number of uses to progress to the v1 Cloak.")
      public int v1Cloak = 10;
      @Comment("The number of uses to progress to the v2 Cloak.")
      public int v2Cloak = 100;
      @Comment("The number of uses to progress to the Controlled State.")
      public int controlledState = 250;
      @Comment("The number of uses to achieve Perfect Control State.")
      public int perfectControlledState = 1000;
      @Comment("Magicule cost for each 1.0 of charge for the Tailed Beast Bomb.")
      public double mpCostPerCharge = 800.0;
      @Comment("Aura cost for each 1.0 of charge for the Tailed Beast Bomb.")
      public double apCostPerCharge = 200.0;
      @Comment("The Max Charge of the Tailed Beast Bomb.")
      public double maxMultiplier = 100.0;
      @Comment("The Max Charge of the Tailed Beast Bomb when Mastered.")
      public double maxMultiplierMastered = 150.0;
      @Comment("Ticks the user must hold to gain 1.0 charge (20 = 1 second).")
      public int holdTime = 20;
      @Comment("Ticks the user must hold to gain 1.0 charge when Mastered (10 = 0.5 second).")
      public int holdTimeMastered = 10;
      @Comment("Damage dealt per 1.0 charge (6 per 1.0 = 30 per 5.0).")
      public float baseDamage = 6.0F;
      @Comment("The bomb explodes on impact only if charged past this amount.")
      public float explosionThreshold = 3.0F;
      @Comment("Damage of the explosion at the impact zone.")
      public float explosionDamage = 150.0F;
      @Comment("Base explosion radius once the explosion threshold is passed.")
      public float explosionBaseRadius = 3.0F;
      @Comment("Explosion radius increase for each 10.0 of charge.")
      public float explosionRadiusPer10Charge = 2.0F;
   }

   public static class Kyurem extends ManasSubConfig {
      @Comment("EP Needed to Obtain.")
      public double epRequirement = 100000.0;
      @Comment("Should this skill be acquirable if you have [Reshiram] or [Zekrom]?")
      public boolean canAcquireWithOtherSkills = true;
      @Comment("Amount of Ice Essence needed to be consumed for acquisition.")
      public int iceEssenceAmount = 10;
      @Comment("Amount of Dragon Essence needed to be consumed for acquisition.")
      public int dragonEssenceAmount = 10;
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 64600.0;
      @Comment("Pressure Effect Duration in Seconds")
      public int pressureEffectDuration = 30;
      @Comment("Pressure Cost Multiplier.")
      public double pressureCostMultiplier = 2.0;
      @Comment("Magicule cost for the Glaciate Mode.")
      public double glaciateMPCost = 900.0;
      @Comment("Range of Glaciate Mode in blocks")
      public int glaciateRange = 15;
      @Comment("The amount of incremented damage for Glaciate mode per 10 seconds")
      public int glaciateDamageIncrement = 10;
      @Comment("The Max Damage Glaciate can deal when Mastered.")
      public int glaciateMaxDamageMastered = 200;
      @Comment("The Max Damage Glaciate can deal.")
      public int glaciateMaxDamage = 100;
      @Comment("Cooldown of Glaciate Mode.")
      public int glaciateCooldown = 30;
      @Comment("Magicule cost for the Draconic Pulse Mode.")
      public double draconicPulseMPCost = 700.0;
      @Comment("Draconic Pulse Damage.")
      public float draconicPulseDamage = 40.0F;
      @Comment("Draconic Pulse Explosion Radius.")
      public float draconicPulseExplosionRadius = 7.5F;
      @Comment("Draconic Pulse Cooldown.")
      public int draconicPulseCooldown = 10;
      @Comment("Draconic Pulse Cooldown Mastered.")
      public int draconicPulseCooldownMastered = 5;
      @Comment("Magicule cost for the Freeze Dry Mode.")
      public double freezeDryMPCost = 800.0;
      @Comment("Damage of Freeze Dry Mode.")
      public float freezeDryDamage = 20.0F;
      @Comment("Cooldown of Freeze Dry Mode.")
      public int freezeDryCooldown = 20;
      @Comment("Cooldown of Freeze Dry Mode when Mastered.")
      public int freezeDryCooldownMastered = 10;
      @Comment("Magicule cost for the Blizzard Mode.")
      public double blizzardMPCost = 900.0;
      @Comment("Blizzard Damage.")
      public float blizzardDamage = 40.0F;
      @Comment("Cooldown of Blizzard.")
      public int blizzardCooldown = 10;
      @Comment("Radius of Blizzard")
      public float blizzardRadius = 7.5F;
   }

   public static class Looter extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 50000.0;
      @Comment("Targeting range in blocks for turning a soul into a shadow.")
      public double range = 5.0;
      @Comment("The radius in blocks of the Return mode of Shadow Storage.")
      public float radiusReturn = 15.0F;
   }

   public static class Malleable extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 50000.0;
      @Comment("The bonus learning points gained towards Earth-type abilities AND Resist-type skills when the skill is toggled on.")
      public int learningPoint = 4;
      @Comment("The bonus mastery points gained towards Earth-type abilities AND Resist-type skills when the skill is toggled on.")
      public int masteryPoint = 4;
      @Comment("The duration of the Alchemy mode in ticks when the skill is NOT mastered.")
      public int alchemyDuration = 200;
      @Comment("The cooldown of the Alchemy mode in seconds.")
      public int alchemyCooldown = 10;
      @Comment("If true, Alchemy becomes toggleable on mastery. If false, it follows a cooldown that you can change below.")
      public boolean alchemyToggleableMastery = true;
      @Comment("The cooldown of the Alchemy mode in seconds when the skill is mastered.")
      public int alchemyCooldownMastered = 10;
      @Comment("The cooldown of the Harden mode in seconds.")
      public int hardenCooldown = 120;
      @Comment("The cooldown of the Harden mode in seconds when the skill is mastered.")
      public int hardenCooldownMastered = 60;
      @Comment("The maximum duration the Harden mode can be used for in ticks (seconds multiplied by 20.)")
      public int maximumHardenDuration = 200;
      @Comment("The level of the respective effects you gain while Harden is active.")
      public int regenerationLevel = 5;
      public int resistanceLevel = 2;
      @Comment("The maximum duration the Harden mode can be used for in ticks (seconds multiplied by 20.)")
      public int maximumHardenDurationMastery = 300;
      @Comment("Should the Harden mode disable attack/movement speed? False if they should not.")
      public boolean hardenShouldDisableAttack = true;
      public boolean hardenShouldDisableMovement = true;
      @Comment("The amount of armor points that should be granted to the user when the Harden mode is used.")
      public int hardenArmorPoints = 10;
      @Comment("The cooldown of the Vessel mode in seconds.")
      public int vesselCooldown = 60;
      @Comment("The cooldown of the Vessel mode in seconds.")
      public int vesselCooldownMastered = 30;
      @Comment("The health points given to the Vessel created.")
      public double vesselHP = 50.0;
      @Comment("The armor points given to the Vessel created.")
      public double vesselArmor = 10.0;
      @Comment("The health points given to the Vessel created when the skill is mastered.")
      public double vesselHPMastered = 200.0;
      @Comment("The armor points given to the Vessel created when the skill is mastered.")
      public double vesselArmorMastered = 30.0;
   }

   public static class Melancholy extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 100000.0;
      @Comment("Flat magicule cost when using Melancholy (per press/activation).")
      public double magiculeCost = 750.0;
      @Comment("Default targeting range in blocks for Gust.")
      public double defaultRange = 15.0;
      @Comment("Range of Ishtar's Tear.")
      public double ishaRange = 10.0;
      @Comment("Range of Ishtar's Tear when the skill is mastered.")
      public double ishaRangeMastered = 15.0;
      @Comment("Level of slowness given to entities other than the user.")
      public int ishaLevel = 4;
      @Comment("Level of slowness given to the user.")
      public int ishaLevelUser = 2;
      @Comment("Level of resistance given to the user.")
      public int ishaResistLevel = 2;
      @Comment("Radius of the Veil mode.")
      public int veilKnockbackRadius = 12;
      @Comment("Level of slowness given to entities when Veil is used.")
      public int veilSlownessLevel = 10;
      @Comment("Level of slowness given to entities when Veil is used.")
      public int veilBurdenLevel = 4;
      @Comment("Radius of the Grief mode.")
      public int griefKnockbackRadius = 8;
      @Comment("Level of blindness and weakness given to entities when Grief is used.")
      public int griefEffectLevel = 2;
      @Comment("Maximum adjustable targeting range in blocks.")
      public double maxRange = 50.0;
      @Comment("Base push velocity scale applied to entities when using Gust.")
      public double pushEntity = 1.4;
      @Comment("Base pull velocity scale applied to entities when using Gust while sneaking.")
      public double pullEntity = 1.1;
      @Comment("Additional push scale when Gravity Manipulation is toggled.")
      public double pushEntityManipulation = 0.25;
      @Comment("Additional push scale when Gravity Domination is toggled.")
      public double pushEntityDomination = 0.45;
      @Comment("Additional pull scale when Gravity Manipulation is toggled.")
      public double pullEntityManipulation = 0.2;
      @Comment("Additional pull scale when Gravity Domination is toggled.")
      public double pullEntityDomination = 0.4;
      @Comment("The base damage of thrown air.")
      public float airThrowDamage = 75.0F;
      @Comment("The base damage of thrown air when mastered.")
      public float airThrowDamageMastered = 100.0F;
      @Comment("The base damage of thrown items.")
      public float itemThrowDamage = 125.0F;
      @Comment("The base damage of thrown items when mastered.")
      public float itemThrowDamageMastered = 175.0F;
      @Comment("Damage multiplier applied to thrown items when Gravity Manipulation is toggled.")
      public double itemThrowManipulation = 1.25;
      @Comment("Damage multiplier applied to thrown items when Gravity Domination is toggled.")
      public double itemThrowDomination = 1.5;
   }

   public static class Phaser extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 72000.0;
      @Comment("The level of presence sense gained when toggling on the skill.")
      public double presenceSense = 2.0;
      @Comment("The Magicule cost of the Eye of Insight passive.")
      public double eyeOfInsightCost = 50.0;
      @Comment("The maximum number of abilities you can use without rest before you are forced on cooldowns from overuse.")
      public int overuseCap = 10;
      @Comment("The cooldown of the Intangibility passive in seconds.")
      public int intangibilityPassiveCooldown = 15;
      @Comment("The cooldown of the Intangibility active in seconds, which only becomes available when the skill is mastered.")
      public int intangibilityActiveCooldown = 15;
      @Comment("How long should the player phase through all attacks in ticks (seconds x 20).")
      public int intangibilityDuration = 100;
      @Comment("The magicule cost of the Intangibility active mode.")
      public int intangibilityActiveCost = 100;
      @Comment("The magicule cost of the Intangibility passive mode.")
      public int intangibilityPassiveCost = 100;
      @Comment("The amount of damage that an ejected item from Phaser's portals should deal.")
      public float itemEjectDamage = 20.0F;
      @Comment("The amount of damage that an ejected item from Phaser's portals should deal when the skill is mastered.")
      public float itemEjectDamageMastered = 30.0F;
      @Comment("The time taken for the portals to prime before you can shoot in ticks (seconds x 20).")
      public int ejectChargeTicks = 60;
      @Comment("The time taken for the portals to prime before you can shoot in ticks when the skill is mastered (seconds x 20).")
      public int ejectChargeTicksMastered = 30;
      @Comment("The magicule cost of each portal that is opened when the Eject mode is used.")
      public int ejectCost = 200;
      @Comment("The time in ticks (seconds x 20) that it takes for the user to warp into the Kamui dimension.")
      public int kamuiWarpTicks = 60;
      @Comment("The cooldown of the Authority Of The Gods mode in seconds.")
      public int kamuiCooldown = 5;
      @Comment("The magicule cost of the Authority Of The Gods mode.")
      public int kamuiCost = 1500;
   }

   public static class Provider extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 85000.0;
      @Comment("The initial magicule cost of Provider's Generosity Halo when it is summoned.")
      public double generosityHaloCost = 1000.0;
      @Comment("The magicule cost of Provider's Generosity Halo every subsequent second it is summoned.")
      public double generosityHaloCostSecond = 500.0;
      @Comment("The lifespan of the Generosity Halo entity in ticks.")
      public int generosityHaloLifespan = 72000;
      @Comment("The damage multiplier that all projectiles receive when passing through Provider's Generosity Halo.")
      public double generosityBoost = 3.0;
      @Comment("The damage multiplier that all entities receive when gaining the Provider Boost effect from passing through Provider's Generosity Halo.")
      public double generosityEntityDamageBoost = 3.0;
      @Comment("The speed multiplier that all entities receive when gaining the Provider Boost effect from passing through Provider's Generosity Halo.")
      public double generosityEntitySpeedBoost = 3.0;
      @Comment("The step height that all entities receive when gaining the Provider Boost effect from passing through Provider's Generosity Halo.")
      public double generosityEntityStepHeightBoost = 3.0;
      @Comment("The cooldown for the Generosity mode in seconds.")
      public int generosityCooldown = 5;
      @Comment("The cooldown for the Generosity mode in seconds when the skill is mastered..")
      public int generosityCooldownMastery = 5;
      @Comment("The cooldown of the Pay It Forward mode in seconds.")
      public int payItForwardCooldown = 300;
      @Comment("The cooldown of the Pay It Forward mode in seconds when the skill is mastered.")
      public int payItForwardCooldownMastered = 300;
      @Comment("The magicule cost to turn on Provider's Pay It Forward mode.")
      public double payItForwardCost = 1500.0;
      @Comment("The magicule cost of Provider's Pay It Forward mode every subsequent second it is toggled.")
      public double payItForwardCostTicking = 750.0;
      @Comment("The following values are the contributions required to reach the next tier.")
      public int contributionsTier1 = 1000;
      public int contributionsTier2 = 2500;
      public int contributionsTier3 = 5000;
      public int contributionsTier4 = 25000;
      public int contributionsTier5 = 100000;
   }

   public static class Reducer extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquisition = 90000.0;
      @Comment("The percentage of energy cost reduction you receive when Reducer is toggled on.")
      public double purificationReduction = 0.5;
      @Comment("The percentage of energy cost reduction you receive when Reducer is toggled on, when the skill is mastered.")
      public double purificationReductionMastered = 0.33;
      @Comment("The ADDITIONAL multiplier (base 1) of magicule regeneration you receive when Reducer is in slot.")
      public double magiculeRegenerationMultiplierBuff = 9.0;
      @Comment("The ADDITIONAL multiplier (base 1) of aura regeneration you receive when Reducer is in slot.")
      public double auraRegenerationMultiplierBuff = 9.0;
      @Comment("The magicule cost to toggle on the Emancipation mode.")
      public double emancipationCost = 2000.0;
      @Comment("The magicule cost to maintain the Emancipation mode.")
      public double emancipationMaintainCost = 300.0;
      @Comment("The multiplier of Holy Damage the user will deal when Emancipation is toggled on.")
      public float emancipationMultiplier = 1.75F;
      @Comment("The magicule cost of the Purity Edge mode.")
      public double purityEdgeCost = 10000.0;
      @Comment("The percentage of health that will be drained from the target on connecting with Purity Edge.")
      public float purityEdgeDamagePercentage = 0.25F;
      @Comment("The percentage of health that will be drained from the target on connecting with Purity Edge when the skill is mastered.")
      public float purityEdgeDamagePercentageMastered = 0.5F;
      @Comment("The time you have to connect a hit before suffering the recoil damage in ticks.")
      public int purityEdgeRecoilTime = 60;
      @Comment("The time you have to connect a hit before suffering the recoil damage in ticks when the skill is mastered.")
      public int purityEdgeRecoilTimeMastered = 100;
      @Comment("The cooldown of the Purity Edge mode in seconds.")
      public int purityEdgeCooldown = 120;
      @Comment("The cooldown of the Purity Edge mode in seconds when the skill is mastered.")
      public int purityEdgeCooldownMastered = 60;
      @Comment("The recoil of the Purity Edge mode if you do not land a hit in time.")
      public float purityEdgeRecoil = 0.75F;
      @Comment("The recoil of the Purity Edge mode if you do not land a hit in time when the skill is mastered.")
      public float purityEdgeRecoilMastered = 0.25F;
   }

   public static class Repeater extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost")
      public double mpAcquirement = 75000.0;
      @Comment("How long should Recursion Depth last, in ticks (seconds x 20).")
      public int recursionDepthTimer = 100;
      @Comment("The cooldown of the Recursion Depth mode.")
      public int recursionDepthCooldown = 30;
      @Comment("The cooldown of the Recursion Depth mode when the skill is mastered.")
      public int recursionDepthCooldownMastered = 15;
   }

   public static class Reshiram extends ManasSubConfig {
      @Comment("EP Needed to Obtain.")
      public double epRequirement = 100000.0;
      @Comment("Should this skill be acquirable if you have [Zekrom] or [Kyurem]?")
      public boolean canAcquireWithOtherSkills = true;
      @Comment("Amount of Blaze Essence needed to be consumed for acquisition.")
      public int blazeEssenceAmount = 10;
      @Comment("Amount of Dragon Essence needed to be consumed for acquisition.")
      public int dragonEssenceAmount = 10;
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 64300.0;
      @Comment("The amount to boost Flame Damage by.")
      public double flameBoostAmount = 1.5;
      @Comment("MP Cost of Blue Flare.")
      public double blueFlareMPCost = 800.0;
      @Comment("Damage of Blue Flare.")
      public float blueFlareDamage = 60.0F;
      @Comment("Radius of Blue Flare.")
      public float blueFlareRadius = 20.0F;
      @Comment("Cooldown of Blue Flare.")
      public int blueFlareCooldown = 10;
      @Comment("Cooldown of Blue Flare when Mastered.")
      public int blueFlareCooldownMastered = 5;
      @Comment("MP Cost of Fusion Flare.")
      public double fusionFlareMPCost = 700.0;
      @Comment("MP Cost of Fusion Flare with Zekrom boosted from Original Cost.")
      public double fusionFlareZekromMPCostBoost = 2.0;
      @Comment("Damage of Fusion Flare.")
      public float fusionFlareDamage = 200.0F;
      @Comment("Radius of Fusion Flare.")
      public float fusionFlareRadius = 5.0F;
      @Comment("Damage of Fusion Flare Zekrom Bonus")
      public float fusionFlareZekromBonus = 40.0F;
      @Comment("Cooldown of Fusion Flare.")
      public int fusionFlareCooldown = 20;
      @Comment("MP Cost of Draconic Meteor.")
      public double draconicMeteorMPCost = 1000.0;
      @Comment("Damage of Draconic Meteor.")
      public float draconicMeteorDamage = 70.0F;
      @Comment("Explosion Radius of Draconic Meteor.")
      public float draconicMeteorExplosionRadius = 10.0F;
      @Comment("Knockback Force of Draconic Meteor.")
      public float draconicMeteorKnockbackForce = 5.0F;
      @Comment("Cooldown of Draconic Meteor.")
      public int draconicMeteorCooldown = 20;
      @Comment("MP Cost of Flamethrower.")
      public double flamethrowerMPCost = 400.0;
      @Comment("Damage of Flamethrower.")
      public float flamethrowerDamage = 30.0F;
      @Comment("Cooldown of Flamethrower.")
      public int flamethrowerCooldown = 10;
   }

   public static class Restricted extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 20000.0;
      @Comment("Damage multiplier of Battlewills.")
      public double battlewillMultiplier = 5.0;
      @Comment("Damage multiplier of Physical Attacks.")
      public double physicalAttackMultiplier = 3.0;
      @Comment("Maximum additional multiplier that your stats can be boosted to depending on the skill's mastery.")
      public double maximumStatBoost = 2.0;
      @Comment("Maximum additional flat boost that your armor stat can be boosted to depending on the skill's mastery.")
      public double maximumFlatStatBoost = 20.0;
      @Comment("The distance traveled when performing an attack using Decimate.")
      public int decimateDistance = 12;
      @Comment("The distance traveled when performing an attack using Decimate when the skill is mastered.")
      public int decimateDistanceMastered = 16;
      @Comment("The amount of bonus damage dealt when performing an attack using Decimate.")
      public float decimateDamage = 25.0F;
      @Comment("The amount of bonus damage dealt when performing an attack using Decimate when the skill is mastered.")
      public float decimateDamageMastered = 75.0F;
      @Comment("The duration that your Counter state lasts in ticks (seconds x 20).")
      public int counterStateDuration = 60;
      @Comment("The duration that your Counter state lasts in ticks (seconds x 20) when the skill is mastered.")
      public int counterStateDurationMastered = 100;
      @Comment("The duration that enemies become Imbalanced for when striking you while you are Countering.")
      public int counterImbalanceDuration = 100;
      @Comment("The duration that enemies become Imbalanced for when striking you while you are Countering, when the skill is mastered.")
      public int counterImbalanceDurationMastered = 100;
      @Comment("The cooldown of the Counter mode in seconds.")
      public int counterCooldown = 15;
      @Comment("The cooldown of the Counter mode in seconds when the skill is mastered.")
      public int counterCooldownMastered = 15;
   }

   public static class Saint extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 75000.0;
      @Comment("The Magicule cost of the Compassion passive.")
      public double compassionCost = 10000.0;
   }

   public static class Scholar extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 35000.0;
      @Comment("The bonus number of learning point to gain when toggled.")
      public double learningPoint = 6.0;
      @Comment("The bonus number of mastery point to gain when toggled.")
      public double masteryPoint = 6.0;
      @Comment("The chance to study the opponent when they attack you.")
      public int studyChance = 20;
      @Comment("The base dodge chance without studying the opponent.")
      public int studyDodgeBase = 0;
      @Comment("The study points that translate into dodge chance you gain towards that entity type whenever you successfully study them.")
      public int studyDodgePerPoint = 1;
      @Comment("The maximum amount of dodge chance you can gain against players.")
      public int studyDodgePlayerMax = 50;
      @Comment("The maximum amount of dodge chance you can gain against players when the skill is mastered.")
      public int studyDodgePlayerMaxMastered = 70;
      @Comment("The maximum amount of dodge chance you can gain against a specific entity type.")
      public int studyDodgeEntityMax = 75;
      @Comment("The maximum amount of dodge chance you can gain against a specific entity type when the skill is mastered.")
      public int studyDodgeEntityMaxMastered = 100;
      @Comment("Lists of enchantments that Scholar cannot learn or enchant.")
      public List<String> enchantmentBlacklist;
      @Comment("Lists of enchantments that Scholar cannot learn or enchant above the enchantment's maximum level.")
      public List<String> maxBonusBlacklist;
      @Comment("The maximum bonus of level that the player can increase.")
      public int maxBonusLevel = 5;

      public Scholar() {
         List<String> toBlacklist = new ArrayList<>(TensuraEnchantmentHelper.ENGRAVEMENT_ID_LIST);
         toBlacklist.add("mysticism:boreal_frost");
         toBlacklist.add("mysticism:holy_flames");
         this.enchantmentBlacklist = toBlacklist;
         this.maxBonusBlacklist = toBlacklist;
      }
   }

   public static class Schrodinger extends ManasSubConfig {
      @Comment("The duration in tick of the poison effect on target when touching them.")
      public int poisonEffectDuration = 160;
   }

   public static class Spiritualist extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 80000.0;
      @Comment("Magicule cost of the Summon Scythe mode.")
      public double summonScytheCost = 500.0;
      @Comment("Magicule cost of the Carve mode.")
      public double carveCost = 500.0;
      @Comment("Magicule cost of the Enhance mode.")
      public double enhanceCost = 1500.0;
      @Comment("The Range of the Carve mode.")
      public double carveRange = 5.0;
      @Comment("The chant speed multiplier when toggled.")
      public double chantSpeed = 2.0;
   }

   public static class Stagnator extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 50000.0;
      @Comment("The magicule cost of the Stagnate Aura mode, taken every 10 ticks.")
      public double stagnateAuraCost = 250.0;
      @Comment("The radius in blocks of the Stagnate Aura mode.")
      public int stagnateAuraRadius = 25;
      @Comment("The cooldown of the Stagnate Aura mode in seconds.")
      public int stagnateAuraCooldown = 5;
      @Comment("The cooldown of the Stagnate Aura mode in seconds, when the skill is mastered.")
      public int stagnateAuraCooldownMastered = 3;
      @Comment("The Magicule cost of the Inaction mode, taken every 10 ticks.")
      public double inactionCost = 50.0;
      @Comment("The cooldown of the Inaction mode in seconds.")
      public int inactionCooldown = 5;
      @Comment("The cooldown of the Inaction mode in seconds, when the skill is mastered.")
      public int inactionCooldownMastered = 3;
      @Comment("The Magicule Cost of the Stasis Shot mode.")
      public double stasisShotCost = 1000.0;
      @Comment("The Damage of Stasis Shot projectile.")
      public float stasisShotDamage = 50.0F;
      @Comment("The Damage of Stasis Shot Projectile when Mastered")
      public float stasisShotMasteredDamage = 100.0F;
      @Comment("The cooldown of Stasis Shot Mode in seconds.")
      public int stasisShotCooldown = 10;
      @Comment("The cooldown of Stasis Shot Mode in seconds, when the skill is mastered.")
      public int stasisShotCooldownMastered = 5;
   }

   public static class Subjugator extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 95000.0;
      @Comment("The step in loyalty points that is accounted for whenever the user is to gain more damage reduction from the passive.")
      public int unbreakableDefenseStep = 2000;
      @Comment(
         "The percentage of damage reduction you gain every time you gain a multiple of the above value. (Please do it in decimal notation and not percentage notation.)"
      )
      public double unbreakableDefenseGain = 0.02;
      @Comment("The range of the Undying Loyalty passive in blocks.")
      public double loyaltyRange = 8.0;
      @Comment("The amount of loyalty points gained from nearby subordinates every five seconds.")
      public int loyaltyPointGain = 2;
      @Comment("The range of the Undying Loyalty passive in blocks when the skill is mastered.")
      public double loyaltyRangeMastery = 16.0;
      @Comment("The amount of loyalty points gained from nearby subordinates every five seconds when the skill is mastered.")
      public int loyaltyPointGainMastery = 4;
      @Comment("The resistance level granted to subordinates in your vicinity.")
      public int loyaltyResistance = 1;
      @Comment("The resistance level granted to subordinates in your vicinity when the skill is mastered.")
      public int loyaltyResistanceMastered = 2;
      @Comment("The level of inspiration granted to subordinates when changing their movement mode.")
      public int inspirationLevel = 1;
      @Comment("The duration of the inspiration effect bestowed onto subordinates when changing their movement mode, in seconds.")
      public int inspirationDuration = 30;
      @Comment("The level of inspiration granted to subordinates when changing their movement mode when the skill is mastered.")
      public int inspirationLevelMastery = 2;
      @Comment("The duration of the inspiration effect bestowed onto subordinates when changing their movement mode when the skill is mastered, in seconds.")
      public int inspirationDurationMastery = 60;
      @Comment("The maximum gainable loyalty points that will cap out the user's Damage Reduction.")
      public int maximumTotalLoyaltyPoints = 50000;
      @Comment("The distance in blocks that Takeover can be used from.")
      public int takeoverDistance = 5;
      @Comment("The distance in blocks that Takeover can be used from when the skill is mastered.")
      public int takeoverDistanceMastery = 10;
      @Comment("The multiplier of EP the player needs to meet in order to convert the target.")
      public double takeoverEPMultiplierRequirement = 1.0;
      @Comment("The multiplier of EP the player needs to meet in order to convert the target.")
      public double takeoverEPMultiplierRequirementMastered = 2.0;
      @Comment("The cooldown of the Takeover mode in seconds.")
      public int takeoverCooldown = 300;
      @Comment("The cooldown of the Takeover mode in seconds when the skill is mastered.")
      public int takeoverCooldownMastered = 120;
      @Comment("The chance that a conversation is started every 5 seconds.")
      public float casualConversationChance = 0.02F;
      @Comment("The radius in block to apply skill effect on Allies.")
      public double allyRadius = 20.0;
      @Comment("The base max amount of EP that the target can have for Takeover can work")
      public double baseMaxEPForMindControl = 600000.0;
   }

   public static class TheBalance extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 35000.0;
      @Comment("Flat magicule cost per activation.")
      public double magiculeCost = 500.0;
      @Comment("Targeting range in blocks for TheBalance activation.")
      public double range = 8.0;
      @Comment("Damage dealt when points are at or under the threshold.")
      public double heavyDamage = 10000.0;
      @Comment("Damage per MisfortunePoint when points exceed the threshold.")
      public double perPointDamage = 1000.0;
      @Comment("Threshold of MisfortunePoint used by TheBalance.")
      public int thresholdPoints = 10;
   }

   public static class VictoriousHarbinger extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 80000.0;
      @Comment("The magicule cost of the Might mode, taken every 10 ticks.")
      public double mightCost = 2000.0;
      @Comment("The Cooldown of the Might mode")
      public int mightCooldown = 40;
      @Comment("The Magicule cost of the Tenacious Resolve mode")
      public double tenaciousResolveCost = 500.0;
      @Comment("The Level of effect to reduce by")
      public int effectLevel = 1;
      @Comment("List of effects the holder of this skill is immune to")
      public List<String> immunityList = new ArrayList<>();

      public VictoriousHarbinger() {
         this.immunityList.add(TensuraMobEffects.FEAR.getRegisteredName());
         this.immunityList.add(TensuraMobEffects.MIND_CONTROL.getRegisteredName());
      }
   }

   public static class Zekrom extends ManasSubConfig {
      @Comment("EP Needed to Obtain.")
      public double epRequirement = 100000.0;
      @Comment("Should this skill be acquirable if you have [Reshiram] or [Kyurem]?")
      public boolean canAcquireWithOtherSkills = true;
      @Comment("Amount of Dragon Essence needed to be consumed for acquisition.")
      public int dragonEssenceAmount = 10;
      @Comment("Amount of Lightning Strikes needed to be consumed for acquisition.")
      public int lightningEssenceAmount = 10;
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 64400.0;
      @Comment("Amount to boost Lightning Damage by.")
      public double lightningBoostAmount = 1.5;
      @Comment("MP Cost of Bolt Strike Mode.")
      public double boltStrikeMPCost = 800.0;
      @Comment("Damage of Bolt Strike Mode.")
      public float boltStrikeDamage = 150.0F;
      @Comment("Radius of Bolt Strike Mode..")
      public float boltStrikeRadius = 7.5F;
      @Comment("Additional Visual of Bolt Strike.")
      public int boltStrikeAdditionalVisual = 8;
      @Comment("Cooldown of Bolt Strike Mode.")
      public int boltStrikeCooldown = 20;
      @Comment("Cooldown of Bolt Strike Mode when Mastered.")
      public int boltStrikeCooldownMastered = 10;
      @Comment("MP Cost of Fusion Bolt Mode.")
      public double fusionBoltMPCost = 800.0;
      @Comment("Damage of Fusion Bolt Mode.")
      public float fusionBoltDamage = 60.0F;
      @Comment("Bonus Damage if Reshiram in slot.")
      public float fusionBoltReshiramBonus = 40.0F;
      @Comment("MP Cost of Fusion Bolt Mode with Reshiram boosted from Original Cost.")
      public double fusionboltReshiramMPCost = 2.0;
      @Comment("Range of Fusion Bolt.")
      public float fusionBoltRange = 15.0F;
      @Comment("Duration of Fire on Target in seconds.")
      public int fusionBoltFireDuration = 5;
      @Comment("Fusion Bolt Cooldown.")
      public int fusionBoltCooldown = 20;
      @Comment("MP Cost of Draconic Breath Mode.")
      public double draconicBreathMPCost = 800.0;
      @Comment("Damage of Draconic Breath.")
      public float draconicBreathDamage = 60.0F;
      @Comment("Cooldown of Draconic Breath Mode.")
      public int draconicBreathCooldown = 10;
      @Comment("MP Cost of Thunderbolt Mode.")
      public double thunderboltMPCost = 1000.0;
      @Comment("Damage of Thunderbolt Mode.")
      public float thunderboltDamage = 100.0F;
      @Comment("Range of Thunderbolt in blocks.")
      public float thunderboltRange = 30.0F;
   }
}
