package net.unusual.block_factorys_bosses.util;

import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;

public class ItemAttributeModifiersUtil {
   public static Builder addAll(Builder builder, ItemAttributeModifiers modifiers) {
      for (Entry modifier : modifiers.modifiers()) {
         builder.add(modifier.attribute(), modifier.modifier(), modifier.slot());
      }

      return builder;
   }
}
