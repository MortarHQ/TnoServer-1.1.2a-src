package com.teamresourceful.resourcefullib.common.codecs.bounds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.exceptions.UtilityClassException;
import java.util.function.Function;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;

public final class UniformedNumberCodecs {
   public static final Codec<UniformFloat> FLOAT_CODEC = getFloatCodec();
   public static final Codec<UniformInt> INT_CODEC = getIntCodec();

   private UniformedNumberCodecs() throws UtilityClassException {
      throw new UtilityClassException();
   }

   private static Codec<UniformFloat> getFloatCodec() {
      Codec<UniformFloat> codec = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.FLOAT.fieldOf("min").forGetter(UniformFloat::getMinValue), Codec.FLOAT.fieldOf("max").forGetter(UniformFloat::getMaxValue)
            )
            .apply(instance, UniformFloat::of)
      );
      return codec.comapFlatMap(
         uniformFloat -> uniformFloat.getMaxValue() < uniformFloat.getMinValue()
            ? DataResult.error(() -> "Max must be at least min, min: " + uniformFloat.getMinValue() + ", max: " + uniformFloat.getMaxValue())
            : DataResult.success(uniformFloat),
         Function.identity()
      );
   }

   public static Codec<UniformInt> getIntCodec() {
      Codec<UniformInt> codec = RecordCodecBuilder.create(
         instance -> instance.group(Codec.INT.fieldOf("min").forGetter(UniformInt::getMinValue), Codec.INT.fieldOf("max").forGetter(UniformInt::getMaxValue))
            .apply(instance, UniformInt::of)
      );
      return codec.comapFlatMap(
         uniformInt -> uniformInt.getMaxValue() < uniformInt.getMinValue()
            ? DataResult.error(() -> "Max must be at least min, min: " + uniformInt.getMinValue() + ", max: " + uniformInt.getMaxValue())
            : DataResult.success(uniformInt),
         Function.identity()
      );
   }

   public static Codec<UniformInt> rangedUniformIntCodec(int min, int max) {
      Codec<UniformInt> codec = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.intRange(min, max).fieldOf("min").forGetter(UniformInt::getMinValue),
               Codec.intRange(min, max).fieldOf("max").forGetter(UniformInt::getMaxValue)
            )
            .apply(instance, UniformInt::of)
      );
      return codec.comapFlatMap(
         uniformInt -> uniformInt.getMaxValue() < uniformInt.getMinValue()
            ? DataResult.error(() -> "Max must be at least min, min: " + uniformInt.getMinValue() + ", max: " + uniformInt.getMaxValue())
            : DataResult.success(uniformInt),
         Function.identity()
      );
   }

   public static Codec<UniformFloat> rangedUniformFloatCodec(float min, float max) {
      Codec<UniformFloat> codec = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.floatRange(min, max).fieldOf("min").forGetter(UniformFloat::getMinValue),
               Codec.floatRange(min, max).fieldOf("max").forGetter(UniformFloat::getMaxValue)
            )
            .apply(instance, UniformFloat::of)
      );
      return codec.comapFlatMap(
         uniformFloat -> uniformFloat.getMaxValue() < uniformFloat.getMinValue()
            ? DataResult.error(() -> "Max must be at least min, min: " + uniformFloat.getMinValue() + ", max: " + uniformFloat.getMaxValue())
            : DataResult.success(uniformFloat),
         Function.identity()
      );
   }
}
