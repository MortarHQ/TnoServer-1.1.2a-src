package com.teamresourceful.resourcefullib.common.nbt.validators.object;

import com.mojang.serialization.Codec;
import com.teamresourceful.resourcefullib.common.nbt.validators.Validator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.nbt.CompoundTag;

public record PartialObjectValidator(Map<String, Validator<?>> validators) implements ObjectValidator {
   public static final String ID = "object:partial";
   public static final Codec<PartialObjectValidator> CODEC = Codec.unboundedMap(Codec.STRING, Validator.CODEC)
      .xmap(PartialObjectValidator::new, PartialObjectValidator::validators)
      .fieldOf("object")
      .codec();

   @Override
   public String id() {
      return "object:partial";
   }

   public boolean test(CompoundTag tag) {
      for (Entry<String, Validator<?>> entry : this.validators.entrySet()) {
         if (!tag.contains(entry.getKey())) {
            return false;
         }

         if (!entry.getValue().testAndValidate(tag.get(entry.getKey()))) {
            return false;
         }
      }

      return true;
   }
}
