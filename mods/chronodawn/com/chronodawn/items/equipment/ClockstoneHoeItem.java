package com.chronodawn.items.equipment;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item.Properties;

public class ClockstoneHoeItem extends HoeItem {
   public ClockstoneHoeItem(Properties properties) {
      super(ClockstoneTier.INSTANCE, properties.attributes(HoeItem.createAttributes(ClockstoneTier.INSTANCE, -2.0F, -1.0F)));
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(450);
   }
}
