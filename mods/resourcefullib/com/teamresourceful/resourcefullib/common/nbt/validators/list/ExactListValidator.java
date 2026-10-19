package com.teamresourceful.resourcefullib.common.nbt.validators.list;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.teamresourceful.resourcefullib.common.nbt.validators.TagValidationType;
import com.teamresourceful.resourcefullib.common.nbt.validators.Validator;
import java.util.List;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.Tag;

public record ExactListValidator(List<Validator<?>> validators) implements ListValidator {
   public static final String ID = "list:exact";
   public static final Codec<ExactListValidator> CODEC = Validator.CODEC.listOf().validate(validators -> {
      TagValidationType type = null;

      for (Validator<?> validator : validators) {
         if (type == null) {
            type = validator.type();
         } else if (type != validator.type()) {
            return DataResult.error(() -> "All validators must be of the same type.");
         }
      }

      return DataResult.success(validators);
   }).xmap(ExactListValidator::new, ExactListValidator::validators).fieldOf("validators").codec();

   @Override
   public String id() {
      return "list:exact";
   }

   public boolean test(CollectionTag<?> tag) {
      if (tag.isEmpty()) {
         return false;
      }

      if (tag.size() != this.validators.size()) {
         return false;
      }

      for (int i = 0; i < tag.size(); i++) {
         if (!this.validators.get(i).testAndValidate((Tag)tag.get(i))) {
            return false;
         }
      }

      return true;
   }
}
