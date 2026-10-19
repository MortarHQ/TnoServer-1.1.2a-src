package io.github.Memoires.mysticism.config.race;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import java.util.List;

@SyncToClient
public class WyrmConfig extends ManasConfig {
   public WyrmConfig.AttunedWyrm AttunedWyrm = new WyrmConfig.AttunedWyrm();
   public WyrmConfig.LesserGlacierWyrm LesserGlacierWyrm = new WyrmConfig.LesserGlacierWyrm();
   public WyrmConfig.GreaterGlacierWyrm GreaterGlacierWyrm = new WyrmConfig.GreaterGlacierWyrm();
   public WyrmConfig.RimeFangDrake RimeFangDrake = new WyrmConfig.RimeFangDrake();
   public WyrmConfig.RimeblightHydra RimeblightHydra = new WyrmConfig.RimeblightHydra();
   public WyrmConfig.FrostcoilSeaSerpent FrostcoilSeaSerpent = new WyrmConfig.FrostcoilSeaSerpent();
   public WyrmConfig.FrostwroughtLeviathan FrostwroughtLeviathan = new WyrmConfig.FrostwroughtLeviathan();
   public WyrmConfig.LesserPyreWyrm LesserPyreWyrm = new WyrmConfig.LesserPyreWyrm();
   public WyrmConfig.GreaterPyreWyrm GreaterPyreWyrm = new WyrmConfig.GreaterPyreWyrm();
   public WyrmConfig.ScorchtailSalamander ScorchtailSalamander = new WyrmConfig.ScorchtailSalamander();
   public WyrmConfig.ScorchtalonWyvern ScorchtalonWyvern = new WyrmConfig.ScorchtalonWyvern();
   public WyrmConfig.SunfireLindwurm SunfireLindwurm = new WyrmConfig.SunfireLindwurm();
   public WyrmConfig.SundeityLoong SundeityLoong = new WyrmConfig.SundeityLoong();

   public String getFileName() {
      return "mysticism/race/wyrm_config";
   }

   public static class AttunedWyrm extends Default {
      @Comment("Minimal aura.")
      public double minAura = 500.0;
      @Comment("Maximum aura.")
      public double maxAura = 1000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1500.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 2000.0;
      @Comment("Bonus Size.")
      public double size = 0.3F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 0.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 0.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 1.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of();

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

   public static class FrostcoilSeaSerpent extends Default {
      @Comment("Minimal aura.")
      public double minAura = 200000.0;
      @Comment("Maximum aura.")
      public double maxAura = 200000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 150000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 150000.0;
      @Comment("Bonus Size.")
      public double size = 1.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 340.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 1360.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 7.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.06F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.03F;
      @Comment("EP requirement to evolve into Frostcoil Sea Serpent.")
      public double epRequirement = 200000.0;
      @Comment("The first ability needed to evolve into Frostcoil Sea Serpent.")
      public String abilityRequirement = "tensura:water_manipulation";
      @Comment("Does the ffirst ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("The second ability needed to evolve into Frostcoil Sea Serpent.")
      public String ability2Requirement = "mysticism:cryogenic_cessation";
      @Comment("Does the second ability need to be mastered? (true/false)")
      public boolean ability2MasteryRequirement = true;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:cold_resistance",
         "mysticism:cryogenic_cessation",
         "tensura:dragon_ear",
         "tensura:dragon_skin",
         "tensura:ultrasonic_waves",
         "tensura:hydraulic_propulsion"
      );

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public String getAbilityRequirement() {
         return this.abilityRequirement;
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

   public static class FrostwroughtLeviathan extends Default {
      @Comment("Minimal aura.")
      public double minAura = 1200000.0;
      @Comment("Maximum aura.")
      public double maxAura = 1200000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 800000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 800000.0;
      @Comment("Bonus Size.")
      public double size = 1.2F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 880.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 3520.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 14.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.08F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.05F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.05F;
      @Comment("EP requirement to evolve into a Frostwrought Leviathan.")
      public double epRequirement = 1500000.0;
      @Comment("The first ability needed to evolve into a Frostwrought Leviathan.")
      public String abilityRequirement = "tensura:water_domination";
      @Comment("Does the first ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("The second ability needed to evolve into a Frostwrought Leviathan.")
      public String ability2Requirement = "tensura:dragon_ear";
      @Comment("Does the second ability need to be mastered? (true/false)")
      public boolean ability2MasteryRequirement = true;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:cold_resistance",
         "mysticism:cryogenic_cessation",
         "tensura:dragon_ear",
         "tensura:dragon_skin",
         "tensura:ultrasonic_waves",
         "tensura:hydraulic_propulsion",
         "tensura:dragon_mode",
         "tensura:sound_manipulation",
         "tensura:divine_ki_release"
      );

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public String getAbilityRequirement() {
         return this.abilityRequirement;
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

   public static class GreaterGlacierWyrm extends Default {
      @Comment("Minimal aura.")
      public double minAura = 8000.0;
      @Comment("Maximum aura.")
      public double maxAura = 8000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 10000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 10000.0;
      @Comment("Bonus Size.")
      public double size = 0.8F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 180.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 660.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 2.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.05F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.02F;
      @Comment("Amount of Ice Essence needed eaten to evolve into a Greater Glacier Wyrm.")
      public int iceEssenceAmount = 10;
      @Comment("EP requirement to evolve into Greater Glacier Wyrm.")
      public double epRequirement = 30000.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:cold_resistance", "mysticism:cryogenic_cessation");

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

   public static class GreaterPyreWyrm extends Default {
      @Comment("Minimal aura.")
      public double minAura = 5000.0;
      @Comment("Maximum aura.")
      public double maxAura = 8000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 5000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 10000.0;
      @Comment("Bonus Size.")
      public double size = 0.8F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 180.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 720.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.8F;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.08F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.03F;
      @Comment("Amount of Flame Essence needed eaten to evolve into a Greater Pyre Wyrm.")
      public int flameEssenceAmount = 10;
      @Comment("EP requirement to evolve into Greater Pyre Wyrm.")
      public double epRequirement = 30000.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:flame_attack_resistance", "mysticism:profaned_prominence");

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

   public static class LesserGlacierWyrm extends Default {
      @Comment("Minimal aura.")
      public double minAura = 1000.0;
      @Comment("Maximum aura.")
      public double maxAura = 1500.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 2000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 2500.0;
      @Comment("Bonus Size.")
      public double size = 0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 40.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 160.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.04F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.03F;
      @Comment("Amount of Ice Essence needed eaten to evolve into a Lesser Glacier Wyrm.")
      public int iceEssenceAmount = 1;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:cold_resistance");

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

   public static class LesserPyreWyrm extends Default {
      @Comment("Minimal aura.")
      public double minAura = 1500.0;
      @Comment("Maximum aura.")
      public double maxAura = 2000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 2000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 2500.0;
      @Comment("Bonus Size.")
      public double size = 0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 30.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 120.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.04F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.03F;
      @Comment("Amount of Flame Essence needed eaten to evolve into a Lesser Pyre Wyrm.")
      public int flameEssenceAmount = 1;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:flame_attack_resistance");

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

   public static class RimeFangDrake extends Default {
      @Comment("Minimal aura.")
      public double minAura = 150000.0;
      @Comment("Maximum aura.")
      public double maxAura = 150000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 200000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 200000.0;
      @Comment("Bonus Size.")
      public double size = 1.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 380.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 1520.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 6.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.8F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.06F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.03F;
      @Comment("EP requirement to evolve into Rimefang Drake.")
      public double epRequirement = 200000.0;
      @Comment("The first ability needed to evolve into Rimefang Drake.")
      public String abilityRequirement = "mysticism:ice_manipulation";
      @Comment("Does the first ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("The second ability needed to evolve into Rimefang Drake.")
      public String ability2Requirement = "mysticism:cryogenic_cessation";
      @Comment("Does the second ability need to be mastered? (true/false)")
      public boolean ability2MasteryRequirement = true;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:cold_resistance", "mysticism:cryogenic_cessation", "tensura:dragon_eye", "tensura:dragon_skin");

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public String getAbilityRequirement() {
         return this.abilityRequirement;
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

   public static class RimeblightHydra extends Default {
      @Comment("Minimal aura.")
      public double minAura = 800000.0;
      @Comment("Maximum aura.")
      public double maxAura = 800000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1200000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1200000.0;
      @Comment("Bonus Size.")
      public double size = 1.2F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1080.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4320.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 5.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.08F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.05F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.05F;
      @Comment("EP requirement to evolve into a Rimeblight Hydra.")
      public double epRequirement = 1500000.0;
      @Comment("The first ability needed to evolve into a Rimeblight Hydra.")
      public String abilityRequirement = "mysticism:ice_domination";
      @Comment("Does the first ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("The second ability needed to evolve into a Rimeblight Hydra.")
      public String ability2Requirement = "tensura:dragon_eye";
      @Comment("Does the second ability need to be mastered? (true/false)")
      public boolean ability2MasteryRequirement = true;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:cold_resistance",
         "mysticism:cryogenic_cessation",
         "tensura:dragon_eye",
         "tensura:dragon_skin",
         "tensura:dragon_mode",
         "tensura:poisonous_breath",
         "tensura:paralysing_breath",
         "tensura:infinite_regeneration",
         "tensura:divine_ki_release"
      );

      public String getAbilityRequirement() {
         return this.abilityRequirement;
      }

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

   public static class ScorchtailSalamander extends Default {
      @Comment("Minimal aura.")
      public double minAura = 100000.0;
      @Comment("Maximum aura.")
      public double maxAura = 100000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 150000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 150000.0;
      @Comment("Bonus Size.")
      public double size = 0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 380.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 1520.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.8F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.09F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.03F;
      @Comment("EP requirement to evolve into Scorchtail Salamander.")
      public double epRequirement = 180000.0;
      @Comment("The first ability needed to evolve into Scorchtail Salamander.")
      public String abilityRequirement = "tensura:flame_manipulation";
      @Comment("Does the ffirst ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("The second ability needed to evolve into Scorchtail Salamander.")
      public String ability2Requirement = "mysticism:profaned_prominence";
      @Comment("Does the second ability need to be mastered? (true/false)")
      public boolean ability2MasteryRequirement = true;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:flame_attack_resistance", "mysticism:profaned_prominence", "tensura:dragon_skin", "tensura:dragon_eye", "tensura:heat_resistance"
      );

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public String getAbilityRequirement() {
         return this.abilityRequirement;
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

   public static class ScorchtalonWyvern extends Default {
      @Comment("Minimal aura.")
      public double minAura = 750000.0;
      @Comment("Maximum aura.")
      public double maxAura = 750000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1500000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1500000.0;
      @Comment("Bonus Size.")
      public double size = 1.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 780.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 3120.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 6.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.05F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.05F;
      @Comment("EP requirement to evolve into a Scorhtalon Wyvern.")
      public double epRequirement = 1500000.0;
      @Comment("The first ability needed to evolve into a Scorhtalon Wyvern.")
      public String abilityRequirement = "tensura:flame_domination";
      @Comment("Does the first ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("The second ability needed to evolve into a Scorhtalon Wyvern.")
      public String ability2Requirement = "tensura:dragon_skin";
      @Comment("Does the second ability need to be mastered? (true/false)")
      public boolean ability2MasteryRequirement = true;
      @Comment("Flight Boost Power.")
      public float flightBoost = 0.5F;
      @Comment("Flight Boost Cooldown.")
      public int flightCooldown = 3;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:flame_attack_resistance",
         "mysticism:profaned_prominence",
         "tensura:dragon_skin",
         "tensura:dragon_eye",
         "tensura:heat_resistance",
         "tensura:dragon_mode",
         "tensura:divine_ki_release"
      );

      public float getFlightBoost() {
         return this.flightBoost;
      }

      public int getFlightCooldown() {
         return this.flightCooldown;
      }

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public String getAbilityRequirement() {
         return this.abilityRequirement;
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

   public static class SundeityLoong extends Default {
      @Comment("Minimal aura.")
      public double minAura = 600000.0;
      @Comment("Maximum aura.")
      public double maxAura = 600000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1700000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1700000.0;
      @Comment("Bonus Size.")
      public double size = 3.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 920.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 3680.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 7.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 5.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.05F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.05F;
      @Comment("EP requirement to evolve into a Sundeity Loong.")
      public double epRequirement = 1500000.0;
      @Comment("The first ability needed to evolve into a Sundeity Loong.")
      public String abilityRequirement = "mysticism:light_domination";
      @Comment("Does the first ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("The second ability needed to evolve into a Sundeity Loong.")
      public String ability2Requirement = "tensura:dragon_skin";
      @Comment("Does the second ability need to be mastered? (true/false)")
      public boolean ability2MasteryRequirement = true;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:flame_attack_resistance",
         "mysticism:profaned_prominence",
         "tensura:dragon_ear",
         "tensura:dragon_skin",
         "tensura:flame_transform",
         "tensura:dragon_mode",
         "tensura:divine_ki_release"
      );

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public String getAbilityRequirement() {
         return this.abilityRequirement;
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

   public static class SunfireLindwurm extends Default {
      @Comment("Minimal aura.")
      public double minAura = 180000.0;
      @Comment("Maximum aura.")
      public double maxAura = 180000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 140000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 140000.0;
      @Comment("Bonus Size.")
      public double size = 1.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 440.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 1760.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 6.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 5.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.09F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.03F;
      @Comment("EP requirement to evolve into Sunfire Lindwurm.")
      public double epRequirement = 180000.0;
      @Comment("The first ability needed to evolve into Sunfire Lindwurm.")
      public String abilityRequirement = "mysticism:light_manipulation";
      @Comment("Does the ffirst ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("The second ability needed to evolve into Sunfire Lindwurm.")
      public String ability2Requirement = "mysticism:profaned_prominence";
      @Comment("Does the second ability need to be mastered? (true/false)")
      public boolean ability2MasteryRequirement = true;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:flame_attack_resistance", "mysticism:profaned_prominence", "tensura:dragon_ear", "tensura:dragon_skin", "tensura:flame_transform"
      );

      public double getEpRequirement() {
         return this.epRequirement;
      }

      public String getAbilityRequirement() {
         return this.abilityRequirement;
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
