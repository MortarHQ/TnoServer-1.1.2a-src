package org.anti_ad.a.b.a.a.a.a;

public final class av extends ap {
   private int a;
   private int b;
   private boolean c;

   protected av() {
      this.a = -1;
      this.b = -1;
      this.c = false;
   }

   public av(int var1, int var2, boolean var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   @Override
   public final boolean a(org.anti_ad.a.b.a.a.a.K var1, org.anti_ad.a.b.a.a.a.M var2) {
      var2 = this.c ? var2 : null;
      return var1.sempred(var2, this.a, this.b);
   }

   @Override
   public final int hashCode() {
      return org.anti_ad.a.b.a.a.a.c.m.b(
         org.anti_ad.a.b.a.a.a.c.m.a(org.anti_ad.a.b.a.a.a.c.m.a(org.anti_ad.a.b.a.a.a.c.m.a(0, this.a), this.b), this.c ? 1 : 0), 3
      );
   }

   @Override
   public final boolean equals(Object var1) {
      if (!(var1 instanceof av)) {
         return false;
      }

      if (this == var1) {
         return true;
      }

      var1 = var1;
      return this.a == var1.a && this.b == var1.b && this.c == var1.c;
   }

   @Override
   public final String toString() {
      return "{" + this.a + ":" + this.b + "}?";
   }
}
