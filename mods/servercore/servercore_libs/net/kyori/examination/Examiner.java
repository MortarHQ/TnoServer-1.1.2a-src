package servercore_libs.net.kyori.examination;

import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Examiner<R> {
   @NotNull
   default R examine(@NotNull final Examinable examinable) {
      return this.examine(examinable.examinableName(), examinable.examinableProperties());
   }

   @NotNull
   R examine(@NotNull final String name, @NotNull final Stream<? extends ExaminableProperty> properties);

   @NotNull
   R examine(@Nullable final Object value);

   @NotNull
   R examine(final boolean value);

   @NotNull
   R examine(final boolean @Nullable [] values);

   @NotNull
   R examine(final byte value);

   @NotNull
   R examine(final byte @Nullable [] values);

   @NotNull
   R examine(final char value);

   @NotNull
   R examine(final char @Nullable [] values);

   @NotNull
   R examine(final double value);

   @NotNull
   R examine(final double @Nullable [] values);

   @NotNull
   R examine(final float value);

   @NotNull
   R examine(final float @Nullable [] values);

   @NotNull
   R examine(final int value);

   @NotNull
   R examine(final int @Nullable [] values);

   @NotNull
   R examine(final long value);

   @NotNull
   R examine(final long @Nullable [] values);

   @NotNull
   R examine(final short value);

   @NotNull
   R examine(final short @Nullable [] values);

   @NotNull
   R examine(@Nullable final String value);
}
