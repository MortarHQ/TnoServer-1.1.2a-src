package com.yungnickyoung.minecraft.yungscavebiomes.config;

import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class NeoForgeConfigLostCaves {
   public final ConfigValue<Boolean> enableSandstorms;
   public final ConfigValue<Integer> minSandstormDuration;
   public final ConfigValue<Integer> maxSandstormDuration;
   public final ConfigValue<Integer> minTimeBetweenSandstorms;
   public final ConfigValue<Integer> maxTimeBetweenSandstorms;
   public final ConfigValue<Boolean> extraSandstormParticles;
   public final NeoForgeConfigLostCavesNoiseParams noiseParameters;

   public NeoForgeConfigLostCaves(Builder BUILDER) {
      BUILDER.push("Lost Caves");
      this.enableSandstorms = BUILDER.worldRestart().define("Enable Sandstorms", true);
      this.minSandstormDuration = BUILDER.worldRestart().define("Min Sandstorm Duration (seconds)", 120);
      this.maxSandstormDuration = BUILDER.worldRestart().define("Max Sandstorm Duration (seconds)", 600);
      this.minTimeBetweenSandstorms = BUILDER.worldRestart().define("Min Time Between Sandstorms (seconds)", 1200);
      this.maxTimeBetweenSandstorms = BUILDER.worldRestart().define("Max Time Between Sandstorms (seconds)", 2400);
      this.extraSandstormParticles = BUILDER.define("Render Extra Particles During Sandstorms", true);
      this.noiseParameters = new NeoForgeConfigLostCavesNoiseParams(BUILDER);
      BUILDER.pop();
   }
}
