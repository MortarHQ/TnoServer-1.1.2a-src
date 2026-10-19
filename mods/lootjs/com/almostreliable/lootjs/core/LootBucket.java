package com.almostreliable.lootjs.core;

import com.almostreliable.lootjs.core.entry.LootEntry;
import com.almostreliable.lootjs.core.filters.ItemFilter;
import com.almostreliable.lootjs.util.NullableFunction;
import com.almostreliable.lootjs.util.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;

public class LootBucket implements Iterable<ItemStack> {
   private final LootContext context;
   private final List<ItemStack> loot;

   public LootBucket(LootContext context) {
      this(context, new ArrayList<>());
   }

   public LootBucket(LootContext context, List<ItemStack> loot) {
      this.context = context;
      this.loot = loot;
   }

   public void addItem(ItemStack item) {
      this.loot.add(item);
   }

   public void addAllItems(List<ItemStack> items) {
      for (ItemStack item : items) {
         this.addItem(item);
      }
   }

   public void addEntry(LootEntry unknown) {
      unknown.getVanillaEntry().expand(this.context, this::addEntry);
   }

   private void addEntry(LootPoolEntry entry) {
      entry.createItemStack(this::addItem, this.context);
   }

   public void remove(ItemFilter removeFilter) {
      ListIterator<ItemStack> it = this.iterator();

      while (it.hasNext()) {
         ItemStack itemStack = it.next();
         if (removeFilter.test(itemStack)) {
            it.remove();
         }
      }
   }

   public void clear() {
      this.remove(ItemFilter.ANY);
   }

   public boolean hasItem(ItemFilter filter) {
      for (ItemStack itemStack : this) {
         if (filter.test(itemStack)) {
            return true;
         }
      }

      return false;
   }

   public void modifyItems(NullableFunction<ItemStack, ItemStack> function) {
      ListIterator<ItemStack> it = this.iterator();

      while (it.hasNext()) {
         ItemStack result = function.apply(it.next());
         if (result == null) {
            it.remove();
         } else {
            it.set(result);
         }
      }
   }

   public void replace(ItemFilter filter, LootEntry lootEntry) {
      this.replace(filter, lootEntry, false);
   }

   public void replace(ItemFilter filter, LootEntry lootEntry, boolean preserveCount) {
      this.replace(filter, lootEntry, preserveCount, new DataComponentType[0]);
   }

   public void replace(ItemFilter filter, LootEntry lootEntry, boolean preserveCount, DataComponentType<?>[] componentTypes) {
      ListIterator<ItemStack> it = this.iterator();

      while (it.hasNext()) {
         ItemStack thisItem = it.next();
         if (filter.test(thisItem)) {
            ItemStack newItem = lootEntry.createRandomItem(this.context);
            if (newItem.isEmpty()) {
               it.remove();
            } else {
               if (preserveCount) {
                  newItem.setCount(Math.min(thisItem.getCount(), newItem.getMaxStackSize()));
               }

               for (DataComponentType<?> type : componentTypes) {
                  Object component = thisItem.get(type);
                  if (component != null) {
                     newItem.set(type, Utils.cast(component));
                  }
               }

               it.set(newItem);
            }
         }
      }
   }

   public LootBucket extract(ItemFilter filter) {
      List<ItemStack> extracted = new ArrayList<>();
      ListIterator<ItemStack> it = this.iterator();

      while (it.hasNext()) {
         ItemStack itemStack = it.next();
         if (filter.test(itemStack)) {
            extracted.add(itemStack);
            it.remove();
         }
      }

      return new LootBucket(this.context, extracted);
   }

   public void merge(LootBucket other) {
      this.addAllItems(other.loot);
   }

   public ItemStack get(int index) {
      return index >= 0 && index < this.loot.size() ? this.loot.get(index) : ItemStack.EMPTY;
   }

   public int size() {
      return this.loot.size();
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public ListIterator<ItemStack> iterator() {
      return this.loot.listIterator();
   }
}
