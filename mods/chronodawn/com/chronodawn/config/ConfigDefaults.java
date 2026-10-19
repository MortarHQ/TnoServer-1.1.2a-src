package com.chronodawn.config;

public final class ConfigDefaults {
   public static final boolean ANCIENT_RUINS_ENABLED = true;
   public static final int ANCIENT_RUINS_SPACING = 56;
   public static final int ANCIENT_RUINS_SEPARATION = 20;
   public static final int ANCIENT_RUINS_SALT = 20005897;
   public static final OreSettings TIME_CRYSTAL_DEFAULTS = new OreSettings(true, 3, 0, 48);
   public static final OreSettings ENTROPY_CRYSTAL_DEFAULTS = new OreSettings(true, 4, 40, 100);
   public static final OreSettings TEMPORAL_AMBER_DEFAULTS = new OreSettings(true, 4, -30, 20);
   public static final OreSettings CLOCKSTONE_DEFAULTS = new OreSettings(true, 8, -16, 80);

   private ConfigDefaults() {
   }

   public static ChronoDawnConfig defaults() {
      return new ChronoDawnConfig(
         1,
         new ChronoDawnConfig.World(
            new ChronoDawnConfig.Structures(new ChronoDawnConfig.AncientRuins(true, 56, 20, 20005897)),
            new OresConfig(TIME_CRYSTAL_DEFAULTS, ENTROPY_CRYSTAL_DEFAULTS, TEMPORAL_AMBER_DEFAULTS, CLOCKSTONE_DEFAULTS)
         )
      );
   }
}
