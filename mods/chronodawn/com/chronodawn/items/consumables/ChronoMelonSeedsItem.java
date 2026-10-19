package com.chronodawn.items.consumables;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;

public class ChronoMelonSeedsItem extends ItemNameBlockItem {
   public ChronoMelonSeedsItem(Properties properties) {
      super((Block)ModBlocks.CHRONO_MELON_STEM.get(), properties);
   }

   public static Properties createProperties() {
      return new Properties();
   }
}
