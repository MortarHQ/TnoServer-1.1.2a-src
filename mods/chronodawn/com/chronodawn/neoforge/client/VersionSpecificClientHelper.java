package com.chronodawn.neoforge.client;

import com.chronodawn.ChronoDawn;
import com.chronodawn.client.PortalEffectHandler;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class VersionSpecificClientHelper {
   public static void registerBoatModelLayers(RegisterLayerDefinitions event) {
      ChronoDawn.LOGGER.debug("Boat model layer registration skipped for 1.21.1");
   }

   public static void onClientTick() {
      PortalEffectHandler.onClientTick();
   }
}
