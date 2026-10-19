package com.finderfeed.fdlib.systems.screen.default_components.buttons;

import com.finderfeed.fdlib.systems.screen.FDEditorComponent;
import com.finderfeed.fdlib.systems.screen.FDScreen;
import com.finderfeed.fdlib.systems.screen.ValueComponent;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import java.lang.annotation.Annotation;
import net.minecraft.client.gui.GuiGraphics;

public class BooleanVComponent extends ValueComponent<Boolean> {
   public boolean state;

   public BooleanVComponent(FDScreen screen, String uniqueId) {
      super(screen, uniqueId, 0.0F, 0.0F, 12.0F, 12.0F);
   }

   @Override
   public void renderComponent(GuiGraphics graphics, float x, float y, float mx, float my, float partialTicks) {
      FDRenderUtil.fill(graphics.pose(), x, y, this.getWidth(), this.getHeight(), 0.25F, 0.25F, 0.25F, 1.0F);
      if (this.state) {
         FDRenderUtil.fill(graphics.pose(), x + 2.0F, y + 2.0F, this.getWidth() - 4.0F, this.getHeight() - 4.0F, 0.75F, 0.75F, 0.75F, 1.0F);
      }
   }

   public boolean mouseClicked(double p_94737_, double p_94738_, int btn) {
      if (btn == 0) {
         this.state = !this.state;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean isWidthFixed() {
      return true;
   }

   public void setValue(Boolean value) {
      this.state = value;
   }

   public Boolean getValue() {
      return this.state;
   }

   @Override
   public void applyOptions(FDEditorComponent owner, Annotation annotation) {
   }
}
