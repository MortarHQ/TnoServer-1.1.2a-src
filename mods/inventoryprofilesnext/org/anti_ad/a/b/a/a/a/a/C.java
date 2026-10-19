package org.anti_ad.a.b.a.a.a.a;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public final class C {
   private a a;

   public C(a var1) {
      this.a = var1;
   }

   public final org.anti_ad.a.b.a.a.a.c.l a(k var1, org.anti_ad.a.b.a.a.a.H var2) {
      org.anti_ad.a.b.a.a.a.H var3 = var2;
      k var6 = var1;
      C var5 = this;
      org.anti_ad.a.b.a.a.a.c.l var4 = new org.anti_ad.a.b.a.a.a.c.l();
      ax var7 = var3 != null ? af.a(var6.a, var3) : null;
      var5.a(var6, null, var7, var4, new HashSet(), new BitSet(), true, true);
      return var4;
   }

   private void a(k var1, k var2, af var3, org.anti_ad.a.b.a.a.a.c.l var4, Set var5, BitSet var6, boolean var7, boolean var8) {
      b var9 = new b(var1, 0, var3);
      if (var5.add(var9)) {
         if (var1 == var2) {
            if (var3 == null) {
               var4.b(-2);
               return;
            }

            if (var3.a() && var8) {
               var4.b(-1);
               return;
            }
         }

         if (var1 instanceof an) {
            if (var3 == null) {
               var4.b(-2);
               return;
            }

            if (var3.a() && var8) {
               var4.b(-1);
               return;
            }

            if (var3 != z.a) {
               boolean var20 = var6.get(var1.c);

               try {
                  var6.clear(var1.c);

                  for (int var21 = 0; var21 < var3.b(); var21++) {
                     k var22 = (k)this.a.a.get(var3.b(var21));
                     this.a(var22, var2, var3.a(var21), var4, var5, var6, var7, var8);
                  }
               } finally {
                  if (var20) {
                     var6.set(var1.c);
                  }
               }

               return;
            }
         }

         int var19 = var1.a();

         for (int var10 = 0; var10 < var19; var10++) {
            aC var11;
            if ((var11 = var1.a(var10)).getClass() == ao.class) {
               if (!var6.get(((ao)var11).d.c)) {
                  ax var12 = ax.a(var3, ((ao)var11).b.b);

                  try {
                     var6.set(((ao)var11).d.c);
                     this.a(var11.d, var2, var12, var4, var5, var6, var7, var8);
                  } finally {
                     var6.clear(((ao)var11).d.c);
                  }
               }
            } else if (var11 instanceof m) {
               if (var7) {
                  this.a(var11.d, var2, var3, var4, var5, var6, var7, var8);
               } else {
                  var4.b(0);
               }
            } else if (var11.b()) {
               this.a(var11.d, var2, var3, var4, var5, var6, var7, var8);
            } else if (var11.getClass() == aE.class) {
               var4.a(org.anti_ad.a.b.a.a.a.c.l.a(1, this.a.f));
            } else {
               org.anti_ad.a.b.a.a.a.c.l var23;
               if ((var23 = var11.c()) != null) {
                  if (var11 instanceof U) {
                     var23 = var23.b(org.anti_ad.a.b.a.a.a.c.l.a(1, this.a.f));
                  }

                  var4.a(var23);
               }
            }
         }
      }
   }
}
