package com.finderfeed.fdlib.util.math;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.util.Mth;

public class ComplexEasingFunction implements Function<Float, Float> {
   private List<ComplexEasingFunction.EasingArea> areas = new ArrayList<>();
   private float length = 0.0F;

   public float apply(float value) {
      return this.apply(Float.valueOf(value));
   }

   public Float apply(Float value) {
      if (this.length != 0.0F && !this.areas.isEmpty()) {
         value = Mth.clamp(value, 0.0F, this.length);
         ComplexEasingFunction.EasingArea lastArea = this.areas.getFirst();
         float accumulatedLength = 0.0F;
         int idx = 1;

         while (true) {
            float len = accumulatedLength + lastArea.length;
            if (len > value || idx >= this.areas.size()) {
               len = value - accumulatedLength;
               float v;
               if (lastArea.easingFunction instanceof ComplexEasingFunction complexEasingFunction) {
                  v = lastArea.easingFunction.apply(len);
               } else {
                  float localPercent = len / lastArea.length();
                  v = lastArea.easingFunction.apply(localPercent);
               }

               return v;
            }

            accumulatedLength = len;
            lastArea = this.areas.get(idx++);
         }
      } else {
         return value;
      }
   }

   public void addEasingArea(float length, Function<Float, Float> easing) {
      this.length += length;
      this.areas.add(new ComplexEasingFunction.EasingArea(length, easing));
   }

   public float getLength() {
      return this.length;
   }

   public static ComplexEasingFunction.Builder builder() {
      return new ComplexEasingFunction.Builder();
   }

   public static class Builder {
      private ComplexEasingFunction easingFunction = new ComplexEasingFunction();

      public ComplexEasingFunction.Builder addArea(float areaLength, Function<Float, Float> easing) {
         this.easingFunction.addEasingArea(areaLength, easing);
         return this;
      }

      public ComplexEasingFunction build() {
         return this.easingFunction;
      }
   }

   private record EasingArea(float length, Function<Float, Float> easingFunction) {
   }
}
