package com.natamus.collective_common_fabric.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Arrays;
import java.util.List;
import net.minecraft.class_1304;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1890;
import net.minecraft.class_1893;
import net.minecraft.class_1937;
import net.minecraft.class_7924;
import net.minecraft.class_9304;
import net.minecraft.class_9304.class_9305;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
   public static final Logger LOG = LoggerFactory.getLogger("Collective");
   public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   public static final List<class_1304> equipmentSlots = Arrays.asList(
      class_1304.field_6169, class_1304.field_6174, class_1304.field_6172, class_1304.field_6166, class_1304.field_6171
   );
   public static final class_1799 normalPickaxeStack = new class_1799(class_1802.field_22024);
   public static final class_1799 silkPickaxeStack = new class_1799(class_1802.field_22024);
   private static boolean ranInit = false;

   public static void initConstantData(class_1937 level) {
      if (!ranInit) {
         class_9305 itemEnchantmentsMutable = new class_9305(class_9304.field_49385);
         itemEnchantmentsMutable.method_57547(level.method_30349().method_30530(class_7924.field_41265).method_40290(class_1893.field_9099), 1);
         class_1890.method_57530(silkPickaxeStack, itemEnchantmentsMutable.method_57549());
         ranInit = true;
      }
   }
}
