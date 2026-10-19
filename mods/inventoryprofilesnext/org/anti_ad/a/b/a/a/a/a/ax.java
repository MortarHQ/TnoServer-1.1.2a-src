package org.anti_ad.a.b.a.a.a.a;

public class ax extends af {
   public final af b;
   public final int c;

   ax(af var1, int var2) {
      int var10001;
      if (var1 != null) {
         int var4 = var2;
         af var3 = var1;
         var10001 = org.anti_ad.a.b.a.a.a.c.m.b(org.anti_ad.a.b.a.a.a.c.m.a(org.anti_ad.a.b.a.a.a.c.m.a(1, var3), var4), 2);
      } else {
         var10001 = org.anti_ad.a.b.a.a.a.c.m.b(1, 0);
      }

      super(var10001);
      assert var2 != -1;
      this.b = var1;
      this.c = var2;
   }

   public static ax a(af var0, int var1) {
      return var1 == Integer.MAX_VALUE && var0 == null ? z.a : new ax(var0, var1);
   }

   @Override
   public int b() {
      return 1;
   }

   @Override
   public af a(int var1) {
      assert var1 == 0;
      return this.b;
   }

   @Override
   public int b(int var1) {
      assert var1 == 0;
      return this.c;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      }

      if (!(var1 instanceof ax)) {
         return false;
      }

      if (this.hashCode() != var1.hashCode()) {
         return false;
      }

      var1 = var1;
      return this.c == var1.c && this.b != null && this.b.equals(var1.b);
   }

   @Override
   public String toString() {
      String var1;
      if ((var1 = this.b != null ? this.b.toString() : "").length() == 0) {
         return this.c == Integer.MAX_VALUE ? "$" : String.valueOf(this.c);
      } else {
         return this.c + " " + var1;
      }
   }
}
