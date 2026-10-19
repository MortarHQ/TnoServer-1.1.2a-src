package com.chronodawn.items.consumables;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class ClockworkCookieItem extends Item {
   public ClockworkCookieItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      FoodProperties foodProperties = new Builder()
         .nutrition(2)
         .saturationModifier(0.4F)
         .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 0), 1.0F)
         .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0), 1.0F)
         .fast()
         .build();
      return new Properties().food(foodProperties);
   }
}
