package servercore_libs.net.kyori.adventure.text.minimessage.tag.standard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.TranslatableComponent;
import servercore_libs.net.kyori.adventure.text.TranslationArgument;
import servercore_libs.net.kyori.adventure.text.minimessage.Context;
import servercore_libs.net.kyori.adventure.text.minimessage.ParsingException;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.Emitable;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

final class TranslatableTag {
   private static final String TR = "tr";
   private static final String TRANSLATE = "translate";
   private static final String LANG = "lang";
   static final TagResolver RESOLVER = SerializableResolver.claimingComponent(
      StandardTags.names("lang", "translate", "tr"), TranslatableTag::create, TranslatableTag::claim
   );

   private TranslatableTag() {
   }

   static Tag create(final ArgumentQueue args, final Context ctx) throws ParsingException {
      String key = args.popOr("A translation key is required").value();
      List<Component> with;
      if (args.hasNext()) {
         with = new ArrayList<>();

         while (args.hasNext()) {
            with.add(ctx.deserialize(args.pop().value()));
         }
      } else {
         with = Collections.emptyList();
      }

      return Tag.inserting(Component.translatable(key, with));
   }

   @Nullable
   static Emitable claim(final Component input) {
      if (input instanceof TranslatableComponent && ((TranslatableComponent)input).fallback() == null) {
         TranslatableComponent tr = (TranslatableComponent)input;
         return emit -> {
            emit.tag("lang");
            emit.argument(tr.key());

            for (TranslationArgument with : tr.arguments()) {
               emit.argument(with.asComponent());
            }
         };
      } else {
         return null;
      }
   }
}
