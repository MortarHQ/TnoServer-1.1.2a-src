package artifacts.integration.trinkets;

import artifacts.equipment.client.EquipmentRenderingManager;
import artifacts.platform.PlatformServices;

public class TrinketsCompatClient {
   public static void setup() {
      if (!PlatformServices.platformHelper.isModLoaded("tclayer")) {
         EquipmentRenderingManager.register(new TrinketsRenderingHandler());
      }
   }
}
