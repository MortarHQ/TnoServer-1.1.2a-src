package com.chronodawn.items.quest;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class AncientGearItem extends Item {
   public static final int REQUIRED_COUNT = 3;

   public AncientGearItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(64);
   }
}
