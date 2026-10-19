package com.natamus.collective_common_fabric.functions;

import net.minecraft.class_2195;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2261;
import net.minecraft.class_2302;
import net.minecraft.class_2311;
import net.minecraft.class_2320;
import net.minecraft.class_2356;
import net.minecraft.class_2397;
import net.minecraft.class_2423;
import net.minecraft.class_2465;
import net.minecraft.class_2473;
import net.minecraft.class_2488;
import net.minecraft.class_2513;
import net.minecraft.class_2526;
import net.minecraft.class_3481;
import net.minecraft.class_3830;
import net.minecraft.class_6862;

public class CompareBlockFunctions {
   public static boolean blockIsInRegistryHolder(class_2248 block, class_6862<class_2248> tagKey) {
      return block.method_40142().method_40220(tagKey);
   }

   public static boolean isStoneBlock(class_2248 block) {
      return blockIsInRegistryHolder(block, class_3481.field_25806);
   }

   public static boolean isNetherStoneBlock(class_2248 block) {
      return blockIsInRegistryHolder(block, class_3481.field_25807);
   }

   public static boolean isTreeLeaf(class_2248 block, boolean withNetherVariants) {
      if (!blockIsInRegistryHolder(block, class_3481.field_15503) && !(block instanceof class_2397)) {
         if (!withNetherVariants || !block.equals(class_2246.field_10541) && !block.equals(class_2246.field_22115) && !block.equals(class_2246.field_22122)) {
            return !(block instanceof class_2261)
               ? false
               : !(block instanceof class_2302)
                  && !(block instanceof class_2311)
                  && !(block instanceof class_2320)
                  && !(block instanceof class_2356)
                  && !(block instanceof class_2473)
                  && !(block instanceof class_2513)
                  && !(block instanceof class_2195)
                  && !(block instanceof class_3830)
                  && !(block instanceof class_2526);
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   public static boolean isTreeLeaf(class_2248 block) {
      return isTreeLeaf(block, true);
   }

   public static boolean isTreeLog(class_2248 block) {
      return blockIsInRegistryHolder(block, class_3481.field_15475) || block instanceof class_2465;
   }

   public static boolean isSapling(class_2248 block) {
      return blockIsInRegistryHolder(block, class_3481.field_15462) || block instanceof class_2473;
   }

   public static boolean isDirtBlock(class_2248 block) {
      return block.equals(class_2246.field_10219)
         || block.equals(class_2246.field_10566)
         || block.equals(class_2246.field_10253)
         || block.equals(class_2246.field_10520);
   }

   public static boolean isPortalBlock(class_2248 block) {
      return block instanceof class_2423 || BlockFunctions.blockToReadableString(block).equals("portal placeholder");
   }

   public static boolean isAirOrOverwritableBlock(class_2248 block) {
      return block.equals(class_2246.field_10124) || block instanceof class_2261 || block instanceof class_2488;
   }
}
