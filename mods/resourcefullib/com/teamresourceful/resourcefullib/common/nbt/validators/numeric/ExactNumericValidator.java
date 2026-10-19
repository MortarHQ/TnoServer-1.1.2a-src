package com.teamresourceful.resourcefullib.common.nbt.validators.numeric;

import com.mojang.serialization.Codec;
import com.teamresourceful.resourcefullib.common.codecs.CodecExtras;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.ShortTag;

public record ExactNumericValidator(Number number) implements NumericValidator {
   public static final String ID = "number:exact";
   public static final Codec<ExactNumericValidator> CODEC = CodecExtras.NUMBER
      .xmap(ExactNumericValidator::new, ExactNumericValidator::number)
      .fieldOf("number")
      .codec();

   @Override
   public String id() {
      return "number:exact";
   }

   public boolean test(NumericTag tag) {
      return switch (tag) {
         case null -> false;
         case ByteTag byteTag -> byteTag.getAsByte() == this.number.byteValue();
         case ShortTag shortTag -> shortTag.getAsShort() == this.number.shortValue();
         case IntTag intTag -> intTag.getAsInt() == this.number.intValue();
         case LongTag longTag -> longTag.getAsLong() == this.number.longValue();
         case FloatTag floatTag -> floatTag.getAsFloat() == this.number.floatValue();
         case DoubleTag doubleTag -> doubleTag.getAsDouble() == this.number.doubleValue();
         default -> tag.getAsNumber().equals(this.number);
      };
   }
}
