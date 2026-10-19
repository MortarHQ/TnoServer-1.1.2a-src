package twilightforest.world.components.chunkgenerators;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import net.minecraft.world.level.levelgen.DensityFunction.SimpleFunction;
import net.minecraft.world.level.levelgen.DensityFunction.Visitor;

public record SqrtDensityFunction(DensityFunction input) implements SimpleFunction {
   public static final MapCodec<SqrtDensityFunction> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(DensityFunction.HOLDER_HELPER_CODEC.fieldOf("input").forGetter(SqrtDensityFunction::input))
         .apply(instance, SqrtDensityFunction::new)
   );
   public static final KeyDispatchDataCodec<SqrtDensityFunction> KEY_CODEC = KeyDispatchDataCodec.of(CODEC);

   public double compute(FunctionContext context) {
      return Math.sqrt(this.input.compute(context));
   }

   public double minValue() {
      return this.input.minValue();
   }

   public double maxValue() {
      return this.input.maxValue();
   }

   public KeyDispatchDataCodec<? extends DensityFunction> codec() {
      return KEY_CODEC;
   }

   public DensityFunction mapAll(Visitor visitor) {
      return visitor.apply(new SqrtDensityFunction(this.input.mapAll(visitor)));
   }
}
