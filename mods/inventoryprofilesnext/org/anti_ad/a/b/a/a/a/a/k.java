package org.anti_ad.a.b.a.a.a.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public abstract class k {
   public a a = null;
   public int b = -1;
   public int c;
   public boolean d = false;
   protected final List e = new ArrayList(4);
   public org.anti_ad.a.b.a.a.a.c.l f;

   @Override
   public int hashCode() {
      return this.b;
   }

   @Override
   public boolean equals(Object var1) {
      return var1 instanceof k ? this.b == ((k)var1).b : false;
   }

   @Override
   public String toString() {
      return String.valueOf(this.b);
   }

   public final int a() {
      return this.e.size();
   }

   public final void a(aC var1) {
      int var10001 = this.e.size();
      aC var3 = var1;
      int var2 = var10001;
      k var7 = this;
      if (this.e.isEmpty()) {
         var7.d = var3.b();
      } else if (var7.d != var3.b()) {
         System.err.format(Locale.getDefault(), "ATN state %d has both epsilon and non-epsilon transitions.\n", var7.b);
         var7.d = false;
      }

      boolean var4 = false;
      Iterator var5 = var7.e.iterator();

      while (var5.hasNext()) {
         aC var6;
         if ((var6 = (aC)var5.next()).d.b == var3.d.b) {
            if (var6.c() != null && var3.c() != null && var6.c().equals(var3.c())) {
               var4 = true;
               break;
            }

            if (var6.b() && var3.b()) {
               var4 = true;
               break;
            }
         }
      }

      if (!var4) {
         var7.e.add(var2, var3);
      }
   }

   public final aC a(int var1) {
      return (aC)this.e.get(var1);
   }

   public final aC b(int var1) {
      return (aC)this.e.remove(var1);
   }

   public abstract int b();

   static {
      Collections.unmodifiableList(
         Arrays.asList(
            "INVALID",
            "BASIC",
            "RULE_START",
            "BLOCK_START",
            "PLUS_BLOCK_START",
            "STAR_BLOCK_START",
            "TOKEN_START",
            "RULE_STOP",
            "BLOCK_END",
            "STAR_LOOP_BACK",
            "STAR_LOOP_ENTRY",
            "PLUS_LOOP_BACK",
            "LOOP_END"
         )
      );
   }
}
