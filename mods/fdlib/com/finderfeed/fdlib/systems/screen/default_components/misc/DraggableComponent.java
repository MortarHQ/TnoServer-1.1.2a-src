package com.finderfeed.fdlib.systems.screen.default_components.misc;

import com.finderfeed.fdlib.systems.screen.FDScreen;
import com.finderfeed.fdlib.systems.screen.FDScreenComponent;
import net.minecraft.client.gui.GuiGraphics;

public class DraggableComponent extends FDScreenComponent {
   public FDScreenComponent component;

   public DraggableComponent(FDScreenComponent toDrag, FDScreen screen, String uniqueId, float x, float y, float width, float height) {
      super(screen, uniqueId, x, y, width, height);
      this.component = toDrag;
   }

   @Override
   public void renderComponent(GuiGraphics graphics, float x, float y, float mx, float my, float partialTicks) {
   }

   public boolean mouseDragged(double mx, double my, int button, double dx, double dy) {
      if (button == 0) {
         this.component.setX(this.component.getX() + (float)dx);
         this.component.setY(this.component.getY() + (float)dy);
         return true;
      } else {
         return false;
      }
   }

   public boolean mouseClicked(double mx, double my, int button) {
      return button == 0;
   }

   public boolean mouseReleased(double p_94753_, double p_94754_, int button) {
      return button == 0;
   }
}
