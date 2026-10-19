package com.chronodawn.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class PhantomEssenceItem extends Item {
   public PhantomEssenceItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().rarity(Rarity.RARE).fireResistant().stacksTo(16);
   }
}
