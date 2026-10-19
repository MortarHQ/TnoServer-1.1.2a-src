package net.darkhax.bookshelf.common.api.util;

import com.mojang.datafixers.util.Either;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionHelper {
   public static <T> boolean test(Optional<T> input, Predicate<T> test) {
      return input.isEmpty() || test.test(input.get());
   }

   public static <T> T unpack(Either<T, T> either) {
      return (T)either.map(Function.identity(), Function.identity());
   }
}
