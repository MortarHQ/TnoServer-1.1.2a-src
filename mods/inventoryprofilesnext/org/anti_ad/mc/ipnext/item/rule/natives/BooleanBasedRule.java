package org.anti_ad.mc.ipnext.item.rule.natives;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.rule.ArgumentMap;
import org.anti_ad.mc.ipnext.item.rule.EmptyRule;
import org.anti_ad.mc.ipnext.item.rule.Rule;
import org.anti_ad.mc.ipnext.item.rule.parameter.Match;
import org.anti_ad.mc.ipnext.item.rule.parameter.NativeParametersKt;
import org.jetbrains.annotations.NotNull;

public class BooleanBasedRule extends TypeBasedRule {
   @NotNull
   private Function2 valueOf = BooleanBasedRule::valueOf$lambda$0;

   public BooleanBasedRule() {
      ArgumentMap var1;
      (var1 = this.getArguments()).defineParameter(NativeParametersKt.getMatch(), Match.FIRST);
      var1.defineParameter(NativeParametersKt.getSub_rule_match(), EmptyRule.INSTANCE);
      var1.defineParameter(NativeParametersKt.getSub_rule_not_match(), EmptyRule.INSTANCE);
      this.setComparator(BooleanBasedRule::_init_$lambda$3);
   }

   @NotNull
   @Override
   public Function2 getValueOf() {
      return this.valueOf;
   }

   @Override
   public void setValueOf(@NotNull Function2 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.valueOf = var1;
   }

   public final void andValue(@NotNull Function2 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Function2 var2 = this.getValueOf();
      this.setValueOf(BooleanBasedRule::andValue$lambda$4);
   }

   private static final boolean valueOf$lambda$0(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      return false;
   }

   private static final boolean lambda$3$lambda$2(BooleanBasedRule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return (Boolean)var0.getValueOf().invoke(var0, var1);
   }

   private static final int _init_$lambda$3(BooleanBasedRule var0, ItemType var1, ItemType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return NativeRuleKt.compareByMatchSeparate(
         var1,
         var2,
         BooleanBasedRule::lambda$3$lambda$2,
         (Match)var0.getArguments().get(NativeParametersKt.getMatch()),
         new BooleanBasedRule$2$2(var0.getArguments().get(NativeParametersKt.getSub_rule_match())),
         new BooleanBasedRule$2$3(var0.getArguments().get(NativeParametersKt.getSub_rule_not_match()))
      );
   }

   private static final boolean andValue$lambda$4(Function2 var0, Function2 var1, Rule var2, ItemType var3) {
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      return (Boolean)var0.invoke(var2, var3) && (Boolean)var1.invoke(var2, var3);
   }
}
