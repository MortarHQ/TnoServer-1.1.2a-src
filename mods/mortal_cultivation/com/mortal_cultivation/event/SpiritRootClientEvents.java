package com.mortal_cultivation.event;

import com.mortal_cultivation.spiritroot.SpiritRootClientState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;

@EventBusSubscriber(modid = "mortal_cultivation", value = Dist.CLIENT, bus = Bus.GAME)
public final class SpiritRootClientEvents {
   private SpiritRootClientEvents() {
   }

   @SubscribeEvent
   public static void onClientLogout(LoggingOut event) {
      SpiritRootClientState.clear();
   }
}
