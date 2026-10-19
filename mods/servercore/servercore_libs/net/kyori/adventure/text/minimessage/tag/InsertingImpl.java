package servercore_libs.net.kyori.adventure.text.minimessage.tag;

import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.examination.ExaminableProperty;

final class InsertingImpl extends AbstractTag implements Inserting {
   private final boolean allowsChildren;
   private final Component value;

   InsertingImpl(final boolean allowsChildren, final Component value) {
      this.allowsChildren = allowsChildren;
      this.value = value;
   }

   @Override
   public boolean allowsChildren() {
      return this.allowsChildren;
   }

   @NotNull
   @Override
   public Component value() {
      return this.value;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.allowsChildren, this.value);
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      }

      if (!(other instanceof InsertingImpl)) {
         return false;
      }

      InsertingImpl that = (InsertingImpl)other;
      return this.allowsChildren == that.allowsChildren && Objects.equals(this.value, that.value);
   }

   @NotNull
   @Override
   public Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.of(ExaminableProperty.of("allowsChildren", this.allowsChildren), ExaminableProperty.of("value", this.value));
   }
}
