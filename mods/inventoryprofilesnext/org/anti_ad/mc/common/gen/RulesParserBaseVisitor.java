package org.anti_ad.mc.common.gen;

import org.anti_ad.a.b.a.a.a.d.a;

public class RulesParserBaseVisitor extends a implements RulesParserVisitor {
   @Override
   public Object visitCustomRuleEOF(RulesParser$CustomRuleEOFContext var1) {
      return this.visitChildren(var1);
   }

   @Override
   public Object visitSubRuleEOF(RulesParser$SubRuleEOFContext var1) {
      return this.visitChildren(var1);
   }

   @Override
   public Object visitHead(RulesParser$HeadContext var1) {
      return this.visitChildren(var1);
   }

   @Override
   public Object visitSubRule(RulesParser$SubRuleContext var1) {
      return this.visitChildren(var1);
   }

   @Override
   public Object visitSubRuleIdentifier(RulesParser$SubRuleIdentifierContext var1) {
      return this.visitChildren(var1);
   }

   @Override
   public Object visitArguments(RulesParser$ArgumentsContext var1) {
      return this.visitChildren(var1);
   }

   @Override
   public Object visitPair(RulesParser$PairContext var1) {
      return this.visitChildren(var1);
   }
}
