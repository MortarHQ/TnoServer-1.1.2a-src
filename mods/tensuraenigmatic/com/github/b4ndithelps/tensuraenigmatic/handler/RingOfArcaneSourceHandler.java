package com.github.b4ndithelps.tensuraenigmatic.handler;

import com.github.b4ndithelps.tensuraenigmatic.items.RingOfArcaneSource;
import com.github.b4ndithelps.tensuraenigmatic.registry.TensuraEnigmaticItems;
import com.github.b4ndithelps.tensuraenigmatic.util.CurioHelper;
import com.github.b4ndithelps.tensuraenigmatic.util.TensuraEnergyHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public class RingOfArcaneSourceHandler {
   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (!event.getEntity().level().isClientSide()
         && event.getEntity().tickCount % 20 == 0
         && CurioHelper.hasCurio(event.getEntity(), TensuraEnigmaticItems.RING_OF_ARCANE_SOURCE)) {
         TensuraEnergyHelper.restoreMagicule(event.getEntity(), RingOfArcaneSource.getMagiculeRecovery());
      }
   }
}
