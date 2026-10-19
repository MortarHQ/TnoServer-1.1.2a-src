package com.natamus.collective.fabric.services;

import com.mojang.datafixers.util.Pair;
import com.natamus.collective_common_fabric.services.helpers.RegisterBlockHelper;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.function.Supplier;
import net.minecraft.class_1747;
import net.minecraft.class_1761;
import net.minecraft.class_1792;
import net.minecraft.class_2248;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_5321;
import net.minecraft.class_7923;
import net.minecraft.class_1792.class_1793;

public class FabricRegisterBlockHelper implements RegisterBlockHelper {
   private static final HashMap<class_2960, class_2248> registeredBlocksWithoutItem = new HashMap<>();
   private static final HashMap<class_2960, Pair<class_2248, class_1792>> registeredBlockWithItemPairs = new HashMap<>();

   @Override
   public <T extends class_2248> void registerBlockWithoutItem(
      Object modEventBusObject, class_2960 resourceLocation, Supplier<T> blockSupplier, boolean lastBlock
   ) {
      staticRegisterBlock(modEventBusObject, resourceLocation, blockSupplier, null, lastBlock, false);
   }

   @Override
   public class_2248 getRegisteredBlockWithoutItem(class_2960 resourceLocation) {
      return registeredBlocksWithoutItem.get(resourceLocation);
   }

   @Override
   public <T extends class_2248> void registerBlockWithItem(
      Object modEventBusObject, class_2960 resourceLocation, Supplier<T> blockSupplier, class_5321<class_1761> creativeModeTabResourceKey, boolean lastBlock
   ) {
      staticRegisterBlock(modEventBusObject, resourceLocation, blockSupplier, creativeModeTabResourceKey, lastBlock, true);
   }

   @Override
   public class_2248 getRegisteredBlockWithItem(class_2960 resourceLocation) {
      return (class_2248)registeredBlockWithItemPairs.get(resourceLocation).getFirst();
   }

   @Override
   public Pair<class_2248, class_1747> getRegisteredBlockWithItemPair(class_2960 resourceLocation) {
      Pair<class_2248, class_1792> registeredPair = registeredBlockWithItemPairs.get(resourceLocation);
      return Pair.of((class_2248)registeredPair.getFirst(), (class_1747)registeredPair.getSecond());
   }

   @Override
   public void setRegisteredBlockWithItemPair(
      class_2960 resourceLocation, Class<?> blockClass, String blockFieldName, Class<?> blockItemClass, String blockItemFieldName
   ) {
      Pair<class_2248, class_1792> registeredPair = registeredBlockWithItemPairs.get(resourceLocation);

      try {
         Field blockField = blockClass.getDeclaredField(blockFieldName);
         blockField.setAccessible(true);
         blockField.set(null, registeredPair.getFirst());
         Field blockItemField = blockItemClass.getDeclaredField(blockItemFieldName);
         blockItemField.setAccessible(true);
         blockItemField.set(null, registeredPair.getSecond());
      } catch (NoSuchFieldException | IllegalAccessException e) {
         e.printStackTrace();
      }
   }

   public static <T extends class_2248> void staticRegisterBlock(
      Object modEventBusObject,
      class_2960 resourceLocation,
      Supplier<T> blockSupplier,
      class_5321<class_1761> creativeModeTabResourceKey,
      boolean lastBlock,
      boolean registerAsItem
   ) {
      class_2248 block = blockSupplier.get();
      class_2378.method_10230(class_7923.field_41175, resourceLocation, block);
      if (registerAsItem) {
         class_1792 item = FabricRegisterItemHelper.staticRegisterItem(
            modEventBusObject, resourceLocation, () -> (T)(new class_1747(block, new class_1793())), creativeModeTabResourceKey
         );
         registeredBlockWithItemPairs.put(resourceLocation, Pair.of(block, item));
      } else {
         registeredBlocksWithoutItem.put(resourceLocation, block);
      }
   }
}
