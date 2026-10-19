package servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.minimessage.Context;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.ClaimConsumer;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;

final class EmptyTagResolver implements TagResolver, MappableResolver, SerializableResolver {
   static final EmptyTagResolver INSTANCE = new EmptyTagResolver();

   private EmptyTagResolver() {
   }

   @Nullable
   @Override
   public Tag resolve(@NotNull final String name, @NotNull final ArgumentQueue arguments, @NotNull final Context ctx) {
      return null;
   }

   @Override
   public boolean has(@NotNull final String name) {
      return false;
   }

   @Override
   public boolean contributeToMap(@NotNull final Map<String, Tag> map) {
      return true;
   }

   @Override
   public void handle(@NotNull final Component serializable, @NotNull final ClaimConsumer consumer) {
   }
}
