package dev.ftb.mods.ftblibrary.ui;

import java.util.function.Predicate;
import net.minecraft.util.Mth;

public class IntTextBox extends TextBox {
   private static final Predicate<String> IS_NUMBER = s -> s.matches("^-?[0-9]*$");
   private int min = Integer.MIN_VALUE;
   private int max = Integer.MAX_VALUE;

   public IntTextBox(Panel panel) {
      super(panel);
      this.setFilter(IS_NUMBER);
      this.setStrictValidity(true);
   }

   public int getIntValue() {
      String text = this.getText();
      if (!text.isEmpty() && !text.equals("-")) {
         try {
            return Integer.parseInt(text);
         } catch (NumberFormatException ignored) {
            return Mth.clamp(0, this.min, this.max);
         }
      } else {
         return Mth.clamp(0, this.min, this.max);
      }
   }

   public void setMin(int min) {
      this.min = min;
   }

   public void setMax(int max) {
      this.max = max;
   }

   public void setMinMax(int min, int max) {
      this.min = min;
      this.max = max;
   }

   @Override
   public boolean mouseScrolled(double scroll) {
      if (this.allowInput()) {
         this.setAmount(this.getIntValue() + (int)scroll);
         return true;
      } else {
         return false;
      }
   }

   public void setAmount(int amount) {
      this.setText(String.valueOf(amount));
   }

   @Override
   public void onTextChanged() {
      this.ensureValue();
   }

   public void ensureValue() {
      int amount = this.getIntValue();
      if (amount < this.min) {
         this.setAmount(this.min);
      } else if (amount > this.max) {
         this.setAmount(this.max);
      }
   }
}
