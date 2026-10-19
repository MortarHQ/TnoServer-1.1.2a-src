package org.anti_ad.a.b.a.a.a;

import java.util.Locale;
import org.anti_ad.a.b.a.a.a.a.Y;
import org.anti_ad.a.b.a.a.a.a.ae;

public final class v extends J {
   private final int a;
   private final int b;
   private final String c;

   public v(G var1) {
      this(var1, null);
   }

   public v(G var1, String var2) {
      this(var1, var2, (byte)0);
   }

   private v(G var1, String var2, byte var3) {
      String var4 = var2;
      super(String.format(Locale.getDefault(), "failed predicate: {%s}?", var4), var1, var1.getInputStream(), var1._ctx);
      org.anti_ad.a.b.a.a.a.a.m var5;
      if ((var5 = (org.anti_ad.a.b.a.a.a.a.m)((org.anti_ad.a.b.a.a.a.a.k)((Y)var1.getInterpreter()).b.a.get(var1.getState())).a(0)) instanceof ae) {
         this.a = ((ae)var5).a;
         this.b = ((ae)var5).b;
      } else {
         this.a = 0;
         this.b = 0;
      }

      this.c = var2;
      this.a(var1.getCurrentToken());
   }
}
