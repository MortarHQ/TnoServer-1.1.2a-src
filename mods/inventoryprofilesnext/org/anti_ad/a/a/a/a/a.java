package org.anti_ad.a.a.a.a;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;

public final class a {
   private static final c a = new c("?", ".");
   private static final c b = new c("*", ".*");
   private static final d c = new d(new HashSet<>(Arrays.asList(a, b)));
   private static final c d = new c("?", "_");
   private static final c e = new c("*", "%");

   public static boolean a(String var0, String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Text must not be null");
      }

      d var2 = c;
      var0 = var0;
      if (var0 == null) {
         throw new IllegalArgumentException("Wildcard must not be null");
      }

      if (var2 == null) {
         throw new IllegalArgumentException("Rules must not be null");
      }

      d var3 = var2;
      String var9 = var0;
      LinkedList var4 = new LinkedList();

      for (c var5 : var3.a()) {
         int var6 = -1;

         do {
            if ((var6 = var9.indexOf(var5.a(), var6 + 1)) >= 0) {
               var4.add(new b(var5, var6));
            }
         } while (var6 >= 0);
      }

      var4.sort((var0x, var1x) -> {
         if (org.anti_ad.a.a.a.a.b.a(var0x) == org.anti_ad.a.a.a.a.b.a(var1x)) {
            return 0;
         } else {
            return org.anti_ad.a.a.a.a.b.a(var0x) > org.anti_ad.a.a.a.a.b.a(var1x) ? 1 : -1;
         }
      });
      LinkedList var10 = var4;
      StringBuilder var13 = new StringBuilder();
      int var14 = 0;
      Iterator var11 = var10.iterator();

      while (var11.hasNext()) {
         b var15;
         int var16;
         if ((var16 = org.anti_ad.a.a.a.a.b.a(var15 = (b)var11.next())) != 0) {
            var13.append(Pattern.quote(var0.substring(var14, var16)));
         }

         var13.append(org.anti_ad.a.a.a.a.b.b(var15).b());
         var14 = var16 + org.anti_ad.a.a.a.a.b.b(var15).a().length();
      }

      if (var14 <= var0.length() - 1) {
         var13.append(Pattern.quote(var0.substring(var14, var0.length())));
      }

      var0 = var13.toString();
      return Pattern.compile("^" + var0 + "$").matcher(var1).matches();
   }

   static {
      new d(new HashSet<>(Arrays.asList(d, e)));
   }
}
