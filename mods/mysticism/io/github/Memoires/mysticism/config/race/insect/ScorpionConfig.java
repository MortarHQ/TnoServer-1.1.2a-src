package io.github.Memoires.mysticism.config.race.insect;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import java.util.List;

@SyncToClient
public class ScorpionConfig extends ManasConfig {
   public ScorpionConfig.Scorpion Scorpion = new ScorpionConfig.Scorpion();
   public ScorpionConfig.LoxodromeScorpion LoxodromeScorpion = new ScorpionConfig.LoxodromeScorpion();
   public ScorpionConfig.LoxodromeScorpionInsectar LoxodromeScorpionInsectar = new ScorpionConfig.LoxodromeScorpionInsectar();
   public ScorpionConfig.LoxodromeScorpionSavant LoxodromeScorpionSavant = new ScorpionConfig.LoxodromeScorpionSavant();
   public ScorpionConfig.PoisonSoulInsect PoisonSoulInsect = new ScorpionConfig.PoisonSoulInsect();
   public ScorpionConfig.DivineLoxodromeScorpion DivineLoxodromeScorpion = new ScorpionConfig.DivineLoxodromeScorpion();
   public ScorpionConfig.SingularityScorpion SingularityScorpion = new ScorpionConfig.SingularityScorpion();
   public ScorpionConfig.SingularityScorpionInsectar SingularityScorpionInsectar = new ScorpionConfig.SingularityScorpionInsectar();
   public ScorpionConfig.SingularityScorpionSavant SingularityScorpionSavant = new ScorpionConfig.SingularityScorpionSavant();
   public ScorpionConfig.GravitySoulInsect GravitySoulInsect = new ScorpionConfig.GravitySoulInsect();
   public ScorpionConfig.DivineSingularityScorpion DivineSingularityScorpion = new ScorpionConfig.DivineSingularityScorpion();

   public String getFileName() {
      return "mysticism/race/insect/scorpion_config";
   }

   public static class DivineLoxodromeScorpion extends Default {
      @Comment("EP requirement to evolve into Divine Loxodrome Scorpion.")
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
      public double size = -0.1F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 650.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4060.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 5.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.04F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:poison", "tensura:divine_ki_release", "mysticism:lethal_poison"
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

   public static class DivineSingularityScorpion extends Default {
      @Comment("EP requirement to evolve into Divine Singularity Scorpion.")
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
      public double size = -0.1F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 530.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 3580.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.4F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.05F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:poison", "tensura:divine_ki_release");

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

   public static class GravitySoulInsect extends Default {
      @Comment("EP requirement to evolve into Gravity Soul Insect.")
      public double epRequirement = 400000.0;
      @Comment("Ability requirement to evolve into Gravity Soul Insect.")
      public String abilityRequirement = "tensura:gravity_manipulation";
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 400000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 400000.0;
      @Comment("Bonus Size.")
      public double size = -0.2F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 210.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 1990.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.3F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.025F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.04F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "mysticism:exoskeleton",
         "tensura:analytical_appraisal",
         "tensura:poison",
         "tensura:gravity_attack_resistance",
         "tensura:gravity_manipulation",
         "mysticism:gravity_flux"
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

   public static class LoxodromeScorpion extends Default {
      @Comment("EP requirement to evolve into Loxodrome Scorpion.")
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
      public double size = -0.6F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 14.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 110.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.015F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:poison");

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

   public static class LoxodromeScorpionInsectar extends Default {
      @Comment("EP requirement to evolve into Loxodrome Scorpion Insectar.")
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
      public double size = -0.4F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 120.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 730.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:poison");

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

   public static class LoxodromeScorpionSavant extends Default {
      @Comment("EP requirement to evolve into Loxodrome Scorpion Savant.")
      public double epRequirement = 400000.0;
      @Comment("The number of Bosses defeated to evolve into Loxodrome Scorpion Savant.")
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
      public double size = -0.2F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 340.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 1680.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:poison");

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

   public static class PoisonSoulInsect extends Default {
      @Comment("EP requirement to evolve into Poison Soul Insect.")
      public double epRequirement = 400000.0;
      @Comment("Ability requirement to evolve into Poison Soul Insect.")
      public String abilityRequirement = "tensura:ranged_barrier";
      @Comment("Minimal aura.")
      public double minAura = 400000.0;
      @Comment("Maximum aura.")
      public double maxAura = 400000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 400000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 400000.0;
      @Comment("Bonus Size.")
      public double size = -0.2F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 280.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 2200.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.025F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:poison", "tensura:poison_resistance", "mysticism:poison_transform"
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

   public static class Scorpion extends Default {
      @Comment("Minimal aura.")
      public double minAura = 1000.0;
      @Comment("Maximum aura.")
      public double maxAura = 2000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 2000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 3000.0;
      @Comment("Bonus Size.")
      public double size = -0.7F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 5.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 40.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:poison");

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

   public static class SingularityScorpion extends Default {
      @Comment("EP requirement to evolve into Singularity Scorpion.")
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
      public double size = -0.6F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 12.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 100.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.2F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.02F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:poison");

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

   public static class SingularityScorpionInsectar extends Default {
      @Comment("EP requirement to evolve into Singularity Scorpion Insectar.")
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
      public double size = -0.4F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 70.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 680.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.3F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:poison");

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

   public static class SingularityScorpionSavant extends Default {
      @Comment("EP requirement to evolve into Singularity Scorpion Savant.")
      public double epRequirement = 400000.0;
      @Comment("The number of Bosses defeated to evolve into Singularity Scorpion Savant.")
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
      public double size = -0.2F;
      @Comment("Bonus Max Health.")
      public double maxHealth = 250.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 1420.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.4F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.045F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.0;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("mysticism:exoskeleton", "tensura:analytical_appraisal", "tensura:poison");

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
}
