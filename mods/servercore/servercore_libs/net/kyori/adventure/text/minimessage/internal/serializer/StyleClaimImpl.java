package servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.format.Style;

class StyleClaimImpl<V> implements StyleClaim<V> {
   private final String claimKey;
   private final Function<Style, V> lens;
   private final Predicate<V> filter;
   private final BiConsumer<V, TokenEmitter> emitable;

   StyleClaimImpl(final String claimKey, final Function<Style, @Nullable V> lens, final Predicate<V> filter, final BiConsumer<V, TokenEmitter> emitable) {
      this.claimKey = claimKey;
      this.lens = lens;
      this.filter = filter;
      this.emitable = emitable;
   }

   @NotNull
   @Override
   public String claimKey() {
      return this.claimKey;
   }

   @Nullable
   @Override
   public Emitable apply(@NotNull final Style style) {
      V element = this.lens.apply(style);
      return element != null && this.filter.test(element) ? emitter -> this.emitable.accept(element, emitter) : null;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.claimKey);
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      }

      if (!(other instanceof StyleClaimImpl)) {
         return false;
      }

      StyleClaimImpl<?> that = (StyleClaimImpl<?>)other;
      return Objects.equals(this.claimKey, that.claimKey);
   }
}
