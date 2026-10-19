package dev.ftb.mods.ftbultimine.api.restriction;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;

public interface RegisterRestrictionHandlerEvent {
   Event<RegisterRestrictionHandlerEvent> REGISTER = EventFactory.createLoop(new RegisterRestrictionHandlerEvent[0]);

   void register(RegisterRestrictionHandlerEvent.Registry var1);

   interface Registry {
      void register(RestrictionHandler var1);
   }
}
