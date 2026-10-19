package com.tensurabossstructure.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class BossConfig {
   public static final ModConfigSpec SPEC;
   public static final BooleanValue SPAWNER_RECOVERY_ENABLED;
   public static final IntValue SPAWNER_RECOVERY_COOLDOWN_MINUTES;

   static {
      Builder builder = new Builder();
      builder.push("spawner");
      SPAWNER_RECOVERY_ENABLED = builder.comment("Whether boss spawners regenerate after the boss is killed (default: true)").define("recoveryEnabled", true);
      SPAWNER_RECOVERY_COOLDOWN_MINUTES = builder.comment("Cooldown in minutes before a boss spawner regenerates (default: 60)")
         .defineInRange("recoveryCooldownMinutes", 60, 1, 1440);
      builder.pop();
      SPEC = builder.build();
   }
}
