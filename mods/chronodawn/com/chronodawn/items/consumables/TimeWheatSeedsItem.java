package com.chronodawn.items.consumables;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;

public class TimeWheatSeedsItem extends ItemNameBlockItem {
   public TimeWheatSeedsItem(Properties properties) {
      super((Block)ModBlocks.TIME_WHEAT.get(), properties);
   }

   public static Properties createProperties() {
      return new Properties();
   }
}
