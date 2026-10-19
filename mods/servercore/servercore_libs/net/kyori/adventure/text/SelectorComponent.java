package servercore_libs.net.kyori.adventure.text;

import java.util.stream.Stream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.examination.ExaminableProperty;

public interface SelectorComponent extends BuildableComponent<SelectorComponent, SelectorComponent.Builder>, ScopedComponent<SelectorComponent> {
   @NotNull
   String pattern();

   @Contract(pure = true)
   @NotNull
   SelectorComponent pattern(@NotNull final String pattern);

   @Nullable
   Component separator();

   @NotNull
   SelectorComponent separator(@Nullable final ComponentLike separator);

   @NotNull
   @Override
   default Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.concat(
         Stream.of(ExaminableProperty.of("pattern", this.pattern()), ExaminableProperty.of("separator", this.separator())),
         BuildableComponent.super.examinableProperties()
      );
   }

   interface Builder extends ComponentBuilder<SelectorComponent, SelectorComponent.Builder> {
      @Contract("_ -> this")
      @NotNull
      SelectorComponent.Builder pattern(@NotNull final String pattern);

      @Contract("_ -> this")
      @NotNull
      SelectorComponent.Builder separator(@Nullable final ComponentLike separator);
   }
}
