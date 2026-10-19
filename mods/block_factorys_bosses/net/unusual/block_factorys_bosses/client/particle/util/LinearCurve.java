package net.unusual.block_factorys_bosses.client.particle.util;

import java.util.function.BiFunction;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record LinearCurve(float[] points, BiFunction<SubParticle, Float, Float> input, BiFunction<SubParticle, Float, Float> horizontal_range) {
   public float get(SubParticle proxy, float partial) {
      return this.interpolate(this.input.apply(proxy, partial)) * this.horizontal_range.apply(proxy, partial);
   }

   public float interpolate(float x) {
      if (this.points.length < 2) {
         throw new IllegalArgumentException("Array must contain at least two points.");
      }

      if (x < 0.0F) {
         x = 0.0F;
      } else if (x > 1.0F) {
         x = 1.0F;
      }

      int n = this.points.length - 1;
      float segmentLength = 1.0F / n;
      int i = (int)(x / segmentLength);
      if (i >= n) {
         i = n - 1;
      }

      float y0 = this.points[i];
      float y1 = this.points[i + 1];
      float x0 = i * segmentLength;
      float x1 = (i + 1) * segmentLength;
      return y0 + (y1 - y0) * ((x - x0) / (x1 - x0));
   }
}
