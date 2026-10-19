package com.chronodawn.items.consumables;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class ChronoMelonSliceItem extends Item {
   public ChronoMelonSliceItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      FoodProperties foodProperties = new Builder().nutrition(2).saturationModifier(0.3F).fast().build();
      return new Properties().food(foodProperties);
   }
}
