package com.natamus.collective_common_fabric.functions;

import java.util.Collections;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2256;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_2758;
import net.minecraft.class_2769;
import net.minecraft.class_3218;

public class CropFunctions {
   public static boolean applyBonemeal(class_1799 itemstack, class_1937 world, class_2338 pos, class_1657 player) {
      class_2680 blockstate = world.method_8320(pos);
      if (blockstate.method_26204() instanceof class_2256) {
         class_2256 bonemealableblock = (class_2256)blockstate.method_26204();
         if (bonemealableblock.method_9651(world, pos, blockstate)) {
            if (world instanceof class_3218) {
               if (bonemealableblock.method_9650(world, world.field_9229, pos, blockstate)) {
                  bonemealableblock.method_9652((class_3218)world, world.field_9229, pos, blockstate);
               }

               if (!player.method_7337()) {
                  itemstack.method_7934(1);
               }
            }

            return true;
         }
      }

      return false;
   }

   public static boolean growCrop(class_1937 level, class_1657 player, class_2680 blockState, class_2338 blockPos) {
      return growCrop(level, player, blockState, blockPos, player.method_5998(class_1268.field_5808));
   }

   public static boolean growCrop(class_1937 level, class_1657 player, class_2680 blockState, class_2338 blockPos, class_1268 interactionHand) {
      return growCrop(level, player, blockState, blockPos, player.method_5998(interactionHand));
   }

   public static boolean growCrop(class_1937 level, class_1657 player, class_2680 blockState, class_2338 blockPos, class_1799 itemStack) {
      if (level.field_9236) {
         return false;
      }

      if (blockState.method_26204() instanceof class_2256 igrowable) {
         while (igrowable.method_9651(level, blockPos, blockState) && igrowable.method_9650(level, level.field_9229, blockPos, blockState)) {
            igrowable.method_9652((class_3218)level, level.field_9229, blockPos, blockState);
            blockState = level.method_8320(blockPos);
            itemStack.method_7934(1);
            if (itemStack.method_7947() == 0) {
               break;
            }
         }
      } else {
         for (class_2769<?> property : Collections.unmodifiableCollection(blockState.method_11656().keySet())) {
            if (property instanceof class_2758 prop) {
               String name = prop.method_11899();
               if (name.equals("age")) {
                  Comparable<?> cv = (Comparable<?>)blockState.method_11656().get(property);
                  int value = Integer.parseUnsignedInt(cv.toString());
                  int max = Collections.<Integer>max(prop.method_11898());
                  if (value == max) {
                     return false;
                  }

                  while (value < max) {
                     level.method_8501(blockPos, (class_2680)level.method_8320(blockPos).method_28493(property));
                     if (!player.method_7337()) {
                        itemStack.method_7934(1);
                        if (itemStack.method_7947() == 0) {
                           break;
                        }
                     }

                     value++;
                     if (!player.method_18276()) {
                        break;
                     }
                  }
               }
            }
         }
      }

      level.method_20290(2005, blockPos, 0);
      return true;
   }

   public static boolean growCactus(class_1937 world, class_2338 pos) {
      int height = world.method_31605();

      for (int y = pos.method_10264(); y <= height; y++) {
         class_2338 uppos = new class_2338(pos.method_10263(), y, pos.method_10260());
         class_2248 block = world.method_8320(uppos).method_26204();
         if (block != class_2246.field_10029) {
            if (block.equals(class_2246.field_10124)) {
               world.method_8501(uppos, class_2246.field_10029.method_9564());
               world.method_20290(2005, uppos, 0);
               world.method_20290(2005, uppos.method_10084(), 0);
               return true;
            }
            break;
         }
      }

      return false;
   }

   public static boolean growSugarcane(class_1937 world, class_2338 pos) {
      int height = world.method_31605();

      for (int y = pos.method_10264(); y <= height; y++) {
         class_2338 uppos = new class_2338(pos.method_10263(), y, pos.method_10260());
         class_2248 block = world.method_8320(uppos).method_26204();
         if (block != class_2246.field_10424) {
            if (block.equals(class_2246.field_10124)) {
               world.method_8501(uppos, class_2246.field_10424.method_9564());
               world.method_20290(2005, uppos, 0);
               world.method_20290(2005, uppos.method_10084(), 0);
               return true;
            }
            break;
         }
      }

      return false;
   }

   public static boolean growVine(class_1937 world, class_2338 pos) {
      for (int y = pos.method_10264(); y > 0; y--) {
         class_2338 downpos = new class_2338(pos.method_10263(), y, pos.method_10260());
         class_2248 block = world.method_8320(downpos).method_26204();
         if (block != class_2246.field_10597) {
            if (block.equals(class_2246.field_10124)) {
               world.method_8501(downpos, world.method_8320(pos));
               world.method_20290(2005, downpos, 0);
               world.method_20290(2005, downpos.method_10074(), 0);
               return true;
            }
            break;
         }
      }

      return false;
   }
}
