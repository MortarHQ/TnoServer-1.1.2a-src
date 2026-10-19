package com.chronodawn.items.equipment;

import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Item.Properties;

public class EnhancedClockstoneShovelItem extends ShovelItem {
   public EnhancedClockstoneShovelItem(Properties properties) {
      super(EnhancedClockstoneTier.INSTANCE, properties.attributes(ShovelItem.createAttributes(EnhancedClockstoneTier.INSTANCE, 1.5F, -3.0F)));
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(1200);
   }
}
