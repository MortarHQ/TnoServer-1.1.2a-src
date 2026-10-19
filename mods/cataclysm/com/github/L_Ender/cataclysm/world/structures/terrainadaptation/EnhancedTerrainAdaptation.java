package com.github.L_Ender.cataclysm.world.structures.terrainadaptation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.util.Mth;

public abstract class EnhancedTerrainAdaptation {
   public static final EnhancedTerrainAdaptation NONE = new NoneAdaptation();
   private final TerrainAction topAction;
   private final TerrainAction bottomAction;
   private final int kernelSize;
   private final int kernelDistance;
   private final double bottomOffset;
   private final EnhancedTerrainAdaptation.Padding padding;
   private final float[] kernel;

   public abstract EnhancedTerrainAdaptationType<?> type();

   EnhancedTerrainAdaptation(
      int kernelSize, int kernelDistance, TerrainAction topAction, TerrainAction bottomAction, double bottomOffset, EnhancedTerrainAdaptation.Padding padding
   ) {
      this.kernelSize = kernelSize;
      this.kernelDistance = kernelDistance;
      this.topAction = topAction;
      this.bottomAction = bottomAction;
      this.bottomOffset = bottomOffset;
      this.padding = padding;
      int kernelRadius = this.getKernelRadius();
      this.kernel = (float[])Util.make(new float[kernelSize * kernelSize * kernelSize], kernel -> {
         for (int x = 0; x < kernelSize; x++) {
            for (int y = 0; y < kernelSize; y++) {
               for (int z = 0; z < kernelSize; z++) {
                  int i = this.index(x, y, z);
                  double kernelX = x - kernelRadius;
                  double kernelY = y - kernelRadius + 0.5;
                  double kernelZ = z - kernelRadius;
                  kernel[i] = this.computeKernelValue(kernelX, kernelY, kernelZ);
               }
            }
         }
      });
   }

   private float computeKernelValue(double xDistance, double yDistance, double zDistance) {
      double squaredDistance = Mth.lengthSquared(xDistance, yDistance, zDistance);
      return (float)Math.pow(Math.E, -squaredDistance / this.kernelDistance);
   }

   public TerrainAction topAction() {
      return this.topAction;
   }

   public TerrainAction bottomAction() {
      return this.bottomAction;
   }

   public double getBottomOffset() {
      return this.bottomOffset;
   }

   public EnhancedTerrainAdaptation.Padding getPadding() {
      return this.padding;
   }

   public int getKernelSize() {
      return this.kernelSize;
   }

   public int getKernelRadius() {
      return this.kernelSize / 2;
   }

   public int getKernelDistance() {
      return this.kernelDistance;
   }

   public float[] getKernel() {
      return this.kernel;
   }

   public double computeDensityFactor(int xDistance, int yDistance, int zDistance, int yDistanceToPieceBottom) {
      int kernelRadius = this.getKernelRadius();
      int kernelX = xDistance + kernelRadius;
      int kernelY = yDistance + kernelRadius;
      int kernelZ = zDistance + kernelRadius;
      if (this.isInKernelRange(kernelX) && this.isInKernelRange(kernelY) && this.isInKernelRange(kernelZ)) {
         int i = this.index(kernelX, kernelY, kernelZ);
         float kernelValue = this.getKernel()[i];
         double actualYDistanceToPieceBottom = yDistanceToPieceBottom + 0.5;
         double squaredDistance = Mth.lengthSquared(xDistance, actualYDistanceToPieceBottom, zDistance);
         double multiplier = Math.abs(actualYDistanceToPieceBottom * Mth.invSqrt(squaredDistance / 2.0) / 2.0);
         boolean isAboveBeardBase = actualYDistanceToPieceBottom > 0.0;
         int densityModifier = isAboveBeardBase ? this.topAction.getDensityModifier() : this.bottomAction.getDensityModifier();
         return multiplier * kernelValue * densityModifier;
      } else {
         return 0.0;
      }
   }

   private boolean isInKernelRange(int i) {
      return i >= 0 && i < this.kernelSize;
   }

   private int index(int x, int y, int z) {
      return z * this.kernelSize * this.kernelSize + x * this.kernelSize + y;
   }

   public record Padding(int x, int top, int bottom, int z) {
      public static final EnhancedTerrainAdaptation.Padding ZERO = new EnhancedTerrainAdaptation.Padding(0, 0, 0, 0);
      public static final Codec<EnhancedTerrainAdaptation.Padding> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.INT.optionalFieldOf("x", 0).forGetter(padding -> padding.x),
               Codec.INT.optionalFieldOf("top", 0).forGetter(padding -> padding.top),
               Codec.INT.optionalFieldOf("bottom", 0).forGetter(padding -> padding.bottom),
               Codec.INT.optionalFieldOf("z", 0).forGetter(padding -> padding.z)
            )
            .apply(instance, EnhancedTerrainAdaptation.Padding::new)
      );
   }
}
