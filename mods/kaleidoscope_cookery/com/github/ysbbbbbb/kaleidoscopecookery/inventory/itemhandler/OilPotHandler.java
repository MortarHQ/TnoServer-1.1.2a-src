package com.github.ysbbbbbb.kaleidoscopecookery.inventory.itemhandler;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.OilPotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class OilPotHandler extends ItemStackHandler {
   private final OilPotBlockEntity oilPot;

   public OilPotHandler(OilPotBlockEntity oilPot) {
      super(1);
      this.oilPot = oilPot;
      int count = oilPot.getOilCount();
      if (count > 0) {
         this.setStackInSlot(0, new ItemStack((ItemLike)ModItems.OIL.get(), count));
      }
   }

   public void setOilCount(int count) {
      this.setStackInSlot(0, new ItemStack((ItemLike)ModItems.OIL.get(), count));
   }

   public boolean isItemValid(int slot, @NotNull ItemStack stack) {
      return stack.is((Item)ModItems.OIL.get());
   }

   public int getSlotLimit(int slot) {
      return 256;
   }

   protected int getStackLimit(int slot, @NotNull ItemStack stack) {
      return 256;
   }

   protected void onContentsChanged(int slot) {
      ItemStack stackInSlot = this.getStackInSlot(slot);
      this.oilPot.setOilCountWithoutCapUpdate(stackInSlot.getCount());
   }
}
