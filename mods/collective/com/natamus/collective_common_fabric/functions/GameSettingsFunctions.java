package com.natamus.collective_common_fabric.functions;

import net.minecraft.class_315;

public class GameSettingsFunctions {
   public static void setGamma(class_315 options, double gamma) {
      options.field_1840.field_37868 = gamma;
   }

   public static double getGamma(class_315 options) {
      return (Double)options.field_1840.method_41753();
   }
}
