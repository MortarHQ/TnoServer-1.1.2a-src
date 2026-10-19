package org.anti_ad.a.b.a.a.a.c;

import java.util.Iterator;

public final class q {
   public static String a(Iterator var0, String var1) {
      StringBuilder var2 = new StringBuilder();

      while (var0.hasNext()) {
         var2.append(var0.next());
         if (var0.hasNext()) {
            var2.append(var1);
         }
      }

      return var2.toString();
   }

   public static String a(String var0) {
      StringBuilder var1 = new StringBuilder();
      char[] var5;
      int var2 = (var5 = var0.toCharArray()).length;

      for (int var3 = 0; var3 < var2; var3++) {
         char var4;
         if ((var4 = var5[var3]) == '\t') {
            var1.append("\\t");
         } else if (var4 == '\n') {
            var1.append("\\n");
         } else if (var4 == '\r') {
            var1.append("\\r");
         } else {
            var1.append(var4);
         }
      }

      return var1.toString();
   }
}
