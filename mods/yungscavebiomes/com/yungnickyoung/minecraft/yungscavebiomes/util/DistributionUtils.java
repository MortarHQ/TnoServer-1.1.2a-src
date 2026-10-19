package com.yungnickyoung.minecraft.yungscavebiomes.util;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class DistributionUtils {
   public static <R> R ellipsoidCenterBiasedSpread(float radiusXZ, float radiusY, RandomSource random, DistributionUtils.CoordinateFunction<R> callback) {
      float sphereY = random.nextFloat() * 2.0F - 1.0F;
      float sphereTheta = random.nextFloat() * (float) (Math.PI * 2);
      float sphereXZScale = Mth.sqrt(1.0F - sphereY * sphereY);
      float radiusScale = 1.0F - Math.abs(random.nextFloat() - random.nextFloat());
      return callback.apply(
         radiusScale * sphereXZScale * radiusXZ * Mth.cos(sphereTheta),
         radiusScale * sphereY * radiusY,
         radiusScale * sphereXZScale * radiusXZ * Mth.sin(sphereTheta)
      );
   }

   public interface CoordinateFunction<R> {
      R apply(float var1, float var2, float var3);
   }
}
