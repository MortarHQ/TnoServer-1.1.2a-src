package com.teamresourceful.resourcefullib.common.codecs.yabn;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.ListBuilder;
import com.teamresourceful.yabn.elements.YabnArray;
import com.teamresourceful.yabn.elements.YabnElement;
import java.util.function.UnaryOperator;

public class YabnArrayBuilder implements ListBuilder<YabnElement> {
   private DataResult<YabnArray> builder = DataResult.success(new YabnArray(), Lifecycle.stable());

   public DynamicOps<YabnElement> ops() {
      return YabnOps.INSTANCE;
   }

   public ListBuilder<YabnElement> add(YabnElement value) {
      this.builder = this.builder.map(b -> b.add(value));
      return this;
   }

   public ListBuilder<YabnElement> add(DataResult<YabnElement> value) {
      this.builder = this.builder.apply2stable(YabnArray::add, value);
      return this;
   }

   public ListBuilder<YabnElement> withErrorsFrom(DataResult<?> result) {
      this.builder = this.builder.flatMap(r -> result.map(v -> r));
      return this;
   }

   public ListBuilder<YabnElement> mapError(UnaryOperator<String> onError) {
      this.builder = this.builder.mapError(onError);
      return this;
   }

   public DataResult<YabnElement> build(YabnElement prefix) {
      DataResult<YabnElement> result = this.builder.flatMap(b -> {
         if (!(prefix instanceof YabnArray) && prefix != this.ops().empty()) {
            return DataResult.error(() -> "Cannot append a list to not a list: " + prefix, prefix);
         }

         YabnArray array = new YabnArray();
         if (prefix instanceof YabnArray yabnArray) {
            array.elements().addAll(yabnArray.elements());
         }

         array.elements().addAll(b.elements());
         return DataResult.success(array, Lifecycle.stable());
      });
      this.builder = DataResult.success(new YabnArray(), Lifecycle.stable());
      return result;
   }
}
