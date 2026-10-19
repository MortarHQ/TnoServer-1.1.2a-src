package org.anti_ad.mc.ipnext.item.rule.natives;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.rule.Rule;
import org.anti_ad.mc.ipnext.item.rule.parameter.NativeParametersKt;
import org.anti_ad.mc.ipnext.item.rule.parameter.NumberOrder;
import org.jetbrains.annotations.NotNull;

public final class NumberBasedRule extends TypeBasedRule {
   @NotNull
   private Function2 valueOf = NumberBasedRule::valueOf$lambda$0;

   public NumberBasedRule() {
      this.getArguments().defineParameter(NativeParametersKt.getNumber_order(), NumberOrder.ASCENDING);
      this.setComparator(NumberBasedRule::_init_$lambda$1);
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

   public final int compareNumber(@NotNull Number var1, @NotNull Number var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return ((NumberOrder)this.getArguments().get(NativeParametersKt.getNumber_order())).compare(var1, var2);
   }

   private static final int valueOf$lambda$0(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      return 0;
   }

   private static final int _init_$lambda$1(NumberBasedRule var0, ItemType var1, ItemType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Number var3 = (Number)var0.getValueOf().invoke(var0, var1);
      Number var4 = (Number)var0.getValueOf().invoke(var0, var2);
      return var0.compareNumber(var3, var4);
   }
}
