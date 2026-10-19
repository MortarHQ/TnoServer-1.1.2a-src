package com.github.dominickwd04.traddon.config.ability.skill;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class TrAddonSkillConfig extends ManasConfig {
   public TrAddonSkillConfig.Deviator deviator = new TrAddonSkillConfig.Deviator();
   public TrAddonSkillConfig.Devastator devastator = new TrAddonSkillConfig.Devastator();
   public TrAddonSkillConfig.CrystalDust CrystalDust = new TrAddonSkillConfig.CrystalDust();
   public TrAddonSkillConfig.RefractiveShell RefractiveShell = new TrAddonSkillConfig.RefractiveShell();
   public TrAddonSkillConfig.PhotonBlade PhotonBlade = new TrAddonSkillConfig.PhotonBlade();
   public TrAddonSkillConfig.ResonantCore ResonantCore = new TrAddonSkillConfig.ResonantCore();
   public TrAddonSkillConfig.Silentium silentium = new TrAddonSkillConfig.Silentium();
   public TrAddonSkillConfig.TotalReflection TotalReflection = new TrAddonSkillConfig.TotalReflection();
   public TrAddonSkillConfig.Origin origin = new TrAddonSkillConfig.Origin();
   public TrAddonSkillConfig.Necromancer necromancer = new TrAddonSkillConfig.Necromancer();
   public TrAddonSkillConfig.SpatialSamurai spatialSamurai = new TrAddonSkillConfig.SpatialSamurai();

   public String getFileName() {
      return "traddon/ability/skill/unique_config";
   }

   public static class CrystalDust extends ManasSubConfig {
      @Comment("Magicule Cost to activate the Default Mode.")
      public double magiculeCost = 200.0;
      @Comment("The spawn range in block of the Crystal Dust.")
      public int dustRange = 4;
      @Comment("The damage per second of the Crystal Dust.")
      public int dustDamage = 2;
      @Comment("The radius of the Crystal Dust.")
      public int dustRadius = 3;
      @Comment("The cooldown of the Crystal Dust (halved with mastery).")
      public int dustCooldown = 12;
   }

   public class Devastator extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 80000.0;
      @Comment("The attack damage reduction percentage granted by the Despair effect.")
      public double attack = -0.25;
      @Comment("The attack speed reduction percentage granted by the Despair effect.")
      public double attackSpeed = -0.25;
      @Comment("The movement speed reduction percentage granted by the Despair effect.")
      public double movementSpeed = -0.25;
      @Comment("The chant speed reduction percentage granted by the Despair effect.")
      public double chantSpeed = -0.25;
      @Comment("The extra damage multiplier for Devastator when mastered.")
      public double masterDamageMultiplier = 1.5;
      @Comment("The extra damage multiplier for Devastator when not mastered.")
      public double spiritualDamageMultiplier = 1.0;
      @Comment("The armor durability reduction multiplier for Devastator.")
      public int armorDurability = 2;
      @Comment("The weapon durability reduction multiplier for Devastator.")
      public int weaponDurability = 2;
      @Comment("The EP difference multiplier for Devastator.")
      public double epDifferenceMultiplier = 0.2;
      @Comment("The physical damage multiplier for Devastator.")
      public double physicalDamageMultiplier = 1.5;
      @Comment("The radius for the End of Days effect.")
      public double endOfDaysRadius = 16.0;
      @Comment("The attack damage multiplier for the Post Mortem Surge effect.")
      public double surgeAttack = 0.1;
      @Comment("The attack speed multiplier for the Post Mortem Surge effect.")
      public double surgeAttackSpeed = 0.1;
      @Comment("The magicule cost for the Tyrant's Haki mode.")
      public double tyrantsHakiCost = 100.0;
      @Comment("The magicule cost for the End of Days mode.")
      public double endOfDaysCost = 2500.0;
   }

   public class Deviator extends ManasSubConfig {
      @Comment("The multiplier of the maximum Health that a target needs to be under to be possessed.")
      public double hpMultiplier = 0.1;
      @Comment("The multiplier of the maximum Spiritual Health that a target needs to be under to be possessed.")
      public double shpMultiplier = 0.1;
      @Comment("The multiplier of the user's maximum EP that a target needs to be under to be possessed.")
      public double epMultiplier = 0.25;
      @Comment("The multiplier of the possession requirement multipliers when the target has Spiritual Attack Resistance.")
      public double resistanceMultiplier = 0.5;
      @Comment("The maximum amount of HP the user can get from possessing an entity.")
      public double maxHealth = 1000.0;
      @Comment("The maximum amount of Attack Damage the user can get from possessing an entity.")
      public double maxAttack = 100.0;
      @Comment("The number of seconds that Possession Bodies will despawn. (0 = instant despawn, -1 = doesn't despawn)")
      public int bodyDespawnTick = 300;
      @Comment("Extra Spiritual damage multiplier.")
      public double spiritualDamageMultiplier = 1.0;
      @Comment("Extra Spiritual damage multiplier when mastered.")
      public double masterDamageMultiplier = 1.5;
      @Comment("chance for deviator to get unique skills when possessing an entity")
      public double uniqueSkillChance = 0.1;
      @Comment("chance for deviator to get unique skills when possessing an entity if mastered")
      public double masterUniqueSkillChance = 0.2;
      @Comment("The MP cost to acquire the Deviator skill.")
      public double mpAcquirement = 50000.0;
      @Comment("Range")
      public double range = 4.0;
   }

   public class Necromancer extends ManasSubConfig {
      @Comment("The MP cost to acquire the Necromancer skill.")
      public double mpAcquirement = 45000.0;
      @Comment("The maximum number of summoned minions allowed at one time.")
      public int maxMinions = 5;
      @Comment("The Cost in Souls to summon a minion.")
      public int summonCost = 50;
      @Comment("The health for summoned minions.")
      public double minionHealth = 20.0;
      @Comment("The attack damage for summoned minions.")
      public double minionAttackDamage = 6.0;
      @Comment("The speed for summoned minions.")
      public double minionSpeed = 0.25;
      @Comment("The armor for summoned minions.")
      public double minionArmor = 10.0;
      @Comment("The Soul Points returned when a summoned minion dies (as a multiplier of the summon cost).")
      public double summonedDeathSoulReturn = 25.0;
   }

   public class Origin extends ManasSubConfig {
      @Comment("The melee dodge chance bonus granted by the Origin skill.")
      public double meleeDodge = 0.2;
      @Comment("The projectile dodge chance bonus granted by the Origin skill.")
      public double projectileDodge = 0.2;
      @Comment("The dodge negation chance bonus granted by the Origin skill.")
      public double dodgeNegation = 0.2;
      @Comment("The MP cost to acquire the Origin skill.")
      public double mpAcquirement = 500.0;
      @Comment("The learning points bonus granted by the Origin skill.")
      public double learningPoint = 9.0;
      @Comment("The mastery points bonus granted by the Origin skill.")
      public double masteryPoint = 9.0;
      @Comment("Extra Aura gain percentage granted by the Origin skill.")
      public double auraPercentage = 2.5;
      @Comment("Extra Magicule gain percentage granted by the Origin skill.")
      public double magiculePercentage = 2.5;
      @Comment("The level of the Self Regeneration effect granted by the Origin skill.")
      public int regenLevel = 5;
   }

   public static class PhotonBlade extends ManasSubConfig {
      @Comment("The base extra damage dealt by Photon Blade on hit.")
      public double photonBladeBaseDamage = 6.0;
      @Comment("The extra damage dealt by Photon Blade on hit when mastered.")
      public double photonBladeMasteredExtraDamage = 12.0;
      @Comment("Physical resist degradation from effect.")
      public boolean physicalResist = true;
   }

   public static class RefractiveShell extends ManasSubConfig {
      @Comment("The movement speed multiplier when Dispersion is active.")
      public double refractiveShellSpeed = 0.8;
      @Comment("The magicule cost per point of damage canceled by the Refractive Shell.")
      public double magiculeCostRefractiveShell = 100.0;
   }

   public static class ResonantCore extends ManasSubConfig {
      @Comment("Magicule cost to acquire the Resonant Core skill.")
      public double mpAcquirement = 30000.0;
      @Comment("Cooldown in ticks for Spectral Refraction active mode.")
      public int spectralRefractionCooldown = 100;
      @Comment("Duration in ticks for Spectral Refraction to be held.")
      public int spectralRefractionDuration = 200;
      @Comment("Cooldown in ticks for Data Overload active mode.")
      public int dataOverloadCooldown = 100;
      @Comment("Stun duration in ticks for Data Overload.")
      public int dataOverloadStunDuration = 60;
   }

   public class Silentium extends ManasSubConfig {
      @Comment("Radius in blocks for Acoustic Nullification.")
      public int acousticNullRadius = 100;
      @Comment("Duration in ticks for Acoustic Nullification.")
      public int acousticNullDuration = 200;
      @Comment("Cooldown in ticks for Acoustic Nullification.")
      public int acousticNullCooldown = 400;
      @Comment("Attack boost duration for Vibro-Edge (ticks).")
      public int vibroEdgeDuration = 200;
      @Comment("Attack boost amplifier for Vibro-Edge.")
      public int vibroEdgeAmplifier = 2;
      @Comment("Night vision duration for Echo Location (ticks).")
      public int echoLocationDuration = 400;
   }

   public class SpatialSamurai extends ManasSubConfig {
      @Comment("The MP cost to acquire the Spatial Samurai skill.")
      public double mpAcquirement = 6000.0;
      @Comment("The maximum health multiplier used to calculate the Cleave extra damage.")
      public double cleaveMaxHealthMultiplier = 0.15;
      @Comment("The magicule cost per point of extra damage for the Cleave effect.")
      public double cleaveMagiculePerDamage = 100.0;
      @Comment("The damage of the Severance Cutter projectile.")
      public double projectileDamage = 8.0;
      @Comment("The damage of the Severance Cutter projectile when mastered.")
      public double projectileDamageMastered = 12.0;
      @Comment("The size of the Severance Cutter projectile.")
      public double projectileSize = 0.5;
      @Comment("The size of the Severance Cutter projectile when mastered.")
      public double projectileSizeMastered = 0.75;
      @Comment("The cooldown of the Severance Cutter projectile.")
      public int projectileCooldown = 100;
   }

   public class TotalReflection extends ManasSubConfig {
      @Comment("Magicule cost to acquire the Total Reflection skill.")
      public double mpAcquirement = 70000.0;
      @Comment("Chance to learn a spell upon being hit by it (Analytical Mirror).")
      public double spellLearnChance = 0.2;
      @Comment("Maximum stored magicules from absorbed spells (Energy Condensation).")
      public double maxStoredMagicules = 2.0;
      @Comment("Maximum stored damage from damage taken (Prismatic Fracture).")
      public float maxStoredDamage = 2000.0F;
      @Comment("Cooldown in ticks for Prismatic Fracture active mode.")
      public int prismaticFractureCooldown = 30;
      @Comment("Maximum amount of magicules restored per second from stored magicules as a percent of max (Energy Condensation).")
      public double maxRestoredPerSecond = 0.1;
      @Comment("Movement speed multiplier when Full Vector Reversal is active.")
      public double moveSpeed = -0.2;
      @Comment("Magicule cost per point of damage reflected in Full Vector Reversal.")
      public double vectorReversalMagiculeCost = 50.0;
      @Comment("Magicule cost per second to maintain Full Vector Reversal.")
      public double vectorReversalMaintainCost = 50.0;
      @Comment("Magicule cost for Prismatic Fracture.")
      public double prismaticFractureCost = 100.0;
   }
}
