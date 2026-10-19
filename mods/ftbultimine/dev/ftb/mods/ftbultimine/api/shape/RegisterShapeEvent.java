package dev.ftb.mods.ftbultimine.api.shape;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;

@FunctionalInterface
public interface RegisterShapeEvent {
   Event<RegisterShapeEvent> REGISTER = EventFactory.createLoop(new RegisterShapeEvent[0]);

   void register(RegisterShapeEvent.Registry var1);

   interface Registry {
      void register(Shape var1);
   }
}
