package org.anti_ad.a.b.a.a.a.c;

public class k {
   public static final k a = new k(-1, -2);
   private static k[] d = new k[1001];
   public int b;
   public int c;

   public k(int var1, int var2) {
      this.b = var1;
      this.c = var2;
   }

   public static k a(int var0, int var1) {
      if (var0 == var1 && var0 >= 0 && var0 <= 1000) {
         if (d[var0] == null) {
            d[var0] = new k(var0, var0);
         }

         return d[var0];
      } else {
         return new k(var0, var1);
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof k) {
         var1 = var1;
         return this.b == var1.b && this.c == var1.c;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return (713 + this.b) * 31 + this.c;
   }

   public final boolean a(k var1) {
      return this.b < var1.b && this.c < var1.b;
   }

   public final boolean b(k var1) {
      return this.a(var1) || this.b > var1.c;
   }

   public final boolean c(k var1) {
      return this.b == var1.c + 1 || this.c == var1.b - 1;
   }

   public final k d(k var1) {
      return a(Math.min(this.b, var1.b), Math.max(this.c, var1.c));
   }

   @Override
   public String toString() {
      return this.b + ".." + this.c;
   }
}
