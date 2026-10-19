package me.decce.gnetum.gui.widgets;

import java.time.Duration;
import java.util.function.Supplier;
import me.decce.gnetum.util.AnyBoolean;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

public class ToggleButton extends Button {
   private Supplier<String> text;
   private Supplier<String> tooltip;
   private AnyBoolean value;

   public ToggleButton(int left, int top, int width, int height, AnyBoolean value, Supplier<String> text) {
      super(left, top, width, height, Component.empty(), btn -> {}, Button.DEFAULT_NARRATION);
      this.text = text;
      this.value = value;
      this.updateMessage();
   }

   public void setTooltip(Supplier<String> tooltip) {
      this.tooltip = tooltip;
      this.updateTooltip();
   }

   public void onPress() {
      super.onPress();
      this.next();
      this.updateMessage();
   }

   protected void next() {
      this.value.next();
   }

   private void updateMessage() {
      this.setMessage(Component.literal(String.format(this.text.get(), this.value.text())));
      this.updateTooltip();
   }

   private void updateTooltip() {
      if (this.tooltip == null) {
         super.setTooltip(null);
      } else {
         String str = this.tooltip.get();
         if (str != null && !str.isEmpty()) {
            super.setTooltip(Tooltip.create(Component.literal(str)));
            super.setTooltipDelay(Duration.ZERO);
         } else {
            super.setTooltip(null);
         }
      }
   }
}
