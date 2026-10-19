package org.anti_ad.mc.common.gen;

import java.util.List;
import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class RulesParser$ArgumentsContext extends H {
   public i OPEN() {
      return this.getToken(8, 0);
   }

   public List pair() {
      return this.getRuleContexts(RulesParser$PairContext.class);
   }

   public RulesParser$PairContext pair(int var1) {
      return (RulesParser$PairContext)this.getRuleContext(RulesParser$PairContext.class, var1);
   }

   public i CLOSE() {
      return this.getToken(14, 0);
   }

   public List COMMA() {
      return this.getTokens(16);
   }

   public i COMMA(int var1) {
      return this.getToken(16, var1);
   }

   public RulesParser$ArgumentsContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 5;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof RulesParserListener) {
         ((RulesParserListener)var1).enterArguments(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof RulesParserListener) {
         ((RulesParserListener)var1).exitArguments(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof RulesParserVisitor ? ((RulesParserVisitor)var1).visitArguments(this) : var1.visitChildren(this);
   }
}
