package servercore_libs.net.kyori.adventure.text;

import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.examination.ExaminableProperty;

public interface KeybindComponent extends BuildableComponent<KeybindComponent, KeybindComponent.Builder>, ScopedComponent<KeybindComponent> {
   @NotNull
   String keybind();

   @Contract(pure = true)
   @NotNull
   KeybindComponent keybind(@NotNull final String keybind);

   @Contract(pure = true)
   @NotNull
   default KeybindComponent keybind(@NotNull final KeybindComponent.KeybindLike keybind) {
      return this.keybind(Objects.requireNonNull(keybind, "keybind").asKeybind());
   }

   @NotNull
   @Override
   default Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.concat(Stream.of(ExaminableProperty.of("keybind", this.keybind())), BuildableComponent.super.examinableProperties());
   }

   interface Builder extends ComponentBuilder<KeybindComponent, KeybindComponent.Builder> {
      @Contract("_ -> this")
      @NotNull
      KeybindComponent.Builder keybind(@NotNull final String keybind);

      @Contract(pure = true)
      @NotNull
      default KeybindComponent.Builder keybind(@NotNull final KeybindComponent.KeybindLike keybind) {
         return this.keybind(Objects.requireNonNull(keybind, "keybind").asKeybind());
      }
   }

   interface KeybindLike {
      @NotNull
      String asKeybind();
   }
}
