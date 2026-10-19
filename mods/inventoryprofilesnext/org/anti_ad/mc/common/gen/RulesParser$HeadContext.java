package org.anti_ad.mc.common.gen;

import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class RulesParser$HeadContext extends H {
   public i AT() {
      return this.getToken(2, 0);
   }

   public i RuleName() {
      return this.getToken(11, 0);
   }

   public RulesParser$HeadContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 2;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof RulesParserListener) {
         ((RulesParserListener)var1).enterHead(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof RulesParserListener) {
         ((RulesParserListener)var1).exitHead(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof RulesParserVisitor ? ((RulesParserVisitor)var1).visitHead(this) : var1.visitChildren(this);
   }
}
