package com.natamus.collective.neoforge.events;

import com.natamus.collective_common_neoforge.events.CollectiveClientEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

public class RegisterCollectiveNeoForgeClientEvents {
   @SubscribeEvent
   public static void onClientTick(Post e) {
      CollectiveClientEvents.onClientTick();
   }
}
