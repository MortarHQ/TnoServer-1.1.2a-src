package org.crypticdev.stextras.config.race;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;

@SyncToClient
public class MysticFoxConfig extends ManasConfig {
   public MysticFoxConfig.MysticFox MysticFox = new MysticFoxConfig.MysticFox();
   public MysticFoxConfig.SoulBeast SoulBeast = new MysticFoxConfig.SoulBeast();
   public MysticFoxConfig.Ninetail Ninetail = new MysticFoxConfig.Ninetail();
   public MysticFoxConfig.DivineFox DivineFox = new MysticFoxConfig.DivineFox();

   public String getFileName() {
      return "stextras/race/mystic_fox_config";
   }

   public static class DivineFox extends Default {
      @Comment("EP requirement to evolve into Divine Fox.")
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
      public double maxHealth = 980.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 7000.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 6.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.1F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3F;
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

   public static class MysticFox extends Default {
      @Comment("Minimal aura.")
      public double minAura = 200.0;
      @Comment("Maximum aura.")
      public double maxAura = 600.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 3000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 12.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 0.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = -0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
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

   public static class Ninetail extends Default {
      @Comment("EP requirement to evolve into Ninetail.")
      public double epRequirement = 400000.0;
      @Comment("The number of Bosses defeated to evolve into Ninetail.")
      public int bossRequirement = 4;
      @Comment("Minimal aura.")
      public double minAura = 70000.0;
      @Comment("Maximum aura.")
      public double maxAura = 70000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 330000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 330000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 300.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4500.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 5.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.08F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;

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

   public static class SoulBeast extends Default {
      @Comment("EP requirement to evolve into Soul Beast.")
      public double epRequirement = 100000.0;
      @Comment("Minimal aura.")
      public double minAura = 30000.0;
      @Comment("Maximum aura.")
      public double maxAura = 30000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 70000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 70000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 160.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 440.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
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
}
