package com.chronodawn.items.equipment;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;

public class ClockstoneSwordItem extends SwordItem {
   public ClockstoneSwordItem(Properties properties) {
      super(ClockstoneTier.INSTANCE, properties.attributes(SwordItem.createAttributes(ClockstoneTier.INSTANCE, 3, -2.4F)));
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(450);
   }
}
