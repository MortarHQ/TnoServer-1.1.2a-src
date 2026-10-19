package cn.leolezury.eternalstarlight.common.item.component;

import java.util.AbstractList;
import java.util.List;
import net.minecraft.world.item.ItemStack;

public class ItemStackList extends AbstractList<ItemStack> {
   private final List<ItemStack> list;

   public ItemStackList(List<ItemStack> list) {
      this.list = list;
   }

   public ItemStack get(int index) {
      return this.list.get(index);
   }

   public ItemStack set(int index, ItemStack value) {
      return this.list.set(index, value);
   }

   public void add(int index, ItemStack value) {
      this.list.add(index, value);
   }

   public ItemStack remove(int index) {
      return this.list.remove(index);
   }

   @Override
   public int size() {
      return this.list.size();
   }

   @Override
   public boolean equals(Object other) {
      return this == other ? true : other instanceof ItemStackList stacks && ItemStack.listMatches(this.list, stacks.list);
   }

   @Override
   public int hashCode() {
      return ItemStack.hashStackList(this.list);
   }
}
