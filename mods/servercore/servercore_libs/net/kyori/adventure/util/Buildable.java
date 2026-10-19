package servercore_libs.net.kyori.adventure.util;

import java.util.function.Consumer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.builder.AbstractBuilder;

public interface Buildable<R, B extends Buildable.Builder<R>> {
   @Deprecated
   @Contract(mutates = "param1")
   @NotNull
   static <R extends Buildable<R, B>, B extends Buildable.Builder<R>> R configureAndBuild(
      @NotNull final B builder, @Nullable final Consumer<? super B> consumer
   ) {
      return AbstractBuilder.configureAndBuild(builder, consumer);
   }

   @Contract(value = "-> new", pure = true)
   @NotNull
   B toBuilder();

   @Deprecated
   interface Builder<R> extends AbstractBuilder<R> {
      @Contract(value = "-> new", pure = true)
      @NotNull
      @Override
      R build();
   }
}
