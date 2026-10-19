package dev.ftb.mods.ftbquests.util;

import dev.ftb.mods.ftbquests.integration.item_filtering.ItemMatchingSystem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PlayerInventorySummary {
   private static final List<ItemStack> nonEmptyStacks = new ArrayList<>();
   private static final Map<Item, List<ItemStack>> stacksByItem = new HashMap<>();

   public static void build(ServerPlayer player) {
      nonEmptyStacks.clear();
      stacksByItem.clear();
      player.getInventory().items.forEach(stack -> {
         if (!stack.isEmpty()) {
            nonEmptyStacks.add(stack);
            stacksByItem.computeIfAbsent(stack.getItem(), k -> new ArrayList<>()).add(stack);
         }
      });
   }

   public static Collection<ItemStack> getRelevantItems(ItemStack stack) {
      return ItemMatchingSystem.INSTANCE.isItemFilter(stack) ? nonEmptyStacks : stacksByItem.getOrDefault(stack.getItem(), List.of());
   }
}
