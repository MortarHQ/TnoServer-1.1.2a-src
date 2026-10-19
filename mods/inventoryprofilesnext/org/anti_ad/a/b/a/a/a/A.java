package org.anti_ad.a.b.a.a.a;

import java.util.Locale;

public class A extends J {
   private final int a;
   private final org.anti_ad.a.b.a.a.a.a.c b;

   public A(z var1, g var2, int var3, org.anti_ad.a.b.a.a.a.a.c var4) {
      super(var1, var2, null);
      this.a = var3;
      this.b = var4;
   }

   @Override
   public String toString() {
      String var1 = "";
      if (this.a >= 0 && this.a < ((g)super.a()).c()) {
         var1 = org.anti_ad.a.b.a.a.a.c.q.a(((g)super.a()).a(org.anti_ad.a.b.a.a.a.c.k.a(this.a, this.a)));
      }

      return String.format(Locale.getDefault(), "%s('%s')", A.class.getSimpleName(), var1);
   }
}
