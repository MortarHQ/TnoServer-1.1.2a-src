package org.anti_ad.mc.ipnext.item;

import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MutableItemStack extends ItemStack {
   @NotNull
   public static final MutableItemStack$Companion Companion = new MutableItemStack$Companion(null);
   @NotNull
   private ItemType itemType;
   private int count;
   @Nullable
   private Slot sourceSlot;

   public MutableItemStack(@NotNull ItemType var1, int var2, @Nullable Slot var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      super(null);
      this.itemType = var1;
      this.count = var2;
      this.sourceSlot = var3;
   }

   @NotNull
   @Override
   public final ItemType getItemType() {
      return this.itemType;
   }

   public final void setItemType(@NotNull ItemType var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.itemType = var1;
   }

   @Override
   public final int getCount() {
      return this.count;
   }

   public final void setCount(int var1) {
      this.count = var1;
   }

   @Nullable
   @Override
   public final Slot getSourceSlot() {
      return this.sourceSlot;
   }

   @Override
   public final void setSourceSlot(@Nullable Slot var1) {
      this.sourceSlot = var1;
   }
}
