package com.natamus.starterkit.forge.events;

import com.natamus.starterkit_common_forge.events.StarterClientEvents;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeStarterClientEvents {
   @SubscribeEvent
   public static void onClientTick(ClientTickEvent e) {
      if (e.phase.equals(Phase.START)) {
         StarterClientEvents.onClientTick();
      }
   }
}
