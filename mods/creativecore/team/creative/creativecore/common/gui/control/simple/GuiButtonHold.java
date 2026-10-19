package team.creative.creativecore.common.gui.control.simple;

import java.util.function.Consumer;

public class GuiButtonHold extends GuiButton {
   public static final int INITIAL_WAIT = 250;
   public static final int CONTINOUS_WAIT = 100;
   public int clicked = -1;
   public boolean inital = false;
   public long wait = 0L;

   public GuiButtonHold(String name, Consumer<Integer> pressed) {
      super(name, pressed);
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      this.wait = System.currentTimeMillis();
      this.clicked = button;
      this.inital = true;
      return super.mouseClicked(x, y, button);
   }

   @Override
   public void mouseReleased(double x, double y, int button) {
      this.clicked = -1;
      super.mouseReleased(x, y, button);
   }

   @Override
   public void tick() {
      if (this.clicked != -1) {
         if (this.inital) {
            if (System.currentTimeMillis() - this.wait >= 250L) {
               this.pressed.accept(this.clicked);
               this.wait = System.currentTimeMillis();
               this.inital = false;
            }
         } else if (System.currentTimeMillis() - this.wait >= 100L) {
            this.pressed.accept(this.clicked);
            this.wait = System.currentTimeMillis();
         }
      }
   }

   @Override
   public void mouseMoved(double x, double y) {
      if (this.clicked != -1 && !this.rect.inside(x, y)) {
         this.wait = System.currentTimeMillis();
      }

      super.mouseMoved(x, y);
   }
}
