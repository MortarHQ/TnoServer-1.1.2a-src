package com.natamus.collective_common_fabric.functions;

import com.natamus.collective_common_fabric.data.GlobalVariables;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.stream.IntStream;
import net.minecraft.class_156;
import net.minecraft.class_1923;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2680;
import net.minecraft.class_39;
import net.minecraft.class_8934;
import net.minecraft.class_2338.class_2339;
import net.minecraft.class_2350.class_2353;
import net.minecraft.class_2902.class_2903;

public class FeatureFunctions {
   public static boolean placeBonusChest(class_1937 level, class_2338 blockposIn) {
      class_1923 chunkPos = new class_1923(blockposIn);
      IntArrayList list0 = class_156.method_43251(IntStream.rangeClosed(chunkPos.method_8326(), chunkPos.method_8327()), GlobalVariables.randomSource);
      IntArrayList list1 = class_156.method_43251(IntStream.rangeClosed(chunkPos.method_8328(), chunkPos.method_8329()), GlobalVariables.randomSource);
      class_2339 mutableBlockPos = new class_2339();
      IntListIterator var6 = list0.iterator();

      while (var6.hasNext()) {
         Integer i = (Integer)var6.next();
         IntListIterator var8 = list1.iterator();

         while (var8.hasNext()) {
            Integer j = (Integer)var8.next();
            mutableBlockPos.method_10103(i, 0, j);
            class_2338 heightMapPos = level.method_8598(class_2903.field_13203, mutableBlockPos);
            if (level.method_22347(heightMapPos) || level.method_8320(heightMapPos).method_26220(level, heightMapPos).method_1110()) {
               level.method_8652(heightMapPos, class_2246.field_10034.method_9564(), 2);
               class_8934.method_54868(level, GlobalVariables.randomSource, heightMapPos, class_39.field_850);
               class_2680 blockState = class_2246.field_10336.method_9564();

               for (class_2350 direction : class_2353.field_11062) {
                  class_2338 blockPos = heightMapPos.method_10093(direction);
                  if (blockState.method_26184(level, blockPos)) {
                     level.method_8652(blockPos, blockState, 2);
                  }
               }

               return true;
            }
         }
      }

      return false;
   }
}
