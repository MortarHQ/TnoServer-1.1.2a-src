package net.regions_unexplored.config.json5;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.Optional;
import java.util.stream.Stream;

public class CommentedMapCodec<A> extends MapCodec<A> {
   private final MapCodec<A> delegate;
   private final String key;
   private final String comment;

   private CommentedMapCodec(MapCodec<A> delegate, String key, String comment) {
      this.delegate = delegate;
      this.key = key;
      this.comment = comment;
   }

   public static <A> CommentedMapCodec<A> commented(Codec<A> delegate, String key, String comment) {
      return commented(delegate.fieldOf(key), key, comment);
   }

   public static <A> CommentedMapCodec<A> optionalCommented(Codec<A> delegate, A fallback, String key, String comment) {
      return commented(delegate.fieldOf(key).orElse(fallback), key, comment);
   }

   public static <A> CommentedMapCodec<Optional<A>> lenientOptionalCommented(Codec<A> delegate, String key, String comment) {
      return commented(delegate.lenientOptionalFieldOf(key), key, comment);
   }

   public static <A> CommentedMapCodec<A> commented(MapCodec<A> delegate, String key, String comment) {
      return new CommentedMapCodec<>(delegate, key, comment);
   }

   public <T> Stream<T> keys(DynamicOps<T> ops) {
      return this.delegate.keys(ops);
   }

   public <T> DataResult<A> decode(DynamicOps<T> ops, MapLike<T> input) {
      return this.delegate.decode(ops, input);
   }

   public <T> RecordBuilder<T> encode(A input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
      RecordBuilder<T> encoded = this.delegate.encode(input, ops, prefix);
      if (encoded instanceof Json5Ops.Json5RecordBuilder json5builder) {
         json5builder.putComment(this.key, this.comment);
      }

      return encoded;
   }
}
