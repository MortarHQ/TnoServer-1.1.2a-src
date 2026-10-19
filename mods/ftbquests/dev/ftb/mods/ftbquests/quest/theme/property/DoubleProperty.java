package dev.ftb.mods.ftbquests.quest.theme.property;

import net.minecraft.util.Mth;

public class DoubleProperty extends ThemeProperty<Double> {
   private final double min;
   private final double max;

   public DoubleProperty(String name, double min, double max) {
      super(name, 0.0);
      this.min = min;
      this.max = max;
   }

   public DoubleProperty(String n) {
      this(n, 0.0, 1.0);
   }

   public Double parse(String string) {
      try {
         double i = Double.parseDouble(string);
         return Mth.clamp(i, this.min, this.max);
      } catch (Exception var4) {
         return null;
      }
   }
}
