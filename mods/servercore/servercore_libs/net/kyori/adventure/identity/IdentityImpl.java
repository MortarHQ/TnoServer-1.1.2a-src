package servercore_libs.net.kyori.adventure.identity;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.internal.Internals;
import servercore_libs.net.kyori.examination.Examinable;

final class IdentityImpl implements Examinable, Identity {
   private final UUID uuid;

   IdentityImpl(final UUID uuid) {
      this.uuid = uuid;
   }

   @NotNull
   @Override
   public UUID uuid() {
      return this.uuid;
   }

   @Override
   public String toString() {
      return Internals.toString(this);
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      }

      if (!(other instanceof Identity)) {
         return false;
      }

      Identity that = (Identity)other;
      return this.uuid.equals(that.uuid());
   }

   @Override
   public int hashCode() {
      return this.uuid.hashCode();
   }
}
