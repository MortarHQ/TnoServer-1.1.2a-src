package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.inventory.data.ItemStackListExtensionsKt;
import org.anti_ad.mc.ipnext.inventory.data.ItemStat;
import org.anti_ad.mc.ipnext.inventory.data.ItemTracker;
import org.anti_ad.mc.ipnext.inventory.sandbox.ContainerSandbox;
import org.anti_ad.mc.ipnext.inventory.sandbox.ContainerSandboxKt;
import org.anti_ad.mc.ipnext.inventory.sandbox.SandboxClick;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nScoreBasedSingleDiffCalculatorInstance.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ScoreBasedSingleDiffCalculatorInstance.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/ScoreBasedSingleDiffCalculatorInstance\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,53:1\n1788#2,4:54\n1788#2,4:58\n*S KotlinDebug\n*F\n+ 1 ScoreBasedSingleDiffCalculatorInstance.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/ScoreBasedSingleDiffCalculatorInstance\n*L\n41#1:54,4\n42#1:58,4\n*E\n"
)
public final class ScoreBasedSingleDiffCalculatorInstance extends SimpleDiffCalculatorInstance {
   @NotNull
   private final ItemStat statGoal;

   public ScoreBasedSingleDiffCalculatorInstance(@NotNull ContainerSandbox var1, @NotNull ItemTracker var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super(var1, var2);
      this.setUntilEqualsTypeOnly(true);
      this.statGoal = ItemStackListExtensionsKt.stat(var2.getSlots());
   }

   @Override
   public final void run() {
      super.run();
      List var1;
      Iterable var3;
      int var10000;
      if ((var3 = (Iterable & List)(var1 = ContainerSandboxKt.toList(this.getSandbox().getClickNode()))) instanceof Collection && ((Collection)var3).isEmpty()) {
         var10000 = 0;
      } else {
         int var2 = 0;
         Iterator var7 = var3.iterator();

         while (var7.hasNext()) {
            if (((SandboxClick)var7.next()).getButton() == 0) {
               if (++var2 < 0) {
                  CollectionsKt.throwCountOverflow();
               }
            }
         }

         var10000 = var2;
      }

      int var6 = var10000;
      if ((var1 = (Iterable & List)var1) instanceof Collection && var1.isEmpty()) {
         var10000 = 0;
      } else {
         int var8 = 0;
         Iterator var5 = var1.iterator();

         while (var5.hasNext()) {
            if (((SandboxClick)var5.next()).getButton() == 1) {
               if (++var8 < 0) {
                  CollectionsKt.throwCountOverflow();
               }
            }
         }

         var10000 = var8;
      }

      int var9 = var10000;
      Log.INSTANCE.debug("Stage A click count total " + this.getSandbox().getClickCount() + ". " + var6 + " left. " + var9 + " right.");
      this.runFinal();
   }

   @NotNull
   public final ItemStat getStatGoal() {
      return this.statGoal;
   }

   public final void runFinal() {
      throw new IllegalStateException("todo".toString());
   }
}
