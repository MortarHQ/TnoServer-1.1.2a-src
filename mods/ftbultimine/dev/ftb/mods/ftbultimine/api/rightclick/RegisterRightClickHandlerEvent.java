package dev.ftb.mods.ftbultimine.api.rightclick;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;

@FunctionalInterface
public interface RegisterRightClickHandlerEvent {
   Event<RegisterRightClickHandlerEvent> REGISTER = EventFactory.createLoop(new RegisterRightClickHandlerEvent[0]);

   void register(RegisterRightClickHandlerEvent.Dispatcher var1);

   @FunctionalInterface
   interface Dispatcher {
      void registerHandler(RightClickHandler var1);
   }
}
