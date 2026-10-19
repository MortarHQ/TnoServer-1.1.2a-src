package com.chronodawn.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class KeyToMasterClockItem extends Item {
   public KeyToMasterClockItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1);
   }
}
