package com.teamresourceful.resourcefullib.common.nbt.validators.string;

import com.mojang.serialization.Codec;
import com.teamresourceful.resourcefullib.common.nbt.validators.TagValidationType;
import com.teamresourceful.resourcefullib.common.nbt.validators.Validator;
import com.teamresourceful.resourcefullib.common.nbt.validators.ValidatorCodec;
import net.minecraft.nbt.StringTag;

public interface StringValidator extends Validator<StringTag> {
   Codec<Validator<StringTag>> CODEC = new ValidatorCodec(ExactStringValidator.CODEC, adder -> {
      adder.add("string:exact", ExactStringValidator.CODEC);
      adder.add("string:contains", ContainsStringValidator.CODEC);
      adder.add("string:starts_with", StartsWithStringValidator.CODEC);
      adder.add("string:ends_with", EndsWithStringValidator.CODEC);
      adder.add("string:regex", RegexStringValidator.CODEC);
   });

   @Override
   default TagValidationType type() {
      return TagValidationType.STRING;
   }
}
