package com.natamus.collective.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_1113;
import net.minecraft.class_1140;

public class CollectiveSoundEvents {
   public static final Event<CollectiveSoundEvents.Sound_Play> SOUND_PLAY = EventFactory.createArrayBacked(
      CollectiveSoundEvents.Sound_Play.class, callbacks -> (soundEngine, soundInstance) -> {
         for (CollectiveSoundEvents.Sound_Play callback : callbacks) {
            if (!callback.onSoundPlay(soundEngine, soundInstance)) {
               return false;
            }
         }

         return true;
      }
   );

   private CollectiveSoundEvents() {
   }

   @FunctionalInterface
   public interface Sound_Play {
      boolean onSoundPlay(class_1140 var1, class_1113 var2);
   }
}
