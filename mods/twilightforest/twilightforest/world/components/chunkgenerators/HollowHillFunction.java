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

public record HollowHillFunction(float centerX, float bottomY, float centerZ, float radius, float heightScale) implements SimpleFunction {
   public static final MapCodec<HollowHillFunction> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Codec.FLOAT.fieldOf("x_center").forGetter(HollowHillFunction::centerX),
            Codec.FLOAT.fieldOf("y_bottom").forGetter(HollowHillFunction::bottomY),
            Codec.FLOAT.fieldOf("z_center").forGetter(HollowHillFunction::centerZ),
            Codec.FLOAT.fieldOf("radius").forGetter(HollowHillFunction::radius),
            Codec.FLOAT.fieldOf("height_scale").forGetter(HollowHillFunction::heightScale)
         )
         .apply(instance, HollowHillFunction::new)
   );
   public static final KeyDispatchDataCodec<HollowHillFunction> KEY_CODEC = KeyDispatchDataCodec.of(CODEC);

   public static HollowHillFunction fromPos(BlockPos blockPos, float radius, float heightScale) {
      return new HollowHillFunction(blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, radius, heightScale);
   }

   public double compute(FunctionContext context) {
      float dX = context.blockX() - this.centerX;
      float dY = context.blockY() - this.bottomY;
      float dZ = context.blockZ() - this.centerZ;
      return this.compute(dX, dY, dZ);
   }

   public double compute(float dX, float dY, float dZ) {
      float dist = Mth.sqrt(dX * dX + dZ * dZ);
      float height = Mth.cos(dist / this.radius * (float) Math.PI) * this.radius * 0.33333334F;
      float normalizedDist = Mth.clamp(dist / Mth.abs(this.radius), 0.0F, 1.0F);
      return normalizedDist >= 1.0F ? 0.0 : Mth.clamp(height * this.heightScale - dY, -1.0F, 1.0F);
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
