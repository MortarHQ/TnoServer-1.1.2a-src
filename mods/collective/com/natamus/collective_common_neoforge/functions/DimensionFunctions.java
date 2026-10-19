package com.natamus.collective_common_neoforge.functions;

import net.minecraft.world.level.Level;

public class DimensionFunctions {
   public static String getSimpleDimensionString(Level world) {
      String dimensionfolder = WorldFunctions.getWorldDimensionName(world).toLowerCase();
      if (dimensionfolder.contains(":")) {
         dimensionfolder = dimensionfolder.split(":")[1];
      }

      return dimensionfolder;
   }
}
