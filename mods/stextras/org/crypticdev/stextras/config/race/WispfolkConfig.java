package org.crypticdev.stextras.config.race;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import java.util.List;

@SyncToClient
public class WispfolkConfig extends ManasConfig {
   @Comment("Dimensions where Wisp Mode phasing is disabled.")
   public List<String> blacklistedDimensions = List.of("tensura:labyrinth");
   @Comment("Blocks Wisp Mode cannot phase through. These blocks render red in Wisp vision.")
   public List<String> blacklistedBlocks = List.of("minecraft:bedrock");
   @Comment("If true and Open Parties and Claims is installed, Wisp Mode phasing only works in wilderness or claims the player can access.")
   public boolean opacSupport = false;
   public WispfolkConfig.Wispfolk Wispfolk = new WispfolkConfig.Wispfolk();
   public WispfolkConfig.GaleWisp GaleWisp = new WispfolkConfig.GaleWisp();
   public WispfolkConfig.ZephyrWisp ZephyrWisp = new WispfolkConfig.ZephyrWisp();
   public WispfolkConfig.DivineWisp DivineWisp = new WispfolkConfig.DivineWisp();

   public String getFileName() {
      return "stextras/race/wispfolk_config";
   }

   public static class DivineWisp extends Default {
      @Comment("EP requirement to evolve into Divine Wisp.")
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
      public double size = -0.15F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 980.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 6000.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 6.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.12F;
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

   public static class GaleWisp extends Default {
      @Comment("EP requirement to evolve into Gale Wisp.")
      public double epRequirement = 100000.0;
      @Comment("Minimal aura.")
      public double minAura = 2000.0;
      @Comment("Maximum aura.")
      public double maxAura = 2000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 5000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 5000.0;
      @Comment("Bonus Size.")
      public double size = -0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 30.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 300.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
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

   public static class Wispfolk extends Default {
      @Comment("Minimal aura.")
      public double minAura = 1000.0;
      @Comment("Maximum aura.")
      public double maxAura = 1000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 3000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 3000.0;
      @Comment("Bonus Size.")
      public double size = -0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 0.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 0.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.7F;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
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

   public static class ZephyrWisp extends Default {
      @Comment("EP requirement to evolve into Zephyr Wisp.")
      public double epRequirement = 400000.0;
      @Comment("Minimal aura.")
      public double minAura = 150000.0;
      @Comment("Maximum aura.")
      public double maxAura = 150000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 250000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 250000.0;
      @Comment("Bonus Size.")
      public double size = -0.15F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 580.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 3000.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.07F;
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
