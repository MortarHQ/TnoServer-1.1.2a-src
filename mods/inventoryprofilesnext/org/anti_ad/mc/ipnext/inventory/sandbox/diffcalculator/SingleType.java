package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SingleType implements DiffCalculatorUtil {
   @NotNull
   public static final SingleType INSTANCE = new SingleType();
   @NotNull
   private static final List rankAfterAllowed;

   private SingleType() {
   }

   @NotNull
   public final List getRankAfterAllowed() {
      return rankAfterAllowed;
   }

   @NotNull
   public final Comparator getNodeComparator() {
      Comparator var1 = new SingleType$special$$inlined$compareBy$1();
      return new SingleType$special$$inlined$thenBy$1(var1);
   }

   @NotNull
   public final List solve(@NotNull SingleType$Node var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Set var2 = new LinkedHashSet();
      Pair[] var4;
      (var4 = new Pair[1])[0] = TuplesKt.to(var1, var1);
      SortedMap var3 = MapsKt.sortedMapOf(var4);
      int var10 = var1.getUpperTotal();
      int var9 = var1.getLowerTotal();
      int var5 = 0;
      int var6 = 0;

      while (!var3.isEmpty()) {
         if (++var5 > 100000) {
            throw new IllegalStateException(("Too many loop. " + var5 + " > 100000").toString());
         }

         SingleType$Node var10000 = (SingleType$Node)var3.firstKey();
         if (var10000 != null) {
            SingleType$Node var7 = var10000;
            if (var10000.isGoal()) {
               Log.INSTANCE.trace("loopCounter " + var5);
               Log.INSTANCE.trace("skippedCount " + var6);
               return this.constructClickPath(var7);
            }

            var3.remove(var7);
            var2.add(var7);
            if (var7.getLowerTotal() <= var10 && (var7.getLowerTotal() != var10 || var10 <= var9 && var7.getUpperTotal() <= var7.getLowerTotal())) {
               for (SingleType$Node var8 : var7.neighbor()) {
                  if (!var2.contains(var8)) {
                     if (var3.containsKey(var8)) {
                        Object var10001 = MapsKt.getValue(var3, var8);
                        Intrinsics.checkNotNullExpressionValue(var10001, "");
                        if (var8.compareTo((SingleType$Node)var10001) >= 0) {
                           continue;
                        }
                     }

                     if (var8.getLowerTotal() <= var10
                        && (var8.getLowerTotal() != var10 || var10 <= var9 && var8.getUpperTotal() <= var8.getLowerTotal())
                        && !var8.getSkipThis()) {
                        var3.put(var8, var8);
                        if (var8.getUpperTotal() < var10) {
                           var10 = var8.getUpperTotal();
                           var9 = var8.getLowerTotal();
                        } else if (var8.getUpperTotal() == var10 && var8.getLowerTotal() < var9) {
                           var9 = var8.getLowerTotal();
                        }
                     } else {
                        var6++;
                        var3.remove(var8);
                        var2.add(var8);
                     }
                  }
               }
               continue;
            }

            throw new IllegalStateException("emm x.lowerTotal > minUpper etc".toString());
         }
         break;
      }

      Log.INSTANCE.trace("loopCounter " + var5);
      Log.INSTANCE.trace("skippedCount " + var6);
      throw new IllegalStateException("solve failure".toString());
   }

   @NotNull
   public final List constructClickPath(@NotNull SingleType$Node var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.toList(var1.getClickNode());
   }

   @NotNull
   public final List toList(@Nullable SingleType$Click var1) {
      List var2 = new ArrayList();

      while (var1 != null) {
         var2.add(var1);
         var1 = var1.getPreviousClick();
      }

      CollectionsKt.reverse(var2);
      return var2;
   }

   @Override
   public final int calcRank(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.calcRank(this, var1, var2);
   }

   @Override
   public final int clickCountLowerBound(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.clickCountLowerBound(this, var1, var2);
   }

   @Override
   public final int clickCountUpperBound(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.clickCountUpperBound(this, var1, var2);
   }

   @Override
   public final int clickCountSingleSlotToLess(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.clickCountSingleSlotToLess(this, var1, var2);
   }

   @Override
   public final boolean canRight(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.canRight(this, var1, var2);
   }

   static {
      List[] var0;
      (var0 = new List[5])[0] = CollectionsKt.emptyList();
      var0[1] = CollectionsKt.listOf(0);
      Integer[] var1;
      (var1 = new Integer[3])[0] = 0;
      var1[1] = 1;
      var1[2] = 2;
      var0[2] = CollectionsKt.listOf(var1);
      var0[3] = CollectionsKt.listOf(1);
      (var1 = new Integer[3])[0] = 2;
      var1[1] = 3;
      var1[2] = 4;
      var0[4] = CollectionsKt.listOf(var1);
      rankAfterAllowed = CollectionsKt.listOf(var0);
   }
}
