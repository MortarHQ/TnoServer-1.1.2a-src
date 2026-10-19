package net.regions_unexplored.config.json5;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.BaseMapCodec;
import java.util.Map;
import java.util.Map.Entry;

public record CommentedUnboundedMapCodec<K, V>(Codec<K> keyCodec, Codec<V> elementCodec, Map<K, String> comments)
   implements BaseMapCodec<K, V>,
   Codec<Map<K, V>> {
   public <T> DataResult<Pair<Map<K, V>, T>> decode(DynamicOps<T> ops, T input) {
      return ops.getMap(input).setLifecycle(Lifecycle.stable()).flatMap(map -> this.decode(ops, map)).map(r -> Pair.of(r, input));
   }

   public <T> DataResult<T> encode(Map<K, V> input, DynamicOps<T> ops, T prefix) {
      RecordBuilder<T> encoded = this.encode(input, ops, ops.mapBuilder());
      if (encoded instanceof Json5Ops.Json5RecordBuilder json5builder) {
         for (Entry<K, String> entry : this.comments.entrySet()) {
            json5builder.putComment(entry.getKey().toString(), entry.getValue());
         }
      }

      return encoded.build(prefix);
   }

   @Override
   public String toString() {
      return "UnboundedMapCodec[" + this.keyCodec + " -> " + this.elementCodec + "]";
   }
}
