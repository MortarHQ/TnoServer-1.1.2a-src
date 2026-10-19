package org.anti_ad.a.b.a.a.a.a;

import java.util.Iterator;

public class E extends j {
   private org.anti_ad.a.b.a.a.a.z d;
   private int e = -1;
   private int f = 1;
   private int g = 0;
   private org.anti_ad.a.b.a.a.a.b.a[] h;
   private int i = 0;
   private F j = new F();

   public E(org.anti_ad.a.b.a.a.a.z var1, a var2, org.anti_ad.a.b.a.a.a.b.a[] var3, ag var4) {
      super(var2, var4);
      this.h = var3;
      this.d = var1;
   }

   public final int a(org.anti_ad.a.b.a.a.a.g var1, int var2) {
      this.i = var2;

      try {
         this.e = var1.b();
         this.j.a();
         org.anti_ad.a.b.a.a.a.b.a var13;
         if ((var13 = this.h[var2]).b != null) {
            return this.a(var1, var13.b);
         }

         org.anti_ad.a.b.a.a.a.g var14 = var1;
         E var12 = this;
         k var3 = (k)super.b.i.get(var12.i);
         k var5 = var3;
         org.anti_ad.a.b.a.a.a.g var4 = var14;
         E var15 = var12;
         z var6 = z.a;
         V var7 = new V();

         for (int var8 = 0; var8 < var5.a(); var8++) {
            k var9 = var5.a(var8).d;
            D var19 = new D(var9, var8 + 1, var6);
            var15.a(var4, var19, var7, false, false, false);
         }

         V var16 = var7;
         boolean var18 = var7.d;
         var16.d = false;
         org.anti_ad.a.b.a.a.a.b.d var17 = var12.a(var16);
         if (!var18) {
            var12.h[var12.i].b = var17;
         }

         return var12.a(var14, var17);
      } finally {
         ;
      }
   }

   @Override
   public final void a() {
      this.j.a();
      this.e = -1;
      this.f = 1;
      this.g = 0;
      this.i = 0;
   }

   private int a(org.anti_ad.a.b.a.a.a.g var1, org.anti_ad.a.b.a.a.a.b.d var2) {
      if (var2.d) {
         this.a(this.j, var1, var2);
      }

      int var3 = var1.a(1);

      while (true) {
         org.anti_ad.a.b.a.a.a.b.d var4;
         if ((var4 = a(var2, var3)) == null) {
            var4 = this.a(var1, var2, var3);
         }

         if (var4 == a) {
            break;
         }

         if (var3 != -1) {
            this.b(var1);
         }

         if (var4.d) {
            this.a(this.j, var1, var4);
            if (var3 == -1) {
               break;
            }
         }

         var3 = var1.a(1);
         var2 = var4;
      }

      return this.a(this.j, var1, var2.b, var3);
   }

   private static org.anti_ad.a.b.a.a.a.b.d a(org.anti_ad.a.b.a.a.a.b.d var0, int var1) {
      return var0.c != null && var1 >= 0 && var1 <= 127 ? var0.c[var1] : null;
   }

   private org.anti_ad.a.b.a.a.a.b.d a(org.anti_ad.a.b.a.a.a.g var1, org.anti_ad.a.b.a.a.a.b.d var2, int var3) {
      V var4 = new V();
      this.a(var1, var2.b, var4, var3);
      if (var4.isEmpty()) {
         if (!var4.d) {
            a(var2, var3, a);
         }

         return a;
      } else {
         return this.a(var2, var3, var4);
      }
   }

   private int a(F var1, org.anti_ad.a.b.a.a.a.g var2, c var3, int var4) {
      if (var1.d != null) {
         H var5 = var1.d.f;
         this.a(var2, var5, this.e, var1.a, var1.b, var1.c);
         return var1.d.e;
      } else if (var4 == -1 && var2.b() == this.e) {
         return -1;
      } else {
         throw new org.anti_ad.a.b.a.a.a.A(this.d, var2, this.e, var3);
      }
   }

   private void a(org.anti_ad.a.b.a.a.a.g var1, c var2, V var3, int var4) {
      int var5 = 0;
      Iterator var13 = var2.iterator();

      while (var13.hasNext()) {
         b var6;
         boolean var7;
         if (!(var7 = (var6 = (b)var13.next()).b == var5) || !((D)var6).d()) {
            int var8 = var6.a.a();

            for (int var9 = 0; var9 < var8; var9++) {
               k var10;
               if ((var10 = a(var6.a.a(var9), var4)) != null) {
                  H var11;
                  if ((var11 = ((D)var6).c()) != null) {
                     var11 = var11.a(var1.b() - this.e);
                  }

                  boolean var12 = var4 == -1;
                  if (this.a(var1, new D((D)var6, var10, var11), var3, var7, true, var12)) {
                     var5 = var6.b;
                     break;
                  }
               }
            }
         }
      }
   }

   private void a(org.anti_ad.a.b.a.a.a.g var1, H var2, int var3, int var4, int var5, int var6) {
      var1.b(var4);
      this.f = var5;
      this.g = var6;
      if (var2 != null && this.d != null) {
         var2.a(this.d, var1, var3);
      }
   }

   private static k a(aC var0, int var1) {
      return var0.a(var1, 0, 1114111) ? var0.d : null;
   }

   private boolean a(org.anti_ad.a.b.a.a.a.g var1, D var2, c var3, boolean var4, boolean var5, boolean var6) {
      if (var2.a instanceof an) {
         if (var2.c == null || var2.c.c()) {
            if (var2.c == null || var2.c.a()) {
               var3.a(var2);
               return true;
            }

            var3.a(new D(var2, var2.a, z.a));
            var4 = true;
         }

         if (var2.c != null && !var2.c.a()) {
            for (int var10 = 0; var10 < var2.c.b(); var10++) {
               if (var2.c.b(var10) != Integer.MAX_VALUE) {
                  af var11 = var2.c.a(var10);
                  k var13 = (k)super.b.a.get(var2.c.b(var10));
                  D var14 = new D(var2, var13, var11);
                  var4 = this.a(var1, var14, var3, var4, var5, var6);
               }
            }
         }

         return var4;
      } else {
         if (!var2.a.d && (!var4 || !var2.d())) {
            var3.a(var2);
         }

         k var7 = var2.a;

         for (int var8 = 0; var8 < var7.a(); var8++) {
            aC var9 = var7.a(var8);
            D var12;
            if ((var12 = this.a(var1, var2, var9, var3, var5, var6)) != null) {
               var4 = this.a(var1, var12, var3, var4, var5, var6);
            }
         }

         return var4;
      }
   }

   private D a(org.anti_ad.a.b.a.a.a.g var1, D var2, aC var3, c var4, boolean var5, boolean var6) {
      D var7 = null;
      switch (var3.a()) {
         case 1:
            var7 = new D(var2, var3.d);
            break;
         case 2:
         case 5:
         case 7:
            if (var6 && var3.a(-1, 0, 1114111)) {
               var7 = new D(var2, var3.d);
            }
            break;
         case 3:
            ao var9 = (ao)var3;
            ax var10 = ax.a(var2.c, var9.b.b);
            var7 = new D(var2, var3.d, var10);
            break;
         case 4:
            ae var11 = (ae)var3;
            var4.d = true;
            if (this.a(var1, var11.a, var11.b, var5)) {
               var7 = new D(var2, var3.d);
            }
            break;
         case 6:
            if (var2.c != null && !var2.c.c()) {
               var7 = new D(var2, var3.d);
            } else {
               H var8 = H.a(var2.c(), super.b.h[((n)var3).b]);
               var7 = new D(var2, var3.d, var8);
            }
         case 8:
         case 9:
         default:
            break;
         case 10:
            throw new UnsupportedOperationException("Precedence predicates are not supported in lexers.");
      }

      return var7;
   }

   private boolean a(org.anti_ad.a.b.a.a.a.g var1, int var2, int var3, boolean var4) {
      if (this.d == null) {
         return true;
      }

      if (!var4) {
         return this.d.sempred(null, var2, var3);
      }

      var4 = this.g;
      int var5 = this.f;
      int var6 = var1.b();

      try {
         this.b(var1);
         return this.d.sempred(null, var2, var3);
      } finally {
         this.g = var4;
         this.f = var5;
         var1.b(var6);
      }
   }

   private void a(F var1, org.anti_ad.a.b.a.a.a.g var2, org.anti_ad.a.b.a.a.a.b.d var3) {
      var1.a = var2.b();
      var1.b = this.f;
      var1.c = this.g;
      var1.d = var3;
   }

   private org.anti_ad.a.b.a.a.a.b.d a(org.anti_ad.a.b.a.a.a.b.d var1, int var2, V var3) {
      boolean var4 = var3.d;
      var3.d = false;
      org.anti_ad.a.b.a.a.a.b.d var5 = this.a(var3);
      if (var4) {
         return var5;
      }

      a(var1, var2, var5);
      return var5;
   }

   private static void a(org.anti_ad.a.b.a.a.a.b.d var0, int var1, org.anti_ad.a.b.a.a.a.b.d var2) {
      if (var1 >= 0 && var1 <= 127) {
         synchronized (var0) {
            if (var0.c == null) {
               var0.c = new org.anti_ad.a.b.a.a.a.b.d[128];
            }

            var0.c[var1] = var2;
         }
      }
   }

   private org.anti_ad.a.b.a.a.a.b.d a(V var1) {
      assert !var1.d;
      org.anti_ad.a.b.a.a.a.b.d var2 = new org.anti_ad.a.b.a.a.a.b.d(var1);
      b var3 = null;
      Iterator var4 = var1.iterator();

      while (var4.hasNext()) {
         b var5;
         if ((var5 = (b)var4.next()).a instanceof an) {
            var3 = var5;
            break;
         }
      }

      if (var3 != null) {
         var2.d = true;
         var2.f = ((D)var3).c();
         var2.e = super.b.g[var3.a.c];
      }

      org.anti_ad.a.b.a.a.a.b.a var8;
      synchronized ((var8 = this.h[this.i]).a) {
         org.anti_ad.a.b.a.a.a.b.d var7;
         if ((var7 = (org.anti_ad.a.b.a.a.a.b.d)var8.a.get(var2)) != null) {
            return var7;
         }

         var2.a = var8.a.size();
         var1.c();
         var2.b = var1;
         var8.a.put(var2, var2);
         return var2;
      }
   }

   public final String a(org.anti_ad.a.b.a.a.a.g var1) {
      return var1.a(org.anti_ad.a.b.a.a.a.c.k.a(this.e, var1.b() - 1));
   }

   public final int c() {
      return this.f;
   }

   public final void a(int var1) {
      this.f = var1;
   }

   public final int d() {
      return this.g;
   }

   public final void b(int var1) {
      this.g = var1;
   }

   public final void b(org.anti_ad.a.b.a.a.a.g var1) {
      if (var1.a(1) == 10) {
         this.f++;
         this.g = 0;
      } else {
         this.g++;
      }

      var1.a();
   }
}
