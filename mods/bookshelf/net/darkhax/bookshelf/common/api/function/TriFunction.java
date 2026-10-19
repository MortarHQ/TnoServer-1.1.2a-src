package net.darkhax.bookshelf.common.api.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<P1, P2, P3, R> {
   R apply(P1 var1, P2 var2, P3 var3);

   default TriFunction<P1, P2, P3, R> andThen(Function<? super R, ? extends R> after) {
      Objects.requireNonNull(after);
      return (p1, p2, p3) -> (R)after.apply(this.apply(p1, p2, p3));
   }
}
