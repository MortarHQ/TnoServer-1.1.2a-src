package org.anti_ad.a.b.a.a.a.a;

public final class D extends b {
   private final H f;
   private final boolean g;

   public D(k var1, int var2, z var3) {
      super(var1, var2, var3, ar.a);
      this.g = false;
      this.f = null;
   }

   public D(D var1, k var2) {
      super(var1, var2, var1.c, var1.e);
      this.f = var1.f;
      this.g = a(var1, var2);
   }

   public D(D var1, k var2, H var3) {
      super(var1, var2, var1.c, var1.e);
      this.f = var3;
      this.g = a(var1, var2);
   }

   public D(D var1, k var2, af var3) {
      super(var1, var2, var3, var1.e);
      this.f = var1.f;
      this.g = a(var1, var2);
   }

   public final H c() {
      return this.f;
   }

   public final boolean d() {
      return this.g;
   }

   @Override
   public final int hashCode() {
      return org.anti_ad.a.b.a.a.a.c.m.b(
         org.anti_ad.a.b.a.a.a.c.m.a(
            org.anti_ad.a.b.a.a.a.c.m.a(
               org.anti_ad.a.b.a.a.a.c.m.a(
                  org.anti_ad.a.b.a.a.a.c.m.a(org.anti_ad.a.b.a.a.a.c.m.a(org.anti_ad.a.b.a.a.a.c.m.a(7, super.a.b), super.b), super.c), super.e
               ),
               this.g ? 1 : 0
            ),
            this.f
         ),
         6
      );
   }

   @Override
   public final boolean a(b var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof D)) {
         return false;
      } else {
         D var2 = (D)var1;
         if (this.g != var2.g) {
            return false;
         } else {
            return !org.anti_ad.a.b.a.a.a.c.n.a.a(this.f, var2.f) ? false : super.a(var1);
         }
      }
   }

   private static boolean a(D var0, k var1) {
      return var0.g || var1 instanceof y && ((y)var1).i;
   }
}
