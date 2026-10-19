package org.anti_ad.mc.ipnext.item.rule.parameter;

import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.extensions.Kt_commonKt;
import org.anti_ad.mc.ipnext.item.rule.ArgumentType;
import org.anti_ad.mc.ipnext.item.rule.Rule;
import org.anti_ad.mc.ipnext.parser.TemporaryRuleParser;
import org.jetbrains.annotations.NotNull;

public final class RuleArgumentType implements ArgumentType {
   @NotNull
   public static final RuleArgumentType INSTANCE = new RuleArgumentType();

   private RuleArgumentType() {
   }

   @NotNull
   public final String toString(@NotNull Rule var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return "[" + Kt_commonKt.getUsefulName(var1.getClass()) + "]";
   }

   @NotNull
   public final Rule parse(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return TemporaryRuleParser.INSTANCE.parse(var1);
   }
}
