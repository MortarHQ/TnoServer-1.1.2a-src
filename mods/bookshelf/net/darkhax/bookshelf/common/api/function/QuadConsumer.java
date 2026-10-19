package net.darkhax.bookshelf.common.api.function;

import java.util.Objects;

@FunctionalInterface
public interface QuadConsumer<P1, P2, P3, P4> {
   void accept(P1 var1, P2 var2, P3 var3, P4 var4);

   default QuadConsumer<P1, P2, P3, P4> andThen(QuadConsumer<P1, P2, P3, P4> after) {
      Objects.requireNonNull(after);
      return (p1, p2, p3, p4) -> {
         this.accept(p1, p2, p3, p4);
         after.accept(p1, p2, p3, p4);
      };
   }
}
