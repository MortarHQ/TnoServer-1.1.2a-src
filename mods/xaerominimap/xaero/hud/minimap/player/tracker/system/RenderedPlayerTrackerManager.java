package xaero.hud.minimap.player.tracker.system;

import java.util.HashMap;
import java.util.Map;
import xaero.common.minimap.radar.tracker.system.PlayerTrackerSystemManager;
import xaero.hud.minimap.MinimapLogs;

public class RenderedPlayerTrackerManager {
   private final Map<String, IRenderedPlayerTracker<?>> systems = new HashMap<>();

   protected RenderedPlayerTrackerManager() {
   }

   public void register(String name, IRenderedPlayerTracker<?> system) {
      if (this.systems.containsKey(name)) {
         MinimapLogs.LOGGER.error("Player tracker system with the name " + name + " has already been registered!");
      } else {
         this.systems.put(name, system);
         MinimapLogs.LOGGER.info("Registered player tracker system: " + name);
      }
   }

   public Iterable<IRenderedPlayerTracker<?>> getAllSystems() {
      return this.systems.values();
   }

   public static final class Builder {
      private Builder() {
      }

      private RenderedPlayerTrackerManager.Builder setDefault() {
         return this;
      }

      public RenderedPlayerTrackerManager build() {
         return new PlayerTrackerSystemManager();
      }

      public static RenderedPlayerTrackerManager.Builder begin() {
         return new RenderedPlayerTrackerManager.Builder().setDefault();
      }
   }
}
