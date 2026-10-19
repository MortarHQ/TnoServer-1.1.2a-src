package xaero.common.minimap.radar.tracker.synced;

import java.util.UUID;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.common.server.radar.tracker.SyncedTrackedPlayer;

@Deprecated
public class ClientSyncedTrackedPlayerManager extends xaero.hud.minimap.player.tracker.synced.ClientSyncedTrackedPlayerManager {
   @Deprecated
   @Override
   public void remove(UUID id) {
      super.remove(id);
   }

   @Deprecated
   @Override
   public void update(UUID id, double x, double y, double z, ResourceKey<Level> dim) {
      super.update(id, x, y, z, dim);
   }

   @Deprecated
   @Override
   public Iterable<SyncedTrackedPlayer> getPlayers() {
      return super.getPlayers();
   }

   @Deprecated
   @Override
   public void reset() {
      super.reset();
   }
}
