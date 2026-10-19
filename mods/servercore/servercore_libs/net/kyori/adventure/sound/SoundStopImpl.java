package servercore_libs.net.kyori.adventure.sound;

import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.internal.Internals;
import servercore_libs.net.kyori.adventure.key.Key;
import servercore_libs.net.kyori.examination.ExaminableProperty;

abstract class SoundStopImpl implements SoundStop {
   static final SoundStop ALL = new SoundStopImpl(null) {
      @Nullable
      @Override
      public Key sound() {
         return null;
      }
   };
   private final Sound.@Nullable Source source;

   SoundStopImpl(final Sound.@Nullable Source source) {
      this.source = source;
   }

   @Override
   public Sound.@Nullable Source source() {
      return this.source;
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      }

      if (!(other instanceof SoundStopImpl)) {
         return false;
      }

      SoundStopImpl that = (SoundStopImpl)other;
      return Objects.equals(this.sound(), that.sound()) && Objects.equals(this.source, that.source);
   }

   @Override
   public int hashCode() {
      int result = Objects.hashCode(this.sound());
      return 31 * result + Objects.hashCode(this.source);
   }

   @NotNull
   @Override
   public Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.of(ExaminableProperty.of("name", this.sound()), ExaminableProperty.of("source", this.source));
   }

   @Override
   public String toString() {
      return Internals.toString(this);
   }
}
