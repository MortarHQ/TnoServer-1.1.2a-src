package team.creative.creativecore.common.config.premade;

import java.util.Random;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.api.CreativeConfig;
import team.creative.creativecore.common.config.api.ICreativeConfig;

public class DecimalMinMax implements ICreativeConfig {
   @CreativeConfig
   public double min;
   @CreativeConfig
   public double max;

   public DecimalMinMax(double min, double max) {
      this.min = min;
      this.max = max;
   }

   public double next(Random rand) {
      return this.min == this.max ? this.min : this.min + rand.nextDouble() * (this.max - this.min);
   }

   @Override
   public void configured(Side side) {
      if (this.min > this.max) {
         double temp = this.min;
         this.min = this.max;
         this.max = temp;
      }
   }

   public double spanLength() {
      return this.max - this.min;
   }
}
