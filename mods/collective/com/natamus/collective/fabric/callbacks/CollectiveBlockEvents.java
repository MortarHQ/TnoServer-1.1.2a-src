package com.natamus.collective.fabric.callbacks;

import java.util.EnumSet;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_1268;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2424;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_3965;

public class CollectiveBlockEvents {
   public static final Event<CollectiveBlockEvents.On_Block_Place> BLOCK_PLACE = EventFactory.createArrayBacked(
      CollectiveBlockEvents.On_Block_Place.class, callbacks -> (level, blockPos, blockState, livingEntity, itemStack) -> {
         for (CollectiveBlockEvents.On_Block_Place callback : callbacks) {
            if (!callback.onBlockPlace(level, blockPos, blockState, livingEntity, itemStack)) {
               return false;
            }
         }

         return true;
      }
   );
   public static final Event<CollectiveBlockEvents.On_Block_Destroy> BLOCK_DESTROY = EventFactory.createArrayBacked(
      CollectiveBlockEvents.On_Block_Destroy.class, callbacks -> (level, player, blockPos, blockState, blockEntity, itemStack) -> {
         for (CollectiveBlockEvents.On_Block_Destroy callback : callbacks) {
            if (!callback.onBlockDestroy(level, player, blockPos, blockState, blockEntity, itemStack)) {
               return false;
            }
         }

         return true;
      }
   );
   public static final Event<CollectiveBlockEvents.On_Neighbour_Notify> NEIGHBOUR_NOTIFY = EventFactory.createArrayBacked(
      CollectiveBlockEvents.On_Neighbour_Notify.class, callbacks -> (world, pos, state, notifiedSides, forceRedstoneUpdate) -> {
         for (CollectiveBlockEvents.On_Neighbour_Notify callback : callbacks) {
            if (!callback.onNeighbourNotify(world, pos, state, notifiedSides, forceRedstoneUpdate)) {
               return false;
            }
         }

         return true;
      }
   );
   public static final Event<CollectiveBlockEvents.Possible_Portal_Spawn> ON_NETHER_PORTAL_SPAWN = EventFactory.createArrayBacked(
      CollectiveBlockEvents.Possible_Portal_Spawn.class, callbacks -> (world, pos, shape) -> {
         for (CollectiveBlockEvents.Possible_Portal_Spawn callback : callbacks) {
            callback.onPossiblePortal(world, pos, shape);
         }
      }
   );
   public static final Event<CollectiveBlockEvents.Block_Right_Click> BLOCK_RIGHT_CLICK = EventFactory.createArrayBacked(
      CollectiveBlockEvents.Block_Right_Click.class, callbacks -> (world, player, hand, pos, hitVec) -> {
         for (CollectiveBlockEvents.Block_Right_Click callback : callbacks) {
            if (!callback.onBlockRightClick(world, player, hand, pos, hitVec)) {
               return false;
            }
         }

         return true;
      }
   );
   public static final Event<CollectiveBlockEvents.Block_Left_Click> BLOCK_LEFT_CLICK = EventFactory.createArrayBacked(
      CollectiveBlockEvents.Block_Left_Click.class, callbacks -> (world, player, pos, direction) -> {
         for (CollectiveBlockEvents.Block_Left_Click callback : callbacks) {
            if (!callback.onBlockLeftClick(world, player, pos, direction)) {
               return false;
            }
         }

         return true;
      }
   );

   private CollectiveBlockEvents() {
   }

   @FunctionalInterface
   public interface Block_Left_Click {
      boolean onBlockLeftClick(class_1937 var1, class_1657 var2, class_2338 var3, class_2350 var4);
   }

   @FunctionalInterface
   public interface Block_Right_Click {
      boolean onBlockRightClick(class_1937 var1, class_1657 var2, class_1268 var3, class_2338 var4, class_3965 var5);
   }

   @FunctionalInterface
   public interface On_Block_Destroy {
      boolean onBlockDestroy(class_1937 var1, class_1657 var2, class_2338 var3, class_2680 var4, class_2586 var5, class_1799 var6);
   }

   @FunctionalInterface
   public interface On_Block_Place {
      boolean onBlockPlace(class_1937 var1, class_2338 var2, class_2680 var3, class_1309 var4, class_1799 var5);
   }

   @FunctionalInterface
   public interface On_Neighbour_Notify {
      boolean onNeighbourNotify(class_1937 var1, class_2338 var2, class_2680 var3, EnumSet<class_2350> var4, boolean var5);
   }

   @FunctionalInterface
   public interface Possible_Portal_Spawn {
      void onPossiblePortal(class_1937 var1, class_2338 var2, class_2424 var3);
   }
}
