package com.natamus.collective_common_fabric.functions;

import net.minecraft.class_1282;
import net.minecraft.class_6880;
import net.minecraft.class_8110;

public class UtilityFunctions {
   public static class_1282 createDamageSource(String identifier) {
      class_8110 damageType = new class_8110(identifier, 1.0F);
      return new class_1282(class_6880.method_40223(damageType));
   }
}
