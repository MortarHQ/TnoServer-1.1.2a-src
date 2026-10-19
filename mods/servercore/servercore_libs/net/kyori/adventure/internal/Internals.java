package servercore_libs.net.kyori.adventure.internal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import servercore_libs.net.kyori.examination.Examinable;
import servercore_libs.net.kyori.examination.string.StringExaminer;

@Internal
public final class Internals {
   private Internals() {
   }

   @NotNull
   public static String toString(@NotNull final Examinable examinable) {
      return examinable.examine(StringExaminer.simpleEscaping());
   }
}
