package xaero.map.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Pre;

public class CommonEventsNeoForge extends CommonEvents {
   @SubscribeEvent
   public void onPlayerClone(Clone event) {
      super.onPlayerClone(event.getOriginal(), event.getEntity(), !event.isWasDeath());
   }

   @SubscribeEvent
   public void onServerStarting(ServerStartingEvent event) {
      super.onServerStarting(event.getServer());
   }

   @SubscribeEvent
   public void onServerStopped(ServerStoppedEvent event) {
      super.onServerStopped(event.getServer());
   }

   @SubscribeEvent
   public void onPlayerLogIn(PlayerLoggedInEvent event) {
      super.onPlayerLogIn(event.getEntity());
   }

   @SubscribeEvent
   public void handlePlayerTickStart(Pre event) {
      super.handlePlayerTickStart(event.getEntity());
   }
}
