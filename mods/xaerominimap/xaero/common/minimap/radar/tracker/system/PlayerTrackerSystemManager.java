package xaero.common.minimap.radar.tracker.system;

import java.util.HashMap;
import java.util.Map;
import xaero.common.minimap.radar.tracker.system.compat.PlayerTrackerSystemWrapper;
import xaero.hud.minimap.player.tracker.system.IRenderedPlayerTracker;
import xaero.hud.minimap.player.tracker.system.RenderedPlayerTrackerManager;

@Deprecated
public class PlayerTrackerSystemManager extends RenderedPlayerTrackerManager {
   private final Map<String, IPlayerTrackerSystem<?>> compatibleSystems = new HashMap<>();

   @Deprecated
   public void register(String name, IPlayerTrackerSystem<?> system) {
      this.register(name, (IRenderedPlayerTracker<?>)system);
   }

   @Override
   public void register(String name, IRenderedPlayerTracker<?> system) {
      super.register(name, system);
      if (!this.compatibleSystems.containsKey(name)) {
         if (!(system instanceof IPlayerTrackerSystem<?> compatibleSystem)) {
            compatibleSystem = new PlayerTrackerSystemWrapper<>(system);
         }

         this.compatibleSystems.put(name, compatibleSystem);
      }
   }

   @Deprecated
   public Iterable<IPlayerTrackerSystem<?>> getSystems() {
      return this.compatibleSystems.values();
   }
}
