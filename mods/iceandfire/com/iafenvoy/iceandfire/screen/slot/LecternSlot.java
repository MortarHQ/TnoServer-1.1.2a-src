package com.iafenvoy.iceandfire.screen.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class LecternSlot extends Slot {
   public LecternSlot(Container inv, int slotIndex, int xPosition, int yPosition) {
      super(inv, slotIndex, xPosition, yPosition);
   }

   public void setChanged() {
      this.container.setChanged();
   }

   public void onTake(Player playerIn, ItemStack stack) {
      this.checkTakeAchievements(stack);
      super.onTake(playerIn, stack);
   }

   protected void onQuickCraft(ItemStack stack, int amount) {
      this.checkTakeAchievements(stack);
   }

   protected void checkTakeAchievements(ItemStack stack) {
   }

   public int getMaxStackSize() {
      return 1;
   }
}
