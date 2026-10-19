package com.cerbon.bosses_of_mass_destruction.config.mob;

import me.shedaniel.autoconfig.annotation.ConfigEntry.BoundedDiscrete;

public class GauntletConfig {
   public double health = 250.0;
   public double armor = 8.0;
   public double attack = 16.0;
   public float idleHealingPerTick = 0.5F;
   @BoundedDiscrete(min = 0L, max = 10000L)
   public int experienceDrop = 1000;
   public boolean spawnAncientDebrisOnDeath = true;
   public double energizedPunchExplosionSize = 4.5;
   public double normalPunchExplosionMultiplier = 1.5;
}
