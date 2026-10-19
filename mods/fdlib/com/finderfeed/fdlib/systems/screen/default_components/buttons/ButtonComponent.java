package com.finderfeed.fdlib.systems.screen.default_components.buttons;

import com.finderfeed.fdlib.systems.screen.FDScreen;
import com.finderfeed.fdlib.systems.screen.FDScreenComponent;

public abstract class ButtonComponent extends FDScreenComponent {
   public OnFDButtonPress onPress;

   public ButtonComponent(FDScreen screen, String uniqueId, float x, float y, float width, float height, OnFDButtonPress onPress) {
      super(screen, uniqueId, x, y, width, height);
      this.onPress = onPress;
   }

   public boolean mouseClicked(double mx, double my, int button) {
      return button != 0 ? false : this.onPress != null && this.onPress.onButtonPress(this.getScreen(), this, (float)mx, (float)my);
   }
}
