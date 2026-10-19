package com.teamresourceful.resourcefullib.common.codecs.yabn;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapLike;
import com.teamresourceful.yabn.elements.YabnElement;
import com.teamresourceful.yabn.elements.YabnObject;
import com.teamresourceful.yabn.elements.YabnPrimitive;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

public class YabnObjectMapLike implements MapLike<YabnElement> {
   private final YabnObject object;

   public YabnObjectMapLike(YabnObject yabnObject) {
      this.object = yabnObject;
   }

   @Nullable
   public YabnElement get(YabnElement key) {
      if (key instanceof YabnPrimitive primitive) {
         return this.get(YabnOps.getAsString(primitive));
      } else {
         throw new IllegalArgumentException("Not a string: " + key);
      }
   }

   @Nullable
   public YabnElement get(String key) {
      return (YabnElement)this.object.elements().get(key);
   }

   public Stream<Pair<YabnElement, YabnElement>> entries() {
      return this.object.elements().entrySet().stream().map(entry -> Pair.of(YabnPrimitive.ofString((String)entry.getKey()), (YabnElement)entry.getValue()));
   }
}
