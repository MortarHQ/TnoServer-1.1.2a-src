package org.anti_ad.mc.common.gen;

import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class RulesParser$SubRuleIdentifierContext extends H {
   public i RuleName() {
      return this.getToken(11, 0);
   }

   public i AT() {
      return this.getToken(2, 0);
   }

   public i DOUBLE_COLON() {
      return this.getToken(5, 0);
   }

   public i NamespacedId() {
      return this.getToken(7, 0);
   }

   public i HASHTAG() {
      return this.getToken(6, 0);
   }

   public i NBT() {
      return this.getToken(9, 0);
   }

   public RulesParser$SubRuleIdentifierContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 4;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof RulesParserListener) {
         ((RulesParserListener)var1).enterSubRuleIdentifier(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof RulesParserListener) {
         ((RulesParserListener)var1).exitSubRuleIdentifier(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof RulesParserVisitor ? ((RulesParserVisitor)var1).visitSubRuleIdentifier(this) : var1.visitChildren(this);
   }
}
