package com.chronodawn.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class EntropyCoreItem extends Item {
   public EntropyCoreItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().rarity(Rarity.RARE).fireResistant().stacksTo(16);
   }
}
