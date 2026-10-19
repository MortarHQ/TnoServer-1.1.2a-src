package twilightforest.world.components.chunkgenerators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import net.minecraft.world.level.levelgen.DensityFunction.SimpleFunction;

public abstract class AbsoluteDifferenceFunction implements SimpleFunction {
   protected final double max;
   protected final double centerX;
   protected final double centerZ;

   public static AbsoluteDifferenceFunction.Min min(double max, BlockPos pos) {
      return new AbsoluteDifferenceFunction.Min(max, pos.getX(), pos.getZ());
   }

   public static AbsoluteDifferenceFunction.Max max(double max, BlockPos pos) {
      return new AbsoluteDifferenceFunction.Max(max, pos.getX(), pos.getZ());
   }

   public AbsoluteDifferenceFunction(double max, double centerX, double centerZ) {
      this.max = max;
      this.centerX = centerX;
      this.centerZ = centerZ;
   }

   public double minValue() {
      return 0.0;
   }

   public double maxValue() {
      return this.max;
   }

   public static class Max extends AbsoluteDifferenceFunction {
      public static final MapCodec<AbsoluteDifferenceFunction.Max> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Codec.DOUBLE.fieldOf("max").forGetter(f -> f.max),
               Codec.DOUBLE.fieldOf("x_center").forGetter(f -> f.centerX),
               Codec.DOUBLE.fieldOf("z_center").forGetter(f -> f.centerZ)
            )
            .apply(instance, AbsoluteDifferenceFunction.Max::new)
      );
      public static final KeyDispatchDataCodec<AbsoluteDifferenceFunction.Max> KEY_CODEC = KeyDispatchDataCodec.of(CODEC);

      public Max(double max, double xCenter, double zCenter) {
         super(max, xCenter, zCenter);
      }

      public double compute(FunctionContext context) {
         return Math.min(Math.max(Math.abs(context.blockX() - this.centerX), Math.abs(context.blockZ() - this.centerZ)), this.max);
      }

      public KeyDispatchDataCodec<? extends DensityFunction> codec() {
         return KEY_CODEC;
      }
   }

   public static class Min extends AbsoluteDifferenceFunction {
      public static final MapCodec<AbsoluteDifferenceFunction.Min> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Codec.DOUBLE.fieldOf("max").forGetter(f -> f.max),
               Codec.DOUBLE.fieldOf("x_center").forGetter(f -> f.centerX),
               Codec.DOUBLE.fieldOf("z_center").forGetter(f -> f.centerZ)
            )
            .apply(instance, AbsoluteDifferenceFunction.Min::new)
      );
      public static final KeyDispatchDataCodec<AbsoluteDifferenceFunction.Min> KEY_CODEC = KeyDispatchDataCodec.of(CODEC);

      public Min(double max, double xCenter, double zCenter) {
         super(max, xCenter, zCenter);
      }

      public double compute(FunctionContext context) {
         return Math.min(Math.min(Math.abs(context.blockX() - this.centerX), Math.abs(context.blockZ() - this.centerZ)), this.max);
      }

      public KeyDispatchDataCodec<? extends DensityFunction> codec() {
         return KEY_CODEC;
      }
   }
}
