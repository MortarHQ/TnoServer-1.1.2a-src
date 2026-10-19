package com.yungnickyoung.minecraft.yungscavebiomes.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;

public class YCBNeoForgeConfig {
   public static final Builder BUILDER = new Builder();
   public static final ModConfigSpec SPEC = BUILDER.build();
   public static final NeoForgeConfigLostCaves lostCaves = new NeoForgeConfigLostCaves(BUILDER);
   public static final NeoForgeConfigFrostedCaves frostedCaves = new NeoForgeConfigFrostedCaves(BUILDER);
   public static final NeoForgeConfigOther other = new NeoForgeConfigOther(BUILDER);

   static {
      BUILDER.push("YUNG's Cave Biomes");
      BUILDER.pop();
   }
}
