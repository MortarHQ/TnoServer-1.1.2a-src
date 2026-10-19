package xaero.map.gui;

import net.minecraft.client.gui.components.Button.OnPress;
import xaero.lib.client.gui.widget.Tooltip;
import xaero.map.WorldMap;

public class GuiMapSwitchingButton extends GuiTexturedButton {
   public static final Tooltip TOOLTIP = new Tooltip("gui.xaero_box_map_switching");

   public GuiMapSwitchingButton(boolean menuActive, int x, int y, OnPress onPress) {
      super(x, y, 20, 20, menuActive ? 97 : 81, 0, 16, 16, WorldMap.guiTextures, onPress, () -> TOOLTIP);
   }
}
