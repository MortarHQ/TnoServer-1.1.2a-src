package org.anti_ad.mc.common.gen;

import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;

public class RulesParser$CustomRuleEOFContext extends H {
   public RulesParser$HeadContext head() {
      return (RulesParser$HeadContext)this.getRuleContext(RulesParser$HeadContext.class, 0);
   }

   public RulesParser$SubRuleEOFContext subRuleEOF() {
      return (RulesParser$SubRuleEOFContext)this.getRuleContext(RulesParser$SubRuleEOFContext.class, 0);
   }

   public RulesParser$CustomRuleEOFContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 0;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof RulesParserListener) {
         ((RulesParserListener)var1).enterCustomRuleEOF(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof RulesParserListener) {
         ((RulesParserListener)var1).exitCustomRuleEOF(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof RulesParserVisitor ? ((RulesParserVisitor)var1).visitCustomRuleEOF(this) : var1.visitChildren(this);
   }
}
