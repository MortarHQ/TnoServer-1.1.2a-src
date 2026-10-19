package com.shin.tensura_kumodesu.config.race;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;

public class TaratectConfig extends ManasConfig {
   public TaratectConfig.SmallLesserTaratect SmallLesserTaratect = new TaratectConfig.SmallLesserTaratect();
   public TaratectConfig.Orthocadinaht Orthocadinaht = new TaratectConfig.Orthocadinaht();
   public TaratectConfig.SmallPoisonTaratect SmallPoisonTaratect = new TaratectConfig.SmallPoisonTaratect();
   public TaratectConfig.ZoaEle ZoaEle = new TaratectConfig.ZoaEle();
   public TaratectConfig.EdeSaine EdeSaine = new TaratectConfig.EdeSaine();
   public TaratectConfig.ZanaHorowa ZanaHorowa = new TaratectConfig.ZanaHorowa();
   public TaratectConfig.Arachne Arachne = new TaratectConfig.Arachne();
   public TaratectConfig.SmallTaratect SmallTaratect = new TaratectConfig.SmallTaratect();
   public TaratectConfig.Taratect Taratect = new TaratectConfig.Taratect();
   public TaratectConfig.GreatTaratect GreatTaratect = new TaratectConfig.GreatTaratect();
   public TaratectConfig.GreaterTaratect GreaterTaratect = new TaratectConfig.GreaterTaratect();
   public TaratectConfig.ArchTaratect ArchTaratect = new TaratectConfig.ArchTaratect();
   public TaratectConfig.QueenTaratect QueenTaratect = new TaratectConfig.QueenTaratect();
   public TaratectConfig.LesserTaratect LesserTaratect = new TaratectConfig.LesserTaratect();
   public TaratectConfig.LesserPoisonTaratect LesserPoisonTaratect = new TaratectConfig.LesserPoisonTaratect();
   public TaratectConfig.PoisonTaratect PoisonTaratect = new TaratectConfig.PoisonTaratect();
   public TaratectConfig.GreatPoisonTaratect GreatPoisonTaratect = new TaratectConfig.GreatPoisonTaratect();

   public String getFileName() {
      return "tensura_kumodesu/race/taratect_config";
   }

   public static class Arachne extends Default {
      @Comment("EP requirement to evolve into an Arachne.")
      public double epRequirement = 3500000.0;
      @Comment("Minimal aura.")
      public double minAura = 500000.0;
      @Comment("Maximum aura.")
      public double maxAura = 500000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 3000000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 3000000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1030.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 10300.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 6.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.9;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.45;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.15;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.5;
      @Comment("Shadow Size.")
      public double shadowSize = 1.5;
      @Comment("Eye Height.")
      public double eyeHeight = 1.0;
      @Comment("Hitbox width multiplier.")
      public float width = 3.0F;
      @Comment("Hitbox height multiplier.")
      public float height = 1.4F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }

   public static class ArchTaratect extends Default {
      @Comment("EP requirement to evolve into an Arch Taratect.")
      public double epRequirement = 2500000.0;
      @Comment("Minimal aura.")
      public double minAura = 1500000.0;
      @Comment("Maximum aura.")
      public double maxAura = 1500000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1000000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1000000.0;
      @Comment("Bonus Size.")
      public double size = 0.3;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1180.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 4640.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 2.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.55;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.4;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.14;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1;
      @Comment("Shadow Size.")
      public double shadowSize = 3.1;
      @Comment("Eye Height.")
      public double eyeHeight = 0.83;
      @Comment("Hitbox width multiplier.")
      public float width = 5.0F;
      @Comment("Hitbox height multiplier.")
      public float height = 2.0F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;
      @Comment("Arch Entity Interaction Range")
      public double entityRange = 6.0;
      @Comment("Arch Block Interaction Range")
      public double blockRange = 6.0;
      @Comment("Arch Step Height")
      public double stepHeight = 2.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }

      public double getEntityRange() {
         return this.entityRange;
      }

      public double getBlockRange() {
         return this.blockRange;
      }

      public double getStepHeight() {
         return this.stepHeight;
      }
   }

   public static class EdeSaine extends Default {
      @Comment("EP requirement to evolve into an Ede Saine.")
      public double epRequirement = 600000.0;
      @Comment("Minimal aura.")
      public double minAura = 250000.0;
      @Comment("Maximum aura.")
      public double maxAura = 250000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 350000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 350000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 380.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 780.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.29;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.08;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.078;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.055;
      @Comment("Shadow Size.")
      public double shadowSize = 1.3;
      @Comment("Eye Height.")
      public double eyeHeight = 0.74;
      @Comment("Hitbox width multiplier.")
      public float width = 3.0F;
      @Comment("Hitbox height multiplier.")
      public float height = 1.0F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }

   public static class GreatPoisonTaratect extends Default {
      @Comment("EP requirement to evolve into a Great Poison Taratect.")
      public double epRequirement = 3800000.0;
      @Comment("Minimal aura.")
      public double minAura = 2800000.0;
      @Comment("Maximum aura.")
      public double maxAura = 2800000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1000000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1000000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 980.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 5940.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.8;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.15;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1;
      @Comment("Shadow Size.")
      public double shadowSize = 1.45;
      @Comment("Eye Height.")
      public double eyeHeight = 0.69;
      @Comment("Hitbox width multiplier.")
      public float width = 2.5F;
      @Comment("Hitbox height multiplier.")
      public float height = 1.2F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }

   public static class GreatTaratect extends Default {
      @Comment("EP requirement to evolve into a Great Taratect.")
      public double epRequirement = 400000.0;
      @Comment("Minimal aura.")
      public double minAura = 250000.0;
      @Comment("Maximum aura.")
      public double maxAura = 250000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 150000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 150000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 480.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 640.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = -0.1;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = -0.1;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.093;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.058;
      @Comment("Shadow Size.")
      public double shadowSize = 1.45;
      @Comment("Eye Height.")
      public double eyeHeight = 0.69;
      @Comment("Hitbox width multiplier.")
      public float width = 2.5F;
      @Comment("Hitbox height multiplier.")
      public float height = 1.2F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }

   public static class GreaterTaratect extends Default {
      @Comment("EP requirement to evolve into a Greater Taratect.")
      public double epRequirement = 800000.0;
      @Comment("Minimal aura.")
      public double minAura = 450000.0;
      @Comment("Maximum aura.")
      public double maxAura = 450000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 350000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 350000.0;
      @Comment("Bonus Size.")
      public double size = 0.2;
      @Comment("Bonus Max Health.")
      public double maxHealth = 680.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 840.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.4;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.12;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.12;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.03;
      @Comment("Shadow Size.")
      public double shadowSize = 2.25;
      @Comment("Eye Height.")
      public double eyeHeight = 0.67;
      @Comment("Hitbox width multiplier.")
      public float width = 4.0F;
      @Comment("Hitbox height multiplier.")
      public float height = 1.6F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }

   public static class LesserPoisonTaratect extends Default {
      @Comment("EP requirement to evolve into a Lesser Poison Taratect.")
      public double epRequirement = 150000.0;
      @Comment("Minimal aura.")
      public double minAura = 100000.0;
      @Comment("Maximum aura.")
      public double maxAura = 100000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 50000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 50000.0;
      @Comment("Bonus Size.")
      public double size = -0.3;
      @Comment("Bonus Max Health.")
      public double maxHealth = 100.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 2710.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.3;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = -0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = -0.115;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.078;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.05;
      @Comment("Shadow Size.")
      public double shadowSize = 0.85;
      @Comment("Eye Height.")
      public double eyeHeight = 0.87;
      @Comment("Hitbox width multiplier.")
      public float width = 1.4F;
      @Comment("Hitbox height multiplier.")
      public float height = 0.77F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }

   public static class LesserTaratect extends Default {
      @Comment("EP requirement to evolve into a Lesser Taratect.")
      public double epRequirement = 20000.0;
      @Comment("Minimal aura.")
      public double minAura = 13400.0;
      @Comment("Maximum aura.")
      public double maxAura = 13400.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 6600.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 6600.0;
      @Comment("Bonus Size.")
      public double size = -0.3;
      @Comment("Bonus Max Health.")
      public double maxHealth = -2.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = -40.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = -0.7;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = -0.13;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.07;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.067;
      @Comment("Shadow Size.")
      public double shadowSize = 0.85;
      @Comment("Eye Height.")
      public double eyeHeight = 0.87;
      @Comment("Hitbox width multiplier.")
      public float width = 1.4F;
      @Comment("Hitbox height multiplier.")
      public float height = 0.77F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }

   public static class Orthocadinaht extends Default {
      @Comment("EP requirement to evolve into a Orthocadinaht.")
      public double epRequirement = 3800000.0;
      @Comment("Minimal aura.")
      public double minAura = 1000000.0;
      @Comment("Maximum aura.")
      public double maxAura = 1000000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 2800000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 2800000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 820.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 6140.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 4.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.5;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = -0.3;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.17;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 1.1;
      @Comment("Shadow Size.")
      public double shadowSize = 1.3;
      @Comment("Eye Height.")
      public double eyeHeight = 0.74;
      @Comment("Hitbox width multiplier.")
      public float width = 3.0F;
      @Comment("Hitbox height multiplier.")
      public float height = 1.0F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }

   public static class PoisonTaratect extends Default {
      @Comment("EP requirement to evolve into a Poison Taratect.")
      public double epRequirement = 750000.0;
      @Comment("Minimal aura.")
      public double minAura = 600000.0;
      @Comment("Maximum aura.")
      public double maxAura = 600000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 150000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 150000.0;
      @Comment("Bonus Size.")
      public double size = -0.2;
      @Comment("Bonus Max Health.")
      public double maxHealth = 680.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 90.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 1.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.58;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.0;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.078;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.03;
      @Comment("Shadow Size.")
      public double shadowSize = 1.0;
      @Comment("Eye Height.")
      public double eyeHeight = 0.72;
      @Comment("Hitbox width multiplier.")
      public float width = 2.0F;
      @Comment("Hitbox height multiplier.")
      public float height = 0.9F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }

   public static class QueenTaratect extends Default {
      @Comment("EP requirement to evolve into a Queen Taratect.")
      public double epRequirement = 3500000.0;
      @Comment("Minimal aura.")
      public double minAura = 2500000.0;
      @Comment("Maximum aura.")
      public double maxAura = 2500000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 1000000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 1000000.0;
      @Comment("Bonus Size.")
      public double size = 0.47;
      @Comment("Bonus Max Health.")
      public double maxHealth = 1380.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 8640.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 11.0;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.8;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.55;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.15;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1;
      @Comment("Shadow Size.")
      public double shadowSize = 3.1;
      @Comment("Eye Height.")
      public double eyeHeight = 0.86;
      @Comment("Hitbox width multiplier.")
      public float width = 6.0F;
      @Comment("Hitbox height multiplier.")
      public float height = 3.0F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;
      @Comment("Queen Entity Interaction Range")
      public double entityRange = 10.0;
      @Comment("Queen Block Interaction Range")
      public double blockRange = 10.0;
      @Comment("Queen Step Height")
      public double stepHeight = 4.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }

      public double getEntityRange() {
         return this.entityRange;
      }

      public double getBlockRange() {
         return this.blockRange;
      }

      public double getStepHeight() {
         return this.stepHeight;
      }
   }

   public static class SmallLesserTaratect extends Default {
      @Comment("Minimal aura.")
      public double minAura = 500.0;
      @Comment("Maximum aura.")
      public double maxAura = 1000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 100.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 250.0;
      @Comment("Bonus Size.")
      public double size = -0.37;
      @Comment("Bonus Max Health.")
      public double maxHealth = -12.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = -48.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.7;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = -0.9;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = -0.15;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.07;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.07;
      @Comment("Shadow Size.")
      public double shadowSize = 0.7;
      @Comment("Eye Height.")
      public double eyeHeight = 0.67;
      @Comment("Hitbox width multiplier.")
      public float width = 1.5F;
      @Comment("Hitbox height multiplier.")
      public float height = 0.48F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }

   public static class SmallPoisonTaratect extends Default {
      @Comment("EP requirement to evolve into a Small Poison Taratect.")
      public double epRequirement = 100000.0;
      @Comment("Minimal aura.")
      public double minAura = 33000.0;
      @Comment("Maximum aura.")
      public double maxAura = 33000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 67000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 67000.0;
      @Comment("Bonus Size.")
      public double size = -0.37;
      @Comment("Bonus Max Health.")
      public double maxHealth = 79.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 155.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.05;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = -0.25;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = -0.1;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.074;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.064;
      @Comment("Shadow Size.")
      public double shadowSize = 0.7;
      @Comment("Eye Height.")
      public double eyeHeight = 0.75;
      @Comment("Hitbox width multiplier.")
      public float width = 1.5F;
      @Comment("Hitbox height multiplier.")
      public float height = 0.48F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }

   public static class SmallTaratect extends Default {
      @Comment("EP requirement to evolve into a Small Taratect.")
      public double epRequirement = 20000.0;
      @Comment("Minimal aura.")
      public double minAura = 13400.0;
      @Comment("Maximum aura.")
      public double maxAura = 13400.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 6600.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 6600.0;
      @Comment("Bonus Size.")
      public double size = -0.37;
      @Comment("Bonus Max Health.")
      public double maxHealth = -4.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = -42.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.6;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = -0.8;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = -0.15;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.07;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.07;
      @Comment("Shadow Size.")
      public double shadowSize = 0.7;
      @Comment("Eye Height.")
      public double eyeHeight = 0.67;
      @Comment("Hitbox width multiplier.")
      public float width = 1.5F;
      @Comment("Hitbox height multiplier.")
      public float height = 0.48F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }

   public static class Taratect extends Default {
      @Comment("EP requirement to evolve into a Taratect.")
      public double epRequirement = 100000.0;
      @Comment("Minimal aura.")
      public double minAura = 67000.0;
      @Comment("Maximum aura.")
      public double maxAura = 67000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 33000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 33000.0;
      @Comment("Bonus Size.")
      public double size = -0.2;
      @Comment("Bonus Max Health.")
      public double maxHealth = 99.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 140.0;
      @Comment("Bonus Attack Damage.")
      public double attack = -0.1;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = -0.3;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = -0.1;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.073;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.065;
      @Comment("Shadow Size.")
      public double shadowSize = 1.0;
      @Comment("Eye Height.")
      public double eyeHeight = 0.72;
      @Comment("Hitbox width multiplier.")
      public float width = 2.0F;
      @Comment("Hitbox height multiplier.")
      public float height = 0.9F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }

   public static class ZanaHorowa extends Default {
      @Comment("EP requirement to evolve into a Zana Horowa.")
      public double epRequirement = 1000000.0;
      @Comment("Minimal aura.")
      public double minAura = 300000.0;
      @Comment("Maximum aura.")
      public double maxAura = 300000.0;
      @Comment("Minimal magicule.")
      public double minMagicule = 700000.0;
      @Comment("Maximum magicule.")
      public double maxMagicule = 700000.0;
      @Comment("Bonus Size.")
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 680.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 5300.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 3.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 1.0;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.3;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.1;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = 0.1;
      @Comment("Shadow Size.")
      public double shadowSize = 1.3;
      @Comment("Eye Height.")
      public double eyeHeight = 0.74;
      @Comment("Hitbox width multiplier.")
      public float width = 3.0F;
      @Comment("Hitbox height multiplier.")
      public float height = 1.0F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }

   public static class ZoaEle extends Default {
      @Comment("EP requirement to evolve into a Zoa Ele.")
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
      public double size = 0.0;
      @Comment("Bonus Max Health.")
      public double maxHealth = 99.0;
      @Comment("Bonus Max Spiritual Health.")
      public double maxSpiritualHealth = 600.0;
      @Comment("Bonus Attack Damage.")
      public double attack = 0.5;
      @Comment("Bonus Attack Speed.")
      public double attackSpeed = 0.2;
      @Comment("Bonus Knockback Resistance.")
      public double knockbackResistance = 0.05;
      @Comment("Bonus Movement Speed.")
      public double movementSpeed = 0.078;
      @Comment("Bonus Swimming Speed Multiplier.")
      public double swimSpeed = -0.059;
      @Comment("Shadow Size.")
      public double shadowSize = 1.3;
      @Comment("Eye Height.")
      public double eyeHeight = 0.74;
      @Comment("Hitbox width multiplier.")
      public float width = 3.0F;
      @Comment("Hitbox height multiplier.")
      public float height = 1.0F;
      @Comment("Model Y offset")
      public double yOffset = 0.0;

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

      public double getShadowSize() {
         return this.shadowSize;
      }

      public double getEyeHeight() {
         return this.eyeHeight;
      }

      public float getWidth() {
         return this.width;
      }

      public float getHeight() {
         return this.height;
      }

      public double getYOffset() {
         return this.yOffset;
      }
   }
}
