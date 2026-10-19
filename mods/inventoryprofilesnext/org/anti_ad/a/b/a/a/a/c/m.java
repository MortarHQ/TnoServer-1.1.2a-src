package org.anti_ad.a.b.a.a.a.c;

import org.anti_ad.a.b.a.a.a.a.ap;

public final class m {
   public static int a(int var0, int var1) {
      int var3;
      var1 = ((var3 = var1 * -862048943) << 15 | var3 >>> 17) * 461845907;
      int var2;
      return ((var2 = var0 ^ var1) << 13 | var2 >>> 19) * 5 + -430675100;
   }

   public static int a(int var0, Object var1) {
      return a(var0, var1 != null ? var1.hashCode() : 0);
   }

   public static int b(int var0, int var1) {
      return ((var0 ^ var1 << 2 ^ (var0 ^ var1 << 2) >>> 16) * -2048144789 ^ (var0 ^ var1 << 2 ^ (var0 ^ var1 << 2) >>> 16) * -2048144789 >>> 13) * -1028477387
         ^ ((var0 ^ var1 << 2 ^ (var0 ^ var1 << 2) >>> 16) * -2048144789 ^ (var0 ^ var1 << 2 ^ (var0 ^ var1 << 2) >>> 16) * -2048144789 >>> 13) * -1028477387
            >>> 16;
   }

   public static int a(ap[] var0, int var1) {
      ap[] var2 = var0;
      int var3 = var0.length;

      for (int var4 = 0; var4 < var3; var4++) {
         ap var5 = var2[var4];
         var1 = a(var1, var5);
      }

      return b(var1, var0.length);
   }
}
