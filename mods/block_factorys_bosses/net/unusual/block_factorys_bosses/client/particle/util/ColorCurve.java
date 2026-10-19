package net.unusual.block_factorys_bosses.client.particle.util;

import java.util.function.BiFunction;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record ColorCurve(ColorCurve.Point[] points, BiFunction<SubParticle, Float, Float> input) {
   public ColorCurve.FloatRGBA get(SubParticle proxy, float partial) {
      return this.interpolate(this.input.apply(proxy, partial));
   }

   public ColorCurve.FloatRGBA interpolate(float x) {
      if (this.points.length < 2) {
         throw new IllegalArgumentException("Array must contain at least two points.");
      }

      if (x < 0.0F) {
         x = 0.0F;
      } else if (x > 1.0F) {
         x = 1.0F;
      }

      int i = 0;

      while (i < this.points.length - 1 && x > this.points[i + 1].x) {
         i++;
      }

      float x0 = this.points[i].x;
      float x1 = this.points[i + 1].x;
      float xFactor = (x - x0) / (x1 - x0);
      float r0 = this.points[i].color.r;
      float r1 = this.points[i + 1].color.r;
      float g0 = this.points[i].color.g;
      float g1 = this.points[i + 1].color.g;
      float b0 = this.points[i].color.b;
      float b1 = this.points[i + 1].color.b;
      float a0 = this.points[i].color.a;
      float a1 = this.points[i + 1].color.a;
      return new ColorCurve.FloatRGBA(r0 + (r1 - r0) * xFactor, g0 + (g1 - g0) * xFactor, b0 + (b1 - b0) * xFactor, a0 + (a1 - a0) * xFactor);
   }

   public record FloatRGBA(float r, float g, float b, float a) {
      public static ColorCurve.FloatRGBA fromARGB(int color) {
         return new ColorCurve.FloatRGBA((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, (color >> 24 & 0xFF) / 255.0F);
      }
   }

   public record Point(ColorCurve.FloatRGBA color, float x) {
   }
}
