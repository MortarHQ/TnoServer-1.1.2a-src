package com.natamus.collective_common_neoforge.functions;

import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class CreativeModeTabFunctions {
   @Nullable
   public static ResourceKey<CreativeModeTab> getCreativeModeTabResourceKey(String path) {
      return getCreativeModeTabResourceKey("minecraft", path);
   }

   @Nullable
   public static ResourceKey<CreativeModeTab> getCreativeModeTabResourceKey(String namespace, String path) {
      return getCreativeModeTabResourceKey(ResourceLocation.fromNamespaceAndPath(namespace, path));
   }

   @Nullable
   public static ResourceKey<CreativeModeTab> getCreativeModeTabResourceKey(ResourceLocation resourceLocation) {
      return BuiltInRegistries.CREATIVE_MODE_TAB
         .getResourceKey((CreativeModeTab)BuiltInRegistries.CREATIVE_MODE_TAB.get(resourceLocation))
         .orElseGet(() -> null);
   }
}
