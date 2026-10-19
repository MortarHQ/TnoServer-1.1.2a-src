package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.inventory.data.ItemStackListExtensionsKt;
import org.anti_ad.mc.ipnext.inventory.data.ItemStat;
import org.anti_ad.mc.ipnext.inventory.data.ItemStat$GroupEntry;
import org.anti_ad.mc.ipnext.inventory.data.ItemTracker;
import org.anti_ad.mc.ipnext.inventory.sandbox.ContainerSandbox;
import org.anti_ad.mc.ipnext.inventory.sandbox.ContainerSandboxKt;
import org.anti_ad.mc.ipnext.inventory.sandbox.SandboxClick;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nScoreBasedDualDiffCalculatorInstance.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ScoreBasedDualDiffCalculatorInstance.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/ScoreBasedDualDiffCalculatorInstance\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,455:1\n1788#2,4:456\n1788#2,4:460\n1869#2,2:464\n774#2:466\n865#2,2:467\n295#2,2:478\n98#3,9:469\n*S KotlinDebug\n*F\n+ 1 ScoreBasedDualDiffCalculatorInstance.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/ScoreBasedDualDiffCalculatorInstance\n*L\n49#1:456,4\n50#1:460,4\n87#1:464,2\n92#1:466\n92#1:467,2\n103#1:478,2\n94#1:469,9\n*E\n"
)
public final class ScoreBasedDualDiffCalculatorInstance extends SimpleDiffCalculatorInstance {
   @NotNull
   private final ItemStat statGoal;

   public ScoreBasedDualDiffCalculatorInstance(@NotNull ContainerSandbox var1, @NotNull ItemTracker var2) {
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
      if (!ItemStackExtensionsKt.isEmpty(this.getCursorNow())) {
         this.doItemType(this.getCursorNow().getItemType());
      }

      for (ItemType var2 : this.statGoal.getItemTypes()) {
         this.doItemType(var2);
      }
   }

   public final void doItemType(@NotNull ItemType var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Iterable var3 = ((ItemStat$GroupEntry)MapsKt.getValue(this.statGoal.getItemGroups(), var1)).getSlotIndices();
      var var4 = new ArrayList();
      Iterator var5 = var3.iterator();

      while (var5.hasNext()) {
         Object var6;
         int var9 = ((Number)(var6 = var5.next())).intValue();
         if (!new DiffCalculatorInstance$CompareSlotDsl(this, var9).getEquals()) {
            var4.add(var6);
         }
      }

      List var2;
      if (!(var2 = var4).isEmpty()) {
         ItemType var14 = var1;
         ItemStack var10000 = new ItemStack;
         ItemLike var10002 = (ItemLike)var14.getItem();
         PatchedDataComponentMap var10004 = var14.getTag();
         if (var10004 == null) {
            DataComponentMap var24 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10004 = (PatchedDataComponentMap)var24;
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
         int var10 = var10000.getMaxStackSize();
         SingleType$Node var11;
         SingleType$Node var15;
         (var15 = var11 = new SingleType$Node(var10, null, 2, null)).setC(this.getCursorNow().getCount());
         var5 = var2.iterator();

         while (var5.hasNext()) {
            int var20 = ((Number)var5.next()).intValue();
            var15.getIdentities().add(this.toSlot(new DiffCalculatorInstance$CompareSlotDsl(this, var20)));
         }

         SingleType$Node var7 = var11;

         for (SingleType$Click var8 : SingleType.INSTANCE.solve(var7)) {
            Iterator var12 = var2.iterator();

            while (true) {
               if (!var12.hasNext()) {
                  var22 = null;
                  break;
               }

               Object var18;
               int var21 = ((Number)(var18 = var12.next())).intValue();
               if (Intrinsics.areEqual(this.toSlot(new DiffCalculatorInstance$CompareSlotDsl(this, var21)), var8.getSlot())) {
                  var22 = var18;
                  break;
               }
            }

            Integer var23 = (Integer)var22;
            if ((Integer)var22 == null) {
               throw new IllegalStateException(("target slot " + var8.getSlot() + " not found").toString());
            }

            int var19 = var23;
            DiffCalculatorInstance$CompareSlotDsl var13 = new DiffCalculatorInstance$CompareSlotDsl(this, var19);
            switch (var8.getButton()) {
               case LEFT:
                  var13.leftClick();
                  break;
               case RIGHT:
                  var13.rightClick();
                  break;
               default:
                  throw new NoWhenBranchMatchedException();
            }
         }
      }
   }

   @NotNull
   public final SingleType$Slot toSlot(@NotNull DiffCalculatorInstance$CompareSlotDsl var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return new SingleType$Slot(var1.getN(), var1.getG());
   }
}
