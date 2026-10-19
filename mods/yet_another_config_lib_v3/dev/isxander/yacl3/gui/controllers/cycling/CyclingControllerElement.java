package dev.isxander.yacl3.gui.controllers.cycling;

import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.controllers.ControllerWidget;
import net.minecraft.client.gui.screens.Screen;

public class CyclingControllerElement extends ControllerWidget<ICyclingController<?>> {
   public CyclingControllerElement(ICyclingController<?> control, YACLScreen screen, Dimension<Integer> dim) {
      super(control, screen, dim);
   }

   public void cycleValue(int increment) {
      int targetIdx = this.control.getPendingValue() + increment;
      if (targetIdx >= this.control.getCycleLength()) {
         targetIdx -= this.control.getCycleLength();
      } else if (targetIdx < 0) {
         targetIdx += this.control.getCycleLength();
      }

      this.control.setPendingValue(targetIdx);
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.isMouseOver(mouseX, mouseY) && (button == 0 || button == 1) && this.isAvailable()) {
         this.playDownSound();
         this.cycleValue(button != 1 && !Screen.hasShiftDown() && !Screen.hasControlDown() ? 1 : -1);
         return true;
      } else {
         return false;
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (!this.focused) {
         return false;
      }

      switch (keyCode) {
         case 32:
         case 257:
         case 335:
            this.cycleValue(!Screen.hasControlDown() && !Screen.hasShiftDown() ? 1 : -1);
            break;
         case 262:
            this.cycleValue(1);
            break;
         case 263:
            this.cycleValue(-1);
            break;
         default:
            return false;
      }

      return true;
   }

   @Override
   protected int getHoveredControlWidth() {
      return this.getUnhoveredControlWidth();
   }
}
