package com.natamus.collective_common_fabric.functions;

import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;

public class RayTraceFunctions {
   public static class_239 rayTrace(class_1937 worldIn, class_1657 player, boolean stopOnLiquid) {
      class_242 fluidMode;
      if (stopOnLiquid) {
         fluidMode = class_242.field_1347;
      } else {
         fluidMode = class_242.field_1348;
      }

      float f = player.method_36455();
      float f1 = player.method_36454();
      class_243 vec3d = player.method_5836(1.0F);
      float f2 = class_3532.method_15362(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f3 = class_3532.method_15374(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f4 = -class_3532.method_15362(-f * (float) (Math.PI / 180.0));
      float f5 = class_3532.method_15374(-f * (float) (Math.PI / 180.0));
      float f6 = f3 * f4;
      float f7 = f2 * f4;
      double d0 = 8192.0;
      class_243 vec3d1 = vec3d.method_1031(f6 * d0, f5 * d0, f7 * d0);
      return worldIn.method_17742(new class_3959(vec3d, vec3d1, class_3960.field_17559, fluidMode, player));
   }
}
