package io.github.Memoires.mysticism.config.race.insect;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import java.util.List;

@SyncToClient
public class WaspConfig extends ManasConfig {
   public WaspConfig.Wasp Wasp = new WaspConfig.Wasp();
   public WaspConfig.ArmyWasp ArmyWasp = new WaspConfig.ArmyWasp();
   public WaspConfig.ArmyWaspInsectar ArmyWaspInsectar = new WaspConfig.ArmyWaspInsectar();
   public WaspConfig.ArmyWaspSavant ArmyWaspSavant = new WaspConfig.ArmyWaspSavant();
   public WaspConfig.WindSoulInsect WindSoulInsect = new WaspConfig.WindSoulInsect();
   public WaspConfig.DivineArmyWasp DivineArmyWasp = new WaspConfig.DivineArmyWasp();
   public WaspConfig.QueenWasp QueenWasp = new WaspConfig.QueenWasp();
   public WaspConfig.QueenWaspInsectar QueenWaspInsectar = new WaspConfig.QueenWaspInsectar();
   public WaspConfig.QueenWaspSavant QueenWaspSavant = new WaspConfig.QueenWaspSavant();
   public WaspConfig.StarSoulInsect StarSoulInsect = new WaspConfig.StarSoulInsect();
   public WaspConfig.DivineQueenWasp DivineQueenWasp = new WaspConfig.DivineQueenWasp();

   public String getFileName() {
      return "mysticism/race/insect/wasp_config";
   }

   public static class ArmyWasp extends Default {
      @Comment("EP requirement to evolve into Army Wasp.")
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
      public double size = 0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 50.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 350.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.3F;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal");

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

   public static class ArmyWaspInsectar extends Default {
      @Comment("EP requirement to evolve into Army Wasp Insectar.")
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
      public double size = 0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 300.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 2320.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.25;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.3;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = 0.01F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal");

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

   public static class ArmyWaspSavant extends Default {
      @Comment("EP requirement to evolve into Army Wasp Savant.")
      public double epRequirement = 400000.0;
      @Comment("The number of Bosses defeated to evolve into Army Wasp Savant.")
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
      public double size = 0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 600.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 3650.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.4F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.35F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.2;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = 0.015F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal");

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public int getBossRequirement() {
         return this.bossRequirement;
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

   public static class DivineArmyWasp extends Default {
      @Comment("EP requirement to evolve into Divine Army Wasp.")
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
      public double size = 0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1100.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 8230.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.4F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = 0.022F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:divine_ki_release");

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

   public static class DivineQueenWasp extends Default {
      @Comment("EP requirement to evolve into Divine Queen Wasp.")
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
      public double maxHealth = 560.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4230.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.8F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = 0.01F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:divine_ki_release");

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

   public static class QueenWasp extends Default {
      @Comment("EP requirement to evolve into Queen Wasp.")
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
      public double size = -0.6F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 10.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 65.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.3F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.3F;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = -0.01F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal");

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

   public static class QueenWaspInsectar extends Default {
      @Comment("EP requirement to evolve into Queen Wasp Insectar.")
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
      public double size = -0.2F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 75.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 645.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.4F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.3F;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal");

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

   public static class QueenWaspSavant extends Default {
      @Comment("EP requirement to evolve into Queen Wasp Savant.")
      public double epRequirement = 400000.0;
      @Comment("The number of Bosses defeated to evolve into Queen Wasp Savant.")
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
      public double maxHealth = 240.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 1280.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.025F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.2F;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal");

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public int getBossRequirement() {
         return this.bossRequirement;
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

   public static class StarSoulInsect extends Default {
      @Comment("EP requirement to evolve into Star Soul Insect.")
      public double epRequirement = 400000.0;
      @Comment("The ability needed to evolve into Star Soul Insect.")
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
      public double maxHealth = 200.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 2010.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.25;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.45F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.023F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.2F;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:magic_jamming", "tensura:physical_attack_resistance"
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

   public static class Wasp extends Default {
      @Comment("Minimal aura.")
      public double minAura = 1000.0;
      @Comment("Maximum aura.")
      public double maxAura = 2000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 2000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 3000.0;
      @Comment("Bonus Size.")
      public double size = -0.75;
      @Comment("Bonus Max Health.")
      public double maxHealth = 4.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 35.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.3F;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = -0.02F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal");

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

   public static class WindSoulInsect extends Default {
      @Comment("EP requirement to evolve into Wind Soul Insect.")
      public double epRequirement = 400000.0;
      @Comment("Ability requirement to evolve into Wind Soul Insect.")
      public String abilityRequirement = "tensura:wind_manipulation";
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 400000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 400000.0;
      @Comment("Bonus Size.")
      public double size = 0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 550.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 5650.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.35F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.3F;
      @Comment("Bonus Flight Speed Multiplier.")
      public double flightSpeed = 0.02F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:wind_manipulation", "tensura:wind_attack_resistance", "tensura:wind_transform"
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
}
