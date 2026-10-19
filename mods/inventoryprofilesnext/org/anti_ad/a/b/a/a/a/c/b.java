package org.anti_ad.a.b.a.a.a.c;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class b implements Set {
   private a a;
   private Object[][] b;
   private int c = 0;
   private int d = 1;
   private int e;
   private int f;
   private int g;

   public b() {
      this(null, 8);
   }

   public b(a var1, int var2) {
      if (var1 == null) {
         var1 = n.a;
      }

      this.a = var1;
      this.f = 16;
      this.g = var2;
      this.b = this.b(16);
      this.e = (int)Math.floor(12.0);
   }

   public final Object b(Object var1) {
      if (this.c > this.e) {
         b var2 = this;
         Object[][] var3 = this.b;
         var2.d += 4;
         int var4 = var2.b.length << 1;
         Object[][] var5;
         int[] var6 = new int[(var5 = var2.b(var4)).length];
         var2.b = var5;
         var2.e = (int)(var4 * 0.75);
         int var7 = var2.size();
         var4 = var3.length;

         for (int var8 = 0; var8 < var4; var8++) {
            Object[] var9;
            if ((var9 = var3[var8]) != null) {
               int var10 = var9.length;

               Object var12;
               for (int var11 = 0; var11 < var10 && (var12 = var9[var11]) != null; var11++) {
                  int var13 = var2.d(var12);
                  int var14;
                  Object[] var15;
                  if ((var14 = var6[var13]) == 0) {
                     var15 = var2.a(var2.g);
                     var5[var13] = var15;
                  } else {
                     var15 = var5[var13];
                     if (var14 == var15.length) {
                        var15 = Arrays.copyOf(var15, var15.length << 1);
                        var5[var13] = var15;
                     }
                  }

                  var15[var14] = var12;
                  var6[var13]++;
               }
            }
         }

         assert var2.c == var7;
      }

      Object var17 = var1;
      b var16 = this;
      int var19 = this.d(var17);
      Object[] var20;
      if ((var20 = var16.b[var19]) == null) {
         (var20 = var16.a(var16.g))[0] = var17;
         var16.b[var19] = var20;
         var16.c++;
         return var17;
      }

      for (int var23 = 0; var23 < var20.length; var23++) {
         Object var25;
         if ((var25 = var20[var23]) == null) {
            var20[var23] = var17;
            var16.c++;
            return var17;
         }

         if (var16.a.a(var25, var17)) {
            return var25;
         }
      }

      int var24 = var20.length;
      var20 = Arrays.copyOf(var20, var20.length << 1);
      var16.b[var19] = var20;
      var20[var24] = var17;
      var16.c++;
      return var17;
   }

   private Object c(Object var1) {
      if (var1 == null) {
         return var1;
      }

      int var2 = this.d(var1);
      Object[] var6;
      if ((var6 = this.b[var2]) == null) {
         return null;
      }

      int var3 = var6.length;

      for (int var4 = 0; var4 < var3; var4++) {
         Object var5;
         if ((var5 = var6[var4]) == null) {
            return null;
         }

         if (this.a.a(var5, var1)) {
            return var5;
         }
      }

      return null;
   }

   private int d(Object var1) {
      return this.a.a(var1) & this.b.length - 1;
   }

   @Override
   public int hashCode() {
      int var1 = 0;
      Object[][] var2 = this.b;
      int var3 = this.b.length;

      for (int var4 = 0; var4 < var3; var4++) {
         Object[] var5;
         if ((var5 = var2[var4]) != null) {
            int var6 = var5.length;

            Object var8;
            for (int var7 = 0; var7 < var6 && (var8 = var5[var7]) != null; var7++) {
               var1 = m.a(var1, this.a.a(var8));
            }
         }
      }

      return m.b(var1, this.size());
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      }

      if (!(var1 instanceof b)) {
         return false;
      }

      b var2;
      return (var2 = (b)var1).size() != this.size() ? false : this.containsAll(var2);
   }

   @Override
   public final boolean add(Object var1) {
      return this.b(var1) == var1;
   }

   @Override
   public final int size() {
      return this.c;
   }

   @Override
   public final boolean isEmpty() {
      return this.c == 0;
   }

   @Override
   public final boolean contains(Object var1) {
      return this.e(this.a(var1));
   }

   private boolean e(Object var1) {
      return var1 == null ? false : this.c(var1) != null;
   }

   @Override
   public Iterator iterator() {
      return new c(this, this.toArray());
   }

   @Override
   public Object[] toArray() {
      Object[] var1 = this.a(this.size());
      int var2 = 0;
      Object[][] var3 = this.b;
      int var4 = this.b.length;

      for (int var5 = 0; var5 < var4; var5++) {
         Object[] var6;
         if ((var6 = var3[var5]) != null) {
            int var7 = var6.length;

            Object var9;
            for (int var8 = 0; var8 < var7 && (var9 = var6[var8]) != null; var8++) {
               var1[var2++] = var9;
            }
         }
      }

      return var1;
   }

   @Override
   public Object[] toArray(Object[] var1) {
      if (var1.length < this.size()) {
         var1 = Arrays.copyOf(var1, this.size());
      }

      int var2 = 0;
      Object[][] var3 = this.b;
      int var4 = this.b.length;

      for (int var5 = 0; var5 < var4; var5++) {
         Object[] var6;
         if ((var6 = var3[var5]) != null) {
            int var7 = var6.length;

            Object var9;
            for (int var8 = 0; var8 < var7 && (var9 = var6[var8]) != null; var8++) {
               var1[var2++] = var9;
            }
         }
      }

      return var1;
   }

   @Override
   public final boolean remove(Object var1) {
      return this.f(this.a(var1));
   }

   private boolean f(Object var1) {
      if (var1 == null) {
         return false;
      }

      int var2 = this.d(var1);
      Object[] var5;
      if ((var5 = this.b[var2]) == null) {
         return false;
      }

      for (int var3 = 0; var3 < var5.length; var3++) {
         Object var4;
         if ((var4 = var5[var3]) == null) {
            return false;
         }

         if (this.a.a(var4, var1)) {
            System.arraycopy(var5, var3 + 1, var5, var3, var5.length - var3 - 1);
            var5[var5.length - 1] = null;
            this.c--;
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean containsAll(Collection var1) {
      if (var1 instanceof b) {
         Object[][] var2 = ((b)var1).b;
         int var8 = ((b)var1).b.length;

         for (int var3 = 0; var3 < var8; var3++) {
            Object[] var4;
            if ((var4 = var2[var3]) != null) {
               int var5 = var4.length;

               Object var7;
               for (int var6 = 0; var6 < var5 && (var7 = var4[var6]) != null; var6++) {
                  if (!this.e(this.a(var7))) {
                     return false;
                  }
               }
            }
         }
      } else {
         for (Object var10 : var1) {
            if (!this.e(this.a(var10))) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public boolean addAll(Collection var1) {
      boolean var2 = false;

      for (Object var3 : var1) {
         if (this.b(var3) != var3) {
            var2 = true;
         }
      }

      return var2;
   }

   @Override
   public boolean retainAll(Collection var1) {
      int var2 = 0;
      Object[][] var3 = this.b;
      int var4 = this.b.length;

      for (int var5 = 0; var5 < var4; var5++) {
         Object[] var6;
         if ((var6 = var3[var5]) != null) {
            int var7 = 0;
            int var8 = 0;

            while (var7 < var6.length && var6[var7] != null) {
               if (var1.contains(var6[var7])) {
                  if (var7 != var8) {
                     var6[var8] = var6[var7];
                  }

                  var8++;
                  var2++;
               }

               var7++;
            }

            var2 += var8;

            while (var8 < var7) {
               var6[var8] = null;
               var8++;
            }
         }
      }

      boolean var9 = var2 != this.c;
      this.c = var2;
      return var9;
   }

   @Override
   public boolean removeAll(Collection var1) {
      boolean var2 = false;

      for (Object var3 : var1) {
         var2 |= this.f(this.a(var3));
      }

      return var2;
   }

   @Override
   public void clear() {
      this.c = 0;
      this.b = this.b(this.f);
      this.e = (int)Math.floor(this.f * 0.75);
   }

   @Override
   public String toString() {
      if (this.size() == 0) {
         return "{}";
      }

      StringBuilder var1;
      (var1 = new StringBuilder()).append('{');
      boolean var2 = true;
      Object[][] var3 = this.b;
      int var4 = this.b.length;

      for (int var5 = 0; var5 < var4; var5++) {
         Object[] var6;
         if ((var6 = var3[var5]) != null) {
            int var7 = var6.length;

            Object var9;
            for (int var8 = 0; var8 < var7 && (var9 = var6[var8]) != null; var8++) {
               if (var2) {
                  var2 = false;
               } else {
                  var1.append(", ");
               }

               var1.append(var9.toString());
            }
         }
      }

      var1.append('}');
      return var1.toString();
   }

   protected Object a(Object var1) {
      return var1;
   }

   protected Object[][] b(int var1) {
      return new Object[var1][];
   }

   protected Object[] a(int var1) {
      return new Object[var1];
   }
}
