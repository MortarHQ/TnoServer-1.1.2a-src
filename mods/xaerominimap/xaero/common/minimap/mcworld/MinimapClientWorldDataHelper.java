package xaero.common.minimap.mcworld;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

public class MinimapClientWorldDataHelper {
   public static MinimapClientWorldData getCurrentWorldData() {
      return getWorldData(Minecraft.getInstance().level);
   }

   public static MinimapClientWorldData getWorldData(ClientLevel clientWorld) {
      IXaeroMinimapClientWorld inter = (IXaeroMinimapClientWorld)clientWorld;
      MinimapClientWorldData minimapWorldData = inter.getXaero_minimapData();
      if (minimapWorldData == null) {
         inter.setXaero_minimapData(minimapWorldData = new MinimapClientWorldData(clientWorld));
      }

      return minimapWorldData;
   }
}
