package org.anti_ad.a.b.a.a.a;

@Deprecated
public class c implements g {
   private char[] a;
   private int b;
   private int c = 0;
   private String d;

   public c() {
   }

   public c(String var1) {
      this.a = var1.toCharArray();
      this.b = var1.length();
   }

   @Override
   public final void a() {
      if (this.c >= this.b) {
         assert this.a(1) == -1;
         throw new IllegalStateException("cannot consume EOF");
      }

      if (this.c < this.b) {
         this.c++;
      }
   }

   @Override
   public final int a(int var1) {
      if (var1 == 0) {
         return 0;
      }

      if (var1 < 0) {
         if (this.c + ++var1 - 1 < 0) {
            return -1;
         }
      }

      return this.c + var1 - 1 >= this.b ? -1 : this.a[this.c + var1 - 1];
   }

   @Override
   public final int b() {
      return this.c;
   }

   @Override
   public final int c() {
      return this.b;
   }

   @Override
   public final void b(int var1) {
      if (var1 <= this.c) {
         this.c = var1;
      } else {
         var1 = Math.min(var1, this.b);

         while (this.c < var1) {
            this.a();
         }
      }
   }

   @Override
   public final String a(org.anti_ad.a.b.a.a.a.c.k var1) {
      int var2 = var1.b;
      int var3;
      if ((var3 = var1.c) >= this.b) {
         var3 = this.b - 1;
      }

      int var4 = var3 - var2 + 1;
      return var2 >= this.b ? "" : new String(this.a, var2, var4);
   }

   @Override
   public final String d() {
      return "<unknown>";
   }

   @Override
   public String toString() {
      return new String(this.a);
   }
}
