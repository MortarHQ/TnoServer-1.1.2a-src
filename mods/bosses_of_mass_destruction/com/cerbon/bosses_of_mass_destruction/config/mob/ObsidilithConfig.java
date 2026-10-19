package com.cerbon.bosses_of_mass_destruction.config.mob;

import me.shedaniel.autoconfig.annotation.ConfigEntry.BoundedDiscrete;

public class ObsidilithConfig {
   public double health = 300.0;
   public double armor = 14.0;
   public double attack = 16.0;
   public float idleHealingPerTick = 0.5F;
   @BoundedDiscrete(min = 0L, max = 10000L)
   public int experienceDrop = 1000;
   public boolean spawnPillarOnDeath = true;
   public float anvilAttackExplosionStrength = 4.0F;
}
