package xaero.common.controls;

import net.minecraft.client.KeyMapping;
import xaero.common.HudMod;
import xaero.common.IXaeroMinimap;
import xaero.hud.HudSession;
import xaero.hud.controls.key.KeyMappingController;
import xaero.hud.controls.key.function.KeyMappingFunction;
import xaero.lib.client.controls.util.KeyMappingUtils;

@Deprecated
public class ControlsHandler {
   protected IXaeroMinimap modMain;
   protected HudSession hudSession;

   public ControlsHandler(IXaeroMinimap modMain, HudSession hudSession) {
      this.modMain = modMain;
      this.hudSession = hudSession;
   }

   @Deprecated
   public void setKeyState(KeyMapping kb, boolean pressed) {
      KeyMappingUtils.setKeyState(kb, pressed);
   }

   @Deprecated
   public boolean isDown(KeyMapping kb) {
      return KeyMappingUtils.isPhysicallyDown(kb);
   }

   @Deprecated
   public void keyDown(KeyMapping kb, boolean tickEnd, boolean isRepeat) {
      if (!tickEnd) {
         KeyMappingController controller = HudMod.INSTANCE.getKeyMappingControllers().getController(kb);

         for (KeyMappingFunction function : controller) {
            if (!function.isHeld()) {
               function.onPress();
            }
         }

         for (KeyMappingFunction function : controller) {
            if (!function.isHeld()) {
               function.onRelease();
            }
         }
      }
   }

   @Deprecated
   public void keyUp(KeyMapping kb, boolean tickEnd) {
   }
}
