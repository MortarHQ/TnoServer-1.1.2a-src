package servercore_libs.net.kyori.adventure.pointer;

import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.key.Key;
import servercore_libs.net.kyori.examination.Examinable;
import servercore_libs.net.kyori.examination.ExaminableProperty;

public interface Pointer<V> extends Examinable {
   @NotNull
   static <V> Pointer<V> pointer(@NotNull final Class<V> type, @NotNull final Key key) {
      return new PointerImpl<>(type, key);
   }

   @NotNull
   Class<V> type();

   @NotNull
   Key key();

   @NotNull
   @Override
   default Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.of(ExaminableProperty.of("type", this.type()), ExaminableProperty.of("key", this.key()));
   }
}
