package dev.ftb.mods.ftblibrary.util;

import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenCustomHashSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SetOfItemStack extends ObjectLinkedOpenCustomHashSet<ItemStack> {
   private static final Comparator<? super ItemStack> COMPARE_STACKS = Comparator.<ItemStack, String>comparing(stack -> namespace(stack.getItem()))
      .thenComparing(stack -> stack.getHoverName().getString());

   public SetOfItemStack() {
      super(new SetOfItemStack.ItemStackHashingStrategy());
   }

   public SetOfItemStack(Collection<? extends ItemStack> collection) {
      super(collection, new SetOfItemStack.ItemStackHashingStrategy());
   }

   private static String namespace(Item item) {
      return BuiltInRegistries.ITEM.getKey(item).getNamespace();
   }

   public List<ItemStack> sortedList() {
      return this.stream().sorted(COMPARE_STACKS).toList();
   }

   private record ItemStackHashingStrategy() implements Strategy<ItemStack> {
      public int hashCode(ItemStack stack) {
         return ItemStack.hashItemAndComponents(stack);
      }

      public boolean equals(ItemStack o1, ItemStack o2) {
         return ItemStack.isSameItemSameComponents(o1, o2);
      }
   }
}
