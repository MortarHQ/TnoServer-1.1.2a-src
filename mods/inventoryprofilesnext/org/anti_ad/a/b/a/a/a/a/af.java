package org.anti_ad.a.b.a.a.a.a;

import java.util.Arrays;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class af {
   private static final AtomicInteger a = new AtomicInteger();
   private int b = a.getAndIncrement();
   private int c;

   protected af(int var1) {
      this.c = var1;
   }

   public static ax a(a var0, org.anti_ad.a.b.a.a.a.M var1) {
      if (var1 == null) {
         var1 = org.anti_ad.a.b.a.a.a.H.EMPTY;
      }

      if (var1.parent != null && var1 != org.anti_ad.a.b.a.a.a.H.EMPTY) {
         ax var2 = a(var0, var1.parent);
         ao var3 = (ao)((k)var0.a.get(var1.invokingState)).a(0);
         return ax.a(var2, var3.b.b);
      } else {
         return z.a;
      }
   }

   public abstract int b();

   public abstract af a(int var1);

   public abstract int b(int var1);

   public boolean a() {
      return this == z.a;
   }

   public final boolean c() {
      return this.b(this.b() - 1) == Integer.MAX_VALUE;
   }

   @Override
   public final int hashCode() {
      return this.c;
   }

   @Override
   public abstract boolean equals(Object var1);

   protected static int a(af[] var0, int[] var1) {
      int var2 = 1;
      af[] var3 = var0;
      int var4 = var0.length;

      for (int var5 = 0; var5 < var4; var5++) {
         af var6 = var3[var5];
         var2 = org.anti_ad.a.b.a.a.a.c.m.a(var2, var6);
      }

      int[] var7 = var1;
      var4 = var1.length;

      for (int var9 = 0; var9 < var4; var9++) {
         int var10 = var7[var9];
         var2 = org.anti_ad.a.b.a.a.a.c.m.a(var2, var10);
      }

      return org.anti_ad.a.b.a.a.a.c.m.b(var2, 2 * var0.length);
   }

   public static af a(af var0, af var1, boolean var2, org.anti_ad.a.b.a.a.a.c.d var3) {
      assert var0 != null && var1 != null;
      if (var0 == var1 || var0.equals(var1)) {
         return var0;
      }

      if (var0 instanceof ax && var1 instanceof ax) {
         ax var49 = var0;
         var1 = var1;
         var0 = var49;
         if (var3 != null) {
            af var22;
            if ((var22 = (af)var3.a(var0, var1)) != null) {
               return var22;
            }

            if ((var22 = (af)var3.a(var1, var0)) != null) {
               return var22;
            }
         }

         label205: {
            boolean var44 = var2;
            ax var43 = var1;
            ax var42 = var0;
            if (var44) {
               if (var42 == z.a) {
                  var49 = z.a;
                  break label205;
               }

               if (var43 == z.a) {
                  var49 = z.a;
                  break label205;
               }
            } else {
               if (var42 == z.a && var43 == z.a) {
                  var49 = z.a;
                  break label205;
               }

               if (var42 == z.a) {
                  int[] var46 = new int[]{var43.c, Integer.MAX_VALUE};
                  af[] var48 = new af[]{var43.b, null};
                  var49 = new p(var48, var46);
                  break label205;
               }

               if (var43 == z.a) {
                  int[] var45 = new int[]{var42.c, Integer.MAX_VALUE};
                  af[] var47 = new af[]{var42.b, null};
                  var49 = new p(var47, var45);
                  break label205;
               }
            }

            var49 = null;
         }

         af var24 = var49;
         if (var49 != null) {
            if (var3 != null) {
               var3.a(var0, var1, var24);
            }

            return var24;
         } else if (var0.c != var1.c) {
            af var28 = null;
            if (var0 == var1 || var0.b != null && var0.b.equals(var1.b)) {
               var28 = var0.b;
            }

            if (var28 != null) {
               int[] var32 = new int[]{var0.c, var1.c};
               if (var0.c > var1.c) {
                  var32[0] = var1.c;
                  var32[1] = var0.c;
               }

               af[] var34 = new af[]{var28, var28};
               p var36 = new p(var34, var32);
               if (var3 != null) {
                  var3.a(var0, var1, var36);
               }

               return var36;
            } else {
               int[] var31 = new int[]{var0.c, var1.c};
               af[] var33 = new af[]{var0.b, var1.b};
               if (var0.c > var1.c) {
                  var31[0] = var1.c;
                  var31[1] = var0.c;
                  var33 = new af[]{var1.b, var0.b};
               }

               p var35 = new p(var33, var31);
               if (var3 != null) {
                  var3.a(var0, var1, var35);
               }

               return var35;
            }
         } else {
            af var27;
            if ((var27 = a(var0.b, var1.b, var2, var3)) == var0.b) {
               return var0;
            }

            if (var27 == var1.b) {
               return var1;
            }

            ax var30 = ax.a(var27, var0.c);
            if (var3 != null) {
               var3.a(var0, var1, var30);
            }

            return var30;
         }
      } else {
         if (var2) {
            if (var0 instanceof z) {
               return var0;
            }

            if (var1 instanceof z) {
               return var1;
            }
         }

         if (var0 instanceof ax) {
            var0 = new p(var0);
         }

         if (var1 instanceof ax) {
            var1 = new p(var1);
         }

         p var10000 = (p)var0;
         p var16 = (p)var1;
         p var14 = var10000;
         if (var3 != null) {
            af var4;
            if ((var4 = (af)var3.a(var14, var16)) != null) {
               return var4;
            }

            if ((var4 = (af)var3.a(var16, var14)) != null) {
               return var4;
            }
         }

         int var20 = 0;
         int var5 = 0;
         int var6 = 0;
         int[] var7 = new int[var14.b.length + var16.b.length];
         af[] var8 = new af[var14.b.length + var16.b.length];

         while (var20 < var14.b.length && var5 < var16.b.length) {
            af var9 = var14.a[var20];
            af var10 = var16.a[var5];
            if (var14.b[var20] == var16.b[var5]) {
               int var11;
               boolean var12 = (var11 = var14.b[var20]) == Integer.MAX_VALUE && var9 == null && var10 == null;
               boolean var13 = var9 != null && var10 != null && var9.equals(var10);
               if (!var12 && !var13) {
                  var9 = a(var9, var10, var2, var3);
                  var8[var6] = var9;
                  var7[var6] = var11;
               } else {
                  var8[var6] = var9;
                  var7[var6] = var11;
               }

               var20++;
               var5++;
            } else if (var14.b[var20] < var16.b[var5]) {
               var8[var6] = var9;
               var7[var6] = var14.b[var20];
               var20++;
            } else {
               var8[var6] = var10;
               var7[var6] = var16.b[var5];
               var5++;
            }

            var6++;
         }

         if (var20 < var14.b.length) {
            for (int var38 = var20; var38 < var14.b.length; var38++) {
               var8[var6] = var14.a[var38];
               var7[var6] = var14.b[var38];
               var6++;
            }
         } else {
            for (int var39 = var5; var39 < var16.b.length; var39++) {
               var8[var6] = var16.a[var39];
               var7[var6] = var16.b[var39];
               var6++;
            }
         }

         if (var6 < var8.length) {
            if (var6 == 1) {
               ax var41 = ax.a(var8[0], var7[0]);
               if (var3 != null) {
                  var3.a(var14, var16, var41);
               }

               return var41;
            }

            var8 = Arrays.copyOf(var8, var6);
            var7 = Arrays.copyOf(var7, var6);
         }

         p var40;
         if ((var40 = new p(var8, var7)).equals(var14)) {
            if (var3 != null) {
               var3.a(var14, var16, var14);
            }

            return var14;
         } else if (var40.equals(var16)) {
            if (var3 != null) {
               var3.a(var14, var16, var16);
            }

            return var16;
         } else {
            af[] var18 = var8;
            HashMap var21 = new HashMap();

            for (int var25 = 0; var25 < var18.length; var25++) {
               af var29 = var18[var25];
               if (!var21.containsKey(var29)) {
                  var21.put(var29, var29);
               }
            }

            for (int var26 = 0; var26 < var18.length; var26++) {
               var18[var26] = (af)var21.get(var18[var26]);
            }

            if (var3 != null) {
               var3.a(var14, var16, var40);
            }

            return var40;
         }
      }
   }

   public static af a(af var0, ag var1, IdentityHashMap var2) {
      if (var0.a()) {
         return var0;
      }

      af var3;
      if ((var3 = (af)var2.get(var0)) != null) {
         return var3;
      }

      if ((var3 = var1.b(var0)) != null) {
         var2.put(var0, var3);
         return var3;
      }

      boolean var8 = false;
      af[] var4 = new af[var0.b()];

      for (int var5 = 0; var5 < var4.length; var5++) {
         af var6 = a(var0.a(var5), var1, var2);
         if (var8 || var6 != var0.a(var5)) {
            if (!var8) {
               var4 = new af[var0.b()];

               for (int var9 = 0; var9 < var0.b(); var9++) {
                  var4[var9] = var0.a(var9);
               }

               var8 = true;
            }

            var4[var5] = var6;
         }
      }

      if (!var8) {
         var1.a(var0);
         var2.put(var0, var0);
         return var0;
      }

      af var10;
      if (var4.length == 0) {
         var10 = z.a;
      } else if (var4.length == 1) {
         var10 = ax.a(var4[0], var0.b(0));
      } else {
         p var11 = (p)var0;
         var10 = new p(var4, var11.b);
      }

      var1.a(var10);
      var2.put(var10, var10);
      var2.put(var0, var10);
      return var10;
   }
}
