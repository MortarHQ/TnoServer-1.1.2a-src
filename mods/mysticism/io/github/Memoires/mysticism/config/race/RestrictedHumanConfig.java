package io.github.Memoires.mysticism.config.race;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import java.util.List;

@SyncToClient
public class RestrictedHumanConfig extends ManasConfig {
   public RestrictedHumanConfig.RestrictedHuman RestrictedHuman = new RestrictedHumanConfig.RestrictedHuman();
   public RestrictedHumanConfig.RestrictedSaint RestrictedSaint = new RestrictedHumanConfig.RestrictedSaint();
   public RestrictedHumanConfig.BoundEnlightenment BoundEnlightenment = new RestrictedHumanConfig.BoundEnlightenment();
   public RestrictedHumanConfig.HeavenlyRestriction HeavenlyRestriction = new RestrictedHumanConfig.HeavenlyRestriction();

   public String getFileName() {
      return "mysticism/race/restricted_human_config";
   }

   public static class BoundEnlightenment extends Default {
      @Comment("EP requirement to evolve into Bound Enlightenment.")
      public double epRequirement = 800000.0;
      @Comment("The number of Bosses defeated to evolve into Bound Enlightenment.")
      public int bossRequirement = 4;
      @Comment("Minimal aura.")
      public double minAura = 800000.0;
      @Comment("Maximum aura.")
      public double maxAura = 800000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 100.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 100.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 720.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4500.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.75;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.075;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.75;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:restricted", "mysticism:tenacity");

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
   }

   public static class HeavenlyRestriction extends Default {
      @Comment("EP requirement to evolve into Heavenly Restriction.")
      public double epRequirement = 4000000.0;
      @Comment("Minimal aura.")
      public double minAura = 2000000.0;
      @Comment("Maximum aura.")
      public double maxAura = 2000000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 100.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 100.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1470.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 9000.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.05;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.45;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.15;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 1.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:restricted", "mysticism:tenacity", "tensura:divine_ki_release");

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

   public static class RestrictedHuman extends Default {
      @Comment("EP requirement to evolve into Restricted Human.")
      public double epRequirement = 50000.0;
      @Comment("The number of Bosses defeated to evolve into Restricted Human.")
      public int bossRequirement = 2;
      @Comment("Minimal aura.")
      public double minAura = 1520.0;
      @Comment("Maximum aura.")
      public double maxAura = 2280.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 50.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 70.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 10.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 0.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.1F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.05F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:restricted");

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

   public static class RestrictedSaint extends Default {
      @Comment("EP requirement to evolve into Restricted Saint.")
      public double epRequirement = 200000.0;
      @Comment("Minimal aura.")
      public double minAura = 280000.0;
      @Comment("Maximum aura.")
      public double maxAura = 280000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 100.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 100.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 120.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 450.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.3;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.15;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.015;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.15;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:restricted");

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
