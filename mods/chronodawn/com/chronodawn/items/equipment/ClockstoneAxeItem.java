package com.chronodawn.items.equipment;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item.Properties;

public class ClockstoneAxeItem extends AxeItem {
   public ClockstoneAxeItem(Properties properties) {
      super(ClockstoneTier.INSTANCE, properties.attributes(AxeItem.createAttributes(ClockstoneTier.INSTANCE, 6.0F, -3.1F)));
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(450);
   }
}
