package org.anti_ad.mc.common.forge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;
import org.anti_ad.mc.common.events.TicksDispatcher;

public class NeoForgeTicksSource {
   @SubscribeEvent
   public void clientTick(Pre e) {
      TicksDispatcher.INSTANCE.dispatchPre();
   }

   @SubscribeEvent
   public void clientTick(Post e) {
      TicksDispatcher.INSTANCE.dispatchPost();
   }
}
