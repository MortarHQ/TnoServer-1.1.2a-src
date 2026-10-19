package xaero.common.minimap.mcworld;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import xaero.hud.packet.basic.ClientboundRulesPacket;

public class MinimapClientWorldData {
   private int serverModNetworkVersion;
   public Integer serverLevelId;
   public float shadowR = 1.0F;
   public float shadowG = 1.0F;
   public float shadowB = 1.0F;
   private ClientboundRulesPacket syncedRules;

   public MinimapClientWorldData(ClientLevel world) {
      if (world.dimensionType().effectsLocation().equals(BuiltinDimensionTypes.OVERWORLD_EFFECTS)) {
         this.shadowR = 0.518F;
         this.shadowG = 0.678F;
         this.shadowB = 1.0F;
      } else if (world.dimensionType().effectsLocation().equals(BuiltinDimensionTypes.NETHER_EFFECTS)) {
         this.shadowR = 1.0F;
         this.shadowG = 0.0F;
         this.shadowB = 0.0F;
      }
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
         this.syncedRules = new ClientboundRulesPacket(true, true, true);
      }

      return this.syncedRules;
   }
}
