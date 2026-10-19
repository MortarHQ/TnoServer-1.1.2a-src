package org.anti_ad.a.b.a.a.a.a;

public final class au extends ap implements Comparable {
   private int a;

   protected au() {
      this.a = 0;
   }

   public au(int var1) {
      this.a = var1;
   }

   @Override
   public final boolean a(org.anti_ad.a.b.a.a.a.K var1, org.anti_ad.a.b.a.a.a.M var2) {
      return var1.precpred(var2, this.a);
   }

   @Override
   public final ap b(org.anti_ad.a.b.a.a.a.K var1, org.anti_ad.a.b.a.a.a.M var2) {
      return var1.precpred(var2, this.a) ? ar.a : null;
   }

   @Override
   public final int hashCode() {
      return 31 + this.a;
   }

   @Override
   public final boolean equals(Object var1) {
      if (!(var1 instanceof au)) {
         return false;
      }

      if (this == var1) {
         return true;
      }

      var1 = var1;
      return this.a == var1.a;
   }

   @Override
   public final String toString() {
      return "{" + this.a + ">=prec}?";
   }
}
