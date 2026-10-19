package com.teamresourceful.resourcefullib.client.screens;

import com.teamresourceful.resourcefullib.client.utils.CursorUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractContainerCursorScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> implements CursorScreen {
   private CursorScreen.Cursor cursor = CursorScreen.Cursor.DEFAULT;

   public AbstractContainerCursorScreen(T abstractContainerMenu, Inventory inventory, Component component) {
      super(abstractContainerMenu, inventory, component);
   }

   public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float f) {
      this.setCursor(CursorScreen.Cursor.DEFAULT);
      super.render(graphics, mouseX, mouseY, f);
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

   public void removed() {
      super.removed();
      CursorUtils.setDefault();
   }

   @Override
   public void setCursor(CursorScreen.Cursor cursor) {
      this.cursor = cursor;
   }
}
