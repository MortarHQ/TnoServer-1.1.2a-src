package org.anti_ad.a.b.a.a.a.d.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.anti_ad.a.b.a.a.a.B;
import org.anti_ad.a.b.a.a.a.D;
import org.anti_ad.a.b.a.a.a.G;
import org.anti_ad.a.b.a.a.a.J;
import org.anti_ad.a.b.a.a.a.O;
import org.anti_ad.a.b.a.a.a.s;
import org.anti_ad.a.b.a.a.a.z;
import org.anti_ad.a.b.a.a.a.c.p;

public final class c {
   private final z a;
   private final D b;
   private String c = "<";
   private String d = ">";
   private String e = "\\";

   public c(z var1, D var2) {
      this.a = var1;
      this.b = var2;
   }

   public final b a(String var1, int var2) {
      String var4 = var1;
      c var3 = this;
      String var6 = var4;
      c var5 = this;
      int var7 = 0;
      int var8 = var6.length();
      ArrayList var9 = new ArrayList();
      new StringBuilder();
      ArrayList var10 = new ArrayList();
      ArrayList var11 = new ArrayList();

      while (var7 < var8) {
         if (var7 == var6.indexOf(var5.e + var5.c, var7)) {
            var7 += var5.e.length() + var5.c.length();
         } else if (var7 == var6.indexOf(var5.e + var5.d, var7)) {
            var7 += var5.e.length() + var5.d.length();
         } else if (var7 == var6.indexOf(var5.c, var7)) {
            var10.add(var7);
            var7 += var5.c.length();
         } else if (var7 == var6.indexOf(var5.d, var7)) {
            var11.add(var7);
            var7 += var5.d.length();
         } else {
            var7++;
         }
      }

      if (var10.size() > var11.size()) {
         throw new IllegalArgumentException("unterminated tag in pattern: ".concat(String.valueOf(var6)));
      }

      if (var10.size() < var11.size()) {
         throw new IllegalArgumentException("missing start tag in pattern: ".concat(String.valueOf(var6)));
      }

      var7 = var10.size();

      for (int var12 = 0; var12 < var7; var12++) {
         if ((Integer)var10.get(var12) >= (Integer)var11.get(var12)) {
            throw new IllegalArgumentException("tag delimiters out of order in pattern: ".concat(String.valueOf(var6)));
         }
      }

      if (var7 == 0) {
         String var38 = var6.substring(0, var8);
         var9.add(new h(var38));
      }

      if (var7 > 0 && (Integer)var10.get(0) > 0) {
         String var39 = var6.substring(0, (Integer)var10.get(0));
         var9.add(new h(var39));
      }

      for (int var40 = 0; var40 < var7; var40++) {
         String var13;
         String var14 = var13 = var6.substring((Integer)var10.get(var40) + var5.c.length(), (Integer)var11.get(var40));
         String var15 = null;
         int var16;
         if ((var16 = var13.indexOf(58)) >= 0) {
            var15 = var13.substring(0, var16);
            var14 = var13.substring(var16 + 1, var13.length());
         }

         var9.add(new g(var15, var14));
         if (var40 + 1 < var7) {
            var13 = var6.substring((Integer)var11.get(var40) + var5.d.length(), (Integer)var10.get(var40 + 1));
            var9.add(new h(var13));
         }
      }

      int var41;
      if (var7 > 0 && (var41 = (Integer)var11.get(var7 - 1) + var5.d.length()) < var8) {
         String var44 = var6.substring(var41, var8);
         var9.add(new h(var44));
      }

      for (int var42 = 0; var42 < var9.size(); var42++) {
         a var45;
         h var46;
         String var47;
         if ((var45 = (a)var9.get(var42)) instanceof h && (var47 = (var46 = (h)var45).a().replace(var5.e, "")).length() < var46.a().length()) {
            var9.set(var42, new h(var47));
         }
      }

      ArrayList var25 = var9;
      ArrayList var27 = new ArrayList();
      Iterator var26 = var25.iterator();

      while (var26.hasNext()) {
         a var29;
         if (!((var29 = (a)var26.next()) instanceof g)) {
            h var30 = (h)var29;
            org.anti_ad.a.b.a.a.a.c var32 = new org.anti_ad.a.b.a.a.a.c(var30.a());
            var3.a.setInputStream(var32);

            for (O var33 = var3.a.nextToken(); var33.a() != -1; var33 = var3.a.nextToken()) {
               var27.add(var33);
            }
         } else {
            g var31;
            if (Character.isUpperCase((var31 = (g)var29).a().charAt(0))) {
               Integer var34;
               if (var34 = var3.b.getTokenType(var31.a()) == 0) {
                  throw new IllegalArgumentException("Unknown token " + var31.a() + " in pattern: " + var4);
               }

               i var35 = new i(var31.a(), var34, var31.b());
               var27.add(var35);
            } else {
               if (!Character.isLowerCase(var31.a().charAt(0))) {
                  throw new IllegalArgumentException("invalid tag: " + var31.a() + " in pattern: " + var4);
               }

               if ((var8 = var3.b.getRuleIndex(var31.a())) == -1) {
                  throw new IllegalArgumentException("Unknown rule " + var31.a() + " in pattern: " + var4);
               }

               var8 = var3.b.getATNWithBypassAlts().g[var8];
               var27.add(new f(var31.a(), var8, var31.b()));
            }
         }
      }

      ArrayList var20 = var27;
      B var21 = new B(var20);
      s var22 = new s(var21);
      G var23 = new G(this.b.getGrammarFileName(), this.b.getVocabulary(), Arrays.asList(this.b.getRuleNames()), this.b.getATNWithBypassAlts(), var22);

      try {
         var23.setErrorHandler(new org.anti_ad.a.b.a.a.a.d());
         var24 = var23.a(var2);
      } catch (p var17) {
         throw (J)var17.getCause();
      } catch (J var18) {
         throw var18;
      } catch (Exception var19) {
         throw new d(var19);
      }

      if (var22.a(1) != -1) {
         throw new e();
      } else {
         return new b(this, var1, var2, var24);
      }
   }
}
