package servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.ComponentLike;
import servercore_libs.net.kyori.adventure.text.format.StyleBuilderApplicable;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.TagPattern;

public final class Placeholder {
   private Placeholder() {
   }

   public static TagResolver.@NotNull Single parsed(@TagPattern @NotNull final String key, @NotNull final String value) {
      return TagResolver.resolver(key, Tag.preProcessParsed(value));
   }

   public static TagResolver.@NotNull Single unparsed(@TagPattern @NotNull final String key, @NotNull final String value) {
      Objects.requireNonNull(value, "value");
      return component(key, Component.text(value));
   }

   public static TagResolver.@NotNull Single component(@TagPattern @NotNull final String key, @NotNull final ComponentLike value) {
      return TagResolver.resolver(key, Tag.selfClosingInserting(value));
   }

   public static TagResolver.@NotNull Single styling(@TagPattern @NotNull final String key, @NotNull final StyleBuilderApplicable... style) {
      return TagResolver.resolver(key, Tag.styling(style));
   }
}
