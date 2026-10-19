package com.natamus.collective.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;

public class CollectivePistonEvents {
   public static final Event<CollectivePistonEvents.Piston_Activate> PRE_PISTON_ACTIVATE = EventFactory.createArrayBacked(
      CollectivePistonEvents.Piston_Activate.class, callbacks -> (level, blockPos, direction, isExtending) -> {
         for (CollectivePistonEvents.Piston_Activate callback : callbacks) {
            if (!callback.onPistonActivate(level, blockPos, direction, isExtending)) {
               return false;
            }
         }

         return true;
      }
   );

   private CollectivePistonEvents() {
   }

   @FunctionalInterface
   public interface Piston_Activate {
      boolean onPistonActivate(class_1937 var1, class_2338 var2, class_2350 var3, boolean var4);
   }
}
