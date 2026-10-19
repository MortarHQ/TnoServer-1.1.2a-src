package io.github.Memoires.mysticism.config.race.insect;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import java.util.List;

@SyncToClient
public class BeetleConfig extends ManasConfig {
   public BeetleConfig.Beetle Beetle = new BeetleConfig.Beetle();
   public BeetleConfig.StagBeetle StagBeetle = new BeetleConfig.StagBeetle();
   public BeetleConfig.StagBeetleInsectar StagBeetleInsectar = new BeetleConfig.StagBeetleInsectar();
   public BeetleConfig.StagBeetleSavant StagBeetleSavant = new BeetleConfig.StagBeetleSavant();
   public BeetleConfig.FantasySoulInsect FantasySoulInsect = new BeetleConfig.FantasySoulInsect();
   public BeetleConfig.DivineStagBeetle DivineStagBeetle = new BeetleConfig.DivineStagBeetle();
   public BeetleConfig.DroneBeetle DroneBeetle = new BeetleConfig.DroneBeetle();
   public BeetleConfig.DroneBeetleInsectar DroneBeetleInsectar = new BeetleConfig.DroneBeetleInsectar();
   public BeetleConfig.DroneBeetleSavant DroneBeetleSavant = new BeetleConfig.DroneBeetleSavant();
   public BeetleConfig.LightningSoulInsect LightningSoulInsect = new BeetleConfig.LightningSoulInsect();
   public BeetleConfig.DivineDroneBeetle DivineDroneBeetle = new BeetleConfig.DivineDroneBeetle();

   public String getFileName() {
      return "mysticism/race/insect/beetle_config";
   }

   public static class Beetle extends Default {
      @Comment("Minimal aura.")
      public double minAura = 1000.0;
      @Comment("Maximum aura.")
      public double maxAura = 2000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 2000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 3000.0;
      @Comment("Bonus Size.")
      public double size = -0.6F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 2.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 15.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:strength");

      public double getMinAura() {
         return this.minAura;
      }

      public double getMaxAura() {
         return this.maxAura;
      }

      public double getMinMagicule() {
         return this.minMagicule;
      }

      public double getMaxMagicule() {
         return this.maxMagicule;
      }

      public double getSize() {
         return this.size;
      }

      public double getMaxHealth() {
         return this.maxHealth;
      }

      public double getMaxSpiritualHealth() {
         return this.maxSpiritualHealth;
      }

      public double getAttack() {
         return this.attack;
      }

      public double getAttackSpeed() {
         return this.attackSpeed;
      }

      public double getKnockbackResistance() {
         return this.knockbackResistance;
      }

      public double getMovementSpeed() {
         return this.movementSpeed;
      }

      public double getSwimSpeed() {
         return this.swimSpeed;
      }
   }

   public static class DivineDroneBeetle extends Default {
      @Comment("EP requirement to evolve into Divine Drone Beetle.")
      public double epRequirement = 2000000.0;
      @Comment("Minimal aura.")
      public double minAura = 1000000.0;
      @Comment("Maximum aura.")
      public double maxAura = 1000000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1000000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1000000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 730.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4705.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 2.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.4F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.2F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.2F;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = 0.025;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:strength", "tensura:divine_ki_release");

      public double getMinAura() {
         return this.minAura;
      }

      public double getMaxAura() {
         return this.maxAura;
      }

      public double getMinMagicule() {
         return this.minMagicule;
      }

      public double getMaxMagicule() {
         return this.maxMagicule;
      }

      public double getSize() {
         return this.size;
      }

      public double getMaxHealth() {
         return this.maxHealth;
      }

      public double getMaxSpiritualHealth() {
         return this.maxSpiritualHealth;
      }

      public double getAttack() {
         return this.attack;
      }

      public double getAttackSpeed() {
         return this.attackSpeed;
      }

      public double getKnockbackResistance() {
         return this.knockbackResistance;
      }

      public double getMovementSpeed() {
         return this.movementSpeed;
      }

      public double getSwimSpeed() {
         return this.swimSpeed;
      }

      public double getFlightSpeed() {
         return this.flightSpeed;
      }
   }

   public static class DivineStagBeetle extends Default {
      @Comment("EP requirement to evolve into Divine Stag Beetle.")
      public double epRequirement = 2000000.0;
      @Comment("Minimal aura.")
      public double minAura = 1000000.0;
      @Comment("Maximum aura.")
      public double maxAura = 1000000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1000000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1000000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 580.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4320.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 7.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.25;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.4F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.7F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:strength", "tensura:divine_ki_release");

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public double getMinAura() {
         return this.minAura;
      }

      public double getMaxAura() {
         return this.maxAura;
      }

      public double getMinMagicule() {
         return this.minMagicule;
      }

      public double getMaxMagicule() {
         return this.maxMagicule;
      }

      public double getSize() {
         return this.size;
      }

      public double getMaxHealth() {
         return this.maxHealth;
      }

      public double getMaxSpiritualHealth() {
         return this.maxSpiritualHealth;
      }

      public double getAttack() {
         return this.attack;
      }

      public double getAttackSpeed() {
         return this.attackSpeed;
      }

      public double getKnockbackResistance() {
         return this.knockbackResistance;
      }

      public double getMovementSpeed() {
         return this.movementSpeed;
      }

      public double getSwimSpeed() {
         return this.swimSpeed;
      }
   }

   public static class DroneBeetle extends Default {
      @Comment("EP requirement to evolve into Drone Beetle.")
      public double epRequirement = 10000.0;
      @Comment("Minimal aura.")
      public double minAura = 5250.0;
      @Comment("Maximum aura.")
      public double maxAura = 5250.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 5250.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 5250.0;
      @Comment("Bonus Size.")
      public double size = -0.65F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 15.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 45.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.25;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.2F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.05F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.1F;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = -0.01;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:strength");

      public double getMinAura() {
         return this.minAura;
      }

      public double getMaxAura() {
         return this.maxAura;
      }

      public double getMinMagicule() {
         return this.minMagicule;
      }

      public double getMaxMagicule() {
         return this.maxMagicule;
      }

      public double getSize() {
         return this.size;
      }

      public double getMaxHealth() {
         return this.maxHealth;
      }

      public double getMaxSpiritualHealth() {
         return this.maxSpiritualHealth;
      }

      public double getAttack() {
         return this.attack;
      }

      public double getAttackSpeed() {
         return this.attackSpeed;
      }

      public double getKnockbackResistance() {
         return this.knockbackResistance;
      }

      public double getMovementSpeed() {
         return this.movementSpeed;
      }

      public double getSwimSpeed() {
         return this.swimSpeed;
      }

      public double getFlightSpeed() {
         return this.flightSpeed;
      }
   }

   public static class DroneBeetleInsectar extends Default {
      @Comment("EP requirement to evolve into Drone Beetle Insectar.")
      public double epRequirement = 100000.0;
      @Comment("Minimal aura.")
      public double minAura = 100000.0;
      @Comment("Maximum aura.")
      public double maxAura = 100000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 100000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 100000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 130.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 825.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.075F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:strength");

      public double getMinAura() {
         return this.minAura;
      }

      public double getMaxAura() {
         return this.maxAura;
      }

      public double getMinMagicule() {
         return this.minMagicule;
      }

      public double getMaxMagicule() {
         return this.maxMagicule;
      }

      public double getSize() {
         return this.size;
      }

      public double getMaxHealth() {
         return this.maxHealth;
      }

      public double getMaxSpiritualHealth() {
         return this.maxSpiritualHealth;
      }

      public double getAttack() {
         return this.attack;
      }

      public double getAttackSpeed() {
         return this.attackSpeed;
      }

      public double getKnockbackResistance() {
         return this.knockbackResistance;
      }

      public double getMovementSpeed() {
         return this.movementSpeed;
      }

      public double getSwimSpeed() {
         return this.swimSpeed;
      }

      public double getFlightSpeed() {
         return this.flightSpeed;
      }
   }

   public static class DroneBeetleSavant extends Default {
      @Comment("EP requirement to evolve into Drone Beetle Savant.")
      public double epRequirement = 400000.0;
      @Comment("The number of Bosses defeated to evolve into Drone Beetle Savant.")
      public int bossRequirement = 4;
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 400000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 400000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 430.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 1835.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.1F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1F;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = 0.0125;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:strength");

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public double getMinAura() {
         return this.minAura;
      }

      public double getMaxAura() {
         return this.maxAura;
      }

      public double getMinMagicule() {
         return this.minMagicule;
      }

      public double getMaxMagicule() {
         return this.maxMagicule;
      }

      public double getSize() {
         return this.size;
      }

      public double getMaxHealth() {
         return this.maxHealth;
      }

      public double getMaxSpiritualHealth() {
         return this.maxSpiritualHealth;
      }

      public double getAttack() {
         return this.attack;
      }

      public double getAttackSpeed() {
         return this.attackSpeed;
      }

      public double getKnockbackResistance() {
         return this.knockbackResistance;
      }

      public double getMovementSpeed() {
         return this.movementSpeed;
      }

      public double getSwimSpeed() {
         return this.swimSpeed;
      }

      public double getFlightSpeed() {
         return this.flightSpeed;
      }
   }

   public static class FantasySoulInsect extends Default {
      @Comment("EP requirement to evolve into Fantasy Soul Insect.")
      public double epRequirement = 400000.0;
      @Comment("The ability needed to evolve into Fantasy Soul Insect.")
      public String abilityRequirement = "tensura:magic_jamming";
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 400000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 400000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 230.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 2230.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.15F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:strength", "tensura:magic_jamming", "tensura:physical_attack_resistance"
      );

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public String getAbilityRequirement() {
         return this.abilityRequirement;
      }

      public double getMinAura() {
         return this.minAura;
      }

      public double getMaxAura() {
         return this.maxAura;
      }

      public double getMinMagicule() {
         return this.minMagicule;
      }

      public double getMaxMagicule() {
         return this.maxMagicule;
      }

      public double getSize() {
         return this.size;
      }

      public double getMaxHealth() {
         return this.maxHealth;
      }

      public double getMaxSpiritualHealth() {
         return this.maxSpiritualHealth;
      }

      public double getAttack() {
         return this.attack;
      }

      public double getAttackSpeed() {
         return this.attackSpeed;
      }

      public double getKnockbackResistance() {
         return this.knockbackResistance;
      }

      public double getMovementSpeed() {
         return this.movementSpeed;
      }

      public double getSwimSpeed() {
         return this.swimSpeed;
      }
   }

   public static class LightningSoulInsect extends Default {
      @Comment("EP requirement to evolve into Lightning Soul Insect.")
      public double epRequirement = 400000.0;
      @Comment("The ability needed to evolve into Lightning Soul Insect.")
      public String abilityRequirement = "tensura:lightning_manipulation";
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 400000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 400000.0;
      @Comment("Bonus Size.")
      public double size = -0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 380.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 6265.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.15F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.15F;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = 0.02;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "mysticism:exoskeleton",
         "tensura:analytical_appraisal",
         "tensura:strength",
         "tensura:lightning_manipulation",
         "tensura:electricity_resistance",
         "mysticism:lightning_mode"
      );

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public String getAbilityRequirement() {
         return this.abilityRequirement;
      }

      public double getMinAura() {
         return this.minAura;
      }

      public double getMaxAura() {
         return this.maxAura;
      }

      public double getMinMagicule() {
         return this.minMagicule;
      }

      public double getMaxMagicule() {
         return this.maxMagicule;
      }

      public double getSize() {
         return this.size;
      }

      public double getMaxHealth() {
         return this.maxHealth;
      }

      public double getMaxSpiritualHealth() {
         return this.maxSpiritualHealth;
      }

      public double getAttack() {
         return this.attack;
      }

      public double getAttackSpeed() {
         return this.attackSpeed;
      }

      public double getKnockbackResistance() {
         return this.knockbackResistance;
      }

      public double getMovementSpeed() {
         return this.movementSpeed;
      }

      public double getSwimSpeed() {
         return this.swimSpeed;
      }

      public double getFlightSpeed() {
         return this.flightSpeed;
      }
   }

   public static class StagBeetle extends Default {
      @Comment("EP requirement to evolve into Stag Beetle.")
      public double epRequirement = 10000.0;
      @Comment("Minimal aura.")
      public double minAura = 5250.0;
      @Comment("Maximum aura.")
      public double maxAura = 5250.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 5250.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 5250.0;
      @Comment("Bonus Size.")
      public double size = -0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 10.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 65.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:strength");

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public double getMinAura() {
         return this.minAura;
      }

      public double getMaxAura() {
         return this.maxAura;
      }

      public double getMinMagicule() {
         return this.minMagicule;
      }

      public double getMaxMagicule() {
         return this.maxMagicule;
      }

      public double getSize() {
         return this.size;
      }

      public double getMaxHealth() {
         return this.maxHealth;
      }

      public double getMaxSpiritualHealth() {
         return this.maxSpiritualHealth;
      }

      public double getAttack() {
         return this.attack;
      }

      public double getAttackSpeed() {
         return this.attackSpeed;
      }

      public double getKnockbackResistance() {
         return this.knockbackResistance;
      }

      public double getMovementSpeed() {
         return this.movementSpeed;
      }

      public double getSwimSpeed() {
         return this.swimSpeed;
      }
   }

   public static class StagBeetleInsectar extends Default {
      @Comment("EP requirement to evolve into Stag Beetle Insectar.")
      public double epRequirement = 100000.0;
      @Comment("Minimal aura.")
      public double minAura = 100000.0;
      @Comment("Maximum aura.")
      public double maxAura = 100000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 100000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 100000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 80.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 655.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.1F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:strength");

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public double getMinAura() {
         return this.minAura;
      }

      public double getMaxAura() {
         return this.maxAura;
      }

      public double getMinMagicule() {
         return this.minMagicule;
      }

      public double getMaxMagicule() {
         return this.maxMagicule;
      }

      public double getSize() {
         return this.size;
      }

      public double getMaxHealth() {
         return this.maxHealth;
      }

      public double getMaxSpiritualHealth() {
         return this.maxSpiritualHealth;
      }

      public double getAttack() {
         return this.attack;
      }

      public double getAttackSpeed() {
         return this.attackSpeed;
      }

      public double getKnockbackResistance() {
         return this.knockbackResistance;
      }

      public double getMovementSpeed() {
         return this.movementSpeed;
      }

      public double getSwimSpeed() {
         return this.swimSpeed;
      }
   }

   public static class StagBeetleSavant extends Default {
      @Comment("EP requirement to evolve into Stag Beetle Savant.")
      public double epRequirement = 400000.0;
      @Comment("The number of Bosses defeated to evolve into Stag Beetle Savant.")
      public int bossRequirement = 4;
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 400000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 400000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 280.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 1460.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 5.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.2F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:strength");

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public double getMinAura() {
         return this.minAura;
      }

      public double getMaxAura() {
         return this.maxAura;
      }

      public double getMinMagicule() {
         return this.minMagicule;
      }

      public double getMaxMagicule() {
         return this.maxMagicule;
      }

      public double getSize() {
         return this.size;
      }

      public double getMaxHealth() {
         return this.maxHealth;
      }

      public double getMaxSpiritualHealth() {
         return this.maxSpiritualHealth;
      }

      public double getAttack() {
         return this.attack;
      }

      public double getAttackSpeed() {
         return this.attackSpeed;
      }

      public double getKnockbackResistance() {
         return this.knockbackResistance;
      }

      public double getMovementSpeed() {
         return this.movementSpeed;
      }

      public double getSwimSpeed() {
         return this.swimSpeed;
      }
   }
}
