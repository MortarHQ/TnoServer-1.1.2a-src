package xaero.hud.minimap.info.widget;

import net.minecraft.client.gui.components.AbstractWidget;
import xaero.common.gui.GuiInfoDisplayEdit;

public interface InfoDisplayWidgetFactory<T> {
   AbstractWidget create(int var1, int var2, int var3, int var4, GuiInfoDisplayEdit.MoveableEntry<T> var5, Runnable var6, boolean var7);
}
