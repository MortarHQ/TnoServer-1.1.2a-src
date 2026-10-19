package org.anti_ad.a.b.a.a.a.b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.anti_ad.a.b.a.a.a.S;

public final class c {
   private final a a;
   private final S b;

   public c(a var1, S var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public final String toString() {
      if (this.a.b == null) {
         return null;
      }

      StringBuilder var1 = new StringBuilder();
      a var7 = this.a;
      ArrayList var8;
      Collections.sort(var8 = new ArrayList(var7.a.keySet()), new b(var7));

      for (d var3 : var8) {
         int var4 = 0;
         if (var3.c != null) {
            var4 = var3.c.length;
         }

         for (int var5 = 0; var5 < var4; var5++) {
            d var6;
            if ((var6 = var3.c[var5]) != null && var6.a != Integer.MAX_VALUE) {
               var1.append(a(var3));
               int var11 = var5;
               String var10 = this.b.c(var11 - 1);
               var1.append("-").append(var10).append("->").append(a(var6)).append('\n');
            }
         }
      }

      String var9;
      return (var9 = var1.toString()).length() == 0 ? null : var9;
   }

   private static String a(d var0) {
      int var1 = var0.a;
      String var2 = (var0.d ? ":" : "") + "s" + var1 + (var0.g ? "^" : "");
      if (var0.d) {
         return var0.h != null ? var2 + "=>" + Arrays.toString(var0.h) : var2 + "=>" + var0.e;
      } else {
         return var2;
      }
   }
}
