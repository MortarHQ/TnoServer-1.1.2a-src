package io.github.Memoires.mysticism.config.race.direwolf;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import java.util.List;

@SyncToClient
public class DirewolfConfig extends ManasConfig {
   public DirewolfConfig.Direwolf Direwolf = new DirewolfConfig.Direwolf();
   public DirewolfConfig.BlackFang BlackFang = new DirewolfConfig.BlackFang();
   public DirewolfConfig.MysticalBlackFang MysticalBlackFang = new DirewolfConfig.MysticalBlackFang();
   public DirewolfConfig.MagicSpiritWolf MagicSpiritWolf = new DirewolfConfig.MagicSpiritWolf();
   public DirewolfConfig.BlueFang BlueFang = new DirewolfConfig.BlueFang();
   public DirewolfConfig.MysticalBlueFang MysticalBlueFang = new DirewolfConfig.MysticalBlueFang();
   public DirewolfConfig.OceanSpiritWolf OceanSpiritWolf = new DirewolfConfig.OceanSpiritWolf();
   public DirewolfConfig.BrownFang BrownFang = new DirewolfConfig.BrownFang();
   public DirewolfConfig.MysticalBrownFang MysticalBrownFang = new DirewolfConfig.MysticalBrownFang();
   public DirewolfConfig.MountainSpiritWolf MountainSpiritWolf = new DirewolfConfig.MountainSpiritWolf();
   public DirewolfConfig.GreenFang GreenFang = new DirewolfConfig.GreenFang();
   public DirewolfConfig.MysticalGreenFang MysticalGreenFang = new DirewolfConfig.MysticalGreenFang();
   public DirewolfConfig.GaleSpiritWolf GaleSpiritWolf = new DirewolfConfig.GaleSpiritWolf();
   public DirewolfConfig.PurpleFang PurpleFang = new DirewolfConfig.PurpleFang();
   public DirewolfConfig.MysticalPurpleFang MysticalPurpleFang = new DirewolfConfig.MysticalPurpleFang();
   public DirewolfConfig.PlanetSpiritWolf PlanetSpiritWolf = new DirewolfConfig.PlanetSpiritWolf();
   public DirewolfConfig.RedFang RedFang = new DirewolfConfig.RedFang();
   public DirewolfConfig.MysticalRedFang MysticalRedFang = new DirewolfConfig.MysticalRedFang();
   public DirewolfConfig.VolcanicSpiritWolf VolcanicSpiritWolf = new DirewolfConfig.VolcanicSpiritWolf();
   public DirewolfConfig.DivineWolf DivineWolf = new DirewolfConfig.DivineWolf();

   public String getFileName() {
      return "mysticism/race/direwolf/direwolf_config";
   }

   public static class BlackFang extends Default {
      @Comment("EP requirement to evolve into Black Fang.")
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
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 25.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 265.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.1F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0275F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:magic_sense", "tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion");

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

   public static class BlueFang extends Default {
      @Comment("EP requirement to evolve into Blue Fang.")
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
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 25.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 265.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.1F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0275F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "tensura:water_manipulation");

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

   public static class BrownFang extends Default {
      @Comment("EP requirement to evolve into Brown Fang.")
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
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 25.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 265.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.1F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0275F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "tensura:earth_manipulation");

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

   public static class Direwolf extends Default {
      @Comment("Minimal aura.")
      public double minAura = 1834.0;
      @Comment("Maximum aura.")
      public double maxAura = 2166.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1833.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 2167.0;
      @Comment("Bonus Size.")
      public double size = -0.25;
      @Comment("Bonus Max Health.")
      public double maxHealth = 6.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 35.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.025F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion");

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

   public static class DivineWolf extends Default {
      @Comment("EP requirement to evolve into Divine Wolf.")
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
      public double maxHealth = 1130.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 8625.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 5.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.05F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.625;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:thought_communication",
         "tensura:shadow_motion",
         "tensura:coercion",
         "tensura:godwolf_sense",
         "tensura:ultra_instinct",
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

   public static class GaleSpiritWolf extends Default {
      @Comment("EP requirement to evolve into Gale Spirit Wolf.")
      public double epRequirement = 800000.0;
      @Comment("The ability needed to evolve into Gale Spirit Wolf.")
      public String abilityRequirement = "tensura:wind_domination";
      @Comment("Does the ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("Minimal aura.")
      public double minAura = 425000.0;
      @Comment("Maximum aura.")
      public double maxAura = 425000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 425000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 425000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 680.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4360.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.04F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.4F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:thought_communication",
         "tensura:shadow_motion",
         "tensura:coercion",
         "tensura:wind_manipulation",
         "tensura:godwolf_sense",
         "tensura:wind_attack_resistance",
         "tensura:ultra_instinct"
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

   public static class GreenFang extends Default {
      @Comment("EP requirement to evolve into Green Fang.")
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
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 25.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 265.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.1F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0275F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "tensura:wind_manipulation");

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

   public static class MagicSpiritWolf extends Default {
      @Comment("EP requirement to evolve into Magic Spirit Wolf.")
      public double epRequirement = 800000.0;
      @Comment("The ability needed to evolve into Magic Spirit Wolf.")
      public String abilityRequirement = "tensura:magic_jamming";
      @Comment("Does the ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("Minimal aura.")
      public double minAura = 425000.0;
      @Comment("Maximum aura.")
      public double maxAura = 425000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 425000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 425000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 680.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4360.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.04F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.4F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:magic_sense",
         "tensura:thought_communication",
         "tensura:shadow_motion",
         "tensura:coercion",
         "tensura:godwolf_sense",
         "tensura:magic_jamming",
         "tensura:ultra_instinct",
         "tensura:magic_resistance"
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

   public static class MountainSpiritWolf extends Default {
      @Comment("EP requirement to evolve into Mountain Spirit Wolf.")
      public double epRequirement = 800000.0;
      @Comment("The ability needed to evolve into Mountain Spirit Wolf.")
      public String abilityRequirement = "tensura:earth_domination";
      @Comment("Does the ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("Minimal aura.")
      public double minAura = 425000.0;
      @Comment("Maximum aura.")
      public double maxAura = 425000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 425000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 425000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 680.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4360.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.04F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.4F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:thought_communication",
         "tensura:shadow_motion",
         "tensura:coercion",
         "tensura:earth_manipulation",
         "tensura:godwolf_sense",
         "tensura:earth_attack_resistance",
         "tensura:ultra_instinct"
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

   public static class MysticalBlackFang extends Default {
      @Comment("EP requirement to evolve into Mystical Black Fang.")
      public double epRequirement = 200000.0;
      @Comment("The ability needed to evolve into Mystical Black Fang.")
      public String abilityRequirement = "tensura:magic_sense";
      @Comment("Does the ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("Minimal aura.")
      public double minAura = 125000.0;
      @Comment("Maximum aura.")
      public double maxAura = 125000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 125000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 125000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 380.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 2320.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.3F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:magic_sense", "tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "tensura:godwolf_sense"
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

   public static class MysticalBlueFang extends Default {
      @Comment("EP requirement to evolve into Mystical Blue Fang.")
      public double epRequirement = 200000.0;
      @Comment("The ability needed to evolve into Mystical Blue Fang.")
      public String abilityRequirement = "tensura:water_manipulation";
      @Comment("Does the ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("Minimal aura.")
      public double minAura = 125000.0;
      @Comment("Maximum aura.")
      public double maxAura = 125000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 125000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 125000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 380.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 2320.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.3F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "tensura:water_manipulation", "tensura:godwolf_sense"
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

   public static class MysticalBrownFang extends Default {
      @Comment("EP requirement to evolve into Mystical Brown Fang.")
      public double epRequirement = 200000.0;
      @Comment("The ability needed to evolve into Mystical Brown Fang.")
      public String abilityRequirement = "tensura:earth_manipulation";
      @Comment("Does the ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("Minimal aura.")
      public double minAura = 125000.0;
      @Comment("Maximum aura.")
      public double maxAura = 125000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 125000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 125000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 380.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 2320.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.3F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "tensura:earth_manipulation", "tensura:godwolf_sense"
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

   public static class MysticalGreenFang extends Default {
      @Comment("EP requirement to evolve into Mystical Green Fang.")
      public double epRequirement = 200000.0;
      @Comment("The ability needed to evolve into Mystical Green Fang.")
      public String abilityRequirement = "tensura:wind_manipulation";
      @Comment("Does the ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("Minimal aura.")
      public double minAura = 125000.0;
      @Comment("Maximum aura.")
      public double maxAura = 125000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 125000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 125000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 380.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 2320.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.3F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "tensura:wind_manipulation", "tensura:godwolf_sense"
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

   public static class MysticalPurpleFang extends Default {
      @Comment("EP requirement to evolve into Mystical Purple Fang.")
      public double epRequirement = 200000.0;
      @Comment("The ability needed to evolve into Mystical Purple Fang.")
      public String abilityRequirement = "tensura:spatial_manipulation";
      @Comment("Does the ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("Minimal aura.")
      public double minAura = 125000.0;
      @Comment("Maximum aura.")
      public double maxAura = 125000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 125000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 125000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 380.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 2320.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.3F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "tensura:spatial_manipulation", "tensura:godwolf_sense"
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

   public static class MysticalRedFang extends Default {
      @Comment("EP requirement to evolve into Mystical Red Fang.")
      public double epRequirement = 200000.0;
      @Comment("The ability needed to evolve into Mystical Red Fang.")
      public String abilityRequirement = "tensura:flame_manipulation";
      @Comment("Does the ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("Minimal aura.")
      public double minAura = 125000.0;
      @Comment("Maximum aura.")
      public double maxAura = 125000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 125000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 125000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 380.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 2320.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.1F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.03F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.3F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "tensura:flame_manipulation", "tensura:godwolf_sense"
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

   public static class OceanSpiritWolf extends Default {
      @Comment("EP requirement to evolve into Ocean Spirit Wolf.")
      public double epRequirement = 800000.0;
      @Comment("The ability needed to evolve into Ocean Spirit Wolf.")
      public String abilityRequirement = "tensura:water_domination";
      @Comment("Does the ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("Minimal aura.")
      public double minAura = 425000.0;
      @Comment("Maximum aura.")
      public double maxAura = 425000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 425000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 425000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 680.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4360.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.04F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.4F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:thought_communication",
         "tensura:shadow_motion",
         "tensura:coercion",
         "tensura:water_manipulation",
         "tensura:godwolf_sense",
         "tensura:water_attack_resistance",
         "tensura:ultra_instinct"
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

   public static class PlanetSpiritWolf extends Default {
      @Comment("EP requirement to evolve into Planet Spirit Wolf.")
      public double epRequirement = 800000.0;
      @Comment("The ability needed to evolve into Planet Spirit Wolf.")
      public String abilityRequirement = "tensura:spatial_domination";
      @Comment("Does the ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("Minimal aura.")
      public double minAura = 425000.0;
      @Comment("Maximum aura.")
      public double maxAura = 425000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 425000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 425000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 680.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4360.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.04F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.4F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:thought_communication",
         "tensura:shadow_motion",
         "tensura:coercion",
         "tensura:spatial_manipulation",
         "tensura:godwolf_sense",
         "tensura:spatial_attack_resistance",
         "tensura:ultra_instinct"
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

   public static class PurpleFang extends Default {
      @Comment("EP requirement to evolve into Purple Fang.")
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
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 25.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 265.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.1F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0275F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "tensura:spatial_manipulation"
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

   public static class RedFang extends Default {
      @Comment("EP requirement to evolve into Red Fang.")
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
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 25.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 265.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.1F;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.0275F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of("tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "tensura:flame_manipulation");

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

   public static class VolcanicSpiritWolf extends Default {
      @Comment("EP requirement to evolve into Volcanic Spirit Wolf.")
      public double epRequirement = 800000.0;
      @Comment("The ability needed to evolve into Volcanic Spirit Wolf.")
      public String abilityRequirement = "tensura:flame_domination";
      @Comment("Does the ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = true;
      @Comment("Minimal aura.")
      public double minAura = 425000.0;
      @Comment("Maximum aura.")
      public double maxAura = 425000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 425000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 425000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 680.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4360.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.2F;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.04F;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.4F;
      @Comment("Bonus Step Height.")
      public double stepHeight = 0.5;
      @Comment("The list of intrinsic skills that the race gets.")
      public List<String> intrinsicSkills = List.of(
         "tensura:thought_communication",
         "tensura:shadow_motion",
         "tensura:coercion",
         "tensura:flame_manipulation",
         "tensura:godwolf_sense",
         "tensura:flame_attack_resistance",
         "tensura:ultra_instinct"
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
