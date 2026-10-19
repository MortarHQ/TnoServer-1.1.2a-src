package com.natamus.collective_common_fabric.services.helpers;

import java.util.function.Supplier;
import net.minecraft.class_1761;
import net.minecraft.class_1792;
import net.minecraft.class_2960;
import net.minecraft.class_5321;

public interface RegisterItemHelper {
   default <T extends class_1792> void registerItem(
      Object modEventBusObject, class_2960 resourceLocation, Supplier<T> itemSupplier, class_5321<class_1761> creativeModeTabResourceKey
   ) {
      this.registerItem(modEventBusObject, resourceLocation, itemSupplier, creativeModeTabResourceKey, false);
   }

   <T extends class_1792> void registerItem(Object var1, class_2960 var2, Supplier<T> var3, class_5321<class_1761> var4, boolean var5);

   class_1792 getRegisteredItem(class_2960 var1);
}
