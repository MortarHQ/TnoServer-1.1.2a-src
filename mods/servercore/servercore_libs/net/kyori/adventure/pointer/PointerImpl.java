package servercore_libs.net.kyori.adventure.pointer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.internal.Internals;
import servercore_libs.net.kyori.adventure.key.Key;

final class PointerImpl<T> implements Pointer<T> {
   private final Class<T> type;
   private final Key key;

   PointerImpl(final Class<T> type, final Key key) {
      this.type = type;
      this.key = key;
   }

   @NotNull
   @Override
   public Class<T> type() {
      return this.type;
   }

   @NotNull
   @Override
   public Key key() {
      return this.key;
   }

   @Override
   public String toString() {
      return Internals.toString(this);
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (other != null && this.getClass() == other.getClass()) {
         PointerImpl<?> that = (PointerImpl<?>)other;
         return this.type.equals(that.type) && this.key.equals(that.key);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.type.hashCode();
      return 31 * result + this.key.hashCode();
   }
}
