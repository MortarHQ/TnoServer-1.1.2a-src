package net.darkhax.bookshelf.common.impl.data.loot.modifiers;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

public class FingerprintCodec<T> implements Codec<T> {
   private final Codec<T> delegate;

   public FingerprintCodec(Codec<T> delegate) {
      this.delegate = delegate;
   }

   public <T1> DataResult<Pair<T, T1>> decode(DynamicOps<T1> ops, T1 input) {
      DataResult<Pair<T, T1>> result = this.delegate.decode(ops, input);
      if (input instanceof JsonElement json && result.error().isEmpty()) {
         result.result().ifPresent(r -> {
            if (r.getFirst() instanceof ILootPoolHooks hooks) {
               hooks.bookshelf$setHash(json.toString().hashCode());
            }
         });
      }

      return result;
   }

   public <T1> DataResult<T1> encode(T input, DynamicOps<T1> ops, T1 prefix) {
      return this.delegate.encode(input, ops, prefix);
   }
}
