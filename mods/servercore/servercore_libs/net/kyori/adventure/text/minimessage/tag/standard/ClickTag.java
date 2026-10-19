package servercore_libs.net.kyori.adventure.text.minimessage.tag.standard;

import servercore_libs.net.kyori.adventure.text.event.ClickEvent;
import servercore_libs.net.kyori.adventure.text.format.Style;
import servercore_libs.net.kyori.adventure.text.minimessage.Context;
import servercore_libs.net.kyori.adventure.text.minimessage.ParsingException;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.QuotingOverride;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.StyleClaim;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

final class ClickTag {
   private static final String CLICK = "click";
   static final TagResolver RESOLVER = SerializableResolver.claimingStyle(
      "click",
      ClickTag::create,
      StyleClaim.claim(
         "click",
         Style::clickEvent,
         (event, emitter) -> emitter.tag("click").argument(ClickEvent.Action.NAMES.key(event.action())).argument(event.value(), QuotingOverride.QUOTED)
      )
   );

   private ClickTag() {
   }

   static Tag create(final ArgumentQueue args, final Context ctx) throws ParsingException {
      String actionName = args.popOr(() -> "A click tag requires an action of one of " + ClickEvent.Action.NAMES.keys()).lowerValue();
      ClickEvent.Action action = ClickEvent.Action.NAMES.value(actionName);
      if (action == null) {
         throw ctx.newException("Unknown click event action '" + actionName + "'", args);
      }

      String value = args.popOr("Click event actions require a value").value();
      return Tag.styling(ClickEvent.clickEvent(action, value));
   }
}
