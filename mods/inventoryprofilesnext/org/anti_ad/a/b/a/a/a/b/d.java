package org.anti_ad.a.b.a.a.a.b;

import java.util.Arrays;
import org.anti_ad.a.b.a.a.a.a.H;
import org.anti_ad.a.b.a.a.a.c.m;

public final class d {
   public int a = -1;
   public org.anti_ad.a.b.a.a.a.a.c b = new org.anti_ad.a.b.a.a.a.a.c();
   public d[] c;
   public boolean d = false;
   public int e;
   public H f;
   public boolean g;
   public e[] h;

   public d() {
   }

   public d(org.anti_ad.a.b.a.a.a.a.c var1) {
      this.b = var1;
   }

   @Override
   public final int hashCode() {
      return m.b(m.a(7, this.b.hashCode()), 1);
   }

   @Override
   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      }

      if (!(var1 instanceof d)) {
         return false;
      }

      var1 = var1;
      return this.b.equals(var1.b);
   }

   @Override
   public final String toString() {
      StringBuilder var1;
      (var1 = new StringBuilder()).append(this.a).append(":").append(this.b);
      if (this.d) {
         var1.append("=>");
         if (this.h != null) {
            var1.append(Arrays.toString(this.h));
         } else {
            var1.append(this.e);
         }
      }

      return var1.toString();
   }
}
