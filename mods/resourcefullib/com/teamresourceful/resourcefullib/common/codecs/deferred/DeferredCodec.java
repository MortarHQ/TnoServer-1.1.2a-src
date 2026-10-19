package com.teamresourceful.resourcefullib.common.codecs.deferred;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import org.jetbrains.annotations.ApiStatus.Experimental;

@Experimental
public class DeferredCodec<E> implements Codec<Deferred<E>> {
   private final Codec<E> codec;

   public DeferredCodec(Codec<E> codec) {
      this.codec = codec;
   }

   public <T> DataResult<Pair<Deferred<E>, T>> decode(DynamicOps<T> ops, T input) {
      return DataResult.success(Pair.of(Deferred.of(ops, this.codec, input), input));
   }

   public <T> DataResult<T> encode(Deferred<E> input, DynamicOps<T> ops, T prefix) {
      return input.get() == null ? DataResult.success(prefix) : this.codec.encode(input.get(), ops, prefix);
   }
}
