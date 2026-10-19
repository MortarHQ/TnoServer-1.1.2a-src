package org.anti_ad.a.b.a.a.a.d.a;

import org.anti_ad.a.b.a.a.a.O;
import org.anti_ad.a.b.a.a.a.Q;

public final class f implements O {
   private final String a;
   private final int b;
   private final String c;

   public f(String var1, int var2, String var3) {
      if (var1 != null && !var1.isEmpty()) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
      } else {
         throw new IllegalArgumentException("ruleName cannot be null or empty.");
      }
   }

   @Override
   public final int e() {
      return 0;
   }

   @Override
   public final String b() {
      return this.c != null ? "<" + this.c + ":" + this.a + ">" : "<" + this.a + ">";
   }

   @Override
   public final int a() {
      return this.b;
   }

   @Override
   public final int c() {
      return 0;
   }

   @Override
   public final int d() {
      return -1;
   }

   @Override
   public final int h() {
      return -1;
   }

   @Override
   public final int f() {
      return -1;
   }

   @Override
   public final int g() {
      return -1;
   }

   @Override
   public final Q i() {
      return null;
   }

   @Override
   public final org.anti_ad.a.b.a.a.a.g j() {
      return null;
   }

   @Override
   public final String toString() {
      return this.a + ":" + this.b;
   }
}
