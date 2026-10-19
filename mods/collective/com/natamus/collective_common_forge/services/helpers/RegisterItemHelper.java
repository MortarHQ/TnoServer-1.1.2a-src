package com.natamus.collective_common_forge.services.helpers;

import java.util.function.Supplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public interface RegisterItemHelper {
   default <T extends Item> void registerItem(
      Object modEventBusObject, ResourceLocation resourceLocation, Supplier<T> itemSupplier, ResourceKey<CreativeModeTab> creativeModeTabResourceKey
   ) {
      this.registerItem(modEventBusObject, resourceLocation, itemSupplier, creativeModeTabResourceKey, false);
   }

   <T extends Item> void registerItem(Object var1, ResourceLocation var2, Supplier<T> var3, ResourceKey<CreativeModeTab> var4, boolean var5);

   Item getRegisteredItem(ResourceLocation var1);
}
