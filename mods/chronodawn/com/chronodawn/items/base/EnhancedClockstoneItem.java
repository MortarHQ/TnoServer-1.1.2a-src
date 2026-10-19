package com.chronodawn.items.base;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class EnhancedClockstoneItem extends Item {
   public EnhancedClockstoneItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(64);
   }
}
