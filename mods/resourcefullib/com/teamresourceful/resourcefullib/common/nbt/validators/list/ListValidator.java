package com.teamresourceful.resourcefullib.common.nbt.validators.list;

import com.mojang.serialization.Codec;
import com.teamresourceful.resourcefullib.common.nbt.validators.TagValidationType;
import com.teamresourceful.resourcefullib.common.nbt.validators.Validator;
import com.teamresourceful.resourcefullib.common.nbt.validators.ValidatorCodec;
import net.minecraft.nbt.CollectionTag;

public interface ListValidator extends Validator<CollectionTag<?>> {
   Codec<Validator<CollectionTag<?>>> CODEC = new ValidatorCodec(ExactListValidator.CODEC, adder -> {
      adder.add("list:exact", ExactListValidator.CODEC);
      adder.add("list:matches", MatchesListValidator.CODEC);
   });

   @Override
   default TagValidationType type() {
      return TagValidationType.LIST;
   }
}
