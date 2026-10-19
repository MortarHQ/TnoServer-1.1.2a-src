package servercore_libs.net.kyori.adventure.text.minimessage.tag;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.ComponentLike;
import servercore_libs.net.kyori.adventure.text.format.Style;
import servercore_libs.net.kyori.adventure.text.format.StyleBuilderApplicable;

public interface Tag {
   @NotNull
   static PreProcess preProcessParsed(@NotNull final String content) {
      return new PreProcessTagImpl(Objects.requireNonNull(content, "content"));
   }

   @NotNull
   static Tag inserting(@NotNull final Component content) {
      return new InsertingImpl(true, Objects.requireNonNull(content, "content must not be null"));
   }

   @NotNull
   static Tag inserting(@NotNull final ComponentLike value) {
      return inserting(Objects.requireNonNull(value, "value").asComponent());
   }

   @NotNull
   static Tag selfClosingInserting(@NotNull final Component content) {
      return new InsertingImpl(false, Objects.requireNonNull(content, "content must not be null"));
   }

   @NotNull
   static Tag selfClosingInserting(@NotNull final ComponentLike value) {
      return selfClosingInserting(Objects.requireNonNull(value, "value").asComponent());
   }

   @NotNull
   static Tag styling(final Consumer<Style.Builder> styles) {
      return new CallbackStylingTagImpl(styles);
   }

   @NotNull
   static Tag styling(@NotNull final StyleBuilderApplicable... actions) {
      Objects.requireNonNull(actions, "actions");
      int i = 0;

      for (int length = actions.length; i < length; i++) {
         if (actions[i] == null) {
            throw new NullPointerException("actions[" + i + "]");
         }
      }

      return new StylingTagImpl(Arrays.copyOf(actions, actions.length));
   }

   @NonExtendable
   interface Argument {
      @NotNull
      String value();

      @NotNull
      default String lowerValue() {
         return this.value().toLowerCase(Locale.ROOT);
      }

      default boolean isTrue() {
         return "true".equals(this.value()) || "on".equals(this.value());
      }

      default boolean isFalse() {
         return "false".equals(this.value()) || "off".equals(this.value());
      }

      @NotNull
      default OptionalInt asInt() {
         try {
            return OptionalInt.of(Integer.parseInt(this.value()));
         } catch (NumberFormatException ex) {
            return OptionalInt.empty();
         }
      }

      @NotNull
      default OptionalDouble asDouble() {
         try {
            return OptionalDouble.of(Double.parseDouble(this.value()));
         } catch (NumberFormatException ex) {
            return OptionalDouble.empty();
         }
      }
   }
}
