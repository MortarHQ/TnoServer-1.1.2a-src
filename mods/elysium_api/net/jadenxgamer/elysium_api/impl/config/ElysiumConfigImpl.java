package net.jadenxgamer.elysium_api.impl.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;

public class ElysiumConfigImpl {
   public static ModConfigSpec COMMON;

   static {
      Builder COMMON = new Builder();
      COMMON.comment("Building Tools").push("buildingTools");
      ElysiumConfigImpl.BuildingTools.init(COMMON);
      COMMON.pop();
      ElysiumConfigImpl.COMMON = COMMON.build();
   }

   public static class BuildingTools {
      public static void init(Builder builder) {
         ElysiumConfig.BLOCK_SWAP = builder.comment("Replace blocks without breaking them first").define("blockSwap", false);
      }
   }
}
