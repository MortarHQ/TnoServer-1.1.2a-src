package io.github.Memoires.mysticism.config.race.insect;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import java.util.List;

@SyncToClient
public class AntConfig extends ManasConfig {
   public AntConfig.Ant Ant = new AntConfig.Ant();
   public AntConfig.FireAnt FireAnt = new AntConfig.FireAnt();
   public AntConfig.FireAntInsectar FireAntInsectar = new AntConfig.FireAntInsectar();
   public AntConfig.FireAntSavant FireAntSavant = new AntConfig.FireAntSavant();
   public AntConfig.FlameSoulInsect FlameSoulInsect = new AntConfig.FlameSoulInsect();
   public AntConfig.DivineFireAnt DivineFireAnt = new AntConfig.DivineFireAnt();
   public AntConfig.HardshellAnt HardshellAnt = new AntConfig.HardshellAnt();
   public AntConfig.HardshellAntInsectar HardshellAntInsectar = new AntConfig.HardshellAntInsectar();
   public AntConfig.HardshellAntSavant HardshellAntSavant = new AntConfig.HardshellAntSavant();
   public AntConfig.EarthSoulInsect EarthSoulInsect = new AntConfig.EarthSoulInsect();
   public AntConfig.DivineHardshellAnt DivineHardshellAnt = new AntConfig.DivineHardshellAnt();

   public String getFileName() {
      return "mysticism/race/insect/ant_config";
   }

   public static class Ant extends Default {
      @Comment("Minimal aura.")
      public double minAura = 1000.0;
      @Comment("Maximum aura.")
      public double maxAura = 2000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 2000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 3000.0;
      @Comment("Bonus Size.")
      public double size = -0.75;
      @Comment("Bonus Max Health.")
      public double maxHealth = 15.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 85.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.8F;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = -0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = -0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("Bonus Armor")
      public double armor = 5.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal");

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

   public static class DivineFireAnt extends Default {
      @Comment("EP requirement to evolve into Divine Fire Ant.")
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
      public double maxHealth = 1230.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 7835.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.5;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("Bonus Armor")
      public double armor = 20.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "mysticism:spark", "tensura:divine_ki_release");

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

      public double getArmor() {
         return this.armor;
      }
   }

   public static class DivineHardshellAnt extends Default {
      @Comment("EP requirement to evolve into Earth Soul Insect.")
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
      public double maxHealth = 1780.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 9345.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.1F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 1.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1F;
      @Comment("Bonus Armor")
      public double armor = 25.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "mysticism:exoskeleton", "tensura:analytical_appraisal", "mysticism:clangorous_soul", "tensura:divine_ki_release"
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

      public double getArmor() {
         return this.armor;
      }
   }

   public static class EarthSoulInsect extends Default {
      @Comment("EP requirement to evolve into Earth Soul Insect.")
      public double epRequirement = 400000.0;
      @Comment("Ability requirement to evolve into Earth Soul Insect.")
      public String abilityRequirement = "tensura:earth_manipulation";
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 400000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 400000.0;
      @Comment("Bonus Size.")
      public double size = -0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 740.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 6265.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.25;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = -0.25;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.6F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = -0.005F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("Bonus Armor")
      public double armor = 17.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "mysticism:exoskeleton",
         "tensura:analytical_appraisal",
         "mysticism:clangorous_soul",
         "tensura:earth_attack_resistance",
         "tensura:earth_manipulation",
         "tensura:earth_transform"
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

      public double getArmor() {
         return this.armor;
      }
   }

   public static class FireAnt extends Default {
      @Comment("EP requirement to evolve into Fire Ant.")
      public double epRequirement = 10000.0;
      @Comment("Minimal aura.")
      public double minAura = 5250.0;
      @Comment("Maximum aura.")
      public double maxAura = 5250.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 5250.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 5250.0;
      @Comment("Bonus Size.")
      public double size = -0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 45.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 235.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = -0.2F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("Bonus Armor")
      public double armor = 7.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "mysticism:spark");

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

      public double getArmor() {
         return this.armor;
      }
   }

   public static class FireAntInsectar extends Default {
      @Comment("EP requirement to evolve into Fire Ant Insectar.")
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
      public double size = -0.5;
      @Comment("Bonus Max Health.")
      public double maxHealth = 205.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 935.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("Bonus Armor")
      public double armor = 10.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "mysticism:spark");

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

      public double getArmor() {
         return this.armor;
      }
   }

   public static class FireAntSavant extends Default {
      @Comment("EP requirement to evolve into Fire Ant Savant.")
      public double epRequirement = 400000.0;
      @Comment("The number of Bosses defeated to evolve into Fire Ant Savant.")
      public int bossRequirement = 4;
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 400000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 400000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 580.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 1935.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.2F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.4F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.015F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("Bonus Armor")
      public double armor = 15.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "mysticism:spark");

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

      public double getArmor() {
         return this.armor;
      }
   }

   public static class FlameSoulInsect extends Default {
      @Comment("EP requirement to evolve into Flame Soul Insect.")
      public double epRequirement = 400000.0;
      @Comment("The ability needed to evolve into Flame Soul Insect.")
      public String abilityRequirement = "tensura:flame_manipulation";
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 400000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 400000.0;
      @Comment("Bonus Size.")
      public double size = -0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 430.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 2395.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.25;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.1F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0125F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("Bonus Armor")
      public double armor = 12.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "mysticism:exoskeleton",
         "tensura:analytical_appraisal",
         "mysticism:spark",
         "tensura:flame_manipulation",
         "tensura:flame_attack_resistance",
         "tensura:flame_transform"
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

      public double getArmor() {
         return this.armor;
      }
   }

   public static class HardshellAnt extends Default {
      @Comment("EP requirement to evolve into Hardshell Ant.")
      public double epRequirement = 10000.0;
      @Comment("Minimal aura.")
      public double minAura = 5250.0;
      @Comment("Maximum aura.")
      public double maxAura = 5250.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 5250.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 5250.0;
      @Comment("Bonus Size.")
      public double size = -0.75;
      @Comment("Bonus Max Health.")
      public double maxHealth = 55.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 375.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.75;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = -0.4F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.5;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = -0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("Bonus Armor")
      public double armor = 10.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal");

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

   public static class HardshellAntInsectar extends Default {
      @Comment("EP requirement to evolve into Hardshell Ant Insectar.")
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
      public double size = -0.75;
      @Comment("Bonus Max Health.")
      public double maxHealth = 330.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 1235.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = -0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = -0.01F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("Bonus Armor")
      public double armor = 15.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "mysticism:clangorous_soul");

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

   public static class HardshellAntSavant extends Default {
      @Comment("EP requirement to evolve into Hardshell Ant Savant.")
      public double epRequirement = 400000.0;
      @Comment("The number of Bosses defeated to evolve into Hardshell Ant Savant.")
      public int bossRequirement = 4;
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 400000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 400000.0;
      @Comment("Bonus Size.")
      public double size = -0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 900.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4635.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.8F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.01F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("Bonus Armor")
      public double armor = 20.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "mysticism:clangorous_soul");

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

      public double getArmor() {
         return this.armor;
      }
   }
}
