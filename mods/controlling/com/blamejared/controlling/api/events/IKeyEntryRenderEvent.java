package com.blamejared.controlling.api.events;

import com.blamejared.controlling.api.entries.IKeyEntry;
import net.minecraft.client.gui.GuiGraphics;

public interface IKeyEntryRenderEvent {
   IKeyEntry getEntry();

   GuiGraphics getGuiGraphics();

   int getSlotIndex();

   int getY();

   int getX();

   int getRowLeft();

   int getRowWidth();

   int getMouseX();

   int getMouseY();

   boolean isHovered();

   float getPartialTicks();
}
