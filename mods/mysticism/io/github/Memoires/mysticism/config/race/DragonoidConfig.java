package io.github.Memoires.mysticism.config.race;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import java.util.List;

public class DragonoidConfig extends ManasConfig {
   public DragonoidConfig.Dragonoid Dragonoid = new DragonoidConfig.Dragonoid();
   public DragonoidConfig.TrueDragonoid TrueDragonoid = new DragonoidConfig.TrueDragonoid();

   public String getFileName() {
      return "mysticism/race/dragonoid_config";
   }

   public static class Dragonoid extends Default {
      @Comment("Dragonoid Min MP.")
      public double minMP = 10000.0;
      @Comment("Dragonoid Max MP.")
      public double maxMP = 1000000.0;
      @Comment("Min amount of HP.")
      public int dragonoidMinHP = 30;
      @Comment("Min amount of HP.")
      public int dragonoidMaxHP = 1080;
      @Comment("Min amount of attack damage.")
      public double dragonoidMinAttackDamage = 1.0;
      @Comment("Min amount of attack damage.")
      public double dragonoidMaxAttackDamage = 5.0;
      @Comment("Min amount of attack speed.")
      public double dragonoidMinAttackSpeed = 4.0;
      @Comment("Max amount of attack speed.")
      public double dragonoidMaxAttackSpeed = 6.0;
      @Comment("Min amount of movement speed.")
      public double dragonoidMinMovementSpeed = 0.1F;
      @Comment("Max amount of movement speed.")
      public double dragonoidMaxMovementSpeed = 0.18F;
      @Comment("Minimal aura.")
      public double minAura = 5000.0;
      @Comment("Maximum aura.")
      public double maxAura = 5000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 5000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 5000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 0.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 20.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:dragon_ear", "tensura:dragon_eye");

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

   public static class TrueDragonoid extends Default {
      @Comment("EP requirement to evolve into True Dragonoid.")
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
      public double maxHealth = 0.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 20.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:divine_ki_release", "tensura:dragon_ear", "tensura:dragon_eye");

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
