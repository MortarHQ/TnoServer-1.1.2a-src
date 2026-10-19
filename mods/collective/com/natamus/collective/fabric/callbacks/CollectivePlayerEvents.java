package com.natamus.collective.fabric.callbacks;

import javax.annotation.Nullable;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

public final class CollectivePlayerEvents {
   public static final Event<CollectivePlayerEvents.Player_Tick> PLAYER_TICK = EventFactory.createArrayBacked(
      CollectivePlayerEvents.Player_Tick.class, callbacks -> (world, player) -> {
         for (CollectivePlayerEvents.Player_Tick callback : callbacks) {
            callback.onTick(world, player);
         }
      }
   );
   public static final Event<CollectivePlayerEvents.Player_Death> PLAYER_DEATH = EventFactory.createArrayBacked(
      CollectivePlayerEvents.Player_Death.class, callbacks -> (world, player) -> {
         for (CollectivePlayerEvents.Player_Death callback : callbacks) {
            callback.onDeath(world, player);
         }
      }
   );
   public static final Event<CollectivePlayerEvents.Player_Change_Dimension> PLAYER_CHANGE_DIMENSION = EventFactory.createArrayBacked(
      CollectivePlayerEvents.Player_Change_Dimension.class, callbacks -> (world, player) -> {
         for (CollectivePlayerEvents.Player_Change_Dimension callback : callbacks) {
            callback.onChangeDimension(world, player);
         }
      }
   );
   public static final Event<CollectivePlayerEvents.Player_Dig_Speed_Calc> ON_PLAYER_DIG_SPEED_CALC = EventFactory.createArrayBacked(
      CollectivePlayerEvents.Player_Dig_Speed_Calc.class, callbacks -> (world, player, digSpeed, state) -> {
         for (CollectivePlayerEvents.Player_Dig_Speed_Calc callback : callbacks) {
            float newSpeed = callback.onDigSpeedCalc(world, player, digSpeed, state);
            if (newSpeed != digSpeed) {
               return newSpeed;
            }
         }

         return -1.0F;
      }
   );
   public static final Event<CollectivePlayerEvents.Player_Picked_Up_Item> ON_ITEM_PICKED_UP = EventFactory.createArrayBacked(
      CollectivePlayerEvents.Player_Picked_Up_Item.class, callbacks -> (level, player, itemStack) -> {
         for (CollectivePlayerEvents.Player_Picked_Up_Item callback : callbacks) {
            callback.onItemPickedUp(level, player, itemStack);
         }
      }
   );
   public static final Event<CollectivePlayerEvents.Player_Logged_In> PLAYER_LOGGED_IN = EventFactory.createArrayBacked(
      CollectivePlayerEvents.Player_Logged_In.class, callbacks -> (world, player) -> {
         for (CollectivePlayerEvents.Player_Logged_In callback : callbacks) {
            callback.onPlayerLoggedIn(world, player);
         }
      }
   );
   public static final Event<CollectivePlayerEvents.Player_Logged_Out> PLAYER_LOGGED_OUT = EventFactory.createArrayBacked(
      CollectivePlayerEvents.Player_Logged_Out.class, callbacks -> (world, player) -> {
         for (CollectivePlayerEvents.Player_Logged_Out callback : callbacks) {
            callback.onPlayerLoggedOut(world, player);
         }
      }
   );

   private CollectivePlayerEvents() {
   }

   @FunctionalInterface
   public interface Player_Change_Dimension {
      void onChangeDimension(class_3218 var1, class_3222 var2);
   }

   @FunctionalInterface
   public interface Player_Death {
      void onDeath(class_3218 var1, class_3222 var2);
   }

   @FunctionalInterface
   public interface Player_Dig_Speed_Calc {
      float onDigSpeedCalc(class_1937 var1, class_1657 var2, float var3, class_2680 var4);
   }

   @FunctionalInterface
   public interface Player_Logged_In {
      void onPlayerLoggedIn(class_1937 var1, class_1657 var2);
   }

   @FunctionalInterface
   public interface Player_Logged_Out {
      void onPlayerLoggedOut(class_1937 var1, class_1657 var2);
   }

   @FunctionalInterface
   public interface Player_Picked_Up_Item {
      void onItemPickedUp(class_1937 var1, class_1657 var2, @Nullable class_1799 var3);
   }

   @FunctionalInterface
   public interface Player_Tick {
      void onTick(class_3218 var1, class_3222 var2);
   }
}
