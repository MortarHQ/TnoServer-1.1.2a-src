package dev.ftb.mods.ftbultimine.api.crop;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;

@FunctionalInterface
public interface RegisterCropLikeEvent {
   Event<RegisterCropLikeEvent> REGISTER = EventFactory.createLoop(new RegisterCropLikeEvent[0]);

   void register(RegisterCropLikeEvent.Dispatcher var1);

   interface Dispatcher {
      void registerHandler(CropLikeHandler var1);
   }
}
