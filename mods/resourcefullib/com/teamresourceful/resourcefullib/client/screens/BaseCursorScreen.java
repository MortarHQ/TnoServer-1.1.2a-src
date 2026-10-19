package com.teamresourceful.resourcefullib.client.screens;

import com.teamresourceful.resourcefullib.client.utils.CursorUtils;
import com.teamresourceful.resourcefullib.client.utils.ScreenUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public abstract class BaseCursorScreen extends Screen implements CursorScreen {
   private CursorScreen.Cursor cursor = CursorScreen.Cursor.DEFAULT;

   protected BaseCursorScreen(Component component) {
      super(component);
   }

   public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float f) {
      boolean wihinBounds = ScreenUtils.inBounds(this.getRectangle(), mouseX, mouseY);
      if (!wihinBounds) {
         this.actuallyRender(graphics, mouseX, mouseY, f);
      } else {
         this.setCursor(CursorScreen.Cursor.DEFAULT);
         this.actuallyRender(graphics, mouseX, mouseY, f);
         this.setCursor(this.children(), mouseX, mouseY);
         switch (this.cursor) {
            case DEFAULT:
               CursorUtils.setDefault();
               break;
            case POINTER:
               CursorUtils.setPointing();
               break;
            case DISABLED:
               CursorUtils.setDisabled();
               break;
            case TEXT:
               CursorUtils.setText();
               break;
            case CROSSHAIR:
               CursorUtils.setCrosshair();
               break;
            case RESIZE_EW:
               CursorUtils.setResizeEastWest();
               break;
            case RESIZE_NS:
               CursorUtils.setResizeNorthSouth();
               break;
            case RESIZE_NESW:
               CursorUtils.setResizeNorthEastSouthWest();
               break;
            case RESIZE_NWSE:
               CursorUtils.setResizeNorthWestSouthEast();
               break;
            case RESIZE_ALL:
               CursorUtils.setResizeAll();
         }
      }
   }

   public void actuallyRender(@NotNull GuiGraphics graphics, int i, int j, float f) {
      super.render(graphics, i, j, f);
   }

   public void removed() {
      super.removed();
      CursorUtils.setDefault();
   }

   @Override
   public void setCursor(CursorScreen.Cursor cursor) {
      this.cursor = cursor;
   }
}
