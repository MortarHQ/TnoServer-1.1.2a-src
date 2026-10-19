package team.creative.creativecore.common.config.premade;

import java.util.Random;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.api.CreativeConfig;
import team.creative.creativecore.common.config.api.ICreativeConfig;

public class IntMinMax implements ICreativeConfig {
   @CreativeConfig
   public int min;
   @CreativeConfig
   public int max;

   public IntMinMax(int min, int max) {
      this.min = min;
      this.max = max;
   }

   public int next(Random rand) {
      return this.min == this.max ? this.min : this.min + rand.nextInt(this.max - this.min);
   }

   @Override
   public void configured(Side side) {
      if (this.min > this.max) {
         int temp = this.min;
         this.min = this.max;
         this.max = temp;
      }
   }

   public int spanLength() {
      return this.max - this.min;
   }
}
