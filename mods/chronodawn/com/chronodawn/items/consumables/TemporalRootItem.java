package com.chronodawn.items.consumables;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;

public class TemporalRootItem extends ItemNameBlockItem {
   public TemporalRootItem(Properties properties) {
      super((Block)ModBlocks.TEMPORAL_ROOT.get(), properties);
   }

   public static Properties createProperties() {
      FoodProperties foodProperties = new Builder().nutrition(3).saturationModifier(0.6F).build();
      return new Properties().food(foodProperties);
   }
}
