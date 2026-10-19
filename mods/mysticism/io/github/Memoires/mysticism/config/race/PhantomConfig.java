package io.github.Memoires.mysticism.config.race;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import java.util.List;

@SyncToClient
public class PhantomConfig extends ManasConfig {
   public PhantomConfig.Phantom Phantom = new PhantomConfig.Phantom();
   public PhantomConfig.FieldOfficer FieldOfficer = new PhantomConfig.FieldOfficer();
   public PhantomConfig.General General = new PhantomConfig.General();
   public PhantomConfig.StaffOfficer StaffOfficer = new PhantomConfig.StaffOfficer();
   public PhantomConfig.MysticAngel MysticAngel = new PhantomConfig.MysticAngel();

   public String getFileName() {
      return "mysticism/race/phantom_config";
   }

   public static class FieldOfficer extends Default {
      @Comment("Quantity of cryptid essence required to become a field officer as a greater angel.")
      public int essenceRequired = 3;
      @Comment("EP requirement to evolve into a field officer.")
      public double epRequirement = 20000.0;
      @Comment("Minimal aura.")
      public double minAura = 15000.0;
      @Comment("Maximum aura.")
      public double maxAura = 25000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 25000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 30000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 50.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 150.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.2F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:magic_resistance", "tensura:possession");

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

   public static class General extends Default {
      @Comment("Quantity of cryptid essence required to become a general as an arch angel.")
      public int essenceRequired = 5;
      @Comment("EP requirement to evolve into a General.")
      public double epRequirement = 160000.0;
      @Comment("Minimal aura.")
      public double minAura = 80000.0;
      @Comment("Maximum aura.")
      public double maxAura = 85000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 155000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 160000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 80.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 540.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.2;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.4;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:magic_resistance", "tensura:possession", "tensura:physical_attack_resistance");

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

   public static class MysticAngel extends Default {
      @Comment("Quantity of cryptid essence required to become a mystic angel as a seraphim.")
      public int essenceRequired = 10;
      @Comment("Minimal aura.")
      public double minAura = 1100000.0;
      @Comment("Maximum aura.")
      public double maxAura = 1150000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1200000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1250000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1060.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 6350.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.7;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.8;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.08;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.8;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:magic_resistance",
         "tensura:possession",
         "tensura:physical_attack_resistance",
         "tensura:holy_attack_resistance",
         "tensura:divine_ki_release",
         "mysticism:light_domination"
      );

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

   public static class Phantom extends Default {
      @Comment("Quantity of cryptid essence required to become a phantom as a lesser angel.")
      public int essenceRequired = 1;
      @Comment("Minimal aura.")
      public double minAura = 2500.0;
      @Comment("Maximum aura.")
      public double maxAura = 3500.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 5500.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 6500.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 15.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 70.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.3F;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:magic_resistance", "tensura:possession");

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

   public static class StaffOfficer extends Default {
      @Comment("Quantity of cryptid essence required to become a staff officer as a cherub.")
      public int essenceRequired = 7;
      @Comment("EP requirement to evolve into a Staff Officer.")
      public double epRequirement = 800000.0;
      @Comment("Minimal aura.")
      public double minAura = 455000.0;
      @Comment("Maximum aura.")
      public double maxAura = 460000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 505000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 510000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 600.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 3650.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.6;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.04;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.4;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:magic_resistance", "tensura:possession", "tensura:physical_attack_resistance");

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
