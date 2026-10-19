package org.anti_ad.mc.ipnext.item.rule.file;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.anti_ad.mc.common.annotation.MayThrow;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.item.rule.Parameter;
import org.anti_ad.mc.ipnext.item.rule.Rule;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nSubRuleDefinition.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SubRuleDefinition.kt\norg/anti_ad/mc/ipnext/item/rule/file/SubRuleDefinition\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,72:1\n1563#2:73\n1634#2,3:74\n*S KotlinDebug\n*F\n+ 1 SubRuleDefinition.kt\norg/anti_ad/mc/ipnext/item/rule/file/SubRuleDefinition\n*L\n40#1:73\n40#1:74,3\n*E\n"
)
public final class SubRuleDefinition {
   @NotNull
   private final String prefix;
   @NotNull
   private final String name;
   @NotNull
   private final List arguments;

   public SubRuleDefinition(@NotNull String var1, @NotNull String var2, @NotNull List var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      super();
      this.prefix = var1;
      this.name = var2;
      Iterable var7 = var3;
      SubRuleDefinition var5 = this;
      Iterable var9 = var7;
      var var8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));

      for (Object var11 : var9) {
         Pair var12 = (Pair)var11;
         ArrayList var6 = var8;
         String var4 = (String)var12.component1();
         String var13 = (String)var12.component2();
         var6.add(TuplesKt.to(StringsKt.trim(var4).toString(), StringsKt.trim(var13).toString()));
      }

      var5.arguments = var8;
   }

   @NotNull
   public final String getPrefix() {
      return this.prefix;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   @NotNull
   public final List getArguments() {
      return this.arguments;
   }

   private final String getIdentifier() {
      return this.prefix + this.name;
   }

   @MayThrow
   @NotNull
   public final Rule toRule() {
      String var2 = this.prefix;
      Rule var1;
      if ((
            var1 = Intrinsics.areEqual(this.prefix, "@")
               ? RuleFileRegister.INSTANCE.getCustomRule(this.name, false)
               : (Intrinsics.areEqual(var2, "::") ? RuleFileRegister.INSTANCE.getNativeRule(this.name) : null)
         )
         == null) {
         throw new NoSuchElementException("rule " + this.getIdentifier() + " is broken rule or does not exist");
      }

      Iterator var6 = this.arguments.iterator();

      while (var6.hasNext()) {
         Pair var3;
         String var4 = (String)(var3 = (Pair)var6.next()).component1();
         String var8 = (String)var3.component2();
         if (!var1.getArguments().contains(var4)) {
            Log.INSTANCE.warn("Rule " + this.getIdentifier() + " has no parameter " + var4);
         } else {
            Parameter var5;
            if ((var5 = RuleFileRegister.INSTANCE.getParameter(var4)) == null) {
               throw new IllegalStateException((this.getIdentifier() + " defined nonexistent parameter " + var4).toString());
            }

            if (!var1.getArguments().trySetArgument(var5, var8)) {
               Log.INSTANCE.warn("Failed to parse argument '" + var8 + "' for parameter " + var4);
            }
         }
      }

      List var7;
      if (!(var7 = var1.getArguments().getMissingParameters()).isEmpty()) {
         throw new MissingParameterException(
            "required parameter is missing: " + CollectionsKt.joinToString$default(var7, null, null, null, 0, null, null, 63, null)
         );
      } else {
         return var1;
      }
   }
}
