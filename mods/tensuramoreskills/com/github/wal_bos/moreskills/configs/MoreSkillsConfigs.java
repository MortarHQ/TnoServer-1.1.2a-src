package com.github.wal_bos.moreskills.configs;

import java.util.List;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

public final class MoreSkillsConfigs {
   public static final ModConfigSpec COMMON_SPEC;
   public static final MoreSkillsConfigs.GrandConfig GRAND;

   private MoreSkillsConfigs() {
   }

   public static void init() {
      ModLoadingContext.get().getActiveContainer().registerConfig(Type.COMMON, COMMON_SPEC, "tensuramoreskills-grand.toml");
   }

   static {
      Pair<MoreSkillsConfigs.GrandConfig, ModConfigSpec> pair = new Builder().configure(MoreSkillsConfigs.GrandConfig::new);
      GRAND = (MoreSkillsConfigs.GrandConfig)pair.getLeft();
      COMMON_SPEC = (ModConfigSpec)pair.getRight();
   }

   public static final class AbhothLordOfPestilenceConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final DoubleValue epidemicSequencingSweepCost;
      public final DoubleValue mutationVaultCost;
      public final DoubleValue fungalCatalystCost;
      public final DoubleValue fungalCatalystAscendedCost;
      public final DoubleValue fleshyMiscreationsCost;
      public final IntValue epidemicSequencingSweepCooldownTicks;
      public final IntValue mutationVaultCooldownTicks;
      public final IntValue fungalCatalystCooldownTicks;
      public final IntValue fungalCatalystAscendedCooldownTicks;
      public final IntValue fleshyMiscreationsCooldownTicks;
      public final IntValue passiveVisualIntervalTicks;
      public final IntValue passiveAshParticles;
      public final IntValue passiveMyceliumParticles;
      public final DoubleValue passiveParticleXZ;
      public final DoubleValue passiveParticleY;
      public final DoubleValue passiveMyceliumXZ;
      public final DoubleValue passiveMyceliumY;
      public final DoubleValue passiveParticleSpeed;
      public final IntValue oozeFilmIntervalTicks;
      public final IntValue oozeFilmDurationTicks;
      public final IntValue oozeFilmAshParticles;
      public final DoubleValue oozeFilmParticleXZ;
      public final DoubleValue oozeFilmParticleY;
      public final DoubleValue oozeFilmParticleSpeed;
      public final DoubleValue oozeFilmSoundVolume;
      public final DoubleValue oozeFilmSoundPitch;
      public final DoubleValue epidemicSequencingSweepRadius;
      public final IntValue epidemicSequencingPointsPerEffect;
      public final IntValue epidemicSequencingPoisonParticles;
      public final DoubleValue epidemicSequencingParticleRadius;
      public final IntValue epidemicSequencingAshParticles;
      public final DoubleValue epidemicSequencingAshRadius;
      public final DoubleValue epidemicSequencingAshY;
      public final DoubleValue epidemicSequencingAshSpeed;
      public final DoubleValue fungalCatalystRadius;
      public final DoubleValue fungalCatalystAscendedRadius;
      public final IntValue fungalCatalystEffectDurationTicks;
      public final IntValue fungalCatalystAscendedEffectDurationTicks;
      public final IntValue fungalCatalystBonusAmplifier;
      public final IntValue fungalCatalystAscendedBonusAmplifier;
      public final IntValue fungalCatalystInfectionDurationTicks;
      public final IntValue fungalCatalystAscendedInfectionDurationTicks;
      public final IntValue fungalCatalystAshParticles;
      public final IntValue fungalCatalystAscendedAshParticles;
      public final DoubleValue fungalCatalystAshRadiusMultiplier;
      public final DoubleValue fungalCatalystAshY;
      public final DoubleValue fungalCatalystAshSpeed;
      public final IntValue fungalCatalystMyceliumParticles;
      public final IntValue fungalCatalystAscendedMyceliumParticles;
      public final DoubleValue fungalCatalystMyceliumRadiusMultiplier;
      public final DoubleValue fungalCatalystMyceliumY;
      public final DoubleValue fungalCatalystMyceliumSpeed;
      public final DoubleValue fungalCatalystSoundVolume;
      public final DoubleValue fungalCatalystAscendedSoundVolume;
      public final DoubleValue fungalCatalystSoundPitch;
      public final IntValue ascendedSelfEffectExtensionTicks;
      public final IntValue ascendedSelfEffectMinimumDurationTicks;
      public final IntValue ascendedSelfEffectAmplifierBonus;
      public final IntValue ascendedSelfRegenerationDurationTicks;
      public final IntValue ascendedSelfRegenerationAmplifier;
      public final IntValue ascendedMagiculeRegenerationDurationTicks;
      public final IntValue ascendedMagiculeRegenerationAmplifier;
      public final DoubleValue ascendedMagiculeGain;
      public final DoubleValue fleshyMiscreationsMinimumHealth;
      public final DoubleValue fleshyMiscreationsMinimumSacrifice;
      public final DoubleValue fleshyMiscreationsHealthSacrificeRatio;
      public final IntValue fleshyMiscreationsSpawnCount;
      public final DoubleValue fleshyMiscreationsSpawnSpread;
      public final IntValue fleshyMiscreationsSlimeSize;
      public final IntValue fleshyMiscreationsLifetimeTicks;
      public final IntValue fleshyMiscreationsParticles;
      public final DoubleValue fleshyMiscreationsParticleXZ;
      public final DoubleValue fleshyMiscreationsParticleY;
      public final DoubleValue fleshyMiscreationsParticleSpeed;
      public final DoubleValue fleshyMiscreationsSoundVolume;
      public final DoubleValue fleshyMiscreationsSoundPitch;
      public final IntValue storedEffectMinimumDurationTicks;
      public final DoubleValue storedEffectDurationMultiplierPerUpgrade;
      public final DoubleValue combatRecentTargetMaxDistanceSqr;
      public final DoubleValue combatNearbyRadius;
      public final IntValue filmTickIntervalTicks;
      public final IntValue filmAshParticles;
      public final DoubleValue filmAshXZ;
      public final DoubleValue filmAshY;
      public final DoubleValue filmAshSpeed;
      public final IntValue filmMyceliumParticles;
      public final DoubleValue filmMyceliumXZ;
      public final DoubleValue filmMyceliumY;
      public final DoubleValue filmMyceliumSpeed;
      public final DoubleValue filmInfectionInflateXZ;
      public final DoubleValue filmInfectionInflateY;
      public final IntValue filmInfectionDurationTicks;
      public final IntValue plagueEffectRefreshDurationTicks;
      public final IntValue plagueDamageIntervalTicks;
      public final DoubleValue plagueBaseDamage;
      public final DoubleValue plagueDamagePerStack;
      public final DoubleValue plagueMaxStackBonusDamage;
      public final DoubleValue plagueMinimumHeal;
      public final DoubleValue plagueHealRatio;
      public final DoubleValue plagueMagiculeGainBase;
      public final DoubleValue plagueMagiculeGainPerStack;
      public final IntValue plagueSpreadIntervalTicks;
      public final DoubleValue plagueSpreadRadius;
      public final IntValue plagueSpreadInfectionDurationTicks;
      public final IntValue plagueSpreadParticles;
      public final DoubleValue plagueSpreadParticleXZ;
      public final DoubleValue plagueSpreadParticleY;
      public final DoubleValue plagueSpreadParticleSpeed;
      public final IntValue plagueMaximumAmplifier;
      public final IntValue plagueStackGainPerRefresh;
      public final DoubleValue temporaryResistanceDegradation;
      public final DoubleValue minionExpireHealRatio;
      public final IntValue minionExpireParticles;
      public final DoubleValue minionExpireParticleXZ;
      public final DoubleValue minionExpireParticleY;
      public final DoubleValue minionExpireParticleSpeed;
      public final DoubleValue minionTargetRadius;
      public final DoubleValue minionNavigationSpeed;
      public final DoubleValue minionFallbackAcceleration;
      public final DoubleValue minionHitDistanceSqr;
      public final IntValue minionHitIntervalTicks;
      public final IntValue minionInfectionDurationTicks;
      public final IntValue minionStoredEffectDurationTicks;
      public final IntValue minionStoredEffectBonusAmplifier;
      public final DoubleValue minionDamage;
      public final IntValue minionHitParticles;
      public final DoubleValue minionHitParticleXZ;
      public final DoubleValue minionHitParticleY;
      public final DoubleValue minionHitParticleSpeed;

      AbhothLordOfPestilenceConfig(Builder builder) {
         builder.comment("Obtainment settings for Abhoth, Lord of Pestilence.");
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.comment("Magicule cost required to naturally acquire Abhoth.")
            .defineInRange("acquirementMagiculeCost", 500000.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.comment("Starting mastery value when acquired.").defineInRange("acquirementMastery", 0, -1, Integer.MAX_VALUE);
         this.requiredSkillId = builder.comment("Required skill registry ID for obtainment. Empty string disables the required skill check.")
            .define("requiredSkillId", "tensuramoreskills:fungus_infection");
         this.requiredSkillMustBeMastered = builder.comment("If true, Fungus Infection must be mastered before Abhoth can be acquired.")
            .define("requiredSkillMustBeMastered", true);
         builder.pop();
         builder.comment("Magicule costs for every Abhoth mode.");
         builder.push("costs");
         this.epidemicSequencingSweepCost = builder.defineInRange("epidemicSequencingSweepCost", 12000.0, 0.0, Double.MAX_VALUE);
         this.mutationVaultCost = builder.defineInRange("mutationVaultCost", 0.0, 0.0, Double.MAX_VALUE);
         this.fungalCatalystCost = builder.defineInRange("fungalCatalystCost", 35000.0, 0.0, Double.MAX_VALUE);
         this.fungalCatalystAscendedCost = builder.defineInRange("fungalCatalystAscendedCost", 140000.0, 0.0, Double.MAX_VALUE);
         this.fleshyMiscreationsCost = builder.defineInRange("fleshyMiscreationsCost", 25000.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.comment("Cooldowns. 20 ticks = 1 second.");
         builder.push("cooldowns");
         this.epidemicSequencingSweepCooldownTicks = builder.defineInRange("epidemicSequencingSweepCooldownTicks", 5, 0, Integer.MAX_VALUE);
         this.mutationVaultCooldownTicks = builder.defineInRange("mutationVaultCooldownTicks", 2, 0, Integer.MAX_VALUE);
         this.fungalCatalystCooldownTicks = builder.defineInRange("fungalCatalystCooldownTicks", 20, 0, Integer.MAX_VALUE);
         this.fungalCatalystAscendedCooldownTicks = builder.defineInRange("fungalCatalystAscendedCooldownTicks", 50, 0, Integer.MAX_VALUE);
         this.fleshyMiscreationsCooldownTicks = builder.defineInRange("fleshyMiscreationsCooldownTicks", 40, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Passive visual settings while the skill is known.");
         builder.push("passive_visuals");
         this.passiveVisualIntervalTicks = builder.defineInRange("passiveVisualIntervalTicks", 10, 1, Integer.MAX_VALUE);
         this.passiveAshParticles = builder.defineInRange("passiveAshParticles", 12, 0, 10000);
         this.passiveMyceliumParticles = builder.defineInRange("passiveMyceliumParticles", 8, 0, 10000);
         this.passiveParticleXZ = builder.defineInRange("passiveParticleXZ", 0.45, 0.0, 128.0);
         this.passiveParticleY = builder.defineInRange("passiveParticleY", 0.55, 0.0, 128.0);
         this.passiveMyceliumXZ = builder.defineInRange("passiveMyceliumXZ", 0.55, 0.0, 128.0);
         this.passiveMyceliumY = builder.defineInRange("passiveMyceliumY", 0.15, 0.0, 128.0);
         this.passiveParticleSpeed = builder.defineInRange("passiveParticleSpeed", 0.01, 0.0, 128.0);
         builder.pop();
         builder.comment("Toggled ooze film settings.");
         builder.push("ooze_films");
         this.oozeFilmIntervalTicks = builder.defineInRange("oozeFilmIntervalTicks", 160, 1, Integer.MAX_VALUE);
         this.oozeFilmDurationTicks = builder.defineInRange("oozeFilmDurationTicks", 260, 1, Integer.MAX_VALUE);
         this.oozeFilmAshParticles = builder.defineInRange("oozeFilmAshParticles", 42, 0, 10000);
         this.oozeFilmParticleXZ = builder.defineInRange("oozeFilmParticleXZ", 1.8, 0.0, 128.0);
         this.oozeFilmParticleY = builder.defineInRange("oozeFilmParticleY", 0.06, 0.0, 128.0);
         this.oozeFilmParticleSpeed = builder.defineInRange("oozeFilmParticleSpeed", 0.02, 0.0, 128.0);
         this.oozeFilmSoundVolume = builder.defineInRange("oozeFilmSoundVolume", 0.65, 0.0, 100.0);
         this.oozeFilmSoundPitch = builder.defineInRange("oozeFilmSoundPitch", 0.55, 0.0, 4.0);
         builder.pop();
         builder.comment("Epidemic Sequencing Sweep scan and reward values.");
         builder.push("epidemic_sequencing_sweep");
         this.epidemicSequencingSweepRadius = builder.defineInRange("epidemicSequencingSweepRadius", 15.0, 0.0, 512.0);
         this.epidemicSequencingPointsPerEffect = builder.defineInRange("epidemicSequencingPointsPerEffect", 5, 0, Integer.MAX_VALUE);
         this.epidemicSequencingPoisonParticles = builder.defineInRange("epidemicSequencingPoisonParticles", 64, 0, 10000);
         this.epidemicSequencingParticleRadius = builder.defineInRange("epidemicSequencingParticleRadius", 1.6, 0.0, 128.0);
         this.epidemicSequencingAshParticles = builder.defineInRange("epidemicSequencingAshParticles", 80, 0, 10000);
         this.epidemicSequencingAshRadius = builder.defineInRange("epidemicSequencingAshRadius", 7.5, 0.0, 512.0);
         this.epidemicSequencingAshY = builder.defineInRange("epidemicSequencingAshY", 1.0, 0.0, 128.0);
         this.epidemicSequencingAshSpeed = builder.defineInRange("epidemicSequencingAshSpeed", 0.04, 0.0, 128.0);
         builder.pop();
         builder.comment("Fungal Catalyst and crouching Ascended Catalyst settings.");
         builder.push("fungal_catalyst");
         this.fungalCatalystRadius = builder.defineInRange("fungalCatalystRadius", 15.0, 0.0, 512.0);
         this.fungalCatalystAscendedRadius = builder.defineInRange("fungalCatalystAscendedRadius", 30.0, 0.0, 512.0);
         this.fungalCatalystEffectDurationTicks = builder.defineInRange("fungalCatalystEffectDurationTicks", 160, 1, Integer.MAX_VALUE);
         this.fungalCatalystAscendedEffectDurationTicks = builder.defineInRange("fungalCatalystAscendedEffectDurationTicks", 260, 1, Integer.MAX_VALUE);
         this.fungalCatalystBonusAmplifier = builder.defineInRange("fungalCatalystBonusAmplifier", 0, 0, 255);
         this.fungalCatalystAscendedBonusAmplifier = builder.defineInRange("fungalCatalystAscendedBonusAmplifier", 1, 0, 255);
         this.fungalCatalystInfectionDurationTicks = builder.defineInRange("fungalCatalystInfectionDurationTicks", 420, 1, Integer.MAX_VALUE);
         this.fungalCatalystAscendedInfectionDurationTicks = builder.defineInRange("fungalCatalystAscendedInfectionDurationTicks", 700, 1, Integer.MAX_VALUE);
         this.fungalCatalystAshParticles = builder.defineInRange("fungalCatalystAshParticles", 90, 0, 10000);
         this.fungalCatalystAscendedAshParticles = builder.defineInRange("fungalCatalystAscendedAshParticles", 170, 0, 10000);
         this.fungalCatalystAshRadiusMultiplier = builder.defineInRange("fungalCatalystAshRadiusMultiplier", 0.45, 0.0, 16.0);
         this.fungalCatalystAshY = builder.defineInRange("fungalCatalystAshY", 1.2, 0.0, 128.0);
         this.fungalCatalystAshSpeed = builder.defineInRange("fungalCatalystAshSpeed", 0.08, 0.0, 128.0);
         this.fungalCatalystMyceliumParticles = builder.defineInRange("fungalCatalystMyceliumParticles", 70, 0, 10000);
         this.fungalCatalystAscendedMyceliumParticles = builder.defineInRange("fungalCatalystAscendedMyceliumParticles", 120, 0, 10000);
         this.fungalCatalystMyceliumRadiusMultiplier = builder.defineInRange("fungalCatalystMyceliumRadiusMultiplier", 0.38, 0.0, 16.0);
         this.fungalCatalystMyceliumY = builder.defineInRange("fungalCatalystMyceliumY", 0.35, 0.0, 128.0);
         this.fungalCatalystMyceliumSpeed = builder.defineInRange("fungalCatalystMyceliumSpeed", 0.04, 0.0, 128.0);
         this.fungalCatalystSoundVolume = builder.defineInRange("fungalCatalystSoundVolume", 0.9, 0.0, 100.0);
         this.fungalCatalystAscendedSoundVolume = builder.defineInRange("fungalCatalystAscendedSoundVolume", 1.4, 0.0, 100.0);
         this.fungalCatalystSoundPitch = builder.defineInRange("fungalCatalystSoundPitch", 0.45, 0.0, 4.0);
         builder.pop();
         builder.comment("Ascended Catalyst self-buff values.");
         builder.push("ascended_self_buffs");
         this.ascendedSelfEffectExtensionTicks = builder.defineInRange("ascendedSelfEffectExtensionTicks", 200, 0, Integer.MAX_VALUE);
         this.ascendedSelfEffectMinimumDurationTicks = builder.defineInRange("ascendedSelfEffectMinimumDurationTicks", 400, 1, Integer.MAX_VALUE);
         this.ascendedSelfEffectAmplifierBonus = builder.defineInRange("ascendedSelfEffectAmplifierBonus", 1, 0, 255);
         this.ascendedSelfRegenerationDurationTicks = builder.defineInRange("ascendedSelfRegenerationDurationTicks", 400, 1, Integer.MAX_VALUE);
         this.ascendedSelfRegenerationAmplifier = builder.defineInRange("ascendedSelfRegenerationAmplifier", 1, 0, 255);
         this.ascendedMagiculeRegenerationDurationTicks = builder.defineInRange("ascendedMagiculeRegenerationDurationTicks", 400, 1, Integer.MAX_VALUE);
         this.ascendedMagiculeRegenerationAmplifier = builder.defineInRange("ascendedMagiculeRegenerationAmplifier", 1, 0, 255);
         this.ascendedMagiculeGain = builder.defineInRange("ascendedMagiculeGain", 5000.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.comment("Fleshy Miscreations spawn, sacrifice, lifetime, and visual values.");
         builder.push("fleshy_miscreations");
         this.fleshyMiscreationsMinimumHealth = builder.defineInRange("fleshyMiscreationsMinimumHealth", 2.0, 0.0, Float.MAX_VALUE);
         this.fleshyMiscreationsMinimumSacrifice = builder.defineInRange("fleshyMiscreationsMinimumSacrifice", 1.0, 0.0, Float.MAX_VALUE);
         this.fleshyMiscreationsHealthSacrificeRatio = builder.defineInRange("fleshyMiscreationsHealthSacrificeRatio", 0.1, 0.0, 1.0);
         this.fleshyMiscreationsSpawnCount = builder.defineInRange("fleshyMiscreationsSpawnCount", 2, 0, 64);
         this.fleshyMiscreationsSpawnSpread = builder.defineInRange("fleshyMiscreationsSpawnSpread", 0.7, 0.0, 16.0);
         this.fleshyMiscreationsSlimeSize = builder.defineInRange("fleshyMiscreationsSlimeSize", 2, 1, 16);
         this.fleshyMiscreationsLifetimeTicks = builder.defineInRange("fleshyMiscreationsLifetimeTicks", 300, 1, Integer.MAX_VALUE);
         this.fleshyMiscreationsParticles = builder.defineInRange("fleshyMiscreationsParticles", 60, 0, 10000);
         this.fleshyMiscreationsParticleXZ = builder.defineInRange("fleshyMiscreationsParticleXZ", 0.8, 0.0, 128.0);
         this.fleshyMiscreationsParticleY = builder.defineInRange("fleshyMiscreationsParticleY", 0.6, 0.0, 128.0);
         this.fleshyMiscreationsParticleSpeed = builder.defineInRange("fleshyMiscreationsParticleSpeed", 0.08, 0.0, 128.0);
         this.fleshyMiscreationsSoundVolume = builder.defineInRange("fleshyMiscreationsSoundVolume", 1.0, 0.0, 100.0);
         this.fleshyMiscreationsSoundPitch = builder.defineInRange("fleshyMiscreationsSoundPitch", 0.55, 0.0, 4.0);
         builder.pop();
         builder.comment("Stored mutation effect scaling and combat detection.");
         builder.push("stored_effects_and_combat");
         this.storedEffectMinimumDurationTicks = builder.defineInRange("storedEffectMinimumDurationTicks", 60, 1, Integer.MAX_VALUE);
         this.storedEffectDurationMultiplierPerUpgrade = builder.defineInRange("storedEffectDurationMultiplierPerUpgrade", 0.25, 0.0, 100.0);
         this.combatRecentTargetMaxDistanceSqr = builder.defineInRange("combatRecentTargetMaxDistanceSqr", 1024.0, 0.0, Double.MAX_VALUE);
         this.combatNearbyRadius = builder.defineInRange("combatNearbyRadius", 12.0, 0.0, 512.0);
         builder.pop();
         builder.comment("Ooze film handler values.");
         builder.push("handler_films");
         this.filmTickIntervalTicks = builder.defineInRange("filmTickIntervalTicks", 10, 1, Integer.MAX_VALUE);
         this.filmAshParticles = builder.defineInRange("filmAshParticles", 4, 0, 10000);
         this.filmAshXZ = builder.defineInRange("filmAshXZ", 0.45, 0.0, 128.0);
         this.filmAshY = builder.defineInRange("filmAshY", 0.02, 0.0, 128.0);
         this.filmAshSpeed = builder.defineInRange("filmAshSpeed", 0.003, 0.0, 128.0);
         this.filmMyceliumParticles = builder.defineInRange("filmMyceliumParticles", 2, 0, 10000);
         this.filmMyceliumXZ = builder.defineInRange("filmMyceliumXZ", 0.35, 0.0, 128.0);
         this.filmMyceliumY = builder.defineInRange("filmMyceliumY", 0.02, 0.0, 128.0);
         this.filmMyceliumSpeed = builder.defineInRange("filmMyceliumSpeed", 0.001, 0.0, 128.0);
         this.filmInfectionInflateXZ = builder.defineInRange("filmInfectionInflateXZ", 0.18, 0.0, 32.0);
         this.filmInfectionInflateY = builder.defineInRange("filmInfectionInflateY", 0.45, 0.0, 32.0);
         this.filmInfectionDurationTicks = builder.defineInRange("filmInfectionDurationTicks", 500, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Plague infection damage, healing, spread, stacking, and resistance degradation.");
         builder.push("handler_plague");
         this.plagueEffectRefreshDurationTicks = builder.defineInRange("plagueEffectRefreshDurationTicks", 120, 1, Integer.MAX_VALUE);
         this.plagueDamageIntervalTicks = builder.defineInRange("plagueDamageIntervalTicks", 20, 1, Integer.MAX_VALUE);
         this.plagueBaseDamage = builder.defineInRange("plagueBaseDamage", 1.0, 0.0, Float.MAX_VALUE);
         this.plagueDamagePerStack = builder.defineInRange("plagueDamagePerStack", 0.5, 0.0, Float.MAX_VALUE);
         this.plagueMaxStackBonusDamage = builder.defineInRange("plagueMaxStackBonusDamage", 6.0, 0.0, Float.MAX_VALUE);
         this.plagueMinimumHeal = builder.defineInRange("plagueMinimumHeal", 0.25, 0.0, Float.MAX_VALUE);
         this.plagueHealRatio = builder.defineInRange("plagueHealRatio", 0.006, 0.0, 1.0);
         this.plagueMagiculeGainBase = builder.defineInRange("plagueMagiculeGainBase", 80.0, 0.0, Double.MAX_VALUE);
         this.plagueMagiculeGainPerStack = builder.defineInRange("plagueMagiculeGainPerStack", 15.0, 0.0, Double.MAX_VALUE);
         this.plagueSpreadIntervalTicks = builder.defineInRange("plagueSpreadIntervalTicks", 40, 1, Integer.MAX_VALUE);
         this.plagueSpreadRadius = builder.defineInRange("plagueSpreadRadius", 3.0, 0.0, 512.0);
         this.plagueSpreadInfectionDurationTicks = builder.defineInRange("plagueSpreadInfectionDurationTicks", 420, 1, Integer.MAX_VALUE);
         this.plagueSpreadParticles = builder.defineInRange("plagueSpreadParticles", 24, 0, 10000);
         this.plagueSpreadParticleXZ = builder.defineInRange("plagueSpreadParticleXZ", 0.4, 0.0, 128.0);
         this.plagueSpreadParticleY = builder.defineInRange("plagueSpreadParticleY", 0.5, 0.0, 128.0);
         this.plagueSpreadParticleSpeed = builder.defineInRange("plagueSpreadParticleSpeed", 0.025, 0.0, 128.0);
         this.plagueMaximumAmplifier = builder.defineInRange("plagueMaximumAmplifier", 9, 0, 255);
         this.plagueStackGainPerRefresh = builder.defineInRange("plagueStackGainPerRefresh", 1, 0, 255);
         this.temporaryResistanceDegradation = builder.defineInRange("temporaryResistanceDegradation", 1.0, 0.0, 1000.0);
         builder.pop();
         builder.comment("Fleshy Miscreation AI, hit, expiry, and particles.");
         builder.push("handler_minions");
         this.minionExpireHealRatio = builder.defineInRange("minionExpireHealRatio", 0.15, 0.0, 1.0);
         this.minionExpireParticles = builder.defineInRange("minionExpireParticles", 35, 0, 10000);
         this.minionExpireParticleXZ = builder.defineInRange("minionExpireParticleXZ", 0.35, 0.0, 128.0);
         this.minionExpireParticleY = builder.defineInRange("minionExpireParticleY", 0.35, 0.0, 128.0);
         this.minionExpireParticleSpeed = builder.defineInRange("minionExpireParticleSpeed", 0.04, 0.0, 128.0);
         this.minionTargetRadius = builder.defineInRange("minionTargetRadius", 18.0, 0.0, 512.0);
         this.minionNavigationSpeed = builder.defineInRange("minionNavigationSpeed", 1.65, 0.0, 16.0);
         this.minionFallbackAcceleration = builder.defineInRange("minionFallbackAcceleration", 0.08, 0.0, 16.0);
         this.minionHitDistanceSqr = builder.defineInRange("minionHitDistanceSqr", 2.2, 0.0, 1024.0);
         this.minionHitIntervalTicks = builder.defineInRange("minionHitIntervalTicks", 12, 1, Integer.MAX_VALUE);
         this.minionInfectionDurationTicks = builder.defineInRange("minionInfectionDurationTicks", 420, 1, Integer.MAX_VALUE);
         this.minionStoredEffectDurationTicks = builder.defineInRange("minionStoredEffectDurationTicks", 140, 1, Integer.MAX_VALUE);
         this.minionStoredEffectBonusAmplifier = builder.defineInRange("minionStoredEffectBonusAmplifier", 0, 0, 255);
         this.minionDamage = builder.defineInRange("minionDamage", 2.0, 0.0, Float.MAX_VALUE);
         this.minionHitParticles = builder.defineInRange("minionHitParticles", 18, 0, 10000);
         this.minionHitParticleXZ = builder.defineInRange("minionHitParticleXZ", 0.35, 0.0, 128.0);
         this.minionHitParticleY = builder.defineInRange("minionHitParticleY", 0.45, 0.0, 128.0);
         this.minionHitParticleSpeed = builder.defineInRange("minionHitParticleSpeed", 0.02, 0.0, 128.0);
         builder.pop();
      }
   }

   public static final class AdaptabilityConfig {
      public final DoubleValue obtainCost;
      public final IntValue maxMastery;
      public final DoubleValue damageReductionPerHit;
      public final IntValue maxStacksNormal;
      public final IntValue maxStacksMastered;
      public final DoubleValue minimumDamageMultiplier;
      public final DoubleValue resistanceLearningMultiplier;
      public final DoubleValue resistanceLearningMultiplierMastered;
      public final IntValue severanceHitsForImmunity;
      public final IntValue bladeCooldownTicks;
      public final IntValue globalMasteryIntervalTicks;
      public final DoubleValue globalMasteryBonus;
      public final DoubleValue globalMasteryBonusMastered;
      public final DoubleValue adaptiveMasteryBaseBonus;
      public final DoubleValue adaptiveMasteryMasteredBonus;
      public final DoubleValue adaptiveMasteryMaxBonus;
      public final DoubleValue bladeMaxEp;
      public final DoubleValue bladeHitCost;
      public final DoubleValue bladeHitCostAdapted;
      public final DoubleValue bladeBonusBase;
      public final DoubleValue bladeBonusAttackDamagePercent;
      public final DoubleValue bladeBonusEpDivisor;
      public final DoubleValue bladeBonusEpCap;
      public final DoubleValue bladeBonusCap;
      public final DoubleValue bladeAdaptedBonusMultiplier;
      public final IntValue bladeSeveranceLevel;
      public final IntValue bladeSeveranceLevelMastered;
      public final IntValue bladeTsukumogamiLevel;
      public final IntValue bladeTsukumogamiLevelMastered;
      public final IntValue bladeUnbreakingLevel;
      public final IntValue bladeSharpnessLevel;
      public final IntValue bladeSharpnessLevelMastered;

      public AdaptabilityConfig(Builder builder) {
         builder.push("Adaptability");
         builder.push("Skill");
         this.obtainCost = builder.comment("Magicule cost shown by the skill acquisition system.").defineInRange("obtainCost", 90000.0, 0.0, Double.MAX_VALUE);
         this.maxMastery = builder.comment("Maximum mastery for Adaptability.").defineInRange("maxMastery", 2000, 1, Integer.MAX_VALUE);
         this.bladeCooldownTicks = builder.comment("Cooldown after summoning Extermination Blade. 20 ticks = 1 second.")
            .defineInRange("bladeCooldownTicks", 500, 0, 72000);
         builder.pop();
         builder.push("Damage Adaptation");
         this.damageReductionPerHit = builder.comment("Damage reduction gained per incoming hit stack. 0.007142857 is about 0.714% per stack.")
            .defineInRange("damageReductionPerHit", 0.007142857, 0.0, 1.0);
         this.maxStacksNormal = builder.comment("Maximum adaptive damage reduction stacks before mastery.").defineInRange("maxStacksNormal", 20, 0, 100000);
         this.maxStacksMastered = builder.comment("Maximum adaptive damage reduction stacks after mastery.").defineInRange("maxStacksMastered", 35, 0, 100000);
         this.minimumDamageMultiplier = builder.comment("Lowest incoming damage multiplier after adaptation. 0.75 means damage cannot be reduced below 75%.")
            .defineInRange("minimumDamageMultiplier", 0.75, 0.0, 1.0);
         builder.pop();
         builder.push("Resistance Learning");
         this.resistanceLearningMultiplier = builder.comment("Multiplier applied to resistance-skill learning progress while Adaptability is toggled.")
            .defineInRange("resistanceLearningMultiplier", 3.45, 0.0, 100000.0);
         this.resistanceLearningMultiplierMastered = builder.comment("Multiplier applied to resistance-skill learning progress while Adaptability is mastered.")
            .defineInRange("resistanceLearningMultiplierMastered", 5.85, 0.0, 100000.0);
         builder.pop();
         builder.push("Severance Adaptation");
         this.severanceHitsForImmunity = builder.comment("Number of severance hits needed before Adaptability clears and blocks severance damage.")
            .defineInRange("severanceHitsForImmunity", 12, 1, 100000);
         builder.pop();
         builder.push("Mastery Gain");
         this.globalMasteryIntervalTicks = builder.comment("How often Adaptability gives mastery to learned skills while toggled. 100 ticks = 5 seconds.")
            .defineInRange("globalMasteryIntervalTicks", 100, 1, 72000);
         this.globalMasteryBonus = builder.comment("Mastery points given to each learned skill every global mastery interval.")
            .defineInRange("globalMasteryBonus", 1.0, 0.0, 100000.0);
         this.globalMasteryBonusMastered = builder.comment(
               "Mastery points given to each learned skill every global mastery interval after Adaptability is mastered."
            )
            .defineInRange("globalMasteryBonusMastered", 2.0, 0.0, 100000.0);
         this.adaptiveMasteryBaseBonus = builder.comment("Base mastery bonus Adaptability gives itself when it adapts.")
            .defineInRange("adaptiveMasteryBaseBonus", 1.0, 0.0, 100000.0);
         this.adaptiveMasteryMasteredBonus = builder.comment("Extra self-mastery bonus when Adaptability is mastered.")
            .defineInRange("adaptiveMasteryMasteredBonus", 2.0, 0.0, 100000.0);
         this.adaptiveMasteryMaxBonus = builder.comment("Maximum self-mastery bonus from a single adaptation event.")
            .defineInRange("adaptiveMasteryMaxBonus", 6.0, 0.0, 100000.0);
         builder.pop();
         builder.push("Extermination Blade EP");
         this.bladeMaxEp = builder.comment("Maximum EP stored inside a summoned Extermination Blade.")
            .defineInRange("bladeMaxEp", 50000.0, 1.0, Double.MAX_VALUE);
         this.bladeHitCost = builder.comment("EP consumed per blade hit before severance immunity is obtained.")
            .defineInRange("bladeHitCost", 180.0, 0.0, Double.MAX_VALUE);
         this.bladeHitCostAdapted = builder.comment("EP consumed per blade hit after severance immunity is obtained.")
            .defineInRange("bladeHitCostAdapted", 240.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("Extermination Blade Damage");
         this.bladeBonusBase = builder.comment("Flat bonus damage dealt by Extermination Blade when it has enough EP.")
            .defineInRange("bladeBonusBase", 0.75, 0.0, 1000000.0);
         this.bladeBonusAttackDamagePercent = builder.comment("Bonus damage scaling from attacker attack damage. 0.12 means 12% of attack damage.")
            .defineInRange("bladeBonusAttackDamagePercent", 0.12, 0.0, 1000.0);
         this.bladeBonusEpDivisor = builder.comment("EP divisor used for blade bonus scaling. Higher values make EP contribute less damage.")
            .defineInRange("bladeBonusEpDivisor", 25000.0, 1.0, Double.MAX_VALUE);
         this.bladeBonusEpCap = builder.comment("Maximum bonus damage contribution from stored EP.").defineInRange("bladeBonusEpCap", 2.0, 0.0, 1000000.0);
         this.bladeBonusCap = builder.comment("Maximum total bonus damage dealt by the blade EP strike.").defineInRange("bladeBonusCap", 6.0, 0.0, 1000000.0);
         this.bladeAdaptedBonusMultiplier = builder.comment("Multiplier applied to blade bonus damage after severance immunity is obtained.")
            .defineInRange("bladeAdaptedBonusMultiplier", 1.1, 0.0, 1000.0);
         builder.pop();
         builder.push("Extermination Blade Enchants");
         this.bladeSeveranceLevel = builder.comment("Severance enchantment level on normal Extermination Blade.")
            .defineInRange("bladeSeveranceLevel", 2, 0, 255);
         this.bladeSeveranceLevelMastered = builder.comment("Severance enchantment level on mastered Extermination Blade.")
            .defineInRange("bladeSeveranceLevelMastered", 3, 0, 255);
         this.bladeTsukumogamiLevel = builder.comment("Tsukumogami enchantment level on normal Extermination Blade.")
            .defineInRange("bladeTsukumogamiLevel", 1, 0, 255);
         this.bladeTsukumogamiLevelMastered = builder.comment("Tsukumogami enchantment level on mastered Extermination Blade.")
            .defineInRange("bladeTsukumogamiLevelMastered", 2, 0, 255);
         this.bladeUnbreakingLevel = builder.comment("Unbreaking enchantment level on Extermination Blade.").defineInRange("bladeUnbreakingLevel", 3, 0, 255);
         this.bladeSharpnessLevel = builder.comment("Sharpness enchantment level on normal Extermination Blade.")
            .defineInRange("bladeSharpnessLevel", 2, 0, 255);
         this.bladeSharpnessLevelMastered = builder.comment("Sharpness enchantment level on mastered Extermination Blade.")
            .defineInRange("bladeSharpnessLevelMastered", 3, 0, 255);
         builder.pop();
         builder.pop();
      }
   }

   public static final class AlbisVinaConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final IntValue maxMastery;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final DoubleValue toggleChantSpeed;
      public final DoubleValue toggleChantSpeedMastered;
      public final DoubleValue toggledWaterIceDamageMultiplier;
      public final DoubleValue toggledWaterIceDamageMultiplierMastered;
      public final DoubleValue diamondDustWaterIceDamageMultiplier;
      public final DoubleValue diamondDustWaterIceDamageMultiplierMastered;
      public final IntValue diamondDustDurationTicks;
      public final DoubleValue diamondDustMovementMultiplier;
      public final IntValue arsWeissCloneCount;
      public final IntValue arsWeissCloneCountMastered;
      public final IntValue arsWeissDurationTicks;
      public final IntValue arsWeissDurationTicksMastered;
      public final DoubleValue arsWeissCost;
      public final DoubleValue arsWeissCostMastered;
      public final IntValue arsWeissCooldownTicks;
      public final IntValue arsWeissCooldownTicksMastered;
      public final DoubleValue rivilleCost;
      public final DoubleValue rivilleCostMastered;
      public final IntValue rivilleCastTicks;
      public final IntValue rivilleCastTicksMastered;
      public final IntValue rivilleCooldownTicks;
      public final IntValue rivilleCooldownTicksMastered;
      public final DoubleValue rivilleRange;
      public final DoubleValue rivilleRangeMastered;
      public final DoubleValue rivilleDamage;
      public final DoubleValue rivilleDamageMastered;
      public final IntValue rivillePierceTargets;
      public final DoubleValue cloneBeamDamageMultiplier;
      public final DoubleValue cloneBeamDamageMultiplierMastered;
      public final DoubleValue stellasCost;
      public final DoubleValue stellasCostMastered;
      public final IntValue stellasCastTicks;
      public final IntValue stellasCastTicksMastered;
      public final IntValue stellasCooldownTicks;
      public final IntValue stellasCooldownTicksMastered;
      public final DoubleValue stellasRange;
      public final DoubleValue stellasRangeMastered;
      public final IntValue stellasSpearCount;
      public final IntValue stellasSpearCountMastered;
      public final DoubleValue stellasSpearDamage;
      public final DoubleValue stellasSpearDamageMastered;
      public final DoubleValue cloneSpearDamage;
      public final DoubleValue cloneSpearDamageMastered;
      public final BooleanValue formulaCloneCastingEnabled;
      public final BooleanValue formulaAllowInstantMagic;
      public final IntValue formulaMaxCloneCastsPerPlayerCast;
      public final DoubleValue formulaCloneMagiculeCostMultiplier;
      public final DoubleValue formulaCloneAuraCostMultiplier;
      public final DoubleValue formulaCloneCastPowerMultiplier;
      public final BooleanValue formulaConsumesCloneOnCast;
      public final BooleanValue formulaDestroyCloneOnFailedCast;
      public final IntValue formulaCloneExtraLifeTicks;
      public final IntValue formulaCooldownTicks;
      public final IntValue formulaMinimumHeldTicks;
      public final IntValue formulaInterruptGraceTicks;
      public final BooleanValue formulaAllowStaffCasting;
      public final BooleanValue formulaAllowAllMagic;
      public final BooleanValue formulaAllowSummoningMagic;
      public final BooleanValue formulaAllowNecromancyMagic;
      public final ConfigValue<List<? extends String>> formulaSuccessionBlacklist;
      public final BooleanValue grantPackageOnAcquire;
      public final BooleanValue retryPackageGrantOnToggle;
      public final ConfigValue<List<? extends String>> grantedSkillIds;

      AlbisVinaConfig(Builder builder) {
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.comment("Magicule cost required for natural obtainment of Albis Vina.")
            .defineInRange("acquirementMagiculeCost", 54000.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.comment("Starting mastery value when Albis Vina is acquired.")
            .defineInRange("acquirementMastery", 0, -1, Integer.MAX_VALUE);
         this.maxMastery = builder.comment("Maximum mastery value for Albis Vina.").defineInRange("maxMastery", 1000, 1, Integer.MAX_VALUE);
         this.requiredSkillId = builder.comment("Skill registry ID required before Albis Vina can be naturally acquired.").define("requiredSkillId", "");
         this.requiredSkillMustBeMastered = builder.comment("If true, the required skill must be mastered before Albis Vina can be acquired.")
            .define("requiredSkillMustBeMastered", false);
         builder.pop();
         builder.push("toggle");
         this.toggleChantSpeed = builder.comment("Chant speed attribute bonus while Albis Vina is toggled on.")
            .defineInRange("toggleChantSpeed", 0.2, -1000.0, 1000.0);
         this.toggleChantSpeedMastered = builder.comment("Chant speed attribute bonus while Albis Vina is toggled on and mastered.")
            .defineInRange("toggleChantSpeedMastered", 0.35, -1000.0, 1000.0);
         this.toggledWaterIceDamageMultiplier = builder.comment("Multiplier applied to water and ice damage while Albis Vina is toggled on.")
            .defineInRange("toggledWaterIceDamageMultiplier", 1.12, 0.0, 100.0);
         this.toggledWaterIceDamageMultiplierMastered = builder.comment(
               "Multiplier applied to water and ice damage while Albis Vina is toggled on and mastered."
            )
            .defineInRange("toggledWaterIceDamageMultiplierMastered", 1.2, 0.0, 100.0);
         builder.pop();
         builder.push("diamond_dust");
         this.diamondDustDurationTicks = builder.comment("Duration of the Diamond Dust debuff in ticks.")
            .defineInRange("diamondDustDurationTicks", 120, 1, Integer.MAX_VALUE);
         this.diamondDustWaterIceDamageMultiplier = builder.comment("Extra water and ice damage multiplier against targets affected by Diamond Dust.")
            .defineInRange("diamondDustWaterIceDamageMultiplier", 1.15, 0.0, 100.0);
         this.diamondDustWaterIceDamageMultiplierMastered = builder.comment(
               "Extra water and ice damage multiplier against Diamond Dust targets when Albis Vina is mastered."
            )
            .defineInRange("diamondDustWaterIceDamageMultiplierMastered", 1.25, 0.0, 100.0);
         this.diamondDustMovementMultiplier = builder.comment("Movement speed modifier applied by Diamond Dust.")
            .defineInRange("diamondDustMovementMultiplier", -0.08, -1.0, 100.0);
         builder.pop();
         builder.push("ars_weiss");
         this.arsWeissCloneCount = builder.comment("Number of Ars Weiss clones summoned before mastery.").defineInRange("arsWeissCloneCount", 2, 0, 64);
         this.arsWeissCloneCountMastered = builder.comment("Number of Ars Weiss clones summoned when mastered.")
            .defineInRange("arsWeissCloneCountMastered", 3, 0, 64);
         this.arsWeissDurationTicks = builder.comment("How long Ars Weiss clones last before mastery, in ticks.")
            .defineInRange("arsWeissDurationTicks", 600, 1, Integer.MAX_VALUE);
         this.arsWeissDurationTicksMastered = builder.comment("How long Ars Weiss clones last when mastered, in ticks.")
            .defineInRange("arsWeissDurationTicksMastered", 1000, 1, Integer.MAX_VALUE);
         this.arsWeissCost = builder.comment("Magicule cost to summon Ars Weiss clones before mastery.")
            .defineInRange("arsWeissCost", 500.0, 0.0, Double.MAX_VALUE);
         this.arsWeissCostMastered = builder.comment("Magicule cost to summon Ars Weiss clones when mastered.")
            .defineInRange("arsWeissCostMastered", 700.0, 0.0, Double.MAX_VALUE);
         this.arsWeissCooldownTicks = builder.comment("Cooldown of Ars Weiss before mastery, in ticks.")
            .defineInRange("arsWeissCooldownTicks", 22, 0, Integer.MAX_VALUE);
         this.arsWeissCooldownTicksMastered = builder.comment("Cooldown of Ars Weiss when mastered, in ticks.")
            .defineInRange("arsWeissCooldownTicksMastered", 16, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("riville_calantibium");
         this.rivilleCost = builder.comment("Magicule cost of Riville Calantibium before mastery.").defineInRange("rivilleCost", 300.0, 0.0, Double.MAX_VALUE);
         this.rivilleCostMastered = builder.comment("Magicule cost of Riville Calantibium when mastered.")
            .defineInRange("rivilleCostMastered", 420.0, 0.0, Double.MAX_VALUE);
         this.rivilleCastTicks = builder.comment("Required hold/cast time for Riville Calantibium before mastery, in ticks.")
            .defineInRange("rivilleCastTicks", 12, 1, Integer.MAX_VALUE);
         this.rivilleCastTicksMastered = builder.comment("Required hold/cast time for Riville Calantibium when mastered, in ticks.")
            .defineInRange("rivilleCastTicksMastered", 8, 1, Integer.MAX_VALUE);
         this.rivilleCooldownTicks = builder.comment("Cooldown of Riville Calantibium before mastery, in ticks.")
            .defineInRange("rivilleCooldownTicks", 8, 0, Integer.MAX_VALUE);
         this.rivilleCooldownTicksMastered = builder.comment("Cooldown of Riville Calantibium when mastered, in ticks.")
            .defineInRange("rivilleCooldownTicksMastered", 6, 0, Integer.MAX_VALUE);
         this.rivilleRange = builder.comment("Beam range of Riville Calantibium before mastery, in blocks.").defineInRange("rivilleRange", 18.0, 1.0, 512.0);
         this.rivilleRangeMastered = builder.comment("Beam range of Riville Calantibium when mastered, in blocks.")
            .defineInRange("rivilleRangeMastered", 24.0, 1.0, 512.0);
         this.rivilleDamage = builder.comment("Base beam damage of Riville Calantibium before mastery.")
            .defineInRange("rivilleDamage", 18.0, 0.0, Float.MAX_VALUE);
         this.rivilleDamageMastered = builder.comment("Base beam damage of Riville Calantibium when mastered.")
            .defineInRange("rivilleDamageMastered", 24.0, 0.0, Float.MAX_VALUE);
         this.rivillePierceTargets = builder.comment("Maximum number of targets Riville Calantibium can pierce.")
            .defineInRange("rivillePierceTargets", 3, 1, 128);
         this.cloneBeamDamageMultiplier = builder.comment("Damage multiplier for clone-fired Riville beams before mastery.")
            .defineInRange("cloneBeamDamageMultiplier", 0.35, 0.0, 100.0);
         this.cloneBeamDamageMultiplierMastered = builder.comment("Damage multiplier for clone-fired Riville beams when mastered.")
            .defineInRange("cloneBeamDamageMultiplierMastered", 0.5, 0.0, 100.0);
         builder.pop();
         builder.push("stellas_natea");
         this.stellasCost = builder.comment("Magicule cost of Stellas Natea before mastery.").defineInRange("stellasCost", 450.0, 0.0, Double.MAX_VALUE);
         this.stellasCostMastered = builder.comment("Magicule cost of Stellas Natea when mastered.")
            .defineInRange("stellasCostMastered", 620.0, 0.0, Double.MAX_VALUE);
         this.stellasCastTicks = builder.comment("Required hold/cast time for Stellas Natea before mastery, in ticks.")
            .defineInRange("stellasCastTicks", 18, 1, Integer.MAX_VALUE);
         this.stellasCastTicksMastered = builder.comment("Required hold/cast time for Stellas Natea when mastered, in ticks.")
            .defineInRange("stellasCastTicksMastered", 14, 1, Integer.MAX_VALUE);
         this.stellasCooldownTicks = builder.comment("Cooldown of Stellas Natea before mastery, in ticks.")
            .defineInRange("stellasCooldownTicks", 14, 0, Integer.MAX_VALUE);
         this.stellasCooldownTicksMastered = builder.comment("Cooldown of Stellas Natea when mastered, in ticks.")
            .defineInRange("stellasCooldownTicksMastered", 10, 0, Integer.MAX_VALUE);
         this.stellasRange = builder.comment("Targeting range of Stellas Natea before mastery, in blocks.").defineInRange("stellasRange", 20.0, 1.0, 512.0);
         this.stellasRangeMastered = builder.comment("Targeting range of Stellas Natea when mastered, in blocks.")
            .defineInRange("stellasRangeMastered", 26.0, 1.0, 512.0);
         this.stellasSpearCount = builder.comment("Number of main Stellas Natea spear impacts before mastery.").defineInRange("stellasSpearCount", 5, 1, 128);
         this.stellasSpearCountMastered = builder.comment("Number of main Stellas Natea spear impacts when mastered.")
            .defineInRange("stellasSpearCountMastered", 7, 1, 128);
         this.stellasSpearDamage = builder.comment("Damage of each main Stellas Natea spear before mastery.")
            .defineInRange("stellasSpearDamage", 14.0, 0.0, Float.MAX_VALUE);
         this.stellasSpearDamageMastered = builder.comment("Damage of each main Stellas Natea spear when mastered.")
            .defineInRange("stellasSpearDamageMastered", 18.0, 0.0, Float.MAX_VALUE);
         this.cloneSpearDamage = builder.comment("Damage of each clone-added Stellas Natea spear before mastery.")
            .defineInRange("cloneSpearDamage", 6.0, 0.0, Float.MAX_VALUE);
         this.cloneSpearDamageMastered = builder.comment("Damage of each clone-added Stellas Natea spear when mastered.")
            .defineInRange("cloneSpearDamageMastered", 9.0, 0.0, Float.MAX_VALUE);
         builder.pop();
         builder.push("formula_succession");
         this.formulaCloneCastingEnabled = builder.comment("If true, Ars Weiss clones mirror the user's magic casts while Albis Vina is toggled.")
            .define("formulaCloneCastingEnabled", true);
         this.formulaAllowInstantMagic = builder.comment("If true, clones may mirror instant-cast magic. Usually false is safer.")
            .define("formulaAllowInstantMagic", false);
         this.formulaMaxCloneCastsPerPlayerCast = builder.comment("Maximum number of Ars Weiss clones that may mirror one player magic cast.")
            .defineInRange("formulaMaxCloneCastsPerPlayerCast", 3, 0, 64);
         this.formulaCloneMagiculeCostMultiplier = builder.comment("Magicule cost multiplier charged to the owner for each clone-cast magic.")
            .defineInRange("formulaCloneMagiculeCostMultiplier", 0.35, 0.0, 100.0);
         this.formulaCloneAuraCostMultiplier = builder.comment("Aura cost multiplier charged to the owner for each clone-cast magic.")
            .defineInRange("formulaCloneAuraCostMultiplier", 0.35, 0.0, 100.0);
         this.formulaCloneCastPowerMultiplier = builder.comment("Power multiplier for clone-cast magic when a custom damage hook supports it.")
            .defineInRange("formulaCloneCastPowerMultiplier", 0.5, 0.0, 100.0);
         this.formulaConsumesCloneOnCast = builder.comment("If true, a clone shatters after finishing a mirrored magic cast.")
            .define("formulaConsumesCloneOnCast", false);
         this.formulaDestroyCloneOnFailedCast = builder.comment("If true, a clone shatters when its mirrored magic cast cannot be completed.")
            .define("formulaDestroyCloneOnFailedCast", false);
         this.formulaCloneExtraLifeTicks = builder.comment("Extra lifetime given to a clone when it starts mirroring a magic cast.")
            .defineInRange("formulaCloneExtraLifeTicks", 100, 0, Integer.MAX_VALUE);
         this.formulaCooldownTicks = builder.comment("Legacy cooldown value for old interrupted-cast Formula Succession logic.")
            .defineInRange("formulaCooldownTicks", 12, 0, Integer.MAX_VALUE);
         this.formulaMinimumHeldTicks = builder.comment("Legacy minimum held ticks for old interrupted-cast Formula Succession logic.")
            .defineInRange("formulaMinimumHeldTicks", 5, 0, Integer.MAX_VALUE);
         this.formulaInterruptGraceTicks = builder.comment("Legacy grace ticks for old interrupted-cast Formula Succession logic.")
            .defineInRange("formulaInterruptGraceTicks", 10, 1, 20);
         this.formulaAllowStaffCasting = builder.comment("If true, staff casting can be mirrored by Ars Weiss clones.")
            .define("formulaAllowStaffCasting", false);
         this.formulaAllowAllMagic = builder.comment("If true, Formula Succession can mirror any skill tagged as Tensura magic.")
            .define("formulaAllowAllMagic", true);
         this.formulaAllowSummoningMagic = builder.comment("If true, summoning magic is allowed when Formula Succession is not restricted to all magic.")
            .define("formulaAllowSummoningMagic", true);
         this.formulaAllowNecromancyMagic = builder.comment("If true, necromancy magic is allowed when Formula Succession is not restricted to all magic.")
            .define("formulaAllowNecromancyMagic", true);
         this.formulaSuccessionBlacklist = builder.comment("Spell registry IDs that Formula Succession is not allowed to mirror.")
            .defineListAllowEmpty(
               "formulaSuccessionBlacklist",
               List.of("tensura:megiddo", "tensura:spatial_storage", "tensura:warp_portal", "tensura:escape", "tensura:teleport", "tensura:gate"),
               o -> o instanceof String
            );
         builder.pop();
         builder.push("granted_package");
         this.grantPackageOnAcquire = builder.comment("If true, Albis Vina grants the configured water and ice package when acquired, equipped, or toggled.")
            .define("grantPackageOnAcquire", true);
         this.retryPackageGrantOnToggle = builder.comment("If true, the grant package is retried when the skill is toggled.")
            .define("retryPackageGrantOnToggle", true);
         this.grantedSkillIds = builder.comment("Skill registry IDs automatically granted with Albis Vina.")
            .defineListAllowEmpty(
               "grantedSkillIds",
               List.of(
                  "tensura:freeze",
                  "tensura:icicle_lance",
                  "tensura:icicle_spear",
                  "tensura:icicle_rain",
                  "tensura:ice_wall",
                  "tensura:ice_blizzard",
                  "tensura:ice_breaker",
                  "tensura:water_aspectual",
                  "tensura:drainage",
                  "tensura:water_cutter_aspectual",
                  "tensura:water_jail",
                  "tensura:sleep_mist",
                  "tensura:acid_shell",
                  "tensura:water",
                  "tensura:water_cutter",
                  "tensura:acid_rain",
                  "tensura:megiddo",
                  "tensura:blizzard",
                  "tensura:cold_resistance",
                  "tensura:water_attack_resistance"
               ),
               o -> o instanceof String
            );
         builder.pop();
      }
   }

   public static final class ArthurConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMastered;
      public final IntValue maxMastery;
      public final DoubleValue holyOrderAuraCostPerPulse;
      public final DoubleValue holyOrderRadius;
      public final DoubleValue holyOrderSoulDamageScale;
      public final DoubleValue lightDominationCost;
      public final DoubleValue lightDominationDamage;
      public final DoubleValue lightDominationRange;
      public final IntValue lightDominationCooldown;
      public final DoubleValue holyDisintegrationCost;
      public final DoubleValue holyDisintegrationDamage;
      public final DoubleValue holyDisintegrationRadius;
      public final IntValue holyDisintegrationChargeTicks;
      public final IntValue holyDisintegrationCooldown;
      public final DoubleValue holyAuraCostPerPulse;
      public final DoubleValue holyAuraDamage;
      public final DoubleValue holyAuraRadius;
      public final DoubleValue kingsBattleCostPerPulse;
      public final DoubleValue kingsBattleRadius;
      public final DoubleValue promisedVictoryCost;
      public final DoubleValue promisedVictoryBaseDamage;
      public final DoubleValue promisedVictorySoulScale;
      public final DoubleValue promisedVictoryRange;
      public final IntValue promisedVictoryCooldown;

      ArthurConfig(Builder builder) {
         this.acquirementMagiculeCost = builder.defineInRange("acquirement_magicule_cost", 1.0E9, 0.0, Double.MAX_VALUE);
         this.requiredSkillId = builder.define("required_skill_id", "tensuramoreskills:parasite");
         this.requiredSkillMastered = builder.define("required_skill_mastered", true);
         this.maxMastery = builder.defineInRange("max_mastery", 1000, 1, Integer.MAX_VALUE);
         this.holyOrderAuraCostPerPulse = builder.defineInRange("holy_order_aura_cost_per_pulse", 450.0, 0.0, Double.MAX_VALUE);
         this.holyOrderRadius = builder.defineInRange("holy_order_radius", 28.0, 1.0, 256.0);
         this.holyOrderSoulDamageScale = builder.defineInRange("holy_order_soul_damage_scale", 0.0125, 0.0, 100.0);
         this.lightDominationCost = builder.defineInRange("light_domination_cost", 6000.0, 0.0, Double.MAX_VALUE);
         this.lightDominationDamage = builder.defineInRange("light_domination_damage", 850.0, 0.0, Float.MAX_VALUE);
         this.lightDominationRange = builder.defineInRange("light_domination_range", 96.0, 1.0, 512.0);
         this.lightDominationCooldown = builder.defineInRange("light_domination_cooldown_ticks", 140, 0, Integer.MAX_VALUE);
         this.holyDisintegrationCost = builder.defineInRange("holy_disintegration_cost", 18000.0, 0.0, Double.MAX_VALUE);
         this.holyDisintegrationDamage = builder.defineInRange("holy_disintegration_damage", 2400.0, 0.0, Float.MAX_VALUE);
         this.holyDisintegrationRadius = builder.defineInRange("holy_disintegration_radius", 18.0, 1.0, 128.0);
         this.holyDisintegrationChargeTicks = builder.defineInRange("holy_disintegration_charge_ticks", 100, 20, 1200);
         this.holyDisintegrationCooldown = builder.defineInRange("holy_disintegration_cooldown_ticks", 600, 0, Integer.MAX_VALUE);
         this.holyAuraCostPerPulse = builder.defineInRange("holy_aura_cost_per_pulse", 320.0, 0.0, Double.MAX_VALUE);
         this.holyAuraDamage = builder.defineInRange("holy_aura_damage", 180.0, 0.0, Float.MAX_VALUE);
         this.holyAuraRadius = builder.defineInRange("holy_aura_radius", 14.0, 1.0, 128.0);
         this.kingsBattleCostPerPulse = builder.defineInRange("kings_battle_cost_per_pulse", 700.0, 0.0, Double.MAX_VALUE);
         this.kingsBattleRadius = builder.defineInRange("kings_battle_radius", 34.0, 1.0, 256.0);
         this.promisedVictoryCost = builder.defineInRange("promised_victory_cost", 35000.0, 0.0, Double.MAX_VALUE);
         this.promisedVictoryBaseDamage = builder.defineInRange("promised_victory_base_damage", 1000.0, 0.0, Float.MAX_VALUE);
         this.promisedVictorySoulScale = builder.defineInRange("promised_victory_soul_scale", 1.0, 0.0, 100.0);
         this.promisedVictoryRange = builder.defineInRange("promised_victory_range", 64.0, 1.0, 512.0);
         this.promisedVictoryCooldown = builder.defineInRange("promised_victory_cooldown_ticks", 1200, 0, Integer.MAX_VALUE);
      }
   }

   public static final class AsmodayConfig {
      public final DoubleValue spaceEmbodimentCost;
      public final DoubleValue spatialCubeBarrageCost;
      public final DoubleValue cubeJudgementCost;
      public final DoubleValue prisonRealmCost;
      public final DoubleValue endBlackHoleCost;
      public final DoubleValue avatarBeamCost;
      public final IntValue avatarBeamArmReleaseTicks;
      public final IntValue avatarBeamMaxHoldTicks;
      public final IntValue avatarBeamMinDurationTicks;
      public final IntValue avatarBeamMaxDurationTicks;
      public final IntValue avatarBeamCooldownTicks;
      public final DoubleValue avatarBeamTargetRange;
      public final DoubleValue avatarBeamRange;
      public final DoubleValue avatarBeamSendRange;
      public final DoubleValue avatarBeamRadius;
      public final DoubleValue avatarBeamFinalRadius;
      public final DoubleValue avatarBeamCoreRadius;
      public final DoubleValue avatarBeamTickDamage;
      public final DoubleValue avatarBeamFinalDamage;
      public final DoubleValue avatarBeamCoreDamage;
      public final DoubleValue avatarBeamSpiritualDamage;
      public final DoubleValue avatarBeamForcedMinimumHealthDamage;
      public final DoubleValue avatarBeamDamageMultiplierBase;
      public final DoubleValue avatarBeamDamageMultiplierBonus;
      public final DoubleValue avatarBeamRadiusMultiplierBase;
      public final DoubleValue avatarBeamRadiusMultiplierBonus;
      public final IntValue avatarBeamPayloadRefreshTicks;
      public final IntValue avatarBeamDamageIntervalTicks;
      public final IntValue avatarBeamPulseIntervalTicks;
      public final IntValue avatarBeamFinisherPulseIntervalTicks;
      public final IntValue avatarBeamMasteryIntervalTicks;
      public final IntValue avatarBeamChargeMessageIntervalTicks;
      public final IntValue avatarBeamChargeSoundIntervalTicks;
      public final DoubleValue cubeJudgementNormalCost;
      public final DoubleValue cubeJudgementShiftCost;
      public final DoubleValue passiveDistanceTaxStart;
      public final DoubleValue passiveDistanceTaxStartMastered;
      public final DoubleValue passiveDistanceTaxFull;
      public final DoubleValue passiveDistanceTaxFullMastered;
      public final DoubleValue passiveMaxDistanceReduction;
      public final DoubleValue passiveMaxDistanceReductionMastered;
      public final DoubleValue passiveSpaceLockRange;
      public final DoubleValue passiveSpaceLockRangeMastered;
      public final DoubleValue passiveSpaceLockPush;
      public final DoubleValue passiveSpaceLockPushMastered;
      public final IntValue passiveSpaceLockCooldownTicks;
      public final IntValue passiveSpaceLockCooldownTicksMastered;
      public final DoubleValue passiveAnchorHorizontalCap;
      public final DoubleValue passiveAnchorHorizontalCapMastered;
      public final DoubleValue passiveAnchorVerticalCap;
      public final DoubleValue passiveAnchorVerticalCapMastered;
      public final DoubleValue passiveForcedMovementScale;
      public final DoubleValue passiveForcedMovementScaleMastered;
      public final DoubleValue passiveForcedMovementMinimumStrength;
      public final IntValue passiveAnchorPulseCooldownTicks;
      public final IntValue passiveAnchorPulseCooldownTicksMastered;
      public final DoubleValue prisonRealmCostMastered;
      public final IntValue prisonRealmCooldownTicks;
      public final IntValue prisonRealmCooldownTicksMastered;
      public final IntValue prisonRealmHoldSeconds;
      public final DoubleValue prisonRealmRange;
      public final DoubleValue prisonRealmRangeMastered;
      public final DoubleValue prisonRealmHoldSlowMultiplier;
      public final IntValue prisonPayIntervalTicks;
      public final DoubleValue prisonMagiculeDrain;
      public final DoubleValue prisonAuraDrain;
      public final DoubleValue prisonMasteredDrainMultiplier;
      public final IntValue prisonMaxStability;
      public final IntValue prisonDestabilizeOnFail;
      public final IntValue prisonStabilityRecoverPayIntervals;
      public final IntValue prisonBoxRadius;
      public final IntValue prisonBoxFloorYOffset;
      public final IntValue prisonBoxCeilingYOffset;
      public final DoubleValue prisonWatchRange;
      public final DoubleValue prisonMaxDistanceFromCenter;
      public final DoubleValue prisonSpawnY;
      public final IntValue prisonCoordinateSpreadBits;
      public final IntValue prisonCoordinateSpacing;
      public final IntValue prisonProtectionInvulnerabilityTicks;
      public final DoubleValue prisonProtectionAbsorption;
      public final DoubleValue prisonProtectionMotionScale;
      public final IntValue prisonEffectDurationTicks;
      public final IntValue prisonEffectRefreshTicks;
      public final IntValue prisonSpatialBlockadeAmplifier;
      public final IntValue prisonMagicInterferenceAmplifier;
      public final IntValue prisonMovementInterferenceAmplifier;
      public final IntValue prisonEnergyBlockadeAmplifier;
      public final IntValue prisonViolentReleaseInvulnerabilityTicks;
      public final IntValue prisonSafeReleaseInvulnerabilityTicks;
      public final IntValue prisonViolentReleaseMovementTicks;
      public final IntValue prisonViolentReleaseMovementAmplifier;
      public final DoubleValue prisonChargeBaseSize;
      public final DoubleValue prisonChargeBonusSize;
      public final IntValue prisonChargeFxDurationTicks;
      public final IntValue prisonAmbientFxIntervalTicks;
      public final IntValue prisonAmbientFxDurationTicks;
      public final DoubleValue prisonAmbientFxSize;
      public final IntValue cubeJudgementNormalCooldownTicks;
      public final IntValue cubeJudgementShiftCooldownTicks;
      public final IntValue cubeJudgementNormalMasteredCooldownReductionTicks;
      public final IntValue cubeJudgementShiftMasteredCooldownReductionTicks;
      public final DoubleValue cubeJudgementTargetRange;
      public final DoubleValue cubeJudgementTargetRangeMastered;
      public final IntValue cubeJudgementSingleFallDurationTicks;
      public final IntValue cubeJudgementRainFallDurationTicks;
      public final IntValue cubeJudgementGroundDespawnTicks;
      public final IntValue cubeJudgementNoGroundDespawnTicks;
      public final DoubleValue cubeJudgementWatchRange;
      public final IntValue cubeJudgementRainCubes;
      public final IntValue cubeJudgementRainCubesMastered;
      public final IntValue cubeJudgementRainCubeDelayTicks;
      public final IntValue cubeJudgementRainCubeDelayRandomTicks;
      public final IntValue cubeJudgementRainExtraLifetimeTicks;
      public final IntValue cubeJudgementSingleRecentHitTicks;
      public final IntValue cubeJudgementRainRecentHitTicks;
      public final IntValue cubeJudgementRainMaxHitsPerTick;
      public final IntValue cubeJudgementRainMaxHitsPerTickMastered;
      public final IntValue cubeJudgementSingleMaxImpactTargets;
      public final IntValue cubeJudgementRainMaxImpactTargets;
      public final DoubleValue cubeJudgementSingleSize;
      public final DoubleValue cubeJudgementSingleSizeMastered;
      public final DoubleValue cubeJudgementRainSize;
      public final DoubleValue cubeJudgementRainSizeMastered;
      public final DoubleValue cubeJudgementRainBigSizeBonus;
      public final DoubleValue cubeJudgementRainBigSizeBonusMastered;
      public final DoubleValue cubeJudgementRainMediumSizeBonus;
      public final DoubleValue cubeJudgementRainMediumSizeBonusMastered;
      public final DoubleValue cubeJudgementRainSmallSizeBonus;
      public final DoubleValue cubeJudgementRainRadius;
      public final DoubleValue cubeJudgementRainRadiusMastered;
      public final DoubleValue cubeJudgementSingleStartHeight;
      public final DoubleValue cubeJudgementSingleStartHeightMastered;
      public final DoubleValue cubeJudgementRainStartHeight;
      public final DoubleValue cubeJudgementRainStartHeightMastered;
      public final DoubleValue cubeJudgementSingleFallbackEndYOffset;
      public final DoubleValue cubeJudgementSingleFallbackEndYOffsetMastered;
      public final DoubleValue cubeJudgementRainFallbackEndYOffset;
      public final DoubleValue cubeJudgementRainFallbackEndYOffsetMastered;
      public final DoubleValue cubeJudgementSingleSway;
      public final DoubleValue cubeJudgementRainSway;
      public final DoubleValue cubeJudgementSingleDamage;
      public final DoubleValue cubeJudgementSingleDamageMastered;
      public final DoubleValue cubeJudgementRainDamage;
      public final DoubleValue cubeJudgementRainDamageMastered;
      public final DoubleValue cubeJudgementSingleSizeDamageMultiplier;
      public final DoubleValue cubeJudgementRainSizeDamageMultiplier;
      public final DoubleValue cubeJudgementSingleMobMaxHealthDamage;
      public final DoubleValue cubeJudgementSinglePlayerMaxHealthDamage;
      public final DoubleValue cubeJudgementRainMobMaxHealthDamage;
      public final DoubleValue cubeJudgementRainPlayerMaxHealthDamage;
      public final DoubleValue cubeJudgementSinglePlayerDamageMultiplier;
      public final DoubleValue cubeJudgementRainPlayerDamageMultiplier;
      public final DoubleValue cubeJudgementForcedDamageMultiplier;
      public final DoubleValue spatialCubeBarrageCostMastered;
      public final IntValue spatialCubeBarrageCooldownTicks;
      public final IntValue spatialCubeBarrageShiftCooldownBonusTicks;
      public final DoubleValue spatialCubeBarrageRange;
      public final DoubleValue spatialCubeBarrageRangeMastered;
      public final IntValue spatialCubeClientDurationTicks;
      public final IntValue spatialCubeServerLifeTicks;
      public final DoubleValue spatialCubeWatchRange;
      public final DoubleValue spatialCubeHitboxPadding;
      public final IntValue spatialCubeStreams;
      public final IntValue spatialCubeStreamsShifted;
      public final IntValue spatialCubeCubesPerStream;
      public final IntValue spatialCubeCubesPerStreamMastered;
      public final IntValue spatialCubeCubesPerStreamShifted;
      public final IntValue spatialCubeCubesPerStreamShiftedMastered;
      public final IntValue spatialCubeVisualCubes;
      public final IntValue spatialCubeVisualCubesMastered;
      public final IntValue spatialCubeVisualCubesShifted;
      public final IntValue spatialCubeVisualCubesShiftedMastered;
      public final IntValue spatialCubeNormalStepDelayTicks;
      public final IntValue spatialCubeShiftedStepDelayTicks;
      public final DoubleValue spatialCubeNormalTravelTicks;
      public final DoubleValue spatialCubeShiftedTravelTicks;
      public final IntValue spatialCubeNormalHitCooldownTicks;
      public final IntValue spatialCubeShiftedHitCooldownTicks;
      public final IntValue spatialCubeTrailParticleIntervalTicks;
      public final DoubleValue spatialCubeNormalBaseHitRadius;
      public final DoubleValue spatialCubeShiftedBaseHitRadius;
      public final DoubleValue spatialCubeMasteredHitRadiusBonus;
      public final DoubleValue spatialCubeVariance0;
      public final DoubleValue spatialCubeVariance1;
      public final DoubleValue spatialCubeVariance2;
      public final DoubleValue spatialCubeVariance4;
      public final DoubleValue spatialCubeDamage;
      public final DoubleValue spatialCubeDamageMastered;
      public final DoubleValue spatialCubeShiftDamageBonus;
      public final DoubleValue spatialCubeNormalStreamDamage;
      public final DoubleValue spatialCubeShiftedStreamDamage;
      public final DoubleValue spatialCubeCubeDamage;
      public final DoubleValue spatialCubeCubeDamageMastered;
      public final DoubleValue spatialCubeNormalMobMaxHealthDamage;
      public final DoubleValue spatialCubeShiftedMobMaxHealthDamage;
      public final DoubleValue spatialCubeNormalPlayerMaxHealthDamage;
      public final DoubleValue spatialCubeShiftedPlayerMaxHealthDamage;
      public final DoubleValue spatialCubeMasteredDamageMultiplier;
      public final DoubleValue spatialCubeShiftedDamageMultiplier;
      public final DoubleValue spatialCubePlayerDamageMultiplier;
      public final DoubleValue spatialCubeFallbackDamageMultiplier;
      public final DoubleValue spatialCubeForcedPlayerDamageMultiplier;
      public final DoubleValue spatialCubeForcedMobDamageMultiplier;
      public final IntValue spatialCubeSpatialBlockadeTicks;
      public final IntValue spatialCubeSpatialBlockadeTicksMastered;
      public final IntValue spatialCubeSpatialBlockadeAmplifier;
      public final IntValue spatialCubeSpatialBlockadeAmplifierShifted;
      public final IntValue spatialCubeMagicInterferenceTicks;
      public final IntValue spatialCubeMagicInterferenceTicksMastered;
      public final IntValue spatialCubeMagicInterferenceAmplifier;
      public final IntValue spatialCubeMagicInterferenceAmplifierShifted;
      public final IntValue spatialCubeMovementInterferenceTicks;
      public final IntValue spatialCubeMovementInterferenceTicksMastered;
      public final IntValue spatialCubeMovementInterferenceAmplifier;
      public final IntValue spatialCubeMovementInterferenceAmplifierShifted;
      public final DoubleValue spatialCubeNormalSideSpread;
      public final DoubleValue spatialCubeShiftedSideSpread;
      public final DoubleValue spatialCubeNormalBackOffset;
      public final DoubleValue spatialCubeShiftedBackOffset;
      public final DoubleValue spatialCubeStepBackOffset;
      public final DoubleValue spatialCubeNormalCurveLift;
      public final DoubleValue spatialCubeShiftedCurveLift;
      public final DoubleValue spatialCubeNormalCurveSideMultiplier;
      public final DoubleValue spatialCubeShiftedCurveSideMultiplier;
      public final DoubleValue spatialCubeNormalTargetSideMultiplier;
      public final DoubleValue spatialCubeShiftedTargetSideMultiplier;
      public final DoubleValue spatialCubeNormalTargetHeightMultiplier;
      public final DoubleValue spatialCubeShiftedTargetHeightMultiplier;
      public final DoubleValue spatialCubeNormalJitter;
      public final DoubleValue spatialCubeShiftedJitter;
      public final DoubleValue spatialCubeNormalUpJitter;
      public final DoubleValue spatialCubeShiftedUpJitter;
      public final DoubleValue spatialCubeNormalForwardJitter;
      public final DoubleValue spatialCubeShiftedForwardJitter;
      public final DoubleValue spatialCubeNormalSmoothMultiplier;
      public final DoubleValue spatialCubeShiftedSmoothMultiplier;
      public final DoubleValue spatialCubeHeight0;
      public final DoubleValue spatialCubeHeight1;
      public final DoubleValue spatialCubeHeight2;
      public final DoubleValue spatialCubeHeight3;
      public final DoubleValue spatialCubeHeight0Shifted;
      public final DoubleValue spatialCubeHeight1Shifted;
      public final DoubleValue spatialCubeHeight2Shifted;
      public final DoubleValue spatialCubeHeight3Shifted;
      public final IntValue cubeJudgementSingleSpatialBlockadeTicks;
      public final IntValue cubeJudgementRainSpatialBlockadeTicks;
      public final IntValue cubeJudgementSingleSpatialBlockadeAmplifier;
      public final IntValue cubeJudgementRainSpatialBlockadeAmplifier;
      public final IntValue cubeJudgementSingleMagicInterferenceTicks;
      public final IntValue cubeJudgementRainMagicInterferenceTicks;
      public final IntValue cubeJudgementSingleMagicInterferenceAmplifier;
      public final IntValue cubeJudgementRainMagicInterferenceAmplifier;
      public final IntValue cubeJudgementSingleMovementInterferenceTicks;
      public final IntValue cubeJudgementRainMovementInterferenceTicks;
      public final IntValue cubeJudgementSingleMovementInterferenceAmplifier;
      public final IntValue cubeJudgementRainMovementInterferenceAmplifier;
      public final DoubleValue endBlackHoleRadius;
      public final IntValue endBlackHoleDurationTicks;
      public final DoubleValue endBlackHoleTargetRange;
      public final DoubleValue endBlackHoleFallbackRange;
      public final DoubleValue endBlackHoleFallbackMissRange;
      public final DoubleValue endBlackHoleHitYOffset;
      public final DoubleValue endBlackHoleLookTargetInflate;
      public final IntValue endBlackHoleCooldownTicks;
      public final DoubleValue avatarBeamResistanceDegradation;
      public final DoubleValue avatarBeamSpaceResistanceDegradation;

      AsmodayConfig(Builder builder) {
         builder.comment("Magicule costs for Asmoday modes. These are only the activation/hold costs used by each mode, not obtainment costs.");
         builder.push("costs");
         this.spaceEmbodimentCost = builder.comment("Magicule cost for Space Embodiment. This mode is Asmoday's defensive/spatial state.")
            .defineInRange("spaceEmbodimentCost", 50000.0, 0.0, Double.MAX_VALUE);
         this.spatialCubeBarrageCost = builder.comment("Magicule cost for Spatial Cube Barrage. This mode fires cube projectiles.")
            .defineInRange("spatialCubeBarrageCost", 65000.0, 0.0, Double.MAX_VALUE);
         this.cubeJudgementCost = builder.comment("Magicule cost for Cube Judgement. This mode calls down the falling spatial judgement cube.")
            .defineInRange("cubeJudgementCost", 90000.0, 0.0, Double.MAX_VALUE);
         this.prisonRealmCost = builder.comment("Magicule cost for Prison Realm. This mode traps the target inside Asmoday's prison logic.")
            .defineInRange("prisonRealmCost", 120000.0, 0.0, Double.MAX_VALUE);
         this.endBlackHoleCost = builder.comment("Magicule cost for End Black Hole. This mode creates the black hole absorption attack.")
            .defineInRange("endBlackHoleCost", 150000.0, 0.0, Double.MAX_VALUE);
         this.avatarBeamCost = builder.comment("Magicule cost for Avatar Beam. This is checked when the beam starts charging/releasing.")
            .defineInRange("avatarBeamCost", 120000.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.comment("Avatar Beam charge, duration, and cooldown values. 20 ticks = 1 second.");
         builder.push("avatar_beam_timing");
         this.avatarBeamArmReleaseTicks = builder.comment("Minimum hold time before releasing Avatar Beam is allowed. Default 10 = 0.5 seconds.")
            .defineInRange("avatarBeamArmReleaseTicks", 10, 1, Integer.MAX_VALUE);
         this.avatarBeamMaxHoldTicks = builder.comment("Hold time needed for a full-power Avatar Beam. Default 100 = 5 seconds.")
            .defineInRange("avatarBeamMaxHoldTicks", 100, 1, Integer.MAX_VALUE);
         this.avatarBeamMinDurationTicks = builder.comment("Beam duration at minimum charge. Default 100 = 5 seconds.")
            .defineInRange("avatarBeamMinDurationTicks", 100, 1, Integer.MAX_VALUE);
         this.avatarBeamMaxDurationTicks = builder.comment("Beam duration at full charge. Default 220 = 11 seconds.")
            .defineInRange("avatarBeamMaxDurationTicks", 220, 1, Integer.MAX_VALUE);
         this.avatarBeamCooldownTicks = builder.comment("Cooldown after Avatar Beam releases. Default 90 = 4.5 seconds.")
            .defineInRange("avatarBeamCooldownTicks", 90, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Avatar Beam range and hitbox values.");
         builder.push("avatar_beam_ranges");
         this.avatarBeamTargetRange = builder.comment("Range used to find the looked-at target for beam visuals.")
            .defineInRange("avatarBeamTargetRange", 220.0, 1.0, 4096.0);
         this.avatarBeamRange = builder.comment("Actual beam length from the caster's aim direction.").defineInRange("avatarBeamRange", 260.0, 1.0, 4096.0);
         this.avatarBeamSendRange = builder.comment(
               "Network visibility range around the beam line. Higher values show the beam to farther players but send to more clients."
            )
            .defineInRange("avatarBeamSendRange", 420.0, 1.0, 4096.0);
         this.avatarBeamRadius = builder.comment("Main beam damage radius before charge scaling. Bigger values make the beam easier to hit with.")
            .defineInRange("avatarBeamRadius", 20.0, 0.0, 512.0);
         this.avatarBeamFinalRadius = builder.comment("Finisher pulse radius before charge scaling. This is used on release and final pulses.")
            .defineInRange("avatarBeamFinalRadius", 34.0, 0.0, 512.0);
         this.avatarBeamCoreRadius = builder.comment("Inner beam core radius. Targets near the center take extra core damage.")
            .defineInRange("avatarBeamCoreRadius", 9.0, 0.0, 512.0);
         builder.pop();
         builder.comment("Avatar Beam damage values.");
         builder.push("avatar_beam_damage");
         this.avatarBeamTickDamage = builder.comment("Base damage for repeated beam ticks before charge multiplier, distance bonus, and core bonus.")
            .defineInRange("avatarBeamTickDamage", 85000.0, 0.0, Float.MAX_VALUE);
         this.avatarBeamFinalDamage = builder.comment("Base damage for release/final pulses before charge multiplier, distance bonus, and core bonus.")
            .defineInRange("avatarBeamFinalDamage", 180000.0, 0.0, Float.MAX_VALUE);
         this.avatarBeamCoreDamage = builder.comment("Extra damage added near the center of the beam. This rewards direct hits.")
            .defineInRange("avatarBeamCoreDamage", 260000.0, 0.0, Float.MAX_VALUE);
         this.avatarBeamSpiritualDamage = builder.comment(
               "Extra direct spiritual damage applied after normal space damage. This helps against high-health Tensura entities."
            )
            .defineInRange("avatarBeamSpiritualDamage", 95000.0, 0.0, Float.MAX_VALUE);
         this.avatarBeamForcedMinimumHealthDamage = builder.comment(
               "Fallback direct health damage if normal damage was fully resisted. Prevents the beam from doing literally nothing to extreme resistance targets."
            )
            .defineInRange("avatarBeamForcedMinimumHealthDamage", 80.0, 0.0, Float.MAX_VALUE);
         builder.pop();
         builder.comment("Avatar Beam charge scaling. Final multiplier = base + chargePercent * bonus.");
         builder.push("avatar_beam_scaling");
         this.avatarBeamDamageMultiplierBase = builder.comment("Damage multiplier at minimum release. Default 2.0 means 200 percent.")
            .defineInRange("avatarBeamDamageMultiplierBase", 2.0, 0.0, 1024.0);
         this.avatarBeamDamageMultiplierBonus = builder.comment(
               "Extra damage multiplier gained at full charge. Default 8.0 means full charge becomes 10x total with base 2.0."
            )
            .defineInRange("avatarBeamDamageMultiplierBonus", 8.0, 0.0, 1024.0);
         this.avatarBeamRadiusMultiplierBase = builder.comment("Radius multiplier at minimum release.")
            .defineInRange("avatarBeamRadiusMultiplierBase", 0.95, 0.0, 1024.0);
         this.avatarBeamRadiusMultiplierBonus = builder.comment("Extra radius multiplier gained at full charge.")
            .defineInRange("avatarBeamRadiusMultiplierBonus", 0.95, 0.0, 1024.0);
         builder.pop();
         builder.comment("Avatar Beam interval values. Higher intervals are generally better for performance.");
         builder.push("avatar_beam_intervals");
         this.avatarBeamPayloadRefreshTicks = builder.comment("How often active beam visuals are resent to nearby players. Default 4 = five times per second.")
            .defineInRange("avatarBeamPayloadRefreshTicks", 4, 1, Integer.MAX_VALUE);
         this.avatarBeamDamageIntervalTicks = builder.comment("How often the active beam deals repeated damage. Default 2 = ten times per second.")
            .defineInRange("avatarBeamDamageIntervalTicks", 2, 1, Integer.MAX_VALUE);
         this.avatarBeamPulseIntervalTicks = builder.comment("How often normal pulse damage/sounds happen during the beam. Default 16 = 0.8 seconds.")
            .defineInRange("avatarBeamPulseIntervalTicks", 16, 1, Integer.MAX_VALUE);
         this.avatarBeamFinisherPulseIntervalTicks = builder.comment(
               "How often stronger finisher pulse damage/sounds happen during the beam. Default 45 = 2.25 seconds."
            )
            .defineInRange("avatarBeamFinisherPulseIntervalTicks", 45, 1, Integer.MAX_VALUE);
         this.avatarBeamMasteryIntervalTicks = builder.comment("How often holding the beam gives mastery. Default 20 = once per second.")
            .defineInRange("avatarBeamMasteryIntervalTicks", 20, 1, Integer.MAX_VALUE);
         this.avatarBeamChargeMessageIntervalTicks = builder.comment("How often the action-bar charge percentage updates while holding.")
            .defineInRange("avatarBeamChargeMessageIntervalTicks", 5, 1, Integer.MAX_VALUE);
         this.avatarBeamChargeSoundIntervalTicks = builder.comment("How often the charge sound plays while holding.")
            .defineInRange("avatarBeamChargeSoundIntervalTicks", 10, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Temporary resistance degradation applied to the caster while Avatar Beam is active.");
         builder.push("avatar_beam_degradation");
         this.avatarBeamResistanceDegradation = builder.comment(
               "General Resistance Degradation bonus while the beam is active. Higher values make enemies resist less."
            )
            .defineInRange("avatarBeamResistanceDegradation", 4.0, 0.0, 1000.0);
         this.avatarBeamSpaceResistanceDegradation = builder.comment(
               "Space Resistance Degradation bonus while the beam is active. Higher values specifically strengthen space damage."
            )
            .defineInRange("avatarBeamSpaceResistanceDegradation", 4.0, 0.0, 1000.0);
         builder.pop();
         builder.comment("Cube Judgement costs. Normal cast drops one cube on a target. Shift cast creates a cube rain around the user.");
         builder.push("cube_judgement_costs");
         this.cubeJudgementNormalCost = builder.comment("Magicule cost for normal Cube Judgement.")
            .defineInRange("cubeJudgementNormalCost", 9000.0, 0.0, Double.MAX_VALUE);
         this.cubeJudgementShiftCost = builder.comment("Magicule cost for shift Cube Judgement rain.")
            .defineInRange("cubeJudgementShiftCost", 18500.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.comment("Cube Judgement activation and targeting.");
         builder.push("cube_judgement_activation");
         this.cubeJudgementNormalCooldownTicks = builder.comment("Cooldown after normal Cube Judgement.")
            .defineInRange("cubeJudgementNormalCooldownTicks", 42, 0, Integer.MAX_VALUE);
         this.cubeJudgementShiftCooldownTicks = builder.comment("Cooldown after shift Cube Judgement rain.")
            .defineInRange("cubeJudgementShiftCooldownTicks", 90, 0, Integer.MAX_VALUE);
         this.cubeJudgementNormalMasteredCooldownReductionTicks = builder.comment("Cooldown reduction for normal Cube Judgement when mastered.")
            .defineInRange("cubeJudgementNormalMasteredCooldownReductionTicks", 8, 0, Integer.MAX_VALUE);
         this.cubeJudgementShiftMasteredCooldownReductionTicks = builder.comment("Cooldown reduction for shift Cube Judgement when mastered.")
            .defineInRange("cubeJudgementShiftMasteredCooldownReductionTicks", 14, 0, Integer.MAX_VALUE);
         this.cubeJudgementTargetRange = builder.comment("Target range for normal Cube Judgement before mastery.")
            .defineInRange("cubeJudgementTargetRange", 48.0, 1.0, 512.0);
         this.cubeJudgementTargetRangeMastered = builder.comment("Target range for normal Cube Judgement after mastery.")
            .defineInRange("cubeJudgementTargetRangeMastered", 64.0, 1.0, 512.0);
         builder.pop();
         builder.comment("Cube Judgement lifetime and anti-lag limits.");
         builder.push("cube_judgement_timing");
         this.cubeJudgementSingleFallDurationTicks = builder.comment("Fall duration for the single cube.")
            .defineInRange("cubeJudgementSingleFallDurationTicks", 105, 1, Integer.MAX_VALUE);
         this.cubeJudgementRainFallDurationTicks = builder.comment("Fall duration for rain cubes.")
            .defineInRange("cubeJudgementRainFallDurationTicks", 145, 1, Integer.MAX_VALUE);
         this.cubeJudgementGroundDespawnTicks = builder.comment("How long cubes remain after hitting solid ground.")
            .defineInRange("cubeJudgementGroundDespawnTicks", 20, 0, Integer.MAX_VALUE);
         this.cubeJudgementNoGroundDespawnTicks = builder.comment("How long cubes remain when no solid ground is found.")
            .defineInRange("cubeJudgementNoGroundDespawnTicks", 400, 0, Integer.MAX_VALUE);
         this.cubeJudgementWatchRange = builder.comment(
               "Range used to send falling cube visuals to players. Higher values show farther but send to more clients."
            )
            .defineInRange("cubeJudgementWatchRange", 280.0, 1.0, 4096.0);
         this.cubeJudgementRainCubes = builder.comment("Number of cubes in shift rain before mastery.").defineInRange("cubeJudgementRainCubes", 34, 1, 512);
         this.cubeJudgementRainCubesMastered = builder.comment("Number of cubes in shift rain after mastery.")
            .defineInRange("cubeJudgementRainCubesMastered", 42, 1, 512);
         this.cubeJudgementRainCubeDelayTicks = builder.comment("Base delay added between each rain cube.")
            .defineInRange("cubeJudgementRainCubeDelayTicks", 2, 0, Integer.MAX_VALUE);
         this.cubeJudgementRainCubeDelayRandomTicks = builder.comment("Random extra delay spread for rain cubes.")
            .defineInRange("cubeJudgementRainCubeDelayRandomTicks", 8, 0, Integer.MAX_VALUE);
         this.cubeJudgementRainExtraLifetimeTicks = builder.comment("Extra lifetime added after rain cube fall/despawn time.")
            .defineInRange("cubeJudgementRainExtraLifetimeTicks", 90, 0, Integer.MAX_VALUE);
         this.cubeJudgementSingleRecentHitTicks = builder.comment("Same target cannot be hit again by the single cube inside this tick window.")
            .defineInRange("cubeJudgementSingleRecentHitTicks", 12, 0, Integer.MAX_VALUE);
         this.cubeJudgementRainRecentHitTicks = builder.comment("Same target cannot be hit again by rain cubes inside this tick window.")
            .defineInRange("cubeJudgementRainRecentHitTicks", 9, 0, Integer.MAX_VALUE);
         this.cubeJudgementRainMaxHitsPerTick = builder.comment("Maximum rain hits processed per tick before mastery. Keeps rain from lagging massive crowds.")
            .defineInRange("cubeJudgementRainMaxHitsPerTick", 6, 1, Integer.MAX_VALUE);
         this.cubeJudgementRainMaxHitsPerTickMastered = builder.comment("Maximum rain hits processed per tick after mastery.")
            .defineInRange("cubeJudgementRainMaxHitsPerTickMastered", 8, 1, Integer.MAX_VALUE);
         this.cubeJudgementSingleMaxImpactTargets = builder.comment("Maximum targets hit by the single cube impact burst.")
            .defineInRange("cubeJudgementSingleMaxImpactTargets", 10, 1, Integer.MAX_VALUE);
         this.cubeJudgementRainMaxImpactTargets = builder.comment("Maximum targets hit by each rain cube impact burst.")
            .defineInRange("cubeJudgementRainMaxImpactTargets", 4, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Cube Judgement cube sizes, rain spread, height, and falling sway.");
         builder.push("cube_judgement_shape");
         this.cubeJudgementSingleSize = builder.comment("Single cube size before mastery.").defineInRange("cubeJudgementSingleSize", 7.2, 0.1, 128.0);
         this.cubeJudgementSingleSizeMastered = builder.comment("Single cube size after mastery.")
            .defineInRange("cubeJudgementSingleSizeMastered", 8.6, 0.1, 128.0);
         this.cubeJudgementRainSize = builder.comment("Base rain cube size before mastery.").defineInRange("cubeJudgementRainSize", 4.0, 0.1, 128.0);
         this.cubeJudgementRainSizeMastered = builder.comment("Base rain cube size after mastery.")
            .defineInRange("cubeJudgementRainSizeMastered", 4.8, 0.1, 128.0);
         this.cubeJudgementRainBigSizeBonus = builder.comment("Extra size for every sixth large rain cube before mastery.")
            .defineInRange("cubeJudgementRainBigSizeBonus", 1.5, 0.0, 128.0);
         this.cubeJudgementRainBigSizeBonusMastered = builder.comment("Extra size for every sixth large rain cube after mastery.")
            .defineInRange("cubeJudgementRainBigSizeBonusMastered", 2.0, 0.0, 128.0);
         this.cubeJudgementRainMediumSizeBonus = builder.comment("Extra size for medium rain cubes before mastery.")
            .defineInRange("cubeJudgementRainMediumSizeBonus", 1.0, 0.0, 128.0);
         this.cubeJudgementRainMediumSizeBonusMastered = builder.comment("Extra size for medium rain cubes after mastery.")
            .defineInRange("cubeJudgementRainMediumSizeBonusMastered", 1.4, 0.0, 128.0);
         this.cubeJudgementRainSmallSizeBonus = builder.comment("Extra size for small bonus rain cubes.")
            .defineInRange("cubeJudgementRainSmallSizeBonus", 0.7, 0.0, 128.0);
         this.cubeJudgementRainRadius = builder.comment("Rain spread radius before mastery.").defineInRange("cubeJudgementRainRadius", 25.0, 0.0, 512.0);
         this.cubeJudgementRainRadiusMastered = builder.comment("Rain spread radius after mastery.")
            .defineInRange("cubeJudgementRainRadiusMastered", 31.0, 0.0, 512.0);
         this.cubeJudgementSingleStartHeight = builder.comment("Single cube start height before mastery.")
            .defineInRange("cubeJudgementSingleStartHeight", 41.0, 0.0, 512.0);
         this.cubeJudgementSingleStartHeightMastered = builder.comment("Single cube start height after mastery.")
            .defineInRange("cubeJudgementSingleStartHeightMastered", 49.0, 0.0, 512.0);
         this.cubeJudgementRainStartHeight = builder.comment("Rain cube start height before mastery.")
            .defineInRange("cubeJudgementRainStartHeight", 36.0, 0.0, 512.0);
         this.cubeJudgementRainStartHeightMastered = builder.comment("Rain cube start height after mastery.")
            .defineInRange("cubeJudgementRainStartHeightMastered", 43.0, 0.0, 512.0);
         this.cubeJudgementSingleFallbackEndYOffset = builder.comment("Single cube fallback end Y offset when no solid ground is found before mastery.")
            .defineInRange("cubeJudgementSingleFallbackEndYOffset", 1.75, -512.0, 512.0);
         this.cubeJudgementSingleFallbackEndYOffsetMastered = builder.comment("Single cube fallback end Y offset when no solid ground is found after mastery.")
            .defineInRange("cubeJudgementSingleFallbackEndYOffsetMastered", 2.25, -512.0, 512.0);
         this.cubeJudgementRainFallbackEndYOffset = builder.comment("Rain cube fallback end Y offset when no solid ground is found before mastery.")
            .defineInRange("cubeJudgementRainFallbackEndYOffset", 1.25, -512.0, 512.0);
         this.cubeJudgementRainFallbackEndYOffsetMastered = builder.comment("Rain cube fallback end Y offset when no solid ground is found after mastery.")
            .defineInRange("cubeJudgementRainFallbackEndYOffsetMastered", 1.55, -512.0, 512.0);
         this.cubeJudgementSingleSway = builder.comment("Horizontal falling sway for single cubes.").defineInRange("cubeJudgementSingleSway", 0.65, 0.0, 128.0);
         this.cubeJudgementRainSway = builder.comment("Horizontal falling sway for rain cubes.").defineInRange("cubeJudgementRainSway", 1.55, 0.0, 128.0);
         builder.pop();
         builder.comment("Cube Judgement damage values.");
         builder.push("cube_judgement_damage");
         this.cubeJudgementSingleDamage = builder.comment("Flat damage for single cube before mastery.")
            .defineInRange("cubeJudgementSingleDamage", 310.0, 0.0, Float.MAX_VALUE);
         this.cubeJudgementSingleDamageMastered = builder.comment("Flat damage for single cube after mastery.")
            .defineInRange("cubeJudgementSingleDamageMastered", 430.0, 0.0, Float.MAX_VALUE);
         this.cubeJudgementRainDamage = builder.comment("Flat damage for rain cubes before mastery.")
            .defineInRange("cubeJudgementRainDamage", 96.0, 0.0, Float.MAX_VALUE);
         this.cubeJudgementRainDamageMastered = builder.comment("Flat damage for rain cubes after mastery.")
            .defineInRange("cubeJudgementRainDamageMastered", 135.0, 0.0, Float.MAX_VALUE);
         this.cubeJudgementSingleSizeDamageMultiplier = builder.comment("Extra single cube damage per cube size.")
            .defineInRange("cubeJudgementSingleSizeDamageMultiplier", 28.0, 0.0, Float.MAX_VALUE);
         this.cubeJudgementRainSizeDamageMultiplier = builder.comment("Extra rain cube damage per cube size.")
            .defineInRange("cubeJudgementRainSizeDamageMultiplier", 13.0, 0.0, Float.MAX_VALUE);
         this.cubeJudgementSingleMobMaxHealthDamage = builder.comment("Single cube bonus damage to mobs based on max health. 0.50 = 50 percent.")
            .defineInRange("cubeJudgementSingleMobMaxHealthDamage", 0.5, 0.0, 100.0);
         this.cubeJudgementSinglePlayerMaxHealthDamage = builder.comment("Single cube bonus damage to players based on max health.")
            .defineInRange("cubeJudgementSinglePlayerMaxHealthDamage", 0.2, 0.0, 100.0);
         this.cubeJudgementRainMobMaxHealthDamage = builder.comment("Rain cube bonus damage to mobs based on max health.")
            .defineInRange("cubeJudgementRainMobMaxHealthDamage", 0.2, 0.0, 100.0);
         this.cubeJudgementRainPlayerMaxHealthDamage = builder.comment("Rain cube bonus damage to players based on max health.")
            .defineInRange("cubeJudgementRainPlayerMaxHealthDamage", 0.075, 0.0, 100.0);
         this.cubeJudgementSinglePlayerDamageMultiplier = builder.comment("Final single cube multiplier against players. 0.72 = players take 72 percent.")
            .defineInRange("cubeJudgementSinglePlayerDamageMultiplier", 0.72, 0.0, 100.0);
         this.cubeJudgementRainPlayerDamageMultiplier = builder.comment("Final rain cube multiplier against players.")
            .defineInRange("cubeJudgementRainPlayerDamageMultiplier", 0.64, 0.0, 100.0);
         this.cubeJudgementForcedDamageMultiplier = builder.comment("Fallback direct health damage multiplier if normal damage is fully resisted.")
            .defineInRange("cubeJudgementForcedDamageMultiplier", 0.9, 0.0, 100.0);
         builder.pop();
         builder.comment("Cube Judgement debuff durations and amplifiers.");
         builder.push("cube_judgement_debuffs");
         this.cubeJudgementSingleSpatialBlockadeTicks = builder.defineInRange("cubeJudgementSingleSpatialBlockadeTicks", 170, 1, Integer.MAX_VALUE);
         this.cubeJudgementRainSpatialBlockadeTicks = builder.defineInRange("cubeJudgementRainSpatialBlockadeTicks", 105, 1, Integer.MAX_VALUE);
         this.cubeJudgementSingleSpatialBlockadeAmplifier = builder.defineInRange("cubeJudgementSingleSpatialBlockadeAmplifier", 4, 0, 255);
         this.cubeJudgementRainSpatialBlockadeAmplifier = builder.defineInRange("cubeJudgementRainSpatialBlockadeAmplifier", 2, 0, 255);
         this.cubeJudgementSingleMagicInterferenceTicks = builder.defineInRange("cubeJudgementSingleMagicInterferenceTicks", 135, 1, Integer.MAX_VALUE);
         this.cubeJudgementRainMagicInterferenceTicks = builder.defineInRange("cubeJudgementRainMagicInterferenceTicks", 80, 1, Integer.MAX_VALUE);
         this.cubeJudgementSingleMagicInterferenceAmplifier = builder.defineInRange("cubeJudgementSingleMagicInterferenceAmplifier", 3, 0, 255);
         this.cubeJudgementRainMagicInterferenceAmplifier = builder.defineInRange("cubeJudgementRainMagicInterferenceAmplifier", 1, 0, 255);
         this.cubeJudgementSingleMovementInterferenceTicks = builder.defineInRange("cubeJudgementSingleMovementInterferenceTicks", 120, 1, Integer.MAX_VALUE);
         this.cubeJudgementRainMovementInterferenceTicks = builder.defineInRange("cubeJudgementRainMovementInterferenceTicks", 70, 1, Integer.MAX_VALUE);
         this.cubeJudgementSingleMovementInterferenceAmplifier = builder.defineInRange("cubeJudgementSingleMovementInterferenceAmplifier", 3, 0, 255);
         this.cubeJudgementRainMovementInterferenceAmplifier = builder.defineInRange("cubeJudgementRainMovementInterferenceAmplifier", 1, 0, 255);
         builder.pop();
         builder.comment("End Black Hole values.");
         builder.push("end_black_hole");
         this.endBlackHoleRadius = builder.comment("Black hole radius passed into the black hole handler.")
            .defineInRange("endBlackHoleRadius", 60.0, 0.0, 512.0);
         this.endBlackHoleDurationTicks = builder.comment("Black hole duration. Default 700 = 35 seconds.")
            .defineInRange("endBlackHoleDurationTicks", 700, 1, Integer.MAX_VALUE);
         this.endBlackHoleTargetRange = builder.comment("Range used to place the black hole on a looked-at entity.")
            .defineInRange("endBlackHoleTargetRange", 96.0, 1.0, 1024.0);
         this.endBlackHoleFallbackRange = builder.comment("Raycast range used when no entity target is found.")
            .defineInRange("endBlackHoleFallbackRange", 42.0, 1.0, 1024.0);
         this.endBlackHoleFallbackMissRange = builder.comment("Distance in front of the caster used if the raycast does not hit a block.")
            .defineInRange("endBlackHoleFallbackMissRange", 32.0, 1.0, 1024.0);
         this.endBlackHoleHitYOffset = builder.comment("Y offset above the hit block for black hole placement.")
            .defineInRange("endBlackHoleHitYOffset", 2.5, -64.0, 64.0);
         this.endBlackHoleLookTargetInflate = builder.comment("Extra targeting width for finding looked-at entities.")
            .defineInRange("endBlackHoleLookTargetInflate", 4.0, 0.0, 64.0);
         this.endBlackHoleCooldownTicks = builder.comment("Cooldown after spawning End Black Hole.")
            .defineInRange("endBlackHoleCooldownTicks", 55, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Asmoday toggled passive. Distance Tax reduces incoming damage more when attackers are farther away.");
         builder.push("passive_distance_tax");
         this.passiveDistanceTaxStart = builder.comment(
               "Distance where ranged damage reduction starts before mastery. Attacks closer than this are not reduced."
            )
            .defineInRange("passiveDistanceTaxStart", 5.0, 0.0, 512.0);
         this.passiveDistanceTaxStartMastered = builder.comment("Distance where ranged damage reduction starts after mastery.")
            .defineInRange("passiveDistanceTaxStartMastered", 3.5, 0.0, 512.0);
         this.passiveDistanceTaxFull = builder.comment("Distance where ranged damage reduction reaches full strength before mastery.")
            .defineInRange("passiveDistanceTaxFull", 30.0, 1.0, 512.0);
         this.passiveDistanceTaxFullMastered = builder.comment("Distance where ranged damage reduction reaches full strength after mastery.")
            .defineInRange("passiveDistanceTaxFullMastered", 42.0, 1.0, 512.0);
         this.passiveMaxDistanceReduction = builder.comment("Maximum incoming damage reduction before mastery. 0.48 = 48 percent reduction.")
            .defineInRange("passiveMaxDistanceReduction", 0.48, 0.0, 1.0);
         this.passiveMaxDistanceReductionMastered = builder.comment("Maximum incoming damage reduction after mastery.")
            .defineInRange("passiveMaxDistanceReductionMastered", 0.68, 0.0, 1.0);
         builder.pop();
         builder.comment("Asmoday spatial lock. Redirects enemy teleport / instant transmission destinations away from the Asmoday user.");
         builder.push("passive_space_lock");
         this.passiveSpaceLockRange = builder.comment("Range where Asmoday can intercept spatial movement before mastery.")
            .defineInRange("passiveSpaceLockRange", 20.0, 0.0, 512.0);
         this.passiveSpaceLockRangeMastered = builder.comment("Range where Asmoday can intercept spatial movement after mastery.")
            .defineInRange("passiveSpaceLockRangeMastered", 32.0, 0.0, 512.0);
         this.passiveSpaceLockPush = builder.comment("Distance the intercepted destination is pushed away from the Asmoday user before mastery.")
            .defineInRange("passiveSpaceLockPush", 13.0, 0.0, 512.0);
         this.passiveSpaceLockPushMastered = builder.comment("Distance the intercepted destination is pushed away after mastery.")
            .defineInRange("passiveSpaceLockPushMastered", 20.0, 0.0, 512.0);
         this.passiveSpaceLockCooldownTicks = builder.comment("Cooldown between spatial movement redirects before mastery.")
            .defineInRange("passiveSpaceLockCooldownTicks", 45, 0, Integer.MAX_VALUE);
         this.passiveSpaceLockCooldownTicksMastered = builder.comment("Cooldown between spatial movement redirects after mastery.")
            .defineInRange("passiveSpaceLockCooldownTicksMastered", 25, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Asmoday spatial anchor. Limits forced movement and extreme velocity while toggled.");
         builder.push("passive_anchor");
         this.passiveAnchorHorizontalCap = builder.comment("Maximum horizontal velocity while anchored before mastery.")
            .defineInRange("passiveAnchorHorizontalCap", 0.72, 0.0, 128.0);
         this.passiveAnchorHorizontalCapMastered = builder.comment("Maximum horizontal velocity while anchored after mastery.")
            .defineInRange("passiveAnchorHorizontalCapMastered", 0.48, 0.0, 128.0);
         this.passiveAnchorVerticalCap = builder.comment("Maximum vertical velocity while anchored before mastery.")
            .defineInRange("passiveAnchorVerticalCap", 0.72, 0.0, 128.0);
         this.passiveAnchorVerticalCapMastered = builder.comment("Maximum vertical velocity while anchored after mastery.")
            .defineInRange("passiveAnchorVerticalCapMastered", 0.48, 0.0, 128.0);
         this.passiveForcedMovementScale = builder.comment("Multiplier applied to external forced movement before mastery. 0.10 = only 10 percent remains.")
            .defineInRange("passiveForcedMovementScale", 0.1, 0.0, 1.0);
         this.passiveForcedMovementScaleMastered = builder.comment("Multiplier applied to external forced movement after mastery.")
            .defineInRange("passiveForcedMovementScaleMastered", 0.04, 0.0, 1.0);
         this.passiveForcedMovementMinimumStrength = builder.comment("Forced movement below this strength is ignored by the passive.")
            .defineInRange("passiveForcedMovementMinimumStrength", 0.08, 0.0, 128.0);
         this.passiveAnchorPulseCooldownTicks = builder.comment("Visual pulse cooldown before mastery. Higher values are better for performance.")
            .defineInRange("passiveAnchorPulseCooldownTicks", 10, 0, Integer.MAX_VALUE);
         this.passiveAnchorPulseCooldownTicksMastered = builder.comment("Visual pulse cooldown after mastery.")
            .defineInRange("passiveAnchorPulseCooldownTicksMastered", 6, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Prison Realm activation. This has no obtainment config; it only controls the mode behavior.");
         builder.push("prison_realm_activation");
         this.prisonRealmCostMastered = builder.comment("Magicule cost to close Prison Realm after mastery.")
            .defineInRange("prisonRealmCostMastered", 100000.0, 0.0, Double.MAX_VALUE);
         this.prisonRealmCooldownTicks = builder.comment("Cooldown after successfully sealing a player before mastery. Default 1800 = 90 seconds.")
            .defineInRange("prisonRealmCooldownTicks", 1800, 0, Integer.MAX_VALUE);
         this.prisonRealmCooldownTicksMastered = builder.comment("Cooldown after successfully sealing a player after mastery. Default 1200 = 60 seconds.")
            .defineInRange("prisonRealmCooldownTicksMastered", 1200, 0, Integer.MAX_VALUE);
         this.prisonRealmHoldSeconds = builder.comment("Seconds the user must hold the mode while staring at a player before the prison closes.")
            .defineInRange("prisonRealmHoldSeconds", 30, 1, Integer.MAX_VALUE);
         this.prisonRealmRange = builder.comment("Targeting range for Prison Realm before mastery.").defineInRange("prisonRealmRange", 48.0, 1.0, 512.0);
         this.prisonRealmRangeMastered = builder.comment("Targeting range for Prison Realm after mastery.")
            .defineInRange("prisonRealmRangeMastered", 64.0, 1.0, 512.0);
         this.prisonRealmHoldSlowMultiplier = builder.comment(
               "Movement multiplier applied to the target while the prison is charging. 0.45 = target keeps 45 percent velocity."
            )
            .defineInRange("prisonRealmHoldSlowMultiplier", 0.45, 0.0, 1.0);
         builder.pop();
         builder.comment("Prison Realm upkeep and stability. If upkeep cannot be paid, stability drops until the prison collapses.");
         builder.push("prison_realm_upkeep");
         this.prisonPayIntervalTicks = builder.comment("How often the owner pays prison upkeep. Default 100 = every 5 seconds.")
            .defineInRange("prisonPayIntervalTicks", 100, 1, Integer.MAX_VALUE);
         this.prisonMagiculeDrain = builder.comment("Magicules drained per upkeep payment before mastery. Magicules are used first.")
            .defineInRange("prisonMagiculeDrain", 500000.0, 0.0, Double.MAX_VALUE);
         this.prisonAuraDrain = builder.comment("Aura drained per upkeep payment if magicules are insufficient.")
            .defineInRange("prisonAuraDrain", 450.0, 0.0, Double.MAX_VALUE);
         this.prisonMasteredDrainMultiplier = builder.comment("Drain multiplier after mastery. 0.62 = mastered upkeep costs 62 percent of normal.")
            .defineInRange("prisonMasteredDrainMultiplier", 0.62, 0.0, 100.0);
         this.prisonMaxStability = builder.comment("Maximum prison stability percent.").defineInRange("prisonMaxStability", 100, 1, Integer.MAX_VALUE);
         this.prisonDestabilizeOnFail = builder.comment("Stability lost when upkeep cannot be paid.")
            .defineInRange("prisonDestabilizeOnFail", 8, 1, Integer.MAX_VALUE);
         this.prisonStabilityRecoverPayIntervals = builder.comment(
               "Successful payment intervals needed to recover 1 stability. Default 3 = every 15 seconds if pay interval is 100."
            )
            .defineInRange("prisonStabilityRecoverPayIntervals", 3, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Prison Realm box and placement values.");
         builder.push("prison_realm_box");
         this.prisonBoxRadius = builder.comment("Half-width of the bedrock prison box. 8 creates a 17x17 interior span before walls.")
            .defineInRange("prisonBoxRadius", 8, 1, 64);
         this.prisonBoxFloorYOffset = builder.comment("Floor Y offset from the prison center.").defineInRange("prisonBoxFloorYOffset", -1, -64, 64);
         this.prisonBoxCeilingYOffset = builder.comment("Ceiling Y offset from the prison center.").defineInRange("prisonBoxCeilingYOffset", 11, -64, 128);
         this.prisonWatchRange = builder.comment("Range used for prison visuals and broadcast messages. Higher values send to more players.")
            .defineInRange("prisonWatchRange", 220.0, 1.0, 4096.0);
         this.prisonMaxDistanceFromCenter = builder.comment("Maximum horizontal distance prisoner may be from center before being snapped back.")
            .defineInRange("prisonMaxDistanceFromCenter", 6.5, 0.0, 512.0);
         this.prisonSpawnY = builder.comment("Y coordinate used for generated prison cells.").defineInRange("prisonSpawnY", 80.0, -2048.0, 2048.0);
         this.prisonCoordinateSpreadBits = builder.comment("UUID coordinate spread mask bits. 12 means 4096 possible slots per axis.")
            .defineInRange("prisonCoordinateSpreadBits", 12, 1, 20);
         this.prisonCoordinateSpacing = builder.comment("Spacing between generated prison cells.").defineInRange("prisonCoordinateSpacing", 64, 16, 4096);
         builder.pop();
         builder.comment("Prison Realm protection and debuff effects.");
         builder.push("prison_realm_effects");
         this.prisonProtectionInvulnerabilityTicks = builder.comment("Invulnerability maintained on prisoners while sealed.")
            .defineInRange("prisonProtectionInvulnerabilityTicks", 80, 0, Integer.MAX_VALUE);
         this.prisonProtectionAbsorption = builder.comment("Minimum absorption kept on prisoners.")
            .defineInRange("prisonProtectionAbsorption", 20.0, 0.0, Float.MAX_VALUE);
         this.prisonProtectionMotionScale = builder.comment("Velocity multiplier applied while prisoner is protected.")
            .defineInRange("prisonProtectionMotionScale", 0.12, 0.0, 1.0);
         this.prisonEffectDurationTicks = builder.comment("Duration of each refreshed prison debuff effect.")
            .defineInRange("prisonEffectDurationTicks", 100, 1, Integer.MAX_VALUE);
         this.prisonEffectRefreshTicks = builder.comment("How often prison effects refresh. Higher values are better for performance.")
            .defineInRange("prisonEffectRefreshTicks", 40, 1, Integer.MAX_VALUE);
         this.prisonSpatialBlockadeAmplifier = builder.comment("Spatial Blockade amplifier while sealed. 0 means level 1.")
            .defineInRange("prisonSpatialBlockadeAmplifier", 7, 0, 255);
         this.prisonMagicInterferenceAmplifier = builder.comment("Magic Interference amplifier while sealed. 0 means level 1.")
            .defineInRange("prisonMagicInterferenceAmplifier", 5, 0, 255);
         this.prisonMovementInterferenceAmplifier = builder.comment("Movement Interference amplifier while sealed. 0 means level 1.")
            .defineInRange("prisonMovementInterferenceAmplifier", 5, 0, 255);
         this.prisonEnergyBlockadeAmplifier = builder.comment("Energy Blockade amplifier while sealed. 0 means level 1.")
            .defineInRange("prisonEnergyBlockadeAmplifier", 4, 0, 255);
         this.prisonViolentReleaseInvulnerabilityTicks = builder.comment("Invulnerability after violent prison release.")
            .defineInRange("prisonViolentReleaseInvulnerabilityTicks", 80, 0, Integer.MAX_VALUE);
         this.prisonSafeReleaseInvulnerabilityTicks = builder.comment("Invulnerability after safe prison release.")
            .defineInRange("prisonSafeReleaseInvulnerabilityTicks", 20, 0, Integer.MAX_VALUE);
         this.prisonViolentReleaseMovementTicks = builder.comment("Movement Interference duration after violent release.")
            .defineInRange("prisonViolentReleaseMovementTicks", 80, 1, Integer.MAX_VALUE);
         this.prisonViolentReleaseMovementAmplifier = builder.comment("Movement Interference amplifier after violent release.")
            .defineInRange("prisonViolentReleaseMovementAmplifier", 1, 0, 255);
         builder.pop();
         builder.comment("Prison Realm visual packet values.");
         builder.push("prison_realm_fx");
         this.prisonChargeBaseSize = builder.comment("Starting size of the charging prison visual.").defineInRange("prisonChargeBaseSize", 4.0, 0.0, 256.0);
         this.prisonChargeBonusSize = builder.comment("Extra size added as charge progress reaches 100 percent.")
            .defineInRange("prisonChargeBonusSize", 11.0, 0.0, 256.0);
         this.prisonChargeFxDurationTicks = builder.comment("Duration of each charge visual packet.")
            .defineInRange("prisonChargeFxDurationTicks", 34, 1, Integer.MAX_VALUE);
         this.prisonAmbientFxIntervalTicks = builder.comment("How often prisoner ambient visuals are emitted. Higher values are better for performance.")
            .defineInRange("prisonAmbientFxIntervalTicks", 40, 1, Integer.MAX_VALUE);
         this.prisonAmbientFxDurationTicks = builder.comment("Duration of ambient prison visual packets.")
            .defineInRange("prisonAmbientFxDurationTicks", 48, 1, Integer.MAX_VALUE);
         this.prisonAmbientFxSize = builder.comment("Size of ambient prison visual packets.").defineInRange("prisonAmbientFxSize", 10.0, 0.0, 256.0);
         builder.pop();
         builder.comment("Spatial Cube Barrage activation values.");
         builder.push("spatial_cube_barrage_activation");
         this.spatialCubeBarrageCostMastered = builder.comment("Magicule cost for Spatial Cube Barrage after mastery.")
            .defineInRange("spatialCubeBarrageCostMastered", 10500.0, 0.0, Double.MAX_VALUE);
         this.spatialCubeBarrageCooldownTicks = builder.comment("Base cooldown after firing Spatial Cube Barrage.")
            .defineInRange("spatialCubeBarrageCooldownTicks", 24, 0, Integer.MAX_VALUE);
         this.spatialCubeBarrageShiftCooldownBonusTicks = builder.comment("Extra cooldown added when shift-firing the stronger barrage.")
            .defineInRange("spatialCubeBarrageShiftCooldownBonusTicks", 14, 0, Integer.MAX_VALUE);
         this.spatialCubeBarrageRange = builder.comment("Targeting range before mastery.").defineInRange("spatialCubeBarrageRange", 42.0, 1.0, 512.0);
         this.spatialCubeBarrageRangeMastered = builder.comment("Targeting range after mastery.")
            .defineInRange("spatialCubeBarrageRangeMastered", 58.0, 1.0, 512.0);
         builder.pop();
         builder.comment("Spatial Cube Barrage lifetime, visuals, and anti-lag values.");
         builder.push("spatial_cube_barrage_timing");
         this.spatialCubeClientDurationTicks = builder.comment("Duration sent to the client visual payload.")
            .defineInRange("spatialCubeClientDurationTicks", 128, 1, Integer.MAX_VALUE);
         this.spatialCubeServerLifeTicks = builder.comment("Server-side lifetime of the cube barrage hit detection.")
            .defineInRange("spatialCubeServerLifeTicks", 138, 1, Integer.MAX_VALUE);
         this.spatialCubeWatchRange = builder.comment("Range used to send cube barrage visuals to nearby players. Higher values send to more clients.")
            .defineInRange("spatialCubeWatchRange", 260.0, 1.0, 4096.0);
         this.spatialCubeHitboxPadding = builder.comment("Extra padding added to every cube collision check.")
            .defineInRange("spatialCubeHitboxPadding", 0.12, 0.0, 16.0);
         this.spatialCubeNormalStepDelayTicks = builder.comment("Delay between cube steps in normal barrage.")
            .defineInRange("spatialCubeNormalStepDelayTicks", 2, 0, Integer.MAX_VALUE);
         this.spatialCubeShiftedStepDelayTicks = builder.comment("Delay between cube steps in shifted barrage.")
            .defineInRange("spatialCubeShiftedStepDelayTicks", 1, 0, Integer.MAX_VALUE);
         this.spatialCubeNormalTravelTicks = builder.comment("Travel timing divisor for normal cube streams. Lower = faster hit timing.")
            .defineInRange("spatialCubeNormalTravelTicks", 66.0, 1.0, 1000.0);
         this.spatialCubeShiftedTravelTicks = builder.comment("Travel timing divisor for shifted cube streams. Lower = faster hit timing.")
            .defineInRange("spatialCubeShiftedTravelTicks", 54.0, 1.0, 1000.0);
         this.spatialCubeNormalHitCooldownTicks = builder.comment("Minimum ticks between successful normal barrage hits on the target.")
            .defineInRange("spatialCubeNormalHitCooldownTicks", 4, 1, Integer.MAX_VALUE);
         this.spatialCubeShiftedHitCooldownTicks = builder.comment("Minimum ticks between successful shifted barrage hits on the target.")
            .defineInRange("spatialCubeShiftedHitCooldownTicks", 3, 1, Integer.MAX_VALUE);
         this.spatialCubeTrailParticleIntervalTicks = builder.comment("How often server-side trail particles appear. Higher values are better for performance.")
            .defineInRange("spatialCubeTrailParticleIntervalTicks", 6, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Spatial Cube Barrage stream and visual cube count values.");
         builder.push("spatial_cube_barrage_counts");
         this.spatialCubeStreams = builder.comment("Number of streams in normal barrage.").defineInRange("spatialCubeStreams", 4, 1, 64);
         this.spatialCubeStreamsShifted = builder.comment("Number of streams in shifted barrage.").defineInRange("spatialCubeStreamsShifted", 7, 1, 64);
         this.spatialCubeCubesPerStream = builder.comment("Server hit-detection cubes per stream before mastery.")
            .defineInRange("spatialCubeCubesPerStream", 24, 1, 512);
         this.spatialCubeCubesPerStreamMastered = builder.comment("Server hit-detection cubes per stream after mastery.")
            .defineInRange("spatialCubeCubesPerStreamMastered", 28, 1, 512);
         this.spatialCubeCubesPerStreamShifted = builder.comment("Server hit-detection cubes per shifted stream before mastery.")
            .defineInRange("spatialCubeCubesPerStreamShifted", 30, 1, 512);
         this.spatialCubeCubesPerStreamShiftedMastered = builder.comment("Server hit-detection cubes per shifted stream after mastery.")
            .defineInRange("spatialCubeCubesPerStreamShiftedMastered", 34, 1, 512);
         this.spatialCubeVisualCubes = builder.comment("Visual cube count sent to client before mastery. This affects visuals only.")
            .defineInRange("spatialCubeVisualCubes", 950, 1, 100000);
         this.spatialCubeVisualCubesMastered = builder.comment("Visual cube count sent to client after mastery.")
            .defineInRange("spatialCubeVisualCubesMastered", 1250, 1, 100000);
         this.spatialCubeVisualCubesShifted = builder.comment("Visual cube count for shifted barrage before mastery.")
            .defineInRange("spatialCubeVisualCubesShifted", 1550, 1, 100000);
         this.spatialCubeVisualCubesShiftedMastered = builder.comment("Visual cube count for shifted barrage after mastery.")
            .defineInRange("spatialCubeVisualCubesShiftedMastered", 1900, 1, 100000);
         builder.pop();
         builder.comment("Spatial Cube Barrage hitbox size variance.");
         builder.push("spatial_cube_barrage_hitboxes");
         this.spatialCubeNormalBaseHitRadius = builder.comment("Base cube hit radius in normal barrage.")
            .defineInRange("spatialCubeNormalBaseHitRadius", 0.38, 0.0, 64.0);
         this.spatialCubeShiftedBaseHitRadius = builder.comment("Base cube hit radius in shifted barrage.")
            .defineInRange("spatialCubeShiftedBaseHitRadius", 0.44, 0.0, 64.0);
         this.spatialCubeMasteredHitRadiusBonus = builder.comment("Extra cube hit radius after mastery.")
            .defineInRange("spatialCubeMasteredHitRadiusBonus", 0.07, 0.0, 64.0);
         this.spatialCubeVariance0 = builder.comment("Hitbox variance for pattern slot 0.").defineInRange("spatialCubeVariance0", 0.24, 0.0, 64.0);
         this.spatialCubeVariance1 = builder.comment("Hitbox variance for pattern slot 1.").defineInRange("spatialCubeVariance1", 0.18, 0.0, 64.0);
         this.spatialCubeVariance2 = builder.comment("Hitbox variance for pattern slot 2.").defineInRange("spatialCubeVariance2", 0.1, 0.0, 64.0);
         this.spatialCubeVariance4 = builder.comment("Hitbox variance for pattern slot 4.").defineInRange("spatialCubeVariance4", 0.2, 0.0, 64.0);
         builder.pop();
         builder.comment("Spatial Cube Barrage damage values.");
         builder.push("spatial_cube_barrage_damage");
         this.spatialCubeDamage = builder.comment("Base damage before mastery.").defineInRange("spatialCubeDamage", 2800.0, 0.0, Float.MAX_VALUE);
         this.spatialCubeDamageMastered = builder.comment("Base damage after mastery.")
            .defineInRange("spatialCubeDamageMastered", 4200.0, 0.0, Float.MAX_VALUE);
         this.spatialCubeShiftDamageBonus = builder.comment("Flat bonus damage added when shift-firing.")
            .defineInRange("spatialCubeShiftDamageBonus", 3600.0, 0.0, Float.MAX_VALUE);
         this.spatialCubeNormalStreamDamage = builder.comment("Damage added per stream in normal barrage.")
            .defineInRange("spatialCubeNormalStreamDamage", 620.0, 0.0, Float.MAX_VALUE);
         this.spatialCubeShiftedStreamDamage = builder.comment("Damage added per stream in shifted barrage.")
            .defineInRange("spatialCubeShiftedStreamDamage", 850.0, 0.0, Float.MAX_VALUE);
         this.spatialCubeCubeDamage = builder.comment("Damage added per cube per stream before mastery.")
            .defineInRange("spatialCubeCubeDamage", 85.0, 0.0, Float.MAX_VALUE);
         this.spatialCubeCubeDamageMastered = builder.comment("Damage added per cube per stream after mastery.")
            .defineInRange("spatialCubeCubeDamageMastered", 120.0, 0.0, Float.MAX_VALUE);
         this.spatialCubeNormalMobMaxHealthDamage = builder.comment("Normal barrage bonus damage to mobs based on max health. 0.45 = 45 percent.")
            .defineInRange("spatialCubeNormalMobMaxHealthDamage", 0.45, 0.0, 100.0);
         this.spatialCubeShiftedMobMaxHealthDamage = builder.comment("Shifted barrage bonus damage to mobs based on max health.")
            .defineInRange("spatialCubeShiftedMobMaxHealthDamage", 0.7, 0.0, 100.0);
         this.spatialCubeNormalPlayerMaxHealthDamage = builder.comment("Normal barrage bonus damage to players based on max health.")
            .defineInRange("spatialCubeNormalPlayerMaxHealthDamage", 0.14, 0.0, 100.0);
         this.spatialCubeShiftedPlayerMaxHealthDamage = builder.comment("Shifted barrage bonus damage to players based on max health.")
            .defineInRange("spatialCubeShiftedPlayerMaxHealthDamage", 0.22, 0.0, 100.0);
         this.spatialCubeMasteredDamageMultiplier = builder.comment("Final multiplier after mastery.")
            .defineInRange("spatialCubeMasteredDamageMultiplier", 1.65, 0.0, 100.0);
         this.spatialCubeShiftedDamageMultiplier = builder.comment("Final multiplier when shift-firing.")
            .defineInRange("spatialCubeShiftedDamageMultiplier", 1.85, 0.0, 100.0);
         this.spatialCubePlayerDamageMultiplier = builder.comment("Final damage multiplier against players. 0.82 = players take 82 percent.")
            .defineInRange("spatialCubePlayerDamageMultiplier", 0.82, 0.0, 100.0);
         this.spatialCubeFallbackDamageMultiplier = builder.comment("Fallback mob-attack damage multiplier if space damage fails.")
            .defineInRange("spatialCubeFallbackDamageMultiplier", 0.7, 0.0, 100.0);
         this.spatialCubeForcedPlayerDamageMultiplier = builder.comment("Direct health damage multiplier if damage is fully resisted by a player.")
            .defineInRange("spatialCubeForcedPlayerDamageMultiplier", 0.55, 0.0, 100.0);
         this.spatialCubeForcedMobDamageMultiplier = builder.comment("Direct health damage multiplier if damage is fully resisted by a mob.")
            .defineInRange("spatialCubeForcedMobDamageMultiplier", 0.95, 0.0, 100.0);
         builder.pop();
         builder.comment("Spatial Cube Barrage debuffs.");
         builder.push("spatial_cube_barrage_debuffs");
         this.spatialCubeSpatialBlockadeTicks = builder.comment("Spatial Blockade duration before mastery.")
            .defineInRange("spatialCubeSpatialBlockadeTicks", 120, 1, Integer.MAX_VALUE);
         this.spatialCubeSpatialBlockadeTicksMastered = builder.comment("Spatial Blockade duration after mastery.")
            .defineInRange("spatialCubeSpatialBlockadeTicksMastered", 180, 1, Integer.MAX_VALUE);
         this.spatialCubeSpatialBlockadeAmplifier = builder.comment("Spatial Blockade amplifier for normal barrage. 0 means level 1.")
            .defineInRange("spatialCubeSpatialBlockadeAmplifier", 3, 0, 255);
         this.spatialCubeSpatialBlockadeAmplifierShifted = builder.comment("Spatial Blockade amplifier for shifted barrage.")
            .defineInRange("spatialCubeSpatialBlockadeAmplifierShifted", 6, 0, 255);
         this.spatialCubeMagicInterferenceTicks = builder.comment("Magic Interference duration before mastery.")
            .defineInRange("spatialCubeMagicInterferenceTicks", 100, 1, Integer.MAX_VALUE);
         this.spatialCubeMagicInterferenceTicksMastered = builder.comment("Magic Interference duration after mastery.")
            .defineInRange("spatialCubeMagicInterferenceTicksMastered", 160, 1, Integer.MAX_VALUE);
         this.spatialCubeMagicInterferenceAmplifier = builder.comment("Magic Interference amplifier for normal barrage.")
            .defineInRange("spatialCubeMagicInterferenceAmplifier", 2, 0, 255);
         this.spatialCubeMagicInterferenceAmplifierShifted = builder.comment("Magic Interference amplifier for shifted barrage.")
            .defineInRange("spatialCubeMagicInterferenceAmplifierShifted", 4, 0, 255);
         this.spatialCubeMovementInterferenceTicks = builder.comment("Movement Interference duration before mastery.")
            .defineInRange("spatialCubeMovementInterferenceTicks", 90, 1, Integer.MAX_VALUE);
         this.spatialCubeMovementInterferenceTicksMastered = builder.comment("Movement Interference duration after mastery.")
            .defineInRange("spatialCubeMovementInterferenceTicksMastered", 140, 1, Integer.MAX_VALUE);
         this.spatialCubeMovementInterferenceAmplifier = builder.comment("Movement Interference amplifier for normal barrage.")
            .defineInRange("spatialCubeMovementInterferenceAmplifier", 2, 0, 255);
         this.spatialCubeMovementInterferenceAmplifierShifted = builder.comment("Movement Interference amplifier for shifted barrage.")
            .defineInRange("spatialCubeMovementInterferenceAmplifierShifted", 4, 0, 255);
         builder.pop();
         builder.comment("Spatial Cube Barrage stream shape and curve values.");
         builder.push("spatial_cube_barrage_shape");
         this.spatialCubeNormalSideSpread = builder.comment("Side spread for normal stream formation.")
            .defineInRange("spatialCubeNormalSideSpread", 6.2, 0.0, 256.0);
         this.spatialCubeShiftedSideSpread = builder.comment("Side spread for shifted stream formation.")
            .defineInRange("spatialCubeShiftedSideSpread", 10.4, 0.0, 256.0);
         this.spatialCubeNormalBackOffset = builder.comment("How far behind origin normal streams begin.")
            .defineInRange("spatialCubeNormalBackOffset", 7.8, 0.0, 256.0);
         this.spatialCubeShiftedBackOffset = builder.comment("How far behind origin shifted streams begin.")
            .defineInRange("spatialCubeShiftedBackOffset", 10.8, 0.0, 256.0);
         this.spatialCubeStepBackOffset = builder.comment("Tiny per-step backward offset that spaces server cubes.")
            .defineInRange("spatialCubeStepBackOffset", 0.01, 0.0, 16.0);
         this.spatialCubeNormalCurveLift = builder.comment("Arc lift for normal barrage.").defineInRange("spatialCubeNormalCurveLift", 2.0, 0.0, 256.0);
         this.spatialCubeShiftedCurveLift = builder.comment("Arc lift for shifted barrage.").defineInRange("spatialCubeShiftedCurveLift", 3.0, 0.0, 256.0);
         this.spatialCubeNormalCurveSideMultiplier = builder.comment("Side curve multiplier for normal barrage.")
            .defineInRange("spatialCubeNormalCurveSideMultiplier", 0.13, -64.0, 64.0);
         this.spatialCubeShiftedCurveSideMultiplier = builder.comment("Side curve multiplier for shifted barrage.")
            .defineInRange("spatialCubeShiftedCurveSideMultiplier", 0.18, -64.0, 64.0);
         this.spatialCubeNormalTargetSideMultiplier = builder.comment("How much normal streams tighten sideways at the target.")
            .defineInRange("spatialCubeNormalTargetSideMultiplier", 0.035, -64.0, 64.0);
         this.spatialCubeShiftedTargetSideMultiplier = builder.comment("How much shifted streams tighten sideways at the target.")
            .defineInRange("spatialCubeShiftedTargetSideMultiplier", 0.035, -64.0, 64.0);
         this.spatialCubeNormalTargetHeightMultiplier = builder.comment("How much normal stream height remains at the target.")
            .defineInRange("spatialCubeNormalTargetHeightMultiplier", 0.06, -64.0, 64.0);
         this.spatialCubeShiftedTargetHeightMultiplier = builder.comment("How much shifted stream height remains at the target.")
            .defineInRange("spatialCubeShiftedTargetHeightMultiplier", 0.06, -64.0, 64.0);
         this.spatialCubeNormalJitter = builder.comment("Side jitter for normal cubes.").defineInRange("spatialCubeNormalJitter", 0.085, 0.0, 16.0);
         this.spatialCubeShiftedJitter = builder.comment("Side jitter for shifted cubes.").defineInRange("spatialCubeShiftedJitter", 0.13, 0.0, 16.0);
         this.spatialCubeNormalUpJitter = builder.comment("Vertical jitter for normal cubes.").defineInRange("spatialCubeNormalUpJitter", 0.08, 0.0, 16.0);
         this.spatialCubeShiftedUpJitter = builder.comment("Vertical jitter for shifted cubes.").defineInRange("spatialCubeShiftedUpJitter", 0.13, 0.0, 16.0);
         this.spatialCubeNormalForwardJitter = builder.comment("Forward jitter for normal cubes.")
            .defineInRange("spatialCubeNormalForwardJitter", 0.11, 0.0, 16.0);
         this.spatialCubeShiftedForwardJitter = builder.comment("Forward jitter for shifted cubes.")
            .defineInRange("spatialCubeShiftedForwardJitter", 0.18, 0.0, 16.0);
         this.spatialCubeNormalSmoothMultiplier = builder.comment("Final progress multiplier after smoothing for normal barrage.")
            .defineInRange("spatialCubeNormalSmoothMultiplier", 1.18, 0.0, 16.0);
         this.spatialCubeShiftedSmoothMultiplier = builder.comment("Final progress multiplier after smoothing for shifted barrage.")
            .defineInRange("spatialCubeShiftedSmoothMultiplier", 1.18, 0.0, 16.0);
         builder.pop();
         builder.comment("Spatial Cube Barrage stream height pattern.");
         builder.push("spatial_cube_barrage_heights");
         this.spatialCubeHeight0 = builder.defineInRange("spatialCubeHeight0", 1.65, -128.0, 128.0);
         this.spatialCubeHeight1 = builder.defineInRange("spatialCubeHeight1", 0.4, -128.0, 128.0);
         this.spatialCubeHeight2 = builder.defineInRange("spatialCubeHeight2", 2.25, -128.0, 128.0);
         this.spatialCubeHeight3 = builder.defineInRange("spatialCubeHeight3", 0.0, -128.0, 128.0);
         this.spatialCubeHeight0Shifted = builder.defineInRange("spatialCubeHeight0Shifted", 2.25, -128.0, 128.0);
         this.spatialCubeHeight1Shifted = builder.defineInRange("spatialCubeHeight1Shifted", 0.65, -128.0, 128.0);
         this.spatialCubeHeight2Shifted = builder.defineInRange("spatialCubeHeight2Shifted", 3.1, -128.0, 128.0);
         this.spatialCubeHeight3Shifted = builder.defineInRange("spatialCubeHeight3Shifted", -0.05, -128.0, 128.0);
         builder.pop();
      }
   }

   public static final class AuthorityOfGreedConfig {
      public final IntValue holdArmTicks;
      public final IntValue preventionFreeTicks;
      public final IntValue penaltyIntervalTicks;
      public final IntValue baseCooldownTicks;
      public final IntValue heartRange;
      public final IntValue heartRangeMastered;
      public final IntValue penaltyDurationTicks;
      public final IntValue passiveRepairIntervalTicks;
      public final IntValue masteryHoldTick;
      public final DoubleValue lawDegradation;
      public final DoubleValue resistanceDegradation;
      public final DoubleValue resistanceDegradationMastered;
      public final DoubleValue lionheartMagiculeLossPercent;
      public final DoubleValue lionheartMaxHealthPenaltyPercent;
      public final DoubleValue lionheartMinHpPenalty;

      public AuthorityOfGreedConfig(Builder builder) {
         builder.push("Lionheart");
         this.holdArmTicks = builder.comment("Ticks the player must hold before Lionheart actually arms. 20 ticks = 1 second.")
            .defineInRange("holdArmTicks", 20, 0, 72000);
         this.preventionFreeTicks = builder.comment("Safe Lionheart duration before penalties begin. Default 100 ticks = 5 seconds.")
            .defineInRange("preventionFreeTicks", 100, 0, 72000);
         this.penaltyIntervalTicks = builder.comment("How often Lionheart applies greed punishment after the safe window.")
            .defineInRange("penaltyIntervalTicks", 100, 1, 72000);
         this.baseCooldownTicks = builder.comment("Base Lionheart cooldown after release. Extra cooldown is added based on held seconds past the free window.")
            .defineInRange("baseCooldownTicks", 10, 0, 72000);
         this.penaltyDurationTicks = builder.comment("How long Lionheart max-health penalty lasts. Default 6000 ticks = 5 minutes.")
            .defineInRange("penaltyDurationTicks", 6000, 0, 1728000);
         this.lionheartMagiculeLossPercent = builder.comment("Percent of max magicule lost whenever Lionheart punishment triggers. 0.20 = 20%.")
            .defineInRange("lionheartMagiculeLossPercent", 0.2, 0.0, 1.0);
         this.lionheartMaxHealthPenaltyPercent = builder.comment("Percent of max HP removed whenever Lionheart punishment triggers. 0.20 = 20%.")
            .defineInRange("lionheartMaxHealthPenaltyPercent", 0.2, 0.0, 1.0);
         this.lionheartMinHpPenalty = builder.comment("Minimum flat max HP removed whenever Lionheart punishment triggers.")
            .defineInRange("lionheartMinHpPenalty", 1.0, 0.0, 1000000.0);
         builder.pop();
         builder.push("Little King");
         this.heartRange = builder.comment("Maximum Little King heart-link range before mastery.").defineInRange("heartRange", 30, 1, 1024);
         this.heartRangeMastered = builder.comment("Maximum Little King heart-link range after mastery.").defineInRange("heartRangeMastered", 70, 1, 2048);
         builder.pop();
         builder.push("Passive");
         this.passiveRepairIntervalTicks = builder.comment("How often Greed clears severance and Cook HP damage while ticking. Default 100 ticks = 5 seconds.")
            .defineInRange("passiveRepairIntervalTicks", 100, 1, 72000);
         this.masteryHoldTick = builder.comment("How often Lionheart and Little King grant mastery while active.")
            .defineInRange("masteryHoldTick", 20, 1, 72000);
         this.lawDegradation = builder.comment("LAW_DEGRADATION granted by Authority of Greed.").defineInRange("lawDegradation", 1.0, 0.0, 100000.0);
         this.resistanceDegradation = builder.comment("RESISTANCE_DEGRADATION granted before mastery.")
            .defineInRange("resistanceDegradation", 1.0, 0.0, 100000.0);
         this.resistanceDegradationMastered = builder.comment("RESISTANCE_DEGRADATION granted after mastery.")
            .defineInRange("resistanceDegradationMastered", 2.0, 0.0, 100000.0);
         builder.pop();
      }
   }

   public static final class AvalonConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue maxMastery;
      public final IntValue requiredRaidWins;
      public final DoubleValue kingdomCost;
      public final IntValue kingdomDurationTicks;
      public final DoubleValue kingdomTargetRange;
      public final IntValue kingdomCooldown;
      public final DoubleValue royalLawCost;
      public final DoubleValue royalLawRadius;
      public final DoubleValue sovereignJudgementCost;
      public final DoubleValue sovereignJudgementDamage;
      public final DoubleValue sovereignJudgementRange;
      public final DoubleValue heavensFallCost;
      public final DoubleValue heavensFallDamage;
      public final DoubleValue heavensFallRadius;
      public final DoubleValue sanctuaryCost;
      public final DoubleValue sanctuaryRadius;
      public final IntValue sanctuaryDurationTicks;
      public final DoubleValue sanctuaryDamage;
      public final DoubleValue divineDisintegrationCost;
      public final DoubleValue divineDisintegrationDamage;
      public final DoubleValue divineDisintegrationRadius;
      public final IntValue divineDisintegrationChargeTicks;
      public final IntValue divineDisintegrationCooldown;
      public final DoubleValue eternalVictoryCost;
      public final DoubleValue eternalVictoryBaseDamage;
      public final DoubleValue eternalVictorySoulScale;
      public final DoubleValue eternalVictoryRange;
      public final IntValue eternalVictoryCooldown;
      public final DoubleValue holyAuraCostPerPulse;
      public final DoubleValue holyAuraDamage;
      public final DoubleValue holyAuraRadius;
      public final DoubleValue roundTableCost;
      public final IntValue roundTableLifetimeTicks;
      public final IntValue roundTableCooldown;

      AvalonConfig(Builder builder) {
         this.acquirementMagiculeCost = builder.defineInRange("acquirement_magicule_cost", 1.0E8, 0.0, Double.MAX_VALUE);
         this.maxMastery = builder.defineInRange("max_mastery", 2000, 1, Integer.MAX_VALUE);
         this.requiredRaidWins = builder.defineInRange("required_raid_wins", 25, 0, Integer.MAX_VALUE);
         this.kingdomCost = builder.defineInRange("kingdom_cost", 50000.0, 0.0, Double.MAX_VALUE);
         this.kingdomDurationTicks = builder.defineInRange("kingdom_duration_ticks", 2400, 100, 24000);
         this.kingdomTargetRange = builder.defineInRange("kingdom_target_range", 48.0, 1.0, 256.0);
         this.kingdomCooldown = builder.defineInRange("kingdom_cooldown_ticks", 2400, 0, Integer.MAX_VALUE);
         this.royalLawCost = builder.defineInRange("royal_law_cost", 12000.0, 0.0, Double.MAX_VALUE);
         this.royalLawRadius = builder.defineInRange("royal_law_radius", 60.0, 1.0, 256.0);
         this.sovereignJudgementCost = builder.defineInRange("sovereign_judgement_cost", 10000.0, 0.0, Double.MAX_VALUE);
         this.sovereignJudgementDamage = builder.defineInRange("sovereign_judgement_damage", 1800.0, 0.0, Float.MAX_VALUE);
         this.sovereignJudgementRange = builder.defineInRange("sovereign_judgement_range", 128.0, 1.0, 512.0);
         this.heavensFallCost = builder.defineInRange("heavens_fall_cost", 16000.0, 0.0, Double.MAX_VALUE);
         this.heavensFallDamage = builder.defineInRange("heavens_fall_damage", 950.0, 0.0, Float.MAX_VALUE);
         this.heavensFallRadius = builder.defineInRange("heavens_fall_radius", 54.0, 1.0, 256.0);
         this.sanctuaryCost = builder.defineInRange("sanctuary_cost", 18000.0, 0.0, Double.MAX_VALUE);
         this.sanctuaryRadius = builder.defineInRange("sanctuary_radius", 24.0, 1.0, 128.0);
         this.sanctuaryDurationTicks = builder.defineInRange("sanctuary_duration_ticks", 600, 40, 6000);
         this.sanctuaryDamage = builder.defineInRange("sanctuary_damage", 260.0, 0.0, Float.MAX_VALUE);
         this.divineDisintegrationCost = builder.defineInRange("divine_disintegration_cost", 50000.0, 0.0, Double.MAX_VALUE);
         this.divineDisintegrationDamage = builder.defineInRange("divine_disintegration_damage", 6500.0, 0.0, Float.MAX_VALUE);
         this.divineDisintegrationRadius = builder.defineInRange("divine_disintegration_radius", 30.0, 1.0, 256.0);
         this.divineDisintegrationChargeTicks = builder.defineInRange("divine_disintegration_charge_ticks", 200, 20, 2400);
         this.divineDisintegrationCooldown = builder.defineInRange("divine_disintegration_cooldown_ticks", 1800, 0, Integer.MAX_VALUE);
         this.eternalVictoryCost = builder.defineInRange("eternal_victory_cost", 90000.0, 0.0, Double.MAX_VALUE);
         this.eternalVictoryBaseDamage = builder.defineInRange("eternal_victory_base_damage", 4500.0, 0.0, Float.MAX_VALUE);
         this.eternalVictorySoulScale = builder.defineInRange("eternal_victory_soul_scale", 2.25, 0.0, 100.0);
         this.eternalVictoryRange = builder.defineInRange("eternal_victory_range", 96.0, 1.0, 512.0);
         this.eternalVictoryCooldown = builder.defineInRange("eternal_victory_cooldown_ticks", 2400, 0, Integer.MAX_VALUE);
         this.holyAuraCostPerPulse = builder.defineInRange("holy_aura_cost_per_pulse", 700.0, 0.0, Double.MAX_VALUE);
         this.holyAuraDamage = builder.defineInRange("holy_aura_damage", 420.0, 0.0, Float.MAX_VALUE);
         this.holyAuraRadius = builder.defineInRange("holy_aura_radius", 22.0, 1.0, 128.0);
         this.roundTableCost = builder.defineInRange("round_table_cost", 60000.0, 0.0, Double.MAX_VALUE);
         this.roundTableLifetimeTicks = builder.defineInRange("round_table_lifetime_ticks", 2400, 200, 24000);
         this.roundTableCooldown = builder.defineInRange("round_table_cooldown_ticks", 2400, 0, Integer.MAX_VALUE);
      }
   }

   public static final class AxiomConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final IntValue maxMastery;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final IntValue maxMemory;
      public final IntValue maxMemoryMastered;
      public final DoubleValue rippleScriptCost;
      public final DoubleValue mirrorfieldCost;
      public final DoubleValue truthCost;
      public final DoubleValue planetfallCost;
      public final IntValue rippleScriptCooldownTicks;
      public final IntValue truthCooldownTicks;
      public final IntValue planetfallCooldownTicks;
      public final IntValue planetfallCooldownTicksMastered;
      public final DoubleValue rippleScriptRange;
      public final IntValue rippleScriptDurationTicks;
      public final IntValue rippleScriptDurationTicksMastered;
      public final DoubleValue rippleScriptBonusMultiplier;
      public final DoubleValue rippleScriptBonusMultiplierMastered;
      public final DoubleValue passiveDamageReduction;
      public final IntValue counterCooldownTicks;
      public final IntValue counterCooldownTicksMastered;
      public final DoubleValue counterMultiplier;
      public final DoubleValue counterMultiplierMastered;
      public final BooleanValue counterIgnoresBypassInvulnerability;
      public final BooleanValue projectileReflectionEnabled;
      public final DoubleValue projectileReflectSpeed;
      public final DoubleValue projectileArrowDamageMultiplier;
      public final DoubleValue projectileArrowDamageMultiplierMastered;
      public final DoubleValue mirrorfieldRadius;
      public final IntValue mirrorfieldRefreshTicks;
      public final IntValue mirrorfieldActiveTicks;
      public final DoubleValue mirrorfieldDamage;
      public final DoubleValue mirrorfieldMemoryDamage;
      public final DoubleValue mirrorfieldMemoryDamageMastered;
      public final IntValue mirrorfieldMovementInterferenceAmplifier;
      public final IntValue mirrorfieldMovementInterferenceAmplifierMastered;
      public final IntValue mirrorfieldMovementInterferenceTicks;
      public final IntValue truthConsumeMemories;
      public final IntValue truthConsumeMemoriesMastered;
      public final DoubleValue truthRadius;
      public final DoubleValue truthRadiusMastered;
      public final DoubleValue truthInversionMultiplier;
      public final DoubleValue truthMinDamage;
      public final DoubleValue truthMaxDamage;
      public final DoubleValue truthMaxDamageMastered;
      public final DoubleValue truthFalloffMinimum;
      public final IntValue truthBlindnessTicks;
      public final DoubleValue inversionHealthRatio;
      public final DoubleValue inversionMinimumHealth;
      public final IntValue inversionBarrierTicks;
      public final IntValue inversionBarrierAmplifier;
      public final IntValue inversionRegenTicks;
      public final IntValue inversionRegenAmplifier;
      public final IntValue planetfallAmplifier;
      public final IntValue planetfallBuffTicks;
      public final DoubleValue planetfallRadius;
      public final DoubleValue planetfallRadiusMastered;
      public final DoubleValue planetfallBaseDamage;
      public final DoubleValue planetfallBaseDamageMastered;
      public final DoubleValue planetfallHpPercent;
      public final DoubleValue planetfallHpPercentMastered;
      public final DoubleValue planetfallMaxDamage;
      public final DoubleValue planetfallMaxDamageMastered;
      public final DoubleValue planetfallMinDamage;
      public final BooleanValue planetfallExplosions;
      public final DoubleValue planetfallCenterExplosionPower;
      public final DoubleValue planetfallCenterExplosionPowerMastered;
      public final DoubleValue planetfallRingExplosionPower;
      public final DoubleValue planetfallRingExplosionPowerMastered;
      public final IntValue planetfallBlindnessTicks;
      public final IntValue planetfallBlindnessTicksMastered;
      public final IntValue planetfallMovementTicks;
      public final IntValue planetfallMovementTicksMastered;
      public final IntValue planetfallMovementAmplifier;
      public final IntValue planetfallMovementAmplifierMastered;
      public final IntValue planetfallFragilityTicks;
      public final IntValue planetfallFragilityTicksMastered;
      public final IntValue planetfallFragilityAmplifier;
      public final IntValue planetfallFragilityAmplifierMastered;
      public final DoubleValue planetfallKnockback;
      public final DoubleValue planetfallKnockbackMastered;
      public final DoubleValue planetfallVerticalKnockback;
      public final DoubleValue planetfallVerticalKnockbackMastered;

      AxiomConfig(Builder builder) {
         builder.comment("Obtainment and mastery settings for Axiom, Lord of Reflected Judgment.");
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.comment("Magicule cost required to naturally acquire Axiom.")
            .defineInRange("acquirementMagiculeCost", 500000.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.comment("Starting mastery when Axiom is obtained.").defineInRange("acquirementMastery", 0, -1, Integer.MAX_VALUE);
         this.maxMastery = builder.comment("Maximum mastery for Axiom.").defineInRange("maxMastery", 4500, 1, Integer.MAX_VALUE);
         this.requiredSkillId = builder.comment("Required skill registry ID. Empty string disables the required skill check if the skill code supports it.")
            .define("requiredSkillId", "tensura:reflector");
         this.requiredSkillMustBeMastered = builder.comment("If true, the required skill must be mastered before Axiom can be acquired.")
            .define("requiredSkillMustBeMastered", true);
         builder.pop();
         builder.comment("Reflection memory storage. Axiom records damage and kill events into this memory list.");
         builder.push("memory");
         this.maxMemory = builder.comment("Maximum stored memories before mastery.").defineInRange("maxMemory", 220, 1, 10000);
         this.maxMemoryMastered = builder.comment("Maximum stored memories when mastered.").defineInRange("maxMemoryMastered", 360, 1, 10000);
         builder.pop();
         builder.comment("Magicule costs for each active mode.");
         builder.push("costs");
         this.rippleScriptCost = builder.defineInRange("rippleScriptCost", 90000.0, 0.0, Double.MAX_VALUE);
         this.mirrorfieldCost = builder.defineInRange("mirrorfieldCost", 70000.0, 0.0, Double.MAX_VALUE);
         this.truthCost = builder.defineInRange("truthCost", 500000.0, 0.0, Double.MAX_VALUE);
         this.planetfallCost = builder.defineInRange("planetfallCost", 600000.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.comment("Mode cooldowns. 20 ticks = 1 second.");
         builder.push("cooldowns");
         this.rippleScriptCooldownTicks = builder.defineInRange("rippleScriptCooldownTicks", 6, 0, Integer.MAX_VALUE);
         this.truthCooldownTicks = builder.defineInRange("truthCooldownTicks", 35, 0, Integer.MAX_VALUE);
         this.planetfallCooldownTicks = builder.defineInRange("planetfallCooldownTicks", 180, 0, Integer.MAX_VALUE);
         this.planetfallCooldownTicksMastered = builder.defineInRange("planetfallCooldownTicksMastered", 140, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Ripple Script marks one target. Damage dealt to that target is repeated as bonus reflected judgment damage.");
         builder.push("ripple_script");
         this.rippleScriptRange = builder.defineInRange("rippleScriptRange", 40.0, 1.0, 512.0);
         this.rippleScriptDurationTicks = builder.defineInRange("rippleScriptDurationTicks", 360, 1, Integer.MAX_VALUE);
         this.rippleScriptDurationTicksMastered = builder.defineInRange("rippleScriptDurationTicksMastered", 600, 1, Integer.MAX_VALUE);
         this.rippleScriptBonusMultiplier = builder.comment("Bonus damage multiplier before mastery. 0.95 = 95 percent of dealt damage added again.")
            .defineInRange("rippleScriptBonusMultiplier", 0.95, 0.0, 1024.0);
         this.rippleScriptBonusMultiplierMastered = builder.comment("Bonus damage multiplier when mastered.")
            .defineInRange("rippleScriptBonusMultiplierMastered", 1.25, 0.0, 1024.0);
         builder.pop();
         builder.comment("Passive reflection counter and projectile reflection values.");
         builder.push("passive_counter");
         this.passiveDamageReduction = builder.comment("Damage reduction while Axiom is toggled. 0.40 = 40 percent reduction.")
            .defineInRange("passiveDamageReduction", 0.4, 0.0, 1.0);
         this.counterCooldownTicks = builder.defineInRange("counterCooldownTicks", 5, 0, Integer.MAX_VALUE);
         this.counterCooldownTicksMastered = builder.defineInRange("counterCooldownTicksMastered", 3, 0, Integer.MAX_VALUE);
         this.counterMultiplier = builder.comment("Counter damage multiplier before mastery.").defineInRange("counterMultiplier", 2.5, 0.0, 1024.0);
         this.counterMultiplierMastered = builder.comment("Counter damage multiplier when mastered.")
            .defineInRange("counterMultiplierMastered", 3.0, 0.0, 1024.0);
         this.counterIgnoresBypassInvulnerability = builder.comment(
               "If true, Axiom can counter even bypass-invulnerability damage. The skill code must check this value."
            )
            .define("counterIgnoresBypassInvulnerability", true);
         this.projectileReflectionEnabled = builder.comment("If false, projectile reflection should be disabled in the skill code.")
            .define("projectileReflectionEnabled", true);
         this.projectileReflectSpeed = builder.comment("Speed applied to reflected projectiles.").defineInRange("projectileReflectSpeed", 2.0, 0.0, 64.0);
         this.projectileArrowDamageMultiplier = builder.comment("Arrow damage multiplier when reflected before mastery.")
            .defineInRange("projectileArrowDamageMultiplier", 2.5, 0.0, 1024.0);
         this.projectileArrowDamageMultiplierMastered = builder.comment("Arrow damage multiplier when reflected while mastered.")
            .defineInRange("projectileArrowDamageMultiplierMastered", 3.0, 0.0, 1024.0);
         builder.pop();
         builder.comment("Mirrorfield hold mode. Creates an active reflection field around the user.");
         builder.push("mirrorfield");
         this.mirrorfieldRadius = builder.defineInRange("mirrorfieldRadius", 26.0, 1.0, 512.0);
         this.mirrorfieldRefreshTicks = builder.comment("How often holding Mirrorfield refreshes the active field.")
            .defineInRange("mirrorfieldRefreshTicks", 10, 1, Integer.MAX_VALUE);
         this.mirrorfieldActiveTicks = builder.comment("How long each Mirrorfield refresh keeps the field active.")
            .defineInRange("mirrorfieldActiveTicks", 30, 1, Integer.MAX_VALUE);
         this.mirrorfieldDamage = builder.comment("Direct damage dealt each Mirrorfield tick to enemies in range.")
            .defineInRange("mirrorfieldDamage", 100.0, 0.0, Float.MAX_VALUE);
         this.mirrorfieldMemoryDamage = builder.comment("Memory damage recorded per Mirrorfield hit before mastery.")
            .defineInRange("mirrorfieldMemoryDamage", 35.0, 0.0, Float.MAX_VALUE);
         this.mirrorfieldMemoryDamageMastered = builder.comment("Memory damage recorded per Mirrorfield hit when mastered.")
            .defineInRange("mirrorfieldMemoryDamageMastered", 60.0, 0.0, Float.MAX_VALUE);
         this.mirrorfieldMovementInterferenceAmplifier = builder.defineInRange("mirrorfieldMovementInterferenceAmplifier", 1, 0, 255);
         this.mirrorfieldMovementInterferenceAmplifierMastered = builder.defineInRange("mirrorfieldMovementInterferenceAmplifierMastered", 2, 0, 255);
         this.mirrorfieldMovementInterferenceTicks = builder.defineInRange("mirrorfieldMovementInterferenceTicks", 30, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Truth consumes stored reflection memories and releases them as an area burst.");
         builder.push("truth");
         this.truthConsumeMemories = builder.comment("Maximum memories consumed by Truth before mastery.").defineInRange("truthConsumeMemories", 70, 1, 10000);
         this.truthConsumeMemoriesMastered = builder.comment("Maximum memories consumed by Truth when mastered.")
            .defineInRange("truthConsumeMemoriesMastered", 100, 1, 10000);
         this.truthRadius = builder.defineInRange("truthRadius", 26.0, 1.0, 512.0);
         this.truthRadiusMastered = builder.defineInRange("truthRadiusMastered", 32.0, 1.0, 512.0);
         this.truthInversionMultiplier = builder.comment("Extra multiplier when Truth is triggered by Inversion.")
            .defineInRange("truthInversionMultiplier", 1.35, 0.0, 1024.0);
         this.truthMinDamage = builder.defineInRange("truthMinDamage", 12.0, 0.0, Float.MAX_VALUE);
         this.truthMaxDamage = builder.defineInRange("truthMaxDamage", 3200.0, 0.0, Float.MAX_VALUE);
         this.truthMaxDamageMastered = builder.defineInRange("truthMaxDamageMastered", 5000.0, 0.0, Float.MAX_VALUE);
         this.truthFalloffMinimum = builder.comment("Minimum falloff multiplier at the edge of Truth radius.")
            .defineInRange("truthFalloffMinimum", 0.25, 0.0, 1.0);
         this.truthBlindnessTicks = builder.defineInRange("truthBlindnessTicks", 40, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Inversion prevents death once while mastered and toggled.");
         builder.push("inversion");
         this.inversionHealthRatio = builder.comment("Health restored when Inversion prevents death. 0.5 = 50 percent of max health.")
            .defineInRange("inversionHealthRatio", 0.5, 0.0, 1.0);
         this.inversionMinimumHealth = builder.comment("Minimum health restored by Inversion.")
            .defineInRange("inversionMinimumHealth", 20.0, 0.0, Float.MAX_VALUE);
         this.inversionBarrierTicks = builder.comment("Magic Barrier duration after Inversion triggers.")
            .defineInRange("inversionBarrierTicks", 100, 1, Integer.MAX_VALUE);
         this.inversionBarrierAmplifier = builder.comment("Magic Barrier amplifier after Inversion triggers.")
            .defineInRange("inversionBarrierAmplifier", 3, 0, 255);
         this.inversionRegenTicks = builder.comment("Self Regeneration duration after Inversion triggers.")
            .defineInRange("inversionRegenTicks", 100, 1, Integer.MAX_VALUE);
         this.inversionRegenAmplifier = builder.comment("Self Regeneration amplifier after Inversion triggers.")
            .defineInRange("inversionRegenAmplifier", 3, 0, 255);
         builder.pop();
         builder.comment("Planetfall ultimate burst, buffs, explosions, debuffs, and knockback.");
         builder.push("planetfall");
         this.planetfallAmplifier = builder.comment("Amplifier for Planetfall self buffs.").defineInRange("planetfallAmplifier", 4, 0, 255);
         this.planetfallBuffTicks = builder.defineInRange("planetfallBuffTicks", 200, 1, Integer.MAX_VALUE);
         this.planetfallRadius = builder.defineInRange("planetfallRadius", 60.0, 1.0, 512.0);
         this.planetfallRadiusMastered = builder.defineInRange("planetfallRadiusMastered", 85.0, 1.0, 512.0);
         this.planetfallBaseDamage = builder.defineInRange("planetfallBaseDamage", 420.0, 0.0, Float.MAX_VALUE);
         this.planetfallBaseDamageMastered = builder.defineInRange("planetfallBaseDamageMastered", 720.0, 0.0, Float.MAX_VALUE);
         this.planetfallHpPercent = builder.comment("Extra Planetfall damage from target max health before mastery. 0.45 = 45 percent.")
            .defineInRange("planetfallHpPercent", 0.45, 0.0, 100.0);
         this.planetfallHpPercentMastered = builder.comment("Extra Planetfall damage from target max health when mastered.")
            .defineInRange("planetfallHpPercentMastered", 0.65, 0.0, 100.0);
         this.planetfallMaxDamage = builder.defineInRange("planetfallMaxDamage", 1800.0, 0.0, Float.MAX_VALUE);
         this.planetfallMaxDamageMastered = builder.defineInRange("planetfallMaxDamageMastered", 3200.0, 0.0, Float.MAX_VALUE);
         this.planetfallMinDamage = builder.defineInRange("planetfallMinDamage", 40.0, 0.0, Float.MAX_VALUE);
         this.planetfallExplosions = builder.comment("If false, Planetfall should skip visual/non-grief explosions if the skill code checks this.")
            .define("planetfallExplosions", true);
         this.planetfallCenterExplosionPower = builder.defineInRange("planetfallCenterExplosionPower", 4.5, 0.0, 128.0);
         this.planetfallCenterExplosionPowerMastered = builder.defineInRange("planetfallCenterExplosionPowerMastered", 6.0, 0.0, 128.0);
         this.planetfallRingExplosionPower = builder.defineInRange("planetfallRingExplosionPower", 3.0, 0.0, 128.0);
         this.planetfallRingExplosionPowerMastered = builder.defineInRange("planetfallRingExplosionPowerMastered", 4.0, 0.0, 128.0);
         this.planetfallBlindnessTicks = builder.defineInRange("planetfallBlindnessTicks", 60, 1, Integer.MAX_VALUE);
         this.planetfallBlindnessTicksMastered = builder.defineInRange("planetfallBlindnessTicksMastered", 90, 1, Integer.MAX_VALUE);
         this.planetfallMovementTicks = builder.defineInRange("planetfallMovementTicks", 110, 1, Integer.MAX_VALUE);
         this.planetfallMovementTicksMastered = builder.defineInRange("planetfallMovementTicksMastered", 140, 1, Integer.MAX_VALUE);
         this.planetfallMovementAmplifier = builder.defineInRange("planetfallMovementAmplifier", 3, 0, 255);
         this.planetfallMovementAmplifierMastered = builder.defineInRange("planetfallMovementAmplifierMastered", 4, 0, 255);
         this.planetfallFragilityTicks = builder.defineInRange("planetfallFragilityTicks", 110, 1, Integer.MAX_VALUE);
         this.planetfallFragilityTicksMastered = builder.defineInRange("planetfallFragilityTicksMastered", 140, 1, Integer.MAX_VALUE);
         this.planetfallFragilityAmplifier = builder.defineInRange("planetfallFragilityAmplifier", 2, 0, 255);
         this.planetfallFragilityAmplifierMastered = builder.defineInRange("planetfallFragilityAmplifierMastered", 3, 0, 255);
         this.planetfallKnockback = builder.defineInRange("planetfallKnockback", 0.95, 0.0, 64.0);
         this.planetfallKnockbackMastered = builder.defineInRange("planetfallKnockbackMastered", 1.25, 0.0, 64.0);
         this.planetfallVerticalKnockback = builder.defineInRange("planetfallVerticalKnockback", 0.45, 0.0, 64.0);
         this.planetfallVerticalKnockbackMastered = builder.defineInRange("planetfallVerticalKnockbackMastered", 0.65, 0.0, 64.0);
         builder.pop();
      }
   }

   public static final class BoogieWoogieConfig {
      public final DoubleValue range;
      public final IntValue applyEveryTicks;
      public final IntValue buffDurationTicks;
      public final IntValue maxSelectedTargets;
      public final IntValue strengthAmplifier;
      public final IntValue resistanceAmplifier;
      public final IntValue masteredSpeedAmplifier;
      public final IntValue swapPortalParticles;
      public final IntValue swapEndRodParticles;
      public final DoubleValue portalParticleHorizontalSpread;
      public final DoubleValue portalParticleVerticalSpread;
      public final DoubleValue portalParticleSpeed;
      public final DoubleValue endRodParticleHorizontalSpread;
      public final DoubleValue endRodParticleVerticalSpread;
      public final DoubleValue endRodParticleSpeed;
      public final DoubleValue toggleOnVolume;
      public final DoubleValue toggleOnPitch;
      public final DoubleValue toggleOffVolume;
      public final DoubleValue toggleOffPitch;
      public final DoubleValue swapSoundVolume;
      public final DoubleValue swapSoundPitch;

      public BoogieWoogieConfig(Builder builder) {
         builder.push("Selection");
         this.range = builder.comment("Maximum range for target marking, self swaps, other swaps, and projectile selection.")
            .defineInRange("range", 80.0, 1.0, 2048.0);
         this.maxSelectedTargets = builder.comment("Maximum remembered marked targets. Older targets are removed first.")
            .defineInRange("maxSelectedTargets", 3, 1, 32);
         builder.pop();
         builder.push("Passive Buffs");
         this.applyEveryTicks = builder.comment("How often Boogie Woogie reapplies its toggle buffs and grants mastery. 40 ticks = 2 seconds.")
            .defineInRange("applyEveryTicks", 40, 1, 72000);
         this.buffDurationTicks = builder.comment("Duration of the passive strength/resistance/speed buffs. 1200 ticks = 60 seconds.")
            .defineInRange("buffDurationTicks", 1200, 1, 1728000);
         this.strengthAmplifier = builder.comment("Minecraft Strength amplifier while toggled. 4 means Strength V.")
            .defineInRange("strengthAmplifier", 4, 0, 255);
         this.resistanceAmplifier = builder.comment("Minecraft Resistance amplifier while toggled. 4 means Resistance V.")
            .defineInRange("resistanceAmplifier", 4, 0, 255);
         this.masteredSpeedAmplifier = builder.comment("Speed amplifier after mastery. 1 means Speed II.").defineInRange("masteredSpeedAmplifier", 1, 0, 255);
         builder.pop();
         builder.push("Swap VFX");
         this.swapPortalParticles = builder.comment("Portal particles spawned at each swapped entity.").defineInRange("swapPortalParticles", 18, 0, 10000);
         this.swapEndRodParticles = builder.comment("End rod particles spawned at each swapped entity.").defineInRange("swapEndRodParticles", 6, 0, 10000);
         this.portalParticleHorizontalSpread = builder.comment("Horizontal spread for portal swap particles.")
            .defineInRange("portalParticleHorizontalSpread", 0.3, 0.0, 64.0);
         this.portalParticleVerticalSpread = builder.comment("Vertical spread for portal swap particles.")
            .defineInRange("portalParticleVerticalSpread", 0.4, 0.0, 64.0);
         this.portalParticleSpeed = builder.comment("Speed for portal swap particles.").defineInRange("portalParticleSpeed", 0.02, 0.0, 64.0);
         this.endRodParticleHorizontalSpread = builder.comment("Horizontal spread for end rod swap particles.")
            .defineInRange("endRodParticleHorizontalSpread", 0.2, 0.0, 64.0);
         this.endRodParticleVerticalSpread = builder.comment("Vertical spread for end rod swap particles.")
            .defineInRange("endRodParticleVerticalSpread", 0.25, 0.0, 64.0);
         this.endRodParticleSpeed = builder.comment("Speed for end rod swap particles.").defineInRange("endRodParticleSpeed", 0.02, 0.0, 64.0);
         builder.pop();
         builder.push("Sounds");
         this.toggleOnVolume = builder.comment("Volume when Boogie Woogie toggles on.").defineInRange("toggleOnVolume", 0.8, 0.0, 100.0);
         this.toggleOnPitch = builder.comment("Pitch when Boogie Woogie toggles on.").defineInRange("toggleOnPitch", 1.6, 0.0, 10.0);
         this.toggleOffVolume = builder.comment("Volume when Boogie Woogie toggles off.").defineInRange("toggleOffVolume", 0.8, 0.0, 100.0);
         this.toggleOffPitch = builder.comment("Pitch when Boogie Woogie toggles off.").defineInRange("toggleOffPitch", 0.8, 0.0, 10.0);
         this.swapSoundVolume = builder.comment("Volume played at both swap positions.").defineInRange("swapSoundVolume", 1.0, 0.0, 100.0);
         this.swapSoundPitch = builder.comment("Pitch played at both swap positions.").defineInRange("swapSoundPitch", 1.6, 0.0, 10.0);
         builder.pop();
      }
   }

   public static final class CaedrosGodOfConquest {
      public final IntValue acquirementMastery;
      public final IntValue maxMastery;
      public final IntValue conquestCost;
      public final IntValue plagueCost;
      public final IntValue bangCost;
      public final IntValue cloudCost;
      public final IntValue fangCost;
      public final IntValue constructCost;
      public final DoubleValue guiltOfflineGainPerSecond;
      public final DoubleValue guiltSubordinateBonus;
      public final DoubleValue guiltSkillBonus;
      public final DoubleValue guiltToggleDrainPerSecond;
      public final DoubleValue guiltSubordinateDrain;
      public final DoubleValue guiltSkillDrain;
      public final DoubleValue guiltTierDivisor;
      public final IntValue maxGuiltTier;
      public final IntValue guiltSubordinateRadius;
      public final BooleanValue enableOuroborosSkillEating;
      public final BooleanValue allowOuroborosToKillUser;
      public final IntValue ouroborosCheckIntervalTicks;
      public final DoubleValue ouroborosGuiltUpkeep;
      public final BooleanValue allowEatingUniqueSkills;
      public final BooleanValue allowEatingUltimateSkills;
      public final BooleanValue allowEatingIntrinsicSkills;
      public final BooleanValue allowEatingResistanceSkills;
      public final BooleanValue allowEatingCommonSkills;
      public final BooleanValue allowEatingExtraSkills;
      public final IntValue fearInsanityRadius;
      public final IntValue fearInsanityIntervalTicks;
      public final IntValue fearInsanityDurationTicks;
      public final IntValue fearInsanityMaxAmplifier;
      public final DoubleValue fearDamagePerStack;
      public final DoubleValue fearDamagePerStackMastered;
      public final BooleanValue enableDeathRevive;
      public final IntValue deathReviveSoulPointCost;
      public final IntValue conquestSweepRadius;
      public final DoubleValue conquestSweepDamage;
      public final DoubleValue conquestChargedDamage;
      public final DoubleValue conquestMiniBangDamage;
      public final IntValue conquestChargedRange;
      public final IntValue conquestMiniBangRange;
      public final IntValue conquestMiniBangRadius;
      public final IntValue conquestChargeTicks;
      public final IntValue conquestSummonCooldownTicks;
      public final IntValue conquestMiniBangCooldownTicks;
      public final IntValue conquestSweepCooldownTicks;
      public final IntValue conquestReleaseCooldownTicks;
      public final DoubleValue plagueBaseDamage;
      public final DoubleValue plagueMasteredDamage;
      public final IntValue plagueCooldownTicks;
      public final IntValue plagueSlashes;
      public final IntValue plagueSlashesMastered;
      public final DoubleValue plagueReach;
      public final DoubleValue plagueReachMastered;
      public final DoubleValue plagueWidth;
      public final DoubleValue plagueWidthMastered;
      public final IntValue plagueDebuffPicks;
      public final IntValue plagueDebuffPicksMastered;
      public final IntValue plagueDebuffDurationTicks;
      public final IntValue plagueDebuffDurationMasteredTicks;
      public final IntValue plagueDebuffAmplifier;
      public final IntValue plagueDebuffAmplifierMastered;
      public final IntValue bangWindupTicks;
      public final IntValue bangCooldownTicks;
      public final IntValue bangRange;
      public final IntValue bangRadius;
      public final DoubleValue bangDamage;
      public final DoubleValue bangGuiltCost;
      public final BooleanValue bangCanDestroyBlocks;
      public final BooleanValue bangCanDestroyBedrock;
      public final IntValue bangRepeatFireLockoutTicks;
      public final IntValue totalWarBangRange;
      public final IntValue totalWarBangRadius;
      public final DoubleValue totalWarBangDamage;
      public final BooleanValue totalWarBangCanDestroyBedrock;
      public final IntValue cloudWindupTicks;
      public final IntValue cloudDurationTicks;
      public final IntValue cloudCooldownTicks;
      public final IntValue edenLeashRange;
      public final DoubleValue edenWorldBorderSize;
      public final DoubleValue cloudOwnerAttackMultiplier;
      public final DoubleValue cloudOwnerHealthMultiplier;
      public final BooleanValue cloudTeleportsAllPlayers;
      public final BooleanValue cloudStripsCaedrosIfOwnerDies;
      public final BooleanValue cloudRespawnsVictimsInsidePaths;
      public final DoubleValue constructDamage;
      public final IntValue constructBaseRange;
      public final IntValue constructMasterRange;
      public final IntValue constructMaxSwords;
      public final IntValue constructSwordUses;
      public final IntValue constructAttackIntervalTicks;
      public final DoubleValue constructSwordBreakGuilt;
      public final IntValue constructCooldownTicks;
      public final DoubleValue constructSpiritBaseDamage;
      public final DoubleValue constructSpiritMasteredDamage;
      public final IntValue fangCooldownTicks;
      public final IntValue fangRadius;
      public final IntValue fangFearDurationTicks;
      public final IntValue fangInsanityDurationTicks;
      public final IntValue fangBurdenDurationTicks;
      public final IntValue fangMagicInterferenceDurationTicks;
      public final IntValue fangSkillDisableAmount;
      public final IntValue fangSkillDisableAmountMastered;
      public final BooleanValue fangCanDisableUniqueSkills;
      public final BooleanValue fangCanDisableUltimateSkills;
      public final BooleanValue fangCanPermanentlyDeleteSkills;
      public final BooleanValue controlEnabled;
      public final DoubleValue controlEpThresholdRatio;
      public final BooleanValue controlForcesSpectator;
      public final BooleanValue controlCanDisableLockedSkill;
      public final BooleanValue controlCanUnslotLockedSkill;
      public final BooleanValue controlCanPermanentlyDeleteLockedSkill;
      public final BooleanValue famineEnabled;
      public final DoubleValue famineRadius;
      public final BooleanValue famineStarvesPlayers;
      public final IntValue famineDebuffsPerStack;
      public final IntValue famineDurationTicks;
      public final IntValue famineBurstDurationTicks;
      public final IntValue famineMaxAmplifier;
      public final IntValue famineMaxSecondaryAmplifier;
      public final IntValue famineDisableSkillsOnHit;
      public final BooleanValue famineCanDisableUniqueSkills;
      public final BooleanValue famineCanDisableUltimateSkills;
      public final BooleanValue famineCanPermanentlyDeleteSkillsOnHit;
      public final BooleanValue warEnabled;
      public final DoubleValue warConquerThresholdRatio;
      public final DoubleValue warTempEpGainRatio;
      public final DoubleValue warDamageEpGainCapMultiplier;
      public final DoubleValue warMagiculeDamageRatio;
      public final DoubleValue warMagiculeDamageDivisor;
      public final BooleanValue deathEnabled;
      public final DoubleValue deathReplicaFollowRadius;
      public final DoubleValue deathReplicaTeleportDistanceSqr;
      public final DoubleValue deathSoulDamageDivisor;
      public final DoubleValue toggleAttackBase;
      public final DoubleValue toggleAttackPerTier;
      public final DoubleValue toggleAttackSpeedBase;
      public final DoubleValue toggleAttackSpeedPerTier;
      public final DoubleValue toggleKnockbackBase;
      public final DoubleValue toggleKnockbackPerTier;
      public final DoubleValue toggleKnockbackCap;
      public final DoubleValue toggleDodgeBase;
      public final DoubleValue toggleDodgePerTier;
      public final DoubleValue toggleDodgeCap;
      public final DoubleValue toggleEnergyGainBase;
      public final DoubleValue toggleEnergyGainPerTier;
      public final IntValue toggleBuffDurationTicks;
      public final IntValue toggleGuardedMaxAmplifier;
      public final IntValue toggleHakiMaxAmplifier;
      public final IntValue toggleStrengthenMaxAmplifier;
      public final IntValue toggleInspirationMaxAmplifier;
      public final IntValue toggleMagicAuraMaxAmplifier;
      public final IntValue toggleSelfRegenMaxAmplifier;
      public final DoubleValue finalDamageGuiltPerTier;
      public final DoubleValue finalDamageWarCap;
      public final DoubleValue trueDamageMinimum;
      public final DoubleValue reconstructEpCost;
      public final DoubleValue reconstructBaseGuiltCost;
      public final DoubleValue reconstructGuiltDivisor;
      public final DoubleValue reconstructGuiltDivisorMastered;

      CaedrosGodOfConquest(Builder builder) {
         builder.comment("Obtainment and mastery settings for Caedros, God of Conquest.");
         builder.push("obtainment");
         this.acquirementMastery = builder.comment("Starting mastery when Caedros is obtained.").defineInRange("acquirementMastery", 0, -1, Integer.MAX_VALUE);
         this.maxMastery = builder.comment("Maximum mastery for Caedros.").defineInRange("maxMastery", 6000, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Magicule costs for each active mode.");
         builder.push("costs");
         this.conquestCost = builder.defineInRange("conquestCost", 0, 0, Integer.MAX_VALUE);
         this.plagueCost = builder.defineInRange("plagueCost", 0, 0, Integer.MAX_VALUE);
         this.bangCost = builder.defineInRange("bangCost", 0, 0, Integer.MAX_VALUE);
         this.cloudCost = builder.defineInRange("cloudCost", 0, 0, Integer.MAX_VALUE);
         this.fangCost = builder.defineInRange("fangCost", 0, 0, Integer.MAX_VALUE);
         this.constructCost = builder.defineInRange("constructCost", 0, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Guilt economy. Passive gain applies while Caedros is not toggled. Drain applies once per second while toggled.");
         builder.push("guilt");
         this.guiltOfflineGainPerSecond = builder.defineInRange("guiltOfflineGainPerSecond", 30.0, 0.0, Double.MAX_VALUE);
         this.guiltSubordinateBonus = builder.defineInRange("guiltSubordinateBonus", 15.0, 0.0, Double.MAX_VALUE);
         this.guiltSkillBonus = builder.defineInRange("guiltSkillBonus", 12.0, 0.0, Double.MAX_VALUE);
         this.guiltToggleDrainPerSecond = builder.defineInRange("guiltToggleDrainPerSecond", 25.0, 0.0, Double.MAX_VALUE);
         this.guiltSubordinateDrain = builder.defineInRange("guiltSubordinateDrain", 8.0, 0.0, Double.MAX_VALUE);
         this.guiltSkillDrain = builder.defineInRange("guiltSkillDrain", 4.0, 0.0, Double.MAX_VALUE);
         this.guiltTierDivisor = builder.comment("Guilt required per tier. 10000 = one tier every 10k guilt.")
            .defineInRange("guiltTierDivisor", 10000.0, 1.0, Double.MAX_VALUE);
         this.maxGuiltTier = builder.defineInRange("maxGuiltTier", 10, 0, 1000);
         this.guiltSubordinateRadius = builder.comment("Radius used to count subordinate-based guilt gain/drain.")
            .defineInRange("guiltSubordinateRadius", 50, 0, 512);
         builder.pop();
         builder.comment("Ouroboros self-skill-eating safety switches.");
         builder.push("ouroboros");
         this.enableOuroborosSkillEating = builder.comment("If false, Caedros will never eat the user's skills from guilt starvation.")
            .define("enableOuroborosSkillEating", true);
         this.allowOuroborosToKillUser = builder.comment("If true, the user dies when Ouroboros cannot eat another skill.")
            .define("allowOuroborosToKillUser", true);
         this.ouroborosCheckIntervalTicks = builder.defineInRange("ouroborosCheckIntervalTicks", 1200, 1, Integer.MAX_VALUE);
         this.ouroborosGuiltUpkeep = builder.defineInRange("ouroborosGuiltUpkeep", 1.0, 0.0, Double.MAX_VALUE);
         this.allowEatingUniqueSkills = builder.define("allowEatingUniqueSkills", true);
         this.allowEatingUltimateSkills = builder.define("allowEatingUltimateSkills", true);
         this.allowEatingIntrinsicSkills = builder.define("allowEatingIntrinsicSkills", true);
         this.allowEatingResistanceSkills = builder.define("allowEatingResistanceSkills", true);
         this.allowEatingCommonSkills = builder.define("allowEatingCommonSkills", true);
         this.allowEatingExtraSkills = builder.define("allowEatingExtraSkills", true);
         builder.pop();
         builder.comment("Permanent skill deletion safety switches. Keep these false unless you want Caedros to permanently delete other players' skills.");
         builder.push("skill_deletion_safety");
         this.controlCanPermanentlyDeleteLockedSkill = builder.comment(
               "If true, CONTROL can permanently delete the selected locked skill. If false, it only disables/unslots it."
            )
            .define("controlCanPermanentlyDeleteLockedSkill", false);
         this.famineCanPermanentlyDeleteSkillsOnHit = builder.comment(
               "If true, FAMINE can permanently delete random Unique/Ultimate skills on hit. If false, it only disables them."
            )
            .define("famineCanPermanentlyDeleteSkillsOnHit", false);
         this.fangCanPermanentlyDeleteSkills = builder.comment(
               "If true, Fang and Serpent can permanently delete random Unique/Ultimate skills. If false, it only disables them."
            )
            .define("fangCanPermanentlyDeleteSkills", false);
         builder.pop();
         builder.comment("Fear and insanity aura values.");
         builder.push("fear_aura");
         this.fearInsanityRadius = builder.defineInRange("fearInsanityRadius", 25, 0, 512);
         this.fearInsanityIntervalTicks = builder.defineInRange("fearInsanityIntervalTicks", 70, 1, Integer.MAX_VALUE);
         this.fearInsanityDurationTicks = builder.defineInRange("fearInsanityDurationTicks", 140, 1, Integer.MAX_VALUE);
         this.fearInsanityMaxAmplifier = builder.defineInRange("fearInsanityMaxAmplifier", 9, 0, 255);
         this.fearDamagePerStack = builder.defineInRange("fearDamagePerStack", 0.05, 0.0, 100.0);
         this.fearDamagePerStackMastered = builder.defineInRange("fearDamagePerStackMastered", 0.2, 0.0, 100.0);
         builder.pop();
         builder.comment("Death aspect revive settings.");
         builder.push("death_revive");
         this.enableDeathRevive = builder.define("enableDeathRevive", true);
         this.deathReviveSoulPointCost = builder.defineInRange("deathReviveSoulPointCost", 10000, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Conquest halberd chain values.");
         builder.push("conquest");
         this.conquestSweepRadius = builder.defineInRange("conquestSweepRadius", 10, 0, 256);
         this.conquestSweepDamage = builder.defineInRange("conquestSweepDamage", 3200.0, 0.0, Float.MAX_VALUE);
         this.conquestChargedDamage = builder.defineInRange("conquestChargedDamage", 38000.0, 0.0, Float.MAX_VALUE);
         this.conquestMiniBangDamage = builder.defineInRange("conquestMiniBangDamage", 5000.0, 0.0, Float.MAX_VALUE);
         this.conquestChargedRange = builder.defineInRange("conquestChargedRange", 50, 1, 512);
         this.conquestMiniBangRange = builder.defineInRange("conquestMiniBangRange", 28, 1, 512);
         this.conquestMiniBangRadius = builder.defineInRange("conquestMiniBangRadius", 3, 0, 128);
         this.conquestChargeTicks = builder.defineInRange("conquestChargeTicks", 100, 1, Integer.MAX_VALUE);
         this.conquestSummonCooldownTicks = builder.defineInRange("conquestSummonCooldownTicks", 8, 0, Integer.MAX_VALUE);
         this.conquestMiniBangCooldownTicks = builder.defineInRange("conquestMiniBangCooldownTicks", 16, 0, Integer.MAX_VALUE);
         this.conquestSweepCooldownTicks = builder.defineInRange("conquestSweepCooldownTicks", 10, 0, Integer.MAX_VALUE);
         this.conquestReleaseCooldownTicks = builder.defineInRange("conquestReleaseCooldownTicks", 20, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Plague slash and random debuff values.");
         builder.push("plague");
         this.plagueBaseDamage = builder.defineInRange("plagueBaseDamage", 3500.0, 0.0, Float.MAX_VALUE);
         this.plagueMasteredDamage = builder.defineInRange("plagueMasteredDamage", 9000.0, 0.0, Float.MAX_VALUE);
         this.plagueCooldownTicks = builder.defineInRange("plagueCooldownTicks", 28, 0, Integer.MAX_VALUE);
         this.plagueSlashes = builder.defineInRange("plagueSlashes", 6, 1, 128);
         this.plagueSlashesMastered = builder.defineInRange("plagueSlashesMastered", 8, 1, 128);
         this.plagueReach = builder.defineInRange("plagueReach", 18.0, 1.0, 512.0);
         this.plagueReachMastered = builder.defineInRange("plagueReachMastered", 24.0, 1.0, 512.0);
         this.plagueWidth = builder.defineInRange("plagueWidth", 4.25, 0.0, 128.0);
         this.plagueWidthMastered = builder.defineInRange("plagueWidthMastered", 5.5, 0.0, 128.0);
         this.plagueDebuffPicks = builder.defineInRange("plagueDebuffPicks", 3, 0, 64);
         this.plagueDebuffPicksMastered = builder.defineInRange("plagueDebuffPicksMastered", 4, 0, 64);
         this.plagueDebuffDurationTicks = builder.defineInRange("plagueDebuffDurationTicks", 140, 1, Integer.MAX_VALUE);
         this.plagueDebuffDurationMasteredTicks = builder.defineInRange("plagueDebuffDurationMasteredTicks", 180, 1, Integer.MAX_VALUE);
         this.plagueDebuffAmplifier = builder.defineInRange("plagueDebuffAmplifier", 1, 0, 255);
         this.plagueDebuffAmplifierMastered = builder.defineInRange("plagueDebuffAmplifierMastered", 2, 0, 255);
         builder.pop();
         builder.comment("Bang beam values. Block destruction has explicit safety switches.");
         builder.push("bang");
         this.bangWindupTicks = builder.defineInRange("bangWindupTicks", 60, 1, Integer.MAX_VALUE);
         this.bangCooldownTicks = builder.defineInRange("bangCooldownTicks", 300, 0, Integer.MAX_VALUE);
         this.bangRange = builder.defineInRange("bangRange", 150, 1, 1024);
         this.bangRadius = builder.defineInRange("bangRadius", 12, 0, 256);
         this.bangDamage = builder.defineInRange("bangDamage", 70000.0, 0.0, Float.MAX_VALUE);
         this.bangGuiltCost = builder.defineInRange("bangGuiltCost", 1000.0, 0.0, Double.MAX_VALUE);
         this.bangCanDestroyBlocks = builder.define("bangCanDestroyBlocks", true);
         this.bangCanDestroyBedrock = builder.define("bangCanDestroyBedrock", false);
         this.bangRepeatFireLockoutTicks = builder.defineInRange("bangRepeatFireLockoutTicks", 4, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Total War version of Bang.");
         builder.push("total_war_bang");
         this.totalWarBangRange = builder.defineInRange("totalWarBangRange", 300, 1, 2048);
         this.totalWarBangRadius = builder.defineInRange("totalWarBangRadius", 30, 0, 512);
         this.totalWarBangDamage = builder.defineInRange("totalWarBangDamage", Float.MAX_VALUE, 0.0, Float.MAX_VALUE);
         this.totalWarBangCanDestroyBedrock = builder.define("totalWarBangCanDestroyBedrock", true);
         builder.pop();
         builder.comment("Cloud the Skies / Eden domain values.");
         builder.push("cloud");
         this.cloudWindupTicks = builder.defineInRange("cloudWindupTicks", 100, 1, Integer.MAX_VALUE);
         this.cloudDurationTicks = builder.defineInRange("cloudDurationTicks", 2400, 1, Integer.MAX_VALUE);
         this.cloudCooldownTicks = builder.defineInRange("cloudCooldownTicks", 120, 0, Integer.MAX_VALUE);
         this.edenLeashRange = builder.defineInRange("edenLeashRange", 100, 1, 1024);
         this.edenWorldBorderSize = builder.defineInRange("edenWorldBorderSize", 200.0, 1.0, 6.0E7);
         this.cloudOwnerAttackMultiplier = builder.defineInRange("cloudOwnerAttackMultiplier", 2.0, 0.0, 100.0);
         this.cloudOwnerHealthMultiplier = builder.defineInRange("cloudOwnerHealthMultiplier", 1.5, 0.0, 100.0);
         this.cloudTeleportsAllPlayers = builder.define("cloudTeleportsAllPlayers", true);
         this.cloudStripsCaedrosIfOwnerDies = builder.define("cloudStripsCaedrosIfOwnerDies", true);
         this.cloudRespawnsVictimsInsidePaths = builder.define("cloudRespawnsVictimsInsidePaths", true);
         builder.pop();
         builder.comment("Construct and sword spirit values.");
         builder.push("construct");
         this.constructDamage = builder.defineInRange("constructDamage", 10000.0, 0.0, Float.MAX_VALUE);
         this.constructBaseRange = builder.defineInRange("constructBaseRange", 10, 1, 512);
         this.constructMasterRange = builder.defineInRange("constructMasterRange", 20, 1, 512);
         this.constructMaxSwords = builder.defineInRange("constructMaxSwords", 6, 0, 128);
         this.constructSwordUses = builder.defineInRange("constructSwordUses", 20, 1, Integer.MAX_VALUE);
         this.constructAttackIntervalTicks = builder.defineInRange("constructAttackIntervalTicks", 10, 1, Integer.MAX_VALUE);
         this.constructSwordBreakGuilt = builder.defineInRange("constructSwordBreakGuilt", 250.0, 0.0, Double.MAX_VALUE);
         this.constructCooldownTicks = builder.defineInRange("constructCooldownTicks", 30, 0, Integer.MAX_VALUE);
         this.constructSpiritBaseDamage = builder.defineInRange("constructSpiritBaseDamage", 1200.0, 0.0, Float.MAX_VALUE);
         this.constructSpiritMasteredDamage = builder.defineInRange("constructSpiritMasteredDamage", 1500.0, 0.0, Float.MAX_VALUE);
         builder.pop();
         builder.comment("Fang and Serpent values.");
         builder.push("fang");
         this.fangCooldownTicks = builder.defineInRange("fangCooldownTicks", 4, 0, Integer.MAX_VALUE);
         this.fangRadius = builder.defineInRange("fangRadius", 25, 0, 512);
         this.fangFearDurationTicks = builder.defineInRange("fangFearDurationTicks", 140, 1, Integer.MAX_VALUE);
         this.fangInsanityDurationTicks = builder.defineInRange("fangInsanityDurationTicks", 140, 1, Integer.MAX_VALUE);
         this.fangBurdenDurationTicks = builder.defineInRange("fangBurdenDurationTicks", 120, 1, Integer.MAX_VALUE);
         this.fangMagicInterferenceDurationTicks = builder.defineInRange("fangMagicInterferenceDurationTicks", 120, 1, Integer.MAX_VALUE);
         this.fangSkillDisableAmount = builder.defineInRange("fangSkillDisableAmount", 1, 0, 64);
         this.fangSkillDisableAmountMastered = builder.defineInRange("fangSkillDisableAmountMastered", 2, 0, 64);
         this.fangCanDisableUniqueSkills = builder.define("fangCanDisableUniqueSkills", true);
         this.fangCanDisableUltimateSkills = builder.define("fangCanDisableUltimateSkills", true);
         builder.pop();
         builder.comment("CONTROL aspect values.");
         builder.push("control");
         this.controlEnabled = builder.define("controlEnabled", true);
         this.controlEpThresholdRatio = builder.defineInRange("controlEpThresholdRatio", 0.005, 0.0, 1.0);
         this.controlForcesSpectator = builder.define("controlForcesSpectator", true);
         this.controlCanDisableLockedSkill = builder.define("controlCanDisableLockedSkill", true);
         this.controlCanUnslotLockedSkill = builder.define("controlCanUnslotLockedSkill", true);
         builder.pop();
         builder.comment("FAMINE aspect values.");
         builder.push("famine");
         this.famineEnabled = builder.define("famineEnabled", true);
         this.famineRadius = builder.defineInRange("famineRadius", 512.0, 1.0, 4096.0);
         this.famineStarvesPlayers = builder.define("famineStarvesPlayers", true);
         this.famineDebuffsPerStack = builder.defineInRange("famineDebuffsPerStack", 10, 1, Integer.MAX_VALUE);
         this.famineDurationTicks = builder.defineInRange("famineDurationTicks", 600, 1, Integer.MAX_VALUE);
         this.famineBurstDurationTicks = builder.defineInRange("famineBurstDurationTicks", 120, 1, Integer.MAX_VALUE);
         this.famineMaxAmplifier = builder.defineInRange("famineMaxAmplifier", 5, 0, 255);
         this.famineMaxSecondaryAmplifier = builder.defineInRange("famineMaxSecondaryAmplifier", 2, 0, 255);
         this.famineDisableSkillsOnHit = builder.defineInRange("famineDisableSkillsOnHit", 1, 0, 64);
         this.famineCanDisableUniqueSkills = builder.define("famineCanDisableUniqueSkills", true);
         this.famineCanDisableUltimateSkills = builder.define("famineCanDisableUltimateSkills", true);
         builder.pop();
         builder.comment("WAR aspect values.");
         builder.push("war");
         this.warEnabled = builder.define("warEnabled", true);
         this.warConquerThresholdRatio = builder.defineInRange("warConquerThresholdRatio", 0.1, 0.0, 1.0);
         this.warTempEpGainRatio = builder.defineInRange("warTempEpGainRatio", 0.5, 0.0, 100.0);
         this.warDamageEpGainCapMultiplier = builder.defineInRange("warDamageEpGainCapMultiplier", 6.0, 1.0, 1000.0);
         this.warMagiculeDamageRatio = builder.defineInRange("warMagiculeDamageRatio", 0.1, 0.0, 100.0);
         this.warMagiculeDamageDivisor = builder.defineInRange("warMagiculeDamageDivisor", 500.0, 1.0, Double.MAX_VALUE);
         builder.pop();
         builder.comment("DEATH aspect values.");
         builder.push("death");
         this.deathEnabled = builder.define("deathEnabled", true);
         this.deathReplicaFollowRadius = builder.defineInRange("deathReplicaFollowRadius", 64.0, 1.0, 4096.0);
         this.deathReplicaTeleportDistanceSqr = builder.defineInRange("deathReplicaTeleportDistanceSqr", 16.0, 1.0, Double.MAX_VALUE);
         this.deathSoulDamageDivisor = builder.defineInRange("deathSoulDamageDivisor", 100000.0, 1.0, Double.MAX_VALUE);
         builder.pop();
         builder.comment("Toggled stat buffs from guilt tier.");
         builder.push("toggle_buffs");
         this.toggleAttackBase = builder.defineInRange("toggleAttackBase", 0.45, -1.0, 100.0);
         this.toggleAttackPerTier = builder.defineInRange("toggleAttackPerTier", 0.24, 0.0, 100.0);
         this.toggleAttackSpeedBase = builder.defineInRange("toggleAttackSpeedBase", 0.12, -1.0, 100.0);
         this.toggleAttackSpeedPerTier = builder.defineInRange("toggleAttackSpeedPerTier", 0.05, 0.0, 100.0);
         this.toggleKnockbackBase = builder.defineInRange("toggleKnockbackBase", 0.15, 0.0, 100.0);
         this.toggleKnockbackPerTier = builder.defineInRange("toggleKnockbackPerTier", 0.08, 0.0, 100.0);
         this.toggleKnockbackCap = builder.defineInRange("toggleKnockbackCap", 1.0, 0.0, 100.0);
         this.toggleDodgeBase = builder.defineInRange("toggleDodgeBase", 10.0, 0.0, 1000.0);
         this.toggleDodgePerTier = builder.defineInRange("toggleDodgePerTier", 7.5, 0.0, 1000.0);
         this.toggleDodgeCap = builder.defineInRange("toggleDodgeCap", 90.0, 0.0, 1000.0);
         this.toggleEnergyGainBase = builder.defineInRange("toggleEnergyGainBase", 0.2, 0.0, 100.0);
         this.toggleEnergyGainPerTier = builder.defineInRange("toggleEnergyGainPerTier", 0.1, 0.0, 100.0);
         this.toggleBuffDurationTicks = builder.defineInRange("toggleBuffDurationTicks", 120, 1, Integer.MAX_VALUE);
         this.toggleGuardedMaxAmplifier = builder.defineInRange("toggleGuardedMaxAmplifier", 5, 0, 255);
         this.toggleHakiMaxAmplifier = builder.defineInRange("toggleHakiMaxAmplifier", 4, 0, 255);
         this.toggleStrengthenMaxAmplifier = builder.defineInRange("toggleStrengthenMaxAmplifier", 6, 0, 255);
         this.toggleInspirationMaxAmplifier = builder.defineInRange("toggleInspirationMaxAmplifier", 6, 0, 255);
         this.toggleMagicAuraMaxAmplifier = builder.defineInRange("toggleMagicAuraMaxAmplifier", 4, 0, 255);
         this.toggleSelfRegenMaxAmplifier = builder.defineInRange("toggleSelfRegenMaxAmplifier", 3, 0, 255);
         builder.pop();
         builder.comment("Final Caedros damage scaling.");
         builder.push("final_damage");
         this.finalDamageGuiltPerTier = builder.defineInRange("finalDamageGuiltPerTier", 0.22, 0.0, 100.0);
         this.finalDamageWarCap = builder.defineInRange("finalDamageWarCap", 6.0, 1.0, 1000.0);
         this.trueDamageMinimum = builder.defineInRange("trueDamageMinimum", 1.0, 0.0, Float.MAX_VALUE);
         builder.pop();
         builder.comment("Reconstruct menu costs.");
         builder.push("reconstruct");
         this.reconstructEpCost = builder.defineInRange("reconstructEpCost", 10000.0, 0.0, Double.MAX_VALUE);
         this.reconstructBaseGuiltCost = builder.defineInRange("reconstructBaseGuiltCost", 10000.0, 0.0, Double.MAX_VALUE);
         this.reconstructGuiltDivisor = builder.defineInRange("reconstructGuiltDivisor", 10.0, 1.0, Double.MAX_VALUE);
         this.reconstructGuiltDivisorMastered = builder.defineInRange("reconstructGuiltDivisorMastered", 20.0, 1.0, Double.MAX_VALUE);
         builder.pop();
      }
   }

   public static final class DivineGeneralMahoraga {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue maxMastery;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final IntValue gainNullAfterHits;
      public final IntValue baseDamageReductionPercent;
      public final IntValue maxDamageReductionUnmasteredPercent;
      public final IntValue maxDamageReductionMasteredPercent;
      public final IntValue hitsPerExtraReductionPercent;
      public final IntValue bladeCooldownTicks;
      public final IntValue bladeAlreadyOwnedCooldownTicks;
      public final IntValue learningBonusIntervalTicks;
      public final IntValue learningBonusAmount;
      public final DoubleValue masteredResistanceDegradationBonus;
      public final DoubleValue stage2DamageMultiplier;
      public final DoubleValue stage3DamageMultiplier;
      public final DoubleValue stage4DamageMultiplier;
      public final DoubleValue stage5DamageMultiplier;
      public final DoubleValue stage5TensuraDamageMultiplier;
      public final DoubleValue masteredOutgoingDamageBonus;
      public final IntValue playerRegenDurationTicks;
      public final IntValue playerRegenRefreshBelowTicks;
      public final IntValue playerRegenAmplifier;
      public final IntValue mobRegenDurationTicks;
      public final IntValue mobRegenRefreshBelowTicks;
      public final IntValue mobRegenAmplifier;
      public final IntValue stage2BuiltHits;
      public final IntValue stage3BuiltHits;
      public final IntValue stage4BuiltHits;
      public final IntValue stage5BuiltHits;
      public final IntValue severanceBladeLevel;
      public final IntValue severanceBladeLevelMastered;
      public final IntValue tsukumogamiLevel;
      public final IntValue tsukumogamiLevelMastered;
      public final IntValue unbreakingLevel;
      public final IntValue sharpnessLevel;
      public final IntValue deadEndRainbowLevel;
      public final IntValue soulEaterLevel;
      public final IntValue soulEaterLevelMastered;
      public final IntValue energyStealLevel;
      public final IntValue holyWeaponLevel;
      public final IntValue holyCoatLevel;

      DivineGeneralMahoraga(Builder builder) {
         builder.comment("Obtainment settings for Divine General Mahoraga.");
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.comment("Magicule cost required to naturally acquire Divine General Mahoraga.")
            .defineInRange("acquirementMagiculeCost", 1.0E7, 0.0, Double.MAX_VALUE);
         this.maxMastery = builder.comment("Maximum mastery value.").defineInRange("maxMastery", 6000, 1, Integer.MAX_VALUE);
         this.requiredSkillId = builder.comment("Required skill registry ID for obtainment. Empty string disables the required skill check.")
            .define("requiredSkillId", "tensuramoreskills:adaptability");
         this.requiredSkillMustBeMastered = builder.comment("If true, the required skill must be mastered before Mahoraga can be acquired.")
            .define("requiredSkillMustBeMastered", true);
         builder.pop();
         builder.comment("Adaptation damage reduction settings.");
         builder.push("adaptation_reduction");
         this.gainNullAfterHits = builder.comment("Hits taken from a damage bucket before Mahoraga learns the matching nullification skill.")
            .defineInRange("gainNullAfterHits", 2, 1, Integer.MAX_VALUE);
         this.baseDamageReductionPercent = builder.comment("Base percent damage reduction when a damage adaptation is enabled.")
            .defineInRange("baseDamageReductionPercent", 1, 0, 99);
         this.maxDamageReductionUnmasteredPercent = builder.comment("Maximum percent damage reduction before mastery.")
            .defineInRange("maxDamageReductionUnmasteredPercent", 25, 0, 99);
         this.maxDamageReductionMasteredPercent = builder.comment("Maximum percent damage reduction when mastered.")
            .defineInRange("maxDamageReductionMasteredPercent", 50, 0, 99);
         this.hitsPerExtraReductionPercent = builder.comment("Extra hits needed for each additional 1 percent damage reduction.")
            .defineInRange("hitsPerExtraReductionPercent", 3, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Cooldown and mastery learning settings.");
         builder.push("timing");
         this.bladeCooldownTicks = builder.comment("Cooldown after summoning the Extermination Blade.")
            .defineInRange("bladeCooldownTicks", 200, 0, Integer.MAX_VALUE);
         this.bladeAlreadyOwnedCooldownTicks = builder.comment("Cooldown if the player already has the Extermination Blade.")
            .defineInRange("bladeAlreadyOwnedCooldownTicks", 20, 0, Integer.MAX_VALUE);
         this.learningBonusIntervalTicks = builder.comment("How often the global mastery bonus applies while toggled.")
            .defineInRange("learningBonusIntervalTicks", 20, 1, Integer.MAX_VALUE);
         this.learningBonusAmount = builder.comment("Mastery points granted to learned skills each interval.")
            .defineInRange("learningBonusAmount", 50, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Mastery attribute bonus settings.");
         builder.push("mastery_bonus");
         this.masteredResistanceDegradationBonus = builder.comment("Flat Resistance Degradation bonus while Mahoraga is mastered.")
            .defineInRange("masteredResistanceDegradationBonus", 1.0, 0.0, 1000.0);
         builder.pop();
         builder.comment("Outgoing learned-stage damage multipliers.");
         builder.push("outgoing_stage_damage");
         this.stage2DamageMultiplier = builder.comment("Outgoing damage multiplier at learned stage 2.")
            .defineInRange("stage2DamageMultiplier", 1.15, 0.0, 100.0);
         this.stage3DamageMultiplier = builder.comment("Outgoing damage multiplier at learned stage 3.")
            .defineInRange("stage3DamageMultiplier", 1.3, 0.0, 100.0);
         this.stage4DamageMultiplier = builder.comment("Outgoing damage multiplier at learned stage 4.")
            .defineInRange("stage4DamageMultiplier", 1.45, 0.0, 100.0);
         this.stage5DamageMultiplier = builder.comment("Outgoing damage multiplier at learned stage 5 for non-Tensura-typed buckets.")
            .defineInRange("stage5DamageMultiplier", 1.7, 0.0, 100.0);
         this.stage5TensuraDamageMultiplier = builder.comment("Outgoing damage multiplier at learned stage 5 for Tensura resistance buckets.")
            .defineInRange("stage5TensuraDamageMultiplier", 1.9, 0.0, 100.0);
         this.masteredOutgoingDamageBonus = builder.comment("Extra outgoing damage multiplier added when mastered. 0.10 = +10 percent.")
            .defineInRange("masteredOutgoingDamageBonus", 0.1, 0.0, 100.0);
         builder.pop();
         builder.comment("Regeneration refresh settings.");
         builder.push("regeneration");
         this.playerRegenDurationTicks = builder.comment("Instant Regeneration duration applied to players while toggled.")
            .defineInRange("playerRegenDurationTicks", 240, 1, Integer.MAX_VALUE);
         this.playerRegenRefreshBelowTicks = builder.comment("Refresh player regeneration when remaining duration falls below this.")
            .defineInRange("playerRegenRefreshBelowTicks", 200, 0, Integer.MAX_VALUE);
         this.playerRegenAmplifier = builder.comment("Instant Regeneration amplifier for players. 0 means level 1.")
            .defineInRange("playerRegenAmplifier", 1, 0, 255);
         this.mobRegenDurationTicks = builder.comment("Self Regeneration duration applied to non-players.")
            .defineInRange("mobRegenDurationTicks", 240, 1, Integer.MAX_VALUE);
         this.mobRegenRefreshBelowTicks = builder.comment("Refresh mob regeneration when remaining duration falls below this.")
            .defineInRange("mobRegenRefreshBelowTicks", 200, 0, Integer.MAX_VALUE);
         this.mobRegenAmplifier = builder.comment("Self Regeneration amplifier for non-players.").defineInRange("mobRegenAmplifier", 30, 0, 255);
         builder.pop();
         builder.comment("Hit thresholds for learned damage stages.");
         builder.push("stage_thresholds");
         this.stage2BuiltHits = builder.comment("Hits needed for learned stage 2.").defineInRange("stage2BuiltHits", 2, 1, Integer.MAX_VALUE);
         this.stage3BuiltHits = builder.comment("Hits needed for learned stage 3.").defineInRange("stage3BuiltHits", 3, 1, Integer.MAX_VALUE);
         this.stage4BuiltHits = builder.comment("Hits needed for learned stage 4.").defineInRange("stage4BuiltHits", 4, 1, Integer.MAX_VALUE);
         this.stage5BuiltHits = builder.comment("Hits needed for learned stage 5.").defineInRange("stage5BuiltHits", 5, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Extermination Blade enchantment levels.");
         builder.push("blade_enchantments");
         this.severanceBladeLevel = builder.defineInRange("severanceBladeLevel", 5, 0, 255);
         this.severanceBladeLevelMastered = builder.defineInRange("severanceBladeLevelMastered", 10, 0, 255);
         this.tsukumogamiLevel = builder.defineInRange("tsukumogamiLevel", 5, 0, 255);
         this.tsukumogamiLevelMastered = builder.defineInRange("tsukumogamiLevelMastered", 10, 0, 255);
         this.unbreakingLevel = builder.defineInRange("unbreakingLevel", 5, 0, 255);
         this.sharpnessLevel = builder.defineInRange("sharpnessLevel", 8, 0, 255);
         this.deadEndRainbowLevel = builder.defineInRange("deadEndRainbowLevel", 2, 0, 255);
         this.soulEaterLevel = builder.defineInRange("soulEaterLevel", 5, 0, 255);
         this.soulEaterLevelMastered = builder.defineInRange("soulEaterLevelMastered", 10, 0, 255);
         this.energyStealLevel = builder.defineInRange("energyStealLevel", 2, 0, 255);
         this.holyWeaponLevel = builder.defineInRange("holyWeaponLevel", 2, 0, 255);
         this.holyCoatLevel = builder.defineInRange("holyCoatLevel", 3, 0, 255);
         builder.pop();
      }
   }

   public static final class DomainOfNegationConfig {
      public final DoubleValue domainCost;
      public final DoubleValue burstCost;
      public final IntValue domainRadius;
      public final IntValue domainDurationTicks;
      public final IntValue shellBlockPlaceFlags;
      public final IntValue ownerEffectTicks;
      public final IntValue targetEffectTicks;
      public final IntValue ownerStrengthAmplifier;
      public final IntValue ownerResistanceAmplifier;
      public final IntValue ownerSpeedAmplifier;
      public final IntValue ownerRegenerationAmplifier;
      public final IntValue ownerAntiSkillAmplifier;
      public final IntValue targetAntiSkillAmplifier;
      public final IntValue targetSlownessAmplifier;
      public final IntValue targetWeaknessAmplifier;
      public final IntValue passivePulseIntervalTicks;
      public final DoubleValue passivePulseDamage;
      public final DoubleValue passivePullStrength;
      public final DoubleValue passivePullY;
      public final DoubleValue burstRadiusOutsideDomain;
      public final DoubleValue burstDamageOutsideDomain;
      public final DoubleValue burstDamageInsideDomain;
      public final IntValue burstEffectTicks;
      public final IntValue burstAntiSkillAmplifier;
      public final IntValue burstSlownessAmplifier;
      public final IntValue burstWeaknessAmplifier;
      public final DoubleValue burstPullStrength;
      public final DoubleValue burstPullY;
      public final IntValue domainCooldownTicks;
      public final IntValue burstCooldownTicks;
      public final IntValue maxSummons;
      public final DoubleValue servantHealthMultiplier;
      public final IntValue servantBuffTicks;
      public final IntValue servantStrengthAmplifier;
      public final IntValue servantResistanceAmplifier;
      public final IntValue servantAntiSkillAmplifier;
      public final DoubleValue activateBeaconVolume;
      public final DoubleValue activateBeaconPitch;
      public final DoubleValue activatePortalVolume;
      public final DoubleValue activatePortalPitch;
      public final DoubleValue deactivateVolume;
      public final DoubleValue deactivatePitch;
      public final DoubleValue burstWitherVolume;
      public final DoubleValue burstWitherPitch;
      public final DoubleValue burstBeaconVolume;
      public final DoubleValue burstBeaconPitch;
      public final DoubleValue burstDragonVolume;
      public final DoubleValue burstDragonPitch;
      public final DoubleValue servantSoundVolume;
      public final DoubleValue servantSoundPitch;

      public DomainOfNegationConfig(Builder builder) {
         builder.push("Costs");
         this.domainCost = builder.comment("Magicule cost for activating or deactivating the domain mode.")
            .defineInRange("domainCost", 3000.0, 0.0, Double.MAX_VALUE);
         this.burstCost = builder.comment("Magicule cost for Divine Burst.").defineInRange("burstCost", 5000.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("Domain Shape");
         this.domainRadius = builder.comment("Radius of the black concrete domain sphere.").defineInRange("domainRadius", 12, 1, 128);
         this.domainDurationTicks = builder.comment("How long the domain lasts before auto-collapsing. 900 ticks = 45 seconds.")
            .defineInRange("domainDurationTicks", 900, 1, 1728000);
         this.shellBlockPlaceFlags = builder.comment("Block update flags used when placing/removing the shell. 3 updates clients and neighbors.")
            .defineInRange("shellBlockPlaceFlags", 3, 1, 32);
         builder.pop();
         builder.push("Owner Buffs");
         this.ownerEffectTicks = builder.comment("Duration of effects refreshed on the domain owner while inside the domain.")
            .defineInRange("ownerEffectTicks", 60, 1, 72000);
         this.ownerStrengthAmplifier = builder.comment("Strength amplifier for the owner while inside the domain. 3 means Strength IV.")
            .defineInRange("ownerStrengthAmplifier", 3, 0, 255);
         this.ownerResistanceAmplifier = builder.comment("Resistance amplifier for the owner while inside the domain. 2 means Resistance III.")
            .defineInRange("ownerResistanceAmplifier", 2, 0, 255);
         this.ownerSpeedAmplifier = builder.comment("Speed amplifier for the owner while inside the domain. 2 means Speed III.")
            .defineInRange("ownerSpeedAmplifier", 2, 0, 255);
         this.ownerRegenerationAmplifier = builder.comment("Regeneration amplifier for the owner while inside the domain. 1 means Regeneration II.")
            .defineInRange("ownerRegenerationAmplifier", 1, 0, 255);
         this.ownerAntiSkillAmplifier = builder.comment("Anti-Skill amplifier applied to the owner while inside the domain.")
            .defineInRange("ownerAntiSkillAmplifier", 0, 0, 255);
         builder.pop();
         builder.push("Passive Suppression");
         this.targetEffectTicks = builder.comment("Duration of effects refreshed on enemies inside the domain.")
            .defineInRange("targetEffectTicks", 40, 1, 72000);
         this.targetAntiSkillAmplifier = builder.comment("Anti-Skill amplifier applied to enemies inside the domain.")
            .defineInRange("targetAntiSkillAmplifier", 1, 0, 255);
         this.targetSlownessAmplifier = builder.comment("Slowness amplifier applied to enemies inside the domain.")
            .defineInRange("targetSlownessAmplifier", 2, 0, 255);
         this.targetWeaknessAmplifier = builder.comment("Weakness amplifier applied to enemies inside the domain.")
            .defineInRange("targetWeaknessAmplifier", 1, 0, 255);
         this.passivePulseIntervalTicks = builder.comment("How often the domain passively damages enemies. 20 ticks = 1 second.")
            .defineInRange("passivePulseIntervalTicks", 20, 1, 72000);
         this.passivePulseDamage = builder.comment("Magic damage dealt by each passive pulse.").defineInRange("passivePulseDamage", 5.0, 0.0, 1000000.0);
         this.passivePullStrength = builder.comment("Horizontal pull strength toward the domain center during passive suppression.")
            .defineInRange("passivePullStrength", 0.22, 0.0, 64.0);
         this.passivePullY = builder.comment("Vertical push added during passive suppression pull.").defineInRange("passivePullY", 0.04, -64.0, 64.0);
         builder.pop();
         builder.push("Divine Burst");
         this.burstRadiusOutsideDomain = builder.comment("Burst radius when no domain is active. Active domain uses domainRadius.")
            .defineInRange("burstRadiusOutsideDomain", 7.0, 1.0, 256.0);
         this.burstDamageOutsideDomain = builder.comment("Damage dealt by Divine Burst outside a domain.")
            .defineInRange("burstDamageOutsideDomain", 40.0, 0.0, 1000000.0);
         this.burstDamageInsideDomain = builder.comment("Damage dealt by Divine Burst while empowered by an active domain.")
            .defineInRange("burstDamageInsideDomain", 70.0, 0.0, 1000000.0);
         this.burstEffectTicks = builder.comment("Duration of Divine Burst debuffs. 240 ticks = 12 seconds.").defineInRange("burstEffectTicks", 240, 1, 72000);
         this.burstAntiSkillAmplifier = builder.comment("Anti-Skill amplifier applied by Divine Burst.").defineInRange("burstAntiSkillAmplifier", 1, 0, 255);
         this.burstSlownessAmplifier = builder.comment("Slowness amplifier applied by Divine Burst.").defineInRange("burstSlownessAmplifier", 4, 0, 255);
         this.burstWeaknessAmplifier = builder.comment("Weakness amplifier applied by Divine Burst.").defineInRange("burstWeaknessAmplifier", 3, 0, 255);
         this.burstPullStrength = builder.comment("Horizontal pull strength toward burst center.").defineInRange("burstPullStrength", 1.15, 0.0, 64.0);
         this.burstPullY = builder.comment("Vertical push added by Divine Burst pull.").defineInRange("burstPullY", 0.25, -64.0, 64.0);
         builder.pop();
         builder.push("Cooldowns");
         this.domainCooldownTicks = builder.comment("Cooldown after toggling domain mode.").defineInRange("domainCooldownTicks", 20, 0, 72000);
         this.burstCooldownTicks = builder.comment("Cooldown after Divine Burst. 300 ticks = 15 seconds.").defineInRange("burstCooldownTicks", 300, 0, 72000);
         builder.pop();
         builder.push("Negated Servants");
         this.maxSummons = builder.comment("Maximum servants created by kills inside one domain.").defineInRange("maxSummons", 8, 0, 256);
         this.servantHealthMultiplier = builder.comment("Health multiplier applied to spawned negated servants. 0.6 means 60% health.")
            .defineInRange("servantHealthMultiplier", 0.6, 0.01, 1000.0);
         this.servantBuffTicks = builder.comment("Duration of buffs on spawned negated servants. 600 ticks = 30 seconds.")
            .defineInRange("servantBuffTicks", 600, 1, 72000);
         this.servantStrengthAmplifier = builder.comment("Strength amplifier for negated servants.").defineInRange("servantStrengthAmplifier", 1, 0, 255);
         this.servantResistanceAmplifier = builder.comment("Resistance amplifier for negated servants.").defineInRange("servantResistanceAmplifier", 1, 0, 255);
         this.servantAntiSkillAmplifier = builder.comment("Anti-Skill amplifier for negated servants.").defineInRange("servantAntiSkillAmplifier", 0, 0, 255);
         builder.pop();
         builder.push("Sounds");
         this.activateBeaconVolume = builder.comment("Beacon activation sound volume when domain opens.")
            .defineInRange("activateBeaconVolume", 1.0, 0.0, 100.0);
         this.activateBeaconPitch = builder.comment("Beacon activation sound pitch when domain opens.").defineInRange("activateBeaconPitch", 0.7, 0.0, 10.0);
         this.activatePortalVolume = builder.comment("End portal spawn sound volume when domain opens.").defineInRange("activatePortalVolume", 0.8, 0.0, 100.0);
         this.activatePortalPitch = builder.comment("End portal spawn sound pitch when domain opens.").defineInRange("activatePortalPitch", 0.7, 0.0, 10.0);
         this.deactivateVolume = builder.comment("Beacon deactivation volume when domain closes.").defineInRange("deactivateVolume", 1.0, 0.0, 100.0);
         this.deactivatePitch = builder.comment("Beacon deactivation pitch when domain closes.").defineInRange("deactivatePitch", 0.8, 0.0, 10.0);
         this.burstWitherVolume = builder.comment("Wither shoot sound volume during Divine Burst.").defineInRange("burstWitherVolume", 1.2, 0.0, 100.0);
         this.burstWitherPitch = builder.comment("Wither shoot sound pitch during Divine Burst.").defineInRange("burstWitherPitch", 0.5, 0.0, 10.0);
         this.burstBeaconVolume = builder.comment("Beacon power select sound volume during Divine Burst.").defineInRange("burstBeaconVolume", 1.0, 0.0, 100.0);
         this.burstBeaconPitch = builder.comment("Beacon power select sound pitch during Divine Burst.").defineInRange("burstBeaconPitch", 0.6, 0.0, 10.0);
         this.burstDragonVolume = builder.comment("Dragon growl sound volume during Divine Burst.").defineInRange("burstDragonVolume", 0.5, 0.0, 100.0);
         this.burstDragonPitch = builder.comment("Dragon growl sound pitch during Divine Burst.").defineInRange("burstDragonPitch", 1.8, 0.0, 10.0);
         this.servantSoundVolume = builder.comment("Evoker cast sound volume when a negated servant spawns.")
            .defineInRange("servantSoundVolume", 0.9, 0.0, 100.0);
         this.servantSoundPitch = builder.comment("Evoker cast sound pitch when a negated servant spawns.").defineInRange("servantSoundPitch", 0.6, 0.0, 10.0);
         builder.pop();
      }
   }

   public static final class ElfariaTheHallowedIcemaidenConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final IntValue maxMastery;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final BooleanValue grantPackageOnAcquire;
      public final BooleanValue retryPackageGrantOnToggle;
      public final ConfigValue<List<? extends String>> grantedSkillIds;
      public final DoubleValue chantSpeed;
      public final DoubleValue chantSpeedMastered;
      public final DoubleValue presenceSense;
      public final DoubleValue presenceSenseRadius;
      public final IntValue passiveRefreshTicks;
      public final IntValue masteryTickInterval;
      public final DoubleValue waterIceDamageMultiplier;
      public final DoubleValue waterIceDamageMultiplierMastered;
      public final DoubleValue allMagicDamageMultiplier;
      public final DoubleValue allMagicDamageMultiplierMastered;
      public final DoubleValue passiveFreezeAuraRadius;
      public final IntValue passiveAuraRefreshTicks;
      public final IntValue passiveFreezeAuraEffectTicks;
      public final IntValue passiveFreezeAuraAmplifier;
      public final DoubleValue passiveFreezeAuraDamage;
      public final IntValue arsWeissCloneCount;
      public final IntValue arsWeissCloneCountMastered;
      public final IntValue arsWeissHardCap;
      public final IntValue arsWeissDurationTicks;
      public final IntValue arsWeissDurationTicksMastered;
      public final DoubleValue arsWeissCost;
      public final DoubleValue arsWeissCostMastered;
      public final IntValue arsWeissCooldownTicks;
      public final IntValue arsWeissCooldownTicksMastered;
      public final BooleanValue parallelBodyPossessionEnabled;
      public final BooleanValue parallelBodyDropOrdersEnabled;
      public final BooleanValue parallelBodyAllowDimensionTravelWhilePossessing;
      public final BooleanValue parallelBodyCloneInvulnerableWhileCasting;
      public final BooleanValue parallelBodyCanBreakBlockEntities;
      public final IntValue parallelBodyPossessionMaxTicks;
      public final IntValue parallelBodyPossessInvulnerabilityTicks;
      public final IntValue parallelBodyPossessCooldownTicks;
      public final IntValue parallelBodyReturnCooldownTicks;
      public final DoubleValue parallelBodyPossessRange;
      public final DoubleValue parallelBodyBlueprintRange;
      public final IntValue parallelBodyWorkerScanIntervalTicks;
      public final IntValue parallelBodyWorkerSearchRadius;
      public final IntValue parallelBodyMaxBlocksPerOrder;
      public final IntValue parallelBodyWorkerMineDelayTicks;
      public final IntValue parallelBodyWorkerCarryLimit;
      public final DoubleValue parallelBodyWorkerMoveSpeed;
      public final DoubleValue parallelBodyWorkerMineReach;
      public final DoubleValue parallelBodyWorkerPickupRadius;
      public final DoubleValue parallelBodyWorkerFollowDistance;
      public final DoubleValue parallelBodyReturnDistance;
      public final IntValue parallelBodyWorkerTickInterval;
      public final IntValue parallelBodyReturnInvulnerabilityTicks;
      public final DoubleValue parallelBodyCloneDeathReturnHealthRatio;
      public final DoubleValue parallelBodyManualReturnHealthRatio;
      public final DoubleValue parallelBodyCloneDeathMagiculeBacklashRatio;
      public final DoubleValue parallelBodyCloneDeathAuraBacklashRatio;
      public final DoubleValue parallelBodyWorkerMineHardnessLimit;
      public final IntValue parallelBodyWorkerSlots;
      public final IntValue parallelBodyWorkerSlotsMastered;
      public final DoubleValue parallelBodyHuntTeleportDistance;
      public final BooleanValue parallelBodyForceChunks;
      public final DoubleValue rivilleCost;
      public final DoubleValue rivilleCostMastered;
      public final IntValue rivilleCastTicks;
      public final IntValue rivilleCastTicksMastered;
      public final IntValue rivilleCooldownTicks;
      public final IntValue rivilleCooldownTicksMastered;
      public final DoubleValue rivilleDamage;
      public final DoubleValue rivilleDamageMastered;
      public final DoubleValue rivilleRange;
      public final DoubleValue rivilleRangeMastered;
      public final DoubleValue rivilleWidth;
      public final DoubleValue rivilleWidthMastered;
      public final IntValue rivillePierce;
      public final IntValue rivillePierceMastered;
      public final DoubleValue rivilleCloneScale;
      public final DoubleValue rivilleCloneScaleMastered;
      public final IntValue rivilleMaxCloneBeams;
      public final IntValue rivilleMaxCloneBeamsMastered;
      public final DoubleValue stellasCost;
      public final DoubleValue stellasCostMastered;
      public final IntValue stellasCastTicks;
      public final IntValue stellasCastTicksMastered;
      public final IntValue stellasCooldownTicks;
      public final IntValue stellasCooldownTicksMastered;
      public final DoubleValue stellasRange;
      public final DoubleValue stellasRangeMastered;
      public final IntValue stellasSpearCount;
      public final IntValue stellasSpearCountMastered;
      public final IntValue stellasMaxSpears;
      public final IntValue stellasCloneBonusSpears;
      public final IntValue stellasMaxCloneSpears;
      public final DoubleValue stellasSpearDamage;
      public final DoubleValue stellasSpearDamageMastered;
      public final DoubleValue cloneSpearDamage;
      public final DoubleValue cloneSpearDamageMastered;
      public final DoubleValue fruzelCardeneiaCost;
      public final DoubleValue fruzelCardeneiaCostMastered;
      public final IntValue fruzelCardeneiaCooldownTicks;
      public final IntValue fruzelCardeneiaCooldownTicksMastered;
      public final IntValue fruzelCardeneiaDurationTicks;
      public final IntValue fruzelCardeneiaDurationTicksMastered;
      public final DoubleValue fruzelCardeneiaRadius;
      public final DoubleValue fruzelCardeneiaRadiusMastered;
      public final IntValue fruzelCardeneiaRefreshTicks;
      public final DoubleValue fruzelCardeneiaSpikeDamage;
      public final DoubleValue fruzelCardeneiaSpikeDamageMastered;
      public final DoubleValue fruzelCardeneiaLaunchVelocity;
      public final BooleanValue fruzelCardeneiaDeletesProjectiles;
      public final DoubleValue glaciaLastAlbisCost;
      public final DoubleValue glaciaLastAlbisCostMastered;
      public final IntValue glaciaLastAlbisCooldownTicks;
      public final IntValue glaciaLastAlbisCooldownTicksMastered;
      public final DoubleValue glaciaLastAlbisRange;
      public final DoubleValue glaciaLastAlbisRangeMastered;
      public final DoubleValue glaciaLastAlbisRadius;
      public final DoubleValue glaciaLastAlbisRadiusMastered;
      public final DoubleValue glaciaLastAlbisDamage;
      public final DoubleValue glaciaLastAlbisDamageMastered;
      public final DoubleValue glaciaLastAlbisCloneBonusMultiplier;
      public final DoubleValue glaciaLastAlbisMaxCloneBonusMultiplier;
      public final DoubleValue glaciaLastAlbisKnockback;
      public final BooleanValue glaciaLastAlbisConsumesClones;
      public final IntValue diamondDustDurationTicks;
      public final IntValue diamondDustDurationTicksMastered;
      public final IntValue diamondDustAmplifier;
      public final IntValue diamondDustAmplifierMastered;
      public final IntValue maxRevivesPerDay;
      public final BooleanValue reviveWorksAgainstBypassInvulnerability;
      public final DoubleValue reviveHealthRatio;
      public final DoubleValue reviveMinimumHealth;
      public final DoubleValue reviveMagiculeRatio;
      public final DoubleValue reviveAuraRatio;
      public final DoubleValue reviveSpiritualHealthRatio;
      public final IntValue reviveInvulnerabilityTicks;
      public final IntValue reviveRegenerationTicks;
      public final IntValue reviveRegenerationAmplifier;
      public final IntValue reviveHakiCoatTicks;
      public final IntValue reviveHakiCoatAmplifier;
      public final DoubleValue reviveFreezeRadius;
      public final DoubleValue reviveFreezeDamage;
      public final DoubleValue reviveKnockback;
      public final DoubleValue reviveVerticalKnockback;
      public final IntValue reviveCooldownTicks;
      public final IntValue myrdasModeDurationTicks;
      public final IntValue myrdasRefreshTicks;
      public final DoubleValue myrdasWaterIceDamageMultiplier;
      public final DoubleValue myrdasWaterIceDamageMultiplierMastered;
      public final DoubleValue myrdasAllMagicDamageMultiplier;
      public final DoubleValue myrdasAllMagicDamageMultiplierMastered;
      public final DoubleValue myrdasChantSpeedBonus;
      public final DoubleValue myrdasChantSpeedBonusMastered;
      public final DoubleValue myrdasFreezeAuraRadius;
      public final IntValue myrdasFreezeAuraEffectTicks;
      public final IntValue myrdasFreezeAuraAmplifier;
      public final DoubleValue myrdasFreezeAuraDamage;
      public final DoubleValue myrdasFreezeAuraKnockback;

      ElfariaTheHallowedIcemaidenConfig(Builder builder) {
         builder.comment("Obtainment settings for Elfaria, the Hallowed Icemaiden.");
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.comment("Magicule cost required to naturally acquire the skill.")
            .defineInRange("acquirementMagiculeCost", 850000.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.comment("Starting mastery value when acquired.").defineInRange("acquirementMastery", 0, -1, Integer.MAX_VALUE);
         this.maxMastery = builder.comment("Maximum mastery value.").defineInRange("maxMastery", 2000, 1, Integer.MAX_VALUE);
         this.requiredSkillId = builder.comment("Required skill registry ID for obtainment.").define("requiredSkillId", "tensuramoreskills:albis_vina");
         this.requiredSkillMustBeMastered = builder.comment("If true, required skill must be mastered.").define("requiredSkillMustBeMastered", true);
         builder.pop();
         builder.comment("Skills granted when this ultimate is acquired/toggled.");
         builder.push("grant_package");
         this.grantPackageOnAcquire = builder.comment("If true, grants the package below.").define("grantPackageOnAcquire", true);
         this.retryPackageGrantOnToggle = builder.comment("If true, retries granting missing package skills on toggle/tick.")
            .define("retryPackageGrantOnToggle", true);
         this.grantedSkillIds = builder.comment(
               "Skill IDs granted by Elfaria. Includes Albis so old Ars Weiss clones stay compatible, magic basics, transforms, senses, chant annulment, and nullifications."
            )
            .defineListAllowEmpty(
               "grantedSkillIds",
               List.of(
                  "tensuramoreskills:albis_vina",
                  "tensura:mana_manipulation",
                  "tensura:magic_sense",
                  "tensura:chant_annulment",
                  "tensura:magic_water_transform",
                  "tensura:magic_space_transform",
                  "tensura:magic_wind_transform",
                  "tensura:magic_earth_transform",
                  "tensura:magic_flame_transform",
                  "tensura:magic_light_transform",
                  "tensura:magic_darkness_transform",
                  "tensura:water_manipulation",
                  "tensura:water_domination",
                  "tensura:spatial_manipulation",
                  "tensura:spatial_domination",
                  "tensura:universal_perception",
                  "tensura:analytical_appraisal",
                  "tensura:magic_nullification",
                  "tensura:water_attack_nullification",
                  "tensura:cold_nullification",
                  "tensura:thermal_fluctuation_nullification",
                  "tensura:spiritual_attack_nullification",
                  "tensura:abnormal_condition_nullification"
               ),
               () -> "",
               value -> value instanceof String
            );
         builder.pop();
         builder.comment("Passive/toggled magic revival settings.");
         builder.push("passive");
         this.chantSpeed = builder.comment("Flat chant speed bonus while toggled.").defineInRange("chantSpeed", 1.5, 0.0, 1000.0);
         this.chantSpeedMastered = builder.comment("Flat chant speed bonus while mastered and toggled.").defineInRange("chantSpeedMastered", 2.5, 0.0, 1000.0);
         this.presenceSense = builder.comment("Presence Sense level bonus.").defineInRange("presenceSense", 4.0, 0.0, 1000.0);
         this.presenceSenseRadius = builder.comment("Presence Sense radius bonus.").defineInRange("presenceSenseRadius", 64.0, 0.0, 1024.0);
         this.passiveRefreshTicks = builder.comment("How often passive attributes/package refresh.").defineInRange("passiveRefreshTicks", 80, 1, 72000);
         this.masteryTickInterval = builder.comment("How often mastery is gained while toggled.").defineInRange("masteryTickInterval", 100, 1, 72000);
         this.waterIceDamageMultiplier = builder.comment("Water/Ice magic damage multiplier.").defineInRange("waterIceDamageMultiplier", 7.5, 0.0, 1000000.0);
         this.waterIceDamageMultiplierMastered = builder.comment("Mastered Water/Ice magic damage multiplier.")
            .defineInRange("waterIceDamageMultiplierMastered", 10.5, 0.0, 1000000.0);
         this.allMagicDamageMultiplier = builder.comment("All magic damage multiplier.").defineInRange("allMagicDamageMultiplier", 1.75, 0.0, 1000000.0);
         this.allMagicDamageMultiplierMastered = builder.comment("Mastered all magic damage multiplier.")
            .defineInRange("allMagicDamageMultiplierMastered", 2.5, 0.0, 1000000.0);
         this.passiveFreezeAuraRadius = builder.comment("Unused unless passive aura damage is enabled.")
            .defineInRange("passiveFreezeAuraRadius", 0.0, 0.0, 256.0);
         this.passiveAuraRefreshTicks = builder.comment("How often passive aura checks.").defineInRange("passiveAuraRefreshTicks", 80, 1, 72000);
         this.passiveFreezeAuraEffectTicks = builder.comment("Passive aura effect duration.").defineInRange("passiveFreezeAuraEffectTicks", 100, 1, 72000);
         this.passiveFreezeAuraAmplifier = builder.comment("Passive aura effect amplifier.").defineInRange("passiveFreezeAuraAmplifier", 0, 0, 255);
         this.passiveFreezeAuraDamage = builder.comment("Passive aura damage. Keep 0 unless you want constant aura damage.")
            .defineInRange("passiveFreezeAuraDamage", 0.0, 0.0, 1000000.0);
         builder.pop();
         builder.push("ars_weiss");
         this.arsWeissCloneCount = builder.defineInRange("arsWeissCloneCount", 2, 0, 300);
         this.arsWeissCloneCountMastered = builder.defineInRange("arsWeissCloneCountMastered", 3, 0, 300);
         this.arsWeissHardCap = builder.defineInRange("arsWeissHardCap", 16, 1, 300);
         this.arsWeissDurationTicks = builder.defineInRange("arsWeissDurationTicks", -1, -1, Integer.MAX_VALUE);
         this.arsWeissDurationTicksMastered = builder.defineInRange("arsWeissDurationTicksMastered", -1, -1, Integer.MAX_VALUE);
         this.arsWeissCost = builder.defineInRange("arsWeissCost", 80000.0, 0.0, Double.MAX_VALUE);
         this.arsWeissCostMastered = builder.defineInRange("arsWeissCostMastered", 60000.0, 0.0, Double.MAX_VALUE);
         this.arsWeissCooldownTicks = builder.defineInRange("arsWeissCooldownTicks", 20, 0, Integer.MAX_VALUE);
         this.arsWeissCooldownTicksMastered = builder.defineInRange("arsWeissCooldownTicksMastered", 10, 0, Integer.MAX_VALUE);
         this.parallelBodyPossessionEnabled = builder.define("parallelBodyPossessionEnabled", true);
         this.parallelBodyDropOrdersEnabled = builder.define("parallelBodyDropOrdersEnabled", true);
         this.parallelBodyAllowDimensionTravelWhilePossessing = builder.define("parallelBodyAllowDimensionTravelWhilePossessing", false);
         this.parallelBodyCloneInvulnerableWhileCasting = builder.define("parallelBodyCloneInvulnerableWhileCasting", true);
         this.parallelBodyCanBreakBlockEntities = builder.define("parallelBodyCanBreakBlockEntities", false);
         this.parallelBodyPossessionMaxTicks = builder.defineInRange("parallelBodyPossessionMaxTicks", 2400, 40, Integer.MAX_VALUE);
         this.parallelBodyPossessInvulnerabilityTicks = builder.defineInRange("parallelBodyPossessInvulnerabilityTicks", 40, 0, Integer.MAX_VALUE);
         this.parallelBodyPossessCooldownTicks = builder.defineInRange("parallelBodyPossessCooldownTicks", 80, 0, Integer.MAX_VALUE);
         this.parallelBodyReturnCooldownTicks = builder.defineInRange("parallelBodyReturnCooldownTicks", 60, 0, Integer.MAX_VALUE);
         this.parallelBodyPossessRange = builder.defineInRange("parallelBodyPossessRange", 80.0, 1.0, 1024.0);
         this.parallelBodyBlueprintRange = builder.defineInRange("parallelBodyBlueprintRange", 8.0, 1.0, 64.0);
         this.parallelBodyWorkerScanIntervalTicks = builder.defineInRange("parallelBodyWorkerScanIntervalTicks", 40, 1, 72000);
         this.parallelBodyWorkerSearchRadius = builder.defineInRange("parallelBodyWorkerSearchRadius", 18, 1, 128);
         this.parallelBodyMaxBlocksPerOrder = builder.defineInRange("parallelBodyMaxBlocksPerOrder", 32, 1, 4096);
         this.parallelBodyWorkerMineDelayTicks = builder.defineInRange("parallelBodyWorkerMineDelayTicks", 18, 1, 72000);
         this.parallelBodyWorkerCarryLimit = builder.defineInRange("parallelBodyWorkerCarryLimit", 64, 1, 2304);
         this.parallelBodyWorkerMoveSpeed = builder.defineInRange("parallelBodyWorkerMoveSpeed", 1.25, 0.0, 10.0);
         this.parallelBodyWorkerMineReach = builder.defineInRange("parallelBodyWorkerMineReach", 2.6, 0.5, 16.0);
         this.parallelBodyWorkerPickupRadius = builder.defineInRange("parallelBodyWorkerPickupRadius", 3.5, 0.5, 32.0);
         this.parallelBodyWorkerFollowDistance = builder.defineInRange("parallelBodyWorkerFollowDistance", 6.0, 1.0, 128.0);
         this.parallelBodyReturnDistance = builder.defineInRange("parallelBodyReturnDistance", 2.8, 1.0, 32.0);
         this.parallelBodyWorkerTickInterval = builder.defineInRange("parallelBodyWorkerTickInterval", 10, 1, 200);
         this.parallelBodyReturnInvulnerabilityTicks = builder.defineInRange("parallelBodyReturnInvulnerabilityTicks", 60, 0, 400);
         this.parallelBodyCloneDeathReturnHealthRatio = builder.defineInRange("parallelBodyCloneDeathReturnHealthRatio", 0.45, 0.01, 1.0);
         this.parallelBodyManualReturnHealthRatio = builder.defineInRange("parallelBodyManualReturnHealthRatio", 0.75, 0.01, 1.0);
         this.parallelBodyCloneDeathMagiculeBacklashRatio = builder.defineInRange("parallelBodyCloneDeathMagiculeBacklashRatio", 0.12, 0.0, 1.0);
         this.parallelBodyCloneDeathAuraBacklashRatio = builder.defineInRange("parallelBodyCloneDeathAuraBacklashRatio", 0.12, 0.0, 1.0);
         this.parallelBodyWorkerMineHardnessLimit = builder.defineInRange("parallelBodyWorkerMineHardnessLimit", 75.0, 0.0, 10000.0);
         this.parallelBodyWorkerSlots = builder.comment(
               "Maximum non-mastered Ars Weiss clones allowed to execute gather/mine/hunt orders. Other clones remain casting bodies/followers."
            )
            .defineInRange("parallelBodyWorkerSlots", 1, 0, 64);
         this.parallelBodyWorkerSlotsMastered = builder.comment("Maximum mastered Ars Weiss clones allowed to execute gather/mine/hunt orders.")
            .defineInRange("parallelBodyWorkerSlotsMastered", 2, 0, 64);
         this.parallelBodyHuntTeleportDistance = builder.comment(
               "If a hunting clone is farther than this from the target player, it warps near them. Set 0 to force walking only."
            )
            .defineInRange("parallelBodyHuntTeleportDistance", 128.0, 0.0, 100000.0);
         this.parallelBodyForceChunks = builder.comment("If true, worker/hunter clones keep their current task chunk loaded while working.")
            .define("parallelBodyForceChunks", true);
         builder.pop();
         builder.push("riville_calantibium");
         this.rivilleCost = builder.defineInRange("rivilleCost", 60000.0, 0.0, Double.MAX_VALUE);
         this.rivilleCostMastered = builder.defineInRange("rivilleCostMastered", 45000.0, 0.0, Double.MAX_VALUE);
         this.rivilleCastTicks = builder.defineInRange("rivilleCastTicks", 8, 0, Integer.MAX_VALUE);
         this.rivilleCastTicksMastered = builder.defineInRange("rivilleCastTicksMastered", 3, 0, Integer.MAX_VALUE);
         this.rivilleCooldownTicks = builder.defineInRange("rivilleCooldownTicks", 30, 0, Integer.MAX_VALUE);
         this.rivilleCooldownTicksMastered = builder.defineInRange("rivilleCooldownTicksMastered", 70, 0, Integer.MAX_VALUE);
         this.rivilleDamage = builder.defineInRange("rivilleDamage", 20.0, 0.0, 1000000.0);
         this.rivilleDamageMastered = builder.defineInRange("rivilleDamageMastered", 130.0, 0.0, 1000000.0);
         this.rivilleRange = builder.defineInRange("rivilleRange", 48.0, 0.0, 512.0);
         this.rivilleRangeMastered = builder.defineInRange("rivilleRangeMastered", 72.0, 0.0, 512.0);
         this.rivilleWidth = builder.defineInRange("rivilleWidth", 2.0, 0.0, 64.0);
         this.rivilleWidthMastered = builder.defineInRange("rivilleWidthMastered", 2.8, 0.0, 64.0);
         this.rivillePierce = builder.defineInRange("rivillePierce", 12, 1, 512);
         this.rivillePierceMastered = builder.defineInRange("rivillePierceMastered", 24, 1, 512);
         this.rivilleCloneScale = builder.defineInRange("rivilleCloneScale", 0.0, 0.0, 100.0);
         this.rivilleCloneScaleMastered = builder.defineInRange("rivilleCloneScaleMastered", 0.0, 0.0, 100.0);
         this.rivilleMaxCloneBeams = builder.defineInRange("rivilleMaxCloneBeams", 0, 0, 300);
         this.rivilleMaxCloneBeamsMastered = builder.defineInRange("rivilleMaxCloneBeamsMastered", 0, 0, 300);
         builder.pop();
         builder.push("stellas_natea");
         this.stellasCost = builder.defineInRange("stellasCost", 85000.0, 0.0, Double.MAX_VALUE);
         this.stellasCostMastered = builder.defineInRange("stellasCostMastered", 65000.0, 0.0, Double.MAX_VALUE);
         this.stellasCastTicks = builder.defineInRange("stellasCastTicks", 12, 0, Integer.MAX_VALUE);
         this.stellasCastTicksMastered = builder.defineInRange("stellasCastTicksMastered", 5, 0, Integer.MAX_VALUE);
         this.stellasCooldownTicks = builder.defineInRange("stellasCooldownTicks", 50, 0, Integer.MAX_VALUE);
         this.stellasCooldownTicksMastered = builder.defineInRange("stellasCooldownTicksMastered", 35, 0, Integer.MAX_VALUE);
         this.stellasRange = builder.defineInRange("stellasRange", 48.0, 0.0, 512.0);
         this.stellasRangeMastered = builder.defineInRange("stellasRangeMastered", 72.0, 0.0, 512.0);
         this.stellasSpearCount = builder.defineInRange("stellasSpearCount", 80, 0, 2000);
         this.stellasSpearCountMastered = builder.defineInRange("stellasSpearCountMastered", 140, 0, 2000);
         this.stellasMaxSpears = builder.defineInRange("stellasMaxSpears", 180, 0, 5000);
         this.stellasCloneBonusSpears = builder.defineInRange("stellasCloneBonusSpears", 0, 0, 100);
         this.stellasMaxCloneSpears = builder.defineInRange("stellasMaxCloneSpears", 0, 0, 5000);
         this.stellasSpearDamage = builder.defineInRange("stellasSpearDamage", 380.0, 0.0, 1000000.0);
         this.stellasSpearDamageMastered = builder.defineInRange("stellasSpearDamageMastered", 580.0, 0.0, 1000000.0);
         this.cloneSpearDamage = builder.defineInRange("cloneSpearDamage", 0.0, 0.0, 1000000.0);
         this.cloneSpearDamageMastered = builder.defineInRange("cloneSpearDamageMastered", 0.0, 0.0, 1000000.0);
         builder.pop();
         builder.push("fruzel_cardeneia");
         this.fruzelCardeneiaCost = builder.defineInRange("fruzelCardeneiaCost", 160000.0, 0.0, Double.MAX_VALUE);
         this.fruzelCardeneiaCostMastered = builder.defineInRange("fruzelCardeneiaCostMastered", 120000.0, 0.0, Double.MAX_VALUE);
         this.fruzelCardeneiaCooldownTicks = builder.defineInRange("fruzelCardeneiaCooldownTicks", 100, 0, Integer.MAX_VALUE);
         this.fruzelCardeneiaCooldownTicksMastered = builder.defineInRange("fruzelCardeneiaCooldownTicksMastered", 360, 0, Integer.MAX_VALUE);
         this.fruzelCardeneiaDurationTicks = builder.defineInRange("fruzelCardeneiaDurationTicks", 70, 1, Integer.MAX_VALUE);
         this.fruzelCardeneiaDurationTicksMastered = builder.defineInRange("fruzelCardeneiaDurationTicksMastered", 420, 1, Integer.MAX_VALUE);
         this.fruzelCardeneiaRadius = builder.defineInRange("fruzelCardeneiaRadius", 18.0, 0.0, 256.0);
         this.fruzelCardeneiaRadiusMastered = builder.defineInRange("fruzelCardeneiaRadiusMastered", 26.0, 0.0, 256.0);
         this.fruzelCardeneiaRefreshTicks = builder.defineInRange("fruzelCardeneiaRefreshTicks", 40, 1, 72000);
         this.fruzelCardeneiaSpikeDamage = builder.defineInRange("fruzelCardeneiaSpikeDamage", 480.0, 0.0, 1000000.0);
         this.fruzelCardeneiaSpikeDamageMastered = builder.defineInRange("fruzelCardeneiaSpikeDamageMastered", 750.0, 0.0, 1000000.0);
         this.fruzelCardeneiaLaunchVelocity = builder.defineInRange("fruzelCardeneiaLaunchVelocity", 0.35, 0.0, 10.0);
         this.fruzelCardeneiaDeletesProjectiles = builder.define("fruzelCardeneiaDeletesProjectiles", true);
         builder.pop();
         builder.push("glacia_last_albis");
         this.glaciaLastAlbisCost = builder.defineInRange("glaciaLastAlbisCost", 260000.0, 0.0, Double.MAX_VALUE);
         this.glaciaLastAlbisCostMastered = builder.defineInRange("glaciaLastAlbisCostMastered", 200000.0, 0.0, Double.MAX_VALUE);
         this.glaciaLastAlbisCooldownTicks = builder.defineInRange("glaciaLastAlbisCooldownTicks", 200, 0, Integer.MAX_VALUE);
         this.glaciaLastAlbisCooldownTicksMastered = builder.defineInRange("glaciaLastAlbisCooldownTicksMastered", 150, 0, Integer.MAX_VALUE);
         this.glaciaLastAlbisRange = builder.defineInRange("glaciaLastAlbisRange", 64.0, 0.0, 512.0);
         this.glaciaLastAlbisRangeMastered = builder.defineInRange("glaciaLastAlbisRangeMastered", 92.0, 0.0, 512.0);
         this.glaciaLastAlbisRadius = builder.defineInRange("glaciaLastAlbisRadius", 12.0, 0.0, 256.0);
         this.glaciaLastAlbisRadiusMastered = builder.defineInRange("glaciaLastAlbisRadiusMastered", 18.0, 0.0, 256.0);
         this.glaciaLastAlbisDamage = builder.defineInRange("glaciaLastAlbisDamage", 2400.0, 0.0, 1000000.0);
         this.glaciaLastAlbisDamageMastered = builder.defineInRange("glaciaLastAlbisDamageMastered", 4200.0, 0.0, 1000000.0);
         this.glaciaLastAlbisCloneBonusMultiplier = builder.defineInRange("glaciaLastAlbisCloneBonusMultiplier", 0.0, 0.0, 100.0);
         this.glaciaLastAlbisMaxCloneBonusMultiplier = builder.defineInRange("glaciaLastAlbisMaxCloneBonusMultiplier", 0.0, 0.0, 100.0);
         this.glaciaLastAlbisKnockback = builder.defineInRange("glaciaLastAlbisKnockback", 2.2, 0.0, 100.0);
         this.glaciaLastAlbisConsumesClones = builder.define("glaciaLastAlbisConsumesClones", false);
         builder.pop();
         builder.push("diamond_dust");
         this.diamondDustDurationTicks = builder.defineInRange("diamondDustDurationTicks", 160, 1, 72000);
         this.diamondDustDurationTicksMastered = builder.defineInRange("diamondDustDurationTicksMastered", 240, 1, 72000);
         this.diamondDustAmplifier = builder.defineInRange("diamondDustAmplifier", 2, 0, 255);
         this.diamondDustAmplifierMastered = builder.defineInRange("diamondDustAmplifierMastered", 4, 0, 255);
         builder.pop();
         builder.push("myrdas_fridoliete");
         this.maxRevivesPerDay = builder.defineInRange("maxRevivesPerDay", 1, 0, Integer.MAX_VALUE);
         this.reviveWorksAgainstBypassInvulnerability = builder.define("reviveWorksAgainstBypassInvulnerability", false);
         this.reviveHealthRatio = builder.defineInRange("reviveHealthRatio", 0.75, 0.0, 1.0);
         this.reviveMinimumHealth = builder.defineInRange("reviveMinimumHealth", 30.0, 0.0, Float.MAX_VALUE);
         this.reviveMagiculeRatio = builder.defineInRange("reviveMagiculeRatio", 0.75, 0.0, 1.0);
         this.reviveAuraRatio = builder.defineInRange("reviveAuraRatio", 0.75, 0.0, 1.0);
         this.reviveSpiritualHealthRatio = builder.defineInRange("reviveSpiritualHealthRatio", 0.75, 0.0, 1.0);
         this.reviveInvulnerabilityTicks = builder.defineInRange("reviveInvulnerabilityTicks", 120, 0, Integer.MAX_VALUE);
         this.reviveRegenerationTicks = builder.defineInRange("reviveRegenerationTicks", 240, 1, Integer.MAX_VALUE);
         this.reviveRegenerationAmplifier = builder.defineInRange("reviveRegenerationAmplifier", 2, 0, 255);
         this.reviveHakiCoatTicks = builder.defineInRange("reviveHakiCoatTicks", 240, 1, Integer.MAX_VALUE);
         this.reviveHakiCoatAmplifier = builder.defineInRange("reviveHakiCoatAmplifier", 2, 0, 255);
         this.reviveFreezeRadius = builder.defineInRange("reviveFreezeRadius", 14.0, 0.0, 256.0);
         this.reviveFreezeDamage = builder.defineInRange("reviveFreezeDamage", 130.0, 0.0, 1000000.0);
         this.reviveKnockback = builder.defineInRange("reviveKnockback", 2.6, 0.0, 100.0);
         this.reviveVerticalKnockback = builder.defineInRange("reviveVerticalKnockback", 0.45, 0.0, 10.0);
         this.reviveCooldownTicks = builder.defineInRange("reviveCooldownTicks", 300, 0, Integer.MAX_VALUE);
         this.myrdasModeDurationTicks = builder.defineInRange("myrdasModeDurationTicks", 2400, 1, Integer.MAX_VALUE);
         this.myrdasRefreshTicks = builder.defineInRange("myrdasRefreshTicks", 40, 1, 72000);
         this.myrdasWaterIceDamageMultiplier = builder.defineInRange("myrdasWaterIceDamageMultiplier", 1.6, 0.0, 1000000.0);
         this.myrdasWaterIceDamageMultiplierMastered = builder.defineInRange("myrdasWaterIceDamageMultiplierMastered", 2.0, 0.0, 1000000.0);
         this.myrdasAllMagicDamageMultiplier = builder.defineInRange("myrdasAllMagicDamageMultiplier", 1.4, 0.0, 1000000.0);
         this.myrdasAllMagicDamageMultiplierMastered = builder.defineInRange("myrdasAllMagicDamageMultiplierMastered", 1.75, 0.0, 1000000.0);
         this.myrdasChantSpeedBonus = builder.defineInRange("myrdasChantSpeedBonus", 2.0, 0.0, 1000.0);
         this.myrdasChantSpeedBonusMastered = builder.defineInRange("myrdasChantSpeedBonusMastered", 3.0, 0.0, 1000.0);
         this.myrdasFreezeAuraRadius = builder.defineInRange("myrdasFreezeAuraRadius", 9.0, 0.0, 256.0);
         this.myrdasFreezeAuraEffectTicks = builder.defineInRange("myrdasFreezeAuraEffectTicks", 120, 1, 72000);
         this.myrdasFreezeAuraAmplifier = builder.defineInRange("myrdasFreezeAuraAmplifier", 3, 0, 255);
         this.myrdasFreezeAuraDamage = builder.defineInRange("myrdasFreezeAuraDamage", 35.0, 0.0, 1000000.0);
         this.myrdasFreezeAuraKnockback = builder.defineInRange("myrdasFreezeAuraKnockback", 0.18, 0.0, 10.0);
         builder.pop();
      }
   }

   public static final class ExploiterConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue maxMastery;
      public final IntValue masteryTickInterval;
      public final DoubleValue fullBrightDarkVision;
      public final DoubleValue presenceSenseBonus;
      public final DoubleValue presenceSenseRadiusBonus;
      public final DoubleValue flightMagiculeCostPerSecond;
      public final IntValue inventoryCheckIntervalTicks;
      public final DoubleValue autoEatMagiculeCost;
      public final DoubleValue autoTotemMagiculeCost;
      public final IntValue xrayToggleCooldownTicks;
      public final IntValue killAuraToggleCooldownTicks;
      public final IntValue killAuraIntervalTicks;
      public final DoubleValue killAuraMagiculeCostPerAttack;
      public final DoubleValue killAuraRadius;
      public final BooleanValue killAuraRequiresMastery;
      public final BooleanValue killAuraRequiresLineOfSight;
      public final BooleanValue killAuraTargetsPlayers;
      public final BooleanValue killAuraTargetsPassive;

      ExploiterConfig(Builder builder) {
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.defineInRange("acquirementMagiculeCost", 250000.0, 0.0, Double.MAX_VALUE);
         this.maxMastery = builder.defineInRange("maxMastery", 3000, 1, Integer.MAX_VALUE);
         this.masteryTickInterval = builder.defineInRange("masteryTickInterval", 100, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.push("toggle");
         this.fullBrightDarkVision = builder.defineInRange("fullBrightDarkVision", 1.0, 0.0, 10.0);
         this.presenceSenseBonus = builder.defineInRange("presenceSenseBonus", 1.0, 0.0, 1000.0);
         this.presenceSenseRadiusBonus = builder.defineInRange("presenceSenseRadiusBonus", 32.0, 0.0, 512.0);
         this.flightMagiculeCostPerSecond = builder.defineInRange("flightMagiculeCostPerSecond", 500.0, 0.0, Double.MAX_VALUE);
         this.inventoryCheckIntervalTicks = builder.defineInRange("inventoryCheckIntervalTicks", 20, 1, Integer.MAX_VALUE);
         this.autoEatMagiculeCost = builder.defineInRange("autoEatMagiculeCost", 1000.0, 0.0, Double.MAX_VALUE);
         this.autoTotemMagiculeCost = builder.defineInRange("autoTotemMagiculeCost", 5000.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("xray");
         this.xrayToggleCooldownTicks = builder.defineInRange("xrayToggleCooldownTicks", 10, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("kill_aura");
         this.killAuraToggleCooldownTicks = builder.defineInRange("killAuraToggleCooldownTicks", 10, 0, Integer.MAX_VALUE);
         this.killAuraIntervalTicks = builder.defineInRange("killAuraIntervalTicks", 10, 1, Integer.MAX_VALUE);
         this.killAuraMagiculeCostPerAttack = builder.defineInRange("killAuraMagiculeCostPerAttack", 250.0, 0.0, Double.MAX_VALUE);
         this.killAuraRadius = builder.defineInRange("killAuraRadius", 4.5, 0.0, 64.0);
         this.killAuraRequiresMastery = builder.define("killAuraRequiresMastery", true);
         this.killAuraRequiresLineOfSight = builder.define("killAuraRequiresLineOfSight", true);
         this.killAuraTargetsPlayers = builder.define("killAuraTargetsPlayers", true);
         this.killAuraTargetsPassive = builder.define("killAuraTargetsPassive", false);
         builder.pop();
      }
   }

   public static final class FungusInfectionConfig {
      public final DoubleValue obtainCost;
      public final IntValue auraApplyEveryTicks;
      public final DoubleValue auraRadius;
      public final IntValue auraParalysisTicks;
      public final IntValue auraParalysisAmplifier;
      public final IntValue auraPoisonParticlesVictim;
      public final IntValue auraPoisonParticlesOwner;
      public final IntValue auraMushroomPatchAttemptsVictim;
      public final IntValue auraMushroomPatchAttemptsOwner;
      public final IntValue storedAuraVisualIntervalTicks;
      public final IntValue storedAuraVisualBaseParticles;
      public final IntValue storedAuraVisualParticlesPerEffect;
      public final IntValue storedAuraVisualMaxParticles;
      public final DoubleValue analyzeRange;
      public final DoubleValue analyzeFallbackInflate;
      public final IntValue analyzeCooldownTicks;
      public final IntValue analyzePoisonParticles;
      public final IntValue analyzeMushroomPatchAttempts;
      public final IntValue workshopCooldownTicks;
      public final IntValue configureCooldownTicks;
      public final IntValue spreadCooldownTicks;
      public final IntValue workshopLimitNormal;
      public final IntValue workshopLimitMastered;
      public final IntValue spreadCountNormal;
      public final IntValue spreadCountMastered;
      public final DoubleValue spreadRange;
      public final IntValue spreadSelfBaseDurationMin;
      public final IntValue spreadSelfBaseDurationRandom;
      public final IntValue spreadSelfMinimumDuration;
      public final IntValue spreadTargetBaseDurationMin;
      public final IntValue spreadTargetBaseDurationRandom;
      public final IntValue spreadTargetMinimumDuration;
      public final DoubleValue spreadDurationPerUpgrade;
      public final IntValue spreadSelfPoisonParticles;
      public final IntValue spreadTargetPoisonParticles;
      public final IntValue spreadOwnerPoisonParticles;
      public final IntValue spreadMushroomPatchAttempts;
      public final IntValue mushroomPatchSearchRadius;
      public final IntValue mushroomPatchBlockFlags;

      public FungusInfectionConfig(Builder builder) {
         builder.push("Skill");
         this.obtainCost = builder.comment("Magicule cost shown by the skill acquisition system.").defineInRange("obtainCost", 100000.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("Passive Aura");
         this.auraApplyEveryTicks = builder.comment("How often the toggled infection aura applies effects. 70 ticks = 3.5 seconds.")
            .defineInRange("auraApplyEveryTicks", 70, 1, 72000);
         this.auraRadius = builder.comment("Radius of the toggled infection aura.").defineInRange("auraRadius", 10.0, 0.0, 256.0);
         this.auraParalysisTicks = builder.comment("Paralysis duration applied by the passive aura.").defineInRange("auraParalysisTicks", 80, 1, 72000);
         this.auraParalysisAmplifier = builder.comment("Paralysis amplifier applied by the passive aura. 0 means level I.")
            .defineInRange("auraParalysisAmplifier", 0, 0, 255);
         this.auraPoisonParticlesVictim = builder.comment("Poison particles spawned around each aura victim.")
            .defineInRange("auraPoisonParticlesVictim", 22, 0, 10000);
         this.auraPoisonParticlesOwner = builder.comment("Poison particles spawned around the owner each aura pulse.")
            .defineInRange("auraPoisonParticlesOwner", 28, 0, 10000);
         this.auraMushroomPatchAttemptsVictim = builder.comment("Mushroom placement attempts near each aura victim.")
            .defineInRange("auraMushroomPatchAttemptsVictim", 2, 0, 256);
         this.auraMushroomPatchAttemptsOwner = builder.comment("Mushroom placement attempts near the owner each aura pulse.")
            .defineInRange("auraMushroomPatchAttemptsOwner", 2, 0, 256);
         builder.pop();
         builder.push("Stored Aura Visuals");
         this.storedAuraVisualIntervalTicks = builder.comment("How often stored effects create owner aura visuals.")
            .defineInRange("storedAuraVisualIntervalTicks", 12, 1, 72000);
         this.storedAuraVisualBaseParticles = builder.comment("Base particle count for stored effect aura visuals.")
            .defineInRange("storedAuraVisualBaseParticles", 8, 0, 10000);
         this.storedAuraVisualParticlesPerEffect = builder.comment("Extra particle count per stored effect.")
            .defineInRange("storedAuraVisualParticlesPerEffect", 2, 0, 10000);
         this.storedAuraVisualMaxParticles = builder.comment("Maximum stored effect aura particle count.")
            .defineInRange("storedAuraVisualMaxParticles", 30, 0, 10000);
         builder.pop();
         builder.push("Analyze");
         this.analyzeRange = builder.comment("Range for direct effect analysis target selection.").defineInRange("analyzeRange", 12.0, 0.0, 256.0);
         this.analyzeFallbackInflate = builder.comment("Fallback target search inflation if direct targeting misses.")
            .defineInRange("analyzeFallbackInflate", 1.0, 0.0, 32.0);
         this.analyzeCooldownTicks = builder.comment("Cooldown after Analyze mode.").defineInRange("analyzeCooldownTicks", 1, 0, 72000);
         this.analyzePoisonParticles = builder.comment("Poison particles used when analysis succeeds.").defineInRange("analyzePoisonParticles", 18, 0, 10000);
         this.analyzeMushroomPatchAttempts = builder.comment("Mushroom placement attempts near analyzed target.")
            .defineInRange("analyzeMushroomPatchAttempts", 1, 0, 256);
         builder.pop();
         builder.push("Cooldowns");
         this.workshopCooldownTicks = builder.comment("Cooldown after opening the Fungus Workshop.").defineInRange("workshopCooldownTicks", 2, 0, 72000);
         this.configureCooldownTicks = builder.comment("Cooldown after configuring or clearing the workshop.")
            .defineInRange("configureCooldownTicks", 10, 0, 72000);
         this.spreadCooldownTicks = builder.comment("Cooldown after Spread mode.").defineInRange("spreadCooldownTicks", 5, 0, 72000);
         builder.pop();
         builder.push("Workshop");
         this.workshopLimitNormal = builder.comment("Number of stored effects usable in workshop before mastery.")
            .defineInRange("workshopLimitNormal", 3, 1, 64);
         this.workshopLimitMastered = builder.comment("Number of stored effects usable in workshop after mastery.")
            .defineInRange("workshopLimitMastered", 5, 1, 64);
         builder.pop();
         builder.push("Spread");
         this.spreadCountNormal = builder.comment("Number of random configured effects applied before mastery.").defineInRange("spreadCountNormal", 3, 1, 256);
         this.spreadCountMastered = builder.comment("Number of random configured effects applied after mastery.")
            .defineInRange("spreadCountMastered", 5, 1, 256);
         this.spreadRange = builder.comment("Radius for spreading configured effects to nearby living entities.").defineInRange("spreadRange", 5.0, 0.0, 256.0);
         this.spreadSelfBaseDurationMin = builder.comment("Minimum base duration when shift-spreading effects to self.")
            .defineInRange("spreadSelfBaseDurationMin", 120, 1, 72000);
         this.spreadSelfBaseDurationRandom = builder.comment("Random extra duration bound when shift-spreading effects to self.")
            .defineInRange("spreadSelfBaseDurationRandom", 120, 1, 72000);
         this.spreadSelfMinimumDuration = builder.comment("Final minimum duration when shift-spreading effects to self.")
            .defineInRange("spreadSelfMinimumDuration", 40, 1, 72000);
         this.spreadTargetBaseDurationMin = builder.comment("Minimum base duration when spreading effects to targets.")
            .defineInRange("spreadTargetBaseDurationMin", 80, 1, 72000);
         this.spreadTargetBaseDurationRandom = builder.comment("Random extra duration bound when spreading effects to targets.")
            .defineInRange("spreadTargetBaseDurationRandom", 100, 1, 72000);
         this.spreadTargetMinimumDuration = builder.comment("Final minimum duration when spreading effects to targets.")
            .defineInRange("spreadTargetMinimumDuration", 20, 1, 72000);
         this.spreadDurationPerUpgrade = builder.comment("Duration multiplier added per duration upgrade level. 0.25 means +25% per level.")
            .defineInRange("spreadDurationPerUpgrade", 0.25, 0.0, 1000.0);
         this.spreadSelfPoisonParticles = builder.comment("Poison particles when shift-spreading to self.")
            .defineInRange("spreadSelfPoisonParticles", 40, 0, 10000);
         this.spreadTargetPoisonParticles = builder.comment("Poison particles around each spread target.")
            .defineInRange("spreadTargetPoisonParticles", 24, 0, 10000);
         this.spreadOwnerPoisonParticles = builder.comment("Poison particles around owner after normal spread.")
            .defineInRange("spreadOwnerPoisonParticles", 30, 0, 10000);
         this.spreadMushroomPatchAttempts = builder.comment("Mushroom placement attempts during spread.")
            .defineInRange("spreadMushroomPatchAttempts", 2, 0, 256);
         builder.pop();
         builder.push("Mushrooms");
         this.mushroomPatchSearchRadius = builder.comment("Horizontal random offset radius for mushroom patch placement. 2 means a 5x5 area.")
            .defineInRange("mushroomPatchSearchRadius", 2, 0, 32);
         this.mushroomPatchBlockFlags = builder.comment("Block update flags used when placing mushrooms. 3 updates clients and neighbors.")
            .defineInRange("mushroomPatchBlockFlags", 3, 1, 32);
         builder.pop();
      }
   }

   public static final class GeneEditorConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue normalExtractSkillCount;
      public final IntValue masteredExtractSkillCount;
      public final IntValue extractCooldownTicks;
      public final IntValue failedExtractCooldownTicks;
      public final IntValue emptySyringeCooldownTicks;
      public final DoubleValue extractMagiculeCost;
      public final DoubleValue archiveMagiculeCost;
      public final DoubleValue extractRange;
      public final DoubleValue copiedEpMultiplier;
      public final DoubleValue copiedMpMultiplier;
      public final DoubleValue copiedHpMultiplier;
      public final DoubleValue copiedShpMultiplier;
      public final BooleanValue blockNoPlunderingSkills;
      public final BooleanValue allowUniqueSkillCopies;
      public final BooleanValue allowUltimateSkillCopies;
      public final IntValue maxGenesPerSyringe;
      public final IntValue epStep;
      public final IntValue mpStep;
      public final IntValue hpStep;
      public final IntValue shpStep;
      public final DoubleValue injectedEpToAuraMultiplier;
      public final DoubleValue injectedKnockbackResistance;
      public final DoubleValue injectedMovementSpeed;
      public final DoubleValue injectedAttackDamage;
      public final DoubleValue injectedAttackSpeed;
      public final DoubleValue injectedPresenceSense;
      public final DoubleValue injectedChantSpeed;
      public final DoubleValue injectedProjectileDodgeChance;
      public final DoubleValue injectedResistanceDegradation;
      public final IntValue mobScanIntervalTicks;
      public final DoubleValue mobScanRadius;
      public final DoubleValue mobCastRange;
      public final IntValue mobCastCooldownTicks;
      public final IntValue mobToggleCheckTicks;
      public final IntValue mobModeCycleTicks;

      GeneEditorConfig(Builder builder) {
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.defineInRange("acquirementMagiculeCost", 350000.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("extract");
         this.normalExtractSkillCount = builder.defineInRange("normalExtractSkillCount", 4, 0, 64);
         this.masteredExtractSkillCount = builder.defineInRange("masteredExtractSkillCount", 7, 0, 64);
         this.extractCooldownTicks = builder.defineInRange("extractCooldownTicks", 5, 0, Integer.MAX_VALUE);
         this.failedExtractCooldownTicks = builder.defineInRange("failedExtractCooldownTicks", 20, 0, Integer.MAX_VALUE);
         this.emptySyringeCooldownTicks = builder.defineInRange("emptySyringeCooldownTicks", 20, 0, Integer.MAX_VALUE);
         this.extractMagiculeCost = builder.defineInRange("extractMagiculeCost", 1000.0, 0.0, Double.MAX_VALUE);
         this.archiveMagiculeCost = builder.defineInRange("archiveMagiculeCost", 0.0, 0.0, Double.MAX_VALUE);
         this.extractRange = builder.defineInRange("extractRange", 8.0, 1.0, 128.0);
         this.copiedEpMultiplier = builder.defineInRange("copiedEpMultiplier", 0.25, 0.0, 100.0);
         this.copiedMpMultiplier = builder.defineInRange("copiedMpMultiplier", 0.25, 0.0, 100.0);
         this.copiedHpMultiplier = builder.defineInRange("copiedHpMultiplier", 0.25, 0.0, 100.0);
         this.copiedShpMultiplier = builder.defineInRange("copiedShpMultiplier", 0.25, 0.0, 100.0);
         this.blockNoPlunderingSkills = builder.define("blockNoPlunderingSkills", true);
         this.allowUniqueSkillCopies = builder.define("allowUniqueSkillCopies", true);
         this.allowUltimateSkillCopies = builder.define("allowUltimateSkillCopies", false);
         builder.pop();
         builder.push("archive_menu");
         this.maxGenesPerSyringe = builder.defineInRange("maxGenesPerSyringe", 10, 1, 128);
         this.epStep = builder.defineInRange("epStep", 1000, 1, Integer.MAX_VALUE);
         this.mpStep = builder.defineInRange("mpStep", 1000, 1, Integer.MAX_VALUE);
         this.hpStep = builder.defineInRange("hpStep", 10, 1, Integer.MAX_VALUE);
         this.shpStep = builder.defineInRange("shpStep", 1000, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.push("injected_attributes");
         this.injectedEpToAuraMultiplier = builder.defineInRange("injectedEpToAuraMultiplier", 0.5, 0.0, 100.0);
         this.injectedKnockbackResistance = builder.defineInRange("injectedKnockbackResistance", 0.35, 0.0, 100.0);
         this.injectedMovementSpeed = builder.defineInRange("injectedMovementSpeed", 0.05, -1.0, 100.0);
         this.injectedAttackDamage = builder.defineInRange("injectedAttackDamage", 25.0, 0.0, 1000000.0);
         this.injectedAttackSpeed = builder.defineInRange("injectedAttackSpeed", 0.25, -10.0, 100.0);
         this.injectedPresenceSense = builder.defineInRange("injectedPresenceSense", 3.0, 0.0, 1000.0);
         this.injectedChantSpeed = builder.defineInRange("injectedChantSpeed", 1.0, 0.0, 1000.0);
         this.injectedProjectileDodgeChance = builder.defineInRange("injectedProjectileDodgeChance", 10.0, 0.0, 100.0);
         this.injectedResistanceDegradation = builder.defineInRange("injectedResistanceDegradation", 0.25, 0.0, 1.0);
         builder.pop();
         builder.push("mob_autocast");
         this.mobScanIntervalTicks = builder.defineInRange("mobScanIntervalTicks", 20, 1, 72000);
         this.mobScanRadius = builder.defineInRange("mobScanRadius", 96.0, 1.0, 512.0);
         this.mobCastRange = builder.defineInRange("mobCastRange", 48.0, 1.0, 512.0);
         this.mobCastCooldownTicks = builder.defineInRange("mobCastCooldownTicks", 60, 20, 72000);
         this.mobToggleCheckTicks = builder.defineInRange("mobToggleCheckTicks", 80, 1, 72000);
         this.mobModeCycleTicks = builder.defineInRange("mobModeCycleTicks", 100, 1, 72000);
         builder.pop();
      }
   }

   public static final class GodlyScientistConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final IntValue maxMastery;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final DoubleValue requiredEp;
      public final IntValue elementalEssenceRequired;
      public final IntValue dragonEssenceRequired;
      public final IntValue analysisLevel;
      public final IntValue analysisDistance;
      public final DoubleValue presenceSenseLevel;
      public final DoubleValue presenceSenseRadius;
      public final DoubleValue masteryMultiplier;
      public final IntValue spellMasteryRefreshTicks;
      public final IntValue masteryGainIntervalTicks;
      public final DoubleValue genesisCost;
      public final IntValue genesisCooldownTicks;
      public final DoubleValue divineIncrementCost;
      public final IntValue divineIncrementCooldownTicks;
      public final IntValue archiveMaximumPages;

      GodlyScientistConfig(Builder builder) {
         this.acquirementMagiculeCost = builder.defineInRange("acquirementMagiculeCost", 0.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.defineInRange("acquirementMastery", 0, 0, Integer.MAX_VALUE);
         this.maxMastery = builder.defineInRange("maxMastery", 1000, 1, Integer.MAX_VALUE);
         this.requiredSkillId = builder.define("requiredSkillId", "tensura:researcher");
         this.requiredSkillMustBeMastered = builder.define("requiredSkillMustBeMastered", true);
         this.requiredEp = builder.defineInRange("requiredEp", 10000.0, 0.0, Double.MAX_VALUE);
         this.elementalEssenceRequired = builder.defineInRange("elementalEssenceRequired", 5, 0, Integer.MAX_VALUE);
         this.dragonEssenceRequired = builder.defineInRange("dragonEssenceRequired", 5, 0, Integer.MAX_VALUE);
         this.analysisLevel = builder.defineInRange("analysisLevel", 500, 0, 1024);
         this.analysisDistance = builder.defineInRange("analysisDistance", 256, 0, 4096);
         this.presenceSenseLevel = builder.defineInRange("presenceSenseLevel", 500.0, 0.0, 1024.0);
         this.presenceSenseRadius = builder.defineInRange("presenceSenseRadius", 256.0, 0.0, 4096.0);
         this.masteryMultiplier = builder.defineInRange("masteryMultiplier", 3.0, 1.0, 1024.0);
         this.spellMasteryRefreshTicks = builder.defineInRange("spellMasteryRefreshTicks", 80, 1, Integer.MAX_VALUE);
         this.masteryGainIntervalTicks = builder.defineInRange("masteryGainIntervalTicks", 20, 1, Integer.MAX_VALUE);
         this.genesisCost = builder.defineInRange("genesisCost", 0.0, 0.0, Double.MAX_VALUE);
         this.genesisCooldownTicks = builder.defineInRange("genesisCooldownTicks", 100, 0, Integer.MAX_VALUE);
         this.divineIncrementCost = builder.defineInRange("divineIncrementCost", 0.0, 0.0, Double.MAX_VALUE);
         this.divineIncrementCooldownTicks = builder.defineInRange("divineIncrementCooldownTicks", 2, 0, Integer.MAX_VALUE);
         this.archiveMaximumPages = builder.defineInRange("archiveMaximumPages", Integer.MAX_VALUE, 1, Integer.MAX_VALUE);
      }
   }

   public static final class GodlySmitheryConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final IntValue maxMastery;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final DoubleValue requiredEp;
      public final DoubleValue divineConstructionCost;
      public final IntValue divineConstructionCooldownTicks;
      public final DoubleValue heavenlyForgeCost;
      public final IntValue heavenlyForgeCooldownTicks;
      public final IntValue heavenlyForgeStorageSlots;
      public final IntValue heavenlyForgeStorageStack;
      public final IntValue heavenlyForgeMaximumBonusLevel;
      public final IntValue randomCraftedEnchantments;
      public final DoubleValue soulBuffPerEssence;

      GodlySmitheryConfig(Builder builder) {
         this.acquirementMagiculeCost = builder.defineInRange("acquirementMagiculeCost", 0.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.defineInRange("acquirementMastery", 0, 0, Integer.MAX_VALUE);
         this.maxMastery = builder.defineInRange("maxMastery", 1000, 1, Integer.MAX_VALUE);
         this.requiredSkillId = builder.define("requiredSkillId", "tensuramoreskills:godly_scientist");
         this.requiredSkillMustBeMastered = builder.define("requiredSkillMustBeMastered", false);
         this.requiredEp = builder.defineInRange("requiredEp", 0.0, 0.0, Double.MAX_VALUE);
         this.divineConstructionCost = builder.defineInRange("divineConstructionCost", 0.0, 0.0, Double.MAX_VALUE);
         this.divineConstructionCooldownTicks = builder.defineInRange("divineConstructionCooldownTicks", 500, 0, Integer.MAX_VALUE);
         this.heavenlyForgeCost = builder.defineInRange("heavenlyForgeCost", 0.0, 0.0, Double.MAX_VALUE);
         this.heavenlyForgeCooldownTicks = builder.defineInRange("heavenlyForgeCooldownTicks", 2, 0, Integer.MAX_VALUE);
         this.heavenlyForgeStorageSlots = builder.defineInRange("heavenlyForgeStorageSlots", 54, 9, 216);
         this.heavenlyForgeStorageStack = builder.defineInRange("heavenlyForgeStorageStack", 999, 1, 999);
         this.heavenlyForgeMaximumBonusLevel = builder.defineInRange("heavenlyForgeMaximumBonusLevel", 255, 1, Integer.MAX_VALUE);
         this.randomCraftedEnchantments = builder.defineInRange("randomCraftedEnchantments", 6, 0, 256);
         this.soulBuffPerEssence = builder.defineInRange("soulBuffPerEssence", 0.01, 0.0, Double.MAX_VALUE);
      }
   }

   public static final class GrandConfig {
      public final MoreSkillsConfigs.WitchOfVainglory witchOfVainglory;
      public final MoreSkillsConfigs.ParasiteConfig parasite;
      public final MoreSkillsConfigs.MichaelAngelOfLocksConfig michaelAngelOfLocks;
      public final MoreSkillsConfigs.WitchOfEnvySatellaConfig witchOfEnvySatella;
      public final MoreSkillsConfigs.GreenThumbConfig greenThumb;
      public final MoreSkillsConfigs.ArthurConfig arthur;
      public final MoreSkillsConfigs.AvalonConfig avalon;
      public final MoreSkillsConfigs.AbhothLordOfPestilenceConfig abhothLordOfPestilence;
      public final MoreSkillsConfigs.IWantToBeJustLikeYouConfig iWantToBeJustLikeYou;
      public final MoreSkillsConfigs.ExploiterConfig exploiter;
      public final MoreSkillsConfigs.SatellaConfig satella;
      public final MoreSkillsConfigs.NirvanaConfig nirvana;
      public final MoreSkillsConfigs.SchwiExMachina schwiExMachina;
      public final MoreSkillsConfigs.TrueAuthorityOfGreedConfig trueAuthorityOfGreed;
      public final MoreSkillsConfigs.AlbisVinaConfig albisVina;
      public final MoreSkillsConfigs.GeneEditorConfig geneEditor;
      public final MoreSkillsConfigs.PainLordOfSixPaths painLordOfSixPaths;
      public final MoreSkillsConfigs.MammonCasino mammonCasino;
      public final MoreSkillsConfigs.DivineGeneralMahoraga divineGeneralMahoraga;
      public final MoreSkillsConfigs.KronairosLordOfTheEndless kronairosLordOfTheEndless;
      public final MoreSkillsConfigs.CaedrosGodOfConquest caedrosGodOfConquest;
      public final MoreSkillsConfigs.AxiomConfig axiomLordOfReflectedJudgment;
      public final MoreSkillsConfigs.RonovaConfig ronova;
      public final MoreSkillsConfigs.AsmodayConfig asmoday;
      public final MoreSkillsConfigs.GodlyScientistConfig godlyScientist;
      public final MoreSkillsConfigs.GodlySmitheryConfig godlySmithery;
      public final MoreSkillsConfigs.IstarothConfig istaroth;
      public final MoreSkillsConfigs.ElfariaTheHallowedIcemaidenConfig elfariaTheHallowedIcemaiden;
      public final MoreSkillsConfigs.NaberiusConfig naberius;
      public final MoreSkillsConfigs.AdaptabilityConfig adaptability;
      public final MoreSkillsConfigs.MorphConfig morph;
      public final MoreSkillsConfigs.MitamaFaithConfig mitamaFaith;
      public final MoreSkillsConfigs.AuthorityOfGreedConfig authorityOfGreed;
      public final MoreSkillsConfigs.BoogieWoogieConfig boogieWoogie;
      public final MoreSkillsConfigs.DomainOfNegationConfig domainOfNegation;
      public final MoreSkillsConfigs.FungusInfectionConfig fungusInfection;

      GrandConfig(Builder builder) {
         builder.comment(
            "Grand balance config for Tensura More Skills. All major obtainment, cost, cooldown, range, duration, damage, revive, and multiplier values should live here instead of being hardcoded inside skills. TO NAVIGATE THE CONFIG PRESS CONTROL+F THEN TYPE THE WANTED SKILL"
         );
         builder.push("witch_of_vainglory");
         this.witchOfVainglory = new MoreSkillsConfigs.WitchOfVainglory(builder);
         builder.pop();
         builder.push("schwi_ex_machina");
         this.schwiExMachina = new MoreSkillsConfigs.SchwiExMachina(builder);
         builder.pop();
         builder.push("mammon_casino");
         this.mammonCasino = new MoreSkillsConfigs.MammonCasino(builder);
         builder.pop();
         builder.push("divine_general_mahoraga");
         this.divineGeneralMahoraga = new MoreSkillsConfigs.DivineGeneralMahoraga(builder);
         builder.pop();
         builder.push("kronairos_lord_of_the_endless");
         this.kronairosLordOfTheEndless = new MoreSkillsConfigs.KronairosLordOfTheEndless(builder);
         builder.pop();
         builder.push("caedros_god_of_conquest");
         this.caedrosGodOfConquest = new MoreSkillsConfigs.CaedrosGodOfConquest(builder);
         builder.pop();
         builder.push("axiom_lord_of_reflected_judgment");
         this.axiomLordOfReflectedJudgment = new MoreSkillsConfigs.AxiomConfig(builder);
         builder.pop();
         builder.push("istaroth");
         this.istaroth = new MoreSkillsConfigs.IstarothConfig(builder);
         builder.pop();
         builder.push("asmoday");
         this.asmoday = new MoreSkillsConfigs.AsmodayConfig(builder);
         builder.pop();
         builder.push("naberius");
         this.naberius = new MoreSkillsConfigs.NaberiusConfig(builder);
         builder.pop();
         this.adaptability = new MoreSkillsConfigs.AdaptabilityConfig(builder);
         builder.push("authority_of_greed");
         this.authorityOfGreed = new MoreSkillsConfigs.AuthorityOfGreedConfig(builder);
         builder.pop();
         builder.push("boogie_woogie");
         this.boogieWoogie = new MoreSkillsConfigs.BoogieWoogieConfig(builder);
         builder.pop();
         builder.push("domain_of_negation");
         this.domainOfNegation = new MoreSkillsConfigs.DomainOfNegationConfig(builder);
         builder.pop();
         builder.push("fungus_infection");
         this.fungusInfection = new MoreSkillsConfigs.FungusInfectionConfig(builder);
         builder.pop();
         builder.push("ronova");
         this.ronova = new MoreSkillsConfigs.RonovaConfig(builder);
         builder.pop();
         builder.push("morph");
         this.morph = new MoreSkillsConfigs.MorphConfig(builder);
         builder.pop();
         builder.push("albis_vina");
         this.albisVina = new MoreSkillsConfigs.AlbisVinaConfig(builder);
         builder.pop();
         builder.push("elfaria_the_hallowed_icemaiden");
         this.elfariaTheHallowedIcemaiden = new MoreSkillsConfigs.ElfariaTheHallowedIcemaidenConfig(builder);
         builder.pop();
         builder.push("mitama_faith");
         this.mitamaFaith = new MoreSkillsConfigs.MitamaFaithConfig(builder);
         builder.pop();
         builder.push("abhoth_lord_of_pestilence");
         this.abhothLordOfPestilence = new MoreSkillsConfigs.AbhothLordOfPestilenceConfig(builder);
         builder.pop();
         builder.push("exploiter");
         this.exploiter = new MoreSkillsConfigs.ExploiterConfig(builder);
         builder.pop();
         builder.push("pain_lord_of_six_paths");
         this.painLordOfSixPaths = new MoreSkillsConfigs.PainLordOfSixPaths(builder);
         builder.pop();
         builder.push("gene_editor");
         this.geneEditor = new MoreSkillsConfigs.GeneEditorConfig(builder);
         builder.pop();
         builder.push("true_authority_of_greed");
         this.trueAuthorityOfGreed = new MoreSkillsConfigs.TrueAuthorityOfGreedConfig(builder);
         builder.pop();
         builder.push("nirvana");
         this.nirvana = new MoreSkillsConfigs.NirvanaConfig(builder);
         builder.pop();
         builder.push("i_want_to_be_just_like_you");
         this.iWantToBeJustLikeYou = new MoreSkillsConfigs.IWantToBeJustLikeYouConfig(builder);
         builder.pop();
         builder.push("green_thumb");
         this.greenThumb = new MoreSkillsConfigs.GreenThumbConfig(builder);
         builder.pop();
         builder.push("michael_angel_of_locks");
         this.michaelAngelOfLocks = new MoreSkillsConfigs.MichaelAngelOfLocksConfig(builder);
         builder.pop();
         builder.push("satella");
         this.satella = new MoreSkillsConfigs.SatellaConfig(builder);
         builder.pop();
         builder.push("witch_of_envy_satella");
         this.witchOfEnvySatella = new MoreSkillsConfigs.WitchOfEnvySatellaConfig(builder);
         builder.pop();
         builder.push("parasite");
         this.parasite = new MoreSkillsConfigs.ParasiteConfig(builder);
         builder.pop();
         builder.push("godly_scientist");
         this.godlyScientist = new MoreSkillsConfigs.GodlyScientistConfig(builder);
         builder.pop();
         builder.push("godly_smithery");
         this.godlySmithery = new MoreSkillsConfigs.GodlySmitheryConfig(builder);
         builder.pop();
         builder.push("arthur_once_and_future_king");
         this.arthur = new MoreSkillsConfigs.ArthurConfig(builder);
         builder.pop();
         builder.push("avalon_god_of_eternal_kingdom");
         this.avalon = new MoreSkillsConfigs.AvalonConfig(builder);
         builder.pop();
      }
   }

   public static final class GreenThumbConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final IntValue blessingCost;
      public final IntValue growthCost;
      public final IntValue offeringCost;
      public final IntValue blessingCooldownTicks;
      public final IntValue growthCooldownTicks;
      public final IntValue offeringCooldownTicks;
      public final DoubleValue growthRange;
      public final IntValue offeringDurationTicks;
      public final IntValue offeringRegenerationAmplifier;
      public final IntValue offeringHealthcareAmplifier;
      public final IntValue offeringMagiculeRegenerationAmplifier;
      public final IntValue offeringHakiCoatAmplifier;
      public final IntValue eatenEpChancePercent;
      public final IntValue eatenEpMinimum;
      public final IntValue eatenEpMaximum;

      GreenThumbConfig(Builder builder) {
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.defineInRange("acquirementMagiculeCost", 25000.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.defineInRange("acquirementMastery", -1, -1, Integer.MAX_VALUE);
         builder.pop();
         builder.push("costs");
         this.blessingCost = builder.defineInRange("blessingCost", 50, 0, Integer.MAX_VALUE);
         this.growthCost = builder.defineInRange("growthCost", 100, 0, Integer.MAX_VALUE);
         this.offeringCost = builder.defineInRange("offeringCost", 250, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("cooldowns");
         this.blessingCooldownTicks = builder.defineInRange("blessingCooldownTicks", 7, 0, Integer.MAX_VALUE);
         this.growthCooldownTicks = builder.defineInRange("growthCooldownTicks", 4, 0, Integer.MAX_VALUE);
         this.offeringCooldownTicks = builder.defineInRange("offeringCooldownTicks", 100, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("growth");
         this.growthRange = builder.defineInRange("growthRange", 6.0, 1.0, 64.0);
         builder.pop();
         builder.push("offering");
         this.offeringDurationTicks = builder.defineInRange("offeringDurationTicks", 1200, 1, Integer.MAX_VALUE);
         this.offeringRegenerationAmplifier = builder.defineInRange("offeringRegenerationAmplifier", 1, 0, 255);
         this.offeringHealthcareAmplifier = builder.defineInRange("offeringHealthcareAmplifier", 0, 0, 255);
         this.offeringMagiculeRegenerationAmplifier = builder.defineInRange("offeringMagiculeRegenerationAmplifier", 1, 0, 255);
         this.offeringHakiCoatAmplifier = builder.defineInRange("offeringHakiCoatAmplifier", 0, 0, 255);
         builder.pop();
         builder.push("passive");
         this.eatenEpChancePercent = builder.defineInRange("eatenEpChancePercent", 100, 0, 100);
         this.eatenEpMinimum = builder.defineInRange("eatenEpMinimum", -3, -1000000, 1000000);
         this.eatenEpMaximum = builder.defineInRange("eatenEpMaximum", 5, -1000000, 1000000);
         builder.pop();
      }
   }

   public static final class IWantToBeJustLikeYouConfig {
      public final IntValue maxCards;
      public final IntValue commonWeight;
      public final IntValue extraIntrinsicWeight;
      public final IntValue uniqueWeight;
      public final IntValue limboInvulnerabilityTicks;
      public final IntValue reviveInvulnerabilityTicks;
      public final DoubleValue reviveHealthRatio;
      public final IntValue cardRevealTicks;
      public final IntValue limboRoomX;
      public final IntValue limboRoomY;
      public final IntValue limboRoomZ;

      IWantToBeJustLikeYouConfig(Builder builder) {
         this.maxCards = builder.defineInRange("maxCards", 3, 1, 5);
         this.commonWeight = builder.defineInRange("commonWeight", 100, 0, Integer.MAX_VALUE);
         this.extraIntrinsicWeight = builder.defineInRange("extraIntrinsicWeight", 45, 0, Integer.MAX_VALUE);
         this.uniqueWeight = builder.defineInRange("uniqueWeight", 2, 0, Integer.MAX_VALUE);
         this.limboInvulnerabilityTicks = builder.defineInRange("limboInvulnerabilityTicks", 200, 20, Integer.MAX_VALUE);
         this.reviveInvulnerabilityTicks = builder.defineInRange("reviveInvulnerabilityTicks", 100, 0, Integer.MAX_VALUE);
         this.reviveHealthRatio = builder.defineInRange("reviveHealthRatio", 0.65, 0.01, 1.0);
         this.cardRevealTicks = builder.defineInRange("cardRevealTicks", 90, 20, 400);
         this.limboRoomX = builder.defineInRange("limboRoomX", 0, -29999000, 29999000);
         this.limboRoomY = builder.defineInRange("limboRoomY", -32, -2032, 2032);
         this.limboRoomZ = builder.defineInRange("limboRoomZ", 0, -29999000, 29999000);
      }
   }

   public static final class IstarothConfig {
      public final IntValue maxMastery;
      public final IntValue masteryTickInterval;
      public final DoubleValue chronicleCost;
      public final DoubleValue timeSkipCost;
      public final DoubleValue timeSkipCostMastered;
      public final DoubleValue durationCost;
      public final DoubleValue durationCostMastered;
      public final DoubleValue stasisCost;
      public final DoubleValue stasisCostMastered;
      public final DoubleValue rewindCost;
      public final DoubleValue rewindCostMastered;
      public final DoubleValue causalityCost;
      public final DoubleValue causalityCostMastered;
      public final DoubleValue loopCost;
      public final DoubleValue loopCostMastered;
      public final DoubleValue banishmentCost;
      public final DoubleValue banishmentCostMastered;
      public final DoubleValue worldOfStillSecondsCost;
      public final DoubleValue worldOfStillSecondsCostMastered;
      public final DoubleValue lastSecondCost;
      public final DoubleValue lastSecondCostMastered;
      public final IntValue chronicleCooldownTicks;
      public final IntValue chronicleAnchorCooldownTicks;
      public final IntValue timeSkipCooldownTicks;
      public final IntValue momentumTheftCooldownTicks;
      public final IntValue borrowedSecondsCooldownTicks;
      public final IntValue eternalMomentCooldownTicks;
      public final IntValue stasisCooldownTicks;
      public final IntValue objectPermanenceCooldownTicks;
      public final IntValue rewindSelfCooldownTicks;
      public final IntValue rewindTargetCooldownTicks;
      public final IntValue delayedVerdictCooldownTicks;
      public final IntValue effectBeforeCauseCooldownTicks;
      public final IntValue loopCooldownTicks;
      public final IntValue condemnedRepeatCooldownTicks;
      public final IntValue futureExileCooldownTicks;
      public final IntValue pastBurialCooldownTicks;
      public final IntValue worldStopReleaseCooldownTicks;
      public final IntValue worldStopReleaseCooldownTicksMastered;
      public final IntValue lastSecondCooldownTicks;
      public final DoubleValue chronoMoveSpeed;
      public final DoubleValue chronoMoveSpeedMastered;
      public final DoubleValue chronoAttackSpeed;
      public final DoubleValue chronoAttackSpeedMastered;
      public final DoubleValue chronoFlyingSpeed;
      public final DoubleValue chronoFlyingSpeedMastered;
      public final DoubleValue chronoChantSpeed;
      public final DoubleValue chronoChantSpeedMastered;
      public final DoubleValue chronoPresenceSense;
      public final DoubleValue chronoPresenceSenseMastered;
      public final DoubleValue chronoDodgeInvulnerability;
      public final DoubleValue chronoDodgeInvulnerabilityMastered;
      public final DoubleValue chronoResistanceDegradation;
      public final DoubleValue chronoResistanceDegradationMastered;
      public final DoubleValue predictionMeleeDodge;
      public final DoubleValue predictionMeleeDodgeMastered;
      public final DoubleValue predictionProjectileDodge;
      public final DoubleValue predictionProjectileDodgeMastered;
      public final DoubleValue outsideStreamLawDegradation;
      public final DoubleValue outsideStreamSpaceDegradation;
      public final DoubleValue chronicleRadius;
      public final DoubleValue timeSkipDistance;
      public final DoubleValue timeSkipDistanceMastered;
      public final DoubleValue timeSkipPathInflate;
      public final DoubleValue timeSkipPathInflateMastered;
      public final DoubleValue timeSkipDamage;
      public final DoubleValue timeSkipDamageMastered;
      public final DoubleValue timeSkipVelocityBonusCap;
      public final DoubleValue timeSkipVelocityBonusMultiplier;
      public final DoubleValue timeSkipPlayerDamageMultiplier;
      public final DoubleValue timeSkipDebtMultiplier;
      public final IntValue timeSkipProjectileFreezeTicks;
      public final IntValue timeSkipProjectileFreezeTicksMastered;
      public final IntValue timeSkipActionLockTicks;
      public final IntValue timeSkipActionLockTicksMastered;
      public final IntValue timeSkipStutterTicks;
      public final IntValue timeSkipFutureRejectionTicks;
      public final DoubleValue momentumTheftRange;
      public final DoubleValue momentumTheftRangeMastered;
      public final DoubleValue momentumTheftBoxInflate;
      public final DoubleValue momentumTheftBoxInflateMastered;
      public final DoubleValue momentumTheftViewDot;
      public final DoubleValue momentumTheftBaseDamage;
      public final DoubleValue momentumTheftDamageCap;
      public final DoubleValue momentumTheftDamageCapMastered;
      public final DoubleValue momentumTheftDamageMultiplier;
      public final DoubleValue momentumTheftPlayerDamageMultiplier;
      public final DoubleValue momentumTheftDebtMultiplier;
      public final DoubleValue momentumTheftDebtBonusMultiplier;
      public final IntValue momentumTheftProjectileFreezeTicks;
      public final IntValue momentumTheftProjectileFreezeTicksMastered;
      public final IntValue momentumTheftActionLockTicks;
      public final IntValue momentumTheftStutterTicks;
      public final IntValue momentumTheftMovementInterferenceTicks;
      public final DoubleValue borrowedSecondsRadius;
      public final DoubleValue borrowedSecondsRadiusMastered;
      public final IntValue borrowedSecondsDecayAmount;
      public final IntValue borrowedSecondsDecayAmountMastered;
      public final DoubleValue borrowedSecondsDamage;
      public final DoubleValue borrowedSecondsDamageMastered;
      public final DoubleValue borrowedSecondsMobPercentDamage;
      public final DoubleValue borrowedSecondsPlayerPercentDamage;
      public final DoubleValue borrowedSecondsDebtBonus;
      public final DoubleValue borrowedSecondsDebtBonusMastered;
      public final IntValue borrowedSecondsEffectTicks;
      public final IntValue borrowedSecondsEventSilenceTicks;
      public final IntValue borrowedSecondsSelfExtendTicks;
      public final IntValue borrowedSecondsSelfExtendTicksMastered;
      public final IntValue borrowedSecondsRemoveHarmful;
      public final IntValue borrowedSecondsRemoveHarmfulMastered;
      public final IntValue borrowedSecondsCooldownReduction;
      public final IntValue borrowedSecondsCooldownReductionMastered;
      public final IntValue eternalMomentTicks;
      public final IntValue eternalMomentTicksMastered;
      public final IntValue eternalMomentSelfExtendTicks;
      public final IntValue eternalMomentSelfExtendTicksMastered;
      public final IntValue eternalMomentRemoveHarmful;
      public final IntValue eternalMomentRemoveHarmfulMastered;
      public final DoubleValue eternalMomentRadius;
      public final DoubleValue eternalMomentRadiusMastered;
      public final DoubleValue eternalMomentDamage;
      public final DoubleValue eternalMomentDamageMastered;
      public final DoubleValue eternalMomentPlayerDamageMultiplier;
      public final IntValue eternalMomentStutterTicks;
      public final DoubleValue objectPermanenceRange;
      public final DoubleValue objectPermanenceRangeMastered;
      public final IntValue objectPermanencePlayerTicks;
      public final IntValue objectPermanenceTicks;
      public final IntValue objectPermanenceTicksMastered;
      public final IntValue objectPermanenceActionLockCap;
      public final DoubleValue objectPermanenceDebt;
      public final DoubleValue objectPermanenceDebtMastered;
      public final DoubleValue rewindTargetRange;
      public final DoubleValue rewindTargetRangeMastered;
      public final IntValue rewindTargetSnapshotTicks;
      public final IntValue rewindTargetSnapshotTicksMastered;
      public final DoubleValue rewindTargetDamage;
      public final DoubleValue rewindTargetDamageMastered;
      public final DoubleValue rewindTargetPlayerDamageMultiplier;
      public final DoubleValue rewindTargetDebtBonus;
      public final IntValue rewindTargetActionLockTicks;
      public final IntValue rewindTargetStutterTicks;
      public final DoubleValue delayedVerdictRange;
      public final DoubleValue delayedVerdictRangeMastered;
      public final IntValue delayedVerdictTicks;
      public final IntValue delayedVerdictTicksMastered;
      public final DoubleValue delayedVerdictDamage;
      public final DoubleValue delayedVerdictDamageMastered;
      public final DoubleValue delayedVerdictPlayerDamageMultiplier;
      public final DoubleValue delayedVerdictStoredMultiplier;
      public final DoubleValue delayedVerdictDebtBonus;
      public final DoubleValue delayedVerdictDebtBonusMastered;
      public final DoubleValue effectBeforeCauseRange;
      public final DoubleValue effectBeforeCauseRangeMastered;
      public final DoubleValue effectBeforeCauseBaseDamage;
      public final DoubleValue effectBeforeCauseBaseDamageMastered;
      public final DoubleValue effectBeforeCauseMobPercentDamage;
      public final DoubleValue effectBeforeCausePlayerPercentDamage;
      public final DoubleValue effectBeforeCauseResistanceBase;
      public final DoubleValue effectBeforeCauseResistanceMultiplier;
      public final DoubleValue effectBeforeCausePlayerDamageMultiplier;
      public final DoubleValue effectBeforeCauseDebtMultiplier;
      public final DoubleValue effectBeforeCauseSplashRadius;
      public final DoubleValue effectBeforeCauseSplashRadiusMastered;
      public final DoubleValue effectBeforeCauseSplashMobMultiplier;
      public final DoubleValue effectBeforeCauseSplashPlayerMultiplier;
      public final DoubleValue loopRadius;
      public final IntValue loopRecordTicks;
      public final IntValue loopRecordTicksMastered;
      public final IntValue loopPlaybackTicks;
      public final IntValue loopPlaybackTicksMastered;
      public final DoubleValue loopDamage;
      public final DoubleValue loopDamageMastered;
      public final DoubleValue loopPlayerDamageMultiplier;
      public final DoubleValue loopDebtBonus;
      public final DoubleValue condemnedRepeatRange;
      public final DoubleValue condemnedRepeatRangeMastered;
      public final IntValue condemnedRepeatCharges;
      public final IntValue condemnedRepeatChargesMastered;
      public final IntValue condemnedRepeatTicks;
      public final IntValue condemnedRepeatTicksMastered;
      public final DoubleValue condemnedRepeatDamage;
      public final DoubleValue condemnedRepeatDamageMastered;
      public final DoubleValue condemnedRepeatPlayerDamageMultiplier;
      public final DoubleValue condemnedRepeatDebtBonus;
      public final DoubleValue futureExileRange;
      public final DoubleValue futureExileRangeMastered;
      public final IntValue futureExilePlayerTicks;
      public final IntValue futureExileTicks;
      public final IntValue futureExileTicksMastered;
      public final DoubleValue futureExileResistanceThreshold;
      public final DoubleValue futureExileDebt;
      public final DoubleValue futureExileDebtMastered;
      public final DoubleValue pastBurialRange;
      public final DoubleValue pastBurialRangeMastered;
      public final IntValue pastBurialTicks;
      public final IntValue pastBurialPower;
      public final IntValue pastBurialPowerMastered;
      public final IntValue pastBurialActionLockTicks;
      public final DoubleValue pastBurialDebt;
      public final DoubleValue pastBurialDebtMastered;
      public final IntValue stasisReleaseBaseDamage;
      public final DoubleValue stasisStoredDamageMultiplier;
      public final DoubleValue stasisStoredDamageMultiplierMastered;
      public final DoubleValue stasisHitDamage;
      public final DoubleValue stasisHitDamageMastered;
      public final DoubleValue stasisPlayerDamageMultiplier;
      public final IntValue chronologyDenialCooldownTicks;
      public final IntValue chronologyDenialSnapshotTicks;
      public final IntValue chronologyDenialInvulnerableTicks;
      public final IntValue worldStopMaxHeldTicks;
      public final IntValue worldStopMaxHeldTicksMastered;
      public final IntValue worldStopRadius;
      public final IntValue worldStopRadiusMastered;
      public final IntValue worldStopProjectileFreezeTicks;
      public final DoubleValue clockbreakBaseDamage;
      public final DoubleValue clockbreakBaseDamageMastered;
      public final DoubleValue clockbreakHeldPowerMultiplier;
      public final DoubleValue clockbreakHeldPowerMultiplierMastered;
      public final DoubleValue clockbreakHeldPowerCap;
      public final DoubleValue clockbreakProjectilePowerCap;
      public final DoubleValue clockbreakProjectilePowerMultiplier;
      public final DoubleValue clockbreakMobMaxHealthDamage;
      public final DoubleValue clockbreakPlayerMaxHealthDamage;
      public final DoubleValue clockbreakPlayerDamageMultiplier;
      public final IntValue doomTornadoMinTicks;
      public final DoubleValue doomTornadoMinRadius;
      public final IntValue doomTornadoEndCleanupLockTicks;
      public final IntValue doomTornadoDetonateCleanupLockTicks;
      public final DoubleValue doomTornadoSendStartRangeMultiplier;
      public final DoubleValue doomTornadoFinalLaunchForceBase;
      public final DoubleValue doomTornadoFinalLaunchForceCap;
      public final DoubleValue doomTornadoFinalLaunchForceRadiusMultiplier;
      public final DoubleValue doomTornadoFinalLaunchLiftBase;
      public final DoubleValue doomTornadoFinalLaunchLiftCap;
      public final DoubleValue doomTornadoFinalLaunchLiftRadiusMultiplier;
      public final DoubleValue doomTornadoFinalLaunchTangentForce;
      public final IntValue doomTornadoFinalTemporalStutterTicks;
      public final IntValue doomTornadoFinalTemporalStutterAmplifier;
      public final IntValue doomTornadoFinalStillSecondsTicks;
      public final IntValue doomTornadoFinalStillSecondsAmplifier;
      public final IntValue lastSecondTicks;
      public final IntValue lastSecondRadius;
      public final IntValue lastSecondCollapseRadius;
      public final DoubleValue lastSecondBaseDamage;
      public final DoubleValue lastSecondBaseDamageMastered;
      public final DoubleValue lastSecondDebtMultiplier;
      public final DoubleValue lastSecondPrimaryDebtMultiplier;
      public final DoubleValue lastSecondForceMultiplier;
      public final DoubleValue lastSecondPrimaryForceMultiplier;
      public final DoubleValue lastSecondForceCap;
      public final DoubleValue lastSecondPrimaryForceCap;
      public final DoubleValue lastSecondMobMaxHealthDamage;
      public final DoubleValue lastSecondPlayerMaxHealthDamage;
      public final DoubleValue lastSecondPlayerDamageMultiplier;
      public final DoubleValue lastSecondPrimaryMobMultiplier;
      public final DoubleValue lastSecondPrimaryPlayerMultiplier;

      IstarothConfig(Builder builder) {
         builder.push("mastery");
         this.maxMastery = i(
            builder, "maxMastery", 6000, 1, Integer.MAX_VALUE, "Maximum mastery value for Istaroth.", "Higher values make mastery take longer to finish."
         );
         this.masteryTickInterval = i(
            builder,
            "masteryTickInterval",
            140,
            1,
            Integer.MAX_VALUE,
            "How often mastery progresses, in ticks.",
            "20 ticks = 1 second. Default 140 = 7 seconds."
         );
         builder.pop();
         builder.push("costs");
         this.chronicleCost = d(builder, "chronicleCost", 650.0, 0.0, Double.MAX_VALUE, "Magicule cost for Chronicle, the information/status reading mode.");
         this.timeSkipCost = d(builder, "timeSkipCost", 3200.0, 0.0, Double.MAX_VALUE, "Magicule cost for Time Skip before mastery.");
         this.timeSkipCostMastered = d(builder, "timeSkipCostMastered", 2200.0, 0.0, Double.MAX_VALUE, "Magicule cost for Time Skip after mastery.");
         this.durationCost = d(
            builder,
            "durationCost",
            5200.0,
            0.0,
            Double.MAX_VALUE,
            "Magicule cost for duration-manipulation moves like Borrowed Seconds and Eternal Moment before mastery."
         );
         this.durationCostMastered = d(
            builder, "durationCostMastered", 3600.0, 0.0, Double.MAX_VALUE, "Magicule cost for duration-manipulation moves after mastery."
         );
         this.stasisCost = d(builder, "stasisCost", 6800.0, 0.0, Double.MAX_VALUE, "Magicule cost for Object Permanence / stasis style moves before mastery.");
         this.stasisCostMastered = d(
            builder, "stasisCostMastered", 4600.0, 0.0, Double.MAX_VALUE, "Magicule cost for Object Permanence / stasis style moves after mastery."
         );
         this.rewindCost = d(builder, "rewindCost", 9500.0, 0.0, Double.MAX_VALUE, "Magicule cost for rewind effects before mastery.");
         this.rewindCostMastered = d(builder, "rewindCostMastered", 7000.0, 0.0, Double.MAX_VALUE, "Magicule cost for rewind effects after mastery.");
         this.causalityCost = d(
            builder,
            "causalityCost",
            9000.0,
            0.0,
            Double.MAX_VALUE,
            "Magicule cost for causality attacks like Delayed Verdict, Effect Before Cause, and Condemned Repeat before mastery."
         );
         this.causalityCostMastered = d(builder, "causalityCostMastered", 6500.0, 0.0, Double.MAX_VALUE, "Magicule cost for causality attacks after mastery.");
         this.loopCost = d(builder, "loopCost", 23000.0, 0.0, Double.MAX_VALUE, "Magicule cost for Twenty-Second Causality Loop before mastery.");
         this.loopCostMastered = d(builder, "loopCostMastered", 16000.0, 0.0, Double.MAX_VALUE, "Magicule cost for Twenty-Second Causality Loop after mastery.");
         this.banishmentCost = d(builder, "banishmentCost", 18000.0, 0.0, Double.MAX_VALUE, "Magicule cost for Future Exile / Past Burial before mastery.");
         this.banishmentCostMastered = d(
            builder, "banishmentCostMastered", 12500.0, 0.0, Double.MAX_VALUE, "Magicule cost for Future Exile / Past Burial after mastery."
         );
         this.worldOfStillSecondsCost = d(
            builder, "worldOfStillSecondsCost", 1700.0, 0.0, Double.MAX_VALUE, "Magicule cost per use/tick cycle for World of Still Seconds before mastery."
         );
         this.worldOfStillSecondsCostMastered = d(
            builder,
            "worldOfStillSecondsCostMastered",
            1200.0,
            0.0,
            Double.MAX_VALUE,
            "Magicule cost per use/tick cycle for World of Still Seconds after mastery."
         );
         this.lastSecondCost = d(builder, "lastSecondCost", 190000.0, 0.0, Double.MAX_VALUE, "Magicule cost for Last Second of Creation before mastery.");
         this.lastSecondCostMastered = d(
            builder, "lastSecondCostMastered", 110000.0, 0.0, Double.MAX_VALUE, "Magicule cost for Last Second of Creation after mastery."
         );
         builder.pop();
         builder.push("cooldowns");
         this.chronicleCooldownTicks = i(builder, "chronicleCooldownTicks", 8, 0, Integer.MAX_VALUE, "Cooldown for Chronicle in ticks.");
         this.chronicleAnchorCooldownTicks = i(
            builder, "chronicleAnchorCooldownTicks", 24, 0, Integer.MAX_VALUE, "Cooldown for manually saving a rewind anchor in ticks."
         );
         this.timeSkipCooldownTicks = i(builder, "timeSkipCooldownTicks", 6, 0, Integer.MAX_VALUE, "Cooldown for Time Skip in ticks.");
         this.momentumTheftCooldownTicks = i(builder, "momentumTheftCooldownTicks", 11, 0, Integer.MAX_VALUE, "Cooldown for Momentum Theft in ticks.");
         this.borrowedSecondsCooldownTicks = i(builder, "borrowedSecondsCooldownTicks", 24, 0, Integer.MAX_VALUE, "Cooldown for Borrowed Seconds in ticks.");
         this.eternalMomentCooldownTicks = i(builder, "eternalMomentCooldownTicks", 55, 0, Integer.MAX_VALUE, "Cooldown for Eternal Moment in ticks.");
         this.stasisCooldownTicks = i(builder, "stasisCooldownTicks", 30, 0, Integer.MAX_VALUE, "Cooldown for stasis-category moves in ticks.");
         this.objectPermanenceCooldownTicks = i(builder, "objectPermanenceCooldownTicks", 42, 0, Integer.MAX_VALUE, "Cooldown for Object Permanence in ticks.");
         this.rewindSelfCooldownTicks = i(builder, "rewindSelfCooldownTicks", 38, 0, Integer.MAX_VALUE, "Cooldown for self rewind in ticks.");
         this.rewindTargetCooldownTicks = i(builder, "rewindTargetCooldownTicks", 55, 0, Integer.MAX_VALUE, "Cooldown for target rewind in ticks.");
         this.delayedVerdictCooldownTicks = i(builder, "delayedVerdictCooldownTicks", 34, 0, Integer.MAX_VALUE, "Cooldown for Delayed Verdict in ticks.");
         this.effectBeforeCauseCooldownTicks = i(
            builder, "effectBeforeCauseCooldownTicks", 24, 0, Integer.MAX_VALUE, "Cooldown for Effect Before Cause in ticks."
         );
         this.loopCooldownTicks = i(builder, "loopCooldownTicks", 100, 0, Integer.MAX_VALUE, "Cooldown for Twenty-Second Causality Loop in ticks.");
         this.condemnedRepeatCooldownTicks = i(builder, "condemnedRepeatCooldownTicks", 70, 0, Integer.MAX_VALUE, "Cooldown for Condemned Repeat in ticks.");
         this.futureExileCooldownTicks = i(builder, "futureExileCooldownTicks", 75, 0, Integer.MAX_VALUE, "Cooldown for Future Exile in ticks.");
         this.pastBurialCooldownTicks = i(builder, "pastBurialCooldownTicks", 42, 0, Integer.MAX_VALUE, "Cooldown for Past Burial in ticks.");
         this.worldStopReleaseCooldownTicks = i(
            builder, "worldStopReleaseCooldownTicks", 70, 0, Integer.MAX_VALUE, "Cooldown for Clockbreak Release before mastery."
         );
         this.worldStopReleaseCooldownTicksMastered = i(
            builder, "worldStopReleaseCooldownTicksMastered", 48, 0, Integer.MAX_VALUE, "Cooldown for Clockbreak Release after mastery."
         );
         this.lastSecondCooldownTicks = i(
            builder, "lastSecondCooldownTicks", 760, 0, Integer.MAX_VALUE, "Cooldown for Last Second of Creation in ticks.", "Default 760 = 38 seconds."
         );
         builder.pop();
         builder.push("chrono_divinity");
         this.chronoMoveSpeed = d(builder, "chronoMoveSpeed", 0.24, -1.0, 100.0, "Passive movement speed modifier before mastery.");
         this.chronoMoveSpeedMastered = d(builder, "chronoMoveSpeedMastered", 0.34, -1.0, 100.0, "Passive movement speed modifier after mastery.");
         this.chronoAttackSpeed = d(builder, "chronoAttackSpeed", 0.34, -1.0, 100.0, "Passive attack speed modifier before mastery.");
         this.chronoAttackSpeedMastered = d(builder, "chronoAttackSpeedMastered", 0.48, -1.0, 100.0, "Passive attack speed modifier after mastery.");
         this.chronoFlyingSpeed = d(builder, "chronoFlyingSpeed", 0.2, -1.0, 100.0, "Passive flying speed modifier before mastery.");
         this.chronoFlyingSpeedMastered = d(builder, "chronoFlyingSpeedMastered", 0.32, -1.0, 100.0, "Passive flying speed modifier after mastery.");
         this.chronoChantSpeed = d(builder, "chronoChantSpeed", 0.48, -1000.0, 1000.0, "Chant speed bonus before mastery.");
         this.chronoChantSpeedMastered = d(builder, "chronoChantSpeedMastered", 0.75, -1000.0, 1000.0, "Chant speed bonus after mastery.");
         this.chronoPresenceSense = d(builder, "chronoPresenceSense", 2.5, -1000.0, 1000.0, "Presence sensing bonus before mastery.");
         this.chronoPresenceSenseMastered = d(builder, "chronoPresenceSenseMastered", 4.0, -1000.0, 1000.0, "Presence sensing bonus after mastery.");
         this.chronoDodgeInvulnerability = d(
            builder, "chronoDodgeInvulnerability", 0.36, -1000.0, 1000.0, "Extra dodge / invulnerability strength before mastery."
         );
         this.chronoDodgeInvulnerabilityMastered = d(
            builder, "chronoDodgeInvulnerabilityMastered", 0.6, -1000.0, 1000.0, "Extra dodge / invulnerability strength after mastery."
         );
         this.chronoResistanceDegradation = d(
            builder, "chronoResistanceDegradation", 1.25, -1000.0, 1000.0, "How strongly Istaroth degrades enemy resistance before mastery."
         );
         this.chronoResistanceDegradationMastered = d(
            builder, "chronoResistanceDegradationMastered", 2.25, -1000.0, 1000.0, "How strongly Istaroth degrades enemy resistance after mastery."
         );
         this.predictionMeleeDodge = d(builder, "predictionMeleeDodge", 13.0, -1000.0, 1000.0, "Melee prediction dodge strength before mastery.");
         this.predictionMeleeDodgeMastered = d(builder, "predictionMeleeDodgeMastered", 24.0, -1000.0, 1000.0, "Melee prediction dodge strength after mastery.");
         this.predictionProjectileDodge = d(builder, "predictionProjectileDodge", 22.0, -1000.0, 1000.0, "Projectile prediction dodge strength before mastery.");
         this.predictionProjectileDodgeMastered = d(
            builder, "predictionProjectileDodgeMastered", 40.0, -1000.0, 1000.0, "Projectile prediction dodge strength after mastery."
         );
         this.outsideStreamLawDegradation = d(
            builder, "outsideStreamLawDegradation", 1.5, -1000.0, 1000.0, "Law resistance degradation applied by Outside the Stream."
         );
         this.outsideStreamSpaceDegradation = d(
            builder, "outsideStreamSpaceDegradation", 1.5, -1000.0, 1000.0, "Space resistance degradation applied by Outside the Stream."
         );
         builder.pop();
         builder.push("actions");
         this.chronicleRadius = d(builder, "chronicleRadius", 30.0, 0.0, 512.0, "Radius scanned by Chronicle.");
         this.timeSkipDistance = d(builder, "timeSkipDistance", 20.0, 0.0, 512.0, "Teleport distance for Time Skip before mastery.");
         this.timeSkipDistanceMastered = d(builder, "timeSkipDistanceMastered", 30.0, 0.0, 512.0, "Teleport distance for Time Skip after mastery.");
         this.timeSkipPathInflate = d(builder, "timeSkipPathInflate", 2.6, 0.0, 64.0, "Width of the damage/freeze path during Time Skip before mastery.");
         this.timeSkipPathInflateMastered = d(
            builder, "timeSkipPathInflateMastered", 3.4, 0.0, 64.0, "Width of the damage/freeze path during Time Skip after mastery."
         );
         this.timeSkipDamage = d(builder, "timeSkipDamage", 48.0, 0.0, 100000.0, "Base damage dealt to enemies crossed by Time Skip before mastery.");
         this.timeSkipDamageMastered = d(
            builder, "timeSkipDamageMastered", 72.0, 0.0, 100000.0, "Base damage dealt to enemies crossed by Time Skip after mastery."
         );
         this.timeSkipVelocityBonusCap = d(
            builder, "timeSkipVelocityBonusCap", 32.0, 0.0, 100000.0, "Maximum bonus Time Skip damage gained from enemy movement speed."
         );
         this.timeSkipVelocityBonusMultiplier = d(
            builder, "timeSkipVelocityBonusMultiplier", 12.0, 0.0, 100000.0, "Multiplier for converting enemy movement speed into bonus Time Skip damage."
         );
         this.timeSkipPlayerDamageMultiplier = d(
            builder,
            "timeSkipPlayerDamageMultiplier",
            0.7,
            0.0,
            100.0,
            "Damage multiplier when Time Skip hits players.",
            "0.70 = players take 70% of normal damage."
         );
         this.timeSkipDebtMultiplier = d(builder, "timeSkipDebtMultiplier", 1.15, 0.0, 100.0, "Temporal debt added based on Time Skip damage.");
         this.timeSkipProjectileFreezeTicks = i(
            builder, "timeSkipProjectileFreezeTicks", 85, 0, Integer.MAX_VALUE, "How long projectiles inside the Time Skip path are frozen before mastery."
         );
         this.timeSkipProjectileFreezeTicksMastered = i(
            builder,
            "timeSkipProjectileFreezeTicksMastered",
            110,
            0,
            Integer.MAX_VALUE,
            "How long projectiles inside the Time Skip path are frozen after mastery."
         );
         this.timeSkipActionLockTicks = i(
            builder, "timeSkipActionLockTicks", 18, 0, Integer.MAX_VALUE, "How long enemies hit by Time Skip are action-locked before mastery."
         );
         this.timeSkipActionLockTicksMastered = i(
            builder, "timeSkipActionLockTicksMastered", 26, 0, Integer.MAX_VALUE, "How long enemies hit by Time Skip are action-locked after mastery."
         );
         this.timeSkipStutterTicks = i(builder, "timeSkipStutterTicks", 115, 0, Integer.MAX_VALUE, "Temporal Stutter duration applied by Time Skip.");
         this.timeSkipFutureRejectionTicks = i(
            builder, "timeSkipFutureRejectionTicks", 70, 0, Integer.MAX_VALUE, "Future Rejection duration applied by Time Skip."
         );
         this.momentumTheftRange = d(builder, "momentumTheftRange", 28.0, 0.0, 512.0, "Forward range of Momentum Theft before mastery.");
         this.momentumTheftRangeMastered = d(builder, "momentumTheftRangeMastered", 38.0, 0.0, 512.0, "Forward range of Momentum Theft after mastery.");
         this.momentumTheftBoxInflate = d(builder, "momentumTheftBoxInflate", 3.0, 0.0, 64.0, "Width of the Momentum Theft hitbox before mastery.");
         this.momentumTheftBoxInflateMastered = d(
            builder, "momentumTheftBoxInflateMastered", 4.0, 0.0, 64.0, "Width of the Momentum Theft hitbox after mastery."
         );
         this.momentumTheftViewDot = d(
            builder,
            "momentumTheftViewDot",
            0.62,
            -1.0,
            1.0,
            "How closely a target must be in front of the user for Momentum Theft.",
            "Higher = stricter aim. Lower = wider cone."
         );
         this.momentumTheftBaseDamage = d(builder, "momentumTheftBaseDamage", 24.0, 0.0, 100000.0, "Base damage for Momentum Theft.");
         this.momentumTheftDamageCap = d(builder, "momentumTheftDamageCap", 55.0, 0.0, 100000.0, "Maximum bonus damage Momentum Theft can gain before mastery.");
         this.momentumTheftDamageCapMastered = d(
            builder, "momentumTheftDamageCapMastered", 90.0, 0.0, 100000.0, "Maximum bonus damage Momentum Theft can gain after mastery."
         );
         this.momentumTheftDamageMultiplier = d(
            builder, "momentumTheftDamageMultiplier", 4.2, 0.0, 100000.0, "Multiplier for converting stolen movement into Momentum Theft damage."
         );
         this.momentumTheftPlayerDamageMultiplier = d(
            builder, "momentumTheftPlayerDamageMultiplier", 0.65, 0.0, 100.0, "Damage multiplier when Momentum Theft hits players."
         );
         this.momentumTheftDebtMultiplier = d(builder, "momentumTheftDebtMultiplier", 1.1, 0.0, 100.0, "Temporal debt added based on Momentum Theft damage.");
         this.momentumTheftDebtBonusMultiplier = d(
            builder, "momentumTheftDebtBonusMultiplier", 3.0, 0.0, 100000.0, "Extra debt added from the amount of momentum stolen."
         );
         this.momentumTheftProjectileFreezeTicks = i(
            builder, "momentumTheftProjectileFreezeTicks", 105, 0, Integer.MAX_VALUE, "Projectile freeze duration from Momentum Theft before mastery."
         );
         this.momentumTheftProjectileFreezeTicksMastered = i(
            builder, "momentumTheftProjectileFreezeTicksMastered", 150, 0, Integer.MAX_VALUE, "Projectile freeze duration from Momentum Theft after mastery."
         );
         this.momentumTheftActionLockTicks = i(
            builder, "momentumTheftActionLockTicks", 30, 0, Integer.MAX_VALUE, "Action-lock duration on living targets hit by Momentum Theft."
         );
         this.momentumTheftStutterTicks = i(builder, "momentumTheftStutterTicks", 130, 0, Integer.MAX_VALUE, "Temporal Stutter duration from Momentum Theft.");
         this.momentumTheftMovementInterferenceTicks = i(
            builder, "momentumTheftMovementInterferenceTicks", 75, 0, Integer.MAX_VALUE, "Movement Interference duration from Momentum Theft."
         );
         this.borrowedSecondsRadius = d(builder, "borrowedSecondsRadius", 19.0, 0.0, 512.0, "Radius of Borrowed Seconds before mastery.");
         this.borrowedSecondsRadiusMastered = d(builder, "borrowedSecondsRadiusMastered", 27.0, 0.0, 512.0, "Radius of Borrowed Seconds after mastery.");
         this.borrowedSecondsDecayAmount = i(
            builder,
            "borrowedSecondsDecayAmount",
            180,
            0,
            Integer.MAX_VALUE,
            "How many ticks Borrowed Seconds removes from enemy beneficial effects before mastery."
         );
         this.borrowedSecondsDecayAmountMastered = i(
            builder,
            "borrowedSecondsDecayAmountMastered",
            260,
            0,
            Integer.MAX_VALUE,
            "How many ticks Borrowed Seconds removes from enemy beneficial effects after mastery."
         );
         this.borrowedSecondsDamage = d(builder, "borrowedSecondsDamage", 28.0, 0.0, 100000.0, "Flat damage dealt by Borrowed Seconds before mastery.");
         this.borrowedSecondsDamageMastered = d(
            builder, "borrowedSecondsDamageMastered", 44.0, 0.0, 100000.0, "Flat damage dealt by Borrowed Seconds after mastery."
         );
         this.borrowedSecondsMobPercentDamage = d(
            builder, "borrowedSecondsMobPercentDamage", 0.12, 0.0, 100.0, "Extra damage to mobs based on max health.", "0.12 = 12% of max health."
         );
         this.borrowedSecondsPlayerPercentDamage = d(
            builder, "borrowedSecondsPlayerPercentDamage", 0.05, 0.0, 100.0, "Extra damage to players based on max health.", "0.05 = 5% of max health."
         );
         this.borrowedSecondsDebtBonus = d(
            builder, "borrowedSecondsDebtBonus", 28.0, 0.0, 100000.0, "Extra temporal debt added by Borrowed Seconds before mastery."
         );
         this.borrowedSecondsDebtBonusMastered = d(
            builder, "borrowedSecondsDebtBonusMastered", 45.0, 0.0, 100000.0, "Extra temporal debt added by Borrowed Seconds after mastery."
         );
         this.borrowedSecondsEffectTicks = i(builder, "borrowedSecondsEffectTicks", 180, 0, Integer.MAX_VALUE, "Borrowed Seconds custom effect duration.");
         this.borrowedSecondsEventSilenceTicks = i(
            builder, "borrowedSecondsEventSilenceTicks", 70, 0, Integer.MAX_VALUE, "Event Silence duration applied by Borrowed Seconds."
         );
         this.borrowedSecondsSelfExtendTicks = i(
            builder,
            "borrowedSecondsSelfExtendTicks",
            200,
            0,
            Integer.MAX_VALUE,
            "How much Borrowed Seconds extends the user's beneficial effects before mastery."
         );
         this.borrowedSecondsSelfExtendTicksMastered = i(
            builder,
            "borrowedSecondsSelfExtendTicksMastered",
            300,
            0,
            Integer.MAX_VALUE,
            "How much Borrowed Seconds extends the user's beneficial effects after mastery."
         );
         this.borrowedSecondsRemoveHarmful = i(
            builder,
            "borrowedSecondsRemoveHarmful",
            2,
            0,
            Integer.MAX_VALUE,
            "How many recent harmful effects Borrowed Seconds removes from the user before mastery."
         );
         this.borrowedSecondsRemoveHarmfulMastered = i(
            builder,
            "borrowedSecondsRemoveHarmfulMastered",
            4,
            0,
            Integer.MAX_VALUE,
            "How many recent harmful effects Borrowed Seconds removes from the user after mastery."
         );
         this.borrowedSecondsCooldownReduction = i(
            builder, "borrowedSecondsCooldownReduction", 18, 0, Integer.MAX_VALUE, "Cooldown reduction granted by Borrowed Seconds before mastery."
         );
         this.borrowedSecondsCooldownReductionMastered = i(
            builder, "borrowedSecondsCooldownReductionMastered", 30, 0, Integer.MAX_VALUE, "Cooldown reduction granted by Borrowed Seconds after mastery."
         );
         this.eternalMomentTicks = i(builder, "eternalMomentTicks", 140, 0, Integer.MAX_VALUE, "Duration of Eternal Moment before mastery.");
         this.eternalMomentTicksMastered = i(builder, "eternalMomentTicksMastered", 200, 0, Integer.MAX_VALUE, "Duration of Eternal Moment after mastery.");
         this.eternalMomentSelfExtendTicks = i(
            builder, "eternalMomentSelfExtendTicks", 220, 0, Integer.MAX_VALUE, "How much Eternal Moment extends user beneficial effects before mastery."
         );
         this.eternalMomentSelfExtendTicksMastered = i(
            builder,
            "eternalMomentSelfExtendTicksMastered",
            340,
            0,
            Integer.MAX_VALUE,
            "How much Eternal Moment extends user beneficial effects after mastery."
         );
         this.eternalMomentRemoveHarmful = i(
            builder, "eternalMomentRemoveHarmful", 2, 0, Integer.MAX_VALUE, "How many recent harmful effects Eternal Moment removes before mastery."
         );
         this.eternalMomentRemoveHarmfulMastered = i(
            builder, "eternalMomentRemoveHarmfulMastered", 5, 0, Integer.MAX_VALUE, "How many recent harmful effects Eternal Moment removes after mastery."
         );
         this.eternalMomentRadius = d(builder, "eternalMomentRadius", 9.0, 0.0, 512.0, "Enemy hit radius when Eternal Moment activates before mastery.");
         this.eternalMomentRadiusMastered = d(
            builder, "eternalMomentRadiusMastered", 14.0, 0.0, 512.0, "Enemy hit radius when Eternal Moment activates after mastery."
         );
         this.eternalMomentDamage = d(builder, "eternalMomentDamage", 20.0, 0.0, 100000.0, "Damage dealt by Eternal Moment before mastery.");
         this.eternalMomentDamageMastered = d(builder, "eternalMomentDamageMastered", 35.0, 0.0, 100000.0, "Damage dealt by Eternal Moment after mastery.");
         this.eternalMomentPlayerDamageMultiplier = d(
            builder, "eternalMomentPlayerDamageMultiplier", 0.6, 0.0, 100.0, "Damage multiplier when Eternal Moment hits players."
         );
         this.eternalMomentStutterTicks = i(builder, "eternalMomentStutterTicks", 85, 0, Integer.MAX_VALUE, "Temporal Stutter duration from Eternal Moment.");
         this.objectPermanenceRange = d(builder, "objectPermanenceRange", 26.0, 0.0, 512.0, "Targeting range for Object Permanence before mastery.");
         this.objectPermanenceRangeMastered = d(
            builder, "objectPermanenceRangeMastered", 36.0, 0.0, 512.0, "Targeting range for Object Permanence after mastery."
         );
         this.objectPermanencePlayerTicks = i(
            builder, "objectPermanencePlayerTicks", 100, 0, Integer.MAX_VALUE, "Stasis duration when Object Permanence hits players."
         );
         this.objectPermanenceTicks = i(
            builder,
            "objectPermanenceTicks",
            155,
            0,
            Integer.MAX_VALUE,
            "Stasis duration when Object Permanence hits non-player living entities before mastery."
         );
         this.objectPermanenceTicksMastered = i(
            builder,
            "objectPermanenceTicksMastered",
            220,
            0,
            Integer.MAX_VALUE,
            "Stasis duration when Object Permanence hits non-player living entities after mastery."
         );
         this.objectPermanenceActionLockCap = i(
            builder, "objectPermanenceActionLockCap", 120, 0, Integer.MAX_VALUE, "Maximum action-lock duration from Object Permanence."
         );
         this.objectPermanenceDebt = d(builder, "objectPermanenceDebt", 44.0, 0.0, 100000.0, "Temporal debt added by Object Permanence before mastery.");
         this.objectPermanenceDebtMastered = d(
            builder, "objectPermanenceDebtMastered", 70.0, 0.0, 100000.0, "Temporal debt added by Object Permanence after mastery."
         );
         this.rewindTargetRange = d(builder, "rewindTargetRange", 26.0, 0.0, 512.0, "Targeting range for Rewind Target before mastery.");
         this.rewindTargetRangeMastered = d(builder, "rewindTargetRangeMastered", 36.0, 0.0, 512.0, "Targeting range for Rewind Target after mastery.");
         this.rewindTargetSnapshotTicks = i(
            builder, "rewindTargetSnapshotTicks", 70, 0, Integer.MAX_VALUE, "How far back Rewind Target searches for a snapshot before mastery."
         );
         this.rewindTargetSnapshotTicksMastered = i(
            builder, "rewindTargetSnapshotTicksMastered", 110, 0, Integer.MAX_VALUE, "How far back Rewind Target searches for a snapshot after mastery."
         );
         this.rewindTargetDamage = d(builder, "rewindTargetDamage", 40.0, 0.0, 100000.0, "Damage dealt to enemies after being rewound before mastery.");
         this.rewindTargetDamageMastered = d(
            builder, "rewindTargetDamageMastered", 62.0, 0.0, 100000.0, "Damage dealt to enemies after being rewound after mastery."
         );
         this.rewindTargetPlayerDamageMultiplier = d(
            builder, "rewindTargetPlayerDamageMultiplier", 0.65, 0.0, 100.0, "Damage multiplier when Rewind Target hits players."
         );
         this.rewindTargetDebtBonus = d(builder, "rewindTargetDebtBonus", 38.0, 0.0, 100000.0, "Extra temporal debt added by Rewind Target.");
         this.rewindTargetActionLockTicks = i(
            builder, "rewindTargetActionLockTicks", 42, 0, Integer.MAX_VALUE, "Action-lock duration applied by Rewind Target."
         );
         this.rewindTargetStutterTicks = i(
            builder, "rewindTargetStutterTicks", 130, 0, Integer.MAX_VALUE, "Temporal Stutter duration applied by Rewind Target."
         );
         this.delayedVerdictRange = d(builder, "delayedVerdictRange", 30.0, 0.0, 512.0, "Targeting range for Delayed Verdict before mastery.");
         this.delayedVerdictRangeMastered = d(builder, "delayedVerdictRangeMastered", 40.0, 0.0, 512.0, "Targeting range for Delayed Verdict after mastery.");
         this.delayedVerdictTicks = i(builder, "delayedVerdictTicks", 150, 0, Integer.MAX_VALUE, "Delay before Delayed Verdict detonates before mastery.");
         this.delayedVerdictTicksMastered = i(
            builder, "delayedVerdictTicksMastered", 200, 0, Integer.MAX_VALUE, "Delay before Delayed Verdict detonates after mastery."
         );
         this.delayedVerdictDamage = d(
            builder, "delayedVerdictDamage", 56.0, 0.0, 100000.0, "Opening damage dealt when Delayed Verdict is applied before mastery."
         );
         this.delayedVerdictDamageMastered = d(
            builder, "delayedVerdictDamageMastered", 88.0, 0.0, 100000.0, "Opening damage dealt when Delayed Verdict is applied after mastery."
         );
         this.delayedVerdictPlayerDamageMultiplier = d(
            builder, "delayedVerdictPlayerDamageMultiplier", 0.72, 0.0, 100.0, "Damage multiplier when Delayed Verdict hits players."
         );
         this.delayedVerdictStoredMultiplier = d(
            builder, "delayedVerdictStoredMultiplier", 0.65, 0.0, 100.0, "How much opening damage is stored into the final verdict explosion."
         );
         this.delayedVerdictDebtBonus = d(
            builder, "delayedVerdictDebtBonus", 55.0, 0.0, 100000.0, "Extra temporal debt added by Delayed Verdict before mastery."
         );
         this.delayedVerdictDebtBonusMastered = d(
            builder, "delayedVerdictDebtBonusMastered", 85.0, 0.0, 100000.0, "Extra temporal debt added by Delayed Verdict after mastery."
         );
         this.effectBeforeCauseRange = d(builder, "effectBeforeCauseRange", 26.0, 0.0, 512.0, "Targeting range for Effect Before Cause before mastery.");
         this.effectBeforeCauseRangeMastered = d(
            builder, "effectBeforeCauseRangeMastered", 36.0, 0.0, 512.0, "Targeting range for Effect Before Cause after mastery."
         );
         this.effectBeforeCauseBaseDamage = d(builder, "effectBeforeCauseBaseDamage", 88.0, 0.0, 100000.0, "Base damage of Effect Before Cause before mastery.");
         this.effectBeforeCauseBaseDamageMastered = d(
            builder, "effectBeforeCauseBaseDamageMastered", 135.0, 0.0, 100000.0, "Base damage of Effect Before Cause after mastery."
         );
         this.effectBeforeCauseMobPercentDamage = d(builder, "effectBeforeCauseMobPercentDamage", 0.26, 0.0, 100.0, "Extra damage to mobs based on max health.");
         this.effectBeforeCausePlayerPercentDamage = d(
            builder, "effectBeforeCausePlayerPercentDamage", 0.12, 0.0, 100.0, "Extra damage to players based on max health."
         );
         this.effectBeforeCauseResistanceBase = d(
            builder, "effectBeforeCauseResistanceBase", 1.35, 0.0, 100.0, "Base multiplier before enemy resistance reduces Effect Before Cause damage."
         );
         this.effectBeforeCauseResistanceMultiplier = d(
            builder, "effectBeforeCauseResistanceMultiplier", 0.38, 0.0, 100.0, "How strongly enemy resistance lowers Effect Before Cause damage."
         );
         this.effectBeforeCausePlayerDamageMultiplier = d(
            builder, "effectBeforeCausePlayerDamageMultiplier", 0.75, 0.0, 100.0, "Final damage multiplier when Effect Before Cause hits players."
         );
         this.effectBeforeCauseDebtMultiplier = d(
            builder, "effectBeforeCauseDebtMultiplier", 1.6, 0.0, 100.0, "Temporal debt added based on Effect Before Cause damage."
         );
         this.effectBeforeCauseSplashRadius = d(
            builder, "effectBeforeCauseSplashRadius", 5.5, 0.0, 512.0, "Splash radius around the main Effect Before Cause target before mastery."
         );
         this.effectBeforeCauseSplashRadiusMastered = d(
            builder, "effectBeforeCauseSplashRadiusMastered", 8.5, 0.0, 512.0, "Splash radius around the main Effect Before Cause target after mastery."
         );
         this.effectBeforeCauseSplashMobMultiplier = d(
            builder, "effectBeforeCauseSplashMobMultiplier", 0.45, 0.0, 100.0, "Splash damage multiplier against mobs."
         );
         this.effectBeforeCauseSplashPlayerMultiplier = d(
            builder, "effectBeforeCauseSplashPlayerMultiplier", 0.25, 0.0, 100.0, "Splash damage multiplier against players."
         );
         this.loopRadius = d(builder, "loopRadius", 28.0, 0.0, 512.0, "Radius for Twenty-Second Causality Loop.");
         this.loopRecordTicks = i(builder, "loopRecordTicks", 320, 0, Integer.MAX_VALUE, "How long the loop records target positions before mastery.");
         this.loopRecordTicksMastered = i(
            builder, "loopRecordTicksMastered", 420, 0, Integer.MAX_VALUE, "How long the loop records target positions after mastery."
         );
         this.loopPlaybackTicks = i(builder, "loopPlaybackTicks", 110, 0, Integer.MAX_VALUE, "How long the loop plays targets backward before mastery.");
         this.loopPlaybackTicksMastered = i(
            builder, "loopPlaybackTicksMastered", 150, 0, Integer.MAX_VALUE, "How long the loop plays targets backward after mastery."
         );
         this.loopDamage = d(builder, "loopDamage", 38.0, 0.0, 100000.0, "Opening damage dealt by Twenty-Second Causality Loop before mastery.");
         this.loopDamageMastered = d(builder, "loopDamageMastered", 58.0, 0.0, 100000.0, "Opening damage dealt by Twenty-Second Causality Loop after mastery.");
         this.loopPlayerDamageMultiplier = d(builder, "loopPlayerDamageMultiplier", 0.65, 0.0, 100.0, "Damage multiplier when the loop hits players.");
         this.loopDebtBonus = d(builder, "loopDebtBonus", 60.0, 0.0, 100000.0, "Extra temporal debt added when the loop starts.");
         this.condemnedRepeatRange = d(builder, "condemnedRepeatRange", 26.0, 0.0, 512.0, "Targeting range for Condemned Repeat before mastery.");
         this.condemnedRepeatRangeMastered = d(builder, "condemnedRepeatRangeMastered", 36.0, 0.0, 512.0, "Targeting range for Condemned Repeat after mastery.");
         this.condemnedRepeatCharges = i(
            builder, "condemnedRepeatCharges", 8, 0, Integer.MAX_VALUE, "How many escape attempts Condemned Repeat can punish before mastery."
         );
         this.condemnedRepeatChargesMastered = i(
            builder, "condemnedRepeatChargesMastered", 12, 0, Integer.MAX_VALUE, "How many escape attempts Condemned Repeat can punish after mastery."
         );
         this.condemnedRepeatTicks = i(builder, "condemnedRepeatTicks", 245, 0, Integer.MAX_VALUE, "Duration of Condemned Repeat before mastery.");
         this.condemnedRepeatTicksMastered = i(
            builder, "condemnedRepeatTicksMastered", 320, 0, Integer.MAX_VALUE, "Duration of Condemned Repeat after mastery."
         );
         this.condemnedRepeatDamage = d(builder, "condemnedRepeatDamage", 52.0, 0.0, 100000.0, "Opening damage of Condemned Repeat before mastery.");
         this.condemnedRepeatDamageMastered = d(
            builder, "condemnedRepeatDamageMastered", 78.0, 0.0, 100000.0, "Opening damage of Condemned Repeat after mastery."
         );
         this.condemnedRepeatPlayerDamageMultiplier = d(
            builder, "condemnedRepeatPlayerDamageMultiplier", 0.7, 0.0, 100.0, "Damage multiplier when Condemned Repeat hits players."
         );
         this.condemnedRepeatDebtBonus = d(builder, "condemnedRepeatDebtBonus", 60.0, 0.0, 100000.0, "Extra temporal debt added by Condemned Repeat.");
         this.futureExileRange = d(builder, "futureExileRange", 28.0, 0.0, 512.0, "Targeting range for Future Exile before mastery.");
         this.futureExileRangeMastered = d(builder, "futureExileRangeMastered", 38.0, 0.0, 512.0, "Targeting range for Future Exile after mastery.");
         this.futureExilePlayerTicks = i(builder, "futureExilePlayerTicks", 120, 0, Integer.MAX_VALUE, "Banishment duration when Future Exile hits players.");
         this.futureExileTicks = i(builder, "futureExileTicks", 130, 0, Integer.MAX_VALUE, "Banishment duration when Future Exile hits mobs before mastery.");
         this.futureExileTicksMastered = i(
            builder, "futureExileTicksMastered", 190, 0, Integer.MAX_VALUE, "Banishment duration when Future Exile hits mobs after mastery."
         );
         this.futureExileResistanceThreshold = d(
            builder, "futureExileResistanceThreshold", 0.94, 0.0, 100.0, "Resistance threshold where strong non-player targets partially resist Future Exile."
         );
         this.futureExileDebt = d(builder, "futureExileDebt", 80.0, 0.0, 100000.0, "Temporal debt added by Future Exile before mastery.");
         this.futureExileDebtMastered = d(builder, "futureExileDebtMastered", 120.0, 0.0, 100000.0, "Temporal debt added by Future Exile after mastery.");
         this.pastBurialRange = d(builder, "pastBurialRange", 26.0, 0.0, 512.0, "Targeting range for Past Burial before mastery.");
         this.pastBurialRangeMastered = d(builder, "pastBurialRangeMastered", 34.0, 0.0, 512.0, "Targeting range for Past Burial after mastery.");
         this.pastBurialTicks = i(builder, "pastBurialTicks", 200, 0, Integer.MAX_VALUE, "Banishment duration for Past Burial.");
         this.pastBurialPower = i(builder, "pastBurialPower", 4, 0, 255, "Effect amplifier used by Past Burial before mastery.");
         this.pastBurialPowerMastered = i(builder, "pastBurialPowerMastered", 5, 0, 255, "Effect amplifier used by Past Burial after mastery.");
         this.pastBurialActionLockTicks = i(builder, "pastBurialActionLockTicks", 100, 0, Integer.MAX_VALUE, "Action-lock duration applied by Past Burial.");
         this.pastBurialDebt = d(builder, "pastBurialDebt", 125.0, 0.0, 100000.0, "Temporal debt added by Past Burial before mastery.");
         this.pastBurialDebtMastered = d(builder, "pastBurialDebtMastered", 170.0, 0.0, 100000.0, "Temporal debt added by Past Burial after mastery.");
         this.stasisReleaseBaseDamage = i(
            builder, "stasisReleaseBaseDamage", 55, 0, Integer.MAX_VALUE, "Base damage dealt when Object Permanence stasis releases."
         );
         this.stasisStoredDamageMultiplier = d(
            builder, "stasisStoredDamageMultiplier", 1.05, 0.0, 100.0, "Multiplier for stored damage released from stasis before mastery."
         );
         this.stasisStoredDamageMultiplierMastered = d(
            builder, "stasisStoredDamageMultiplierMastered", 1.45, 0.0, 100.0, "Multiplier for stored damage released from stasis after mastery."
         );
         this.stasisHitDamage = d(builder, "stasisHitDamage", 8.0, 0.0, 100000.0, "Extra release damage per hit taken while frozen before mastery.");
         this.stasisHitDamageMastered = d(
            builder, "stasisHitDamageMastered", 14.0, 0.0, 100000.0, "Extra release damage per hit taken while frozen after mastery."
         );
         this.stasisPlayerDamageMultiplier = d(
            builder, "stasisPlayerDamageMultiplier", 0.65, 0.0, 100.0, "Final stasis release damage multiplier against players."
         );
         builder.pop();
         builder.push("common");
         this.chronologyDenialCooldownTicks = i(
            builder, "chronologyDenialCooldownTicks", 6000, 0, Integer.MAX_VALUE, "Cooldown for automatic Chronology Denial death/damage prevention."
         );
         this.chronologyDenialSnapshotTicks = i(
            builder, "chronologyDenialSnapshotTicks", 100, 0, Integer.MAX_VALUE, "How far back Chronology Denial searches for a safe snapshot."
         );
         this.chronologyDenialInvulnerableTicks = i(
            builder, "chronologyDenialInvulnerableTicks", 100, 0, Integer.MAX_VALUE, "Invulnerability granted after Chronology Denial activates."
         );
         builder.pop();
         builder.push("world_stop");
         this.worldStopMaxHeldTicks = i(
            builder, "worldStopMaxHeldTicks", 150, 1, Integer.MAX_VALUE, "Maximum hold duration for World of Still Seconds before mastery."
         );
         this.worldStopMaxHeldTicksMastered = i(
            builder, "worldStopMaxHeldTicksMastered", 230, 1, Integer.MAX_VALUE, "Maximum hold duration for World of Still Seconds after mastery."
         );
         this.worldStopRadius = i(builder, "worldStopRadius", 30, 1, 512, "Radius of World of Still Seconds before mastery.");
         this.worldStopRadiusMastered = i(builder, "worldStopRadiusMastered", 42, 1, 512, "Radius of World of Still Seconds after mastery.");
         this.worldStopProjectileFreezeTicks = i(
            builder, "worldStopProjectileFreezeTicks", 65, 0, Integer.MAX_VALUE, "How long projectiles are frozen by World of Still Seconds."
         );
         this.clockbreakBaseDamage = d(builder, "clockbreakBaseDamage", 58.0, 0.0, 100000.0, "Base damage of Clockbreak Release before mastery.");
         this.clockbreakBaseDamageMastered = d(builder, "clockbreakBaseDamageMastered", 88.0, 0.0, 100000.0, "Base damage of Clockbreak Release after mastery.");
         this.clockbreakHeldPowerMultiplier = d(builder, "clockbreakHeldPowerMultiplier", 0.95, 0.0, 1000.0, "Damage gained per held tick before mastery.");
         this.clockbreakHeldPowerMultiplierMastered = d(
            builder, "clockbreakHeldPowerMultiplierMastered", 1.35, 0.0, 1000.0, "Damage gained per held tick after mastery."
         );
         this.clockbreakHeldPowerCap = d(builder, "clockbreakHeldPowerCap", 260.0, 0.0, 100000.0, "Maximum damage bonus from hold duration.");
         this.clockbreakProjectilePowerCap = d(
            builder, "clockbreakProjectilePowerCap", 230.0, 0.0, 100000.0, "Maximum damage bonus from frozen projectile force."
         );
         this.clockbreakProjectilePowerMultiplier = d(
            builder, "clockbreakProjectilePowerMultiplier", 0.24, 0.0, 1000.0, "Multiplier for converting frozen projectile force into Clockbreak damage."
         );
         this.clockbreakMobMaxHealthDamage = d(
            builder, "clockbreakMobMaxHealthDamage", 0.18, 0.0, 100.0, "Extra Clockbreak damage to mobs based on max health."
         );
         this.clockbreakPlayerMaxHealthDamage = d(
            builder, "clockbreakPlayerMaxHealthDamage", 0.1, 0.0, 100.0, "Extra Clockbreak damage to players based on max health."
         );
         this.clockbreakPlayerDamageMultiplier = d(
            builder, "clockbreakPlayerDamageMultiplier", 0.72, 0.0, 100.0, "Final Clockbreak damage multiplier against players."
         );
         builder.pop();
         builder.push("last_second");
         this.lastSecondTicks = i(
            builder, "lastSecondTicks", 600, 1, Integer.MAX_VALUE, "Duration of Last Second of Creation before collapse.", "Default 600 = 30 seconds."
         );
         this.lastSecondRadius = i(builder, "lastSecondRadius", 56, 1, 512, "Main radius of Last Second of Creation.");
         this.lastSecondCollapseRadius = i(builder, "lastSecondCollapseRadius", 74, 1, 512, "Final collapse damage radius.");
         this.lastSecondBaseDamage = d(builder, "lastSecondBaseDamage", 300.0, 0.0, 1000000.0, "Base final collapse damage before mastery.");
         this.lastSecondBaseDamageMastered = d(builder, "lastSecondBaseDamageMastered", 420.0, 0.0, 1000000.0, "Base final collapse damage after mastery.");
         this.lastSecondDebtMultiplier = d(
            builder, "lastSecondDebtMultiplier", 1.45, 0.0, 1000.0, "Debt damage multiplier for normal targets during final collapse."
         );
         this.lastSecondPrimaryDebtMultiplier = d(
            builder, "lastSecondPrimaryDebtMultiplier", 2.35, 0.0, 1000.0, "Debt damage multiplier for the selected primary target."
         );
         this.lastSecondForceMultiplier = d(
            builder, "lastSecondForceMultiplier", 0.34, 0.0, 1000.0, "Multiplier for converting accumulated force into final damage on normal targets."
         );
         this.lastSecondPrimaryForceMultiplier = d(
            builder,
            "lastSecondPrimaryForceMultiplier",
            0.55,
            0.0,
            1000.0,
            "Multiplier for converting accumulated force into final damage on the primary target."
         );
         this.lastSecondForceCap = d(builder, "lastSecondForceCap", 980.0, 0.0, 1000000.0, "Maximum force-based damage on normal targets.");
         this.lastSecondPrimaryForceCap = d(builder, "lastSecondPrimaryForceCap", 1850.0, 0.0, 1000000.0, "Maximum force-based damage on the primary target.");
         this.lastSecondMobMaxHealthDamage = d(builder, "lastSecondMobMaxHealthDamage", 0.75, 0.0, 100.0, "Extra final damage to mobs based on max health.");
         this.lastSecondPlayerMaxHealthDamage = d(
            builder, "lastSecondPlayerMaxHealthDamage", 0.38, 0.0, 100.0, "Extra final damage to players based on max health."
         );
         this.lastSecondPlayerDamageMultiplier = d(
            builder, "lastSecondPlayerDamageMultiplier", 0.82, 0.0, 100.0, "Final Last Second damage multiplier against players."
         );
         this.lastSecondPrimaryMobMultiplier = d(
            builder, "lastSecondPrimaryMobMultiplier", 3.2, 0.0, 1000.0, "Extra damage multiplier for the selected primary mob target."
         );
         this.lastSecondPrimaryPlayerMultiplier = d(
            builder, "lastSecondPrimaryPlayerMultiplier", 1.45, 0.0, 1000.0, "Extra damage multiplier for the selected primary player target."
         );
         builder.pop();
         builder.push("doom_tornado");
         this.doomTornadoMinTicks = i(
            builder, "doomTornadoMinTicks", 20, 1, Integer.MAX_VALUE, "Minimum duration allowed for the Doom Tornado visual/world event."
         );
         this.doomTornadoMinRadius = d(builder, "doomTornadoMinRadius", 72.0, 1.0, 1024.0, "Minimum radius allowed for Doom Tornado.");
         this.doomTornadoEndCleanupLockTicks = i(
            builder,
            "doomTornadoEndCleanupLockTicks",
            80,
            0,
            Integer.MAX_VALUE,
            "Cleanup lock after Doom Tornado is manually cleared.",
            "Prevents lingering screen/time effects from immediately reapplying."
         );
         this.doomTornadoDetonateCleanupLockTicks = i(
            builder, "doomTornadoDetonateCleanupLockTicks", 100, 0, Integer.MAX_VALUE, "Cleanup lock after Doom Tornado detonates."
         );
         this.doomTornadoSendStartRangeMultiplier = d(
            builder,
            "doomTornadoSendStartRangeMultiplier",
            64.0,
            1.0,
            1024.0,
            "Network visibility range multiplier for sending Doom Tornado start packets.",
            "Higher values show the effect to players farther away."
         );
         this.doomTornadoFinalLaunchForceBase = d(
            builder, "doomTornadoFinalLaunchForceBase", 7.25, 0.0, 1000.0, "Base horizontal launch force applied when Doom Tornado detonates."
         );
         this.doomTornadoFinalLaunchForceCap = d(
            builder, "doomTornadoFinalLaunchForceCap", 22.0, 0.0, 1000.0, "Maximum extra horizontal launch force from Doom Tornado detonation."
         );
         this.doomTornadoFinalLaunchForceRadiusMultiplier = d(
            builder, "doomTornadoFinalLaunchForceRadiusMultiplier", 0.95, 0.0, 1000.0, "How much tornado radius contributes to horizontal launch force."
         );
         this.doomTornadoFinalLaunchLiftBase = d(
            builder, "doomTornadoFinalLaunchLiftBase", 13.0, 0.0, 1000.0, "Base upward launch force applied when Doom Tornado detonates."
         );
         this.doomTornadoFinalLaunchLiftCap = d(
            builder, "doomTornadoFinalLaunchLiftCap", 26.0, 0.0, 1000.0, "Maximum extra upward launch force from Doom Tornado detonation."
         );
         this.doomTornadoFinalLaunchLiftRadiusMultiplier = d(
            builder, "doomTornadoFinalLaunchLiftRadiusMultiplier", 0.9, 0.0, 1000.0, "How much tornado radius contributes to upward launch force."
         );
         this.doomTornadoFinalLaunchTangentForce = d(
            builder, "doomTornadoFinalLaunchTangentForce", 3.75, 0.0, 1000.0, "Sideways spiral force applied during Doom Tornado detonation."
         );
         this.doomTornadoFinalTemporalStutterTicks = i(
            builder, "doomTornadoFinalTemporalStutterTicks", 220, 0, Integer.MAX_VALUE, "Temporal Stutter duration applied by Doom Tornado detonation."
         );
         this.doomTornadoFinalTemporalStutterAmplifier = i(
            builder, "doomTornadoFinalTemporalStutterAmplifier", 5, 0, 255, "Temporal Stutter amplifier applied by Doom Tornado detonation."
         );
         this.doomTornadoFinalStillSecondsTicks = i(
            builder, "doomTornadoFinalStillSecondsTicks", 120, 0, Integer.MAX_VALUE, "World of Still Seconds duration applied by Doom Tornado detonation."
         );
         this.doomTornadoFinalStillSecondsAmplifier = i(
            builder, "doomTornadoFinalStillSecondsAmplifier", 5, 0, 255, "World of Still Seconds amplifier applied by Doom Tornado detonation."
         );
         builder.pop();
      }

      private static IntValue i(Builder builder, String name, int defaultValue, int min, int max, String... comment) {
         return builder.comment(comment).defineInRange(name, defaultValue, min, max);
      }

      private static DoubleValue d(Builder builder, String name, double defaultValue, double min, double max, String... comment) {
         return builder.comment(comment).defineInRange(name, defaultValue, min, max);
      }
   }

   public static final class KronairosLordOfTheEndless {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final DoubleValue learningCostMultiplier;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final DoubleValue onLearnMagiculeDrain;
      public final IntValue takersWillCost;
      public final IntValue powerHungerCost;
      public final IntValue roomCost;
      public final IntValue isolationCost;
      public final IntValue divineLifestealCost;
      public final IntValue takersWillCooldownTicks;
      public final IntValue powerHungerCooldownTicks;
      public final IntValue roomCooldownTicks;
      public final IntValue isolationCooldownTicks;
      public final IntValue divineLifestealCooldownTicks;
      public final IntValue failedTargetCooldownTicks;
      public final IntValue failedPowerHungerCooldownTicks;
      public final DoubleValue toggleAuraRadius;
      public final IntValue auraDebuffDurationTicks;
      public final IntValue auraMovementInterferenceAmplifier;
      public final IntValue auraWeaknessAmplifier;
      public final IntValue auraTrueBlindnessAmplifier;
      public final IntValue takersWillDurationTicks;
      public final IntValue takersWillIntervalTicks;
      public final DoubleValue takersWillTargetRange;
      public final DoubleValue takersWillNormalHealthPercent;
      public final DoubleValue takersWillMagicResistanceHealthPercent;
      public final DoubleValue takersWillMagicNullificationHealthPercent;
      public final DoubleValue lowHealthThreshold1;
      public final DoubleValue lowHealthThreshold2;
      public final DoubleValue lowHealthThreshold3;
      public final DoubleValue lowHealthThreshold4;
      public final IntValue lowHealthStrengthAmplifier1;
      public final IntValue lowHealthStrengthAmplifier2;
      public final IntValue lowHealthStrengthAmplifier3;
      public final IntValue lowHealthStrengthAmplifier4;
      public final IntValue lowHealthStrengthDurationTicks;
      public final DoubleValue lowHealthDamageTakenMultiplier;
      public final DoubleValue magiculeStealPercentOnHit;
      public final IntValue magiculeStealChanceDivisor;
      public final IntValue infiniteImprisonmentOnHitTicks;
      public final IntValue infiniteImprisonmentOnHitAmplifier;
      public final DoubleValue isolationTargetRange;
      public final IntValue isolationHoldSeconds;
      public final IntValue isolationLockTicks;
      public final IntValue isolationAntiSkillAmplifier;
      public final IntValue isolationInfiniteImprisonmentAmplifier;
      public final DoubleValue powerHungerTargetRange;
      public final DoubleValue powerHungerMaxTargetEpMultiplier;
      public final IntValue powerHungerBaseCopies;
      public final IntValue powerHungerTwoCopyChancePercent;
      public final IntValue powerHungerThreeCopyChancePercent;
      public final DoubleValue powerHungerExistingMasteryRatio;
      public final DoubleValue roomRadius;
      public final IntValue roomDurationTicks;
      public final IntValue roomInfiniteImprisonmentAmplifier;
      public final IntValue roomAntiSkillAmplifier;
      public final DoubleValue divineLifestealRadius;
      public final DoubleValue divineLifestealSpatialNormalDamage;
      public final DoubleValue divineLifestealSpatialResistedDamage;
      public final DoubleValue divineLifestealHolyNormalDamage;
      public final DoubleValue divineLifestealHolyResistedDamage;
      public final DoubleValue divineLifestealHealRatio;

      KronairosLordOfTheEndless(Builder builder) {
         builder.comment("Obtainment settings for Kronairos, Lord of the Endless.");
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.comment("Magicule cost required to naturally acquire Kronairos.")
            .defineInRange("acquirementMagiculeCost", 1000000.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.comment("Starting mastery value when acquired.").defineInRange("acquirementMastery", 0, -1, Integer.MAX_VALUE);
         this.learningCostMultiplier = builder.comment("Learning cost multiplier for all modes.").defineInRange("learningCostMultiplier", 1.0, 0.0, 1000.0);
         this.requiredSkillId = builder.comment("Required skill registry ID for obtainment. Empty string disables the required skill check.")
            .define("requiredSkillId", "tensura:infinity_prison");
         this.requiredSkillMustBeMastered = builder.comment("If true, the required skill must be mastered before Kronairos can be acquired.")
            .define("requiredSkillMustBeMastered", true);
         this.onLearnMagiculeDrain = builder.comment("Magicules removed once when Kronairos is learned permanently.")
            .defineInRange("onLearnMagiculeDrain", 2000000.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.comment("Magicule costs for each mode.");
         builder.push("costs");
         this.takersWillCost = builder.defineInRange("takersWillCost", 25000, 0, Integer.MAX_VALUE);
         this.powerHungerCost = builder.defineInRange("powerHungerCost", 35000, 0, Integer.MAX_VALUE);
         this.roomCost = builder.defineInRange("roomCost", 45000, 0, Integer.MAX_VALUE);
         this.isolationCost = builder.defineInRange("isolationCost", 15000, 0, Integer.MAX_VALUE);
         this.divineLifestealCost = builder.defineInRange("divineLifestealCost", 60000, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Cooldowns. 20 ticks = 1 second.");
         builder.push("cooldowns");
         this.takersWillCooldownTicks = builder.defineInRange("takersWillCooldownTicks", 200, 0, Integer.MAX_VALUE);
         this.powerHungerCooldownTicks = builder.defineInRange("powerHungerCooldownTicks", 100, 0, Integer.MAX_VALUE);
         this.roomCooldownTicks = builder.defineInRange("roomCooldownTicks", 100, 0, Integer.MAX_VALUE);
         this.isolationCooldownTicks = builder.defineInRange("isolationCooldownTicks", 200, 0, Integer.MAX_VALUE);
         this.divineLifestealCooldownTicks = builder.defineInRange("divineLifestealCooldownTicks", 50, 0, Integer.MAX_VALUE);
         this.failedTargetCooldownTicks = builder.defineInRange("failedTargetCooldownTicks", 20, 0, Integer.MAX_VALUE);
         this.failedPowerHungerCooldownTicks = builder.defineInRange("failedPowerHungerCooldownTicks", 40, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Toggle aura values.");
         builder.push("toggle_aura");
         this.toggleAuraRadius = builder.defineInRange("toggleAuraRadius", 3.0, 0.0, 128.0);
         this.auraDebuffDurationTicks = builder.defineInRange("auraDebuffDurationTicks", 60, 1, Integer.MAX_VALUE);
         this.auraMovementInterferenceAmplifier = builder.defineInRange("auraMovementInterferenceAmplifier", 0, 0, 255);
         this.auraWeaknessAmplifier = builder.defineInRange("auraWeaknessAmplifier", 0, 0, 255);
         this.auraTrueBlindnessAmplifier = builder.defineInRange("auraTrueBlindnessAmplifier", 0, 0, 255);
         builder.pop();
         builder.comment("Taker's Will values.");
         builder.push("takers_will");
         this.takersWillDurationTicks = builder.defineInRange("takersWillDurationTicks", 1200, 1, Integer.MAX_VALUE);
         this.takersWillIntervalTicks = builder.defineInRange("takersWillIntervalTicks", 200, 1, Integer.MAX_VALUE);
         this.takersWillTargetRange = builder.defineInRange("takersWillTargetRange", 30.0, 1.0, 512.0);
         this.takersWillNormalHealthPercent = builder.comment("Max health removed per interval with no magic resistance. 0.10 = 10 percent.")
            .defineInRange("takersWillNormalHealthPercent", 0.1, 0.0, 1.0);
         this.takersWillMagicResistanceHealthPercent = builder.comment("Max health removed per interval if target has Magic Resistance toggled.")
            .defineInRange("takersWillMagicResistanceHealthPercent", 0.05, 0.0, 1.0);
         this.takersWillMagicNullificationHealthPercent = builder.comment("Max health removed per interval if target has Magic Nullification toggled.")
            .defineInRange("takersWillMagicNullificationHealthPercent", 0.01, 0.0, 1.0);
         builder.pop();
         builder.comment("Low health toggle bonuses.");
         builder.push("low_health");
         this.lowHealthThreshold1 = builder.defineInRange("lowHealthThreshold1", 0.2, 0.0, 1.0);
         this.lowHealthThreshold2 = builder.defineInRange("lowHealthThreshold2", 0.4, 0.0, 1.0);
         this.lowHealthThreshold3 = builder.defineInRange("lowHealthThreshold3", 0.6, 0.0, 1.0);
         this.lowHealthThreshold4 = builder.defineInRange("lowHealthThreshold4", 0.8, 0.0, 1.0);
         this.lowHealthStrengthAmplifier1 = builder.defineInRange("lowHealthStrengthAmplifier1", 4, 0, 255);
         this.lowHealthStrengthAmplifier2 = builder.defineInRange("lowHealthStrengthAmplifier2", 3, 0, 255);
         this.lowHealthStrengthAmplifier3 = builder.defineInRange("lowHealthStrengthAmplifier3", 2, 0, 255);
         this.lowHealthStrengthAmplifier4 = builder.defineInRange("lowHealthStrengthAmplifier4", 1, 0, 255);
         this.lowHealthStrengthDurationTicks = builder.defineInRange("lowHealthStrengthDurationTicks", 60, 1, Integer.MAX_VALUE);
         this.lowHealthDamageTakenMultiplier = builder.comment("Damage multiplier when under threshold 1. 0.70 = 30 percent reduction.")
            .defineInRange("lowHealthDamageTakenMultiplier", 0.7, 0.0, 100.0);
         builder.pop();
         builder.comment("Magicule stealing while toggled.");
         builder.push("magicule_steal");
         this.magiculeStealPercentOnHit = builder.comment("Percent of target magicules stolen on hit. 0.01 = 1 percent.")
            .defineInRange("magiculeStealPercentOnHit", 0.01, 0.0, 1.0);
         this.magiculeStealChanceDivisor = builder.comment("1 in this number chance to steal magicules on hit. 10 = 10 percent.")
            .defineInRange("magiculeStealChanceDivisor", 10, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("On-hit Infinite Imprisonment values.");
         builder.push("on_hit_imprisonment");
         this.infiniteImprisonmentOnHitTicks = builder.defineInRange("infiniteImprisonmentOnHitTicks", 60, 1, Integer.MAX_VALUE);
         this.infiniteImprisonmentOnHitAmplifier = builder.defineInRange("infiniteImprisonmentOnHitAmplifier", 0, 0, 255);
         builder.pop();
         builder.comment("Isolation hold and lock values.");
         builder.push("isolation");
         this.isolationTargetRange = builder.defineInRange("isolationTargetRange", 20.0, 1.0, 512.0);
         this.isolationHoldSeconds = builder.defineInRange("isolationHoldSeconds", 10, 1, Integer.MAX_VALUE);
         this.isolationLockTicks = builder.defineInRange("isolationLockTicks", 100, 1, Integer.MAX_VALUE);
         this.isolationAntiSkillAmplifier = builder.defineInRange("isolationAntiSkillAmplifier", 1, 0, 255);
         this.isolationInfiniteImprisonmentAmplifier = builder.defineInRange("isolationInfiniteImprisonmentAmplifier", 1, 0, 255);
         builder.pop();
         builder.comment("Power Hunger copy values.");
         builder.push("power_hunger");
         this.powerHungerTargetRange = builder.defineInRange("powerHungerTargetRange", 20.0, 1.0, 512.0);
         this.powerHungerMaxTargetEpMultiplier = builder.comment("Fails if target EP is higher than user EP multiplied by this.")
            .defineInRange("powerHungerMaxTargetEpMultiplier", 5.0, 0.0, 1000.0);
         this.powerHungerBaseCopies = builder.defineInRange("powerHungerBaseCopies", 1, 1, 100);
         this.powerHungerTwoCopyChancePercent = builder.defineInRange("powerHungerTwoCopyChancePercent", 10, 0, 100);
         this.powerHungerThreeCopyChancePercent = builder.defineInRange("powerHungerThreeCopyChancePercent", 5, 0, 100);
         this.powerHungerExistingMasteryRatio = builder.comment("If the user somehow already has the copied skill, mastery is raised to at least this ratio.")
            .defineInRange("powerHungerExistingMasteryRatio", 0.25, 0.0, 1.0);
         builder.pop();
         builder.comment("Room area lock values.");
         builder.push("room");
         this.roomRadius = builder.defineInRange("roomRadius", 1.5, 0.0, 128.0);
         this.roomDurationTicks = builder.defineInRange("roomDurationTicks", 600, 1, Integer.MAX_VALUE);
         this.roomInfiniteImprisonmentAmplifier = builder.defineInRange("roomInfiniteImprisonmentAmplifier", 1, 0, 255);
         this.roomAntiSkillAmplifier = builder.defineInRange("roomAntiSkillAmplifier", 0, 0, 255);
         builder.pop();
         builder.comment("Divine Lifesteal damage and healing values.");
         builder.push("divine_lifesteal");
         this.divineLifestealRadius = builder.defineInRange("divineLifestealRadius", 2.5, 0.0, 128.0);
         this.divineLifestealSpatialNormalDamage = builder.defineInRange("divineLifestealSpatialNormalDamage", 5000.0, 0.0, Float.MAX_VALUE);
         this.divineLifestealSpatialResistedDamage = builder.defineInRange("divineLifestealSpatialResistedDamage", 2000.0, 0.0, Float.MAX_VALUE);
         this.divineLifestealHolyNormalDamage = builder.defineInRange("divineLifestealHolyNormalDamage", 5000.0, 0.0, Float.MAX_VALUE);
         this.divineLifestealHolyResistedDamage = builder.defineInRange("divineLifestealHolyResistedDamage", 2000.0, 0.0, Float.MAX_VALUE);
         this.divineLifestealHealRatio = builder.comment("Caster healing from damage attempted. 0.15 = 15 percent.")
            .defineInRange("divineLifestealHealRatio", 0.15, 0.0, 100.0);
         builder.pop();
      }
   }

   public static final class MammonCasino {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final IntValue chipsPerTrackedKill;
      public final IntValue duplicateKillProtectionTicks;
      public final IntValue smallBet;
      public final IntValue mediumBet;
      public final IntValue largeBet;
      public final DoubleValue game0RewardMultiplier;
      public final DoubleValue game1RewardMultiplier;
      public final DoubleValue game2RewardMultiplier;
      public final DoubleValue smallLoseChance;
      public final DoubleValue mediumLoseChance;
      public final DoubleValue largeLoseChance;
      public final DoubleValue smallUltimateChance;
      public final DoubleValue mediumUltimateChance;
      public final DoubleValue largeUltimateChance;
      public final DoubleValue smallUniqueChance;
      public final DoubleValue mediumUniqueChance;
      public final DoubleValue largeUniqueChance;
      public final DoubleValue smallExtraChance;
      public final DoubleValue mediumExtraChance;
      public final DoubleValue largeExtraChance;
      public final DoubleValue smallCommonChance;
      public final DoubleValue mediumCommonChance;
      public final DoubleValue largeCommonChance;
      public final DoubleValue smallTempChance;
      public final DoubleValue mediumTempChance;
      public final DoubleValue largeTempChance;
      public final DoubleValue smallEpChance;
      public final DoubleValue mediumEpChance;
      public final DoubleValue largeEpChance;
      public final DoubleValue smallMpChance;
      public final DoubleValue mediumMpChance;
      public final DoubleValue largeMpChance;
      public final IntValue smallEpAmount;
      public final IntValue mediumEpAmount;
      public final IntValue largeEpAmount;
      public final IntValue smallMpAmount;
      public final IntValue mediumMpAmount;
      public final IntValue largeMpAmount;
      public final IntValue smallItemChipRefund;
      public final IntValue mediumItemChipRefund;
      public final IntValue largeItemChipRefund;
      public final DoubleValue game1ItemRefundMultiplier;
      public final DoubleValue game2ItemRefundMultiplier;
      public final IntValue ultimatePoolFailMinimumRefund;
      public final IntValue ultimatePoolFailRefundDivisor;
      public final DoubleValue uniquePoolFailEpMultiplier;
      public final DoubleValue extraPoolFailMpMultiplier;
      public final IntValue commonPoolFailMinimumRefund;
      public final IntValue commonPoolFailRefundDivisor;
      public final IntValue tempPoolFailMinimumRefund;
      public final IntValue tempPoolFailRefundDivisor;
      public final DoubleValue epRewardRandomMinimumMultiplier;
      public final DoubleValue epRewardRandomExtraMultiplier;
      public final DoubleValue mpRewardRandomMinimumMultiplier;
      public final DoubleValue mpRewardRandomExtraMultiplier;
      public final IntValue temporarySkillDurationTicks;
      public final IntValue tempCommonWeight;
      public final IntValue tempExtraWeight;
      public final IntValue tempUniqueWeight;

      MammonCasino(Builder builder) {
         builder.comment("Obtainment settings for Mammon Casino.");
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.comment("Magicule cost required to naturally acquire Mammon Casino.")
            .defineInRange("acquirementMagiculeCost", 850000.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.comment("Starting mastery value when acquired.").defineInRange("acquirementMastery", 0, -1, Integer.MAX_VALUE);
         this.requiredSkillId = builder.comment("Required skill registry ID for obtainment. Empty string disables the required skill check.")
            .define("requiredSkillId", "tensura:greed");
         this.requiredSkillMustBeMastered = builder.comment("If true, the required skill must be mastered before Mammon Casino can be acquired.")
            .define("requiredSkillMustBeMastered", true);
         builder.pop();
         builder.comment("Chip economy settings.");
         builder.push("chips");
         this.chipsPerTrackedKill = builder.comment("Chips gained for each tracked kill.").defineInRange("chipsPerTrackedKill", 1, 0, Integer.MAX_VALUE);
         this.duplicateKillProtectionTicks = builder.comment("Prevents the same killed entity from awarding chips again inside this time window.")
            .defineInRange("duplicateKillProtectionTicks", 80, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Allowed bet tiers. Incoming bets are normalized to one of these values.");
         builder.push("bets");
         this.smallBet = builder.comment("Small bet tier.").defineInRange("smallBet", 50, 1, Integer.MAX_VALUE);
         this.mediumBet = builder.comment("Medium bet tier.").defineInRange("mediumBet", 250, 1, Integer.MAX_VALUE);
         this.largeBet = builder.comment("Large bet tier.").defineInRange("largeBet", 1000, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Reward multipliers by selected casino game.");
         builder.push("game_multipliers");
         this.game0RewardMultiplier = builder.comment("Reward multiplier for game 0.").defineInRange("game0RewardMultiplier", 1.0, 0.0, 100.0);
         this.game1RewardMultiplier = builder.comment("Reward multiplier for game 1.").defineInRange("game1RewardMultiplier", 1.05, 0.0, 100.0);
         this.game2RewardMultiplier = builder.comment("Reward multiplier for game 2.").defineInRange("game2RewardMultiplier", 1.1, 0.0, 100.0);
         builder.pop();
         builder.comment("Gamble outcome chances. Values are percentages. 14.0 means 14 percent, 0.03 means 0.03 percent.");
         builder.push("chances");
         this.smallLoseChance = builder.defineInRange("smallLoseChance", 14.0, 0.0, 100.0);
         this.mediumLoseChance = builder.defineInRange("mediumLoseChance", 12.5, 0.0, 100.0);
         this.largeLoseChance = builder.defineInRange("largeLoseChance", 11.0, 0.0, 100.0);
         this.smallUltimateChance = builder.defineInRange("smallUltimateChance", 0.002, 0.0, 100.0);
         this.mediumUltimateChance = builder.defineInRange("mediumUltimateChance", 0.01, 0.0, 100.0);
         this.largeUltimateChance = builder.defineInRange("largeUltimateChance", 0.03, 0.0, 100.0);
         this.smallUniqueChance = builder.defineInRange("smallUniqueChance", 0.04, 0.0, 100.0);
         this.mediumUniqueChance = builder.defineInRange("mediumUniqueChance", 0.12, 0.0, 100.0);
         this.largeUniqueChance = builder.defineInRange("largeUniqueChance", 0.3, 0.0, 100.0);
         this.smallExtraChance = builder.defineInRange("smallExtraChance", 3.0, 0.0, 100.0);
         this.mediumExtraChance = builder.defineInRange("mediumExtraChance", 4.5, 0.0, 100.0);
         this.largeExtraChance = builder.defineInRange("largeExtraChance", 6.0, 0.0, 100.0);
         this.smallCommonChance = builder.defineInRange("smallCommonChance", 7.0, 0.0, 100.0);
         this.mediumCommonChance = builder.defineInRange("mediumCommonChance", 8.5, 0.0, 100.0);
         this.largeCommonChance = builder.defineInRange("largeCommonChance", 10.0, 0.0, 100.0);
         this.smallTempChance = builder.defineInRange("smallTempChance", 2.5, 0.0, 100.0);
         this.mediumTempChance = builder.defineInRange("mediumTempChance", 3.5, 0.0, 100.0);
         this.largeTempChance = builder.defineInRange("largeTempChance", 4.0, 0.0, 100.0);
         this.smallEpChance = builder.defineInRange("smallEpChance", 16.0, 0.0, 100.0);
         this.mediumEpChance = builder.defineInRange("mediumEpChance", 18.0, 0.0, 100.0);
         this.largeEpChance = builder.defineInRange("largeEpChance", 20.0, 0.0, 100.0);
         this.smallMpChance = builder.defineInRange("smallMpChance", 18.0, 0.0, 100.0);
         this.mediumMpChance = builder.defineInRange("mediumMpChance", 19.0, 0.0, 100.0);
         this.largeMpChance = builder.defineInRange("largeMpChance", 20.0, 0.0, 100.0);
         builder.pop();
         builder.comment("EP and MP reward amounts by bet tier.");
         builder.push("amounts");
         this.smallEpAmount = builder.defineInRange("smallEpAmount", 8000, 0, Integer.MAX_VALUE);
         this.mediumEpAmount = builder.defineInRange("mediumEpAmount", 40000, 0, Integer.MAX_VALUE);
         this.largeEpAmount = builder.defineInRange("largeEpAmount", 175000, 0, Integer.MAX_VALUE);
         this.smallMpAmount = builder.defineInRange("smallMpAmount", 20000, 0, Integer.MAX_VALUE);
         this.mediumMpAmount = builder.defineInRange("mediumMpAmount", 100000, 0, Integer.MAX_VALUE);
         this.largeMpAmount = builder.defineInRange("largeMpAmount", 450000, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Chip refunds for item rewards and failed skill pools.");
         builder.push("refunds");
         this.smallItemChipRefund = builder.defineInRange("smallItemChipRefund", 3, 0, Integer.MAX_VALUE);
         this.mediumItemChipRefund = builder.defineInRange("mediumItemChipRefund", 15, 0, Integer.MAX_VALUE);
         this.largeItemChipRefund = builder.defineInRange("largeItemChipRefund", 65, 0, Integer.MAX_VALUE);
         this.game1ItemRefundMultiplier = builder.defineInRange("game1ItemRefundMultiplier", 1.05, 0.0, 100.0);
         this.game2ItemRefundMultiplier = builder.defineInRange("game2ItemRefundMultiplier", 1.15, 0.0, 100.0);
         this.ultimatePoolFailMinimumRefund = builder.defineInRange("ultimatePoolFailMinimumRefund", 5, 0, Integer.MAX_VALUE);
         this.ultimatePoolFailRefundDivisor = builder.defineInRange("ultimatePoolFailRefundDivisor", 20, 1, Integer.MAX_VALUE);
         this.uniquePoolFailEpMultiplier = builder.defineInRange("uniquePoolFailEpMultiplier", 0.45, 0.0, 100.0);
         this.extraPoolFailMpMultiplier = builder.defineInRange("extraPoolFailMpMultiplier", 0.45, 0.0, 100.0);
         this.commonPoolFailMinimumRefund = builder.defineInRange("commonPoolFailMinimumRefund", 1, 0, Integer.MAX_VALUE);
         this.commonPoolFailRefundDivisor = builder.defineInRange("commonPoolFailRefundDivisor", 50, 1, Integer.MAX_VALUE);
         this.tempPoolFailMinimumRefund = builder.defineInRange("tempPoolFailMinimumRefund", 1, 0, Integer.MAX_VALUE);
         this.tempPoolFailRefundDivisor = builder.defineInRange("tempPoolFailRefundDivisor", 40, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Random reward variance for EP and MP rewards.");
         builder.push("reward_variance");
         this.epRewardRandomMinimumMultiplier = builder.defineInRange("epRewardRandomMinimumMultiplier", 0.8, 0.0, 100.0);
         this.epRewardRandomExtraMultiplier = builder.defineInRange("epRewardRandomExtraMultiplier", 0.25, 0.0, 100.0);
         this.mpRewardRandomMinimumMultiplier = builder.defineInRange("mpRewardRandomMinimumMultiplier", 0.8, 0.0, 100.0);
         this.mpRewardRandomExtraMultiplier = builder.defineInRange("mpRewardRandomExtraMultiplier", 0.25, 0.0, 100.0);
         builder.pop();
         builder.comment("Temporary skill reward settings.");
         builder.push("temporary_skills");
         this.temporarySkillDurationTicks = builder.comment("Temporary skill duration. 36000 ticks = 30 minutes.")
            .defineInRange("temporarySkillDurationTicks", 36000, 1, Integer.MAX_VALUE);
         this.tempCommonWeight = builder.comment("Weight for temporary common skill rewards.").defineInRange("tempCommonWeight", 65, 0, Integer.MAX_VALUE);
         this.tempExtraWeight = builder.comment("Weight for temporary extra skill rewards.").defineInRange("tempExtraWeight", 27, 0, Integer.MAX_VALUE);
         this.tempUniqueWeight = builder.comment("Weight for temporary unique skill rewards.").defineInRange("tempUniqueWeight", 8, 0, Integer.MAX_VALUE);
         builder.pop();
      }
   }

   public static final class MichaelAngelOfLocksConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final IntValue maxMastery;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final IntValue passiveRefreshTicks;
      public final DoubleValue eloahArmor;
      public final IntValue spatialSuppressionTicks;
      public final DoubleValue kingsPresenceRadius;
      public final IntValue summonCost;
      public final IntValue segvaCost;
      public final DoubleValue segvaRange;
      public final IntValue segvaHitCooldownTicks;
      public final IntValue segvaMissCooldownTicks;
      public final IntValue segvaApplyCooldownTicks;
      public final IntValue segvaAbilityOffTicks;
      public final IntValue sealMarkDurationTicks;
      public final IntValue lataibBarrageCost;
      public final DoubleValue lataibTargetRange;
      public final IntValue lataibBarrageDurationTicks;
      public final DoubleValue lataibBarrageDamage;
      public final IntValue lataibBarrageCooldownTicks;
      public final IntValue lataibGateCooldownTicks;
      public final IntValue spatialStorageCost;
      public final IntValue spatialStorageCooldownTicks;
      public final IntValue spatialStorageSlots;
      public final IntValue spatialStorageStack;
      public final IntValue lataibHalberdShotCost;
      public final DoubleValue lataibHalberdShotRange;
      public final DoubleValue lataibHalberdShotRangeMastered;
      public final DoubleValue lataibHalberdShotDamage;
      public final DoubleValue lataibHalberdShotDamageMastered;
      public final IntValue lataibHalberdShotCooldownTicks;
      public final IntValue lataibHalberdShotCooldownMasteredTicks;
      public final IntValue shifuruCost;
      public final IntValue shifuruMeteorCost;
      public final IntValue shifuruMeteorHoldTicks;
      public final IntValue shifuruMeteorCooldownTicks;
      public final DoubleValue shifuruSmallMeteorDamage;
      public final DoubleValue shifuruBigMeteorDamage;
      public final IntValue jerezCost;
      public final DoubleValue jerezRange;
      public final IntValue jerezCooldownTicks;
      public final IntValue jerezCooldownMasteredTicks;
      public final IntValue keterCastTicks;
      public final IntValue keterCostPerSecond;
      public final DoubleValue keterDamagePerHalfSecond;
      public final DoubleValue keterRange;
      public final DoubleValue keterRangeMastered;

      MichaelAngelOfLocksConfig(Builder builder) {
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.defineInRange("acquirementMagiculeCost", 8000000.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.defineInRange("acquirementMastery", 0, -1, Integer.MAX_VALUE);
         this.maxMastery = builder.defineInRange("maxMastery", 1000, 1, Integer.MAX_VALUE);
         this.requiredSkillId = builder.define("requiredSkillId", "tensura:oppressor");
         this.requiredSkillMustBeMastered = builder.define("requiredSkillMustBeMastered", false);
         builder.pop();
         builder.push("passives");
         this.passiveRefreshTicks = builder.defineInRange("passiveRefreshTicks", 80, 1, Integer.MAX_VALUE);
         this.eloahArmor = builder.defineInRange("eloahArmor", 50.0, 0.0, 2048.0);
         this.spatialSuppressionTicks = builder.defineInRange("spatialSuppressionTicks", 200, 1, Integer.MAX_VALUE);
         this.kingsPresenceRadius = builder.defineInRange("kingsPresenceRadius", 12.0, 0.0, 128.0);
         builder.pop();
         builder.push("summon");
         this.summonCost = builder.defineInRange("summonCost", 0, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("segva");
         this.segvaCost = builder.defineInRange("segvaCost", 15000, 0, Integer.MAX_VALUE);
         this.segvaRange = builder.defineInRange("segvaRange", 18.0, 1.0, 128.0);
         this.segvaHitCooldownTicks = builder.defineInRange("segvaHitCooldownTicks", 8, 0, Integer.MAX_VALUE);
         this.segvaMissCooldownTicks = builder.defineInRange("segvaMissCooldownTicks", 5, 0, Integer.MAX_VALUE);
         this.segvaApplyCooldownTicks = builder.defineInRange("segvaApplyCooldownTicks", 100, 0, Integer.MAX_VALUE);
         this.segvaAbilityOffTicks = builder.defineInRange("segvaAbilityOffTicks", 100, 1, Integer.MAX_VALUE);
         this.sealMarkDurationTicks = builder.defineInRange("sealMarkDurationTicks", 1200, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.push("lataib");
         this.lataibBarrageCost = builder.defineInRange("lataibBarrageCost", 10000, 0, Integer.MAX_VALUE);
         this.lataibTargetRange = builder.defineInRange("lataibTargetRange", 15.0, 1.0, 128.0);
         this.lataibBarrageDurationTicks = builder.defineInRange("lataibBarrageDurationTicks", 400, 1, Integer.MAX_VALUE);
         this.lataibBarrageDamage = builder.defineInRange("lataibBarrageDamage", 75.0, 0.0, Float.MAX_VALUE);
         this.lataibBarrageCooldownTicks = builder.defineInRange("lataibBarrageCooldownTicks", 10, 0, Integer.MAX_VALUE);
         this.lataibGateCooldownTicks = builder.defineInRange("lataibGateCooldownTicks", 5, 0, Integer.MAX_VALUE);
         this.spatialStorageCost = builder.defineInRange("spatialStorageCost", 3000, 0, Integer.MAX_VALUE);
         this.spatialStorageCooldownTicks = builder.defineInRange("spatialStorageCooldownTicks", 2, 0, Integer.MAX_VALUE);
         this.spatialStorageSlots = builder.defineInRange("spatialStorageSlots", 108, 1, 216);
         this.spatialStorageStack = builder.defineInRange("spatialStorageStack", 999, 1, 999);
         this.lataibHalberdShotCost = builder.defineInRange("lataibHalberdShotCost", 35000, 0, Integer.MAX_VALUE);
         this.lataibHalberdShotRange = builder.defineInRange("lataibHalberdShotRange", 9.0, 1.0, 128.0);
         this.lataibHalberdShotRangeMastered = builder.defineInRange("lataibHalberdShotRangeMastered", 14.0, 1.0, 128.0);
         this.lataibHalberdShotDamage = builder.defineInRange("lataibHalberdShotDamage", 500.0, 0.0, Float.MAX_VALUE);
         this.lataibHalberdShotDamageMastered = builder.defineInRange("lataibHalberdShotDamageMastered", 650.0, 0.0, Float.MAX_VALUE);
         this.lataibHalberdShotCooldownTicks = builder.defineInRange("lataibHalberdShotCooldownTicks", 10, 0, Integer.MAX_VALUE);
         this.lataibHalberdShotCooldownMasteredTicks = builder.defineInRange("lataibHalberdShotCooldownMasteredTicks", 5, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("shifuru");
         this.shifuruCost = builder.defineInRange("shifuruCost", 10000, 0, Integer.MAX_VALUE);
         this.shifuruMeteorCost = builder.defineInRange("shifuruMeteorCost", 60000, 0, Integer.MAX_VALUE);
         this.shifuruMeteorHoldTicks = builder.defineInRange("shifuruMeteorHoldTicks", 200, 1, Integer.MAX_VALUE);
         this.shifuruMeteorCooldownTicks = builder.defineInRange("shifuruMeteorCooldownTicks", 100, 0, Integer.MAX_VALUE);
         this.shifuruSmallMeteorDamage = builder.defineInRange("shifuruSmallMeteorDamage", 120.0, 0.0, Float.MAX_VALUE);
         this.shifuruBigMeteorDamage = builder.defineInRange("shifuruBigMeteorDamage", 850.0, 0.0, Float.MAX_VALUE);
         builder.pop();
         builder.push("jerez");
         this.jerezCost = builder.defineInRange("jerezCost", 40000, 0, Integer.MAX_VALUE);
         this.jerezRange = builder.defineInRange("jerezRange", 18.0, 1.0, 128.0);
         this.jerezCooldownTicks = builder.defineInRange("jerezCooldownTicks", 20, 0, Integer.MAX_VALUE);
         this.jerezCooldownMasteredTicks = builder.defineInRange("jerezCooldownMasteredTicks", 3, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("keter");
         this.keterCastTicks = builder.defineInRange("keterCastTicks", 50, 0, Integer.MAX_VALUE);
         this.keterCostPerSecond = builder.defineInRange("keterCostPerSecond", 750, 0, Integer.MAX_VALUE);
         this.keterDamagePerHalfSecond = builder.defineInRange("keterDamagePerHalfSecond", 100.0, 0.0, Float.MAX_VALUE);
         this.keterRange = builder.defineInRange("keterRange", 24.0, 1.0, 256.0);
         this.keterRangeMastered = builder.defineInRange("keterRangeMastered", 30.0, 1.0, 256.0);
         builder.pop();
      }
   }

   public static final class MitamaFaithConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue maxMastery;
      public final DoubleValue recruitCost;
      public final DoubleValue transferCost;
      public final DoubleValue beamsCost;
      public final DoubleValue creationModeCost;
      public final DoubleValue edictCost;
      public final DoubleValue worshipCostDiscountDivisor;
      public final DoubleValue minimumCostMultiplier;
      public final DoubleValue recruitRange;
      public final DoubleValue transferRange;
      public final DoubleValue mobHpThreshold;
      public final DoubleValue mobEpThresholdRatio;
      public final DoubleValue mobWorshipEpDivisor;
      public final IntValue recruitSuccessCooldownTicks;
      public final IntValue recruitFailCooldownTicks;
      public final IntValue playerInviteCooldownTicks;
      public final DoubleValue playerBaseWorship;
      public final DoubleValue playerEpWorshipDivisor;
      public final DoubleValue damageMultiplierBase;
      public final DoubleValue damageMultiplierWorshipDivisor;
      public final DoubleValue healthPerWorship;
      public final DoubleValue attackDamagePerWorship;
      public final DoubleValue maxMagiculePerWorship;
      public final DoubleValue maxAuraPerWorship;
      public final DoubleValue nullResistanceDegradation;
      public final DoubleValue nullLawDegradation;
      public final DoubleValue nullDodgeNegateChance;
      public final BooleanValue nullCriticalHitImmunity;
      public final DoubleValue worshipPerExtraBeam;
      public final IntValue beamVisualCap;
      public final DoubleValue beamsBaseDamage;
      public final DoubleValue beamsBaseRadius;
      public final DoubleValue beamsRadiusSqrtDivisor;
      public final DoubleValue beamsRadiusCap;
      public final IntValue beamsCooldownTicks;
      public final DoubleValue edictBaseDamage;
      public final DoubleValue edictBaseRadius;
      public final DoubleValue edictRadiusSqrtDivisor;
      public final DoubleValue edictRadiusCap;
      public final IntValue edictCooldownTicks;
      public final IntValue edictOppressionDurationTicks;
      public final DoubleValue edictOppressionWorshipDivisor;
      public final IntValue edictOppressionMaxAmplifier;
      public final DoubleValue followerHealBase;
      public final DoubleValue followerHealWorshipDivisor;
      public final DoubleValue followerEnergyBase;
      public final DoubleValue followerEnergyWorshipDivisor;
      public final DoubleValue magiculeWorshipMultiplier;
      public final DoubleValue magicWorshipMultiplier;
      public final DoubleValue auraWorshipMultiplier;
      public final DoubleValue epWorshipMultiplier;
      public final DoubleValue soulWorshipMultiplier;
      public final DoubleValue commonSkillWorship;
      public final DoubleValue extraSkillWorship;
      public final DoubleValue intrinsicSkillWorship;
      public final DoubleValue resistanceSkillWorship;
      public final DoubleValue uniqueSkillWorship;
      public final DoubleValue ultimateSkillWorship;
      public final DoubleValue skillMasteryMinimumBonus;
      public final DoubleValue skillMasteryDivisor;
      public final DoubleValue createCommonCost;
      public final DoubleValue createExtraCost;
      public final DoubleValue createResistanceCost;
      public final DoubleValue createIntrinsicCost;
      public final DoubleValue createUniqueCost;

      MitamaFaithConfig(Builder builder) {
         builder.comment("Mitama Faith / worship system config.");
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.comment("Magicule cost required to naturally acquire Mitama Faith.")
            .defineInRange("acquirementMagiculeCost", 50000.0, 0.0, Double.MAX_VALUE);
         this.maxMastery = builder.comment("Maximum mastery value for Mitama Faith.").defineInRange("maxMastery", 1000, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.push("costs");
         this.recruitCost = builder.defineInRange("recruitCost", 1000.0, 0.0, Double.MAX_VALUE);
         this.transferCost = builder.defineInRange("transferCost", 0.0, 0.0, Double.MAX_VALUE);
         this.beamsCost = builder.defineInRange("beamsCost", 6000.0, 0.0, Double.MAX_VALUE);
         this.creationModeCost = builder.defineInRange("creationModeCost", 25000.0, 0.0, Double.MAX_VALUE);
         this.edictCost = builder.defineInRange("edictCost", 50000.0, 0.0, Double.MAX_VALUE);
         this.worshipCostDiscountDivisor = builder.comment("Worship needed to reduce costs toward the minimum multiplier.")
            .defineInRange("worshipCostDiscountDivisor", 500000.0, 1.0, Double.MAX_VALUE);
         this.minimumCostMultiplier = builder.comment("Minimum mode cost multiplier after worship discount.")
            .defineInRange("minimumCostMultiplier", 0.01, 0.0, 1.0);
         builder.pop();
         builder.push("recruitment");
         this.recruitRange = builder.defineInRange("recruitRange", 32.0, 1.0, 512.0);
         this.transferRange = builder.defineInRange("transferRange", 16.0, 1.0, 512.0);
         this.mobHpThreshold = builder.comment("Mob can be recruited if its health ratio is at or below this value. 0.20 = 20 percent.")
            .defineInRange("mobHpThreshold", 0.2, 0.0, 1.0);
         this.mobEpThresholdRatio = builder.comment("Mob can be recruited if its max EP is at or below user EP multiplied by this value. 0.01 = 1 percent.")
            .defineInRange("mobEpThresholdRatio", 0.01, 0.0, 1.0);
         this.mobWorshipEpDivisor = builder.comment("Mob worship points = mob max EP divided by this value, minimum 1.")
            .defineInRange("mobWorshipEpDivisor", 1000.0, 1.0, Double.MAX_VALUE);
         this.recruitSuccessCooldownTicks = builder.defineInRange("recruitSuccessCooldownTicks", 80, 0, Integer.MAX_VALUE);
         this.recruitFailCooldownTicks = builder.defineInRange("recruitFailCooldownTicks", 40, 0, Integer.MAX_VALUE);
         this.playerInviteCooldownTicks = builder.defineInRange("playerInviteCooldownTicks", 100, 0, Integer.MAX_VALUE);
         this.playerBaseWorship = builder.comment("Minimum worship value of a player supporter.")
            .defineInRange("playerBaseWorship", 100.0, 0.0, Double.MAX_VALUE);
         this.playerEpWorshipDivisor = builder.comment("Player worship from EP = player max EP divided by this value.")
            .defineInRange("playerEpWorshipDivisor", 500.0, 1.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("passive_scaling");
         this.damageMultiplierBase = builder.defineInRange("damageMultiplierBase", 1.0, 0.0, Double.MAX_VALUE);
         this.damageMultiplierWorshipDivisor = builder.comment("Outgoing damage multiplier = base + worship / this value.")
            .defineInRange("damageMultiplierWorshipDivisor", 1000.0, 1.0, Double.MAX_VALUE);
         this.healthPerWorship = builder.comment("Flat max health added per worship point.").defineInRange("healthPerWorship", 0.004, 0.0, Double.MAX_VALUE);
         this.attackDamagePerWorship = builder.comment("Flat attack damage added per worship point.")
            .defineInRange("attackDamagePerWorship", 0.002, 0.0, Double.MAX_VALUE);
         this.maxMagiculePerWorship = builder.comment("Flat max magicule added per worship point.")
            .defineInRange("maxMagiculePerWorship", 0.5, 0.0, Double.MAX_VALUE);
         this.maxAuraPerWorship = builder.comment("Flat max aura added per worship point.").defineInRange("maxAuraPerWorship", 0.5, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("null_toggle");
         this.nullResistanceDegradation = builder.comment("Resistance Degradation attribute added while Null is toggled.")
            .defineInRange("nullResistanceDegradation", 1.0, 0.0, 1.0);
         this.nullLawDegradation = builder.comment("Law Degradation attribute added while Null is toggled, used for nullification degradation.")
            .defineInRange("nullLawDegradation", 1.0, 0.0, 1.0);
         this.nullDodgeNegateChance = builder.comment("Dodge Negate Chance added while Null is toggled. 50 = negate 50 percent of dodge chance.")
            .defineInRange("nullDodgeNegateChance", 50.0, 0.0, 100.0);
         this.nullCriticalHitImmunity = builder.comment("If true, Null toggle interrupts critical hit chance against the user.")
            .define("nullCriticalHitImmunity", true);
         builder.pop();
         builder.push("homing_beams");
         this.worshipPerExtraBeam = builder.comment("One extra logical beam is gained per this much worship.")
            .defineInRange("worshipPerExtraBeam", 200.0, 1.0, Double.MAX_VALUE);
         this.beamVisualCap = builder.comment("Maximum beam targets/VFX processed per cast. Damage still scales using logical beams.")
            .defineInRange("beamVisualCap", 64, 1, 1024);
         this.beamsBaseDamage = builder.defineInRange("beamsBaseDamage", 12.0, 0.0, Float.MAX_VALUE);
         this.beamsBaseRadius = builder.defineInRange("beamsBaseRadius", 12.0, 0.0, 512.0);
         this.beamsRadiusSqrtDivisor = builder.defineInRange("beamsRadiusSqrtDivisor", 3.0, 1.0, Double.MAX_VALUE);
         this.beamsRadiusCap = builder.defineInRange("beamsRadiusCap", 48.0, 0.0, 512.0);
         this.beamsCooldownTicks = builder.defineInRange("beamsCooldownTicks", 80, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("heavens_edict");
         this.edictBaseDamage = builder.defineInRange("edictBaseDamage", 80.0, 0.0, Float.MAX_VALUE);
         this.edictBaseRadius = builder.defineInRange("edictBaseRadius", 20.0, 0.0, 512.0);
         this.edictRadiusSqrtDivisor = builder.defineInRange("edictRadiusSqrtDivisor", 2.0, 1.0, Double.MAX_VALUE);
         this.edictRadiusCap = builder.defineInRange("edictRadiusCap", 96.0, 0.0, 512.0);
         this.edictCooldownTicks = builder.defineInRange("edictCooldownTicks", 300, 0, Integer.MAX_VALUE);
         this.edictOppressionDurationTicks = builder.defineInRange("edictOppressionDurationTicks", 120, 1, Integer.MAX_VALUE);
         this.edictOppressionWorshipDivisor = builder.defineInRange("edictOppressionWorshipDivisor", 10000.0, 1.0, Double.MAX_VALUE);
         this.edictOppressionMaxAmplifier = builder.defineInRange("edictOppressionMaxAmplifier", 9, 0, 255);
         this.followerHealBase = builder.defineInRange("followerHealBase", 10.0, 0.0, Float.MAX_VALUE);
         this.followerHealWorshipDivisor = builder.defineInRange("followerHealWorshipDivisor", 1000.0, 1.0, Double.MAX_VALUE);
         this.followerEnergyBase = builder.defineInRange("followerEnergyBase", 100.0, 0.0, Double.MAX_VALUE);
         this.followerEnergyWorshipDivisor = builder.defineInRange("followerEnergyWorshipDivisor", 10.0, 1.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("donations");
         this.magiculeWorshipMultiplier = builder.defineInRange("magiculeWorshipMultiplier", 0.1, 0.0, Double.MAX_VALUE);
         this.magicWorshipMultiplier = builder.defineInRange("magicWorshipMultiplier", 0.1, 0.0, Double.MAX_VALUE);
         this.auraWorshipMultiplier = builder.defineInRange("auraWorshipMultiplier", 0.1, 0.0, Double.MAX_VALUE);
         this.epWorshipMultiplier = builder.defineInRange("epWorshipMultiplier", 1.0, 0.0, Double.MAX_VALUE);
         this.soulWorshipMultiplier = builder.defineInRange("soulWorshipMultiplier", 25.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("skill_worship_values");
         this.commonSkillWorship = builder.defineInRange("commonSkillWorship", 3500.0, 0.0, Double.MAX_VALUE);
         this.extraSkillWorship = builder.defineInRange("extraSkillWorship", 15000.0, 0.0, Double.MAX_VALUE);
         this.intrinsicSkillWorship = builder.defineInRange("intrinsicSkillWorship", 25000.0, 0.0, Double.MAX_VALUE);
         this.resistanceSkillWorship = builder.defineInRange("resistanceSkillWorship", 8000.0, 0.0, Double.MAX_VALUE);
         this.uniqueSkillWorship = builder.defineInRange("uniqueSkillWorship", 75000.0, 0.0, Double.MAX_VALUE);
         this.ultimateSkillWorship = builder.defineInRange("ultimateSkillWorship", 250000.0, 0.0, Double.MAX_VALUE);
         this.skillMasteryMinimumBonus = builder.defineInRange("skillMasteryMinimumBonus", 10.0, 0.0, Double.MAX_VALUE);
         this.skillMasteryDivisor = builder.defineInRange("skillMasteryDivisor", 1000.0, 1.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("skill_creation");
         this.createCommonCost = builder.defineInRange("createCommonCost", 50000.0, 0.0, Double.MAX_VALUE);
         this.createExtraCost = builder.defineInRange("createExtraCost", 250000.0, 0.0, Double.MAX_VALUE);
         this.createResistanceCost = builder.defineInRange("createResistanceCost", 125000.0, 0.0, Double.MAX_VALUE);
         this.createIntrinsicCost = builder.defineInRange("createIntrinsicCost", 600000.0, 0.0, Double.MAX_VALUE);
         this.createUniqueCost = builder.defineInRange("createUniqueCost", 5000000.0, 0.0, Double.MAX_VALUE);
         builder.pop();
      }
   }

   public static final class MorphConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final IntValue maxMastery;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final DoubleValue magiculeCost;
      public final IntValue cooldownTicks;
      public final DoubleValue range;
      public final DoubleValue maxTargetEpPercent;
      public final BooleanValue allowPlayerMorphs;
      public final ConfigValue<List<? extends String>> blacklistedEntities;

      MorphConfig(Builder builder) {
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.comment("Magicule cost required to naturally acquire Morph.")
            .defineInRange("acquirementMagiculeCost", 10000.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.comment("Starting mastery value when acquired.").defineInRange("acquirementMastery", 0, -1, Integer.MAX_VALUE);
         this.maxMastery = builder.comment("Maximum mastery value for Morph.").defineInRange("maxMastery", 1000, 1, Integer.MAX_VALUE);
         this.requiredSkillId = builder.comment("Required skill registry ID for obtainment. Empty disables the required skill check.")
            .define("requiredSkillId", "tensura:spatial_domination");
         this.requiredSkillMustBeMastered = builder.comment("If true, the required skill must be mastered before Morph can be acquired.")
            .define("requiredSkillMustBeMastered", true);
         builder.pop();
         builder.push("general");
         this.magiculeCost = builder.comment("Magicule cost to transform into the selected target.")
            .defineInRange("magiculeCost", 500.0, 0.0, Double.MAX_VALUE);
         this.cooldownTicks = builder.comment("Cooldown in ticks after morphing or cancelling Morph.").defineInRange("cooldownTicks", 60, 0, Integer.MAX_VALUE);
         this.range = builder.comment("Maximum look range for choosing a morph target.").defineInRange("range", 12.0, 1.0, 128.0);
         this.maxTargetEpPercent = builder.comment("Target must have EP at or below this fraction of the user's max EP. 0.01 means 1 percent.")
            .defineInRange("maxTargetEpPercent", 0.01, 0.0, 1.0);
         this.allowPlayerMorphs = builder.comment("If true, Morph can try to copy players. Keep false or crashes may occur.")
            .define("allowPlayerMorphs", false);
         this.blacklistedEntities = builder.comment("Entity IDs Morph can never copy.")
            .defineListAllowEmpty("blacklistedEntities", List.of("minecraft:ender_dragon", "minecraft:wither"), o -> o instanceof String);
         builder.pop();
      }
   }

   public static final class NaberiusConfig {
      public final IntValue maxMastery;
      public final IntValue passiveMasteryIntervalTicks;
      public final DoubleValue manifestCost;
      public final DoubleValue chimeraCost;
      public final DoubleValue domainCost;
      public final DoubleValue lifeDrainCost;
      public final DoubleValue organFailureCost;
      public final DoubleValue hinataFusionCost;
      public final DoubleValue learnKnockbackResistance;
      public final DoubleValue learnMovementSpeed;
      public final DoubleValue learnAttackDamage;
      public final DoubleValue learnAttackSpeed;
      public final DoubleValue learnPresenceSense;
      public final DoubleValue learnChantSpeed;
      public final DoubleValue masteredResistanceDegradation;
      public final DoubleValue masteredProjectileDodgeChance;
      public final IntValue passiveEffectDurationTicks;
      public final IntValue passivePresenceSenseAmplifier;
      public final IntValue auraParticleIntervalTicks;
      public final IntValue domainBaseRadius;
      public final IntValue domainBaseDurationTicks;
      public final IntValue domainDurationPerPowerTicks;
      public final IntValue domainRadiusPowerDivisor;
      public final IntValue domainHoldCheckIntervalTicks;
      public final DoubleValue domainHoldBaseCost;
      public final DoubleValue domainHoldCostPerSecond;
      public final DoubleValue domainReleaseBaseCost;
      public final DoubleValue domainReleaseCostPerSecond;
      public final DoubleValue domainReleaseCostPerSecondSquared;
      public final IntValue domainCooldownTicks;
      public final IntValue domainAuraIntervalTicks;
      public final IntValue domainBuffIntervalTicks;
      public final IntValue domainOrganDecayDurationTicks;
      public final IntValue domainOrganDecayBaseAmplifier;
      public final IntValue domainOrganDecayPowerDivisor;
      public final IntValue domainOrganDecayMaxAmplifier;
      public final DoubleValue domainSpiritualFlatDrainPerPower;
      public final DoubleValue domainSpiritualPercentBase;
      public final DoubleValue domainSpiritualPercentPerPower;
      public final IntValue maxStoredSummons;
      public final IntValue maxActiveSummons;
      public final IntValue summonDefaultSeconds;
      public final DoubleValue summonSpawnDistance;
      public final DoubleValue summonPlayerCloneHpBaseMultiplier;
      public final DoubleValue summonPlayerCloneDamageBaseMultiplier;
      public final DoubleValue summonDamageBase;
      public final DoubleValue summonEpDamageMultiplier;
      public final DoubleValue summonPlayerCloneMinimumDamage;
      public final DoubleValue summonArmorBase;
      public final DoubleValue summonEpArmorMultiplier;
      public final DoubleValue summonMovementSpeedMinimum;
      public final DoubleValue summonFollowRangeMinimum;
      public final DoubleValue summonScaleMinimum;
      public final DoubleValue summonScaleBonus;
      public final DoubleValue summonResistanceDegradation;
      public final IntValue manifestCooldownTicks;
      public final DoubleValue summonRetargetRange;
      public final IntValue summonRetargetIntervalTicks;
      public final DoubleValue summonCommandMoveSpeed;
      public final DoubleValue summonReviveMoveSpeed;
      public final DoubleValue reviveBaseCost;
      public final DoubleValue reviveExponentBase;
      public final DoubleValue reviveEpFactor;
      public final DoubleValue reviveHpFactor;
      public final IntValue lethalSaveCooldownTicks;
      public final IntValue lethalSaveInvulnerabilityTicks;
      public final DoubleValue lethalSaveHealthRatio;
      public final IntValue lethalSaveHakiCoatAmplifier;
      public final IntValue lethalSaveRegenerationAmplifier;
      public final DoubleValue domainSummonHpPerPower;
      public final DoubleValue domainSummonHpCap;
      public final DoubleValue domainSummonDamagePerPower;
      public final DoubleValue domainSummonDamageCap;
      public final DoubleValue domainSummonScalePerPower;
      public final DoubleValue domainSummonScaleCap;
      public final DoubleValue domainSummonHealPerPower;
      public final IntValue domainSummonHakiDurationTicks;
      public final IntValue domainSummonHakiPowerDivisor;
      public final IntValue domainSummonHakiMaxAmplifier;
      public final DoubleValue lifeDrainRange;
      public final DoubleValue lifeDrainDamage;
      public final IntValue lifeDrainEffectTicks;
      public final IntValue lifeDrainEffectAmplifier;
      public final DoubleValue lifeDrainSummonHealRatio;
      public final DoubleValue lifeDrainMinimumStolenMagicules;
      public final DoubleValue lifeDrainTargetEpStealRatio;
      public final IntValue lifeDrainCooldownTicks;
      public final DoubleValue stealSummonsRadius;
      public final IntValue stealSummonsCooldownTicks;
      public final IntValue stealSummonsSeconds;
      public final DoubleValue stealSummonsNavigationSpeed;
      public final DoubleValue organFailureRange;
      public final IntValue organFailureDurationTicks;
      public final IntValue organFailureOrganDecayAmplifier;
      public final IntValue organFailureBurdenAmplifier;
      public final IntValue organFailureFragilityAmplifier;
      public final IntValue organFailureCooldownTicks;
      public final IntValue hinataFusionCooldownTicks;
      public final DoubleValue fusionSpawnDistance;
      public final DoubleValue fusionFallbackMinHealth;
      public final DoubleValue fusionHinataMinHealth;
      public final DoubleValue fusionFallbackHpMultiplier;
      public final DoubleValue fusionHinataHpMultiplier;
      public final DoubleValue fusionFallbackMinDamage;
      public final DoubleValue fusionHinataMinDamage;
      public final DoubleValue fusionDamageBase;
      public final DoubleValue fusionFallbackEpDamageMultiplier;
      public final DoubleValue fusionHinataEpDamageMultiplier;
      public final DoubleValue fusionArmorBase;
      public final DoubleValue fusionArmorPerSacrifice;
      public final DoubleValue fusionFallbackSpeed;
      public final DoubleValue fusionHinataSpeed;
      public final DoubleValue fusionFollowRange;
      public final DoubleValue fusionFallbackScale;
      public final DoubleValue fusionHinataScale;
      public final DoubleValue fusionResistanceDegradation;
      public final DoubleValue fusionPresenceSense;
      public final IntValue fusionSummonedSeconds;
      public final DoubleValue fusionMinimumSpiritualHealth;
      public final IntValue combatSwitchOvergrowthRadius;
      public final IntValue combatSwitchOvergrowthAttempts;
      public final IntValue combatSwitchReleaseCharge;
      public final IntValue combatAttributeRefreshTicks;
      public final IntValue combatPassiveEffectDurationTicks;
      public final IntValue combatPassiveEffectAmplifier;
      public final IntValue combatForceWarPulseIntervalTicks;
      public final IntValue combatOvergrowthIntervalTicks;
      public final IntValue combatPassiveOvergrowthRadius;
      public final IntValue combatPassiveOvergrowthAttempts;
      public final IntValue combatRecoveryIntervalTicks;
      public final IntValue combatPressureIntervalTicks;
      public final IntValue combatAuraIntervalTicks;
      public final DoubleValue combatMaxHealthMultiplier;
      public final DoubleValue combatAttackDamageMultiplier;
      public final DoubleValue combatArmorBonus;
      public final DoubleValue combatMovementSpeedMultiplier;
      public final DoubleValue combatAttackSpeedMultiplier;
      public final DoubleValue combatSpiritualHealthRegenBonus;
      public final DoubleValue combatMagicCostMultiplierBonus;
      public final DoubleValue combatResistanceDegradationBonus;
      public final DoubleValue combatDodgeInvulnerabilityBonus;
      public final DoubleValue combatPresenceSenseBonus;
      public final DoubleValue combatDodgeNegateChanceBonus;
      public final IntValue combatDomainHoldCheckIntervalTicks;
      public final DoubleValue combatDomainHoldBaseCost;
      public final DoubleValue combatDomainHoldCostPerSecond;
      public final IntValue combatDomainHoldPulseIntervalTicks;
      public final DoubleValue combatDomainHoldMinHeal;
      public final DoubleValue combatDomainHoldHealMaxHealthRatio;
      public final DoubleValue combatDomainHoldMagiculeRestorePerCharge;
      public final DoubleValue combatDomainHoldAuraRestorePerCharge;
      public final DoubleValue combatDomainHoldSpiritualRestorePerCharge;
      public final IntValue combatDomainHoldOvergrowthMaxRadius;
      public final IntValue combatDomainHoldOvergrowthBaseRadius;
      public final IntValue combatDomainHoldOvergrowthChargeDivisor;
      public final IntValue combatDomainHoldOvergrowthBaseAttempts;
      public final IntValue combatDomainHoldOvergrowthAttemptsPerCharge;
      public final DoubleValue combatDomainHoldAllyRadiusBase;
      public final DoubleValue combatDomainHoldAllyRadiusPerCharge;
      public final IntValue combatDomainHoldAllyDurationBase;
      public final IntValue combatDomainHoldAllyDurationPerCharge;
      public final IntValue combatDomainHoldAllyMaxAmplifier;
      public final IntValue combatDomainHoldAllyAmplifierDivisor;
      public final IntValue combatDomainMinimumHeldTicks;
      public final DoubleValue combatDomainReleaseBaseCost;
      public final DoubleValue combatDomainReleaseCostPerSecond;
      public final DoubleValue combatDomainReleaseCostPerSecondSquared;
      public final DoubleValue combatDomainRadiusBase;
      public final DoubleValue combatDomainRadiusPerCharge;
      public final IntValue combatDomainAllyRegenDurationBase;
      public final IntValue combatDomainAllyRegenDurationPerCharge;
      public final IntValue combatDomainAllyRegenMaxAmplifier;
      public final IntValue combatDomainAllyRegenBaseAmplifier;
      public final IntValue combatDomainAllyRegenAmplifierDivisor;
      public final IntValue combatDomainAllyHakiDurationBase;
      public final IntValue combatDomainAllyHakiDurationPerCharge;
      public final IntValue combatDomainAllyHakiMaxAmplifier;
      public final IntValue combatDomainAllyHakiAmplifierDivisor;
      public final DoubleValue combatDomainAllyMagiculeRestoreBase;
      public final DoubleValue combatDomainAllyMagiculeRestorePerCharge;
      public final DoubleValue combatDomainAllyAuraRestoreBase;
      public final DoubleValue combatDomainAllyAuraRestorePerCharge;
      public final DoubleValue combatDomainAllySpiritualRestoreBase;
      public final DoubleValue combatDomainAllySpiritualRestorePerCharge;
      public final DoubleValue combatDomainEnemyDamageBase;
      public final DoubleValue combatDomainEnemyDamagePerCharge;
      public final IntValue combatDomainEnemyEffectDurationBase;
      public final IntValue combatDomainEnemyEffectDurationPerCharge;
      public final IntValue combatDomainEnemyOrganDecayMaxAmplifier;
      public final IntValue combatDomainEnemyOrganDecayBaseAmplifier;
      public final IntValue combatDomainEnemyOrganDecayAmplifierDivisor;
      public final IntValue combatDomainEnemyLifeSeizureMaxAmplifier;
      public final IntValue combatDomainEnemyLifeSeizureBaseAmplifier;
      public final IntValue combatDomainEnemyLifeSeizureAmplifierDivisor;
      public final DoubleValue combatDomainEnemyMagiculeStealBase;
      public final DoubleValue combatDomainEnemyMagiculeStealPerCharge;
      public final DoubleValue combatDomainEnemyAuraStealBase;
      public final DoubleValue combatDomainEnemyAuraStealPerCharge;
      public final DoubleValue combatDomainEnemySpiritualStealBase;
      public final DoubleValue combatDomainEnemySpiritualStealPerCharge;
      public final DoubleValue combatDomainPullBase;
      public final DoubleValue combatDomainPullPerCharge;
      public final DoubleValue combatDomainSelfHealMaxHealthRatio;
      public final DoubleValue combatDomainSelfHealPerHit;
      public final DoubleValue combatDomainSelfHealPerBlessed;
      public final DoubleValue combatDomainSelfMagiculeRestoreBase;
      public final DoubleValue combatDomainSelfMagiculeRestorePerHit;
      public final DoubleValue combatDomainSelfAuraRestoreBase;
      public final DoubleValue combatDomainSelfAuraRestorePerHit;
      public final DoubleValue combatDomainSelfSpiritualRestoreBase;
      public final DoubleValue combatDomainSelfSpiritualRestorePerHit;
      public final IntValue combatDomainReleaseOvergrowthRadiusBase;
      public final IntValue combatDomainReleaseOvergrowthAttemptsBase;
      public final IntValue combatDomainReleaseOvergrowthAttemptsPerCharge;
      public final IntValue combatDomainReleaseVisualChargeBonus;
      public final IntValue combatDomainCooldownTicks;
      public final DoubleValue genesisPulseCost;
      public final DoubleValue genesisPulseMinHeal;
      public final DoubleValue genesisPulseHealMaxHealthRatio;
      public final DoubleValue genesisPulseMagiculeRestore;
      public final DoubleValue genesisPulseAuraRestore;
      public final DoubleValue genesisPulseSpiritualRestore;
      public final DoubleValue genesisPulseAllyRadius;
      public final IntValue genesisPulseAllyRegenDurationTicks;
      public final IntValue genesisPulseAllyRegenAmplifier;
      public final IntValue genesisPulseHakiDurationTicks;
      public final IntValue genesisPulseHakiAmplifier;
      public final IntValue genesisPulseSelfRegenDurationTicks;
      public final IntValue genesisPulseSelfRegenAmplifier;
      public final IntValue genesisPulseOvergrowthRadius;
      public final IntValue genesisPulseOvergrowthAttempts;
      public final DoubleValue genesisPulseBurstRadius;
      public final IntValue genesisPulseCooldownTicks;
      public final DoubleValue spiritWarStepCost;
      public final DoubleValue spiritWarStepForwardVelocity;
      public final DoubleValue spiritWarStepUpVelocity;
      public final DoubleValue spiritWarStepHitboxForwardOffset;
      public final DoubleValue spiritWarStepHitboxHorizontalInflate;
      public final DoubleValue spiritWarStepHitboxVerticalInflate;
      public final DoubleValue spiritWarStepDamage;
      public final IntValue spiritWarStepLifeSeizureDurationTicks;
      public final IntValue spiritWarStepLifeSeizureAmplifier;
      public final DoubleValue spiritWarStepMagiculeSteal;
      public final DoubleValue spiritWarStepAuraSteal;
      public final DoubleValue spiritWarStepSpiritualSteal;
      public final DoubleValue spiritWarStepKnockbackStrength;
      public final DoubleValue spiritWarStepMinHealOnHit;
      public final DoubleValue spiritWarStepHealPerHit;
      public final DoubleValue spiritWarStepMagiculeRestorePerHit;
      public final DoubleValue spiritWarStepAuraRestorePerHit;
      public final DoubleValue spiritWarStepSpiritualRestorePerHit;
      public final DoubleValue spiritWarStepBurstRadius;
      public final IntValue spiritWarStepCooldownTicks;
      public final DoubleValue samsaraBloomCost;
      public final DoubleValue samsaraBloomRadius;
      public final DoubleValue samsaraBloomDamageBase;
      public final DoubleValue samsaraBloomMissingHealthDamageRatio;
      public final IntValue samsaraBloomEffectDurationTicks;
      public final IntValue samsaraBloomOrganDecayAmplifier;
      public final IntValue samsaraBloomLifeSeizureAmplifier;
      public final DoubleValue samsaraBloomMagiculeSteal;
      public final DoubleValue samsaraBloomAuraSteal;
      public final DoubleValue samsaraBloomSpiritualSteal;
      public final DoubleValue samsaraBloomPullStrength;
      public final IntValue samsaraBloomRootAttemptsPerTarget;
      public final DoubleValue samsaraBloomMinHeal;
      public final DoubleValue samsaraBloomHealPerHit;
      public final DoubleValue samsaraBloomMagiculeRestorePerHit;
      public final DoubleValue samsaraBloomAuraRestorePerHit;
      public final DoubleValue samsaraBloomSpiritualRestorePerHit;
      public final IntValue samsaraBloomOvergrowthRadius;
      public final IntValue samsaraBloomOvergrowthAttempts;
      public final IntValue samsaraBloomReleaseCharge;
      public final IntValue samsaraBloomCooldownTicks;
      public final DoubleValue deathlessLifeSiphonRange;
      public final DoubleValue deathlessLifeSiphonCost;
      public final IntValue deathlessLifeSiphonBypassTicks;
      public final DoubleValue deathlessLifeSiphonMaxHealthDamageCapRatio;
      public final DoubleValue deathlessLifeSiphonDamageBase;
      public final DoubleValue deathlessLifeSiphonDamageMaxHealthRatio;
      public final IntValue deathlessLifeSiphonEffectDurationTicks;
      public final IntValue deathlessLifeSiphonLifeSeizureAmplifier;
      public final IntValue deathlessLifeSiphonOrganDecayAmplifier;
      public final DoubleValue deathlessLifeSiphonMagiculeSteal;
      public final DoubleValue deathlessLifeSiphonAuraSteal;
      public final DoubleValue deathlessLifeSiphonSpiritualSteal;
      public final DoubleValue deathlessLifeSiphonExecuteMinHealth;
      public final DoubleValue deathlessLifeSiphonExecuteMaxHealthRatio;
      public final DoubleValue deathlessLifeSiphonMinHeal;
      public final DoubleValue deathlessLifeSiphonHealDamageRatio;
      public final DoubleValue deathlessLifeSiphonSpiritualRestoreStolenRatio;
      public final DoubleValue deathlessLifeSiphonTargetBurstRadius;
      public final IntValue deathlessLifeSiphonCooldownTicks;
      public final DoubleValue bestowOneLifeRange;
      public final DoubleValue bestowOneLifeCost;
      public final IntValue bestowOneLifeRegenDurationTicks;
      public final IntValue bestowOneLifeRegenAmplifier;
      public final IntValue bestowOneLifeHakiDurationTicks;
      public final IntValue bestowOneLifeHakiAmplifier;
      public final DoubleValue bestowOneLifeMagiculeRestore;
      public final DoubleValue bestowOneLifeAuraRestore;
      public final DoubleValue bestowOneLifeSpiritualRestore;
      public final DoubleValue bestowOneLifeBurstRadius;
      public final IntValue bestowOneLifeCooldownTicks;
      public final DoubleValue forceEvolutionRange;
      public final IntValue forceRaceEvolutionRequiredProgress;
      public final IntValue forcePlayerRaceEvolutionSoulCost;
      public final DoubleValue forcePlayerRaceEvolutionCost;
      public final IntValue forcePlayerRaceEvolutionReleaseCharge;
      public final IntValue forcePlayerRaceEvolutionOvergrowthRadius;
      public final IntValue forcePlayerRaceEvolutionOvergrowthAttempts;
      public final IntValue forcePlayerRaceEvolutionCooldownTicks;
      public final IntValue forcePlayerAwakeningSoulCost;
      public final DoubleValue forcePlayerAwakeningCost;
      public final IntValue forcePlayerAwakeningReleaseCharge;
      public final IntValue forcePlayerAwakeningOvergrowthRadius;
      public final IntValue forcePlayerAwakeningOvergrowthAttempts;
      public final IntValue forcePlayerAwakeningCooldownTicks;
      public final IntValue forceSubordinateEvolutionSoulCostBase;
      public final IntValue forceSubordinateEvolutionSoulCostPerStage;
      public final DoubleValue forceSubordinateEvolutionCostBase;
      public final DoubleValue forceSubordinateEvolutionCostPerStage;
      public final DoubleValue forceSubordinateEvolutionHpBase;
      public final DoubleValue forceSubordinateEvolutionHpPerStage;
      public final DoubleValue forceSubordinateEvolutionDamageBase;
      public final DoubleValue forceSubordinateEvolutionDamagePerStage;
      public final DoubleValue forceSubordinateEvolutionSpeedBase;
      public final DoubleValue forceSubordinateEvolutionSpeedPerStage;
      public final DoubleValue forceSubordinateEvolutionScaleCap;
      public final DoubleValue forceSubordinateEvolutionScaleBase;
      public final DoubleValue forceSubordinateEvolutionScalePerStage;
      public final IntValue forceSubordinateEvolutionMaxTrackedModifierStage;
      public final DoubleValue forceSubordinateEvolutionDegradationBase;
      public final DoubleValue forceSubordinateEvolutionDegradationPerStage;
      public final IntValue forceSubordinateEvolutionHakiDurationTicks;
      public final IntValue forceSubordinateEvolutionHakiMaxAmplifier;
      public final IntValue forceSubordinateEvolutionHakiBaseAmplifier;
      public final IntValue forceSubordinateEvolutionRegenDurationTicks;
      public final IntValue forceSubordinateEvolutionRegenMaxAmplifier;
      public final IntValue forceSubordinateEvolutionRegenBaseAmplifier;
      public final DoubleValue forceSubordinateEvolutionMagiculeRestoreBase;
      public final DoubleValue forceSubordinateEvolutionMagiculeRestorePerStage;
      public final DoubleValue forceSubordinateEvolutionAuraRestoreBase;
      public final DoubleValue forceSubordinateEvolutionAuraRestorePerStage;
      public final DoubleValue forceSubordinateEvolutionSpiritualRestoreBase;
      public final DoubleValue forceSubordinateEvolutionSpiritualRestorePerStage;
      public final IntValue forceSubordinateEvolutionReleaseChargeBase;
      public final IntValue forceSubordinateEvolutionOvergrowthRadiusBase;
      public final IntValue forceSubordinateEvolutionOvergrowthAttemptsBase;
      public final IntValue forceSubordinateEvolutionOvergrowthAttemptsPerStage;
      public final IntValue forceSubordinateEvolutionCooldownTicks;
      public final IntValue forceSubordinateAwakeningSoulCost;
      public final DoubleValue forceSubordinateAwakeningCost;
      public final DoubleValue forceSubordinateAwakeningHpMultiplier;
      public final DoubleValue forceSubordinateAwakeningDamageMultiplier;
      public final DoubleValue forceSubordinateAwakeningArmorBonus;
      public final DoubleValue forceSubordinateAwakeningSpeedMultiplier;
      public final DoubleValue forceSubordinateAwakeningScaleBonus;
      public final DoubleValue forceSubordinateAwakeningDegradationBonus;
      public final DoubleValue forceSubordinateAwakeningSpiritualRegenBonus;
      public final IntValue forceSubordinateAwakeningHakiDurationTicks;
      public final IntValue forceSubordinateAwakeningHakiAmplifier;
      public final IntValue forceSubordinateAwakeningRegenDurationTicks;
      public final IntValue forceSubordinateAwakeningRegenAmplifier;
      public final DoubleValue forceSubordinateAwakeningMagiculeRestore;
      public final DoubleValue forceSubordinateAwakeningAuraRestore;
      public final DoubleValue forceSubordinateAwakeningSpiritualRestore;
      public final IntValue forceSubordinateAwakeningReleaseCharge;
      public final IntValue forceSubordinateAwakeningOvergrowthRadius;
      public final IntValue forceSubordinateAwakeningOvergrowthAttempts;
      public final IntValue forceSubordinateAwakeningCooldownTicks;
      public final DoubleValue fruitOfLifeCost;
      public final IntValue fruitOfLifeOvergrowthRadius;
      public final IntValue fruitOfLifeOvergrowthAttempts;
      public final IntValue fruitOfLifeReleaseCharge;
      public final IntValue fruitOfLifeCooldownTicks;
      public final DoubleValue combatRecoveryMinHeal;
      public final DoubleValue combatRecoveryMissingHealthRatio;
      public final DoubleValue combatRecoveryLowHealthThreshold;
      public final DoubleValue combatRecoveryLowHealthHealMultiplier;
      public final IntValue combatRecoveryLowHealthHakiDurationTicks;
      public final IntValue combatRecoveryLowHealthHakiAmplifier;
      public final DoubleValue combatRecoveryMagiculeRestore;
      public final DoubleValue combatRecoveryAuraRestore;
      public final DoubleValue combatRecoverySpiritualRestore;
      public final DoubleValue combatPressureRadius;
      public final IntValue combatPressureOrganDecayDurationTicks;
      public final IntValue combatPressureOrganDecayAmplifier;
      public final IntValue combatSustainCostIntervalTicks;
      public final DoubleValue combatSustainCost;
      public final IntValue combatSustainRegenDurationTicks;
      public final IntValue combatSustainRegenAmplifier;
      public final IntValue combatSustainHealIntervalTicks;
      public final DoubleValue combatSustainMinHeal;
      public final DoubleValue combatSustainHealMaxHealthRatio;
      public final DoubleValue combatSustainMagiculeRestore;
      public final DoubleValue combatSustainAuraRestore;
      public final DoubleValue combatSustainSpiritualRestore;
      public final DoubleValue allyRegenPulseMagiculeRestoreBase;
      public final DoubleValue allyRegenPulseMagiculeRestorePerAmplifier;
      public final DoubleValue allyRegenPulseAuraRestoreBase;
      public final DoubleValue allyRegenPulseAuraRestorePerAmplifier;
      public final DoubleValue allyRegenPulseSpiritualRestoreBase;
      public final DoubleValue allyRegenPulseSpiritualRestorePerAmplifier;
      public final DoubleValue combatStealMagiculeRatio;
      public final DoubleValue combatStealAuraRatio;
      public final DoubleValue combatStealSpiritualRatio;
      public final DoubleValue knockAwayVerticalVelocity;
      public final DoubleValue pullHorizontalDamping;
      public final DoubleValue pullVerticalDamping;
      public final DoubleValue pullVerticalVelocity;
      public final DoubleValue combatChargeMaxRadius;
      public final DoubleValue combatChargeRadiusBase;
      public final DoubleValue combatChargeRadiusPerCharge;
      public final IntValue combatChargeSoundEveryCharge;
      public final DoubleValue combatReleaseMaxRadius;
      public final DoubleValue combatReleaseRadiusBase;
      public final DoubleValue combatReleaseRadiusPerCharge;
      public final IntValue drainLineParticleSteps;

      NaberiusConfig(Builder builder) {
         builder.comment("Naberius core mastery settings.");
         builder.push("mastery");
         this.maxMastery = builder.comment("Maximum mastery for Naberius.").defineInRange("maxMastery", 1000, 1, Integer.MAX_VALUE);
         this.passiveMasteryIntervalTicks = builder.comment("How often Naberius gains mastery while toggled. Default 100 = 5 seconds.")
            .defineInRange("passiveMasteryIntervalTicks", 100, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Magicule costs for Naberius modes.");
         builder.push("costs");
         this.manifestCost = builder.comment("Magicule cost for Manifest.").defineInRange("manifestCost", 100000.0, 0.0, Double.MAX_VALUE);
         this.chimeraCost = builder.comment("Magicule cost for Chimera menu/action.").defineInRange("chimeraCost", 300000.0, 0.0, Double.MAX_VALUE);
         this.domainCost = builder.comment("Displayed base cost for Domain mode. Actual release cost is controlled in domain settings.")
            .defineInRange("domainCost", 50000.0, 0.0, Double.MAX_VALUE);
         this.lifeDrainCost = builder.comment("Magicule cost for Life Drain and summon stealing.")
            .defineInRange("lifeDrainCost", 60000.0, 0.0, Double.MAX_VALUE);
         this.organFailureCost = builder.comment("Magicule cost for Organ Failure.").defineInRange("organFailureCost", 90000.0, 0.0, Double.MAX_VALUE);
         this.hinataFusionCost = builder.comment("Magicule cost for Hinata Fusion.").defineInRange("hinataFusionCost", 500000.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.comment("Permanent attributes granted by learning and mastering Naberius.");
         builder.push("attributes");
         this.learnKnockbackResistance = builder.comment("Flat knockback resistance granted on learn.")
            .defineInRange("learnKnockbackResistance", 0.25, -100.0, 100.0);
         this.learnMovementSpeed = builder.comment("Flat movement speed granted on learn.").defineInRange("learnMovementSpeed", 0.015, -100.0, 100.0);
         this.learnAttackDamage = builder.comment("Flat attack damage granted on learn.").defineInRange("learnAttackDamage", 20.0, -1000000.0, 1000000.0);
         this.learnAttackSpeed = builder.comment("Flat attack speed granted on learn.").defineInRange("learnAttackSpeed", 0.2, -100.0, 100.0);
         this.learnPresenceSense = builder.comment("Presence Sense granted on learn.").defineInRange("learnPresenceSense", 3.0, -1000.0, 1000.0);
         this.learnChantSpeed = builder.comment("Chant Speed granted on learn.").defineInRange("learnChantSpeed", 2.0, -1000.0, 1000.0);
         this.masteredResistanceDegradation = builder.comment("Resistance Degradation granted when mastered.")
            .defineInRange("masteredResistanceDegradation", 1.0, -1000.0, 1000.0);
         this.masteredProjectileDodgeChance = builder.comment(
               "Auto projectile dodge chance granted when mastered. 0.15 = 15 percent if the attribute treats 1.0 as 100 percent."
            )
            .defineInRange("masteredProjectileDodgeChance", 0.15, 0.0, 1000.0);
         builder.pop();
         builder.comment("Toggled passive effects and visuals.");
         builder.push("passive");
         this.passiveEffectDurationTicks = builder.comment("Duration of refreshed Naberius Will, Vision, and Presence Sense effects while toggled.")
            .defineInRange("passiveEffectDurationTicks", 100, 1, Integer.MAX_VALUE);
         this.passivePresenceSenseAmplifier = builder.comment("Presence Sense amplifier while toggled. 0 means level 1.")
            .defineInRange("passivePresenceSenseAmplifier", 1, 0, 255);
         this.auraParticleIntervalTicks = builder.comment(
               "How often aura particles spawn while toggled or in combat stance. Higher values are better for performance."
            )
            .defineInRange("auraParticleIntervalTicks", 8, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Garden of False Life domain settings.");
         builder.push("domain");
         this.domainBaseRadius = builder.comment("Base radius of Garden of False Life.").defineInRange("domainBaseRadius", 18, 1, 512);
         this.domainBaseDurationTicks = builder.comment("Base duration of Garden of False Life. Default 480 = 24 seconds.")
            .defineInRange("domainBaseDurationTicks", 480, 1, Integer.MAX_VALUE);
         this.domainDurationPerPowerTicks = builder.comment("Extra domain duration per charge power.")
            .defineInRange("domainDurationPerPowerTicks", 25, 0, Integer.MAX_VALUE);
         this.domainRadiusPowerDivisor = builder.comment("Charge power divisor for bonus radius. 2 means +1 radius per 2 charge.")
            .defineInRange("domainRadiusPowerDivisor", 2, 1, Integer.MAX_VALUE);
         this.domainHoldCheckIntervalTicks = builder.comment("How often holding Domain checks MP and plays hold visuals.")
            .defineInRange("domainHoldCheckIntervalTicks", 10, 1, Integer.MAX_VALUE);
         this.domainHoldBaseCost = builder.comment("MP required to keep holding Domain before release.")
            .defineInRange("domainHoldBaseCost", 750.0, 0.0, Double.MAX_VALUE);
         this.domainHoldCostPerSecond = builder.comment("Extra hold MP requirement per charge second.")
            .defineInRange("domainHoldCostPerSecond", 450.0, 0.0, Double.MAX_VALUE);
         this.domainReleaseBaseCost = builder.comment("Base MP spent when Domain is released.")
            .defineInRange("domainReleaseBaseCost", 50000.0, 0.0, Double.MAX_VALUE);
         this.domainReleaseCostPerSecond = builder.comment("Linear MP cost per charge second on release.")
            .defineInRange("domainReleaseCostPerSecond", 2500.0, 0.0, Double.MAX_VALUE);
         this.domainReleaseCostPerSecondSquared = builder.comment("Quadratic MP cost per charge second squared on release. High values punish huge charges.")
            .defineInRange("domainReleaseCostPerSecondSquared", 700.0, 0.0, Double.MAX_VALUE);
         this.domainCooldownTicks = builder.comment("Cooldown after Domain release.").defineInRange("domainCooldownTicks", 60, 0, Integer.MAX_VALUE);
         this.domainAuraIntervalTicks = builder.comment("How often Domain aura particles spawn.")
            .defineInRange("domainAuraIntervalTicks", 5, 1, Integer.MAX_VALUE);
         this.domainBuffIntervalTicks = builder.comment("How often Domain buffs summons and damages enemies. Default 20 = once per second.")
            .defineInRange("domainBuffIntervalTicks", 20, 1, Integer.MAX_VALUE);
         this.domainOrganDecayDurationTicks = builder.comment("Organ Decay duration applied by Domain pulses.")
            .defineInRange("domainOrganDecayDurationTicks", 60, 1, Integer.MAX_VALUE);
         this.domainOrganDecayBaseAmplifier = builder.comment("Base Organ Decay amplifier in Domain.")
            .defineInRange("domainOrganDecayBaseAmplifier", 1, 0, 255);
         this.domainOrganDecayPowerDivisor = builder.comment("Charge divisor for extra Organ Decay amplifier.")
            .defineInRange("domainOrganDecayPowerDivisor", 3, 1, Integer.MAX_VALUE);
         this.domainOrganDecayMaxAmplifier = builder.comment("Maximum Organ Decay amplifier from Domain.")
            .defineInRange("domainOrganDecayMaxAmplifier", 4, 0, 255);
         this.domainSpiritualFlatDrainPerPower = builder.comment("Flat spiritual health drained per charge power each Domain pulse.")
            .defineInRange("domainSpiritualFlatDrainPerPower", 250.0, 0.0, Double.MAX_VALUE);
         this.domainSpiritualPercentBase = builder.comment("Base percent spiritual health drained by Domain. 0.01 = 1 percent.")
            .defineInRange("domainSpiritualPercentBase", 0.01, 0.0, 1.0);
         this.domainSpiritualPercentPerPower = builder.comment("Extra spiritual health percent drained per charge power.")
            .defineInRange("domainSpiritualPercentPerPower", 0.002, 0.0, 1.0);
         builder.pop();
         builder.comment("Archive and active summon limits.");
         builder.push("summon_limits");
         this.maxStoredSummons = builder.comment("Maximum archive entries stored. Oldest entry is removed when exceeded.")
            .defineInRange("maxStoredSummons", 100, 1, 100000);
         this.maxActiveSummons = builder.comment("Maximum active Naberius summons at once.").defineInRange("maxActiveSummons", 30, 0, 10000);
         this.summonDefaultSeconds = builder.comment("Summoned lifetime stored into Tensura existence data. 3600 = 3 minutes if treated as seconds.")
            .defineInRange("summonDefaultSeconds", 3600, 0, Integer.MAX_VALUE);
         this.summonSpawnDistance = builder.comment("Distance in front of owner where summons appear.").defineInRange("summonSpawnDistance", 3.0, 0.0, 128.0);
         this.manifestCooldownTicks = builder.comment("Cooldown after Manifest successfully spawns servants.")
            .defineInRange("manifestCooldownTicks", 20, 0, Integer.MAX_VALUE);
         this.summonRetargetRange = builder.comment("Maximum distance from owner where saved aggro target is still commanded.")
            .defineInRange("summonRetargetRange", 96.0, 1.0, 1024.0);
         this.summonRetargetIntervalTicks = builder.comment("How often summons are retargeted while active.")
            .defineInRange("summonRetargetIntervalTicks", 20, 1, Integer.MAX_VALUE);
         this.summonCommandMoveSpeed = builder.comment("Navigation speed when commanding summons to attack.")
            .defineInRange("summonCommandMoveSpeed", 1.35, 0.0, 64.0);
         this.summonReviveMoveSpeed = builder.comment("Navigation speed after a revived summon receives aggro.")
            .defineInRange("summonReviveMoveSpeed", 1.35, 0.0, 64.0);
         builder.pop();
         builder.comment("Stats applied to normal archived summons and player clone archives.");
         builder.push("summon_stats");
         this.summonPlayerCloneHpBaseMultiplier = builder.comment("Player clone minimum max HP multiplier against its existing base HP.")
            .defineInRange("summonPlayerCloneHpBaseMultiplier", 1.25, 0.0, 100.0);
         this.summonPlayerCloneDamageBaseMultiplier = builder.comment("Player clone minimum damage multiplier against its existing base damage.")
            .defineInRange("summonPlayerCloneDamageBaseMultiplier", 1.35, 0.0, 100.0);
         this.summonDamageBase = builder.comment("Base attack damage floor added from archive EP.")
            .defineInRange("summonDamageBase", 20.0, 0.0, Float.MAX_VALUE);
         this.summonEpDamageMultiplier = builder.comment("Attack damage gained from average archived EP.")
            .defineInRange("summonEpDamageMultiplier", 0.0015, 0.0, 100.0);
         this.summonPlayerCloneMinimumDamage = builder.comment("Minimum base damage for player clone archives before EP scaling.")
            .defineInRange("summonPlayerCloneMinimumDamage", 35.0, 0.0, Float.MAX_VALUE);
         this.summonArmorBase = builder.comment("Armor floor for archived summons.").defineInRange("summonArmorBase", 20.0, 0.0, 1000000.0);
         this.summonEpArmorMultiplier = builder.comment("Armor gained from average archived EP.").defineInRange("summonEpArmorMultiplier", 2.5E-4, 0.0, 100.0);
         this.summonMovementSpeedMinimum = builder.comment("Minimum movement speed for archived summons.")
            .defineInRange("summonMovementSpeedMinimum", 0.31, 0.0, 10.0);
         this.summonFollowRangeMinimum = builder.comment("Minimum follow range for archived summons.")
            .defineInRange("summonFollowRangeMinimum", 48.0, 0.0, 2048.0);
         this.summonScaleMinimum = builder.comment("Minimum scale for archived summons.").defineInRange("summonScaleMinimum", 1.1, 0.0, 100.0);
         this.summonScaleBonus = builder.comment("Flat scale added to archived summons if their original scale is lower.")
            .defineInRange("summonScaleBonus", 0.15, 0.0, 100.0);
         this.summonResistanceDegradation = builder.comment("Resistance Degradation applied to archived summons.")
            .defineInRange("summonResistanceDegradation", 1.0, -1000.0, 1000.0);
         builder.pop();
         builder.comment("Automatic revive economy for active summons.");
         builder.push("revive");
         this.reviveBaseCost = builder.comment("Base MP cost to revive a dead active summon.").defineInRange("reviveBaseCost", 2500.0, 0.0, Double.MAX_VALUE);
         this.reviveExponentBase = builder.comment("Exponential multiplier base per revive count. 1.8 means each repeated revive gets much more expensive.")
            .defineInRange("reviveExponentBase", 1.8, 1.0, 100.0);
         this.reviveEpFactor = builder.comment("How much archived EP contributes to revive cost.").defineInRange("reviveEpFactor", 0.014, 0.0, 100.0);
         this.reviveHpFactor = builder.comment("How much archived HP contributes to revive cost.").defineInRange("reviveHpFactor", 0.4, 0.0, 100.0);
         builder.pop();
         builder.comment("Mastered lethal-save passive.");
         builder.push("lethal_save");
         this.lethalSaveCooldownTicks = builder.comment("Cooldown for Naberius death veto. Default 6000 = 5 minutes.")
            .defineInRange("lethalSaveCooldownTicks", 6000, 0, Integer.MAX_VALUE);
         this.lethalSaveInvulnerabilityTicks = builder.comment("Invulnerability and buff duration after death veto.")
            .defineInRange("lethalSaveInvulnerabilityTicks", 100, 1, Integer.MAX_VALUE);
         this.lethalSaveHealthRatio = builder.comment("Health restored after death veto. 0.25 = 25 percent max health.")
            .defineInRange("lethalSaveHealthRatio", 0.25, 0.0, 1.0);
         this.lethalSaveHakiCoatAmplifier = builder.comment("Haki Coat amplifier after death veto.").defineInRange("lethalSaveHakiCoatAmplifier", 3, 0, 255);
         this.lethalSaveRegenerationAmplifier = builder.comment("Instant Regeneration amplifier after death veto.")
            .defineInRange("lethalSaveRegenerationAmplifier", 2, 0, 255);
         builder.pop();
         builder.comment("Domain buffs applied to Naberius summons.");
         builder.push("domain_summon_buffs");
         this.domainSummonHpPerPower = builder.comment("Max HP multiplier added per Domain power.").defineInRange("domainSummonHpPerPower", 0.1, 0.0, 100.0);
         this.domainSummonHpCap = builder.comment("Cap for Domain HP multiplier.").defineInRange("domainSummonHpCap", 4.0, 0.0, 100.0);
         this.domainSummonDamagePerPower = builder.comment("Attack damage multiplier added per Domain power.")
            .defineInRange("domainSummonDamagePerPower", 0.08, 0.0, 100.0);
         this.domainSummonDamageCap = builder.comment("Cap for Domain attack damage multiplier.").defineInRange("domainSummonDamageCap", 3.0, 0.0, 100.0);
         this.domainSummonScalePerPower = builder.comment("Scale added per Domain power.").defineInRange("domainSummonScalePerPower", 0.035, 0.0, 100.0);
         this.domainSummonScaleCap = builder.comment("Cap for Domain scale bonus.").defineInRange("domainSummonScaleCap", 2.5, 0.0, 100.0);
         this.domainSummonHealPerPower = builder.comment("Health restored to summons per Domain power each buff pulse.")
            .defineInRange("domainSummonHealPerPower", 8.0, 0.0, Float.MAX_VALUE);
         this.domainSummonHakiDurationTicks = builder.comment("Haki Coat duration on summons during Domain.")
            .defineInRange("domainSummonHakiDurationTicks", 60, 1, Integer.MAX_VALUE);
         this.domainSummonHakiPowerDivisor = builder.comment("Domain power divisor for Haki Coat amplifier.")
            .defineInRange("domainSummonHakiPowerDivisor", 4, 1, Integer.MAX_VALUE);
         this.domainSummonHakiMaxAmplifier = builder.comment("Maximum Haki Coat amplifier from Domain.")
            .defineInRange("domainSummonHakiMaxAmplifier", 3, 0, 255);
         builder.pop();
         builder.comment("Life Drain values.");
         builder.push("life_drain");
         this.lifeDrainRange = builder.comment("Targeting range for Life Drain.").defineInRange("lifeDrainRange", 16.0, 1.0, 512.0);
         this.lifeDrainDamage = builder.comment("Magic damage dealt by Life Drain.").defineInRange("lifeDrainDamage", 120.0, 0.0, Float.MAX_VALUE);
         this.lifeDrainEffectTicks = builder.comment("Life Seizure duration from Life Drain.").defineInRange("lifeDrainEffectTicks", 100, 1, Integer.MAX_VALUE);
         this.lifeDrainEffectAmplifier = builder.comment("Life Seizure amplifier from Life Drain.").defineInRange("lifeDrainEffectAmplifier", 1, 0, 255);
         this.lifeDrainSummonHealRatio = builder.comment("Percent of max health restored to active summons. 0.12 = 12 percent.")
            .defineInRange("lifeDrainSummonHealRatio", 0.12, 0.0, 1.0);
         this.lifeDrainMinimumStolenMagicules = builder.comment("Minimum MP gained from Life Drain.")
            .defineInRange("lifeDrainMinimumStolenMagicules", 1500.0, 0.0, Double.MAX_VALUE);
         this.lifeDrainTargetEpStealRatio = builder.comment("MP gained from target max EP. 0.015 = 1.5 percent.")
            .defineInRange("lifeDrainTargetEpStealRatio", 0.015, 0.0, 1.0);
         this.lifeDrainCooldownTicks = builder.comment("Cooldown after Life Drain.").defineInRange("lifeDrainCooldownTicks", 20, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Foreign summon stealing values.");
         builder.push("steal_summons");
         this.stealSummonsRadius = builder.comment("Radius for stealing nearby foreign summons when shift-using Life Drain.")
            .defineInRange("stealSummonsRadius", 48.0, 1.0, 1024.0);
         this.stealSummonsCooldownTicks = builder.comment("Cooldown after successfully stealing summons.")
            .defineInRange("stealSummonsCooldownTicks", 60, 0, Integer.MAX_VALUE);
         this.stealSummonsSeconds = builder.comment("Summoned lifetime assigned to stolen summons.")
            .defineInRange("stealSummonsSeconds", 3600, 0, Integer.MAX_VALUE);
         this.stealSummonsNavigationSpeed = builder.comment("Navigation speed used by stolen summons when commanded.")
            .defineInRange("stealSummonsNavigationSpeed", 1.35, 0.0, 64.0);
         builder.pop();
         builder.comment("Organ Failure values.");
         builder.push("organ_failure");
         this.organFailureRange = builder.comment("Targeting range for Organ Failure.").defineInRange("organFailureRange", 18.0, 1.0, 512.0);
         this.organFailureDurationTicks = builder.comment("Duration of Organ Failure debuffs. Default 300 = 15 seconds.")
            .defineInRange("organFailureDurationTicks", 300, 1, Integer.MAX_VALUE);
         this.organFailureOrganDecayAmplifier = builder.comment("Organ Decay amplifier.").defineInRange("organFailureOrganDecayAmplifier", 2, 0, 255);
         this.organFailureBurdenAmplifier = builder.comment("Burden amplifier.").defineInRange("organFailureBurdenAmplifier", 1, 0, 255);
         this.organFailureFragilityAmplifier = builder.comment("Fragility amplifier.").defineInRange("organFailureFragilityAmplifier", 1, 0, 255);
         this.organFailureCooldownTicks = builder.comment("Cooldown after Organ Failure.").defineInRange("organFailureCooldownTicks", 30, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Hinata Fusion values.");
         builder.push("hinata_fusion");
         this.hinataFusionCooldownTicks = builder.comment("Cooldown after successful Hinata Fusion.")
            .defineInRange("hinataFusionCooldownTicks", 80, 0, Integer.MAX_VALUE);
         this.fusionSpawnDistance = builder.comment("Distance in front of owner where the fusion appears.")
            .defineInRange("fusionSpawnDistance", 4.0, 0.0, 128.0);
         this.fusionFallbackMinHealth = builder.comment("Minimum max health if Hinata entity is missing and zombie fallback is used.")
            .defineInRange("fusionFallbackMinHealth", 8000.0, 1.0, Float.MAX_VALUE);
         this.fusionHinataMinHealth = builder.comment("Minimum max health for real Hinata fusion.")
            .defineInRange("fusionHinataMinHealth", 4000.0, 1.0, Float.MAX_VALUE);
         this.fusionFallbackHpMultiplier = builder.comment("Archive HP multiplier for fallback god-corpse.")
            .defineInRange("fusionFallbackHpMultiplier", 0.9, 0.0, 100.0);
         this.fusionHinataHpMultiplier = builder.comment("Archive HP multiplier for Hinata fusion.").defineInRange("fusionHinataHpMultiplier", 1.2, 0.0, 100.0);
         this.fusionFallbackMinDamage = builder.comment("Minimum attack damage for fallback god-corpse.")
            .defineInRange("fusionFallbackMinDamage", 300.0, 0.0, Float.MAX_VALUE);
         this.fusionHinataMinDamage = builder.comment("Minimum attack damage for Hinata fusion.")
            .defineInRange("fusionHinataMinDamage", 400.0, 0.0, Float.MAX_VALUE);
         this.fusionDamageBase = builder.comment("Base attack damage before EP scaling.").defineInRange("fusionDamageBase", 100.0, 0.0, Float.MAX_VALUE);
         this.fusionFallbackEpDamageMultiplier = builder.comment("EP to attack damage multiplier for fallback god-corpse.")
            .defineInRange("fusionFallbackEpDamageMultiplier", 0.003, 0.0, 100.0);
         this.fusionHinataEpDamageMultiplier = builder.comment("EP to attack damage multiplier for Hinata fusion.")
            .defineInRange("fusionHinataEpDamageMultiplier", 0.0045, 0.0, 100.0);
         this.fusionArmorBase = builder.comment("Base armor floor for fusion.").defineInRange("fusionArmorBase", 60.0, 0.0, 1000000.0);
         this.fusionArmorPerSacrifice = builder.comment("Armor gained per archived sacrifice count.")
            .defineInRange("fusionArmorPerSacrifice", 2.5, 0.0, 1000000.0);
         this.fusionFallbackSpeed = builder.comment("Minimum movement speed for fallback god-corpse.").defineInRange("fusionFallbackSpeed", 0.36, 0.0, 10.0);
         this.fusionHinataSpeed = builder.comment("Minimum movement speed for Hinata fusion.").defineInRange("fusionHinataSpeed", 0.4, 0.0, 10.0);
         this.fusionFollowRange = builder.comment("Minimum follow range for fusion.").defineInRange("fusionFollowRange", 80.0, 0.0, 2048.0);
         this.fusionFallbackScale = builder.comment("Minimum scale for fallback god-corpse.").defineInRange("fusionFallbackScale", 1.45, 0.0, 100.0);
         this.fusionHinataScale = builder.comment("Minimum scale for Hinata fusion.").defineInRange("fusionHinataScale", 1.2, 0.0, 100.0);
         this.fusionResistanceDegradation = builder.comment("Resistance Degradation applied to fusion.")
            .defineInRange("fusionResistanceDegradation", 1.0, -1000.0, 1000.0);
         this.fusionPresenceSense = builder.comment("Presence Sense applied to fusion.").defineInRange("fusionPresenceSense", 5.0, -1000.0, 1000.0);
         this.fusionSummonedSeconds = builder.comment("Summoned lifetime assigned to fusion.")
            .defineInRange("fusionSummonedSeconds", 3600, 0, Integer.MAX_VALUE);
         this.fusionMinimumSpiritualHealth = builder.comment("Minimum spiritual health for fusion.")
            .defineInRange("fusionMinimumSpiritualHealth", 5000.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("Crimson Eden Stance");
         this.combatSwitchOvergrowthRadius = builder.comment("Radius of the plant/life overgrowth burst when entering Crimson Eden Warform.")
            .defineInRange("combatSwitchOvergrowthRadius", 9, 0, 64);
         this.combatSwitchOvergrowthAttempts = builder.comment(
               "How many block-growth attempts happen when entering Crimson Eden Warform. Higher values create more plants but cost more server time."
            )
            .defineInRange("combatSwitchOvergrowthAttempts", 140, 0, 2000);
         this.combatSwitchReleaseCharge = builder.comment("Visual charge size used for the stance-switch release burst.")
            .defineInRange("combatSwitchReleaseCharge", 4, 0, 64);
         this.combatAttributeRefreshTicks = builder.comment("How often combat stance reapplies its attribute modifiers.")
            .defineInRange("combatAttributeRefreshTicks", 100, 1, 72000);
         this.combatPassiveEffectDurationTicks = builder.comment("Duration of the passive Naberius Will/Vision effects while combat stance is active.")
            .defineInRange("combatPassiveEffectDurationTicks", 120, 1, 72000);
         this.combatPassiveEffectAmplifier = builder.comment("Amplifier for passive Naberius Will/Vision while combat stance is active.")
            .defineInRange("combatPassiveEffectAmplifier", 1, 0, 255);
         this.combatForceWarPulseIntervalTicks = builder.comment("How often Crimson Eden applies its regeneration pulse.")
            .defineInRange("combatForceWarPulseIntervalTicks", 5, 1, 72000);
         this.combatOvergrowthIntervalTicks = builder.comment("How often passive life overgrowth happens around the user.")
            .defineInRange("combatOvergrowthIntervalTicks", 20, 1, 72000);
         this.combatPassiveOvergrowthRadius = builder.comment("Passive overgrowth radius during combat stance.")
            .defineInRange("combatPassiveOvergrowthRadius", 8, 0, 64);
         this.combatPassiveOvergrowthAttempts = builder.comment("Passive overgrowth block attempts during combat stance.")
            .defineInRange("combatPassiveOvergrowthAttempts", 110, 0, 2000);
         this.combatRecoveryIntervalTicks = builder.comment("How often passive combat recovery heals and restores existence.")
            .defineInRange("combatRecoveryIntervalTicks", 40, 1, 72000);
         this.combatPressureIntervalTicks = builder.comment("How often nearby enemies are pressured with Organ Decay.")
            .defineInRange("combatPressureIntervalTicks", 60, 1, 72000);
         this.combatAuraIntervalTicks = builder.comment("How often Naberius aura particles spawn on the user in combat stance.")
            .defineInRange("combatAuraIntervalTicks", 8, 1, 72000);
         builder.pop();
         builder.push("Combat Attributes");
         this.combatMaxHealthMultiplier = builder.comment("Combat stance max health multiplier. 0.50 means +50% total max health.")
            .defineInRange("combatMaxHealthMultiplier", 0.5, -1.0, 100.0);
         this.combatAttackDamageMultiplier = builder.comment("Combat stance attack damage multiplier. 0.55 means +55% total attack damage.")
            .defineInRange("combatAttackDamageMultiplier", 0.55, -1.0, 100.0);
         this.combatArmorBonus = builder.comment("Flat armor bonus while combat stance is active.").defineInRange("combatArmorBonus", 35.0, -1024.0, 1000000.0);
         this.combatMovementSpeedMultiplier = builder.comment("Combat stance movement speed multiplier.")
            .defineInRange("combatMovementSpeedMultiplier", 0.12, -1.0, 100.0);
         this.combatAttackSpeedMultiplier = builder.comment("Combat stance attack speed multiplier.")
            .defineInRange("combatAttackSpeedMultiplier", 0.35, -1.0, 100.0);
         this.combatSpiritualHealthRegenBonus = builder.comment("Flat spiritual health regeneration bonus in combat stance.")
            .defineInRange("combatSpiritualHealthRegenBonus", 100.0, -1000000.0, 1000000.0);
         this.combatMagicCostMultiplierBonus = builder.comment(
               "Magic cost multiplier modifier in combat stance. Negative lowers costs. -0.15 means 15% cheaper."
            )
            .defineInRange("combatMagicCostMultiplierBonus", -0.15, -1.0, 100.0);
         this.combatResistanceDegradationBonus = builder.comment("Resistance degradation bonus in combat stance.")
            .defineInRange("combatResistanceDegradationBonus", 0.4, -100.0, 100.0);
         this.combatDodgeInvulnerabilityBonus = builder.comment("Dodge invulnerability bonus in combat stance.")
            .defineInRange("combatDodgeInvulnerabilityBonus", 0.25, -100.0, 100.0);
         this.combatPresenceSenseBonus = builder.comment("Presence Sense bonus in combat stance.")
            .defineInRange("combatPresenceSenseBonus", 4.0, -1000.0, 1000.0);
         this.combatDodgeNegateChanceBonus = builder.comment("Dodge negate chance bonus in combat stance.")
            .defineInRange("combatDodgeNegateChanceBonus", 12.0, -1000.0, 1000.0);
         builder.pop();
         builder.push("Eden Domain Hold");
         this.combatDomainHoldCheckIntervalTicks = builder.comment("How often holding Eden checks cost and spawns charge effects.")
            .defineInRange("combatDomainHoldCheckIntervalTicks", 10, 1, 72000);
         this.combatDomainHoldBaseCost = builder.comment("Base MP check while holding Eden. This is only an affordability check, not spent.")
            .defineInRange("combatDomainHoldBaseCost", 900.0, 0.0, Double.MAX_VALUE);
         this.combatDomainHoldCostPerSecond = builder.comment("Extra MP affordability check per held second while charging Eden.")
            .defineInRange("combatDomainHoldCostPerSecond", 600.0, 0.0, Double.MAX_VALUE);
         this.combatDomainHoldPulseIntervalTicks = builder.comment("How often Eden hold applies healing, restoration, and ally pulses.")
            .defineInRange("combatDomainHoldPulseIntervalTicks", 20, 1, 72000);
         this.combatDomainHoldMinHeal = builder.comment("Minimum self heal during Eden hold pulse.")
            .defineInRange("combatDomainHoldMinHeal", 4.0, 0.0, 1000000.0);
         this.combatDomainHoldHealMaxHealthRatio = builder.comment("Self heal ratio of max health during Eden hold pulse.")
            .defineInRange("combatDomainHoldHealMaxHealthRatio", 0.015, 0.0, 100.0);
         this.combatDomainHoldMagiculeRestorePerCharge = builder.comment("Magicule restored per charge level during Eden hold pulse.")
            .defineInRange("combatDomainHoldMagiculeRestorePerCharge", 1000.0, 0.0, Double.MAX_VALUE);
         this.combatDomainHoldAuraRestorePerCharge = builder.comment("Aura restored per charge level during Eden hold pulse.")
            .defineInRange("combatDomainHoldAuraRestorePerCharge", 1000.0, 0.0, Double.MAX_VALUE);
         this.combatDomainHoldSpiritualRestorePerCharge = builder.comment("Spiritual health restored per charge level during Eden hold pulse.")
            .defineInRange("combatDomainHoldSpiritualRestorePerCharge", 600.0, 0.0, Double.MAX_VALUE);
         this.combatDomainHoldOvergrowthMaxRadius = builder.comment("Maximum overgrowth radius while holding Eden.")
            .defineInRange("combatDomainHoldOvergrowthMaxRadius", 10, 0, 128);
         this.combatDomainHoldOvergrowthBaseRadius = builder.comment("Base overgrowth radius while holding Eden.")
            .defineInRange("combatDomainHoldOvergrowthBaseRadius", 4, 0, 128);
         this.combatDomainHoldOvergrowthChargeDivisor = builder.comment("Charge divisor for overgrowth radius. Lower means radius grows faster.")
            .defineInRange("combatDomainHoldOvergrowthChargeDivisor", 2, 1, 1000);
         this.combatDomainHoldOvergrowthBaseAttempts = builder.comment("Base overgrowth attempts while holding Eden.")
            .defineInRange("combatDomainHoldOvergrowthBaseAttempts", 35, 0, 2000);
         this.combatDomainHoldOvergrowthAttemptsPerCharge = builder.comment("Extra overgrowth attempts per charge while holding Eden.")
            .defineInRange("combatDomainHoldOvergrowthAttemptsPerCharge", 4, 0, 1000);
         this.combatDomainHoldAllyRadiusBase = builder.comment("Base ally regeneration pulse radius while holding Eden.")
            .defineInRange("combatDomainHoldAllyRadiusBase", 5.0, 0.0, 256.0);
         this.combatDomainHoldAllyRadiusPerCharge = builder.comment("Extra ally regeneration radius per charge while holding Eden.")
            .defineInRange("combatDomainHoldAllyRadiusPerCharge", 0.4, 0.0, 256.0);
         this.combatDomainHoldAllyDurationBase = builder.comment("Base ally effect duration while holding Eden.")
            .defineInRange("combatDomainHoldAllyDurationBase", 100, 1, 72000);
         this.combatDomainHoldAllyDurationPerCharge = builder.comment("Extra ally effect duration per charge while holding Eden.")
            .defineInRange("combatDomainHoldAllyDurationPerCharge", 4, 0, 72000);
         this.combatDomainHoldAllyMaxAmplifier = builder.comment("Maximum ally regeneration amplifier while holding Eden.")
            .defineInRange("combatDomainHoldAllyMaxAmplifier", 3, 0, 255);
         this.combatDomainHoldAllyAmplifierDivisor = builder.comment(
               "Charge divisor for ally amplifier while holding Eden. Lower means amplifier grows faster."
            )
            .defineInRange("combatDomainHoldAllyAmplifierDivisor", 4, 1, 1000);
         builder.pop();
         builder.push("Eden Domain Release");
         this.combatDomainMinimumHeldTicks = builder.comment("Minimum held ticks before Eden can release.")
            .defineInRange("combatDomainMinimumHeldTicks", 10, 0, 72000);
         this.combatDomainReleaseBaseCost = builder.comment("Base MP spent when releasing Eden.")
            .defineInRange("combatDomainReleaseBaseCost", 85000.0, 0.0, Double.MAX_VALUE);
         this.combatDomainReleaseCostPerSecond = builder.comment("Linear MP cost per charge second when releasing Eden.")
            .defineInRange("combatDomainReleaseCostPerSecond", 4500.0, 0.0, Double.MAX_VALUE);
         this.combatDomainReleaseCostPerSecondSquared = builder.comment("Quadratic MP cost per charge squared when releasing Eden.")
            .defineInRange("combatDomainReleaseCostPerSecondSquared", 550.0, 0.0, Double.MAX_VALUE);
         this.combatDomainRadiusBase = builder.comment("Base radius of Eden release.").defineInRange("combatDomainRadiusBase", 12.0, 0.0, 256.0);
         this.combatDomainRadiusPerCharge = builder.comment("Extra radius per charge on Eden release.")
            .defineInRange("combatDomainRadiusPerCharge", 1.8, 0.0, 256.0);
         this.combatDomainAllyRegenDurationBase = builder.comment("Base regeneration duration for allies hit by Eden release.")
            .defineInRange("combatDomainAllyRegenDurationBase", 200, 1, 72000);
         this.combatDomainAllyRegenDurationPerCharge = builder.comment("Extra ally regeneration duration per charge.")
            .defineInRange("combatDomainAllyRegenDurationPerCharge", 12, 0, 72000);
         this.combatDomainAllyRegenMaxAmplifier = builder.comment("Maximum ally regeneration amplifier from Eden release.")
            .defineInRange("combatDomainAllyRegenMaxAmplifier", 4, 0, 255);
         this.combatDomainAllyRegenBaseAmplifier = builder.comment("Base ally regeneration amplifier from Eden release.")
            .defineInRange("combatDomainAllyRegenBaseAmplifier", 1, 0, 255);
         this.combatDomainAllyRegenAmplifierDivisor = builder.comment("Charge divisor for ally regeneration amplifier.")
            .defineInRange("combatDomainAllyRegenAmplifierDivisor", 3, 1, 1000);
         this.combatDomainAllyHakiDurationBase = builder.comment("Base Haki Coat duration for allies hit by Eden release.")
            .defineInRange("combatDomainAllyHakiDurationBase", 180, 1, 72000);
         this.combatDomainAllyHakiDurationPerCharge = builder.comment("Extra Haki Coat duration per charge for allies.")
            .defineInRange("combatDomainAllyHakiDurationPerCharge", 8, 0, 72000);
         this.combatDomainAllyHakiMaxAmplifier = builder.comment("Maximum Haki Coat amplifier for allies from Eden release.")
            .defineInRange("combatDomainAllyHakiMaxAmplifier", 3, 0, 255);
         this.combatDomainAllyHakiAmplifierDivisor = builder.comment("Charge divisor for ally Haki Coat amplifier.")
            .defineInRange("combatDomainAllyHakiAmplifierDivisor", 4, 1, 1000);
         this.combatDomainAllyMagiculeRestoreBase = builder.comment("Base magicule restored to allies by Eden release.")
            .defineInRange("combatDomainAllyMagiculeRestoreBase", 3000.0, 0.0, Double.MAX_VALUE);
         this.combatDomainAllyMagiculeRestorePerCharge = builder.comment("Extra magicule restored to allies per charge.")
            .defineInRange("combatDomainAllyMagiculeRestorePerCharge", 700.0, 0.0, Double.MAX_VALUE);
         this.combatDomainAllyAuraRestoreBase = builder.comment("Base aura restored to allies by Eden release.")
            .defineInRange("combatDomainAllyAuraRestoreBase", 3000.0, 0.0, Double.MAX_VALUE);
         this.combatDomainAllyAuraRestorePerCharge = builder.comment("Extra aura restored to allies per charge.")
            .defineInRange("combatDomainAllyAuraRestorePerCharge", 700.0, 0.0, Double.MAX_VALUE);
         this.combatDomainAllySpiritualRestoreBase = builder.comment("Base spiritual health restored to allies by Eden release.")
            .defineInRange("combatDomainAllySpiritualRestoreBase", 1800.0, 0.0, Double.MAX_VALUE);
         this.combatDomainAllySpiritualRestorePerCharge = builder.comment("Extra spiritual health restored to allies per charge.")
            .defineInRange("combatDomainAllySpiritualRestorePerCharge", 450.0, 0.0, Double.MAX_VALUE);
         this.combatDomainEnemyDamageBase = builder.comment("Base magic damage dealt to enemies by Eden release.")
            .defineInRange("combatDomainEnemyDamageBase", 95.0, 0.0, 1000000.0);
         this.combatDomainEnemyDamagePerCharge = builder.comment("Extra magic damage per charge dealt to enemies by Eden release.")
            .defineInRange("combatDomainEnemyDamagePerCharge", 24.0, 0.0, 1000000.0);
         this.combatDomainEnemyEffectDurationBase = builder.comment("Base duration for enemy debuffs from Eden release.")
            .defineInRange("combatDomainEnemyEffectDurationBase", 160, 1, 72000);
         this.combatDomainEnemyEffectDurationPerCharge = builder.comment("Extra enemy debuff duration per charge from Eden release.")
            .defineInRange("combatDomainEnemyEffectDurationPerCharge", 12, 0, 72000);
         this.combatDomainEnemyOrganDecayMaxAmplifier = builder.comment("Maximum Organ Decay amplifier on enemies from Eden release.")
            .defineInRange("combatDomainEnemyOrganDecayMaxAmplifier", 5, 0, 255);
         this.combatDomainEnemyOrganDecayBaseAmplifier = builder.comment("Base Organ Decay amplifier on enemies from Eden release.")
            .defineInRange("combatDomainEnemyOrganDecayBaseAmplifier", 2, 0, 255);
         this.combatDomainEnemyOrganDecayAmplifierDivisor = builder.comment("Charge divisor for enemy Organ Decay amplifier.")
            .defineInRange("combatDomainEnemyOrganDecayAmplifierDivisor", 3, 1, 1000);
         this.combatDomainEnemyLifeSeizureMaxAmplifier = builder.comment("Maximum Life Seizure amplifier on enemies from Eden release.")
            .defineInRange("combatDomainEnemyLifeSeizureMaxAmplifier", 4, 0, 255);
         this.combatDomainEnemyLifeSeizureBaseAmplifier = builder.comment("Base Life Seizure amplifier on enemies from Eden release.")
            .defineInRange("combatDomainEnemyLifeSeizureBaseAmplifier", 1, 0, 255);
         this.combatDomainEnemyLifeSeizureAmplifierDivisor = builder.comment("Charge divisor for enemy Life Seizure amplifier.")
            .defineInRange("combatDomainEnemyLifeSeizureAmplifierDivisor", 4, 1, 1000);
         this.combatDomainEnemyMagiculeStealBase = builder.comment("Base magicule steal minimum from enemies hit by Eden release.")
            .defineInRange("combatDomainEnemyMagiculeStealBase", 3500.0, 0.0, Double.MAX_VALUE);
         this.combatDomainEnemyMagiculeStealPerCharge = builder.comment("Extra minimum magicule steal per charge from enemies hit by Eden release.")
            .defineInRange("combatDomainEnemyMagiculeStealPerCharge", 400.0, 0.0, Double.MAX_VALUE);
         this.combatDomainEnemyAuraStealBase = builder.comment("Base aura steal minimum from enemies hit by Eden release.")
            .defineInRange("combatDomainEnemyAuraStealBase", 3500.0, 0.0, Double.MAX_VALUE);
         this.combatDomainEnemyAuraStealPerCharge = builder.comment("Extra minimum aura steal per charge from enemies hit by Eden release.")
            .defineInRange("combatDomainEnemyAuraStealPerCharge", 400.0, 0.0, Double.MAX_VALUE);
         this.combatDomainEnemySpiritualStealBase = builder.comment("Base spiritual health steal minimum from enemies hit by Eden release.")
            .defineInRange("combatDomainEnemySpiritualStealBase", 1800.0, 0.0, Double.MAX_VALUE);
         this.combatDomainEnemySpiritualStealPerCharge = builder.comment("Extra minimum spiritual health steal per charge from enemies hit by Eden release.")
            .defineInRange("combatDomainEnemySpiritualStealPerCharge", 250.0, 0.0, Double.MAX_VALUE);
         this.combatDomainPullBase = builder.comment("Base pull strength applied to enemies hit by Eden release.")
            .defineInRange("combatDomainPullBase", 0.25, 0.0, 100.0);
         this.combatDomainPullPerCharge = builder.comment("Extra pull strength per charge applied to enemies hit by Eden release.")
            .defineInRange("combatDomainPullPerCharge", 0.018, 0.0, 100.0);
         this.combatDomainSelfHealMaxHealthRatio = builder.comment("Self heal ratio after Eden release.")
            .defineInRange("combatDomainSelfHealMaxHealthRatio", 0.3, 0.0, 100.0);
         this.combatDomainSelfHealPerHit = builder.comment("Flat self heal per enemy hit by Eden release.")
            .defineInRange("combatDomainSelfHealPerHit", 14.0, 0.0, 1000000.0);
         this.combatDomainSelfHealPerBlessed = builder.comment("Flat self heal per ally blessed by Eden release.")
            .defineInRange("combatDomainSelfHealPerBlessed", 8.0, 0.0, 1000000.0);
         this.combatDomainSelfMagiculeRestoreBase = builder.comment("Base self magicule restore after Eden release.")
            .defineInRange("combatDomainSelfMagiculeRestoreBase", 8000.0, 0.0, Double.MAX_VALUE);
         this.combatDomainSelfMagiculeRestorePerHit = builder.comment("Self magicule restore per enemy hit by Eden release.")
            .defineInRange("combatDomainSelfMagiculeRestorePerHit", 2500.0, 0.0, Double.MAX_VALUE);
         this.combatDomainSelfAuraRestoreBase = builder.comment("Base self aura restore after Eden release.")
            .defineInRange("combatDomainSelfAuraRestoreBase", 8000.0, 0.0, Double.MAX_VALUE);
         this.combatDomainSelfAuraRestorePerHit = builder.comment("Self aura restore per enemy hit by Eden release.")
            .defineInRange("combatDomainSelfAuraRestorePerHit", 2500.0, 0.0, Double.MAX_VALUE);
         this.combatDomainSelfSpiritualRestoreBase = builder.comment("Base self spiritual health restore after Eden release.")
            .defineInRange("combatDomainSelfSpiritualRestoreBase", 4500.0, 0.0, Double.MAX_VALUE);
         this.combatDomainSelfSpiritualRestorePerHit = builder.comment("Self spiritual health restore per enemy hit by Eden release.")
            .defineInRange("combatDomainSelfSpiritualRestorePerHit", 1400.0, 0.0, Double.MAX_VALUE);
         this.combatDomainReleaseOvergrowthRadiusBase = builder.comment("Base overgrowth radius on Eden release. Final radius is this plus charge.")
            .defineInRange("combatDomainReleaseOvergrowthRadiusBase", 9, 0, 128);
         this.combatDomainReleaseOvergrowthAttemptsBase = builder.comment("Base overgrowth attempts on Eden release.")
            .defineInRange("combatDomainReleaseOvergrowthAttemptsBase", 140, 0, 4000);
         this.combatDomainReleaseOvergrowthAttemptsPerCharge = builder.comment("Extra overgrowth attempts per charge on Eden release.")
            .defineInRange("combatDomainReleaseOvergrowthAttemptsPerCharge", 12, 0, 1000);
         this.combatDomainReleaseVisualChargeBonus = builder.comment("Extra visual charge added to Eden release burst.")
            .defineInRange("combatDomainReleaseVisualChargeBonus", 5, 0, 128);
         this.combatDomainCooldownTicks = builder.comment("Cooldown after releasing Eden of the Life Deity.")
            .defineInRange("combatDomainCooldownTicks", 100, 0, 72000);
         builder.pop();
         builder.push("Genesis Pulse");
         this.genesisPulseCost = builder.comment("MP cost of Genesis Pulse.").defineInRange("genesisPulseCost", 35000.0, 0.0, Double.MAX_VALUE);
         this.genesisPulseMinHeal = builder.comment("Minimum self heal from Genesis Pulse.").defineInRange("genesisPulseMinHeal", 28.0, 0.0, 1000000.0);
         this.genesisPulseHealMaxHealthRatio = builder.comment("Self heal ratio of max health from Genesis Pulse.")
            .defineInRange("genesisPulseHealMaxHealthRatio", 0.22, 0.0, 100.0);
         this.genesisPulseMagiculeRestore = builder.comment("Magicule restored by Genesis Pulse.")
            .defineInRange("genesisPulseMagiculeRestore", 9000.0, 0.0, Double.MAX_VALUE);
         this.genesisPulseAuraRestore = builder.comment("Aura restored by Genesis Pulse.")
            .defineInRange("genesisPulseAuraRestore", 9000.0, 0.0, Double.MAX_VALUE);
         this.genesisPulseSpiritualRestore = builder.comment("Spiritual health restored by Genesis Pulse.")
            .defineInRange("genesisPulseSpiritualRestore", 6000.0, 0.0, Double.MAX_VALUE);
         this.genesisPulseAllyRadius = builder.comment("Ally restoration radius for Genesis Pulse.").defineInRange("genesisPulseAllyRadius", 10.0, 0.0, 256.0);
         this.genesisPulseAllyRegenDurationTicks = builder.comment("Ally regeneration duration from Genesis Pulse.")
            .defineInRange("genesisPulseAllyRegenDurationTicks", 160, 1, 72000);
         this.genesisPulseAllyRegenAmplifier = builder.comment("Ally regeneration amplifier from Genesis Pulse.")
            .defineInRange("genesisPulseAllyRegenAmplifier", 2, 0, 255);
         this.genesisPulseHakiDurationTicks = builder.comment("Self Haki Coat duration from Genesis Pulse.")
            .defineInRange("genesisPulseHakiDurationTicks", 160, 1, 72000);
         this.genesisPulseHakiAmplifier = builder.comment("Self Haki Coat amplifier from Genesis Pulse.").defineInRange("genesisPulseHakiAmplifier", 2, 0, 255);
         this.genesisPulseSelfRegenDurationTicks = builder.comment("Self regeneration duration from Genesis Pulse.")
            .defineInRange("genesisPulseSelfRegenDurationTicks", 160, 1, 72000);
         this.genesisPulseSelfRegenAmplifier = builder.comment("Self regeneration amplifier from Genesis Pulse.")
            .defineInRange("genesisPulseSelfRegenAmplifier", 2, 0, 255);
         this.genesisPulseOvergrowthRadius = builder.comment("Overgrowth radius from Genesis Pulse.").defineInRange("genesisPulseOvergrowthRadius", 7, 0, 128);
         this.genesisPulseOvergrowthAttempts = builder.comment("Overgrowth attempts from Genesis Pulse.")
            .defineInRange("genesisPulseOvergrowthAttempts", 100, 0, 4000);
         this.genesisPulseBurstRadius = builder.comment("Visual burst radius for Genesis Pulse.").defineInRange("genesisPulseBurstRadius", 3.0, 0.0, 128.0);
         this.genesisPulseCooldownTicks = builder.comment("Cooldown for Genesis Pulse.").defineInRange("genesisPulseCooldownTicks", 35, 0, 72000);
         builder.pop();
         builder.push("Spirit War Step");
         this.spiritWarStepCost = builder.comment("MP cost of Spirit War Step.").defineInRange("spiritWarStepCost", 45000.0, 0.0, Double.MAX_VALUE);
         this.spiritWarStepForwardVelocity = builder.comment("Forward launch velocity of Spirit War Step.")
            .defineInRange("spiritWarStepForwardVelocity", 2.8, 0.0, 100.0);
         this.spiritWarStepUpVelocity = builder.comment("Upward launch velocity of Spirit War Step.")
            .defineInRange("spiritWarStepUpVelocity", 0.45, -100.0, 100.0);
         this.spiritWarStepHitboxForwardOffset = builder.comment("Forward offset of Spirit War Step hitbox.")
            .defineInRange("spiritWarStepHitboxForwardOffset", 4.0, 0.0, 256.0);
         this.spiritWarStepHitboxHorizontalInflate = builder.comment("Horizontal inflate radius of Spirit War Step hitbox.")
            .defineInRange("spiritWarStepHitboxHorizontalInflate", 5.0, 0.0, 256.0);
         this.spiritWarStepHitboxVerticalInflate = builder.comment("Vertical inflate radius of Spirit War Step hitbox.")
            .defineInRange("spiritWarStepHitboxVerticalInflate", 2.5, 0.0, 256.0);
         this.spiritWarStepDamage = builder.comment("Magic damage dealt by Spirit War Step.").defineInRange("spiritWarStepDamage", 115.0, 0.0, 1000000.0);
         this.spiritWarStepLifeSeizureDurationTicks = builder.comment("Life Seizure duration from Spirit War Step.")
            .defineInRange("spiritWarStepLifeSeizureDurationTicks", 120, 1, 72000);
         this.spiritWarStepLifeSeizureAmplifier = builder.comment("Life Seizure amplifier from Spirit War Step.")
            .defineInRange("spiritWarStepLifeSeizureAmplifier", 2, 0, 255);
         this.spiritWarStepMagiculeSteal = builder.comment("Minimum magicule steal per Spirit War Step hit.")
            .defineInRange("spiritWarStepMagiculeSteal", 2500.0, 0.0, Double.MAX_VALUE);
         this.spiritWarStepAuraSteal = builder.comment("Minimum aura steal per Spirit War Step hit.")
            .defineInRange("spiritWarStepAuraSteal", 2500.0, 0.0, Double.MAX_VALUE);
         this.spiritWarStepSpiritualSteal = builder.comment("Minimum spiritual health steal per Spirit War Step hit.")
            .defineInRange("spiritWarStepSpiritualSteal", 1300.0, 0.0, Double.MAX_VALUE);
         this.spiritWarStepKnockbackStrength = builder.comment("Knockback strength applied by Spirit War Step.")
            .defineInRange("spiritWarStepKnockbackStrength", 1.35, 0.0, 100.0);
         this.spiritWarStepMinHealOnHit = builder.comment("Minimum self heal if Spirit War Step hits anything.")
            .defineInRange("spiritWarStepMinHealOnHit", 12.0, 0.0, 1000000.0);
         this.spiritWarStepHealPerHit = builder.comment("Self heal per Spirit War Step hit.").defineInRange("spiritWarStepHealPerHit", 14.0, 0.0, 1000000.0);
         this.spiritWarStepMagiculeRestorePerHit = builder.comment("Magicule restored per Spirit War Step hit.")
            .defineInRange("spiritWarStepMagiculeRestorePerHit", 2200.0, 0.0, Double.MAX_VALUE);
         this.spiritWarStepAuraRestorePerHit = builder.comment("Aura restored per Spirit War Step hit.")
            .defineInRange("spiritWarStepAuraRestorePerHit", 2200.0, 0.0, Double.MAX_VALUE);
         this.spiritWarStepSpiritualRestorePerHit = builder.comment("Spiritual health restored per Spirit War Step hit.")
            .defineInRange("spiritWarStepSpiritualRestorePerHit", 1200.0, 0.0, Double.MAX_VALUE);
         this.spiritWarStepBurstRadius = builder.comment("Visual burst radius of Spirit War Step.").defineInRange("spiritWarStepBurstRadius", 2.5, 0.0, 128.0);
         this.spiritWarStepCooldownTicks = builder.comment("Cooldown of Spirit War Step.").defineInRange("spiritWarStepCooldownTicks", 25, 0, 72000);
         builder.pop();
         builder.push("Samsara Bloom");
         this.samsaraBloomCost = builder.comment("MP cost of Samsara Bloom.").defineInRange("samsaraBloomCost", 80000.0, 0.0, Double.MAX_VALUE);
         this.samsaraBloomRadius = builder.comment("Enemy hit radius of Samsara Bloom.").defineInRange("samsaraBloomRadius", 8.0, 0.0, 256.0);
         this.samsaraBloomDamageBase = builder.comment("Base magic damage from Samsara Bloom.").defineInRange("samsaraBloomDamageBase", 90.0, 0.0, 1000000.0);
         this.samsaraBloomMissingHealthDamageRatio = builder.comment("Extra Samsara Bloom damage from enemy missing health.")
            .defineInRange("samsaraBloomMissingHealthDamageRatio", 0.18, 0.0, 100.0);
         this.samsaraBloomEffectDurationTicks = builder.comment("Debuff duration from Samsara Bloom.")
            .defineInRange("samsaraBloomEffectDurationTicks", 180, 1, 72000);
         this.samsaraBloomOrganDecayAmplifier = builder.comment("Organ Decay amplifier from Samsara Bloom.")
            .defineInRange("samsaraBloomOrganDecayAmplifier", 3, 0, 255);
         this.samsaraBloomLifeSeizureAmplifier = builder.comment("Life Seizure amplifier from Samsara Bloom.")
            .defineInRange("samsaraBloomLifeSeizureAmplifier", 2, 0, 255);
         this.samsaraBloomMagiculeSteal = builder.comment("Minimum magicule steal per Samsara Bloom hit.")
            .defineInRange("samsaraBloomMagiculeSteal", 3500.0, 0.0, Double.MAX_VALUE);
         this.samsaraBloomAuraSteal = builder.comment("Minimum aura steal per Samsara Bloom hit.")
            .defineInRange("samsaraBloomAuraSteal", 3500.0, 0.0, Double.MAX_VALUE);
         this.samsaraBloomSpiritualSteal = builder.comment("Minimum spiritual health steal per Samsara Bloom hit.")
            .defineInRange("samsaraBloomSpiritualSteal", 2000.0, 0.0, Double.MAX_VALUE);
         this.samsaraBloomPullStrength = builder.comment("Pull strength from Samsara Bloom.").defineInRange("samsaraBloomPullStrength", 0.35, 0.0, 100.0);
         this.samsaraBloomRootAttemptsPerTarget = builder.comment("How many sapling/root placement attempts occur per grounded target hit.")
            .defineInRange("samsaraBloomRootAttemptsPerTarget", 5, 0, 256);
         this.samsaraBloomMinHeal = builder.comment("Minimum self heal from Samsara Bloom.").defineInRange("samsaraBloomMinHeal", 20.0, 0.0, 1000000.0);
         this.samsaraBloomHealPerHit = builder.comment("Self heal per Samsara Bloom hit.").defineInRange("samsaraBloomHealPerHit", 18.0, 0.0, 1000000.0);
         this.samsaraBloomMagiculeRestorePerHit = builder.comment("Magicule restored per Samsara Bloom hit.")
            .defineInRange("samsaraBloomMagiculeRestorePerHit", 3000.0, 0.0, Double.MAX_VALUE);
         this.samsaraBloomAuraRestorePerHit = builder.comment("Aura restored per Samsara Bloom hit.")
            .defineInRange("samsaraBloomAuraRestorePerHit", 3000.0, 0.0, Double.MAX_VALUE);
         this.samsaraBloomSpiritualRestorePerHit = builder.comment("Spiritual health restored per Samsara Bloom hit.")
            .defineInRange("samsaraBloomSpiritualRestorePerHit", 1800.0, 0.0, Double.MAX_VALUE);
         this.samsaraBloomOvergrowthRadius = builder.comment("Overgrowth radius from Samsara Bloom.").defineInRange("samsaraBloomOvergrowthRadius", 8, 0, 128);
         this.samsaraBloomOvergrowthAttempts = builder.comment("Overgrowth attempts from Samsara Bloom.")
            .defineInRange("samsaraBloomOvergrowthAttempts", 110, 0, 4000);
         this.samsaraBloomReleaseCharge = builder.comment("Visual release charge for Samsara Bloom.").defineInRange("samsaraBloomReleaseCharge", 5, 0, 128);
         this.samsaraBloomCooldownTicks = builder.comment("Cooldown of Samsara Bloom.").defineInRange("samsaraBloomCooldownTicks", 45, 0, 72000);
         builder.pop();
         builder.push("Deathless Life Siphon");
         this.deathlessLifeSiphonRange = builder.comment("Targeting range of Deathless Life Siphon.")
            .defineInRange("deathlessLifeSiphonRange", 24.0, 0.0, 256.0);
         this.deathlessLifeSiphonCost = builder.comment("MP cost of Deathless Life Siphon.")
            .defineInRange("deathlessLifeSiphonCost", 95000.0, 0.0, Double.MAX_VALUE);
         this.deathlessLifeSiphonBypassTicks = builder.comment("How long the target is marked for life-bypass damage.")
            .defineInRange("deathlessLifeSiphonBypassTicks", 100, 0, 72000);
         this.deathlessLifeSiphonMaxHealthDamageCapRatio = builder.comment("Damage cap as a ratio of target max health.")
            .defineInRange("deathlessLifeSiphonMaxHealthDamageCapRatio", 0.45, 0.0, 100.0);
         this.deathlessLifeSiphonDamageBase = builder.comment("Base damage before max health scaling.")
            .defineInRange("deathlessLifeSiphonDamageBase", 180.0, 0.0, 1000000.0);
         this.deathlessLifeSiphonDamageMaxHealthRatio = builder.comment("Added damage as a ratio of target max health.")
            .defineInRange("deathlessLifeSiphonDamageMaxHealthRatio", 0.1, 0.0, 100.0);
         this.deathlessLifeSiphonEffectDurationTicks = builder.comment("Debuff duration from Deathless Life Siphon.")
            .defineInRange("deathlessLifeSiphonEffectDurationTicks", 220, 1, 72000);
         this.deathlessLifeSiphonLifeSeizureAmplifier = builder.comment("Life Seizure amplifier from Deathless Life Siphon.")
            .defineInRange("deathlessLifeSiphonLifeSeizureAmplifier", 4, 0, 255);
         this.deathlessLifeSiphonOrganDecayAmplifier = builder.comment("Organ Decay amplifier from Deathless Life Siphon.")
            .defineInRange("deathlessLifeSiphonOrganDecayAmplifier", 3, 0, 255);
         this.deathlessLifeSiphonMagiculeSteal = builder.comment("Minimum magicule steal from Deathless Life Siphon.")
            .defineInRange("deathlessLifeSiphonMagiculeSteal", 10000.0, 0.0, Double.MAX_VALUE);
         this.deathlessLifeSiphonAuraSteal = builder.comment("Minimum aura steal from Deathless Life Siphon.")
            .defineInRange("deathlessLifeSiphonAuraSteal", 10000.0, 0.0, Double.MAX_VALUE);
         this.deathlessLifeSiphonSpiritualSteal = builder.comment("Minimum spiritual health steal from Deathless Life Siphon.")
            .defineInRange("deathlessLifeSiphonSpiritualSteal", 5500.0, 0.0, Double.MAX_VALUE);
         this.deathlessLifeSiphonExecuteMinHealth = builder.comment("Minimum health threshold for execute check.")
            .defineInRange("deathlessLifeSiphonExecuteMinHealth", 8.0, 0.0, 1000000.0);
         this.deathlessLifeSiphonExecuteMaxHealthRatio = builder.comment("Max health ratio threshold for execute check.")
            .defineInRange("deathlessLifeSiphonExecuteMaxHealthRatio", 0.18, 0.0, 100.0);
         this.deathlessLifeSiphonMinHeal = builder.comment("Minimum self heal from Deathless Life Siphon.")
            .defineInRange("deathlessLifeSiphonMinHeal", 28.0, 0.0, 1000000.0);
         this.deathlessLifeSiphonHealDamageRatio = builder.comment("Self heal based on damage dealt by Deathless Life Siphon.")
            .defineInRange("deathlessLifeSiphonHealDamageRatio", 0.55, 0.0, 100.0);
         this.deathlessLifeSiphonSpiritualRestoreStolenRatio = builder.comment("Spiritual self restore ratio based on stolen magicule value.")
            .defineInRange("deathlessLifeSiphonSpiritualRestoreStolenRatio", 0.5, 0.0, 100.0);
         this.deathlessLifeSiphonTargetBurstRadius = builder.comment("Visual burst radius on the siphoned target.")
            .defineInRange("deathlessLifeSiphonTargetBurstRadius", 3.0, 0.0, 128.0);
         this.deathlessLifeSiphonCooldownTicks = builder.comment("Cooldown of Deathless Life Siphon.")
            .defineInRange("deathlessLifeSiphonCooldownTicks", 55, 0, 72000);
         builder.pop();
         builder.push("Bestow One Life");
         this.bestowOneLifeRange = builder.comment("Targeting range of Bestow One Life.").defineInRange("bestowOneLifeRange", 18.0, 0.0, 256.0);
         this.bestowOneLifeCost = builder.comment("MP cost of Bestow One Life.").defineInRange("bestowOneLifeCost", 125000.0, 0.0, Double.MAX_VALUE);
         this.bestowOneLifeRegenDurationTicks = builder.comment("Regeneration duration granted by Bestow One Life.")
            .defineInRange("bestowOneLifeRegenDurationTicks", 180, 1, 72000);
         this.bestowOneLifeRegenAmplifier = builder.comment("Regeneration amplifier granted by Bestow One Life.")
            .defineInRange("bestowOneLifeRegenAmplifier", 2, 0, 255);
         this.bestowOneLifeHakiDurationTicks = builder.comment("Haki Coat duration granted by Bestow One Life.")
            .defineInRange("bestowOneLifeHakiDurationTicks", 180, 1, 72000);
         this.bestowOneLifeHakiAmplifier = builder.comment("Haki Coat amplifier granted by Bestow One Life.")
            .defineInRange("bestowOneLifeHakiAmplifier", 2, 0, 255);
         this.bestowOneLifeMagiculeRestore = builder.comment("Magicule restored to the receiver of Bestow One Life.")
            .defineInRange("bestowOneLifeMagiculeRestore", 12000.0, 0.0, Double.MAX_VALUE);
         this.bestowOneLifeAuraRestore = builder.comment("Aura restored to the receiver of Bestow One Life.")
            .defineInRange("bestowOneLifeAuraRestore", 12000.0, 0.0, Double.MAX_VALUE);
         this.bestowOneLifeSpiritualRestore = builder.comment("Spiritual health restored to the receiver of Bestow One Life.")
            .defineInRange("bestowOneLifeSpiritualRestore", 8000.0, 0.0, Double.MAX_VALUE);
         this.bestowOneLifeBurstRadius = builder.comment("Visual burst radius for Bestow One Life.").defineInRange("bestowOneLifeBurstRadius", 2.8, 0.0, 128.0);
         this.bestowOneLifeCooldownTicks = builder.comment("Cooldown of Bestow One Life.").defineInRange("bestowOneLifeCooldownTicks", 90, 0, 72000);
         builder.pop();
         builder.push("Forced Evolution");
         this.forceEvolutionRange = builder.comment("Targeting range for forced evolution and awakening.")
            .defineInRange("forceEvolutionRange", 24.0, 0.0, 256.0);
         this.forceRaceEvolutionRequiredProgress = builder.comment("Required evolution progress percentage for forced player race evolution.")
            .defineInRange("forceRaceEvolutionRequiredProgress", 100, 0, 1000000);
         this.forcePlayerRaceEvolutionSoulCost = builder.comment("Soul point cost to force another player's race evolution.")
            .defineInRange("forcePlayerRaceEvolutionSoulCost", 1000000, 0, Integer.MAX_VALUE);
         this.forcePlayerRaceEvolutionCost = builder.comment("MP cost to force another player's race evolution.")
            .defineInRange("forcePlayerRaceEvolutionCost", 250000.0, 0.0, Double.MAX_VALUE);
         this.forcePlayerRaceEvolutionReleaseCharge = builder.comment("Visual release charge for forced player race evolution.")
            .defineInRange("forcePlayerRaceEvolutionReleaseCharge", 8, 0, 128);
         this.forcePlayerRaceEvolutionOvergrowthRadius = builder.comment("Overgrowth radius for forced player race evolution.")
            .defineInRange("forcePlayerRaceEvolutionOvergrowthRadius", 8, 0, 128);
         this.forcePlayerRaceEvolutionOvergrowthAttempts = builder.comment("Overgrowth attempts for forced player race evolution.")
            .defineInRange("forcePlayerRaceEvolutionOvergrowthAttempts", 100, 0, 4000);
         this.forcePlayerRaceEvolutionCooldownTicks = builder.comment("Cooldown for forced player race evolution.")
            .defineInRange("forcePlayerRaceEvolutionCooldownTicks", 180, 0, 72000);
         this.forcePlayerAwakeningSoulCost = builder.comment("Soul point cost to force True Hero or True Demon Lord awakening.")
            .defineInRange("forcePlayerAwakeningSoulCost", 20000000, 0, Integer.MAX_VALUE);
         this.forcePlayerAwakeningCost = builder.comment("MP cost to force True Hero or True Demon Lord awakening.")
            .defineInRange("forcePlayerAwakeningCost", 500000.0, 0.0, Double.MAX_VALUE);
         this.forcePlayerAwakeningReleaseCharge = builder.comment("Visual release charge for forced player awakening.")
            .defineInRange("forcePlayerAwakeningReleaseCharge", 12, 0, 128);
         this.forcePlayerAwakeningOvergrowthRadius = builder.comment("Overgrowth radius for forced player awakening.")
            .defineInRange("forcePlayerAwakeningOvergrowthRadius", 10, 0, 128);
         this.forcePlayerAwakeningOvergrowthAttempts = builder.comment("Overgrowth attempts for forced player awakening.")
            .defineInRange("forcePlayerAwakeningOvergrowthAttempts", 140, 0, 4000);
         this.forcePlayerAwakeningCooldownTicks = builder.comment("Cooldown for forced player awakening.")
            .defineInRange("forcePlayerAwakeningCooldownTicks", 240, 0, 72000);
         builder.pop();
         builder.push("Subordinate Evolution");
         this.forceSubordinateEvolutionSoulCostBase = builder.comment("Base soul point cost for subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionSoulCostBase", 250000, 0, Integer.MAX_VALUE);
         this.forceSubordinateEvolutionSoulCostPerStage = builder.comment("Extra soul point cost per subordinate evolution stage.")
            .defineInRange("forceSubordinateEvolutionSoulCostPerStage", 250000, 0, Integer.MAX_VALUE);
         this.forceSubordinateEvolutionCostBase = builder.comment("Base MP cost for subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionCostBase", 180000.0, 0.0, Double.MAX_VALUE);
         this.forceSubordinateEvolutionCostPerStage = builder.comment("Extra MP cost per subordinate evolution stage.")
            .defineInRange("forceSubordinateEvolutionCostPerStage", 40000.0, 0.0, Double.MAX_VALUE);
         this.forceSubordinateEvolutionHpBase = builder.comment("Base max health multiplier added per subordinate evolution stage.")
            .defineInRange("forceSubordinateEvolutionHpBase", 0.35, -1.0, 100.0);
         this.forceSubordinateEvolutionHpPerStage = builder.comment("Extra max health multiplier per subordinate stage.")
            .defineInRange("forceSubordinateEvolutionHpPerStage", 0.12, -1.0, 100.0);
         this.forceSubordinateEvolutionDamageBase = builder.comment("Base damage multiplier added per subordinate evolution stage.")
            .defineInRange("forceSubordinateEvolutionDamageBase", 0.3, -1.0, 100.0);
         this.forceSubordinateEvolutionDamagePerStage = builder.comment("Extra damage multiplier per subordinate stage.")
            .defineInRange("forceSubordinateEvolutionDamagePerStage", 0.1, -1.0, 100.0);
         this.forceSubordinateEvolutionSpeedBase = builder.comment("Base speed multiplier added per subordinate evolution stage.")
            .defineInRange("forceSubordinateEvolutionSpeedBase", 0.08, -1.0, 100.0);
         this.forceSubordinateEvolutionSpeedPerStage = builder.comment("Extra speed multiplier per subordinate stage.")
            .defineInRange("forceSubordinateEvolutionSpeedPerStage", 0.02, -1.0, 100.0);
         this.forceSubordinateEvolutionScaleCap = builder.comment("Maximum scale bonus from subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionScaleCap", 2.0, 0.0, 100.0);
         this.forceSubordinateEvolutionScaleBase = builder.comment("Base scale bonus from subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionScaleBase", 0.12, 0.0, 100.0);
         this.forceSubordinateEvolutionScalePerStage = builder.comment("Extra scale bonus per subordinate stage.")
            .defineInRange("forceSubordinateEvolutionScalePerStage", 0.04, 0.0, 100.0);
         this.forceSubordinateEvolutionMaxTrackedModifierStage = builder.comment(
               "Highest stage index used in attribute modifier ids. Prevents infinite unique modifier names."
            )
            .defineInRange("forceSubordinateEvolutionMaxTrackedModifierStage", 20, 0, 1000);
         this.forceSubordinateEvolutionDegradationBase = builder.comment("Base resistance degradation bonus from subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionDegradationBase", 0.2, -100.0, 100.0);
         this.forceSubordinateEvolutionDegradationPerStage = builder.comment("Extra resistance degradation per subordinate stage.")
            .defineInRange("forceSubordinateEvolutionDegradationPerStage", 0.05, -100.0, 100.0);
         this.forceSubordinateEvolutionHakiDurationTicks = builder.comment("Haki Coat duration from subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionHakiDurationTicks", 300, 1, 72000);
         this.forceSubordinateEvolutionHakiMaxAmplifier = builder.comment("Maximum Haki Coat amplifier from subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionHakiMaxAmplifier", 4, 0, 255);
         this.forceSubordinateEvolutionHakiBaseAmplifier = builder.comment("Base Haki Coat amplifier from subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionHakiBaseAmplifier", 1, 0, 255);
         this.forceSubordinateEvolutionRegenDurationTicks = builder.comment("Regeneration duration from subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionRegenDurationTicks", 300, 1, 72000);
         this.forceSubordinateEvolutionRegenMaxAmplifier = builder.comment("Maximum regeneration amplifier from subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionRegenMaxAmplifier", 4, 0, 255);
         this.forceSubordinateEvolutionRegenBaseAmplifier = builder.comment("Base regeneration amplifier from subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionRegenBaseAmplifier", 1, 0, 255);
         this.forceSubordinateEvolutionMagiculeRestoreBase = builder.comment("Base magicule restored to evolved subordinate.")
            .defineInRange("forceSubordinateEvolutionMagiculeRestoreBase", 200000.0, 0.0, Double.MAX_VALUE);
         this.forceSubordinateEvolutionMagiculeRestorePerStage = builder.comment("Extra magicule restored per subordinate stage.")
            .defineInRange("forceSubordinateEvolutionMagiculeRestorePerStage", 60000.0, 0.0, Double.MAX_VALUE);
         this.forceSubordinateEvolutionAuraRestoreBase = builder.comment("Base aura restored to evolved subordinate.")
            .defineInRange("forceSubordinateEvolutionAuraRestoreBase", 200000.0, 0.0, Double.MAX_VALUE);
         this.forceSubordinateEvolutionAuraRestorePerStage = builder.comment("Extra aura restored per subordinate stage.")
            .defineInRange("forceSubordinateEvolutionAuraRestorePerStage", 60000.0, 0.0, Double.MAX_VALUE);
         this.forceSubordinateEvolutionSpiritualRestoreBase = builder.comment("Base spiritual health restored to evolved subordinate.")
            .defineInRange("forceSubordinateEvolutionSpiritualRestoreBase", 120000.0, 0.0, Double.MAX_VALUE);
         this.forceSubordinateEvolutionSpiritualRestorePerStage = builder.comment("Extra spiritual health restored per subordinate stage.")
            .defineInRange("forceSubordinateEvolutionSpiritualRestorePerStage", 40000.0, 0.0, Double.MAX_VALUE);
         this.forceSubordinateEvolutionReleaseChargeBase = builder.comment("Base visual release charge for subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionReleaseChargeBase", 8, 0, 128);
         this.forceSubordinateEvolutionOvergrowthRadiusBase = builder.comment("Base overgrowth radius for subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionOvergrowthRadiusBase", 7, 0, 128);
         this.forceSubordinateEvolutionOvergrowthAttemptsBase = builder.comment("Base overgrowth attempts for subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionOvergrowthAttemptsBase", 90, 0, 4000);
         this.forceSubordinateEvolutionOvergrowthAttemptsPerStage = builder.comment("Extra overgrowth attempts per subordinate stage.")
            .defineInRange("forceSubordinateEvolutionOvergrowthAttemptsPerStage", 12, 0, 1000);
         this.forceSubordinateEvolutionCooldownTicks = builder.comment("Cooldown for subordinate evolution.")
            .defineInRange("forceSubordinateEvolutionCooldownTicks", 160, 0, 72000);
         builder.pop();
         builder.push("Subordinate Awakening");
         this.forceSubordinateAwakeningSoulCost = builder.comment("Soul point cost for false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningSoulCost", 5000000, 0, Integer.MAX_VALUE);
         this.forceSubordinateAwakeningCost = builder.comment("MP cost for false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningCost", 750000.0, 0.0, Double.MAX_VALUE);
         this.forceSubordinateAwakeningHpMultiplier = builder.comment("Max health multiplier from false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningHpMultiplier", 2.0, -1.0, 100.0);
         this.forceSubordinateAwakeningDamageMultiplier = builder.comment("Attack damage multiplier from false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningDamageMultiplier", 1.75, -1.0, 100.0);
         this.forceSubordinateAwakeningArmorBonus = builder.comment("Flat armor bonus from false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningArmorBonus", 50.0, -1024.0, 1000000.0);
         this.forceSubordinateAwakeningSpeedMultiplier = builder.comment("Movement speed multiplier from false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningSpeedMultiplier", 0.35, -1.0, 100.0);
         this.forceSubordinateAwakeningScaleBonus = builder.comment("Scale bonus from false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningScaleBonus", 0.75, 0.0, 100.0);
         this.forceSubordinateAwakeningDegradationBonus = builder.comment("Resistance degradation bonus from false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningDegradationBonus", 1.0, -100.0, 100.0);
         this.forceSubordinateAwakeningSpiritualRegenBonus = builder.comment("Spiritual health regeneration bonus from false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningSpiritualRegenBonus", 150.0, -1000000.0, 1000000.0);
         this.forceSubordinateAwakeningHakiDurationTicks = builder.comment("Haki Coat duration from false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningHakiDurationTicks", 600, 1, 72000);
         this.forceSubordinateAwakeningHakiAmplifier = builder.comment("Haki Coat amplifier from false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningHakiAmplifier", 4, 0, 255);
         this.forceSubordinateAwakeningRegenDurationTicks = builder.comment("Regeneration duration from false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningRegenDurationTicks", 600, 1, 72000);
         this.forceSubordinateAwakeningRegenAmplifier = builder.comment("Regeneration amplifier from false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningRegenAmplifier", 4, 0, 255);
         this.forceSubordinateAwakeningMagiculeRestore = builder.comment("Magicule restored to awakened subordinate.")
            .defineInRange("forceSubordinateAwakeningMagiculeRestore", 1500000.0, 0.0, Double.MAX_VALUE);
         this.forceSubordinateAwakeningAuraRestore = builder.comment("Aura restored to awakened subordinate.")
            .defineInRange("forceSubordinateAwakeningAuraRestore", 1500000.0, 0.0, Double.MAX_VALUE);
         this.forceSubordinateAwakeningSpiritualRestore = builder.comment("Spiritual health restored to awakened subordinate.")
            .defineInRange("forceSubordinateAwakeningSpiritualRestore", 900000.0, 0.0, Double.MAX_VALUE);
         this.forceSubordinateAwakeningReleaseCharge = builder.comment("Visual release charge for false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningReleaseCharge", 15, 0, 128);
         this.forceSubordinateAwakeningOvergrowthRadius = builder.comment("Overgrowth radius for false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningOvergrowthRadius", 12, 0, 128);
         this.forceSubordinateAwakeningOvergrowthAttempts = builder.comment("Overgrowth attempts for false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningOvergrowthAttempts", 170, 0, 4000);
         this.forceSubordinateAwakeningCooldownTicks = builder.comment("Cooldown for false subordinate awakening.")
            .defineInRange("forceSubordinateAwakeningCooldownTicks", 300, 0, 72000);
         builder.pop();
         builder.push("Fruit Of Life");
         this.fruitOfLifeCost = builder.comment("MP cost to create Fruit of Life.").defineInRange("fruitOfLifeCost", 300000.0, 0.0, Double.MAX_VALUE);
         this.fruitOfLifeOvergrowthRadius = builder.comment("Overgrowth radius when creating Fruit of Life.")
            .defineInRange("fruitOfLifeOvergrowthRadius", 9, 0, 128);
         this.fruitOfLifeOvergrowthAttempts = builder.comment("Overgrowth attempts when creating Fruit of Life.")
            .defineInRange("fruitOfLifeOvergrowthAttempts", 130, 0, 4000);
         this.fruitOfLifeReleaseCharge = builder.comment("Visual release charge when creating Fruit of Life.")
            .defineInRange("fruitOfLifeReleaseCharge", 8, 0, 128);
         this.fruitOfLifeCooldownTicks = builder.comment("Cooldown for creating Fruit of Life.").defineInRange("fruitOfLifeCooldownTicks", 300, 0, 72000);
         builder.pop();
         builder.push("Passive Recovery And Pressure");
         this.combatRecoveryMinHeal = builder.comment("Minimum heal from passive combat recovery.").defineInRange("combatRecoveryMinHeal", 4.0, 0.0, 1000000.0);
         this.combatRecoveryMissingHealthRatio = builder.comment("Passive recovery heal as ratio of missing health.")
            .defineInRange("combatRecoveryMissingHealthRatio", 0.08, 0.0, 100.0);
         this.combatRecoveryLowHealthThreshold = builder.comment("Health ratio threshold where passive recovery becomes stronger.")
            .defineInRange("combatRecoveryLowHealthThreshold", 0.35, 0.0, 1.0);
         this.combatRecoveryLowHealthHealMultiplier = builder.comment("Heal multiplier while below the low-health threshold.")
            .defineInRange("combatRecoveryLowHealthHealMultiplier", 2.0, 0.0, 100.0);
         this.combatRecoveryLowHealthHakiDurationTicks = builder.comment("Haki Coat duration while passive recovery triggers at low health.")
            .defineInRange("combatRecoveryLowHealthHakiDurationTicks", 80, 1, 72000);
         this.combatRecoveryLowHealthHakiAmplifier = builder.comment("Haki Coat amplifier while passive recovery triggers at low health.")
            .defineInRange("combatRecoveryLowHealthHakiAmplifier", 1, 0, 255);
         this.combatRecoveryMagiculeRestore = builder.comment("Magicule restored by passive combat recovery.")
            .defineInRange("combatRecoveryMagiculeRestore", 1800.0, 0.0, Double.MAX_VALUE);
         this.combatRecoveryAuraRestore = builder.comment("Aura restored by passive combat recovery.")
            .defineInRange("combatRecoveryAuraRestore", 1800.0, 0.0, Double.MAX_VALUE);
         this.combatRecoverySpiritualRestore = builder.comment("Spiritual health restored by passive combat recovery.")
            .defineInRange("combatRecoverySpiritualRestore", 900.0, 0.0, Double.MAX_VALUE);
         this.combatPressureRadius = builder.comment("Radius of passive combat pressure.").defineInRange("combatPressureRadius", 7.0, 0.0, 256.0);
         this.combatPressureOrganDecayDurationTicks = builder.comment("Organ Decay duration from passive combat pressure.")
            .defineInRange("combatPressureOrganDecayDurationTicks", 80, 1, 72000);
         this.combatPressureOrganDecayAmplifier = builder.comment("Organ Decay amplifier from passive combat pressure.")
            .defineInRange("combatPressureOrganDecayAmplifier", 0, 0, 255);
         builder.pop();
         builder.push("Sustain Pulse");
         this.combatSustainCostIntervalTicks = builder.comment("How often Crimson Eden charges its sustain MP cost.")
            .defineInRange("combatSustainCostIntervalTicks", 20, 1, 72000);
         this.combatSustainCost = builder.comment("MP cost per sustain interval.").defineInRange("combatSustainCost", 50.0, 0.0, Double.MAX_VALUE);
         this.combatSustainRegenDurationTicks = builder.comment("Regeneration duration from sustain pulse.")
            .defineInRange("combatSustainRegenDurationTicks", 120, 1, 72000);
         this.combatSustainRegenAmplifier = builder.comment("Regeneration amplifier from sustain pulse.")
            .defineInRange("combatSustainRegenAmplifier", 1, 0, 255);
         this.combatSustainHealIntervalTicks = builder.comment("How often sustain pulse applies direct healing and existence restore.")
            .defineInRange("combatSustainHealIntervalTicks", 20, 1, 72000);
         this.combatSustainMinHeal = builder.comment("Minimum direct heal from sustain pulse.").defineInRange("combatSustainMinHeal", 3.0, 0.0, 1000000.0);
         this.combatSustainHealMaxHealthRatio = builder.comment("Direct heal ratio of max health from sustain pulse.")
            .defineInRange("combatSustainHealMaxHealthRatio", 0.02, 0.0, 100.0);
         this.combatSustainMagiculeRestore = builder.comment("Magicule restored by sustain pulse.")
            .defineInRange("combatSustainMagiculeRestore", 1200.0, 0.0, Double.MAX_VALUE);
         this.combatSustainAuraRestore = builder.comment("Aura restored by sustain pulse.")
            .defineInRange("combatSustainAuraRestore", 1200.0, 0.0, Double.MAX_VALUE);
         this.combatSustainSpiritualRestore = builder.comment("Spiritual health restored by sustain pulse.")
            .defineInRange("combatSustainSpiritualRestore", 800.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("Shared Combat Values");
         this.allyRegenPulseMagiculeRestoreBase = builder.comment("Base magicule restored by ally regeneration pulses.")
            .defineInRange("allyRegenPulseMagiculeRestoreBase", 2000.0, 0.0, Double.MAX_VALUE);
         this.allyRegenPulseMagiculeRestorePerAmplifier = builder.comment("Extra magicule restored by ally regeneration pulses per amplifier.")
            .defineInRange("allyRegenPulseMagiculeRestorePerAmplifier", 1000.0, 0.0, Double.MAX_VALUE);
         this.allyRegenPulseAuraRestoreBase = builder.comment("Base aura restored by ally regeneration pulses.")
            .defineInRange("allyRegenPulseAuraRestoreBase", 2000.0, 0.0, Double.MAX_VALUE);
         this.allyRegenPulseAuraRestorePerAmplifier = builder.comment("Extra aura restored by ally regeneration pulses per amplifier.")
            .defineInRange("allyRegenPulseAuraRestorePerAmplifier", 1000.0, 0.0, Double.MAX_VALUE);
         this.allyRegenPulseSpiritualRestoreBase = builder.comment("Base spiritual health restored by ally regeneration pulses.")
            .defineInRange("allyRegenPulseSpiritualRestoreBase", 1200.0, 0.0, Double.MAX_VALUE);
         this.allyRegenPulseSpiritualRestorePerAmplifier = builder.comment("Extra spiritual health restored by ally regeneration pulses per amplifier.")
            .defineInRange("allyRegenPulseSpiritualRestorePerAmplifier", 700.0, 0.0, Double.MAX_VALUE);
         this.combatStealMagiculeRatio = builder.comment("Ratio of target magicule stolen when stealing existence. Minimum steal values still apply.")
            .defineInRange("combatStealMagiculeRatio", 0.08, 0.0, 1.0);
         this.combatStealAuraRatio = builder.comment("Ratio of target aura stolen when stealing existence. Minimum steal values still apply.")
            .defineInRange("combatStealAuraRatio", 0.08, 0.0, 1.0);
         this.combatStealSpiritualRatio = builder.comment("Ratio of target spiritual health stolen when stealing existence. Minimum steal values still apply.")
            .defineInRange("combatStealSpiritualRatio", 0.04, 0.0, 1.0);
         this.knockAwayVerticalVelocity = builder.comment("Vertical velocity added by knock-away effects.")
            .defineInRange("knockAwayVerticalVelocity", 0.28, -100.0, 100.0);
         this.pullHorizontalDamping = builder.comment("Horizontal motion multiplier applied before pull effects.")
            .defineInRange("pullHorizontalDamping", 0.45, 0.0, 100.0);
         this.pullVerticalDamping = builder.comment("Vertical motion multiplier applied before pull effects.")
            .defineInRange("pullVerticalDamping", 0.25, 0.0, 100.0);
         this.pullVerticalVelocity = builder.comment("Vertical velocity added by pull effects.").defineInRange("pullVerticalVelocity", 0.05, -100.0, 100.0);
         this.combatChargeMaxRadius = builder.comment("Maximum radius for combat charge particles.").defineInRange("combatChargeMaxRadius", 5.5, 0.0, 256.0);
         this.combatChargeRadiusBase = builder.comment("Base radius for combat charge particles.").defineInRange("combatChargeRadiusBase", 1.4, 0.0, 256.0);
         this.combatChargeRadiusPerCharge = builder.comment("Extra combat charge particle radius per charge.")
            .defineInRange("combatChargeRadiusPerCharge", 0.22, 0.0, 256.0);
         this.combatChargeSoundEveryCharge = builder.comment("Combat charge sound frequency. 3 means every third charge.")
            .defineInRange("combatChargeSoundEveryCharge", 3, 1, 1000);
         this.combatReleaseMaxRadius = builder.comment("Maximum radius for combat release particles.")
            .defineInRange("combatReleaseMaxRadius", 10.0, 0.0, 256.0);
         this.combatReleaseRadiusBase = builder.comment("Base radius for combat release particles.").defineInRange("combatReleaseRadiusBase", 3.0, 0.0, 256.0);
         this.combatReleaseRadiusPerCharge = builder.comment("Extra combat release particle radius per charge.")
            .defineInRange("combatReleaseRadiusPerCharge", 0.45, 0.0, 256.0);
         this.drainLineParticleSteps = builder.comment("Number of particle steps between target and user for drain beams.")
            .defineInRange("drainLineParticleSteps", 22, 2, 512);
         builder.pop();
      }
   }

   public static final class NirvanaConfig {
      public final IntValue maxArchiveSize;
      public final IntValue bonusUniqueChance;
      public final IntValue epDivisor;
      public final IntValue minimumEPKarma;
      public final IntValue maximumEPKarma;
      public final IntValue minimumKarmaLifeTicks;
      public final IntValue commonModeCost;
      public final IntValue intrinsicModeCost;
      public final IntValue extraModeCost;
      public final IntValue resistanceModeCost;
      public final IntValue nullificationModeCost;
      public final IntValue uniqueModeCost;
      public final IntValue ultimateModeCost;
      public final BooleanValue allowUltimateMigration;
      public final IntValue timeKarma1Day;
      public final IntValue timeKarma3Days;
      public final IntValue timeKarma7Days;
      public final IntValue timeKarma14Days;
      public final IntValue timeKarma30Days;
      public final IntValue timeKarma60Days;
      public final IntValue timeKarma120Days;
      public final IntValue timeKarma240Days;
      public final IntValue timeKarma365Days;
      public final IntValue commonAcquireKarma;
      public final IntValue commonMasterKarma;
      public final IntValue intrinsicAcquireKarma;
      public final IntValue intrinsicMasterKarma;
      public final IntValue extraAcquireKarma;
      public final IntValue extraMasterKarma;
      public final IntValue resistanceAcquireKarma;
      public final IntValue nullificationAcquireKarma;
      public final IntValue uniqueAcquireKarma;
      public final IntValue uniqueMasterKarma;
      public final IntValue ultimateAcquireKarma;
      public final IntValue ultimateMasterKarma;
      public final IntValue orcLordBossKarma;
      public final IntValue supermassiveSlimeBossKarma;
      public final IntValue greaterSpiritBossKarma;
      public final IntValue orcDisasterBossKarma;
      public final IntValue ogreRimuruBossKarma;
      public final IntValue shizuBossKarma;
      public final IntValue elementalColossusBossKarma;
      public final IntValue witherBossKarma;
      public final IntValue wardenBossKarma;
      public final IntValue enderDragonBossKarma;
      public final IntValue hinataBossKarma;
      public final IntValue carrionBossKarma;
      public final IntValue luminousBossKarma;

      NirvanaConfig(Builder builder) {
         builder.push("nirvana");
         this.maxArchiveSize = builder.defineInRange("maxArchiveSize", 128, 1, 10000);
         this.bonusUniqueChance = builder.defineInRange("bonusUniqueChance", 5, 0, 100);
         this.epDivisor = builder.defineInRange("epDivisor", 100, 1, Integer.MAX_VALUE);
         this.minimumEPKarma = builder.defineInRange("minimumEPKarma", 50, 0, Integer.MAX_VALUE);
         this.maximumEPKarma = builder.defineInRange("maximumEPKarma", 500000, 0, Integer.MAX_VALUE);
         this.minimumKarmaLifeTicks = builder.defineInRange("minimumKarmaLifeTicks", 5000, 0, Integer.MAX_VALUE);
         this.commonModeCost = builder.defineInRange("commonModeCost", 300, 0, Integer.MAX_VALUE);
         this.intrinsicModeCost = builder.defineInRange("intrinsicModeCost", 1000, 0, Integer.MAX_VALUE);
         this.extraModeCost = builder.defineInRange("extraModeCost", 3000, 0, Integer.MAX_VALUE);
         this.resistanceModeCost = builder.defineInRange("resistanceModeCost", 10000, 0, Integer.MAX_VALUE);
         this.nullificationModeCost = builder.defineInRange("nullificationModeCost", 30000, 0, Integer.MAX_VALUE);
         this.uniqueModeCost = builder.defineInRange("uniqueModeCost", 60000, 0, Integer.MAX_VALUE);
         this.ultimateModeCost = builder.defineInRange("ultimateModeCost", 500000, 0, Integer.MAX_VALUE);
         this.allowUltimateMigration = builder.define("allowUltimateMigration", true);
         this.timeKarma1Day = builder.defineInRange("timeKarma1Day", 25, 0, Integer.MAX_VALUE);
         this.timeKarma3Days = builder.defineInRange("timeKarma3Days", 75, 0, Integer.MAX_VALUE);
         this.timeKarma7Days = builder.defineInRange("timeKarma7Days", 200, 0, Integer.MAX_VALUE);
         this.timeKarma14Days = builder.defineInRange("timeKarma14Days", 450, 0, Integer.MAX_VALUE);
         this.timeKarma30Days = builder.defineInRange("timeKarma30Days", 1000, 0, Integer.MAX_VALUE);
         this.timeKarma60Days = builder.defineInRange("timeKarma60Days", 2500, 0, Integer.MAX_VALUE);
         this.timeKarma120Days = builder.defineInRange("timeKarma120Days", 6000, 0, Integer.MAX_VALUE);
         this.timeKarma240Days = builder.defineInRange("timeKarma240Days", 12000, 0, Integer.MAX_VALUE);
         this.timeKarma365Days = builder.defineInRange("timeKarma365Days", 20000, 0, Integer.MAX_VALUE);
         this.commonAcquireKarma = builder.defineInRange("commonAcquireKarma", 50, 0, Integer.MAX_VALUE);
         this.commonMasterKarma = builder.defineInRange("commonMasterKarma", 50, 0, Integer.MAX_VALUE);
         this.intrinsicAcquireKarma = builder.defineInRange("intrinsicAcquireKarma", 150, 0, Integer.MAX_VALUE);
         this.intrinsicMasterKarma = builder.defineInRange("intrinsicMasterKarma", 150, 0, Integer.MAX_VALUE);
         this.extraAcquireKarma = builder.defineInRange("extraAcquireKarma", 500, 0, Integer.MAX_VALUE);
         this.extraMasterKarma = builder.defineInRange("extraMasterKarma", 500, 0, Integer.MAX_VALUE);
         this.resistanceAcquireKarma = builder.defineInRange("resistanceAcquireKarma", 500, 0, Integer.MAX_VALUE);
         this.nullificationAcquireKarma = builder.defineInRange("nullificationAcquireKarma", 1000, 0, Integer.MAX_VALUE);
         this.uniqueAcquireKarma = builder.defineInRange("uniqueAcquireKarma", 10000, 0, Integer.MAX_VALUE);
         this.uniqueMasterKarma = builder.defineInRange("uniqueMasterKarma", 10000, 0, Integer.MAX_VALUE);
         this.ultimateAcquireKarma = builder.defineInRange("ultimateAcquireKarma", 100000, 0, Integer.MAX_VALUE);
         this.ultimateMasterKarma = builder.defineInRange("ultimateMasterKarma", 50000, 0, Integer.MAX_VALUE);
         this.orcLordBossKarma = builder.defineInRange("orcLordBossKarma", 1500, 0, Integer.MAX_VALUE);
         this.supermassiveSlimeBossKarma = builder.defineInRange("supermassiveSlimeBossKarma", 1500, 0, Integer.MAX_VALUE);
         this.greaterSpiritBossKarma = builder.defineInRange("greaterSpiritBossKarma", 10000, 0, Integer.MAX_VALUE);
         this.orcDisasterBossKarma = builder.defineInRange("orcDisasterBossKarma", 10000, 0, Integer.MAX_VALUE);
         this.ogreRimuruBossKarma = builder.defineInRange("ogreRimuruBossKarma", 10000, 0, Integer.MAX_VALUE);
         this.shizuBossKarma = builder.defineInRange("shizuBossKarma", 10000, 0, Integer.MAX_VALUE);
         this.elementalColossusBossKarma = builder.defineInRange("elementalColossusBossKarma", 10000, 0, Integer.MAX_VALUE);
         this.witherBossKarma = builder.defineInRange("witherBossKarma", 10000, 0, Integer.MAX_VALUE);
         this.wardenBossKarma = builder.defineInRange("wardenBossKarma", 10000, 0, Integer.MAX_VALUE);
         this.enderDragonBossKarma = builder.defineInRange("enderDragonBossKarma", 10000, 0, Integer.MAX_VALUE);
         this.hinataBossKarma = builder.defineInRange("hinataBossKarma", 50000, 0, Integer.MAX_VALUE);
         this.carrionBossKarma = builder.defineInRange("carrionBossKarma", 50000, 0, Integer.MAX_VALUE);
         this.luminousBossKarma = builder.defineInRange("luminousBossKarma", 100000, 0, Integer.MAX_VALUE);
         builder.pop();
      }
   }

   public static final class PainLordOfSixPaths {
      public final DoubleValue acquirementMagiculeCost;
      public final DoubleValue requiredEp;
      public final DoubleValue almightyPushCost;
      public final DoubleValue almightyPushMinChargeRatio;
      public final DoubleValue almightyPushMinRadius;
      public final DoubleValue almightyPushMaxRadius;
      public final DoubleValue almightyPushMinDamage;
      public final DoubleValue almightyPushMaxDamage;
      public final DoubleValue almightyPushMaxDamageMastered;
      public final DoubleValue almightyPushMinForce;
      public final DoubleValue almightyPushMaxForce;
      public final DoubleValue almightyPushVerticalLift;
      public final DoubleValue almightyPushMinFalloff;
      public final DoubleValue almightyPushProjectileSpeed;
      public final DoubleValue almightyPushBlockFlingChargeRatio;
      public final DoubleValue almightyPushCraterChargeRatio;
      public final DoubleValue almightyPushBlockRadiusRatio;
      public final DoubleValue almightyPushCraterRadiusRatio;
      public final DoubleValue universalPullCost;
      public final DoubleValue universalPullRange;
      public final DoubleValue universalPullRangeMastered;
      public final DoubleValue universalPullForce;
      public final DoubleValue universalPullForceMastered;
      public final DoubleValue universalPullConeDot;
      public final DoubleValue heavenlyRuptureCost;
      public final DoubleValue heavenlyRuptureRange;
      public final DoubleValue heavenlyRuptureDamage;
      public final DoubleValue heavenlyRuptureDamageMastered;
      public final DoubleValue heavenlyRuptureLaunch;
      public final DoubleValue heavenlyRuptureLaunchMastered;
      public final DoubleValue pathDevaCost;
      public final DoubleValue pathDevaRadius;
      public final DoubleValue pathDevaDamage;
      public final DoubleValue pathDevaForce;
      public final DoubleValue pathAsuraCost;
      public final DoubleValue pathAsuraDamage;
      public final DoubleValue pathAsuraForce;
      public final DoubleValue pathAsuraShotRadius;
      public final DoubleValue pathAsuraShotSpacing;
      public final DoubleValue pathHumanCost;
      public final DoubleValue pathHumanRange;
      public final DoubleValue pathHumanHealthRatio;
      public final DoubleValue pathHumanMinDamage;
      public final DoubleValue pathHumanMaxDamage;
      public final DoubleValue pathHumanEnergyDrain;
      public final DoubleValue pathAnimalCost;
      public final DoubleValue pathAnimalTargetRange;
      public final DoubleValue pathAnimalSpawnRadius;
      public final DoubleValue pathPretaCost;
      public final DoubleValue pathPretaRadius;
      public final DoubleValue pathPretaProjectileSpeed;
      public final DoubleValue pathPretaProjectileMagiculeGain;
      public final DoubleValue pathPretaDamageToMagiculeRatio;
      public final DoubleValue pathPretaMinAbsorbGain;
      public final DoubleValue pathNarakaCost;
      public final DoubleValue pathNarakaHealRatio;
      public final DoubleValue pathNarakaMinHeal;
      public final DoubleValue pathNarakaSpiritualHeal;
      public final DoubleValue pathNarakaMagiculeHeal;
      public final DoubleValue planetaryDevastationCost;
      public final DoubleValue planetaryDevastationShootDistance;
      public final DoubleValue planetaryDevastationSkyRise;
      public final DoubleValue planetaryDevastationEntityPullRadius;
      public final DoubleValue planetaryDevastationPullForce;
      public final DoubleValue planetaryDevastationPullDamage;
      public final DoubleValue planetaryDevastationPlanetRadius;
      public final DoubleValue planetaryDevastationDropSpeed;
      public final DoubleValue planetaryDevastationExplosionRadius;
      public final DoubleValue planetaryDevastationImpactDamage;
      public final DoubleValue planetaryDevastationHealthDamageRatio;
      public final DoubleValue planetaryDevastationMaxImpactDamage;
      public final DoubleValue planetaryDevastationMinFalloff;
      public final DoubleValue planetaryDevastationImpactKnockback;
      public final DoubleValue planetaryDevastationImpactLift;
      public final DoubleValue planetaryDevastationExplosionPower;
      public final IntValue acquirementMastery;
      public final IntValue maxMastery;
      public final IntValue almightyPushMinChargeTicks;
      public final IntValue almightyPushMaxChargeTicks;
      public final IntValue almightyPushChargeParticleIntervalTicks;
      public final IntValue almightyPushCooldownTicks;
      public final IntValue almightyPushMaxCooldownTicks;
      public final IntValue almightyPushBurdenTicks;
      public final IntValue almightyPushBurdenAmplifier;
      public final IntValue almightyPushStaggerTicks;
      public final IntValue almightyPushStaggerAmplifier;
      public final IntValue almightyPushMinBlocks;
      public final IntValue almightyPushMaxBlocks;
      public final IntValue almightyPushFakeBlockLifeTicks;
      public final IntValue almightyPushCraterMaxRadius;
      public final IntValue almightyPushCraterMinDepth;
      public final IntValue almightyPushCraterMaxDepth;
      public final IntValue failedTargetCooldownTicks;
      public final IntValue passiveRefreshTicks;
      public final IntValue heavenlyRuptureCooldownTicks;
      public final IntValue heavenlyRuptureBurdenTicks;
      public final IntValue heavenlyRuptureBurdenAmplifier;
      public final IntValue heavenlyRuptureDropDelayTicks;
      public final IntValue pathDevaCooldownTicks;
      public final IntValue pathAsuraShots;
      public final IntValue pathAsuraCooldownTicks;
      public final IntValue pathHumanCooldownTicks;
      public final IntValue pathHumanSoulDrainTicks;
      public final IntValue pathHumanSoulDrainAmplifier;
      public final IntValue pathAnimalSummons;
      public final IntValue pathAnimalSummonsMastered;
      public final IntValue pathAnimalDurationTicks;
      public final IntValue pathAnimalAuraAmplifier;
      public final IntValue pathAnimalCooldownTicks;
      public final IntValue pathPretaDurationTicks;
      public final IntValue pathPretaProjectileAbsorbIntervalTicks;
      public final IntValue pathPretaBarrierAmplifier;
      public final IntValue pathPretaMagicInterferenceAmplifier;
      public final IntValue pathPretaCooldownTicks;
      public final IntValue pathNarakaCooldownTicks;
      public final IntValue planetaryDevastationCooldownTicks;
      public final IntValue planetaryDevastationFlightTicks;
      public final IntValue planetaryDevastationHoldTicks;
      public final IntValue planetaryDevastationBlockScanIntervalTicks;
      public final IntValue planetaryDevastationBlocksPerScan;
      public final IntValue planetaryDevastationEntityPullIntervalTicks;
      public final IntValue planetaryDevastationEntityDamageIntervalTicks;
      public final IntValue planetaryDevastationAssembleTicks;
      public final IntValue planetaryDevastationMaxBlocks;
      public final IntValue planetaryDevastationBlockPullRadius;
      public final IntValue planetaryDevastationMaxDropTicks;
      public final IntValue planetaryDevastationCraterRadius;
      public final IntValue planetaryDevastationDebuffTicks;
      public final IntValue planetaryDevastationMovementAmplifier;
      public final IntValue planetaryDevastationFragilityAmplifier;
      public final BooleanValue requiredSkillMustBeMastered;
      public final BooleanValue almightyPushCraterDropBlocks;
      public final BooleanValue planetaryDevastationDropDestroyedBlocks;
      public final ConfigValue<String> requiredSkillId;

      PainLordOfSixPaths(Builder builder) {
         this.acquirementMagiculeCost = builder.defineInRange("acquirementMagiculeCost", 1000000.0, 0.0, Double.MAX_VALUE);
         this.requiredEp = builder.defineInRange("requiredEp", 1000000.0, 0.0, Double.MAX_VALUE);
         this.almightyPushCost = builder.defineInRange("almightyPushCost", 120000.0, 0.0, Double.MAX_VALUE);
         this.almightyPushMinChargeRatio = builder.defineInRange("almightyPushMinChargeRatio", 0.04, 0.0, Double.MAX_VALUE);
         this.almightyPushMinRadius = builder.defineInRange("almightyPushMinRadius", 7.0, 0.0, Double.MAX_VALUE);
         this.almightyPushMaxRadius = builder.defineInRange("almightyPushMaxRadius", 80.0, 0.0, Double.MAX_VALUE);
         this.almightyPushMinDamage = builder.defineInRange("almightyPushMinDamage", 120.0, 0.0, Double.MAX_VALUE);
         this.almightyPushMaxDamage = builder.defineInRange("almightyPushMaxDamage", 9000.0, 0.0, Double.MAX_VALUE);
         this.almightyPushMaxDamageMastered = builder.defineInRange("almightyPushMaxDamageMastered", 14000.0, 0.0, Double.MAX_VALUE);
         this.almightyPushMinForce = builder.defineInRange("almightyPushMinForce", 1.2, 0.0, Double.MAX_VALUE);
         this.almightyPushMaxForce = builder.defineInRange("almightyPushMaxForce", 9.0, 0.0, Double.MAX_VALUE);
         this.almightyPushVerticalLift = builder.defineInRange("almightyPushVerticalLift", 0.75, 0.0, Double.MAX_VALUE);
         this.almightyPushMinFalloff = builder.defineInRange("almightyPushMinFalloff", 0.25, 0.0, Double.MAX_VALUE);
         this.almightyPushProjectileSpeed = builder.defineInRange("almightyPushProjectileSpeed", 3.2, 0.0, Double.MAX_VALUE);
         this.almightyPushBlockFlingChargeRatio = builder.defineInRange("almightyPushBlockFlingChargeRatio", 0.35, 0.0, Double.MAX_VALUE);
         this.almightyPushCraterChargeRatio = builder.defineInRange("almightyPushCraterChargeRatio", 0.95, 0.0, Double.MAX_VALUE);
         this.almightyPushBlockRadiusRatio = builder.defineInRange("almightyPushBlockRadiusRatio", 0.78, 0.0, Double.MAX_VALUE);
         this.almightyPushCraterRadiusRatio = builder.defineInRange("almightyPushCraterRadiusRatio", 0.55, 0.0, Double.MAX_VALUE);
         this.universalPullCost = builder.defineInRange("universalPullCost", 70000.0, 0.0, Double.MAX_VALUE);
         this.universalPullRange = builder.defineInRange("universalPullRange", 48.0, 0.0, Double.MAX_VALUE);
         this.universalPullRangeMastered = builder.defineInRange("universalPullRangeMastered", 72.0, 0.0, Double.MAX_VALUE);
         this.universalPullForce = builder.defineInRange("universalPullForce", 3.4, 0.0, Double.MAX_VALUE);
         this.universalPullForceMastered = builder.defineInRange("universalPullForceMastered", 5.2, 0.0, Double.MAX_VALUE);
         this.universalPullConeDot = builder.defineInRange("universalPullConeDot", 0.35, 0.0, Double.MAX_VALUE);
         this.heavenlyRuptureCost = builder.defineInRange("heavenlyRuptureCost", 150000.0, 0.0, Double.MAX_VALUE);
         this.heavenlyRuptureRange = builder.defineInRange("heavenlyRuptureRange", 54.0, 0.0, Double.MAX_VALUE);
         this.heavenlyRuptureDamage = builder.defineInRange("heavenlyRuptureDamage", 2500.0, 0.0, Double.MAX_VALUE);
         this.heavenlyRuptureDamageMastered = builder.defineInRange("heavenlyRuptureDamageMastered", 4500.0, 0.0, Double.MAX_VALUE);
         this.heavenlyRuptureLaunch = builder.defineInRange("heavenlyRuptureLaunch", 3.2, 0.0, Double.MAX_VALUE);
         this.heavenlyRuptureLaunchMastered = builder.defineInRange("heavenlyRuptureLaunchMastered", 4.6, 0.0, Double.MAX_VALUE);
         this.pathDevaCost = builder.defineInRange("pathDevaCost", 65000.0, 0.0, Double.MAX_VALUE);
         this.pathDevaRadius = builder.defineInRange("pathDevaRadius", 10.0, 0.0, Double.MAX_VALUE);
         this.pathDevaDamage = builder.defineInRange("pathDevaDamage", 1800.0, 0.0, Double.MAX_VALUE);
         this.pathDevaForce = builder.defineInRange("pathDevaForce", 3.0, 0.0, Double.MAX_VALUE);
         this.pathAsuraCost = builder.defineInRange("pathAsuraCost", 90000.0, 0.0, Double.MAX_VALUE);
         this.pathAsuraDamage = builder.defineInRange("pathAsuraDamage", 900.0, 0.0, Double.MAX_VALUE);
         this.pathAsuraForce = builder.defineInRange("pathAsuraForce", 1.6, 0.0, Double.MAX_VALUE);
         this.pathAsuraShotRadius = builder.defineInRange("pathAsuraShotRadius", 2.4, 0.0, Double.MAX_VALUE);
         this.pathAsuraShotSpacing = builder.defineInRange("pathAsuraShotSpacing", 3.0, 0.0, Double.MAX_VALUE);
         this.pathHumanCost = builder.defineInRange("pathHumanCost", 160000.0, 0.0, Double.MAX_VALUE);
         this.pathHumanRange = builder.defineInRange("pathHumanRange", 6.0, 0.0, Double.MAX_VALUE);
         this.pathHumanHealthRatio = builder.defineInRange("pathHumanHealthRatio", 0.35, 0.0, Double.MAX_VALUE);
         this.pathHumanMinDamage = builder.defineInRange("pathHumanMinDamage", 500.0, 0.0, Double.MAX_VALUE);
         this.pathHumanMaxDamage = builder.defineInRange("pathHumanMaxDamage", 9000.0, 0.0, Double.MAX_VALUE);
         this.pathHumanEnergyDrain = builder.defineInRange("pathHumanEnergyDrain", 250000.0, 0.0, Double.MAX_VALUE);
         this.pathAnimalCost = builder.defineInRange("pathAnimalCost", 140000.0, 0.0, Double.MAX_VALUE);
         this.pathAnimalTargetRange = builder.defineInRange("pathAnimalTargetRange", 48.0, 0.0, Double.MAX_VALUE);
         this.pathAnimalSpawnRadius = builder.defineInRange("pathAnimalSpawnRadius", 3.5, 0.0, Double.MAX_VALUE);
         this.pathPretaCost = builder.defineInRange("pathPretaCost", 90000.0, 0.0, Double.MAX_VALUE);
         this.pathPretaRadius = builder.defineInRange("pathPretaRadius", 8.0, 0.0, Double.MAX_VALUE);
         this.pathPretaProjectileSpeed = builder.defineInRange("pathPretaProjectileSpeed", 3.5, 0.0, Double.MAX_VALUE);
         this.pathPretaProjectileMagiculeGain = builder.defineInRange("pathPretaProjectileMagiculeGain", 3000.0, 0.0, Double.MAX_VALUE);
         this.pathPretaDamageToMagiculeRatio = builder.defineInRange("pathPretaDamageToMagiculeRatio", 60.0, 0.0, Double.MAX_VALUE);
         this.pathPretaMinAbsorbGain = builder.defineInRange("pathPretaMinAbsorbGain", 5000.0, 0.0, Double.MAX_VALUE);
         this.pathNarakaCost = builder.defineInRange("pathNarakaCost", 180000.0, 0.0, Double.MAX_VALUE);
         this.pathNarakaHealRatio = builder.defineInRange("pathNarakaHealRatio", 0.45, 0.0, Double.MAX_VALUE);
         this.pathNarakaMinHeal = builder.defineInRange("pathNarakaMinHeal", 30.0, 0.0, Double.MAX_VALUE);
         this.pathNarakaSpiritualHeal = builder.defineInRange("pathNarakaSpiritualHeal", 50000.0, 0.0, Double.MAX_VALUE);
         this.pathNarakaMagiculeHeal = builder.defineInRange("pathNarakaMagiculeHeal", 250000.0, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationCost = builder.defineInRange("planetaryDevastationCost", 600000.0, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationShootDistance = builder.defineInRange("planetaryDevastationShootDistance", 24.0, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationSkyRise = builder.defineInRange("planetaryDevastationSkyRise", 52.0, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationEntityPullRadius = builder.defineInRange("planetaryDevastationEntityPullRadius", 64.0, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationPullForce = builder.defineInRange("planetaryDevastationPullForce", 0.35, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationPullDamage = builder.defineInRange("planetaryDevastationPullDamage", 150.0, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationPlanetRadius = builder.defineInRange("planetaryDevastationPlanetRadius", 10.0, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationDropSpeed = builder.defineInRange("planetaryDevastationDropSpeed", 1.35, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationExplosionRadius = builder.defineInRange("planetaryDevastationExplosionRadius", 58.0, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationImpactDamage = builder.defineInRange("planetaryDevastationImpactDamage", 3500.0, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationHealthDamageRatio = builder.defineInRange("planetaryDevastationHealthDamageRatio", 0.7, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationMaxImpactDamage = builder.defineInRange("planetaryDevastationMaxImpactDamage", 30000.0, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationMinFalloff = builder.defineInRange("planetaryDevastationMinFalloff", 0.2, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationImpactKnockback = builder.defineInRange("planetaryDevastationImpactKnockback", 3.0, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationImpactLift = builder.defineInRange("planetaryDevastationImpactLift", 1.2, 0.0, Double.MAX_VALUE);
         this.planetaryDevastationExplosionPower = builder.defineInRange("planetaryDevastationExplosionPower", 7.5, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.defineInRange("acquirementMastery", 0, 0, Integer.MAX_VALUE);
         this.maxMastery = builder.defineInRange("maxMastery", 6000, 0, Integer.MAX_VALUE);
         this.almightyPushMinChargeTicks = builder.defineInRange("almightyPushMinChargeTicks", 20, 0, Integer.MAX_VALUE);
         this.almightyPushMaxChargeTicks = builder.defineInRange("almightyPushMaxChargeTicks", 1200, 0, Integer.MAX_VALUE);
         this.almightyPushChargeParticleIntervalTicks = builder.defineInRange("almightyPushChargeParticleIntervalTicks", 5, 0, Integer.MAX_VALUE);
         this.almightyPushCooldownTicks = builder.defineInRange("almightyPushCooldownTicks", 30, 0, Integer.MAX_VALUE);
         this.almightyPushMaxCooldownTicks = builder.defineInRange("almightyPushMaxCooldownTicks", 200, 0, Integer.MAX_VALUE);
         this.almightyPushBurdenTicks = builder.defineInRange("almightyPushBurdenTicks", 160, 0, Integer.MAX_VALUE);
         this.almightyPushBurdenAmplifier = builder.defineInRange("almightyPushBurdenAmplifier", 2, 0, Integer.MAX_VALUE);
         this.almightyPushStaggerTicks = builder.defineInRange("almightyPushStaggerTicks", 80, 0, Integer.MAX_VALUE);
         this.almightyPushStaggerAmplifier = builder.defineInRange("almightyPushStaggerAmplifier", 2, 0, Integer.MAX_VALUE);
         this.almightyPushMinBlocks = builder.defineInRange("almightyPushMinBlocks", 24, 0, Integer.MAX_VALUE);
         this.almightyPushMaxBlocks = builder.defineInRange("almightyPushMaxBlocks", 700, 0, Integer.MAX_VALUE);
         this.almightyPushFakeBlockLifeTicks = builder.defineInRange("almightyPushFakeBlockLifeTicks", 120, 0, Integer.MAX_VALUE);
         this.almightyPushCraterMaxRadius = builder.defineInRange("almightyPushCraterMaxRadius", 48, 0, Integer.MAX_VALUE);
         this.almightyPushCraterMinDepth = builder.defineInRange("almightyPushCraterMinDepth", 3, 0, Integer.MAX_VALUE);
         this.almightyPushCraterMaxDepth = builder.defineInRange("almightyPushCraterMaxDepth", 18, 0, Integer.MAX_VALUE);
         this.failedTargetCooldownTicks = builder.defineInRange("failedTargetCooldownTicks", 2, 0, Integer.MAX_VALUE);
         this.passiveRefreshTicks = builder.defineInRange("passiveRefreshTicks", 40, 0, Integer.MAX_VALUE);
         this.heavenlyRuptureCooldownTicks = builder.defineInRange("heavenlyRuptureCooldownTicks", 30, 0, Integer.MAX_VALUE);
         this.heavenlyRuptureBurdenTicks = builder.defineInRange("heavenlyRuptureBurdenTicks", 120, 0, Integer.MAX_VALUE);
         this.heavenlyRuptureBurdenAmplifier = builder.defineInRange("heavenlyRuptureBurdenAmplifier", 2, 0, Integer.MAX_VALUE);
         this.heavenlyRuptureDropDelayTicks = builder.defineInRange("heavenlyRuptureDropDelayTicks", 35, 0, Integer.MAX_VALUE);
         this.pathDevaCooldownTicks = builder.defineInRange("pathDevaCooldownTicks", 2, 0, Integer.MAX_VALUE);
         this.pathAsuraShots = builder.defineInRange("pathAsuraShots", 12, 0, Integer.MAX_VALUE);
         this.pathAsuraCooldownTicks = builder.defineInRange("pathAsuraCooldownTicks", 2, 0, Integer.MAX_VALUE);
         this.pathHumanCooldownTicks = builder.defineInRange("pathHumanCooldownTicks", 100, 0, Integer.MAX_VALUE);
         this.pathHumanSoulDrainTicks = builder.defineInRange("pathHumanSoulDrainTicks", 60, 0, Integer.MAX_VALUE);
         this.pathHumanSoulDrainAmplifier = builder.defineInRange("pathHumanSoulDrainAmplifier", 2, 0, Integer.MAX_VALUE);
         this.pathAnimalSummons = builder.defineInRange("pathAnimalSummons", 3, 0, Integer.MAX_VALUE);
         this.pathAnimalSummonsMastered = builder.defineInRange("pathAnimalSummonsMastered", 6, 0, Integer.MAX_VALUE);
         this.pathAnimalDurationTicks = builder.defineInRange("pathAnimalDurationTicks", 1200, 0, Integer.MAX_VALUE);
         this.pathAnimalAuraAmplifier = builder.defineInRange("pathAnimalAuraAmplifier", 1, 0, Integer.MAX_VALUE);
         this.pathAnimalCooldownTicks = builder.defineInRange("pathAnimalCooldownTicks", 100, 0, Integer.MAX_VALUE);
         this.pathPretaDurationTicks = builder.defineInRange("pathPretaDurationTicks", 90, 0, Integer.MAX_VALUE);
         this.pathPretaProjectileAbsorbIntervalTicks = builder.defineInRange("pathPretaProjectileAbsorbIntervalTicks", 5, 0, Integer.MAX_VALUE);
         this.pathPretaBarrierAmplifier = builder.defineInRange("pathPretaBarrierAmplifier", 3, 0, Integer.MAX_VALUE);
         this.pathPretaMagicInterferenceAmplifier = builder.defineInRange("pathPretaMagicInterferenceAmplifier", 3, 0, Integer.MAX_VALUE);
         this.pathPretaCooldownTicks = builder.defineInRange("pathPretaCooldownTicks", 90, 0, Integer.MAX_VALUE);
         this.pathNarakaCooldownTicks = builder.defineInRange("pathNarakaCooldownTicks", 50, 0, Integer.MAX_VALUE);
         this.planetaryDevastationCooldownTicks = builder.defineInRange("planetaryDevastationCooldownTicks", 24, 0, Integer.MAX_VALUE);
         this.planetaryDevastationFlightTicks = builder.defineInRange("planetaryDevastationFlightTicks", 80, 0, Integer.MAX_VALUE);
         this.planetaryDevastationHoldTicks = builder.defineInRange("planetaryDevastationHoldTicks", 300, 0, Integer.MAX_VALUE);
         this.planetaryDevastationBlockScanIntervalTicks = builder.defineInRange("planetaryDevastationBlockScanIntervalTicks", 5, 0, Integer.MAX_VALUE);
         this.planetaryDevastationBlocksPerScan = builder.defineInRange("planetaryDevastationBlocksPerScan", 24, 0, Integer.MAX_VALUE);
         this.planetaryDevastationEntityPullIntervalTicks = builder.defineInRange("planetaryDevastationEntityPullIntervalTicks", 2, 0, Integer.MAX_VALUE);
         this.planetaryDevastationEntityDamageIntervalTicks = builder.defineInRange("planetaryDevastationEntityDamageIntervalTicks", 20, 0, Integer.MAX_VALUE);
         this.planetaryDevastationAssembleTicks = builder.defineInRange("planetaryDevastationAssembleTicks", 180, 0, Integer.MAX_VALUE);
         this.planetaryDevastationMaxBlocks = builder.defineInRange("planetaryDevastationMaxBlocks", 900, 0, Integer.MAX_VALUE);
         this.planetaryDevastationBlockPullRadius = builder.defineInRange("planetaryDevastationBlockPullRadius", 42, 0, Integer.MAX_VALUE);
         this.planetaryDevastationMaxDropTicks = builder.defineInRange("planetaryDevastationMaxDropTicks", 200, 0, Integer.MAX_VALUE);
         this.planetaryDevastationCraterRadius = builder.defineInRange("planetaryDevastationCraterRadius", 28, 0, Integer.MAX_VALUE);
         this.planetaryDevastationDebuffTicks = builder.defineInRange("planetaryDevastationDebuffTicks", 200, 0, Integer.MAX_VALUE);
         this.planetaryDevastationMovementAmplifier = builder.defineInRange("planetaryDevastationMovementAmplifier", 4, 0, Integer.MAX_VALUE);
         this.planetaryDevastationFragilityAmplifier = builder.defineInRange("planetaryDevastationFragilityAmplifier", 3, 0, Integer.MAX_VALUE);
         this.requiredSkillMustBeMastered = builder.define("requiredSkillMustBeMastered", true);
         this.almightyPushCraterDropBlocks = builder.define("almightyPushCraterDropBlocks", false);
         this.planetaryDevastationDropDestroyedBlocks = builder.define("planetaryDevastationDropDestroyedBlocks", false);
         this.requiredSkillId = builder.define("requiredSkillId", "tensura:oppressor");
      }
   }

   public static final class ParasiteConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final IntValue maxMastery;
      public final DoubleValue bondRange;
      public final DoubleValue bondCost;
      public final DoubleValue leaveCost;
      public final DoubleValue parasiteRaceHealth;
      public final DoubleValue fireSoundDamageMultiplier;
      public final IntValue maxRelationship;
      public final IntValue mergerExpelRelationship;
      public final IntValue antiMaxRelationship;
      public final IntValue takeoverRequiredPoints;
      public final IntValue relationshipPerSymbiosisUse;
      public final IntValue takeoverPerSymbiosisUse;
      public final IntValue passiveRelationshipPerDay;
      public final IntValue passiveTakeoverPerDay;
      public final IntValue relationshipPerSymbiosisInterval;
      public final IntValue symbiosisRelationshipIntervalTicks;
      public final IntValue symbiosisRefreshTicks;
      public final IntValue minecraftDayTicks;
      public final DoubleValue epMultiplierPerRelationship;
      public final DoubleValue attackPerRelationship;
      public final DoubleValue leaveEpPerRelationship;
      public final DoubleValue mergerExpelEpPerRelationship;
      public final DoubleValue hostileInvalidHostEpMultiplier;
      public final DoubleValue instantTakeoverHostEpRatio;
      public final IntValue antiDurationTicks;
      public final IntValue antiCooldownTicks;

      ParasiteConfig(Builder builder) {
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.defineInRange("acquirementMagiculeCost", 0.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.defineInRange("acquirementMastery", 0, -1, Integer.MAX_VALUE);
         this.maxMastery = builder.defineInRange("maxMastery", 1000, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.push("bond");
         this.bondRange = builder.defineInRange("bondRange", 5.0, 0.0, 128.0);
         this.bondCost = builder.defineInRange("bondCost", 0.0, 0.0, Double.MAX_VALUE);
         this.leaveCost = builder.defineInRange("leaveCost", 0.0, 0.0, Double.MAX_VALUE);
         this.leaveEpPerRelationship = builder.defineInRange("leaveEpPerRelationship", 1000.0, 0.0, Double.MAX_VALUE);
         this.mergerExpelEpPerRelationship = builder.defineInRange("mergerExpelEpPerRelationship", 2000.0, 0.0, Double.MAX_VALUE);
         this.hostileInvalidHostEpMultiplier = builder.defineInRange("hostileInvalidHostEpMultiplier", 2.0, 0.0, 1024.0);
         this.instantTakeoverHostEpRatio = builder.defineInRange("instantTakeoverHostEpRatio", 0.25, 0.0, 1024.0);
         builder.pop();
         builder.push("race");
         this.parasiteRaceHealth = builder.defineInRange("parasiteRaceHealth", 5.0, 1.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("weakness");
         this.fireSoundDamageMultiplier = builder.defineInRange("fireSoundDamageMultiplier", 2.0, 1.0, 1024.0);
         builder.pop();
         builder.push("relationship");
         this.maxRelationship = builder.defineInRange("maxRelationship", 200, 1, Integer.MAX_VALUE);
         this.mergerExpelRelationship = builder.defineInRange("mergerExpelRelationship", 100, 0, Integer.MAX_VALUE);
         this.antiMaxRelationship = builder.defineInRange("antiMaxRelationship", 150, 0, Integer.MAX_VALUE);
         this.relationshipPerSymbiosisUse = builder.defineInRange("relationshipPerSymbiosisUse", 3, 0, Integer.MAX_VALUE);
         this.passiveRelationshipPerDay = builder.defineInRange("passiveRelationshipPerDay", 5, 0, Integer.MAX_VALUE);
         this.relationshipPerSymbiosisInterval = builder.defineInRange("relationshipPerSymbiosisInterval", 1, 0, Integer.MAX_VALUE);
         this.symbiosisRelationshipIntervalTicks = builder.defineInRange("symbiosisRelationshipIntervalTicks", 200, 1, Integer.MAX_VALUE);
         this.symbiosisRefreshTicks = builder.defineInRange("symbiosisRefreshTicks", 220, 20, Integer.MAX_VALUE);
         this.minecraftDayTicks = builder.defineInRange("minecraftDayTicks", 24000, 20, Integer.MAX_VALUE);
         builder.pop();
         builder.push("takeover");
         this.takeoverRequiredPoints = builder.defineInRange("takeoverRequiredPoints", 100, 1, Integer.MAX_VALUE);
         this.takeoverPerSymbiosisUse = builder.defineInRange("takeoverPerSymbiosisUse", 6, 0, Integer.MAX_VALUE);
         this.passiveTakeoverPerDay = builder.defineInRange("passiveTakeoverPerDay", 10, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("form");
         this.epMultiplierPerRelationship = builder.defineInRange("epMultiplierPerRelationship", 0.02, 0.0, 1024.0);
         this.attackPerRelationship = builder.defineInRange("attackPerRelationship", 0.2, 0.0, Double.MAX_VALUE);
         this.antiDurationTicks = builder.defineInRange("antiDurationTicks", 1200, 1, Integer.MAX_VALUE);
         this.antiCooldownTicks = builder.defineInRange("antiCooldownTicks", 200, 0, Integer.MAX_VALUE);
         builder.pop();
      }
   }

   public static final class RonovaConfig {
      public final BooleanValue silentDeathEnabled;
      public final BooleanValue finalityBalanceTrueDeathEnabled;
      public final BooleanValue finalityBalanceInstantKill;
      public final DoubleValue finalityBalanceDamage;
      public final DoubleValue finalityBalanceRadius;
      public final BooleanValue finalityBalanceEraseBlocks;
      public final BooleanValue finalityBalanceDeleteDrops;
      public final IntValue finalityBalanceMaxBlocksPerDeath;
      public final BooleanValue learnShadowMotion;
      public final BooleanValue learnSpiritualNullification;
      public final BooleanValue learnAbnormalNullification;
      public final BooleanValue learnSpatialNullification;
      public final BooleanValue learnGravityNullification;
      public final DoubleValue healthPerKill;
      public final DoubleValue damagePerKill;
      public final IntValue killGrowthMaxKills;
      public final DoubleValue spiritualForceCost;
      public final IntValue spiritualForceCooldown;
      public final IntValue spiritualForceEffectTicks;
      public final IntValue spiritualForceAmplifier;
      public final BooleanValue spiritualForceFakeLeave;
      public final DoubleValue salvationLordCost;
      public final IntValue salvationLordCooldown;
      public final BooleanValue salvationEnabled;
      public final DoubleValue salvationRadius;
      public final IntValue salvationSoulTicks;
      public final DoubleValue salvationRescueReach;
      public final DoubleValue salvationReviveHealthRatio;
      public final DoubleValue allySacrificeCost;
      public final DoubleValue allySacrificeRadius;
      public final IntValue allySacrificeRequired;
      public final DoubleValue allySacrificeHealthDrainRatio;
      public final IntValue allySacrificeBuffTicks;
      public final IntValue allySacrificeAmplifier;
      public final IntValue allySacrificeExtraLives;
      public final IntValue allySacrificeMaxExtraLives;
      public final BooleanValue allySacrificeExtraLivesEnabled;
      public final DoubleValue extraLifeHealthRatio;
      public final IntValue extraLifeInvulnerabilityTicks;
      public final IntValue allySacrificeCooldown;
      public final DoubleValue deathFieldCost;
      public final DoubleValue deathFieldRadius;
      public final IntValue deathFieldDurationTicks;
      public final IntValue deathFieldPulseTicks;
      public final DoubleValue deathFieldPulseDamage;
      public final IntValue deathFieldEffectTicks;
      public final IntValue deathFieldBaseAmplifier;
      public final IntValue deathFieldMaxAmplifier;
      public final IntValue deathFieldAmplifierStepTicks;
      public final IntValue deathFieldSelfDamageTicks;
      public final DoubleValue deathFieldSelfDamageBaseRatio;
      public final DoubleValue deathFieldSelfDamageAmpRatio;
      public final IntValue deathFieldCooldown;
      public final BooleanValue deathFieldStealSkills;
      public final BooleanValue deathFieldStealOnAnyRonovaKill;
      public final BooleanValue deathFieldAddVictimEpToMax;
      public final DoubleValue deathFieldEpGainMultiplier;
      public final BooleanValue deathFieldDeadLandEnabled;
      public final IntValue deathFieldDeadLandRadius;
      public final IntValue deathFieldDeadLandBlocksPerTick;
      public final DoubleValue finalityCost;
      public final DoubleValue finalityRadius;
      public final BooleanValue finalityInstantKill;
      public final DoubleValue finalityDamage;
      public final BooleanValue finalityDiscardEntities;
      public final BooleanValue finalityEraseBlocks;
      public final IntValue finalityCooldown;

      public RonovaConfig(Builder builder) {
         builder.push("true_passives");
         this.silentDeathEnabled = builder.define("silentDeathEnabled", true);
         this.finalityBalanceTrueDeathEnabled = builder.define("finalityBalanceTrueDeathEnabled", true);
         this.finalityBalanceInstantKill = builder.define("finalityBalanceInstantKill", true);
         this.finalityBalanceDamage = builder.defineInRange("finalityBalanceDamage", 1000000.0, 1.0, Double.MAX_VALUE);
         this.finalityBalanceRadius = builder.defineInRange("finalityBalanceRadius", 50.0, 0.0, 256.0);
         this.finalityBalanceEraseBlocks = builder.define("finalityBalanceEraseBlocks", false);
         this.finalityBalanceDeleteDrops = builder.define("finalityBalanceDeleteDrops", false);
         this.finalityBalanceMaxBlocksPerDeath = builder.defineInRange("finalityBalanceMaxBlocksPerDeath", 4096, 0, 262144);
         builder.pop();
         builder.push("obtainment_rewards");
         this.learnShadowMotion = builder.define("learnShadowMotion", true);
         this.learnSpiritualNullification = builder.define("learnSpiritualNullification", true);
         this.learnAbnormalNullification = builder.define("learnAbnormalNullification", true);
         this.learnSpatialNullification = builder.define("learnSpatialNullification", true);
         this.learnGravityNullification = builder.define("learnGravityNullification", true);
         builder.pop();
         builder.push("kill_growth");
         this.healthPerKill = builder.defineInRange("healthPerKill", 0.05, 0.0, 1024.0);
         this.damagePerKill = builder.defineInRange("damagePerKill", 0.01, 0.0, 1024.0);
         this.killGrowthMaxKills = builder.defineInRange("killGrowthMaxKills", 100000, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("spiritual_force");
         this.spiritualForceCost = builder.defineInRange("spiritualForceCost", 0.0, 0.0, Double.MAX_VALUE);
         this.spiritualForceCooldown = builder.defineInRange("spiritualForceCooldown", 5, 0, Integer.MAX_VALUE);
         this.spiritualForceEffectTicks = builder.defineInRange("spiritualForceEffectTicks", 120, 1, 72000);
         this.spiritualForceAmplifier = builder.defineInRange("spiritualForceAmplifier", 4, 0, 255);
         this.spiritualForceFakeLeave = builder.define("spiritualForceFakeLeave", true);
         builder.pop();
         builder.push("salvation_lord");
         this.salvationLordCost = builder.defineInRange("salvationLordCost", 0.0, 0.0, Double.MAX_VALUE);
         this.salvationLordCooldown = builder.defineInRange("salvationLordCooldown", 1200, 0, Integer.MAX_VALUE);
         this.salvationEnabled = builder.define("salvationEnabled", true);
         this.salvationRadius = builder.defineInRange("salvationRadius", 50.0, 0.0, 256.0);
         this.salvationSoulTicks = builder.defineInRange("salvationSoulTicks", 6000, 20, 72000);
         this.salvationRescueReach = builder.defineInRange("salvationRescueReach", 32.0, 1.0, 256.0);
         this.salvationReviveHealthRatio = builder.defineInRange("salvationReviveHealthRatio", 0.35, 0.01, 1.0);
         builder.pop();
         builder.push("ally_sacrifice");
         this.allySacrificeCost = builder.defineInRange("allySacrificeCost", 20000.0, 0.0, Double.MAX_VALUE);
         this.allySacrificeRadius = builder.defineInRange("allySacrificeRadius", 50.0, 0.0, 256.0);
         this.allySacrificeRequired = builder.defineInRange("allySacrificeRequired", 5, 1, 64);
         this.allySacrificeHealthDrainRatio = builder.defineInRange("allySacrificeHealthDrainRatio", 0.5, 0.0, 1.0);
         this.allySacrificeBuffTicks = builder.defineInRange("allySacrificeBuffTicks", 2400, 1, 72000);
         this.allySacrificeAmplifier = builder.defineInRange("allySacrificeAmplifier", 14, 0, 255);
         this.allySacrificeExtraLives = builder.defineInRange("allySacrificeExtraLives", 3, 0, 64);
         this.allySacrificeMaxExtraLives = builder.defineInRange("allySacrificeMaxExtraLives", 3, 0, 64);
         this.allySacrificeExtraLivesEnabled = builder.define("allySacrificeExtraLivesEnabled", true);
         this.extraLifeHealthRatio = builder.defineInRange("extraLifeHealthRatio", 0.35, 0.0, 1.0);
         this.extraLifeInvulnerabilityTicks = builder.defineInRange("extraLifeInvulnerabilityTicks", 100, 0, 72000);
         this.allySacrificeCooldown = builder.defineInRange("allySacrificeCooldown", 2400, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("death_field");
         this.deathFieldCost = builder.defineInRange("deathFieldCost", 1000000.0, 0.0, Double.MAX_VALUE);
         this.deathFieldRadius = builder.defineInRange("deathFieldRadius", 30.0, 0.0, 256.0);
         this.deathFieldDurationTicks = builder.defineInRange("deathFieldDurationTicks", 3600, 1, 72000);
         this.deathFieldPulseTicks = builder.defineInRange("deathFieldPulseTicks", 20, 1, 200);
         this.deathFieldPulseDamage = builder.defineInRange("deathFieldPulseDamage", 80.0, 0.0, Float.MAX_VALUE);
         this.deathFieldEffectTicks = builder.defineInRange("deathFieldEffectTicks", 120, 1, 72000);
         this.deathFieldBaseAmplifier = builder.defineInRange("deathFieldBaseAmplifier", 5, 0, 255);
         this.deathFieldMaxAmplifier = builder.defineInRange("deathFieldMaxAmplifier", 20, 0, 255);
         this.deathFieldAmplifierStepTicks = builder.defineInRange("deathFieldAmplifierStepTicks", 400, 1, 72000);
         this.deathFieldSelfDamageTicks = builder.defineInRange("deathFieldSelfDamageTicks", 20, 1, 200);
         this.deathFieldSelfDamageBaseRatio = builder.defineInRange("deathFieldSelfDamageBaseRatio", 0.01, 0.0, 1.0);
         this.deathFieldSelfDamageAmpRatio = builder.defineInRange("deathFieldSelfDamageAmpRatio", 0.002, 0.0, 1.0);
         this.deathFieldCooldown = builder.defineInRange("deathFieldCooldown", 3600, 0, Integer.MAX_VALUE);
         this.deathFieldStealSkills = builder.define("deathFieldStealSkills", true);
         this.deathFieldStealOnAnyRonovaKill = builder.define("deathFieldStealOnAnyRonovaKill", false);
         this.deathFieldAddVictimEpToMax = builder.define("deathFieldAddVictimEpToMax", true);
         this.deathFieldEpGainMultiplier = builder.defineInRange("deathFieldEpGainMultiplier", 1.0, 0.0, 1024.0);
         builder.pop();
         builder.push("dead_land");
         this.deathFieldDeadLandEnabled = builder.define("deathFieldDeadLandEnabled", true);
         this.deathFieldDeadLandRadius = builder.defineInRange("deathFieldDeadLandRadius", 30, 0, 128);
         this.deathFieldDeadLandBlocksPerTick = builder.defineInRange("deathFieldDeadLandBlocksPerTick", 24, 0, 4096);
         builder.pop();
         builder.push("finality");
         this.finalityCost = builder.defineInRange("finalityCost", 350000.0, 0.0, Double.MAX_VALUE);
         this.finalityRadius = builder.defineInRange("finalityRadius", 80.0, 0.0, 256.0);
         this.finalityInstantKill = builder.define("finalityInstantKill", false);
         this.finalityDamage = builder.defineInRange("finalityDamage", 10000.0, 1.0, Double.MAX_VALUE);
         this.finalityDiscardEntities = builder.define("finalityDiscardEntities", false);
         this.finalityEraseBlocks = builder.define("finalityEraseBlocks", false);
         this.finalityCooldown = builder.defineInRange("finalityCooldown", 1000, 0, Integer.MAX_VALUE);
         builder.pop();
      }
   }

   public static final class SatellaConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final IntValue maxMastery;
      public final DoubleValue magiculeGain;
      public final DoubleValue magiculeGainMastered;
      public final DoubleValue magiculeRegen;
      public final DoubleValue magiculeRegenMastered;
      public final DoubleValue fearAuraRadius;
      public final DoubleValue fearMagiculeRatio;
      public final IntValue fearDurationTicks;
      public final IntValue auraRefreshIntervalTicks;
      public final DoubleValue copyChance;
      public final DoubleValue copyBasicChance;
      public final DoubleValue copyBasicChanceMastered;
      public final DoubleValue copyAuthorityChance;
      public final DoubleValue copyAuthorityChanceMastered;
      public final DoubleValue copyMasteryRatio;
      public final IntValue copyCooldownTicks;
      public final IntValue shadowTendrilsCost;
      public final DoubleValue shadowTendrilsRange;
      public final IntValue shadowTendrilsCount;
      public final IntValue shadowTendrilsCountMastered;
      public final DoubleValue shadowTendrilsPhysicalDamage;
      public final DoubleValue shadowTendrilsSpiritualDamage;
      public final IntValue shadowTendrilsCooldownTicks;
      public final IntValue shadowTendrilsVfxLifeTicks;
      public final IntValue devouringShadowsCost;
      public final IntValue devouringShadowsDurationTicks;
      public final IntValue devouringShadowsCooldownTicks;
      public final IntValue devouringShadowsDamageIntervalTicks;
      public final DoubleValue devouringShadowsPhysicalDamage;
      public final DoubleValue devouringShadowsPhysicalDamageMastered;
      public final DoubleValue devouringShadowsSpiritualDamage;
      public final DoubleValue devouringShadowsSpiritualDamageMastered;
      public final IntValue devouringShadowsSlowDurationTicks;

      SatellaConfig(Builder builder) {
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.defineInRange("acquirementMagiculeCost", 70000.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.defineInRange("acquirementMastery", 0, -1, Integer.MAX_VALUE);
         this.maxMastery = builder.defineInRange("maxMastery", 1000, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.push("passive");
         this.magiculeGain = builder.defineInRange("magiculeGain", 1.5, 0.0, 1024.0);
         this.magiculeGainMastered = builder.defineInRange("magiculeGainMastered", 2.0, 0.0, 1024.0);
         this.magiculeRegen = builder.defineInRange("magiculeRegen", 1.3, 0.0, 1024.0);
         this.magiculeRegenMastered = builder.defineInRange("magiculeRegenMastered", 1.5, 0.0, 1024.0);
         this.fearAuraRadius = builder.defineInRange("fearAuraRadius", 10.0, 0.0, 128.0);
         this.fearMagiculeRatio = builder.defineInRange("fearMagiculeRatio", 0.05, 0.0, 1.0);
         this.fearDurationTicks = builder.defineInRange("fearDurationTicks", 120, 1, 72000);
         this.auraRefreshIntervalTicks = builder.defineInRange("auraRefreshIntervalTicks", 80, 1, 72000);
         this.copyChance = builder.defineInRange("copyChance", 0.5, 0.0, 1.0);
         this.copyBasicChance = builder.defineInRange("copyBasicChance", 0.5, 0.0, 1.0);
         this.copyBasicChanceMastered = builder.defineInRange("copyBasicChanceMastered", 1.0, 0.0, 1.0);
         this.copyAuthorityChance = builder.defineInRange("copyAuthorityChance", 0.2, 0.0, 1.0);
         this.copyAuthorityChanceMastered = builder.defineInRange("copyAuthorityChanceMastered", 0.4, 0.0, 1.0);
         this.copyMasteryRatio = builder.defineInRange("copyMasteryRatio", 0.05, 0.0, 1.0);
         this.copyCooldownTicks = builder.defineInRange("copyCooldownTicks", 100, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("shadow_tendrils");
         this.shadowTendrilsCost = builder.defineInRange("shadowTendrilsCost", 35000, 0, Integer.MAX_VALUE);
         this.shadowTendrilsRange = builder.defineInRange("shadowTendrilsRange", 34.0, 1.0, 256.0);
         this.shadowTendrilsCount = builder.defineInRange("shadowTendrilsCount", 5, 1, 128);
         this.shadowTendrilsCountMastered = builder.defineInRange("shadowTendrilsCountMastered", 10, 1, 128);
         this.shadowTendrilsPhysicalDamage = builder.defineInRange("shadowTendrilsPhysicalDamage", 10.0, 0.0, Float.MAX_VALUE);
         this.shadowTendrilsSpiritualDamage = builder.defineInRange("shadowTendrilsSpiritualDamage", 10.0, 0.0, Float.MAX_VALUE);
         this.shadowTendrilsCooldownTicks = builder.defineInRange("shadowTendrilsCooldownTicks", 20, 0, Integer.MAX_VALUE);
         this.shadowTendrilsVfxLifeTicks = builder.defineInRange("shadowTendrilsVfxLifeTicks", 400, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.push("devouring_shadows");
         this.devouringShadowsCost = builder.defineInRange("devouringShadowsCost", 90000, 0, Integer.MAX_VALUE);
         this.devouringShadowsDurationTicks = builder.defineInRange("devouringShadowsDurationTicks", 1200, 1, Integer.MAX_VALUE);
         this.devouringShadowsCooldownTicks = builder.defineInRange("devouringShadowsCooldownTicks", 200, 0, Integer.MAX_VALUE);
         this.devouringShadowsDamageIntervalTicks = builder.defineInRange("devouringShadowsDamageIntervalTicks", 20, 1, 72000);
         this.devouringShadowsPhysicalDamage = builder.defineInRange("devouringShadowsPhysicalDamage", 60.0, 0.0, Float.MAX_VALUE);
         this.devouringShadowsPhysicalDamageMastered = builder.defineInRange("devouringShadowsPhysicalDamageMastered", 120.0, 0.0, Float.MAX_VALUE);
         this.devouringShadowsSpiritualDamage = builder.defineInRange("devouringShadowsSpiritualDamage", 60.0, 0.0, Float.MAX_VALUE);
         this.devouringShadowsSpiritualDamageMastered = builder.defineInRange("devouringShadowsSpiritualDamageMastered", 120.0, 0.0, Float.MAX_VALUE);
         this.devouringShadowsSlowDurationTicks = builder.defineInRange("devouringShadowsSlowDurationTicks", 60, 1, 72000);
         builder.pop();
      }
   }

   public static final class SchwiExMachina {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final IntValue maxMastery;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final IntValue autoShootingCost;
      public final IntValue lockCost;
      public final IntValue continuousFireCost;
      public final IntValue structuralAnalysisCost;
      public final IntValue finalCost;
      public final IntValue stackIntervalTicks;
      public final IntValue maxStacksBase;
      public final IntValue maxStacksMastered;
      public final DoubleValue damagePerStack;
      public final DoubleValue cooldownRatePerStack;
      public final IntValue autoCooldownTicks;
      public final IntValue lockCooldownTicks;
      public final IntValue scanCooldownTicks;
      public final IntValue finalCooldownTicks;
      public final IntValue lockDurationTicks;
      public final DoubleValue lockDamageBonus;
      public final DoubleValue targetRange;
      public final IntValue autoDurationTicks;
      public final IntValue autoMasteredBonusDurationTicks;
      public final IntValue autoShotIntervalTicks;
      public final IntValue fireDrainCheckTicks;
      public final DoubleValue fireMaxBonus;
      public final DoubleValue fireRampPerSecond;
      public final IntValue finalDurationTicks;
      public final IntValue overheatDurationTicks;
      public final DoubleValue autoHitDamage;
      public final DoubleValue fireBaseDamage;
      public final DoubleValue projectileSecondaryDamageMultiplier;
      public final IntValue projectileBurnTicks;
      public final DoubleValue autoProjectileSpeed;
      public final DoubleValue fireProjectileSpeed;
      public final DoubleValue blindFireProjectileSpeed;
      public final DoubleValue zeroMoveSpeedMultiplier;
      public final DoubleValue zeroAttackSpeedMultiplier;
      public final DoubleValue finalMoveSpeedMultiplier;
      public final DoubleValue finalAttackSpeedMultiplier;
      public final DoubleValue finalArmorBonus;
      public final DoubleValue finalToughnessBonus;
      public final DoubleValue finalAttackMultiplier;
      public final DoubleValue finalKnockbackResistanceBonus;
      public final DoubleValue predictDodgeChance;
      public final DoubleValue predictCritChance;
      public final DoubleValue analysisDamageTakenBonus;
      public final IntValue combatWindowTicks;
      public final IntValue buffRefreshTicks;
      public final IntValue zeroResistanceDurationTicks;
      public final IntValue finalShortBuffDurationTicks;
      public final IntValue finalNightVisionDurationTicks;
      public final IntValue cooldownApplyIntervalTicks;
      public final IntValue continuousFireIntervalStage0;
      public final IntValue continuousFireIntervalStage1;
      public final IntValue continuousFireIntervalStage2;
      public final IntValue continuousFireIntervalStage3;
      public final IntValue continuousFireIntervalStage4;
      public final IntValue continuousFireIntervalStage5;
      public final IntValue continuousFireFinalInterval;
      public final IntValue structuralAnalysisDurationTicks;
      public final IntValue lockGlowDurationTicks;
      public final IntValue lockMovementInterferenceDurationTicks;
      public final IntValue lockMovementInterferenceAmplifier;

      SchwiExMachina(Builder builder) {
         builder.comment("Obtainment settings for Schwi Ex Machina.");
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.comment("Magicule cost required to naturally acquire the skill.")
            .defineInRange("acquirementMagiculeCost", 350000.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.comment("Starting mastery value when acquired.").defineInRange("acquirementMastery", -1, -1, Integer.MAX_VALUE);
         this.maxMastery = builder.comment("Maximum mastery value for Schwi Ex Machina.").defineInRange("maxMastery", 2500, 1, Integer.MAX_VALUE);
         this.requiredSkillId = builder.comment("Required skill registry ID for obtainment. Empty string disables the required skill check.")
            .define("requiredSkillId", "tensura:mathematician");
         this.requiredSkillMustBeMastered = builder.comment("If true, the required skill must be mastered before Schwi Ex Machina can be acquired.")
            .define("requiredSkillMustBeMastered", true);
         builder.pop();
         builder.comment("Magicule costs for each mode.");
         builder.push("costs");
         this.autoShootingCost = builder.comment("Magicule cost for Auto Shooting.").defineInRange("autoShootingCost", 100, 0, Integer.MAX_VALUE);
         this.lockCost = builder.comment("Magicule cost for Lock.").defineInRange("lockCost", 100, 0, Integer.MAX_VALUE);
         this.continuousFireCost = builder.comment("Magicule cost for Continuous Fire checks.").defineInRange("continuousFireCost", 100, 0, Integer.MAX_VALUE);
         this.structuralAnalysisCost = builder.comment("Magicule cost for Structural Analysis.")
            .defineInRange("structuralAnalysisCost", 100, 0, Integer.MAX_VALUE);
         this.finalCost = builder.comment("Magicule cost for Final Mode.").defineInRange("finalCost", 100, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Calculation stack scaling.");
         builder.push("calculation_stacks");
         this.stackIntervalTicks = builder.comment("How often one calculation stack is gained while active.").defineInRange("stackIntervalTicks", 2, 1, 72000);
         this.maxStacksBase = builder.comment("Maximum calculation stacks before mastery.").defineInRange("maxStacksBase", 30, 0, 10000);
         this.maxStacksMastered = builder.comment("Maximum calculation stacks when mastered.").defineInRange("maxStacksMastered", 40, 0, 10000);
         this.damagePerStack = builder.comment("Damage multiplier gained per stack. 0.02 = 2 percent per stack.")
            .defineInRange("damagePerStack", 0.02, 0.0, 100.0);
         this.cooldownRatePerStack = builder.comment("Cooldown acceleration gained per stack.").defineInRange("cooldownRatePerStack", 0.1, 0.0, 100.0);
         builder.pop();
         builder.comment("Cooldowns. 20 ticks = 1 second.");
         builder.push("cooldowns");
         this.autoCooldownTicks = builder.comment("Cooldown after Auto Shooting.").defineInRange("autoCooldownTicks", 25, 0, Integer.MAX_VALUE);
         this.lockCooldownTicks = builder.comment("Cooldown after Lock.").defineInRange("lockCooldownTicks", 2, 0, Integer.MAX_VALUE);
         this.scanCooldownTicks = builder.comment("Cooldown after Structural Analysis.").defineInRange("scanCooldownTicks", 25, 0, Integer.MAX_VALUE);
         this.finalCooldownTicks = builder.comment("Cooldown after Final Mode.").defineInRange("finalCooldownTicks", 300, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Lock-on values.");
         builder.push("lock");
         this.lockDurationTicks = builder.comment("How long Lock stays on the target.").defineInRange("lockDurationTicks", 800, 1, Integer.MAX_VALUE);
         this.lockDamageBonus = builder.comment("Bonus damage against locked targets. 0.20 = 20 percent.").defineInRange("lockDamageBonus", 0.2, 0.0, 100.0);
         this.targetRange = builder.comment("Targeting range for Lock, Auto Shooting, Continuous Fire, and Structural Analysis.")
            .defineInRange("targetRange", 70.0, 1.0, 512.0);
         this.lockGlowDurationTicks = builder.comment("Glowing duration applied to locked targets.")
            .defineInRange("lockGlowDurationTicks", 200, 1, Integer.MAX_VALUE);
         this.lockMovementInterferenceDurationTicks = builder.comment("Movement Interference duration applied to locked targets.")
            .defineInRange("lockMovementInterferenceDurationTicks", 120, 1, Integer.MAX_VALUE);
         this.lockMovementInterferenceAmplifier = builder.comment("Movement Interference amplifier applied to locked targets. 0 means level 1.")
            .defineInRange("lockMovementInterferenceAmplifier", 0, 0, 255);
         builder.pop();
         builder.comment("Auto Shooting values.");
         builder.push("auto_shooting");
         this.autoDurationTicks = builder.comment("Auto Shooting duration before mastery.").defineInRange("autoDurationTicks", 240, 1, Integer.MAX_VALUE);
         this.autoMasteredBonusDurationTicks = builder.comment("Extra Auto Shooting duration when mastered.")
            .defineInRange("autoMasteredBonusDurationTicks", 120, 0, Integer.MAX_VALUE);
         this.autoShotIntervalTicks = builder.comment("Delay between Auto Shooting volleys.").defineInRange("autoShotIntervalTicks", 40, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Continuous Fire values.");
         builder.push("continuous_fire");
         this.fireDrainCheckTicks = builder.comment("How often Continuous Fire checks energy while held.")
            .defineInRange("fireDrainCheckTicks", 20, 1, Integer.MAX_VALUE);
         this.fireMaxBonus = builder.comment("Maximum ramp bonus outside Final Mode. 0.40 = 40 percent.").defineInRange("fireMaxBonus", 0.4, 0.0, 100.0);
         this.fireRampPerSecond = builder.comment("Damage ramp gained per second while holding Continuous Fire.")
            .defineInRange("fireRampPerSecond", 0.05, 0.0, 100.0);
         this.continuousFireIntervalStage0 = builder.comment("Shot interval before 2 held seconds.")
            .defineInRange("continuousFireIntervalStage0", 40, 1, Integer.MAX_VALUE);
         this.continuousFireIntervalStage1 = builder.comment("Shot interval after 2 held seconds.")
            .defineInRange("continuousFireIntervalStage1", 20, 1, Integer.MAX_VALUE);
         this.continuousFireIntervalStage2 = builder.comment("Shot interval after 4 held seconds.")
            .defineInRange("continuousFireIntervalStage2", 10, 1, Integer.MAX_VALUE);
         this.continuousFireIntervalStage3 = builder.comment("Shot interval after 6 held seconds.")
            .defineInRange("continuousFireIntervalStage3", 5, 1, Integer.MAX_VALUE);
         this.continuousFireIntervalStage4 = builder.comment("Shot interval after 8 held seconds.")
            .defineInRange("continuousFireIntervalStage4", 3, 1, Integer.MAX_VALUE);
         this.continuousFireIntervalStage5 = builder.comment("Shot interval after 10 held seconds.")
            .defineInRange("continuousFireIntervalStage5", 2, 1, Integer.MAX_VALUE);
         this.continuousFireFinalInterval = builder.comment("Shot interval while Final Mode is active.")
            .defineInRange("continuousFireFinalInterval", 1, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Final Mode values.");
         builder.push("final_mode");
         this.finalDurationTicks = builder.comment("Final Mode duration.").defineInRange("finalDurationTicks", 400, 1, Integer.MAX_VALUE);
         this.overheatDurationTicks = builder.comment("Overheat duration after Final Mode ends.")
            .defineInRange("overheatDurationTicks", 300, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Projectile damage and projectile behavior.");
         builder.push("projectiles");
         this.autoHitDamage = builder.comment("Damage of each Auto Shooting projectile.").defineInRange("autoHitDamage", 1000.0, 0.0, Float.MAX_VALUE);
         this.fireBaseDamage = builder.comment("Base damage of Continuous Fire projectiles before ramp.")
            .defineInRange("fireBaseDamage", 1000.0, 0.0, Float.MAX_VALUE);
         this.projectileSecondaryDamageMultiplier = builder.comment("Secondary damage multiplier for fired bolts. 0.35 = 35 percent secondary damage.")
            .defineInRange("projectileSecondaryDamageMultiplier", 0.35, 0.0, 100.0);
         this.projectileBurnTicks = builder.comment("Fire ticks applied by fired bolts.").defineInRange("projectileBurnTicks", 40, 0, Integer.MAX_VALUE);
         this.autoProjectileSpeed = builder.comment("Projectile speed for Auto Shooting.").defineInRange("autoProjectileSpeed", 1.7, 0.0, 128.0);
         this.fireProjectileSpeed = builder.comment("Projectile speed for aimed Continuous Fire.").defineInRange("fireProjectileSpeed", 2.0, 0.0, 128.0);
         this.blindFireProjectileSpeed = builder.comment("Projectile speed for Continuous Fire when no target is locked or aimed at.")
            .defineInRange("blindFireProjectileSpeed", 2.1, 0.0, 128.0);
         builder.pop();
         builder.comment("Zero Mode toggle attribute bonuses.");
         builder.push("zero_mode_attributes");
         this.zeroMoveSpeedMultiplier = builder.comment("Movement speed multiplier while toggled. 0.20 = 20 percent added total.")
            .defineInRange("zeroMoveSpeedMultiplier", 0.2, -1.0, 100.0);
         this.zeroAttackSpeedMultiplier = builder.comment("Attack speed multiplier while toggled. 0.20 = 20 percent added total.")
            .defineInRange("zeroAttackSpeedMultiplier", 0.2, -1.0, 100.0);
         builder.pop();
         builder.comment("Final Mode attribute bonuses.");
         builder.push("final_mode_attributes");
         this.finalMoveSpeedMultiplier = builder.comment("Movement speed multiplier in Final Mode.")
            .defineInRange("finalMoveSpeedMultiplier", 1.0, -1.0, 100.0);
         this.finalAttackSpeedMultiplier = builder.comment("Attack speed multiplier in Final Mode.")
            .defineInRange("finalAttackSpeedMultiplier", 0.8, -1.0, 100.0);
         this.finalArmorBonus = builder.comment("Flat armor bonus in Final Mode.").defineInRange("finalArmorBonus", 300.0, 0.0, 1000000.0);
         this.finalToughnessBonus = builder.comment("Flat armor toughness bonus in Final Mode.").defineInRange("finalToughnessBonus", 200.0, 0.0, 1000000.0);
         this.finalAttackMultiplier = builder.comment("Attack damage multiplier in Final Mode. 1.45 = 145 percent added total.")
            .defineInRange("finalAttackMultiplier", 1.45, -1.0, 100.0);
         this.finalKnockbackResistanceBonus = builder.comment("Flat knockback resistance bonus in Final Mode.")
            .defineInRange("finalKnockbackResistanceBonus", 0.35, 0.0, 100.0);
         builder.pop();
         builder.comment("Predictive combat values.");
         builder.push("prediction");
         this.predictDodgeChance = builder.comment("Chance to nullify direct melee/projectile damage. 0.30 = 30 percent.")
            .defineInRange("predictDodgeChance", 0.3, 0.0, 1.0);
         this.predictCritChance = builder.comment("Chance to force critical multiplier while above the health threshold. 0.90 = 90 percent.")
            .defineInRange("predictCritChance", 0.9, 0.0, 1.0);
         this.analysisDamageTakenBonus = builder.comment("Bonus damage against structurally analyzed targets. 0.60 = 60 percent.")
            .defineInRange("analysisDamageTakenBonus", 0.6, 0.0, 100.0);
         builder.pop();
         builder.comment("Timing and refresh values.");
         builder.push("timing");
         this.combatWindowTicks = builder.comment("How long calculation stacks keep building after combat interaction.")
            .defineInRange("combatWindowTicks", 1000, 1, Integer.MAX_VALUE);
         this.buffRefreshTicks = builder.comment("How often buffs refresh. Higher is better for performance.")
            .defineInRange("buffRefreshTicks", 20, 1, Integer.MAX_VALUE);
         this.zeroResistanceDurationTicks = builder.comment("Resistance effect duration while toggled.")
            .defineInRange("zeroResistanceDurationTicks", 60, 1, Integer.MAX_VALUE);
         this.finalShortBuffDurationTicks = builder.comment("Duration for short Final Mode refreshed buffs.")
            .defineInRange("finalShortBuffDurationTicks", 60, 1, Integer.MAX_VALUE);
         this.finalNightVisionDurationTicks = builder.comment("Night vision duration while in Final Mode.")
            .defineInRange("finalNightVisionDurationTicks", 280, 1, Integer.MAX_VALUE);
         this.cooldownApplyIntervalTicks = builder.comment("How often calculation stacks reduce cooldowns.")
            .defineInRange("cooldownApplyIntervalTicks", 5, 1, Integer.MAX_VALUE);
         this.structuralAnalysisDurationTicks = builder.comment("Structural Analysis mark duration.")
            .defineInRange("structuralAnalysisDurationTicks", 1200, 1, Integer.MAX_VALUE);
         builder.pop();
      }
   }

   public static final class TrueAuthorityOfGreedConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final IntValue maxMastery;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final IntValue holdArmTicks;
      public final IntValue penaltyDurationTicks;
      public final IntValue preventionFreeTicks;
      public final DoubleValue lionheartOvertimeHpPercentPerSecond;
      public final IntValue baseCooldownTicks;
      public final IntValue extraCooldownPerOverSecondTicks;
      public final IntValue heartRange;
      public final IntValue heartRangeMastered;
      public final BooleanValue chunkLoadHeart;
      public final IntValue heartChunkRefreshTicks;
      public final IntValue passiveRepairIntervalTicks;
      public final IntValue masteryHoldTick;
      public final IntValue indicatorIntervalTicks;
      public final DoubleValue lawDegradation;
      public final DoubleValue resistanceDegradation;
      public final DoubleValue resistanceDegradationMastered;
      public final DoubleValue spiritualDamageMultiplier;
      public final DoubleValue physicalBonusDamage;
      public final DoubleValue physicalRangeBonus;
      public final DoubleValue physicalRangeBonusMastered;
      public final DoubleValue physicalResistanceBypassLevel;
      public final DoubleValue physicalBarrierBypassLevel;

      TrueAuthorityOfGreedConfig(Builder builder) {
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.comment("Magicule cost required to naturally acquire True Authority of Greed.")
            .defineInRange("acquirementMagiculeCost", 1000000.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.comment("Starting mastery value when acquired.").defineInRange("acquirementMastery", 0, -1, Integer.MAX_VALUE);
         this.maxMastery = builder.comment("Maximum mastery value.").defineInRange("maxMastery", 2000, 1, Integer.MAX_VALUE);
         this.requiredSkillId = builder.comment("Required skill registry ID. This should be the old Unique Authority of Greed.")
            .define("requiredSkillId", "tensuramoreskills:authority_of_greed");
         this.requiredSkillMustBeMastered = builder.comment("If true, required skill must be mastered.").define("requiredSkillMustBeMastered", true);
         builder.pop();
         builder.push("lionheart");
         this.holdArmTicks = builder.comment("Ticks held before Lionheart activates. 20 ticks = 1 second.").defineInRange("holdArmTicks", 20, 0, 72000);
         this.preventionFreeTicks = builder.comment("Safe Lionheart time. Default 200 ticks = 10 seconds.").defineInRange("preventionFreeTicks", 200, 0, 72000);
         this.lionheartOvertimeHpPercentPerSecond = builder.comment("Self severance HP damage per second after the safe window. 0.10 = 10% max HP per second.")
            .defineInRange("lionheartOvertimeHpPercentPerSecond", 0.1, 0.0, 1.0);
         this.baseCooldownTicks = builder.comment("Base Lionheart cooldown after release. Default 200 ticks = 10 seconds.")
            .defineInRange("baseCooldownTicks", 10, 0, 72000);
         this.extraCooldownPerOverSecondTicks = builder.comment("Extra cooldown per second held over the safe window.")
            .defineInRange("extraCooldownPerOverSecondTicks", 10, 0, 72000);
         this.penaltyDurationTicks = builder.comment("How long Lionheart overtime HP penalty lasts after being applied. Default 6000 ticks = 5 minutes.")
            .defineInRange("penaltyDurationTicks", 6000, 1, 72000);
         builder.pop();
         builder.push("little_king");
         this.heartRange = builder.comment("Maximum Little King pseudo-heart range before mastery.").defineInRange("heartRange", 150, 1, 2048);
         this.heartRangeMastered = builder.comment("Maximum Little King pseudo-heart range after mastery.").defineInRange("heartRangeMastered", 300, 1, 4096);
         this.chunkLoadHeart = builder.comment("If true, force-loads only the pseudo-heart entity's current chunk while linked.")
            .define("chunkLoadHeart", true);
         this.heartChunkRefreshTicks = builder.comment("How often the linked heart chunk is refreshed. Higher is less laggy.")
            .defineInRange("heartChunkRefreshTicks", 100, 20, 72000);
         builder.pop();
         builder.push("passive");
         this.passiveRepairIntervalTicks = builder.comment("How often severance and Cook damage are cleared. Default 100 ticks = 5 seconds.")
            .defineInRange("passiveRepairIntervalTicks", 100, 1, 72000);
         this.masteryHoldTick = builder.comment("How often Lionheart and Little King grant mastery while active.")
            .defineInRange("masteryHoldTick", 20, 1, 72000);
         this.indicatorIntervalTicks = builder.comment("How often Little King displays linked target range info.")
            .defineInRange("indicatorIntervalTicks", 20, 1, 72000);
         this.lawDegradation = builder.comment("LAW_DEGRADATION granted by True Authority of Greed.").defineInRange("lawDegradation", 1.0, 0.0, 100000.0);
         this.resistanceDegradation = builder.comment("RESISTANCE_DEGRADATION granted before mastery.")
            .defineInRange("resistanceDegradation", 1.0, 0.0, 100000.0);
         this.resistanceDegradationMastered = builder.comment("RESISTANCE_DEGRADATION granted after mastery.")
            .defineInRange("resistanceDegradationMastered", 2.0, 0.0, 100000.0);
         this.spiritualDamageMultiplier = builder.comment(
               "Incoming spiritual health damage multiplier while Little King/Lionheart is active. 0.5 = 2x SHP damage reduction. Normal HP damage is cancelled separately."
            )
            .defineInRange("spiritualDamageMultiplier", 0.5, 0.0, 1.0);
         this.physicalBonusDamage = builder.comment("Flat physical M1 damage bonus while Little King/Lionheart is active.")
            .defineInRange("physicalBonusDamage", 150.0, 0.0, 1000000.0);
         this.physicalRangeBonus = builder.comment("Entity/block interaction range bonus while active before mastery.")
            .defineInRange("physicalRangeBonus", 4.0, 0.0, 128.0);
         this.physicalRangeBonusMastered = builder.comment("Entity/block interaction range bonus while active after mastery.")
            .defineInRange("physicalRangeBonusMastered", 4.0, 0.0, 128.0);
         this.physicalResistanceBypassLevel = builder.comment("Resistance bypass level applied to physical attacks while active.")
            .defineInRange("physicalResistanceBypassLevel", 999.0, 0.0, 999.0);
         this.physicalBarrierBypassLevel = builder.comment("Barrier bypass level applied to physical attacks while active.")
            .defineInRange("physicalBarrierBypassLevel", 999.0, 0.0, 999.0);
         builder.pop();
      }
   }

   public static final class WitchOfEnvySatellaConfig {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final IntValue maxMastery;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final DoubleValue magiculeGain;
      public final DoubleValue magiculeGainMastered;
      public final DoubleValue magiculeRegen;
      public final DoubleValue magiculeRegenMastered;
      public final DoubleValue physicalDamageTakenMultiplier;
      public final IntValue revives;
      public final IntValue revivesMastered;
      public final IntValue reviveWindowTicks;
      public final DoubleValue reviveMagiculeCostRatio;
      public final DoubleValue reviveMagiculeCostRatioMastered;
      public final DoubleValue reviveHealthRatio;
      public final DoubleValue reviveSpiritualHealthRatio;
      public final DoubleValue reviveAuraRatio;
      public final DoubleValue reviveMagiculeRatio;
      public final DoubleValue fearMagiculeRatio;
      public final DoubleValue fearAuraRadius;
      public final IntValue fearDurationTicks;
      public final IntValue fearAmplifier;
      public final IntValue autoCheckpointIntervalTicks;
      public final DoubleValue copyChance;
      public final DoubleValue copyChanceMastered;
      public final DoubleValue basicCopyChance;
      public final DoubleValue basicCopyChanceMastered;
      public final DoubleValue uniqueCopyChance;
      public final DoubleValue uniqueCopyChanceMastered;
      public final DoubleValue copyMasteryRatio;
      public final IntValue copyCooldownTicks;
      public final DoubleValue shadowTendrilsCost;
      public final DoubleValue shadowHandCost;
      public final DoubleValue shadowHandsCost;
      public final DoubleValue authorityOfEnvyCost;
      public final DoubleValue checkpointCost;
      public final DoubleValue devouringShadowsCost;
      public final IntValue shadowTendrilsCooldownTicks;
      public final IntValue shadowHandCooldownTicks;
      public final IntValue shadowHandsCooldownTicks;
      public final IntValue authorityOfEnvyCooldownTicks;
      public final IntValue checkpointCooldownTicks;
      public final IntValue devouringShadowsCooldownTicks;
      public final DoubleValue shadowTendrilsRange;
      public final IntValue shadowTendrilsCount;
      public final IntValue shadowTendrilsCountMastered;
      public final DoubleValue shadowTendrilsPhysicalDamage;
      public final DoubleValue shadowTendrilsSpiritualDamage;
      public final IntValue shadowTendrilsDurationTicks;
      public final DoubleValue shadowHandRange;
      public final DoubleValue shadowHandBreakRange;
      public final DoubleValue shadowHandPhysicalDamage;
      public final DoubleValue shadowHandPhysicalDamageMastered;
      public final DoubleValue shadowHandSpiritualDamage;
      public final DoubleValue shadowHandSpiritualDamageMastered;
      public final IntValue shadowHandSlowTicks;
      public final IntValue shadowHandSlowAmplifier;
      public final DoubleValue shadowHandsRange;
      public final IntValue shadowHandsCount;
      public final IntValue shadowHandsCountMastered;
      public final IntValue shadowHandsDurationTicks;
      public final DoubleValue shadowHandsPhysicalDamage;
      public final DoubleValue shadowHandsPhysicalDamageMastered;
      public final DoubleValue shadowHandsSpiritualDamage;
      public final DoubleValue shadowHandsSpiritualDamageMastered;
      public final IntValue shadowHandsSlowTicks;
      public final IntValue shadowHandsSlowAmplifier;
      public final IntValue shadowHandsSlowAmplifierMastered;
      public final DoubleValue authorityRange;
      public final IntValue devouringShadowsDurationTicks;
      public final IntValue devouringShadowsDamageIntervalTicks;
      public final DoubleValue devouringShadowsPhysicalDamage;
      public final DoubleValue devouringShadowsPhysicalDamageMastered;
      public final DoubleValue devouringShadowsSpiritualDamage;
      public final DoubleValue devouringShadowsSpiritualDamageMastered;
      public final IntValue devouringShadowsSlowDurationTicks;

      WitchOfEnvySatellaConfig(Builder builder) {
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.defineInRange("acquirementMagiculeCost", 1000000.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.defineInRange("acquirementMastery", 0, -1, Integer.MAX_VALUE);
         this.maxMastery = builder.defineInRange("maxMastery", 3000, 1, Integer.MAX_VALUE);
         this.requiredSkillId = builder.define("requiredSkillId", "tensuramoreskills:satella");
         this.requiredSkillMustBeMastered = builder.define("requiredSkillMustBeMastered", true);
         builder.pop();
         builder.push("passives");
         this.magiculeGain = builder.defineInRange("magiculeGain", 2.0, 0.0, 1000.0);
         this.magiculeGainMastered = builder.defineInRange("magiculeGainMastered", 2.5, 0.0, 1000.0);
         this.magiculeRegen = builder.defineInRange("magiculeRegen", 1.5, 0.0, 1000.0);
         this.magiculeRegenMastered = builder.defineInRange("magiculeRegenMastered", 2.0, 0.0, 1000.0);
         this.physicalDamageTakenMultiplier = builder.defineInRange("physicalDamageTakenMultiplier", 0.5, 0.0, 100.0);
         this.fearMagiculeRatio = builder.defineInRange("fearMagiculeRatio", 0.3, 0.0, 1.0);
         this.fearAuraRadius = builder.defineInRange("fearAuraRadius", 18.0, 0.0, 256.0);
         this.fearDurationTicks = builder.defineInRange("fearDurationTicks", 120, 1, Integer.MAX_VALUE);
         this.fearAmplifier = builder.defineInRange("fearAmplifier", 2, 0, 255);
         this.autoCheckpointIntervalTicks = builder.defineInRange("autoCheckpointIntervalTicks", 6000, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.push("revive");
         this.revives = builder.defineInRange("revives", 5, 0, Integer.MAX_VALUE);
         this.revivesMastered = builder.defineInRange("revivesMastered", 10, 0, Integer.MAX_VALUE);
         this.reviveWindowTicks = builder.defineInRange("reviveWindowTicks", 24000, 1, Integer.MAX_VALUE);
         this.reviveMagiculeCostRatio = builder.defineInRange("reviveMagiculeCostRatio", 0.19, 0.0, 1.0);
         this.reviveMagiculeCostRatioMastered = builder.defineInRange("reviveMagiculeCostRatioMastered", 0.09, 0.0, 1.0);
         this.reviveHealthRatio = builder.defineInRange("reviveHealthRatio", 0.5, 0.0, 1.0);
         this.reviveSpiritualHealthRatio = builder.defineInRange("reviveSpiritualHealthRatio", 0.5, 0.0, 1.0);
         this.reviveAuraRatio = builder.defineInRange("reviveAuraRatio", 0.5, 0.0, 1.0);
         this.reviveMagiculeRatio = builder.defineInRange("reviveMagiculeRatio", 0.5, 0.0, 1.0);
         builder.pop();
         builder.push("copy");
         this.copyChance = builder.defineInRange("copyChance", 0.25, 0.0, 1.0);
         this.copyChanceMastered = builder.defineInRange("copyChanceMastered", 0.5, 0.0, 1.0);
         this.basicCopyChance = builder.defineInRange("basicCopyChance", 1.0, 0.0, 1.0);
         this.basicCopyChanceMastered = builder.defineInRange("basicCopyChanceMastered", 1.0, 0.0, 1.0);
         this.uniqueCopyChance = builder.defineInRange("uniqueCopyChance", 0.25, 0.0, 1.0);
         this.uniqueCopyChanceMastered = builder.defineInRange("uniqueCopyChanceMastered", 0.5, 0.0, 1.0);
         this.copyMasteryRatio = builder.defineInRange("copyMasteryRatio", 0.15, 0.0, 1.0);
         this.copyCooldownTicks = builder.defineInRange("copyCooldownTicks", 60, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("costs");
         this.shadowTendrilsCost = builder.defineInRange("shadowTendrilsCost", 5000.0, 0.0, Double.MAX_VALUE);
         this.shadowHandCost = builder.defineInRange("shadowHandCost", 7000.0, 0.0, Double.MAX_VALUE);
         this.shadowHandsCost = builder.defineInRange("shadowHandsCost", 12000.0, 0.0, Double.MAX_VALUE);
         this.authorityOfEnvyCost = builder.defineInRange("authorityOfEnvyCost", 20000.0, 0.0, Double.MAX_VALUE);
         this.checkpointCost = builder.defineInRange("checkpointCost", 8000.0, 0.0, Double.MAX_VALUE);
         this.devouringShadowsCost = builder.defineInRange("devouringShadowsCost", 90000.0, 0.0, Double.MAX_VALUE);
         builder.pop();
         builder.push("cooldowns");
         this.shadowTendrilsCooldownTicks = builder.defineInRange("shadowTendrilsCooldownTicks", 30, 0, Integer.MAX_VALUE);
         this.shadowHandCooldownTicks = builder.defineInRange("shadowHandCooldownTicks", 30, 0, Integer.MAX_VALUE);
         this.shadowHandsCooldownTicks = builder.defineInRange("shadowHandsCooldownTicks", 30, 0, Integer.MAX_VALUE);
         this.authorityOfEnvyCooldownTicks = builder.defineInRange("authorityOfEnvyCooldownTicks", 5, 0, Integer.MAX_VALUE);
         this.checkpointCooldownTicks = builder.defineInRange("checkpointCooldownTicks", 40, 0, Integer.MAX_VALUE);
         this.devouringShadowsCooldownTicks = builder.defineInRange("devouringShadowsCooldownTicks", 20, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.push("shadow_tendrils");
         this.shadowTendrilsRange = builder.defineInRange("shadowTendrilsRange", 64.0, 1.0, 512.0);
         this.shadowTendrilsCount = builder.defineInRange("shadowTendrilsCount", 15, 1, 256);
         this.shadowTendrilsCountMastered = builder.defineInRange("shadowTendrilsCountMastered", 30, 1, 256);
         this.shadowTendrilsPhysicalDamage = builder.defineInRange("shadowTendrilsPhysicalDamage", 20.0, 0.0, Float.MAX_VALUE);
         this.shadowTendrilsSpiritualDamage = builder.defineInRange("shadowTendrilsSpiritualDamage", 25.0, 0.0, Float.MAX_VALUE);
         this.shadowTendrilsDurationTicks = builder.defineInRange("shadowTendrilsDurationTicks", 400, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.push("shadow_hand");
         this.shadowHandRange = builder.defineInRange("shadowHandRange", 20.0, 1.0, 512.0);
         this.shadowHandBreakRange = builder.defineInRange("shadowHandBreakRange", 28.0, 1.0, 512.0);
         this.shadowHandPhysicalDamage = builder.defineInRange("shadowHandPhysicalDamage", 200.0, 0.0, Float.MAX_VALUE);
         this.shadowHandPhysicalDamageMastered = builder.defineInRange("shadowHandPhysicalDamageMastered", 300.0, 0.0, Float.MAX_VALUE);
         this.shadowHandSpiritualDamage = builder.defineInRange("shadowHandSpiritualDamage", 350.0, 0.0, Float.MAX_VALUE);
         this.shadowHandSpiritualDamageMastered = builder.defineInRange("shadowHandSpiritualDamageMastered", 500.0, 0.0, Float.MAX_VALUE);
         this.shadowHandSlowTicks = builder.defineInRange("shadowHandSlowTicks", 40, 1, Integer.MAX_VALUE);
         this.shadowHandSlowAmplifier = builder.defineInRange("shadowHandSlowAmplifier", 2, 0, 255);
         builder.pop();
         builder.push("shadow_hands");
         this.shadowHandsRange = builder.defineInRange("shadowHandsRange", 48.0, 1.0, 512.0);
         this.shadowHandsCount = builder.defineInRange("shadowHandsCount", 15, 1, 256);
         this.shadowHandsCountMastered = builder.defineInRange("shadowHandsCountMastered", 20, 1, 256);
         this.shadowHandsDurationTicks = builder.defineInRange("shadowHandsDurationTicks", 300, 1, Integer.MAX_VALUE);
         this.shadowHandsPhysicalDamage = builder.defineInRange("shadowHandsPhysicalDamage", 100.0, 0.0, Float.MAX_VALUE);
         this.shadowHandsPhysicalDamageMastered = builder.defineInRange("shadowHandsPhysicalDamageMastered", 200.0, 0.0, Float.MAX_VALUE);
         this.shadowHandsSpiritualDamage = builder.defineInRange("shadowHandsSpiritualDamage", 100.0, 0.0, Float.MAX_VALUE);
         this.shadowHandsSpiritualDamageMastered = builder.defineInRange("shadowHandsSpiritualDamageMastered", 150.0, 0.0, Float.MAX_VALUE);
         this.shadowHandsSlowTicks = builder.defineInRange("shadowHandsSlowTicks", 40, 1, Integer.MAX_VALUE);
         this.shadowHandsSlowAmplifier = builder.defineInRange("shadowHandsSlowAmplifier", 1, 0, 255);
         this.shadowHandsSlowAmplifierMastered = builder.defineInRange("shadowHandsSlowAmplifierMastered", 2, 0, 255);
         builder.pop();
         builder.push("authority_of_envy");
         this.authorityRange = builder.defineInRange("authorityRange", 40.0, 1.0, 512.0);
         builder.pop();
         builder.push("devouring_shadows");
         this.devouringShadowsDurationTicks = builder.defineInRange("devouringShadowsDurationTicks", 1200, 1, Integer.MAX_VALUE);
         this.devouringShadowsDamageIntervalTicks = builder.defineInRange("devouringShadowsDamageIntervalTicks", 20, 1, 72000);
         this.devouringShadowsPhysicalDamage = builder.defineInRange("devouringShadowsPhysicalDamage", 60.0, 0.0, Float.MAX_VALUE);
         this.devouringShadowsPhysicalDamageMastered = builder.defineInRange("devouringShadowsPhysicalDamageMastered", 120.0, 0.0, Float.MAX_VALUE);
         this.devouringShadowsSpiritualDamage = builder.defineInRange("devouringShadowsSpiritualDamage", 60.0, 0.0, Float.MAX_VALUE);
         this.devouringShadowsSpiritualDamageMastered = builder.defineInRange("devouringShadowsSpiritualDamageMastered", 120.0, 0.0, Float.MAX_VALUE);
         this.devouringShadowsSlowDurationTicks = builder.defineInRange("devouringShadowsSlowDurationTicks", 60, 1, 72000);
         builder.pop();
      }
   }

   public static final class WitchOfVainglory {
      public final DoubleValue acquirementMagiculeCost;
      public final IntValue acquirementMastery;
      public final ConfigValue<String> requiredSkillId;
      public final BooleanValue requiredSkillMustBeMastered;
      public final DoubleValue realityAuraRadius;
      public final IntValue auraEffectDurationTicks;
      public final IntValue auraEffectRefreshIntervalTicks;
      public final IntValue realityChangeCost;
      public final IntValue deletedTimelineCost;
      public final IntValue falsePerceptionCost;
      public final IntValue neverHereCost;
      public final IntValue realityChangeCooldownTicks;
      public final IntValue deletedTimelineReadyCooldownTicks;
      public final IntValue deletedTimelineTriggeredCooldownTicks;
      public final IntValue falsePerceptionCooldownTicks;
      public final IntValue neverHereCooldownTicks;
      public final IntValue rewrittenCooldownTicks;
      public final IntValue realityCoatDurationTicks;
      public final IntValue counterDurationTicks;
      public final IntValue falsePerceptionDurationTicks;
      public final IntValue falsePerceptionRadius;
      public final IntValue neverHereRadius;
      public final DoubleValue realityChangeBurstRadius;
      public final DoubleValue realityChangeProjectileClearRadius;
      public final DoubleValue realityChangeKnockback;
      public final IntValue realityChangeConfusionTicks;
      public final IntValue realityChangeConfusionAmplifier;
      public final IntValue realityCoatResistanceAmplifier;
      public final IntValue realityCoatStrengthAmplifier;
      public final IntValue realityCoatSpeedAmplifier;
      public final IntValue realityCoatRegenerationAmplifier;
      public final IntValue realityCoatEffectDurationTicks;
      public final IntValue realityCoatEffectRefreshIntervalTicks;
      public final IntValue counterResistanceAmplifier;
      public final DoubleValue counterReflectMultiplier;
      public final DoubleValue counterReflectMinimumDamage;
      public final DoubleValue projectileReverseMinimumSpeed;
      public final DoubleValue projectileReverseFallbackSpeed;
      public final DoubleValue falsePerceptionTeleportSpread;
      public final DoubleValue falsePerceptionYawRotation;
      public final IntValue falsePerceptionBlindnessAmplifier;
      public final IntValue falsePerceptionConfusionAmplifier;
      public final IntValue falsePerceptionMovementInterferenceAmplifier;
      public final IntValue maxRevivesPerDay;
      public final DoubleValue reviveHealthRatio;
      public final DoubleValue reviveMinimumHealth;
      public final IntValue reviveInvulnerabilityTicks;
      public final IntValue reviveResistanceTicks;
      public final IntValue reviveResistanceAmplifier;
      public final IntValue reviveRegenerationTicks;
      public final IntValue reviveRegenerationAmplifier;
      public final IntValue reviveHakiCoatTicks;
      public final IntValue reviveHakiCoatAmplifier;
      public final DoubleValue reviveSpiritualHealthRatio;
      public final DoubleValue reviveAuraRatio;
      public final DoubleValue reviveMagiculeRatio;

      WitchOfVainglory(Builder builder) {
         builder.comment("Obtainment settings for Witch of Vainglory.");
         builder.push("obtainment");
         this.acquirementMagiculeCost = builder.comment("Magicule cost required to naturally acquire the skill. Set to 0 for no MP cost.")
            .defineInRange("acquirementMagiculeCost", 300000.0, 0.0, Double.MAX_VALUE);
         this.acquirementMastery = builder.comment("Starting mastery value when acquired. 0 means no starting mastery.")
            .defineInRange("acquirementMastery", 0, -1, Integer.MAX_VALUE);
         this.requiredSkillId = builder.comment("Required skill registry ID for obtainment. Empty string disables the required skill check.")
            .define("requiredSkillId", "tensura:tuner");
         this.requiredSkillMustBeMastered = builder.comment("If true, the required skill must be mastered before Witch of Vainglory can be acquired.")
            .define("requiredSkillMustBeMastered", true);
         builder.pop();
         builder.comment("General passive and toggle settings.");
         builder.push("general");
         this.realityAuraRadius = builder.comment("Radius of the toggled reality aura around the user.").defineInRange("realityAuraRadius", 4.0, 0.0, 128.0);
         this.auraEffectDurationTicks = builder.comment("Duration applied to enemies hit by the toggled aura. 20 ticks = 1 second.")
            .defineInRange("auraEffectDurationTicks", 100, 1, 72000);
         this.auraEffectRefreshIntervalTicks = builder.comment(
               "How often the toggled aura reapplies its effect. Higher is better for performance. 80 ticks = 4 seconds."
            )
            .defineInRange("auraEffectRefreshIntervalTicks", 80, 1, 72000);
         builder.pop();
         builder.comment("Magicule costs for each active mode.");
         builder.push("costs");
         this.realityChangeCost = builder.comment("Magicule cost for Reality Change.").defineInRange("realityChangeCost", 60000, 0, Integer.MAX_VALUE);
         this.deletedTimelineCost = builder.comment("Magicule cost for Deleted Timeline.").defineInRange("deletedTimelineCost", 40000, 0, Integer.MAX_VALUE);
         this.falsePerceptionCost = builder.comment("Magicule cost for False Perception.").defineInRange("falsePerceptionCost", 50000, 0, Integer.MAX_VALUE);
         this.neverHereCost = builder.comment("Magicule cost for You Were Never Here.").defineInRange("neverHereCost", 70000, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Cooldowns for each mode. 20 ticks = 1 second.");
         builder.push("cooldowns");
         this.realityChangeCooldownTicks = builder.comment("Cooldown after casting Reality Change.")
            .defineInRange("realityChangeCooldownTicks", 120, 0, Integer.MAX_VALUE);
         this.deletedTimelineReadyCooldownTicks = builder.comment("Small cooldown after preparing Deleted Timeline before it reflects damage.")
            .defineInRange("deletedTimelineReadyCooldownTicks", 20, 0, Integer.MAX_VALUE);
         this.deletedTimelineTriggeredCooldownTicks = builder.comment("Cooldown after Deleted Timeline successfully reflects damage or reverses a projectile.")
            .defineInRange("deletedTimelineTriggeredCooldownTicks", 160, 0, Integer.MAX_VALUE);
         this.falsePerceptionCooldownTicks = builder.comment("Cooldown after casting False Perception.")
            .defineInRange("falsePerceptionCooldownTicks", 120, 0, Integer.MAX_VALUE);
         this.neverHereCooldownTicks = builder.comment("Cooldown after casting You Were Never Here.")
            .defineInRange("neverHereCooldownTicks", 240, 0, Integer.MAX_VALUE);
         this.rewrittenCooldownTicks = builder.comment("Cooldown applied to all modes after the death rewrite revive triggers.")
            .defineInRange("rewrittenCooldownTicks", 1800, 0, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Core duration values. 20 ticks = 1 second.");
         builder.push("durations");
         this.realityCoatDurationTicks = builder.comment("Duration of Reality Change's self-buff state.")
            .defineInRange("realityCoatDurationTicks", 200, 1, Integer.MAX_VALUE);
         this.counterDurationTicks = builder.comment("Duration of Deleted Timeline's counter window.")
            .defineInRange("counterDurationTicks", 60, 1, Integer.MAX_VALUE);
         this.falsePerceptionDurationTicks = builder.comment("Duration of False Perception effects.")
            .defineInRange("falsePerceptionDurationTicks", 160, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Area and range values.");
         builder.push("ranges");
         this.falsePerceptionRadius = builder.comment("Radius of False Perception around the caster.").defineInRange("falsePerceptionRadius", 16, 0, 128);
         this.neverHereRadius = builder.comment("Radius of You Were Never Here around the caster.").defineInRange("neverHereRadius", 24, 0, 128);
         this.realityChangeBurstRadius = builder.comment("Radius of the Reality Change burst knockback and confusion.")
            .defineInRange("realityChangeBurstRadius", 5.0, 0.0, 128.0);
         this.realityChangeProjectileClearRadius = builder.comment("Radius where Reality Change deletes nearby projectiles.")
            .defineInRange("realityChangeProjectileClearRadius", 5.0, 0.0, 128.0);
         builder.pop();
         builder.comment("Reality Change balance values.");
         builder.push("reality_change");
         this.realityChangeKnockback = builder.comment("Knockback strength applied by the Reality Change burst.")
            .defineInRange("realityChangeKnockback", 1.4, 0.0, 64.0);
         this.realityChangeConfusionTicks = builder.comment("Confusion duration applied by Reality Change.")
            .defineInRange("realityChangeConfusionTicks", 80, 1, Integer.MAX_VALUE);
         this.realityChangeConfusionAmplifier = builder.comment("Confusion amplifier applied by Reality Change. 0 means level 1.")
            .defineInRange("realityChangeConfusionAmplifier", 0, 0, 255);
         this.realityCoatResistanceAmplifier = builder.comment("Resistance amplifier during Reality Coat. 0 means level 1.")
            .defineInRange("realityCoatResistanceAmplifier", 2, 0, 255);
         this.realityCoatStrengthAmplifier = builder.comment("Strength amplifier during Reality Coat. 0 means level 1.")
            .defineInRange("realityCoatStrengthAmplifier", 1, 0, 255);
         this.realityCoatSpeedAmplifier = builder.comment("Speed amplifier during Reality Coat. 0 means level 1.")
            .defineInRange("realityCoatSpeedAmplifier", 1, 0, 255);
         this.realityCoatRegenerationAmplifier = builder.comment("Self Regeneration amplifier during Reality Coat. 0 means level 1.")
            .defineInRange("realityCoatRegenerationAmplifier", 1, 0, 255);
         this.realityCoatEffectDurationTicks = builder.comment("Duration of each refreshed Reality Coat effect application.")
            .defineInRange("realityCoatEffectDurationTicks", 100, 1, Integer.MAX_VALUE);
         this.realityCoatEffectRefreshIntervalTicks = builder.comment("How often Reality Coat refreshes its buffs. Higher is better for performance.")
            .defineInRange("realityCoatEffectRefreshIntervalTicks", 80, 1, Integer.MAX_VALUE);
         builder.pop();
         builder.comment("Deleted Timeline counter and projectile reversal values.");
         builder.push("deleted_timeline");
         this.counterResistanceAmplifier = builder.comment("Resistance amplifier while Deleted Timeline is waiting to counter. 0 means level 1.")
            .defineInRange("counterResistanceAmplifier", 1, 0, 255);
         this.counterReflectMultiplier = builder.comment("Damage reflection multiplier. Reflected damage = incoming damage multiplied by this value.")
            .defineInRange("counterReflectMultiplier", 1.75, 0.0, 1024.0);
         this.counterReflectMinimumDamage = builder.comment("Minimum reflected damage dealt by Deleted Timeline.")
            .defineInRange("counterReflectMinimumDamage", 1.0, 0.0, Float.MAX_VALUE);
         this.projectileReverseMinimumSpeed = builder.comment("Minimum speed given to a reversed projectile.")
            .defineInRange("projectileReverseMinimumSpeed", 1.5, 0.0, 128.0);
         this.projectileReverseFallbackSpeed = builder.comment("Speed used when a projectile has almost no movement and needs a fallback direction.")
            .defineInRange("projectileReverseFallbackSpeed", 1.5, 0.0, 128.0);
         builder.pop();
         builder.comment("False Perception illusion and displacement values.");
         builder.push("false_perception");
         this.falsePerceptionTeleportSpread = builder.comment("Random teleport spread for affected targets. Higher values scatter targets further.")
            .defineInRange("falsePerceptionTeleportSpread", 2.5, 0.0, 64.0);
         this.falsePerceptionYawRotation = builder.comment("Yaw rotation applied while a target remains under False Perception.")
            .defineInRange("falsePerceptionYawRotation", 22.5, -360.0, 360.0);
         this.falsePerceptionBlindnessAmplifier = builder.comment("Flashed Blindness amplifier. 0 means level 1.")
            .defineInRange("falsePerceptionBlindnessAmplifier", 0, 0, 255);
         this.falsePerceptionConfusionAmplifier = builder.comment("Confusion amplifier. 0 means level 1.")
            .defineInRange("falsePerceptionConfusionAmplifier", 0, 0, 255);
         this.falsePerceptionMovementInterferenceAmplifier = builder.comment("Movement Interference amplifier. 0 means level 1.")
            .defineInRange("falsePerceptionMovementInterferenceAmplifier", 0, 0, 255);
         builder.pop();
         builder.comment("Death rewrite revive values.");
         builder.push("rewritten_death");
         this.maxRevivesPerDay = builder.comment("Maximum number of death rewrites per Minecraft day.")
            .defineInRange("maxRevivesPerDay", 3, 0, Integer.MAX_VALUE);
         this.reviveHealthRatio = builder.comment("Health restored on revive as a percentage of max health. 0.6 = 60%.")
            .defineInRange("reviveHealthRatio", 0.6, 0.0, 1.0);
         this.reviveMinimumHealth = builder.comment("Minimum health restored by revive, even if the percentage would be lower.")
            .defineInRange("reviveMinimumHealth", 20.0, 0.0, Float.MAX_VALUE);
         this.reviveInvulnerabilityTicks = builder.comment("Invulnerability ticks after revive.")
            .defineInRange("reviveInvulnerabilityTicks", 80, 0, Integer.MAX_VALUE);
         this.reviveResistanceTicks = builder.comment("Resistance duration after revive.").defineInRange("reviveResistanceTicks", 100, 1, Integer.MAX_VALUE);
         this.reviveResistanceAmplifier = builder.comment("Resistance amplifier after revive. 0 means level 1.")
            .defineInRange("reviveResistanceAmplifier", 4, 0, 255);
         this.reviveRegenerationTicks = builder.comment("Self Regeneration duration after revive.")
            .defineInRange("reviveRegenerationTicks", 200, 1, Integer.MAX_VALUE);
         this.reviveRegenerationAmplifier = builder.comment("Self Regeneration amplifier after revive. 0 means level 1.")
            .defineInRange("reviveRegenerationAmplifier", 1, 0, 255);
         this.reviveHakiCoatTicks = builder.comment("Haki Coat duration after revive.").defineInRange("reviveHakiCoatTicks", 120, 1, Integer.MAX_VALUE);
         this.reviveHakiCoatAmplifier = builder.comment("Haki Coat amplifier after revive. 0 means level 1.")
            .defineInRange("reviveHakiCoatAmplifier", 0, 0, 255);
         this.reviveSpiritualHealthRatio = builder.comment("Minimum spiritual health restored on revive as a percentage of max spiritual health.")
            .defineInRange("reviveSpiritualHealthRatio", 0.5, 0.0, 1.0);
         this.reviveAuraRatio = builder.comment("Minimum aura restored on revive as a percentage of max aura.").defineInRange("reviveAuraRatio", 0.4, 0.0, 1.0);
         this.reviveMagiculeRatio = builder.comment("Minimum magicules restored on revive as a percentage of max magicules.")
            .defineInRange("reviveMagiculeRatio", 0.4, 0.0, 1.0);
         builder.pop();
      }
   }
}
