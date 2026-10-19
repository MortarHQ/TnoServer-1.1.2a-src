package io.github.Memoires.mysticism.config.race;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import java.util.List;

@SyncToClient
public class SculkConfig extends ManasConfig {
   public SculkConfig.SculkWorm SculkWorm = new SculkConfig.SculkWorm();
   public SculkConfig.SoulShrieker SoulShrieker = new SculkConfig.SoulShrieker();
   public SculkConfig.Warden Warden = new SculkConfig.Warden();
   public SculkConfig.SoulAberration SoulAberration = new SculkConfig.SoulAberration();
   public SculkConfig.ReaperAberration ReaperAberration = new SculkConfig.ReaperAberration();
   public SculkConfig.MoltenPerforator MoltenPerforator = new SculkConfig.MoltenPerforator();
   public SculkConfig.MagmaWorm MagmaWorm = new SculkConfig.MagmaWorm();
   public SculkConfig.EnflamedAberration EnflamedAberration = new SculkConfig.EnflamedAberration();
   public SculkConfig.ViolenceDeity ViolenceDeity = new SculkConfig.ViolenceDeity();
   public SculkConfig.ChargedPerforator ChargedPerforator = new SculkConfig.ChargedPerforator();
   public SculkConfig.OverloadingWorm OverloadingWorm = new SculkConfig.OverloadingWorm();
   public SculkConfig.LightningAberration LightningAberration = new SculkConfig.LightningAberration();
   public SculkConfig.DissonanceDeity DissonanceDeity = new SculkConfig.DissonanceDeity();

   public String getFileName() {
      return "mysticism/race/sculk_config";
   }

   public static class ChargedPerforator extends Default {
      @Comment("Quantity of Lightning Essence required to become a Charged Perforator as a Sculk Worm.")
      public int essenceRequired = 10;
      @Comment("EP requirement to evolve into Charged Perforator.")
      public double epRequirement = 4000.0;
      @Comment("Minimal aura.")
      public double minAura = 10000.0;
      @Comment("Maximum aura.")
      public double maxAura = 10000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 10000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 10000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 55.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 270.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 5.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.01F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:sense_soundwave", "tensura:lightning_manipulation", "tensura:electricity_resistance", "tensura:heat_resistance"
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

   public static class DissonanceDeity extends Default {
      @Comment("Quantity of Lightning Essence required to become a Dissonance Deity as a Lightning Aberration.")
      public int essenceRequired = 50;
      @Comment("EP requirement to evolve into a Dissonance Deity.")
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
      public double size = 0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1080.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 7450.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 5.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.2F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.8;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.05;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.4;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:sense_soundwave",
         "tensura:lightning_manipulation",
         "tensura:electricity_resistance",
         "tensura:heat_resistance",
         "mysticism:discharge",
         "tensura:lightning_domination",
         "mysticism:dissonance",
         "tensura:divine_ki_release"
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

   public static class EnflamedAberration extends Default {
      @Comment("Quantity of Flame Essence required to become a Enflamed Aberration as a Magma Worm.")
      public int essenceRequired = 20;
      @Comment("EP requirement to evolve into an Enflamed Aberration.")
      public double epRequirement = 400000.0;
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 400000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 400000.0;
      @Comment("Bonus Size.")
      public double size = 0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 580.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 580.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.2;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.6;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.025;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.25;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:sense_soundwave", "tensura:heat_resistance", "tensura:flame_breath", "tensura:heat_wave", "mysticism:hell_hall"
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

   public static class LightningAberration extends Default {
      @Comment("Quantity of Lightning Essence required to become a Lightning Aberration as a Overloading Worm.")
      public int essenceRequired = 30;
      @Comment("EP requirement to evolve into a Lightning Aberration.")
      public double epRequirement = 400000.0;
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 400000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 400000.0;
      @Comment("Bonus Size.")
      public double size = 0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 730.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 600.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.4;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.5;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0425;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.3;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:sense_soundwave",
         "tensura:lightning_manipulation",
         "tensura:electricity_resistance",
         "tensura:heat_resistance",
         "mysticism:discharge",
         "tensura:lightning_domination"
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

   public static class MagmaWorm extends Default {
      @Comment("Quantity of Flame Essence required to become a Magma Worm as a Molten Perforator.")
      public int essenceRequired = 10;
      @Comment("EP requirement to evolve into Magma Worm.")
      public double epRequirement = 100000.0;
      @Comment("Minimal aura.")
      public double minAura = 75000.0;
      @Comment("Maximum aura.")
      public double maxAura = 75000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 75000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 75000.0;
      @Comment("Bonus Size.")
      public double size = 0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 100.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 300.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.4F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.2F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:sense_soundwave", "tensura:heat_resistance", "tensura:flame_breath");

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

   public static class MoltenPerforator extends Default {
      @Comment("Quantity of Flame Essence required to become a Molten Perforator as a Sculk Worm.")
      public int essenceRequired = 3;
      @Comment("EP requirement to evolve into Magma Worm.")
      public double epRequirement = 4000.0;
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
      public double maxHealth = 30.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 90.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.4F;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.005F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:sense_soundwave", "tensura:heat_resistance", "tensura:flame_breath");

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

   public static class OverloadingWorm extends Default {
      @Comment("Quantity of Lightning Essence required to become an Overloading Worm as a Charged Perforator.")
      public int essenceRequired = 15;
      @Comment("EP requirement to evolve into Overloading Worm.")
      public double epRequirement = 100000.0;
      @Comment("Minimal aura.")
      public double minAura = 100000.0;
      @Comment("Maximum aura.")
      public double maxAura = 100000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 100000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 100000.0;
      @Comment("Bonus Size.")
      public double size = 0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 180.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 470.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.2F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.2F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:sense_soundwave", "tensura:lightning_manipulation", "tensura:electricity_resistance", "tensura:heat_resistance", "mysticism:discharge"
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

   public static class ReaperAberration extends Default {
      @Comment("EP requirement to evolve into a Reaper Aberration.")
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
      public double size = 2.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1280.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 9420.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 15.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 3.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 1.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.045;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:sense_soundwave", "tensura:pain_resistance", "tensura:ultrasonic_waves", "tensura:magic_resistance", "tensura:divine_ki_release"
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

   public static class SculkWorm extends Default {
      @Comment("Minimal aura.")
      public double minAura = 100.0;
      @Comment("Maximum aura.")
      public double maxAura = 250.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 800.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1200.0;
      @Comment("Bonus Size.")
      public double size = -0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = -12.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = -4.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 3.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:sense_soundwave");

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

   public static class SoulAberration extends Default {
      @Comment("EP requirement to evolve into a Soul Aberration.")
      public double epRequirement = 400000.0;
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 400000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 400000.0;
      @Comment("Bonus Size.")
      public double size = 1.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 930.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4730.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 11.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 2.75;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.75;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.4;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:sense_soundwave", "tensura:pain_resistance", "tensura:ultrasonic_waves", "tensura:magic_resistance"
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

   public static class SoulShrieker extends Default {
      @Comment("EP requirement to evolve into a Soul Shrieker.")
      public double epRequirement = 4000.0;
      @Comment("Minimal aura.")
      public double minAura = 1500.0;
      @Comment("Maximum aura.")
      public double maxAura = 1500.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 2500.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 2500.0;
      @Comment("Bonus Size.")
      public double size = -0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 4.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 28.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 3.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.2F;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:sense_soundwave", "tensura:pain_resistance", "tensura:ultrasonic_waves");

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

   public static class ViolenceDeity extends Default {
      @Comment("Quantity of Flame Essence required to become a Violence Deity as an Enflamed Aberration.")
      public int essenceRequired = 30;
      @Comment("EP requirement to evolve into a Violence Deity.")
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
      public double size = 0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 980.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 6800.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 1.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.035;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.4;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:sense_soundwave", "tensura:heat_resistance", "tensura:flame_breath", "tensura:heat_wave", "mysticism:hell_hall", "tensura:divine_ki_release"
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

   public static class Warden extends Default {
      @Comment("EP requirement to evolve into a Warden.")
      public double epRequirement = 50000.0;
      @Comment("Minimal aura.")
      public double minAura = 35000.0;
      @Comment("Maximum aura.")
      public double maxAura = 35000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 65000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 65000.0;
      @Comment("Bonus Size.")
      public double size = 0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 480.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 980.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 7.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 2.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.5;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.025;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:sense_soundwave", "tensura:pain_resistance", "tensura:ultrasonic_waves");

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
