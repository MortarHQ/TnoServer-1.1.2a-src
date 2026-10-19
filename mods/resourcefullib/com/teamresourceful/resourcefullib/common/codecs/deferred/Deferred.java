package com.teamresourceful.resourcefullib.common.codecs.deferred;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus.Experimental;

@Experimental
public class Deferred<E> implements Supplier<E> {
   private Deferred.DeferredDecoder<E, ?> decoder;
   private E value;

   private Deferred(Deferred.DeferredDecoder<E, ?> decoder) {
      this.decoder = decoder;
   }

   @Override
   public E get() {
      if (this.value == null) {
         this.value = this.decoder.decode();
         this.decoder = null;
      }

      return this.value;
   }

   public static <V, T> Deferred<V> of(DynamicOps<T> ops, Codec<V> codec, T input) {
      return new Deferred(new Deferred.DeferredDecoder<>(ops, (Codec<E>)codec, input));
   }

   private record DeferredDecoder<E, T>(DynamicOps<T> ops, Codec<E> codec, T input) {
      public E decode() {
         return this.codec
            .decode(this.ops, this.input)
            .result()
            .<E>map(Pair::getFirst)
            .orElseThrow(() -> new IllegalStateException("Unable to parse deferred value"));
      }
   }
}
