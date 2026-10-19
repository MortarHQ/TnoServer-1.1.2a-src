package net.jadenxgamer.elysium_api.api.util;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.world.item.ItemStack;

public class ClientItemData {
   private static final WeakHashMap<ItemStack, Map<String, Object>> dataMap = new WeakHashMap<>();

   public static Map<String, Object> getOrCreate(ItemStack stack) {
      return dataMap.computeIfAbsent(stack, k -> new HashMap<>());
   }

   public static Map<String, Object> get(ItemStack stack) {
      return dataMap.get(stack);
   }

   public static boolean has(ItemStack stack, String key) {
      if (stack == null) {
         return false;
      }

      Map<String, Object> map = dataMap.get(stack);
      return map != null && map.containsKey(key);
   }

   public static void copy(ItemStack from, ItemStack to) {
      Map<String, Object> fromData = dataMap.get(from);
      if (fromData != null) {
         dataMap.put(to, new HashMap<>(fromData));
      }
   }

   public static void remove(ItemStack stack) {
      dataMap.remove(stack);
   }
}
