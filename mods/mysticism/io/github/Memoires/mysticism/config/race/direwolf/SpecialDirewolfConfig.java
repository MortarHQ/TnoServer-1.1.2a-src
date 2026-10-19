package io.github.Memoires.mysticism.config.race.direwolf;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import java.util.List;

public class SpecialDirewolfConfig extends ManasConfig {
   public SpecialDirewolfConfig.ScorchWolf ScorchWolf = new SpecialDirewolfConfig.ScorchWolf();
   public SpecialDirewolfConfig.BlazingScorchWolf BlazingScorchWolf = new SpecialDirewolfConfig.BlazingScorchWolf();
   public SpecialDirewolfConfig.MoltenSpiritWolf MoltenSpiritWolf = new SpecialDirewolfConfig.MoltenSpiritWolf();
   public SpecialDirewolfConfig.DarknessFang DarknessFang = new SpecialDirewolfConfig.DarknessFang();
   public SpecialDirewolfConfig.MysticalDarknessFang MysticalDarknessFang = new SpecialDirewolfConfig.MysticalDarknessFang();
   public SpecialDirewolfConfig.PenumbraSpiritWolf PenumbraSpiritWolf = new SpecialDirewolfConfig.PenumbraSpiritWolf();
   public SpecialDirewolfConfig.FrostWolf FrostWolf = new SpecialDirewolfConfig.FrostWolf();
   public SpecialDirewolfConfig.NivalisFrostWolf NivalisFrostWolf = new SpecialDirewolfConfig.NivalisFrostWolf();
   public SpecialDirewolfConfig.GlacierSpiritWolf GlacierSpiritWolf = new SpecialDirewolfConfig.GlacierSpiritWolf();
   public SpecialDirewolfConfig.GuitarWolf GuitarWolf = new SpecialDirewolfConfig.GuitarWolf();
   public SpecialDirewolfConfig.AmpedGuitarWolf AmpedGuitarWolf = new SpecialDirewolfConfig.AmpedGuitarWolf();
   public SpecialDirewolfConfig.StringSpiritWolf StringSpiritWolf = new SpecialDirewolfConfig.StringSpiritWolf();
   public SpecialDirewolfConfig.LightFang LightFang = new SpecialDirewolfConfig.LightFang();
   public SpecialDirewolfConfig.MysticalLightFang MysticalLightFang = new SpecialDirewolfConfig.MysticalLightFang();
   public SpecialDirewolfConfig.AntumbraSpiritWolf AntumbraSpiritWolf = new SpecialDirewolfConfig.AntumbraSpiritWolf();
   public SpecialDirewolfConfig.StarWolf StarWolf = new SpecialDirewolfConfig.StarWolf();
   public SpecialDirewolfConfig.TempestStarWolf TempestStarWolf = new SpecialDirewolfConfig.TempestStarWolf();
   public SpecialDirewolfConfig.StormSpiritWolf StormSpiritWolf = new SpecialDirewolfConfig.StormSpiritWolf();

   public String getFileName() {
      return "mysticism/race/direwolf/special_direwolf_config";
   }

   public static class AmpedGuitarWolf extends Default {
      @Comment("EP requirement to evolve into Amped Guitar Wolf.")
      public double epRequirement = 200000.0;
      @Comment("The ability needed to evolve into Amped Guitar Wolf.")
      public String abilityRequirement = "tensura:sound_manipulation";
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
         "tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "tensura:sticky_steel_thread", "tensura:godwolf_sense"
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

   public static class AntumbraSpiritWolf extends Default {
      @Comment("EP requirement to evolve into Antumbra Spirit Wolf.")
      public double epRequirement = 800000.0;
      @Comment("The ability needed to evolve into Antumbra Spirit Wolf.")
      public String abilityRequirement = "mysticism:light_domination";
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
         "mysticism:light_manipulation",
         "tensura:godwolf_sense",
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

   public static class BlazingScorchWolf extends Default {
      @Comment("EP requirement to evolve into Blazing Scorch Wolf.")
      public double epRequirement = 200000.0;
      @Comment("The first ability that needs to be mastered to evolve into Blazing Scorch Wolf.")
      public String abilityRequirement1 = "tensura:flame_manipulation";
      @Comment("Does the first ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement1 = true;
      @Comment("The second ability that needs to be mastered to evolve into Blazing Scorch Wolf.")
      public String abilityRequirement2 = "tensura:earth_manipulation";
      @Comment("Does the second ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement2 = true;
      @Comment("The third ability that needs to be mastered to evolve into Blazing Scorch Wolf.")
      public String abilityRequirement3 = "tensura:wind_manipulation";
      @Comment("Does the third ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement3 = true;
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
         "tensura:thought_communication",
         "tensura:shadow_motion",
         "tensura:coercion",
         "tensura:flame_manipulation",
         "tensura:earth_manipulation",
         "tensura:wind_manipulation",
         "tensura:godwolf_sense",
         "tensura:ultra_instinct",
         "mysticism:profaned_prominence"
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

   public static class DarknessFang extends Default {
      @Comment("EP requirement to evolve into Darkness Fang.")
      public double epRequirement = 10000.0;
      @Comment("Light level requirement (less than or equal to?) to evolve into Darkness Fang.")
      public int lightLevelRequirement = 7;
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
         "tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "mysticism:darkness_manipulation"
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

   public static class FrostWolf extends Default {
      @Comment("EP requirement to evolve into Frost Wolf.")
      public double epRequirement = 10000.0;
      @Comment("The first ability that needs to be mastered to evolve into Scorch Wolf.")
      public String abilityRequirement1 = "tensura:water_manipulation";
      @Comment("Does the first ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement1 = false;
      @Comment("The second ability that needs to be mastered to evolve into Scorch Wolf.")
      public String abilityRequirement2 = "tensura:wind_manipulation";
      @Comment("Does the second ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement2 = false;
      @Comment("The third ability that needs to be mastered to evolve into Scorch Wolf.")
      public String abilityRequirement3 = "mysticism:ice_manipulation";
      @Comment("Does the third ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement3 = false;
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
         "tensura:thought_communication",
         "tensura:shadow_motion",
         "tensura:coercion",
         "tensura:water_manipulation",
         "tensura:wind_manipulation",
         "mysticism:ice_manipulation"
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

   public static class GlacierSpiritWolf extends Default {
      @Comment("EP requirement to evolve into Glacier Spirit Wolf.")
      public double epRequirement = 800000.0;
      @Comment("The ability needed to evolve into Glacier Spirit Wolf.")
      public String abilityRequirement = "mysticism:cryogenic_cessation";
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
         "tensura:wind_manipulation",
         "mysticism:ice_manipulation",
         "tensura:godwolf_sense",
         "tensura:ultra_instinct",
         "mysticism:cryogenic_cessation"
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

   public static class GuitarWolf extends Default {
      @Comment("EP requirement to evolve into Guitar Wolf.")
      public double epRequirement = 10000.0;
      @Comment("The ability needed to evolve into Guitar Wolf.")
      public String abilityRequirement = "tensura:sound_manipulation";
      @Comment("Does the ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement = false;
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
      public List<String> intrinsicSkills = List.of("tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "tensura:sticky_steel_thread");

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

   public static class LightFang extends Default {
      @Comment("EP requirement to evolve into Light Fang.")
      public double epRequirement = 10000.0;
      @Comment("Light level requirement (more than or equal to?) to evolve into Darkness Fang.")
      public int lightLevelRequirement = 13;
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
         "tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "mysticism:light_manipulation"
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

   public static class MoltenSpiritWolf extends Default {
      @Comment("EP requirement to evolve into Molten Spirit Wolf.")
      public double epRequirement = 800000.0;
      @Comment("The ability needed to evolve into Molten Spirit Wolf.")
      public String abilityRequirement = "mysticism:profaned_prominence";
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
         "tensura:earth_manipulation",
         "tensura:wind_manipulation",
         "tensura:godwolf_sense",
         "tensura:ultra_instinct",
         "mysticism:profaned_prominence"
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

   public static class MysticalDarknessFang extends Default {
      @Comment("EP requirement to evolve into Mystical Dark Fang.")
      public double epRequirement = 200000.0;
      @Comment("The ability needed to evolve into Mystical Dark Fang.")
      public String abilityRequirement = "mysticism:darkness_manipulation";
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
         "tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "mysticism:darkness_manipulation", "tensura:godwolf_sense"
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

   public static class MysticalLightFang extends Default {
      @Comment("EP requirement to evolve into Mystical Light Fang.")
      public double epRequirement = 200000.0;
      @Comment("The ability needed to evolve into Mystical Light Fang.")
      public String abilityRequirement = "mysticism:light_manipulation";
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
         "tensura:thought_communication", "tensura:shadow_motion", "tensura:coercion", "mysticism:light_manipulation", "tensura:godwolf_sense"
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

   public static class NivalisFrostWolf extends Default {
      @Comment("EP requirement to evolve into Nivalis Frost Wolf.")
      public double epRequirement = 200000.0;
      @Comment("The first ability that needs to be mastered to evolve into Nivalis Frost Wolf.")
      public String abilityRequirement1 = "tensura:water_manipulation";
      @Comment("Does the first ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement1 = true;
      @Comment("The second ability that needs to be mastered to evolve into Nivalis Frost Wolf.")
      public String abilityRequirement2 = "tensura:wind_manipulation";
      @Comment("Does the second ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement2 = true;
      @Comment("The third ability that needs to be mastered to evolve into Nivalis Frost Wolf.")
      public String abilityRequirement3 = "mysticism:ice_manipulation";
      @Comment("Does the third ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement3 = true;
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
         "tensura:thought_communication",
         "tensura:shadow_motion",
         "tensura:coercion",
         "tensura:water_manipulation",
         "tensura:wind_manipulation",
         "mysticism:ice_manipulation",
         "tensura:godwolf_sense",
         "tensura:ultra_instinct",
         "mysticism:cryogenic_cessation"
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

   public static class PenumbraSpiritWolf extends Default {
      @Comment("EP requirement to evolve into Penumbra Spirit Wolf.")
      public double epRequirement = 800000.0;
      @Comment("The ability needed to evolve into Penumbra Spirit Wolf.")
      public String abilityRequirement = "mysticism:darkness_domination";
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
         "mysticism:darkness_manipulation",
         "tensura:godwolf_sense",
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

   public static class ScorchWolf extends Default {
      @Comment("EP requirement to evolve into Scorch Wolf.")
      public double epRequirement = 10000.0;
      @Comment("The first ability that needs to be mastered to evolve into Scorch Wolf.")
      public String abilityRequirement1 = "tensura:flame_manipulation";
      @Comment("Does the first ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement1 = false;
      @Comment("The second ability that needs to be mastered to evolve into Scorch Wolf.")
      public String abilityRequirement2 = "tensura:earth_manipulation";
      @Comment("Does the second ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement2 = false;
      @Comment("The third ability that needs to be mastered to evolve into Scorch Wolf.")
      public String abilityRequirement3 = "tensura:wind_manipulation";
      @Comment("Does the third ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement3 = false;
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
         "tensura:thought_communication",
         "tensura:shadow_motion",
         "tensura:coercion",
         "tensura:flame_manipulation",
         "tensura:earth_manipulation",
         "tensura:wind_manipulation"
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

   public static class StarWolf extends Default {
      @Comment("EP requirement to evolve into Star Wolf.")
      public double epRequirement = 10000.0;
      @Comment("The first ability that needs to be mastered to evolve into Star Wolf.")
      public String abilityRequirement1 = "tensura:water_manipulation";
      @Comment("Does the first ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement1 = false;
      @Comment("The second ability that needs to be mastered to evolve into Star Wolf.")
      public String abilityRequirement2 = "tensura:wind_manipulation";
      @Comment("Does the second ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement2 = false;
      @Comment("The third ability that needs to be mastered to evolve into Star Wolf.")
      public String abilityRequirement3 = "tensura:spatial_manipulation";
      @Comment("Does the third ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement3 = false;
      @Comment("The height requirement to evolve into Star Wolf.")
      public int heightRequirement = 120;
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
         "tensura:thought_communication",
         "tensura:shadow_motion",
         "tensura:coercion",
         "tensura:water_manipulation",
         "tensura:wind_manipulation",
         "tensura:spatial_manipulation"
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

   public static class StormSpiritWolf extends Default {
      @Comment("EP requirement to evolve into Storm Spirit Wolf.")
      public double epRequirement = 800000.0;
      @Comment("The ability needed to evolve into Storm Spirit Wolf.")
      public String abilityRequirement = "tensura:black_lightning";
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
         "tensura:wind_manipulation",
         "tensura:spatial_manipulation",
         "tensura:godwolf_sense",
         "tensura:ultra_instinct",
         "tensura:black_lightning",
         "tensura:lightning_manipulation"
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

   public static class StringSpiritWolf extends Default {
      @Comment("EP requirement to evolve into String Spirit Wolf.")
      public double epRequirement = 800000.0;
      @Comment("The ability needed to evolve into String Spirit Wolf.")
      public String abilityRequirement = "tensura:sound_domination";
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
         "tensura:sticky_steel_thread",
         "tensura:godwolf_sense",
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

   public static class TempestStarWolf extends Default {
      @Comment("EP requirement to evolve into Tempest Star Wolf.")
      public double epRequirement = 200000.0;
      @Comment("The first ability that needs to be mastered to evolve into Tempest Star Wolf.")
      public String abilityRequirement1 = "tensura:water_manipulation";
      @Comment("Does the first ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement1 = true;
      @Comment("The second ability that needs to be mastered to evolve into Tempest Star Wolf.")
      public String abilityRequirement2 = "tensura:wind_manipulation";
      @Comment("Does the second ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement2 = true;
      @Comment("The third ability that needs to be mastered to evolve into Tempest Star Wolf.")
      public String abilityRequirement3 = "tensura:spatial_manipulation";
      @Comment("Does the third ability need to be mastered? (true/false)")
      public boolean abilityMasteryRequirement3 = true;
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
         "tensura:thought_communication",
         "tensura:shadow_motion",
         "tensura:coercion",
         "tensura:water_manipulation",
         "tensura:wind_manipulation",
         "tensura:spatial_manipulation",
         "tensura:godwolf_sense",
         "tensura:ultra_instinct",
         "tensura:black_lightning",
         "tensura:lightning_manipulation"
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
