package org.anti_ad.mc.ipnext.config;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.rule.Rule;
import org.anti_ad.mc.ipnext.item.rule.file.RuleFileRegister;
import org.anti_ad.mc.ipnext.parser.TemporaryRuleParser;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nConfigEnumsExt.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConfigEnumsExt.kt\norg/anti_ad/mc/ipnext/config/ConfigEnumsExtKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,38:1\n1#2:39\n*E\n"
)
public final class ConfigEnumsExtKt {
   @NotNull
   public static final Rule rule(@NotNull SortingMethodIndividual var0, @NotNull String var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      switch (var0) {
         case GLOBAL:
            return getRule((SortingMethod)ModSettings.INSTANCE.getSORT_ORDER().getValue());
         case CUSTOM:
            return TemporaryRuleParser.INSTANCE.parse(var1);
         default:
            return getRule(SortingMethod.values()[var0.ordinal() - 1]);
      }
   }

   private static final Rule getRule(SortingMethod var0) {
      String var10000 = var0.getRuleName();
      if (var10000 != null) {
         String var1 = var10000;
         Rule var2 = RuleFileRegister.INSTANCE.getCustomRuleOrEmpty(var1);
         if (var2 != null) {
            return var2;
         }
      }

      return TemporaryRuleParser.INSTANCE.parse(ModSettings.INSTANCE.getCUSTOM_RULE().getValue());
   }
}
