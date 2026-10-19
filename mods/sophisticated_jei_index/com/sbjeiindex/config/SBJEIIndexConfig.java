package com.sbjeiindex.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public final class SBJEIIndexConfig {
   public static final ModConfigSpec SPEC;
   public static final IntValue maxEnabledBackpacksScanned;

   private SBJEIIndexConfig() {
   }

   static {
      Builder b = new Builder();
      b.push("general");
      maxEnabledBackpacksScanned = b.comment(
            "Maximum number of equipped backpacks with JEI Index Upgrade to scan (in Sophisticated Backpacks selection order). 0 means unlimited."
         )
         .defineInRange("maxEnabledBackpacksScanned", 0, 0, 64);
      b.pop();
      SPEC = b.build();
   }
}
