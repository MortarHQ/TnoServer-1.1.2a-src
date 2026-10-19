package com.chronodawn.core.portal;

import com.chronodawn.ChronoDawn;
import com.chronodawn.data.PortalRegistryData;
import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class PortalPersistenceManager {
   public static void initialize() {
      LifecycleEvent.SERVER_LEVEL_LOAD.register(PortalPersistenceManager::onServerLevelLoad);
      ChronoDawn.LOGGER.debug("Portal Persistence Manager initialized");
   }

   private static void onServerLevelLoad(ServerLevel level) {
      if (level.dimension().equals(Level.OVERWORLD)) {
         ChronoDawn.LOGGER.debug("Loading portal registry from world save data...");
         PortalRegistryData data = PortalRegistryData.get(level);
         PortalRegistry.getInstance().setSavedData(data);
         ChronoDawn.LOGGER.debug("Portal registry loaded successfully");
      }
   }
}
