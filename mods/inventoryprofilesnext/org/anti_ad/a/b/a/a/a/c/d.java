package org.anti_ad.a.b.a.a.a.c;

import java.util.LinkedHashMap;
import java.util.Map;
import org.anti_ad.a.b.a.a.a.a.af;

public final class d {
   private Map a = new LinkedHashMap();

   public final Object a(af var1, af var2, af var3) {
      Map var4 = (Map)this.a.get(var1);
      Object var5 = null;
      if (var4 == null) {
         var4 = new LinkedHashMap();
         this.a.put(var1, var4);
      } else {
         var5 = var4.get(var2);
      }

      var4.put(var2, var3);
      return var5;
   }

   public final Object a(af var1, af var2) {
      Map var3;
      return (var3 = (Map)this.a.get(var1)) == null ? null : var3.get(var2);
   }
}
