package org.anti_ad.mc.ipnext.inventory.sandbox;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.inventory.data.ItemTracker;
import org.anti_ad.mc.ipnext.inventory.data.MutableItemTracker;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;
import org.anti_ad.mc.ipnext.item.MutableItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nContainerSandbox.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContainerSandbox.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/ContainerSandbox\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,121:1\n1#2:122\n*E\n"
)
public final class ContainerSandbox {
   @NotNull
   private final MutableItemTracker mutableItems;
   @Nullable
   private SandboxClick clickNode;

   public ContainerSandbox(@NotNull MutableItemTracker var1, @Nullable SandboxClick var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.mutableItems = var1;
      this.clickNode = var2;
   }

   @NotNull
   public final ItemTracker getItems() {
      return this.mutableItems;
   }

   public final void leftClick(int var1) {
      MutableItemTracker var2 = this.mutableItems;
      MutableItemStack var3 = (MutableItemStack)this.mutableItems.getSlots().get(var1);
      if (!ItemStackExtensionsKt.isEmpty(var2.getCursor())
         && !ItemStackExtensionsKt.isEmpty(var3)
         && ItemStackExtensionsKt.stackableWith(var2.getCursor(), var3)) {
         ItemStackExtensionsKt.transferTo(var2.getCursor(), var3);
      } else {
         ItemStackExtensionsKt.swapWith(var2.getCursor(), var3);
      }

      int var4 = (ItemStackExtensionsKt.isEmpty(var2.getCursor()) || !ItemTypeExtensionsKt.get_isBundle_/* $VF was: get(isBundle) */(var3.getItemType()))
            && (!ItemTypeExtensionsKt.get_isBundle_/* $VF was: get(isBundle) */(var2.getCursor().getItemType()) || ItemStackExtensionsKt.isEmpty(var3))
         ? 0
         : 1;
      this.addClick(var1, var4);
   }

   public final void rightClick(int var1) {
      MutableItemTracker var2 = this.mutableItems;
      MutableItemStack var3 = (MutableItemStack)this.mutableItems.getSlots().get(var1);
      if (ItemStackExtensionsKt.isEmpty(var2.getCursor())) {
         ItemStackExtensionsKt.splitHalfTo(var3, var2.getCursor());
      } else if (ItemStackExtensionsKt.stackableWith(var2.getCursor(), var3)) {
         ItemStackExtensionsKt.transferOneTo(var2.getCursor(), var3);
      } else {
         ItemStackExtensionsKt.swapWith(var2.getCursor(), var3);
      }

      this.addClick(var1, 1);
   }

   public final void leftClickOutside() {
      MutableItemTracker var1 = this.mutableItems;
      this.mutableItems.getThrownItems().add(var1.getCursor());
      ItemStackExtensionsKt.setEmpty(var1.getCursor());
      this.addClick(-999, 0);
   }

   public final void rightClickOutside() {
      MutableItemTracker var1 = this.mutableItems;
      MutableItemStack var2 = ItemStackExtensionsKt.empty(MutableItemStack.Companion);
      ItemStackExtensionsKt.transferOneTo(var1.getCursor(), var2);
      var1.getThrownItems().add(var2);
      this.addClick(-999, 1);
   }

   public final int getClickCount() {
      return this.clickNode != null ? this.clickNode.getClickIndex() + 1 : 0;
   }

   @Nullable
   public final SandboxClick getClickNode() {
      return this.clickNode;
   }

   private final void addClick(int var1, int var2) {
      SandboxClick var3 = new SandboxClick(this.getClickCount(), var1, var2, this.clickNode);
      this.clickNode = var3;
   }
}
