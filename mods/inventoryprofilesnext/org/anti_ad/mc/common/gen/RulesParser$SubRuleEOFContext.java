package org.anti_ad.mc.common.gen;

import java.util.List;
import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class RulesParser$SubRuleEOFContext extends H {
   public i EOF() {
      return this.getToken(-1, 0);
   }

   public List subRule() {
      return this.getRuleContexts(RulesParser$SubRuleContext.class);
   }

   public RulesParser$SubRuleContext subRule(int var1) {
      return (RulesParser$SubRuleContext)this.getRuleContext(RulesParser$SubRuleContext.class, var1);
   }

   public RulesParser$SubRuleEOFContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 1;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof RulesParserListener) {
         ((RulesParserListener)var1).enterSubRuleEOF(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof RulesParserListener) {
         ((RulesParserListener)var1).exitSubRuleEOF(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof RulesParserVisitor ? ((RulesParserVisitor)var1).visitSubRuleEOF(this) : var1.visitChildren(this);
   }
}
