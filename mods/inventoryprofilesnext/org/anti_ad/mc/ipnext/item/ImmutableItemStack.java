package org.anti_ad.mc.ipnext.item;

import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ImmutableItemStack extends ItemStack {
   @NotNull
   private final ItemType itemType;
   private final int count;
   @Nullable
   private Slot sourceSlot;

   public ImmutableItemStack(@NotNull ItemType var1, int var2, @Nullable Slot var3) {
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

   @Override
   public final int getCount() {
      return this.count;
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
