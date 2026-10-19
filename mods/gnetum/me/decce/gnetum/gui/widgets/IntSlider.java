package me.decce.gnetum.gui.widgets;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;

public class IntSlider extends ExtendedSlider {
   private Supplier<String> text;
   private Supplier<String> specialText;
   private Consumer<Integer> setter;
   private Predicate<Integer> special;

   public IntSlider(
      int x, int y, int width, int height, Supplier<String> text, int minValue, int maxValue, int currentValue, boolean drawString, Consumer<Integer> setter
   ) {
      this(x, y, width, height, text, minValue, maxValue, currentValue, 1, drawString, setter);
   }

   public IntSlider(
      int x,
      int y,
      int width,
      int height,
      Supplier<String> text,
      int minValue,
      int maxValue,
      int currentValue,
      int step,
      boolean drawString,
      Consumer<Integer> setter
   ) {
      this(x, y, width, height, text, minValue, maxValue, currentValue, step, drawString, setter, null, null);
   }

   public IntSlider(
      int x,
      int y,
      int width,
      int height,
      Supplier<String> text,
      int minValue,
      int maxValue,
      int currentValue,
      int step,
      boolean drawString,
      Consumer<Integer> setter,
      Predicate<Integer> special,
      Supplier<String> specialText
   ) {
      super(x, y, width, height, Component.empty(), Component.empty(), minValue, maxValue, currentValue, step, 0, drawString);
      this.setter = setter;
      this.text = text;
      this.special = special;
      this.specialText = specialText;
      this.updateMessage();
   }

   protected void applyValue() {
      super.applyValue();
      this.setter.accept(this.getValueInt());
   }

   protected void updateMessage() {
      if (this.special != null && this.special.test(this.getValueInt())) {
         this.setMessage(Component.literal(String.format(this.specialText.get(), this.getValueString())));
      } else if (this.text != null) {
         this.setMessage(Component.literal(String.format(this.text.get(), this.getValueString())));
      }
   }
}
