package xaero.map.mods.minimap.tracker.system;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import xaero.common.XaeroMinimapSession;
import xaero.common.minimap.mcworld.MinimapClientWorldData;
import xaero.common.minimap.mcworld.MinimapClientWorldDataHelper;
import xaero.common.minimap.radar.tracker.synced.ClientSyncedTrackedPlayerManager;
import xaero.common.server.radar.tracker.SyncedTrackedPlayer;
import xaero.map.mods.SupportXaeroMinimap;
import xaero.map.radar.tracker.system.IPlayerTrackerSystem;
import xaero.map.radar.tracker.system.ITrackedPlayerReader;

public class MinimapSyncedPlayerTrackerSystem implements IPlayerTrackerSystem<SyncedTrackedPlayer> {
   private final MinimapSyncedTrackedPlayerReader reader;

   public MinimapSyncedPlayerTrackerSystem(SupportXaeroMinimap minimapSupport) {
      this.reader = new MinimapSyncedTrackedPlayerReader(minimapSupport);
   }

   @Override
   public ITrackedPlayerReader<SyncedTrackedPlayer> getReader() {
      return this.reader;
   }

   @Override
   public Iterator<SyncedTrackedPlayer> getTrackedPlayerIterator() {
      XaeroMinimapSession session = XaeroMinimapSession.getCurrentSession();
      if (session == null) {
         return null;
      }

      if (Minecraft.getInstance().getSingleplayerServer() == null) {
         MinimapClientWorldData worldData = MinimapClientWorldDataHelper.getCurrentWorldData();
         if (worldData.serverLevelId == null) {
            return null;
         }
      }

      ClientSyncedTrackedPlayerManager manager = session.getMinimapProcessor().getClientSyncedTrackedPlayerManager();
      return manager.getPlayers().iterator();
   }
}
