package com.natamus.collective.forge.events;

import com.natamus.collective_common_forge.events.CollectiveClientEvents;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RegisterCollectiveForgeClientEvents {
   @SubscribeEvent
   public static void onClientTick(ClientTickEvent e) {
      if (e.phase.equals(Phase.END)) {
         CollectiveClientEvents.onClientTick();
      }
   }
}
