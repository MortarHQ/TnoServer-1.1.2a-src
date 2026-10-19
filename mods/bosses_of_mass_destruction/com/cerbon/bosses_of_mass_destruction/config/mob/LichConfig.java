package com.cerbon.bosses_of_mass_destruction.config.mob;

import java.util.List;
import me.shedaniel.autoconfig.annotation.ConfigEntry.BoundedDiscrete;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;

public class LichConfig {
   public boolean eternalNighttime = true;
   @BoundedDiscrete(min = 0L, max = 10000L)
   public int experienceDrop = 1500;
   public float idleHealingPerTick = 0.2F;
   public double health = 300.0;
   @CollapsibleObject
   public LichConfig.Missile missile = new LichConfig.Missile();
   @CollapsibleObject
   public LichConfig.Comet comet = new LichConfig.Comet();
   @CollapsibleObject
   public LichConfig.SummonMechanic summonMechanic = new LichConfig.SummonMechanic();

   public static class Comet {
      public float explosionStrength = 4.0F;
   }

   public static class Missile {
      public String mobEffectId = "minecraft:slowness";
      @BoundedDiscrete(min = 0L, max = 1000L)
      public int mobEffectDuration = 100;
      @BoundedDiscrete(min = 0L, max = 4L)
      public int mobEffectAmplifier = 2;
      public double damage = 9.0;
   }

   public static class SummonMechanic {
      public boolean isEnabled = true;
      public List<String> entitiesThatCountToSummonCounter;
      @BoundedDiscrete(min = 1L, max = 1000L)
      public int numEntitiesKilledToDropSoulStar = 50;
   }
}
