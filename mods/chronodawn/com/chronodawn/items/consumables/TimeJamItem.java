package com.chronodawn.items.consumables;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class TimeJamItem extends Item {
   public TimeJamItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      FoodProperties foodProperties = new Builder()
         .nutrition(4)
         .saturationModifier(0.5F)
         .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0), 1.0F)
         .build();
      return new Properties().food(foodProperties);
   }
}
