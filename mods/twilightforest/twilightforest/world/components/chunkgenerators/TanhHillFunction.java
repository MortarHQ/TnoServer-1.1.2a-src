package twilightforest.world.components.chunkgenerators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import net.minecraft.world.level.levelgen.DensityFunction.SimpleFunction;

public record TanhHillFunction(
   float centerX,
   float bottomY,
   float centerZ,
   float radius,
   float heightScale,
   float cosAngleBiasDirection,
   float sinAngleBiasDirection,
   boolean isXOriented,
   boolean isOnRightSide
) implements SimpleFunction {
   public static final MapCodec<TanhHillFunction> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Codec.FLOAT.fieldOf("x_center").forGetter(TanhHillFunction::centerX),
            Codec.FLOAT.fieldOf("y_bottom").forGetter(TanhHillFunction::bottomY),
            Codec.FLOAT.fieldOf("z_center").forGetter(TanhHillFunction::centerZ),
            Codec.FLOAT.fieldOf("radius").forGetter(TanhHillFunction::radius),
            Codec.FLOAT.fieldOf("height_scale").forGetter(TanhHillFunction::heightScale),
            Codec.FLOAT.fieldOf("cos_angle_bias_direction").forGetter(TanhHillFunction::cosAngleBiasDirection),
            Codec.FLOAT.fieldOf("sin_angle_bias_direction").forGetter(TanhHillFunction::sinAngleBiasDirection),
            Codec.BOOL.fieldOf("is_x_oriented").forGetter(TanhHillFunction::isXOriented),
            Codec.BOOL.fieldOf("is_on_right_side").forGetter(TanhHillFunction::isOnRightSide)
         )
         .apply(instance, TanhHillFunction::new)
   );
   public static final KeyDispatchDataCodec<TanhHillFunction> KEY_CODEC = KeyDispatchDataCodec.of(CODEC);
   private static final float PERPENDICULAR_BIAS = 1.3F;

   public TanhHillFunction(
      float centerX, float bottomY, float centerZ, float radius, float heightScale, float angleBiasDirection, boolean isXOriented, boolean isOnRightSide
   ) {
      this(centerX, bottomY, centerZ, radius, heightScale, Mth.cos(angleBiasDirection), Mth.sin(angleBiasDirection), isXOriented, isOnRightSide);
   }

   public double compute(FunctionContext context) {
      float dX = context.blockX() - this.centerX;
      float dY = context.blockY() - this.bottomY;
      float dZ = context.blockZ() - this.centerZ;
      if (this.isXOriented) {
         dZ *= 1.3F;
      } else {
         dX *= 1.3F;
      }

      return this.compute(dX, dY, dZ);
   }

   public double compute(float dX, float dY, float dZ) {
      if (!(dY > 10.0F) && !(dY < -10.0F)) {
         float perpendicularDist = this.isXOriented ? dZ : dX;
         if (!this.isOnRightSide) {
            perpendicularDist = -perpendicularDist;
         }

         if (perpendicularDist < 0.0F) {
            return -1.0;
         }

         double distSquared = dX * dX + dZ * dZ;
         if (distSquared > 9.0F * this.radius * this.radius) {
            return -1.0;
         }

         double dist = Math.sqrt(distSquared);
         double cosPhi = dX / dist;
         double sinPhi = dZ / dist;
         double cos2Phi = cosPhi * cosPhi - sinPhi * sinPhi;
         double sin2Phi = 2.0 * cosPhi * sinPhi;
         dist /= 1.0 + (sin2Phi * this.cosAngleBiasDirection - cos2Phi * this.sinAngleBiasDirection) / 3.0;
         double height = this.getHeight(dist);
         return this.heightScale * (height + 1.0) > dY ? 1.0 : -1.0;
      } else {
         return -1.0;
      }
   }

   private double getHeight(double dist) {
      double tanhExp = Math.exp(2.0 * (2.0 - 2.0 * dist / this.radius));
      return (tanhExp - 1.0) / (tanhExp + 1.0) - dist * (1.0 - Math.exp(-dist * this.radius * 0.01));
   }

   public double minValue() {
      return -1.0;
   }

   public double maxValue() {
      return 1.0;
   }

   public KeyDispatchDataCodec<? extends DensityFunction> codec() {
      return KEY_CODEC;
   }
}
