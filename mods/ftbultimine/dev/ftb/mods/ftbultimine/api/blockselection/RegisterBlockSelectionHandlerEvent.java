package dev.ftb.mods.ftbultimine.api.blockselection;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;

@FunctionalInterface
public interface RegisterBlockSelectionHandlerEvent {
   Event<RegisterBlockSelectionHandlerEvent> REGISTER = EventFactory.createLoop(new RegisterBlockSelectionHandlerEvent[0]);

   void register(RegisterBlockSelectionHandlerEvent.Dispatcher var1);

   interface Dispatcher {
      void registerHandler(BlockSelectionHandler var1);
   }
}
