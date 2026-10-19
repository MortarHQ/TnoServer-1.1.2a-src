package servercore_libs.net.kyori.adventure.text;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.event.ClickEvent;
import servercore_libs.net.kyori.adventure.text.event.HoverEventSource;
import servercore_libs.net.kyori.adventure.text.format.Style;
import servercore_libs.net.kyori.adventure.text.format.TextColor;
import servercore_libs.net.kyori.adventure.text.format.TextDecoration;

public interface ScopedComponent<C extends Component> extends Component {
   @NotNull
   @Override
   C children(@NotNull final List<? extends ComponentLike> children);

   @NotNull
   @Override
   C style(@NotNull final Style style);

   @NotNull
   @Override
   default C style(@NotNull final Consumer<Style.Builder> style) {
      return (C)Component.super.style(style);
   }

   @NotNull
   @Override
   default C style(final Style.@NotNull Builder style) {
      return (C)Component.super.style(style);
   }

   @NotNull
   @Override
   default C mergeStyle(@NotNull final Component that) {
      return (C)Component.super.mergeStyle(that);
   }

   @NotNull
   @Override
   default C mergeStyle(@NotNull final Component that, final Style.@NotNull Merge @NotNull ... merges) {
      return (C)Component.super.mergeStyle(that, merges);
   }

   @NotNull
   @Override
   default C append(@NotNull final Component component) {
      return (C)Component.super.append(component);
   }

   @NotNull
   @Override
   default C append(@NotNull final ComponentLike like) {
      return (C)Component.super.append(like);
   }

   @NotNull
   @Override
   default C append(@NotNull final ComponentBuilder<?, ?> builder) {
      return (C)Component.super.append(builder);
   }

   @NotNull
   @Override
   default C mergeStyle(@NotNull final Component that, @NotNull final Set<Style.Merge> merges) {
      return (C)Component.super.mergeStyle(that, merges);
   }

   @NotNull
   @Override
   default C color(@Nullable final TextColor color) {
      return (C)Component.super.color(color);
   }

   @NotNull
   @Override
   default C colorIfAbsent(@Nullable final TextColor color) {
      return (C)Component.super.colorIfAbsent(color);
   }

   @NotNull
   @Override
   default C decorate(@NotNull final TextDecoration decoration) {
      return (C)Component.super.decorate(decoration);
   }

   @NotNull
   @Override
   default C decoration(@NotNull final TextDecoration decoration, final boolean flag) {
      return (C)Component.super.decoration(decoration, flag);
   }

   @NotNull
   @Override
   default C decoration(@NotNull final TextDecoration decoration, final TextDecoration.@NotNull State state) {
      return (C)Component.super.decoration(decoration, state);
   }

   @NotNull
   @Override
   default C clickEvent(@Nullable final ClickEvent event) {
      return (C)Component.super.clickEvent(event);
   }

   @NotNull
   @Override
   default C hoverEvent(@Nullable final HoverEventSource<?> event) {
      return (C)Component.super.hoverEvent(event);
   }

   @NotNull
   @Override
   default C insertion(@Nullable final String insertion) {
      return (C)Component.super.insertion(insertion);
   }
}
