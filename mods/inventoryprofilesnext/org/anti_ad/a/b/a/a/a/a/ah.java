package org.anti_ad.a.b.a.a.a.a;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public final class ah extends Enum {
   public static final int a = 1;
   public static final int b = 2;
   public static final int c = 3;

   public static boolean a(int var0, c var1) {
      if (b(var1)) {
         return true;
      }

      if (var0 == a && var1.d) {
         c var4 = new c();

         for (b var2 : var1) {
            var2 = new b(var2, ar.a);
            var4.a(var2);
         }

         var1 = var4;
      }

      Iterator var5 = c(var1).iterator();

      boolean var10000;
      while (true) {
         if (var5.hasNext()) {
            if (((BitSet)var5.next()).cardinality() <= 1) {
               continue;
            }

            var10000 = true;
            break;
         }

         var10000 = false;
         break;
      }

      if (var10000) {
         c var6 = var1;
         HashMap var9 = new HashMap();

         for (b var11 : var6) {
            BitSet var3;
            if ((var3 = (BitSet)var9.get(var11.a)) == null) {
               var3 = new BitSet();
               var9.put(var11.a, var3);
            }

            var3.set(var11.b);
         }

         Iterator var12 = var9.values().iterator();

         while (true) {
            if (!var12.hasNext()) {
               var10000 = false;
               break;
            }

            if (((BitSet)var12.next()).cardinality() == 1) {
               var10000 = true;
               break;
            }
         }

         if (!var10000) {
            return true;
         }
      }

      return false;
   }

   public static boolean a(c var0) {
      Iterator var1 = var0.iterator();

      while (var1.hasNext()) {
         if (((b)var1.next()).a instanceof an) {
            return true;
         }
      }

      return false;
   }

   public static boolean b(c var0) {
      Iterator var1 = var0.iterator();

      while (var1.hasNext()) {
         if (!(((b)var1.next()).a instanceof an)) {
            return false;
         }
      }

      return true;
   }

   public static int a(Collection var0) {
      return e(var0);
   }

   public static boolean b(Collection var0) {
      Iterator var1 = var0.iterator();

      boolean var10000;
      while (true) {
         if (var1.hasNext()) {
            if (((BitSet)var1.next()).cardinality() != 1) {
               continue;
            }

            var10000 = true;
            break;
         }

         var10000 = false;
         break;
      }

      return !var10000;
   }

   public static boolean c(Collection var0) {
      Iterator var2;
      BitSet var1 = (BitSet)(var2 = var0.iterator()).next();

      while (var2.hasNext()) {
         if (!((BitSet)var2.next()).equals(var1)) {
            return false;
         }
      }

      return true;
   }

   public static BitSet d(Collection var0) {
      BitSet var1 = new BitSet();

      for (BitSet var2 : var0) {
         var1.or(var2);
      }

      return var1;
   }

   public static Collection c(c var0) {
      aj var1 = new aj();

      for (b var2 : var0) {
         BitSet var3;
         if ((var3 = (BitSet)var1.get(var2)) == null) {
            var3 = new BitSet();
            var1.put(var2, var3);
         }

         var3.set(var2.b);
      }

      return var1.values();
   }

   public static int e(Collection var0) {
      BitSet var1 = new BitSet();
      Iterator var3 = var0.iterator();

      while (var3.hasNext()) {
         int var2 = ((BitSet)var3.next()).nextSetBit(0);
         var1.set(var2);
         if (var1.cardinality() > 1) {
            return 0;
         }
      }

      return var1.nextSetBit(0);
   }

   static {
      int[] var10000 = new int[]{a, b, c};
   }
}
