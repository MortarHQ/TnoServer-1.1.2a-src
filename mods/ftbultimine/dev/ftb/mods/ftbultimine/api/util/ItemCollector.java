package dev.ftb.mods.ftbultimine.api.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ItemCollector {
   private final List<ItemStack> items = new ArrayList<>();

   public void add(ItemStack stack) {
      if (!stack.isEmpty()) {
         this.items.add(stack.copy());
      }
   }

   public void drop(Level level, BlockPos pos) {
      if (!this.items.isEmpty()) {
         List<ItemStack> stacks = new ArrayList<>();

         for (ItemStack stack : this.items) {
            if (!stack.isStackable()) {
               stacks.add(stack);
            } else {
               int i = 0;

               while (true) {
                  if (i < stacks.size()) {
                     stack = this.insert(stacks, stack, i);
                     if (!stack.isEmpty()) {
                        i++;
                        continue;
                     }
                  }

                  if (!stack.isEmpty()) {
                     stacks.add(stack);
                  }
                  break;
               }
            }
         }

         for (ItemStack stack : stacks) {
            Block.popResource(level, pos, stack);
         }
      }
   }

   private ItemStack insert(List<ItemStack> stacks, ItemStack stack, int slot) {
      if (stack.isEmpty()) {
         return ItemStack.EMPTY;
      } else {
         ItemStack existing = stacks.get(slot);
         if (existing.isEmpty()) {
            stacks.set(slot, stack.copy());
            return ItemStack.EMPTY;
         } else if (stack.isStackable() && ItemStack.isSameItemSameComponents(stack, existing)) {
            int available = stack.getMaxStackSize() - existing.getCount();
            int toAdd = Math.min(available, stack.getCount());
            existing.grow(toAdd);
            return toAdd == stack.getCount() ? ItemStack.EMPTY : stack.copyWithCount(stack.getCount() - toAdd);
         } else {
            return stack;
         }
      }
   }
}
