package org.anti_ad.a.b.a.a.a.a;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class a {
   public final List a = new ArrayList();
   public final List b = new ArrayList();
   public am[] c;
   public an[] d;
   private Map j = new LinkedHashMap();
   public final int e;
   public final int f;
   public int[] g;
   public G[] h;
   public final List i = new ArrayList();

   public a(int var1, int var2) {
      this.e = var1;
      this.f = var2;
   }

   public final org.anti_ad.a.b.a.a.a.c.l a(k var1, org.anti_ad.a.b.a.a.a.H var2) {
      return new C(this).a(var1, var2);
   }

   public final org.anti_ad.a.b.a.a.a.c.l a(k var1) {
      if (var1.f != null) {
         return var1.f;
      }

      var1.f = this.a(var1, null);
      var1.f.f();
      return var1.f;
   }

   public final void b(k var1) {
      if (var1 != null) {
         var1.a = this;
         var1.b = this.a.size();
      }

      this.a.add(var1);
   }

   public final y a(int var1) {
      return !this.b.isEmpty() ? (y)this.b.get(var1) : null;
   }

   public final int a() {
      return this.b.size();
   }

   public final org.anti_ad.a.b.a.a.a.c.l a(int var1, org.anti_ad.a.b.a.a.a.H var2) {
      if (var1 >= 0 && var1 < this.a.size()) {
         k var4 = (k)this.a.get(var1);
         org.anti_ad.a.b.a.a.a.c.l var5;
         if (!(var5 = this.a(var4)).c(-2)) {
            return var5;
         }

         org.anti_ad.a.b.a.a.a.c.l var3;
         (var3 = new org.anti_ad.a.b.a.a.a.c.l()).a(var5);
         var3.e();

         while (var2 != null && var2.invokingState >= 0 && var5.c(-2)) {
            ao var6 = (ao)((k)this.a.get(var2.invokingState)).a(0);
            var5 = this.a(var6.b);
            var3.a(var5);
            var3.e();
            var2 = var2.parent;
         }

         if (var5.c(-2)) {
            var3.b(-1);
         }

         return var3;
      } else {
         throw new IllegalArgumentException("Invalid state number.");
      }
   }
}
