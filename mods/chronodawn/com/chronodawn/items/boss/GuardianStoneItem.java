package com.chronodawn.items.boss;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class GuardianStoneItem extends Item {
   public GuardianStoneItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().rarity(Rarity.RARE).fireResistant().stacksTo(16);
   }
}
