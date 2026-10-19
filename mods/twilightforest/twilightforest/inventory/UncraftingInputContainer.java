package twilightforest.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class UncraftingInputContainer implements Container {
   private ItemStack stack = ItemStack.EMPTY;
   private final UncraftingMenu container;

   public UncraftingInputContainer(UncraftingMenu containerTFGoblinCrafting) {
      this.container = containerTFGoblinCrafting;
   }

   public int getContainerSize() {
      return 1;
   }

   public boolean isEmpty() {
      return this.stack.isEmpty();
   }

   public ItemStack getItem(int index) {
      return index == 0 ? this.stack : ItemStack.EMPTY;
   }

   public ItemStack removeItem(int index, int amount) {
      if (index == 0 && !this.stack.isEmpty()) {
         if (this.stack.getCount() <= amount) {
            ItemStack takenStack = this.stack;
            this.stack = ItemStack.EMPTY;
            this.container.slotsChanged(this);
            return takenStack;
         } else {
            ItemStack takenStack = this.stack.split(amount);
            this.container.slotsChanged(this);
            return takenStack;
         }
      } else {
         return ItemStack.EMPTY;
      }
   }

   public ItemStack removeItemNoUpdate(int index) {
      if (index == 0 && !this.stack.isEmpty()) {
         ItemStack stack = this.stack;
         this.stack = ItemStack.EMPTY;
         return stack;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public void setItem(int index, ItemStack stack) {
      if (index == 0) {
         this.stack = stack;
         this.container.slotsChanged(this);
      }
   }

   public void setChanged() {
      this.container.slotsChanged(this);
   }

   public boolean stillValid(Player player) {
      return true;
   }

   public void clearContent() {
      this.stack = ItemStack.EMPTY;
   }
}
