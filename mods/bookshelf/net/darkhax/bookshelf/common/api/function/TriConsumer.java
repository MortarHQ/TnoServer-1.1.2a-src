package net.darkhax.bookshelf.common.api.function;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<P1, P2, P3> {
   void accept(P1 var1, P2 var2, P3 var3);

   default TriConsumer<P1, P2, P3> andThen(TriConsumer<P1, P2, P3> after) {
      Objects.requireNonNull(after);
      return (p1, p2, p3) -> {
         this.accept(p1, p2, p3);
         after.accept(p1, p2, p3);
      };
   }
}
