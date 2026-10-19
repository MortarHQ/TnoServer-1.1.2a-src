package org.anti_ad.mc.ipnext.inventory.action;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.random.RandomKt;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nMisc.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Misc.kt\norg/anti_ad/mc/ipnext/inventory/action/RandFixedSum\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,200:1\n1#2:201\n*E\n"
)
public final class RandFixedSum {
   @NotNull
   public static final RandFixedSum INSTANCE = new RandFixedSum();

   private RandFixedSum() {
   }

   @NotNull
   public final List randfixedsum(int var1, int var2, int var3) {
      if (var3 > var1 * var2) {
         String var9 = "sum > n * max";
         throw new IllegalArgumentException(var9.toString());
      }

      if (var1 <= 0) {
         return CollectionsKt.emptyList();
      }

      if (var1 == 1) {
         return CollectionsKt.listOf(var3);
      }

      int var4 = var1 / 2;
      var1 -= var4;
      int var5 = RangesKt.coerceAtLeast(var3 - var1 * var2, 0);
      int var6 = RangesKt.coerceAtMost(var3, var4 * var2);
      var5 = RandomKt.nextInt((Random)MiscKt.access$getRandom$p(), new IntRange(var5, var6));
      List var10 = this.randfixedsum(var4, var2, var5);
      List var8 = this.randfixedsum(var1, var2, var3 - var5);
      return CollectionsKt.plus(var10, var8);
   }

   @NotNull
   public final List randfixedsum(@NotNull List var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (this.getTotalMin(var1) > var2 || this.getTotalMax(var1) < var2) {
         String var8 = "impossible sum";
         throw new IllegalArgumentException(var8.toString());
      }

      if (var1.size() <= 0) {
         return CollectionsKt.emptyList();
      }

      if (var1.size() == 1) {
         return CollectionsKt.listOf(var2);
      }

      List var3 = var1.subList(0, var1.size() / 2);
      var1 = var1.subList(var3.size(), var1.size());
      int var4 = RangesKt.coerceAtLeast(var2 - this.getTotalMax(var1), this.getTotalMin(var3));
      int var5 = RangesKt.coerceAtMost(var2 - this.getTotalMin(var1), this.getTotalMax(var3));
      var4 = RandomKt.nextInt((Random)MiscKt.access$getRandom$p(), new IntRange(var4, var5));
      var3 = this.randfixedsum(var3, var4);
      var1 = this.randfixedsum(var1, var2 - var4);
      return CollectionsKt.plus(var3, var1);
   }

   private final int getTotalMin(List var1) {
      Iterable var4 = var1;
      int var2 = 0;

      for (Object var3 : var4) {
         RandFixedSum$Constraint var6 = (RandFixedSum$Constraint)var3;
         int var8 = var2;
         var2 = var6.getMin();
         var2 = var8 + var2;
      }

      return var2;
   }

   private final int getTotalMax(List var1) {
      Iterable var4 = var1;
      int var2 = 0;

      for (Object var3 : var4) {
         RandFixedSum$Constraint var6 = (RandFixedSum$Constraint)var3;
         int var8 = var2;
         var2 = var6.getMax();
         var2 = var8 + var2;
      }

      return var2;
   }
}
