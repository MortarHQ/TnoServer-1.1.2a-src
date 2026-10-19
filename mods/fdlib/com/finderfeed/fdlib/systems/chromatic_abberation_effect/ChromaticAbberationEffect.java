package com.finderfeed.fdlib.systems.chromatic_abberation_effect;

import com.finderfeed.fdlib.util.math.ComplexEasingFunction;
import com.finderfeed.fdlib.util.rendering.FDEasings;
import net.minecraft.util.Mth;

public class ChromaticAbberationEffect {
   private ComplexEasingFunction easingFunction;
   public int tick;
   public int inTime;
   public int stayTime;
   public int outTime;
   public float maxStrength;

   public ChromaticAbberationEffect(int inTime, int stayTime, int outTime, float maxStrength) {
      this.inTime = inTime;
      this.stayTime = stayTime;
      this.outTime = outTime;
      this.maxStrength = maxStrength;
      this.easingFunction = ComplexEasingFunction.builder()
         .addArea(inTime, FDEasings::easeOut)
         .addArea(stayTime, FDEasings::one)
         .addArea(outTime, FDEasings::reversedEaseOut)
         .build();
   }

   public boolean tick() {
      this.tick++;
      return this.tick > this.fullDuration();
   }

   public float getStrength(float pticks) {
      float time = Mth.clamp(this.tick + pticks, 0.0F, this.fullDuration());
      return this.maxStrength * this.easingFunction.apply(time);
   }

   public int fullDuration() {
      return this.inTime + this.stayTime + this.outTime;
   }
}
