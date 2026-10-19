package net.p3pp3rf1y.sophisticatedcore.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.ListBuilder;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

public record SetCodec<E>(Codec<E> elementCodec) implements Codec<Set<E>> {
   public <T> DataResult<T> encode(Set<E> input, DynamicOps<T> ops, T prefix) {
      ListBuilder<T> builder = ops.listBuilder();

      for (E element : input) {
         builder.add(this.elementCodec.encodeStart(ops, element));
      }

      return builder.build(prefix);
   }

   public <T> DataResult<Pair<Set<E>, T>> decode(DynamicOps<T> ops, T input) {
      return ops.getList(input).setLifecycle(Lifecycle.stable()).flatMap(stream -> {
         SetCodec<E>.DecoderState<T> decoder = new SetCodec.DecoderState<>(ops);
         stream.accept(decoder::accept);
         return decoder.build();
      });
   }

   @Override
   public String toString() {
      return "SetCodec[" + this.elementCodec + "]";
   }

   private class DecoderState<T> {
      private static final DataResult<Unit> INITIAL_RESULT = DataResult.success(Unit.INSTANCE, Lifecycle.stable());
      private final DynamicOps<T> ops;
      private final Set<E> elements = new HashSet<>();
      private final Builder<T> failed = Stream.builder();
      private DataResult<Unit> result = INITIAL_RESULT;

      private DecoderState(final DynamicOps<T> ops) {
         this.ops = ops;
      }

      public void accept(T value) {
         DataResult<Pair<E, T>> elementResult = SetCodec.this.elementCodec.decode(this.ops, value);
         elementResult.error().ifPresent(error -> this.failed.add(value));
         elementResult.resultOrPartial().ifPresent(pair -> this.elements.add((E)pair.getFirst()));
         this.result = this.result.apply2stable((result, element) -> result, elementResult);
      }

      public DataResult<Pair<Set<E>, T>> build() {
         T errors = (T)this.ops.createList(this.failed.build());
         Pair<Set<E>, T> pair = Pair.of(Set.copyOf(this.elements), errors);
         return this.result.map(ignored -> pair).setPartial(pair);
      }
   }
}
