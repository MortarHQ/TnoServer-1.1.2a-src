package org.anti_ad.mc.ipnext.item.rule.file;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.annotation.MayThrow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nRuleDefinition.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RuleDefinition.kt\norg/anti_ad/mc/ipnext/item/rule/file/RuleDefinition\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,75:1\n1563#2:76\n1634#2,3:77\n1#3:80\n*S KotlinDebug\n*F\n+ 1 RuleDefinition.kt\norg/anti_ad/mc/ipnext/item/rule/file/RuleDefinition\n*L\n57#1:76\n57#1:77,3\n*E\n"
)
public final class RuleDefinition {
   @NotNull
   private final String ruleName;
   @NotNull
   private final List subRules;
   @NotNull
   private RuleDefinition$Status status;
   @Nullable
   private List ruleList;

   public RuleDefinition(@NotNull String var1, @NotNull List var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.ruleName = var1;
      this.subRules = var2;
      this.status = RuleDefinition$Status.LAZY;
   }

   @NotNull
   public final String getRuleName() {
      return this.ruleName;
   }

   @NotNull
   public final RuleDefinition$Status getStatus() {
      return this.status;
   }

   @MayThrow
   @Nullable
   public final List getRuleList() {
      if (this.status == RuleDefinition$Status.INITIALIZING) {
         this.status = RuleDefinition$Status.FAILED;
         throw new SelfReferenceException("loop (?) detected");
      }

      if (this.status == RuleDefinition$Status.LAZY) {
         this.status = RuleDefinition$Status.INITIALIZING;
         List var1 = this.subRules;
         RuleDefinition var4 = this;
         Iterable var2 = var1;
         var1 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));

         for (SubRuleDefinition var3 : var2) {
            var3 = var3;
            ArrayList var5 = var1;
            var5.add(var3.toRule());
         }

         var1 = var1;
         var4 = var4;
         this.status = RuleDefinition$Status.SUCCESS;
         var4.ruleList = var1;
      }

      return this.ruleList;
   }

   @MayThrow
   @Nullable
   public final CustomRule createCustomRule() {
      List var10000 = this.getRuleList();
      if (var10000 != null) {
         List var1 = var10000;
         return new CustomRule(var1);
      } else {
         return null;
      }
   }
}
