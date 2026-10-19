package com.tom.storagemod.menu;

import com.tom.storagemod.block.entity.CraftingTerminalBlockEntity;
import com.tom.storagemod.inventory.StoredItemStack;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;

class TerminalRecipePlacer extends ServerPlaceRecipe<CraftingInput, CraftingRecipe> {
   private CraftingTerminalBlockEntity te;
   private Player player;

   public TerminalRecipePlacer(CraftingTerminalMenu p_135431_, CraftingTerminalBlockEntity te, Player player) {
      super(p_135431_);
      this.te = te;
      this.player = player;
   }

   protected int moveItemToGrid(Slot slotToFill, ItemStack ingredientIn, int count) {
      int inInv = this.inventory.findSlotMatchingUnusedItem(ingredientIn);
      if (inInv != -1) {
         ItemStack itemStack2 = this.inventory.getItem(inInv);
         int k;
         if (count < itemStack2.getCount()) {
            this.inventory.removeItem(inInv, count);
            k = count;
         } else {
            this.inventory.removeItemNoUpdate(inInv);
            k = itemStack2.getCount();
         }

         if (slotToFill.getItem().isEmpty()) {
            slotToFill.set(itemStack2.copyWithCount(k));
         } else {
            slotToFill.getItem().grow(k);
         }

         return count - k;
      } else {
         if (this.te != null) {
            StoredItemStack st = this.te.pullStack(new StoredItemStack(ingredientIn), 1L);
            if (st != null) {
               if (slotToFill.getItem().isEmpty()) {
                  slotToFill.set(st.getActualStack());
               } else {
                  slotToFill.getItem().grow(1);
               }

               return -1;
            }
         }

         return -1;
      }
   }

   protected void clearGrid() {
      this.te.clear(this.player);
      this.menu.clearCraftingContent();
   }
}
