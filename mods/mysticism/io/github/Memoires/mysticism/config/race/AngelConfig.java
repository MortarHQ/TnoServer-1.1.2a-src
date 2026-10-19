package io.github.Memoires.mysticism.config.race;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import java.util.List;

@SyncToClient
public class AngelConfig extends ManasConfig {
   public AngelConfig.LesserAngel LesserAngel = new AngelConfig.LesserAngel();
   public AngelConfig.GreaterAngel GreaterAngel = new AngelConfig.GreaterAngel();
   public AngelConfig.ArchAngel ArchAngel = new AngelConfig.ArchAngel();
   public AngelConfig.Cherub Cherub = new AngelConfig.Cherub();
   public AngelConfig.Seraph Seraph = new AngelConfig.Seraph();
   public AngelConfig.FallenLesserAngel FallenLesserAngel = new AngelConfig.FallenLesserAngel();
   public AngelConfig.FallenGreaterAngel FallenGreaterAngel = new AngelConfig.FallenGreaterAngel();
   public AngelConfig.FallenArchAngel FallenArchAngel = new AngelConfig.FallenArchAngel();
   public AngelConfig.FallenCherub FallenCherub = new AngelConfig.FallenCherub();
   public AngelConfig.FallenSeraph FallenSeraph = new AngelConfig.FallenSeraph();
   public AngelConfig.LesserFallen LesserFallen = new AngelConfig.LesserFallen();
   public AngelConfig.GreaterFallen GreaterFallen = new AngelConfig.GreaterFallen();
   public AngelConfig.ArchFallen ArchFallen = new AngelConfig.ArchFallen();
   public AngelConfig.FallenLord FallenLord = new AngelConfig.FallenLord();
   public AngelConfig.Fallen Fallen = new AngelConfig.Fallen();
   public AngelConfig.Tengu Tengu = new AngelConfig.Tengu();
   public AngelConfig.TenguSaint TenguSaint = new AngelConfig.TenguSaint();
   public AngelConfig.DivineTengu DivineTengu = new AngelConfig.DivineTengu();

   public String getFileName() {
      return "mysticism/race/angel_config";
   }

   public static class ArchAngel extends Default {
      @Comment("EP requirement to evolve into an Arch Angel.")
      public double epRequirement = 140000.0;
      @Comment("Minimal aura.")
      public double minAura = 100000.0;
      @Comment("Maximum aura.")
      public double maxAura = 105000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 40000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 45000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 100.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 580.0;
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
      public List<String> intrinsicSkills = List.of(
         "tensura:magic_resistance", "tensura:possession", "mysticism:light_manipulation", "tensura:physical_attack_resistance"
      );

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

   public static class ArchFallen extends Default {
      @Comment("EP requirement to evolve into a Fallen Arch Angel.")
      public double epRequirement = 140000.0;
      @Comment("Minimal aura.")
      public double minAura = 70000.0;
      @Comment("Maximum aura.")
      public double maxAura = 75000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 120000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 145000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 110.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 600.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.3F;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.4;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.4;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:magic_resistance", "mysticism:darkness_manipulation", "tensura:physical_attack_resistance");

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

   public static class Cherub extends Default {
      @Comment("Minimal aura.")
      public double minAura = 205000.0;
      @Comment("Maximum aura.")
      public double maxAura = 215000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 175000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 180000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 620.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 3702.0;
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
      public List<String> intrinsicSkills = List.of(
         "tensura:magic_resistance",
         "tensura:possession",
         "mysticism:light_manipulation",
         "tensura:physical_attack_resistance",
         "tensura:holy_attack_resistance"
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

   public static class DivineTengu extends Default {
      @Comment("EP requirement to evolve into a Divine Tengu.")
      public double epRequirement = 2000000.0;
      @Comment("Minimal aura.")
      public double minAura = 1500000.0;
      @Comment("Maximum aura.")
      public double maxAura = 2800000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1500000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 2800000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1080.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 6380.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 5.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.9;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 1.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.08;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.8;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:magic_resistance",
         "tensura:possession",
         "mysticism:light_manipulation",
         "tensura:beast_transformation",
         "tensura:self_regeneration",
         "tensura:godwolf_sense",
         "tensura:holy_attack_resistance",
         "tensura:divine_ki_release"
      );

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

   public static class Fallen extends Default {
      @Comment("Minimal aura.")
      public double minAura = 1000000.0;
      @Comment("Maximum aura.")
      public double maxAura = 1000000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1200000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1300000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1080.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 6350.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.5;
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
         "mysticism:darkness_manipulation",
         "tensura:physical_attack_resistance",
         "tensura:darkness_attack_resistance",
         "tensura:divine_ki_release",
         "mysticism:darkness_domination"
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

   public static class FallenArchAngel extends Default {
      @Comment("Quantity of demon essence required to become a fallen arch angel as an arch angel.")
      public int essenceRequired = 5;
      @Comment("EP requirement to evolve into a Fallen Arch Angel.")
      public double epRequirement = 140000.0;
      @Comment("Minimal aura.")
      public double minAura = 70000.0;
      @Comment("Maximum aura.")
      public double maxAura = 75000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 105000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 110000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 100.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 580.0;
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
      public List<String> intrinsicSkills = List.of("tensura:magic_resistance", "mysticism:light_manipulation", "tensura:physical_attack_resistance");

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

   public static class FallenCherub extends Default {
      @Comment("Quantity of demon essence required to become a fallen cherub as a cherub.")
      public int essenceRequired = 7;
      @Comment("Minimal aura.")
      public double minAura = 165000.0;
      @Comment("Maximum aura.")
      public double maxAura = 170000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 205000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 215000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 620.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 3702.0;
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
      public List<String> intrinsicSkills = List.of("tensura:magic_resistance", "mysticism:light_manipulation", "tensura:physical_attack_resistance");

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

   public static class FallenGreaterAngel extends Default {
      @Comment("Quantity of demon essence required to become a fallen greater angel as a greater angel.")
      public int essenceRequired = 3;
      @Comment("EP requirement to evolve into Fallen Greater Angel.")
      public double epRequirement = 20000.0;
      @Comment("Minimal aura.")
      public double minAura = 25000.0;
      @Comment("Maximum aura.")
      public double maxAura = 30000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 35000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 45000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 60.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 200.0;
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
      public List<String> intrinsicSkills = List.of("tensura:magic_resistance", "mysticism:light_manipulation");

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

   public static class FallenLesserAngel extends Default {
      @Comment("Quantity of demon essence required to become a fallen lesser angel as a lesser angel.")
      public int essenceRequired = 1;
      @Comment("Minimal aura.")
      public double minAura = 4500.0;
      @Comment("Maximum aura.")
      public double maxAura = 5500.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 5500.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 7500.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 20.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 90.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.4F;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:magic_resistance", "mysticism:light_manipulation");

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

   public static class FallenLord extends Default {
      @Comment("EP requirement to evolve into a Fallen Arch Angel.")
      public double epRequirement = 300000.0;
      @Comment("Minimal aura.")
      public double minAura = 165000.0;
      @Comment("Maximum aura.")
      public double maxAura = 170000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 225000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 250000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 640.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 3742.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.8F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.6;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.04;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.4;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:magic_resistance", "mysticism:darkness_manipulation", "tensura:physical_attack_resistance", "tensura:darkness_attack_resistance"
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

   public static class FallenSeraph extends Default {
      @Comment("Quantity of demon essence required to become a fallen seraph as a seraph.")
      public int essenceRequired = 10;
      @Comment("Minimal aura.")
      public double minAura = 1000000.0;
      @Comment("Maximum aura.")
      public double maxAura = 1000000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1010000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1050000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1060.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 6300.0;
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
         "mysticism:light_manipulation",
         "tensura:physical_attack_resistance",
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

   public static class GreaterAngel extends Default {
      @Comment("EP requirement to evolve into a Greater Angel.")
      public double epRequirement = 20000.0;
      @Comment("Minimal aura.")
      public double minAura = 35000.0;
      @Comment("Maximum aura.")
      public double maxAura = 45000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 15000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 25000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 60.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 200.0;
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
      public List<String> intrinsicSkills = List.of("tensura:magic_resistance", "tensura:possession", "mysticism:light_manipulation");

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

   public static class GreaterFallen extends Default {
      @Comment("EP requirement to evolve into Fallen Greater Angel.")
      public double epRequirement = 20000.0;
      @Comment("Minimal aura.")
      public double minAura = 25000.0;
      @Comment("Maximum aura.")
      public double maxAura = 30000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 40000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 55000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 80.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 220.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.2F;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.2F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.2F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:magic_resistance", "mysticism:darkness_manipulation");

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

   public static class LesserAngel extends Default {
      @Comment("Minimal aura.")
      public double minAura = 5500.0;
      @Comment("Maximum aura.")
      public double maxAura = 6500.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1500.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 3500.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 20.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 90.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.4F;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:magic_resistance", "tensura:possession", "mysticism:light_manipulation");

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

   public static class LesserFallen extends Default {
      @Comment("Minimal aura.")
      public double minAura = 4500.0;
      @Comment("Maximum aura.")
      public double maxAura = 5500.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 7500.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 9500.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 30.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 100.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.1F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:magic_resistance", "mysticism:darkness_manipulation");

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

   public static class Seraph extends Default {
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
      public double maxHealth = 1060.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 6300.0;
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
         "mysticism:light_manipulation",
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

   public static class Tengu extends Default {
      @Comment("Minimal aura.")
      public double minAura = 100000.0;
      @Comment("Maximum aura.")
      public double maxAura = 150000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 100000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 150000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 130.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 600.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.4;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.6;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.05;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:magic_resistance",
         "tensura:possession",
         "mysticism:light_manipulation",
         "tensura:beast_transformation",
         "tensura:self_regeneration",
         "tensura:godwolf_sense"
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

   public static class TenguSaint extends Default {
      @Comment("EP requirement to evolve into a Tengu Saint.")
      public double epRequirement = 400000.0;
      @Comment("Minimal aura.")
      public double minAura = 800000.0;
      @Comment("Maximum aura.")
      public double maxAura = 900000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 800000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 900000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 650.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 3800.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.8F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.8;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.04;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.4;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:magic_resistance",
         "tensura:possession",
         "mysticism:light_manipulation",
         "tensura:beast_transformation",
         "tensura:self_regeneration",
         "tensura:godwolf_sense",
         "tensura:holy_attack_resistance"
      );

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
