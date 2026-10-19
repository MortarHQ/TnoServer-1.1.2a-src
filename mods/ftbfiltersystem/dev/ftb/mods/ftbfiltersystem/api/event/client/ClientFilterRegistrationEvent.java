package dev.ftb.mods.ftbfiltersystem.api.event.client;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.ftb.mods.ftbfiltersystem.api.client.FTBFilterSystemClientAPI;

public interface ClientFilterRegistrationEvent {
   Event<ClientFilterRegistrationEvent> REGISTER = EventFactory.createLoop(new ClientFilterRegistrationEvent[0]);

   void registerFilters(FTBFilterSystemClientAPI var1);
}
