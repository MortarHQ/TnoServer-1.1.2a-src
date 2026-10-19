package com.chronodawn.items.base;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class TimeCrystalItem extends Item {
   public TimeCrystalItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(64);
   }
}
