package org.anti_ad.mc.ipnext.item.rule.natives;

import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.extensions.ByPropertyName;
import org.anti_ad.mc.ipnext.item.rule.ArgumentMap;
import org.anti_ad.mc.ipnext.item.rule.Parameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nDefinedNativeRules.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DefinedNativeRules.kt\norg/anti_ad/mc/ipnext/item/rule/natives/TypeBasedRuleProvider\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,159:1\n1#2:160\n1869#3,2:161\n1869#3,2:163\n*S KotlinDebug\n*F\n+ 1 DefinedNativeRules.kt\norg/anti_ad/mc/ipnext/item/rule/natives/TypeBasedRuleProvider\n*L\n64#1:161,2\n72#1:163,2\n*E\n"
)
final class TypeBasedRuleProvider extends ByPropertyName {
   @NotNull
   private final List args;
   @NotNull
   private final List postActions;

   public TypeBasedRuleProvider(@NotNull Function0 var1, @Nullable Function2 var2, @NotNull List var3, @NotNull List var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      super(TypeBasedRuleProvider::_init_$lambda$8);
      this.args = var3;
      this.postActions = var4;
   }

   @NotNull
   public final List getArgs() {
      return this.args;
   }

   @NotNull
   public final List getPostActions() {
      return this.postActions;
   }

   @NotNull
   public final TypeBasedRuleProvider param(@NotNull Parameter var1, @NotNull Object var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      TypeBasedRuleProvider var3 = this;
      this.args.add(TuplesKt.to(var1, var2));
      return var3;
   }

   @NotNull
   public final TypeBasedRuleProvider param(@NotNull Parameter var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      TypeBasedRuleProvider var2 = this;
      this.args.add(TuplesKt.to(var1, null));
      return var2;
   }

   @NotNull
   public final TypeBasedRuleProvider post(@NotNull Function1 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      TypeBasedRuleProvider var2 = this;
      this.postActions.add(var1);
      return var2;
   }

   private static final TypeBasedRule _init_$lambda$8$lambda$6(Function0 var0, Function2 var1, List var2, List var3) {
      Object var7;
      TypeBasedRule var4 = (TypeBasedRule)(var7 = var0.invoke());
      if (var1 != null) {
         var1 = var1;
         var4.setValueOf(var1);
      }

      ArgumentMap var9 = var4.getArguments();
      Iterator var11 = var3.iterator();

      while (var11.hasNext()) {
         Pair var5;
         Parameter var6 = (Parameter)(var5 = (Pair)var11.next()).component1();
         Object var12;
         if ((var12 = var5.component2()) != null) {
            Intrinsics.checkNotNull(var6);
            var9.defineParameter(var6, var12);
         } else {
            var9.defineParameter(var6);
         }
      }

      Iterator var10 = var2.iterator();

      while (var10.hasNext()) {
         ((Function1)var10.next()).invoke(var4);
      }

      return (TypeBasedRule)var7;
   }

   private static final Function0 _init_$lambda$8(Function0 var0, Function2 var1, List var2, List var3, ByPropertyName var4, String var5) {
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      Function0 var6;
      Function0 var7 = var6 = TypeBasedRuleProvider::_init_$lambda$8$lambda$6;
      DefinedNativeRulesKt.access$getNATIVE_MAP$p().put(var5, var7);
      return var6;
   }
}
