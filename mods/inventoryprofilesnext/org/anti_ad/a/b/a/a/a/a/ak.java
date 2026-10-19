package org.anti_ad.a.b.a.a.a.a;

import java.util.BitSet;

public final class ak extends Y {
   private x[] h;
   private int i = this.b.b.size();
   private int j;
   private int k;
   private int l;
   private org.anti_ad.a.b.a.a.a.b.d m;
   private int n;

   public ak(org.anti_ad.a.b.a.a.a.D var1) {
      super(var1, ((Y)var1.getInterpreter()).b, ((Y)var1.getInterpreter()).e, ((Y)var1.getInterpreter()).c);
      this.h = new x[this.i];

      for (int var2 = 0; var2 < this.i; var2++) {
         this.h[var2] = new x(var2);
      }
   }

   @Override
   public final int a(org.anti_ad.a.b.a.a.a.R var1, int var2, org.anti_ad.a.b.a.a.a.H var3) {
      try {
         this.j = -1;
         this.k = -1;
         this.l = var2;
         long var4 = System.nanoTime();
         int var10 = super.a(var1, var2, var3);
         long var6 = System.nanoTime();
         this.h[var2].b += var6 - var4;
         this.h[var2].a++;
         int var11 = this.j - super.g + 1;
         this.h[var2].c += var11;
         this.h[var2].d = this.h[var2].d == 0L ? var11 : Math.min(this.h[var2].d, var11);
         if (var11 > this.h[var2].e) {
            this.h[var2].e = var11;
            this.h[var2].f = new S(var2, var10, var1, super.g, this.j, false);
         }

         if (this.k >= 0) {
            int var12 = this.k - super.g + 1;
            this.h[var2].g += var12;
            this.h[var2].h = this.h[var2].h == 0L ? var12 : Math.min(this.h[var2].h, var12);
            if (var12 > this.h[var2].i) {
               this.h[var2].i = var12;
               this.h[var2].j = new S(var2, var10, var1, super.g, this.k, true);
            }
         }

         return var10;
      } finally {
         this.l = -1;
      }
   }

   @Override
   protected final org.anti_ad.a.b.a.a.a.b.d a(org.anti_ad.a.b.a.a.a.b.d var1, int var2) {
      this.j = super.f.b();
      org.anti_ad.a.b.a.a.a.b.d var3;
      if ((var3 = super.a(var1, var2)) != null) {
         this.h[this.l].p++;
         if (var3 == a) {
            this.h[this.l].l.add(new B(this.l, var1.b, super.f, super.g, this.j, false));
         }
      }

      this.m = var3;
      return var3;
   }

   @Override
   protected final org.anti_ad.a.b.a.a.a.b.d a(org.anti_ad.a.b.a.a.a.b.a var1, org.anti_ad.a.b.a.a.a.b.d var2, int var3) {
      org.anti_ad.a.b.a.a.a.b.d var4 = super.a(var1, var2, var3);
      this.m = var4;
      return var4;
   }

   @Override
   protected final c a(c var1, int var2, boolean var3) {
      if (var3) {
         this.k = super.f.b();
      }

      c var4 = super.a(var1, var2, var3);
      if (var3) {
         this.h[this.l].r++;
         if (var4 == null) {
            this.h[this.l].l.add(new B(this.l, var1, super.f, super.g, this.k, true));
         }
      } else {
         this.h[this.l].o++;
         if (var4 == null) {
            this.h[this.l].l.add(new B(this.l, var1, super.f, super.g, this.j, false));
         }
      }

      return var4;
   }

   @Override
   protected final boolean a(ap var1, org.anti_ad.a.b.a.a.a.H var2, int var3, boolean var4) {
      boolean var6 = super.a(var1, var2, var3, var4);
      if (!(var1 instanceof au)) {
         int var5 = this.k >= 0 ? this.k : this.j;
         this.h[this.l].n.add(new ad(this.l, super.f, super.g, var5, var1, var6, var3, var4));
      }

      return var6;
   }

   @Override
   protected final void a(org.anti_ad.a.b.a.a.a.b.a var1, BitSet var2, c var3, int var4, int var5) {
      if (var2 != null) {
         this.n = var2.nextSetBit(0);
      } else {
         this.n = var3.a().nextSetBit(0);
      }

      this.h[this.l].q++;
      super.a(var1, var2, var3, var4, var5);
   }

   @Override
   protected final void a(org.anti_ad.a.b.a.a.a.b.a var1, int var2, c var3, int var4, int var5) {
      if (var2 != this.n) {
         this.h[this.l].k.add(new v(this.l, var3, super.f, var4, var5));
      }

      super.a(var1, var2, var3, var4, var5);
   }

   @Override
   protected final void a(org.anti_ad.a.b.a.a.a.b.a var1, org.anti_ad.a.b.a.a.a.b.d var2, int var3, int var4, boolean var5, BitSet var6, c var7) {
      int var8;
      if (var6 != null) {
         var8 = var6.nextSetBit(0);
      } else {
         var8 = var7.a().nextSetBit(0);
      }

      if (var7.f && var8 != this.n) {
         this.h[this.l].k.add(new v(this.l, var7, super.f, var3, var4));
      }

      this.h[this.l].m.add(new o(this.l, var7, var6, super.f, var3, var4, var7.f));
      super.a(var1, var2, var3, var4, var5, var6, var7);
   }
}
