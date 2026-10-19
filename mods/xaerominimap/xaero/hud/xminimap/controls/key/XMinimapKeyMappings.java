package xaero.hud.xminimap.controls.key;

import java.util.function.Consumer;
import net.minecraft.client.KeyMapping;
import xaero.hud.controls.key.KeyMappingControllerManager;
import xaero.hud.xminimap.controls.key.function.XMinimapKeyMappingFunctions;

public class XMinimapKeyMappings {
   public static KeyMapping SETTINGS = new KeyMapping("gui.xaero_minimap_settings", 89, "Xaero's Minimap");
   public static KeyMapping SERVER_PROFILES = new KeyMapping("gui.xaero_minimap_server_profiles", -1, "Xaero's Minimap");

   public static void registerAll(KeyMappingControllerManager controllerManager, Consumer<KeyMapping> registry) {
      controllerManager.registerController(SETTINGS, true, registry);
      controllerManager.registerController(SERVER_PROFILES, true, registry);
      XMinimapKeyMappingFunctions.registerAll(controllerManager);
   }
}
