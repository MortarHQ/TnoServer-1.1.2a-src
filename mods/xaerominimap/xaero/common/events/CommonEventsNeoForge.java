package xaero.common.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Pre;
import xaero.common.HudMod;

public class CommonEventsNeoForge extends CommonEvents {
   public CommonEventsNeoForge(HudMod modMain) {
      super(modMain);
   }

   @SubscribeEvent
   public void onPlayerClone(Clone event) {
      this.onPlayerClone(event.getOriginal(), event.getEntity(), !event.isWasDeath());
   }

   @SubscribeEvent
   public void onServerStarting(ServerStartingEvent event) {
      this.onServerStarting(event.getServer());
   }

   @SubscribeEvent
   public void onServerStopped(ServerStoppedEvent event) {
      this.onServerStopped(event.getServer());
   }

   @SubscribeEvent
   public void onPlayerLogIn(PlayerLoggedInEvent event) {
      this.onPlayerLogIn(event.getEntity());
   }

   @SubscribeEvent
   public void handlePlayerTickStart(Pre event) {
      super.handlePlayerTickStart(event.getEntity());
   }
}
