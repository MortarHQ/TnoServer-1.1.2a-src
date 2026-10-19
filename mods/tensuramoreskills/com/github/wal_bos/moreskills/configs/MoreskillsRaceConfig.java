package com.github.wal_bos.moreskills.configs;

import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

public final class MoreskillsRaceConfig {
   public static final ModConfigSpec COMMON_SPEC;
   public static final MoreskillsRaceConfig.Config CONFIG;

   private MoreskillsRaceConfig() {
   }

   public static void init() {
      ModLoadingContext.get().getActiveContainer().registerConfig(Type.COMMON, COMMON_SPEC, "tensuramoreskills-races.toml");
   }

   public static MoreskillsRaceConfig.Config get() {
      return CONFIG;
   }

   static {
      Pair<MoreskillsRaceConfig.Config, ModConfigSpec> pair = new Builder().configure(MoreskillsRaceConfig.Config::new);
      CONFIG = (MoreskillsRaceConfig.Config)pair.getLeft();
      COMMON_SPEC = (ModConfigSpec)pair.getRight();
   }

   public static final class BlackCommunion {
      public final BooleanValue enabled;
      public final IntValue maxMastery;
      public final IntValue activationBloodCost;
      public final IntValue duration;
      public final IntValue pulseInterval;
      public final IntValue collapseCooldown;
      public final DoubleValue baseRadius;
      public final DoubleValue maxRadius;
      public final DoubleValue radiusPerPower;
      public final DoubleValue maxPower;
      public final DoubleValue bloodfiendWeight;
      public final DoubleValue elderBloodfiendWeight;
      public final DoubleValue bloodNobleWeight;
      public final DoubleValue bloodMonarchWeight;
      public final DoubleValue crimsonProgenitorWeight;
      public final DoubleValue generationContributionPenalty;
      public final DoubleValue minGenerationContribution;
      public final DoubleValue emptyBloodContribution;
      public final DoubleValue fullBloodContribution;
      public final DoubleValue bloodDrainedPowerMultiplier;
      public final DoubleValue thrallPowerMultiplier;
      public final DoubleValue maxProgressContribution;
      public final DoubleValue powerGainMultiplier;
      public final DoubleValue nodeOfferingBloodCost;
      public final BooleanValue conquestEnabled;
      public final IntValue conquestRequiredAltarLevel;
      public final IntValue conquestRequiredWorldStageForRoots;
      public final IntValue conquestPlayerMemoryInterval;
      public final IntValue conquestBaseScanRadius;
      public final IntValue conquestMemoryPruneTicks;
      public final IntValue conquestMinPlayerHeatTarget;
      public final IntValue conquestMinBaseHeatTarget;
      public final IntValue conquestMaxActiveRoots;
      public final IntValue domainMaxLevel;
      public final IntValue domainLevel6Xp;
      public final IntValue domainLevel7Xp;
      public final IntValue domainLevel8Xp;
      public final IntValue domainLevel9Xp;
      public final IntValue domainLevel10Xp;
      public final IntValue domainXpPerBloom;
      public final IntValue domainXpPerCompletedRoot;
      public final IntValue domainXpPerBaseBreach;
      public final IntValue domainXpPerConqueredChunk;
      public final IntValue conquestRequiredDomainLevel;
      public final IntValue conquestRequiredDomainLevelForRoots;
      public final IntValue conquestWorldHeartRequiredDomainLevel;
      public final IntValue conquestRootMaxTargetDistanceBlocks;
      public final IntValue conquestRootBlocksPerTick;
      public final IntValue conquestMaxRootsProcessedPerTick;
      public final IntValue conquestRootTickInterval;
      public final IntValue conquestRootStartCost;
      public final IntValue conquestRootStepCost;
      public final IntValue conquestRootMaxAge;
      public final IntValue conquestRootReachDistanceChunks;
      public final IntValue conquestBloomTickInterval;
      public final IntValue conquestBloomStartBlood;
      public final IntValue conquestBaseBloomBloodMultiplier;
      public final IntValue conquestBloomMaxStoredBlood;
      public final IntValue conquestBloomFeedInterval;
      public final IntValue conquestBloomFeedAmount;
      public final IntValue conquestBloomSpreadBlocks;
      public final IntValue conquestBloomBurstSpreadBlocks;
      public final IntValue conquestBaseBloomBurstSpreadBlocks;
      public final IntValue conquestBloomDrainMaxBlood;
      public final IntValue conquestBloomGrowthDivisor;
      public final IntValue conquestBloomBloodDivisor;
      public final DoubleValue conquestBloomHealMultiplier;
      public final DoubleValue conquestBloomSpiritualMultiplier;
      public final DoubleValue conquestBloomMagiculeMultiplier;
      public final IntValue conquestHeartTickInterval;
      public final IntValue conquestWorldHeartStage;
      public final IntValue conquestWorldHeartSpreadBlocks;
      public final IntValue conquestWorldHeartMinGrowth;
      public final DoubleValue conquestWorldHeartHealMultiplier;
      public final DoubleValue conquestWorldHeartSpiritualMultiplier;
      public final DoubleValue conquestWorldHeartMagiculeMultiplier;
      public final IntValue conquestConqueredThreshold;
      public final IntValue conquestBlockBreakPressure;
      public final IntValue conquestStage1RequiredAltarLevel;
      public final IntValue conquestStage1RequiredTotalBlood;
      public final IntValue conquestStage2RequiredBlooms;
      public final IntValue conquestStage2RequiredCompletedRoots;
      public final IntValue conquestStage3RequiredBlooms;
      public final IntValue conquestStage3RequiredBaseBreaches;
      public final IntValue conquestStage3RequiredConqueredChunks;
      public final IntValue conquestStage4RequiredBlooms;
      public final IntValue conquestStage4RequiredBaseBreaches;
      public final IntValue conquestStage4RequiredConqueredChunks;
      public final IntValue conquestStage5RequiredBlooms;
      public final IntValue conquestStage5RequiredBaseBreaches;
      public final IntValue conquestStage5RequiredCompletedRoots;
      public final IntValue conquestStage5RequiredConqueredChunks;
      public final IntValue conquestWorldHeartRequiredBlooms;
      public final IntValue conquestWorldHeartRequiredBaseBreaches;
      public final IntValue conquestWorldHeartRequiredConqueredChunks;
      public final IntValue conquestWorldHeartRequiredCompletedRoots;
      public final DoubleValue conquestGrowthHealthPerPoint;
      public final DoubleValue conquestGrowthAttackPerPoint;
      public final DoubleValue conquestGrowthSpeedPerPoint;
      public final DoubleValue conquestGrowthMaxHealthCap;
      public final DoubleValue conquestGrowthMaxAttackCap;
      public final DoubleValue conquestGrowthMaxSpeedCap;
      public final BooleanValue ecologyEnabled;
      public final IntValue ecologyMinimumWorldStage;
      public final IntValue ecologyVirtualStepInterval;
      public final IntValue ecologyVirtualClaimSteps;
      public final IntValue ecologyVirtualStepBloodCost;
      public final IntValue ecologyMaterializeInterval;
      public final IntValue ecologyBuildInterval;
      public final IntValue ecologyTickInterval;
      public final IntValue ecologyMaxStructures;
      public final IntValue ecologyBuildBloodCost;
      public final IntValue ecologyStructureMinSpacing;
      public final IntValue ecologySpireSpreadInterval;
      public final IntValue ecologySpireSpreadBlocks;
      public final IntValue ecologyNestPulseInterval;
      public final IntValue ecologyNestRadius;
      public final DoubleValue ecologyNestPulseDamage;
      public final IntValue ecologyCorruptionDecayTicks;
      public final IntValue ecologyConversionMarks;
      public final DoubleValue ecologyConversionHealthRatio;
      public final IntValue ecologyMinionCapPerNest;
      public final IntValue ecologyConversionBloodCost;
      public final IntValue ecologyMinionThinkInterval;
      public final IntValue ecologyMinionTargetRadius;
      public final DoubleValue ecologyMinionMoveSpeed;
      public final IntValue ecologyMinionAttackInterval;
      public final DoubleValue ecologyMinionDamage;
      public final IntValue ecologyFusionInterval;
      public final IntValue ecologyFusionRadius;
      public final IntValue ecologyFusionBloodCost;
      public final IntValue ecologyMaxFusionTier;
      public final IntValue ecologyReservoirFeedInterval;
      public final IntValue ecologyReservoirFeedAmount;
      public final IntValue ecologyBloodCollectionRadius;
      public final IntValue ecologyStructureBloodCapacity;
      public final DoubleValue ecologyBloodHealthDivisor;
      public final IntValue ecologyMaxBloodPerKill;
      public final IntValue ecologyVirtualStepsPerPulse;
      public final IntValue ecologyMaxPendingBuilds;
      public final IntValue ecologyPendingProcessInterval;
      public final BooleanValue ecologyRemoteAltarsEnabled;
      public final IntValue ecologyRemoteAltarMinDistance;
      public final IntValue ecologyRemoteAltarRequiredChunkValue;
      public final IntValue ecologyRemoteAltarBloodCost;
      public final IntValue ecologyRemoteAltarSeedFuel;
      public final IntValue ecologyRemoteAltarAttemptEvery;
      public final IntValue ecologyWorldHeartRootCount;
      public final IntValue ecologyWorldHeartRootInterval;
      public final IntValue ecologySummonerInterval;
      public final IntValue ecologySummonerBloodCost;
      public final IntValue ecologySummonerCap;
      public final IntValue ecologyRelayInterval;
      public final IntValue ecologyRelayBloodCost;
      public final IntValue ecologyRelaySteps;
      public final IntValue ecologySanctuaryInterval;
      public final IntValue ecologySanctuaryRadius;
      public final DoubleValue ecologySanctuaryHeal;
      public final IntValue ecologyHuntingBeaconInterval;
      public final IntValue ecologyHuntingBeaconRadius;
      public final IntValue ecologyFusionPowerPerConqueredChunk;
      public final DoubleValue ecologyFusionHealthPerPower;
      public final DoubleValue ecologyFusionAttackPerPower;
      public final DoubleValue ecologyFusionMaxHealthMultiplier;
      public final DoubleValue ecologyFusionMaxAttackMultiplier;
      public final DoubleValue ecologyFusionMaxArmor;
      public final IntValue sharedBloodTransferPerPulse;
      public final DoubleValue sharedBloodLowRatio;
      public final DoubleValue sharedBloodDonorMinRatio;
      public final IntValue progenitorBloodGainPerPulse;
      public final IntValue linkedBloodGainPerPulse;
      public final DoubleValue linkedHealPerPulse;
      public final DoubleValue linkedSpiritualRestorePerPulse;
      public final DoubleValue linkedMagiculeRestorePerPulse;
      public final DoubleValue baseDamageReduction;
      public final DoubleValue damageReductionPerPower;
      public final DoubleValue maxDamageReduction;
      public final DoubleValue damageBoostPerPower;
      public final DoubleValue maxDamageBoost;
      public final IntValue infectionStacksPerPulse;
      public final IntValue maxInfectionStacks;
      public final IntValue infectionDuration;
      public final IntValue infectionBrandDuration;
      public final IntValue infectionDamageInterval;
      public final DoubleValue infectionPulseDamage;
      public final DoubleValue infectionMaxPulseDamage;
      public final IntValue infectionBloodLeak;
      public final DoubleValue infectionHealLeak;
      public final DoubleValue infectionDamageBoostPerStack;
      public final DoubleValue maxInfectionDamageBoost;
      public final BooleanValue skipBloodlessInfection;
      public final DoubleValue deathSpreadRadius;
      public final IntValue killBloodGain;
      public final DoubleValue killHeal;
      public final DoubleValue killPowerGain;
      public final IntValue preventDeathBloodCost;
      public final DoubleValue preventDeathHealthRatio;
      public final IntValue preventDeathInvulnerabilityTicks;
      public final IntValue preventDeathCooldownTicks;
      public final DoubleValue preventDeathSpiritualRestore;
      public final DoubleValue preventDeathMagiculeRestore;
      public final DoubleValue preventDeathInfectionRadius;
      public final IntValue preventDeathInfectionStacks;
      public final DoubleValue collapseBaseDamage;
      public final DoubleValue collapseDamagePerPower;
      public final DoubleValue collapseMaxDamage;
      public final DoubleValue collapseHeal;
      public final DoubleValue collapseSpiritualHeal;
      public final DoubleValue collapseMagiculeRestore;
      public final IntValue collapseBloodRestore;
      public final IntValue collapseBrandDuration;
      public final DoubleValue collapseMobTurnHealthThreshold;
      public final BooleanValue blockInfectionEnabled;
      public final IntValue blockInfectionAttempts;
      public final IntValue blockInfectionBlocksPerPulse;
      public final IntValue blockInfectionMaxBlocksPerPulse;
      public final DoubleValue blockInfectionBlocksPerPower;
      public final DoubleValue blockInfectionMaxRadius;
      public final IntValue blockInfectionVerticalRange;
      public final IntValue linkedBloodfiendsSpreadBlocks;
      public final IntValue linkedBloodfiendSpreadAttempts;
      public final DoubleValue linkedBloodfiendSpreadRadius;
      public final IntValue domainChunkValuePerBlock;
      public final IntValue domainChunkValueSacrificeBonus;
      public final IntValue domainChunkMaxValue;
      public final IntValue domainBiomeChunkValueRequired;
      public final BooleanValue domainBiomeBuffsEnabled;
      public final IntValue domainBiomeBuffInterval;
      public final DoubleValue domainBiomeBloodfiendHeal;
      public final DoubleValue domainBiomeBloodfiendSpiritualRestore;
      public final DoubleValue domainBiomeBloodfiendMagiculeRestore;
      public final IntValue domainBiomeBloodfiendBloodGain;
      public final IntValue domainBiomeBloodfiendBloodGainPerAltarLevel;
      public final DoubleValue domainBiomeBuffPerAltarLevel;
      public final IntValue domainBiomeAltarLevelSearchRadius;
      public final BooleanValue domainBiomeDamagesEnemies;
      public final DoubleValue domainBiomeEnemyDamage;
      public final IntValue domainBiomeKillBloodGain;
      public final BooleanValue bloodAltarEnabled;
      public final BooleanValue allowMultipleBloodAltars;
      public final DoubleValue altarRequiredPower;
      public final IntValue altarRequiredChunkValue;
      public final IntValue altarSpawnSearchRadius;
      public final IntValue altarNoSpawnNearRadius;
      public final BooleanValue altarSacrificeEnabled;
      public final IntValue altarFuelPerBlock;
      public final IntValue altarMaxStoredBlood;
      public final IntValue altarMaxLevel;
      public final IntValue altarLevel1Blood;
      public final IntValue altarLevel2Blood;
      public final IntValue altarLevel3Blood;
      public final IntValue altarLevel4Blood;
      public final IntValue altarLevel5Blood;
      public final BooleanValue altarPassiveSpreadEnabled;
      public final IntValue altarSpreadInterval;
      public final IntValue altarSpreadAttempts;
      public final IntValue altarSpreadBlocksPerPulse;
      public final IntValue altarSpreadBlocksPerLevel;
      public final DoubleValue altarBaseRadius;
      public final DoubleValue altarRadiusPerLevel;
      public final IntValue altarSacrificeBurstAttempts;
      public final IntValue altarSacrificeBurstBlocks;
      public final IntValue altarSacrificeBurstBlocksPerLevel;
      public final IntValue dormantSpreadInterval;
      public final IntValue dormantSpreadBlocksPerPulse;

      BlackCommunion(Builder builder) {
         this.enabled = builder.comment("If true, Crimson Progenitors can use Black Communion.").define("enabled", true);
         this.maxMastery = builder.comment("Maximum mastery for Black Communion.").defineInRange("maxMastery", 1000, 1, Integer.MAX_VALUE);
         builder.push("activation");
         this.activationBloodCost = builder.comment("Blood cost paid by the Crimson Progenitor when the communion starts.")
            .defineInRange("activationBloodCost", 140, 0, Integer.MAX_VALUE);
         this.duration = builder.comment("Maximum active duration in ticks. 2400 ticks = 2 minutes.").defineInRange("duration", 2400, 20, Integer.MAX_VALUE);
         this.pulseInterval = builder.comment("How often the network pulses. 40 ticks = 2 seconds.").defineInRange("pulseInterval", 40, 1, Integer.MAX_VALUE);
         this.collapseCooldown = builder.comment("Cooldown in seconds after manually collapsing Black Communion.")
            .defineInRange("collapseCooldown", 90, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("radius");
         this.baseRadius = builder.comment("Starting ritual radius.").defineInRange("baseRadius", 24.0, 1.0, 512.0);
         this.maxRadius = builder.comment("Maximum ritual radius after power scaling.").defineInRange("maxRadius", 96.0, 1.0, 512.0);
         this.radiusPerPower = builder.comment("Radius gained per stored communion power.").defineInRange("radiusPerPower", 0.08, 0.0, 16.0);
         builder.pop();
         builder.push("power");
         this.maxPower = builder.comment("Maximum stored communion power.").defineInRange("maxPower", 800.0, 1.0, Double.MAX_VALUE);
         this.bloodfiendWeight = builder.comment("Power weight for Bloodfiend participants.").defineInRange("bloodfiendWeight", 1.0, 0.0, 1024.0);
         this.elderBloodfiendWeight = builder.comment("Power weight for Elder Bloodfiend participants.")
            .defineInRange("elderBloodfiendWeight", 2.0, 0.0, 1024.0);
         this.bloodNobleWeight = builder.comment("Power weight for Blood Noble participants.").defineInRange("bloodNobleWeight", 4.0, 0.0, 1024.0);
         this.bloodMonarchWeight = builder.comment("Power weight for Blood Monarch participants.").defineInRange("bloodMonarchWeight", 7.0, 0.0, 1024.0);
         this.crimsonProgenitorWeight = builder.comment("Power weight for Crimson Progenitor participants.")
            .defineInRange("crimsonProgenitorWeight", 12.0, 0.0, 1024.0);
         this.generationContributionPenalty = builder.comment("Power contribution penalty per generation after generation 1.")
            .defineInRange("generationContributionPenalty", 0.04, 0.0, 1.0);
         this.minGenerationContribution = builder.comment("Minimum generation contribution multiplier.")
            .defineInRange("minGenerationContribution", 0.45, 0.0, 1.0);
         this.emptyBloodContribution = builder.comment("Minimum contribution even if a linked Bloodfiend is low on blood.")
            .defineInRange("emptyBloodContribution", 0.25, 0.0, 1024.0);
         this.fullBloodContribution = builder.comment("Contribution added based on stored blood ratio.")
            .defineInRange("fullBloodContribution", 1.0, 0.0, 1024.0);
         this.bloodDrainedPowerMultiplier = builder.comment("Extra power per total blood drained by the participant.")
            .defineInRange("bloodDrainedPowerMultiplier", 8.0E-4, 0.0, 1024.0);
         this.thrallPowerMultiplier = builder.comment("Extra power per total thrall turned by the participant.")
            .defineInRange("thrallPowerMultiplier", 0.08, 0.0, 1024.0);
         this.maxProgressContribution = builder.comment("Maximum extra power from progress stats per participant per pulse.")
            .defineInRange("maxProgressContribution", 10.0, 0.0, 1024.0);
         this.powerGainMultiplier = builder.comment("Final multiplier for communion power gained per pulse.")
            .defineInRange("powerGainMultiplier", 0.08, 0.0, 1024.0);
         this.nodeOfferingBloodCost = builder.comment("Blood paid per participant weight each pulse.")
            .defineInRange("nodeOfferingBloodCost", 0.35, 0.0, 1024.0);
         builder.pop();
         builder.push("shared_bloodline");
         this.sharedBloodTransferPerPulse = builder.comment("Blood moved from high-blood nodes to low-blood nodes per pulse.")
            .defineInRange("sharedBloodTransferPerPulse", 6, 0, Integer.MAX_VALUE);
         this.sharedBloodLowRatio = builder.comment("A linked Bloodfiend below this blood ratio can receive shared blood.")
            .defineInRange("sharedBloodLowRatio", 0.35, 0.0, 1.0);
         this.sharedBloodDonorMinRatio = builder.comment("A linked Bloodfiend must stay above this blood ratio to donate shared blood.")
            .defineInRange("sharedBloodDonorMinRatio", 0.45, 0.0, 1.0);
         this.progenitorBloodGainPerPulse = builder.comment("Blood gained by the Progenitor every pulse.")
            .defineInRange("progenitorBloodGainPerPulse", 4, 0, Integer.MAX_VALUE);
         this.linkedBloodGainPerPulse = builder.comment("Blood gained by every linked Bloodfiend every pulse.")
            .defineInRange("linkedBloodGainPerPulse", 2, 0, Integer.MAX_VALUE);
         this.linkedHealPerPulse = builder.comment("Health restored to every linked Bloodfiend every pulse.")
            .defineInRange("linkedHealPerPulse", 2000.0, 0.0, Double.MAX_VALUE);
         this.linkedSpiritualRestorePerPulse = builder.comment("Spiritual health restored to every linked Bloodfiend every pulse.")
            .defineInRange("linkedSpiritualRestorePerPulse", 6000.0, 0.0, Double.MAX_VALUE);
         this.linkedMagiculeRestorePerPulse = builder.comment("Magicules restored to every linked Bloodfiend every pulse.")
            .defineInRange("linkedMagiculeRestorePerPulse", 120000.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("combat");
         this.baseDamageReduction = builder.comment("Base damage reduction for linked Bloodfiends.").defineInRange("baseDamageReduction", 0.1, 0.0, 1.0);
         this.damageReductionPerPower = builder.comment("Extra damage reduction per communion power.")
            .defineInRange("damageReductionPerPower", 8.0E-4, 0.0, 1.0);
         this.maxDamageReduction = builder.comment("Maximum damage reduction for linked Bloodfiends.").defineInRange("maxDamageReduction", 0.75, 0.0, 1.0);
         this.damageBoostPerPower = builder.comment("Damage boost per communion power for linked Bloodfiends.")
            .defineInRange("damageBoostPerPower", 9.0E-4, 0.0, 1024.0);
         this.maxDamageBoost = builder.comment("Maximum damage boost from communion power.").defineInRange("maxDamageBoost", 1.5, 0.0, 1024.0);
         builder.pop();
         builder.push("infection");
         this.infectionStacksPerPulse = builder.comment("Black infection stacks applied to enemies per pulse.")
            .defineInRange("infectionStacksPerPulse", 1, 0, Integer.MAX_VALUE);
         this.maxInfectionStacks = builder.comment("Maximum black infection stacks.").defineInRange("maxInfectionStacks", 12, 1, Integer.MAX_VALUE);
         this.infectionDuration = builder.comment("Black infection duration in ticks.").defineInRange("infectionDuration", 500, 1, Integer.MAX_VALUE);
         this.infectionBrandDuration = builder.comment("Blood Brand duration applied by black infection.")
            .defineInRange("infectionBrandDuration", 160, 1, Integer.MAX_VALUE);
         this.infectionDamageInterval = builder.comment("How often infection deals damage.").defineInRange("infectionDamageInterval", 60, 1, Integer.MAX_VALUE);
         this.infectionPulseDamage = builder.comment("Damage per infection stack per infection pulse.")
            .defineInRange("infectionPulseDamage", 0.75, 0.0, Double.MAX_VALUE);
         this.infectionMaxPulseDamage = builder.comment("Maximum infection pulse damage.")
            .defineInRange("infectionMaxPulseDamage", 45.0, 0.0, Double.MAX_VALUE);
         this.infectionBloodLeak = builder.comment("Blood gained by linked Bloodfiends per infection stack per pulse.")
            .defineInRange("infectionBloodLeak", 1, 0, Integer.MAX_VALUE);
         this.infectionHealLeak = builder.comment("Healing gained by linked Bloodfiends per infection stack per pulse.")
            .defineInRange("infectionHealLeak", 0.25, 0.0, Double.MAX_VALUE);
         this.infectionDamageBoostPerStack = builder.comment("Extra damage linked Bloodfiends deal per infection stack on the target.")
            .defineInRange("infectionDamageBoostPerStack", 0.035, 0.0, 1024.0);
         this.maxInfectionDamageBoost = builder.comment("Maximum damage boost from infection stacks.")
            .defineInRange("maxInfectionDamageBoost", 1.2, 0.0, 1024.0);
         this.skipBloodlessInfection = builder.comment("If true, black infection ignores bloodless entities.").define("skipBloodlessInfection", true);
         this.deathSpreadRadius = builder.comment("Radius where infection spreads when an infected target dies.")
            .defineInRange("deathSpreadRadius", 10.0, 0.0, 128.0);
         this.killBloodGain = builder.comment("Blood gained by linked Bloodfiends when infected enemies die.")
            .defineInRange("killBloodGain", 8, 0, Integer.MAX_VALUE);
         this.killHeal = builder.comment("Healing gained by linked Bloodfiends when infected enemies die.")
            .defineInRange("killHeal", 4.0, 0.0, Double.MAX_VALUE);
         this.killPowerGain = builder.comment("Communion power gained when infected enemies die.").defineInRange("killPowerGain", 8.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("death_prevention");
         this.preventDeathBloodCost = builder.comment("Total shared network blood cost to prevent a linked Bloodfiend death.")
            .defineInRange("preventDeathBloodCost", 80, 1, Integer.MAX_VALUE);
         this.preventDeathHealthRatio = builder.comment("Health ratio restored when death is prevented.")
            .defineInRange("preventDeathHealthRatio", 0.18, 0.01, 1.0);
         this.preventDeathInvulnerabilityTicks = builder.comment("Invulnerability ticks after death prevention.")
            .defineInRange("preventDeathInvulnerabilityTicks", 80, 0, Integer.MAX_VALUE);
         this.preventDeathCooldownTicks = builder.comment("Cooldown ticks before the network can prevent another death.")
            .defineInRange("preventDeathCooldownTicks", 120, 0, Integer.MAX_VALUE);
         this.preventDeathSpiritualRestore = builder.comment("Spiritual health restored on death prevention.")
            .defineInRange("preventDeathSpiritualRestore", 250.0, 0.0, Double.MAX_VALUE);
         this.preventDeathMagiculeRestore = builder.comment("Magicules restored on death prevention.")
            .defineInRange("preventDeathMagiculeRestore", 500000.0, 0.0, Double.MAX_VALUE);
         this.preventDeathInfectionRadius = builder.comment("Black infection burst radius when death is prevented.")
            .defineInRange("preventDeathInfectionRadius", 12.0, 0.0, 128.0);
         this.preventDeathInfectionStacks = builder.comment("Infection stacks applied by death prevention burst.")
            .defineInRange("preventDeathInfectionStacks", 3, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("collapse");
         this.collapseBaseDamage = builder.comment("Base damage dealt by Black Eucharist collapse.")
            .defineInRange("collapseBaseDamage", 1000.0, 0.0, Double.MAX_VALUE);
         this.collapseDamagePerPower = builder.comment("Extra collapse damage per communion power.")
            .defineInRange("collapseDamagePerPower", 0.32, 0.0, Double.MAX_VALUE);
         this.collapseMaxDamage = builder.comment("Maximum collapse damage.").defineInRange("collapseMaxDamage", 35000.0, 0.0, Double.MAX_VALUE);
         this.collapseHeal = builder.comment("Health restored to linked Bloodfiends on collapse.")
            .defineInRange("collapseHeal", 60000.0, 0.0, Double.MAX_VALUE);
         this.collapseSpiritualHeal = builder.comment("Spiritual health restored to linked Bloodfiends on collapse.")
            .defineInRange("collapseSpiritualHeal", 1200000.0, 0.0, Double.MAX_VALUE);
         this.collapseMagiculeRestore = builder.comment("Magicules restored to linked Bloodfiends on collapse.")
            .defineInRange("collapseMagiculeRestore", 2500000.0, 0.0, Double.MAX_VALUE);
         this.collapseBloodRestore = builder.comment("Blood restored to linked Bloodfiends on collapse.")
            .defineInRange("collapseBloodRestore", 120, 0, Integer.MAX_VALUE);
         this.collapseBrandDuration = builder.comment("Blood Brand duration applied during collapse.")
            .defineInRange("collapseBrandDuration", 700, 1, Integer.MAX_VALUE);
         this.collapseMobTurnHealthThreshold = builder.comment("Infected mobs below this health ratio can be turned by collapse.")
            .defineInRange("collapseMobTurnHealthThreshold", 0.25, 0.0, 1.0);
         builder.pop();
         builder.push("black_blood_domain");
         this.blockInfectionEnabled = builder.comment("If true, Black Communion can infect blocks and create Black Blood Domains.")
            .define("blockInfectionEnabled", false);
         this.blockInfectionAttempts = builder.comment("Random block infection attempts per active communion pulse.")
            .defineInRange("blockInfectionAttempts", 96, 0, Integer.MAX_VALUE);
         this.blockInfectionBlocksPerPulse = builder.comment("Fast spread blocks per communion pulse before power scaling.")
            .defineInRange("blockInfectionBlocksPerPulse", 14, 0, Integer.MAX_VALUE);
         this.blockInfectionMaxBlocksPerPulse = builder.comment("Maximum blocks infected by the progenitor per communion pulse.")
            .defineInRange("blockInfectionMaxBlocksPerPulse", 42, 0, Integer.MAX_VALUE);
         this.blockInfectionBlocksPerPower = builder.comment("Extra fast spread blocks per stored communion power.")
            .defineInRange("blockInfectionBlocksPerPower", 0.035, 0.0, 1024.0);
         this.blockInfectionMaxRadius = builder.comment("Maximum radius used by active communion block infection.")
            .defineInRange("blockInfectionMaxRadius", 72.0, 1.0, 512.0);
         this.blockInfectionVerticalRange = builder.comment("Vertical random spread range for block infection.")
            .defineInRange("blockInfectionVerticalRange", 5, 1, 64);
         this.linkedBloodfiendsSpreadBlocks = builder.comment("Extra block spread from each linked Bloodfiend during active communion.")
            .defineInRange("linkedBloodfiendsSpreadBlocks", 2, 0, Integer.MAX_VALUE);
         this.linkedBloodfiendSpreadAttempts = builder.comment("Random spread attempts around each linked Bloodfiend.")
            .defineInRange("linkedBloodfiendSpreadAttempts", 16, 0, Integer.MAX_VALUE);
         this.linkedBloodfiendSpreadRadius = builder.comment("Spread radius around linked Bloodfiends.")
            .defineInRange("linkedBloodfiendSpreadRadius", 18.0, 1.0, 512.0);
         this.domainChunkValuePerBlock = builder.comment("Virtual domain value added to a chunk per infected block. Buffs use this instead of scanning blocks.")
            .defineInRange("domainChunkValuePerBlock", 1, 0, Integer.MAX_VALUE);
         this.domainChunkValueSacrificeBonus = builder.comment("Extra virtual domain value added to the altar chunk per blood sacrifice.")
            .defineInRange("domainChunkValueSacrificeBonus", 18, 0, Integer.MAX_VALUE);
         this.domainChunkMaxValue = builder.comment("Maximum virtual black blood domain value per chunk.")
            .defineInRange("domainChunkMaxValue", 1000, 1, Integer.MAX_VALUE);
         this.domainBiomeChunkValueRequired = builder.comment("Chunk value required for the area to count as a Black Blood Domain biome for buffs.")
            .defineInRange("domainBiomeChunkValueRequired", 36, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.push("black_blood_domain_buffs");
         this.domainBiomeBuffsEnabled = builder.comment("If true, Bloodfiends receive buffs in infected domain chunks.")
            .define("domainBiomeBuffsEnabled", true);
         this.domainBiomeBuffInterval = builder.comment("How often domain biome buffs pulse. 80 ticks = 4 seconds.")
            .defineInRange("domainBiomeBuffInterval", 80, 1, Integer.MAX_VALUE);
         this.domainBiomeBloodfiendHeal = builder.comment("Health restored to Bloodfiends standing in infected domain chunks.")
            .defineInRange("domainBiomeBloodfiendHeal", 250.0, 0.0, Double.MAX_VALUE);
         this.domainBiomeBloodfiendSpiritualRestore = builder.comment("Spiritual health restored to Bloodfiends in infected domain chunks.")
            .defineInRange("domainBiomeBloodfiendSpiritualRestore", 450.0, 0.0, Double.MAX_VALUE);
         this.domainBiomeBloodfiendMagiculeRestore = builder.comment("Magicules restored to Bloodfiends in infected domain chunks.")
            .defineInRange("domainBiomeBloodfiendMagiculeRestore", 900000.0, 0.0, Double.MAX_VALUE);
         this.domainBiomeBloodfiendBloodGain = builder.comment("Blood gained by Bloodfiends in infected domain chunks.")
            .defineInRange("domainBiomeBloodfiendBloodGain", 3, 0, Integer.MAX_VALUE);
         this.domainBiomeBloodfiendBloodGainPerAltarLevel = builder.comment("Extra blood gained per nearby altar level.")
            .defineInRange("domainBiomeBloodfiendBloodGainPerAltarLevel", 2, 0, Integer.MAX_VALUE);
         this.domainBiomeBuffPerAltarLevel = builder.comment("Multiplier bonus per nearby altar level for domain healing/restoration.")
            .defineInRange("domainBiomeBuffPerAltarLevel", 0.2, 0.0, 1024.0);
         this.domainBiomeAltarLevelSearchRadius = builder.comment("Radius used to find nearby altar level for stronger domain buffs.")
            .defineInRange("domainBiomeAltarLevelSearchRadius", 96, 1, 512);
         this.domainBiomeDamagesEnemies = builder.comment("If true, non-Bloodfiends take light damage in infected domain chunks.")
            .define("domainBiomeDamagesEnemies", true);
         this.domainBiomeEnemyDamage = builder.comment("Light damage dealt to non-Bloodfiends in infected domain chunks per buff interval.")
            .defineInRange("domainBiomeEnemyDamage", 80.0, 0.0, Double.MAX_VALUE);
         this.domainBiomeKillBloodGain = builder.comment(
               "Fuel blood generated into the nearest Blood Altar when black blood domain biome damage kills a non-Bloodfiend. This also counts as lifetime altar blood."
            )
            .defineInRange("domainBiomeKillBloodGain", 2, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("blood_altar");
         this.bloodAltarEnabled = builder.comment("If true, Black Communion can create Blood Altars.").define("bloodAltarEnabled", true);
         this.allowMultipleBloodAltars = builder.comment(
               "If false, only one Blood Altar can exist globally in this world for all Bloodfiends. If true, unlimited altars can exist."
            )
            .define("allowMultipleBloodAltars", false);
         this.altarRequiredPower = builder.comment("Communion power required before an altar can spawn.")
            .defineInRange("altarRequiredPower", 25.0, 0.0, Double.MAX_VALUE);
         this.altarRequiredChunkValue = builder.comment("Virtual domain chunk value required before an altar can spawn near the progenitor.")
            .defineInRange("altarRequiredChunkValue", 28, 0, Integer.MAX_VALUE);
         this.altarSpawnSearchRadius = builder.comment("Search radius for altar spawn positions around the progenitor.")
            .defineInRange("altarSpawnSearchRadius", 8, 1, 128);
         this.altarNoSpawnNearRadius = builder.comment("Prevents a new altar from spawning this close to another altar.")
            .defineInRange("altarNoSpawnNearRadius", 160, 0, Integer.MAX_VALUE);
         this.altarSacrificeEnabled = builder.comment("If true, Bloodfiends can sacrifice blood into Blood Altars.").define("altarSacrificeEnabled", true);
         this.altarFuelPerBlock = builder.comment(
               "Fuel blood consumed per infected block created by altar and vein-node spread. 30 means 30 stored blood = 1 infected block."
            )
            .defineInRange("altarFuelPerBlock", 30, 1, Integer.MAX_VALUE);
         this.altarMaxStoredBlood = builder.comment("Maximum fuel blood inside a Blood Altar.")
            .defineInRange("altarMaxStoredBlood", 10000, 1, Integer.MAX_VALUE);
         this.altarMaxLevel = builder.comment("Maximum Blood Altar level.").defineInRange("altarMaxLevel", 5, 0, 5);
         this.altarLevel1Blood = builder.comment("Stored blood required for altar level 1.").defineInRange("altarLevel1Blood", 1000, 0, Integer.MAX_VALUE);
         this.altarLevel2Blood = builder.comment("Stored blood required for altar level 2.").defineInRange("altarLevel2Blood", 2500, 0, Integer.MAX_VALUE);
         this.altarLevel3Blood = builder.comment("Stored blood required for altar level 3.").defineInRange("altarLevel3Blood", 5000, 0, Integer.MAX_VALUE);
         this.altarLevel4Blood = builder.comment("Stored blood required for altar level 4.").defineInRange("altarLevel4Blood", 7500, 0, Integer.MAX_VALUE);
         this.altarLevel5Blood = builder.comment("Stored blood required for altar level 5.").defineInRange("altarLevel5Blood", 10000, 0, Integer.MAX_VALUE);
         this.altarPassiveSpreadEnabled = builder.comment("If true, Blood Altars passively spread infection after communion ends.")
            .define("altarPassiveSpreadEnabled", true);
         this.altarSpreadInterval = builder.comment("Altar spread interval when the altar has at least level 1.")
            .defineInRange("altarSpreadInterval", 100, 1, Integer.MAX_VALUE);
         this.altarSpreadAttempts = builder.comment("Random spread attempts per altar passive spread pulse.")
            .defineInRange("altarSpreadAttempts", 48, 0, Integer.MAX_VALUE);
         this.altarSpreadBlocksPerPulse = builder.comment("Blocks spread by a powered altar per passive pulse.")
            .defineInRange("altarSpreadBlocksPerPulse", 3, 0, Integer.MAX_VALUE);
         this.altarSpreadBlocksPerLevel = builder.comment("Extra altar passive spread blocks per altar level.")
            .defineInRange("altarSpreadBlocksPerLevel", 2, 0, Integer.MAX_VALUE);
         this.altarBaseRadius = builder.comment("Base altar spread and buff radius.").defineInRange("altarBaseRadius", 24.0, 1.0, 512.0);
         this.altarRadiusPerLevel = builder.comment("Extra altar radius per altar level. Level 1 makes the radius +4 by default.")
            .defineInRange("altarRadiusPerLevel", 4.0, 0.0, 512.0);
         this.altarSacrificeBurstAttempts = builder.comment("Random spread attempts immediately after a blood sacrifice.")
            .defineInRange("altarSacrificeBurstAttempts", 128, 0, Integer.MAX_VALUE);
         this.altarSacrificeBurstBlocks = builder.comment("Blocks infected immediately after a blood sacrifice.")
            .defineInRange("altarSacrificeBurstBlocks", 18, 0, Integer.MAX_VALUE);
         this.altarSacrificeBurstBlocksPerLevel = builder.comment("Extra sacrifice burst blocks per altar level.")
            .defineInRange("altarSacrificeBurstBlocksPerLevel", 10, 0, Integer.MAX_VALUE);
         this.dormantSpreadInterval = builder.comment("Very slow spread interval for a level 0 altar. 1200 ticks = 60 seconds.")
            .defineInRange("dormantSpreadInterval", 1200, 1, Integer.MAX_VALUE);
         this.dormantSpreadBlocksPerPulse = builder.comment("Very slow dormant spread blocks for an unpowered altar.")
            .defineInRange("dormantSpreadBlocksPerPulse", 1, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("black_blood_conquest");
         this.conquestEnabled = builder.comment(
               "If true, Blood Altars can run the long-term world conquest system: player/base memory, World Roots, Blood Flower Blooms, and World Heart."
            )
            .define("conquestEnabled", true);
         this.conquestRequiredAltarLevel = builder.comment(
               "Minimum real Blood Altar level required before conquest ticks at all. This also disables player/base tracking unless a valid altar exists."
            )
            .defineInRange("conquestRequiredAltarLevel", 5, 0, 5);
         this.conquestRequiredWorldStageForRoots = builder.comment(
               "Minimum world conquest stage required before new World Roots can start. Stage 1 is reached from a powerful altar, so roots do not begin immediately at fresh worlds."
            )
            .defineInRange("conquestRequiredWorldStageForRoots", 1, 0, Integer.MAX_VALUE);
         this.conquestPlayerMemoryInterval = builder.comment("How often player/base memory samples run. 200 ticks = 10 seconds.")
            .defineInRange("conquestPlayerMemoryInterval", 200, 20, Integer.MAX_VALUE);
         this.conquestBaseScanRadius = builder.comment("Sparse radius around sampled players used to score artificial/base blocks.")
            .defineInRange("conquestBaseScanRadius", 10, 1, 64);
         this.conquestMemoryPruneTicks = builder.comment("Old non-infected player/base chunk memory is removed after this many ticks. 864000 ticks = 12 hours.")
            .defineInRange("conquestMemoryPruneTicks", 864000, 1200, Integer.MAX_VALUE);
         this.conquestMinPlayerHeatTarget = builder.comment("Minimum long-term player presence score for a chunk to be targetable by roots.")
            .defineInRange("conquestMinPlayerHeatTarget", 80, 1, Integer.MAX_VALUE);
         this.conquestMinBaseHeatTarget = builder.comment("Minimum artificial/base block score for a chunk to count as a base-like root target.")
            .defineInRange("conquestMinBaseHeatTarget", 140, 1, Integer.MAX_VALUE);
         this.conquestMaxActiveRoots = builder.comment("Maximum active underground World Roots before World Heart multiplier.")
            .defineInRange("conquestMaxActiveRoots", 10, 0, 256);
         builder.push("black_blood_domain_level");
         this.domainMaxLevel = builder.comment("Single visible Black Blood Domain level cap. Altar blood and conquest stats both feed this one system.")
            .defineInRange("domainMaxLevel", 10, 1, 100);
         this.domainLevel6Xp = builder.comment("Domain XP required for level 6.").defineInRange("domainLevel6Xp", 25000, 0, Integer.MAX_VALUE);
         this.domainLevel7Xp = builder.comment("Domain XP required for level 7.").defineInRange("domainLevel7Xp", 60000, 0, Integer.MAX_VALUE);
         this.domainLevel8Xp = builder.comment("Domain XP required for level 8.").defineInRange("domainLevel8Xp", 150000, 0, Integer.MAX_VALUE);
         this.domainLevel9Xp = builder.comment("Domain XP required for level 9.").defineInRange("domainLevel9Xp", 350000, 0, Integer.MAX_VALUE);
         this.domainLevel10Xp = builder.comment("Domain XP required for level 10, the World Heart tier.")
            .defineInRange("domainLevel10Xp", 900000, 0, Integer.MAX_VALUE);
         this.domainXpPerBloom = builder.comment("Domain XP gained per created Blood Flower Bloom.")
            .defineInRange("domainXpPerBloom", 10000, 0, Integer.MAX_VALUE);
         this.domainXpPerCompletedRoot = builder.comment("Domain XP gained per completed World Root tunnel.")
            .defineInRange("domainXpPerCompletedRoot", 6000, 0, Integer.MAX_VALUE);
         this.domainXpPerBaseBreach = builder.comment("Domain XP gained per player/base breach.")
            .defineInRange("domainXpPerBaseBreach", 30000, 0, Integer.MAX_VALUE);
         this.domainXpPerConqueredChunk = builder.comment("Domain XP gained per conquered chunk.")
            .defineInRange("domainXpPerConqueredChunk", 450, 0, Integer.MAX_VALUE);
         builder.pop();
         this.conquestRequiredDomainLevel = builder.comment("Minimum single Domain Level required before conquest ticks at all.")
            .defineInRange("conquestRequiredDomainLevel", 5, 0, 100);
         this.conquestRequiredDomainLevelForRoots = builder.comment("Minimum single Domain Level required before World Root tunnels start.")
            .defineInRange("conquestRequiredDomainLevelForRoots", 5, 0, 100);
         this.conquestWorldHeartRequiredDomainLevel = builder.comment("Single Domain Level required to form the World Heart.")
            .defineInRange("conquestWorldHeartRequiredDomainLevel", 10, 1, 100);
         this.conquestRootMaxTargetDistanceBlocks = builder.comment(
               "Maximum distance a World Root tunnel may target. 8192 allows thousands of blocks without force-loading chunks."
            )
            .defineInRange("conquestRootMaxTargetDistanceBlocks", 8192, 16, 30000000);
         this.conquestRootBlocksPerTick = builder.comment(
               "How many tunnel blocks each active World Root may grow per root tick. Higher means thousand-block targets are reached faster."
            )
            .defineInRange("conquestRootBlocksPerTick", 100, 1, 256);
         this.conquestMaxRootsProcessedPerTick = builder.comment("Hard budget for active roots processed per root tick.")
            .defineInRange("conquestMaxRootsProcessedPerTick", 8, 1, 256);
         this.conquestRootTickInterval = builder.comment("How often roots move. 40 ticks = 2 seconds.")
            .defineInRange("conquestRootTickInterval", 10, 1, Integer.MAX_VALUE);
         this.conquestRootStartCost = builder.comment("Blood Altar fuel cost to start one World Root.")
            .defineInRange("conquestRootStartCost", 180, 0, Integer.MAX_VALUE);
         this.conquestRootStepCost = builder.comment("Blood Altar fuel cost per World Root step.")
            .defineInRange("conquestRootStepCost", 35, 0, Integer.MAX_VALUE);
         this.conquestRootMaxAge = builder.comment("Maximum root age in root ticks before it dies.")
            .defineInRange("conquestRootMaxAge", 72000, 20, Integer.MAX_VALUE);
         this.conquestRootReachDistanceChunks = builder.comment("Root completes when it reaches this chunk distance from its target.")
            .defineInRange("conquestRootReachDistanceChunks", 1, 0, 64);
         this.conquestBloomTickInterval = builder.comment("How often Blood Flower Blooms tick. 100 ticks = 5 seconds.")
            .defineInRange("conquestBloomTickInterval", 100, 1, Integer.MAX_VALUE);
         this.conquestBloomStartBlood = builder.comment("Stored conquest blood given to normal blooms when they spawn.")
            .defineInRange("conquestBloomStartBlood", 2500, 0, Integer.MAX_VALUE);
         this.conquestBaseBloomBloodMultiplier = builder.comment("Multiplier for starting blood when a bloom erupts from a player-base target.")
            .defineInRange("conquestBaseBloomBloodMultiplier", 3, 1, Integer.MAX_VALUE);
         this.conquestBloomMaxStoredBlood = builder.comment("Maximum stored conquest blood inside one Blood Flower Bloom.")
            .defineInRange("conquestBloomMaxStoredBlood", 250000, 1, Integer.MAX_VALUE);
         this.conquestBloomFeedInterval = builder.comment("Bloom age interval used to feed stored blood to the main altar.")
            .defineInRange("conquestBloomFeedInterval", 200, 1, Integer.MAX_VALUE);
         this.conquestBloomFeedAmount = builder.comment("Blood sent from a bloom to the main altar per feed interval, multiplied by bloom tier.")
            .defineInRange("conquestBloomFeedAmount", 750, 0, Integer.MAX_VALUE);
         this.conquestBloomSpreadBlocks = builder.comment("Block spread budget for normal bloom spread pulses.")
            .defineInRange("conquestBloomSpreadBlocks", 45, 0, Integer.MAX_VALUE);
         this.conquestBloomBurstSpreadBlocks = builder.comment("Initial infection burst blocks for normal Bloom creation.")
            .defineInRange("conquestBloomBurstSpreadBlocks", 140, 0, Integer.MAX_VALUE);
         this.conquestBaseBloomBurstSpreadBlocks = builder.comment("Initial infection burst blocks for base-breach Bloom creation.")
            .defineInRange("conquestBaseBloomBurstSpreadBlocks", 260, 0, Integer.MAX_VALUE);
         this.conquestBloomDrainMaxBlood = builder.comment("Max stored bloom blood a Bloodfiend can drain per interaction.")
            .defineInRange("conquestBloomDrainMaxBlood", 2500, 1, Integer.MAX_VALUE);
         this.conquestBloomGrowthDivisor = builder.comment("Bloom blood divided by this becomes permanent Black Blood Growth.")
            .defineInRange("conquestBloomGrowthDivisor", 250, 1, Integer.MAX_VALUE);
         this.conquestBloomBloodDivisor = builder.comment("Bloom blood divided by this becomes normal stored Bloodfiend blood.")
            .defineInRange("conquestBloomBloodDivisor", 100, 1, Integer.MAX_VALUE);
         this.conquestBloomHealMultiplier = builder.comment("Health restored per drained bloom blood.")
            .defineInRange("conquestBloomHealMultiplier", 0.05, 0.0, Double.MAX_VALUE);
         this.conquestBloomSpiritualMultiplier = builder.comment("Spiritual health restored per drained bloom blood.")
            .defineInRange("conquestBloomSpiritualMultiplier", 0.25, 0.0, Double.MAX_VALUE);
         this.conquestBloomMagiculeMultiplier = builder.comment("Magicules restored per drained bloom blood.")
            .defineInRange("conquestBloomMagiculeMultiplier", 150.0, 0.0, Double.MAX_VALUE);
         this.conquestHeartTickInterval = builder.comment("How often the World Heart ticks. 200 ticks = 10 seconds.")
            .defineInRange("conquestHeartTickInterval", 200, 1, Integer.MAX_VALUE);
         this.conquestWorldHeartStage = builder.comment("World conquest stage number used for the final World Heart state.")
            .defineInRange("conquestWorldHeartStage", 6, 1, Integer.MAX_VALUE);
         this.conquestWorldHeartSpreadBlocks = builder.comment("Block spread budget for each World Heart pulse.")
            .defineInRange("conquestWorldHeartSpreadBlocks", 180, 0, Integer.MAX_VALUE);
         this.conquestWorldHeartMinGrowth = builder.comment("Minimum permanent growth gained from drinking the active World Heart.")
            .defineInRange("conquestWorldHeartMinGrowth", 100, 0, Integer.MAX_VALUE);
         this.conquestWorldHeartHealMultiplier = builder.comment("Health restored per World Heart growth point.")
            .defineInRange("conquestWorldHeartHealMultiplier", 12.0, 0.0, Double.MAX_VALUE);
         this.conquestWorldHeartSpiritualMultiplier = builder.comment("Spiritual health restored per World Heart growth point.")
            .defineInRange("conquestWorldHeartSpiritualMultiplier", 85.0, 0.0, Double.MAX_VALUE);
         this.conquestWorldHeartMagiculeMultiplier = builder.comment("Magicules restored per World Heart growth point.")
            .defineInRange("conquestWorldHeartMagiculeMultiplier", 75000.0, 0.0, Double.MAX_VALUE);
         this.conquestConqueredThreshold = builder.comment("Infection pressure required for a chunk to count as conquered.")
            .defineInRange("conquestConqueredThreshold", 1800, 1, Integer.MAX_VALUE);
         this.conquestBlockBreakPressure = builder.comment("Infection pressure added when a conquest/infected block is broken while conquest is active.")
            .defineInRange("conquestBlockBreakPressure", 25, 0, Integer.MAX_VALUE);
         this.conquestStage1RequiredAltarLevel = builder.comment("Stage 1 requirement: minimum active altar level.")
            .defineInRange("conquestStage1RequiredAltarLevel", 3, 0, 5);
         this.conquestStage1RequiredTotalBlood = builder.comment("Stage 1 requirement: lifetime blood in the best active altar.")
            .defineInRange("conquestStage1RequiredTotalBlood", 20000, 0, Integer.MAX_VALUE);
         this.conquestStage2RequiredBlooms = builder.comment("Stage 2 requirement: created blooms.")
            .defineInRange("conquestStage2RequiredBlooms", 3, 0, Integer.MAX_VALUE);
         this.conquestStage2RequiredCompletedRoots = builder.comment("Stage 2 requirement: completed roots.")
            .defineInRange("conquestStage2RequiredCompletedRoots", 2, 0, Integer.MAX_VALUE);
         this.conquestStage3RequiredBlooms = builder.comment("Stage 3 requirement: created blooms.")
            .defineInRange("conquestStage3RequiredBlooms", 8, 0, Integer.MAX_VALUE);
         this.conquestStage3RequiredBaseBreaches = builder.comment("Stage 3 requirement: base breaches.")
            .defineInRange("conquestStage3RequiredBaseBreaches", 1, 0, Integer.MAX_VALUE);
         this.conquestStage3RequiredConqueredChunks = builder.comment("Stage 3 requirement: conquered chunks.")
            .defineInRange("conquestStage3RequiredConqueredChunks", 12, 0, Integer.MAX_VALUE);
         this.conquestStage4RequiredBlooms = builder.comment("Stage 4 requirement: created blooms.")
            .defineInRange("conquestStage4RequiredBlooms", 14, 0, Integer.MAX_VALUE);
         this.conquestStage4RequiredBaseBreaches = builder.comment("Stage 4 requirement: base breaches.")
            .defineInRange("conquestStage4RequiredBaseBreaches", 3, 0, Integer.MAX_VALUE);
         this.conquestStage4RequiredConqueredChunks = builder.comment("Stage 4 requirement: conquered chunks.")
            .defineInRange("conquestStage4RequiredConqueredChunks", 35, 0, Integer.MAX_VALUE);
         this.conquestStage5RequiredBlooms = builder.comment("Stage 5 requirement: created blooms.")
            .defineInRange("conquestStage5RequiredBlooms", 20, 0, Integer.MAX_VALUE);
         this.conquestStage5RequiredBaseBreaches = builder.comment("Stage 5 requirement: base breaches.")
            .defineInRange("conquestStage5RequiredBaseBreaches", 4, 0, Integer.MAX_VALUE);
         this.conquestStage5RequiredCompletedRoots = builder.comment("Stage 5 requirement: completed roots.")
            .defineInRange("conquestStage5RequiredCompletedRoots", 14, 0, Integer.MAX_VALUE);
         this.conquestStage5RequiredConqueredChunks = builder.comment("Stage 5 requirement: conquered chunks.")
            .defineInRange("conquestStage5RequiredConqueredChunks", 60, 0, Integer.MAX_VALUE);
         this.conquestWorldHeartRequiredBlooms = builder.comment("World Heart requirement: created blooms.")
            .defineInRange("conquestWorldHeartRequiredBlooms", 24, 0, Integer.MAX_VALUE);
         this.conquestWorldHeartRequiredBaseBreaches = builder.comment("World Heart requirement: base breaches.")
            .defineInRange("conquestWorldHeartRequiredBaseBreaches", 5, 0, Integer.MAX_VALUE);
         this.conquestWorldHeartRequiredConqueredChunks = builder.comment("World Heart requirement: conquered chunks.")
            .defineInRange("conquestWorldHeartRequiredConqueredChunks", 80, 0, Integer.MAX_VALUE);
         this.conquestWorldHeartRequiredCompletedRoots = builder.comment("World Heart requirement: completed roots.")
            .defineInRange("conquestWorldHeartRequiredCompletedRoots", 18, 0, Integer.MAX_VALUE);
         this.conquestGrowthHealthPerPoint = builder.comment("Max health attribute gained per permanent Black Blood Growth point.")
            .defineInRange("conquestGrowthHealthPerPoint", 2.0, 0.0, Double.MAX_VALUE);
         this.conquestGrowthAttackPerPoint = builder.comment("Attack damage attribute gained per permanent Black Blood Growth point.")
            .defineInRange("conquestGrowthAttackPerPoint", 0.35, 0.0, Double.MAX_VALUE);
         this.conquestGrowthSpeedPerPoint = builder.comment("Movement speed attribute gained per permanent Black Blood Growth point.")
            .defineInRange("conquestGrowthSpeedPerPoint", 3.0E-5, 0.0, Double.MAX_VALUE);
         this.conquestGrowthMaxHealthCap = builder.comment("Maximum max-health attribute gained from Black Blood Growth.")
            .defineInRange("conquestGrowthMaxHealthCap", 1000000.0, 0.0, Double.MAX_VALUE);
         this.conquestGrowthMaxAttackCap = builder.comment("Maximum attack attribute gained from Black Blood Growth.")
            .defineInRange("conquestGrowthMaxAttackCap", 250000.0, 0.0, Double.MAX_VALUE);
         this.conquestGrowthMaxSpeedCap = builder.comment("Maximum movement-speed attribute gained from Black Blood Growth.")
            .defineInRange("conquestGrowthMaxSpeedCap", 1.5, 0.0, Double.MAX_VALUE);
         builder.push("black_blood_ecology");
         this.ecologyEnabled = builder.comment("Enables the additive slow ecology, structures, converted mobs, fusion, and unloaded virtual frontier.")
            .define("ecologyEnabled", true);
         this.ecologyMinimumWorldStage = builder.comment("The stage needed for ecology to start.")
            .defineInRange("ecologyMinimumWorldStage", 2, 1, Integer.MAX_VALUE);
         this.ecologyVirtualStepInterval = builder.comment("Ticks between unloaded virtual conquest pulses. 1200 ticks is 1 minute.")
            .defineInRange("ecologyVirtualStepInterval", 1200, 200, Integer.MAX_VALUE);
         this.ecologyVirtualClaimSteps = builder.comment("Paid virtual steps required to claim one unloaded chunk.")
            .defineInRange("ecologyVirtualClaimSteps", 4, 2, 127);
         this.ecologyVirtualStepBloodCost = builder.comment("Altar fuel cost for one virtual conquest step.")
            .defineInRange("ecologyVirtualStepBloodCost", 50, 1, Integer.MAX_VALUE);
         this.ecologyMaterializeInterval = builder.comment("Ticks between checking naturally loaded player chunks for virtual infection materialization.")
            .defineInRange("ecologyMaterializeInterval", 200, 100, Integer.MAX_VALUE);
         this.ecologyBuildInterval = builder.comment("Ticks between attempts to queue one autonomous infection structure. 6000 ticks is 5 minutes.")
            .defineInRange("ecologyBuildInterval", 2000, 1200, Integer.MAX_VALUE);
         this.ecologyTickInterval = builder.comment("Base ecology structure processing interval.")
            .defineInRange("ecologyTickInterval", 200, 40, Integer.MAX_VALUE);
         this.ecologyMaxStructures = builder.comment("Maximum autonomous infection structures per dimension.")
            .defineInRange("ecologyMaxStructures", 150, 1, 512);
         this.ecologyBuildBloodCost = builder.comment("Fuel blood reserved by a pending autonomous structure before it forms.")
            .defineInRange("ecologyBuildBloodCost", 300, 1, Integer.MAX_VALUE);
         this.ecologyStructureMinSpacing = builder.comment("Minimum block spacing between autonomous structures.")
            .defineInRange("ecologyStructureMinSpacing", 48, 16, 512);
         this.ecologySpireSpreadInterval = builder.comment("Ticks between a Vein Spire adding a tiny normal loaded-chunk spread pulse.")
            .defineInRange("ecologySpireSpreadInterval", 3600, 1200, Integer.MAX_VALUE);
         this.ecologySpireSpreadBlocks = builder.comment("Normal infection blocks requested by each Vein Spire pulse.")
            .defineInRange("ecologySpireSpreadBlocks", 1, 1, 2);
         this.ecologyNestPulseInterval = builder.comment("Ticks between Conversion Nest corruption pulses.")
            .defineInRange("ecologyNestPulseInterval", 2400, 1200, Integer.MAX_VALUE);
         this.ecologyNestRadius = builder.comment("Loaded radius used by Conversion Nests.").defineInRange("ecologyNestRadius", 14, 6, 64);
         this.ecologyNestPulseDamage = builder.comment("Very small damage dealt by a Conversion Nest while slowly corrupting a mob.")
            .defineInRange("ecologyNestPulseDamage", 1.0, 0.0, 1000.0);
         this.ecologyCorruptionDecayTicks = builder.comment("Corruption disappears after no converted-minion or nest contact for this long.")
            .defineInRange("ecologyCorruptionDecayTicks", 2400, 400, Integer.MAX_VALUE);
         this.ecologyConversionMarks = builder.comment("Corruption marks required before a weakened mob can be converted.")
            .defineInRange("ecologyConversionMarks", 4, 1, 100);
         this.ecologyConversionHealthRatio = builder.comment("Mob health ratio required for conversion.")
            .defineInRange("ecologyConversionHealthRatio", 0.35, 0.01, 1.0);
         this.ecologyMinionCapPerNest = builder.comment("Nearby converted mob cap supported by one nest.").defineInRange("ecologyMinionCapPerNest", 8, 1, 64);
         this.ecologyConversionBloodCost = builder.comment("Structure or altar blood paid for each successful mob conversion.")
            .defineInRange("ecologyConversionBloodCost", 45, 1, Integer.MAX_VALUE);
         this.ecologyMinionThinkInterval = builder.comment("Ticks between converted mob targeting and movement decisions.")
            .defineInRange("ecologyMinionThinkInterval", 40, 20, 400);
         this.ecologyMinionTargetRadius = builder.comment("Loaded targeting radius for converted mobs.").defineInRange("ecologyMinionTargetRadius", 16, 6, 64);
         this.ecologyMinionMoveSpeed = builder.comment("Navigation speed used by converted mobs.").defineInRange("ecologyMinionMoveSpeed", 0.95, 0.1, 3.0);
         this.ecologyMinionAttackInterval = builder.comment("Ticks between handler-driven attacks from converted mobs.")
            .defineInRange("ecologyMinionAttackInterval", 30, 20, 400);
         this.ecologyMinionDamage = builder.comment("Base handler-driven magic damage from a tier 1 converted mob.")
            .defineInRange("ecologyMinionDamage", 10.5, 0.1, 1000.0);
         this.ecologyFusionInterval = builder.comment("Ticks between Fusion Pit attempts. 4800 ticks is 4 minutes.")
            .defineInRange("ecologyFusionInterval", 4800, 2400, Integer.MAX_VALUE);
         this.ecologyFusionRadius = builder.comment("Loaded radius used by a Fusion Pit.").defineInRange("ecologyFusionRadius", 10, 6, 32);
         this.ecologyFusionBloodCost = builder.comment("Structure or altar blood spent when three equal-tier minions fuse.")
            .defineInRange("ecologyFusionBloodCost", 120, 1, Integer.MAX_VALUE);
         this.ecologyMaxFusionTier = builder.comment("Maximum fusion tier. Tier 2 needs three tier 1 mobs and tier 3 needs three tier 2 mobs.")
            .defineInRange("ecologyMaxFusionTier", 8, 2, 8);
         this.ecologyReservoirFeedInterval = builder.comment("Ticks between Blood Reservoir feed attempts. 6000 ticks is 5 minutes.")
            .defineInRange("ecologyReservoirFeedInterval", 6000, 1200, Integer.MAX_VALUE);
         this.ecologyReservoirFeedAmount = builder.comment("Maximum blood a reservoir can forward per feed attempt.")
            .defineInRange("ecologyReservoirFeedAmount", 400, 1, Integer.MAX_VALUE);
         this.ecologyBloodCollectionRadius = builder.comment("Radius used to route blood earned by converted mob kills into structures.")
            .defineInRange("ecologyBloodCollectionRadius", 96, 32, 512);
         this.ecologyStructureBloodCapacity = builder.comment("Maximum kill-earned blood stored by one autonomous structure.")
            .defineInRange("ecologyStructureBloodCapacity", 10000, 100, Integer.MAX_VALUE);
         this.ecologyBloodHealthDivisor = builder.comment("Killed target max health divided by this becomes collected blood before the cap.")
            .defineInRange("ecologyBloodHealthDivisor", 20.0, 1.0, Double.MAX_VALUE);
         this.ecologyMaxBloodPerKill = builder.comment("Maximum structure blood earned from one converted mob kill.")
            .defineInRange("ecologyMaxBloodPerKill", 50, 1, Integer.MAX_VALUE);
         this.ecologyVirtualStepsPerPulse = builder.comment(
               "Base number of frontier entries processed per virtual pulse. Additional structures increase this up to a hard handler cap."
            )
            .defineInRange("ecologyVirtualStepsPerPulse", 2, 1, 16);
         this.ecologyMaxPendingBuilds = builder.comment("Maximum persisted pending structures and remote altars waiting for fuel or a loaded valid site.")
            .defineInRange("ecologyMaxPendingBuilds", 24, 1, 128);
         this.ecologyPendingProcessInterval = builder.comment("Ticks between attempts to complete pending builds.")
            .defineInRange("ecologyPendingProcessInterval", 200, 40, Integer.MAX_VALUE);
         this.ecologyRemoteAltarsEnabled = builder.comment(
               "Allows the ecology to establish distant colony altars even when normal multiple altars are disabled."
            )
            .define("ecologyRemoteAltarsEnabled", true);
         this.ecologyRemoteAltarMinDistance = builder.comment(
               "Minimum distance in blocks from every existing altar before a remote ecology altar may be queued."
            )
            .defineInRange("ecologyRemoteAltarMinDistance", 400, 128, 30000000);
         this.ecologyRemoteAltarRequiredChunkValue = builder.comment("Required infected chunk value for a distant ecology altar site.")
            .defineInRange("ecologyRemoteAltarRequiredChunkValue", 120, 1, Integer.MAX_VALUE);
         this.ecologyRemoteAltarBloodCost = builder.comment("Fuel blood required by a pending distant ecology altar.")
            .defineInRange("ecologyRemoteAltarBloodCost", 400, 1, Integer.MAX_VALUE);
         this.ecologyRemoteAltarSeedFuel = builder.comment("Part of the remote altar cost transferred into the new altar as starting fuel.")
            .defineInRange("ecologyRemoteAltarSeedFuel", 800, 0, Integer.MAX_VALUE);
         this.ecologyRemoteAltarAttemptEvery = builder.comment(
               "Every Nth five-minute build attempt prefers a distant ecology altar when a valid far infection site exists."
            )
            .defineInRange("ecologyRemoteAltarAttemptEvery", 4, 1, 64);
         this.ecologyWorldHeartRootCount = builder.comment("Number of ecology World Roots maintained from infection nodes toward the current target.")
            .defineInRange("ecologyWorldHeartRootCount", 40, 1, 12);
         this.ecologyWorldHeartRootInterval = builder.comment(
               "Ticks between ecology attempts to queue or launch World Roots from the nearest infection source."
            )
            .defineInRange("ecologyWorldHeartRootInterval", 6000, 1200, Integer.MAX_VALUE);
         this.ecologySummonerInterval = builder.comment("Ticks between Summoner Bloom attempts.")
            .defineInRange("ecologySummonerInterval", 1200, 1200, Integer.MAX_VALUE);
         this.ecologySummonerBloodCost = builder.comment("Structure or altar blood spent for one summoned blood minion.")
            .defineInRange("ecologySummonerBloodCost", 20, 1, Integer.MAX_VALUE);
         this.ecologySummonerCap = builder.comment("Nearby converted minion cap supported by one Summoner Bloom.")
            .defineInRange("ecologySummonerCap", 30, 1, 64);
         this.ecologyRelayInterval = builder.comment("Ticks between Root Relay virtual expansion pulses.")
            .defineInRange("ecologyRelayInterval", 2400, 600, Integer.MAX_VALUE);
         this.ecologyRelayBloodCost = builder.comment("Blood spent by a Root Relay for one local virtual expansion pulse.")
            .defineInRange("ecologyRelayBloodCost", 60, 1, Integer.MAX_VALUE);
         this.ecologyRelaySteps = builder.comment("Local frontier steps attempted by a Root Relay per paid pulse.").defineInRange("ecologyRelaySteps", 2, 1, 8);
         this.ecologySanctuaryInterval = builder.comment("Ticks between Blood Sanctuary healing pulses.")
            .defineInRange("ecologySanctuaryInterval", 400, 100, Integer.MAX_VALUE);
         this.ecologySanctuaryRadius = builder.comment("Loaded healing radius of a Blood Sanctuary.").defineInRange("ecologySanctuaryRadius", 20, 6, 64);
         this.ecologySanctuaryHeal = builder.comment("Health restored to Bloodfiends and converted blood minions per Sanctuary pulse.")
            .defineInRange("ecologySanctuaryHeal", 25.0, 0.0, Double.MAX_VALUE);
         this.ecologyHuntingBeaconInterval = builder.comment("Ticks between Hunting Beacon target assignments.")
            .defineInRange("ecologyHuntingBeaconInterval", 600, 100, Integer.MAX_VALUE);
         this.ecologyHuntingBeaconRadius = builder.comment("Loaded radius used by Hunting Beacons to coordinate minions.")
            .defineInRange("ecologyHuntingBeaconRadius", 32, 8, 96);
         this.ecologyFusionPowerPerConqueredChunk = builder.comment("Internal infection power granted per conquered chunk before fusion scaling.")
            .defineInRange("ecologyFusionPowerPerConqueredChunk", 3, 0, 1000);
         this.ecologyFusionHealthPerPower = builder.comment("Tier-scaled max-health multiplier gained per infection power.")
            .defineInRange("ecologyFusionHealthPerPower", 0.08, 0.0, 1000.0);
         this.ecologyFusionAttackPerPower = builder.comment("Tier-scaled attack multiplier gained per infection power.")
            .defineInRange("ecologyFusionAttackPerPower", 0.012, 0.0, 1000.0);
         this.ecologyFusionMaxHealthMultiplier = builder.comment(
               "Maximum infection-based fused-minion max-health multiplier. 250 allows ordinary mobs to reach several thousand health late-game."
            )
            .defineInRange("ecologyFusionMaxHealthMultiplier", 250.0, 0.0, 100000.0);
         this.ecologyFusionMaxAttackMultiplier = builder.comment("Maximum infection-based fused-minion attack multiplier.")
            .defineInRange("ecologyFusionMaxAttackMultiplier", 2400.0, 0.0, 100000.0);
         this.ecologyFusionMaxArmor = builder.comment("Maximum flat armor added to fused minions from infection strength.")
            .defineInRange("ecologyFusionMaxArmor", 800.0, 0.0, 100000.0);
         builder.pop();
         builder.pop();
      }
   }

   public static final class Blood {
      public final IntValue defaultNaturalGeneration;
      public final IntValue maxGeneration;
      public final DoubleValue bloodCapacityPenaltyPerGeneration;
      public final DoubleValue bloodArtPenaltyPerGeneration;
      public final DoubleValue waterWeaknessPerGeneration;
      public final DoubleValue minimumPowerMultiplier;
      public final IntValue passiveInterval;
      public final IntValue passiveBloodCost;
      public final DoubleValue passiveHeal;
      public final DoubleValue passiveSpiritualHeal;
      public final DoubleValue passiveMagiculeRestore;
      public final IntValue waterCheckInterval;
      public final IntValue waterBloodDrain;
      public final IntValue rainBloodDrain;
      public final DoubleValue waterDamage;
      public final DoubleValue rainDamage;
      public final DoubleValue waterMagiculeDrain;
      public final IntValue hungerInterval;
      public final IntValue hungerBloodCost;
      public final IntValue hungerFoodRestore;
      public final DoubleValue hungerSaturationRestore;
      public final IntValue minimumFoodLevelFromBlood;
      public final IntValue masterBondInterval;
      public final IntValue masterBondBloodGain;
      public final DoubleValue masterBondRange;
      public final DoubleValue masterBondBloodArtMultiplier;
      public final DoubleValue masterBondBloodCapacityMultiplier;
      public final DoubleValue masterBondBloodCostMultiplier;
      public final DoubleValue masterBondWaterWeaknessMultiplier;

      Blood(Builder builder) {
         builder.push("kindred");
         this.defaultNaturalGeneration = builder.comment(
               "Generation assigned to naturally selected Bloodfiend players. Natural Bloodfiends should usually be generation 1."
            )
            .defineInRange("defaultNaturalGeneration", 1, 1, Integer.MAX_VALUE);
         this.maxGeneration = builder.comment("Maximum allowed kindred generation.").defineInRange("maxGeneration", 12, 1, Integer.MAX_VALUE);
         this.bloodCapacityPenaltyPerGeneration = builder.comment("Blood capacity penalty per generation after generation 1.")
            .defineInRange("bloodCapacityPenaltyPerGeneration", 0.025, 0.0, 1.0);
         this.bloodArtPenaltyPerGeneration = builder.comment("Blood Arts power penalty per generation after generation 1.")
            .defineInRange("bloodArtPenaltyPerGeneration", 0.02, 0.0, 1.0);
         this.waterWeaknessPerGeneration = builder.comment("Water weakness increase per generation after generation 1.")
            .defineInRange("waterWeaknessPerGeneration", 0.04, 0.0, 16.0);
         this.minimumPowerMultiplier = builder.comment("Minimum multiplier after generation penalties.")
            .defineInRange("minimumPowerMultiplier", 0.55, 0.0, 1.0);
         builder.pop();
         builder.push("passive_regeneration");
         this.passiveInterval = builder.comment(
               "How often passive blood regeneration checks. 240 ticks = 12 seconds. Blood is only consumed if health, spiritual health, or magicules are missing."
            )
            .defineInRange("passiveInterval", 240, 1, Integer.MAX_VALUE);
         this.passiveBloodCost = builder.comment("Blood consumed per passive regeneration pulse.").defineInRange("passiveBloodCost", 1, 0, Integer.MAX_VALUE);
         this.passiveHeal = builder.comment("Health healed per passive regeneration pulse.").defineInRange("passiveHeal", 2.0, 0.0, Double.MAX_VALUE);
         this.passiveSpiritualHeal = builder.comment("Spiritual health restored per passive regeneration pulse.")
            .defineInRange("passiveSpiritualHeal", 35.0, 0.0, Double.MAX_VALUE);
         this.passiveMagiculeRestore = builder.comment("Magicules restored per passive regeneration pulse.")
            .defineInRange("passiveMagiculeRestore", 75.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("water_weakness");
         this.waterCheckInterval = builder.comment("How often water and rain weakness checks. 160 ticks = 8 seconds.")
            .defineInRange("waterCheckInterval", 160, 1, Integer.MAX_VALUE);
         this.waterBloodDrain = builder.comment("Blood drained when touching water.").defineInRange("waterBloodDrain", 1, 0, Integer.MAX_VALUE);
         this.rainBloodDrain = builder.comment("Blood drained when standing in rain.").defineInRange("rainBloodDrain", 0, 0, Integer.MAX_VALUE);
         this.waterDamage = builder.comment("Damage dealt when touching water.").defineInRange("waterDamage", 0.75, 0.0, Double.MAX_VALUE);
         this.rainDamage = builder.comment("Damage dealt when standing in rain.").defineInRange("rainDamage", 0.1, 0.0, Double.MAX_VALUE);
         this.waterMagiculeDrain = builder.comment("Magicules drained when touching water.").defineInRange("waterMagiculeDrain", 15.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("blood_hunger");
         this.hungerInterval = builder.comment("How often Bloodfiends convert stored blood into hunger. 300 ticks = 15 seconds.")
            .defineInRange("hungerInterval", 300, 1, Integer.MAX_VALUE);
         this.hungerBloodCost = builder.comment("Blood consumed whenever hunger is restored.").defineInRange("hungerBloodCost", 1, 0, Integer.MAX_VALUE);
         this.hungerFoodRestore = builder.comment("Food points restored from blood. 2 food points = 1 hunger shank.")
            .defineInRange("hungerFoodRestore", 4, 0, 20);
         this.hungerSaturationRestore = builder.comment("Saturation restored from blood feeding.").defineInRange("hungerSaturationRestore", 2.0, 0.0, 20.0);
         this.minimumFoodLevelFromBlood = builder.comment("If the player has blood, their hunger is kept at least this high.")
            .defineInRange("minimumFoodLevelFromBlood", 18, 0, 20);
         builder.pop();
         builder.push("master_bond");
         this.masterBondInterval = builder.comment("How often kindred with a living Bloodfiend master receive a bond pulse.")
            .defineInRange("masterBondInterval", 200, 1, Integer.MAX_VALUE);
         this.masterBondBloodGain = builder.comment("Blood gained by kindred near their living master.")
            .defineInRange("masterBondBloodGain", 1, 0, Integer.MAX_VALUE);
         this.masterBondRange = builder.comment("Range required for master bond blood pulse.").defineInRange("masterBondRange", 32.0, 0.0, 512.0);
         this.masterBondBloodArtMultiplier = builder.comment("Blood Arts power multiplier for kindred with a living Bloodfiend master.")
            .defineInRange("masterBondBloodArtMultiplier", 1.12, 1.0, 16.0);
         this.masterBondBloodCapacityMultiplier = builder.comment("Max blood multiplier for kindred with a living Bloodfiend master.")
            .defineInRange("masterBondBloodCapacityMultiplier", 1.15, 1.0, 16.0);
         this.masterBondBloodCostMultiplier = builder.comment("Blood cost multiplier for kindred with a living Bloodfiend master.")
            .defineInRange("masterBondBloodCostMultiplier", 0.75, 0.0, 1.0);
         this.masterBondWaterWeaknessMultiplier = builder.comment("Water weakness multiplier for kindred with a living Bloodfiend master.")
            .defineInRange("masterBondWaterWeaknessMultiplier", 0.75, 0.0, 1.0);
         builder.pop();
      }
   }

   public static final class BloodArts {
      public final IntValue maxMastery;
      public final DoubleValue drainRange;
      public final IntValue drainTickInterval;
      public final IntValue drainPulseCooldown;
      public final IntValue drainBloodGain;
      public final DoubleValue drainDamage;
      public final IntValue drainCooldown;
      public final IntValue bloodBrandDrainMultiplier;
      public final DoubleValue lowHealthDrainBonusThreshold;
      public final DoubleValue lowHealthDrainBonusMultiplier;
      public final IntValue bloodSurgeCost;
      public final IntValue bloodSurgeCostInterval;
      public final DoubleValue bloodSurgeDamageMultiplier;
      public final IntValue crimsonGuardCost;
      public final IntValue crimsonGuardDuration;
      public final IntValue crimsonGuardCooldown;
      public final IntValue crimsonGuardHitBloodCost;
      public final IntValue crimsonGuardHitCostInterval;
      public final DoubleValue crimsonGuardDamageTakenMultiplier;
      public final IntValue bloodBrandCost;
      public final IntValue bloodBrandDuration;
      public final IntValue bloodBrandCooldown;
      public final IntValue kindredRiteCost;
      public final IntValue kindredRiteCooldown;
      public final DoubleValue kindredRiteRange;
      public final DoubleValue mobTurnHealthThreshold;
      public final DoubleValue playerTurnHealthThreshold;
      public final DoubleValue instantTurnEpRatio;
      public final IntValue playerTurnConsentTicks;
      public final IntValue kindredRiteSuccessBloodGain;
      public final IntValue maxActiveBloodThralls;
      public final BooleanValue allowMobTurning;
      public final BooleanValue allowPlayerTurning;

      BloodArts(Builder builder) {
         this.maxMastery = builder.comment("Maximum mastery for Blood Arts.").defineInRange("maxMastery", 1000, 1, Integer.MAX_VALUE);
         builder.push("drain_blood");
         this.drainRange = builder.comment("Target range for held Drain Blood.").defineInRange("drainRange", 10.0, 0.0, 128.0);
         this.drainTickInterval = builder.comment("How often held Drain Blood pulses. 14 ticks is a little slower than twice per second.")
            .defineInRange("drainTickInterval", 14, 1, 200);
         this.drainPulseCooldown = builder.comment(
               "Tiny cooldown after each drain pulse. Usually keep this at 0 because the held interval already controls pacing."
            )
            .defineInRange("drainPulseCooldown", 0, 0, Integer.MAX_VALUE);
         this.drainBloodGain = builder.comment("Blood gained per Drain Blood pulse before generation modifiers.")
            .defineInRange("drainBloodGain", 8, 0, Integer.MAX_VALUE);
         this.drainDamage = builder.comment("Damage dealt per Drain Blood pulse. Kept low so Kindred Rite can be used before targets die.")
            .defineInRange("drainDamage", 1.0, 0.0, Double.MAX_VALUE);
         this.drainCooldown = builder.comment("Unused legacy cooldown for older press-drain behavior.").defineInRange("drainCooldown", 0, 0, Integer.MAX_VALUE);
         this.bloodBrandDrainMultiplier = builder.comment("Drain Blood gain multiplier against Blood Branded targets.")
            .defineInRange("bloodBrandDrainMultiplier", 3, 1, Integer.MAX_VALUE);
         this.lowHealthDrainBonusThreshold = builder.comment("Targets at or below this health ratio give bonus blood.")
            .defineInRange("lowHealthDrainBonusThreshold", 0.35, 0.0, 1.0);
         this.lowHealthDrainBonusMultiplier = builder.comment("Blood gain multiplier against low-health targets.")
            .defineInRange("lowHealthDrainBonusMultiplier", 1.75, 1.0, 64.0);
         builder.pop();
         builder.push("blood_surge");
         this.bloodSurgeCost = builder.comment("Blood consumed by Blood Surge per cost interval while Blood Arts is toggled and damage is boosted.")
            .defineInRange("bloodSurgeCost", 1, 0, Integer.MAX_VALUE);
         this.bloodSurgeCostInterval = builder.comment("Minimum ticks between Blood Surge blood payments. 40 ticks = 2 seconds.")
            .defineInRange("bloodSurgeCostInterval", 40, 1, Integer.MAX_VALUE);
         this.bloodSurgeDamageMultiplier = builder.comment("Damage multiplier added by Blood Surge. 0.45 means +45% before generation and master scaling.")
            .defineInRange("bloodSurgeDamageMultiplier", 0.45, 0.0, 1024.0);
         builder.pop();
         builder.push("crimson_guard");
         this.crimsonGuardCost = builder.comment("Blood cost to activate Crimson Guard.").defineInRange("crimsonGuardCost", 12, 0, Integer.MAX_VALUE);
         this.crimsonGuardDuration = builder.comment("Duration of Crimson Guard in ticks.").defineInRange("crimsonGuardDuration", 200, 1, Integer.MAX_VALUE);
         this.crimsonGuardCooldown = builder.comment("Cooldown for Crimson Guard in seconds.").defineInRange("crimsonGuardCooldown", 10, 0, Integer.MAX_VALUE);
         this.crimsonGuardHitBloodCost = builder.comment("Extra blood consumed when Crimson Guard reduces incoming damage.")
            .defineInRange("crimsonGuardHitBloodCost", 1, 0, Integer.MAX_VALUE);
         this.crimsonGuardHitCostInterval = builder.comment("Minimum ticks between Crimson Guard hit blood costs. 40 ticks = 2 seconds.")
            .defineInRange("crimsonGuardHitCostInterval", 40, 1, Integer.MAX_VALUE);
         this.crimsonGuardDamageTakenMultiplier = builder.comment("Incoming damage multiplier while Crimson Guard is active. 0.45 means 55% reduction.")
            .defineInRange("crimsonGuardDamageTakenMultiplier", 0.45, 0.0, 1.0);
         builder.pop();
         builder.push("blood_brand");
         this.bloodBrandCost = builder.comment("Blood cost to brand a target.").defineInRange("bloodBrandCost", 8, 0, Integer.MAX_VALUE);
         this.bloodBrandDuration = builder.comment("Duration of Blood Brand in ticks.").defineInRange("bloodBrandDuration", 600, 1, Integer.MAX_VALUE);
         this.bloodBrandCooldown = builder.comment("Cooldown for Blood Brand in seconds.").defineInRange("bloodBrandCooldown", 5, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("kindred_rite");
         this.kindredRiteCost = builder.comment("Blood cost to use Kindred Rite.").defineInRange("kindredRiteCost", 90, 0, Integer.MAX_VALUE);
         this.kindredRiteCooldown = builder.comment("Cooldown for Kindred Rite in seconds.").defineInRange("kindredRiteCooldown", 45, 0, Integer.MAX_VALUE);
         this.kindredRiteRange = builder.comment("Target range for Kindred Rite.").defineInRange("kindredRiteRange", 8.0, 0.0, 128.0);
         this.mobTurnHealthThreshold = builder.comment("Target mob must be at or below this health ratio to be turned unless EP ratio allows instant turning.")
            .defineInRange("mobTurnHealthThreshold", 0.35, 0.0, 1.0);
         this.playerTurnHealthThreshold = builder.comment(
               "Target player must be at or below this health ratio unless EP ratio allows instant turning. Consent is still required."
            )
            .defineInRange("playerTurnHealthThreshold", 0.3, 0.0, 1.0);
         this.instantTurnEpRatio = builder.comment(
               "If target EP is below this ratio of owner EP, Kindred Rite ignores the health threshold. 0.01 means below 1 percent."
            )
            .defineInRange("instantTurnEpRatio", 0.01, 0.0, 1.0);
         this.playerTurnConsentTicks = builder.comment(
               "Player turning consent window in ticks. The target must be prompted first, then sneak during a second Kindred Rite attempt. 1200 ticks = 60 seconds."
            )
            .defineInRange("playerTurnConsentTicks", 1200, 20, Integer.MAX_VALUE);
         this.kindredRiteSuccessBloodGain = builder.comment("Blood gained by the owner after a successful Kindred Rite.")
            .defineInRange("kindredRiteSuccessBloodGain", 35, 0, Integer.MAX_VALUE);
         this.maxActiveBloodThralls = builder.comment("Maximum active blood thralls.").defineInRange("maxActiveBloodThralls", 6, 0, Integer.MAX_VALUE);
         this.allowMobTurning = builder.comment("If true, Kindred Rite can turn weakened mobs.").define("allowMobTurning", true);
         this.allowPlayerTurning = builder.comment("If true, Kindred Rite can turn consenting weakened players into Bloodfiends.")
            .define("allowPlayerTurning", true);
         builder.pop();
      }
   }

   public static final class BloodMonarch extends MoreskillsRaceConfig.BloodfiendStats {
      BloodMonarch(Builder builder) {
         super(builder, 1500000.0, 0, 5500, 50, 700000.0, 950000.0, 850000.0, 1200000.0, 0.05, 700.0, 5000.0, 6.5, 0.42, 0.28, 0.055, -0.15, 240, 80);
      }
   }

   public static final class BloodNoble extends MoreskillsRaceConfig.BloodfiendStats {
      BloodNoble(Builder builder) {
         super(builder, 450000.0, 0, 1500, 18, 160000.0, 240000.0, 180000.0, 300000.0, 0.0, 300.0, 2500.0, 4.0, 0.28, 0.18, 0.045, -0.22, 185, 65);
      }
   }

   public static final class Bloodfiend extends MoreskillsRaceConfig.BloodfiendStats {
      Bloodfiend(Builder builder) {
         super(builder, 0.0, 0, 0, 0, 7500.0, 12000.0, 9000.0, 16000.0, 0.0, 20.0, 120.0, 1.5, 0.1, 0.05, 0.025, -0.35, 100, 45);
      }
   }

   public static class BloodfiendStats extends Default {
      public final DoubleValue epRequirement;
      public final IntValue bossRequirement;
      public final IntValue bloodRequirement;
      public final IntValue mobTurnRequirement;
      public final DoubleValue minAura;
      public final DoubleValue maxAura;
      public final DoubleValue minMagicule;
      public final DoubleValue maxMagicule;
      public final DoubleValue size;
      public final DoubleValue maxHealth;
      public final DoubleValue maxSpiritualHealth;
      public final DoubleValue attack;
      public final DoubleValue attackSpeed;
      public final DoubleValue knockbackResistance;
      public final DoubleValue movementSpeed;
      public final DoubleValue swimSpeed;
      public final IntValue maxBlood;
      public final IntValue bloodRegenThreshold;
      private final double defaultMinAura;
      private final double defaultMaxAura;
      private final double defaultMinMagicule;
      private final double defaultMaxMagicule;
      private final double defaultSize;
      private final double defaultMaxHealth;
      private final double defaultMaxSpiritualHealth;
      private final double defaultAttack;
      private final double defaultAttackSpeed;
      private final double defaultKnockbackResistance;
      private final double defaultMovementSpeed;
      private final double defaultSwimSpeed;

      BloodfiendStats(
         Builder builder,
         double epRequirement,
         int bossRequirement,
         int bloodRequirement,
         int mobTurnRequirement,
         double minAura,
         double maxAura,
         double minMagicule,
         double maxMagicule,
         double size,
         double maxHealth,
         double maxSpiritualHealth,
         double attack,
         double attackSpeed,
         double knockbackResistance,
         double movementSpeed,
         double swimSpeed,
         int maxBlood,
         int bloodRegenThreshold
      ) {
         this.defaultMinAura = minAura;
         this.defaultMaxAura = maxAura;
         this.defaultMinMagicule = minMagicule;
         this.defaultMaxMagicule = maxMagicule;
         this.defaultSize = size;
         this.defaultMaxHealth = maxHealth;
         this.defaultMaxSpiritualHealth = maxSpiritualHealth;
         this.defaultAttack = attack;
         this.defaultAttackSpeed = attackSpeed;
         this.defaultKnockbackResistance = knockbackResistance;
         this.defaultMovementSpeed = movementSpeed;
         this.defaultSwimSpeed = swimSpeed;
         builder.push("evolution");
         this.epRequirement = builder.comment("EP required to evolve into this race. The base Bloodfiend value is unused for normal starting race selection.")
            .defineInRange("epRequirement", epRequirement, 0.0, Double.MAX_VALUE);
         this.bossRequirement = builder.comment("Boss kills required to evolve into this race. Set to 0 to disable this requirement.")
            .defineInRange("bossRequirement", bossRequirement, 0, Integer.MAX_VALUE);
         this.bloodRequirement = builder.comment("Total blood drained required to evolve into this race. Set to 0 to disable this requirement.")
            .defineInRange("bloodRequirement", bloodRequirement, 0, Integer.MAX_VALUE);
         this.mobTurnRequirement = builder.comment(
               "Total mobs or players turned into kindred required to evolve into this race. Set to 0 to disable this requirement."
            )
            .defineInRange("mobTurnRequirement", mobTurnRequirement, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("base_energy");
         this.minAura = builder.comment("Minimum base aura for this race.").defineInRange("minAura", minAura, 0.0, Double.MAX_VALUE);
         this.maxAura = builder.comment("Maximum base aura for this race.").defineInRange("maxAura", maxAura, 0.0, Double.MAX_VALUE);
         this.minMagicule = builder.comment("Minimum base magicule for this race.").defineInRange("minMagicule", minMagicule, 0.0, Double.MAX_VALUE);
         this.maxMagicule = builder.comment("Maximum base magicule for this race.").defineInRange("maxMagicule", maxMagicule, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("attributes");
         this.size = builder.comment("Scale modifier for this race. 0 means normal size.").defineInRange("size", size, -0.9, 16.0);
         this.maxHealth = builder.comment("Max health bonus/value used by the race config.").defineInRange("maxHealth", maxHealth, 0.0, Double.MAX_VALUE);
         this.maxSpiritualHealth = builder.comment("Max spiritual health bonus/value used by the race config.")
            .defineInRange("maxSpiritualHealth", maxSpiritualHealth, 0.0, Double.MAX_VALUE);
         this.attack = builder.comment("Attack damage bonus/value used by the race config.").defineInRange("attack", attack, 0.0, Double.MAX_VALUE);
         this.attackSpeed = builder.comment("Attack speed modifier used by the race config.").defineInRange("attackSpeed", attackSpeed, -1.0, Double.MAX_VALUE);
         this.knockbackResistance = builder.comment("Knockback resistance modifier used by the race config.")
            .defineInRange("knockbackResistance", knockbackResistance, 0.0, 1.0);
         this.movementSpeed = builder.comment("Movement speed modifier used by the race config.")
            .defineInRange("movementSpeed", movementSpeed, -1.0, Double.MAX_VALUE);
         this.swimSpeed = builder.comment("Swim speed modifier. Negative values make Bloodfiends worse in water.")
            .defineInRange("swimSpeed", swimSpeed, -1.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("blood");
         this.maxBlood = builder.comment("Maximum stored blood for this race before generation penalties.")
            .defineInRange("maxBlood", maxBlood, 1, Integer.MAX_VALUE);
         this.bloodRegenThreshold = builder.comment("Blood amount required before passive blood regeneration can activate.")
            .defineInRange("bloodRegenThreshold", bloodRegenThreshold, 0, Integer.MAX_VALUE);
         builder.pop();
      }

      private static boolean loaded() {
         return MoreskillsRaceConfig.COMMON_SPEC != null && MoreskillsRaceConfig.COMMON_SPEC.isLoaded();
      }

      public double getMinAura() {
         return loaded() ? (Double)this.minAura.get() : this.defaultMinAura;
      }

      public double getMaxAura() {
         return loaded() ? (Double)this.maxAura.get() : this.defaultMaxAura;
      }

      public double getMinMagicule() {
         return loaded() ? (Double)this.minMagicule.get() : this.defaultMinMagicule;
      }

      public double getMaxMagicule() {
         return loaded() ? (Double)this.maxMagicule.get() : this.defaultMaxMagicule;
      }

      public double getSize() {
         return loaded() ? (Double)this.size.get() : this.defaultSize;
      }

      public double getMaxHealth() {
         return loaded() ? (Double)this.maxHealth.get() : this.defaultMaxHealth;
      }

      public double getMaxSpiritualHealth() {
         return loaded() ? (Double)this.maxSpiritualHealth.get() : this.defaultMaxSpiritualHealth;
      }

      public double getAttack() {
         return loaded() ? (Double)this.attack.get() : this.defaultAttack;
      }

      public double getAttackSpeed() {
         return loaded() ? (Double)this.attackSpeed.get() : this.defaultAttackSpeed;
      }

      public double getKnockbackResistance() {
         return loaded() ? (Double)this.knockbackResistance.get() : this.defaultKnockbackResistance;
      }

      public double getMovementSpeed() {
         return loaded() ? (Double)this.movementSpeed.get() : this.defaultMovementSpeed;
      }

      public double getSwimSpeed() {
         return loaded() ? (Double)this.swimSpeed.get() : this.defaultSwimSpeed;
      }
   }

   public static final class Config {
      public final MoreskillsRaceConfig.Bloodfiend Bloodfiend;
      public final MoreskillsRaceConfig.Parasite Parasite;
      public final MoreskillsRaceConfig.ElderBloodfiend ElderBloodfiend;
      public final MoreskillsRaceConfig.BloodNoble BloodNoble;
      public final MoreskillsRaceConfig.BloodMonarch BloodMonarch;
      public final MoreskillsRaceConfig.CrimsonProgenitor CrimsonProgenitor;
      public final MoreskillsRaceConfig.Blood Blood;
      public final MoreskillsRaceConfig.BlackCommunion BlackCommunion;
      public final MoreskillsRaceConfig.BloodArts BloodArts;

      Config(Builder builder) {
         builder.comment(
            "Race balance config for Tensura More Skills. Contains Bloodfiend race stats, blood system values, Blood Arts costs, and evolution requirements."
         );
         builder.push("bloodfiend");
         this.Bloodfiend = new MoreskillsRaceConfig.Bloodfiend(builder);
         builder.pop();
         builder.push("elder_bloodfiend");
         this.ElderBloodfiend = new MoreskillsRaceConfig.ElderBloodfiend(builder);
         builder.pop();
         builder.push("blood_noble");
         this.BloodNoble = new MoreskillsRaceConfig.BloodNoble(builder);
         builder.pop();
         builder.push("blood_monarch");
         builder.push("black_communion");
         this.BlackCommunion = new MoreskillsRaceConfig.BlackCommunion(builder);
         builder.pop();
         this.BloodMonarch = new MoreskillsRaceConfig.BloodMonarch(builder);
         builder.pop();
         builder.push("crimson_progenitor");
         this.CrimsonProgenitor = new MoreskillsRaceConfig.CrimsonProgenitor(builder);
         builder.pop();
         builder.push("blood");
         this.Blood = new MoreskillsRaceConfig.Blood(builder);
         builder.pop();
         builder.push("blood_arts");
         this.BloodArts = new MoreskillsRaceConfig.BloodArts(builder);
         builder.pop();
         builder.push("parasite");
         this.Parasite = new MoreskillsRaceConfig.Parasite(builder);
         builder.pop();
      }
   }

   public static final class CrimsonProgenitor extends MoreskillsRaceConfig.BloodfiendStats {
      CrimsonProgenitor(Builder builder) {
         super(builder, 3500000.0, 2, 15000, 120, 1800000.0, 2400000.0, 2200000.0, 3000000.0, 0.08, 1100.0, 10000.0, 9.0, 0.65, 0.4, 0.07, -0.05, 320, 110);
      }
   }

   public static final class ElderBloodfiend extends MoreskillsRaceConfig.BloodfiendStats {
      ElderBloodfiend(Builder builder) {
         super(builder, 120000.0, 0, 400, 5, 40000.0, 65000.0, 50000.0, 85000.0, 0.0, 80.0, 900.0, 2.5, 0.18, 0.1, 0.035, -0.28, 140, 55);
      }
   }

   public static final class Parasite extends Default {
      public final DoubleValue minAura;
      public final DoubleValue maxAura;
      public final DoubleValue minMagicule;
      public final DoubleValue maxMagicule;
      public final DoubleValue size;
      public final DoubleValue maxHealth;
      public final DoubleValue maxSpiritualHealth;
      public final DoubleValue attack;
      public final DoubleValue attackSpeed;
      public final DoubleValue knockbackResistance;
      public final DoubleValue movementSpeed;
      public final DoubleValue swimSpeed;
      private final double defaultMinAura = 100.0;
      private final double defaultMaxAura = 200.0;
      private final double defaultMinMagicule = 100.0;
      private final double defaultMaxMagicule = 200.0;
      private final double defaultSize = -0.9;
      private final double defaultMaxHealth = 5.0;
      private final double defaultMaxSpiritualHealth = 5.0;
      private final double defaultAttack = 0.0;
      private final double defaultAttackSpeed = 0.0;
      private final double defaultKnockbackResistance = 0.0;
      private final double defaultMovementSpeed = 0.0;
      private final double defaultSwimSpeed = 0.0;

      Parasite(Builder builder) {
         builder.push("base_energy");
         this.minAura = builder.defineInRange("minAura", this.defaultMinAura, 0.0, Double.MAX_VALUE);
         this.maxAura = builder.defineInRange("maxAura", this.defaultMaxAura, 0.0, Double.MAX_VALUE);
         this.minMagicule = builder.defineInRange("minMagicule", this.defaultMinMagicule, 0.0, Double.MAX_VALUE);
         this.maxMagicule = builder.defineInRange("maxMagicule", this.defaultMaxMagicule, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("attributes");
         this.size = builder.defineInRange("size", this.defaultSize, -0.9, 16.0);
         this.maxHealth = builder.defineInRange("maxHealth", this.defaultMaxHealth, -1024.0, Double.MAX_VALUE);
         this.maxSpiritualHealth = builder.defineInRange("maxSpiritualHealth", this.defaultMaxSpiritualHealth, 0.0, Double.MAX_VALUE);
         this.attack = builder.defineInRange("attack", this.defaultAttack, 0.0, Double.MAX_VALUE);
         this.attackSpeed = builder.defineInRange("attackSpeed", this.defaultAttackSpeed, -1.0, Double.MAX_VALUE);
         this.knockbackResistance = builder.defineInRange("knockbackResistance", this.defaultKnockbackResistance, 0.0, 1.0);
         this.movementSpeed = builder.defineInRange("movementSpeed", this.defaultMovementSpeed, -1.0, Double.MAX_VALUE);
         this.swimSpeed = builder.defineInRange("swimSpeed", this.defaultSwimSpeed, -1.0, Double.MAX_VALUE);
         builder.pop();
      }

      private static boolean loaded() {
         return MoreskillsRaceConfig.COMMON_SPEC != null && MoreskillsRaceConfig.COMMON_SPEC.isLoaded();
      }

      public double getEpRequirement() {
         return 0.0;
      }

      public double getMinAura() {
         return loaded() ? (Double)this.minAura.get() : this.defaultMinAura;
      }

      public double getMaxAura() {
         return loaded() ? (Double)this.maxAura.get() : this.defaultMaxAura;
      }

      public double getMinMagicule() {
         return loaded() ? (Double)this.minMagicule.get() : this.defaultMinMagicule;
      }

      public double getMaxMagicule() {
         return loaded() ? (Double)this.maxMagicule.get() : this.defaultMaxMagicule;
      }

      public double getSize() {
         return loaded() ? (Double)this.size.get() : this.defaultSize;
      }

      public double getMaxHealth() {
         return loaded() ? (Double)this.maxHealth.get() : this.defaultMaxHealth;
      }

      public double getMaxSpiritualHealth() {
         return loaded() ? (Double)this.maxSpiritualHealth.get() : this.defaultMaxSpiritualHealth;
      }

      public double getAttack() {
         return loaded() ? (Double)this.attack.get() : this.defaultAttack;
      }

      public double getAttackSpeed() {
         return loaded() ? (Double)this.attackSpeed.get() : this.defaultAttackSpeed;
      }

      public double getKnockbackResistance() {
         return loaded() ? (Double)this.knockbackResistance.get() : this.defaultKnockbackResistance;
      }

      public double getMovementSpeed() {
         return loaded() ? (Double)this.movementSpeed.get() : this.defaultMovementSpeed;
      }

      public double getSwimSpeed() {
         return loaded() ? (Double)this.swimSpeed.get() : this.defaultSwimSpeed;
      }
   }
}
