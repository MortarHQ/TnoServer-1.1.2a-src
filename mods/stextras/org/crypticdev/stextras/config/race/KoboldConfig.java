package org.crypticdev.stextras.config.race;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;

@SyncToClient
public class KoboldConfig extends ManasConfig {
   public KoboldConfig.Kobold Kobold = new KoboldConfig.Kobold();
   public KoboldConfig.KoboldTrader KoboldTrader = new KoboldConfig.KoboldTrader();
   public KoboldConfig.KoboldWarrior KoboldWarrior = new KoboldConfig.KoboldWarrior();
   public KoboldConfig.KoboldChieftain KoboldChieftain = new KoboldConfig.KoboldChieftain();
   public KoboldConfig.DivineKobold DivineKobold = new KoboldConfig.DivineKobold();

   public String getFileName() {
      return "stextras/race/kobold_config";
   }

   public static class DivineKobold extends Default {
      @Comment("EP requirement to evolve into Divine Kobold.")
      public double epRequirement = 2000000.0;
      @Comment("Minimal aura.")
      public double minAura = 1300000.0;
      @Comment("Maximum aura.")
      public double maxAura = 1300000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 700000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 700000.0;
      @Comment("Bonus Size.")
      public double size = -0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1080.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 6000.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.13F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;

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

   public static class Kobold extends Default {
      @Comment("Minimal aura.")
      public double minAura = 500.0;
      @Comment("Maximum aura.")
      public double maxAura = 800.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 300.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 500.0;
      @Comment("Bonus Size.")
      public double size = -0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = -2.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 0.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.2F;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = -0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;

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

   public static class KoboldChieftain extends Default {
      @Comment("EP requirement to evolve into Kobold Chieftain.")
      public double epRequirement = 400000.0;
      @Comment("Minimal aura.")
      public double minAura = 240000.0;
      @Comment("Maximum aura.")
      public double maxAura = 240000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 160000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 160000.0;
      @Comment("Bonus Size.")
      public double size = -0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 255.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 3000.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.09F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.1F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;

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

   public static class KoboldTrader extends Default {
      @Comment("EP requirement to evolve into Kobold Trader.")
      public double epRequirement = 20000.0;
      @Comment("Minimal aura.")
      public double minAura = 13000.0;
      @Comment("Maximum aura.")
      public double maxAura = 13000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 7000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 7000.0;
      @Comment("Bonus Size.")
      public double size = -0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 32.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 0.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.04F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;

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

   public static class KoboldWarrior extends Default {
      @Comment("EP requirement to evolve into Kobold Warrior.")
      public double epRequirement = 100000.0;
      @Comment("Minimal aura.")
      public double minAura = 130000.0;
      @Comment("Maximum aura.")
      public double maxAura = 130000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 70000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 70000.0;
      @Comment("Bonus Size.")
      public double size = -0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 62.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 300.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.2F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.06F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;

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
