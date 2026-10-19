package com.chronodawn.items.consumables;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;

public class TimelessMushroomItem extends ItemNameBlockItem {
   public TimelessMushroomItem(Properties properties) {
      super((Block)ModBlocks.TIMELESS_MUSHROOM.get(), properties);
   }

   public static Properties createProperties() {
      FoodProperties foodProperties = new Builder().nutrition(2).saturationModifier(0.3F).build();
      return new Properties().food(foodProperties);
   }
}
