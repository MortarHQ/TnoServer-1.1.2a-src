package team.creative.creativecore.common.gui.control;

import team.creative.creativecore.common.gui.GuiControl;

public abstract class GuiFocusControl extends GuiControl {
   private boolean focused = false;

   public GuiFocusControl(String name) {
      super(name);
   }

   public boolean isFocused() {
      return this.focused;
   }

   public void focus() {
      this.focused = true;
      this.focusChanged();
   }

   @Override
   public void looseFocus() {
      this.focused = false;
      this.focusChanged();
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      this.focus();
      return true;
   }

   protected void focusChanged() {
   }
}
