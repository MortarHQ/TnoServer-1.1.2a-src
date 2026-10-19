package com.cerbon.bosses_of_mass_destruction.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.BoundedDiscrete;

public class GeneralConfig {
   @BoundedDiscrete(min = 1L, max = 32L)
   public int tableOfElevationRadius = 3;
}
