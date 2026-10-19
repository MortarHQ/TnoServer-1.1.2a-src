package dev.ftb.mods.ftbxmodcompat.ftbquests.recipemod_common;

import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenCustomHashMap;
import java.util.List;
import java.util.function.Function;
import net.minecraft.world.item.ItemStack;

public class ItemStackToListCache<T> {
   private static final int MAX_CACHE_SIZE = 1024;
   private final Object2ObjectLinkedOpenCustomHashMap<ItemStack, List<T>> cacheMap = new Object2ObjectLinkedOpenCustomHashMap(
      new ItemStackToListCache.ItemStackHashingStrategy()
   );

   public List<T> getList(ItemStack stack, Function<ItemStack, List<T>> toCompute) {
      if (this.cacheMap.containsKey(stack)) {
         return (List<T>)this.cacheMap.getAndMoveToFirst(stack);
      }

      List<T> list = toCompute.apply(stack);
      if (this.cacheMap.size() == 1024) {
         this.cacheMap.removeLast();
      }

      this.cacheMap.put(stack, list);
      return list;
   }

   public void clear() {
      this.cacheMap.clear();
   }

   private static class ItemStackHashingStrategy implements Strategy<ItemStack> {
      public int hashCode(ItemStack object) {
         return ItemStack.hashItemAndComponents(object);
      }

      public boolean equals(ItemStack o1, ItemStack o2) {
         return o1 != null && o2 != null && ItemStack.isSameItemSameComponents(o1, o2);
      }
   }
}
