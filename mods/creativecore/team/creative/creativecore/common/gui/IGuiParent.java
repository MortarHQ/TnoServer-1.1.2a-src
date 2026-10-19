package team.creative.creativecore.common.gui;

import net.minecraft.world.entity.player.Player;
import team.creative.creativecore.common.gui.event.GuiEvent;
import team.creative.creativecore.common.gui.integration.IGuiIntegratedParent;
import team.creative.creativecore.common.util.math.geo.Rect;

public interface IGuiParent {
   boolean isContainer();

   boolean isClient();

   Player getPlayer();

   void closeTopLayer();

   void closeLayer(GuiLayer var1);

   void raiseEvent(GuiEvent var1);

   void reflow();

   boolean hasGui();

   boolean isParent(IGuiParent var1);

   Rect toScreenRect(GuiControl var1, Rect var2);

   Rect toLayerRect(GuiControl var1, Rect var2);

   IGuiIntegratedParent getIntegratedParent();
}
