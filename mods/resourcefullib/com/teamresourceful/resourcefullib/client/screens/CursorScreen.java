package com.teamresourceful.resourcefullib.client.screens;

import com.teamresourceful.resourcefullib.client.components.CursorWidget;
import java.util.List;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;

public interface CursorScreen {
   void setCursor(CursorScreen.Cursor var1);

   default void setCursor(List<? extends GuiEventListener> listeners, double mouseX, double mouseY) {
      for (GuiEventListener child : listeners) {
         boolean hovered = child.isMouseOver(mouseX, mouseY);
         if (child instanceof CursorWidget widget && hovered) {
            this.setCursor(widget.getCursor());
            break;
         }

         if (child instanceof AbstractWidget widget && hovered && widget.visible) {
            if (widget.active) {
               this.setCursor(!(widget instanceof EditBox) && !(widget instanceof MultiLineEditBox) ? CursorScreen.Cursor.POINTER : CursorScreen.Cursor.TEXT);
            } else {
               this.setCursor(CursorScreen.Cursor.DISABLED);
            }
            break;
         }
      }
   }

   enum Cursor {
      DEFAULT,
      POINTER,
      DISABLED,
      TEXT,
      CROSSHAIR,
      RESIZE_EW,
      RESIZE_NS,
      RESIZE_NWSE,
      RESIZE_NESW,
      RESIZE_ALL;
   }
}
