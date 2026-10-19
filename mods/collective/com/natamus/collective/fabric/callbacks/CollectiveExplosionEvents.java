package com.natamus.collective.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_1297;
import net.minecraft.class_1927;
import net.minecraft.class_1937;

public class CollectiveExplosionEvents {
   public static final Event<CollectiveExplosionEvents.Explosion_Detonate> EXPLOSION_DETONATE = EventFactory.createArrayBacked(
      CollectiveExplosionEvents.Explosion_Detonate.class, callbacks -> (world, sourceEntity, explosion) -> {
         for (CollectiveExplosionEvents.Explosion_Detonate callback : callbacks) {
            callback.onDetonate(world, sourceEntity, explosion);
         }
      }
   );

   private CollectiveExplosionEvents() {
   }

   @FunctionalInterface
   public interface Explosion_Detonate {
      void onDetonate(class_1937 var1, class_1297 var2, class_1927 var3);
   }
}
