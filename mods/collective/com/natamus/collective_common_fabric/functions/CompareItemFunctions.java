package com.natamus.collective_common_fabric.functions;

import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2473;
import net.minecraft.class_3489;
import net.minecraft.class_6862;

public class CompareItemFunctions {
   public static boolean itemIsInRegistryHolder(class_1792 item, class_6862<class_1792> tagKey) {
      return item.method_40131().method_40220(tagKey);
   }

   public static boolean isSapling(class_1792 item) {
      return itemIsInRegistryHolder(item, class_3489.field_15528) || class_2248.method_9503(item) instanceof class_2473;
   }

   public static boolean isSapling(class_1799 itemstack) {
      return isSapling(itemstack.method_7909());
   }

   public static boolean isLog(class_1792 item) {
      return itemIsInRegistryHolder(item, class_3489.field_15539);
   }

   public static boolean isLog(class_1799 itemstack) {
      return isLog(itemstack.method_7909());
   }

   public static boolean isPlank(class_1792 item) {
      return itemIsInRegistryHolder(item, class_3489.field_15537);
   }

   public static boolean isPlank(class_1799 itemstack) {
      return isPlank(itemstack.method_7909());
   }

   public static boolean isChest(class_1792 item) {
      class_2248 block = class_2248.method_9503(item);
      return block.equals(class_2246.field_10034) || block.equals(class_2246.field_10380);
   }

   public static boolean isChest(class_1799 itemstack) {
      return isChest(itemstack.method_7909());
   }

   public static boolean isStone(class_1792 item) {
      return itemIsInRegistryHolder(item, class_3489.field_25808);
   }

   public static boolean isStone(class_1799 itemstack) {
      return isStone(itemstack.method_7909());
   }

   public static boolean isSlab(class_1792 item) {
      return itemIsInRegistryHolder(item, class_3489.field_15535);
   }

   public static boolean isSlab(class_1799 itemstack) {
      return isSlab(itemstack.method_7909());
   }
}
