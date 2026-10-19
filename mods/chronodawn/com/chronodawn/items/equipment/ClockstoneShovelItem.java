package com.chronodawn.items.equipment;

import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Item.Properties;

public class ClockstoneShovelItem extends ShovelItem {
   public ClockstoneShovelItem(Properties properties) {
      super(ClockstoneTier.INSTANCE, properties.attributes(ShovelItem.createAttributes(ClockstoneTier.INSTANCE, 1.5F, -3.0F)));
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(450);
   }
}
