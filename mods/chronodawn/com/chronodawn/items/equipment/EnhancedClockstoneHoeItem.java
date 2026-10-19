package com.chronodawn.items.equipment;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item.Properties;

public class EnhancedClockstoneHoeItem extends HoeItem {
   public EnhancedClockstoneHoeItem(Properties properties) {
      super(EnhancedClockstoneTier.INSTANCE, properties.attributes(HoeItem.createAttributes(EnhancedClockstoneTier.INSTANCE, 0.0F, 0.0F)));
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(1200);
   }
}
