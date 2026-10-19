package org.anti_ad.a.b.a.a.a.a;

import java.util.Arrays;

public final class H {
   private final G[] a;
   private final int b;

   private H(G[] var1) {
      this.a = var1;
      int var2 = 0;
      G[] var3 = var1;
      int var4 = var1.length;

      for (int var5 = 0; var5 < var4; var5++) {
         G var6 = var3[var5];
         var2 = org.anti_ad.a.b.a.a.a.c.m.a(var2, var6);
      }

      this.b = org.anti_ad.a.b.a.a.a.c.m.b(var2, var1.length);
   }

   public static H a(H var0, G var1) {
      if (var0 == null) {
         return new H(new G[]{var1});
      }

      G[] var2;
      G[] var10000 = var2 = Arrays.copyOf(var0.a, var0.a.length + 1);
      var10000[var10000.length - 1] = var1;
      return new H(var2);
   }

   public final H a(int var1) {
      G[] var2 = null;

      for (int var3 = 0; var3 < this.a.length; var3++) {
         if (this.a[var3].a() && !(this.a[var3] instanceof L)) {
            if (var2 == null) {
               var2 = (G[])this.a.clone();
            }

            var2[var3] = new L(var1, this.a[var3]);
         }
      }

      return var2 == null ? this : new H(var2);
   }

   public final void a(org.anti_ad.a.b.a.a.a.z var1, org.anti_ad.a.b.a.a.a.g var2, int var3) {
      boolean var4 = false;
      int var5 = var2.b();

      try {
         G[] var6 = this.a;
         int var7 = this.a.length;

         for (int var8 = 0; var8 < var7; var8++) {
            G var9;
            if ((var9 = var6[var8]) instanceof L) {
               int var10 = ((L)var9).b();
               var2.b(var3 + var10);
               var9 = ((L)var9).c();
               var4 = var3 + var10 != var5;
            } else if (var9.a()) {
               var2.b(var5);
               var4 = false;
            }

            var9.a(var1);
         }
      } finally {
         if (var4) {
            var2.b(var5);
         }
      }
   }

   @Override
   public final int hashCode() {
      return this.b;
   }

   @Override
   public final boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      }

      if (!(var1 instanceof H)) {
         return false;
      }

      var1 = var1;
      return this.b == var1.b && Arrays.equals(this.a, var1.a);
   }
}
