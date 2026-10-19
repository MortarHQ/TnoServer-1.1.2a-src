package com.natamus.collective_common_neoforge.functions;

import net.minecraft.client.Options;

public class GameSettingsFunctions {
   public static void setGamma(Options options, double gamma) {
      options.gamma.value = gamma;
   }

   public static double getGamma(Options options) {
      return (Double)options.gamma.get();
   }
}
