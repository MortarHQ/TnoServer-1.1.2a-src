package com.chronodawn.items.base;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class FragmentOfStasisCoreItem extends Item {
   public FragmentOfStasisCoreItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().rarity(Rarity.RARE);
   }
}
