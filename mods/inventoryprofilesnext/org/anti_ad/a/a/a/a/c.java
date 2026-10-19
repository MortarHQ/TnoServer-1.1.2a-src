package org.anti_ad.a.a.a.a;

public final class c {
   private final String a;
   private final String b;

   public c(String var1, String var2) {
      this.a = var1;
      this.b = var2;
   }

   public final String a() {
      return this.a;
   }

   public final String b() {
      return this.b;
   }

   @Override
   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         return !this.a.equals(var1.a) ? false : this.b.equals(var1.b);
      } else {
         return false;
      }
   }

   @Override
   public final int hashCode() {
      return this.a.hashCode() * 31 + this.b.hashCode();
   }
}
