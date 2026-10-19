package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.util.MutableBucket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nScoreBasedDualDiffCalculatorInstance.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ScoreBasedDualDiffCalculatorInstance.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/SingleType$Node\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,455:1\n1740#2,3:456\n1#3:459\n*S KotlinDebug\n*F\n+ 1 ScoreBasedDualDiffCalculatorInstance.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/SingleType$Node\n*L\n247#1:456,3\n*E\n"
)
public final class SingleType$Node implements Comparable {
   private final int maxCount;
   @NotNull
   private final MutableBucket identities;
   private int c;
   private boolean skipThis;
   @NotNull
   private final Lazy lowerBound$delegate;
   @NotNull
   private final Lazy upperBound$delegate;
   @Nullable
   private SingleType$Click clickNode;
   @NotNull
   private final Lazy hashCode$delegate;

   public SingleType$Node(int var1, @NotNull MutableBucket var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.maxCount = var1;
      this.identities = var2;
      this.lowerBound$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, SingleType$Node::lowerBound_delegate$lambda$2);
      this.upperBound$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, SingleType$Node::upperBound_delegate$lambda$4);
      this.hashCode$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, new SingleType$Node$hashCode$2(this));
   }

   public final int getMaxCount() {
      return this.maxCount;
   }

   @NotNull
   public final MutableBucket getIdentities() {
      return this.identities;
   }

   public final int getC() {
      return this.c;
   }

   public final void setC(int var1) {
      this.c = var1;
   }

   public final boolean getSkipThis() {
      return this.skipThis;
   }

   public final void setSkipThis(boolean var1) {
      this.skipThis = var1;
   }

   public final boolean isGoal() {
      if (this.c == 0) {
         boolean var10000;
         label33: {
            Iterable var1;
            if (!((var1 = this.identities.getElementSet()) instanceof Collection) || !((Collection)var1).isEmpty()) {
               Iterator var2 = var1.iterator();

               while (var2.hasNext()) {
                  if (!((SingleType$Slot)var2.next()).isGoal()) {
                     var10000 = false;
                     break label33;
                  }
               }
            }

            var10000 = true;
         }

         if (var10000) {
            return true;
         }
      }

      return false;
   }

   public final int getGScore() {
      return this.getClickCount();
   }

   public final int getHScore() {
      return this.getLowerBound();
   }

   public final int getFScore() {
      return this.getGScore() + this.getHScore();
   }

   private final int getLowerBound() {
      return ((Number)this.lowerBound$delegate.getValue()).intValue();
   }

   private final int getUpperBound() {
      return ((Number)this.upperBound$delegate.getValue()).intValue();
   }

   public final int getLowerTotal() {
      return this.getClickCount() + this.getLowerBound();
   }

   public final int getUpperTotal() {
      return this.getClickCount() + this.getUpperBound();
   }

   public final int getClickCount() {
      return this.clickNode != null ? this.clickNode.getClickIndex() + 1 : 0;
   }

   @Nullable
   public final SingleType$Click getClickNode() {
      return this.clickNode;
   }

   private final void addClick(SingleType$Slot var1, SingleType$Button var2) {
      SingleType$Click var3 = new SingleType$Click(this.getClickCount(), var1, var2, this.clickNode);
      this.clickNode = var3;
   }

   @NotNull
   public final List neighbor() {
      List var1 = new ArrayList();
      boolean var2 = false;
      Iterator var3 = this.identities.getElementSet().iterator();

      while (var3.hasNext()) {
         SingleType$Slot var4;
         if (!(var4 = (SingleType$Slot)var3.next()).isGoal() && (this.c != 0 || var4.getN() != 0) && (this.c == 0 || var4.getN() != this.maxCount)) {
            SingleType$Node var6;
            SingleType$Node var10000;
            if ((var6 = this.copyByAddClick(var4, SingleType$Button.LEFT, this.maxCount)) != null) {
               SingleType$Node var5 = var6;
               var6.skipThis = true;
               var6 = var5;
               var1.add(var6);
               var10000 = var5;
            } else {
               var10000 = null;
            }

            SingleType$Node var8 = var10000;
            SingleType$Node var7;
            if ((var7 = this.copyByAddClick(var4, SingleType$Button.RIGHT, this.maxCount)) != null) {
               var6 = var7;
               var7.skipThis = true;
               var7 = var6;
               var1.add(var7);
               var10000 = var6;
            } else {
               var10000 = null;
            }

            var6 = var10000;
            int var13;
            switch (var13 = var4.getRank()) {
               case 10:
                  if (this.c == 0) {
                     continue;
                  }
                  break;
               case 11:
               case 12:
                  if (this.c != 0) {
                     continue;
                  }
                  break;
               case 30:
               case 31:
                  if (this.c != 0) {
                     continue;
                  }
            }

            if (var13 != 12 && (var13 != 10 || this.getBanRight(var4))) {
               if (var13 == 10) {
                  if (!var2) {
                     var2 = true;
                     if (var8 != null) {
                        var8.skipThis = false;
                     }
                  }
               } else if (var8 != null) {
                  var8.skipThis = false;
               }
            }

            if (!this.getBanRight(var4) && var13 != 30) {
               if (var13 == 10) {
                  if (!var2) {
                     var2 = true;
                     if (var6 != null) {
                        var6.skipThis = false;
                     }
                  }
               } else if (var6 != null) {
                  var6.skipThis = false;
               }
            }
         }
      }

      return var1;
   }

   public final boolean getBanRight(@NotNull SingleType$Slot var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.c == 1 || this.c == 0 && var1.getN() == 1 || this.c != 0 && this.maxCount - var1.getN() == 1;
   }

   @NotNull
   public final SingleType$Node copy() {
      SingleType$Node var1;
      SingleType$Node var2;
      (var2 = var1 = new SingleType$Node(this.maxCount, this.identities.copyAsMutable())).c = this.c;
      var2.clickNode = this.clickNode;
      return var1;
   }

   @Nullable
   public final SingleType$Node copyByAddClick(@NotNull SingleType$Slot var1, @NotNull SingleType$Button var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      switch (var2) {
         case LEFT:
            return this.copyByAddClickLeft(var1, var3);
         case RIGHT:
            return this.copyByAddClickRight(var1, var3);
         default:
            throw new NoWhenBranchMatchedException();
      }
   }

   @Nullable
   public final SingleType$Node copyByAddClickLeft(@NotNull SingleType$Slot var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Pair var6;
      SingleType$Slot var3 = (SingleType$Slot)(var6 = var1.click(this.c, SingleType$Button.LEFT, var2)).component1();
      var2 = ((Number)var6.component2()).intValue();
      SingleType$Node var4;
      SingleType$Node var5;
      (var5 = var4 = this.copy()).addClick(var1, SingleType$Button.LEFT);
      var5.c = var2;
      var5.identities.remove(var1);
      var5.identities.add(var3);
      return var4;
   }

   @Nullable
   public final SingleType$Node copyByAddClickRight(@NotNull SingleType$Slot var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Pair var6;
      SingleType$Slot var3 = (SingleType$Slot)(var6 = var1.click(this.c, SingleType$Button.RIGHT, var2)).component1();
      var2 = ((Number)var6.component2()).intValue();
      SingleType$Node var4;
      SingleType$Node var5;
      (var5 = var4 = this.copy()).addClick(var1, SingleType$Button.RIGHT);
      var5.c = var2;
      var5.identities.remove(var1);
      var5.identities.add(var3);
      return var4;
   }

   public final int compareTo(@NotNull SingleType$Node var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return SingleType.INSTANCE.getNodeComparator().compare(this, var1);
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof SingleType$Node)) {
         return false;
      } else {
         return this.c != ((SingleType$Node)var1).c ? false : Intrinsics.areEqual(this.identities, ((SingleType$Node)var1).identities);
      }
   }

   public final int getHashCode() {
      return ((Number)this.hashCode$delegate.getValue()).intValue();
   }

   @Override
   public final int hashCode() {
      return this.getHashCode();
   }

   private final int genHashCode() {
      return this.c * 31 + this.identities.hashCode();
   }

   private static final int lowerBound_delegate$lambda$2(SingleType$Node var0) {
      Iterable var4 = var0.identities.getEntrySet();
      int var1 = 0;

      for (Object var2 : var4) {
         Entry var6 = (Entry)var2;
         int var3 = var1;
         SingleType$Slot var9 = (SingleType$Slot)var6.getKey();
         var1 = ((Number)var6.getValue()).intValue();
         var1 = SingleType.INSTANCE.clickCountLowerBound(var9.getN(), var9.getG()) * var1;
         var1 = var3 + var1;
      }

      return var1;
   }

   private static final int upperBound_delegate$lambda$4(SingleType$Node var0) {
      Iterable var4 = var0.identities.getEntrySet();
      int var1 = 0;

      for (Object var2 : var4) {
         Entry var6 = (Entry)var2;
         int var3 = var1;
         SingleType$Slot var9 = (SingleType$Slot)var6.getKey();
         var1 = ((Number)var6.getValue()).intValue();
         var1 = SingleType.INSTANCE.clickCountUpperBound(var9.getN(), var9.getG()) * var1;
         var1 = var3 + var1;
      }

      return var1;
   }
}
