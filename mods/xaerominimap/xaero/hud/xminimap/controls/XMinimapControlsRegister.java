package xaero.hud.xminimap.controls;

import java.util.function.Consumer;
import net.minecraft.client.KeyMapping;
import xaero.hud.controls.ControlsRegister;
import xaero.hud.xminimap.controls.key.XMinimapKeyMappings;

public class XMinimapControlsRegister extends ControlsRegister {
   @Override
   public void registerKeybindings(Consumer<KeyMapping> registry) {
      XMinimapKeyMappings.registerAll(this.keyMappingControllers, registry);
      super.registerKeybindings(registry);
   }
}
