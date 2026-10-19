package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nDiffCalculator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DiffCalculator.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/DiffCalculatorInstance$CompareSlotDsl\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,233:1\n1#2:234\n*E\n"
)
public class DiffCalculatorInstance$CompareSlotDsl {
   private final int slotIndex;

   public DiffCalculatorInstance$CompareSlotDsl(DiffCalculatorInstance var1, int var2) {
      this.this$0 = var1;
      this.slotIndex = var2;
   }

   public final int getSlotIndex() {
      return this.slotIndex;
   }

   @NotNull
   public final ItemStack getNow() {
      return (ItemStack)this.this$0.getNowTracker().getSlots().get(this.slotIndex);
   }

   @NotNull
   public final ItemStack getGoal() {
      return (ItemStack)this.this$0.getGoalTracker().getSlots().get(this.slotIndex);
   }

   public final boolean getEquals() {
      return Intrinsics.areEqual(this.getNow(), this.getGoal());
   }

   public final boolean getEqualsType() {
      return Intrinsics.areEqual(this.getNow().getItemType(), this.getGoal().getItemType());
   }

   public final boolean getBothEmpty() {
      return ItemStackExtensionsKt.isEmpty(this.getNow()) && ItemStackExtensionsKt.isEmpty(this.getGoal());
   }

   public final boolean getBothNotEmpty() {
      return !ItemStackExtensionsKt.isEmpty(this.getNow()) && !ItemStackExtensionsKt.isEmpty(this.getGoal());
   }

   public final int getN() {
      return this.getNow().getCount();
   }

   public final int getG() {
      return this.getGoal().getCount();
   }

   public final void leftClick() {
      this.this$0.getSandbox().leftClick(this.slotIndex);
   }

   public final void rightClick() {
      this.this$0.getSandbox().rightClick(this.slotIndex);
   }

   public final void repeatRightClick(int var1) {
      for (int var2 = 0; var2 < var1; var2++) {
         this.rightClick();
      }
   }
}
