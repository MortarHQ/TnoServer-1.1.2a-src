package com.chronodawn.items.equipment;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Item.Properties;

public class EnhancedClockstonePickaxeItem extends PickaxeItem {
   public EnhancedClockstonePickaxeItem(Properties properties) {
      super(EnhancedClockstoneTier.INSTANCE, properties.attributes(PickaxeItem.createAttributes(EnhancedClockstoneTier.INSTANCE, 1.0F, -2.8F)));
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(1200);
   }
}
