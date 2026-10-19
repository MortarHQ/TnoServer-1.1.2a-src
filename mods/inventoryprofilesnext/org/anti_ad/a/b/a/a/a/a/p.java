package org.anti_ad.a.b.a.a.a.a;

import java.util.Arrays;

public class p extends af {
   public final af[] a;
   public final int[] b;

   public p(ax var1) {
      this(new af[]{var1.b}, new int[]{var1.c});
   }

   public p(af[] var1, int[] var2) {
      super(a(var1, var2));
      assert var1 != null && var1.length > 0;
      assert var2 != null && var2.length > 0;
      this.a = var1;
      this.b = var2;
   }

   @Override
   public final boolean a() {
      return this.b[0] == Integer.MAX_VALUE;
   }

   @Override
   public final int b() {
      return this.b.length;
   }

   @Override
   public final af a(int var1) {
      return this.a[var1];
   }

   @Override
   public final int b(int var1) {
      return this.b[var1];
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      }

      if (!(var1 instanceof p)) {
         return false;
      }

      if (this.hashCode() != var1.hashCode()) {
         return false;
      }

      var1 = var1;
      return Arrays.equals(this.b, var1.b) && Arrays.equals(this.a, var1.a);
   }

   @Override
   public String toString() {
      if (this.a()) {
         return "[]";
      }

      StringBuilder var1;
      (var1 = new StringBuilder()).append("[");

      for (int var2 = 0; var2 < this.b.length; var2++) {
         if (var2 > 0) {
            var1.append(", ");
         }

         if (this.b[var2] == Integer.MAX_VALUE) {
            var1.append("$");
         } else {
            var1.append(this.b[var2]);
            if (this.a[var2] != null) {
               var1.append(' ');
               var1.append(this.a[var2].toString());
            } else {
               var1.append("null");
            }
         }
      }

      var1.append("]");
      return var1.toString();
   }
}
