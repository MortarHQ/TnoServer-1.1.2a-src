package org.anti_ad.mc.common.gen;

import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class RulesParser$PairContext extends H {
   public i Parameter() {
      return this.getToken(12, 0);
   }

   public i EQUAL() {
      return this.getToken(13, 0);
   }

   public i Argument() {
      return this.getToken(18, 0);
   }

   public RulesParser$PairContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 6;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof RulesParserListener) {
         ((RulesParserListener)var1).enterPair(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof RulesParserListener) {
         ((RulesParserListener)var1).exitPair(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof RulesParserVisitor ? ((RulesParserVisitor)var1).visitPair(this) : var1.visitChildren(this);
   }
}
