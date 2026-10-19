package org.anti_ad.mc.ipnext.item.rule;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.rule.parameter.NativeParametersKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseRule implements Rule {
   @NotNull
   private final ArgumentMap arguments = new ArgumentMap();
   @NotNull
   private final Function2 defaultComparator = BaseRule::defaultComparator$lambda$0;
   @Nullable
   private Exception createdHere;
   @NotNull
   private Function2 comparator = this.defaultComparator;
   @NotNull
   private final Lazy lazyCompare$delegate;

   public BaseRule() {
      ArgumentMap var1;
      (var1 = this.arguments).defineParameter(NativeParametersKt.getReverse(), Boolean.FALSE);
      var1.defineParameter(NativeParametersKt.getSub_rule(), EmptyRule.INSTANCE);
      this.lazyCompare$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, BaseRule::lazyCompare_delegate$lambda$4);
   }

   @NotNull
   @Override
   public final ArgumentMap getArguments() {
      return this.arguments;
   }

   @NotNull
   public final Function2 getComparator() {
      Exception var1 = this.createdHere;
      if (this.comparator == this.defaultComparator) {
         Log.INSTANCE.trace("Using default comparator");
      } else {
         Log var10000 = Log.INSTANCE;
         String var10001 = "Using comparator set by user: " + this.comparator + ", created at:";
         Intrinsics.checkNotNull(var1);
         var10000.trace(var10001, var1);
      }

      return this.comparator;
   }

   public final void setComparator(@NotNull Function2 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (this.comparator != this.defaultComparator) {
         Log.INSTANCE.trace("Overwriting comparator form  " + this.comparator + " to " + var1, new Exception());
      }

      this.comparator = var1;
      if (var1 != this.defaultComparator) {
         this.createdHere = new Exception();
      }
   }

   private final Function2 getLazyCompare() {
      return (Function2)this.lazyCompare$delegate.getValue();
   }

   @Override
   public final int compare(@NotNull ItemType var1, @NotNull ItemType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return ((Number)this.getLazyCompare().invoke(var1, var2)).intValue();
   }

   private static final int defaultComparator$lambda$0(ItemType var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      return 0;
   }

   private static final int lazyCompare_delegate$lambda$4$lambda$2(BaseRule var0, int var1, ItemType var2, ItemType var3) {
      return ((Number)var0.getComparator().invoke(var2, var3)).intValue() * var1;
   }

   private static final int lazyCompare_delegate$lambda$4$lambda$3(BaseRule var0, int var1, Rule var2, ItemType var3, ItemType var4) {
      int var5;
      return (var5 = ((Number)var0.getComparator().invoke(var3, var4)).intValue()) != 0 ? var5 * var1 : var2.compare(var3, var4);
   }

   private static final Function2 lazyCompare_delegate$lambda$4(BaseRule var0) {
      int var1 = var0.arguments.get(NativeParametersKt.getReverse()) ? -1 : 1;
      if (var0.arguments.isDefaultValue(NativeParametersKt.getSub_rule())) {
         return BaseRule::lazyCompare_delegate$lambda$4$lambda$2;
      }

      Rule var2 = (Rule)var0.arguments.get(NativeParametersKt.getSub_rule());
      return BaseRule::lazyCompare_delegate$lambda$4$lambda$3;
   }
}
