package team.creative.creativecore.common.gui.integration;

import com.mojang.blaze3d.Blaze3D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.client.gui.screens.Screen;
import team.creative.creativecore.mixin.MouseHandlerAccessor;

public class ScreenEventListener implements GuiEventListener, NarratableEntry {
   public static final double DOUBLE_CLICK_TIME = 0.2;
   private final IGuiIntegratedParent gui;
   private final Screen screen;
   private int doubleClickButton = -1;
   private double time;
   private double x;
   private double y;
   private boolean released = false;
   private boolean focused;

   public ScreenEventListener(IGuiIntegratedParent gui, Screen screen) {
      this.gui = gui;
      this.screen = screen;
   }

   public int getOffsetX() {
      return (this.screen.width - this.gui.getTopLayer().getWidth()) / 2;
   }

   public int getOffsetY() {
      return (this.screen.height - this.gui.getTopLayer().getHeight()) / 2;
   }

   public void tick() {
      if (this.doubleClickButton != -1 && Blaze3D.getTime() - this.time > 0.2) {
         this.fireRemaingEvents();
      }
   }

   public double getEventTime() {
      return ((MouseHandlerAccessor)Minecraft.getInstance().mouseHandler).getLastHandleMovementTime();
   }

   protected void fireRemaingEvents() {
      if (this.doubleClickButton != -1) {
         this.gui.getTopLayer().mouseClicked(this.x, this.y, this.doubleClickButton);
         if (this.released) {
            this.gui.getTopLayer().mouseReleased(this.x, this.y, this.doubleClickButton);
         }

         this.doubleClickButton = -1;
         this.released = false;
      }
   }

   public void mouseMoved(double x, double y) {
      this.gui.getTopLayer().mouseMoved(x - this.getOffsetX(), y - this.getOffsetY());
   }

   public boolean mouseClicked(double x, double y, int button) {
      if (this.gui.getTopLayer().testForDoubleClick(x - this.getOffsetX(), y - this.getOffsetY(), button)) {
         if (this.doubleClickButton == button) {
            this.released = false;
            this.doubleClickButton = -1;
            return this.gui.getTopLayer().mouseDoubleClicked(x - this.getOffsetX(), y - this.getOffsetY(), button);
         } else {
            this.fireRemaingEvents();
            this.doubleClickButton = button;
            this.time = this.getEventTime();
            this.x = x - this.getOffsetX();
            this.y = y - this.getOffsetY();
            return true;
         }
      } else {
         this.fireRemaingEvents();
         return this.gui.getTopLayer().mouseClicked(x - this.getOffsetX(), y - this.getOffsetY(), button);
      }
   }

   public boolean mouseReleased(double x, double y, int button) {
      if (this.doubleClickButton == button) {
         this.released = true;
         return true;
      } else {
         this.fireRemaingEvents();
         this.gui.getTopLayer().mouseReleased(x - this.getOffsetX(), y - this.getOffsetY(), button);
         return true;
      }
   }

   public boolean mouseDragged(double x, double y, int button, double dragX, double dragY) {
      if (this.doubleClickButton == -1) {
         this.gui.getTopLayer().mouseDragged(x - this.getOffsetX(), y - this.getOffsetY(), button, dragX, dragY, Blaze3D.getTime() - this.time);
      }

      return true;
   }

   public boolean mouseScrolled(double x, double y, double deltaX, double deltaY) {
      return this.gui.getTopLayer().mouseScrolled(x - this.getOffsetX(), y - this.getOffsetY(), deltaY);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      return this.gui.getTopLayer().keyPressed(keyCode, scanCode, modifiers);
   }

   public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      return this.gui.getTopLayer().keyReleased(keyCode, scanCode, modifiers);
   }

   public boolean charTyped(char codePoint, int modifiers) {
      return this.gui.getTopLayer().charTyped(codePoint, modifiers);
   }

   public void setFocused(boolean focused) {
      this.focused = focused;
   }

   public boolean isFocused() {
      return this.focused;
   }

   public boolean isMouseOver(double mouseX, double mouseY) {
      return true;
   }

   public void updateNarration(NarrationElementOutput p_169152_) {
   }

   public NarrationPriority narrationPriority() {
      return NarrationPriority.NONE;
   }
}
