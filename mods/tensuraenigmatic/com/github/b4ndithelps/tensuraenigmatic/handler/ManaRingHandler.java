package com.github.b4ndithelps.tensuraenigmatic.handler;

import com.github.b4ndithelps.tensuraenigmatic.items.ManaRing;
import com.github.b4ndithelps.tensuraenigmatic.registry.TensuraEnigmaticItems;
import com.github.b4ndithelps.tensuraenigmatic.util.CurioHelper;
import com.github.b4ndithelps.tensuraenigmatic.util.TensuraEnergyHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public class ManaRingHandler {
   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (!event.getEntity().level().isClientSide()
         && event.getEntity().tickCount % ManaRing.getRecoveryInterval() == 0
         && CurioHelper.hasCurio(event.getEntity(), TensuraEnigmaticItems.MANA_RING)) {
         TensuraEnergyHelper.restoreMagicule(event.getEntity(), ManaRing.getManaRecovery());
      }
   }
}
