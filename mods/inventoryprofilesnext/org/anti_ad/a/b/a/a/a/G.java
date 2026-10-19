package org.anti_ad.a.b.a.a.a;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import org.anti_ad.a.b.a.a.a.a.Y;
import org.anti_ad.a.b.a.a.a.a.aC;
import org.anti_ad.a.b.a.a.a.a.ac;
import org.anti_ad.a.b.a.a.a.a.ae;
import org.anti_ad.a.b.a.a.a.a.ag;
import org.anti_ad.a.b.a.a.a.a.am;
import org.anti_ad.a.b.a.a.a.a.ao;
import org.anti_ad.a.b.a.a.a.a.az;

public final class G extends D {
   private String a;
   private org.anti_ad.a.b.a.a.a.a.a b;
   private org.anti_ad.a.b.a.a.a.b.a[] c;
   private ag d = new ag();
   @Deprecated
   private String[] e;
   private String[] f;
   private final S g;
   private Deque h = new ArrayDeque();
   private int i = -1;
   private int j = -1;
   private int k = -1;
   private boolean l = false;
   private y m = null;
   private y n;

   public G(String var1, S var2, Collection var3, org.anti_ad.a.b.a.a.a.a.a var4, s var5) {
      super(var5);
      this.a = var1;
      this.b = var4;
      this.e = new String[var4.f];

      for (int var6 = 0; var6 < this.e.length; var6++) {
         this.e[var6] = var2.c(var6);
      }

      this.f = var3.toArray(new String[0]);
      this.g = var2;
      int var7 = var4.a();
      this.c = new org.anti_ad.a.b.a.a.a.b.a[var7];

      for (int var8 = 0; var8 < var7; var8++) {
         org.anti_ad.a.b.a.a.a.a.y var9 = var4.a(var8);
         this.c[var8] = new org.anti_ad.a.b.a.a.a.b.a(var9, var8);
      }

      this.setInterpreter(new Y(this, var4, this.c, this.d));
   }

   @Override
   public final void reset() {
      super.reset();
      this.l = false;
      this.m = null;
   }

   @Override
   public final org.anti_ad.a.b.a.a.a.a.a getATN() {
      return this.b;
   }

   @Deprecated
   @Override
   public final String[] getTokenNames() {
      return this.e;
   }

   @Override
   public final S getVocabulary() {
      return this.g;
   }

   @Override
   public final String[] getRuleNames() {
      return this.f;
   }

   @Override
   public final String getGrammarFileName() {
      return this.a;
   }

   public final H a(int var1) {
      am var2 = this.b.c[var1];
      this.n = a(null, -1, var1);
      if (var2.h) {
         this.enterRecursionRule(this.n, var2.b, var1, 0);
      } else {
         this.enterRule(this.n, var2.b, var1);
      }

      while (true) {
         G var3 = this;
         org.anti_ad.a.b.a.a.a.a.k var9;
         switch ((var9 = (org.anti_ad.a.b.a.a.a.a.k)this.b.a.get(var3.getState())).b()) {
            case 7:
               if (super._ctx.isEmpty()) {
                  if (var2.h) {
                     H var14 = super._ctx;
                     org.anti_ad.a.b.a.a.a.c.o var10 = (org.anti_ad.a.b.a.a.a.c.o)this.h.pop();
                     this.unrollRecursionContexts((H)var10.a);
                     return var14;
                  }

                  this.exitRule();
                  return this.n;
               }

               org.anti_ad.a.b.a.a.a.a.k var19 = var9;
               var3 = this;
               if (this.b.c[var19.c].h) {
                  org.anti_ad.a.b.a.a.a.c.o var26 = (org.anti_ad.a.b.a.a.a.c.o)var3.h.pop();
                  var3.unrollRecursionContexts((H)var26.a);
                  var3.setState((Integer)var26.b);
               } else {
                  var3.exitRule();
               }

               ao var27 = (ao)((org.anti_ad.a.b.a.a.a.a.k)var3.b.a.get(var3.getState())).a(0);
               var3.setState(var27.b.b);
               continue;
         }

         try {
            org.anti_ad.a.b.a.a.a.a.k var15 = var9;
            var3 = this;
            int var22 = 1;
            if (var15 instanceof org.anti_ad.a.b.a.a.a.a.y) {
               org.anti_ad.a.b.a.a.a.a.y var23 = (org.anti_ad.a.b.a.a.a.a.y)var15;
               G var29 = var3;
               int var33 = 1;
               if (var23.a() > 1) {
                  var29.getErrorHandler().b(var29);
                  if ((var22 = var23.h) == var29.i && var29._input.b() == var29.j && !var29.l) {
                     var33 = var29.k;
                     var29.l = true;
                  } else {
                     var33 = ((Y)var29.getInterpreter()).a(var29._input, var22, var29._ctx);
                  }
               }

               var22 = var33;
            }

            aC var25;
            switch ((var25 = var15.a(var22 - 1)).a()) {
               case 1:
                  if (var15.b() == 10 && ((az)var15).j && !(var25.d instanceof org.anti_ad.a.b.a.a.a.a.T)) {
                     y var32 = a(
                        (H)((org.anti_ad.a.b.a.a.a.c.o)var3.h.peek()).a, (Integer)((org.anti_ad.a.b.a.a.a.c.o)var3.h.peek()).b, var3._ctx.getRuleIndex()
                     );
                     var3.pushNewRecursionContext(var32, var3.b.c[var15.c].b, var3._ctx.getRuleIndex());
                  }
                  break;
               case 2:
               case 7:
               case 8:
                  if (!var25.a(var3._input.a(1), 1, 65535)) {
                     G var31 = var3;
                     var3._errHandler.a(var31);
                  }

                  var3.matchWildcard();
                  break;
               case 3:
                  am var30;
                  int var34 = (var30 = (am)var25.d).c;
                  y var18 = a(var3._ctx, var15.b, var34);
                  if (var30.h) {
                     var3.enterRecursionRule(var18, var30.b, var34, ((ao)var25).a);
                  } else {
                     var3.enterRule(var18, var25.d.b, var34);
                  }
                  break;
               case 4:
                  ae var17 = (ae)var25;
                  if (!var3.sempred(var3._ctx, var17.a, var17.b)) {
                     throw new v(var3);
                  }
                  break;
               case 5:
                  var3.match(((org.anti_ad.a.b.a.a.a.a.q)var25).a);
                  break;
               case 6:
                  org.anti_ad.a.b.a.a.a.a.n var16 = (org.anti_ad.a.b.a.a.a.a.n)var25;
                  var3.action(var3._ctx, var16.a, var16.b);
                  break;
               case 9:
                  var3.matchWildcard();
                  break;
               case 10:
                  if (!var3.precpred(var3._ctx, ((ac)var25).a)) {
                     throw new v(var3, String.format("precpred(_ctx, %d)", ((ac)var25).a));
                  }
                  break;
               default:
                  throw new UnsupportedOperationException("Unrecognized ATN transition type.");
            }

            var3.setState(var25.d.b);
         } catch (J var8) {
            this.setState(this.b.d[var9.c].b);
            this.getContext().exception = var8;
            this.getErrorHandler().b(this, var8);
            var3 = this;
            int var5 = super._input.b();
            var3.getErrorHandler().a(var3, var8);
            if (var3._input.b() == var5) {
               if (var8 instanceof w) {
                  w var20 = (w)var8;
                  O var6 = var8.d();
                  int var7 = 0;
                  if (!var20.c().a()) {
                     var7 = var20.c().c();
                  }

                  q var4 = var3.getTokenFactory()
                     .a(new org.anti_ad.a.b.a.a.a.c.o(var6.i(), var6.i().getInputStream()), var7, var6.b(), 0, -1, -1, var6.c(), var6.d());
                  var3._ctx.addErrorNode(var3.createErrorNode(var3._ctx, var4));
               } else {
                  O var21 = var8.d();
                  q var28 = var3.getTokenFactory()
                     .a(new org.anti_ad.a.b.a.a.a.c.o(var21.i(), var21.i().getInputStream()), 0, var21.b(), 0, -1, -1, var21.c(), var21.d());
                  var3._ctx.addErrorNode(var3.createErrorNode(var3._ctx, var28));
               }
            }
         }
      }
   }

   @Override
   public final void enterRecursionRule(H var1, int var2, int var3, int var4) {
      org.anti_ad.a.b.a.a.a.c.o var5 = new org.anti_ad.a.b.a.a.a.c.o(super._ctx, var1.invokingState);
      this.h.push(var5);
      super.enterRecursionRule(var1, var2, var3, var4);
   }

   private static y a(H var0, int var1, int var2) {
      return new y(var0, var1, var2);
   }
}
