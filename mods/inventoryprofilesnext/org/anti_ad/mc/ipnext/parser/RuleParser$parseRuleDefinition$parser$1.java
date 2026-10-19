package org.anti_ad.mc.ipnext.parser;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.anti_ad.a.b.a.a.a.g;
import org.anti_ad.mc.common.gen.RulesLexer;

// $VF: synthetic class
final class RuleParser$parseRuleDefinition$parser$1 extends FunctionReferenceImpl implements Function1 {
   public static final RuleParser$parseRuleDefinition$parser$1 INSTANCE = new RuleParser$parseRuleDefinition$parser$1();

   RuleParser$parseRuleDefinition$parser$1() {
      super(1, RulesLexer.class, "<init>", "<init>(Lorg/antlr/v4/runtime/CharStream;)V", 0);
   }

   public final RulesLexer invoke(g var1) {
      return new RulesLexer(var1);
   }
}
