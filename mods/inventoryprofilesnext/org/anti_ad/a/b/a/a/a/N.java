package org.anti_ad.a.b.a.a.a;

public final class N {
   public static void a(String var0, String var1) {
      String var2 = "4.13.2";
      boolean var3 = !"4.13.2".equals(var0) && !a(var2).equals(a(var0));
      boolean var4 = !var2.equals(var1) && !a(var2).equals(a(var1));
      if (var3) {
         System.err.printf("ANTLR Tool version %s used for code generation does not match the current runtime version %s%n", var0, var2);
      }

      if (var4) {
         System.err.printf("ANTLR Runtime version %s used for parser compilation does not match the current runtime version %s%n", var1, var2);
      }
   }

   private static String a(String var0) {
      int var1;
      var1 = (var1 = var0.indexOf(46)) >= 0 ? var0.indexOf(46, var1 + 1) : -1;
      int var2 = var0.indexOf(45);
      int var3 = var0.length();
      if (var1 >= 0) {
         var3 = Math.min(var3, var1);
      }

      if (var2 >= 0) {
         var3 = Math.min(var3, var2);
      }

      return var0.substring(0, var3);
   }
}
