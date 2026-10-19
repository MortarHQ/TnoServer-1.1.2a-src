package com.chronodawn.compat;

import net.minecraft.resources.ResourceLocation;

public class CompatResourceLocation {
   public static ResourceLocation create(String namespace, String path) {
      return ResourceLocation.fromNamespaceAndPath(namespace, path);
   }

   public static ResourceLocation parse(String location) {
      return ResourceLocation.parse(location);
   }

   private CompatResourceLocation() {
   }
}
