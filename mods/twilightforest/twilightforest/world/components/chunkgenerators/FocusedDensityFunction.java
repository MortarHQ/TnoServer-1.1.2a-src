package twilightforest.world.components.chunkgenerators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import net.minecraft.world.level.levelgen.DensityFunction.SimpleFunction;

public record FocusedDensityFunction(float centerX, float bottomY, float centerZ, float radius, float nearValue, float farValue) implements SimpleFunction {
   public static final MapCodec<FocusedDensityFunction> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Codec.FLOAT.fieldOf("x_center").forGetter(FocusedDensityFunction::centerX),
            Codec.FLOAT.fieldOf("y_bottom").forGetter(FocusedDensityFunction::bottomY),
            Codec.FLOAT.fieldOf("z_center").forGetter(FocusedDensityFunction::centerZ),
            Codec.FLOAT.fieldOf("radius").forGetter(FocusedDensityFunction::radius),
            Codec.FLOAT.fieldOf("near_value").forGetter(FocusedDensityFunction::nearValue),
            Codec.FLOAT.fieldOf("far_value").forGetter(FocusedDensityFunction::farValue)
         )
         .apply(instance, FocusedDensityFunction::new)
   );
   public static final KeyDispatchDataCodec<FocusedDensityFunction> KEY_CODEC = KeyDispatchDataCodec.of(CODEC);

   public static FocusedDensityFunction fromPos(BlockPos blockPos, float radius, float nearValue, float farValue) {
      return new FocusedDensityFunction(blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, radius, nearValue, farValue);
   }

   public double compute(FunctionContext context) {
      float dX = this.centerX - context.blockX();
      float dY = this.bottomY - context.blockY();
      float dZ = this.centerZ - context.blockZ();
      float dist = Mth.sqrt(dX * dX + dY * dY + dZ * dZ);
      return Mth.clampedMap(dist, 0.0F, this.radius, this.nearValue, this.farValue);
   }

   public double minValue() {
      return 0.0;
   }

   public double maxValue() {
      return this.radius;
   }

   public KeyDispatchDataCodec<? extends DensityFunction> codec() {
      return KEY_CODEC;
   }
}
