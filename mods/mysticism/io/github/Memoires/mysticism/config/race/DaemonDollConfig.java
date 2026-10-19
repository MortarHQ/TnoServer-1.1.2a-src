package io.github.Memoires.mysticism.config.race;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import java.util.List;

@SyncToClient
public class DaemonDollConfig extends ManasConfig {
   public DaemonDollConfig.GreaterDoll GreaterDoll = new DaemonDollConfig.GreaterDoll();
   public DaemonDollConfig.ArchDoll ArchDoll = new DaemonDollConfig.ArchDoll();
   public DaemonDollConfig.ChaosDoll ChaosDoll = new DaemonDollConfig.ChaosDoll();
   public DaemonDollConfig.ChaosMetalloid ChaosMetalloid = new DaemonDollConfig.ChaosMetalloid();
   public DaemonDollConfig.DaemonDoll DaemonDoll = new DaemonDollConfig.DaemonDoll();
   public DaemonDollConfig.DevilDoll DevilDoll = new DaemonDollConfig.DevilDoll();

   public String getFileName() {
      return "mysticism/race/daemon_doll_config";
   }

   public static class ArchDoll extends Default {
      @Comment("EP requirement to evolve into Arch Daemon.")
      public double epRequirement = 140000.0;
      @Comment("Minimal aura.")
      public double minAura = 45000.0;
      @Comment("Maximum aura.")
      public double maxAura = 50000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 130000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 160000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 90.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 606.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.4F;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.2;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.6;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1;

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

   public static class ChaosDoll extends Default {
      @Comment("Minimal aura.")
      public double minAura = 125000.0;
      @Comment("Maximum aura.")
      public double maxAura = 325000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 275000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 600000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 680.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4606.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.6F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.8;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.04;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.4;
      @Comment("Bonus Armor.")
      public double armor = 5.0;

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

      public double getArmor() {
         return this.armor;
      }
   }

   public static class ChaosMetalloid extends Default {
      @Comment("Minimal aura.")
      public double minAura = 1005000.0;
      @Comment("Maximum aura.")
      public double maxAura = 1010000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1060000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1100000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1050.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 6606.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 5.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.8;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 1.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.08;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.8;
      @Comment("Bonus Armor")
      public double armor = 10.0;

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

      public double getArmor() {
         return this.armor;
      }
   }

   public static class DaemonDoll extends Default {
      @Comment("Minimal aura.")
      public double minAura = 105000.0;
      @Comment("Maximum aura.")
      public double maxAura = 310000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 250000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 550000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 630.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 3606.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.8;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.04;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.4;

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

   public static class DevilDoll extends Default {
      @Comment("Minimal aura.")
      public double minAura = 1005000.0;
      @Comment("Maximum aura.")
      public double maxAura = 1010000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1060000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1100000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1036.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 6606.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.7;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 1.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.08;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.8;

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

   public static class GreaterDoll extends Default {
      @Comment("Minimal aura.")
      public double minAura = 15000.0;
      @Comment("Maximum aura.")
      public double maxAura = 25000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 30000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 60000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 50.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 200.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.2F;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.4;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:magisteel_body");

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
