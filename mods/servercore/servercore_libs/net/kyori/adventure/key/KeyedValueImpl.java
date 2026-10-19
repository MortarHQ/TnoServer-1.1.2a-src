package servercore_libs.net.kyori.adventure.key;

import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.examination.Examinable;
import servercore_libs.net.kyori.examination.ExaminableProperty;
import servercore_libs.net.kyori.examination.string.StringExaminer;

final class KeyedValueImpl<T> implements Examinable, KeyedValue<T> {
   private final Key key;
   private final T value;

   KeyedValueImpl(final Key key, final T value) {
      this.key = key;
      this.value = value;
   }

   @NotNull
   @Override
   public Key key() {
      return this.key;
   }

   @NotNull
   @Override
   public T value() {
      return this.value;
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (other != null && this.getClass() == other.getClass()) {
         KeyedValueImpl<?> that = (KeyedValueImpl<?>)other;
         return this.key.equals(that.key) && this.value.equals(that.value);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.key.hashCode();
      return 31 * result + this.value.hashCode();
   }

   @NotNull
   @Override
   public Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.of(ExaminableProperty.of("key", this.key), ExaminableProperty.of("value", this.value));
   }

   @Override
   public String toString() {
      return this.examine(StringExaminer.simpleEscaping());
   }
}
