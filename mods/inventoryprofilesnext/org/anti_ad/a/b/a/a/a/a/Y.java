package org.anti_ad.a.b.a.a.a.a;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Y extends j {
   public static boolean d = false;
   private static boolean h = Boolean.parseBoolean(a("TURN_OFF_LR_LOOP_ENTRY_BRANCH_OPT"));
   private org.anti_ad.a.b.a.a.a.D i;
   public final org.anti_ad.a.b.a.a.a.b.a[] e;
   private int j = ah.b;
   private org.anti_ad.a.b.a.a.a.c.d k;
   protected org.anti_ad.a.b.a.a.a.R f;
   protected int g;
   private org.anti_ad.a.b.a.a.a.H l;
   private org.anti_ad.a.b.a.a.a.b.a m;

   public Y(org.anti_ad.a.b.a.a.a.D var1, a var2, org.anti_ad.a.b.a.a.a.b.a[] var3, ag var4) {
      super(var2, var4);
      this.i = var1;
      this.e = var3;
   }

   @Override
   public final void a() {
   }

   public int a(org.anti_ad.a.b.a.a.a.R var1, int var2, org.anti_ad.a.b.a.a.a.H var3) {
      this.f = var1;
      this.g = var1.b();
      this.l = var3;
      org.anti_ad.a.b.a.a.a.b.a var18 = this.e[var2];
      this.m = var18;
      int var4 = this.g;

      try {
         org.anti_ad.a.b.a.a.a.b.d var5;
         if (var18.a()) {
            int var7 = this.i.getPrecedence();
            org.anti_ad.a.b.a.a.a.b.a var6 = var18;
            if (!var18.a()) {
               throw new IllegalStateException("Only precedence DFAs may contain a precedence start state.");
            }

            var5 = var7 >= 0 && var7 < var6.b.c.length ? var6.b.c[var7] : null;
         } else {
            var5 = var18.b;
         }

         if (var5 == null) {
            if (var3 == null) {
               var3 = org.anti_ad.a.b.a.a.a.H.EMPTY;
            }

            c var27 = this.a(var18.d, org.anti_ad.a.b.a.a.a.H.EMPTY, false);
            if (var18.a()) {
               var18.b.b = var27;
               c var38 = var27;
               Y var33 = this;
               HashMap var8 = new HashMap();
               c var9 = new c(var38.f);
               Iterator var28 = var38.iterator();

               while (var28.hasNext()) {
                  b var10;
                  ap var11;
                  if ((var10 = (b)var28.next()).b == 1 && (var11 = var10.e.b(var33.i, var33.l)) != null) {
                     var8.put(var10.a.b, var10.c);
                     if (var11 != var10.e) {
                        var9.a(new b(var10, var11), var33.k);
                     } else {
                        var9.a(var10, var33.k);
                     }
                  }
               }

               Iterator var29 = var38.iterator();

               while (var29.hasNext()) {
                  b var52;
                  af var55;
                  if ((var52 = (b)var29.next()).b != 1 && (var52.a() || (var55 = (af)var8.get(var52.a.b)) == null || !var55.equals(var52.c))) {
                     var9.a(var52, var33.k);
                  }
               }

               c var30 = var9;
               var5 = this.a(var18, new org.anti_ad.a.b.a.a.a.b.d(var30));
               int var10001 = this.i.getPrecedence();
               org.anti_ad.a.b.a.a.a.b.d var42 = var5;
               int var39 = var10001;
               org.anti_ad.a.b.a.a.a.b.a var34 = var18;
               if (!var18.a()) {
                  throw new IllegalStateException("Only precedence DFAs may contain a precedence start state.");
               }

               if (var39 >= 0) {
                  synchronized (var34.b) {
                     if (var39 >= var34.b.c.length) {
                        var34.b.c = Arrays.copyOf(var34.b.c, var39 + 1);
                     }

                     var34.b.c[var39] = var42;
                  }
               }
            } else {
               var5 = this.a(var18, new org.anti_ad.a.b.a.a.a.b.d(var27));
               var18.b = var5;
            }
         }

         org.anti_ad.a.b.a.a.a.H var53 = var3;
         int var31 = var4;
         org.anti_ad.a.b.a.a.a.R var50 = var1;
         org.anti_ad.a.b.a.a.a.b.d var43 = var5;
         org.anti_ad.a.b.a.a.a.b.a var40 = var18;
         Y var35 = this;
         org.anti_ad.a.b.a.a.a.b.d var56 = var43;
         var2 = var50.a(1);

         int var10000;
         while (true) {
            org.anti_ad.a.b.a.a.a.b.d var25;
            if ((var25 = var35.a(var56, var2)) == null) {
               var25 = var35.a(var40, var56, var2);
            }

            if (var25 == a) {
               org.anti_ad.a.b.a.a.a.C var24 = var35.a(var50, var53, var56.b, var31);
               var50.b(var31);
               int var48;
               if ((var48 = var35.a(var56.b, var53)) == 0) {
                  throw var24;
               }

               var10000 = var48;
               break;
            }

            if (var25.g && var35.j != ah.a) {
               BitSet var21 = var25.b.c;
               if (var25.h != null) {
                  int var45;
                  if ((var45 = var50.b()) != var31) {
                     var50.b(var31);
                  }

                  if ((var21 = var35.a(var25.h, var53)).cardinality() == 1) {
                     var10000 = var21.nextSetBit(0);
                     break;
                  }

                  if (var45 != var31) {
                     var50.b(var45);
                  }
               }

               c var46 = var35.a(var40.d, var53, true);
               var35.a(var40, var21, var25.b, var31, var50.b());
               org.anti_ad.a.b.a.a.a.H var51 = var53;
               int var47 = var31;
               org.anti_ad.a.b.a.a.a.R var41 = var50;
               c var36 = var46;
               var5 = var25;
               org.anti_ad.a.b.a.a.a.b.a var26 = var40;
               Y var22 = var35;
               boolean var54 = false;
               c var57 = var36;
               var41.b(var47);
               int var12 = var41.a(1);

               while (true) {
                  if ((var37 = var22.a(var57, var12, true)) == null) {
                     org.anti_ad.a.b.a.a.a.C var59 = var22.a(var41, var51, var57, var47);
                     var41.b(var47);
                     if ((var2 = var22.a(var57, var51)) == 0) {
                        throw var59;
                     }

                     return var2;
                  }

                  Collection var13 = ah.c(var37);
                  var37.b = b(var37);
                  if (var37.b != 0) {
                     var58 = var37.b;
                     break;
                  }

                  if (var22.j != ah.c) {
                     if ((var58 = ah.a(var13)) != 0) {
                        break;
                     }
                  } else if (ah.b(var13) && ah.c(var13)) {
                     var54 = true;
                     var58 = ah.e(var13);
                     break;
                  }

                  var57 = var37;
                  if (var12 != -1) {
                     var41.a();
                     var12 = var41.a(1);
                  }
               }

               if (var37.b != 0) {
                  var22.a(var26, var58, var37, var47, var41.b());
                  var10000 = var58;
               } else {
                  var22.a(var26, var5, var47, var41.b(), var54, var37.a(), var37);
                  var10000 = var58;
               }
               break;
            }

            if (var25.d) {
               if (var25.h == null) {
                  var10000 = var25.e;
               } else {
                  var2 = var50.b();
                  var50.b(var31);
                  BitSet var44;
                  switch ((var44 = var35.a(var25.h, var53)).cardinality()) {
                     case 0:
                        throw var35.a(var50, var53, var25.b, var31);
                     case 1:
                        return var44.nextSetBit(0);
                     default:
                        var35.a(var40, var25, var31, var2, false, var44, var25.b);
                        var10000 = var44.nextSetBit(0);
                  }
               }
               break;
            }

            var56 = var25;
            if (var2 != -1) {
               var50.a();
               var2 = var50.a(1);
            }
         }

         return var10000;
      } finally {
         this.k = null;
         this.m = null;
         var1.b(var4);
      }
   }

   protected org.anti_ad.a.b.a.a.a.b.d a(org.anti_ad.a.b.a.a.a.b.d var1, int var2) {
      org.anti_ad.a.b.a.a.a.b.d[] var3;
      return (var3 = var1.c) != null && var2 + 1 >= 0 && var2 + 1 < var3.length ? var3[var2 + 1] : null;
   }

   protected org.anti_ad.a.b.a.a.a.b.d a(org.anti_ad.a.b.a.a.a.b.a var1, org.anti_ad.a.b.a.a.a.b.d var2, int var3) {
      c var4;
      if ((var4 = this.a(var2.b, var3, false)) == null) {
         this.a(var1, var2, var3, a);
         return a;
      }

      org.anti_ad.a.b.a.a.a.b.d var5 = new org.anti_ad.a.b.a.a.a.b.d(var4);
      int var6;
      if ((var6 = b(var4)) != 0) {
         var5.d = true;
         var5.b.b = var6;
         var5.e = var6;
      } else if (ah.a(this.j, var4)) {
         var5.b.c = ah.d(ah.c(var4));
         var5.g = true;
         var5.d = true;
         var5.e = var5.b.c.nextSetBit(0);
      }

      if (var5.d && var5.b.d) {
         y var7 = super.b.a(var1.c);
         org.anti_ad.a.b.a.a.a.b.d var16 = var5;
         int var13 = var7.a();
         c var8 = var16.b;
         BitSet var9;
         if (var16.b.b != 0) {
            (var9 = new BitSet()).set(var8.b);
         } else {
            var9 = var8.c;
         }

         BitSet var17;
         BitSet var10000 = var17 = var9;
         int var21 = var13;
         var4 = var16.b;
         BitSet var19 = var10000;
         ap[] var10 = new ap[var21 + 1];

         for (b var12 : var4) {
            if (var19.get(var12.b)) {
               var10[var12.b] = ap.b(var10[var12.b], var12.e);
            }
         }

         int var23 = 0;

         for (int var25 = 1; var25 <= var21; var25++) {
            if (var10[var25] == null) {
               var10[var25] = ar.a;
            } else if (var10[var25] != ar.a) {
               var23++;
            }
         }

         if (var23 == 0) {
            var10 = null;
         }

         ap[] var15 = var10;
         if (var10 == null) {
            var16.e = var17.nextSetBit(0);
         } else {
            BitSet var20 = var17;
            ArrayList var18 = new ArrayList();
            boolean var22 = false;

            for (int var24 = 1; var24 < var15.length; var24++) {
               ap var26 = var15[var24];
               assert var26 != null;
               if (var20 != null && var20.get(var24)) {
                  var18.add(new org.anti_ad.a.b.a.a.a.b.e(var26, var24));
               }

               if (var26 != ar.a) {
                  var22 = true;
               }
            }

            var16.h = !var22 ? null : var18.toArray(new org.anti_ad.a.b.a.a.a.b.e[0]);
            var16.e = 0;
         }

         if (var5.h != null) {
            var5.e = 0;
         }
      }

      return this.a(var1, var2, var3, var5);
   }

   protected c a(c var1, int var2, boolean var3) {
      if (this.k == null) {
         this.k = new org.anti_ad.a.b.a.a.a.c.d();
      }

      c var4 = new c(var3);
      ArrayList var5 = null;
      Iterator var12 = var1.iterator();

      while (var12.hasNext()) {
         b var6;
         if ((var6 = (b)var12.next()).a instanceof an) {
            assert var6.c.a();
            if (var3 || var2 == -1) {
               if (var5 == null) {
                  var5 = new ArrayList();
               }

               var5.add(var6);
            }
         } else {
            int var7 = var6.a.a();

            for (int var8 = 0; var8 < var7; var8++) {
               aC var9 = var6.a.a(var8);
               int var11 = var2;
               aC var10 = var9;
               Y var23 = this;
               k var24;
               if ((var24 = var10.a(var11, 0, var23.b.f) ? var10.d : null) != null) {
                  var4.a(new b(var6, var24), this.k);
               }
            }
         }
      }

      var1 = null;
      if (var5 == null && var2 != -1) {
         if (var4.size() == 1) {
            var1 = var4;
         } else if (b(var4) != 0) {
            var1 = var4;
         }
      }

      if (var1 == null) {
         var1 = new c(var3);
         HashSet var17 = new HashSet();
         boolean var20 = var2 == -1;

         for (b var25 : var4) {
            this.a(var25, var1, var17, false, var3, var20);
         }
      }

      if (var2 == -1) {
         boolean var28 = var1 == var4;
         c var27 = var1;
         Y var26 = this;
         c var10000;
         if (ah.b(var27)) {
            var10000 = var27;
         } else {
            var1 = new c(var27.f);
            Iterator var15 = var27.iterator();

            while (var15.hasNext()) {
               b var16;
               if ((var16 = (b)var15.next()).a instanceof an) {
                  var1.a(var16, var26.k);
               } else if (var28 && var16.a.d && var26.b.a(var16.a).c(-2)) {
                  an var18 = var26.b.d[var16.a.c];
                  var1.a(new b(var16, var18), var26.k);
               }
            }

            var10000 = var1;
         }

         var1 = var10000;
      }

      if (var5 != null && (!var3 || !ah.a(var1))) {
         assert !var5.isEmpty();

         for (b var21 : var5) {
            var1.a(var21, this.k);
         }
      }

      return var1.isEmpty() ? null : var1;
   }

   private c a(y var1, org.anti_ad.a.b.a.a.a.H var2, boolean var3) {
      ax var8 = af.a(super.b, var2);
      c var4 = new c(var3);

      for (int var5 = 0; var5 < var1.a(); var5++) {
         k var6 = var1.a(var5).d;
         b var9 = new b(var6, var5 + 1, var8);
         HashSet var7 = new HashSet();
         this.a(var9, var4, var7, true, var3, false);
      }

      return var4;
   }

   private int a(c var1, org.anti_ad.a.b.a.a.a.H var2) {
      org.anti_ad.a.b.a.a.a.c.o var3;
      c var5 = (c)(var3 = this.b(var1, var2)).a;
      var1 = (c)var3.b;
      int var6;
      if ((var6 = a(var5)) != 0) {
         return var6;
      }

      int var7;
      return var1.size() > 0 && (var7 = a(var1)) != 0 ? var7 : 0;
   }

   private static int a(c var0) {
      org.anti_ad.a.b.a.a.a.c.l var1 = new org.anti_ad.a.b.a.a.a.c.l();
      Iterator var3 = var0.iterator();

      while (var3.hasNext()) {
         b var2;
         if (((var2 = (b)var3.next()).d & -1073741825) > 0 || var2.a instanceof an && var2.c.c()) {
            var1.b(var2.b);
         }
      }

      return var1.d() == 0 ? 0 : var1.c();
   }

   private org.anti_ad.a.b.a.a.a.c.o b(c var1, org.anti_ad.a.b.a.a.a.H var2) {
      c var3 = new c(var1.f);
      c var4 = new c(var1.f);
      Iterator var5 = var1.iterator();

      while (var5.hasNext()) {
         b var6;
         if ((var6 = (b)var5.next()).e != ar.a) {
            if (this.a(var6.e, var2, var6.b, var1.f)) {
               var3.a(var6);
            } else {
               var4.a(var6);
            }
         } else {
            var3.a(var6);
         }
      }

      return new org.anti_ad.a.b.a.a.a.c.o(var3, var4);
   }

   private BitSet a(org.anti_ad.a.b.a.a.a.b.e[] var1, org.anti_ad.a.b.a.a.a.H var2) {
      BitSet var3 = new BitSet();
      int var4 = var1.length;

      for (int var5 = 0; var5 < var4; var5++) {
         org.anti_ad.a.b.a.a.a.b.e var6;
         if ((var6 = var1[var5]).a == ar.a) {
            var3.set(var6.b);
         } else if (this.a(var6.a, var2, var6.b, false)) {
            var3.set(var6.b);
         }
      }

      return var3;
   }

   protected boolean a(ap var1, org.anti_ad.a.b.a.a.a.H var2, int var3, boolean var4) {
      return var1.a(this.i, var2);
   }

   private void a(b var1, c var2, Set var3, boolean var4, boolean var5, boolean var6) {
      this.a(var1, var2, var3, var4, var5, 0, var6);
      assert !var5 || !var2.e;
   }

   private void a(b var1, c var2, Set var3, boolean var4, boolean var5, int var6, boolean var7) {
      if (var1.a instanceof an) {
         if (!var1.c.a()) {
            for (int var8 = 0; var8 < var1.c.b(); var8++) {
               if (var1.c.b(var8) == Integer.MAX_VALUE) {
                  if (var5) {
                     var2.a(new b(var1, var1.a, z.a), this.k);
                  } else {
                     this.b(var1, var2, var3, var4, var5, var6, var7);
                  }
               } else {
                  k var9 = (k)super.b.a.get(var1.c.b(var8));
                  af var10 = var1.c.a(var8);
                  b var11;
                  (var11 = new b(var9, var1.b, var10, var1.e)).d = var1.d;
                  assert var6 > Integer.MIN_VALUE;
                  this.a(var11, var2, var3, var4, var5, var6 - 1, var7);
               }
            }

            return;
         }

         if (var5) {
            var2.a(var1, this.k);
            return;
         }
      }

      this.b(var1, var2, var3, var4, var5, var6, var7);
   }

   private void b(b var1, c var2, Set var3, boolean var4, boolean var5, int var6, boolean var7) {
      k var8 = var1.a;
      if (!var1.a.d) {
         var2.a(var1, this.k);
      }

      for (int var9 = 0; var9 < var8.a(); var9++) {
         if (var9 != 0 || !this.a(var1)) {
            aC var10;
            boolean var11 = !((var10 = var8.a(var9)) instanceof n) && var4;
            b var12;
            if ((var12 = this.a(var1, var10, var11, var6 == 0, var5, var7)) != null) {
               int var13 = var6;
               if (var1.a instanceof an) {
                  assert !var5;
                  if (this.m != null && this.m.a() && ((A)var10).d() == this.m.d.c) {
                     var12.b();
                  }

                  var12.d++;
                  if (!var3.add(var12)) {
                     continue;
                  }

                  var2.e = true;
                  assert var13 > Integer.MIN_VALUE;
                  var13--;
               } else {
                  if (!var10.b() && !var3.add(var12)) {
                     continue;
                  }

                  if (var10 instanceof ao && var13 >= 0) {
                     var13++;
                  }
               }

               this.a(var12, var2, var3, var11, var5, var13, var7);
            }
         }
      }
   }

   private boolean a(b var1) {
      if (h) {
         return false;
      }

      k var2 = var1.a;
      if (var1.a.b() == 10 && ((az)var2).j && !var1.c.a() && !var1.c.c()) {
         int var3 = var1.c.b();

         for (int var4 = 0; var4 < var3; var4++) {
            if (((k)super.b.a.get(var1.c.b(var4))).c != var2.c) {
               return false;
            }
         }

         int var5 = ((u)var2.a(0).d).g.b;
         t var8 = (t)super.b.a.get(var5);
         var5 = 0;

         while (var5 < var3) {
            int var6 = var1.c.b(var5);
            k var10;
            if ((var10 = (k)super.b.a.get(var6)).a() == 1 && var10.a(0).b()) {
               k var7 = var10.a(0).d;
               if (var10.b() == 8 && var7 == var2 || var10 == var8 || var7 == var8 || var7.b() == 8 && var7.a() == 1 && var7.a(0).b() && var7.a(0).d == var2) {
                  var5++;
                  continue;
               }

               return false;
            }

            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   private b a(b var1, aC var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      switch (var2.a()) {
         case 1:
            return new b(var1, var2.d);
         case 2:
         case 5:
         case 7:
            if (var6 && var2.a(-1, 0, 1)) {
               return new b(var1, var2.d);
            }

            return null;
         case 3:
            return a(var1, (ao)var2);
         case 4:
            return this.a(var1, (ae)var2, var3, var4, var5);
         case 6:
            return a(var1, (n)var2);
         case 8:
         case 9:
         default:
            return null;
         case 10:
            return this.a(var1, (ac)var2, var3, var4, var5);
      }
   }

   private static b a(b var0, n var1) {
      return new b(var0, var1.d);
   }

   private b a(b var1, ac var2, boolean var3, boolean var4, boolean var5) {
      b var6 = null;
      if (!var3 || !var4) {
         var6 = new b(var1, var2.d);
      } else if (var5) {
         var3 = this.f.b();
         this.f.b(this.g);
         var4 = this.a(var2.d(), this.l, var1.b, var5);
         this.f.b(var3);
         if (var4) {
            var6 = new b(var1, var2.d);
         }
      } else {
         ap var8 = ap.a(var1.e, var2.d());
         var6 = new b(var1, var2.d, var8);
      }

      return var6;
   }

   private b a(b var1, ae var2, boolean var3, boolean var4, boolean var5) {
      b var6 = null;
      if (var3 && (!var2.c || var2.c && var4)) {
         if (var5) {
            var3 = this.f.b();
            this.f.b(this.g);
            var4 = this.a(var2.d(), this.l, var1.b, var5);
            this.f.b(var3);
            if (var4) {
               var6 = new b(var1, var2.d);
            }
         } else {
            ap var8 = ap.a(var1.e, var2.d());
            var6 = new b(var1, var2.d, var8);
         }
      } else {
         var6 = new b(var1, var2.d);
      }

      return var6;
   }

   private static b a(b var0, ao var1) {
      k var2 = var1.b;
      ax var3 = ax.a(var0.c, var2.b);
      return new b(var0, var1.d, var3);
   }

   private org.anti_ad.a.b.a.a.a.C a(org.anti_ad.a.b.a.a.a.R var1, org.anti_ad.a.b.a.a.a.H var2, c var3, int var4) {
      return new org.anti_ad.a.b.a.a.a.C(this.i, var1, var1.d(var4), var1.f(1), var3, var2);
   }

   private static int b(c var0) {
      int var1 = 0;

      for (b var2 : var0) {
         if (var1 == 0) {
            var1 = var2.b;
         } else if (var2.b != var1) {
            return 0;
         }
      }

      return var1;
   }

   private org.anti_ad.a.b.a.a.a.b.d a(org.anti_ad.a.b.a.a.a.b.a var1, org.anti_ad.a.b.a.a.a.b.d var2, int var3, org.anti_ad.a.b.a.a.a.b.d var4) {
      if (var4 == null) {
         return null;
      }

      var4 = this.a(var1, var4);
      if (var2 != null && var3 >= -1 && var3 <= super.b.f) {
         synchronized (var2) {
            if (var2.c == null) {
               var2.c = new org.anti_ad.a.b.a.a.a.b.d[super.b.f + 1 + 1];
            }

            var2.c[var3 + 1] = var4;
            return var4;
         }
      } else {
         return var4;
      }
   }

   private org.anti_ad.a.b.a.a.a.b.d a(org.anti_ad.a.b.a.a.a.b.a var1, org.anti_ad.a.b.a.a.a.b.d var2) {
      if (var2 == a) {
         return var2;
      }

      synchronized (var1.a) {
         org.anti_ad.a.b.a.a.a.b.d var4;
         if ((var4 = (org.anti_ad.a.b.a.a.a.b.d)var1.a.get(var2)) != null) {
            return var4;
         }

         var2.a = var1.a.size();
         if (!var2.b.b()) {
            var2.b.a(this);
            var2.b.c();
         }

         var1.a.put(var2, var2);
         return var2;
      }
   }

   protected void a(org.anti_ad.a.b.a.a.a.b.a var1, BitSet var2, c var3, int var4, int var5) {
      if (this.i != null) {
         this.i.getErrorListenerDispatch().reportAttemptingFullContext(this.i, var1, var4, var5, var2, var3);
      }
   }

   protected void a(org.anti_ad.a.b.a.a.a.b.a var1, int var2, c var3, int var4, int var5) {
      if (this.i != null) {
         this.i.getErrorListenerDispatch().reportContextSensitivity(this.i, var1, var4, var5, var2, var3);
      }
   }

   protected void a(org.anti_ad.a.b.a.a.a.b.a var1, org.anti_ad.a.b.a.a.a.b.d var2, int var3, int var4, boolean var5, BitSet var6, c var7) {
      if (this.i != null) {
         this.i.getErrorListenerDispatch().reportAmbiguity(this.i, var1, var3, var4, var5, var6, var7);
      }
   }

   public final void a(int var1) {
      this.j = var1;
   }

   public final int c() {
      return this.j;
   }

   private static String a(String var0) {
      try {
         return AccessController.doPrivileged(new Z(var0));
      } catch (SecurityException var1) {
         return null;
      }
   }
}
