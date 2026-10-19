package com.github.b4ndithelps.tennogamenolife.config.race;

import io.github.manasmods.tensura.config.race.RaceConfig.Default;

public class FantasumaRaceConfig {
   public static class Fantasuma extends Default {
      public double minAura = 8000.0;
      public double maxAura = 12000.0;
      public double minMagicule = 4000.0;
      public double maxMagicule = 6000.0;
      public double size = 0.0;
      public double maxHealth = 880.0;
      public double maxSpiritualHealth = 3000.0;
      public double attack = 8.0;
      public double attackSpeed = 4.3;
      public double knockbackResistance = 0.3;
      public double movementSpeed = 0.0;
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
}
