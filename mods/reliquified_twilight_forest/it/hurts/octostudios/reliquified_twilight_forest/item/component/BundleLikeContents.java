package it.hurts.octostudios.reliquified_twilight_forest.item.component;

import java.util.List;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class BundleLikeContents implements TooltipComponent {
   private List<ItemStack> stacks;
   private int size;

   public ItemStack get(int i) {
      return i >= this.stacks.size() ? ItemStack.EMPTY : this.stacks.get(i);
   }

   public List<ItemStack> getStacks() {
      return this.stacks;
   }

   public int getSize() {
      return this.size;
   }

   public void setStacks(List<ItemStack> stacks) {
      this.stacks = stacks;
   }

   public void setSize(int size) {
      this.size = size;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof BundleLikeContents other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         if (this.getSize() != other.getSize()) {
            return false;
         }

         Object this$stacks = this.getStacks();
         Object other$stacks = other.getStacks();
         return this$stacks == null ? other$stacks == null : this$stacks.equals(other$stacks);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof BundleLikeContents;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getSize();
      Object $stacks = this.getStacks();
      return result * 59 + ($stacks == null ? 43 : $stacks.hashCode());
   }

   @Override
   public String toString() {
      return "BundleLikeContents(stacks=" + this.getStacks() + ", size=" + this.getSize() + ")";
   }

   public BundleLikeContents(List<ItemStack> stacks, int size) {
      this.stacks = stacks;
      this.size = size;
   }
}
