package com.natamus.collective_common_fabric.functions;

import net.minecraft.class_1937;

public class DimensionFunctions {
   public static String getSimpleDimensionString(class_1937 world) {
      String dimensionfolder = WorldFunctions.getWorldDimensionName(world).toLowerCase();
      if (dimensionfolder.contains(":")) {
         dimensionfolder = dimensionfolder.split(":")[1];
      }

      return dimensionfolder;
   }
}
