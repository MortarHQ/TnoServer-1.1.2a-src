package com.natamus.collective_common_fabric.functions;

import javax.annotation.Nullable;
import net.minecraft.class_1761;
import net.minecraft.class_2960;
import net.minecraft.class_5321;
import net.minecraft.class_7923;

public class CreativeModeTabFunctions {
   @Nullable
   public static class_5321<class_1761> getCreativeModeTabResourceKey(String path) {
      return getCreativeModeTabResourceKey("minecraft", path);
   }

   @Nullable
   public static class_5321<class_1761> getCreativeModeTabResourceKey(String namespace, String path) {
      return getCreativeModeTabResourceKey(class_2960.method_60655(namespace, path));
   }

   @Nullable
   public static class_5321<class_1761> getCreativeModeTabResourceKey(class_2960 resourceLocation) {
      return class_7923.field_44687.method_29113((class_1761)class_7923.field_44687.method_10223(resourceLocation)).orElseGet(() -> null);
   }
}
