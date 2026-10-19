package com.chronodawn.config;

public record ChronoDawnConfig(int schemaVersion, ChronoDawnConfig.World world) {
   public static final int CURRENT_SCHEMA_VERSION = 1;
   private static volatile ChronoDawnConfig INSTANCE = ConfigDefaults.defaults();

   public static ChronoDawnConfig get() {
      return INSTANCE;
   }

   public static void set(ChronoDawnConfig config) {
      INSTANCE = config;
   }

   public record AncientRuins(boolean enabled, int spacing, int separation, int salt) {
   }

   public record Structures(ChronoDawnConfig.AncientRuins ancientRuins) {
   }

   public record World(ChronoDawnConfig.Structures structures, OresConfig ores) {
   }
}
