package com.teamresourceful.resourcefullib.common.nbt.validators.numeric;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.CodecExtras;
import java.util.Optional;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.ShortTag;

public record BetweenNumericValidator(Optional<Number> min, Optional<Number> max) implements NumericValidator {
   public static final String ID = "number:between";
   public static final Codec<BetweenNumericValidator> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            CodecExtras.NUMBER.optionalFieldOf("min").forGetter(BetweenNumericValidator::min),
            CodecExtras.NUMBER.optionalFieldOf("max").forGetter(BetweenNumericValidator::max)
         )
         .apply(instance, BetweenNumericValidator::new)
   );

   @Override
   public String id() {
      return "number:between";
   }

   public boolean test(NumericTag tag) {
      if (tag instanceof ByteTag byteTag) {
         byte min = this.min.map(Number::byteValue).orElse((byte)-128);
         byte max = this.max.map(Number::byteValue).orElse((byte)127);
         return byteTag.getAsByte() >= min && byteTag.getAsByte() <= max;
      } else if (tag instanceof ShortTag shortTag) {
         short min = this.min.map(Number::shortValue).orElse((short)-32768);
         short max = this.max.map(Number::shortValue).orElse((short)32767);
         return shortTag.getAsShort() >= min && shortTag.getAsShort() <= max;
      } else if (tag instanceof IntTag intTag) {
         int min = this.min.map(Number::intValue).orElse(Integer.MIN_VALUE);
         int max = this.max.map(Number::intValue).orElse(Integer.MAX_VALUE);
         return intTag.getAsInt() >= min && intTag.getAsInt() <= max;
      } else if (tag instanceof LongTag longTag) {
         long min = this.min.map(Number::longValue).orElse(Long.MIN_VALUE);
         long max = this.max.map(Number::longValue).orElse(Long.MAX_VALUE);
         return longTag.getAsLong() >= min && longTag.getAsLong() <= max;
      } else if (tag instanceof FloatTag floatTag) {
         float min = this.min.map(Number::floatValue).orElse(Float.MIN_VALUE);
         float max = this.max.map(Number::floatValue).orElse(Float.MAX_VALUE);
         return floatTag.getAsFloat() >= min && floatTag.getAsFloat() <= max;
      } else if (!(tag instanceof DoubleTag doubleTag)) {
         return false;
      } else {
         double min = this.min.map(Number::doubleValue).orElse(Double.MIN_VALUE);
         double max = this.max.map(Number::doubleValue).orElse(Double.MAX_VALUE);
         return doubleTag.getAsDouble() >= min && doubleTag.getAsDouble() <= max;
      }
   }
}
