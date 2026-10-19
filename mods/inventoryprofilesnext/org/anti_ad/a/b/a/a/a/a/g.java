package org.anti_ad.a.b.a.a.a.a;

public final class g {
   private static final g a;
   private boolean b;
   private boolean c = true;
   private boolean d = false;

   public static g a() {
      return a;
   }

   public final boolean b() {
      return this.c;
   }

   public final boolean c() {
      return this.d;
   }

   public final void d() {
      if (this.b) {
         throw new IllegalStateException("The object is read only.");
      }

      this.d = true;
   }

   static {
      (a = new g()).b = true;
   }
}
