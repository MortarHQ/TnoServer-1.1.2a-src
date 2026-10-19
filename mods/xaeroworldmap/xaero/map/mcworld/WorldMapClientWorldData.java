package xaero.map.mcworld;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import xaero.map.message.basic.ClientboundRulesPacket;

public class WorldMapClientWorldData {
   private int serverModNetworkVersion;
   public Integer serverLevelId;
   public Integer usedServerLevelId;
   public BlockPos latestSpawn;
   public BlockPos usedSpawn;
   private ClientboundRulesPacket syncedRules;

   public WorldMapClientWorldData(ClientLevel world) {
   }

   public void setServerModNetworkVersion(int serverModNetworkVersion) {
      this.serverModNetworkVersion = serverModNetworkVersion;
   }

   public int getServerModNetworkVersion() {
      return this.serverModNetworkVersion;
   }

   public void setSyncedRules(ClientboundRulesPacket syncedRules) {
      this.syncedRules = syncedRules;
   }

   public ClientboundRulesPacket getSyncedRules() {
      if (this.syncedRules == null) {
         this.syncedRules = new ClientboundRulesPacket(true, true);
      }

      return this.syncedRules;
   }
}
