package com.chronodawn.items.consumables;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class EnhancedTimeBreadItem extends Item {
   public EnhancedTimeBreadItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      FoodProperties foodProperties = new Builder()
         .nutrition(7)
         .saturationModifier(0.8F)
         .effect(new MobEffectInstance(MobEffects.SATURATION, 100, 0), 1.0F)
         .build();
      return new Properties().food(foodProperties);
   }
}
