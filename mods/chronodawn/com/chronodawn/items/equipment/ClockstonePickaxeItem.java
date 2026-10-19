package com.chronodawn.items.equipment;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Item.Properties;

public class ClockstonePickaxeItem extends PickaxeItem {
   public ClockstonePickaxeItem(Properties properties) {
      super(ClockstoneTier.INSTANCE, properties.attributes(PickaxeItem.createAttributes(ClockstoneTier.INSTANCE, 1.0F, -2.8F)));
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(450);
   }
}
