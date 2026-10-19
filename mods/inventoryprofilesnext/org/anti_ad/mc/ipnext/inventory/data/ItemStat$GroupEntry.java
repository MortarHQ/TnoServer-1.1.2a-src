package org.anti_ad.mc.ipnext.inventory.data;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.common.extensions.Kt_numberKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nItemStat.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemStat.kt\norg/anti_ad/mc/ipnext/inventory/data/ItemStat$GroupEntry\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,77:1\n98#2,9:78\n*S KotlinDebug\n*F\n+ 1 ItemStat.kt\norg/anti_ad/mc/ipnext/inventory/data/ItemStat$GroupEntry\n*L\n61#1:78,9\n*E\n"
)
public final class ItemStat$GroupEntry {
   @NotNull
   private final ItemType itemType;
   private int itemCount;
   private int slotCount;
   @NotNull
   private final List slotIndices;

   public ItemStat$GroupEntry(@NotNull ItemType var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.itemType = var1;
      this.slotIndices = new ArrayList();
   }

   @NotNull
   public final ItemType getItemType() {
      return this.itemType;
   }

   public final int getItemCount() {
      return this.itemCount;
   }

   public final void setItemCount(int var1) {
      this.itemCount = var1;
   }

   public final int getSlotCount() {
      return this.slotCount;
   }

   public final void setSlotCount(int var1) {
      this.slotCount = var1;
   }

   @NotNull
   public final List getSlotIndices() {
      return this.slotIndices;
   }

   public final int getMinSlotCount() {
      int var10000 = this.itemCount;
      ItemType var1 = this.itemType;
      ItemStack var10001 = new ItemStack;
      ItemLike var10003 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10005 = var1.getTag();
      if (var10005 == null) {
         DataComponentMap var2 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var2;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      return Kt_numberKt.divCeil(var10000, var10001.getMaxStackSize());
   }

   public final void add(int var1, @NotNull org.anti_ad.mc.ipnext.item.ItemStack var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      this.itemCount = this.itemCount + var2.getCount();
      int var4 = this.slotCount++;
      Integer var3 = var1;
      this.slotIndices.add(var3);
   }
}
