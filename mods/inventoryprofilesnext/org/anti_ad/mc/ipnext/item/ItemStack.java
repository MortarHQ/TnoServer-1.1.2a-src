package org.anti_ad.mc.ipnext.item;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nItemStack.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemStack.kt\norg/anti_ad/mc/ipnext/item/ItemStack\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,94:1\n98#2,9:95\n98#2,9:104\n*S KotlinDebug\n*F\n+ 1 ItemStack.kt\norg/anti_ad/mc/ipnext/item/ItemStack\n*L\n34#1:95,9\n37#1:104,9\n*E\n"
)
public abstract sealed class ItemStack permits ImmutableItemStack, MutableItemStack {
   @NotNull
   public static final ItemStack$Companion Companion = new ItemStack$Companion(null);

   private ItemStack() {
   }

   @NotNull
   public abstract ItemType getItemType();

   public abstract int getCount();

   @Nullable
   public abstract Slot getSourceSlot();

   public abstract void setSourceSlot(@Nullable Slot var1);

   @NotNull
   public final ItemType component1() {
      return this.getItemType();
   }

   public final int component2() {
      return this.getCount();
   }

   public final boolean getOverstacked() {
      int var10000 = this.getCount();
      ItemType var1 = this.getItemType();
      net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
      ItemLike var10003 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10005 = var1.getTag();
      if (var10005 == null) {
         DataComponentMap var2 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var2;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      return var10000 > var10001.getMaxStackSize();
   }

   public final boolean getOverstackedAndNotManageable() {
      int var10000 = this.getCount();
      ItemType var1 = this.getItemType();
      net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
      ItemLike var10003 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10005 = var1.getTag();
      if (var10005 == null) {
         DataComponentMap var2 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var2;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      return var10000 > var10001.getMaxStackSize() && this.getCount() > 64;
   }

   @NotNull
   @Override
   public final String toString() {
      return this.getCount() + "x " + this.getItemType();
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ItemStack)) {
         return false;
      } else if (ItemStackExtensionsKt.isEmpty(this) && ItemStackExtensionsKt.isEmpty((ItemStack)var1)) {
         return true;
      } else {
         return !Intrinsics.areEqual(this.getItemType(), ((ItemStack)var1).getItemType()) ? false : this.getCount() == ((ItemStack)var1).getCount();
      }
   }

   @Override
   public final int hashCode() {
      return ItemStackExtensionsKt.isEmpty(this) ? 0 : this.getItemType().hashCode() * 31 + this.getCount();
   }

   @NotNull
   public final MutableItemStack copyAsMutable() {
      return new MutableItemStack(this.getItemType(), this.getCount(), this.getSourceSlot());
   }
}
