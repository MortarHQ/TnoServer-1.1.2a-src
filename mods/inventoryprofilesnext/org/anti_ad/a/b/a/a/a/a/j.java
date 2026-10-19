package org.anti_ad.a.b.a.a.a.a;

import java.util.IdentityHashMap;

public abstract class j {
   public static final org.anti_ad.a.b.a.a.a.b.d a;
   public final a b;
   protected final ag c;

   public j(a var1, ag var2) {
      this.b = var1;
      this.c = var2;
   }

   public abstract void a();

   public final ag b() {
      return this.c;
   }

   public final af a(af var1) {
      if (this.c == null) {
         return var1;
      }

      synchronized (this.c) {
         IdentityHashMap var3 = new IdentityHashMap();
         return af.a(var1, this.c, var3);
      }
   }

   static {
      (a = new org.anti_ad.a.b.a.a.a.b.d(new c())).a = Integer.MAX_VALUE;
   }
}
