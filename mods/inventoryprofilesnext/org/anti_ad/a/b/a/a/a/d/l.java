package org.anti_ad.a.b.a.a.a.d;

import java.util.List;
import org.anti_ad.a.b.a.a.a.M;
import org.anti_ad.a.b.a.a.a.O;
import org.anti_ad.a.b.a.a.a.c.q;

public final class l {
   public static String a(k var0, List var1) {
      String var2 = q.a(b(var0, var1));
      if (var0.getChildCount() == 0) {
         return var2;
      }

      StringBuilder var3;
      (var3 = new StringBuilder()).append("(");
      var2 = q.a(b(var0, var1));
      var3.append(var2);
      var3.append(' ');

      for (int var5 = 0; var5 < var0.getChildCount(); var5++) {
         if (var5 > 0) {
            var3.append(' ');
         }

         var3.append(a(var0.getChild(var5), var1));
      }

      var3.append(")");
      return var3.toString();
   }

   private static String b(k var0, List var1) {
      if (var1 != null) {
         if (var0 instanceof M) {
            int var6 = ((M)var0).getRuleContext().getRuleIndex();
            String var4 = (String)var1.get(var6);
            int var3;
            if ((var3 = ((M)var0).getAltNumber()) != 0) {
               return var4 + ":" + var3;
            }

            return var4;
         }

         if (var0 instanceof b) {
            return var0.toString();
         }

         O var2;
         if (var0 instanceof i && (var2 = ((i)var0).a()) != null) {
            return var2.b();
         }
      }

      Object var5;
      return (var5 = var0.getPayload()) instanceof O ? ((O)var5).b() : var0.getPayload().toString();
   }
}
