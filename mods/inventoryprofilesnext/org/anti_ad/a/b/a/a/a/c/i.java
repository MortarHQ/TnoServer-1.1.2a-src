package org.anti_ad.a.b.a.a.a.c;

import java.util.Arrays;

public class i {
   private static final int[] a = new int[0];
   private int[] b = a;
   private int c;

   public final void a(int var1) {
      if (this.b.length == this.c) {
         int var3 = this.c + 1;
         i var2 = this;
         if (var3 < 0 || var3 > 2147483639) {
            throw new OutOfMemoryError();
         }

         int var4;
         if (var2.b.length == 0) {
            var4 = 4;
         } else {
            var4 = var2.b.length;
         }

         while (var4 < var3) {
            if ((var4 <<= 1) < 0 || var4 > 2147483639) {
               var4 = 2147483639;
            }
         }

         var2.b = Arrays.copyOf(var2.b, var4);
      }

      this.b[this.c] = var1;
      this.c++;
   }

   public final int b(int var1) {
      if (var1 >= 0 && var1 < this.c) {
         return this.b[var1];
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public final int c(int var1) {
      int var2 = this.b(var1);
      System.arraycopy(this.b, var1 + 1, this.b, var1, this.c - var1 - 1);
      this.b[this.c - 1] = 0;
      this.c--;
      return var2;
   }

   public final boolean a() {
      return this.c == 0;
   }

   public final int b() {
      return this.c;
   }

   public final void c() {
      Arrays.fill(this.b, 0, this.c, 0);
      this.c = 0;
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      }

      if (!(var1 instanceof i)) {
         return false;
      }

      var1 = var1;
      if (this.c != var1.c) {
         return false;
      }

      for (int var2 = 0; var2 < this.c; var2++) {
         if (this.b[var2] != var1.b[var2]) {
            return false;
         }
      }

      return true;
   }

   @Override
   public int hashCode() {
      int var1 = 1;

      for (int var2 = 0; var2 < this.c; var2++) {
         var1 = var1 * 31 + this.b[var2];
      }

      return var1;
   }

   @Override
   public String toString() {
      i var1 = this;
      return Arrays.toString(this.c == 0 ? a : Arrays.copyOf(var1.b, var1.c));
   }
}
