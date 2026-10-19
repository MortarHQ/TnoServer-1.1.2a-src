package dev.ftb.mods.ftbchunks.api.client.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.ftb.mods.ftbchunks.api.client.waypoint.WaypointManager;

public interface WaypointManagerEvent {
   Event<WaypointManagerEvent> AVAILABLE = EventFactory.createLoop(new WaypointManagerEvent[0]);

   void onAvailable(WaypointManager var1);
}
