package com.chronodawn.items.consumables;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class GoldenTimeWheatItem extends Item {
   public GoldenTimeWheatItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      FoodProperties foodProperties = new Builder()
         .nutrition(4)
         .saturationModifier(1.2F)
         .effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1), 1.0F)
         .effect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 1), 1.0F)
         .alwaysEdible()
         .build();
      return new Properties().food(foodProperties);
   }
}
