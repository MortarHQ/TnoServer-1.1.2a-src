package org.anti_ad.a.b.a.a.a;

public final class s extends f {
   private int c = 0;

   public s(Q var1) {
      super(var1);
   }

   @Override
   protected final int g(int var1) {
      return this.a(var1, this.c);
   }

   @Override
   protected final O e(int var1) {
      if (var1 != 0 && super.b - var1 >= 0) {
         int var2 = super.b;

         for (int var3 = 1; var3 <= var1 && var2 > 0; var3++) {
            var2 = this.b(var2 - 1, this.c);
         }

         return var2 < 0 ? null : (O)super.a.get(var2);
      } else {
         return null;
      }
   }

   @Override
   public final O f(int var1) {
      this.f();
      if (var1 == 0) {
         return null;
      }

      if (var1 < 0) {
         return this.e(-var1);
      }

      int var2 = super.b;

      for (int var3 = 1; var3 < var1; var3++) {
         if (this.c(var2 + 1)) {
            var2 = this.a(var2 + 1, this.c);
         }
      }

      return (O)super.a.get(var2);
   }
}
