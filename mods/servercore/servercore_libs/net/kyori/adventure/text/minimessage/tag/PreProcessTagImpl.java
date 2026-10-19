package servercore_libs.net.kyori.adventure.text.minimessage.tag;

import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.examination.ExaminableProperty;

final class PreProcessTagImpl extends AbstractTag implements PreProcess {
   private final String value;

   PreProcessTagImpl(final String value) {
      this.value = value;
   }

   @NotNull
   @Override
   public String value() {
      return this.value;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.value);
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      }

      if (!(other instanceof PreProcessTagImpl)) {
         return false;
      }

      PreProcessTagImpl that = (PreProcessTagImpl)other;
      return Objects.equals(this.value, that.value);
   }

   @NotNull
   @Override
   public Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.of(ExaminableProperty.of("value", this.value));
   }
}
