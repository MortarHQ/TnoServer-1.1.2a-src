package org.anti_ad.a.b.a.a.a.a;

import java.util.Objects;

public class b {
   public final k a;
   public final int b;
   public af c;
   public int d;
   public final ap e;

   public b(k var1, int var2, af var3) {
      this(var1, var2, var3, ar.a);
   }

   public b(k var1, int var2, af var3, ap var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.e = var4;
   }

   public b(b var1, k var2) {
      this(var1, var2, var1.c, var1.e);
   }

   public b(b var1, k var2, ap var3) {
      this(var1, var2, var1.c, var3);
   }

   public b(b var1, ap var2) {
      this(var1, var1.a, var1.c, var2);
   }

   public b(b var1, k var2, ax var3) {
      this(var1, var2, var3, var1.e);
   }

   public b(b var1, k var2, af var3, ap var4) {
      this.a = var2;
      this.b = var1.b;
      this.c = var3;
      this.e = var4;
      this.d = var1.d;
   }

   public final boolean a() {
      return (this.d & 1073741824) != 0;
   }

   public final void b() {
      this.d |= 1073741824;
   }

   @Override
   public boolean equals(Object var1) {
      return !(var1 instanceof b) ? false : this.a((b)var1);
   }

   public boolean a(b var1) {
      if (this == var1) {
         return true;
      } else {
         return var1 == null
            ? false
            : this.a.b == var1.a.b && this.b == var1.b && Objects.equals(this.c, var1.c) && this.e.equals(var1.e) && this.a() == var1.a();
      }
   }

   @Override
   public int hashCode() {
      return org.anti_ad.a.b.a.a.a.c.m.b(
         org.anti_ad.a.b.a.a.a.c.m.a(org.anti_ad.a.b.a.a.a.c.m.a(org.anti_ad.a.b.a.a.a.c.m.a(org.anti_ad.a.b.a.a.a.c.m.a(7, this.a.b), this.b), this.c), this.e),
         4
      );
   }

   @Override
   public String toString() {
      b var1 = this;
      StringBuilder var2;
      (var2 = new StringBuilder()).append('(');
      var2.append(var1.a);
      var2.append(",");
      var2.append(var1.b);
      if (var1.c != null) {
         var2.append(",[");
         var2.append(var1.c.toString());
         var2.append("]");
      }

      if (var1.e != null && var1.e != ar.a) {
         var2.append(",");
         var2.append(var1.e);
      }

      if ((var1.d & -1073741825) > 0) {
         var2.append(",up=").append(var1.d & -1073741825);
      }

      var2.append(')');
      return var2.toString();
   }
}
