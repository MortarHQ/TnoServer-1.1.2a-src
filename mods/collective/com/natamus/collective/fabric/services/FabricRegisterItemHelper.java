package com.natamus.collective.fabric.services;

import com.natamus.collective_common_fabric.services.helpers.RegisterItemHelper;
import java.util.HashMap;
import java.util.function.Supplier;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries;
import net.minecraft.class_1761;
import net.minecraft.class_1792;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_5321;
import net.minecraft.class_7923;

public class FabricRegisterItemHelper implements RegisterItemHelper {
   private static final HashMap<class_2960, class_1792> itemMap = new HashMap<>();

   @Override
   public <T extends class_1792> void registerItem(
      Object modEventBusObject, class_2960 resourceLocation, Supplier<T> itemSupplier, class_5321<class_1761> creativeModeTabResourceKey, boolean lastItem
   ) {
      staticRegisterItem(modEventBusObject, resourceLocation, itemSupplier, creativeModeTabResourceKey);
   }

   public static <T extends class_1792> class_1792 staticRegisterItem(
      Object modEventBusObject, class_2960 resourceLocation, Supplier<T> itemSupplier, class_5321<class_1761> creativeModeTabResourceKey
   ) {
      class_1792 item = itemSupplier.get();
      class_2378.method_10230(class_7923.field_41178, resourceLocation, item);
      if (creativeModeTabResourceKey != null) {
         ItemGroupEvents.modifyEntriesEvent(creativeModeTabResourceKey).register((ModifyEntries)entries -> entries.method_45421(item));
      }

      itemMap.put(resourceLocation, item);
      return item;
   }

   @Override
   public class_1792 getRegisteredItem(class_2960 resourceLocation) {
      return itemMap.get(resourceLocation);
   }
}
