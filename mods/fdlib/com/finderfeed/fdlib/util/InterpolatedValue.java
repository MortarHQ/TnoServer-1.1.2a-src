package com.finderfeed.fdlib.util;

import com.finderfeed.fdlib.util.math.FDMathUtil;
import java.util.function.Function;
import net.minecraft.util.Mth;

public class InterpolatedValue {
   private Function<Float, Float> easing;
   private int tickO = 0;
   private int tick = 0;
   private float start;
   private float end;
   private int time;

   public InterpolatedValue(float start, float end, int time, Function<Float, Float> easingFunction) {
      this.easing = easingFunction;
      this.time = time;
      this.start = start;
      this.end = end;
   }

   public void tick() {
      this.tickO = this.tick;
      this.tick = Mth.clamp(this.tick + 1, 0, this.time);
   }

   public float getValue(float pticks) {
      float p = this.easing.apply(FDMathUtil.lerp(this.tickO, this.tick, pticks) / this.time);
      return FDMathUtil.lerp(this.start, this.end, p);
   }

   public void reset() {
      this.tick = 0;
   }

   public int getTick() {
      return this.tick;
   }

   public Function<Float, Float> getEasing() {
      return this.easing;
   }

   public int getTime() {
      return this.time;
   }

   public boolean hasFinished() {
      return this.tick >= this.time;
   }

   public float getStart() {
      return this.start;
   }

   public float getEnd() {
      return this.end;
   }
}
