package org.anti_ad.mc.ipnext.item.rule.natives;

import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.rule.ArgumentMap;
import org.anti_ad.mc.ipnext.item.rule.parameter.Match;
import org.anti_ad.mc.ipnext.item.rule.parameter.NativeParametersKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nNativeRule.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NativeRule.kt\norg/anti_ad/mc/ipnext/item/rule/natives/ByNbtRule\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,456:1\n1869#2,2:457\n1869#2,2:459\n*S KotlinDebug\n*F\n+ 1 NativeRule.kt\norg/anti_ad/mc/ipnext/item/rule/natives/ByNbtRule\n*L\n282#1:457,2\n292#1:459,2\n*E\n"
)
public final class ByNbtRule extends NativeRule {
   @NotNull
   private final StringBasedRule dummyStringRule = new StringBasedRule();
   @NotNull
   private final NumberBasedRule dummyNumberRule = new NumberBasedRule();
   @NotNull
   private final List dummies;
   private boolean initialized;

   public ByNbtRule() {
      TypeBasedRule[] var1;
      (var1 = new TypeBasedRule[2])[0] = this.dummyStringRule;
      var1[1] = this.dummyNumberRule;
      this.dummies = CollectionsKt.listOf(var1);
      ArgumentMap var4;
      (var4 = this.getArguments()).defineParameter(NativeParametersKt.getComponent_id());
      var4.defineParameter(NativeParametersKt.getNbt_path());
      var4.defineParameter(NativeParametersKt.getNot_found(), Match.LAST);

      for (TypeBasedRule var3 : this.dummies) {
         var4.defineParametersFrom(var3.getArguments());
      }

      this.setComparator(new ByNbtRule$1$2(this));
   }

   @NotNull
   public final StringBasedRule getDummyStringRule() {
      return this.dummyStringRule;
   }

   @NotNull
   public final NumberBasedRule getDummyNumberRule() {
      return this.dummyNumberRule;
   }

   @NotNull
   public final List getDummies() {
      return this.dummies;
   }

   public final boolean getInitialized() {
      return this.initialized;
   }

   public final void setInitialized(boolean var1) {
      this.initialized = var1;
   }

   public final void initializeDummyArguments() {
      if (!this.initialized) {
         this.initialized = true;
         Iterator var1 = this.dummies.iterator();

         while (var1.hasNext()) {
            ((TypeBasedRule)var1.next()).getArguments().setArgumentsFrom(this.getArguments());
         }
      }
   }

   public final int compareByNbtPath(@NotNull ItemType var1, @NotNull ItemType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      this.initializeDummyArguments();
      return new ByNbtRule$ByNbtPathComparator(this, var1, var2).compare();
   }
}
