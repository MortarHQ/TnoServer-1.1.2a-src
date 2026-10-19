package org.anti_ad.mc.common.gen;

import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class RulesParser$SubRuleContext extends H {
   public RulesParser$SubRuleIdentifierContext subRuleIdentifier() {
      return (RulesParser$SubRuleIdentifierContext)this.getRuleContext(RulesParser$SubRuleIdentifierContext.class, 0);
   }

   public i REVERSE() {
      return this.getToken(4, 0);
   }

   public RulesParser$ArgumentsContext arguments() {
      return (RulesParser$ArgumentsContext)this.getRuleContext(RulesParser$ArgumentsContext.class, 0);
   }

   public RulesParser$SubRuleContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 3;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof RulesParserListener) {
         ((RulesParserListener)var1).enterSubRule(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof RulesParserListener) {
         ((RulesParserListener)var1).exitSubRule(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof RulesParserVisitor ? ((RulesParserVisitor)var1).visitSubRule(this) : var1.visitChildren(this);
   }
}
