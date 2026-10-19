package dev.ftb.mods.ftbfiltersystem.integration.jei;

import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterConfigScreen;
import mezz.jei.api.gui.handlers.IGuiProperties;
import mezz.jei.api.gui.handlers.IScreenHandler;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

class FFSScreenHandler<T extends AbstractFilterConfigScreen<?>> implements IScreenHandler<T> {
   @Nullable
   public IGuiProperties apply(T guiScreen) {
      return guiScreen.width != 0 && guiScreen.height != 0 ? new FFSScreenHandler.GuiProps(guiScreen) : null;
   }

   private record GuiProps(AbstractFilterConfigScreen<?> guiScreen) implements IGuiProperties {
      public Class<? extends Screen> screenClass() {
         return (Class<? extends Screen>)this.guiScreen.getClass();
      }

      public int guiLeft() {
         return this.guiScreen.getGuiBounds().getX();
      }

      public int guiTop() {
         return this.guiScreen.getGuiBounds().getY();
      }

      public int guiXSize() {
         return this.guiScreen.getGuiBounds().getWidth();
      }

      public int guiYSize() {
         return this.guiScreen.getGuiBounds().getHeight();
      }

      public int screenWidth() {
         return this.guiScreen.width;
      }

      public int screenHeight() {
         return this.guiScreen.height;
      }
   }
}
