package servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.TagInternals;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;

final class TagResolverBuilderImpl implements TagResolver.Builder {
   static final Collector<TagResolver, TagResolver.Builder, TagResolver> COLLECTOR = Collector.of(
      TagResolver::builder,
      TagResolver.Builder::resolver,
      (left, right) -> TagResolver.builder().resolvers(left.build(), right.build()),
      TagResolver.Builder::build
   );
   private final Map<String, Tag> replacements = new HashMap<>();
   private final List<TagResolver> resolvers = new ArrayList<>();

   @Override
   public TagResolver.@NotNull Builder tag(@NotNull final String name, @NotNull final Tag tag) {
      TagInternals.assertValidTagName(Objects.requireNonNull(name, "name"));
      this.replacements.put(name, Objects.requireNonNull(tag, "tag"));
      return this;
   }

   @Override
   public TagResolver.@NotNull Builder resolver(@NotNull final TagResolver resolver) {
      if (resolver instanceof SequentialTagResolver) {
         this.resolvers(((SequentialTagResolver)resolver).resolvers, false);
      } else if (!this.consumePotentialMappable(resolver)) {
         this.popMap();
         this.resolvers.add(Objects.requireNonNull(resolver, "resolver"));
      }

      return this;
   }

   @Override
   public TagResolver.@NotNull Builder resolvers(@NotNull final TagResolver... resolvers) {
      return this.resolvers(resolvers, true);
   }

   private TagResolver.@NotNull Builder resolvers(@NotNull final TagResolver[] resolvers, final boolean forwards) {
      boolean popped = false;
      Objects.requireNonNull(resolvers, "resolvers");
      if (forwards) {
         for (TagResolver resolver : resolvers) {
            popped = this.single(resolver, popped);
         }
      } else {
         for (int i = resolvers.length - 1; i >= 0; i--) {
            popped = this.single(resolvers[i], popped);
         }
      }

      return this;
   }

   @Override
   public TagResolver.@NotNull Builder resolvers(@NotNull final Iterable<? extends TagResolver> resolvers) {
      boolean popped = false;

      for (TagResolver resolver : Objects.requireNonNull(resolvers, "resolvers")) {
         popped = this.single(resolver, popped);
      }

      return this;
   }

   private boolean single(final TagResolver resolver, final boolean popped) {
      if (resolver instanceof SequentialTagResolver) {
         this.resolvers(((SequentialTagResolver)resolver).resolvers, false);
      } else if (!this.consumePotentialMappable(resolver)) {
         if (!popped) {
            this.popMap();
         }

         this.resolvers.add(Objects.requireNonNull(resolver, "resolvers[?]"));
         return true;
      }

      return false;
   }

   private void popMap() {
      if (!this.replacements.isEmpty()) {
         this.resolvers.add(new MapTagResolver(new HashMap<>(this.replacements)));
         this.replacements.clear();
      }
   }

   private boolean consumePotentialMappable(final TagResolver resolver) {
      return resolver instanceof MappableResolver ? ((MappableResolver)resolver).contributeToMap(this.replacements) : false;
   }

   @NotNull
   @Override
   public TagResolver build() {
      this.popMap();
      if (this.resolvers.size() == 0) {
         return EmptyTagResolver.INSTANCE;
      }

      if (this.resolvers.size() == 1) {
         return this.resolvers.get(0);
      }

      TagResolver[] resolvers = this.resolvers.toArray(new TagResolver[0]);
      Collections.reverse(Arrays.asList(resolvers));
      return new SequentialTagResolver(resolvers);
   }
}
