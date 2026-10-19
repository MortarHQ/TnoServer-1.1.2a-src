package team.creative.creativecore.common.config.premade.curve;

import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.api.CreativeConfig;
import team.creative.creativecore.common.config.api.ICreativeConfig;

public class IntCurve implements ICreativeConfig, Curve {
   @CreativeConfig
   public int min;
   @CreativeConfig
   public int minValue;
   @CreativeConfig
   public int max;
   @CreativeConfig
   public int maxValue;

   public IntCurve(int min, int minValue, int max, int maxValue) {
      this.min = min;
      this.minValue = minValue;
      this.max = max;
      this.maxValue = maxValue;
   }

   @Override
   public double valueAt(double x) {
      if (x <= this.min) {
         return this.minValue;
      }

      if (x >= this.max) {
         return this.maxValue;
      }

      double percent = (x - this.min) / (this.max - this.min);
      return (this.maxValue - this.minValue) * percent + this.minValue;
   }

   @Override
   public void configured(Side side) {
      if (this.min > this.max) {
         int temp = this.min;
         this.min = this.max;
         this.max = temp;
         temp = this.minValue;
         this.minValue = this.maxValue;
         this.maxValue = temp;
      }
   }
}
