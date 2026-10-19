package servercore_libs.net.kyori.adventure.text.minimessage.tag.standard;

import servercore_libs.net.kyori.adventure.text.format.Style;
import servercore_libs.net.kyori.adventure.text.minimessage.Context;
import servercore_libs.net.kyori.adventure.text.minimessage.ParsingException;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.StyleClaim;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.TokenEmitter;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

final class InsertionTag {
   private static final String INSERTION = "insert";
   static final TagResolver RESOLVER = SerializableResolver.claimingStyle(
      "insert", InsertionTag::create, StyleClaim.claim("insert", Style::insertion, InsertionTag::emit)
   );

   private InsertionTag() {
   }

   static Tag create(final ArgumentQueue args, final Context ctx) throws ParsingException {
      String insertion = args.popOr("A value is required to produce an insertion component").value();
      return Tag.styling(b -> b.insertion(insertion));
   }

   static void emit(final String insertion, final TokenEmitter emitter) {
      emitter.tag("insert").argument(insertion);
   }
}
