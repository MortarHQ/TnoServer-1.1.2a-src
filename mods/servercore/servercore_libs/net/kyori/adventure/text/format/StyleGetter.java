package servercore_libs.net.kyori.adventure.text.format;

import java.util.EnumMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import servercore_libs.net.kyori.adventure.key.Key;
import servercore_libs.net.kyori.adventure.text.event.ClickEvent;
import servercore_libs.net.kyori.adventure.text.event.HoverEvent;

@NonExtendable
public interface StyleGetter {
   @Nullable
   Key font();

   @Nullable
   TextColor color();

   default boolean hasDecoration(@NotNull final TextDecoration decoration) {
      return this.decoration(decoration) == TextDecoration.State.TRUE;
   }

   TextDecoration.@NotNull State decoration(@NotNull final TextDecoration decoration);

   @NotNull
   default @Unmodifiable Map<TextDecoration, TextDecoration.State> decorations() {
      Map<TextDecoration, TextDecoration.State> decorations = new EnumMap<>(TextDecoration.class);
      int i = 0;

      for (int length = DecorationMap.DECORATIONS.length; i < length; i++) {
         TextDecoration decoration = DecorationMap.DECORATIONS[i];
         TextDecoration.State value = this.decoration(decoration);
         decorations.put(decoration, value);
      }

      return decorations;
   }

   @Nullable
   ClickEvent clickEvent();

   @Nullable
   HoverEvent<?> hoverEvent();

   @Nullable
   String insertion();
}
