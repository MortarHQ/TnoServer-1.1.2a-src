package team.creative.creativecore.common.config.premade.curve;

import java.util.Random;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.api.CreativeConfig;
import team.creative.creativecore.common.config.api.ICreativeConfig;
import team.creative.creativecore.common.config.premade.DecimalMinMax;
import team.creative.creativecore.common.config.premade.IntMinMax;

public class DecimalCurve implements ICreativeConfig, Curve {
   @CreativeConfig
   public double min;
   @CreativeConfig
   public double minValue;
   @CreativeConfig
   public double max;
   @CreativeConfig
   public double maxValue;

   public DecimalCurve(double min, double minValue, double max, double maxValue) {
      this.min = min;
      this.minValue = minValue;
      this.max = max;
      this.maxValue = maxValue;
   }

   public DecimalCurve(Random rand, IntMinMax duration, DecimalMinMax intensity) {
      this.min = 0.0;
      this.minValue = intensity.next(rand);
      this.max = duration.next(rand);
      this.maxValue = 0.0;
   }

   public DecimalCurve(Random rand, IntMinMax duration, double intensity) {
      this.min = 0.0;
      this.minValue = intensity;
      this.max = duration.next(rand);
      this.maxValue = 0.0;
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
         double temp = this.min;
         this.min = this.max;
         this.max = temp;
         temp = this.minValue;
         this.minValue = this.maxValue;
         this.maxValue = temp;
      }
   }
}
