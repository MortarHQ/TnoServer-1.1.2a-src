package xaero.map.radar.tracker.system.impl;

import java.util.UUID;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.map.radar.tracker.system.ITrackedPlayerReader;
import xaero.map.server.radar.tracker.SyncedTrackedPlayer;

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
      return player.getDimension();
   }
}
