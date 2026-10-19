package org.anti_ad.a.b.a.a.a;

import java.util.ArrayList;
import java.util.List;

public class f implements R {
   private Q c;
   protected List a = new ArrayList(100);
   protected int b = -1;
   private boolean d;

   public f(Q var1) {
      if (var1 == null) {
         throw new NullPointerException("tokenSource cannot be null");
      }

      this.c = var1;
   }

   @Override
   public final Q e() {
      return this.c;
   }

   @Override
   public final int b() {
      return this.b;
   }

   @Override
   public final void b(int var1) {
      this.f();
      this.b = this.g(var1);
   }

   @Override
   public final int c() {
      return this.a.size();
   }

   @Override
   public final void a() {
      boolean var1;
      if (this.b >= 0) {
         if (this.d) {
            var1 = this.b < this.a.size() - 1;
         } else {
            var1 = this.b < this.a.size();
         }
      } else {
         var1 = false;
      }

      if (!var1 && this.a(1) == -1) {
         throw new IllegalStateException("cannot consume EOF");
      }

      if (this.c(this.b + 1)) {
         this.b = this.g(this.b + 1);
      }
   }

   protected final boolean c(int var1) {
      assert var1 >= 0;
      int var2;
      return (var2 = var1 - this.a.size() + 1) > 0 ? this.h(var2) >= var2 : true;
   }

   private int h(int var1) {
      if (this.d) {
         return 0;
      }

      for (int var2 = 0; var2 < var1; var2++) {
         O var3;
         if ((var3 = this.c.nextToken()) instanceof U) {
            ((U)var3).c(this.a.size());
         }

         this.a.add(var3);
         if (var3.a() == -1) {
            this.d = true;
            return var2 + 1;
         }
      }

      return var1;
   }

   @Override
   public final O d(int var1) {
      if (var1 >= 0 && var1 < this.a.size()) {
         return (O)this.a.get(var1);
      } else {
         throw new IndexOutOfBoundsException("token index " + var1 + " out of range 0.." + (this.a.size() - 1));
      }
   }

   @Override
   public final int a(int var1) {
      return this.f(var1).a();
   }

   protected O e(int var1) {
      return this.b - var1 < 0 ? null : (O)this.a.get(this.b - var1);
   }

   @Override
   public O f(int var1) {
      this.f();
      if (var1 == 0) {
         return null;
      }

      if (var1 < 0) {
         return this.e(-var1);
      }

      var1 = this.b + var1 - 1;
      this.c(var1);
      return var1 >= this.a.size() ? (O)this.a.get(this.a.size() - 1) : (O)this.a.get(var1);
   }

   protected int g(int var1) {
      return var1;
   }

   protected final void f() {
      if (this.b == -1) {
         this.g();
      }
   }

   private void g() {
      this.c(0);
      this.b = this.g(0);
   }

   protected final int a(int var1, int var2) {
      this.c(var1);
      if (var1 >= this.c()) {
         return this.c() - 1;
      }

      O var3;
      while ((var3 = (O)this.a.get(var1)).e() != var2) {
         if (var3.a() == -1) {
            return var1;
         }

         this.c(++var1);
      }

      return var1;
   }

   protected final int b(int var1, int var2) {
      this.c(var1);
      if (var1 >= this.c()) {
         return this.c() - 1;
      }

      while (var1 >= 0) {
         O var3;
         if ((var3 = (O)this.a.get(var1)).a() == -1 || var3.e() == var2) {
            return var1;
         }

         var1--;
      }

      return var1;
   }

   @Override
   public final String d() {
      return this.c.getSourceName();
   }

   @Override
   public final String a(O var1, O var2) {
      if (var1 != null && var2 != null) {
         org.anti_ad.a.b.a.a.a.c.k var7 = org.anti_ad.a.b.a.a.a.c.k.a(var1.h(), var2.h());
         f var6 = this;
         int var3 = var7.b;
         int var8 = var7.c;
         if (var3 >= 0 && var8 >= 0) {
            var6.c(var8);
            if (var8 >= var6.a.size()) {
               var8 = var6.a.size() - 1;
            }

            StringBuilder var4 = new StringBuilder();

            O var5;
            while (var3 <= var8 && (var5 = (O)var6.a.get(var3)).a() != -1) {
               var4.append(var5.b());
               var3++;
            }

            return var4.toString();
         } else {
            return "";
         }
      } else {
         return "";
      }
   }
}
