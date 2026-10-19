package org.anti_ad.a.b.a.a.a;

import org.anti_ad.a.b.a.a.a.a.Y;
import org.anti_ad.a.b.a.a.a.a.ao;

public class u implements b {
   private boolean a = false;
   private int b = -1;
   private org.anti_ad.a.b.a.a.a.c.l c;
   private H d;
   private int e;

   @Override
   public final void a() {
      this.d();
   }

   @Override
   public final boolean b() {
      return this.a;
   }

   private void d() {
      this.a = false;
      this.c = null;
      this.b = -1;
   }

   @Override
   public final void c() {
      this.d();
   }

   @Override
   public final void b(D var1, J var2) {
      if (!this.a) {
         this.a = true;
         if (var2 instanceof C) {
            C var10 = (C)var2;
            D var9 = var1;
            R var13;
            String var14;
            if ((var13 = var1.getInputStream()) != null) {
               if (var10.b().a() == -1) {
                  var14 = "<EOF>";
               } else {
                  var14 = var13.a(var10.b(), var10.d());
               }
            } else {
               var14 = "<unknown input>";
            }

            String var6 = "no viable alternative at input " + a(var14);
            var9.notifyErrorListeners(var10.d(), var6, var10);
         } else if (var2 instanceof w) {
            w var3 = (w)var2;
            D var8 = var1;
            String var12 = "mismatched input " + a(var3.d()) + " expecting " + var3.c().a(var8.getVocabulary());
            var8.notifyErrorListeners(var3.d(), var12, var3);
         } else if (var2 instanceof v) {
            v var7 = (v)var2;
            D var5 = var1;
            String var4 = var1.getRuleNames()[var5._ctx.getRuleIndex()];
            var4 = "rule " + var4 + " " + var7.getMessage();
            var5.notifyErrorListeners(var7.d(), var4, var7);
         } else {
            System.err.println("unknown recognition error type: " + var2.getClass().getName());
            var1.notifyErrorListeners(var2.d(), var2.getMessage(), var2);
         }
      }
   }

   @Override
   public void a(D var1, J var2) {
      if (this.b == var1.getInputStream().b() && this.c != null && this.c.c(var1.getState())) {
         var1.consume();
      }

      this.b = var1.getInputStream().b();
      if (this.c == null) {
         this.c = new org.anti_ad.a.b.a.a.a.c.l();
      }

      this.c.b(var1.getState());
      org.anti_ad.a.b.a.a.a.c.l var3 = e(var1);
      a(var1, var3);
   }

   @Override
   public void b(D var1) {
      org.anti_ad.a.b.a.a.a.a.k var2 = (org.anti_ad.a.b.a.a.a.a.k)((Y)var1.getInterpreter()).b.a.get(var1.getState());
      if (!this.a) {
         int var3 = var1.getInputStream().a(1);
         org.anti_ad.a.b.a.a.a.c.l var4;
         if ((var4 = var1.getATN().a(var2)).c(var3)) {
            this.d = null;
            this.e = -1;
         } else if (var4.c(-2)) {
            if (this.d == null) {
               this.d = var1.getContext();
               this.e = var1.getState();
            }
         } else {
            switch (var2.b()) {
               case 3:
               case 4:
               case 5:
               case 10:
                  if (this.d(var1) != null) {
                     return;
                  }

                  throw new w(var1);
               case 9:
               case 11:
                  this.c(var1);
                  org.anti_ad.a.b.a.a.a.c.l var7 = e(var1);
                  org.anti_ad.a.b.a.a.a.c.l var5 = var1.getExpectedTokens();
                  (var4 = new org.anti_ad.a.b.a.a.a.c.l()).a(var5);
                  var4.a(var7);
                  org.anti_ad.a.b.a.a.a.c.l var6 = var4;
                  a(var1, var6);
               case 6:
               case 7:
               case 8:
            }
         }
      }
   }

   private void c(D var1) {
      if (!this.a) {
         this.a = true;
         O var2;
         String var3 = a(var2 = var1.getCurrentToken());
         org.anti_ad.a.b.a.a.a.c.l var4 = var1.getExpectedTokens();
         var3 = "extraneous input " + var3 + " expecting " + var4.a(var1.getVocabulary());
         var1.notifyErrorListeners(var2, var3, null);
      }
   }

   @Override
   public O a(D var1) {
      O var2;
      if ((var2 = this.d(var1)) != null) {
         var1.consume();
         return var2;
      }

      D var3 = var1;
      u var9 = this;
      int var4 = var3.getInputStream().a(1);
      org.anti_ad.a.b.a.a.a.a.k var6 = ((org.anti_ad.a.b.a.a.a.a.k)((Y)var3.getInterpreter()).b.a.get(var3.getState())).a(0).d;
      boolean var10000;
      if (((Y)var3.getInterpreter()).b.a(var6, var3._ctx).c(var4)) {
         if (!var9.a) {
            var9.a = true;
            O var13 = var3.getCurrentToken();
            org.anti_ad.a.b.a.a.a.c.l var5 = var3.getExpectedTokens();
            String var10 = "missing " + var5.a(var3.getVocabulary()) + " at " + a(var13);
            var3.notifyErrorListeners(var13, var10, null);
         }

         var10000 = true;
      } else {
         var10000 = false;
      }

      if (var10000) {
         var3 = var1;
         O var14 = var1.getCurrentToken();
         org.anti_ad.a.b.a.a.a.c.l var15 = var3.getExpectedTokens();
         int var17 = 0;
         if (!var15.a()) {
            var17 = var15.c();
         }

         String var16;
         if (var17 == -1) {
            var16 = "<missing EOF>";
         } else {
            var16 = "<missing " + var3.getVocabulary().c(var17) + ">";
         }

         O var8 = var14;
         var2 = var3.getInputStream().f(-1);
         if (var8.a() == -1 && var2 != null) {
            var8 = var2;
         }

         return var3.getTokenFactory().a(new org.anti_ad.a.b.a.a.a.c.o(var8.i(), var8.i().getInputStream()), var17, var16, 0, -1, -1, var8.c(), var8.d());
      } else {
         w var7;
         if (this.d == null) {
            var7 = new w(var1);
         } else {
            var7 = new w(var1, this.e, this.d);
         }

         throw var7;
      }
   }

   private O d(D var1) {
      int var2 = var1.getInputStream().a(2);
      if (var1.getExpectedTokens().c(var2)) {
         this.c(var1);
         var1.consume();
         O var3 = var1.getCurrentToken();
         this.d();
         return var3;
      } else {
         return null;
      }
   }

   private static String a(O var0) {
      if (var0 == null) {
         return "<no token>";
      }

      String var1;
      if ((var1 = var0.b()) == null) {
         if (var0.a() == -1) {
            var1 = "<EOF>";
         } else {
            var1 = "<" + var0.a() + ">";
         }
      }

      return a(var1);
   }

   private static String a(String var0) {
      var0 = var0.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
      return "'" + var0 + "'";
   }

   private static org.anti_ad.a.b.a.a.a.c.l e(D var0) {
      org.anti_ad.a.b.a.a.a.a.a var1 = ((Y)var0.getInterpreter()).b;
      M var4 = var0._ctx;
      org.anti_ad.a.b.a.a.a.c.l var2 = new org.anti_ad.a.b.a.a.a.c.l();

      while (var4 != null && var4.invokingState >= 0) {
         ao var3 = (ao)((org.anti_ad.a.b.a.a.a.a.k)var1.a.get(var4.invokingState)).a(0);
         org.anti_ad.a.b.a.a.a.c.l var5 = var1.a(var3.b);
         var2.a(var5);
         var4 = var4.parent;
      }

      var2.e();
      return var2;
   }

   private static void a(D var0, org.anti_ad.a.b.a.a.a.c.l var1) {
      for (int var2 = var0.getInputStream().a(1); var2 != -1 && !var1.c(var2); var2 = var0.getInputStream().a(1)) {
         var0.consume();
      }
   }
}
