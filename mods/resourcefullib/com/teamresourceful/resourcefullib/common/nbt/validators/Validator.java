package com.teamresourceful.resourcefullib.common.nbt.validators;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.nbt.Tag;

public interface Validator<T extends Tag> extends Predicate<T> {
   Codec<Validator<?>> CODEC = FullValidatorCodec.CODEC;

   String id();

   TagValidationType type();

   default boolean testAndValidate(Tag tag) {
      return this.type().test(tag) && this.test((T)tag);
   }
}
