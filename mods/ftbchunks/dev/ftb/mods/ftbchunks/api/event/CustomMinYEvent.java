package dev.ftb.mods.ftbchunks.api.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.ftb.mods.ftbchunks.api.LevelMinYCalculator;

public interface CustomMinYEvent {
   Event<CustomMinYEvent> REGISTER = EventFactory.createLoop(new CustomMinYEvent[0]);

   void register(CustomMinYEvent.CustomMinYRegistry var1);

   interface CustomMinYRegistry {
      void register(LevelMinYCalculator var1);
   }
}
