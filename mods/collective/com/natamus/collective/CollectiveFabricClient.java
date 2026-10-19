package com.natamus.collective;

import com.natamus.collective.fabric.networking.FabricNetworkHandler;
import com.natamus.collective_common_fabric.events.CollectiveClientEvents;
import com.natamus.collective_common_fabric.globalcallbacks.CollectiveGuiCallback;
import com.natamus.collective_common_fabric.implementations.networking.NetworkSetup;
import com.natamus.collective_common_fabric.implementations.networking.data.Side;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class CollectiveFabricClient implements ClientModInitializer {
   public void onInitializeClient() {
      new NetworkSetup(new FabricNetworkHandler(Side.CLIENT));
      this.registerEvents();
   }

   private void registerEvents() {
      HudRenderCallback.EVENT
         .register((HudRenderCallback)(guiGraphics, deltaTracker) -> CollectiveGuiCallback.ON_GUI_RENDER.invoker().onGuiRender(guiGraphics, deltaTracker));
      ClientTickEvents.END_CLIENT_TICK.register((EndTick)mc -> CollectiveClientEvents.onClientTick());
   }
}
