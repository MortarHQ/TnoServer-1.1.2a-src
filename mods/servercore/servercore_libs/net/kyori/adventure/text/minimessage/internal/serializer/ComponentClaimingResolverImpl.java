package servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.minimessage.Context;
import servercore_libs.net.kyori.adventure.text.minimessage.ParsingException;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

class ComponentClaimingResolverImpl implements TagResolver, SerializableResolver.Single {
   @NotNull
   private final Set<String> names;
   @NotNull
   private final BiFunction<ArgumentQueue, Context, Tag> handler;
   @NotNull
   private final Function<Component, @Nullable Emitable> componentClaim;

   ComponentClaimingResolverImpl(
      final Set<String> names, final BiFunction<ArgumentQueue, Context, Tag> handler, final Function<Component, @Nullable Emitable> componentClaim
   ) {
      this.names = names;
      this.handler = handler;
      this.componentClaim = componentClaim;
   }

   @Nullable
   @Override
   public Tag resolve(@NotNull final String name, @NotNull final ArgumentQueue arguments, @NotNull final Context ctx) throws ParsingException {
      return !this.names.contains(name) ? null : this.handler.apply(arguments, ctx);
   }

   @Override
   public boolean has(@NotNull final String name) {
      return this.names.contains(name);
   }

   @Nullable
   @Override
   public Emitable claimComponent(@NotNull final Component component) {
      return this.componentClaim.apply(component);
   }
}
