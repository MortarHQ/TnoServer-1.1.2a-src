package io.github.Memoires.mysticism.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemLore;

public class ItemDataUtils {
   public static boolean getFlag(ItemStack stack, String key) {
      CustomData data = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag tag = data.copyTag();
      return tag != null && tag.getBoolean(key);
   }

   public static void setFlag(ItemStack stack, String key, boolean value) {
      CustomData data = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CustomData updated = data.update(nbt -> nbt.putBoolean(key, value));
      stack.set(DataComponents.CUSTOM_DATA, updated);
   }

   public static void addLoreLine(ItemStack stack, Component line) {
      ItemLore lore = (ItemLore)stack.getOrDefault(DataComponents.LORE, ItemLore.EMPTY);
      lore = lore.withLineAdded(line);
      stack.set(DataComponents.LORE, lore);
   }
}
