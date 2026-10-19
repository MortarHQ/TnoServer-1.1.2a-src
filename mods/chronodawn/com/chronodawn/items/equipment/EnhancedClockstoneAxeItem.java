package com.chronodawn.items.equipment;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item.Properties;

public class EnhancedClockstoneAxeItem extends AxeItem {
   public EnhancedClockstoneAxeItem(Properties properties) {
      super(EnhancedClockstoneTier.INSTANCE, properties.attributes(AxeItem.createAttributes(EnhancedClockstoneTier.INSTANCE, 6.0F, -3.0F)));
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(1200);
   }
}
