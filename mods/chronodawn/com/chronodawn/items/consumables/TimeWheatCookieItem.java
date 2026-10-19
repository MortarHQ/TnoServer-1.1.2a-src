package com.chronodawn.items.consumables;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class TimeWheatCookieItem extends Item {
   public TimeWheatCookieItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      FoodProperties foodProperties = new Builder().nutrition(2).saturationModifier(0.4F).fast().build();
      return new Properties().food(foodProperties);
   }
}
