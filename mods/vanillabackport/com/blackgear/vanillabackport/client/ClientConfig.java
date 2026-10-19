package com.blackgear.vanillabackport.client;

import com.blackgear.platform.core.util.config.ConfigBuilder;
import com.blackgear.platform.core.util.config.ConfigBuilder.ConfigValue;

public class ClientConfig {
   public final ConfigValue<Boolean> hasFallingLeaves;
   public final ConfigValue<Double> fallingLeavesFrequency;
   public final ConfigValue<Boolean> useLegacySpawnEggs;
   public final ConfigValue<Boolean> useSheepWoolUndercoat;

   public ClientConfig(ConfigBuilder builder) {
      builder.push("Spring to Life");
      this.hasFallingLeaves = builder.comment("allow falling leaves particles to generate").define("falling_leaves", true);
      this.fallingLeavesFrequency = builder.comment("how often should falling leaves particles generate")
         .defineInRange("falling_leaves_frequency", 0.01, 0.0, 1.0);
      this.useLegacySpawnEggs = builder.comment("use the legacy spawn egg textures").define("use_legacy_spawn_eggs", false);
      this.useSheepWoolUndercoat = builder.comment("toggle the colored sheep wool undercoat").define("use_sheep_wool_undercoat", true);
      builder.pop();
   }
}
