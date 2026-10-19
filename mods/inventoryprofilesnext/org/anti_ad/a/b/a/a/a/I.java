package org.anti_ad.a.b.a.a.a;

import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;

public final class I implements a {
   private final Collection a;

   public I(Collection var1) {
      if (var1 == null) {
         throw new NullPointerException("delegates");
      }

      this.a = var1;
   }

   @Override
   public final void syntaxError(K var1, Object var2, int var3, int var4, String var5, J var6) {
      Iterator var7 = this.a.iterator();

      while (var7.hasNext()) {
         ((a)var7.next()).syntaxError(var1, var2, var3, var4, var5, var6);
      }
   }

   @Override
   public final void reportAmbiguity(D var1, org.anti_ad.a.b.a.a.a.b.a var2, int var3, int var4, boolean var5, BitSet var6, org.anti_ad.a.b.a.a.a.a.c var7) {
      Iterator var8 = this.a.iterator();

      while (var8.hasNext()) {
         ((a)var8.next()).reportAmbiguity(var1, var2, var3, var4, var5, var6, var7);
      }
   }

   @Override
   public final void reportAttemptingFullContext(D var1, org.anti_ad.a.b.a.a.a.b.a var2, int var3, int var4, BitSet var5, org.anti_ad.a.b.a.a.a.a.c var6) {
      Iterator var7 = this.a.iterator();

      while (var7.hasNext()) {
         ((a)var7.next()).reportAttemptingFullContext(var1, var2, var3, var4, var5, var6);
      }
   }

   @Override
   public final void reportContextSensitivity(D var1, org.anti_ad.a.b.a.a.a.b.a var2, int var3, int var4, int var5, org.anti_ad.a.b.a.a.a.a.c var6) {
      Iterator var7 = this.a.iterator();

      while (var7.hasNext()) {
         ((a)var7.next()).reportContextSensitivity(var1, var2, var3, var4, var5, var6);
      }
   }
}
