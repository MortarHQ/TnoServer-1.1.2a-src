package servercore_libs.net.kyori.adventure.text.minimessage.tag.standard;

import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.ScoreComponent;
import servercore_libs.net.kyori.adventure.text.minimessage.Context;
import servercore_libs.net.kyori.adventure.text.minimessage.ParsingException;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.Emitable;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

final class ScoreTag {
   public static final String SCORE = "score";
   static final TagResolver RESOLVER = SerializableResolver.claimingComponent("score", ScoreTag::create, ScoreTag::emit);

   private ScoreTag() {
   }

   static Tag create(final ArgumentQueue args, final Context ctx) throws ParsingException {
      String name = args.popOr("A scoreboard member name is required").value();
      String objective = args.popOr("An objective name is required").value();
      return Tag.inserting(Component.score(name, objective));
   }

   @Nullable
   static Emitable emit(final Component component) {
      if (!(component instanceof ScoreComponent)) {
         return null;
      }

      ScoreComponent score = (ScoreComponent)component;
      return emit -> emit.tag("score").argument(score.name()).argument(score.objective());
   }
}
