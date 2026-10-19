package servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.minimessage.Context;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.TagInternals;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public interface SerializableResolver {
   @NotNull
   static TagResolver claimingComponent(
      @NotNull final String name,
      @NotNull final BiFunction<ArgumentQueue, Context, Tag> handler,
      @NotNull final Function<Component, @Nullable Emitable> componentClaim
   ) {
      return claimingComponent(Collections.singleton(name), handler, componentClaim);
   }

   @NotNull
   static TagResolver claimingComponent(
      @NotNull final Set<String> names,
      @NotNull final BiFunction<ArgumentQueue, Context, Tag> handler,
      @NotNull final Function<Component, @Nullable Emitable> componentClaim
   ) {
      Set<String> ownNames = new HashSet<>(names);

      for (String name : ownNames) {
         TagInternals.assertValidTagName(name);
      }

      Objects.requireNonNull(handler, "handler");
      return new ComponentClaimingResolverImpl(ownNames, handler, componentClaim);
   }

   @NotNull
   static TagResolver claimingStyle(
      @NotNull final String name, @NotNull final BiFunction<ArgumentQueue, Context, Tag> handler, @NotNull final StyleClaim<?> styleClaim
   ) {
      return claimingStyle(Collections.singleton(name), handler, styleClaim);
   }

   @NotNull
   static TagResolver claimingStyle(
      @NotNull final Set<String> names, @NotNull final BiFunction<ArgumentQueue, Context, Tag> handler, @NotNull final StyleClaim<?> styleClaim
   ) {
      Set<String> ownNames = new HashSet<>(names);

      for (String name : ownNames) {
         TagInternals.assertValidTagName(name);
      }

      Objects.requireNonNull(handler, "handler");
      return new StyleClaimingResolverImpl(ownNames, handler, styleClaim);
   }

   void handle(@NotNull final Component serializable, @NotNull final ClaimConsumer consumer);

   interface Single extends SerializableResolver {
      @Override
      default void handle(@NotNull final Component serializable, @NotNull final ClaimConsumer consumer) {
         StyleClaim<?> style = this.claimStyle();
         if (style != null && !consumer.styleClaimed(style.claimKey())) {
            Emitable applied = style.apply(serializable.style());
            if (applied != null) {
               consumer.style(style.claimKey(), applied);
            }
         }

         if (!consumer.componentClaimed()) {
            Emitable component = this.claimComponent(serializable);
            if (component != null) {
               consumer.component(component);
            }
         }
      }

      @Nullable
      default StyleClaim<?> claimStyle() {
         return null;
      }

      @Nullable
      default Emitable claimComponent(@NotNull final Component component) {
         return null;
      }
   }
}
