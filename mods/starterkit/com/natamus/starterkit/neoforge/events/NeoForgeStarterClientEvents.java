package com.natamus.starterkit.neoforge.events;

import com.natamus.starterkit_common_neoforge.events.StarterClientEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;

public class NeoForgeStarterClientEvents {
   @SubscribeEvent
   public static void onClientTick(Pre e) {
      StarterClientEvents.onClientTick();
   }
}
