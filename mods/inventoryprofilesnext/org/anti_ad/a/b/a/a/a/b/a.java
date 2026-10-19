package org.anti_ad.a.b.a.a.a.b;

import java.util.HashMap;
import java.util.Map;
import org.anti_ad.a.b.a.a.a.S;
import org.anti_ad.a.b.a.a.a.T;
import org.anti_ad.a.b.a.a.a.a.az;
import org.anti_ad.a.b.a.a.a.a.y;

public class a {
   public final Map a = new HashMap();
   public volatile d b;
   public final int c;
   public final y d;
   private final boolean e;

   public a(y var1, int var2) {
      this.d = var1;
      this.c = var2;
      boolean var4 = false;
      if (var1 instanceof az && ((az)var1).j) {
         var4 = true;
         d var3;
         (var3 = new d(new org.anti_ad.a.b.a.a.a.a.c())).c = new d[0];
         var3.d = false;
         var3.g = false;
         this.b = var3;
      }

      this.e = var4;
   }

   public final boolean a() {
      return this.e;
   }

   @Override
   public String toString() {
      return this.a(T.a);
   }

   public final String a(S var1) {
      return this.b == null ? "" : new c(this, var1).toString();
   }
}
