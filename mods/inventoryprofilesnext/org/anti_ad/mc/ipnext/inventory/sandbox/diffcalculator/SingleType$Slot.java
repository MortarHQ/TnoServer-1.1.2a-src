package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.anti_ad.mc.ipnext.item.rule.CountSink;
import org.anti_ad.mc.ipnext.item.rule.CountSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SingleType$Slot implements CountSink {
   private final int n;
   private final int g;

   public SingleType$Slot(int var1, int var2) {
      this.n = var1;
      this.g = var2;
   }

   public final int getN() {
      return this.n;
   }

   public final int getG() {
      return this.g;
   }

   public final boolean isGoal() {
      return this.n == this.g;
   }

   public final int getRank() {
      return SingleType.INSTANCE.calcRank(this.n, this.g);
   }

   @NotNull
   public final Pair click(int var1, @NotNull SingleType$Button var2, int var3) {
      Intrinsics.checkNotNullParameter(var2, "");
      if (var1 == 0) {
         switch (var2) {
            case LEFT:
               return TuplesKt.to(copy$default(this, 0, 0, 2, null), this.n);
            case RIGHT:
               return TuplesKt.to(copy$default(this, this.n / 2, 0, 2, null), this.n - this.n / 2);
            default:
               throw new NoWhenBranchMatchedException();
         }
      } else {
         int var4 = switch (var2) {
            case LEFT -> RangesKt.coerceAtMost(this.n + var1, var3);
            case RIGHT -> this.n + 1;
            default -> throw new NoWhenBranchMatchedException();
         };
         return TuplesKt.to(copy$default(this, var4, 0, 2, null), var1 - (var4 - this.n));
      }
   }

   @Override
   public final void setCountSource(@NotNull CountSource var1) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   public final int component1() {
      return this.n;
   }

   public final int component2() {
      return this.g;
   }

   @NotNull
   public final SingleType$Slot copy(int var1, int var2) {
      return new SingleType$Slot(var1, var2);
   }

   @NotNull
   @Override
   public final String toString() {
      return "Slot(n=" + this.n + ", g=" + this.g + ")";
   }

   @Override
   public final int hashCode() {
      return Integer.hashCode(this.n) * 31 + Integer.hashCode(this.g);
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof SingleType$Slot var2)) {
         return false;
      } else {
         return this.n != var2.n ? false : this.g == var2.g;
      }
   }
}
