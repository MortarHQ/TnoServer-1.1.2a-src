package servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver;

import java.util.Arrays;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.minimessage.Context;
import servercore_libs.net.kyori.adventure.text.minimessage.ParsingException;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.ClaimConsumer;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;

final class SequentialTagResolver implements TagResolver, SerializableResolver {
   final TagResolver[] resolvers;

   SequentialTagResolver(@NotNull final TagResolver[] resolvers) {
      this.resolvers = resolvers;
   }

   @Nullable
   @Override
   public Tag resolve(@NotNull final String name, @NotNull final ArgumentQueue arguments, @NotNull final Context ctx) throws ParsingException {
      ParsingException thrown = null;

      for (TagResolver resolver : this.resolvers) {
         try {
            Tag placeholder = resolver.resolve(name, arguments, ctx);
            if (placeholder != null) {
               return placeholder;
            }
         } catch (ParsingException ex) {
            arguments.reset();
            if (thrown == null) {
               thrown = ex;
            } else {
               thrown.addSuppressed(ex);
            }
         } catch (Exception ex) {
            arguments.reset();
            ParsingException err = ctx.newException("Exception thrown while parsing <" + name + ">", ex, arguments);
            if (thrown == null) {
               thrown = err;
            } else {
               thrown.addSuppressed(err);
            }
         }
      }

      if (thrown != null) {
         throw thrown;
      } else {
         return null;
      }
   }

   @Override
   public boolean has(@NotNull final String name) {
      for (TagResolver resolver : this.resolvers) {
         if (resolver.has(name)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void handle(@NotNull final Component serializable, @NotNull final ClaimConsumer consumer) {
      for (TagResolver resolver : this.resolvers) {
         if (resolver instanceof SerializableResolver) {
            ((SerializableResolver)resolver).handle(serializable, consumer);
         }
      }
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (other == this) {
         return true;
      }

      if (!(other instanceof SequentialTagResolver)) {
         return false;
      }

      SequentialTagResolver that = (SequentialTagResolver)other;
      return Arrays.equals(this.resolvers, that.resolvers);
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.resolvers);
   }
}
