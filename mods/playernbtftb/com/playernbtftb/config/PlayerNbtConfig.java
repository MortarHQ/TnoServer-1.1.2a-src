package com.playernbtftb.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class PlayerNbtConfig {
   public static final Builder BUILDER = new Builder();
   public static final ModConfigSpec SPEC = BUILDER.build();
   public static final IntValue CHECK_INTERVAL_TICKS = BUILDER.comment(
         "How often to check player NBT data (in ticks). 20 ticks = 1 second. Default: 60 (3 seconds)"
      )
      .defineInRange("checkIntervalTicks", 60, 1, 1200);
   public static final BooleanValue ENABLE_DEBUG_LOGGING = BUILDER.comment("Enable debug logging for PlayerNBT task checks")
      .define("enableDebugLogging", false);
   public static final BooleanValue EXACT_MATCH_MODE = BUILDER.comment("Enable exact match mode (requires complete NBT structure match)")
      .define("exactMatchMode", false);

   static {
      BUILDER.push("PlayerNBT Task Settings");
      BUILDER.pop();
   }
}
