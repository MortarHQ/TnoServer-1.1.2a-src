package com.tom.storagemod.inventory.sorting;

import com.tom.storagemod.inventory.TerminalItemStack;
import java.lang.ref.SoftReference;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ComparatorType implements Comparator<TerminalItemStack> {
   private static SoftReference<Map<Item, Integer>> LOOKUP = new SoftReference<>(null);
   private static long lastAccess;

   private static Map<Item, Integer> makeLookup() {
      CreativeModeTab tab = (CreativeModeTab)BuiltInRegistries.CREATIVE_MODE_TAB.getOrThrow(CreativeModeTabs.SEARCH);
      Item[] items = tab.getSearchTabDisplayItems().stream().map(ItemStack::getItem).toArray(Item[]::new);
      Map<Item, Integer> itemOrderLookup = new HashMap<>();

      for (int i = 0; i < items.length; i++) {
         Item item = items[i];
         itemOrderLookup.putIfAbsent(item, i);
      }

      return itemOrderLookup;
   }

   private static Map<Item, Integer> getLookup() {
      Map<Item, Integer> l = LOOKUP.get();
      long now = System.currentTimeMillis();
      if (l == null || now - lastAccess > 300000L) {
         l = makeLookup();
         LOOKUP = new SoftReference<>(l);
         lastAccess = now;
      }

      return l;
   }

   public int compare(TerminalItemStack in1, TerminalItemStack in2) {
      Map<Item, Integer> lookup = getLookup();
      int val1 = lookup.getOrDefault(in1.getStack().getItem(), Integer.MAX_VALUE);
      int val2 = lookup.getOrDefault(in2.getStack().getItem(), Integer.MAX_VALUE);
      int cmp = Integer.compare(val1, val2);
      return cmp != 0 ? cmp : in1.getDisplayName().compareTo(in2.getDisplayName());
   }
}
