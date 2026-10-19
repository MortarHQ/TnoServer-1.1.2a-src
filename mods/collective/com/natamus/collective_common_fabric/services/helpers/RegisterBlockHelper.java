package com.natamus.collective_common_fabric.services.helpers;

import com.mojang.datafixers.util.Pair;
import java.util.function.Supplier;
import net.minecraft.class_1747;
import net.minecraft.class_1761;
import net.minecraft.class_2248;
import net.minecraft.class_2960;
import net.minecraft.class_5321;

public interface RegisterBlockHelper {
   default <T extends class_2248> void registerBlockWithoutItem(Object modEventBusObject, class_2960 resourceLocation, Supplier<T> blockSupplier) {
      this.registerBlockWithoutItem(modEventBusObject, resourceLocation, blockSupplier, false);
   }

   <T extends class_2248> void registerBlockWithoutItem(Object var1, class_2960 var2, Supplier<T> var3, boolean var4);

   class_2248 getRegisteredBlockWithoutItem(class_2960 var1);

   default <T extends class_2248> void registerBlockWithItem(
      Object modEventBusObject, class_2960 resourceLocation, Supplier<T> blockSupplier, class_5321<class_1761> creativeModeTabResourceKey
   ) {
      this.registerBlockWithItem(modEventBusObject, resourceLocation, blockSupplier, creativeModeTabResourceKey, false);
   }

   <T extends class_2248> void registerBlockWithItem(Object var1, class_2960 var2, Supplier<T> var3, class_5321<class_1761> var4, boolean var5);

   class_2248 getRegisteredBlockWithItem(class_2960 var1);

   Pair<class_2248, class_1747> getRegisteredBlockWithItemPair(class_2960 var1);

   void setRegisteredBlockWithItemPair(class_2960 var1, Class<?> var2, String var3, Class<?> var4, String var5);
}
