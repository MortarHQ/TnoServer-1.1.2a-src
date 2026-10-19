package com.github.b4ndithelps.tensuraenigmatic.compat;

import com.github.b4ndithelps.tensuraenigmatic.handler.CursedItemTooltipHandler;
import com.github.b4ndithelps.tensuraenigmatic.handler.CursedRingTooltipHandler;
import com.github.b4ndithelps.tensuraenigmatic.handler.TheCubeTooltipHandler;
import net.neoforged.bus.api.IEventBus;

public final class EnigmaticClientCompat {
   static void register(IEventBus gameBus) {
      gameBus.register(CursedItemTooltipHandler.class);
      gameBus.register(CursedRingTooltipHandler.class);
      gameBus.register(TheCubeTooltipHandler.class);
   }

   private EnigmaticClientCompat() {
   }
}
