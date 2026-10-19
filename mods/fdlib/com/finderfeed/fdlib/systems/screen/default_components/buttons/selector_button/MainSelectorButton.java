package com.finderfeed.fdlib.systems.screen.default_components.buttons.selector_button;

import com.finderfeed.fdlib.systems.screen.FDScreen;
import com.finderfeed.fdlib.systems.screen.ValueComponent;
import com.finderfeed.fdlib.systems.screen.default_components.buttons.OnFDButtonPress;
import com.finderfeed.fdlib.systems.screen.default_components.misc.FDVerticalComponentContainer;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public abstract class MainSelectorButton<T> extends ValueComponent<T> {
   protected Field field;
   protected T value;
   protected FDVerticalComponentContainer buttonContainer;
   protected float r = 0.25F;
   protected float g = 0.25F;
   protected float b = 0.25F;
   protected float a = 1.0F;

   public MainSelectorButton(Field field, FDScreen screen, String uniqueId) {
      super(screen, uniqueId, 0.0F, 0.0F, 0.0F, 12.0F);
      this.field = field;
   }

   @Override
   public void renderComponent(GuiGraphics graphics, float x, float y, float mx, float my, float partialTicks) {
      if (this.isHovered()) {
         FDRenderUtil.fill(graphics.pose(), x, y, this.getWidth(), this.getHeight(), this.r + 0.05F, this.g + 0.05F, this.b + 0.05F, this.a);
      } else {
         FDRenderUtil.fill(graphics.pose(), x, y, this.getWidth(), this.getHeight(), this.r, this.g, this.b, this.a);
      }

      String str = this.valueToString(this.value);
      Font font = Minecraft.getInstance().font;
      float w = font.width(str);
      float mod = Mth.clamp((this.getWidth() - 2.0F) / w, 0.0F, 1.0F);
      FDRenderUtil.renderCenteredText(graphics, x + this.getWidth() / 2.0F, y + 2.0F, mod, false, str, 16777215);
   }

   @Override
   public void setValue(T value) {
      this.value = value;
   }

   public boolean mouseClicked(double mx, double my, int button) {
      if (this.buttonContainer == null) {
         this.initChoiceButtons();
      } else {
         this.destroyChoiceButtons();
      }

      return true;
   }

   protected void initChoiceButtons() {
      if (this.buttonContainer == null) {
         List<T> objects = this.values();
         float btnHeight = this.getHeight();
         float sizeOfChoiceWindow = Math.min(objects.size(), 3) * btnHeight;
         FDVerticalComponentContainer container = new FDVerticalComponentContainer(
            this.getScreen(), this.getUniqueId() + "_container", 0.0F, this.getHeight(), sizeOfChoiceWindow
         );
         int i = 0;

         for (T s : objects) {
            OnFDButtonPress press = (screen, buttonx, mx, my) -> {
               container.setRemoved(true);
               this.setValue(((SelectorButton)buttonx).value);
               return true;
            };
            String name = this.valueToString(s);
            SelectorButton<T> button = new SelectorButton<>(
               s, name, this.getScreen(), this.getUniqueId() + "_button_" + i, 0.0F, 0.0F, this.getWidth(), btnHeight, press
            );
            button.r = (float)Mth.clamp(this.r + 0.1 * ((i + 1) % 2), 0.0, 1.0);
            button.g = (float)Mth.clamp(this.g + 0.1 * ((i + 1) % 2), 0.0, 1.0);
            button.b = (float)Mth.clamp(this.b + 0.1 * ((i + 1) % 2), 0.0, 1.0);
            container.getChildren().setAsChild(button);
            i++;
         }

         this.getChildren().setAsChild(container);
         this.buttonContainer = container;
      }
   }

   protected void destroyChoiceButtons() {
      if (this.buttonContainer != null) {
         this.buttonContainer.setRemoved(true);
         this.buttonContainer = null;
      }
   }

   @Override
   public boolean isMouseOver(float mx, float my) {
      return this.buttonContainer != null
         ? FDRenderUtil.isMouseInBounds(mx, my, 0.0F, 0.0F, this.getWidth(), this.getHeight() + this.buttonContainer.getHeight())
         : super.isMouseOver(mx, my);
   }

   @Override
   public void onFocused(boolean state) {
      super.onFocused(state);
      if (!state) {
         this.destroyChoiceButtons();
      }
   }

   @Override
   public boolean isWidthFixed() {
      return false;
   }

   public abstract String valueToString(T var1);

   public abstract List<T> values();
}
