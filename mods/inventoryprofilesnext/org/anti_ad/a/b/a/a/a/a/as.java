package org.anti_ad.a.b.a.a.a.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class as extends at {
   public final ap[] a;

   public as(ap var1, ap var2) {
      HashSet var3 = new HashSet();
      if (var1 instanceof as) {
         var3.addAll(Arrays.asList(((as)var1).a));
      } else {
         var3.add(var1);
      }

      if (var2 instanceof as) {
         var3.addAll(Arrays.asList(((as)var2).a));
      } else {
         var3.add(var2);
      }

      List var4;
      if (!(var4 = ap.a(var3)).isEmpty()) {
         au var5 = Collections.max(var4);
         var3.add(var5);
      }

      this.a = var3.toArray(new ap[0]);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      }

      if (!(var1 instanceof as)) {
         return false;
      }

      var1 = var1;
      return Arrays.equals(this.a, var1.a);
   }

   @Override
   public int hashCode() {
      return org.anti_ad.a.b.a.a.a.c.m.a(this.a, as.class.hashCode());
   }

   @Override
   public final boolean a(org.anti_ad.a.b.a.a.a.K var1, org.anti_ad.a.b.a.a.a.M var2) {
      ap[] var3 = this.a;
      int var4 = this.a.length;

      for (int var5 = 0; var5 < var4; var5++) {
         if (var3[var5].a(var1, var2)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public final ap b(org.anti_ad.a.b.a.a.a.K var1, org.anti_ad.a.b.a.a.a.M var2) {
      boolean var3 = false;
      ArrayList var4 = new ArrayList();
      ap[] var5 = this.a;
      int var6 = this.a.length;

      for (int var7 = 0; var7 < var6; var7++) {
         ap var8;
         ap var9 = (var8 = var5[var7]).b(var1, var2);
         var3 |= var9 != var8;
         if (var9 == ar.a) {
            return ar.a;
         }

         if (var9 != null) {
            var4.add(var9);
         }
      }

      if (!var3) {
         return this;
      }

      if (var4.isEmpty()) {
         return null;
      }

      ap var10 = (ap)var4.get(0);

      for (int var11 = 1; var11 < var4.size(); var11++) {
         var10 = ap.b(var10, (ap)var4.get(var11));
      }

      return var10;
   }

   @Override
   public String toString() {
      return org.anti_ad.a.b.a.a.a.c.q.a(Arrays.asList(this.a).iterator(), "||");
   }
}
