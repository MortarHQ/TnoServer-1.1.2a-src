package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import org.anti_ad.mc.ipnext.inventory.data.ExtensionsKt;
import org.anti_ad.mc.ipnext.inventory.data.ItemTracker;
import org.anti_ad.mc.ipnext.inventory.sandbox.ContainerSandbox;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nDiffCalculator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DiffCalculator.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/DiffCalculatorInstance\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,233:1\n1617#2,9:234\n1869#2:243\n1870#2:245\n1626#2:246\n1869#2,2:247\n1626#2:249\n1#3:244\n*S KotlinDebug\n*F\n+ 1 DiffCalculator.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/DiffCalculatorInstance\n*L\n156#1:234,9\n156#1:243\n156#1:245\n156#1:246\n156#1:247,2\n156#1:249\n156#1:244\n*E\n"
)
public abstract class DiffCalculatorInstance {
   @NotNull
   private final ContainerSandbox sandbox;
   @NotNull
   private final ItemTracker goalTracker;
   private int loopCounter;

   public DiffCalculatorInstance(@NotNull ContainerSandbox var1, @NotNull ItemTracker var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.sandbox = var1;
      this.goalTracker = var2;
      if (this.getNowTracker().getSlots().size() != this.goalTracker.getSlots().size()) {
         throw new IllegalStateException("Unequal sandbox and goal slot size".toString());
      }

      if (!Intrinsics.areEqual(ExtensionsKt.collect(this.getNowTracker()), ExtensionsKt.collect(this.goalTracker))) {
         throw new IllegalStateException("Unequal sandbox and goal item counts".toString());
      }

      if (!this.goalTracker.getThrownItems().containsAll(this.getNowTracker().getThrownItems())) {
         throw new IllegalStateException("Impossible. Thrown items cannot be reverted".toString());
      }
   }

   @NotNull
   public final ContainerSandbox getSandbox() {
      return this.sandbox;
   }

   @NotNull
   public final ItemTracker getGoalTracker() {
      return this.goalTracker;
   }

   @NotNull
   public final ItemTracker getNowTracker() {
      return this.sandbox.getItems();
   }

   @NotNull
   public final ItemStack getCursorNow() {
      return this.getNowTracker().getCursor();
   }

   @NotNull
   public final ItemStack getCursorGoal() {
      return this.goalTracker.getCursor();
   }

   public abstract void run();

   public final void increaseLoopCount() {
      this.loopCounter++;
      int var1;
      if ((var1 = Math.max(this.loopCounter, this.sandbox.getClickCount())) > 100000) {
         throw new IllegalStateException(("Infinity loop detected. " + var1 + " > 100000").toString());
      }
   }

   @NotNull
   public final IntRange getIndices() {
      return CollectionsKt.getIndices(this.getNowTracker().getSlots());
   }

   @NotNull
   public final List filtered(@NotNull Function1 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Iterable var2 = (Iterable)this.getIndices();
      var var3 = new ArrayList();
      Iterator var5 = var2.iterator();

      while (var5.hasNext()) {
         int var4 = ((IntIterator)var5).nextInt();
         DiffCalculatorInstance$CompareSlotDsl var6 = new DiffCalculatorInstance$CompareSlotDsl(this, var4);
         DiffCalculatorInstance$CompareSlotDsl var10000 = var1.invoke(var6) ? var6 : null;
         if (var10000 != null) {
            DiffCalculatorInstance$CompareSlotDsl var7 = var10000;
            var3.add(var7);
         }
      }

      return var3;
   }
}
