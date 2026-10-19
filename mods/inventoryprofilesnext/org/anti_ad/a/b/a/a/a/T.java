package org.anti_ad.a.b.a.a.a;

import java.util.Arrays;

public final class T implements S {
   private static final String[] b = new String[0];
   public static final T a = new T(b, b, b);
   private final String[] c;
   private final String[] d;
   private final String[] e;
   private final int f;

   public T(String[] var1, String[] var2) {
      this(var1, var2, null);
   }

   private T(String[] var1, String[] var2, String[] var3) {
      this.c = var1 != null ? var1 : b;
      this.d = var2 != null ? var2 : b;
      this.e = var3 != null ? var3 : b;
      this.f = Math.max(this.e.length, Math.max(this.c.length, this.d.length)) - 1;
   }

   public static T a(String[] var0) {
      if (var0 != null && var0.length != 0) {
         String[] var1 = Arrays.copyOf(var0, var0.length);
         String[] var2 = Arrays.copyOf(var0, var0.length);

         for (int var3 = 0; var3 < var0.length; var3++) {
            String var4;
            if ((var4 = var0[var3]) != null) {
               if (!var4.isEmpty()) {
                  char var5;
                  if ((var5 = var4.charAt(0)) == '\'') {
                     var2[var3] = null;
                     continue;
                  }

                  if (Character.isUpperCase(var5)) {
                     var1[var3] = null;
                     continue;
                  }
               }

               var1[var3] = null;
               var2[var3] = null;
            }
         }

         return new T(var1, var2, var0);
      } else {
         return a;
      }
   }

   @Override
   public final String a(int var1) {
      return var1 >= 0 && var1 < this.c.length ? this.c[var1] : null;
   }

   @Override
   public final String b(int var1) {
      if (var1 >= 0 && var1 < this.d.length) {
         return this.d[var1];
      } else {
         return var1 == -1 ? "EOF" : null;
      }
   }

   @Override
   public final String c(int var1) {
      String var2;
      if (var1 >= 0 && var1 < this.e.length && (var2 = this.e[var1]) != null) {
         return var2;
      } else if ((var2 = this.a(var1)) != null) {
         return var2;
      } else {
         return (var2 = this.b(var1)) != null ? var2 : Integer.toString(var1);
      }
   }
}
