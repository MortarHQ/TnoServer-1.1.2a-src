package servercore_libs.net.kyori.adventure.text.format;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.text.ComponentBuilder;
import servercore_libs.net.kyori.adventure.text.ComponentBuilderApplicable;

@FunctionalInterface
public interface StyleBuilderApplicable extends ComponentBuilderApplicable {
   @Contract(mutates = "param")
   void styleApply(final Style.@NotNull Builder style);

   @Override
   default void componentBuilderApply(@NotNull final ComponentBuilder<?, ?> component) {
      component.style(this::styleApply);
   }
}
