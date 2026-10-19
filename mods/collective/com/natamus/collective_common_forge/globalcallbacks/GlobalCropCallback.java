package com.natamus.collective_common_forge.globalcallbacks;

import com.natamus.collective_common_forge.implementations.event.Event;
import com.natamus.collective_common_forge.implementations.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GlobalCropCallback {
   public static final Event<GlobalCropCallback.On_Bone_Meal_Apply> ON_BONE_MEAL_APPLY = EventFactory.createArrayBacked(
      GlobalCropCallback.On_Bone_Meal_Apply.class, callbacks -> (player, world, pos, state, stack) -> {
         for (GlobalCropCallback.On_Bone_Meal_Apply callback : callbacks) {
            if (!callback.onBoneMealApply(player, world, pos, state, stack)) {
               return false;
            }
         }

         return true;
      }
   );
   public static final Event<GlobalCropCallback.On_General_Bone_Meal_Apply> ON_GENERAL_BONE_MEAL_APPLY = EventFactory.createArrayBacked(
      GlobalCropCallback.On_General_Bone_Meal_Apply.class, callbacks -> (world, pos, state, stack) -> {
         for (GlobalCropCallback.On_General_Bone_Meal_Apply callback : callbacks) {
            if (!callback.onGeneralBoneMealApply(world, pos, state, stack)) {
               return false;
            }
         }

         return true;
      }
   );

   private GlobalCropCallback() {
   }

   @FunctionalInterface
   public interface On_Bone_Meal_Apply {
      boolean onBoneMealApply(Player var1, Level var2, BlockPos var3, BlockState var4, ItemStack var5);
   }

   @FunctionalInterface
   public interface On_General_Bone_Meal_Apply {
      boolean onGeneralBoneMealApply(Level var1, BlockPos var2, BlockState var3, ItemStack var4);
   }
}
