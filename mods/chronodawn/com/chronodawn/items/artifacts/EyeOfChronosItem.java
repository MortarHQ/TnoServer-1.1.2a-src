package com.chronodawn.items.artifacts;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class EyeOfChronosItem extends Item {
   public EyeOfChronosItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).rarity(Rarity.EPIC);
   }
}
