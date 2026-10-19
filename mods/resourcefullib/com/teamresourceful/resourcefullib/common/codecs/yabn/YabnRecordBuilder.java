package com.teamresourceful.resourcefullib.common.codecs.yabn;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.RecordBuilder.AbstractStringBuilder;
import com.teamresourceful.yabn.elements.YabnElement;
import com.teamresourceful.yabn.elements.YabnObject;

public class YabnRecordBuilder extends AbstractStringBuilder<YabnElement, YabnObject> {
   protected YabnRecordBuilder(DynamicOps<YabnElement> ops) {
      super(ops);
   }

   protected YabnObject initBuilder() {
      return new YabnObject();
   }

   protected YabnObject append(String key, YabnElement value, YabnObject builder) {
      builder.put(key, value);
      return builder;
   }

   protected DataResult<YabnElement> build(YabnObject builder, YabnElement prefix) {
      if (prefix == null || prefix.isNull()) {
         return DataResult.success(builder);
      } else if (prefix instanceof YabnObject object) {
         YabnObject result = new YabnObject();
         object.elements().forEach(result::put);
         builder.elements().forEach(result::put);
         return DataResult.success(result);
      } else {
         return DataResult.error(() -> "mergeToMap called with not a map: " + prefix, prefix);
      }
   }
}
