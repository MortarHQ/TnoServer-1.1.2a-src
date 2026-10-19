package servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.ClaimConsumer;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Inserting;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;

final class CachingTagResolver implements TagResolver.WithoutArguments, MappableResolver, SerializableResolver {
   private static final Tag NULL_REPLACEMENT = (Inserting)() -> {
      throw new UnsupportedOperationException("no-op null tag");
   };
   private final Map<String, Tag> cache = new HashMap<>();
   private final TagResolver.WithoutArguments resolver;

   CachingTagResolver(final TagResolver.WithoutArguments resolver) {
      this.resolver = resolver;
   }

   private Tag query(@NotNull final String key) {
      return this.cache.computeIfAbsent(key, k -> {
         Tag result = this.resolver.resolve(k);
         return result == null ? NULL_REPLACEMENT : result;
      });
   }

   @Nullable
   @Override
   public Tag resolve(@NotNull final String name) {
      Tag potentialValue = this.query(name);
      return potentialValue == NULL_REPLACEMENT ? null : potentialValue;
   }

   @Override
   public boolean has(@NotNull final String name) {
      return this.query(name) != NULL_REPLACEMENT;
   }

   @Override
   public boolean contributeToMap(@NotNull final Map<String, Tag> map) {
      return this.resolver instanceof MappableResolver ? ((MappableResolver)this.resolver).contributeToMap(map) : false;
   }

   @Override
   public void handle(@NotNull final Component serializable, @NotNull final ClaimConsumer consumer) {
      if (this.resolver instanceof SerializableResolver) {
         ((SerializableResolver)this.resolver).handle(serializable, consumer);
      }
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      }

      if (!(other instanceof CachingTagResolver)) {
         return false;
      }

      CachingTagResolver that = (CachingTagResolver)other;
      return Objects.equals(this.resolver, that.resolver);
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.resolver);
   }
}
