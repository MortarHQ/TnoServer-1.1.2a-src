package org.anti_ad.mc.ipnext.item.rule.natives;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.anti_ad.mc.common.util.LogicalStringComparator;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.rule.ArgumentMap;
import org.anti_ad.mc.ipnext.item.rule.Rule;
import org.anti_ad.mc.ipnext.item.rule.parameter.Match;
import org.anti_ad.mc.ipnext.item.rule.parameter.NativeParametersKt;
import org.anti_ad.mc.ipnext.item.rule.parameter.Strength;
import org.anti_ad.mc.ipnext.item.rule.parameter.StringCompare;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nNativeRule.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NativeRule.kt\norg/anti_ad/mc/ipnext/item/rule/natives/StringBasedRule\n+ 2 NativeRule.kt\norg/anti_ad/mc/ipnext/item/rule/natives/NativeRuleKt\n+ 3 kt_common.kt\norg/anti_ad/mc/common/extensions/Kt_commonKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,456:1\n218#2,7:457\n96#3:464\n57#3:466\n1#4:465\n*S KotlinDebug\n*F\n+ 1 NativeRule.kt\norg/anti_ad/mc/ipnext/item/rule/natives/StringBasedRule\n*L\n107#1:457,7\n96#1:464\n102#1:466\n*E\n"
)
public final class StringBasedRule extends TypeBasedRule {
   @NotNull
   private Function2 valueOf = StringBasedRule::valueOf$lambda$0;
   @NotNull
   private final Lazy lazyCompareString$delegate;

   public StringBasedRule() {
      ArgumentMap var1;
      (var1 = this.getArguments()).defineParameter(NativeParametersKt.getBlank_string(), Match.LAST);
      var1.defineParameter(NativeParametersKt.getString_compare(), StringCompare.LOCALE);
      var1.defineParameter(NativeParametersKt.getLocale(), "mc");
      var1.defineParameter(NativeParametersKt.getStrength(), Strength.PRIMARY);
      var1.defineParameter(NativeParametersKt.getLogical(), Boolean.TRUE);
      this.setComparator(StringBasedRule::_init_$lambda$2);
      this.lazyCompareString$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, StringBasedRule::lazyCompareString_delegate$lambda$8);
   }

   @NotNull
   @Override
   public final Function2 getValueOf() {
      return this.valueOf;
   }

   @Override
   public final void setValueOf(@NotNull Function2 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.valueOf = var1;
   }

   private final Comparator getLazyCompareString() {
      return (Comparator)this.lazyCompareString$delegate.getValue();
   }

   public final int compareString(@NotNull String var1, @NotNull String var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Match var3 = (Match)this.getArguments().get(NativeParametersKt.getBlank_string());
      Comparator var4 = this.getLazyCompareString();
      boolean var6 = StringsKt.isBlank(var1);
      boolean var5 = StringsKt.isBlank(var2);
      return var6 == var5 ? var4.compare(var1, var2) : var3.getMultiplier() * (var6 ? -1 : 1);
   }

   private static final String valueOf$lambda$0(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      return "";
   }

   private static final int _init_$lambda$2(StringBasedRule var0, ItemType var1, ItemType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return var0.compareString((String)var0.getValueOf().invoke(var0, var1), (String)var0.getValueOf().invoke(var0, var2));
   }

   private static final Comparator lazyCompareString_delegate$lambda$8(StringBasedRule var0) {
      Comparator var10000 = ((StringCompare)var0.getArguments().get(NativeParametersKt.getString_compare())).getComparator();
      if (var10000 == null) {
         StringBasedRule var1 = var0;
         Object var2;
         String var5;
         Locale var6 = Intrinsics.areEqual(
               var5 = StringsKt.replace$default(
                  (String)(
                     Intrinsics.areEqual((String)(var2 = var0.getArguments().get(NativeParametersKt.getLocale())), "mc")
                        ? VanillaUtil.INSTANCE.languageCode()
                        : var2
                  ),
                  '_',
                  '-',
                  false,
                  4,
                  null
               ),
               "sys"
            )
            ? Locale.getDefault()
            : Locale.forLanguageTag(var5);
         int var3 = ((Strength)var1.getArguments().get(NativeParametersKt.getStrength())).getValue();
         Collator var7;
         (var7 = Collator.getInstance(var6)).setStrength(var3);
         Intrinsics.checkNotNullExpressionValue(var7, "");
         var10000 = var7;
      }

      Comparator var4 = var10000;
      return var0.getArguments().get(NativeParametersKt.getLogical()) ? (Comparator)(new LogicalStringComparator(var4)) : var4;
   }
}
