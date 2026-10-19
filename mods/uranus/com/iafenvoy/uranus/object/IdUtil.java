package com.iafenvoy.uranus.object;

import net.minecraft.resources.ResourceLocation;

public class IdUtil {
   public static String build(String namespace, String path) {
      return ResourceLocation.fromNamespaceAndPath(namespace, path).toString();
   }
}
