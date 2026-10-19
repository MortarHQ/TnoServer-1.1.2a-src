package com.yungnickyoung.minecraft.yungscavebiomes.config;

import net.neoforged.neoforge.common.ModConfigSpec.Builder;

public class NeoForgeConfigFrostedCaves {
   public final NeoForgeConfigFrostedCavesNoiseParams noiseParameters;

   public NeoForgeConfigFrostedCaves(Builder BUILDER) {
      BUILDER.push("Frosted Caves");
      this.noiseParameters = new NeoForgeConfigFrostedCavesNoiseParams(BUILDER);
      BUILDER.pop();
   }
}
