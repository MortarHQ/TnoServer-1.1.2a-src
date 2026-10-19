package com.natamus.starterkit;

import com.natamus.collective_common_fabric.check.ShouldLoadCheck;
import com.natamus.starterkit_common_fabric.ModCommon;
import com.natamus.starterkit_common_fabric.events.StarterClientEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.StartTick;

public class ModFabricClient implements ClientModInitializer {
   public void onInitializeClient() {
      if (ShouldLoadCheck.shouldLoad("starterkit")) {
         ModCommon.registerPackets();
         this.registerEvents();
      }
   }

   private void registerEvents() {
      ClientTickEvents.START_CLIENT_TICK.register((StartTick)client -> StarterClientEvents.onClientTick());
   }
}
