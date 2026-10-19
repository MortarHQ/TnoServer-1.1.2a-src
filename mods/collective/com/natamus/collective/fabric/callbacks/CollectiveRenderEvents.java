package com.natamus.collective.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_4587;

public class CollectiveRenderEvents {
   public static final Event<CollectiveRenderEvents.Render_Specific_Hand> RENDER_SPECIFIC_HAND = EventFactory.createArrayBacked(
      CollectiveRenderEvents.Render_Specific_Hand.class, callbacks -> (interactionHand, poseStack, itemStack) -> {
         for (CollectiveRenderEvents.Render_Specific_Hand callback : callbacks) {
            if (!callback.onRenderSpecificHand(interactionHand, poseStack, itemStack)) {
               return false;
            }
         }

         return true;
      }
   );

   private CollectiveRenderEvents() {
   }

   @FunctionalInterface
   public interface Render_Specific_Hand {
      boolean onRenderSpecificHand(class_1268 var1, class_4587 var2, class_1799 var3);
   }
}
