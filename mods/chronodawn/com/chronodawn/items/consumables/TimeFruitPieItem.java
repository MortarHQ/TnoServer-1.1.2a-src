package com.chronodawn.items.consumables;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class TimeFruitPieItem extends Item {
   public TimeFruitPieItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      FoodProperties foodProperties = new Builder()
         .nutrition(8)
         .saturationModifier(0.8F)
         .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 600, 1), 1.0F)
         .build();
      return new Properties().food(foodProperties);
   }
}
