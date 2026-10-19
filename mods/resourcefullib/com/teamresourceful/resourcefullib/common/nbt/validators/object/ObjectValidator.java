package com.teamresourceful.resourcefullib.common.nbt.validators.object;

import com.mojang.serialization.Codec;
import com.teamresourceful.resourcefullib.common.nbt.validators.TagValidationType;
import com.teamresourceful.resourcefullib.common.nbt.validators.Validator;
import com.teamresourceful.resourcefullib.common.nbt.validators.ValidatorCodec;
import net.minecraft.nbt.CompoundTag;

public interface ObjectValidator extends Validator<CompoundTag> {
   Codec<Validator<CompoundTag>> CODEC = new ValidatorCodec(PartialObjectValidator.CODEC, adder -> {
      adder.add("object:partial", PartialObjectValidator.CODEC);
      adder.add("object:exact", ExactObjectValidator.CODEC);
   });

   @Override
   default TagValidationType type() {
      return TagValidationType.COMPOUND;
   }
}
