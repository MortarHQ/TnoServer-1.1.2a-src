package xaero.map.radar.tracker.system.impl;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import xaero.map.WorldMapSession;
import xaero.map.mcworld.WorldMapClientWorldData;
import xaero.map.mcworld.WorldMapClientWorldDataHelper;
import xaero.map.radar.tracker.synced.ClientSyncedTrackedPlayerManager;
import xaero.map.radar.tracker.system.IPlayerTrackerSystem;
import xaero.map.radar.tracker.system.ITrackedPlayerReader;
import xaero.map.server.radar.tracker.SyncedTrackedPlayer;

public class SyncedPlayerTrackerSystem implements IPlayerTrackerSystem<SyncedTrackedPlayer> {
   private final SyncedTrackedPlayerReader reader = new SyncedTrackedPlayerReader();

   @Override
   public ITrackedPlayerReader<SyncedTrackedPlayer> getReader() {
      return this.reader;
   }

   @Override
   public Iterator<SyncedTrackedPlayer> getTrackedPlayerIterator() {
      WorldMapSession session = WorldMapSession.getCurrentSession();
      if (session == null) {
         return null;
      }

      if (Minecraft.getInstance().getSingleplayerServer() == null) {
         WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getCurrentWorldData();
         if (worldData.serverLevelId == null) {
            return null;
         }
      }

      ClientSyncedTrackedPlayerManager manager = session.getMapProcessor().getClientSyncedTrackedPlayerManager();
      return manager.getPlayers().iterator();
   }
}
