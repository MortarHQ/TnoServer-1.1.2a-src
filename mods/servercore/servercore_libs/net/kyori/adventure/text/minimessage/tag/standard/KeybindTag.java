package servercore_libs.net.kyori.adventure.text.minimessage.tag.standard;

import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.KeybindComponent;
import servercore_libs.net.kyori.adventure.text.minimessage.Context;
import servercore_libs.net.kyori.adventure.text.minimessage.ParsingException;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.Emitable;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

final class KeybindTag {
   public static final String KEYBIND = "key";
   static final TagResolver RESOLVER = SerializableResolver.claimingComponent("key", KeybindTag::create, KeybindTag::emit);

   private KeybindTag() {
   }

   static Tag create(final ArgumentQueue args, final Context ctx) throws ParsingException {
      return Tag.inserting(Component.keybind(args.popOr("A keybind id is required").value()));
   }

   @Nullable
   static Emitable emit(final Component component) {
      if (!(component instanceof KeybindComponent)) {
         return null;
      }

      String key = ((KeybindComponent)component).keybind();
      return emit -> emit.tag("key").argument(key);
   }
}
