package org.anti_ad.a.b.a.a.a.d.a;

final class g extends a {
   private final String a;
   private final String b;

   public g(String var1, String var2) {
      if (var2 != null && !var2.isEmpty()) {
         this.b = var1;
         this.a = var2;
      } else {
         throw new IllegalArgumentException("tag cannot be null or empty");
      }
   }

   public final String a() {
      return this.a;
   }

   public final String b() {
      return this.b;
   }

   @Override
   public final String toString() {
      return this.b != null ? this.b + ":" + this.a : this.a;
   }
}
