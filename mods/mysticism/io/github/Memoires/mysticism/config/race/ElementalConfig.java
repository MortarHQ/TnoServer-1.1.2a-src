package io.github.Memoires.mysticism.config.race;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;

@SyncToClient
public class ElementalConfig extends ManasConfig {
   public ElementalConfig.LesserElemental LesserElemental = new ElementalConfig.LesserElemental();
   public ElementalConfig.MediumElemental MediumElemental = new ElementalConfig.MediumElemental();
   public ElementalConfig.GreaterElemental GreaterElemental = new ElementalConfig.GreaterElemental();
   public ElementalConfig.ElementalLord ElementalLord = new ElementalConfig.ElementalLord();
   public ElementalConfig.MajinLord MajinLord = new ElementalConfig.MajinLord();
   public ElementalConfig.DivineElemental DivineElemental = new ElementalConfig.DivineElemental();
   public ElementalConfig.DivineMajinElemental DivineMajinElemental = new ElementalConfig.DivineMajinElemental();

   public String getFileName() {
      return "mysticism/race/elemental_config";
   }

   public static class DivineElemental extends Default {
      @Comment("EP requirement to evolve into Divine Elemental.")
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
      public double maxHealth = 680.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 12630.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.5;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.1F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.8F;
      @Comment("Bonus Flight Speed.")
      public double flightSpeed = 0.0;

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

   public static class DivineMajinElemental extends Default {
      @Comment("EP requirement to evolve into Divine Majin Elemental.")
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
      public double maxHealth = 680.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 12630.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.5;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.1F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.8F;
      @Comment("Bonus Flight Speed.")
      public double flightSpeed = 0.0;

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

   public static class ElementalLord extends Default {
      @Comment("EP requirement to evolve into Elemental Lord.")
      public double epRequirement = 800000.0;
      @Comment("Minimal aura.")
      public double minAura = 500000.0;
      @Comment("Maximum aura.")
      public double maxAura = 500000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 500000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 500000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 430.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 8625.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.35F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.625;
      @Comment("Bonus Flight Speed.")
      public double flightSpeed = 0.0;

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

   public static class GreaterElemental extends Default {
      @Comment("EP requirement to evolve into Greater Elemental.")
      public double epRequirement = 200000.0;
      @Comment("Minimal aura.")
      public double minAura = 200000.0;
      @Comment("Maximum aura.")
      public double maxAura = 200000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 100000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 100000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 180.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 1260.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.2F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.45F;
      @Comment("Bonus Flight Speed.")
      public double flightSpeed = 0.0;

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

   public static class LesserElemental extends Default {
      @Comment("Minimal aura.")
      public double minAura = 300.0;
      @Comment("Maximum aura.")
      public double maxAura = 500.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 500.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1000.0;
      @Comment("Bonus Size.")
      public double size = -0.75;
      @Comment("Bonus Max Health.")
      public double maxHealth = -12.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 120.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.8F;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("Bonus Flight Speed.")
      public double flightSpeed = -0.025F;

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

   public static class MajinLord extends Default {
      @Comment("EP requirement to evolve into Majin Lord.")
      public double epRequirement = 800000.0;
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 600000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 600000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 430.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 8625.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.35F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.625;
      @Comment("Bonus Flight Speed.")
      public double flightSpeed = 0.0;

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

   public static class MediumElemental extends Default {
      @Comment("EP requirement to evolve into Medium Elemental.")
      public double epRequirement = 20000.0;
      @Comment("Minimal aura.")
      public double minAura = 11250.0;
      @Comment("Maximum aura.")
      public double maxAura = 11250.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 11250.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 11250.0;
      @Comment("Bonus Size.")
      public double size = -0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 5.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 425.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.1F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.2F;
      @Comment("Bonus Flight Speed.")
      public double flightSpeed = -0.01F;

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
}
