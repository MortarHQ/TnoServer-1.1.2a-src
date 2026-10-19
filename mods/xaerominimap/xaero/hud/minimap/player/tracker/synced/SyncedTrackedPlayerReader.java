package xaero.hud.minimap.player.tracker.synced;

import java.util.UUID;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.common.server.radar.tracker.SyncedTrackedPlayer;
import xaero.hud.minimap.player.tracker.system.ITrackedPlayerReader;

public class SyncedTrackedPlayerReader implements ITrackedPlayerReader<SyncedTrackedPlayer> {
   public UUID getId(SyncedTrackedPlayer player) {
      return player.getId();
   }

   public double getX(SyncedTrackedPlayer player) {
      return player.getX();
   }

   public double getY(SyncedTrackedPlayer player) {
      return player.getY();
   }

   public double getZ(SyncedTrackedPlayer player) {
      return player.getZ();
   }

   public ResourceKey<Level> getDimension(SyncedTrackedPlayer player) {
      return player.getDimensionKey();
   }
}
