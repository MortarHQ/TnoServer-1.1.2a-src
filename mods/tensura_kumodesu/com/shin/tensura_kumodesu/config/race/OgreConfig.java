package com.shin.tensura_kumodesu.config.race;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;

public class OgreConfig extends ManasConfig {
   public OgreConfig.OgreShaman OgreShaman = new OgreConfig.OgreShaman();
   public OgreConfig.HighOgre HighOgre = new OgreConfig.HighOgre();
   public OgreConfig.OgreGeneral OgreGeneral = new OgreConfig.OgreGeneral();
   public OgreConfig.OgreKing OgreKing = new OgreConfig.OgreKing();
   public OgreConfig.WrathOni WrathOni = new OgreConfig.WrathOni();

   public String getFileName() {
      return "tensura_kumodesu/race/ogre_config";
   }

   public static class HighOgre extends Default {
      @Comment("EP requirement to evolve into a High Ogre.")
      public double epRequirement = 60000.0;
      @Comment("Minimal aura.")
      public double minAura = 30000.0;
      @Comment("Maximum aura.")
      public double maxAura = 30000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 30000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 30000.0;
      @Comment("Bonus Size.")
      public double size = 0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 140.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 460.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 5.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.3;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 1.1;

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

   public static class OgreGeneral extends Default {
      @Comment("EP requirement to evolve into an Ogre General.")
      public double epRequirement = 150000.0;
      @Comment("Minimal aura.")
      public double minAura = 50000.0;
      @Comment("Maximum aura.")
      public double maxAura = 50000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 100000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 100000.0;
      @Comment("Bonus Size.")
      public double size = 0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 180.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 580.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 6.3;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 2.3;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.4;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.35;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 1.3;

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

   public static class OgreKing extends Default {
      @Comment("EP requirement to evolve into an Ogre King.")
      public double epRequirement = 600000.0;
      @Comment("Minimal aura.")
      public double minAura = 100000.0;
      @Comment("Maximum aura.")
      public double maxAura = 100000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 500000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 500000.0;
      @Comment("Bonus Size.")
      public double size = 0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 580.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 3430.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 5.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 2.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.45;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.4;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 2.0;

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

   public static class OgreShaman extends Default {
      @Comment("EP requirement to evolve into an Ogre Shaman.")
      public double epRequirement = 15000.0;
      @Comment("The number of Essences consumed to evolve into an Ogre Shaman.")
      public int essenceRequirement = 10;
      @Comment("Minimal aura.")
      public double minAura = 9000.0;
      @Comment("Maximum aura.")
      public double maxAura = 9000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 6000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 6000.0;
      @Comment("Bonus Size.")
      public double size = 0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 20.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 150.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 5.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.12;
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

   public static class WrathOni extends Default {
      @Comment("EP requirement to evolve into a Wrath Oni.")
      public double epRequirement = 3800000.0;
      @Comment("Minimal aura.")
      public double minAura = 1000000.0;
      @Comment("Maximum aura.")
      public double maxAura = 1000000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 2800000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 2800000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1480.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 6130.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 7.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 3.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.5;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.45;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 2.5;

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
