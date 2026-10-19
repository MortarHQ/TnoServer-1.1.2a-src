package dev.ftb.mods.ftbultimine.api.blockbreaking;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;

@FunctionalInterface
public interface RegisterBlockBreakHandlerEvent {
   Event<RegisterBlockBreakHandlerEvent> REGISTER = EventFactory.createLoop(new RegisterBlockBreakHandlerEvent[0]);

   void register(RegisterBlockBreakHandlerEvent.Registry var1);

   @FunctionalInterface
   interface Registry {
      void registerHandler(BlockBreakHandler var1);
   }
}
