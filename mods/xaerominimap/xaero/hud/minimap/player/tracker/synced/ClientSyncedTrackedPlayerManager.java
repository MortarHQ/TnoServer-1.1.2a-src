package xaero.hud.minimap.player.tracker.synced;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.common.server.radar.tracker.SyncedTrackedPlayer;

public abstract class ClientSyncedTrackedPlayerManager {
   private final Map<UUID, SyncedTrackedPlayer> trackedPlayers = new HashMap<>();

   protected ClientSyncedTrackedPlayerManager() {
   }

   public void remove(UUID id) {
      this.trackedPlayers.remove(id);
   }

   public void update(UUID id, double x, double y, double z, ResourceKey<Level> dim) {
      SyncedTrackedPlayer current = this.trackedPlayers.get(id);
      if (current != null) {
         current.setPos(x, y, z).setDimension(dim);
      } else {
         this.trackedPlayers.put(id, new SyncedTrackedPlayer(id, x, y, z, dim));
      }
   }

   public Iterable<SyncedTrackedPlayer> getPlayers() {
      return this.trackedPlayers.values();
   }

   public void reset() {
      this.trackedPlayers.clear();
   }

   public static final class Builder {
      private Builder() {
      }

      public ClientSyncedTrackedPlayerManager.Builder setDefault() {
         return this;
      }

      public ClientSyncedTrackedPlayerManager build() {
         return new xaero.common.minimap.radar.tracker.synced.ClientSyncedTrackedPlayerManager();
      }

      public static ClientSyncedTrackedPlayerManager.Builder begin() {
         return new ClientSyncedTrackedPlayerManager.Builder().setDefault();
      }
   }
}
