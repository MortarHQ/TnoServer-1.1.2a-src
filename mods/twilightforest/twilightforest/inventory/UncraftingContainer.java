package twilightforest.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import twilightforest.config.TFConfig;

public class UncraftingContainer implements Container {
   private final NonNullList<ItemStack> contents = NonNullList.withSize(9, ItemStack.EMPTY);
   public int numberOfInputItems;
   public int uncraftingCost;
   public int recraftingCost;
   public UncraftingMenu menu;

   public UncraftingContainer(UncraftingMenu menu) {
      this.menu = menu;
   }

   public int getContainerSize() {
      return 9;
   }

   public boolean isEmpty() {
      for (ItemStack stack : this.contents) {
         if (!stack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack getItem(int index) {
      return (ItemStack)this.contents.get(index);
   }

   public ItemStack removeItem(int index, int amount) {
      ItemStack stack = (ItemStack)this.contents.get(index);
      if (stack.isEmpty()) {
         return ItemStack.EMPTY;
      } else if (stack.getCount() <= amount) {
         this.contents.set(index, ItemStack.EMPTY);
         return stack;
      } else {
         return stack.split(amount);
      }
   }

   public ItemStack removeItemNoUpdate(int index) {
      ItemStack stack = (ItemStack)this.contents.get(index);
      if (stack.isEmpty()) {
         return ItemStack.EMPTY;
      }

      this.contents.set(index, ItemStack.EMPTY);
      return stack;
   }

   public void setItem(int index, ItemStack stack) {
      this.contents.set(index, stack);
      if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
         stack.setCount(this.getMaxStackSize());
      }

      this.setChanged();
   }

   public int getMaxStackSize() {
      return 64;
   }

   public void setChanged() {
   }

   public boolean stillValid(Player player) {
      return !TFConfig.disableEntireTable;
   }

   public void startOpen(Player player) {
   }

   public void stopOpen(Player player) {
   }

   public boolean canPlaceItem(int index, ItemStack stack) {
      return false;
   }

   public void clearContent() {
      this.contents.clear();
   }
}
