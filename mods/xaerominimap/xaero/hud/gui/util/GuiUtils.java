package xaero.hud.gui.util;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.client.gui.ScreenBase;

public class GuiUtils {
   public static float getMinimapScale(ClientConfigManager configManager) {
      int uiScaleConfigValue = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.UI_SCALE);
      List<Integer> validValues = MinimapProfiledConfigOptions.UI_SCALE.getValidValues();
      int min = validValues.get(0);
      int max = validValues.get(validValues.size() - 1);
      return getUIScale(uiScaleConfigValue, min, max);
   }

   public static float getUIScale(int optionValue, int min, int max) {
      if (optionValue <= min) {
         return getAutoUIScale();
      } else {
         return optionValue == max ? (float)Minecraft.getInstance().getWindow().getGuiScale() : optionValue;
      }
   }

   public static int getAutoUIScale() {
      int height = Minecraft.getInstance().getWindow().getHeight();
      int width = Minecraft.getInstance().getWindow().getWidth();
      int size = height <= width ? height : width;
      return size >= 1500 ? size / 500 : 2;
   }

   public static void refreshScreenBase() {
      Screen currentScreen = Minecraft.getInstance().screen;
      ScreenBase screenBase = currentScreen instanceof ScreenBase ? (ScreenBase)currentScreen : null;
      if (screenBase != null) {
         screenBase.refresh();
      }
   }
}
