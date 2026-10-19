package com.natamus.collective.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2680;

public class CollectiveCropEvents {
   public static final Event<CollectiveCropEvents.On_Bone_Meal_Apply> ON_BONE_MEAL_APPLY = EventFactory.createArrayBacked(
      CollectiveCropEvents.On_Bone_Meal_Apply.class, callbacks -> (player, world, pos, state, stack) -> {
         for (CollectiveCropEvents.On_Bone_Meal_Apply callback : callbacks) {
            if (!callback.onBoneMealApply(player, world, pos, state, stack)) {
               return false;
            }
         }

         return true;
      }
   );
   public static final Event<CollectiveCropEvents.On_General_Bone_Meal_Apply> ON_GENERAL_BONE_MEAL_APPLY = EventFactory.createArrayBacked(
      CollectiveCropEvents.On_General_Bone_Meal_Apply.class, callbacks -> (world, pos, state, stack) -> {
         for (CollectiveCropEvents.On_General_Bone_Meal_Apply callback : callbacks) {
            if (!callback.onGeneralBoneMealApply(world, pos, state, stack)) {
               return false;
            }
         }

         return true;
      }
   );
   public static final Event<CollectiveCropEvents.On_Pre_Crop_Grow> PRE_CROP_GROW = EventFactory.createArrayBacked(
      CollectiveCropEvents.On_Pre_Crop_Grow.class, callbacks -> (world, pos, state) -> {
         for (CollectiveCropEvents.On_Pre_Crop_Grow callback : callbacks) {
            if (!callback.onPreCropGrow(world, pos, state)) {
               return false;
            }
         }

         return true;
      }
   );

   private CollectiveCropEvents() {
   }

   @FunctionalInterface
   public interface On_Bone_Meal_Apply {
      boolean onBoneMealApply(class_1657 var1, class_1937 var2, class_2338 var3, class_2680 var4, class_1799 var5);
   }

   @FunctionalInterface
   public interface On_General_Bone_Meal_Apply {
      boolean onGeneralBoneMealApply(class_1937 var1, class_2338 var2, class_2680 var3, class_1799 var4);
   }

   @FunctionalInterface
   public interface On_Pre_Crop_Grow {
      boolean onPreCropGrow(class_1937 var1, class_2338 var2, class_2680 var3);
   }
}
