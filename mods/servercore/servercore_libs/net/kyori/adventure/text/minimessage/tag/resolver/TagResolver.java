package servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import servercore_libs.net.kyori.adventure.text.minimessage.Context;
import servercore_libs.net.kyori.adventure.text.minimessage.ParsingException;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.TagInternals;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.TagPattern;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.standard.StandardTags;

public interface TagResolver {
   @NotNull
   static TagResolver.Builder builder() {
      return new TagResolverBuilderImpl();
   }

   @NotNull
   static TagResolver standard() {
      return StandardTags.defaults();
   }

   @NotNull
   static TagResolver empty() {
      return EmptyTagResolver.INSTANCE;
   }

   static TagResolver.@NotNull Single resolver(@TagPattern @NotNull final String name, @NotNull final Tag tag) {
      TagInternals.assertValidTagName(name);
      return new SingleResolver(name, Objects.requireNonNull(tag, "tag"));
   }

   @NotNull
   static TagResolver resolver(@TagPattern @NotNull final String name, @NotNull final BiFunction<ArgumentQueue, Context, Tag> handler) {
      return resolver(Collections.singleton(name), handler);
   }

   @NotNull
   static TagResolver resolver(@NotNull final Set<String> names, @NotNull final BiFunction<ArgumentQueue, Context, Tag> handler) {
      for (String name : new HashSet<>(names)) {
         TagInternals.assertValidTagName(name);
      }

      Objects.requireNonNull(handler, "handler");
      return new TagResolver() {
         @Nullable
         @Override
         public Tag resolve(@NotNull final String name, @NotNull final ArgumentQueue arguments, @NotNull final Context ctx) throws ParsingException {
            return !names.contains(name) ? null : handler.apply(arguments, ctx);
         }

         @Override
         public boolean has(@NotNull final String name) {
            return names.contains(name);
         }
      };
   }

   @NotNull
   static TagResolver resolver(@NotNull final TagResolver... resolvers) {
      return ((TagResolver[])Objects.requireNonNull(resolvers, "resolvers")).length == 1
         ? Objects.requireNonNull(resolvers[0], "resolvers must not contain null elements")
         : builder().resolvers(resolvers).build();
   }

   @NotNull
   static TagResolver resolver(@NotNull final Iterable<? extends TagResolver> resolvers) {
      if (resolvers instanceof Collection) {
         int size = ((Collection)resolvers).size();
         if (size == 0) {
            return empty();
         }

         if (size == 1) {
            return Objects.requireNonNull(resolvers.iterator().next(), "resolvers must not contain null elements");
         }
      }

      return builder().resolvers(resolvers).build();
   }

   @NotNull
   static TagResolver caching(final TagResolver.@NotNull WithoutArguments resolver) {
      return resolver instanceof CachingTagResolver ? resolver : new CachingTagResolver(Objects.requireNonNull(resolver, "resolver"));
   }

   @NotNull
   static Collector<TagResolver, ?, TagResolver> toTagResolver() {
      return TagResolverBuilderImpl.COLLECTOR;
   }

   @Nullable
   Tag resolve(@TagPattern @NotNull final String name, @NotNull final ArgumentQueue arguments, @NotNull final Context ctx) throws ParsingException;

   boolean has(@NotNull final String name);

   interface Builder {
      @NotNull
      TagResolver.Builder tag(@TagPattern @NotNull final String name, @NotNull final Tag tag);

      @NotNull
      default TagResolver.Builder tag(@TagPattern @NotNull final String name, @NotNull final BiFunction<ArgumentQueue, Context, Tag> handler) {
         return this.tag(Collections.singleton(name), handler);
      }

      @NotNull
      default TagResolver.Builder tag(@NotNull final Set<String> names, @NotNull final BiFunction<ArgumentQueue, Context, Tag> handler) {
         return this.resolver(TagResolver.resolver(names, handler));
      }

      @NotNull
      TagResolver.Builder resolver(@NotNull final TagResolver resolver);

      @NotNull
      TagResolver.Builder resolvers(@NotNull final TagResolver... resolvers);

      @NotNull
      TagResolver.Builder resolvers(@NotNull final Iterable<? extends TagResolver> resolvers);

      @NotNull
      default TagResolver.Builder caching(final TagResolver.@NotNull WithoutArguments dynamic) {
         return this.resolver(TagResolver.caching(dynamic));
      }

      @NotNull
      TagResolver build();
   }

   @NonExtendable
   interface Single extends TagResolver.WithoutArguments {
      @NotNull
      String key();

      @NotNull
      Tag tag();

      @Nullable
      @Override
      default Tag resolve(@TagPattern @NotNull final String name) {
         return this.has(name) ? this.tag() : null;
      }

      @Override
      default boolean has(@NotNull final String name) {
         return name.equalsIgnoreCase(this.key());
      }
   }

   @FunctionalInterface
   interface WithoutArguments extends TagResolver {
      @Nullable
      Tag resolve(@TagPattern @NotNull final String name);

      @Override
      default boolean has(@NotNull final String name) {
         return this.resolve(name) != null;
      }

      @Nullable
      @Override
      default Tag resolve(@TagPattern @NotNull final String name, @NotNull final ArgumentQueue arguments, @NotNull final Context ctx) throws ParsingException {
         Tag resolved = this.resolve(name);
         if (resolved != null && arguments.hasNext()) {
            throw ctx.newException("Tag '<" + name + ">' does not accept any arguments");
         } else {
            return resolved;
         }
      }
   }
}
