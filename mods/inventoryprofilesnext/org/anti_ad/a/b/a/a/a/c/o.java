package org.anti_ad.a.b.a.a.a.c;

import java.io.Serializable;

public class o implements Serializable {
   public final Object a;
   public final Object b;

   public o(Object var1, Object var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      }

      if (!(var1 instanceof o)) {
         return false;
      }

      var1 = var1;
      return n.a.a(this.a, var1.a) && n.a.a(this.b, var1.b);
   }

   @Override
   public int hashCode() {
      return m.b(m.a(m.a(0, this.a), this.b), 2);
   }

   @Override
   public String toString() {
      return String.format("(%s, %s)", this.a, this.b);
   }
}
