package com.finderfeed.fdlib.systems.simple_screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;

public abstract class FDScrollableWidget extends FDWidget {
   private float scroll;

   public FDScrollableWidget(Screen screen, float x, float y, float width, float height) {
      super(screen, x, y, width, height);
   }

   public abstract float getMaxScroll();

   public abstract void onScroll(float var1);

   public abstract float scrollAmount();

   @Override
   public boolean onMouseScroll(float mx, float my, float scrollX, float scrollY) {
      float scrollO = this.scroll;
      this.scroll = Mth.clamp(this.scroll - scrollY * this.scrollAmount(), 0.0F, this.getMaxScroll());
      float delta = this.scroll - scrollO;
      if (delta != 0.0F) {
         this.onScroll(delta);
         return true;
      } else {
         return false;
      }
   }

   public float getCurrentScroll() {
      return this.scroll;
   }

   public void setCurrentScroll(float scroll) {
      this.scroll = scroll;
   }
}
