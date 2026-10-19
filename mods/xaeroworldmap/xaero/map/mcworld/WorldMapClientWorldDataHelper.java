package xaero.map.mcworld;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

public class WorldMapClientWorldDataHelper {
   public static WorldMapClientWorldData getCurrentWorldData() {
      return getWorldData(Minecraft.getInstance().level);
   }

   public static synchronized WorldMapClientWorldData getWorldData(ClientLevel clientWorld) {
      if (clientWorld == null) {
         return null;
      }

      IWorldMapClientWorld inter = (IWorldMapClientWorld)clientWorld;
      WorldMapClientWorldData worldmapWorldData = inter.getXaero_worldmapData();
      if (worldmapWorldData == null) {
         inter.setXaero_worldmapData(worldmapWorldData = new WorldMapClientWorldData(clientWorld));
      }

      return worldmapWorldData;
   }
}
